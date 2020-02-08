package test;

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import org.ahocorasick.trie.Trie;

import java.io.*;
import java.util.*;

/**
 * @author hankcs
 */
public class TestAhoCorasickDoubleArrayTrie {
	private AhoCorasickDoubleArrayTrie<String> buildASimpleAhoCorasickDoubleArrayTrie() {
		// Collect test data set
		TreeMap<String, String> map = new TreeMap<String, String>();
		String[] keyArray = new String[] { "hers", "his", "she", "he" };
		for (String key : keyArray) {
			map.put(key, key);
		}
		// Build an AhoCorasickDoubleArrayTrie
		AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<String>();
		acdat.build(map);
		return acdat;
	}

	private void validateASimpleAhoCorasickDoubleArrayTrie(AhoCorasickDoubleArrayTrie<String> acdat) {
		// Test it
		final String text = "uhers";
		acdat.parseText(text, new AhoCorasickDoubleArrayTrie.IHit<String>() {
			@Override
			public void hit(int begin, int end, String value) {
				System.out.printf("[%d:%d]=%s\n", begin, end, value);
				assert (text.substring(begin, end) == value);
			}
		});
		// Or simply use
		List<AhoCorasickDoubleArrayTrie.Hit<String>> wordList = acdat.parseText(text);
		System.out.println(wordList);
	}

	public void testBuildAndParseSimply() throws Exception {
		AhoCorasickDoubleArrayTrie<String> acdat = buildASimpleAhoCorasickDoubleArrayTrie();
		validateASimpleAhoCorasickDoubleArrayTrie(acdat);
	}

	public void testBuildAndParseWithBigFile() throws Exception {
		// Load test data from disk
		Set<String> dictionary = loadDictionary("cn/dictionary.txt");
		final String text = loadText("cn/text.txt");
		// You can use any type of Map to hold data
		Map<String, String> map = new TreeMap<String, String>();
//        Map<String, String> map = new HashMap<String, String>();
//        Map<String, String> map = new LinkedHashMap<String, String>();
		for (String key : dictionary) {
			map.put(key, key);
		}
		// Build an AhoCorasickDoubleArrayTrie
		AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<String>();
		acdat.build(map);
		// Test it
		acdat.parseText(text, new AhoCorasickDoubleArrayTrie.IHit<String>() {
			@Override
			public void hit(int begin, int end, String value) {
				assert (text.substring(begin, end) == value);
			}
		});
	}

	private static class CountHits implements AhoCorasickDoubleArrayTrie.IHitCancellable<String> {
		private int count;
		private boolean countAll;

		CountHits(boolean countAll) {
			this.count = 0;
			this.countAll = countAll;
		}

		public int getCount() {
			return count;
		}

		@Override
		public boolean hit(int begin, int end, String value) {
			count += 1;
			return countAll;
		}
	}

	public void testMatches() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("space", 1);
		map.put("keyword", 2);
		map.put("ch", 3);
		AhoCorasickDoubleArrayTrie<Integer> trie = new AhoCorasickDoubleArrayTrie<Integer>();
		trie.build(map);

