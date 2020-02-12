package org.ahocorasick.trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingDeque;

import com.util.Utility;

/**
 *
 * Based on the Aho-Corasick white paper, Bell technologies:
 * ftp://163.13.200.222/assistant/bearhero/prog/%A8%E4%A5%A6/ac_bm.pdf
 * 
 * @author Robert Bor
 */
public class Trie<V> {

	static public class TrieConfig {

		boolean allowOverlaps = true;

		boolean onlyWholeWords = false;

		boolean caseInsensitive = false;

		public boolean isAllowOverlaps() {
			return allowOverlaps;
		}

		public void setAllowOverlaps(boolean allowOverlaps) {
			this.allowOverlaps = allowOverlaps;
		}

		public boolean isOnlyWholeWords() {
			return onlyWholeWords;
		}

		public void setOnlyWholeWords(boolean onlyWholeWords) {
			this.onlyWholeWords = onlyWholeWords;
		}

		public boolean isCaseInsensitive() {
			return caseInsensitive;
		}

		public void setCaseInsensitive(boolean caseInsensitive) {
			this.caseInsensitive = caseInsensitive;
		}
	}

	public static class Emit<V> {
		Emit(int char_length, V value) {
			this.char_length = char_length;
			this.value = value;
		}

		/**
		 * the length of every key
		 */
		int char_length;

		/**
		 * outer value
		 */
		V value;
	}

	Emit<V> emit[];

	public State root;

	TrieConfig trieConfig;

	public Trie(TrieConfig trieConfig) {
		this.trieConfig = trieConfig;
		this.root = new State();
	}

	public Trie() {
		this(new TrieConfig());
	}

	public Trie<V> caseInsensitive() {
		this.trieConfig.setCaseInsensitive(true);
		return this;
	}

	public Trie<V> removeOverlaps() {
		this.trieConfig.setAllowOverlaps(false);
		return this;
	}

	public Trie<V> onlyWholeWords() {
		this.trieConfig.setOnlyWholeWords(true);
		return this;
	}

	public void put(String keyword, V value) {
		ArrayList<Transition> queue = new ArrayList<Transition>();

		State currentState = this.root;
		for (int i = 0; i < keyword.length(); ++i) {
			char character = keyword.charAt(i);

			currentState = currentState.addState(character, queue);
		}

		if (queue.isEmpty() && currentState.containsKey(keyword.length())) {
			assert currentState.lastKey() == keyword.length();
			this.emit[currentState.lastValue()].value = value;
			return;
		}

		this.emit = Utility.copier(emit, new Emit<V>(keyword.length(), value));
		currentState.addEmit(this.emit.length - 1);

		updateFailureStates(queue, keyword);
	}

	public void remove(String keyword) {
		State currentState = this.root;
		Stack<State> parent = new Stack<State>();
		for (int i = 0; i < keyword.length(); ++i) {
			char character = keyword.charAt(i);

			parent.push(currentState);
			currentState = currentState.success.get(character);
			if (currentState == null)
				return;
		}

		int index = currentState.remove(keyword.length());
		if (index < 0)
			return;

		swap(index, emit.length - 1);

		char character = 0;
		int numOfDeletion = 0;
		for (int i = keyword.length() - 1; i >= 0; --i) {
			if (!currentState.success.isEmpty() || currentState.containsKey(i + 1))
				break;

			character = keyword.charAt(i);
			currentState = parent.pop();
			currentState.success.remove(character);
			++numOfDeletion;
		}

		deleteFailureStates(character, keyword, numOfDeletion);

		emit = Arrays.copyOf(emit, emit.length - 1);
	}

	void swap(int first, int second) {
		Utility.swap(emit, first, second);
		root.correctIndex(first, second);
		root.checkValidity();
	}

	@SuppressWarnings("unchecked")
	public Trie(Map<String, V> map) {
		this();

		emit = new Emit[map.size()];
		int value_index = 0;
		for (Entry<String, V> entry : map.entrySet()) {
			String keyword = entry.getKey();

			State currentState = this.root;
			for (char character : keyword.toCharArray()) {
				currentState = currentState.addState(character);
			}
			emit[value_index] = new Emit<V>(keyword.length(), entry.getValue());
			currentState.addEmit(value_index);

			++value_index;
		}

		constructFailureStates();
	}

