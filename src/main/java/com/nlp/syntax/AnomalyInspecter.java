package com.nlp.syntax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nlp.Sentence;
import com.nlp.syntax.AnomalyInspecter.Lexeme;
import com.nlp.syntax.AnomalySyntacticTree.Action;
import com.nlp.syntax.Compiler.HNode;
import com.nlp.syntax.Compiler.HNodeMultiplication;
import com.util.Utility;

@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
public class AnomalyInspecter {
	public static String pathErr = Utility.workingDirectory + "corpus/err.txt";

	static HashSet<String> properNounCorrect = new HashSet<String>();
	static HashSet<String> normalNounCorrect = new HashSet<String>();

	static HashMap<String, HashSet<String>> lexicon = new HashMap<String, HashSet<String>>();

	static {
//		for (String tag : POSTagger.instance.tagSet) {
//			lexicon.put(tag, new HashSet<String>());
//		}
	}

	public static ArrayList<SyntacticTree> transform_corpus(String x, String y) throws Exception {
		return transform_corpus(x.trim().split("\\s+"), y.trim().split("\\s+"));
	}

	public static ArrayList<SyntacticTree> transform_corpus(String x[], String y[]) throws Exception {
		ArrayList<SyntacticTree> acceptionList = new ArrayList<SyntacticTree>();
		for (SyntacticTree tree : new DependencyTreeReader()) {
			String[] str = tree.getLEX();
			int index = Utility.containsSubstr(str, x);
			if (index >= 0) {
				// System.out.println(tree);
				int d = -x.length + y.length;
				String[] cws = new String[str.length + d];

				int i = 0;
				for (i = 0; i < index; ++i) {
					cws[i] = str[i];
				}
				int mark[] = new int[y.length];
				for (; i - index < y.length; ++i) {
					cws[i] = y[i - index];
					mark[i - index] = i;
				}

				for (; i - d < str.length; ++i) {
					cws[i] = str[i - d];
				}

				tree = SyntacticParser.instance.parse(cws, POSTagger.instance.tag(cws)).adjust();
				System.out.println(tree.toString(mark));

				acceptionList.add(tree);
			}
		}
		return acceptionList;
	}

	public static int dep() throws Exception {
		FilterSet.initialize_err();
		ArrayList<SyntacticTree> acceptionList = new ArrayList<SyntacticTree>();
		TreeSet<String> exceptionList = new TreeSet<String>();

		int modified = 0;

		// for (SyntacticTree tree : new DependencyTreeReader()) {
		// if (tree.getDEP().length < 3) {
		// continue;
		// }
		//
		// Filter sift = FilterSet.containsIrregulation(tree);
		// if (sift == null) {
		// acceptionList.add(tree);
		// } else {
		// ++modified;
		// String sent = tree.unadornedExpression();
		// System.out.println(tree.toString(sift.anomalySet(tree)));
		// System.out.println("Anomaly Inspected: " + sift.regulation);
		//
		// SyntacticTree _tree =
		// SyntacticParser.instance.parseWithAdjustment(tree.getLEX(),
		// tree.getPOS());
		// if (_tree != null && FilterSet.containsIrregulation(_tree) == null) {
		// acceptionList.add(_tree);
		// } else {
		// acceptionList.add(tree);
		// exceptionList.add(sent);
		// }
		// }
		// }

		for (String str : new Utility.Text(DependencyTreeReader._depCorpus)) {
			SyntacticTree tree = new Sentence(str).tree();
			if (tree.getDEP().length < 3) {
				continue;
			}

			Filter filter = FilterSet.containsIrregulation(tree);
			if (filter == null) {
				acceptionList.add(tree);
				// System.out.println(tree);

			} else {
				SyntacticTree old = tree;
				tree = SyntacticParser.instance.parseWithAdjustment(tree.getLEX(), tree.getPOS());
				if (tree != null && FilterSet.containsIrregulation(tree) == null) {
					acceptionList.add(tree);

				} else {
					acceptionList.add(old);
					// exceptionList.add(str);
				}
			}
		}

		// System.out.println(modified + " NEW CASES modified!");

		// DependencyTreeReader.writeTrainingInstance(acceptionList);
		DependencyTreeReader.addTrainingInstance(acceptionList);

		// System.out.println(exceptionList.size() + " EXCEPTIONS CAUGHT!");

		// Utility.writeString(DependencyTreeReader._depCorpus, exceptionList);

		System.out.println("Anomaly Inspection finished");
		return modified;
	}

	public static Map<Integer, String> dep(String regex) throws Exception {
		Filter sift = construct(regex);
		HashMap<Integer, String> correctionMap = new HashMap<Integer, String>();
		int i = 0;
		for (SyntacticTree tree : new DependencyTreeReader()) {
			if (sift.satisfy(tree) >= 0) {
				System.out.println(tree.toString(sift.anomalySet(tree)));
				correctionMap.put(i, tree.infixExpression(true, true));
			}
			++i;
		}
		return correctionMap;
	}

	public static Map depTransform(String regex) throws Exception {
		Filter sift = construct(regex);
		HashMap<Integer, String> correctionMap = new HashMap<Integer, String>();

		int i = -1;
		for (SyntacticTree tree : new DependencyTreeReader()) {
			++i;

			int[] index = sift.anomalySet(tree);
			if (index.length == 0)
				continue;

			if (tree.transform(sift, index)) {
				tree = SyntacticParser.instance.parse(tree.getLEX(), tree.getPOS());
			}

//			Utility.print(tree.toString(index));
			correctionMap.put(i, tree.infixExpression(true, true));
		}

		return correctionMap;
	}

