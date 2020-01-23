package org.ahocorasick.trie;

import org.ahocorasick.interval.IntervalTree;
import org.ahocorasick.interval.Intervalable;
import org.ahocorasick.trie.State.Tuple;

import com.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * Based on the Aho-Corasick white paper, Bell technologies:
 * ftp://163.13.200.222/assistant/bearhero/prog/%A8%E4%A5%A6/ac_bm.pdf
 * 
 * @author Robert Bor
 */
public class Trie {

	private TrieConfig trieConfig;

	public State rootState;

	public Trie(TrieConfig trieConfig) {
		this.trieConfig = trieConfig;
		this.rootState = new State();
	}

	public Trie() {
		this(new TrieConfig());
	}

	public Trie caseInsensitive() {
		this.trieConfig.setCaseInsensitive(true);
		return this;
	}

	public Trie removeOverlaps() {
		this.trieConfig.setAllowOverlaps(false);
		return this;
	}

	public Trie onlyWholeWords() {
		this.trieConfig.setOnlyWholeWords(true);
		return this;
	}

	void addKeyword(String keyword, String value) {
		if (keyword == null || keyword.length() == 0) {
			return;
		}
		State currentState = this.rootState;
		for (char character : keyword.toCharArray()) {
			currentState = currentState.addState(character);
		}
		currentState.addEmit(new State.Tuple(keyword.length(), value));
	}

	public void update(String keyword, String value) {
		if (keyword == null || keyword.length() == 0) {
			return;
		}

//		if (keyword.equals("genesis"))
//			System.out.printf("update %s = %s\n", keyword, value);

		ArrayList<State.Transition> start = new ArrayList<State.Transition>();

		State currentState = this.rootState;
		for (int i = 0; i < keyword.length(); ++i) {
			char character = keyword.charAt(i);

			currentState = currentState.updateState(character, start);
		}
		currentState.updateEmit(new State.Tuple(keyword.length(), value));

		updateFailureStates(start, keyword);
	}

	public void erase(String keyword) {
		if (keyword == null || keyword.length() == 0) {
			return;
		}

//		if (keyword.equals("意思"))
//			System.out.printf("delete %s\n", keyword);

		State currentState = this.rootState;
		Stack<State> parent = new Stack<State>();
		for (int i = 0; i < keyword.length(); ++i) {
			char character = keyword.charAt(i);

			parent.push(currentState);
			currentState = currentState.nextStateIgnoreRootState(character);
			if (currentState == null)
				return;
		}

		currentState.deleteEmit(keyword.length());

		char character = 0;
		int numOfDeletion = 0;
		for (int i = keyword.length() - 1; i >= 0; --i) {
			if (!currentState.success.isEmpty())
				break;

			if (currentState.emits != null) {
				boolean tobebroken = false;
				for (Tuple tuple : currentState.emits) {
					if (tuple.char_length == i + 1) {
						tobebroken = true;
						break;
					}
				}
				if (tobebroken) {
					break;
				}
			}
			character = keyword.charAt(i);
			currentState = parent.pop();
			currentState.success.remove(character);
			++numOfDeletion;
		}

		deleteFailureStates(currentState, character, keyword, numOfDeletion);
	}

	public void build(Map<String, String> map) {
		for (Entry<String, String> entry : map.entrySet()) {
			this.addKeyword(entry.getKey(), entry.getValue());
		}
		this.constructFailureStates();
	}

	public Collection<Token> tokenize(String text) {

		Collection<Token> tokens = new ArrayList<Token>();

		Collection<Emit> collectedEmits = parseText(text);
		int lastCollectedPosition = -1;
		for (Emit emit : collectedEmits) {
			if (emit.getStart() - lastCollectedPosition > 1) {
				tokens.add(createFragment(emit, text, lastCollectedPosition));
			}
			tokens.add(createMatch(emit, text));
			lastCollectedPosition = emit.getEnd();
		}
		if (text.length() - lastCollectedPosition > 1) {
			tokens.add(createFragment(null, text, lastCollectedPosition));
		}

		return tokens;
	}

	private Token createFragment(Emit emit, String text, int lastCollectedPosition) {
		return new FragmentToken(
				text.substring(lastCollectedPosition + 1, emit == null ? text.length() : emit.getStart()));
	}

	private Token createMatch(Emit emit, String text) {
		return new MatchToken(text.substring(emit.getStart(), emit.getEnd() + 1), emit);
	}

