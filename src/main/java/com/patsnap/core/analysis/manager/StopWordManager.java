package com.patsnap.core.analysis.manager;

import java.util.HashMap;
import java.util.Map;

import com.util.MySQL;
import com.util.Native;

public class StopWordManager {
	public static class Chinese extends StopWordManager {
		Chinese() {
			super("cn", 9);
		}

		public boolean isStopWord(String key) {
			if (key.length() > this.max_char_length)
				return true;
			if (key.length() < 3)
				return true;
			Integer value = map.get(key);
			if (value == null)
				return Native.keywordCN(key) == 0;
			return value == 0;
		}
		
		public static Chinese instance = new Chinese();
	}

	public static class English extends StopWordManager {
		English() {
			super("en", 25);
		}

		public boolean isStopWord(String key) {
			if (key.length() > this.max_char_length)
				return true;
			if (!key.contains(" "))
				return true;
			Integer value = map.get(key);
			if (value == null)
				return Native.keywordEN(key) == 0;
			return value == 0;
		}
		
		public static English instance = new English();
	}

	StopWordManager(String lang, int char_length) {
		this.max_char_length = char_length;
		map = new HashMap<String, Integer>();
		for (Map<String, Object> dict : MySQL.instance.select(String
				.format("select text, label from tbl_keyword_%s where char_length(text) <= %d", lang, char_length))) {
			map.put((String) dict.get("text"), (Integer) dict.get("label"));
		}
	}

	public boolean isCommonWord(String key) {
		Integer value = map.get(key);
		if (value == null)
			return false;
		return value == 0;
	}

	public boolean containsKey(String key) {
		if (key.length() > this.max_char_length)
			return false;
		return map.containsKey(key);
	}

	public Integer put(String key, int value) {
		if (key.length() <= this.max_char_length)
			return map.put(key, value);
		return null;
	}

	public int get(String key) {
		Integer value = map.get(key);
		if (value == null)
			return -1;
		return value;
	}

	public Integer remove(String key) {
		return map.remove(key);
	}

//	DoubleArrayTrie trie;
	HashMap<String, Integer> map;
	int max_char_length;

	public static void main(String[] args) {
//		DoubleArrayTrie trie = English.instance.trie;
//
//		TreeSet<String> set = English.instance.set;
//
//		String[] keys = set.toArray(new String[set.size()]);
//		HashSet<String> hashset = new HashSet<String>(set);
//
//		System.out.println("starting timing");
//		Utility.Timer timer = new Utility.Timer();
//		for (String key : keys) {
//			trie.contains(key);
//		}
//		timer.report("trie.contains(key)");
//		for (String key : keys) {
//			set.contains(key);
//		}
//		timer.report("set.contains(key)");
//		for (String key : keys) {
//			hashset.contains(key);
//		}
//		timer.report("hashset.contains(key)");
	}

}
