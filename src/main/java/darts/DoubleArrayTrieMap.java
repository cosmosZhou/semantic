/**
 * DoubleArrayTrie: Java implementation of Darts (Double-ARray Trie System)
 * 
 * <p>
 * Copyright(C) 2001-2007 Taku Kudo &lt;taku@chasen.org&gt;<br />
 * Copyright(C) 2009 MURAWAKI Yugo &lt;murawaki@nlp.kuee.kyoto-u.ac.jp&gt;
 * Copyright(C) 2012 KOMIYA Atsushi &lt;komiya.atsushi@gmail.com&gt;
 * </p>
 * 
 * <p>
 * The contents of this file may be used under the terms of either of the GNU
 * Lesser General Public License Version 2.1 or later (the "LGPL"), or the BSD
 * License (the "BSD").
 * </p>
 */
package darts;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import com.util.Utility;

public class DoubleArrayTrieMap {
	final static int BUF_SIZE = 16384;
	final static int UNIT_SIZE = 8; // size of int + int

	class Node {
		Node(int depth, int left) {
			this.depth = depth;
			this.emit = left;
		}

		int depth;
		int emit;

		TreeMap<Integer, Node> success = new TreeMap<Integer, Node>();

		void fetch(int emit_end) {
			int prev = -1;

			for (int i = this.emit; i < emit_end; i++) {
				if (key[i].length() < this.depth)
					continue;

				String tmp = key[i];

				int cur = -1;
				if (tmp.length() != this.depth)
					cur = tmp.charAt(this.depth);

				assert prev <= cur;

				if (cur != prev || success.size() == 0) {
					if (success.size() != 0) {
						Node last = success.lastEntry().getValue();
						last.fetch(i);
					}

					success.put(cur, new Node(this.depth + 1, i));
				}

				prev = cur;
			}

			if (success.size() != 0) {
				Node last = success.lastEntry().getValue();
				last.fetch(emit_end);
			}
		}

		public String toString() {
			return String.format("%d-%d", emit, depth);
		}
	};

	static class State {
		State(int code, int depth) {
			this.code = code;
			this.depth = depth;
		}

		int code;
		int depth;
		TreeMap<Character, State> success = new TreeMap<Character, State>();
		int emit = -1;

		public Utility.TextTreeNode toShadowTree() {
			Utility.TextTreeNode newNode = new Utility.TextTreeNode('@' + String.valueOf(this.code));
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
					String value = String.valueOf(word);
//					if (state.failure != null && state.failure.depth != 0) {
//						node.value += String.valueOf(state.failure.depth);
//					}
					if (state.emit >= 0) {
						value += '+';
					}
					node.value = value + node.value;
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
					String value = String.valueOf(word);

//					if (state.failure != null && state.failure.depth != 0) {
//						node.value += String.valueOf(state.failure.depth);
//					}
					if (state.emit >= 0) {
						value += '+';
					}
					node.value = value + node.value;
				}
			}

