package com.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;

public class KeyGenerator implements Iterable<Integer> {
	/**
	 * 
	 */

	static class Couplet implements Comparable<Couplet> {
		Couplet(int x, int y) {
			this.x = x;
			this.y = y;
		}

		int x, y;

		@Override
		public int compareTo(Couplet o) {
			return Integer.compare(x, o.x);
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return String.format("[%d, %d)", x, y);
		}
	}

	TreeSet<Couplet> set = new TreeSet<Couplet>();

	public static void main(String[] args) throws Exception {
		KeyGenerator keyGenerator = new KeyGenerator();
		Random r = new java.util.Random();

		int arr[] = new int[400];
		HashSet<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < arr.length; ++i) {
			int a;
			while (true) {
//				a = 1 + r.nextInt(4000);
				a = 100 + i * r.nextInt(4);

				if (set.contains(a))
					continue;
				break;
			}
			arr[i] = a;
			set.add(a);
		}

		log.info(set.toString());

		for (int a : arr) {
			log.info("registering " + a);
			keyGenerator.register_key(a);
			log.info(keyGenerator);

			assert keyGenerator.isRregistered(a);
		}

		int[] newKeys = new int[10000];
		int i = 0;
		for (int key : keyGenerator) {
			newKeys[i++] = key;
			if (i >= newKeys.length)
				break;
		}

//		for (i = 0; i < newKeys.length; ++i) {
//			Iterator<Integer> iterator = keyGenerator.iterator();
//			iterator.hasNext();
//
//			int _key = iterator.next();
//			int key = keyGenerator.generate_key();
//
//			assert (key == _key);
//
//			if (!set.add(key))
//				throw new Exception("logic error");
//			log.info("registering " + key);
//			assert key == newKeys[i];
//		}

		for (int fromElement = 1; fromElement < 1000; ++fromElement) {
			System.out.println("fromElement = " + fromElement);
			for (int next : keyGenerator.tailSet(fromElement)) {
				assert next > fromElement;
				for (int j = fromElement + 1; j < next; ++j)
					assert keyGenerator.isRregistered(j);
				break;
			}
		}

