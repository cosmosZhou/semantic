package com.util;

import java.util.List;

import org.ahocorasick.trie.Trie;

//javah -jni com.util.Native
public class Native {
	public native static void reinitializeLexiconCN();
	
	public native static void reinitializeLexiconEN();
	
	public native static void reinitializeSyntaxParser();

	public native static void reinitializeCWSTagger();

	public native static void reinitializePOSTagger();

	public native static void reinitializeKeywordCN();

	public native static void reinitializeKeywordEN();

	public native static int[] hyponymStructureCN(String[] keywords, int frequency[]);

	public native static String hyponymCN(String hypernym, String hyponym);

	public native static double[][] hyponymCNs(String text[]);

	public native static String hyponymEN(String hypernym, String hyponym);

	public native static int[] depCN(String[] seg, String[] pos, String[] dep);

	public native static String[] posCN(String[] long_array);

	public native static String[] segmentCN(String text);

	public native static String[][] segmentCNs(String[] long_array);

	public native static String[][][] segmentCNss(String[][] long_array);

	public native static int keywordCN(String text);

	public native static double keywordCNDouble(String text);

	public native static int[] keywordCNs(String text[]);

	public native static List<String> keywordCNList(List<String> text);

	public native static int keywordEN(String text);

	public native static double keywordENDouble(String text);

	public native static int[] keywordENs(String text[]);

	public native static List<String> keywordENList(List<String> text);

	public native static String infixCN(String text);

	public native static String infixEN(String text);

	public native static void displayHelloWorld();

	// native method that prints a prompt and reads a line
	public native static String reverse(String prompt);

	public native static int gcdint(int ecx, int edx);

	public native static long gcdlong(long ecx, long edx);

	public native static int gcdinttemplate(int ecx, int edx);

	public native static long gcdlongtemplate(long ecx, long edx);

	public native static int service(String text);

	public native static double[][] SERVICE(String text);

	public native static int[] ner(String service, String text, int[] repertoire);

	public native static double[][][] NER(String service, String text, int[] repertoire);

	public native static int asm6args(int rcx, int rdx, int r8, int r9, int fifthArg, int sixthArg);

	public native static double relu(double rcx);

	public native static void initializeH5Model(String pwd);

	public native static void initializeAhocorasickDictionary(String pwd);

	public native static void ahocorasickTest();

	@SuppressWarnings("rawtypes")
	public native static Trie.Hit[] parseText(String text);

	static {
		String LD_LIBRARY_PATH = System.getProperty("java.library.path");

		System.out.println("java.library.path = " + LD_LIBRARY_PATH);

		try {
			System.loadLibrary("eigen");
			Native.initializeH5Model(PropertyConfig.get("model", "pwd"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

	}
}
//https://dev.mysql.com/doc/refman/8.0/en/adding-udf.html
//https://www.codeproject.com/Articles/15643/MySQL-User-Defined-Functions