	@SuppressWarnings("unchecked")
	public Collection<Emit> parseText(String text) {

		int position = 0;
		State currentState = this.rootState;
		List<Emit> collectedEmits = new ArrayList<Emit>();
		for (char character : text.toCharArray()) {
			if (trieConfig.isCaseInsensitive()) {
				character = Character.toLowerCase(character);
			}
			currentState = getState(currentState, character);
			storeEmits(++position, currentState, collectedEmits);
		}

		if (trieConfig.isOnlyWholeWords()) {
			removePartialMatches(text, collectedEmits);
		}

		if (!trieConfig.isAllowOverlaps()) {
			IntervalTree intervalTree = new IntervalTree((List<Intervalable>) (List<?>) collectedEmits);
			intervalTree.removeOverlaps((List<Intervalable>) (List<?>) collectedEmits);
		}

		return collectedEmits;
	}

	private void removePartialMatches(String searchText, List<Emit> collectedEmits) {
		long size = searchText.length();
		List<Emit> removeEmits = new ArrayList<Emit>();
		for (Emit emit : collectedEmits) {
			if ((emit.getStart() == 0 || !Character.isAlphabetic(searchText.charAt(emit.getStart() - 1)))
					&& (emit.getEnd() + 1 == size || !Character.isAlphabetic(searchText.charAt(emit.getEnd() + 1)))) {
				continue;
			}
			removeEmits.add(emit);
		}

		for (Emit removeEmit : removeEmits) {
			collectedEmits.remove(removeEmit);
		}
	}

	static State getState(State currentState, char transition) {
		for (;;) {
			State state = currentState.nextState(transition);
			if (state != null)
				return state;
			currentState = currentState.failure;
		}
	}

	void constructFailureStates() {
		Queue<State> queue = new LinkedBlockingDeque<State>();

		// First, set the fail state of all depth 1 states to the root state
		for (State depthOneState : rootState.getStates()) {
			depthOneState.failure = rootState;
			queue.add(depthOneState);
		}

		// Second, determine the fail state for all depth > 1 state
		while (!queue.isEmpty()) {
			State currentState = queue.remove();

			for (Entry<Character, State> tuple : currentState.success.entrySet()) {
				State targetState = tuple.getValue();
				queue.add(targetState);

				State newFailureState = State.newFailureState(currentState, tuple.getKey());
				targetState.failure = newFailureState;
				targetState.addEmit(newFailureState.emit());
			}
		}
	}

	void updateFailureStates(List<State.Transition> queue, String keyword) {
		for (State.Transition transit : queue) {
			transit.set_failure();
		}

		List<State> list;
		State.Transition keywordHead = null;

		if (!queue.isEmpty()) {
			keywordHead = queue.get(0);
			if (keywordHead.parent.depth != 0) {
				keywordHead = null;
			}
		}

		State rootState = this.rootState;

		if (keywordHead != null) {
			list = keywordHead.parent.locate_state(keywordHead.character);
		} else {
			String _keyword;
			if (queue.isEmpty()) {
				int mid = keyword.length();
				_keyword = keyword.substring(mid - 1);
			} else {
				int mid = keyword.length() - (queue.size() - 1);
				_keyword = keyword.substring(mid - 1);
				keyword = keyword.substring(0, mid);
			}

			list = rootState.locate_state(keyword);

			for (int i = 0; i < keyword.length() - 1; ++i) {
				rootState = rootState.success.get(keyword.charAt(i));
			}
			keyword = _keyword;
		}

		State.constructFailureStates(list, rootState, keyword);
	}

	void deleteFailureStates(State parent, char character, String keyword, int numOfDeletion) {
		int char_length = keyword.length();
		State rootState = this.rootState;
		List<State> list;
		if (parent.depth == 0) {
			list = parent.locate_state(character);
		} else {
			int mid = keyword.length() - numOfDeletion;
			String _keyword = keyword.substring(mid - 1);
			if (keyword.isEmpty() || keyword.length() < mid)
				return;

			keyword = keyword.substring(0, mid);
			list = rootState.locate_state(keyword);

			keyword = _keyword;
		}

		State.deleteFailureStates(list, keyword, char_length);
	}

	void storeEmits(int position, State currentState, List<Emit> collectedEmits) {
		Collection<State.Tuple> emits = currentState.emit();
		if (emits != null && !emits.isEmpty()) {
			for (State.Tuple emit : emits) {
				collectedEmits.add(new Emit(position - emit.char_length, position, emit.value));
			}
		}
	}
}