	// [(建议/VT)(您|你) VT|VI] => [建议/VT((您|你) VT|VI)]
	public static Map depTransform(String infix, String substituent) throws Exception {
		infix = infix.trim();
		Filter sift = construct(infix);

		substituent = substituent.replaceAll("\\s+", "");

		if (sift instanceof FilterConstituent) {
			FilterConstituent siftConstituent = (FilterConstituent) sift;
			return siftConstituent.correctionList(substituent);
		}

		if (sift instanceof FilterConstituents) {
			// [((PN) 的) (NN)(NT|AD|MD)] => [ (((PN) 的) NN)(NT|AD|MD)]
			HNode infixNode = FilterConstituent.toHNode(substituent);
			AnomalySyntacticTree suffixTree = new Compiler.HNodeSuffix("", infixNode).toAnomalySyntacticTree(null);
			suffixTree.validateSize();
			suffixTree.validateIndex();

			FilterConstituents siftConstituent = (FilterConstituents) sift;
			Map correctionList = new FilterConstituent(siftConstituent.suffixTree).correctionList(suffixTree);

			AnomalySyntacticTree prefixTree = new Compiler.HNodePrefix("", infixNode).toAnomalySyntacticTree(null);
			prefixTree.validateSize();
			prefixTree.validateIndex();

			correctionList.putAll(new FilterConstituent(siftConstituent.prefixTree).correctionList(prefixTree));
			return correctionList;
		}

		Map<Integer, String> correctionList = new HashMap<Integer, String>();// = Utility.dict();
		infix = infix.replaceAll("\\s+", "");

		// assert Utility.equals(infix.split("[\\(\\)\\+\\*]+"),
		// substituent.split("[\\(\\)\\+\\*]+"));

		String regex = "[^\\(\\)\\+\\*]+|[\\(\\)][\\*\\+]";
		Matcher mInfix = Pattern.compile(regex).matcher(infix);
		ArrayList<String> infixList = new ArrayList<String>();
		while (mInfix.find())
			infixList.add(mInfix.group(0));

		Matcher m = Pattern.compile(regex).matcher(substituent);

		ArrayList<String> substituentList = new ArrayList<String>();
		while (m.find())
			substituentList.add(m.group(0));

		for (int j = 0; j < infixList.size(); ++j) {
			substituentList.set(substituentList.indexOf(infixList.get(j)), "\\$" + (j + 1));
		}

		int i = 0;
		StringBuffer sb = new StringBuffer();
		m.reset();
		while (m.find())
			m.appendReplacement(sb, substituentList.get(i++));
		m.appendTail(sb);

		substituent = sb.toString();

		FilterExpression siftExpression = (FilterExpression) sift;
		siftExpression.captureParenthesis();

		i = -1;
		for (SyntacticTree tree : new DependencyTreeReader()) {
			++i;
			int[] index = sift.anomalySet(tree);
			if (index.length == 0)
				continue;
//			Utility.print(tree.toString(index));

			tree = siftExpression.transform(tree, substituent);

			correctionList.put(i, tree.infixExpression(true, true));
		}

		return correctionList;
	}

	public static class FilterSet {
		public static Filter[] syntacticIrregulation;
		static {
			try {
				FilterSet.initialize_err();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public static Filter containsIrregulation(SyntacticTree tree) throws Exception {
			for (int i = 0; i < syntacticIrregulation.length; ++i) {
				if (syntacticIrregulation[i].satisfy(tree) >= 0) {
					return syntacticIrregulation[i];
				}
			}
			return null;
		}

		static public void add(String sift) throws Exception {
//			Utility.appendString(pathErr, sift);
			TreeSet<String> st = FilterSet.initialize_err();
			Utility.writeString(pathErr, st);
		}

		public static TreeSet<String> initialize_err() throws Exception {
			ArrayList<Filter> irregulation = new ArrayList<Filter>();
			TreeSet<String> set = new TreeSet<String>();

			for (String str : new Utility.Text(pathErr)) {
				Filter filter = construct(str);

				str = filter.regulation;
				if (!set.contains(str)) {
					irregulation.add(filter);
					set.add(str);
				} else {
					System.out.println("duplicate rules found! " + str);
				}
			}

			syntacticIrregulation = irregulation.toArray(new Filter[irregulation.size()]);
			return set;
		}

	}

	static public String[] findPossibleTags(String seg) throws Exception {
		return null;
////		seg = Utility.simplifyString(seg);
//		Map<String, Set<String>> dict = null;
//		Map<String, Set<String>> tagTokens = null;
//
////		dict = Generics.newHashMap();
////		tagTokens = Generics.newHashMap();
//
//		TextTaggedFileReader textTaggedFileReader = new TextTaggedFileReader(DependencyTreeReader.depCorpus);
//		for (List<TaggedWord> sent : textTaggedFileReader) {
//			for (TaggedWord taggedWord : sent) {
//				String word = taggedWord.word();
//				String tag = taggedWord.tag();
//
//				if (!dict.containsKey(word)) {
//					dict.put(word, Generics.newHashSet());
//				}
//				dict.get(word).add(tag);
//
//				if (!tagTokens.containsKey(tag)) {
//					tagTokens.put(tag, Generics.newHashSet());
//				}
//				tagTokens.get(tag).add(word);
//			}
//		}
//
//		if (POSTagger.instance.tagSet.contains(seg)) {
//			return tagTokens.get(seg).toArray(new String[0]);
//		}
//
//		return dict.get(seg).toArray(new String[0]);
	}

	static public ArrayList pos(String infix) throws Exception {
		Filter sift = new FilterPOS(infix);
		ArrayList<SyntacticTree> arr = new ArrayList<SyntacticTree>();
		for (SyntacticTree tree : new DependencyTreeReader()) {
			if (sift.satisfy(tree) >= 0) {
				System.out.println(tree.toString(sift.anomalySet(tree)));
				arr.add(tree);
			}
		}
		return arr;
	}

	static void scan(ArrayList<String> arr, ArrayList<String> arrAnomaly)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
	}

	static public int match(String[][] inst, String word, String tag) {
		for (int i = 0; i < inst[0].length; ++i) {
			if (inst[0][i].equals(word) && inst[1][i].equals(tag)) {
				return i;
			}
		}
		return -1;
	}

	static public int dismatch(String[][] inst, String word, String tag) {
		for (int i = 0; i < inst[0].length; ++i) {
			if (inst[0][i].equals(word) && !inst[1][i].equals(tag)) {
				return i;
			}
		}
		return -1;
	}

	static public boolean equals(String[] inst, int _i, String word[]) {
		for (int i = 0; i < word.length; ++i) {
			if (i + _i >= inst.length || !inst[i + _i].matches(word[i]))
				return false;
		}
		return true;
	}

	static public boolean equals(String[][] inst, int _i, String word[]) {
		for (int i = 0; i < word.length; ++i) {
			if (i + _i >= inst[0].length)
				return false;
			String[] res = Utility.regexSingleton(word[i], "(\\S+)/(\\S+)");

			if (res != null) {
				if (!inst[0][i + _i].matches(res[1]) || !inst[1][i + _i].equals(res[2]))
					return false;

			} else {
				if (!inst[0][i + _i].matches(word[i]))
					return false;
			}
		}
		return true;
	}

	static public void set(String[] inst, int _i, String tag[]) {
		for (int i = 0; i < tag.length; ++i) {
			inst[i + _i] = tag[i];
		}
	}

	static public int dismatch(String[][] inst, String word[], String tag[]) {
		for (int i = 0; i < inst[0].length; ++i) {
			if (equals(inst[0], i, word) && !equals(inst[1], i, tag)) {
				return i;
			}
		}
		return -1;
	}

	static public void dismatchAndSet(String[][] inst, String word[][], String tag[][]) {
		int length = inst[0].length;
		for (int i = 0; i < length; ++i) {
			for (int j = 0; j < word.length; ++j) {
				if (equals(inst, i, word[j])) {
					int lengthWord = word[j].length;
					if (tag[j].length == 1 && lengthWord > 1) {
						for (int t = 1; t < lengthWord; ++t) {
							inst[0][i] += inst[0][i + t];
							inst[0][i + t] = "";
							inst[1][i + t] = "";
						}
						inst[1][i] = tag[j][0];
						continue;
					}

					if (tag[j].length != word[j].length) {
						System.out
								.println(Utility.toString(word, " ", null) + " = " + Utility.toString(tag, " ", null));
						throw new RuntimeException("tag[j].length != word[j].length");
					}
					if (!equals(inst[1], i, tag[j])) {
						set(inst[1], i, tag[j]);
					}
				}
			}
		}
	}

