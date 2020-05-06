package com.nlp.syntax;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.util.Native;
import com.util.Utility;

public class POSTagger {
	public static Logger log = Logger.getLogger(POSTagger.class);
	public POSTagger() {
		try {
			new Utility.Text(Utility.modelsDirectory()+ "cn/dep/vocabulary/pos.txt").collect(tagSet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Set<String> tagSet = new HashSet<String>();

	public String[] tag(String[] seg) {
		return Native.posCN(seg);
	}

	public String[] tag(String[] seg, String[] pos) {
		return Native.posCN(seg);
	}

	public static POSTagger instance = new POSTagger();
}
