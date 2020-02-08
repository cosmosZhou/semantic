package test;

import static test.Common.dictionaryMap;
import static test.Common.naiveConstruct;
import static test.Common.text;

import java.util.ArrayList;
import java.util.Collections;

import org.ahocorasick.trie.Trie;

/**
 * @author hankcs
 */
public class TestDelete {
	public void test() throws Exception {
//		try (
//
//				Utility.Printer printer = new Utility.Printer("./debug.txt")) {
		Trie trieDynamic = naiveConstruct();
		Trie trieWhole = naiveConstruct();
		ArrayList<String> list = Common.loadDictionary();
		Collections.shuffle(list);
		for (String wordsToBeDeleted : list) {
			dictionaryMap.remove(wordsToBeDeleted);

			System.out.println("testing word: " + wordsToBeDeleted);

			Trie naiveConstruct = naiveConstruct();
			trieDynamic.erase(wordsToBeDeleted);

			boolean equals = naiveConstruct.rootState.equals(trieDynamic.rootState);
			assert (equals);
			assert (naiveConstruct.parseText(text).size() == trieDynamic.parseText(text).size());

			dictionaryMap.put(wordsToBeDeleted, wordsToBeDeleted);
			trieDynamic.update(wordsToBeDeleted, wordsToBeDeleted);

			equals = trieWhole.rootState.equals(trieDynamic.rootState);
			assert (equals);
			assert (trieWhole.parseText(text).size() == trieDynamic.parseText(text).size());
		}
//		}
	}
}
