package com.nlp.syntax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.util.Native;
import com.util.Utility;


public class SyntacticParser {
	public SyntacticParser() {
		try {
			new Utility.Text(Utility.modelsDirectory()+ "cn/dep/vocabulary/head_tags.txt").collect(tagSet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public Set<String> tagSet = new HashSet<String>();
	public static int maxIteration = 4;

	public SyntacticTree parseWithAdjustment(SyntacticTree tree) throws Exception {
		return tree;
	}

	public SyntacticTree parse(SyntacticTree tree) throws Exception {
		return tree;
	}

	public SyntacticTree parse(String seg[], String pos[]) {
		return null;
	}

	public SyntacticTree parse(ArrayList<String> seg, ArrayList<String> pos) {
		return null;
	}

	public SyntacticTree parseWithAdjustment(String seg[], String pos[]) throws Exception {
		return null;
	}

	public SyntacticTree parse(String string) {
		String[] seg = Native.segmentCN(string);
		String[] pos = Native.posCN(seg);
		return parse(seg, pos);
	}

	public static SyntacticParser instance = new SyntacticParser();
}
