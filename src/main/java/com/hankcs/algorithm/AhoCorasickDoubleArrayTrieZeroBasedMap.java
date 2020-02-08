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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.ahocorasick.trie.Trie;

import com.util.Utility;

import test.Common;

/**
 * An implementation of Aho Corasick algorithm based on Double Array Trie
 *
 * @author hankcs
 */
@SuppressWarnings("serial")
public class AhoCorasickDoubleArrayTrieZeroBasedMap<V> implements Serializable {
	/**
	 * check array of the Double Array Trie structure
	 */
	protected int[] check;
	/**
	 * base array of the Double Array Trie structure
	 */
	protected int[] base;
	/**
	 * fail table of the Aho Corasick automata
	 */
	protected int[] fail;
	/**
	 * output table of the Aho Corasick automata
	 */
	protected int[][] output;
	/**
	 * outer value array
	 */
	protected V[] v;

	/**
	 * the length of every key
	 */
	protected int[] l;

	/**
	 * the size of base and check array
	 */
	protected int size;

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
			int[] hitArray = output[currentState];
			if (hitArray != null) {
				for (int hit : hitArray) {
					processor.hit(position - l[hit], position, v[hit]);
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
			int[] hitArray = output[currentState];
			if (hitArray != null) {
				for (int hit : hitArray) {
					boolean proceed = processor.hit(position - l[hit], position, v[hit]);
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
			int[] hitArray = output[currentState];
			if (hitArray != null) {
				for (int hit : hitArray) {
					processor.hit(position - l[hit], position, v[hit]);
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
			int[] hitArray = output[currentState];
			if (hitArray != null) {
				for (int hit : hitArray) {
					processor.hit(position - l[hit], position, v[hit], hit);
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
			int[] hitArray = output[currentState];
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
			int[] hitArray = output[currentState];
			if (hitArray != null) {
				int hitIndex = hitArray[0];
				return new Hit<V>(position - l[hitIndex], position, v[hitIndex]);
			}
			++position;
		}
		return null;
	}

	/**
	 * Save
	 *
	 * @param out An ObjectOutputStream object
	 * @throws IOException Some IOException
	 */
	public void save(ObjectOutputStream out) throws IOException {
		out.writeObject(base);
		out.writeObject(check);
		out.writeObject(fail);
		out.writeObject(output);
		out.writeObject(l);
		out.writeObject(v);
	}

	/**
	 * Load data from [ObjectInputStream]
	 *
	 * @param in An ObjectInputStream object
	 * @throws IOException            If can't read the file from path
	 * @throws ClassNotFoundException If the class doesn't exist or matched
	 */
	@SuppressWarnings("unchecked")
	public void load(ObjectInputStream in) throws IOException, ClassNotFoundException {
		base = (int[]) in.readObject();
		check = (int[]) in.readObject();
		fail = (int[]) in.readObject();
		output = (int[][]) in.readObject();
		l = (int[]) in.readObject();
		v = (V[]) in.readObject();
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
			return v[index];
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
			v[index] = value;
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
		return v[index];
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
			int newCurrentState = nextState(currentState, character); // 先按success跳转
			if (newCurrentState != -1)
				return newCurrentState;
			// 跳转失败的话，按failure跳转
			currentState = fail[currentState];
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
		int[] hitArray = output[currentState];
		if (hitArray != null) {
			for (int hit : hitArray) {
				collectedEmits.add(new Hit<V>(position - l[hit], position, v[hit]));
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
	protected int transition(int current, char c) {
		int b = current;
		int p;

		p = b + c + 1;
		if (b == check[p])
			b = base[p];
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
	protected int nextState(int nodePos, int c) {
		int b = base[nodePos];
		int p;

		p = b + (char) (c + 1);
		if (b != check[p]) {// if p is not a valid pointer
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

		return getMatched(pos, len, result, keyChars, base[nodePos]);
	}

	int getMatched(int pos, int len, int result, char[] keyChars, int b1) {
		int b = b1;
		int p;

		for (int i = pos; i < len; i++) {
			p = b + (char) (keyChars[i] + 1);
			if (b == check[p])
				b = base[p];
			else
				return result;
		}

		p = b; // transition through '\0' to check if it's the end of a word
		int n = base[p];
		if (b == check[p]) // yes, it is.
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

		return getMatched(pos, len, result, keyChars, base[nodePos]);
	}

	static public class State {

		/**
		 * 模式串的长度，也是这个状态的深度
		 */
		protected final int depth;

		/**
		 * fail 函数，如果没有匹配到，则跳转到此状态。
		 */
		State failure = null;

		Set<Integer> emits = null;

		TreeMap<Integer, State> success = new TreeMap<Integer, State>();

		/**
		 * 在双数组中的对应下标
		 */
		int index;

		/**
		 * 构造深度为0的节点
		 */
		public State() {
			this(0);
		}

		/**
		 * 构造深度为depth的节点
		 *
		 * @param depth
		 */
		public State(int depth) {
			this.depth = depth;
		}

		/**
		 * 获取节点深度
		 *
		 * @return
		 */
		public int getDepth() {
			return this.depth;
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

			return emits.iterator().next();
		}

		/**
		 * 添加一些匹配到的模式串
		 *
		 * @param emits
		 */
		public void addEmit(Collection<Integer> emits) {
			for (int emit : emits) {
				addEmit(emit);
			}
		}

		/**
		 * 获取这个节点代表的模式串（们）
		 *
		 * @return
		 */
		public Collection<Integer> emit() {
			return this.emits == null ? Collections.<Integer>emptyList() : this.emits;
		}

		/**
		 * 是否是终止状态
		 *
		 * @return
		 */
		public boolean isAcceptable() {
			return this.depth > 0 && this.emits != null;
		}

		/**
		 * 获取failure状态
		 *
		 * @return
		 */
		public State failure() {
			return this.failure;
		}

		/**
		 * 设置failure状态
		 *
		 * @param failState
		 */
		public void setFailure(State failState, int fail[]) {
			this.failure = failState;
			fail[index] = failState.index;
		}

		/**
		 * 按照character转移，根节点转移失败会返回自己（永远不会返回null）
		 *
		 * @param character
		 * @return
		 */
		public State nextState(int character) {
			State nextState = success.get(character);
			if (nextState == null && depth == 0)
				return this;

			return nextState;
		}

		/**
		 * 按照character转移，任何节点转移失败会返回null
		 *
		 * @param character
		 * @return
		 */
		public State nextStateIgnoreRootState(int character) {
			return success.get(character);
		}

		public State addState(int character) {
			State nextState = nextStateIgnoreRootState(character);
			if (nextState == null) {
				nextState = new State(this.depth + 1);
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

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder("State{");
			sb.append("depth=").append(depth);
			sb.append(", ID=").append(index);
			sb.append(", emits=").append(emits);
			sb.append(", success=").append(success.keySet());
			sb.append(", failureID=").append(failure == null ? "-1" : failure.index);
			sb.append(", failure=").append(failure);
			sb.append('}');
			return sb.toString();
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/**
	 * @return the size of the keywords
	 */
	public int size() {
		return v.length;
	}

	/**
	 * the root state of trie
	 */
	State rootState = new State();
	/**
	 * whether the position has been used
	 */
	boolean[] used;
	/**
	 * the allocSize of the dynamic array
	 */
	int allocSize;
	/**
	 * a parameter controls the memory growth speed of the dynamic array
	 */
	int progress;
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
	public AhoCorasickDoubleArrayTrieZeroBasedMap(Map<String, V> map) {
		v = (V[]) map.values().toArray();
		l = new int[v.length];
		Set<String> keySet = map.keySet();
		addAllKeyword(keySet);

		assert keySet.size() == v.length;

		buildDoubleArrayTrie();
		used = null;
		constructFailureStates();
		rootState = null;
		loseWeight();
	}

	/**
	 * fetch siblings of a parent node
	 *
	 * @param parent   parent node
	 * @param siblings parent node's child nodes, i . e . the siblings
	 * @return the amount of the siblings
	 */
	int fetch(State parent) {
		if (parent.isAcceptable()) {
			State fakeNode = new State(-(parent.getDepth() + 1)); // 此节点是parent的子节点，同时具备parent的输出
			fakeNode.addEmit(parent.getLargestValueId());
//			siblings.add(new AbstractMap.SimpleEntry<Integer, State>(0, fakeNode));
			parent.success.put(-1, fakeNode);
		}
		return parent.success.size();
	}

	/**
	 * add a keyword
	 *
	 * @param keyword a keyword
	 * @param index   the index of the keyword
	 */
	void addKeyword(String keyword, int index) {
		State currentState = this.rootState;
		for (char character : Utility.toCharArray(keyword)) {
			currentState = currentState.addState(character);
		}
		currentState.addEmit(index);
		l[index] = keyword.length();
	}

	/**
	 * add a collection of keywords
	 *
	 * @param keywordSet the collection holding keywords
	 */
	void addAllKeyword(Collection<String> keywordSet) {
		int i = 0;
		for (String keyword : keywordSet) {
//				System.out.printf("adding %s\n", keyword);
			addKeyword(keyword, i++);
		}
	}

	/**
	 * construct failure table
	 */
	void constructFailureStates() {
		fail = new int[size + 1];
		output = new int[size + 1][];
		Queue<State> queue = new ArrayDeque<State>();

		// 第一步，将深度为1的节点的failure设为根节点
		for (State depthOneState : this.rootState.getStates()) {
			depthOneState.setFailure(this.rootState, fail);
			queue.add(depthOneState);
			constructOutput(depthOneState);
		}

		// 第二步，为深度 > 1 的节点建立failure表，这是一个bfs
		while (!queue.isEmpty()) {
			State currentState = queue.remove();

			for (Integer transition : currentState.getTransitions()) {
				State targetState = currentState.nextState(transition);
				queue.add(targetState);

				State traceFailureState = currentState.failure();
				while (traceFailureState.nextState(transition) == null) {
					traceFailureState = traceFailureState.failure();
				}
				State newFailureState = traceFailureState.nextState(transition);
				targetState.setFailure(newFailureState, fail);
				targetState.addEmit(newFailureState.emit());
				constructOutput(targetState);
			}
		}
	}

	/**
	 * construct output table
	 */
	void constructOutput(State targetState) {
		Collection<Integer> emit = targetState.emit();
		if (emit == null || emit.size() == 0)
			return;
		int[] output = new int[emit.size()];
		Iterator<Integer> it = emit.iterator();
		for (int i = 0; i < output.length; ++i) {
			output[i] = it.next();
		}
		AhoCorasickDoubleArrayTrieZeroBasedMap.this.output[targetState.getIndex()] = output;
	}

	void buildDoubleArrayTrie() {
//		progress = 0;
		resize(65536 * 32); // 32个双字节

		base[0] = 1;
//		nextCheckPos = 0;

		State root_node = this.rootState;

		fetch(root_node);
		if (!root_node.success.isEmpty())
			insert(root_node.success);
	}

	/**
	 * allocate the memory of the dynamic array
	 *
	 * @param newSize of the new array
	 * @return the new-allocated-size
	 */
	int resize(int newSize) {
		int[] base2 = new int[newSize];
		int[] check2 = new int[newSize];
		boolean[] used2 = new boolean[newSize];
		if (allocSize > 0) {
			System.arraycopy(base, 0, base2, 0, allocSize);
			System.arraycopy(check, 0, check2, 0, allocSize);
			System.arraycopy(used, 0, used2, 0, allocSize);
		}

		base = base2;
		check = check2;
		used = used2;

		return allocSize = newSize;
	}

	/**
	 * insert the siblings to double array trie
	 *
	 * @param siblings the siblings being inserted
	 * @return the position to insert them
	 */
	int insert(TreeMap<Integer, State> siblings) {
		int begin = 0;
		int pos = Math.max((char) (siblings.firstKey() + 1) + 1, nextCheckPos) - 1;
		int nonzero_num = 0;
		int first = 0;

		if (allocSize <= pos)
			resize(pos + 1);

		outer:
		// 此循环体的目标是找出满足base[begin + a1...an] == 0的n个空闲空间,a1...an是siblings中的n个节点
		while (true) {
			pos++;

			if (allocSize <= pos)
				resize(pos + 1);

			if (check[pos] != 0) {
				nonzero_num++;
				continue;
			} else if (first == 0) {
				nextCheckPos = pos;
				first = 1;
			}

			begin = pos - (char) (siblings.firstKey() + 1); // 当前位置离第一个兄弟节点的距离
			if (allocSize <= (begin + (char) (siblings.lastKey() + 1))) {
				// progress can be zero // 防止progress产生除零错误
				double l = (1.05 > 1.0 * v.length / (progress + 1)) ? 1.05 : 1.0 * v.length / (progress + 1);
				resize((int) (allocSize * l));
			}

			if (used[begin])
				continue;

			Iterator<Entry<Integer, State>> iterator = siblings.entrySet().iterator();

			if (iterator.hasNext()) {
				iterator.next();
				while (iterator.hasNext()) {
					Entry<Integer, State> p = iterator.next();
					int addr = begin + (char) (p.getKey() + 1);
					if (check[addr] != 0)
						continue outer;
				}
			}

			break;
		}

		// -- Simple heuristics --
		// if the percentage of non-empty contents in check between the
		// index
		// 'next_check_pos' and 'check' is greater than some constant value
		// (e.g. 0.9),
		// new 'next_check_pos' index is written by 'check'.
		if (1.0 * nonzero_num / (pos - nextCheckPos + 1) >= 0.95)
			nextCheckPos = pos; // 从位置 next_check_pos 开始到 pos 间，如果已占用的空间在95%以上，下次插入节点时，直接从 pos 位置处开始查找
		used[begin] = true;

		size = Math.max(size, begin + (char) (siblings.lastKey() + 1) + 1);

		for (Map.Entry<Integer, State> sibling : siblings.entrySet()) {
			check[begin + (char) (sibling.getKey() + 1)] = begin;
		}

		for (Map.Entry<Integer, State> sibling : siblings.entrySet()) {
			if (fetch(sibling.getValue()) == 0) {
				base[begin + (char) (sibling.getKey() + 1)] = (-sibling.getValue().getLargestValueId() - 1);
				progress++;
			} else {
				int h = insert(sibling.getValue().success); // dfs
				base[begin + (char) (sibling.getKey() + 1)] = h;
			}
			sibling.getValue().setIndex(begin + (char) (sibling.getKey() + 1));
		}
		return begin;
	}

	/**
	 * free the unnecessary memory
	 */
	void loseWeight() {
		int[] nbase = new int[size + 65535];
		System.arraycopy(base, 0, nbase, 0, size);
		base = nbase;

		int[] ncheck = new int[size + 65535];
		System.arraycopy(check, 0, ncheck, 0, size);
		check = ncheck;
	}

	public void update(String keyword, String value) {
//		State currentState = this.rootState;
//		for (Character character : keyword.toCharArray()) {
//			currentState = currentState.addState(character);
//		}
//		currentState.addEmit(index);
//		l[index] = keyword.length();
	}

	public static void main(String[] args) {

		String text = Common.text;
		TreeMap<String, String> dictionaryMap = Common.dictionaryMap;

		// Build a ahoCorasickNaive implemented by robert-bor
		Trie ahoCorasickNaive = new Trie(dictionaryMap);

		// Build a AhoCorasickDoubleArrayTrie implemented by hankcs
		AhoCorasickDoubleArrayTrieZeroBasedMap<String> ahoCorasickDoubleArrayTrie = new AhoCorasickDoubleArrayTrieZeroBasedMap<String>(
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
	}
}
