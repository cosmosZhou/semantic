package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import com.util.Utility;

/**
 * @author hankcs
 */
public class Common {
	static ArrayList<String> loadDictionary() throws IOException {
		return loadDictionary("dictionary.txt");
	}
	
	static ArrayList<String> loadDictionary(String path) throws IOException {
		ArrayList<String> dictionary = new ArrayList<String>();
		new Utility.Text(path).collect(dictionary);
		return dictionary;
	}

	static ArrayList<String> loadDictionary(String path, int limit) throws IOException {
		ArrayList<String> dictionary = new ArrayList<String>();
		new Utility.Text(path).collect(dictionary, limit);
		return dictionary;
	}

	static String loadText(String path) throws IOException {
		return new Utility.Text(path).fetchContent();
	}

	public static int countAhoCorasickDoubleArrayTrie() throws Exception {

		// Build a AhoCorasickDoubleArrayTrie implemented by hankcs
		AhoCorasickDoubleArrayTrie<String> ahoCorasickDoubleArrayTrie = new AhoCorasickDoubleArrayTrie<String>();

		ahoCorasickDoubleArrayTrie.build(dictionaryMap);

		ArrayList<String> result = new ArrayList<String>();

		ahoCorasickDoubleArrayTrie.parseText(text, new AhoCorasickDoubleArrayTrie.IHit<String>() {
			@Override
			public void hit(int begin, int end, String value) {
//				System.out.printf("%s = %s\n", text.substring(begin, end), value);
				result.add(value);
			}
		});
		return result.size();
	}

	static TreeMap<String, String> dictionaryMap;
	static String text;
	static boolean debug = false;
	
	static {
		dictionaryMap = new TreeMap<String, String>();
		try {
			for (String word : loadDictionary()) {
				dictionaryMap.put(word, String.format("[%s]", word));
			}
//			dictionaryMap.remove(wordsToBeDeleted);
			
			System.out.println("dictionary.size() = " + dictionaryMap.size());
			text = loadText("text.txt");
			if (dictionaryMap.size() <= 10) {
				debug = true;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static Trie naiveUpdate() throws Exception {

		Trie ahoCorasickNaive = new Trie();

		for (Entry<String, String> entry : dictionaryMap.entrySet()) {
			ahoCorasickNaive.update(entry.getKey(), entry.getValue());
			if (debug)
				System.out.println(ahoCorasickNaive.rootState);
		}
		System.out.println("construction finished");
		return ahoCorasickNaive;
	}

	static AhoCorasickDoubleArrayTrie<String> naiveUpdate4DoubleArray() throws Exception {

		AhoCorasickDoubleArrayTrie<String> ahoCorasickDoubleArrayTrie = new AhoCorasickDoubleArrayTrie<String>();
//		ahoCorasickDoubleArrayTrie.build(dictionaryMap);

		for (Entry<String, String> entry : dictionaryMap.entrySet()) {
			ahoCorasickDoubleArrayTrie.update(entry.getKey(), entry.getValue());
//			if (debug)
//				System.out.println(ahoCorasickDoubleArrayTrie.rootState);
		}
		System.out.println("construction finished");
		return ahoCorasickDoubleArrayTrie;
	}

	static Trie naiveConstruct() {
		Trie ahoCorasickNaive = new Trie();

		ahoCorasickNaive.build(dictionaryMap);

		return ahoCorasickNaive;
	}

	public static int countNaiveConstruct() throws Exception {

		Trie ahoCorasickNaive = new Trie();

//		System.out.println(ahoCorasickNaive.rootState);

		ahoCorasickNaive.build(dictionaryMap);
		if (debug) {
			System.out.println("building ahocorasic all at once:");
			System.out.println(ahoCorasickNaive.rootState);
		}

		ArrayList<String> result = new ArrayList<String>();
		for (Emit emit : ahoCorasickNaive.parseText(text)) {
//			int begin = emit.getStart();
//			int end = emit.getEnd();
			String value = emit.value;

//			System.out.printf("%s = %s\n", text.substring(begin, end), value);
			result.add(value);
		}
//		System.out.println(ahoCorasickNaive.rootState);

		return result.size();
	}

	static Trie naiveDelete(String wordsToBeDeleted) throws Exception {

		Trie ahoCorasickNaive = new Trie();
		ahoCorasickNaive.build(dictionaryMap);

		if (debug) {
			System.out.println("before deletion:");
			System.out.println(ahoCorasickNaive.rootState);
		}

		ahoCorasickNaive.erase(wordsToBeDeleted);
		if (debug)
			System.out.println(ahoCorasickNaive.rootState);
		
		System.out.println("construction finished");
		return ahoCorasickNaive;
	}
}
