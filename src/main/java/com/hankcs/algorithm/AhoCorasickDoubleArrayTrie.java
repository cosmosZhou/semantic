/*
 * AhoCorasickDoubleArrayTrie Project
 *      https://github.com/hankcs/AhoCorasickDoubleArrayTrie
 *
 * Copyright 2008-2016 hankcs <me@hankcs.com>
 * You may modify and redistribute as long as this attribution remains.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hankcs.algorithm;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

import org.ahocorasick.trie.Trie;

import com.util.KeyGenerator;
import com.util.Utility;

/**
 * An implementation of Aho Corasick algorithm based on Double Array Trie
 *
 * @author hankcs
 */
public class AhoCorasickDoubleArrayTrie<V> {

	public static class Node {
		int base;
		int check;

		/**
		 * emission table of the Aho Corasick automata
		 */
		int[] emit;

		/**
		 * fail table of the Aho Corasick automata
		 */
		int failure = -1;

		void clear() {
			base = check = 0;
			emit = null;
			failure = -1;
		}
	}

	@Override
	public String toString() {
		return root.toString();
	}

	Node[] node;

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

	/**
	 * Parse text
	 *
	 * @param text The text
	 * @return a list of outputs
	 */
	public List<Hit<V>> parseText(CharSequence text) {
		int position = 1;
		int currentState = 0;
		List<Hit<V>> collectedEmits = new ArrayList<Hit<V>>();
		for (int i = 0; i < text.length(); ++i) {
			currentState = getState(currentState, text.charAt(i));
			storeEmits(position, currentState, collectedEmits);
			++position;
		}

		return collectedEmits;
	}

	/**
	 * Parse text
	 *
	 * @param text      The text
	 * @param processor A processor which handles the output
	 */
	public void parseText(CharSequence text, IHit<V> processor) {
		int position = 1;
		int currentState = 0;
		for (int i = 0; i < text.length(); ++i) {
			currentState = getState(currentState, text.charAt(i));
			int[] hitArray = node[currentState].emit;
			if (hitArray != null) {
				for (int hit : hitArray) {
					processor.hit(position - emit[hit].char_length, position, emit[hit].value);
				}
			}
			++position;
		}
	}

	/**
	 * Parse text
	 *
	 * @param text      The text
	 * @param processor A processor which handles the output
	 */
	public void parseText(CharSequence text, IHitCancellable<V> processor) {
		int currentState = 0;
		for (int i = 0; i < text.length(); i++) {
			final int position = i + 1;
			currentState = getState(currentState, text.charAt(i));
			int[] hitArray = node[currentState].emit;
			if (hitArray != null) {
				for (int hit : hitArray) {
					boolean proceed = processor.hit(position - emit[hit].char_length, position, emit[hit].value);
					if (!proceed) {
						return;
					}
				}
			}
		}
	}

	/**
	 * Parse text
	 *
	 * @param text      The text
	 * @param processor A processor which handles the output
	 */
	public void parseText(char[] text, IHit<V> processor) {
		int position = 1;
		int currentState = 0;
		for (char c : text) {
			currentState = getState(currentState, c);
			int[] hitArray = node[currentState].emit;
			if (hitArray != null) {
				for (int hit : hitArray) {
					processor.hit(position - emit[hit].char_length, position, emit[hit].value);
				}
			}
			++position;
		}
	}

	/**
	 * Parse text
	 *
	 * @param text      The text
	 * @param processor A processor which handles the output
	 */
	public void parseText(char[] text, IHitFull<V> processor) {
		int position = 1;
		int currentState = 0;
		for (char c : text) {
			currentState = getState(currentState, c);
			int[] hitArray = node[currentState].emit;
			if (hitArray != null) {
				for (int hit : hitArray) {
					processor.hit(position - emit[hit].char_length, position, emit[hit].value, hit);
				}
			}
			++position;
		}
	}