		for (int a : set) {
			keyGenerator.unregister_key(a);
			assert !keyGenerator.isRregistered(a);
		}
	}

	public int elementSize() {
		int sum = 0;
		for (Couplet e : this.set) {
			sum += e.y - e.x;
		}
		return sum;
	}

	public KeyGenerator conjugate() {
		assert !set.isEmpty();

		Iterator<Couplet> iterator = set.iterator();
		Couplet prev = iterator.next();

		while (iterator.hasNext()) {
			Couplet curr = iterator.next();
			prev.x = prev.y;
			prev.y = curr.x;
			prev = curr;
		}
		set.pollLast();
		return this;
	}

	public int[] keySet() {
		int[] arr = new int[elementSize()];
		int i = 0;
		for (Couplet s : set) {
			for (int j = s.x; j < s.y; ++j) {
				arr[i++] = j;
			}
		}
		return arr;
	}

	@Override
	public synchronized String toString() {
		String str = "";
		for (Couplet s : set) {
			str += s.x + " -> " + s.y + " = " + (s.y - s.x);
			str += "\n";
		}
		// TODO Auto-generated method stub
		return str;
	}

	public void register_key(int key) {
		Couplet newElement = new Couplet(key, key + 1);
		Couplet mid = set.floor(newElement);

		if (mid != null) {
			assert mid.x != key : "the key was registered already!";
			assert mid.x < key;
			SortedSet<Couplet> tailSet = set.tailSet(mid, false);
			if (mid.y == key) {
				++mid.y;
				if (!tailSet.isEmpty()) {
					Iterator<Couplet> iterator = tailSet.iterator();
					Couplet next = iterator.next();
					if (next.x == mid.y) {
						mid.y = next.y;
						iterator.remove();
					}
				}
				return;
			}
			Iterator<Couplet> iterator = tailSet.iterator();
			if (iterator.hasNext()) {
				Couplet higher = iterator.next();
				if (key + 1 == higher.x) {
					--higher.x;
					return;
				}
			}

		} else {
			Iterator<Couplet> iterator = set.iterator();
			if (iterator.hasNext()) {
				Couplet first = iterator.next();

				if (key + 1 == first.x) {
					--first.x;
					return;
				}
			}
		}

		set.add(newElement);
	}

	public boolean isRregistered(int key) {
		Couplet newElement = new Couplet(key, key + 1);
		Couplet mid = set.floor(newElement);

		if (mid != null) {
			return key < mid.y;
		}
		return false;
	}

	public void unregister_key(int key) {
		Couplet newElement = new Couplet(key, key + 1);
		Iterator<Couplet> iterator = set.headSet(newElement, true).descendingIterator();

		if (iterator.hasNext()) {
			Couplet mid = iterator.next();
			assert mid.x < mid.y;
			if (mid.x == key) {
				++mid.x;
				if (mid.x == mid.y) {
					iterator.remove();
				}
				return;
			}
			assert mid.x < key;
			assert key < mid.y;
			newElement.y = mid.y;
			mid.y = key;

			++newElement.x;
			if (newElement.x != newElement.y)
				set.add(newElement);
		} else {
			iterator = set.iterator();
			Couplet first = iterator.next();

			if (key == first.x) {
				++first.x;
				if (first.x == first.y) {
					iterator.remove();
				}
				return;
			}

		}
	}

	final int initial_key = 0;
	
	public int generate_key() throws Exception {
		int key;
		if (set.size() == 0)
			key = initial_key;
		else
			key = set.first().y;
		register_key(key);
		return key;
	}

	public static Logger log = Logger.getLogger(KeyGenerator.class);

	class KeyIterator implements Iterator<Integer> {

		Iterator<Couplet> iterator;
		Couplet prev, curr;

		KeyIterator() {
			iterator = KeyGenerator.this.set.iterator();
			if (iterator.hasNext()) {
				prev = iterator.next();
				key = prev.x - 1;
				curr = prev;
			} else
				key = initial_key - 1;
		}

		KeyIterator(int fromElement) {
			Couplet element = new Couplet(fromElement, fromElement + 1);
			Iterator<Couplet> descendingIterator = set.headSet(element, true).descendingIterator();
			if (descendingIterator.hasNext()) {
				Couplet lower = descendingIterator.next();

				iterator = set.tailSet(lower).iterator();
				if (iterator.hasNext()) {
					prev = iterator.next();
					if (prev.y <= fromElement) {
						key = fromElement;
						if (iterator.hasNext()) {
							curr = iterator.next();
						}
					} else {
						key = prev.x - 1;
						curr = prev;
					}
				} else {
					assert false;
				}
			} else {
				key = fromElement;
				iterator = KeyGenerator.this.set.iterator();
				if (iterator.hasNext()) {
					prev = iterator.next();
					curr = prev;
				}
			}
		}

		int key;

		@Override
		public boolean hasNext() {
			++key;
			if (curr != null && this.key == curr.x) {
				key = curr.y;
				prev = curr;
				if (iterator.hasNext()) {
					curr = iterator.next();
				} else
					curr = null;
			}

			return true;
		}

		@Override
		public Integer next() {
			return key;
		}

	}

	@Override
	public Iterator<Integer> iterator() {
		return new KeyIterator();
	}

	public class TailSet implements Iterable<Integer> {
		TailSet(int start) {
			this.start = start;
		}

		int start;

		@Override
		public Iterator<Integer> iterator() {
			return new KeyIterator(start);
		}
	}

	public TailSet tailSet(int start) {
		return new TailSet(start);
	}

	public int lower(int key) {
		Couplet element = new Couplet(key, key + 1);
		Couplet lower = set.lower(element);
		if (lower != null) {
			if (lower.y - 1 < key)
				return lower.y - 1;

			key = lower.y - 1;
			lower = set.lower(lower);
			if (lower != null) {
				return lower.y - 1;
			}	
		}

		return key;
	}
}
