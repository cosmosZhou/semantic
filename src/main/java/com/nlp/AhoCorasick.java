package com.nlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import com.util.MySQL;
import com.util.Utility;

public class AhoCorasick {
	static public AhoCorasick instance;

	static {
		synchronized (AhoCorasick.class) {
			System.out.println("initializing AhoCorasick");
			instance = new AhoCorasick();
		}
	}

	static public String servicedic(String service) {
		switch (service) {
		case "localsearch":
			return "map";

		case "music":
		case "joke":
			return "audio";

		case "sms":
		case "contact":
			return "call";

		case "broadcast":
			return "news";

		case "reminder":
		case "appmgr":
			return "cmd";

		case "tv":
			return "video";

		case "flight":
			return "train";

		case "cookbook":
			return "stock";

		case "qa":
		case "website":
			return "websearch";

		case "error":
			return "chat";

		}
		return service;
	}

	Map<String, AhoCorasickDoubleArrayTrie<String>> acdat = new HashMap<String, AhoCorasickDoubleArrayTrie<String>>();
//	static String acdatFile = Utility.modelsDirectory() + "acdat.gz";
	Map<String, Map<String, ArrayList<String>>> lexicon = new HashMap<String, Map<String, ArrayList<String>>>();

	AhoCorasickDoubleArrayTrie<String> get_repertoire(String service) throws Exception {
		service = servicedic(service);
		if (!acdat.containsKey(service)) {
			synchronized (acdat) {
				System.out.println("initializing acdat");
				AhoCorasickDoubleArrayTrie<String> ahoCorasick = new AhoCorasickDoubleArrayTrie<String>(MySQL.instance.select_repertoire(service));
				acdat.put(service, ahoCorasick);
			}
		}

		return acdat.get(service);
	}

	public HashMap<String, Object> ahocorasick(String text, String service) throws Exception {
		List<AhoCorasickDoubleArrayTrie.Hit<String>> wordList = get_repertoire(service).parseText(text);

		int size = wordList.size();
		HashMap<String, Object> map = new HashMap<String, Object>();
		AhoCorasickDoubleArrayTrie.Hit<String> prev;
		switch (size) {
		case 0:
			break;
		case 1:
			prev = wordList.get(0);
			map.put(prev.value, text.substring(prev.begin, prev.end));
			break;
		default:
			prev = wordList.get(0);
			for (int i = 1; i < wordList.size();) {
				AhoCorasickDoubleArrayTrie.Hit<String> curr = wordList.get(i);

				if (curr.begin >= prev.end) {
					++i;
					prev = curr;
				} else if (curr.end - curr.begin <= prev.end - prev.begin) {
					wordList.remove(i);
				} else {
					List<AhoCorasickDoubleArrayTrie.Hit<String>> deleted = new ArrayList<AhoCorasickDoubleArrayTrie.Hit<String>>();
					do {
						--i;
						deleted.add(prev);
						wordList.remove(i);

						if (i == 0) {
							prev = curr;
							++i;
							break;
						}

						prev = wordList.get(i - 1);
						if (curr.begin >= prev.end) {
							prev = curr;
							++i;
							break;
						}

						if (curr.end - curr.begin <= prev.end - prev.begin) {
							wordList.remove(i);
							wordList.addAll(i, deleted);
							prev = wordList.get(i + deleted.size() - 1);
							i += deleted.size();
							break;
						}
					} while (true);
				}
			}

			for (AhoCorasickDoubleArrayTrie.Hit<String> hit : wordList) {
				Utility.put(map, hit.value, text.substring(hit.begin, hit.end));
			}
		}
		return map;
	}

//	debug version of the algorithm
	public HashMap<String, Object> _ahocorasick(String text, String service) throws Exception {
		System.out.println("text = " + text);
		System.out.println("service = " + service);
		List<AhoCorasickDoubleArrayTrie.Hit<String>> wordList = get_repertoire(service).parseText(text);

		for (AhoCorasickDoubleArrayTrie.Hit<String> hit : wordList) {
			System.out.printf("%s = %s\n", text.substring(hit.begin, hit.end), hit.value);
		}

		int size = wordList.size();
		HashMap<String, Object> map = new HashMap<String, Object>();
		AhoCorasickDoubleArrayTrie.Hit<String> prev;
		switch (size) {
		case 0:
			break;
		case 1:
			prev = wordList.get(0);
			map.put(prev.value, text.substring(prev.begin, prev.end));
			break;
		default:
			prev = wordList.get(0);
			for (int i = 1; i < wordList.size();) {
				AhoCorasickDoubleArrayTrie.Hit<String> curr = wordList.get(i);
				System.out.printf("prev = %s\n", text.substring(prev.begin, prev.end));
				System.out.printf("curr = %s\n", text.substring(curr.begin, curr.end));

				if (curr.begin >= prev.end) {
					++i;
					prev = curr;
				} else if (curr.end - curr.begin <= prev.end - prev.begin) {
					System.out.println("removing curr : " + text.substring(curr.begin, curr.end));
					wordList.remove(i);
				} else {
					List<AhoCorasickDoubleArrayTrie.Hit<String>> deleted = new ArrayList<AhoCorasickDoubleArrayTrie.Hit<String>>();
					do {
						System.out.println("removing prev : " + text.substring(prev.begin, prev.end));
						--i;
						deleted.add(prev);
						assert prev == wordList.get(i);
						wordList.remove(i);

						if (i == 0) {
							prev = curr;
							++i;
							break;
						}

						prev = wordList.get(i - 1);
						if (curr.begin >= prev.end) {
							prev = curr;
							++i;
							break;
						}

						if (curr.end - curr.begin <= prev.end - prev.begin) {
							System.out.println("removing curr : " + text.substring(curr.begin, curr.end));
							wordList.remove(i);
							wordList.addAll(i, deleted);
							prev = wordList.get(i + deleted.size() - 1);
							i += deleted.size();
							break;
						}
					} while (true);

				}
			}

			for (AhoCorasickDoubleArrayTrie.Hit<String> hit : wordList) {
				Utility.put(map, hit.value, text.substring(hit.begin, hit.end));
			}

		}
		System.out.println("map = " + map);
		return map;

	}

	public String random_select(String service, String slot, String exclude) throws Exception {

//		System.out.println("service = " + service);

//		System.out.println("exclude = " + exclude);

		if (!lexicon.containsKey(service)) {
			synchronized (lexicon) {
				Map<String, ArrayList<String>> dict = new HashMap<String, ArrayList<String>>();
				for (Map.Entry<String, String> p : MySQL.instance.select_repertoire(service).entrySet()) {
					String _slot = p.getValue();
					String entity = p.getKey();
					if (!dict.containsKey(_slot)) {
						dict.put(_slot, new ArrayList<String>());
					}
					dict.get(_slot).add(entity);
				}

				lexicon.put(service, dict);
			}
		}

//		System.out.println("slot = " + slot);

		switch (slot) {
		case "fromAddress":
			slot = "address";
			break;
		case "fromCity":
			slot = "city";
			break;
		case "code":
			return exclude;
		}

		Map<String, ArrayList<String>> dict = lexicon.get(service);

		if (!dict.containsKey(slot))
			return exclude;

		ArrayList<String> array = dict.get(slot);
		if (array.size() == 1) {
			return array.get(0);
		}

		Random random = new Random();
		while (true) {
			String entity = array.get(random.nextInt(array.size()));
			System.out.println("entity = " + entity);
			if (!entity.equals(exclude))
				return entity;
		}
	}
}