	static public String dightsChinese = "〇一二三四五六七八九十";

	static public void matchRegex(String[][] inst) {
		for (int i = 0; i < inst[0].length; ++i) {
			if (inst[0][i].matches("[" + dightsChinese + "]+") && !inst[1][i].equals("CD")) {
				System.out.println("before setting WORD = " + inst[0][i]);
				System.out.println("before setting tag  = " + inst[1][i]);
				inst[1][i] = "CD";
			}

			if (i - 1 >= 0 && i - 1 < inst[0].length && inst[0][i].equals("点") && !inst[1][i].equals("O")
					&& inst[0][i - 1].matches(".*[" + dightsChinese + "]$")
					&& inst[0][i + 1].matches("^[" + dightsChinese + "].*")) {
				System.out
						.println("before setting WORD = " + inst[0][i - 1] + "\t" + inst[0][i] + "\t" + inst[0][i + 1]);
				System.out
						.println("before setting tag  = " + inst[1][i - 1] + "\t" + inst[1][i] + "\t" + inst[1][i + 1]);
				inst[1][i] = "O";
				inst[1][i + 1] = "CD";
				inst[1][i - 1] = "CD";
			}
		}
	}

	static public int match(String[][] inst, HashMap<String, HashSet<String>> map) {
		for (Map.Entry<String, HashSet<String>> entry : map.entrySet()) {
			for (String word : entry.getValue()) {
				int index = match(inst, word, entry.getKey());
				if (index >= 0) {
					return index;
				}
			}
		}

		return -1;
	}

	static public int matchLegal(String[][] inst, HashMap<String, HashSet<String>> map) {
		for (int i = 0; i < inst[0].length; ++i) {
			String word = inst[0][i];
			String tag = inst[1][i];

			if (tag.equals("CD") || tag.equals("NT"))
				continue;
//			if (!map.get(tag).contains(new UTF8EquivalenceFunction().apply(word)) && !map.get(tag).contains(word))
//				return i;
		}

		return -1;
	}

	static public int matchIllegal(String[][] inst, HashMap<String, HashSet<String>> map) throws IOException {
		for (int i = 0; i < inst[0].length; ++i) {
			String word = inst[0][i];
			String tag = inst[1][i];
			HashSet<String> set = map.get(tag);
			if (set != null && set.contains(word)) {
				System.out.println("the word to classift is " + word);
				System.out.println("type tag to classify it, type enter to quit, the context is : ");
				System.out.println(Utility.convertToOriginal(inst[0]));
				for (String s : Utility.convertWithAlignment(inst[0], Utility.errorMark(inst[0].length, i), inst[1])) {
					System.out.println(s);
				}

				input = buffer.readLine();
				input = input.toUpperCase();
				if (input.length() > 0) {
					String pos[] = input.split("\\s+");
					if (pos.length == 1) {
						inst[1][i] = pos[0];
					} else if (pos.length == inst[1].length) {
						System.arraycopy(pos, 0, inst[1], 0, pos.length);
					}
					continue;
				}

				return i;
			}
		}

		return -1;
	}

//	static void correct(String[][] inst, int i) throws IOException {
//		String word = inst[0][i];
//		String tag = inst[1][i];
//
//		System.out.println("the word to classift is " + word);
//		System.out.println("the unknown tag is " + tag);
//		for (String s : Utility.convertWithAlignment(inst[0], Utility.errorMark(inst[0].length, i), inst[1])) {
//			System.out.println(s);
//		}
//
//		System.out.println("type correct tag");
//		input = buffer.readLine();
//		input = input.toUpperCase();
//		if (POSTagger.instance.tagSet.contains(input)) {
//			inst[1][i] = input;
//			lexicon.get(input).add(word);
//		}
//	}

//	static public int scanLexicon(String[][] inst) throws IOException {
//		for (int i = 0; i < inst[0].length; ++i) {
//			String word = inst[0][i];
//			String tag = inst[1][i].toUpperCase();
//			if (!POSTagger.instance.tagSet.contains(tag)) {
//				correct(inst, i);
//				continue;
//			}
//			switch (tag) {
//			case "NR":
//				if (properNounCorrect.contains(word)) {
//					continue;
//				}
//				if (normalNounCorrect.contains(word)) {
//					inst[1][i] = "NN";
//					continue;
//				}
//				if (lexicon.get("NN").contains(word)) {
//					System.out.println("the word to classift is " + word);
//					System.out.println("the context is ");
//					System.out.println(Utility.convertToOriginal(inst[0]));
//					for (String s : Utility.convertWithAlignment(inst[0], Utility.errorMark(inst[0].length, i),
//							inst[1])) {
//						System.out.println(s);
//					}
//
//					System.out
//							.println("type NR to classify it as NR, type NN to classify it as NN, type enter to quit.");
//					input = buffer.readLine();
//					input = input.toUpperCase();
//					switch (input) {
//					case "NR":
//						lexicon.get("NR").remove(word);
//						lexicon.get("NN").remove(word);
//						properNounCorrect.add(word);
//						inst[1][i] = "NR";
//						continue;
//					case "NN":
//						lexicon.get("NR").remove(word);
//						lexicon.get("NN").remove(word);
//						normalNounCorrect.add(word);
//						inst[1][i] = "NN";
//						continue;
//					default:
//						if (input.length() > 0) {
//							inst[1][i] = input;
//							continue;
//						}
//
//						return i;
//					}
//
//				} else
//					lexicon.get("NR").add(word);
//				break;
//			case "NN":
//				if (properNounCorrect.contains(word)) {
//					inst[1][i] = "NR";
//					continue;
//				}
//				if (normalNounCorrect.contains(word)) {
//					continue;
//				}
//
//				if (lexicon.get("NR").contains(word)) {
//					System.out.println("the word to classify is " + word);
//					System.out.println("the context is ");
//					System.out.println(Utility.convertToOriginal(inst[0]));
//					for (String s : Utility.convertWithAlignment(inst[0], Utility.errorMark(inst[0].length, i),
//							inst[1])) {
//						System.out.println(s);
//					}
//					System.out
//							.println("type NR to classify it as NR, type NN to classify it as NN, type enter to quit.");
//					input = buffer.readLine();
//					input = input.toUpperCase();
//					switch (input) {
//					case "NR":
//						lexicon.get("NR").remove(word);
//						lexicon.get("NN").remove(word);
//						properNounCorrect.add(word);
//						inst[1][i] = "NR";
//						continue;
//					case "NN":
//						lexicon.get("NR").remove(word);
//						lexicon.get("NN").remove(word);
//						normalNounCorrect.add(word);
//						inst[1][i] = "NN";
//						continue;
//					default:
//						if (input.length() > 0) {
//							inst[1][i] = input;
//							continue;
//						}
//
//						return i;
//					}
//				} else
//					lexicon.get("NN").add(word);
//				break;
//			case "VT":
//				// if (lexicon.get("VT").contains(word)) {
//				// continue;
//				// }
//				// if (lexicon.get("NR").contains(word)) {
//				// correct(inst, i);
//				// continue;
//				// } else
//				// lexicon.get("VT").add(word);
//			case "VI":
//			default:
//			}
//		}
//
//		return -1;
//	}

