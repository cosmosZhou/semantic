package com.nlp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.flexible.core.parser.SyntaxParser;

import com.util.Native;
import com.util.Utility;
import com.util.Utility.Printer;
import com.nlp.syntax.POSTagger;
import com.nlp.syntax.SyntacticParser;
import com.nlp.syntax.SyntacticTree;

import com.nlp.syntax.AnomalyInspecter;

public class Sentence implements Serializable {

	public enum Protagonist {
		CUSTOMER, OPERATOR, ROBOT;
	}

	public enum QATYPE {
		QUERY, REPLY, NEUTRAL;
		public boolean isNeutral() {
			return this == NEUTRAL;
		}

		public boolean isQuery() {
			return this == QUERY;
		}

		public boolean isReply() {
			return this == REPLY;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5755811681194797169L;
	static final int INDEX_INTEGRITY = 0;
	static final int INDEX_INCOMPLETE = 1;
	static final int INDEX_ANAPHORA = 2;
	static final int INDEX_MULTIPLE = 3;

	public String[] focus() {
		ArrayList<String> arr = new ArrayList<String>();

		for (int i = 0; i < seg.length; ++i) {
			if (pos[i].equals("QUE")) {
				arr.add(seg[i]);
			}
		}

		return arr.toArray(new String[arr.size()]);
	}

	public String[] interrogative() {
		ArrayList<String> arr = new ArrayList<String>();

		for (int i = 0; i < seg.length; ++i) {
			if (pos[i].equals("QUE")) {
				arr.add(seg[i]);
			}
		}

		return arr.toArray(new String[arr.size()]);
	}

	public String[] subject() {
		ArrayList<String> arr = new ArrayList<String>();
		for (SyntacticTree tree : tree.leftChildren) {
			if (tree.dep.equals("suj")) {
				// arr.addAll(tree.getWordSet());
			}
		}
		for (SyntacticTree tree : tree.rightChildren) {
			if (tree.dep.equals("suj")) {
				// arr.addAll(tree.getWordSet());
			}
		}
		return arr.toArray(new String[arr.size()]);
	}

	public String[] object() {
		ArrayList<String> arr = new ArrayList<String>();
		for (SyntacticTree tree : tree.leftChildren) {
			if (tree.dep.equals("obj")) {
				// arr.addAll(tree.getWordSet());
			}
		}
		for (SyntacticTree tree : tree.rightChildren) {
			if (tree.dep.equals("obj")) {
				// arr.addAll(tree.getWordSet());
			}
		}
		return arr.toArray(new String[arr.size()]);
	}

	@Override
	public int hashCode() {
		return sentence.hashCode();
	}

	@Override
	public boolean equals(Object anObject) {
		if (anObject instanceof Sentence) {
			Sentence sent = (Sentence) anObject;
			return sentence.equals(sent.sentence);
		}

		return false;
	}

	public Sentence(String sentence) throws Exception {
		this.sentence = sentence;
	}

	public Sentence(String seg[]) {
		this.seg = seg;
		this.sentence = Utility.convertToOriginal(seg);
	}

	public Sentence(SyntacticTree tree) throws Exception {
		this.seg = tree.getLEX();
		this.pos = tree.getPOS();
		this.dep = tree.getSyntacticTree();
		this.tree = tree;
		this.sentence = Utility.convertToOriginal(seg);
	}

	public Sentence(String sentence, Protagonist protagonist) throws Exception {
		this.sentence = sentence;
		this.protagonist = protagonist;
	}

	public Sentence(String sentence, Protagonist protagonist, String speaker) throws Exception {
		this.sentence = sentence;
		this.protagonist = protagonist;
		this.speaker = speaker;
	}

	// int id = -1;
	public String sentence;
	Protagonist protagonist;
	String speaker;

	/**
	 * indicate whether it is a question or an answer;
	 */
	transient QATYPE qatype;
	// int anomaly;
	/**
	 * confidence of judgment of QA type;
	 */
	transient double confidence;
	transient SyntacticTree tree;
	transient SyntacticTree[] dep;
	transient String[] seg;
	transient String[] pos;

	SyntacticTree getOriginalMathExp() throws Exception {
		if (this.qatype != QATYPE.QUERY)
			return null;
		SyntacticTree subject = this.tree.getSubject();
		if (subject != null && subject.pos.equals("O")) {
			return subject;
		}

		SyntacticTree object = this.tree.getObject();
		if (object != null && object.pos.equals("O")) {
			return object;
		}

		return null;
	}

	public QATYPE qatype() throws Exception {
		return null;
	}

	public String toString() {
		return sentence;
	}

	public int tokenLength() {
		String[] pos = pos();
		int cnt = 0;
		for (String p : pos) {
			switch (p) {
			case "IJ":
			case "PU":
				break;
			default:
				++cnt;
				break;
			}
		}
		return cnt;
	}

	public String[] seg() {
		return Native.segmentCN(sentence);
	}

	public String[] pos() {
		return Native.posCN(seg);
	}

	public SyntacticTree tree() {
		synchronized (this) {
			if (tree == null) {
				String[] seg = seg();
				String[] pos = pos();

				boolean[] indicator = new boolean[seg.length];
				for (int j = 0; j < seg.length; ++j) {
					switch (seg[j]) {
					case "您好":
					case "你好":
						if (pos[j].equals("IJ")) {
							indicator[j] = true;
							break;
						}
					}
				}

				for (int j = 0; j < seg.length; ++j) {
					switch (pos[j]) {
					case "IJ":
					case "PU":
						indicator[j] = true;
						continue;
					default:
						break;
					}
					break;
				}

				for (int j = pos.length - 1; j >= 0; --j) {
					if (!pos[j].equals("PU")) {
						break;
					}
					switch (seg[j]) {
					case "\"":
					case "\'":
					case "”":
					case "“":
					case "’":
					case "‘":
						break;
					default:
						indicator[j] = true;
						continue;
					}
					break;
				}
				int size = 0;
				for (int i = 0; i < indicator.length; i++) {
					if (indicator[i])
						++size;
				}

				if (size == 0 || indicator.length == size) {
					tree = SyntacticParser.instance.parse(seg, pos);
				} else {
					size = indicator.length - size;
					String segArr[] = new String[size];
					// String posArr[] = new String[size];
					int index = 0;
					for (int i = 0; i < indicator.length; i++) {
						if (!indicator[i]) {
							segArr[index] = seg[i];
							// posArr[index] = pos[i];
							++index;
						}
					}
					String posArr[] = POSTagger.instance.tag(segArr);
					tree = SyntacticParser.instance.parse(segArr, posArr);

					for (int i = 0; i < indicator.length; i++) {
						if (!indicator[i])
							break;
					}
					for (int i = indicator.length - 1; i >= 0; --i) {
						if (!indicator[i])
							break;
						if (seg[i].matches("[?？]+")) {
							tree.append("?", "PU", "pu");
							segArr = Utility.copier(segArr, "?");
							posArr = Utility.copier(posArr, "PU");
						}
					}

					this.seg = segArr;
					this.pos = posArr;
				}
			}
			return tree;
		}
	}

	public SyntacticTree[] dep() throws Exception {
		synchronized (this) {
			if (dep == null) {
				dep = tree().getSyntacticTree();
			}
			return dep;
		}
	}

	double lengthSimilarity(Sentence sentence) {
		int tokenLength = tokenLength();
		int _tokenLength = sentence.tokenLength();
		if (tokenLength + _tokenLength == 0)
			return 1;
		return 1 - Math.abs(tokenLength - _tokenLength) * 1.0 / (tokenLength + _tokenLength);
	}

	public double similarity(Sentence sentence) throws Exception {
		return 0;
	}

	static public int getIndexOfAnomaly(String anomaly) throws Exception {
		if (anomaly.equals("INTEGRITY"))
			return INDEX_INTEGRITY;
		if (anomaly.equals("INCOMPLETE"))
			return INDEX_INCOMPLETE;
		if (anomaly.equals("ANAPHORA"))
			return INDEX_ANAPHORA;
		if (anomaly.equals("MULTIPLE"))
			return INDEX_MULTIPLE;
		return -1;
	}

	static int anomalyType(String[][] morpheme, SyntacticTree featureanomaly) {
		if (morpheme[0].length == 1) {
			return INDEX_INCOMPLETE;
		}
		if (featureanomaly == null) {
			return INDEX_INCOMPLETE;
		}
		if (featureanomaly.match("主语").size() == 0) {
			return INDEX_INCOMPLETE;
		}
		return INDEX_INTEGRITY;
	}

	static int find_question_sequential(Sentence[] history, int i) {
		for (; i < history.length; ++i) {
			if (history[i].protagonist == Protagonist.OPERATOR)
				continue;
			// if (Utility.contains(history[i].feature[1].x, "BONJOUR"))
			// continue;
			// if (history[i].anomaly == INDEX_INCOMPLETE)
			// continue;

			if (history[i].qatype != QATYPE.QUERY)
				continue;

			// find a pertinent question sentence from the visitor's speech
			return i;
		}

		return -1;
	}

	static Utility.Couplet<Integer, Double> find_answer_sequential(Sentence[] history, int i) throws Exception {
		if ((history[i].protagonist != Protagonist.CUSTOMER)) {
			throw new Exception("(history[i].protagonist != INDEX_CUSTOMER)");
		}

		if (history[i].qatype != QATYPE.QUERY) {
			throw new Exception("history[i].qatype != INDEX_QUE");
		}

		double confidence = 1;
		for (++i; i < history.length; ++i) {
			// if it is a visitor, to see whether the old question is
			// overwhelmed by the new question proposed by the visitor
			confidence *= history[i].confidence;
			if (history[i].qatype.isNeutral())
				continue;

			if (history[i].protagonist == Protagonist.CUSTOMER) {
				if (history[i].qatype == QATYPE.QUERY)
					break;
				else
					continue;
			}

			if (history[i].qatype == QATYPE.REPLY) {
				// a pertinent DECLARATIVE sentence from the operator has been
				// detected.
				return new Utility.Couplet<Integer, Double>(i, confidence);
			}
		}

		return null;
	}

	public String decompile() throws Exception {
		return null;
	}

	boolean isIncompleteQuestion() {
		String[] pos = pos();
		int realWordCnt = 0;
		for (int i = 0; i < pos.length; ++i) {
			switch (pos[i]) {
			case "PU":
			case "IJ":
			case "AS":
				continue;
			}
			++realWordCnt;
		}

		if (realWordCnt > 2)
			return false;
		return true;
	}

	boolean topGeneration(int j) {
		return dep[j].parent == null || dep[j].parent.parent == null;
	}

	boolean buriedGeneration(int j) {
		return dep[j].parent != null && dep[j].parent.parent != null;
	}

	void addFeature(HashSet<String> set, int i, int... arr) {
		String featurePos = seg[i] + " " + pos[i];
		String featurePosPos = pos[i];
		for (int index : arr) {
			if (i + index >= 0 && i + index < pos.length) {
				featurePos += "|" + index + "=" + pos[i + index];
				featurePosPos += "|" + index + "=" + pos[i + index];
			}
		}
		set.add(featurePos);
		set.add(featurePosPos);
	}

	void addFeatureSeg(HashSet<String> set, int i, int... arr) {
		String featureSeg = seg[i];

		for (int index : arr) {
			if (i + index > 0 && i + index < seg.length) {
				featureSeg += "|" + index + "=" + seg[i + index];
			}
		}
		set.add(featureSeg);
	}

	void addFeaturePos(HashSet<String> set, int i, int... arr) {
		String featureSeg = pos[i];

		for (int index : arr) {
			if (i + index > 0 && i + index < pos.length) {
				featureSeg += "|" + index + "=" + pos[i + index];
			}
		}
		set.add(featureSeg);
	}

	static String modalVerb[] = { "want", "need", "must", "need", "will", "should", "could" };

	String modalVerb(int j) {
		return null;
	}

	// we should determine the parents of the interrogative, whether there are
	// multiple verbs above.
	boolean isInterrogative(int j) {
		return false;
	}

	boolean isPredicativeInterrogativeStruct(int j) {
		if (dep[j].dep == null || !dep[j].dep.equals("adj") || j == 0 || dep[j - 1].parent != dep[j].parent)
			return false;
		return dep[j - 1].dep.equals("suj");
	}

	enum InterrogativeVerb {
		provide, tell, ask, query, know, send, business;

		static InterrogativeVerb construct(String seg) {
			return null;
		}
	}

	enum AuxiliaryVerb {
		want, please, could, help;
		static AuxiliaryVerb construct(String seg) {
			return null;
		}

	}

	public boolean interrogativeExtractionRNN(int j) throws Exception {
		return true;
	}

	public Sentence[] splitSentence() {
		ArrayList<String> sent = new ArrayList<String>();
		ArrayList<Sentence> paragraph = new ArrayList<Sentence>();
		for (String lexeme : this.seg()) {
			sent.add(lexeme);
			switch (lexeme.charAt(0)) {
			case '.':
			case '。':

			case ';':
			case '；':

			case '!':
			case '！':

			case '?':
			case '？':

			case '\r':
			case '\n':
				paragraph.add(new Sentence(Utility.toArray(sent)));
				sent.clear();
				break;
			default:

			}
		}

		if (!sent.isEmpty()) {
			paragraph.add(new Sentence(Utility.toArray(sent)));
			sent.clear();
		}
		return paragraph.toArray(new Sentence[paragraph.size()]);
	}

}
