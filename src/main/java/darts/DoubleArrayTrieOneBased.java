package darts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import com.util.KeyGenerator;
import com.util.Utility;

public class DoubleArrayTrieOneBased {
	@Override
	public boolean equals(Object _obj) {
		DoubleArrayTrieOneBased obj = (DoubleArrayTrieOneBased) _obj;

		if (!new TreeSet<String>(Arrays.asList(key)).equals(new TreeSet<String>(Arrays.asList(obj.key))))
			return false;

		if (!root.equals(obj.root))
			return false;

		if (this.getNonzeroBaseIndex().length != obj.getNonzeroBaseIndex().length)
			return false;

		if (this.getNonzeroCheckIndex().length != obj.getNonzeroCheckIndex().length)
			return false;

		if (this.getValidUsedIndex().length != obj.getValidUsedIndex().length)
			return false;

		return true;
	}

	class State {
		int emit() {
			return -node[index].base - 1;
		}

		void emit(int emit) {
			node[index].base = -emit - 1;
			assert node[index].check != 0;
		}

		void appendNullTerminator(int index) {
			State leaf = new State();
			leaf.index = -index - 1;
			success.put(-1, leaf);
		}

		public State addState(int character) {
			State nextState = success.get(character);
			if (nextState == null) {
				nextState = new State();
				this.success.put(character, nextState);
			}
			return nextState;
		}

		void insert() {
			if (success.isEmpty())
				return;

			int begin = try_base_with_optimal_start_point();
//			int begin = try_base(root.success);

			occupy(begin);
			node[index].base = begin;
//			assert code == 0 || pointer[code].check != 0;

			ensureCapacity();
			for (Entry<Integer, State> p : success.entrySet()) {
				State state = p.getValue();
				int index = begin + (char) (p.getKey() + 1);
				if (state.success.isEmpty())
					node[index].base = state.index;

				state.index = index;
				node[index].check = begin;
			}

			for (State state : success.values()) {
				state.insert();
			}

			check_validity();
		}

		void copy2array(State states[]) {
			states[this.index] = this;
			for (State state : success.values()) {
				state.copy2array(states);
			}
		}

		void ensureCapacity() {
			int begin = node[index].base;
			int addr = begin + (char) (this.success.lastKey() + 1);
			if (addr >= node.length) {
				resize(addr + 1);
			}
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

		int try_nextCheckPos(int begin) {
//			assert begin > 0;
			int addr = begin + (char) (success.firstKey() + 1);
			if (addr < node.length && node[addr].check != 0) {
				return -1;
			}
			return addr;
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

		int try_nextCheckPos() {
			int begin = Math.max(1, nextCheckPos - (char) (success.firstKey() + 1));

			while (true) {
				nextCheckPos = try_nextCheckPos(begin);
				if (nextCheckPos > 0) {
					return begin;
				}
				++begin;
			}
		}

		int try_base_with_optimal_start_point() {

			int start = try_nextCheckPos() - 1;
//			start = Math.max(0, start - 13);
			for (int begin : used.tailSet(start)) {
//				assert !used.isRregistered(begin);
				if (try_update_base(begin))
					return begin;
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
					node[state.index].clear();
				}
				state.index = code;

				node[code].check = _begin;
			}

			occupy(_begin);
			recycle(begin);

			return _begin;
		}

		int update_base(State states[], int[] begin2base) {
			int begin = node[index].base;
			assert used.isRregistered(begin) || begin == 0;

			int _begin = this.try_base();
			if (_begin > begin)
				return begin;

			node[index].base = _begin;
			assert node[index].check != 0;

			for (Entry<Integer, State> entry : success.entrySet()) {
				int word = entry.getKey();
				State state = entry.getValue();
				assert states[state.index] == state;

				int code = _begin + (char) (word + 1);
				assert node[code].check == 0 && node[code].base == 0 && states[code] == null;
				assert state.index != 0;

				assert node[state.index].base >= 0 || node[state.index].check == state.index;

				node[code].base = node[state.index].base;
				node[state.index].clear();

				states[state.index] = null;
				state.index = code;
				states[code] = state;

				node[code].check = _begin;
			}

			begin2base[_begin] = index;
			occupy(_begin);

			begin2base[begin] = -1;
			recycle(begin);

			return _begin;
		}

		void incrementKeyIndex() {
			for (Entry<Integer, State> p : success.entrySet()) {
				State state = p.getValue();
				state.incrementKeyIndex();
				if (state.success.isEmpty()) {
					--node[state.index].base;
					assert node[state.index].check != 0;
				}
			}
		}

		void decrementKeyIndex() {
			for (Entry<Integer, State> p : success.entrySet()) {
				State state = p.getValue();
				state.decrementKeyIndex();
				assert state.index == node[index].base + (char) (p.getKey() + 1);
				assert node[state.index].check == node[index].base;
				if (state.success.isEmpty()) {
					++node[state.index].base;
					assert node[state.index].check != 0;
				}
			}
		}

		int index;

		int size() {
			int size = 1;
			for (State state : success.values()) {
				size += state.size();
			}
			return size;
		}

		TreeMap<Integer, State> success = new TreeMap<Integer, State>();

		void check_validity() {
			if (!debug)
				return;
			int begin = node[index].base;
			for (Entry<Integer, State> entry : success.entrySet()) {
				State state = entry.getValue();
				assert begin + (char) (entry.getKey() + 1) == state.index;
				System.out.printf("check[%d] = ", state.index);
				assert node[state.index].check == begin;
			}
			System.out.printf("base[%d] = %d", index, begin);

			for (State state : success.values()) {
				if (state.success.isEmpty()) {
					System.out.printf(", \tbase[%d] = key[%d] = %s", state.index, state.emit(), key[state.emit()]);
				}
			}
			System.out.println();
		}

		State addState(char character) {
			State nextState = this.success.get((int) character);
			if (nextState == null) {
				nextState = new State();
				success.put((int) character, nextState);
			}
			return nextState;
		}

		void addEmit(int emit) {
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
						node.incrementKeyIndex();
						node.check_validity();
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

						if (state.index != begin)
							node[state.index].clear();

						state.index = code;
						state.incrementKeyIndex();
						state.check_validity();
					}
					occupy(begin);
				}

