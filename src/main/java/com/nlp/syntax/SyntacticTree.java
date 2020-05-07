package com.nlp.syntax;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.nlp.syntax.AnomalyInspecter.Filter;
import com.nlp.syntax.AnomalyInspecter.Lexeme;
import com.util.Utility;

public class SyntacticTree implements Serializable, Iterable<SyntacticTree> {

	public boolean containsIrregulation() throws Exception {
		return AnomalyInspecter.FilterSet.containsIrregulation(this) != null;
	}

	private static final long serialVersionUID = -4766669720074872942L;

	public String seg;
	public String lex;
	public String pos;

	/**
	 * 原句中的顺序id
	 */
	public int id;
	public int size = 1;

	/**
	 * dependency relation
	 */
	public String dep;
	public List<SyntacticTree> leftChildren;
	public List<SyntacticTree> rightChildren;
	/**
	 * parent node
	 */
	public SyntacticTree parent = null;

	public SyntacticTree(int id) {
		this(id, null, null, null);
	}

	public SyntacticTree(int id, String word) {
		this(id, word, null, null);
	}

	// change
	public SyntacticTree(int id, String word, String pos) {
		this(id, word, pos, null);
	}

	public SyntacticTree(int id, String word, String pos, String depClass) {
		this.lex = word;
		this.pos = pos;
		this.seg = word;
		this.id = id;
		this.dep = depClass;
		leftChildren = new ArrayList<SyntacticTree>();
		rightChildren = new ArrayList<SyntacticTree>();
	}

	public SyntacticTree(int id, String segOriginal, String word, String pos, String depClass) {
		this.lex = segOriginal;
		this.seg = word;
		this.pos = pos;
		this.id = id;
		this.dep = depClass;
		leftChildren = new ArrayList<SyntacticTree>();
		rightChildren = new ArrayList<SyntacticTree>();
	}

	public SyntacticTree(int id, String word, String pos, String depClass, SyntacticTree parent) {
		this(id, word, pos, depClass);
		this.parent = parent;
	}

	public SyntacticTree(SyntacticTree tree) {
		SyntacticTree clone = tree.clone();
		this.lex = clone.lex;
		this.pos = clone.pos;
		this.seg = clone.seg;
		this.id = clone.id;
		this.dep = clone.dep;
		leftChildren = clone.leftChildren;
		rightChildren = clone.rightChildren;
		this.validateBranches();
	}

	public SyntacticTree(int id, String word, String pos, String depClass, List<SyntacticTree> leftChildren,
			List<SyntacticTree> rightChildren) {
		this(id, word, pos, depClass);
		this.leftChildren = leftChildren;
		this.rightChildren = rightChildren;
		// size = 1;
		for (SyntacticTree tree : leftChildren) {
			tree.parent = this;
			// size += tree.size();
		}
		for (SyntacticTree tree : rightChildren) {
			tree.parent = this;
			// size += tree.size();
		}
	}

	public String getDepClass() {
		return this.dep;
	}

	public void setDepClass(String depClass) {
		this.dep = depClass;
	}

	// add the new node as a left child;
	public SyntacticTree addLeftChild(SyntacticTree ch) {
		// int id = ch.id;
		// int i = 0;
		// for (; i < leftChildren.size(); i++) {
		// int cid = leftChildren.get(i).id;
		// if (cid > id)
		// break;
		// }
		if (this.size != this.evaluateSize()) {
			log.info("size = " + size);
			log.info("evaluateSize = " + evaluateSize());
			throw new RuntimeException("this.size != this.evaluateSize()");
		}

		leftChildren.add(0, ch);
		ch.setParent(this);
		updatesize(ch.size);
		validateIndex();
		if (this.size != this.evaluateSize()) {
			log.info("size = " + size);
			log.info("evaluateSize = " + evaluateSize());
			throw new RuntimeException("this.size != this.evaluateSize()");
		}
		return this;
	}

	public SyntacticTree addRightChild(SyntacticTree ch) {
		rightChildren.add(ch);
		ch.setParent(this);
		updatesize(ch.size);
		validateIndex();
		if (this.size != this.evaluateSize()) {
			log.info("size = " + size);
			log.info("evaluateSize = " + evaluateSize());
			throw new RuntimeException("this.size != this.evaluateSize()");
		}
		return this;
	}

	/**
	 * 更新树大小
	 * 
	 * @param size
	 */
	private void updatesize(int size) {
		this.size += size;
		if (parent != null) {
			parent.updatesize(size);
		}

	}

	/**
	 * 设置父节点
	 * 
	 * @param tree
	 */
	private void setParent(SyntacticTree tree) {
		parent = tree;
	}

	public SyntacticTree getParent() {
		return parent;
	}