			return newNode;
		}

		@Override
		public String toString() {
			Utility.TextTreeNode root = this.toShadowTree();
//			return root.toString();
			return root.toString(root.max_width() * 3 / 2, true);
		}

	};

	@Override
	public String toString() {
		int depth = 0;
		State root = new State(0, depth);

		for (String k : this.key) {
//			System.out.println("constructing " + k);
			this.constructTrie(k, root);
		}

		return root.toString();
	}

	int check[];
	int base[];

	boolean used[];
	int size;
	int allocSize;
	String key[];
	int keySize;
	int progress;
	int nextCheckPos;

	int resize(int newSize) {
		int[] base2 = new int[newSize];
		int[] check2 = new int[newSize];
		boolean used2[] = new boolean[newSize];
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

	void insert(int code, TreeMap<Integer, Node> siblings) {
		int begin = 0;
		int pos = (char) (siblings.firstKey() + 1);

		if (pos < nextCheckPos) {
			pos = nextCheckPos - 1;
		}

		int nonzero_num = 0;
		int first = 0;

		outer: while (true) {
			++pos;

			if (allocSize <= pos)
				resize(pos + 1);

			if (check[pos] != 0) {
				nonzero_num++;
				continue;
			} else if (first == 0) {
				nextCheckPos = pos;
				first = 1;
			}

			begin = pos - (char) (siblings.firstKey() + 1);
			if (allocSize <= (begin + (char) (siblings.lastKey() + 1)))
				// progress can be zero
				resize((int) (allocSize * Math.max(1.05, 1.0 * keySize / (progress + 1))));

			if (used[begin])
				continue;

			Iterator<Entry<Integer, Node>> iterator = siblings.entrySet().iterator();

			if (iterator.hasNext()) {
				iterator.next();
				while (iterator.hasNext()) {
					Entry<Integer, Node> p = iterator.next();
					if (check[begin + (char) (p.getKey() + 1)] != 0)
						continue outer;
				}
			}

			break;
		}

		if (1.0 * nonzero_num / (pos - nextCheckPos + 1) >= 0.95)
			nextCheckPos = pos;

		used[begin] = true;
		size = Math.max(size, begin + (char)(siblings.lastKey() + 1) + 1);

		for (Entry<Integer, Node> p : siblings.entrySet()) {
			check[begin + (char)(p.getKey() + 1)] = begin;
		}

		for (Entry<Integer, Node> p : siblings.entrySet()) {
			Node node = p.getValue();
			TreeMap<Integer, Node> new_siblings = node.success;
			
			if (new_siblings.isEmpty()) {
				base[begin + (char)(p.getKey() + 1)] = -node.emit - 1;
				progress++;
			} else {
				insert(begin + (char)(p.getKey() + 1), new_siblings);
			}
		}

		base[code] = begin;
		for (Entry<Integer, Node> p : siblings.entrySet()) {
			System.out.printf("check[%d] = ", begin + (char)(p.getKey() + 1));
		}
		System.out.printf("base[%d] = %d", code, begin);
		for (Entry<Integer, Node> p : siblings.entrySet()) {
			Node node = p.getValue();
			if (node.success.isEmpty()) {
				System.out.printf(", \tbase[%d] = %s", begin + (char)(p.getKey() + 1), key[node.emit]);
			}
		}
		System.out.println();
	}

	void clear() {
		check = null;
		base = null;
		used = null;
		allocSize = 0;
		size = 0;
	}

	public int getUnitSize() {
		return UNIT_SIZE;
	}

	public int getSize() {
		return size;
	}

	public int getTotalSize() {
		return size * UNIT_SIZE;
	}

	public int getNonzeroSize() {
		int result = 0;
		for (int i = 0; i < size; i++)
			if (check[i] != 0)
				result++;
		return result;
	}

	public DoubleArrayTrieMap(TreeSet<String> _key) {
		size = 0;
		allocSize = 0;

		key = new String[_key.size()];
		key = _key.toArray(key);
		keySize = key.length;
		progress = 0;

		resize(65536 * 32);
//		base[0] = 1;
		nextCheckPos = 0;

		Node root_node = new Node(0, 0);
		root_node.fetch(keySize);
		insert(0, root_node.success);

		used = null;
	}

	public void open(String fileName) throws IOException {
		File file = new File(fileName);
		size = (int) file.length() / UNIT_SIZE;
		check = new int[size];
		base = new int[size];

		DataInputStream is = null;
		try {
			is = new DataInputStream(new BufferedInputStream(new FileInputStream(file), BUF_SIZE));
			for (int i = 0; i < size; i++) {
				base[i] = is.readInt();
				check[i] = is.readInt();
			}
		} finally {
			if (is != null)
				is.close();
		}
	}

	public void save(String fileName) throws IOException {
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
			for (int i = 0; i < size; i++) {
				out.writeInt(base[i]);
				out.writeInt(check[i]);
			}
			out.close();
		} finally {
			if (out != null)
				out.close();
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

		int b = base[nodePos];
		int p;

		for (int i = pos; i < len; i++) {
			p = b + (int) (keyChars[i]) + 1;
			if (b == check[p])
				b = base[p];
			else
				return result;
		}

		p = b;
		int n = base[p];
		if (b == check[p] && n < 0) {
			result = -n - 1;
		}
		return result;
	}

	public void constructTrie(String key, State root) {
		int nodePos = 0;

		int b = base[nodePos];
		int base_value;

		for (int i = 0; i < key.length(); ++i) {
			int a = key.charAt(i);
			base_value = base[b];

			if (b == check[b] && base_value < 0) {
				root.emit = -base_value - 1;
			}

//			System.out.printf("%d = base[%d] + '%c'(%d) + 1\n", b + a + 1, nodePos, a, a);

			if (root.success.containsKey((char) a)) {
				root = root.success.get((char) a);
			} else {
				int m = b + a + 1;
				State kinder = new State(m, root.depth + 1);
				root.success.put((char) a, kinder);
				root = kinder;
			}

//			System.out.printf("check[%d] = base[%d] = %d\n\n", b + a + 1, nodePos, check[b + a + 1]);

			assert check[b + a + 1] == base[nodePos];

			nodePos = b + a + 1;

			if (b == check[nodePos])
				b = base[nodePos];
			else
				return;
		}

		base_value = base[b];

		if (b == check[b] && base_value < 0) {
			root.emit = -base_value - 1;
		}
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

		int b = base[nodePos];
		int base_value;

		for (int i = pos; i < len; i++) {
			base_value = base[b];

			if (b == check[b] && base_value < 0) {
				result.add(-base_value - 1);
			}

			System.out.printf("%d = base[%d] + '%c'(%d) + 1\n", b + (int) (keyChars[i]) + 1, nodePos, keyChars[i],
					(int) keyChars[i]);

			System.out.printf("check[%d] = base[%d] = %d\n\n", b + (int) (keyChars[i]) + 1, nodePos,
					check[b + (int) (keyChars[i]) + 1]);

			assert check[b + (int) (keyChars[i]) + 1] == base[nodePos];

			nodePos = b + (int) (keyChars[i]) + 1;

			if (b == check[nodePos])
				b = base[nodePos];
			else
				return result;
		}

		base_value = base[b];

		if (b == check[b] && base_value < 0) {
			result.add(-base_value - 1);
		}

		return result;
	}

	// debug
	public void dump() {
		for (int i = 0; i < size; i++) {
			System.err.println("i: " + i + " [" + base[i] + ", " + check[i] + "]");
		}
	}

	public static void main(String[] args) throws IOException {
		TreeSet<String> words = new TreeSet<String>();
//		new Utility.Text("../corpus/ahocorasick/dictionary.txt").collect(words, 5);
		new Utility.Text("../corpus/ahocorasick/small.txt").collect(words);
		System.out.println("字典词条：" + words.size());

		for (String word : words) {
			System.out.println(word);
		}

		DoubleArrayTrieMap dat = new DoubleArrayTrieMap(words);
		System.out.println(dat);

//		List<Integer> integerList = dat.commonPrefixSearch("±％");
		dat.commonPrefixSearch("一举成名天下知");
		dat.commonPrefixSearch("万能胶");
		dat.commonPrefixSearch("一举一动");

//		for (int index : integerList) {
//			System.out.println(dat.key[index]);
//		}

//		System.out.println("dat.base.length = " + dat.base.length);
		int cnt = 0;
		int cntCheck = 0;
		for (int n = 0; n < dat.base.length; ++n) {
			int m = dat.base[n];
			if (m != 0) {
				System.out.printf("base[%d] = %d\n", n, m);
				++cnt;
			}
		}

		System.out.println();
		for (int m = 0; m < dat.check.length; ++m) {
			int n = dat.check[m];
			if (n != 0) {
				System.out.printf("check[%d] = %d\n", m, n);
				++cntCheck;
			}
		}
		System.out.println();
		System.out.println("cnt = " + cnt);
		System.out.println("cntCheck = " + cntCheck);
	}
}