package test;

import java.io.IOException;
import java.util.TreeMap;

import org.ahocorasick.trie.Trie;

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrieZeroBased;

/**
 * @author hankcs
 */
public class TestAhoCorasickDoubleArrayTrie {

	static void runTest() throws IOException {

		String text = Common.text;
		TreeMap<String, String> dictionaryMap = Common.dictionaryMap;

		// Build a ahoCorasickNaive implemented by robert-bor
		Trie ahoCorasickNaive = new Trie(dictionaryMap);

		// Build a AhoCorasickDoubleArrayTrie implemented by hankcs
		AhoCorasickDoubleArrayTrieZeroBased<String> ahoCorasickDoubleArrayTrie = new AhoCorasickDoubleArrayTrieZeroBased<String>(
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

	public static void main(String[] args) throws Exception {
		runTest();
//		runTest();
//		runTest();
//		runTest();
//		runTest();
//		testBenchmark();
	}
}