	/**
	 * Checks that string contains at least one substring
	 *
	 * @param text source text to check
	 * @return {@code true} if string contains at least one substring
	 */
	public boolean matches(String text) {
		int currentState = 0;
		for (int i = 0; i < text.length(); ++i) {
			currentState = getState(currentState, text.charAt(i));
			int[] hitArray = node[currentState].emit;
			if (hitArray != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Search first match in string
	 *
	 * @param text source text to check
	 * @return first match or {@code null} if there are no matches
	 */
	public Hit<V> findFirst(String text) {
		int position = 1;
		int currentState = 0;
		for (int i = 0; i < text.length(); ++i) {
			currentState = getState(currentState, text.charAt(i));
			int[] hitArray = node[currentState].emit;
			if (hitArray != null) {
				int hitIndex = hitArray[0];
				return new Hit<V>(position - emit[hitIndex].char_length, position, emit[hitIndex].value);
			}
			++position;
		}
		return null;
	}

	/**
	 * Get value by a String key, just like a map.get() method
	 *
	 * @param key The key
	 * @return value if exist otherwise it return null
	 */
	public V get(String key) {
		int index = exactMatchSearch(key);
		if (index >= 0) {
			return emit[index].value;
		}

		return null;
	}

	/**
	 * Update a value corresponding to a key
	 *
	 * @param key   the key
	 * @param value the value
	 * @return successful or not（failure if there is no key）
	 */
	public boolean set(String key, V value) {
		int index = exactMatchSearch(key);
		if (index >= 0) {
			emit[index].value = value;
			return true;
		}

		return false;
	}

	/**
	 * Pick the value by index in value array <br>
	 * Notice that to be more efficiently, this method DO NOT check the parameter
	 *
	 * @param index The index
	 * @return The value
	 */
	public V get(int index) {
		return emit[index].value;
	}

	/**
	 * Processor handles the output when hit a keyword
	 */
	public interface IHit<V> {
		/**
		 * Hit a keyword, you can use some code like text.substring(begin, end) to get
		 * the keyword
		 *
		 * @param begin the beginning index, inclusive.
		 * @param end   the ending index, exclusive.
		 * @param value the value assigned to the keyword
		 */
		void hit(int begin, int end, V value);
	}

	/**
	 * Processor handles the output when hit a keyword, with more detail
	 */
	public interface IHitFull<V> {
		/**
		 * Hit a keyword, you can use some code like text.substring(begin, end) to get
		 * the keyword
		 *
		 * @param begin the beginning index, inclusive.
		 * @param end   the ending index, exclusive.
		 * @param value the value assigned to the keyword
		 * @param index the index of the value assigned to the keyword, you can use the
		 *              integer as a perfect hash value
		 */
		void hit(int begin, int end, V value, int index);
	}

	/**
	 * Callback that allows to cancel the search process.
	 */
	public interface IHitCancellable<V> {
		/**
		 * Hit a keyword, you can use some code like text.substring(begin, end) to get
		 * the keyword
		 *
		 * @param begin the beginning index, inclusive.
		 * @param end   the ending index, exclusive.
		 * @param value the value assigned to the keyword
		 * @return Return true for continuing the search and false for stopping it.
		 */
		boolean hit(int begin, int end, V value);
	}

	/**
	 * A result output
	 *
	 * @param <V> the value type
	 */
	public static class Hit<V> {
		/**
		 * the beginning index, inclusive.
		 */
		public final int begin;
		/**
		 * the ending index, exclusive.
		 */
		public final int end;
		/**
		 * the value assigned to the keyword
		 */
		public final V value;

		public Hit(int begin, int end, V value) {
			this.begin = begin;
			this.end = end;
			this.value = value;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object _obj) {
			Hit<V> obj = (Hit<V>) _obj;
			return begin == obj.begin && end == obj.end && value.equals(obj.value);
		}

		@Override
		public String toString() {
			return String.format("[%d:%d]=%s", begin, end, value);
		}
	}

	/**
	 * transmit state, supports failure function
	 *
	 * @param currentState
	 * @param character
	 * @return
	 */
	int getState(int currentState, int character) {
		for (;;) {
			int newCurrentState = nextState(currentState, character);
			if (newCurrentState != -1)
				return newCurrentState;
			currentState = node[currentState].failure;
		}
	}

	/**
	 * store output
	 *
	 * @param position
	 * @param currentState
	 * @param collectedEmits
	 */
	void storeEmits(int position, int currentState, List<Hit<V>> collectedEmits) {
		int[] hitArray = node[currentState].emit;
		if (hitArray != null) {
			for (int hit : hitArray) {
				collectedEmits.add(new Hit<V>(position - emit[hit].char_length, position, emit[hit].value));
			}
		}
	}

	/**
	 * transition of a state
	 *
	 * @param current
	 * @param c
	 * @return
	 */
	int transition(int current, char c) {
		int b = current;
		int p;

		p = b + (char) (c + 1);
		if (b == node[p].check)
			b = node[p].base;
		else
			return -1;

		p = b;
		return p;
	}

	/**
	 * transition of a state, if the state is root and it failed, then returns the
	 * root
	 *
	 * @param nodePos
	 * @param c
	 * @return
	 */
	int nextState(int nodePos, int c) {
		int b = node[nodePos].base;
		int p;

		p = b + c + 1;
//		p = b + (char) (c + 1);
		if (b != (p < node.length ? node[p].check : 0)) {
			if (nodePos == 0) // depth == 0
				return 0;
			return -1;
		}

		return p;
	}

	/**
	 * match exactly by a key
	 *
	 * @param key the key
	 * @return the index of the key, you can use it as a perfect hash function
	 */
	public int exactMatchSearch(String key) {
		return exactMatchSearch(key, 0, 0, 0);
	}

	/**
	 * match exactly by a key
	 *
	 * @param key
	 * @param pos
	 * @param len
	 * @param nodePos
	 * @return
	 */
	int exactMatchSearch(String key, int pos, int len, int nodePos) {
		if (len <= 0)
			len = key.length();
		if (nodePos <= 0)
			nodePos = 0;

		int result = -1;

		char[] keyChars = key.toCharArray();

		return getMatched(pos, len, result, keyChars, node[nodePos].base);
	}

	int getMatched(int pos, int len, int result, char[] keyChars, int b1) {
		int b = b1;
		int p;

		for (int i = pos; i < len; i++) {
			p = b + (char) (keyChars[i] + 1);
			if (b == node[p].check)
				b = node[p].base;
			else
				return result;
		}

		p = b; // transition through '\0' to check if it's the end of a word
		int n = node[p].base;
		if (b == node[p].check) {// yes, it is.
			result = -n - 1;
		}
		return result;
	}

	/**
	 * match exactly by a key
	 *
	 * @param keyChars the char array of the key
	 * @param pos      the begin index of char array
	 * @param len      the length of the key
	 * @param nodePos  the starting position of the node for searching
	 * @return the value index of the key, minus indicates null
	 */
	int exactMatchSearch(char[] keyChars, int pos, int len, int nodePos) {
		int result = -1;

		return getMatched(pos, len, result, keyChars, node[nodePos].base);
	}

	State newFailureState(State currentState, int transition) {
		State state;
		do {
			currentState = currentState.failure;
			state = currentState.nextState(transition);
		} while (state == null);

		return state;
	}

	class Transition {
		int character;
		State parent;

		public Transition(int character, State state) {
			this.character = character;
			this.parent = state;
		}

		State node() {
			return parent.success.get(character);
		}

		void set_failure() {
			if (parent.isRoot()) {
				parent.success.get(character).setFailure(parent);
			} else {
				State targetState = parent.success.get(character);
				State newFailureState = newFailureState(parent, character);
				targetState.setFailure(newFailureState);

				targetState.addEmit(newFailureState.emit());
			}
		}

	}

	public class State {

		State failure;

		int[] emit() {
			return node[index].emit;
		}

		void emit(int emit[]) {
			node[index].emit = emit;
		}

		void emit(int emit) {
			node[index].base = -emit - 1;
			assert node[index].check != 0;
		}

		int try_base() {
			for (int begin : used) {
				assert !used.isRregistered(begin);
				if (this.try_update_base(begin)) {
					return begin;
				}
			}

			return -1;
		}

		int update_base() {
			int begin = node[index].base;
			assert used.isRregistered(begin) || begin == 0;

			int _begin = this.try_base();
			node[index].base = _begin;
			assert node[index].check != 0;
			this.ensureCapacity();

			for (Entry<Integer, State> entry : success.entrySet()) {
				int word = entry.getKey();
				State state = entry.getValue();
				if (state.index != 0) {
					assert state.index == begin + (char) (word + 1);
					assert node[state.index].check == begin;
				}

				int code = _begin + (char) (word + 1);
				assert node[code].check == 0;
				if (state.index != 0) {
					node[code].base = node[state.index].base;
					node[code].emit = node[state.index].emit;

					node[state.index].clear();
				}
				state.index = code;

				node[code].check = _begin;
			}

			occupy(_begin);
			recycle(begin);

			return _begin;
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

		TreeMap<Integer, Object> toTreeMap() {
			TreeMap<Integer, Object> map = new TreeMap<Integer, Object>();
			for (Entry<Integer, Integer> entry : AhoCorasickDoubleArrayTrie.this.toTreeMap(this.emit()).entrySet()) {
				map.put(entry.getKey(), AhoCorasickDoubleArrayTrie.this.emit[entry.getValue()].value);
			}
			return map;
		}

		TreeMap<Integer, State> success = new TreeMap<Integer, State>();

		int index;

		public boolean update_failure(State parent, char ch) {
			State newFailureState = newFailureState(parent, ch);
			if (failure == newFailureState) {
				return false;
			}

			setFailure(newFailureState);
			addEmit(newFailureState.emit());
			return true;
		}

		public State update_failure(State parent, char ch, State keywordNode) {
			State newFailureState = newFailureState(parent, ch);
			if (failure == newFailureState) {
				if (keywordNode != null)
					addEmit(keywordNode.emit());
				return failure;
			}

			setFailure(newFailureState);
			addEmit(newFailureState.emit());
			return null;
		}

		void constructFailureStates_(State parent, State rootState, String keyword) {
			char character = keyword.charAt(0);
			rootState = rootState.success.get((int) character);

			keyword = keyword.substring(1);

			if (failure.depth() <= rootState.depth()) {
				setFailure(rootState);
			}

			addEmit(rootState.emit());

			// Second, determine the fail state for all depth > 1 state

			if (!keyword.isEmpty()) {
				State state = success.get((int) keyword.charAt(0));
				if (state != null) {
					state.constructFailureStates_(this, rootState, keyword);
				}
			}
		}

		void constructFailureStates(State parent, State rootState, String keyword) {
			char character = keyword.charAt(0);
			rootState = rootState.success.get((int) character);

			keyword = keyword.substring(1);

			boolean failure = true;
			if (!update_failure(parent, character)) {
				parent = update_failure(parent, character, rootState);
				failure = false;
			}

			// Second, determine the fail state for all depth > 1 state

			if (!keyword.isEmpty()) {
				State state = success.get((int) keyword.charAt(0));
				if (state != null) {
					if (failure)
						state.constructFailureStates(this, rootState, keyword);
					else {
						state.constructFailureStates_(this, rootState, keyword);
					}
				}
			}
		}

		void checkValidity() {
			this.checkValidity(false);
		}

		void checkValidity(boolean recursively) {
			if (emit() != null) {
				for (int i = 1; i < emit().length; ++i) {
					assert AhoCorasickDoubleArrayTrie.this.emit[emit()[i
							- 1]].char_length < AhoCorasickDoubleArrayTrie.this.emit[emit()[i]].char_length;
				}
			}

			if (failure != null) {
				assert node[index].failure == failure.index;
				assert !failure.is_null_terminator();
			} else {
				assert node[index].failure == -1;
			}

			assert node[index].emit == emit();

			int begin = node[index].base;
			for (Entry<Integer, State> entry : success.entrySet()) {
				assert node[begin + (char) (entry.getKey() + 1)].check == begin;
			}

			for (Entry<Integer, State> entry : success.entrySet()) {
				State state = entry.getValue();
				if (state.is_null_terminator()) {
					assert -node[begin + (char) (entry.getKey() + 1)].base - 1 == Utility.last(emit());
				}
			}

			if (recursively)
				for (State state : this.success.values()) {
					state.checkValidity(recursively);
				}
		}

		void removeIndex(int index) {
			int last = AhoCorasickDoubleArrayTrie.this.emit.length - 1;
			if (emit() != null) {
				int first_index = Utility.indexOf(emit(), index);
				int last_index = Utility.indexOf(emit(), last);
				boolean needMofification = false;
				if (last_index >= 0) {
					emit()[last_index] = index;
					needMofification = true;
				}

				if (first_index >= 0) {
					emit(Utility.copierSauf(emit(), first_index));
					needMofification = false;
				}
				if (needMofification) {
					emit(toArray(AhoCorasickDoubleArrayTrie.this.toTreeMap(emit())));
				}
			}

			for (State state : this.success.values()) {
				state.removeIndex(index);
			}
		}

		int depth() {
			if (this.success.size() == 1 && this.success.containsKey(-1)) {
				assert emit != null;
				return lastKey();
			}

			assert !this.success.isEmpty();
			return this.success.lastEntry().getValue().depth() - 1;
		}

		int lastKey() {
			return AhoCorasickDoubleArrayTrie.this.emit[Utility.last(emit())].char_length;
//			TreeMap<Integer, Integer> map = toTreeMap(emit);
//			return map.lastKey();
		}

		int lastValue() {
			return Utility.last(emit());
//			TreeMap<Integer, Integer> map = toTreeMap(emit);
//			return map.lastEntry().getValue();
		}

		int remove(int char_length) {
//			emit.remove(char_length);
			TreeMap<Integer, Integer> map = AhoCorasickDoubleArrayTrie.this.toTreeMap(emit());
			Integer index = map.remove(char_length);
			if (index != null) {
				this.emit(toArray(map));
				return index;
			}
			return -1;
		}

		boolean containsKey(int char_length) {
			TreeMap<Integer, Integer> map = AhoCorasickDoubleArrayTrie.this.toTreeMap(emit());

			return map.containsKey(char_length);
		}

		public boolean delete_failure(State parent, char ch) {
			State newFailureState = newFailureState(parent, ch);
			if (failure == newFailureState) {
				return false;
			}

			setFailure(newFailureState);
			return true;
		}

		void deleteFailureStates(State parent, String keyword, int char_length) {
			char character = keyword.charAt(0);

			keyword = keyword.substring(1);

			delete_failure(parent, character);

			if (keyword.isEmpty()) {
				remove(char_length);
			} else {
				State state = success.get((int) keyword.charAt(0));
				if (state != null) {
					state.deleteFailureStates(this, keyword, char_length);
				}
			}
		}

		int deleteEmit(Stack<State> parent) {
			State leaf = success.get(-1);
			success.remove(-1);
			assert node[leaf.index].check == leaf.index;
			node[leaf.index].clear();

			if (is_null_terminator()) {
				State orphan = this;

				while (!parent.isEmpty()) {
					recycle(node[orphan.index].base);
					node[orphan.index].clear();

					State father = parent.peek();

					int unicode = -1;
					for (Entry<Integer, State> entry : father.success.entrySet()) {
						if (entry.getValue() == orphan) {
							unicode = entry.getKey();
							break;
						}
					}

					father.success.remove(unicode);
					parent.pop();
					if (father.is_null_terminator()) {
						orphan = father;
					} else
						return unicode;
				}
			}

//			parent.add(this);
			return -1;
		}

		void print() {
			int begin = node[index].base;
			for (Entry<Integer, State> entry : success.entrySet()) {
				System.out.printf("check[%d] = ", begin + (char) (entry.getKey() + 1));
				assert node[begin + (char) (entry.getKey() + 1)].check == begin;
			}

			System.out.printf("base[%d] = %d", index, begin);
			for (Entry<Integer, State> entry : success.entrySet()) {
				State state = entry.getValue();
				if (state.is_null_terminator()) {
					System.out.printf(", \tbase[%d] = value[%d] = %s", begin + (char) (entry.getKey() + 1),
							-node[begin + (char) (entry.getKey() + 1)].base - 1,
							AhoCorasickDoubleArrayTrie.this.emit[Utility.last(emit())].value);

					assert -node[begin + (char) (entry.getKey() + 1)].base - 1 == Utility.last(emit());
				}
			}
			System.out.println();
		}

		int size() {
			int size = 1;
			for (State state : success.values()) {
				size += state.size();
			}
			return size;
		}

		void appendNullTerminator(int index) {
			State null_terminator = new State();
			null_terminator.index = -index - 1;
			success.put(-1, null_terminator);
		}

//		/**
//		 * construct output table
//		 */
//		void constructOutput() {
//			assert node[index].emit == emit();
//			node[index].emit = emit();
//		}

		void ensureCapacity() {
			int begin = node[index].base;
			int addr = begin + (char) (this.success.lastKey() + 1);
			if (addr >= node.length) {
				resize(addr + 1);
			}
		}

		int try_nextCheckPos(int begin) {
//			assert begin > 0;
			int addr = begin + (char) (success.firstKey() + 1);
			if (addr < node.length && node[addr].check != 0) {
				return -1;
			}
			return addr;
		}

		int try_nextCheckPos() {
			int begin = Math.max(1, nextCheckPos - (char) (success.firstKey() + 1));

			while (true) {
				nextCheckPos = try_nextCheckPos(begin);
				if (nextCheckPos > 0) {
//					if (nextCheckPos == 21439) {
//						System.out.printf("nextCheckPos = %d,\t", nextCheckPos);
//					} else
//						System.out.printf("nextCheckPos = %d,\t", nextCheckPos);
					return begin;
				}
				++begin;
			}
		}

		int try_base_with_optimal_start_point() {
			int start = try_nextCheckPos() - 1;
//			start = Math.max(-1, start - 13);
			for (int begin : used.tailSet(start)) {
//				assert !used.isRregistered(begin);
				if (try_update_base(begin))
					return begin;
			}

			return -1;
		}

		boolean try_update_base(int begin) {
//			assert begin > 0;
			for (int word : success.keySet()) {
				int addr = begin + (char) (word + 1);
				if (addr < node.length && node[addr].check != 0) {
					return false;
				}
			}
			return true;
		}

		boolean is_null_terminator() {
			return success.isEmpty();
		}

		public State addState(int character, List<Transition> queue) {
			State nextState = success.get(character);
			if (nextState == null) {
				nextState = new State();
				success.put(character, nextState);
				queue.add(new Transition(character, this));
			}
			return nextState;
		}

		/**
		 * insert the siblings to double array trie
		 *
		 * @param siblings the siblings being inserted
		 * @return the position to insert them
		 */
		void insert() {
			if (is_null_terminator())
				return;

			int begin = try_base_with_optimal_start_point();
			occupy(begin);
			node[index].base = begin;

			ensureCapacity();
			for (Map.Entry<Integer, State> sibling : success.entrySet()) {
				State state = sibling.getValue();
				int index = begin + (char) (sibling.getKey() + 1);
				if (state.is_null_terminator()) {
					node[index].base = state.index;
					state.index = index;
				} else {
					if (state.index < 0) {
						int emit = -state.index - 1;
						state.index = index;
						state.emit(new int[] { emit });
					} else {
						state.index = index;
					}
				}

				node[index].check = begin;
			}

			for (State state : success.values()) {
				state.insert();
			}
		}

		void addEmit() {
			State leaf = new State();
			success.put(-1, leaf);
			if (success.size() > 1) {
				Iterator<Entry<Integer, State>> iterator = success.entrySet().iterator();
				iterator.next();

				int begin = node[index].base;
				if (node[begin].check == 0) {
					node[begin].check = begin;
					assert used.isRregistered(begin);
					while (iterator.hasNext()) {
						Entry<Integer, State> entry = iterator.next();
						State node = entry.getValue();
						node.checkValidity();
					}

				} else {
					begin = this.try_base();
					assert !used.isRregistered(begin);

					assert used.isRregistered(node[index].base);
					recycle(node[index].base);

					assert node[begin].check == 0;
					node[begin].check = node[index].base = begin;

					assert node[index].check != 0;

					while (iterator.hasNext()) {
						Entry<Integer, State> entry = iterator.next();
						int code = begin + (char) (entry.getKey() + 1);
						State state = entry.getValue();
						assert node[code].check == 0 && node[code].base == 0;

						node[code].check = begin;
						node[code].base = node[state.index].base;
						node[code].emit = node[state.index].emit;

						if (state.index != begin)
							node[state.index].clear();

						state.index = code;
						state.checkValidity();
					}
					occupy(begin);
				}

				leaf.index = begin;
				leaf.emit(AhoCorasickDoubleArrayTrie.this.emit.length - 1);
			}
		}

		public void addEmit(int value_index) {
			TreeMap<Integer, Integer> map = AhoCorasickDoubleArrayTrie.this.toTreeMap(emit());
			map.put(AhoCorasickDoubleArrayTrie.this.emit[value_index].char_length, value_index);
			emit(toArray(map));
		}

		void check_initialization() {
			assert index <= 0;
			if (index < 0) {
				assert -index - 1 < AhoCorasickDoubleArrayTrie.this.emit.length;
			}

			for (State state : success.values()) {
				state.check_initialization();
			}
		}

		public void initializeEmit(int value_index) {
			index = -value_index - 1;
		}

		public void addEmit(int[] emit) {
			TreeMap<Integer, Integer> map = AhoCorasickDoubleArrayTrie.this.toTreeMap(this.emit());
			map.putAll(AhoCorasickDoubleArrayTrie.this.toTreeMap(emit));

			emit(toArray(map));
		}

		public void setFailure(State failure) {
			this.failure = failure;
			AhoCorasickDoubleArrayTrie.this.node[index].failure = failure.index;
		}

		boolean isRoot() {
//			return index == 0;
			if (index == 0) {
				assert this == root;
				return true;
			}
			return false;

		}

		void locate_state(int ch, List<State> list, int depth) {
			++depth;
			for (Entry<Integer, State> tuple : success.entrySet()) {
				if (tuple.getKey() == -1)
					continue;

				State state = tuple.getValue();
				assert state.depth() == depth;
				if (tuple.getKey() == ch && depth > 1) {
					list.add(this);
				}
				state.locate_state(ch, list, depth);
			}
		}

		List<State> locate_state(int ch) {
			List<State> list = new ArrayList<State>();
			locate_state(ch, list, 0);
			return list;
		}

		List<State> locate_state(String keyword) {
			List<State> list = new ArrayList<State>();
			locate_state("", keyword, list, 0);
			return list;
		}

		void locate_state(String prefix, String keyword, List<State> list, int depth) {
			++depth;
			for (Entry<Integer, State> tuple : success.entrySet()) {
				if (tuple.getKey() == -1)
					continue;
				State state = tuple.getValue();
				String newPrefix = prefix + (char) (int) tuple.getKey();

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

		public State nextState(int character) {
			State nextState = success.get(character);
			if (nextState == null && isRoot())
				return this;

			return nextState;
		}

		public State addState(int character) {
			State nextState = success.get(character);
			if (nextState == null) {
				nextState = new State();
				this.success.put(character, nextState);
			}
			return nextState;
		}

		public Collection<State> getStates() {
			return this.success.values();
		}

		public Set<Integer> getTransitions() {
			return this.success.keySet();
		}

		public Utility.TextTreeNode toShadowTree() {
			String value;

			if (node[this.index].base > 0)
				value = '@' + String.valueOf(this.index) + ':' + node[this.index].base;
			else
				value = '@' + String.valueOf(this.index) + '=' + (-node[this.index].base - 1);

			Utility.TextTreeNode newNode = new Utility.TextTreeNode(value);
			int[] list = new int[success.size()];

			int i = 0;
			for (int ch : success.keySet()) {
				list[i++] = ch;
			}

			Utility.TextTreeNode arr[] = new Utility.TextTreeNode[list.length];
			for (i = 0; i < arr.length; ++i) {
				int word = list[i];
				State state = success.get(word);
				Utility.TextTreeNode node = state.toShadowTree();

				if (word == -1)
					value = "*";
				else
					value = String.valueOf((char) word);

//				if (state.failure != null && state.failure.depth != 0) {
//					node.value += String.valueOf(state.failure.depth);
//				}
//				if (state.is_null_terminator()) {
//					newNode.value += '+';
//				} else {
				node.value = value + node.value;
				arr[i] = node;
//				}
			}

			arr = Utility.remove_null(arr);
			if (arr != null) {
				int x_length = arr.length / 2;
				int y_length = arr.length - x_length;

				// tree node
				if (x_length > 0) {
					newNode.x = new Utility.TextTreeNode[x_length];
					System.arraycopy(arr, 0, newNode.x, 0, x_length);
				}

				if (y_length > 0) {
					newNode.y = new Utility.TextTreeNode[y_length];
					System.arraycopy(arr, x_length, newNode.y, 0, y_length);
				}
			}
			return newNode;
		}

		@Override
		public String toString() {
			Utility.TextTreeNode root = this.toShadowTree();
			return root.toString(root.max_width() + 1, true);
		}
	}

	/**
	 * @return the size of the keywords
	 */
	public int memory_size() {
		int length = 0;
		for (int i = node.length - 1; i >= 0; --i) {
			if (node[i].check != 0) {
				length = i + 1;
				break;
			}
		}
		return length;
	}

	/**
	 * the root state of trie
	 */
	State root = new State();
	/**
	 * whether the position has been used
	 */
	KeyGenerator used = new KeyGenerator();

	/**
	 * the next position to check unused memory
	 */
	int nextCheckPos;

	/**
	 * Build from a map
	 *
	 * @param map a map containing key-value pairs
	 */
	@SuppressWarnings("unchecked")
	public AhoCorasickDoubleArrayTrie(Map<String, V> map) {
		emit = new Emit[map.size()];

		int value_index = 0;
		for (Entry<String, V> entry : map.entrySet()) {
			String keyword = entry.getKey();
			State currentState = this.root;
			for (char character : keyword.toCharArray()) {
				currentState = currentState.addState(character);
			}

			emit[value_index] = new Emit<V>(keyword.length(), entry.getValue());
			currentState.initializeEmit(value_index);
			currentState.appendNullTerminator(value_index);

			++value_index;
		}
		root.check_initialization();
		resize(65536);
		root.insert();

//		used = null;

		int length = memory_size();
		if (length < node.length) {
			Node[] pointer = new Node[length];
			System.arraycopy(this.node, 0, pointer, 0, length);
			this.node = pointer;
		}

		constructFailureStates();
	}

	public void retrievalOnly() {
		this.used = null;
		this.root = null;
		System.gc();
	}

	/**
	 * construct failure table
	 */
	void constructFailureStates() {
		Queue<State> queue = new ArrayDeque<State>();

		// First, set the fail state of all depth 1 states to the root state
		for (State depthOneState : root.getStates()) {
			depthOneState.setFailure(root);
			queue.add(depthOneState);
//			depthOneState.constructOutput();
		}

		// Second, determine the fail state for all depth > 1 state
		while (!queue.isEmpty()) {
			State currentState = queue.remove();

			for (Entry<Integer, State> tuple : currentState.success.entrySet()) {
				int transition = tuple.getKey();
				if (transition == -1)
					continue;

				State targetState = tuple.getValue();
				queue.add(targetState);

				State newFailureState = newFailureState(currentState, transition);
				targetState.setFailure(newFailureState);
				targetState.addEmit(newFailureState.emit());
			}
		}

//		root = null;
	}

	/**
	 * allocate the memory of the dynamic array
	 *
	 * @param newSize of the new array
	 */
	void resize(int newSize) {
		assert node == null || newSize > node.length;
		newSize = (int) Math.pow(2, (int) Math.ceil(Math.log(newSize) / Math.log(2)));

		Node[] _pointer = new Node[newSize];

		int start;
		if (node != null) {
			System.arraycopy(node, 0, _pointer, 0, node.length);
			start = node.length;
		} else
			start = 0;

		for (int i = start; i < newSize; ++i)
			_pointer[i] = new Node();

		node = _pointer;
	}

	int try_base(TreeMap<Integer, State> siblings) {
		int begin = 0;
		int pos = Math.max((char) (siblings.firstKey() + 1) + 1, nextCheckPos) - 1;
//		int nonzero_num = 0;
		int first = 0;

		outer: while (true) {
			pos++;

			if (node[pos].check != 0) {
//				nonzero_num++;
				continue;
			}

			if (first == 0) {
				nextCheckPos = pos;
				first = 1;
			}

			begin = pos - (char) (siblings.firstKey() + 1); // 当前位置离第一个兄弟节点的距离
//			if (pointer.length <= (begin + (char) (siblings.lastKey() + 1))) {
//				// progress can be zero // 防止progress产生除零错误
//				double l = (1.05 > 1.0 * v.length / (progress + 1)) ? 1.05 : 1.0 * v.length / (progress + 1);
//				resize((int) (pointer.length * l));
//			}

			if (used.isRregistered(begin))
				continue;

			Iterator<Entry<Integer, State>> iterator = siblings.entrySet().iterator();

			if (iterator.hasNext()) {
				iterator.next();
				while (iterator.hasNext()) {
					Entry<Integer, State> p = iterator.next();
					int addr = begin + (char) (p.getKey() + 1);
					if (addr < node.length && node[addr].check != 0)
						continue outer;
				}
			}

			break;
		}

		// -- Simple heuristics --
		// if the percentage of non-empty contents in check between the index
		// 'next_check_pos' and 'check' is greater than some constant value (e.g.
		// 0.9),new 'next_check_pos' index is written by 'check'.
//		if (1.0 * nonzero_num / (pos - nextCheckPos + 1) >= 0.95)
//			nextCheckPos = pos; // 从位置 next_check_pos 开始到 pos 间，如果已占用的空间在95%以上，下次插入节点时，直接从 pos 位置处开始查找

		return begin;
	}

	public void update(String keyword, String value) {
//		State currentState = this.rootState;
//		for (Character character : keyword.toCharArray()) {
//			currentState = currentState.addState(character);
//		}
//		currentState.addEmit(index);
//		l[index] = keyword.length();
	}

	public static void testBenchmark() throws IOException {

		Map<String, String> dictionaryMap = new TreeMap<String, String>();
		String path = Utility.corpusDirectory() + "ahocorasick/dictionary.txt";

		ArrayList<String> dictionary = new ArrayList<String>();
		new Utility.Text(path).collect(dictionary);

		for (String word : dictionary) {
			dictionaryMap.put(word, String.format("[%s]", word));
		}

		System.out.println("dictionary.size() = " + dictionaryMap.size());
		String text = new Utility.Text(Utility.corpusDirectory() + "ahocorasick/dictionary.txt").fetchContent()
				+ new Utility.Text(Utility.corpusDirectory() + "ahocorasick/text.txt").fetchContent();

		// Build a ahoCorasickNaive implemented by robert-bor
		Trie<String> ahoCorasickNaive = new Trie<String>(dictionaryMap);

		// Build a AhoCorasickDoubleArrayTrie implemented by hankcs
		AhoCorasickDoubleArrayTrie<String> ahoCorasickDoubleArrayTrie = new AhoCorasickDoubleArrayTrie<String>(
				dictionaryMap);
		ahoCorasickDoubleArrayTrie.checkValidity();
		ahoCorasickDoubleArrayTrie.retrievalOnly();
		// Let's test the speed of the two Aho-Corasick automata
		System.out.printf("Parsing document which contains %d characters, with a dictionary of %d words.\n",
				text.length(), dictionaryMap.size());
		long start = System.currentTimeMillis();
		int hitsNaive = 0;
//		for (int i = 0; i < 100; ++i)
			hitsNaive = ahoCorasickNaive.parseText(text).size();
		long costTimeNaive = System.currentTimeMillis() - start;

		start = System.currentTimeMillis();
		int hitsACDAT = 0;
//		for (int i = 0; i < 100; ++i)
			hitsACDAT = ahoCorasickDoubleArrayTrie.parseText(text).size();
		long costTimeACDAT = System.currentTimeMillis() - start;

		assert hitsNaive == hitsACDAT;
		System.out.printf("%-15s\t%-15s\t%-15s\n", "", "Naive", "ACDAT");
		System.out.printf("%-15s\t%-15d\t%-15d\n", "time", costTimeNaive, costTimeACDAT);
		System.out.printf("%-15s\t%-15.2f\t%-15.2f\n", "char/s", (text.length() / (costTimeNaive / 1000.0)),
				(text.length() / (costTimeACDAT / 1000.0)));
		System.out.printf("%-15s\t%-15.2f\t%-15.2f\n", "rate", 1.0, costTimeNaive / (double) costTimeACDAT);
		System.out.printf("%-15s\t%-15d\t%-15d\n", "hits", hitsNaive, hitsACDAT);
		System.out.println("===========================================================================");
		System.out.println("space cost = " + ahoCorasickDoubleArrayTrie.node.length);
	}

	void occupy(int begin) {
//		System.out.println("occupy begin = " + begin);
//		assert !used.isRregistered(begin);
		used.register_key(begin);
	}

	void recycle(int begin) {
		if (begin > 0) {
//			assert used.isRregistered(begin);
			used.unregister_key(begin);
		}
	}

	public int[] getNonzeroCheckIndex() {
		List<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < node.length; ++i)
			if (node[i].check != 0) {
				indices.add(i);
			}

		int[] arr = Utility.toArray(indices);
		if (arr == null)
			return new int[] {};
		return arr;
	}

	public int[] getNonzeroBaseIndex() {
		List<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < node.length; ++i)
			if (node[i].base != 0) {
				indices.add(i);
			}

		int[] arr = Utility.toArray(indices);
		if (arr == null)
			return new int[] {};
		return arr;
	}

	public int[] getValidUsedIndex() {
		List<Integer> indices = new ArrayList<Integer>();
		for (int i = 1; i < node.length; ++i) {
			if (!used.isRregistered(i))
				continue;

			indices.add(i);
		}

		int[] arr = Utility.toArray(indices);
		if (arr == null)
			return new int[] {};
		return arr;
	}

	void checkValidity() {
		root.checkValidity(true);
		for (Node node : this.node) {
			if (node.check != 0) {
				assert node.base != 0;
			} else {
				assert node.emit == null;
				assert node.failure == -1;
			}
		}

		int[] checkIndex = getNonzeroCheckIndex();

		if (debug) {
			System.out.println("checkIndex.length = " + checkIndex.length);
			System.out.println("checkIndex = " + Utility.toString(checkIndex));
		}

		int[] baseIndex = getNonzeroBaseIndex();
		if (debug) {
			System.out.println("baseIndex.length = " + baseIndex.length);
			System.out.println("baseIndex = " + Utility.toString(baseIndex));
		}

		assert baseIndex[0] == 0;
		assert Utility.equals(Arrays.copyOfRange(baseIndex, 1, baseIndex.length), checkIndex);

		int[] usedIndex = getValidUsedIndex();
		if (debug) {
			System.out.println("usedIndex.length = " + usedIndex.length);
			System.out.println("usedIndex = " + Utility.toString(usedIndex));
			System.out.println("usedIndex.length + key.length - baseIndex.length = "
					+ (usedIndex.length + emit.length - baseIndex.length));

			System.out.println("this.root.size() - baseIndex.length = " + (this.root.size() - baseIndex.length));
		}

		for (int kinder : checkIndex) {
			int begin = node[kinder].check;
			assert used.isRregistered(begin);
		}

		for (int index : baseIndex) {
			int begin = node[index].base;
			if (begin < 0)
				assert -begin - 1 < emit.length;
			else
				assert used.isRregistered(begin);
		}

		for (int begin : usedIndex) {
			if (node[begin].check != 0) {
				if (node[begin].check == begin)
					assert node[begin].base < 0;
				else
					assert node[begin].base > 0;
			}
		}
		assert baseIndex.length == this.root.size();
		assert usedIndex.length + emit.length == baseIndex.length;
	}

//	order > 0 means ascending order
//	order < 0 means descending order
//	order = 0 means random order	
	int order;

	void remove(String keyword) {
		State currentState;
		if (order < 0) {
//		int index = Arrays.binarySearch(key, keyword);
//		if (index < 0)
//			return;
//
//		String[] _key = new String[key.length - 1];
//		System.arraycopy(key, 0, _key, 0, index);
//		System.arraycopy(key, index + 1, _key, index, key.length - 1 - index);
//		key = _key;
//
//		State currentState = this.root;
//		Stack<State> parent = new Stack<State>();
//		for (int i = 0; i < keyword.length(); ++i) {
//			char ch = keyword.charAt(i);
//			parent.add(currentState);
//			currentState = currentState.success.get((int) ch);
//		}
//
//		int unicode = currentState.deleteEmit(parent);
////		if (crutialIndex >= 0) {
////			char ch = keyword.charAt(crutialIndex);
////			update(crutialState, ch);
////		} else {
////		int crutialIndex = keyword.length() - 1;
////		}
////
//		for (;;) {
//			State node = parent.pop();
//			for (Entry<Integer, State> p : node.success.tailMap(unicode, false).entrySet()) {
//				p.getValue().decrementKeyIndex();
//			}
//			if (parent.isEmpty())
//				break;
//			unicode = keyword.charAt(parent.size() - 1);
//		}
		} else {
			int index = exactMatchSearch(keyword);
			if (index < 0)
				return;
			int length = emit.length;
			// now change emit from key.length to index
			removeIndex(index);

			currentState = this.root;
			Stack<State> parent = new Stack<State>();
			for (int i = 0; i < keyword.length(); ++i) {
				char ch = keyword.charAt(i);
				parent.add(currentState);
				currentState = currentState.success.get((int) ch);
			}

			index = currentState.remove(keyword.length());
			assert index < 0;

			int numOfDeletion = parent.size();
			int character = currentState.deleteEmit(parent);

			if (character != -1) {
				numOfDeletion -= parent.size();
				deleteFailureStates((char) character, keyword, numOfDeletion);
			}
			emit = Arrays.copyOf(emit, length - 1);
		}
	}

//delete first, and then change second to first, 
	void removeIndex(int index) {
		int last = emit.length - 1;
		Utility.swap(emit, index, last);

		root.removeIndex(index);
		Node first_node = null, second_node = null;
		index = -index - 1;
		for (Node node : this.node) {
			if (node.base == index) {
				first_node = node;
				break;
			}
		}
		last = -last - 1;
		for (Node node : this.node) {
			if (node.base == last) {
				second_node = node;
				break;
			}
		}

		first_node.base = last;
		second_node.base = index;
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

	void deleteFailureStates(List<State> list, String keyword, int char_length) {
		char character = keyword.charAt(0);
		for (State parent : list) {
			State state = parent.success.get((int) character);
			state.deleteFailureStates(parent, keyword, char_length);
		}
	}

	void put(String keyword, V value) {
		if (order < 0) {
//		int index = Arrays.binarySearch(key, keyword);
//		if (index >= 0)
//			return;
//		index = -index - 1;
//
//		String[] _key = new String[key.length + 1];
//		System.arraycopy(key, 0, _key, 0, index);
//		System.arraycopy(key, index, _key, index + 1, key.length - index);
//		_key[index] = keyword;
//		key = _key;
//
//		State currentState = this.root;
//		State crutialState = null;
//		int crutialIndex = -1;
//		Stack<State> parent = new Stack<State>();
//		for (int i = 0; i < keyword.length(); ++i) {
//			char ch = keyword.charAt(i);
//			if (crutialState == null)
//				parent.add(currentState);
//			currentState = currentState.addState(ch, index);
//			if (currentState.index == 0 && crutialState == null) {
//				crutialState = parent.peek();
//				crutialIndex = i;
//			}
//		}
//
//		currentState.addEmit(index);
//		if (crutialIndex >= 0) {
//			char ch = keyword.charAt(crutialIndex);
//			update(crutialState, ch);
//		} else {
//			crutialIndex = keyword.length() - 1;
//		}
//
//		while (!parent.isEmpty()) {
//			State father = parent.pop();
//			for (Entry<Integer, State> p : father.success.tailMap((int) keyword.charAt(crutialIndex), false)
//					.entrySet()) {
//				p.getValue().incrementKeyIndex();
//			}
//			--crutialIndex;
//		}
		} else {
			int index = exactMatchSearch(keyword);
			if (index >= 0) {
				this.emit[index].value = value;
				return;
			}

			index = emit.length;
			emit = Arrays.copyOf(emit, emit.length + 1);
			emit[index] = new Emit<V>(keyword.length(), value);

			State currentState = this.root;
			State crutialState = null;
			int crutialIndex = -1;
			State parent = null;
			ArrayList<Transition> queue = new ArrayList<Transition>();
			for (int i = 0; i < keyword.length(); ++i) {
				char ch = keyword.charAt(i);
				if (crutialState == null)
					parent = currentState;
				currentState = currentState.addState(ch, queue);
				if (currentState.index == 0 && crutialState == null) {
					crutialState = parent;
					crutialIndex = i;
				}
			}

			currentState.addEmit();
			if (crutialIndex >= 0) {
				char ch = keyword.charAt(crutialIndex);
				update(crutialState, ch, index);
			}

			currentState.addEmit(AhoCorasickDoubleArrayTrie.this.emit.length - 1);
			updateFailureStates(queue, keyword);
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
				rootState = rootState.success.get((int) keyword.charAt(i));
			}
			keyword = _keyword;
		}

		constructFailureStates(list, rootState, keyword);
	}

	void constructFailureStates(List<State> list, State rootState, String keyword) {
		char character = keyword.charAt(0);
		for (State parent : list) {
			State state = parent.success.get((int) character);
			state.constructFailureStates(parent, rootState, keyword);
		}
	}

	void update(State parent, int word, int emit) {
		for (;;) {
			int code = parent.index;
			State state = parent.success.get(word);
			int begin = node[code].base;
			int pos = 0;
			boolean need_update_base;
			if (begin == 0) {
				need_update_base = true;
			} else {
				assert code == 0 || node[code].check != 0;
				pos = begin + (char) (word + 1);
				need_update_base = node[pos].check != 0;
			}

			if (need_update_base) {
				begin = parent.update_base();
				pos = begin + (char) (word + 1);
			}

			if (!used.isRregistered(begin)) {
				occupy(begin);
			}

			state.index = pos;

			assert node[pos].base == 0;
			node[pos].check = begin;

			if (node[code].base != begin) {
				recycle(node[code].base);

				for (Entry<Integer, State> entry : parent.success.entrySet()) {
					int key = entry.getKey();
					if (key != word) {
						State sibling = entry.getValue();
						node[sibling.index].clear();

						int new_code = begin + (char) (key + 1);
						sibling.index = new_code;
						assert node[new_code].check == 0;
						node[new_code].check = begin;

						sibling.update_base();
					}
				}
				node[code].base = begin;
				assert node[code].check != 0;
			}

			if (state.is_null_terminator()) {
				state.emit(emit);
				break;
			}

			assert state.success.size() == 1;
			Entry<Integer, State> entry = state.success.firstEntry();
			parent = state;
			word = entry.getKey();
		}
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

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object _obj) {
		AhoCorasickDoubleArrayTrie<V> obj = (AhoCorasickDoubleArrayTrie<V>) _obj;
		if (!toHashMap().equals(obj.toHashMap()))
			return false;
		return this.root.equals(obj.root);
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

	static final boolean debug = false;

	public static void main(String[] args) throws IOException {
		testBenchmark();

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
		AhoCorasickDoubleArrayTrie<String> dat = new AhoCorasickDoubleArrayTrie<String>(dictionaryMap);
		System.out.println("time cost = " + (System.currentTimeMillis() - start));
		System.out.println("space cost = " + dat.node.length);

		if (debug) {
			System.out.println(dat);
		}

		dat.checkValidity();
		List<Hit<String>> arr = dat.parseText(text);
		start = System.currentTimeMillis();
		AhoCorasickDoubleArrayTrie<String> _dat = new AhoCorasickDoubleArrayTrie<String>(dictionaryMap);
		System.out.println("time cost = " + (System.currentTimeMillis() - start));
		System.out.println("space cost = " + _dat.node.length);
		_dat.checkValidity();
		String debugWord = null;
//		debugWord = "行署";
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
			_dat.checkValidity();
			_dat.put(word, String.format("[%s]", word));
			_dat.put(word, String.format("[%s]", word));
			if (debug) {
				System.out.println(_dat);
			}
			_dat.checkValidity();
			assert dat.equals(_dat);
			List<Hit<String>> _arr = _dat.parseText(text);
			assert Utility.equals(arr, _arr);
		}
	}
}
