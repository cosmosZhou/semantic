
import com.util.Utility;

import junit.framework.TestCase;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import org.ahocorasick.trie.State;
import org.ahocorasick.trie.Trie;

/**
 * @author hankcs
 */
public class DetectFault extends TestCase {
	static ArrayList<String> loadDictionary(String path, int limit) throws IOException {
		ArrayList<String> dictionary = new ArrayList<String>();
		new Utility.Text(path).collect(dictionary, limit);
		return dictionary;
	}

	static ArrayList<String> loadDictionary(String path) throws IOException {
		ArrayList<String> dictionary = new ArrayList<String>();
		new Utility.Text(path).collect(dictionary);
		return dictionary;
	}

	static String loadText(String path) throws IOException {
		return new Utility.Text(path).fetchContent();
//		return new Utility.Text(Thread.currentThread().getContextClassLoader().getResourceAsStream(path))
//				.fetchContent();
	}
	static TreeMap<String, String> dictionaryMap;
	final static boolean debug = false;

	void initialize(int limit) throws IOException {
		dictionaryMap = new TreeMap<String, String>();
		ArrayList<String> dictionary = loadDictionary("dictionary.txt", limit);
		for (String word : dictionary) {
			dictionaryMap.put(word, String.format("[%s]", word));
		}
		System.out.println("dictionary.size() = " + dictionary.size());
	}

	void initialize(Collection<String> dictionary) throws IOException {
		dictionaryMap = new TreeMap<String, String>();
		for (String word : dictionary) {
			dictionaryMap.put(word, String.format("[%s]", word));
		}
		System.out.println("dictionary.size() = " + dictionary.size());
	}


	State naiveUpdate() throws Exception {

		Trie ahoCorasickNaive = new Trie();

		for (Entry<String, String> entry : dictionaryMap.entrySet()) {
			ahoCorasickNaive.update(entry.getKey(), entry.getValue());
			if (debug)
				System.out.println(ahoCorasickNaive.rootState);
		}
		System.out.println("construction finished");
		return ahoCorasickNaive.rootState;
	}

	State naiveConstruct() {
		Trie ahoCorasickNaive = new Trie();

		System.out.println(ahoCorasickNaive.rootState);

		ahoCorasickNaive.build(dictionaryMap);

		return ahoCorasickNaive.rootState;
	}

	public boolean testNaiveUpdate() throws Exception {
		return naiveConstruct().equals(naiveUpdate());
	}

	void rotate(ArrayList<String> list) {
		int initial_size = list.size();

		int size_to_be_moved = initial_size / split_size;

		int new_size = initial_size - size_to_be_moved;
		ArrayList<String> newlist = new ArrayList<String>();

		newlist.addAll(list.subList(new_size, initial_size));
		newlist.addAll(list.subList(0, new_size));

		list.clear();
		list.addAll(newlist);
	}

	final int split_size = 5;

	boolean run_epoch(ArrayList<String> list) throws Exception {
		ArrayList<String> newlist;
		for (int i = 0; i < split_size; ++i) {
			int initial_size = list.size();

			int size_to_be_moved = initial_size / split_size;
			if (size_to_be_moved == 0)
				return false;
			int new_size = initial_size - size_to_be_moved;

			newlist = new ArrayList<String>();
//[*][*][*](*)			
			newlist.addAll(list.subList(0, new_size));
			initialize(newlist);
			if (!testNaiveUpdate()) {
				Utility.writeString("dictionary.txt", newlist);
				System.out.println("successfully shrinking the erroneous dataset!");
				System.out.println("newlist.size() = " + newlist.size());
				return true;
			}
			rotate(list);
		}
		return false;
	}

	public void test_detect_fault() throws Exception {
		Utility.writeString("dictionary.txt", loadDictionary("dictionary.txt", 10000));
		do {
			ArrayList<String> list = loadDictionary("dictionary.txt");
			if (!run_epoch(list))
				break;
		} while (true);
	}
}
