package test;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import org.ahocorasick.trie.Trie;

import com.util.Utility;


import static test.Common.*;


/**
 * @author hankcs
 */
public class TestUpdate {
	static Set<String> loadDictionary(String path) throws IOException {
		Set<String> dictionary = new TreeSet<String>();
		new Utility.Text(path).collect(dictionary);
		return dictionary;
	}

	static String loadText(String path) throws IOException {
		return new Utility.Text(path).fetchContent();
	}

	public void testNaiveConstruct() throws Exception {
		assert(countNaiveConstruct() == countAhoCorasickDoubleArrayTrie());
	}

	static public void testSearchTrie() throws Exception {
		Trie naiveConstruct = naiveConstruct();
		Trie naiveUpdate = naiveUpdate();
		boolean equals = naiveConstruct.root.equals(naiveUpdate.root);
		assert(equals);
		assert(naiveConstruct.parseText(text).size() == naiveUpdate.parseText(text).size());
	}
	
	public static void main(String[] args) throws Exception {
		testSearchTrie();
	}
}
