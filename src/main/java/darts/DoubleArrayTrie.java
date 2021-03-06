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

public class DoubleArrayTrie {
	@Override
	public boolean equals(Object _obj) {
		DoubleArrayTrie obj = (DoubleArrayTrie) _obj;

		if (!Utility.equals(key, obj.key))
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
		State(int depth, int left) {
			this.depth = depth;
			this.emit = left;
		}

		void copy2array(State states[]) {
			if (this.code >= 0)
				states[this.code] = this;
			for (State state : success.values()) {
				state.copy2array(states);
			}
		}

		void ensureCapacity() {
			int begin = getBase(code);
			int addr = begin + this.success.lastKey();
			if (addr >= pointer.length) {
				resize(addr + 1);
			}
		}

		boolean try_update_base(int begin) {
//			assert begin >= 0;
			for (char word : success.keySet()) {
				int addr = begin + word;
				if (addr < pointer.length && pointer[addr].check >= 0) {
					return false;
				}
			}
			return true;
		}

		int try_nextCheckPos(int begin) {
//			assert begin >= 0;
			int addr = begin + success.firstKey();
			if (addr < pointer.length && pointer[addr].check >= 0) {
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
			int begin = Math.max(0, nextCheckPos - success.firstKey());

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

		int update_base() {
			int begin = pointer[code].base;
			assert used.isRregistered(begin) || begin == Integer.MIN_VALUE;

			int _begin = this.try_base();
			pointer[code].base = _begin;
			assert pointer[code].check >= 0;
			this.ensureCapacity();

			for (Entry<Character, State> entry : success.entrySet()) {
				char word = entry.getKey();
				State state = entry.getValue();
				if (state.code != -1) {
					assert state.code == begin + word;
					assert pointer[state.code].check == begin;
				}

				int code = _begin + word;
				assert pointer[code].check == -1;
				if (state.code != -1) {
					pointer[code].base = pointer[state.code].base;
					pointer[state.code].clear();
				}
				state.code = code;

				pointer[code].check = _begin;
			}

			occupy(_begin);
			recycle(begin);

			return _begin;
		}

		int update_base(State states[]) {
			int begin = pointer[code].base;
			assert used.isRregistered(begin) || begin == 0;

			int _begin = this.try_base();
			if (_begin > begin)
				return begin;

			pointer[code].base = _begin;
			assert pointer[code].check >= 0;

			for (Entry<Character, State> entry : success.entrySet()) {
				char word = entry.getKey();
				State state = entry.getValue();

				int code = _begin + word;
				assert pointer[code].check == -1;
				assert state.code != -1;

				pointer[code].base = pointer[state.code].base;
				pointer[state.code].clear();

				states[state.code] = null;
				state.code = code;
				states[code] = state;

				pointer[code].check = _begin;
			}

			occupy(_begin);
			recycle(begin);

			return _begin;
		}

		void incrementKeyIndex() {
			++this.emit;
			for (Entry<Character, State> p : success.entrySet()) {
				State node = p.getValue();
				node.incrementKeyIndex();
				if (node.success.isEmpty()) {
					pointer[node.code].base = -node.emit - 1;
					assert pointer[node.code].check >= 0;
				}
			}
		}

		void decrementKeyIndex() {
			--this.emit;
			for (Entry<Character, State> p : success.entrySet()) {
				State node = p.getValue();
				node.decrementKeyIndex();
				assert node.code == pointer[code].base + p.getKey();
				assert pointer[node.code].check == pointer[code].base;
				if (node.success.isEmpty()) {
					pointer[node.code].base = -node.emit - 1;
					assert pointer[node.code].check >= 0;
				}
			}
		}

		int code = -1;
		int depth;
		int emit;

		int size() {
			int size = 1;
			for (Entry<Character, State> entry : success.entrySet()) {
				size += entry.getValue().size();
			}
			return size;
		}

		TreeMap<Character, State> success = new TreeMap<Character, State>();

		void initialize(int begin) {
			occupy(begin);
			setBase(code, begin);
//			assert code == 0 || pointer[code].check != 0;

			ensureCapacity();
			for (Entry<Character, State> p : success.entrySet()) {
				State node = p.getValue();
				node.code = begin + p.getKey();
				pointer[node.code].check = begin;
			}
		}

		void check_validity() {
			if (!debug)
				return;
			int begin = getBase(code);
			for (Entry<Character, State> entry : success.entrySet()) {
				System.out.printf("check[%d] = ", begin + entry.getKey());
				assert pointer[begin + entry.getKey()].check == begin;
			}
			System.out.printf("base[%d] = %d", code, begin);
			for (Entry<Character, State> entry : success.entrySet()) {
				State node = entry.getValue();
				if (node.success.isEmpty()) {
					System.out.printf(", \tbase[%d] = key[%d] = %s", begin + entry.getKey(),
							-pointer[begin + entry.getKey()].base - 1, key[node.emit]);

					assert -pointer[begin + entry.getKey()].base - 1 == node.emit;
				}
			}
			System.out.println();
		}

		State addState(char character, int index) {
			State nextState = this.success.get(character);
			if (nextState == null) {
				nextState = new State(this.depth + 1, index);
				success.put(character, nextState);
			}
			return nextState;
		}

		void addEmit(int emit) {
			this.emit = emit;
			State leaf = new State(this.depth + 1, emit);
			success.put('\0', leaf);
			if (success.size() > 1) {
				Iterator<Entry<Character, State>> iterator = success.entrySet().iterator();
				iterator.next();

				int begin = pointer[code].base;
				if (pointer[begin].check == -1) {
					pointer[begin].check = begin;
					assert used.isRregistered(begin);
					while (iterator.hasNext()) {
						Entry<Character, State> entry = iterator.next();
						State node = entry.getValue();
						node.incrementKeyIndex();
						node.check_validity();
					}

				} else {
					begin = this.try_base();
					assert !used.isRregistered(begin);

					assert used.isRregistered(pointer[code].base);
					recycle(pointer[code].base);

					assert pointer[begin].check == -1;
					pointer[begin].check = pointer[code].base = begin;

					assert pointer[code].check >= 0;

					while (iterator.hasNext()) {
						Entry<Character, State> entry = iterator.next();
						int code = begin + entry.getKey();
						State node = entry.getValue();
						assert pointer[code].check == -1 && pointer[code].base == Integer.MIN_VALUE;

						pointer[code].check = begin;
						pointer[code].base = pointer[node.code].base;

						if (node.code != begin)
							pointer[node.code].clear();

						node.code = code;
						node.incrementKeyIndex();
						node.check_validity();
					}
					occupy(begin);
				}
				leaf.code = begin;
				pointer[begin].base = -emit - 1;
				assert pointer[begin].check >= 0;

				this.check_validity();
			}
		}

		char deleteEmit(Stack<State> parent) {
			State leaf = success.get('\0');
			success.remove('\0');
			assert pointer[leaf.code].check == leaf.code;
			assert pointer[leaf.code].base + emit == -1;
			pointer[leaf.code].clear();

			if (success.isEmpty()) {
				State orphan = this;

				while (!parent.isEmpty()) {
					recycle(pointer[orphan.code].base);
					pointer[orphan.code].clear();

					State father = parent.peek();

					char unicode = 0;
					for (Entry<Character, State> entry : father.success.entrySet()) {
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
			} else
				--emit;
			parent.add(this);
			return 0;
		}

		void fetch(int emit_end) {
			int prev = 0;

			for (int i = this.emit; i < emit_end; ++i) {
				if (key[i].length() < this.depth)
					continue;

				String tmp = key[i];

				char cur = 0;
				if (tmp.length() != this.depth)
					cur = tmp.charAt(this.depth);

				assert prev <= cur;

				if (cur != prev || success.size() == 0) {
					if (success.size() != 0) {
						State last = success.lastEntry().getValue();
						last.fetch(i);
					}

					success.put(cur, new State(this.depth + 1, i));
				}

				prev = cur;
			}

			if (success.size() != 0) {
				State last = success.lastEntry().getValue();
				last.fetch(emit_end);
			}
		}

		public Utility.TextTreeNode toShadowTree() {
			String value;

			if (getBase(this.code) > 0)
				value = '@' + String.valueOf(this.code) + ':' + getBase(code);
			else
				value = '@' + String.valueOf(this.code) + '-' + this.emit;

			Utility.TextTreeNode newNode = new Utility.TextTreeNode(value);
			char[] list = new char[success.size()];

			int i = 0;
			for (char ch : success.keySet()) {
				list[i++] = ch;
			}

			Utility.TextTreeNode arr[] = new Utility.TextTreeNode[list.length];
			for (i = 0; i < arr.length; ++i) {
				char word = list[i];
				State state = success.get(word);
				Utility.TextTreeNode node = state.toShadowTree();

				if (word == -1)
					value = "*";
				else
					value = String.valueOf(word);

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
			if (depth != obj.depth)
				return false;

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

			if (emit != obj.emit)
				return false;

			return true;
		}

	};

	void setBase(int addr, int base) {
		if (addr >= 0)
			pointer[addr].base = base;
//		else
//			assert base == 0;	
	}

	int getBase(int addr) {
		if (addr < 0)
			return 0;
		else
			return pointer[addr].base;
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

//		assert baseIndex[0] == 0;
		assert Utility.equals(baseIndex, checkIndex);

		int[] usedIndex = getValidUsedIndex();
		if (debug) {
			System.out.println("usedIndex.length = " + usedIndex.length);
			System.out.println("usedIndex = " + Utility.toString(usedIndex));
			System.out.println("usedIndex.length + key.length - baseIndex.length = "
					+ (usedIndex.length + key.length - baseIndex.length));

			System.out.println("this.root.size() - baseIndex.length = " + (this.root.size() - baseIndex.length));
		}

		for (int kinder : checkIndex) {
			int begin = pointer[kinder].check;
			assert used.isRregistered(begin);
		}

		for (int node : baseIndex) {
			int begin = pointer[node].base;
			if (begin < 0)
				assert -begin - 1 < key.length;
			else
				assert used.isRregistered(begin);
		}

		for (int begin : usedIndex) {
			if (pointer[begin].check >= 0) {
				if (pointer[begin].check == begin)
					assert pointer[begin].base < 0;
				else
					assert pointer[begin].base >= 0;
			}
		}
		assert this.root.size() == baseIndex.length + 1;
		assert usedIndex.length + key.length == baseIndex.length + 1;
	}

	@Override
	public String toString() {
		return root.toString();
	}

	public static class Pointer {
		int base = Integer.MIN_VALUE;
		int check = -1;

		void clear() {
			base = Integer.MIN_VALUE;
			check = -1;
		}
	}

	Pointer[] pointer;
	KeyGenerator used = new KeyGenerator();

	String key[];
	int nextCheckPos;
	State root;

	void resize(int newSize) {
		assert pointer == null || newSize > pointer.length;
		int _newSize = (int) Math.pow(2, (int) Math.ceil(Math.log(newSize) / Math.log(2)));
//		assert _newSize >= newSize;
//		assert _newSize / 2 < newSize;

		newSize = _newSize;

		Pointer[] _pointer = new Pointer[newSize];

		int start;
		if (pointer != null) {
			System.arraycopy(pointer, 0, _pointer, 0, pointer.length);
			start = pointer.length;
		} else
			start = 0;

		for (int i = start; i < newSize; ++i)
			_pointer[i] = new Pointer();

		pointer = _pointer;
	}

	public boolean add(String keyword) {
		int index = Arrays.binarySearch(key, keyword);
		if (index >= 0)
			return false;
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
			currentState = currentState.addState(ch, index);
			if (currentState.code == -1 && crutialState == null) {
				crutialState = parent.peek();
				crutialIndex = i;
			}
		}

		currentState.addEmit(index);
		if (crutialIndex >= 0) {
			char ch = keyword.charAt(crutialIndex);
			update(crutialState, ch);
		} else {
			crutialIndex = keyword.length() - 1;
		}

		while (!parent.isEmpty()) {
			State father = parent.pop();
			for (Entry<Character, State> p : father.success.tailMap(keyword.charAt(crutialIndex), false).entrySet()) {
				p.getValue().incrementKeyIndex();
			}
			--crutialIndex;
		}
		return true;
	}

	public boolean remove(String keyword) {
		int index = Arrays.binarySearch(key, keyword);
		if (index < 0)
			return false;

		String[] _key = new String[key.length - 1];
		System.arraycopy(key, 0, _key, 0, index);
		System.arraycopy(key, index + 1, _key, index, key.length - 1 - index);
		key = _key;

		State currentState = this.root;
		Stack<State> parent = new Stack<State>();
		for (int i = 0; i < keyword.length(); ++i) {
			char ch = keyword.charAt(i);
			parent.add(currentState);
			currentState = currentState.success.get(ch);
		}

		char unicode = currentState.deleteEmit(parent);
//		if (crutialIndex >= 0) {
//			char ch = keyword.charAt(crutialIndex);
//			update(crutialState, ch);
//		} else {
//		int crutialIndex = keyword.length() - 1;
//		}
//
		for (;;) {
			State node = parent.pop();
			for (Entry<Character, State> p : node.success.tailMap(unicode, false).entrySet()) {
				p.getValue().decrementKeyIndex();
			}
			if (parent.isEmpty())
				break;
			unicode = keyword.charAt(parent.size() - 1);
		}
		return true;
	}

	int try_base(TreeMap<Character, State> success) {
		int begin = 0;
		int pos = Math.max(success.firstKey(), nextCheckPos) - 1;

//		int nonzero_num = 0;
		int first = 0;

		outer: while (true) {
			++pos;

			if (pos < pointer.length && pointer[pos].check >= 0) {
//				nonzero_num++;
				continue;
			}

			if (first == 0) {
				nextCheckPos = pos;
//				System.out.printf("nextCheckPos = %d,\t", nextCheckPos);
				first = 1;
			}

			begin = pos - success.firstKey();

			if (used.isRregistered(begin))
				continue;

			Iterator<Entry<Character, State>> iterator = success.entrySet().iterator();

			if (iterator.hasNext()) {
				iterator.next();
				while (iterator.hasNext()) {
					Entry<Character, State> p = iterator.next();
					int addr = begin + p.getKey();
					if (addr < pointer.length && pointer[addr].check >= 0)
						continue outer;
				}
			}

			break;
		}

//		if (1.0 * nonzero_num / (pos - nextCheckPos + 1) >= 0.95)
//			nextCheckPos = pos;

		return begin;
	}

	void insert(State root) {
		int begin = root.try_base_with_optimal_start_point();
//		int begin = try_base(root.success);

		root.initialize(begin);

		for (Entry<Character, State> p : root.success.entrySet()) {
			State node = p.getValue();
			if (node.success.isEmpty()) {
				pointer[node.code].base = -node.emit - 1;
//				assert pointer[node.code].check == begin;
			} else {
				insert(node);
			}
		}

//		root.check_validity();
	}

	void occupy(int begin) {
//		System.out.println("occupy begin = " + begin);
//		assert !used.isRregistered(begin);
		used.register_key(begin);
	}

	void recycle(int begin) {
//		if (begin > 0) {
//			assert used.isRregistered(begin);
		used.unregister_key(begin);
//		}
	}

	void update(State parent, char word) {
		int code = parent.code;
		State state = parent.success.get(word);
		int begin = getBase(code);
		int pos = 0;
		boolean need_update_base;
		if (begin == Integer.MIN_VALUE) {
			need_update_base = true;
		} else {
			assert code == -1 || pointer[code].check >= 0;
			pos = begin + word;
			need_update_base = pointer[pos].check >= 0;
		}

		if (need_update_base) {
			begin = parent.update_base();
			pos = begin + word;
		}

		if (!used.isRregistered(begin)) {
			occupy(begin);
		}

		state.code = pos;

		assert pointer[pos].base == Integer.MIN_VALUE;
		pointer[pos].check = begin;

		if (getBase(code) != begin) {
			this.recycle(getBase(code));

			for (Entry<Character, State> entry : parent.success.entrySet()) {
				char key = entry.getKey();
				if (key != word) {
					State sibling = entry.getValue();
					pointer[sibling.code].clear();

					int new_code = begin + key;
					sibling.code = new_code;
					assert pointer[new_code].check == -1;
					pointer[new_code].check = begin;

					sibling.update_base();
				}
			}

			setBase(code, begin);
			assert pointer[code].check >= 0;
		}

		if (state.success.isEmpty()) {
			pointer[pos].base = -state.emit - 1;
			assert pointer[pos].check != 0;
		} else {
			assert state.success.size() == 1;
			Entry<Character, State> entry = state.success.firstEntry();
			update(state, entry.getKey());
		}

		parent.check_validity();
	}

	public int[] getNonzeroCheckIndex() {
		List<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < pointer.length; ++i)
			if (pointer[i].check >= 0) {
				indices.add(i);
			}

		int[] arr = Utility.toArray(indices);
		if (arr == null)
			return new int[] {};
		return arr;
	}

	public int[] getNonzeroBaseIndex() {
		List<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < pointer.length; ++i)
			if (pointer[i].base != Integer.MIN_VALUE) {
				indices.add(i);
			}

		int[] arr = Utility.toArray(indices);
		if (arr == null)
			return new int[] {};
		return arr;
	}

	public int[] getValidUsedIndex() {
		List<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < pointer.length; ++i) {
			if (!used.isRregistered(i))
				continue;

			indices.add(i);
		}

		int[] arr = Utility.toArray(indices);
		if (arr == null)
			return new int[] {};
		return arr;
	}

	public DoubleArrayTrie(List<String> list) {
		this(new TreeSet<String>(list));
	}

	public DoubleArrayTrie(TreeSet<String> _key) {
		key = new String[_key.size()];
		key = _key.toArray(key);

		resize(65536);
//		nextCheckPos = 0;

		root = new State(0, 0);
		root.fetch(key.length);
		insert(root);

		shrink_memory();
//		shrink();
	}

	void shrink_memory() {
		int length = 0;
		for (int i = pointer.length - 1; i >= 0; --i) {
			if (pointer[i].check != 0) {
				length = i + 1;
				break;
			}
		}

		if (length < pointer.length) {
			Pointer[] _pointer = new Pointer[length];
			System.arraycopy(pointer, 0, _pointer, 0, length);
			pointer = _pointer;
		}
	}

	void shrink() {
		shrink_memory();
		int[] begin2base = new int[pointer.length];
		Arrays.fill(begin2base, -1);
		for (int parent = pointer.length - 1; parent >= 0; --parent) {
			int begin = pointer[parent].base;
			if (begin > 0)
				begin2base[begin] = parent;
		}

		State[] states = new State[pointer.length];
		root.copy2array(states);

		int kinder = pointer.length - 1;
		for (; kinder >= 0; --kinder) {
			int begin = pointer[kinder].check;
			if (begin > 0) {
//				assert begin2base[begin] >= 0;
				int parentAddr = begin2base[begin];

				State parent = parentAddr < 0 ? this.root : states[parentAddr];
				assert parent != null;

				assert parent.code == parentAddr;
				assert getBase(parent.code) == begin;
				if (parent.update_base(states) == begin)
					break;
				System.out.println("begin = " + begin);
			}
		}

		++kinder;

		if (kinder < pointer.length) {
			shrink_memory();
		}
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

		int b = pointer[nodePos].base;
		int p;

		for (int i = pos; i < len; ++i) {
			p = b + keyChars[i];
			if (b == pointer[p].check)
				b = pointer[p].base;
			else
				return result;
		}

		p = b;
		int n = pointer[p].base;
		if (b == pointer[p].check && n < 0) {
			result = -n - 1;
		}
		return result;
	}

	public boolean contains(String key) {
		int index = contains_utility(key);
		if (index < 0) {
			return false;
		}
		return this.key[index].length() == key.length();
	}

	public int[][] testPrefixSearch() {
		for (String k : this.key) {
			assert this.contains(k);
		}

		int[][] arrs = new int[this.key.length][];
		int j = 0;
		for (String key : this.key) {
			List<Integer> list = commonPrefixSearch(key);
			int[] arr = new int[list.size()];
			int i = 0;
			for (int index : commonPrefixSearch(key)) {
//				System.out.printf("key[%d] = %s\n", index, this.key[index]);
				assert key.startsWith(this.key[index]);
				arr[i++] = index;
			}
			arrs[j++] = arr;
		}
		return arrs;
	}

	public List<Integer> commonPrefixSearch(String key) {
		int pos = 0;
		int nodePos = -1;
		int len = key.length();

		List<Integer> result = new ArrayList<Integer>();

		char[] keyChars = key.toCharArray();

		int b = 0;
		int base_value;

		for (int i = pos; i < len; ++i) {
			base_value = pointer[b].base;

			if (b == pointer[b].check && base_value < 0) {
				result.add(-base_value - 1);
			}

			if (pointer[b + (keyChars[i])].check != b) {
				System.out.printf("%d = base[%d] + '%c'(%d)\n", b + (keyChars[i]), nodePos, keyChars[i],
						(int) keyChars[i]);

				System.out.printf("check[%d] = %d\n", b + (keyChars[i]), pointer[b + (keyChars[i])].check);
				System.out.printf("base[%d] = %d\n", nodePos, b);
			}
			assert pointer[b + keyChars[i]].check == b;

			nodePos = b + (keyChars[i]);

			if (b == pointer[nodePos].check)
				b = pointer[nodePos].base;
			else
				return result;
		}

		base_value = pointer[b].base;

		if (b == pointer[b].check && base_value < 0) {
			result.add(-base_value - 1);
		}

		return result;
	}

	int contains_utility(String key) {
		int pos = 0;
		int nodePos = -1;
		int len = key.length();

		int result = -1;

		char[] keyChars = key.toCharArray();

		int b = 0;
		int base_value;

		for (int i = pos; i < len; ++i) {
			base_value = pointer[b].base;

			if (b == pointer[b].check && base_value < 0) {
				result = -base_value - 1;
			}

			nodePos = b + (keyChars[i]);

			if (b == pointer[nodePos].check)
				b = pointer[nodePos].base;
			else
				return result;
		}

		base_value = pointer[b].base;

		if (b == pointer[b].check && base_value < 0) {
			result = -base_value - 1;
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
		DoubleArrayTrie dat = new DoubleArrayTrie(words);
		System.out.println("time cost = " + (System.currentTimeMillis() - start));
		System.out.println("space cost = " + dat.pointer.length);

		if (debug) {
			System.out.println(dat);
		}

		dat.checkValidity();
		int[][] arr = dat.testPrefixSearch();
		start = System.currentTimeMillis();
		DoubleArrayTrie _dat = new DoubleArrayTrie(words);
		System.out.println("time cost = " + (System.currentTimeMillis() - start));
		System.out.println("space cost = " + _dat.pointer.length);
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
			_dat.add(word);
			if (debug) {
				System.out.println(_dat);
			}
			_dat.checkValidity();
			assert dat.equals(_dat);
			int[][] _arr = _dat.testPrefixSearch();
			assert Utility.equals(arr, _arr);
		}
	}

	static final boolean debug = false;
}