	static public void printIntersect(String p1, String p2) {
		HashSet<String> intersect;
		System.out.println("intersection of " + p1 + ", " + p2);
		intersect = Utility.intersect(lexicon.get(p1), lexicon.get(p2));
		System.out.println(intersect);
	}

	static public void scanLexiconToSetup() throws UnsupportedEncodingException, FileNotFoundException, IOException {
		for (SyntacticTree inst : new DependencyTreeReader()) {
			// scanLexiconToSetup(inst);
		}

		printIntersect("NR", "VT");
		printIntersect("NR", "VI");
		printIntersect("NR", "VC");
		printIntersect("NR", "VA");
		printIntersect("NR", "JJ");
		printIntersect("NR", "AD");
		printIntersect("NR", "NN");
		printIntersect("NR", "VBG");
		printIntersect("NR", "IJ");
		printIntersect("NR", "NT");
		printIntersect("NR", "M");
		printIntersect("NR", "DT");
		printIntersect("NR", "CD");
		printIntersect("NR", "CS");
		printIntersect("NR", "LC");
		printIntersect("NR", "O");
		printIntersect("VBG", "NN");
		printIntersect("AD", "AS");
		printIntersect("AD", "MD");
		printIntersect("AD", "VI");
		printIntersect("AD", "VT");
		printIntersect("AS", "LC");
	}

	public static Filter construct(String exp) throws Exception {

		if (exp.startsWith("[")) {
			assert exp.endsWith("]");
			try {
				return new FilterConstituent(exp);
			} catch (Exception e) {
				if (e.getMessage().equals("HNodeMultiplication")) {
					return new FilterConstituents(exp);
				}
				throw e;
			}
		}
		return new FilterExpression(exp);

	}

	static Lexeme[][] parseComponent(String x[]) {
		Lexeme[][] res = new Lexeme[x.length][];
		for (int i = 0; i < res.length; i++) {
			res[i] = parseComponent(x[i]);
		}
		return res;
	}

	public static Lexeme[] parseComponent(String x) {
		Lexeme[] res = new Lexeme[3];
		if (x.startsWith("(")) {
			if (x.endsWith(")")) {
				x = x.substring(1, x.length() - 1);
			} else
				throw new RuntimeException();
		}

		for (String s : x.split("/")) {
			s = s.trim();
			String[] arr = s.split("->");
			if (arr.length == 1) {

				boolean negate = false;
				if (s.startsWith("^")) {
					s = s.substring(1);
					negate = true;
				}

				arr = s.split("\\|");
				if (arr.length == 1) {
					if (POSTagger.instance.tagSet.contains(s)) {
						res[1] = negate ? new LexemeSingleNegate(s) : new LexemeSingle(s);
					} else if (SyntacticParser.instance.tagSet.contains(s)) {
						res[2] = negate ? new LexemeSingleNegate(s) : new LexemeSingle(s);
					} else {
//						s = Utility.simplifyString(s);
						if (s.isEmpty())
							continue;
						res[0] = negate ? new LexemeSingleNegate(s) : new LexemeSingle(s);
					}
				} else {
					if (POSTagger.instance.tagSet.contains(arr[0])) {
						res[1] = negate ? new LexemeSequenceNegate(arr) : new LexemeSequence(arr);
					} else if (SyntacticParser.instance.tagSet.contains(arr[0])) {
						res[2] = negate ? new LexemeSequenceNegate(arr) : new LexemeSequence(arr);
					} else {
//						arr = Utility.simplifyString(arr);
						res[0] = negate ? new LexemeSequenceNegate(arr) : new LexemeSequence(arr);
					}
				}
			} else {
				s = arr[0];
				String _s = arr[1];

				boolean negate = false;
				if (s.startsWith("^")) {
					s = s.substring(1);
					negate = true;
				}

				arr = s.split("\\|");
				if (arr.length == 1) {
					if (POSTagger.instance.tagSet.contains(s)) {
						res[1] = negate ? new LexemeSingleNegateTransformer(s, _s) : new LexemeSingleTransformer(s, _s);
					} else if (SyntacticParser.instance.tagSet.contains(s)) {
						res[2] = negate ? new LexemeSingleNegateTransformer(s, _s) : new LexemeSingleTransformer(s, _s);
					} else {
//						s = Utility.simplifyString(s);
						res[0] = negate ? new LexemeSingleNegateTransformer(s, _s) : new LexemeSingleTransformer(s, _s);
					}
				} else {
					if (POSTagger.instance.tagSet.contains(arr[0])) {
						res[1] = negate ? new LexemeSequenceNegateTransformer(arr, _s)
								: new LexemeSequenceTransformer(arr, _s);
					} else if (SyntacticParser.instance.tagSet.contains(arr[0])) {
						res[2] = negate ? new LexemeSequenceNegateTransformer(arr, _s)
								: new LexemeSequenceTransformer(arr, _s);
					} else {
//						arr = Utility.simplifyString(arr);
						res[0] = negate ? new LexemeSequenceNegateTransformer(arr, _s)
								: new LexemeSequenceTransformer(arr, _s);
					}
				}
			}
		}
		return res;
	}

	static String configureComponent(Lexeme[] lex) {
		return configureComponent(lex, true);
	}

	static String configureComponent(Lexeme[] lex, boolean bParenthesis) {
		String s = "";
		for (Lexeme word : lex) {
			if (word == null)
				continue;
			if (!s.isEmpty())
				s += "/";
			s += word;
		}

		if (bParenthesis && (s.contains("/") || s.contains("|") || s.contains("^")))
			return "(" + s + ")";
		return s;
	}

	static String configureComponent(Lexeme[][] lex) {
		String[] str = new String[lex.length];
		for (int i = 0; i < str.length; i++) {
			str[i] = configureComponent(lex[i], false);
		}
		return String.join(", ", str);
	}

	public static abstract class Filter {
		public abstract int satisfy(SyntacticTree tree) throws Exception;

		public SyntacticTree shift(SyntacticTree tree) {
			return null;
		}

		public SyntacticTree left(SyntacticTree tree) {
			return tree;
		}

		public SyntacticTree right(SyntacticTree tree) {
			return tree;
		}

		public int[] anomalySet(SyntacticTree tree) throws Exception {
			ArrayList<Integer> arr = new ArrayList<Integer>();
			anomalySet(tree, arr);
			return Utility.toArray(arr);
		}