		assert (trie.matches("space"));
		assert (trie.matches("keyword"));
		assert (trie.matches("ch"));
		assert (trie.matches("  ch"));
		assert (trie.matches("chkeyword"));
		assert (trie.matches("oooospace2"));
		assert (!trie.matches("c"));
		assert (!trie.matches(""));
		assert (!trie.matches("spac"));
		assert (!trie.matches("nothing"));
	}

	public void testFirstMatch() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("space", 1);
		map.put("keyword", 2);
		map.put("ch", 3);
		AhoCorasickDoubleArrayTrie<Integer> trie = new AhoCorasickDoubleArrayTrie<Integer>();
		trie.build(map);

		AhoCorasickDoubleArrayTrie.Hit<Integer> hit = trie.findFirst("space");
		assert (0 == hit.begin);
		assert (5 == hit.end);
		assert (1 == hit.value.intValue());

		hit = trie.findFirst("a lot of garbage in the space ch");
		assert (24 == hit.begin);
		assert (29 == hit.end);
		assert (1 == hit.value.intValue());

		assert(trie.findFirst("") == null);
		assert(trie.findFirst("value") == null);
		assert(trie.findFirst("keywork") == null);
		assert(trie.findFirst(" no pace") == null);
	}

	public void testCancellation() throws Exception {
		// Collect test data set
		TreeMap<String, String> map = new TreeMap<String, String>();
		String[] keyArray = new String[] { "foo", "bar" };
		for (String key : keyArray) {
			map.put(key, key);
		}
		// Build an AhoCorasickDoubleArrayTrie
		AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<String>();
		acdat.build(map);
		// count matches
		String haystack = "sfwtfoowercwbarqwrcq";
		CountHits cancellingMatcher = new CountHits(false);
		CountHits countingMatcher = new CountHits(true);
		System.out.println("Testing cancellation");
		acdat.parseText(haystack, cancellingMatcher);
		acdat.parseText(haystack, countingMatcher);
		assert (cancellingMatcher.count == 1);
		assert (countingMatcher.count == 2);
	}

	private String loadText(String path) throws IOException {
		StringBuilder sbText = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				Thread.currentThread().getContextClassLoader().getResourceAsStream(path), "UTF-8"));
		String line;
		while ((line = br.readLine()) != null) {
			sbText.append(line).append("\n");
		}
		br.close();

		return sbText.toString();
	}

	private Set<String> loadDictionary(String path) throws IOException {
		Set<String> dictionary = new TreeSet<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				Thread.currentThread().getContextClassLoader().getResourceAsStream(path), "UTF-8"));
		String line;
		while ((line = br.readLine()) != null) {
			dictionary.add(line);
		}
		br.close();

		return dictionary;
	}

	private void runTest(String dictionaryPath, String textPath) throws IOException {
		Set<String> dictionary = loadDictionary(dictionaryPath);
		String text = loadText(textPath);
		TreeMap<String, String> dictionaryMap = new TreeMap<String, String>();
		for (String word : dictionary) {
			dictionaryMap.put(word, word); // we use the same text as the property of a word
		}

		// Build a ahoCorasickNaive implemented by robert-bor
		Trie ahoCorasickNaive = new Trie();
		ahoCorasickNaive.build(dictionaryMap);

		// Build a AhoCorasickDoubleArrayTrie implemented by hankcs
		AhoCorasickDoubleArrayTrie<String> ahoCorasickDoubleArrayTrie = new AhoCorasickDoubleArrayTrie<String>();
		ahoCorasickDoubleArrayTrie.build(dictionaryMap);
		// Let's test the speed of the two Aho-Corasick automata
		System.out.printf("Parsing document which contains %d characters, with a dictionary of %d words.\n",
				text.length(), dictionary.size());
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

	/**
	 * Compare my AhoCorasickDoubleArrayTrie with robert-bor's aho-corasick, notice
	 * that robert-bor's aho-corasick is compiled under jdk1.8, so you will need
	 * jdk1.8 to run this test<br>
	 * To avoid JVM wasting time on allocating memory, please use -Xms512m -Xmx512m
	 * -Xmn256m .
	 *
	 * @throws Exception
	 */
	public void testBenchmark() throws Exception {
		runTest("en/dictionary.txt", "en/text.txt");
		runTest("cn/dictionary.txt", "cn/text.txt");
	}

	public void testSaveAndLoad() throws Exception {
		AhoCorasickDoubleArrayTrie<String> acdat = buildASimpleAhoCorasickDoubleArrayTrie();
		final String tmpPath = System.getProperty("java.io.tmpdir").replace("\\\\", "/") + "/acdat.tmp";
		System.out.println("Saving acdat to: " + tmpPath);
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(tmpPath));
		out.writeObject(acdat);
		out.close();
		System.out.println("Loading acdat from: " + tmpPath);
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(tmpPath));
		acdat = (AhoCorasickDoubleArrayTrie<String>) in.readObject();
		validateASimpleAhoCorasickDoubleArrayTrie(acdat);
	}

	public void testBuildEmptyTrie() {
		AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<String>();
		TreeMap<String, String> map = new TreeMap<String, String>();
		acdat.build(map);
		assert (0 == acdat.size());
	}
}