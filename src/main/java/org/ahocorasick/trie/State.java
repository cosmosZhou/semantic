package org.ahocorasick.trie;

import java.util.*;
import java.util.Map.Entry;

import com.util.Utility;

/**
 * <p>
 * A state has various important tasks it must attend to:
 * </p>
 *
 * <ul>
 * <li>success; when a character points to another state, it must return that
 * state</li>
 * <li>failure; when a character has no matching state, the algorithm must be
 * able to fall back on a state with less depth</li>
 * <li>emits; when this state is passed and keywords have been matched, the
 * matches must be 'emitted' so that they can be used later on.</li>
 * </ul>
 *
 * <p>
 * The root state is special in the sense that it has no failure state; it
 * cannot fail. If it 'fails' it will still parse the next character and start
 * from the root node. This ensures that the algorithm always runs. All other
 * states always have a fail state.
 * </p>
 *
 * @author Robert Bor
 */
public class State {

	/** effective the size of the keyword */
	public final int depth;

	/**
	 * referred to in the white paper as the 'goto' structure. From a state it is
	 * possible to go to other states, depending on the character passed.
	 */
//	public Map<Character, State> success = new TreeMap<Character, State>();
	public Map<Character, State> success = new HashMap<Character, State>();

	/** if no matching states are found, the failure state will be returned */
	public State failure = null;

	/**
	 * whenever this state is reached, it will emit the matches keywords for future
	 * reference
	 */

	static class Tuple {
		public Tuple(int keyword_length, String value) {
			this.char_length = keyword_length;
			this.value = value;
		}

		public int char_length;
		public String value;
	}

	@Override
	public boolean equals(Object _obj) {
		State obj = (State) _obj;
		if (depth != obj.depth)
			return false;

		if (!success.equals(obj.success))
			return false;

		if (failure == null) {
			if (obj.failure != null)
				return false;
		} else {
			if (obj.failure == null)
				return false;
			if (failure.depth != obj.failure.depth)
				return false;
		}

		if (emits == null) {
			if (obj.emits != null)
				return false;
		} else {
			if (obj.emits == null)
				return false;
			if (emits.size() != obj.emits.size())
				return false;
		}

		return true;
	}