	public Collection<Token<V>> tokenize(String text) {

		Collection<Token<V>> tokens = new ArrayList<Token<V>>();

		Collection<Hit<V>> collectedEmits = parseText(text);
		int lastCollectedPosition = -1;
		for (Hit<V> emit : collectedEmits) {
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

	Token<V> createFragment(Hit<V> emit, String text, int lastCollectedPosition) {
		return new FragmentToken<V>(
				text.substring(lastCollectedPosition + 1, emit == null ? text.length() : emit.getStart()));
	}

	Token<V> createMatch(Hit<V> emit, String text) {
		return new MatchToken<V>(text.substring(emit.getStart(), emit.getEnd() + 1), emit);
	}

	@SuppressWarnings("unchecked")
	public Collection<Hit<V>> parseText(String text) {

		int position = 0;
		State currentState = this.root;
		List<Hit<V>> collectedEmits = new ArrayList<Hit<V>>();
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

	void removePartialMatches(String searchText, List<Hit<V>> collectedEmits) {
		long size = searchText.length();
		List<Hit<V>> removeEmits = new ArrayList<Hit<V>>();
		for (Hit<V> emit : collectedEmits) {
			if ((emit.getStart() == 0 || !Character.isAlphabetic(searchText.charAt(emit.getStart() - 1)))
					&& (emit.getEnd() + 1 == size || !Character.isAlphabetic(searchText.charAt(emit.getEnd() + 1)))) {
				continue;
			}
			removeEmits.add(emit);
		}

		for (Hit<V> removeEmit : removeEmits) {
			collectedEmits.remove(removeEmit);
		}
	}

	State getState(State currentState, char transition) {
		for (;;) {
			State state = currentState.nextState(transition);
			if (state != null)
				return state;
			currentState = currentState.failure;
		}
	}

	void constructFailureStates() {
		Queue<State> queue = new LinkedBlockingDeque<State>();

//		root.failure = root;
		// First, set the fail state of all depth 1 states to the root state
		for (State depthOneState : root.getStates()) {
			depthOneState.failure = root;
			queue.add(depthOneState);
		}

		// Second, determine the fail state for all depth > 1 state
		while (!queue.isEmpty()) {
			State currentState = queue.remove();

			for (Entry<Character, State> tuple : currentState.success.entrySet()) {
				State targetState = tuple.getValue();
				queue.add(targetState);

				State newFailureState = newFailureState(currentState, tuple.getKey());
				targetState.failure = newFailureState;
				targetState.addEmit(newFailureState.emit);
			}
		}
	}

	void updateFailureStates(List<Transition> queue, String keyword) {
		for (Transition transit : queue) {
			transit.set_failure();
		}

		List<State> list;
		Transition keywordHead = null;

		if (!queue.isEmpty()) {
			keywordHead = queue.get(0);
			if (!keywordHead.parent.isRoot()) {
				keywordHead = null;
			}
		}

		State rootState = this.root;

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

		constructFailureStates(list, rootState, keyword);
	}

	void deleteFailureStates(char character, String keyword, int numOfDeletion) {
		int char_length = keyword.length();
		List<State> list;
		int mid = keyword.length() - numOfDeletion;
		if (mid == 0) {
			list = root.locate_state(character);
		} else {
			String _keyword = keyword.substring(mid - 1);
			if (keyword.isEmpty() || keyword.length() < mid)
				return;

			keyword = keyword.substring(0, mid);
			list = root.locate_state(keyword);

			keyword = _keyword;
		}

		deleteFailureStates(list, keyword, char_length);
	}

	void storeEmits(int position, State currentState, List<Hit<V>> collectedEmits) {
		if (currentState.emit != null)
			for (int hit : currentState.emit) {
				collectedEmits.add(new Hit<V>(position - this.emit[hit].char_length, position, this.emit[hit].value));
			}
	}

	class Transition {
		char character;
		State parent;

		public Transition(char character, State state) {
			this.character = character;
			this.parent = state;
		}

		State node() {
			return parent.success.get(character);
		}

		void set_failure() {
			if (parent.isRoot()) {
				parent.success.get(character).failure = parent;
			} else {
				State targetState = parent.success.get(character);
				State newFailureState = newFailureState(parent, character);
				targetState.failure = newFailureState;

				targetState.addEmit(newFailureState.emit);
			}
		}

	}

	public class State {
		int depth() {
			if (this.success.isEmpty()) {
				assert emit != null;
				return lastKey();
			}

			return success.entrySet().iterator().next().getValue().depth() - 1;
		}

		void correctIndex(int first, int second) {
			if (emit != null) {
				int first_index = Utility.indexOf(emit, first);
				int second_index = Utility.indexOf(emit, second);
				boolean needMofification = false;
				if (first_index >= 0) {
					emit[first_index] = second;
					needMofification = true;
				}
				if (second_index >= 0) {
					emit[second_index] = first;
					needMofification = true;
				}

				if (needMofification)
					emit = toArray(Trie.this.toTreeMap(emit));
			}

			for (State state : this.success.values()) {
				state.correctIndex(first, second);
			}
		}

		void checkValidity() {
			if (emit != null) {
				for (int i = 1; i < emit.length; ++i) {
					assert Trie.this.emit[emit[i - 1]].char_length < Trie.this.emit[emit[i]].char_length;
				}
			}
			for (State state : this.success.values()) {
				state.checkValidity();
			}
		}

		/**
		 * referred to in the white paper as the 'goto' structure. From a state it is
		 * possible to go to other states, depending on the character passed.
		 */
//		public Map<Character, State> success = new TreeMap<Character, State>();
		public Map<Character, State> success = new HashMap<Character, State>();

		/** if no matching states are found, the failure state will be returned */
		public State failure;

		boolean isRoot() {
			return root == this;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object _obj) {
			State obj = (State) _obj;
//			if (depth != obj.depth)
//				return false;

			if (!success.equals(obj.success))
				return false;

			if (failure == null) {
				if (obj.failure != null)
					return false;
			} else {
				if (obj.failure == null)
					return false;
				if (failure.depth() != obj.failure.depth())
					return false;
			}

			if (!toTreeMap().equals(obj.toTreeMap()))
				return false;

			return true;
		}

//		public TreeMap<Integer, Integer> emit = new TreeMap<Integer, Integer>();
		public int emit[];

		public Utility.TextTreeNode toShadowTree() {
			Utility.TextTreeNode newNode = new Utility.TextTreeNode("");
			int x_length = success.size() / 2;
			int y_length = success.size() - x_length;
			char[] list = new char[success.size()];

			int i = 0;
			for (char ch : success.keySet()) {
				list[i++] = ch;
			}

			// tree node
			if (x_length > 0) {
				newNode.x = new Utility.TextTreeNode[x_length];
				for (i = 0; i < x_length; ++i) {
					char word = list[i];
					State state = success.get(word);
					Utility.TextTreeNode node = state.toShadowTree();

					newNode.x[i] = node;
					node.value = String.valueOf(word);
					if (state.failure != null && !state.failure.isRoot()) {
						node.value += String.valueOf(state.failure.depth());
					}
					if (state.emit != null) {
						node.value += state.emit.length > 1 ? String.format("*%d", state.emit.length) : '*';
					}
				}
			}
			// allocate node for left child at next level in tree;

			if (y_length > 0) {
				newNode.y = new Utility.TextTreeNode[y_length];
				for (i = x_length; i < success.size(); ++i) {
					char word = list[i];
					State state = success.get(word);
					Utility.TextTreeNode node = state.toShadowTree();

					newNode.y[i - x_length] = node;
					node.value = String.valueOf(word);
					if (state.failure != null && !state.failure.isRoot()) {
						node.value += String.valueOf(state.failure.depth());
					}
					if (state.emit != null) {
						node.value += state.emit.length > 1 ? String.format("*%d", state.emit.length) : '*';
					}
				}
			}

			return newNode;
		}

		@Override
		public String toString() {
			Utility.TextTreeNode root = this.toShadowTree();
			return root.toString();
		}

//		public State() {
//			this(0);
//		}
//		public State(int depth) {
//			this.depth = depth;
//		}

		public State nextState(char character) {
			State nextState = success.get(character);
			if (nextState == null && isRoot())
				return this;

			return nextState;
		}

		public State addState(char character) {
			State nextState = success.get(character);
			if (nextState == null) {
//				nextState = new State(this.depth + 1);
				nextState = new State();
				this.success.put(character, nextState);
			}
			return nextState;
		}

		void locate_state(char ch, List<State> list, int depth) {
			++depth;
			for (Entry<Character, State> tuple : success.entrySet()) {
				State state = tuple.getValue();
				assert state.depth() == depth;
				if (tuple.getKey() == ch && depth > 1) {
					list.add(this);
				}
				state.locate_state(ch, list, depth);
			}
		}

		void locate_state(String prefix, String keyword, List<State> list, int depth) {
			++depth;
			for (Entry<Character, State> tuple : success.entrySet()) {
				State state = tuple.getValue();
				String newPrefix = prefix + tuple.getKey();

				assert state.depth() == depth;

				if (newPrefix.equals(keyword) && depth > keyword.length()) {
					list.add(this);
				}

				for (;;) {
					if (keyword.startsWith(newPrefix) && newPrefix.length() < keyword.length()) {
						state.locate_state(newPrefix, keyword, list, depth);
						break;
					}

					if (newPrefix.isEmpty())
						break;
					newPrefix = newPrefix.substring(1);
				}
			}
		}

		List<State> locate_state(char ch) {
			List<State> list = new ArrayList<State>();
			locate_state(ch, list, 0);
			return list;
		}

		List<State> locate_state(String keyword) {
			List<State> list = new ArrayList<State>();
			locate_state("", keyword, list, 0);
			return list;
		}

		public State addState(char character, List<Transition> queue) {
			State nextState = success.get(character);
			if (nextState == null) {
				nextState = new State();
				success.put(character, nextState);
				queue.add(new Transition(character, this));
			}
			return nextState;
		}

		public Collection<State> getStates() {
			return this.success.values();
		}

		public Collection<Character> getTransitions() {
			return this.success.keySet();
		}

		public boolean update_failure(State parent, char ch) {
			State newFailureState = newFailureState(parent, ch);
			if (failure == newFailureState) {
				return false;
			}

			failure = newFailureState;
			addEmit(newFailureState.emit);
			return true;
		}

		public boolean delete_failure(State parent, char ch) {
			State newFailureState = newFailureState(parent, ch);
			if (failure == newFailureState) {
				return false;
			}

			failure = newFailureState;
			return true;
		}

		public State update_failure(State parent, char ch, State keywordNode) {
			State newFailureState = newFailureState(parent, ch);
			if (failure == newFailureState) {
				if (keywordNode != null)
					addEmit(keywordNode.emit);
				return failure;
			}

			failure = newFailureState;
			addEmit(newFailureState.emit);
			return null;
		}

		void constructFailureStates(State parent, State rootState, String keyword) {
			char character = keyword.charAt(0);
			rootState = rootState.success.get(character);

			keyword = keyword.substring(1);

			boolean failure = true;
			if (!update_failure(parent, character)) {
				parent = update_failure(parent, character, rootState);
				failure = false;
			}

			// Second, determine the fail state for all depth > 1 state

			if (!keyword.isEmpty()) {
				State state = success.get(keyword.charAt(0));
				if (state != null) {
					if (failure)
						state.constructFailureStates(this, rootState, keyword);
					else {
						state.constructFailureStates_(this, rootState, keyword);
					}
				}
			}
		}

		int lastKey() {
			return Trie.this.emit[Utility.last(emit)].char_length;
//			TreeMap<Integer, Integer> map = toTreeMap(emit);
//			return map.lastKey();
		}

		int lastValue() {
			return Utility.last(emit);
//			TreeMap<Integer, Integer> map = toTreeMap(emit);
//			return map.lastEntry().getValue();
		}

		TreeMap<Integer, Object> toTreeMap() {
			TreeMap<Integer, Object> map = new TreeMap<Integer, Object>();
			for (Entry<Integer, Integer> entry : Trie.this.toTreeMap(this.emit).entrySet()) {
				map.put(entry.getKey(), Trie.this.emit[entry.getValue()].value);
			}
			return map;
		}

		boolean containsKey(int char_length) {
			TreeMap<Integer, Integer> map = Trie.this.toTreeMap(emit);

			return map.containsKey(char_length);
		}

		void addEmit(int value_index) {
			TreeMap<Integer, Integer> map = Trie.this.toTreeMap(emit);
			map.put(Trie.this.emit[value_index].char_length, value_index);
			emit = toArray(map);
		}

		void addEmit(int[] emit) {
			TreeMap<Integer, Integer> map = Trie.this.toTreeMap(this.emit);
			map.putAll(Trie.this.toTreeMap(emit));

			this.emit = toArray(map);
		}

		int remove(int char_length) {
//			emit.remove(char_length);
			TreeMap<Integer, Integer> map = Trie.this.toTreeMap(emit);
			Integer index = map.remove(char_length);
			if (index != null) {
				this.emit = toArray(map);
				return index;
			}
			return -1;
		}

		void constructFailureStates_(State parent, State rootState, String keyword) {
			char character = keyword.charAt(0);
			rootState = rootState.success.get(character);

			keyword = keyword.substring(1);

			if (failure.depth() <= rootState.depth()) {
				failure = rootState;
			}

			addEmit(rootState.emit);

			// Second, determine the fail state for all depth > 1 state

			if (!keyword.isEmpty()) {
				State state = success.get(keyword.charAt(0));
				if (state != null) {
					state.constructFailureStates_(this, rootState, keyword);
				}
			}
		}

		void deleteFailureStates(State parent, String keyword, int char_length) {
			char character = keyword.charAt(0);

			keyword = keyword.substring(1);

			delete_failure(parent, character);

			if (keyword.isEmpty()) {
				remove(char_length);
			} else {
				State state = success.get(keyword.charAt(0));
				if (state != null) {
					state.deleteFailureStates(this, keyword, char_length);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object _obj) {
		Trie<V> obj = (Trie<V>) _obj;
		if (!toHashMap().equals(obj.toHashMap()))
			return false;
		return this.root.equals(obj.root);
	}

	static boolean debug = false;

	static class IntervalTree {

		IntervalNode rootNode = null;

		public IntervalTree(List<Intervalable> intervals) {
			this.rootNode = new IntervalNode(intervals);
		}

		public List<Intervalable> removeOverlaps(List<Intervalable> intervals) {

			// Sort the intervals on size, then left-most position
			Collections.sort(intervals, new IntervalableComparatorBySize());

			Set<Intervalable> removeIntervals = new TreeSet<Intervalable>();

			for (Intervalable interval : intervals) {
				// If the interval was already removed, ignore it
				if (removeIntervals.contains(interval)) {
					continue;
				}

				// Remove all overallping intervals
				removeIntervals.addAll(findOverlaps(interval));
			}

			// Remove all intervals that were overlapping
			for (Intervalable removeInterval : removeIntervals) {
				intervals.remove(removeInterval);
			}

			// Sort the intervals, now on left-most position only
			Collections.sort(intervals, new IntervalableComparatorByPosition());

			return intervals;
		}

		public List<Intervalable> findOverlaps(Intervalable interval) {
			return rootNode.findOverlaps(interval);
		}

	}

	public interface Intervalable {

		public int getStart();

		public int getEnd();

		public int size();
	}

	static class IntervalNode {

		private enum Direction {
			LEFT, RIGHT
		}

		private IntervalNode left = null;
		private IntervalNode right = null;
		private int point;
		private List<Intervalable> intervals = new ArrayList<Intervalable>();

		public IntervalNode(List<Intervalable> intervals) {
			this.point = determineMedian(intervals);

			List<Intervalable> toLeft = new ArrayList<Intervalable>();
			List<Intervalable> toRight = new ArrayList<Intervalable>();

			for (Intervalable interval : intervals) {
				if (interval.getEnd() < this.point) {
					toLeft.add(interval);
				} else if (interval.getStart() > this.point) {
					toRight.add(interval);
				} else {
					this.intervals.add(interval);
				}
			}

			if (toLeft.size() > 0) {
				this.left = new IntervalNode(toLeft);
			}
			if (toRight.size() > 0) {
				this.right = new IntervalNode(toRight);
			}
		}

		public int determineMedian(List<Intervalable> intervals) {
			int start = -1;
			int end = -1;
			for (Intervalable interval : intervals) {
				int currentStart = interval.getStart();
				int currentEnd = interval.getEnd();
				if (start == -1 || currentStart < start) {
					start = currentStart;
				}
				if (end == -1 || currentEnd > end) {
					end = currentEnd;
				}
			}
			return (start + end) / 2;
		}

		public List<Intervalable> findOverlaps(Intervalable interval) {

			List<Intervalable> overlaps = new ArrayList<Intervalable>();

			if (this.point < interval.getStart()) { // Tends to the right
				addToOverlaps(interval, overlaps, findOverlappingRanges(this.right, interval));
				addToOverlaps(interval, overlaps, checkForOverlapsToTheRight(interval));
			} else if (this.point > interval.getEnd()) { // Tends to the left
				addToOverlaps(interval, overlaps, findOverlappingRanges(this.left, interval));
				addToOverlaps(interval, overlaps, checkForOverlapsToTheLeft(interval));
			} else { // Somewhere in the middle
				addToOverlaps(interval, overlaps, this.intervals);
				addToOverlaps(interval, overlaps, findOverlappingRanges(this.left, interval));
				addToOverlaps(interval, overlaps, findOverlappingRanges(this.right, interval));
			}

			return overlaps;
		}

		protected void addToOverlaps(Intervalable interval, List<Intervalable> overlaps,
				List<Intervalable> newOverlaps) {
			for (Intervalable currentInterval : newOverlaps) {
				if (!currentInterval.equals(interval)) {
					overlaps.add(currentInterval);
				}
			}
		}

		protected List<Intervalable> checkForOverlapsToTheLeft(Intervalable interval) {
			return checkForOverlaps(interval, Direction.LEFT);
		}

		protected List<Intervalable> checkForOverlapsToTheRight(Intervalable interval) {
			return checkForOverlaps(interval, Direction.RIGHT);
		}

		protected List<Intervalable> checkForOverlaps(Intervalable interval, Direction direction) {

			List<Intervalable> overlaps = new ArrayList<Intervalable>();
			for (Intervalable currentInterval : this.intervals) {
				switch (direction) {
				case LEFT:
					if (currentInterval.getStart() <= interval.getEnd()) {
						overlaps.add(currentInterval);
					}
					break;
				case RIGHT:
					if (currentInterval.getEnd() >= interval.getStart()) {
						overlaps.add(currentInterval);
					}
					break;
				}
			}
			return overlaps;
		}

		protected List<Intervalable> findOverlappingRanges(IntervalNode node, Intervalable interval) {
			if (node != null) {
				return node.findOverlaps(interval);
			}
			return Collections.emptyList();
		}

	}

	static class IntervalableComparatorBySize implements java.util.Comparator<Intervalable> {

		@Override
		public int compare(Intervalable intervalable, Intervalable intervalable2) {
			int comparison = intervalable2.size() - intervalable.size();
			if (comparison == 0) {
				comparison = intervalable.getStart() - intervalable2.getStart();
			}
			return comparison;
		}

	}

	static class IntervalableComparatorByPosition implements java.util.Comparator<Intervalable> {

		@Override
		public int compare(Intervalable intervalable, Intervalable intervalable2) {
			return intervalable.getStart() - intervalable2.getStart();
		}

	}

	public static class Hit<V> extends Interval implements Intervalable {

		public V value;

		public Hit(int start, int end, V value) {
			super(start, end);
			this.value = value;
		}

		@Override
		public String toString() {
			return super.toString() + "=" + this.value;
		}

	}

	static class Interval implements Intervalable {

		private int start;
		private int end;

		public Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}

		public int getStart() {
			return this.start;
		}

		public int getEnd() {
			return this.end;
		}

		public int size() {
			return end - start + 1;
		}

		public boolean overlapsWith(Interval other) {
			return this.start <= other.getEnd() && this.end >= other.getStart();
		}

		public boolean overlapsWith(int point) {
			return this.start <= point && point <= this.end;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Intervalable)) {
				return false;
			}
			Intervalable other = (Intervalable) o;
			return this.start == other.getStart() && this.end == other.getEnd();
		}

		@Override
		public int hashCode() {
			return this.start % 100 + this.end % 100;
		}

		public int compareTo(Object o) {
			if (!(o instanceof Intervalable)) {
				return -1;
			}
			Intervalable other = (Intervalable) o;
			int comparison = this.start - other.getStart();
			return comparison != 0 ? comparison : this.end - other.getEnd();
		}

		@Override
		public String toString() {
			return this.start + ":" + this.end;
		}

	}

	static class MatchToken<V> extends Token<V> {

		private Hit<V> emit;

		public MatchToken(String fragment, Hit<V> emit) {
			super(fragment);
			this.emit = emit;
		}

		@Override
		public boolean isMatch() {
			return true;
		}

		@Override
		public Hit<V> getEmit() {
			// TODO Auto-generated method stub
			return emit;
		}
	}

	static abstract class Token<V> {

		private String fragment;

		public Token(String fragment) {
			this.fragment = fragment;
		}

		public String getFragment() {
			return this.fragment;
		}

		public abstract boolean isMatch();

		public abstract Hit<V> getEmit();

	}

	static class FragmentToken<V> extends Token<V> {

		public FragmentToken(String fragment) {
			super(fragment);
		}

		@Override
		public boolean isMatch() {
			return false;
		}

		@Override
		public Hit<V> getEmit() {
			return null;
		}
	}

	void constructFailureStates(List<State> list, State rootState, String keyword) {
		char character = keyword.charAt(0);
		for (State parent : list) {
			State state = parent.success.get(character);
			state.constructFailureStates(parent, rootState, keyword);
		}
	}

	void deleteFailureStates(List<State> list, String keyword, int char_length) {
		char character = keyword.charAt(0);
		for (State parent : list) {
			State state = parent.success.get(character);
			state.deleteFailureStates(parent, keyword, char_length);
		}
	}

	State newFailureState(State currentState, char transition) {
		State state;
		do {
			currentState = currentState.failure;
			state = currentState.nextState(transition);
		} while (state == null);

		return state;
	}

	TreeMap<Integer, Integer> toTreeMap(int emit[]) {
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();

		if (emit != null)
			for (int index : emit) {
				map.put(this.emit[index].char_length, index);
			}
		return map;
	}

	static int[] toArray(TreeMap<Integer, Integer> map) {
		if (map.isEmpty())
			return null;

		int[] emit = new int[map.size()];
		int i = 0;
		for (int index : map.values()) {
			emit[i++] = index;
		}
		return emit;
	}

	HashMap<Integer, HashSet<V>> toHashMap() {
		HashMap<Integer, HashSet<V>> map = new HashMap<Integer, HashSet<V>>();
		for (Emit<V> e : emit) {
			if (!map.containsKey(e.char_length))
				map.put(e.char_length, new HashSet<V>());
			map.get(e.char_length).add(e.value);
		}

		return map;
	}

	public static void main(String[] args) throws Exception {

		Map<String, String> dictionaryMap = new TreeMap<String, String>();
//		String path = Utility.corpusDirectory() + "ahocorasick/small.txt";
		String path = Utility.corpusDirectory() + "ahocorasick/dictionary.txt";

		ArrayList<String> dictionary = new ArrayList<String>();
		new Utility.Text(path).collect(dictionary);

		for (String word : dictionary) {
			dictionaryMap.put(word, String.format("[%s]", word));
		}

		System.out.println("dictionary.size() = " + dictionaryMap.size());
		String text = new Utility.Text(Utility.corpusDirectory() + "ahocorasick/dictionary.txt").fetchContent()
				+ new Utility.Text(Utility.corpusDirectory() + "ahocorasick/text.txt").fetchContent();

		Collections.shuffle(dictionary, new Random());

		System.out.println("字典词条：" + dictionary.size());

		if (debug) {
			for (String word : dictionary) {
				System.out.println(word);
			}
		}

		long start = System.currentTimeMillis();
		Trie<String> dat = new Trie<String>(dictionaryMap);
		System.out.println("time cost = " + (System.currentTimeMillis() - start));

		if (debug) {
			System.out.println(dat);
		}

		dat.root.checkValidity();
		Collection<Hit<String>> arr = dat.parseText(text);
		start = System.currentTimeMillis();
		Trie<String> _dat = new Trie<String>(dictionaryMap);
		System.out.println("time cost = " + (System.currentTimeMillis() - start));
		String debugWord = null;
		debugWord = "行署";
		for (String word : dictionary) {
			if (debugWord != null && !debugWord.equals(word))
				continue;
//			if (debug)
			System.out.println("removing word: " + word);

			_dat.remove(word);
			_dat.remove(word);
			if (debug) {
				System.out.println(_dat);
			}
			_dat.root.checkValidity();
			_dat.put(word, String.format("[%s]", word));
			_dat.put(word, String.format("[%s]", word));
			if (debug) {
				System.out.println(_dat);
			}
			_dat.root.checkValidity();
			assert dat.equals(_dat);
			Collection<Hit<String>> _arr = _dat.parseText(text);
			assert Utility.equals(arr, _arr);
		}
	}

}
