import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import org.ahocorasick.trie.State;
import com.util.Utility;

import junit.framework.TestCase;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

/**
 * @author hankcs
 */
public class TestUpdate extends TestCase {
	static Set<String> loadDictionary(String path) throws IOException {
		Set<String> dictionary = new TreeSet<String>();
		new Utility.Text(path).collect(dictionary, 80000);
		return dictionary;
	}

	static String loadText(String path) throws IOException {
		return new Utility.Text(path).fetchContent();
	}

	public int testAhoCorasickDoubleArrayTrie() throws Exception {

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
	static Set<String> dictionary;
	static String text;
	final static boolean debug = false;
	static {
		dictionaryMap = new TreeMap<String, String>();
		try {
			dictionary = loadDictionary("dictionary.txt");
			for (String word : dictionary) {
				dictionaryMap.put(word, String.format("[%s]", word));
			}
			System.out.println("dictionary.size() = " + dictionary.size());
			text = loadText("text.txt");

		} catch (IOException e) {
			e.printStackTrace();
		}

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

	public void testNaiveConstruct() throws Exception {

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

		assertEquals(result.size(), testAhoCorasickDoubleArrayTrie());

	}

	public void testSearchTrie() throws Exception {
		assertEquals(naiveConstruct(), naiveUpdate());
	}
}