				leaf.index = begin;
				leaf.emit(emit);

				this.check_validity();
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
						node.check_validity();
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

						if (state.index != begin)
							node[state.index].clear();

						state.index = code;
						state.check_validity();
					}
					occupy(begin);
				}

				leaf.index = begin;
				leaf.emit(key.length - 1);

				this.check_validity();
			}
		}

		int deleteEmit(Stack<State> parent) {
			State leaf = success.get(-1);
			success.remove(-1);
			assert node[leaf.index].check == leaf.index;
			assert node[leaf.index].base + leaf.emit() == -1;
			node[leaf.index].clear();

			if (success.isEmpty()) {
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

					if (father.success.isEmpty()) {
						orphan = father;
						parent.pop();
					} else
						return unicode;
				}
			}

			parent.add(this);
			return -1;
		}

//		void fetch(int emit_end) {
//			int prev = -1;
//
//			for (int i = this.emit; i < emit_end; ++i) {
//				if (key[i].length() < this.depth)
//					continue;
//
//				String tmp = key[i];
//
//				int cur = -1;
//				if (tmp.length() != this.depth)
//					cur = tmp.charAt(this.depth);
//
//				assert prev <= cur;
//
//				if (cur != prev || success.size() == 0) {
//					if (success.size() != 0) {
//						State last = success.lastEntry().getValue();
//						last.fetch(i);
//					}
//
//					success.put(cur, new State(this.depth + 1, i));
//				}
//
//				prev = cur;
//			}
//
//			if (success.size() != 0) {
//				State last = success.lastEntry().getValue();
//				last.fetch(emit_end);
//			}
//		}

		public Utility.TextTreeNode toShadowTree() {
			String value;

			if (node[this.index].base > 0)
				value = '@' + String.valueOf(this.index) + ':' + node[index].base;
			else
				value = '@' + String.valueOf(this.index) + '=' + this.emit();

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

		@Override
		public boolean equals(Object _obj) {
			State obj = (State) _obj;
			if (!success.equals(obj.success))
				return false;

//			if (failure == null) {
//				if (obj.failure != null)
//					return false;
//			} else {
//				if (obj.failure == null)
//					return false;
//				if (failure.depth != obj.failure.depth)
//					return false;
//			}
			return true;
		}

	};

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
					+ (usedIndex.length + key.length - baseIndex.length));

			System.out.println("this.root.size() - baseIndex.length = " + (this.root.size() - baseIndex.length));
		}

		for (int kinder : checkIndex) {
			int begin = node[kinder].check;
			assert used.isRregistered(begin);
		}

		for (int index : baseIndex) {
			int begin = node[index].base;
			if (begin < 0)
				assert -begin - 1 < key.length;
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
		assert usedIndex.length + key.length == baseIndex.length;
	}

	@Override
	public String toString() {
		return root.toString();
	}

	public static class Node {
		int base;
		int check;

		void clear() {
			base = check = 0;
		}

	}

	Node[] node;
	KeyGenerator used = new KeyGenerator();

	String key[];
	int nextCheckPos;
	State root;

	boolean isAscendingOrder;

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

	void insert(String keyword) {
		if (isAscendingOrder) {
			// precondition: key is sorted in ascending order
			// insert a new keyword, while maintaining the ascending order.
			int index = Arrays.binarySearch(key, keyword);
			if (index >= 0)
				return;
			index = -index - 1;

			String[] _key = new String[key.length + 1];
			System.arraycopy(key, 0, _key, 0, index);
			System.arraycopy(key, index, _key, index + 1, key.length - index);
			_key[index] = keyword;
			key = _key;

			State currentState = this.root;
			State crutialState = null;
			int crutialIndex = -1;
			Stack<State> parent = new Stack<State>();
			for (int i = 0; i < keyword.length(); ++i) {
				char ch = keyword.charAt(i);
				if (crutialState == null)
					parent.add(currentState);
				currentState = currentState.addState(ch);
				if (currentState.index == 0 && crutialState == null) {
					crutialState = parent.peek();
					crutialIndex = i;
				}
			}

			currentState.addEmit(index);
			if (crutialIndex >= 0) {
				char ch = keyword.charAt(crutialIndex);
				update(crutialState, ch, index);
			} else {
				crutialIndex = keyword.length() - 1;
			}

			while (!parent.isEmpty()) {
				State father = parent.pop();
				for (Entry<Integer, State> p : father.success.tailMap((int) keyword.charAt(crutialIndex), false)
						.entrySet()) {
					p.getValue().incrementKeyIndex();
				}
				--crutialIndex;
			}
		} else {
			if (Utility.indexOf(key, keyword) >= 0)
				return;

			int index = key.length;
			key = Arrays.copyOf(key, key.length + 1);
			key[index] = keyword;

			State currentState = this.root;
			State crutialState = null;
			int crutialIndex = -1;
			Stack<State> parent = new Stack<State>();
			for (int i = 0; i < keyword.length(); ++i) {
				char ch = keyword.charAt(i);
				if (crutialState == null)
					parent.add(currentState);
				currentState = currentState.addState(ch);
				if (currentState.index == 0 && crutialState == null) {
					crutialState = parent.peek();
					crutialIndex = i;
				}
			}

			currentState.addEmit();
			if (crutialIndex >= 0) {
				char ch = keyword.charAt(crutialIndex);
				update(crutialState, ch, index);
			}
		}
	}

	void remove(String keyword) {
		if (isAscendingOrder) {
			// precondition: key is sorted in ascending order
			// remove the keyword, but maintain the original natural order.

			int index = Arrays.binarySearch(key, keyword);
			if (index < 0)
				return;

			String[] _key = new String[key.length - 1];
			System.arraycopy(key, 0, _key, 0, index);
			System.arraycopy(key, index + 1, _key, index, key.length - 1 - index);
			key = _key;

			State currentState = this.root;
			Stack<State> parent = new Stack<State>();
			for (int i = 0; i < keyword.length(); ++i) {
				char ch = keyword.charAt(i);
				parent.add(currentState);
				currentState = currentState.success.get((int) ch);
			}

			int unicode = currentState.deleteEmit(parent);

			for (;;) {
				State node = parent.pop();
				for (Entry<Integer, State> p : node.success.tailMap(unicode, false).entrySet()) {
					p.getValue().decrementKeyIndex();
				}
				if (parent.isEmpty())
					break;
				unicode = keyword.charAt(parent.size() - 1);
			}
		} else {
			int index = Utility.indexOf(key, keyword);
			if (index < 0)
				return;
			int length = key.length;
			Utility.swap(key, index, length - 1);
			key = Arrays.copyOf(key, length - 1);

			State currentState = this.root;
			Stack<State> parent = new Stack<State>();
			for (int i = 0; i < keyword.length(); ++i) {
				char ch = keyword.charAt(i);
				parent.add(currentState);
				currentState = currentState.success.get((int) ch);
			}

			currentState.deleteEmit(parent);
			// now change emit from key.length to index

			for (Node node : this.node) {
				if (node.base == -length) {
					node.base = -index - 1;
					break;
				}
			}
		}
	}

	int try_base(TreeMap<Integer, State> success) {
		int begin = 0;
		int pos = (char) (success.firstKey() + 1);
		if (pos < nextCheckPos) {
			pos = nextCheckPos - 1;
		}

//		int nonzero_num = 0;
		int first = 0;

		outer: while (true) {
			++pos;

			if (pos < node.length && node[pos].check != 0) {
//				nonzero_num++;
				continue;
			}

			if (first == 0) {
				nextCheckPos = pos;
//				System.out.printf("nextCheckPos = %d,\t", nextCheckPos);
				first = 1;
			}

			begin = pos - (char) (success.firstKey() + 1);

			if (used.isRregistered(begin))
				continue;

			Iterator<Entry<Integer, State>> iterator = success.entrySet().iterator();

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

//		if (1.0 * nonzero_num / (pos - nextCheckPos + 1) >= 0.95)
//			nextCheckPos = pos;

		return begin;
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

			if (state.success.isEmpty()) {
				state.emit(emit);
				break;
			}

			assert state.success.size() == 1;
			Entry<Integer, State> entry = state.success.firstEntry();
			parent = state;
			word = entry.getKey();
		}

//		parent.check_validity();
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

	public DoubleArrayTrieOneBased(List<String> list) {
		this(Utility.toArray(list));
	}

	/**
	 * add a collection of keywords
	 *
	 * @param keywordSet the collection holding keywords
	 */
	void addAllKeyword(String[] keywordSet) {
		int i = 0;
		for (String keyword : keywordSet) {
			addKeyword(keyword, i++);
		}
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
//		currentState.addEmit(index);
		currentState.appendNullTerminator(index);
	}

	public DoubleArrayTrieOneBased(TreeSet<String> key) {
		this(Utility.toArray(key));
		isAscendingOrder = true;
	}

	public DoubleArrayTrieOneBased(String[] key) {
		this.key = key;
		resize(65536);

		root = new State();
		addAllKeyword(key);
		root.insert();

		shrink();
	}

	int space_cost() {
		int length = 0;
		for (int i = node.length - 1; i >= 0; --i) {
			if (node[i].check != 0) {
				length = i + 1;
				break;
			}
		}
		return length;
	}

	void loseWeight(int length) {

		if (length < node.length) {
			Node[] _pointer = new Node[length];
			System.arraycopy(node, 0, _pointer, 0, length);
			node = _pointer;
		}
	}

	void shrink() {
		int length = space_cost();
		loseWeight(length);
		int[] begin2base = new int[node.length];
		Arrays.fill(begin2base, -1);
		for (int parent = node.length - 1; parent >= 0; --parent) {
			int begin = node[parent].base;
			if (begin > 0)
				begin2base[begin] = parent;
		}

		State[] states = new State[node.length];
		root.copy2array(states);

		int kinder = node.length - 1;
		for (; kinder >= 0; --kinder) {
			int begin = node[kinder].check;
			if (begin > 0) {
				assert begin2base[begin] >= 0 : String.format("begin2base[%d] = %d < 0", begin, begin2base[begin]);
				int parentAddr = begin2base[begin];

				State parent = states[parentAddr];
				assert parent != null;

				parent.check_validity();

				assert parent.index == parentAddr;
				assert node[parent.index].base == begin;
				if (parent.update_base(states, begin2base) == begin)
					break;
				System.out.println("begin = " + begin);
				parent.check_validity();
			}
		}

		++kinder;

		loseWeight(kinder);
	}

	public int exactMatchSearch(String key) {
		return exactMatchSearch(key, 0, 0, 0);
	}

	public int exactMatchSearch(String key, int pos, int len, int nodePos) {
		if (len <= 0)
			len = key.length();
		if (nodePos <= 0)
			nodePos = 0;

		int result = -1;

		char[] keyChars = key.toCharArray();

		int b = node[nodePos].base;
		int p;

		for (int i = pos; i < len; ++i) {
			p = b + (int) (keyChars[i]) + 1;
			if (b == node[p].check)
				b = node[p].base;
			else
				return result;
		}

		p = b;
		int n = node[p].base;
		if (b == node[p].check && n < 0) {
			result = -n - 1;
		}
		return result;
	}

	public String[][] testSearch() {
		String[][] arrs = new String[this.key.length][];
		int j = 0;
		for (String key : new TreeSet<String>(Arrays.asList(this.key))) {
			List<Integer> list = commonPrefixSearch(key);
			String[] arr = new String[list.size()];
			int i = 0;
			for (int index : commonPrefixSearch(key)) {
//				System.out.printf("key[%d] = %s\n", index, this.key[index]);
				assert key.startsWith(this.key[index]);
				arr[i++] = this.key[index];
			}
			arrs[j++] = arr;
		}
		return arrs;
	}

	public List<Integer> commonPrefixSearch(String key) {
		return commonPrefixSearch(key, 0, 0, 0);
	}

	public List<Integer> commonPrefixSearch(String key, int pos, int len, int nodePos) {
		if (len <= 0)
			len = key.length();
		if (nodePos <= 0)
			nodePos = 0;

		List<Integer> result = new ArrayList<Integer>();

		char[] keyChars = key.toCharArray();

		int b = node[nodePos].base;
		int base_value;

		for (int i = pos; i < len; ++i) {
			base_value = node[b].base;

			if (b == node[b].check && base_value < 0) {
				result.add(-base_value - 1);
			}

			if (node[b + (int) (keyChars[i]) + 1].check != node[nodePos].base) {
				System.out.printf("%d = base[%d] + '%c'(%d) + 1\n", b + (int) (keyChars[i]) + 1, nodePos, keyChars[i],
						(int) keyChars[i]);

				System.out.printf("check[%d] = %d\n", b + (int) (keyChars[i]) + 1,
						node[b + (int) (keyChars[i]) + 1].check);
				System.out.printf("base[%d] = %d\n", nodePos, node[nodePos].base);
			}
			assert node[b + (int) (keyChars[i]) + 1].check == node[nodePos].base;

			nodePos = b + (int) (keyChars[i]) + 1;

			if (b == node[nodePos].check)
				b = node[nodePos].base;
			else
				return result;
		}

		base_value = node[b].base;

		if (b == node[b].check && base_value < 0) {
			result.add(-base_value - 1);
		}

		return result;
	}

	public static void main(String[] args) throws IOException {
		ArrayList<String> words = new ArrayList<String>();
		new Utility.Text("../corpus/ahocorasick/dictionary.txt").collect(words);
//		new Utility.Text("../corpus/ahocorasick/small.txt").collect(words);

		Collections.shuffle(words, new Random());

		System.out.println("字典词条：" + words.size());

		if (debug) {
			for (String word : words) {
				System.out.println(word);
			}
		}

		long start = System.currentTimeMillis();
		DoubleArrayTrieOneBased dat = new DoubleArrayTrieOneBased(words);
		System.out.println("time cost = " + (System.currentTimeMillis() - start));
		System.out.println("space cost = " + dat.node.length);

		if (debug) {
			System.out.println(dat);
		}

		dat.checkValidity();
		String[][] arr = dat.testSearch();
		start = System.currentTimeMillis();
		DoubleArrayTrieOneBased _dat = new DoubleArrayTrieOneBased(words);
		System.out.println("time cost = " + (System.currentTimeMillis() - start));
		System.out.println("space cost = " + _dat.node.length);
		String debugWord = null;
//		debugWord = "一举一动";
		for (String word : words) {
			if (debugWord != null && !debugWord.equals(word))
				continue;
//			if (debug)
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
			String[][] _arr = _dat.testSearch();
			assert Utility.equals(arr, _arr);
		}
	}

	static final boolean debug = false;
}