	public String toBracketString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(id);
		// if (word != null) {
		// sb.append("[");
		// sb.append(word);
		// sb.append("]");
		// }
		sb.append(" ");
		for (int i = 0; i < leftChildren.size(); i++) {
			sb.append(leftChildren.get(i));
		}
		sb.append("-");
		for (int i = 0; i < rightChildren.size(); i++) {
			sb.append(rightChildren.get(i));
		}
		sb.append("]");
		return sb.toString();
	}

	public int[] toHeadsArray() {
		int[] heads = new int[size];
		toHeadsArray(heads);
		heads[this.id] = -1;
		return heads;
	}

	public void toHeadsArray(int[] heads) {
		for (SyntacticTree ch : leftChildren) {
			heads[ch.id] = id;
			ch.toHeadsArray(heads);
		}

		for (SyntacticTree ch : rightChildren) {
			heads[ch.id] = id;
			ch.toHeadsArray(heads);
		}
	}

	public int size() {
		return size;
	}

	int evaluateSize() {
		int size = 1;
		for (SyntacticTree t : this.leftChildren) {
			size += t.evaluateSize();
		}
		for (SyntacticTree t : this.rightChildren) {
			size += t.evaluateSize();
		}
		return size;
	}

	public int validateSize() {
		size = 1;
		for (SyntacticTree tree : this.leftChildren) {
			size += tree.validateSize();
		}
		for (SyntacticTree tree : this.rightChildren) {
			if (tree == null) {
				System.out.println("tree = " + "is null");
			}
			size += tree.validateSize();
		}
		return size;
	}

	public boolean isValidate() {
//		for (SyntacticTree tree : this) {
//			if (!POSTagger.instance.tagSet().contains(tree.pos)) {
//				return false;
//			}
//			if (!SyntacticParser.instance.tagSet().contains(tree.dep)) {
//				return false;
//			}
//		}

		return true;
	}

	public List<SyntacticTree> getAllChild() {
		List<SyntacticTree> childs = new ArrayList<SyntacticTree>();
		childs.addAll(leftChildren);
		childs.addAll(rightChildren);
		return childs;
	}

	public boolean contain(SyntacticTree dt) {
		if (this.equals(dt))
			return true;
		for (SyntacticTree ch : leftChildren) {
			if (ch.contain(dt))
				return true;
		}
		for (SyntacticTree ch : rightChildren) {
			if (ch.contain(dt))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<List<String>> toList() {
		ArrayList<List<String>> lists = new ArrayList<List<String>>(size);
		for (int i = 0; i < size; i++) {
			lists.add(null);
		}
		toList(lists);
		return lists;
	}

	private void toList(ArrayList<List<String>> lists) {
		ArrayList<String> e = new ArrayList<String>();
		e.add(seg);
		e.add(pos);
		if (parent == null) {
			e.add(String.valueOf(-1));
			e.add("Root");
		} else {
			e.add(String.valueOf(parent.id));
			e.add(dep);
		}
		lists.set(id, e);
		for (int i = 0; i < leftChildren.size(); i++) {
			leftChildren.get(i).toList(lists);
		}
		for (int i = 0; i < rightChildren.size(); i++) {
			rightChildren.get(i).toList(lists);
		}
	}

	enum TAG {
		Seg, Pos, Dep, Lex
	}

	public String[] getSEGNonprojective() {
		String[] _seg = new String[this.size];
		String[] seg = getSEG();
		int index = 0;
		for (int i : this.getNonprojectiveMapping()) {
			_seg[index++] = seg[i];
		}
		return _seg;
	}

	public String[] getSEG() {
		ArrayList<String> arr = new ArrayList<String>();
		getTag(arr, TAG.Seg);
		return arr.toArray(new String[arr.size()]);
	}

	public String[] getLEX() {
		ArrayList<String> arr = new ArrayList<String>();
		getTag(arr, TAG.Lex);
		return arr.toArray(new String[arr.size()]);
	}

	public String[] getPOS() {
		ArrayList<String> arr = new ArrayList<String>();
		getTag(arr, TAG.Pos);
		return arr.toArray(new String[arr.size()]);
	}

	public String[] getDEP() {
		ArrayList<String> arr = new ArrayList<String>();
		getTag(arr, TAG.Dep);
		return arr.toArray(new String[arr.size()]);
	}

	public void setDEP(String dep[]) {
		int i = 0;
		for (SyntacticTree t : this) {
			t.dep = dep[i++];
		}
	}

	public void setDEP(ArrayList<String> dep) {
		int i = 0;
		for (SyntacticTree t : this) {
			t.dep = dep.get(i++);
		}
	}

	public void setPOS(String pos[]) {
		int i = 0;
		for (SyntacticTree t : this) {
			t.pos = pos[i++];
		}
	}

	public void setPOS(ArrayList<String> pos) {
		int i = 0;
		for (SyntacticTree t : this) {
			t.pos = pos.get(i++);
		}
	}

	public void setLEX(String lex[]) {
		int i = 0;
		for (SyntacticTree t : this) {
			t.lex = lex[i++];
		}
	}

	public SyntacticTree[] getSyntacticTree() {
		ArrayList<SyntacticTree> arr = new ArrayList<SyntacticTree>();
		getDependencyTree(arr);
		return arr.toArray(new SyntacticTree[arr.size()]);
	}

	/**
	 * 
	 * @param arr = infix traversal of the tree;
	 */
	public void getTag(ArrayList<String> arr, TAG tag) {
		for (SyntacticTree tree : this.leftChildren) {
			tree.getTag(arr, tag);
		}
		switch (tag) {
		case Lex:
			arr.add(lex);
			break;
		case Seg:
			arr.add(seg);
			break;
		case Pos:
			arr.add(pos);
			break;
		case Dep:
			if (dep == null) {
				arr.add("root");
			} else
				arr.add(dep);
			break;
		}

		for (SyntacticTree tree : this.rightChildren) {
			tree.getTag(arr, tag);
		}
	}

	public void getDependencyTree(ArrayList<SyntacticTree> arr) {
		for (SyntacticTree tree : this.leftChildren) {
			tree.getDependencyTree(arr);
		}
		arr.add(this);

		for (SyntacticTree tree : this.rightChildren) {
			tree.getDependencyTree(arr);
		}
	}

	void match(String relation, List<SyntacticTree> list) {
		if (this.dep != null && this.dep.equals(relation)) {
			list.add(this);
			return;
		}

		for (SyntacticTree e : this.leftChildren) {
			e.match(relation, list);
		}

		for (SyntacticTree e : this.rightChildren) {
			e.match(relation, list);
		}
	}

	public List<SyntacticTree> match(String relation) {
		List<SyntacticTree> list = new ArrayList<SyntacticTree>();
		match(relation, list);
		return list;
	}

	// if relation is null, it is a predicate;
	public Utility.TextTreeNode buildShadowTree() {
		// recursive inorder scan used to build the shadow tree
		// create the new shadow tree;
		Utility.TextTreeNode newNode = new Utility.TextTreeNode(seg);
		// tree node
		if (leftChildren != null && leftChildren.size() > 0) {
			newNode.x = new Utility.TextTreeNode[leftChildren.size()];
			int i = 0;
			for (SyntacticTree tree : leftChildren)
				newNode.x[i++] = tree.buildShadowTree();
		}
		// allocate node for left child at next level in tree;

		if (rightChildren != null && rightChildren.size() > 0) {
			newNode.y = new Utility.TextTreeNode[rightChildren.size()];
			int i = 0;
			for (SyntacticTree tree : rightChildren)
				newNode.y[i++] = tree.buildShadowTree();
		}

		return newNode;
	}

	public String toString() {
		return toString(null);
	}

	static boolean bSegInclusive = true;

	public String toString(int mark[]) {
		String tree = null;
		SyntacticTree parent = this.parent;
		this.parent = null;
		try {
			String[] pos = getPOS();
			String[] dep = getDEP();
			String[] seg = getLEX();

			int size = seg.length;
			if (size != pos.length || size != dep.length) {
				System.out.println("size != pos.length || size != dep.length");
			}

			int max_width = -1;
			for (int i = 0; i < size; ++i) {
				max_width = Utility.max(max_width, Utility.strlen(seg[i]), Utility.strlen(dep[i]),
						Utility.strlen(pos[i]));
			}

			++max_width;

			SyntacticTree.Iterator it = iterator();

			int length_lexeme[] = new int[size];
			int left_parenthesis[] = new int[size];
			int right_parenthesis[] = new int[size];

			int index = 0;
			while (it.hasNext() && index < size) {
				left_parenthesis[index] = it.left_parenthesis();
				right_parenthesis[index] = it.right_parenthesis();

				if (!seg[index].equals(it.next().lex)) {
					System.out.println("inconsistent lexeme");
				}

				seg[index] = protectParenthesis(seg[index]);
				length_lexeme[index] = Utility.strlen(seg[index]);

				int length = length_lexeme[index] + left_parenthesis[index];
				if (index > 0)
					length += right_parenthesis[index - 1];

				if (max_width < length) {
					max_width = length;
				}

				++index;
			}

			String infix = "";
			for (index = 0; index < size; ++index) {
				int length = max_width - length_lexeme[index] - left_parenthesis[index];
				if (index > 0)
					length -= right_parenthesis[index - 1];

				infix += Utility.toString(length, ' ');

				infix += Utility.toString(left_parenthesis[index], '(');
				infix += seg[index];
				infix += Utility.toString(right_parenthesis[index], ')');
			}

			if (!infix.replaceAll("\\s+", "").equals(this.infixExpression())) {
				System.out.println("infixExpression error");
				System.out.println(infix.replaceAll("\\s+", ""));
				System.out.println(this.infixExpression());
			}

			String segString = "";
			String posString = "";
			String depString = "";
			String markString = null;
			String[] marks = null;
			if (mark != null) {
				marks = Utility.errorMark(seg.length, mark);
				markString = "";
			}

			seg = getLEX();
			for (int i = 0; i < pos.length; ++i) {
				if (bSegInclusive)
					segString += Utility.toString(max_width - Utility.strlen(seg[i]), ' ') + seg[i];
				posString += Utility.toString(max_width - Utility.strlen(pos[i]), ' ') + pos[i];
				depString += Utility.toString(max_width - Utility.strlen(dep[i]), ' ') + dep[i];
				if (markString != null)
					markString += Utility.toString(max_width - Utility.strlen(marks[i]), ' ') + marks[i];
			}

			Utility.TextTreeNode TextTreeNode = buildShadowTree();
//			TextTreeNode.max_width = max_width;

			tree = TextTreeNode.toString();
			// tree = ";" + tree.replace("\r\n ", "\r\n;");

			tree += infix + Utility.lineSeparator;
			if (bSegInclusive)
				tree += segString + Utility.lineSeparator;
			tree += posString + Utility.lineSeparator;
			tree += depString + Utility.lineSeparator;
			if (markString != null)
				tree += markString + Utility.lineSeparator;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.parent = parent;
		}

		return ";" + this.unadornedExpression() + Utility.lineSeparator + tree;
	}

	public String toString(int mark[], String[] args) {
		String tree = null;
		SyntacticTree parent = this.parent;
		this.parent = null;
		try {
			String[] seg = getLEX();
			String[] pos = getPOS();
			String[] dep = getDEP();

			int size = seg.length;
			if (size != pos.length || size != dep.length) {
				System.out.println("size != pos.length || size != dep.length");
			}

			int max_width = -1;
			for (int i = 0; i < size; ++i) {
				max_width = Utility.max(max_width, Utility.strlen(seg[i]), Utility.strlen(dep[i]),
						Utility.strlen(pos[i]));
			}

			++max_width;

			SyntacticTree.Iterator it = iterator();

			int length_lexeme[] = new int[size];
			int left_parenthesis[] = new int[size];
			int right_parenthesis[] = new int[size];

			int index = 0;
			while (it.hasNext() && index < size) {
				left_parenthesis[index] = it.left_parenthesis();
				right_parenthesis[index] = it.right_parenthesis();

				if (!seg[index].equals(it.next().lex)) {
					System.out.println("inconsistent lexeme");
				}

				seg[index] = protectParenthesis(seg[index]);
				length_lexeme[index] = Utility.strlen(seg[index]);

				int length = length_lexeme[index] + left_parenthesis[index];
				if (index > 0)
					length += right_parenthesis[index - 1];

				if (max_width < length) {
					max_width = length;
				}

				++index;
			}

			String infix = "";
			for (index = 0; index < size; ++index) {
				int length = max_width - length_lexeme[index] - left_parenthesis[index];
				if (index > 0)
					length -= right_parenthesis[index - 1];

				infix += Utility.toString(length, ' ');

				infix += Utility.toString(left_parenthesis[index], '(');
				infix += seg[index];
				infix += Utility.toString(right_parenthesis[index], ')');
			}

			if (!infix.replaceAll("\\s+", "").equals(this.infixExpression())) {
				System.out.println("infixExpression error");
				System.out.println(infix.replaceAll("\\s+", ""));
				System.out.println(this.infixExpression());
			}

			String segString = "";
			String posString = "";
			String depString = "";
			String markString = null;
			String[] marks = null;
			if (mark != null) {
				marks = Utility.errorMark(seg.length, mark);
				markString = "";
			}

			seg = getLEX();
			for (int i = 0; i < pos.length; ++i) {
				segString += Utility.toString(max_width - Utility.strlen(seg[i]), ' ') + seg[i];
				posString += Utility.toString(max_width - Utility.strlen(pos[i]), ' ') + pos[i];
				depString += Utility.toString(max_width - Utility.strlen(dep[i]), ' ') + dep[i];
				if (markString != null)
					markString += Utility.toString(max_width - Utility.strlen(marks[i]), ' ') + marks[i];
			}

			Utility.TextTreeNode TextTreeNode = buildShadowTree();
//			TextTreeNode.max_width = max_width;

			tree = TextTreeNode.toString(max_width);
			// tree = ";" + tree.replace("\r\n ", "\r\n;");
			int i = 0;
			args[i++] = infix;
			args[i++] = segString;
			args[i++] = posString;
			args[i++] = depString;

			if (markString != null)
				args[i++] = markString;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.parent = parent;
		}

		return tree;
	}

	public String toStringNonHierarchical() {
		String tree = null;
		SyntacticTree parent = this.parent;
		this.parent = null;
		try {
			String[] pos = getPOS();
			String[] dep = getDEP();
			String[] seg = getLEX();

			int size = seg.length;
			if (size != pos.length || size != dep.length) {
				System.out.println("size != pos.length || size != dep.length");
			}

			int max_width = -1;
			for (int i = 0; i < size; ++i) {
				max_width = Utility.max(max_width, Utility.strlen(seg[i]), Utility.strlen(dep[i]),
						Utility.strlen(pos[i]));
			}

			++max_width;

			SyntacticTree.Iterator it = iterator();

			int length_lexeme[] = new int[size];
			int left_parenthesis[] = new int[size];
			int right_parenthesis[] = new int[size];

			int index = 0;
			while (it.hasNext() && index < size) {
				left_parenthesis[index] = it.left_parenthesis();
				right_parenthesis[index] = it.right_parenthesis();

				if (!seg[index].equals(it.next().lex)) {
					System.out.println("inconsistent lexeme");
				}

				seg[index] = protectParenthesis(seg[index]);
				length_lexeme[index] = Utility.strlen(seg[index]);

				int length = length_lexeme[index] + left_parenthesis[index];
				if (index > 0)
					length += right_parenthesis[index - 1];

				if (max_width < length) {
					max_width = length;
				}

				++index;
			}

			String infix = "";
			for (index = 0; index < size; ++index) {
				int length = max_width - length_lexeme[index] - left_parenthesis[index];
				if (index > 0)
					length -= right_parenthesis[index - 1];

				infix += Utility.toString(length, ' ');

				infix += Utility.toString(left_parenthesis[index], '(');
				infix += seg[index];
				infix += Utility.toString(right_parenthesis[index], ')');
			}

			if (!infix.replaceAll("\\s+", "").equals(this.infixExpression())) {
				System.out.println("infixExpression error");
				System.out.println(infix.replaceAll("\\s+", ""));
				System.out.println(this.infixExpression());
			}

			String posString = "";
			String depString = "";
			for (int i = 0; i < pos.length; ++i) {
				posString += Utility.toString(max_width - Utility.strlen(pos[i]), ' ') + pos[i];
				depString += Utility.toString(max_width - Utility.strlen(dep[i]), ' ') + dep[i];
			}

			tree = infix + Utility.lineSeparator;

			tree += posString + Utility.lineSeparator;
			tree += depString + Utility.lineSeparator;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.parent = parent;
		}

		return tree;
	}

	public static String protectParenthesis(String word) {
		switch (word) {
		case "+":
			return "＋";
		case "*":
			return "＊";
		case "(":
			return "（";
		case ")":
			return "）";
		case "/":
			return "／";
		case "\\":
			return "＼";
		default:
			return word;
		}
	}

	public String infixExpression() {
		return infixExpression(true, false);
	}

	int[] getNonprojectiveMapping() {
		int[] mapping = new int[this.size];
		int index = 0;
		for (SyntacticTree t : this) {
			mapping[t.id] = index++;
		}
		return mapping;
	}

	public boolean projective() {
		int index = 0;
		for (SyntacticTree t : this) {
			if (t.id != index)
				return false;
			++index;
		}
		return true;
	}

	public String infixExpression(boolean bLex) {
		return infixExpression(bLex, true);
	}

	public String infixExpression(boolean bLex, boolean bPos, boolean projective) {
		StringBuilder infix = new StringBuilder();
		for (SyntacticTree tree : this.leftChildren) {
			infix.append("(" + tree.infixExpression(bLex, bPos, projective) + ")");
		}
		String lexeme = bLex ? protectParenthesis(lex) : seg;
		if (bPos)
			lexeme += "/" + pos + "/" + dep;
		if (!projective)
			lexeme += "/" + this.id;

		infix.append(lexeme);
		for (SyntacticTree tree : this.rightChildren) {
			infix.append("(" + tree.infixExpression(bLex, bPos, projective) + ")");
		}
		return infix.toString();
	}

	public String infixExpression(boolean bLex, boolean bPos) {
		return infixExpression(bLex, bPos, false);
	}

	public String unadornedExpression() {
		String infix = "";
		for (SyntacticTree tree : this.leftChildren) {
			infix += tree.unadornedExpression();
		}
		infix += lex;
		for (SyntacticTree tree : this.rightChildren) {
			infix += tree.unadornedExpression();
		}
		return infix;
	}

	public String simplifiedExpression() {
		String infix = "";
		for (SyntacticTree tree : this.leftChildren) {
			infix += tree.simplifiedExpression();
		}
		infix += lex;
		for (SyntacticTree tree : this.rightChildren) {
			infix += tree.simplifiedExpression();
		}
		return infix;
	}

	public void questionConstituent(HashSet<SyntacticTree> treeSet) {
		if (this.pos.equals("QUE")) {
			treeSet.add(this);
		}
		for (SyntacticTree tree : this.leftChildren) {
			tree.questionConstituent(treeSet);
		}
		for (SyntacticTree tree : this.rightChildren) {
			tree.questionConstituent(treeSet);
		}
	}

	/**
	 * extract the interrogatives of the sentence.
	 * 
	 * @return
	 */
	public HashSet<SyntacticTree> questionConstituent() {
		HashSet<SyntacticTree> treeSet = new HashSet<SyntacticTree>();
		questionConstituent(treeSet);
		return treeSet;
	}

	public void negConstituent(HashSet<SyntacticTree> treeSet) {
		if (this.pos.equals("NEG")) {
			treeSet.add(this);
		}
		for (SyntacticTree tree : this.leftChildren) {
			tree.negConstituent(treeSet);
		}
		for (SyntacticTree tree : this.rightChildren) {
			tree.negConstituent(treeSet);
		}
	}

	public HashSet<SyntacticTree> negConstituent() {
		HashSet<SyntacticTree> treeSet = new HashSet<SyntacticTree>();
		negConstituent(treeSet);
		return treeSet;
	}

	static public SyntacticTree parse(String token[][], int INDICES[]) throws Exception {
		return parse(token[0], token[1], token[2], INDICES);
	}

	static public SyntacticTree parse(String seg[], String pos[], String dep[], int INDICES[]) throws Exception {
		SyntacticTree tree[] = new SyntacticTree[INDICES.length];

		int length = INDICES.length;
		if (seg.length != length || pos.length != length || dep.length != length) {
			log.info(Utility.toString(seg, "\t", null, seg.length));
			log.info(Utility.toString(pos, "\t", null, pos.length));
			log.info(Utility.toString(dep, "\t", null, dep.length));

			throw new Exception("lengths are not coherent!");
		}
		for (int i = 0; i < length; ++i) {
			tree[i] = new SyntacticTree(i, seg[i], pos[i], dep[i]);
		}

		int rootIndex = -1;
		for (int i = 0; i < length; ++i) {
			tree[i].size = 1;
			if (INDICES[i] >= 0) {
				SyntacticTree parent = tree[INDICES[i]];
				tree[i].parent = parent;
				if (i < INDICES[i]) {
					parent.leftChildren.add(tree[i]);
				} else if (i > INDICES[i]) {
					parent.rightChildren.add(tree[i]);
				} else {
					// throw new Exception("self loop occurred!");
				}
			} else {
				rootIndex = i;
			}
		}

		if (rootIndex < 0) {
			log.info(Utility.toString(seg, "\t", null, seg.length));
			log.info(Utility.toString(pos, "\t", null, pos.length));
			log.info(Utility.toString(dep, "\t", null, dep.length));
			throw new Exception("root of the tree is not detected!");
		}

		tree[rootIndex].validateSize();

		assert tree[rootIndex].size == length;
		return tree[rootIndex];
	}

	static public SyntacticTree parse(ArrayList<String> seg, ArrayList<String> pos, ArrayList<String> dep,
			ArrayList<Integer> INDICES) throws Exception {
		return parse(Utility.toArray(seg), Utility.toArray(pos), dep.toArray(new String[dep.size()]),
				Utility.toArray(INDICES));
	}

	boolean leftEmpty() {
		return leftChildren == null || leftChildren.size() == 0;
	}

	boolean rightEmpty() {
		return rightChildren == null || rightChildren.size() == 0;
	}

	// public boolean isLeftChild() {
	// if (parent == null || parent.leftEmpty()) {
	// return false;
	// }
	// return parent.leftChildren.contains(this);
	// }

	public boolean isLeftChild(SyntacticTree root) {
		if (parent == null || parent.leftEmpty() || this == root) {
			return false;
		}
		return parent.leftChildren.contains(this);
	}

	// public boolean isRightChild() {
	// if (parent == null || parent.rightEmpty()) {
	// return false;
	// }
	// return parent.rightChildren.contains(this);
	// }

	public boolean isRightChild(SyntacticTree root) {
		if (parent == null || parent.rightEmpty() || this == root) {
			return false;
		}
		return parent.rightChildren.contains(this);
	}

	boolean isLastRightChild() {
		if (parent == null || parent.rightEmpty()) {
			return false;
		}
		return parent.parent != null && parent.rightChildren.indexOf(this) == parent.rightChildren.size() - 1;
	}

	boolean isLastRightChild(SyntacticTree root) {
		if (parent == null || parent.rightEmpty() || this == root) {
			return false;
		}
		return parent.parent != null && parent.rightChildren.indexOf(this) == parent.rightChildren.size() - 1;
	}

	SyntacticTree leftmost(int... hierarchy) {
		SyntacticTree tree = this;
		while (!tree.leftEmpty()) {
			tree = tree.leftChildren.get(0);
			++hierarchy[0];
		}
		return tree;
	}

	SyntacticTree leftmost() {
		SyntacticTree tree = this;
		while (!tree.leftEmpty()) {
			tree = tree.leftChildren.get(0);
		}
		return tree;
	}

	SyntacticTree rightmost() {
		SyntacticTree tree = this;
		while (!tree.rightEmpty()) {
			tree = tree.rightChildren.get(0);
		}
		return tree;
	}

	public static class Iterator implements java.util.Iterator<SyntacticTree> {
		Iterator(SyntacticTree root, SyntacticTree node) {
			this.node = node;
			this.root = root;
			SyntacticTree ptr = this.node;
			while (ptr.isLeftChild(root)) {
				++left_parenthesis;
				ptr = ptr.parent;
			}

			if (node.rightEmpty()) {
				if (node.isRightChild(root) || node.isLeftChild(root))
					++right_parenthesis;
			}

		}

		SyntacticTree node;
		SyntacticTree root;
		boolean drapeau = true;
		int left_parenthesis = 0;
		int right_parenthesis = 0;

		@Override
		public boolean hasNext() {
			return drapeau;
		}

		public int left_parenthesis() {
			return left_parenthesis;
		}

		public int right_parenthesis() {
			return right_parenthesis;
		}

		@Override
		public SyntacticTree next() {
			SyntacticTree res = node;
			try {
				drapeau = move_forward();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return res;
		}

		public boolean move_left() {
			return move_down(true);
		}

		public boolean move_right() {
			return move_down(false);
		}

		public boolean move_down(boolean bLeft) {
			List<SyntacticTree> children = bLeft ? node.parent.leftChildren : node.parent.rightChildren;
			int i = children.indexOf(node);
			++i;
			if (i < children.size()) {
				move_leftmost(children.get(i));
				return true;
			}
			return false;
		}

		public void move_leftmost(SyntacticTree ptr) {
			++left_parenthesis;
			int level[] = { 0 };
			node = ptr.leftmost(level);

			left_parenthesis += level[0];
			if (node.rightEmpty()) {
				++right_parenthesis;
				if (level[0] == 0) {
					SyntacticTree node = this.node;
					while (node.isLastRightChild(root)) {
						++right_parenthesis;
						node = node.parent;
					}
				}
			}
		}

		public void ProcessLeftChild() {
			if (move_left())
				return;

			node = node.parent;

			if (node.rightEmpty()) {
				SyntacticTree node = this.node;
				for (;;) {
					if (node.isLeftChild(root)) {
						++right_parenthesis;
						break;
					}

					if (node.isRightChild(root)) {
						++right_parenthesis;

						if (node.isLastRightChild(root)) {
							node = node.parent;
							continue;
						} else
							break;
					} else
						break;
				}
			}
		}

		public boolean move_forward() throws Exception {
			left_parenthesis = 0;
			right_parenthesis = 0;

			if (node.rightEmpty()) {
				if (node.isLeftChild(root)) {
					ProcessLeftChild();
					return true;
				}

				if (node.isRightChild(root)) {
					for (;;) {
						if (move_right())
							return true;

						node = node.parent;
						if (node.isLeftChild(root)) {
							ProcessLeftChild();
							return true;
						}
						if (node.isRightChild(root)) {
							continue;
						}

						return false;
					}
				}

				return false;
			}

			move_leftmost(node.rightChildren.get(0));
			return true;
		}

		@Override
		public void remove() {

		}
	}

	@Override
	public Iterator iterator() {
		return new Iterator(this, this.leftmost());
	}

	static public List<SyntacticTree> clone(List<SyntacticTree> child) {
		List<SyntacticTree> arr = new ArrayList<SyntacticTree>();
		for (SyntacticTree tree : child) {
			arr.add(tree.clone());
		}
		return arr;
	}

	@Override
	public SyntacticTree clone() {
		SyntacticTree tree = new SyntacticTree(id, lex, pos, dep, clone(leftChildren), clone(rightChildren));
		tree.size = this.size;
		return tree;
	}

	public SyntacticTree clone(String dep) {
		SyntacticTree tree = new SyntacticTree(id, lex, pos, dep, clone(leftChildren), clone(rightChildren));
		tree.size = this.size;
		return tree;
	}

	public SyntacticTree searchNounPhrase() {
		if (pos.equals("NN")) {
			return this;
		}
		for (SyntacticTree tree : leftChildren) {
			SyntacticTree noun = tree.searchNounPhrase();
			if (noun != null)
				return noun;
		}
		for (SyntacticTree tree : rightChildren) {
			SyntacticTree noun = tree.searchNounPhrase();
			if (noun != null)
				return noun;
		}
		return null;
	}

	public SyntacticTree getSubject() {
		for (int i = 0; i < this.leftChildren.size(); ++i) {
			if (this.leftChildren.get(i).dep.equals("SUJ"))
				return this.leftChildren.get(i);
		}
		return null;
	}

	public SyntacticTree getObject() {
		for (int i = 0; i < this.rightChildren.size(); ++i) {
			if (this.rightChildren.get(i).dep.equals("OBJ"))
				return this.rightChildren.get(i);
		}
		return null;
	}

	public SyntacticTree getParticular(int id) {
		if (id > this.id)
			return getParticular(this.rightChildren, id);
		else if (id < this.id)
			return getParticular(this.leftChildren, id);
		else
			return this;
	}

	static public SyntacticTree getParticular(List<SyntacticTree> children, int id) {
		if (children.size() == 0) {
			return null;
		}
		int i;
		for (i = 0; i < children.size(); ++i) {
			if (id <= children.get(i).id) {
				SyntacticTree ret = children.get(i).getParticular(id);
				if (ret == null) {
					if (i > 0)
						return children.get(i - 1).getParticular(id);
					else
						return null;
				}
			}
		}
		return children.get(i - 1).getParticular(id);
	}

	public void removeClassScope(String classScope) {
		if (pos.equals("DE")) {
			if (this.leftChildren.get(0).unadornedExpression().equals(classScope)) {
				this.leftChildren.remove(0);
				return;
			}
		}

		for (SyntacticTree tree : this.leftChildren) {
			tree.removeClassScope(classScope);
		}
		for (SyntacticTree tree : this.rightChildren) {
			tree.removeClassScope(classScope);
		}
	}

	public void punctuated(String... punct) {
		for (String pu : punct) {
			rightChildren.add(new SyntacticTree(size, pu, "PU", "pu", this));
			++size;
		}
	}

	public void removePunctuationMark() {
		while (rightChildren.size() > 0 && "pu".equals(Utility.last(rightChildren).dep)) {
			rightChildren.remove(rightChildren.size() - 1);
			--this.size;
		}
	}

	public SyntacticTree removeRight(int j) {
		SyntacticTree kinder = rightChildren.remove(j);
		this.size -= kinder.size;
		this.validateIndex();
		return kinder;
	}

	public SyntacticTree removeLeft(int j) {
		SyntacticTree kinder = leftChildren.remove(j);
		this.size -= kinder.size;
		this.validateIndex();
		return kinder;
	}

	public boolean equals(SyntacticTree obj) {
		return Utility.equals(toHeadsArray(), obj.toHeadsArray()) && Utility.equals(this.getDEP(), obj.getDEP());
	}

	public void validateBranches() {
		for (SyntacticTree tree : leftChildren) {
			tree.parent = this;
		}
		for (SyntacticTree tree : rightChildren) {
			tree.parent = this;
		}
	}

	public SyntacticTree validateIndex() {
		int index = 0;
		for (SyntacticTree tree : this) {
			tree.id = index++;
		}
		if (index != this.size) {
			log.info("size = " + size);
			log.info("index = " + index);
			throw new RuntimeException("index != this.size");
		}
		return this;
	}

	public SyntacticTree validateIndex(int heads[]) {
		int index = 0;
		for (SyntacticTree tree : this) {
			tree.id = heads[index++];
		}

		if (index != this.size) {
			log.info("size = " + size);
			log.info("index = " + index);
			throw new RuntimeException("index != this.size");
		}
		return this;
	}

	public void preppend(String seg, String pos, String dep) {
		this.increaseIndex();
		leftChildren.add(0, new SyntacticTree(0, seg, pos, dep, this));
		++size;
	}

	void increaseIndex() {
		++id;
		for (SyntacticTree tree : leftChildren) {
			tree.increaseIndex();
		}
		for (SyntacticTree tree : rightChildren) {
			tree.increaseIndex();
		}
	}

	public void append(String seg, String pos, String dep) {
		rightChildren.add(new SyntacticTree(size, seg, pos, dep, this));
		++size;
	}

	/**
	 * used to create a binary tree version of the dependency tree
	 * 
	 * @author Cosmos
	 *
	 */

	public static class SplitStruct {
		public SplitStruct(String dep, SyntacticTree kinder, SyntacticTree parent) {
			this.dep = dep;
			this.kinder = kinder;
			this.parent = parent;
		}

		public String dep;
		public SyntacticTree kinder, parent;

		@Override
		public String toString() {
			String str = dep;
			str += "\n";
			str += kinder.toString();
			str += "\n";
			str += parent.toString();
			return str;
		}
	}

	SplitStruct splitStruct;

	public SplitStruct splitStruct(String dep) {
		int i = 0;
		int j = rightChildren.size() - 1;
		boolean bRight;

		for (; i < leftChildren.size(); ++i) {
			if (leftChildren.get(i).dep.equals(dep)) {
				break;
			}
		}

		if (i < leftChildren.size()) {
			bRight = false;
		} else {
			for (; j >= 0; --j) {
				if (rightChildren.get(j).dep.equals(dep)) {
					break;
				}

			}

			if (j >= 0) {
				bRight = true;
			} else {
				return null;
			}
		}

		SyntacticTree parent = this.clone();
		SyntacticTree kinder;
		if (bRight) {
			kinder = parent.removeRight(j);
		} else {
			kinder = parent.removeLeft(i);
		}

		return new SplitStruct(kinder.dep, kinder, parent);
	}

	void transformLabel(String oldLabel, String newLabel) {
		for (SyntacticTree tree : this) {
			if (tree.dep.equals(oldLabel)) {
				tree.dep = newLabel;
			}
		}
	}

	// double similarityDegree(SyntacticTree autre) {
	//
	// return id;
	// }
	//
	// double similarityDegree() {
	//
	// return id;
	// }
	//
	// SyntacticTree disolve(SyntacticTree autre) {
	//
	// return autre;
	// }
	//

	public static void main(String[] args) throws Exception {
	}

	public SyntacticTree adjust() throws Exception {
		if (!containsIrregulation())
			return this;

//		Utility.print(this);
		SyntacticTree tree = SyntacticParser.instance.parseWithAdjustment(getLEX(), getPOS());
		if (tree != null) {
//			Utility.print("after adjustment:\n", tree);
			return tree;
		}

		return this;
	}

	private static Logger log = Logger.getLogger(SyntacticTree.class);

	public boolean transform(Filter sift, int[] index) throws Exception {

		SyntacticTree[] arr = this.getSyntacticTree();
		boolean bAltered = false;
		for (int i : index) {
			SyntacticTree tree = arr[i];
			if (sift.transform(tree))
				bAltered = true;
		}

		return bAltered;
	}

	public SyntacticTree left(Filter sift, int[] index) throws Exception {

		SyntacticTree[] arr = this.getSyntacticTree();

		SyntacticTree root = null;
		for (int i : index) {
			SyntacticTree tree = arr[i];
			SyntacticTree _root = sift.left(tree);
			if (_root != null) {
				root = _root;
			}
		}

		if (root == null)
			root = this;
		root.setDEP(new String[root.size]);
		return SyntacticParser.instance.parse(root);
	}

	public SyntacticTree right(Filter sift, int[] index) throws Exception {

		SyntacticTree[] arr = this.getSyntacticTree();

		SyntacticTree root = null;
		for (int i : index) {
			SyntacticTree tree = arr[i];
			SyntacticTree _root = sift.right(tree);
			if (_root != null) {
				root = _root;
			}
		}

		if (root == null)
			root = this;
		root.setDEP(new String[root.size]);
		return SyntacticParser.instance.parse(root);
	}

	public SyntacticTree shift(Filter sift, int[] index) throws Exception {

		SyntacticTree[] arr = this.getSyntacticTree();
		for (int i : index) {
			SyntacticTree tree = arr[i];
			sift.shift(tree);
		}
		setDEP(new String[size]);
		return SyntacticParser.instance.parse(this);
	}

	public boolean transform(Lexeme... component) {
		boolean bAltered = false;
		String transformer = component[0] == null ? null : component[0].transformer();
		if (transformer != null)
			seg = transformer;

		transformer = component[1] == null ? null : component[1].transformer();
		if (transformer != null) {
			pos = transformer;
			bAltered = true;
		}

		transformer = component[2] == null ? null : component[2].transformer();
		if (transformer != null)
			dep = transformer;
		return bAltered;
	}

	public boolean transform(Lexeme component[][]) {
		boolean bAltered = false;
		for (Lexeme[] lexeme : component) {
			if (satisfy(lexeme)) {
				if (transform(lexeme))
					bAltered = true;
				return bAltered;
			}
		}

		return bAltered;
	}

	static public String transform(String exp, Lexeme component[]) {
		String[] arr = exp.split("/");
		String transformer = component[0] == null ? null : component[0].transformer();
		if (transformer != null)
			arr[0] = transformer;

		transformer = component[1] == null ? null : component[1].transformer();
		if (transformer != null)
			arr[1] = transformer;

		transformer = component[2] == null ? null : component[2].transformer();
		if (transformer != null)
			arr[2] = transformer;
		return String.join("/", arr);
	}

	public boolean satisfy(Lexeme... component) {
		return (component[0] == null || component[0].equals(seg))

				&& (component[1] == null || component[1].equals(pos))

				&& (component[2] == null || component[2].equals(dep));
	}

	public boolean satisfy(Lexeme component[][]) {
		for (Lexeme comp[] : component) {
			if (satisfy(comp)) {
				return true;
			}
		}
		return false;
	}

	public boolean satisfy(AnomalySyntacticTree infixTree) {
		return transform(infixTree, false);
	}

	public boolean transform(AnomalySyntacticTree infixTree) {
		boolean bool[] = { true, false };
		transform(infixTree, bool);
		return bool[1];
	}

	public boolean transform(AnomalySyntacticTree infixTree, boolean... bTransform) {
		if (!satisfy(infixTree.seg, infixTree.pos, infixTree.dep))
			return false;

		if (bTransform[0] && transform(infixTree.seg, infixTree.pos, infixTree.dep)) {
			bTransform[1] = true;
		}

		int i = 0, j = 0;
		while (i < this.leftChildren.size() && j < infixTree.leftChildren.size()) {
			if (leftChildren.get(i).transform(infixTree.leftChildren.get(j), bTransform)) {
				++i;
				++j;
			} else
				++i;

		}

		if (j < infixTree.leftChildren.size()) {
			return false;
		}

		i = 0;
		j = 0;
		while (i < rightChildren.size() && j < infixTree.rightChildren.size()) {
			if (rightChildren.get(i).transform(infixTree.rightChildren.get(j), bTransform)) {
				++j;
				++i;
			} else
				++i;
		}

		if (j < infixTree.rightChildren.size()) {
			return false;
		}

		return true;
	}

	public String simplifiedString() {
//		return Utility.removeEndOfSentencePunctuation(simplifiedExpression());
		return simplifiedExpression();
	}

}