	public List<Tuple> emits = null;

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
				if (state.failure != null && state.failure.depth != 0) {
					node.value += String.valueOf(state.failure.depth);
				}
				if (state.emits != null) {
					node.value += state.emits.size() > 1 ? String.format("*%d", state.emits.size()) : '+';
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
				if (state.failure != null && state.failure.depth != 0) {
					node.value += String.valueOf(state.failure.depth);
				}
				if (state.emits != null) {
					node.value += state.emits.size() > 1 ? String.format("*%d", state.emits.size()) : '+';
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

	public State() {
		this(0);
	}

	public State(int depth) {
		this.depth = depth;
	}

	public State nextState(char character) {
		State nextState = success.get(character);
		if (nextState == null && depth == 0)
			return this;

		return nextState;
	}

	public State nextStateIgnoreRootState(char character) {
		return success.get(character);
	}

	public State addState(char character) {
		State nextState = nextStateIgnoreRootState(character);
		if (nextState == null) {
			nextState = new State(this.depth + 1);
			this.success.put(character, nextState);
		}
		return nextState;
	}

	void locate_state(char ch, List<State> list) {
		for (Entry<Character, State> tuple : success.entrySet()) {
			State state = tuple.getValue();
			if (tuple.getKey() == ch && state.depth > 1) {
				list.add(this);
			}
			state.locate_state(ch, list);
		}
	}

	void locate_state(String prefix, String keyword, List<State> list) {
		for (Entry<Character, State> tuple : success.entrySet()) {
			State state = tuple.getValue();
			String newPrefix = prefix + tuple.getKey();

			if (newPrefix.equals(keyword) && state.depth > keyword.length()) {
				list.add(this);
			}

			for (;;) {
				if (keyword.startsWith(newPrefix) && newPrefix.length() < keyword.length()) {
					state.locate_state(newPrefix, keyword, list);
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
		locate_state(ch, list);
		return list;
	}

	List<State> locate_state(String keyword) {
		List<State> list = new ArrayList<State>();
		this.locate_state("", keyword, list);
		return list;
	}

	static class Transition {
		char character;
		State parent;

		public Transition(char character, State state) {
			this.character = character;
			this.parent = state;
		}

		State node() {
			return parent.nextStateIgnoreRootState(character);
		}

		void set_failure() {
			if (parent.depth == 0) {
				parent.nextStateIgnoreRootState(character).failure = parent;
			} else {
				State targetState = parent.nextStateIgnoreRootState(character);
				State newFailureState = newFailureState(parent, character);
				targetState.failure = newFailureState;
				targetState.addEmit(newFailureState.emit());
			}
		}

	}

	static void constructFailureStates(List<State> list, State rootState, String keyword) {
		char character = keyword.charAt(0);
		for (State parent : list) {
			State state = parent.success.get(character);
			state.constructFailureStates(parent, rootState, keyword);
		}
	}

	static void deleteFailureStates(List<State> list, String keyword, int char_length) {
		char character = keyword.charAt(0);
		for (State parent : list) {
			State state = parent.success.get(character);
			state.deleteFailureStates(parent, keyword, char_length);
		}
	}

	static State newFailureState(State currentState, char transition) {
		State state;
		do {
			currentState = currentState.failure;
			state = currentState.nextState(transition);
		} while (state == null);

		return state;
	}

	public State updateState(char character, List<Transition> queue) {
		State nextState = nextStateIgnoreRootState(character);
		if (nextState == null) {
			nextState = new State(depth + 1);
			success.put(character, nextState);
			queue.add(new Transition(character, this));
		}
		return nextState;
	}

	public void addEmit(Tuple tuple) {
		if (this.emits == null) {
			this.emits = new ArrayList<Tuple>();
		}
		this.emits.add(tuple);
	}

	public void updateEmit(Tuple tuple) {
		if (this.emits == null) {
			this.emits = new ArrayList<Tuple>();
		}

		for (Tuple t : emits) {
			if (t.char_length == tuple.char_length) {
				t.value = tuple.value;
				return;
			}
		}

		emits.add(tuple);
	}

	public void deleteEmit(int char_length) {
		if (this.emits == null) {
			return;
		}

		for (Tuple t : emits) {
			if (t.char_length == char_length) {
				emits.remove(t);
				if (emits.isEmpty()) {
					emits = null;
				}
				break;
			}
		}
	}

	public void addEmit(Collection<Tuple> emits) {
		for (Tuple emit : emits) {
			addEmit(emit);
		}
	}

	public void updateEmit(Collection<Tuple> emits) {
		for (Tuple emit : emits) {
			updateEmit(emit);
		}
	}

	public Collection<Tuple> emit() {
		return this.emits == null ? Collections.<Tuple>emptyList() : this.emits;
	}

	public Collection<State> getStates() {
		return this.success.values();
	}

	public Collection<Character> getTransitions() {
		return this.success.keySet();
	}

	public boolean update_failure(State parent, char ch) {
		State newFailureState = State.newFailureState(parent, ch);
		if (failure == newFailureState) {
			return false;
		}

		failure = newFailureState;
		updateEmit(newFailureState.emit());
		return true;
	}

	public boolean delete_failure(State parent, char ch) {
		State newFailureState = State.newFailureState(parent, ch);
		if (failure == newFailureState) {
			return false;
		}

		failure = newFailureState;
		return true;
	}

	public State update_failure(State parent, char ch, State keywordNode) {
		State newFailureState = State.newFailureState(parent, ch);
		if (failure == newFailureState) {
			if (keywordNode != null)
				updateEmit(keywordNode.emit());
			return failure;
		}

		failure = newFailureState;
		updateEmit(newFailureState.emit());
		return null;
	}

	void constructFailureStates(State parent, State rootState, String keyword) {
		char character = keyword.charAt(0);
		rootState = rootState.nextStateIgnoreRootState(character);

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

	void constructFailureStates_(State parent, State rootState, String keyword) {
		char character = keyword.charAt(0);
		rootState = rootState.nextStateIgnoreRootState(character);

		keyword = keyword.substring(1);

		if (failure.depth <= rootState.depth) {
			failure = rootState;
		}
		updateEmit(rootState.emit());

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
			deleteEmit(char_length);
		} else {
			State state = success.get(keyword.charAt(0));
			if (state != null) {
				state.deleteFailureStates(this, keyword, char_length);
			}
		}
	}
}
