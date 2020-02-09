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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.ahocorasick.trie.Trie;

import com.util.KeyGenerator;
import com.util.Utility;

import test.Common;

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
		 * output table of the Aho Corasick automata
		 */
		int[] output;

		/**
		 * fail table of the Aho Corasick automata
		 */
		int failure;

		void clear() {
			base = check = 0;
		}
	}

	@Override
	public String toString() {
		return root.toString();
	}

	Node[] node;

	/**
	 * outer value array
	 */
	V[] value;

	/**
	 * the length of every key
	 */
	int[] char_length;

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
			int[] hitArray = node[currentState].output;
			if (hitArray != null) {
				for (int hit : hitArray) {
					processor.hit(position - char_length[hit], position, value[hit]);
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
			int[] hitArray = node[currentState].output;
			if (hitArray != null) {
				for (int hit : hitArray) {
					boolean proceed = processor.hit(position - char_length[hit], position, value[hit]);
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
			int[] hitArray = node[currentState].output;
			if (hitArray != null) {
				for (int hit : hitArray) {
					processor.hit(position - char_length[hit], position, value[hit]);
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
			int[] hitArray = node[currentState].output;
			if (hitArray != null) {
				for (int hit : hitArray) {
					processor.hit(position - char_length[hit], position, value[hit], hit);
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
			int[] hitArray = node[currentState].output;
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
			int[] hitArray = node[currentState].output;
			if (hitArray != null) {
				int hitIndex = hitArray[0];
				return new Hit<V>(position - char_length[hitIndex], position, value[hitIndex]);
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
			return value[index];
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
			this.value[index] = value;
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
		return value[index];
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
		int[] hitArray = node[currentState].output;
		if (hitArray != null) {
			for (int hit : hitArray) {
				collectedEmits.add(new Hit<V>(position - char_length[hit], position, value[hit]));
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

		p = b + (char) (c + 1);
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
		if (b == node[p].check) // yes, it is.
		{
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

	public class State {
		State failure;

		TreeSet<Integer> emits;

		TreeMap<Integer, State> success = new TreeMap<Integer, State>();

		int index;

		void check_validity() {
			if (!debug)
				return;
			int begin = node[index].base;
			for (Entry<Integer, State> entry : success.entrySet()) {
				System.out.printf("check[%d] = ", begin + (char) (entry.getKey() + 1));
				assert node[begin + (char) (entry.getKey() + 1)].check == begin;
			}

			System.out.printf("base[%d] = %d", index, begin);
			for (Entry<Integer, State> entry : success.entrySet()) {
				State state = entry.getValue();
				if (state.success.isEmpty()) {
					System.out.printf(", \tbase[%d] = value[%d] = %s", begin + (char) (entry.getKey() + 1),
							-node[begin + (char) (entry.getKey() + 1)].base - 1, value[state.getLargestValueId()]);

					assert -node[begin + (char) (entry.getKey() + 1)].base - 1 == state.getLargestValueId();
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

		void appendNullTerminator() {
			assert emits != null;
			State fakeNode = new State();
			fakeNode.addEmit(getLargestValueId());
			success.put(-1, fakeNode);
		}

		/**
		 * construct output table
		 */
		void constructOutput() {
			if (emits == null || emits.size() == 0)
				return;
			int[] output = new int[emits.size()];
			Iterator<Integer> it = emits.iterator();
			for (int i = 0; i < output.length; ++i) {
				output[i] = it.next();
			}
			AhoCorasickDoubleArrayTrie.this.node[index].output = output;
		}

		void ensureCapacity(int begin) {
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

		/**
		 * insert the siblings to double array trie
		 *
		 * @param siblings the siblings being inserted
		 * @return the position to insert them
		 */
		void insert() {
			int begin = try_base_with_optimal_start_point();
			occupy(begin);
			node[index].base = begin;

			ensureCapacity(begin);
			for (Map.Entry<Integer, State> sibling : success.entrySet()) {
				State state = sibling.getValue();
				state.index = begin + (char) (sibling.getKey() + 1);
				node[state.index].check = begin;
			}

			for (Map.Entry<Integer, State> sibling : success.entrySet()) {
				State state = sibling.getValue();
				if (state.success.isEmpty()) {
					node[state.index].base = -state.getLargestValueId() - 1;
				} else {
					state.insert();
				}
			}
		}

		public void addEmit(int keyword) {
			if (this.emits == null) {
				this.emits = new TreeSet<Integer>(Collections.reverseOrder());
			}
			this.emits.add(keyword);
		}

		public Integer getLargestValueId() {
			if (emits == null || emits.size() == 0)
				return null;

			return emits.first();
		}

		public void addEmit(Collection<Integer> emits) {
			if (emits != null)
				for (int emit : emits) {
					addEmit(emit);
				}
		}

		public void setFailure(State failure) {
			this.failure = failure;
			AhoCorasickDoubleArrayTrie.this.node[index].failure = failure.index;
		}

		public State nextState(int character) {
			State nextState = success.get(character);
			if (nextState == null && index == 0)
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
//				if (state.success.isEmpty()) {
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
	public AhoCorasickDoubleArrayTrie(TreeMap<String, V> map) {
		value = (V[]) map.values().toArray();
		char_length = new int[value.length];
		Set<String> keySet = map.keySet();
		addAllKeyword(keySet);

		assert keySet.size() == value.length;

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

	/**
	 * add a keyword
	 *
	 * @param keyword a keyword
	 * @param index   the index of the keyword
	 */
	void addKeyword(String keyword, int index) {
		State currentState = this.root;
		for (char character : Utility.toCharArray(keyword)) {
			currentState = currentState.addState(character);
		}
		currentState.addEmit(index);
		currentState.appendNullTerminator();

		char_length[index] = keyword.length();
	}

	/**
	 * add a collection of keywords
	 *
	 * @param keywordSet the collection holding keywords
	 */
	void addAllKeyword(Collection<String> keywordSet) {
		int i = 0;
		for (String keyword : keywordSet) {
			addKeyword(keyword, i++);
		}
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
			depthOneState.constructOutput();
		}

		// Second, determine the fail state for all depth > 1 state
		while (!queue.isEmpty()) {
			State currentState = queue.remove();

			for (Integer transition : currentState.getTransitions()) {
				State targetState = currentState.nextState(transition);
				queue.add(targetState);

				State traceFailureState = currentState.failure;
				while (traceFailureState.nextState(transition) == null) {
					traceFailureState = traceFailureState.failure;
				}
				State newFailureState = traceFailureState.nextState(transition);
				targetState.setFailure(newFailureState);
				targetState.addEmit(newFailureState.emits);
				targetState.constructOutput();
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

	public static void testBenchmark() {

		String text = Common.text;
		TreeMap<String, String> dictionaryMap = Common.dictionaryMap;

		// Build a ahoCorasickNaive implemented by robert-bor
		Trie ahoCorasickNaive = new Trie(dictionaryMap);

		// Build a AhoCorasickDoubleArrayTrie implemented by hankcs
		AhoCorasickDoubleArrayTrie<String> ahoCorasickDoubleArrayTrie = new AhoCorasickDoubleArrayTrie<String>(
				dictionaryMap);
		// Let's test the speed of the two Aho-Corasick automata
		System.out.printf("Parsing document which contains %d characters, with a dictionary of %d words.\n",
				text.length(), dictionaryMap.size());
		long start = System.currentTimeMillis();
		int hitsNaive = ahoCorasickNaive.parseText(text).size();

		long costTimeNaive = System.currentTimeMillis() - start;
		start = System.currentTimeMillis();
		int hitsACDAT = ahoCorasickDoubleArrayTrie.parseText(text).size();

		assert (hitsNaive == hitsACDAT);

		long costTimeACDAT = System.currentTimeMillis() - start;
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
					+ (usedIndex.length + value.length - baseIndex.length));

			System.out.println("this.root.size() - baseIndex.length = " + (this.root.size() - baseIndex.length));
		}

		for (int kinder : checkIndex) {
			int begin = node[kinder].check;
			assert used.isRregistered(begin);
		}

		for (int index : baseIndex) {
			int begin = node[index].base;
			if (begin < 0)
				assert -begin - 1 < value.length;
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
		assert usedIndex.length + value.length == baseIndex.length;
	}

	void remove(String keyword) {
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
	}

	public int[][] testSearch() {
		int[][] arrs = new int[this.value.length][];
//		int j = 0;
//		for (String key : this.key) {
//			List<Integer> list = commonPrefixSearch(key);
//			int[] arr = new int[list.size()];
//			int i = 0;
//			for (int index : commonPrefixSearch(key)) {
////				System.out.printf("key[%d] = %s\n", index, this.key[index]);
//				assert key.startsWith(this.key[index]);
//				arr[i++] = index;
//			}
//			arrs[j++] = arr;
//		}
		return arrs;
	}

	void insert(String keyword) {
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

	}

	public static void main(String[] args) throws IOException {
		testBenchmark();

		ArrayList<String> words = new ArrayList<String>(Common.dictionaryMap.keySet());

		Collections.shuffle(words, new Random(0));

		System.out.println("字典词条：" + words.size());

		if (debug) {
			for (String word : words) {
				System.out.println(word);
			}
		}

		long start = System.currentTimeMillis();
		AhoCorasickDoubleArrayTrie<String> dat = new AhoCorasickDoubleArrayTrie<String>(Common.dictionaryMap);
		System.out.println("time cost = " + (System.currentTimeMillis() - start));
		System.out.println("space cost = " + dat.node.length);

		if (debug) {
			System.out.println(dat);
		}

		dat.checkValidity();
		int[][] arr = dat.testSearch();
		start = System.currentTimeMillis();
		AhoCorasickDoubleArrayTrie<String> _dat = new AhoCorasickDoubleArrayTrie<String>(Common.dictionaryMap);
		System.out.println("time cost = " + (System.currentTimeMillis() - start));
		System.out.println("space cost = " + _dat.node.length);
		String debugWord = null;
//		debugWord = "万能胶";
		for (String word : words) {
			if (debugWord != null && !debugWord.equals(word))
				continue;
			if (debug)
				System.out.println("removing word: " + word);

			_dat.remove(word);
			if (debug) {
				System.out.println(_dat);
			}
			_dat.checkValidity();
			_dat.insert(word);
			if (debug) {
				System.out.println(_dat);
			}
			_dat.checkValidity();
			assert dat.equals(_dat);
			int[][] _arr = _dat.testSearch();
			assert Utility.equals(arr, _arr);
		}
	}

	static final boolean debug = false;
}