		public boolean anomalySet(SyntacticTree tree, ArrayList<Integer> arr) throws Exception {

			boolean drapeau = true;
			for (SyntacticTree t : tree.leftChildren) {
				if (anomalySet(t, arr)) {
					drapeau = false;
				}
			}

			for (SyntacticTree t : tree.rightChildren) {
				if (anomalySet(t, arr)) {
					drapeau = false;
				}
			}

			if (drapeau) {
				int pos = satisfy(tree);
				if (pos >= 0) {
					arr.add(pos);
					return true;
				}
				return false;
			}
			return true;
		}

		public String regulation;

		@Override
		public String toString() {
			return regulation;
		}

		boolean isPOSRegulation() {
			return false;
		}

		public SyntacticTree locate(SyntacticTree tree, Lexeme errorLex) {
			return null;
		}

		public boolean transform(SyntacticTree tree) throws Exception {
			return false;
		}
	}

	public static class FilterPOS extends Filter {
		private Lexeme[] segSub;
		private Lexeme[] posSub;

		public FilterPOS(String infix) {
			this.regulation = infix;
			String[] arr = infix.split("\\s+");
			this.segSub = new Lexeme[arr.length];
			this.posSub = new Lexeme[arr.length];

			for (int i = 0; i < arr.length; i++) {
				Lexeme[] res = parseComponent(arr[i]);
				posSub[i] = res[1];
				segSub[i] = res[0];
			}
		}

		public int satisfy(SyntacticTree tree) {
			return search(tree);
		}

		int search(SyntacticTree tree) {
			String[] pos = tree.getPOS();
			String[] seg = tree.getSEG();
			for (int i = 0; i <= seg.length - segSub.length; ++i) {
				if (AnomalyInspecter.equals(seg, segSub, i) && AnomalyInspecter.equals(pos, posSub, i))
					return i;
			}
			return -1;
		}

		public void clearDict() {
			for (int j = 0; j < segSub.length; ++j) {
				Lexeme seg = segSub[j];
				Lexeme pos = posSub[j];
//				if (seg != null && pos != null)
//					seg.alterLexicon(POSTagger.instance.dict.dict, pos);
			}
		}

		public SyntacticTree adjust(SyntacticTree tree) throws Exception {
			String[] lex = tree.getLEX();
			String[] seg = tree.getSEG();
			String[] pos = tree.getPOS();

			boolean bNeedAdjustment = false;
			int i = search(tree);
			assert i >= 0;

			for (int j = 0; j < segSub.length; ++j) {
				if (segSub[j] != null && posSub[j] != null) {
					pos[i + j] = "";
					bNeedAdjustment = true;
				}
			}

			if (bNeedAdjustment) {
				pos = POSTagger.instance.tag(seg, pos);
//				tree = SyntacticParser.instance.parse(lex, pos).adjust();
				tree = SyntacticParser.instance.parse(lex, pos);
			}
			return tree;
		}

		public int[] anomalySet(SyntacticTree tree) {

			int i = search(tree);
			if (i < 0)
				return null;
			ArrayList<Integer> arr = new ArrayList<Integer>();

			for (int j = 0; j < segSub.length; ++j) {
				if (segSub[j] != null && posSub[j] != null)
					arr.add(i + j);
			}
			return Utility.toArray(arr);
		}

	}

	static boolean equals(String[] dep, Lexeme[] depSub) {
		for (int i = 0; i < depSub.length; ++i) {
			if (depSub[i] != null && !depSub[i].equals(dep[i])) {
				return false;
			}
		}
		return true;
	}

	static boolean equals(String[] dep, Lexeme[] depSub, int start) {
		for (int i = 0; i < depSub.length; ++i) {
			if (depSub[i] != null && !depSub[i].equals(dep[i + start])) {
				return false;
			}
		}
		return true;
	}

	public static abstract class Lexeme {
		abstract boolean equals(String word);

		public String transformer() {
			return null;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Lexeme) {
				return equals((Lexeme) obj);
			}
			return false;
		}

		public abstract boolean equals(Lexeme word);

		static public boolean equals(Lexeme word1, Lexeme word2) {
			if (word1 == null)
				return word2 == null;
			if (word2 == null)
				return false;
			return word1.equals(word2);
		}

		public abstract String regex();

//		public void alterLexicon(Map<String, TagCount> dict, Lexeme pos) {
//		}

//		public void _alterLexicon(Map<String, TagCount> lexicon, LexemeSingle pos) {
//		}

//		public void _alterLexicon(Map<String, TagCount> lexicon, LexemeSequence pos) {
//		}

		static public String toString(Lexeme lexeme) {
			if (lexeme == null)
				return "*";
			return lexeme.toString();
		}

		@Override
		public abstract String toString();

		public boolean equals(LexemeSingle errorLexSingle) {
			return false;
		}

		public boolean equals(LexemeSingleNegate errorLexSingle) {
			return false;
		}

		public boolean equals(LexemeSequence errorLexSingle) {
			return false;
		}

		public boolean equals(LexemeSequenceNegate errorLexSingle) {
			return false;
		}
	}

	static class LexemeSingle extends Lexeme {
		String tag;

		public LexemeSingle(String tag) {
			this.tag = tag;
		}

		@Override
		boolean equals(String word) {
			return tag.equals(word);
		}

		@Override
		public String toString() {
			return tag;
		}

//		public void alterLexicon(Map<String, TagCount> lexicon, Lexeme pos) {
//			if (!lexicon.containsKey(tag)) {
//				TagCount tagCount = new TagCount();
//				tagCount.initializeTags();
//				lexicon.put(tag, tagCount);
//			}
//
//			pos._alterLexicon(lexicon, this);
//		}

		@Override
		public String regex() {
			// www.regular-expressions.info/lookaround.html
			// lookbehind
			return tag;
		}

//		@Override
//		public void _alterLexicon(Map<String, TagCount> lexicon, LexemeSingle seg) {
//			lexicon.get(seg.tag).removeTag(tag);
//		}

//		@Override
//		public void _alterLexicon(Map<String, TagCount> lexicon, LexemeSequence seg) {
//			for (String word : seg.tag) {
//				if (lexicon.containsKey(word))
//					lexicon.get(word).removeTag(tag);
//			}
//		}
//
		public boolean equals(Lexeme word) {
			return word.equals(this);
		}

		public boolean equals(LexemeSingle errorLexSingle) {
			return tag.equals(errorLexSingle.tag);
		}

	}

	// (DE/adj->de).parent = 价格
	static class LexemeSingleTransformer extends Lexeme {
		String tag;
		String tagTransformed;

		public String transformer() {
			return tagTransformed;
		}

		public LexemeSingleTransformer(String tag, String tagTransformed) {
			this.tag = tag;
			this.tagTransformed = tagTransformed;
		}

		@Override
		boolean equals(String word) {
			return tag.equals(word);
		}

		@Override
		public String toString() {
			return tag + "->" + tagTransformed;
		}

		@Override
		public String regex() {
			// www.regular-expressions.info/lookaround.html
			// lookbehind
			return tag;
		}

//		@Override
//		public void _alterLexicon(Map<String, TagCount> lexicon, LexemeSingle seg) {
//			lexicon.get(seg.tag).removeTag(tag);
//		}

		public boolean equals(Lexeme word) {
			return word.equals(this);
		}

		public boolean equals(LexemeSingleTransformer errorLexSingle) {
			return tag.equals(errorLexSingle.tag) && tagTransformed.equals(errorLexSingle.tagTransformed);
		}

	}

	static class LexemeSingleNegate extends Lexeme {
		String tag;

		public LexemeSingleNegate(String tag) {
			this.tag = tag;
		}

		@Override
		boolean equals(String word) {
			return !tag.equals(word);
		}

		@Override
		public String toString() {
			return "^" + tag;
		}

		@Override
		public String regex() {
			return "(?!" + tag + ")[^\\(\\)]+";
		}

//		@Override
//		public void _alterLexicon(Map<String, TagCount> lexicon, LexemeSingle seg) {
//			lexicon.get(seg.tag).retainTag(tag);
//		}

//		@Override
//		public void _alterLexicon(Map<String, TagCount> lexicon, LexemeSequence seg) {
//			for (String word : seg.tag) {
//				if (lexicon.containsKey(word))
//					lexicon.get(word).retainTag(tag);
//			}
//		}

		@Override
		public boolean equals(Lexeme word) {
			return word.equals(this);
		}

		public boolean equals(LexemeSingleNegate errorLexSingle) {
			return tag.equals(errorLexSingle.tag);
		}

	}

	static class LexemeSingleNegateTransformer extends Lexeme {
		String tag;
		String tagTransformed;

		public String transformer() {
			return tagTransformed;
		}

		public LexemeSingleNegateTransformer(String tag, String tagTransformed) {
			this.tag = tag;
			this.tagTransformed = tagTransformed;
		}

		@Override
		boolean equals(String word) {
			return !tag.equals(word);
		}

		@Override
		public String toString() {
			return "^" + tag + "->" + tagTransformed;
		}

		@Override
		public String regex() {
			return "(?!" + tag + ")[^\\(\\)]+";
		}

//		@Override
//		public void _alterLexicon(Map<String, TagCount> lexicon, LexemeSingle seg) {
//			lexicon.get(seg.tag).retainTag(tag);
//		}

		@Override
		public boolean equals(Lexeme word) {
			return word.equals(this);
		}

		public boolean equals(LexemeSingleNegate errorLexSingle) {
			return tag.equals(errorLexSingle.tag);
		}

	}

	static class LexemeSequence extends Lexeme {
		// return true if the given word matches any one of the pattern
		String tag[];

		LexemeSequence(String tag[]) {
			this.tag = tag;
		}

		public boolean equals(String word) {
			for (String w : this.tag) {
				if (w.equals(word))
					return true;
			}
			return false;
		}

		public String toString() {
			return String.join("|", this.tag);
		}

		@Override
		public String regex() {
			return "(?:" + String.join("|", this.tag) + ")";
		}

//		@Override
//		public void _alterLexicon(Map<String, TagCount> lexicon, LexemeSingle seg) {
//			lexicon.get(seg.tag).removeTag(tag);
//		}

//		@Override
//		public void _alterLexicon(Map<String, TagCount> lexicon, LexemeSequence seg) {
//			for (String word : seg.tag) {
//				if (lexicon.containsKey(word))
//					lexicon.get(word).removeTag(tag);
//			}
//
//		}

//		public void alterLexicon(Map<String, TagCount> lexicon, Lexeme pos) {
//			pos._alterLexicon(lexicon, this);
//		}

		@Override
		public boolean equals(Lexeme word) {
			return word.equals(this);
		}

		public boolean equals(LexemeSequence errorLexSingle) {

			return Utility.equals(tag, errorLexSingle.tag);
		}

	}

	static class LexemeSequenceTransformer extends Lexeme {
		// return true if the given word matches any one of the pattern
		String tag[];
		String tagTransformed;

		public String transformer() {
			return tagTransformed;
		}

		LexemeSequenceTransformer(String tag[], String tagTransformed) {
			this.tag = tag;
			this.tagTransformed = tagTransformed;
		}

		public boolean equals(String word) {
			for (String w : this.tag) {
				if (w.equals(word))
					return true;
			}
			return false;
		}

		public String toString() {
			return String.join("|", this.tag) + "->" + tagTransformed;
		}

		@Override
		public String regex() {
			return "(?:" + String.join("|", this.tag) + ")";
		}

//		@Override
//		public void _alterLexicon(Map<String, TagCount> lexicon, LexemeSingle seg) {
//			lexicon.get(seg.tag).removeTag(tag);
//		}
//
//		@Override
//		public void _alterLexicon(Map<String, TagCount> lexicon, LexemeSequence seg) {
//			lexicon.get(seg.tag).removeTag(tag);
//		}

		@Override
		public boolean equals(Lexeme word) {
			return word.equals(this);
		}

		public boolean equals(LexemeSequence errorLexSingle) {

			return Utility.equals(tag, errorLexSingle.tag);
		}

	}

	static class LexemeSequenceNegate extends Lexeme {
		String tag[];

		/*
		 * return true if the given word does not match any one of the pattern
		 */
		LexemeSequenceNegate(String[] tag) {
			this.tag = tag;
		}

		public boolean equals(String word) {
			for (String w : this.tag) {
				if (w.equals(word))
					return false;
			}
			return true;
		}

		public String toString() {
			return '^' + String.join("|", this.tag);
		}

		@Override
		public String regex() {
			// www.regular-expressions.info/lookaround.html
			// lookahead
			return "(?!(" + String.join("|", this.tag) + "))[^\\(\\)]+";
		}

//		@Override
//		public void _alterLexicon(Map<String, TagCount> lexicon, LexemeSingle seg) {
//			lexicon.get(seg.tag).retainTag(tag);
//		}

//		@Override
//		public void _alterLexicon(Map<String, TagCount> lexicon, LexemeSequence seg) {
//			for (String word : seg.tag) {
//				if (lexicon.containsKey(word))
//					lexicon.get(word).retainTag(tag);
//			}
//		}

		@Override
		public boolean equals(Lexeme word) {
			return word.equals(this);
		}

		public boolean equals(LexemeSequenceNegate errorLexSingle) {

			return Utility.equals(tag, errorLexSingle.tag);
		}

	}

	static class LexemeSequenceNegateTransformer extends Lexeme {
		String tag[];
		String tagTransformed;

		public String transformer() {
			return tagTransformed;
		}

		/*
		 * return true if the given word does not match any one of the pattern
		 */
		LexemeSequenceNegateTransformer(String[] tag, String tagTransformed) {
			this.tag = tag;
			this.tagTransformed = tagTransformed;
		}

		public boolean equals(String word) {
			for (String w : this.tag) {
				if (w.equals(word))
					return false;
			}
			return true;
		}

		public String toString() {
			return '^' + String.join("|", this.tag) + "->" + tagTransformed;
		}

		@Override
		public String regex() {
			// www.regular-expressions.info/lookaround.html
			// https://regular-expressions.mobi/brackets.html?wlr=1
			// lookahead
			return "(?!(?:" + String.join("|", this.tag) + "))[^\\(\\)]+";
		}

//		@Override
//		public void _alterLexicon(Map<String, TagCount> lexicon, LexemeSingle seg) {
//			lexicon.get(seg.tag).retainTag(tag);
//		}

		@Override
		public boolean equals(Lexeme word) {
			return word.equals(this);
		}

		public boolean equals(LexemeSequenceNegate errorLexSingle) {

			return Utility.equals(tag, errorLexSingle.tag);
		}

	}

	static class FilterExpression extends Filter {
		Lexeme[][] lex;
		String infix;

		static public String regexp(Lexeme[] lex) {

			StringBuffer s = new StringBuffer();
			s.append("((?<![^\\(\\)])");

			for (int i = 0; i < lex.length; ++i) {
				Lexeme word = lex[i];
				if (i > 0)
					s.append("/");

				if (word == null) {
					s.append("[^\\(\\)]+");
				} else {
					s.append(word.regex());
				}
			}
			s.append(")");
			return s.toString();
		}

		public FilterExpression(String exp) throws Exception {
			this.regulation = exp;
			regulation = regulation.replaceAll("\\s+", "");

			ArrayList<Lexeme[]> list = new ArrayList<Lexeme[]>();
			infix = "";
			int start = 0;
			Matcher m = Pattern.compile("[\\(\\)\\+\\*]+").matcher(regulation);

			while (m.find()) {
				if (m.start() > start) {
					String lexme = regulation.substring(start, m.start());
					Lexeme[] lex = parseComponent(lexme);
					infix += regexp(lex);
					list.add(lex);
				}

				infix += m.group(0).replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");

				start = m.end();
			}

			if (regulation.length() > start) {
				String lexme = regulation.substring(start);
				Lexeme[] lex = parseComponent(lexme);
				infix += regexp(lex);
				list.add(lex);
			}

			this.lex = Utility.toArray(list);
		}

		@Override
		public int satisfy(SyntacticTree tree) throws Exception {
			Matcher m = Pattern.compile(infix).matcher(tree.infixExpression(false));
			if (m.find()) {
				return tree.id;
			}
			return -1;
		}

		public boolean transform(SyntacticTree tree) throws Exception {
			String infixExpression = tree.infixExpression(false);
			Matcher m = Pattern.compile(infix).matcher(tree.infixExpression(false));
			if (!m.find())
				return false;
			int p = 0;
			StringBuffer infix = new StringBuffer();

			if (m.start() > p) {
				infix.append(infixExpression.substring(p, m.start()));
				p = m.start();
			}

			for (int i = 1; i <= m.groupCount(); ++i) {
				String capture = m.group(i);

				if (m.start(i) > p) {
					infix.append(infixExpression.substring(p, m.start(i)));
					p = m.start(i);
				}

				capture = SyntacticTree.transform(capture, lex[i - 1]);
				infix.append(capture);

				p = m.end(i);
			}

			// if (m.end() > p) {
			// infix.append(infixExpression.substring(p, m.end()));
			//
			// p = m.end();
			// }
			//
			// assert p == m.end();

			if (infixExpression.length() > p) {
				infix.append(infixExpression.substring(p));
			}

			SyntacticTree _tree = Compiler.compile(infix.toString());

			tree.setDEP(_tree.getDEP());
			String[] pos = _tree.getPOS();
			if (!Utility.equals(tree.getPOS(), pos)) {
				tree.setPOS(pos);
				return true;
			}

			return false;
		}

		public void captureParenthesis() throws Exception {
			// Matcher m = Pattern.compile("\\[\\(][\\*\\+]").matcher(infix);
			Matcher m = Pattern.compile("\\\\[\\(\\)][\\*\\+]").matcher(infix);

			int start = 0;
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				if (m.start() > start) {
					sb.append(infix.substring(start, m.start()));
				}

				sb.append("(" + m.group(0) + ")");

				start = m.end();
			}

			sb.append(infix.substring(start));

			infix = sb.toString();
		}

		public SyntacticTree transform(SyntacticTree tree, String substituent) throws Exception {
			String infixExpression = tree.infixExpression(false);
			String[] lex = tree.getLEX();
			Matcher m = Pattern.compile(infix).matcher(infixExpression);

			infixExpression = m.replaceAll(substituent);
			tree = Compiler.compile(infixExpression);
			tree.setLEX(lex);
			tree.setDEP(new String[lex.length]);
			tree = SyntacticParser.instance.parse(tree);
			return tree;
		}
	}

	static class FilterConstituent extends Filter {
		public AnomalySyntacticTree infixTree;

		public FilterConstituent(AnomalySyntacticTree infix) throws Exception {
			infixTree = infix;
		}

		public FilterConstituent(String infix) throws Exception {
			assert infix.startsWith("[");

			assert infix.endsWith("]");

			HNode infixNode = Compiler.HNode.compile(infix.substring(1, infix.length() - 1));

			if (infixNode instanceof HNodeMultiplication) {
				throw new Exception("HNodeMultiplication");
			} else {
				infixTree = infixNode.toAnomalySyntacticTree(null);
				infixTree.validateSize();
				infixTree.validateIndex();

				regulation = "[" + infixTree.infixExpression() + "]";
			}
		}

		@Override
		public int satisfy(SyntacticTree tree) {
			for (SyntacticTree t : tree) {
				if (t.satisfy(infixTree))
					return t.id;
			}
			return -1;
		}

		public boolean transform(SyntacticTree tree) {
			return tree.transform(infixTree);
		}

		static HNode toHNode(String substituent) throws Exception {
			if (substituent.startsWith("[")) {
				assert substituent.endsWith("]");
				substituent = substituent.substring(1, substituent.length() - 1);
			}

			HNode substituentNode = Compiler.HNode.compile(substituent);
			return substituentNode;
		}

		static AnomalySyntacticTree[] toAnomalySyntacticTree(String substituent) throws Exception {
			HNode infixNode = toHNode(substituent);
			if (infixNode instanceof HNodeMultiplication) {
				AnomalySyntacticTree suffixTree = new Compiler.HNodeSuffix("", infixNode).toAnomalySyntacticTree(null);
				suffixTree.validateSize();
				suffixTree.validateIndex();

				AnomalySyntacticTree prefixTree = new Compiler.HNodePrefix("", infixNode).toAnomalySyntacticTree(null);
				prefixTree.validateSize();
				prefixTree.validateIndex();
				return new AnomalySyntacticTree[] { suffixTree, prefixTree };
			}

			AnomalySyntacticTree substituentTree = infixNode.toAnomalySyntacticTree(null);
			substituentTree.validateSize();
			substituentTree.validateIndex();
			return new AnomalySyntacticTree[] { substituentTree };
		}

		Map correctionList(String substituent) throws Exception {
			AnomalySyntacticTree[] arr = toAnomalySyntacticTree(substituent);
			if (arr.length == 1) {
				return correctionList(arr[0]);
			}
			HashMap<Integer, String> list = new HashMap<Integer, String>();

			ArrayList<AnomalySyntacticTree> leftChildren = new ArrayList<AnomalySyntacticTree>();
			leftChildren.add(this.infixTree);
			AnomalySyntacticTree suffix = new AnomalySyntacticTree(-1, null, null, null, leftChildren,
					new ArrayList<AnomalySyntacticTree>());
			suffix.validateSize();
			suffix.validateIndex();
			list.putAll(new FilterConstituent(suffix).correctionList(arr[0]));

			ArrayList<AnomalySyntacticTree> rightChildren = new ArrayList<AnomalySyntacticTree>();
			rightChildren.add(this.infixTree);
			AnomalySyntacticTree prefix = new AnomalySyntacticTree(-1, null, null, null,
					new ArrayList<AnomalySyntacticTree>(), rightChildren);
			prefix.validateSize();
			prefix.validateIndex();
			list.putAll(new FilterConstituent(prefix).correctionList(arr[1]));

			return list;
		}

		Map<Integer, String> correctionList(AnomalySyntacticTree substituentTree) throws Exception {
			Map<Integer, String> correctionList = new HashMap<Integer, String>();
//			Utility.print(infixTree);
//			Utility.print(substituentTree);
			Lexeme seg[] = substituentTree.getSEG();
			Lexeme pos[] = substituentTree.getPOS();
			Lexeme dep[] = substituentTree.getDEP();

			ArrayList<Action> infixList = new ArrayList<Action>();

			assert Utility.equals(seg, infixTree.getSEG());
			assert Utility.equals(pos, infixTree.getPOS());
			assert Utility.equals(dep, infixTree.getDEP());

			infixTree.bubble(substituentTree, infixList);

			int cnt = 0;
			int j = -1;
			for (SyntacticTree tree : new DependencyTreeReader()) {
				++j;

				int[] index = anomalySet(tree);
				++cnt;
				if (index.length == 0)
					continue;
//				Utility.print(tree.toString(index));

				for (int i : index) {
					SyntacticTree _tree = tree.getParticular(i);
					for (Action a : infixList) {
						_tree = a.invoke(_tree);
						if (_tree.parent == null)
							tree = _tree;
					}
				}

				tree.setDEP(new String[tree.size]);
				tree = SyntacticParser.instance.parse(tree);

//				Utility.print("after alteration:");
//				Utility.print(tree);
				correctionList.put(j, tree.infixExpression(true, true));
			}
//			Utility.print("cnt = ", cnt);
			return correctionList;
		}
	}

	static class FilterConstituents extends Filter {
		public AnomalySyntacticTree suffixTree;
		public AnomalySyntacticTree prefixTree;

		public FilterConstituents(String infix) throws Exception {
			assert infix.startsWith("[");

			assert infix.endsWith("]");

			HNode infixNode = Compiler.HNode.compile(infix.substring(1, infix.length() - 1));

			assert infixNode instanceof HNodeMultiplication;

			// [(VT) (PN)] => [(VT(PN)) ]
			suffixTree = new Compiler.HNodeSuffix("", infixNode).toAnomalySyntacticTree(null);
			suffixTree.validateSize();
			suffixTree.validateIndex();

			prefixTree = new Compiler.HNodePrefix("", infixNode).toAnomalySyntacticTree(null);
			prefixTree.validateSize();
			prefixTree.validateIndex();

			StringBuffer str = new StringBuffer();
			str.append("[");
			for (AnomalySyntacticTree tree : suffixTree.leftChildren) {
				str.append("(" + tree.infixExpression() + ")");
			}
			str.append("]");

			regulation = str.toString();
		}

		@Override
		public int satisfy(SyntacticTree tree) {
			for (SyntacticTree t : tree) {
				if (t.satisfy(prefixTree) || t.satisfy(suffixTree))
					return t.id;
			}
			return -1;
		}

		public boolean transform(SyntacticTree tree) {
			return tree.transform(prefixTree) || tree.transform(suffixTree);
		}
	}

	public static void removeDE() throws IOException {
		ArrayList<SyntacticTree> arr = new ArrayList<SyntacticTree>();
		for (SyntacticTree tree : new DependencyTreeReader()) {
			for (SyntacticTree t : tree) {
				if (t.dep.equals("de") && t.parent.pos.equals("DE")) {
					System.out.println(t.parent);
					t.dep = t.parent.dep;
					System.out.println(t.parent);
				}
			}
			arr.add(tree);
			System.out.println(tree);
		}

		DependencyTreeReader.writeTrainingInstance(arr);
	}

	public static void main(String[] args) throws Exception {

		for (Filter filter : FilterSet.syntacticIrregulation) {
			if (filter instanceof FilterExpression) {
				System.out.println(filter.regulation);
			} else if (filter instanceof FilterConstituent) {
				System.out.println(filter.regulation);
			} else if (filter instanceof FilterConstituents) {
				System.out.println(filter.regulation);
			} else {
				System.err.println(filter.getClass().getName());
				System.err.println(filter);
			}

		}
	}

	public static SyntacticTree delete(String infix) throws IOException {
		ArrayList<SyntacticTree> list = new ArrayList<SyntacticTree>();
		SyntacticTree treeDeleted = null;
		for (SyntacticTree tree : new DependencyTreeReader()) {
			if (tree.unadornedExpression().equals(infix)) {
				System.out.println(tree);
				treeDeleted = tree;
			} else {
				list.add(tree);
			}
		}

		DependencyTreeReader.writeTrainingInstance(list);
		return treeDeleted;

	}

	public static void deleteRule(String infix) throws IOException {
		infix = infix.trim();
		int index = -1;
		for (int i = 0; i < FilterSet.syntacticIrregulation.length; i++) {
			if (FilterSet.syntacticIrregulation[i].regulation.equals(infix)) {
				index = i;
			}
		}
		if (index >= 0) {
			Filter[] arr = new Filter[FilterSet.syntacticIrregulation.length - 1];
			for (int i = 0; i < index; ++i) {
				arr[i] = FilterSet.syntacticIrregulation[i];
			}

			for (int i = index + 1; i < FilterSet.syntacticIrregulation.length; ++i) {
				arr[i - 1] = FilterSet.syntacticIrregulation[i];
			}

			Utility.writeString(pathErr, arr);
			FilterSet.syntacticIrregulation = arr;
		}
	}

	public static SyntacticTree peek(String infix) throws IOException {

		for (SyntacticTree tree : new DependencyTreeReader()) {
			if (tree.unadornedExpression().equals(infix)) {
				System.out.println(tree);
				return tree;
			}

		}

		return null;

	}

	static String input;
	static BufferedReader buffer = Utility.readFromStdin();

}
