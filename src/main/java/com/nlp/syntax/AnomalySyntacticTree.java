package com.nlp.syntax;

import static com.util.Utility.last;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.nlp.syntax.AnomalyInspecter.Lexeme;
import com.util.Utility;

public class AnomalySyntacticTree implements Serializable, Iterable<AnomalySyntacticTree> {

	private static final long serialVersionUID = -4766669720074872942L;

	public Lexeme seg;
	public Lexeme pos;

	/**
	 * 原句中的顺序id
	 */
	public int id;
	public int size = 1;

	/**
	 * dependency relation
	 */
	public Lexeme dep;
	public List<AnomalySyntacticTree> leftChildren;
	public List<AnomalySyntacticTree> rightChildren;
	/**
	 * parent node
	 */
	public AnomalySyntacticTree parent = null;

	public AnomalySyntacticTree(int id) {
		this(id, null, null, null);
	}

	public AnomalySyntacticTree(int id, Lexeme word) {
		this(id, word, null, null);
	}

	// change
	public AnomalySyntacticTree(int id, Lexeme word, Lexeme pos) {
		this(id, word, pos, null);
	}

	public AnomalySyntacticTree(int id, Lexeme word, Lexeme pos, Lexeme depClass) {
		this.pos = pos;
		this.seg = word;
		this.id = id;
		this.dep = depClass;
		leftChildren = new ArrayList<AnomalySyntacticTree>();
		rightChildren = new ArrayList<AnomalySyntacticTree>();
	}

	public AnomalySyntacticTree(int id, Lexeme segOriginal, Lexeme word, Lexeme pos, Lexeme depClass) {
		this.seg = word;
		this.pos = pos;
		this.id = id;
		this.dep = depClass;
		leftChildren = new ArrayList<AnomalySyntacticTree>();
		rightChildren = new ArrayList<AnomalySyntacticTree>();
	}

	public AnomalySyntacticTree(int id, Lexeme word, Lexeme pos, Lexeme depClass, AnomalySyntacticTree parent) {
		this(id, word, pos, depClass);
		this.parent = parent;
	}

	public AnomalySyntacticTree(AnomalySyntacticTree tree) {
		AnomalySyntacticTree clone = tree.clone();
		this.pos = clone.pos;
		this.seg = clone.seg;
		this.id = clone.id;
		this.dep = clone.dep;
		leftChildren = clone.leftChildren;
		rightChildren = clone.rightChildren;
		this.validateBranches();
	}

	public AnomalySyntacticTree(int id, Lexeme word, Lexeme pos, Lexeme depClass,
			List<AnomalySyntacticTree> leftChildren, List<AnomalySyntacticTree> rightChildren) {
		this(id, word, pos, depClass);
		this.leftChildren = leftChildren;
		this.rightChildren = rightChildren;
		// size = 1;
		for (AnomalySyntacticTree tree : leftChildren) {
			tree.parent = this;
			// size += tree.size();
		}
		for (AnomalySyntacticTree tree : rightChildren) {
			tree.parent = this;
			// size += tree.size();
		}
	}

	// add the new node as a left child;
	public AnomalySyntacticTree addLeftChild(AnomalySyntacticTree ch) {
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

	public AnomalySyntacticTree addRightChild(AnomalySyntacticTree ch) {
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
	private void setParent(AnomalySyntacticTree tree) {
		parent = tree;
	}

	public AnomalySyntacticTree getParent() {
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
		for (AnomalySyntacticTree ch : leftChildren) {
			heads[ch.id] = id;
			ch.toHeadsArray(heads);
		}

		for (AnomalySyntacticTree ch : rightChildren) {
			heads[ch.id] = id;
			ch.toHeadsArray(heads);
		}
	}

	int evaluateSize() {
		int size = 1;
		for (AnomalySyntacticTree t : this.leftChildren) {
			size += t.evaluateSize();
		}
		for (AnomalySyntacticTree t : this.rightChildren) {
			size += t.evaluateSize();
		}
		return size;
	}

	public int validateSize() {
		size = 1;
		for (AnomalySyntacticTree tree : this.leftChildren) {
			size += tree.validateSize();
		}
		for (AnomalySyntacticTree tree : this.rightChildren) {
			if (tree == null) {
				System.out.println("tree = " + "is null");
			}
			size += tree.validateSize();
		}
		return size;
	}

	public boolean isValidate() {
//		for (AnomalySyntacticTree tree : this) {
//			if (!POSTagger.instance.tagSet().contains(tree.pos)) {
//				return false;
//			}
//			if (!SyntacticParser.instance.tagSet().contains(tree.dep)) {
//				return false;
//			}
//		}
//
		return true;
	}

	public List<AnomalySyntacticTree> getAllChild() {
		List<AnomalySyntacticTree> childs = new ArrayList<AnomalySyntacticTree>();
		childs.addAll(leftChildren);
		childs.addAll(rightChildren);
		return childs;
	}

	public boolean contain(AnomalySyntacticTree dt) {
		if (this.equals(dt))
			return true;
		for (AnomalySyntacticTree ch : leftChildren) {
			if (ch.contain(dt))
				return true;
		}
		for (AnomalySyntacticTree ch : rightChildren) {
			if (ch.contain(dt))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<List<Lexeme>> toList() {
		ArrayList<List<Lexeme>> lists = new ArrayList<List<Lexeme>>(size);
		for (int i = 0; i < size; i++) {
			lists.add(null);
		}
		toList(lists);
		return lists;
	}

	private void toList(ArrayList<List<Lexeme>> lists) {
		ArrayList<Lexeme> e = new ArrayList<Lexeme>();
		e.add(seg);
		e.add(pos);
		if (parent == null) {
			// e.add(String.valueOf(-1));
			// e.add("Root");
		} else {
			// e.add(String.valueOf(parent.id));
			// e.add(dep);
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

	public Lexeme[] getSEG() {
		ArrayList<Lexeme> arr = new ArrayList<Lexeme>();
		getTag(arr, TAG.Seg);
		return arr.toArray(new Lexeme[arr.size()]);
	}

	public Lexeme[] getPOS() {
		ArrayList<Lexeme> arr = new ArrayList<Lexeme>();
		getTag(arr, TAG.Pos);
		return arr.toArray(new Lexeme[arr.size()]);
	}

	public Lexeme[] getDEP() {
		ArrayList<Lexeme> arr = new ArrayList<Lexeme>();
		getTag(arr, TAG.Dep);
		return arr.toArray(new Lexeme[arr.size()]);
	}

	public void setDEP(Lexeme dep[]) {
		int i = 0;
		for (AnomalySyntacticTree t : this) {
			t.dep = dep[i++];
		}
	}

	public void setPOS(Lexeme pos[]) {
		int i = 0;
		for (AnomalySyntacticTree t : this) {
			t.pos = pos[i++];
		}
	}

	public AnomalySyntacticTree[] getSyntacticTree() {
		ArrayList<AnomalySyntacticTree> arr = new ArrayList<AnomalySyntacticTree>();
		getDependencyTree(arr);
		return arr.toArray(new AnomalySyntacticTree[arr.size()]);
	}

	/**
	 * 
	 * @param arr = infix traversal of the tree;
	 */
	public void getTag(ArrayList<Lexeme> arr, TAG tag) {
		for (AnomalySyntacticTree tree : this.leftChildren) {
			tree.getTag(arr, tag);
		}
		switch (tag) {
		case Seg:
			arr.add(seg);
			break;
		case Pos:
			arr.add(pos);
			break;
		case Dep:
			arr.add(dep);
			break;
		default:
			break;
		}

		for (AnomalySyntacticTree tree : this.rightChildren) {
			tree.getTag(arr, tag);
		}
	}

	public void getDependencyTree(ArrayList<AnomalySyntacticTree> arr) {
		for (AnomalySyntacticTree tree : this.leftChildren) {
			tree.getDependencyTree(arr);
		}
		arr.add(this);

		for (AnomalySyntacticTree tree : this.rightChildren) {
			tree.getDependencyTree(arr);
		}
	}

	void match(String relation, List<AnomalySyntacticTree> list) {
		if (this.dep != null && this.dep.equals(relation)) {
			list.add(this);
			return;
		}

		for (AnomalySyntacticTree e : this.leftChildren) {
			e.match(relation, list);
		}

		for (AnomalySyntacticTree e : this.rightChildren) {
			e.match(relation, list);
		}
	}

	public List<AnomalySyntacticTree> match(String relation) {
		List<AnomalySyntacticTree> list = new ArrayList<AnomalySyntacticTree>();
		match(relation, list);
		return list;
	}

	// if relation is null, it is a predicate;
	public Utility.TextTreeNode buildShadowTree() {
		// recursive inorder scan used to build the shadow tree
		// create the new shadow tree;
		Utility.TextTreeNode newNode = new Utility.TextTreeNode(Lexeme.toString(seg));
		// tree node
		if (leftChildren != null && leftChildren.size() > 0) {
			newNode.x = new Utility.TextTreeNode[leftChildren.size()];
			int i = 0;
			for (AnomalySyntacticTree tree : leftChildren)
				newNode.x[i++] = tree.buildShadowTree();
		}
		// allocate node for left child at next level in tree;

		if (rightChildren != null && rightChildren.size() > 0) {
			newNode.y = new Utility.TextTreeNode[rightChildren.size()];
			int i = 0;
			for (AnomalySyntacticTree tree : rightChildren)
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
		AnomalySyntacticTree parent = this.parent;
		this.parent = null;
		try {
			Lexeme[] pos = getPOS();
			Lexeme[] dep = getDEP();
			Lexeme[] seg = getSEG();

			int size = seg.length;
			if (size != pos.length || size != dep.length) {
				System.out.println("size != pos.length || size != dep.length");
			}

			int max_width = -1;
			for (int i = 0; i < size; ++i) {
				max_width = Utility.max(max_width, Utility.strlen(Lexeme.toString(seg[i])),
						Utility.strlen(Lexeme.toString(dep[i])), Utility.strlen(Lexeme.toString(pos[i])));
			}

			++max_width;

			AnomalySyntacticTree.Iterator it = iterator();

			int length_lexeme[] = new int[size];
			int left_parenthesis[] = new int[size];
			int right_parenthesis[] = new int[size];

			int index = 0;
			while (it.hasNext() && index < size) {
				left_parenthesis[index] = it.left_parenthesis();
				right_parenthesis[index] = it.right_parenthesis();

				if (!Lexeme.equals(seg[index], it.next().seg)) {
					System.out.println("inconsistent lexeme");
				}

				// seg[index] = protectParenthesis(seg[index]);
				length_lexeme[index] = Utility.strlen(Lexeme.toString(seg[index]));

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
				infix += Lexeme.toString(seg[index]);
				infix += Utility.toString(right_parenthesis[index], ')');
			}

			// if (!infix.replaceAll("\\s+", "").equals(this.infixExpression())) {
			// System.out.println("infixExpression error");
			// System.out.println(infix.replaceAll("\\s+", ""));
			// System.out.println(this.infixExpression());
			// }

			String segString = "";
			String posString = "";
			String depString = "";
			String markString = null;
			String[] marks = null;
			if (mark != null) {
				marks = Utility.errorMark(seg.length, mark);
				markString = "";
			}

			seg = getSEG();
			for (int i = 0; i < pos.length; ++i) {
				if (bSegInclusive)
					segString += Utility.toString(max_width - Utility.strlen(Lexeme.toString(seg[i])), ' ')
							+ Lexeme.toString(seg[i]);
				posString += Utility.toString(max_width - Utility.strlen(Lexeme.toString(pos[i])), ' ')
						+ Lexeme.toString(pos[i]);
				depString += Utility.toString(max_width - Utility.strlen(Lexeme.toString(dep[i])), ' ')
						+ Lexeme.toString(dep[i]);
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

	public String toStringNonHierarchical() {
		String tree = null;
		AnomalySyntacticTree parent = this.parent;
		this.parent = null;
		try {
			Lexeme[] pos = getPOS();
			Lexeme[] dep = getDEP();
			Lexeme[] seg = getSEG();

			int size = seg.length;
			if (size != pos.length || size != dep.length) {
				System.out.println("size != pos.length || size != dep.length");
			}

			int max_width = -1;
			for (int i = 0; i < size; ++i) {
				max_width = Utility.max(max_width, Utility.strlen(seg[i].toString()), Utility.strlen(dep[i].toString()),
						Utility.strlen(pos[i].toString()));
			}

			++max_width;

			AnomalySyntacticTree.Iterator it = iterator();

			int length_lexeme[] = new int[size];
			int left_parenthesis[] = new int[size];
			int right_parenthesis[] = new int[size];

			int index = 0;
			while (it.hasNext() && index < size) {
				left_parenthesis[index] = it.left_parenthesis();
				right_parenthesis[index] = it.right_parenthesis();

				if (!seg[index].equals(it.next().seg)) {
					System.out.println("inconsistent lexeme");
				}

				// seg[index] = protectParenthesis(seg[index]);
				length_lexeme[index] = Utility.strlen(seg[index].toString());

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
				posString += Utility.toString(max_width - Utility.strlen(pos[i].toString()), ' ') + pos[i];
				depString += Utility.toString(max_width - Utility.strlen(dep[i].toString()), ' ') + dep[i];
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
		StringBuilder infix = new StringBuilder();
		for (AnomalySyntacticTree tree : this.leftChildren) {
			infix.append("(" + tree.infixExpression() + ")");
		}

		ArrayList<String> list = new ArrayList<String>(3);
		if (seg != null)
			list.add(seg.toString());
		if (pos != null)
			list.add(pos.toString());
		if (dep != null)
			list.add(dep.toString());

		infix.append(String.join("/", list));

		for (AnomalySyntacticTree tree : this.rightChildren) {
			infix.append("(" + tree.infixExpression() + ")");
		}
		return infix.toString();
	}

	public String unadornedExpression() {
		String infix = "";
		for (AnomalySyntacticTree tree : this.leftChildren) {
			infix += tree.unadornedExpression();
		}
		// infix += lex;
		for (AnomalySyntacticTree tree : this.rightChildren) {
			infix += tree.unadornedExpression();
		}
		return infix;
	}

	public String simplifiedExpression() {
		String infix = "";
		for (AnomalySyntacticTree tree : this.leftChildren) {
			infix += tree.simplifiedExpression();
		}
		infix += seg;
		for (AnomalySyntacticTree tree : this.rightChildren) {
			infix += tree.simplifiedExpression();
		}
		return infix;
	}

	public void questionConstituent(HashSet<AnomalySyntacticTree> treeSet) {
		if (this.pos.equals("QUE")) {
			treeSet.add(this);
		}
		for (AnomalySyntacticTree tree : this.leftChildren) {
			tree.questionConstituent(treeSet);
		}
		for (AnomalySyntacticTree tree : this.rightChildren) {
			tree.questionConstituent(treeSet);
		}
	}

	/**
	 * extract the interrogatives of the sentence.
	 * 
	 * @return
	 */
	public HashSet<AnomalySyntacticTree> questionConstituent() {
		HashSet<AnomalySyntacticTree> treeSet = new HashSet<AnomalySyntacticTree>();
		questionConstituent(treeSet);
		return treeSet;
	}

	public void negConstituent(HashSet<AnomalySyntacticTree> treeSet) {
		if (this.pos.equals("NEG")) {
			treeSet.add(this);
		}
		for (AnomalySyntacticTree tree : this.leftChildren) {
			tree.negConstituent(treeSet);
		}
		for (AnomalySyntacticTree tree : this.rightChildren) {
			tree.negConstituent(treeSet);
		}
	}

	public HashSet<AnomalySyntacticTree> negConstituent() {
		HashSet<AnomalySyntacticTree> treeSet = new HashSet<AnomalySyntacticTree>();
		negConstituent(treeSet);
		return treeSet;
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

	public boolean isLeftChild(AnomalySyntacticTree root) {
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

	public boolean isRightChild(AnomalySyntacticTree root) {
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

	boolean isLastRightChild(AnomalySyntacticTree root) {
		if (parent == null || parent.rightEmpty() || this == root) {
			return false;
		}
		return parent.parent != null && parent.rightChildren.indexOf(this) == parent.rightChildren.size() - 1;
	}

	AnomalySyntacticTree leftmost(int... hierarchy) {
		AnomalySyntacticTree tree = this;
		while (!tree.leftEmpty()) {
			tree = tree.leftChildren.get(0);
			++hierarchy[0];
		}
		return tree;
	}

	AnomalySyntacticTree leftmost() {
		AnomalySyntacticTree tree = this;
		while (!tree.leftEmpty()) {
			tree = tree.leftChildren.get(0);
		}
		return tree;
	}

	AnomalySyntacticTree rightmost() {
		AnomalySyntacticTree tree = this;
		while (!tree.rightEmpty()) {
			tree = tree.rightChildren.get(0);
		}
		return tree;
	}

	public static class Iterator implements java.util.Iterator<AnomalySyntacticTree> {
		Iterator(AnomalySyntacticTree root, AnomalySyntacticTree node) {
			this.node = node;
			this.root = root;
			AnomalySyntacticTree ptr = this.node;
			while (ptr.isLeftChild(root)) {
				++left_parenthesis;
				ptr = ptr.parent;
			}

			if (node.rightEmpty()) {
				if (node.isRightChild(root) || node.isLeftChild(root))
					++right_parenthesis;
			}

		}

		AnomalySyntacticTree node;
		AnomalySyntacticTree root;
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
		public AnomalySyntacticTree next() {
			AnomalySyntacticTree res = node;
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
			List<AnomalySyntacticTree> children = bLeft ? node.parent.leftChildren : node.parent.rightChildren;
			int i = children.indexOf(node);
			++i;
			if (i < children.size()) {
				move_leftmost(children.get(i));
				return true;
			}
			return false;
		}

		public void move_leftmost(AnomalySyntacticTree ptr) {
			++left_parenthesis;
			int level[] = { 0 };
			node = ptr.leftmost(level);

			left_parenthesis += level[0];
			if (node.rightEmpty()) {
				++right_parenthesis;
				if (level[0] == 0) {
					AnomalySyntacticTree node = this.node;
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
				AnomalySyntacticTree node = this.node;
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

	static public List<AnomalySyntacticTree> clone(List<AnomalySyntacticTree> child) {
		List<AnomalySyntacticTree> arr = new ArrayList<AnomalySyntacticTree>();
		for (AnomalySyntacticTree tree : child) {
			arr.add(tree.clone());
		}
		return arr;
	}

	@Override
	public AnomalySyntacticTree clone() {
		AnomalySyntacticTree tree = new AnomalySyntacticTree(id, seg, pos, dep, clone(leftChildren),
				clone(rightChildren));
		tree.size = this.size;
		return tree;
	}

	public AnomalySyntacticTree clone(Lexeme dep) {
		AnomalySyntacticTree tree = new AnomalySyntacticTree(id, seg, pos, dep, clone(leftChildren),
				clone(rightChildren));
		tree.size = this.size;
		return tree;
	}

	public AnomalySyntacticTree searchNounPhrase() {
		if (pos.equals("NN")) {
			return this;
		}
		for (AnomalySyntacticTree tree : leftChildren) {
			AnomalySyntacticTree noun = tree.searchNounPhrase();
			if (noun != null)
				return noun;
		}
		for (AnomalySyntacticTree tree : rightChildren) {
			AnomalySyntacticTree noun = tree.searchNounPhrase();
			if (noun != null)
				return noun;
		}
		return null;
	}

	public AnomalySyntacticTree getSubject() {
		for (int i = 0; i < this.leftChildren.size(); ++i) {
			if (this.leftChildren.get(i).dep.equals("SUJ"))
				return this.leftChildren.get(i);
		}
		return null;
	}

	public AnomalySyntacticTree getObject() {
		for (int i = 0; i < this.rightChildren.size(); ++i) {
			if (this.rightChildren.get(i).dep.equals("OBJ"))
				return this.rightChildren.get(i);
		}
		return null;
	}

	public AnomalySyntacticTree getParticular(int id) {
		if (id > this.id)
			return getParticular(this.rightChildren, id);
		else if (id < this.id)
			return getParticular(this.leftChildren, id);
		else
			return this;
	}

	static public AnomalySyntacticTree getParticular(List<AnomalySyntacticTree> children, int id) {
		if (children.size() == 0) {
			return null;
		}
		int i;
		for (i = 0; i < children.size(); ++i) {
			if (id <= children.get(i).id) {
				AnomalySyntacticTree ret = children.get(i).getParticular(id);
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

		for (AnomalySyntacticTree tree : this.leftChildren) {
			tree.removeClassScope(classScope);
		}
		for (AnomalySyntacticTree tree : this.rightChildren) {
			tree.removeClassScope(classScope);
		}
	}

	public void removePunctuationMark() {
		while (rightChildren.size() > 0 && "pu".equals(last(rightChildren).dep.toString())) {
			rightChildren.remove(rightChildren.size() - 1);
			--this.size;
		}
	}

	public AnomalySyntacticTree removeRight(int j) {
		AnomalySyntacticTree kinder = rightChildren.remove(j);
		this.size -= kinder.size;
		this.validateIndex();
		return kinder;
	}

	public AnomalySyntacticTree removeLeft(int j) {
		AnomalySyntacticTree kinder = leftChildren.remove(j);
		this.size -= kinder.size;
		this.validateIndex();
		return kinder;
	}

	public boolean equals(AnomalySyntacticTree obj) {
		return Utility.equals(clone().validateIndex().toHeadsArray(), obj.clone().validateIndex().toHeadsArray())
				&& Utility.equals(this.getDEP(), obj.getDEP());
	}

	public void validateBranches() {
		for (AnomalySyntacticTree tree : leftChildren) {
			tree.parent = this;
		}
		for (AnomalySyntacticTree tree : rightChildren) {
			tree.parent = this;
		}
	}

	public AnomalySyntacticTree validateIndex() {
		int index = 0;
		for (AnomalySyntacticTree tree : this) {
			tree.id = index++;
		}
		if (index != this.size) {
			log.info("size = " + size);
			log.info("index = " + index);
			throw new RuntimeException("index != this.size");
		}
		return this;
	}

	void increaseIndex() {
		++id;
		for (AnomalySyntacticTree tree : leftChildren) {
			tree.increaseIndex();
		}
		for (AnomalySyntacticTree tree : rightChildren) {
			tree.increaseIndex();
		}
	}

	/**
	 * used to create a binary tree version of the dependency tree
	 * 
	 * @author Cosmos
	 *
	 */

	public static class SplitStruct {
		public SplitStruct(String dep, AnomalySyntacticTree kinder, AnomalySyntacticTree parent) {
			this.dep = dep;
			this.kinder = kinder;
			this.parent = parent;
		}

		public String dep;
		public AnomalySyntacticTree kinder, parent;

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

	public static void main(String[] args) throws Exception {
	}

	private static Logger log = Logger.getLogger(AnomalySyntacticTree.class);

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

	public boolean satisfy(Lexeme component[]) {
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

	public static abstract class Action {

		public abstract AnomalySyntacticTree invoke(AnomalySyntacticTree tree);

		public abstract SyntacticTree invoke(SyntacticTree tree);
	}

	public static class ActionRightToRSibling extends Action {
		int rightIndex;
		int siblingIndex;
		AnomalySyntacticTree criteria;

		public ActionRightToRSibling(int id, int sibling, AnomalySyntacticTree criteria) {
			this.rightIndex = id;
			this.siblingIndex = sibling;
			this.criteria = criteria;
		}

		public AnomalySyntacticTree invoke(AnomalySyntacticTree tree) {
			AnomalySyntacticTree right = tree.rightChildren.get(rightIndex);

			ArrayList<AnomalySyntacticTree> list = new ArrayList<AnomalySyntacticTree>();
			while (siblingIndex < right.rightChildren.size()) {
				list.add(right.rightChildren.remove(siblingIndex));
			}

			tree.rightChildren.addAll(rightIndex + 1, list);

			right.validateSize();

			return tree;
		}

		public SyntacticTree invoke(SyntacticTree tree) {
			AnomalySyntacticTree _right = criteria.rightChildren.get(rightIndex);
			AnomalySyntacticTree _sibling = _right.rightChildren.get(siblingIndex);

			int rightIndex = -1;
			int siblingIndex = -1;
			SyntacticTree right = null;
			SyntacticTree sibling = null;
			for (int i = 0; i < tree.rightChildren.size(); ++i) {
				if (right == null && tree.rightChildren.get(i).satisfy(_right)) {
					right = tree.rightChildren.get(i);
					rightIndex = i;
					for (int j = 0; j < right.rightChildren.size(); ++j) {
						if (sibling == null && right.rightChildren.get(j).satisfy(_sibling)) {
							sibling = right.rightChildren.get(j);
							siblingIndex = j;
							break;
						}
					}

					if (sibling == null) {
						right = null;
						rightIndex = -1;
					} else
						break;
				}
			}

			assert rightIndex >= 0 && siblingIndex >= 0;

			ArrayList<SyntacticTree> list = new ArrayList<SyntacticTree>();
			while (siblingIndex < right.rightChildren.size()) {
				SyntacticTree t = right.rightChildren.remove(siblingIndex);
				t.parent = tree;
				list.add(t);
			}

			tree.rightChildren.addAll(rightIndex + 1, list);

			right.validateSize();

			return tree;
		}

	}

	public static class ActionLeft extends Action {
		int leftIndex;
		AnomalySyntacticTree criteria;

		public ActionLeft(int id, AnomalySyntacticTree criteria) {
			this.leftIndex = id;
			this.criteria = criteria;
		}

		public AnomalySyntacticTree invoke(AnomalySyntacticTree tree) {
			AnomalySyntacticTree left = tree.leftChildren.get(leftIndex);
			left.validateIndex();
			return left;
		}

		public SyntacticTree invoke(SyntacticTree tree) {
			AnomalySyntacticTree _left = criteria.leftChildren.get(leftIndex);

			int leftIndex = -1;
			SyntacticTree left = null;

			for (int i = 0; i < tree.leftChildren.size(); ++i) {
				if (left == null && tree.leftChildren.get(i).satisfy(_left)) {
					left = tree.leftChildren.get(i);
					leftIndex = i;
					break;
				}
			}

			assert leftIndex >= 0;

			return tree.leftChildren.get(leftIndex);
		}

	}

	public static class ActionRight extends Action {
		int rightIndex;
		AnomalySyntacticTree criteria;

		public ActionRight(int id, AnomalySyntacticTree criteria) {
			this.rightIndex = id;
			this.criteria = criteria;
		}

		public AnomalySyntacticTree invoke(AnomalySyntacticTree tree) {
			AnomalySyntacticTree right = tree.rightChildren.get(rightIndex);
			right.validateIndex();
			return right;
		}

		public SyntacticTree invoke(SyntacticTree tree) {
			AnomalySyntacticTree _right = criteria.rightChildren.get(rightIndex);

			int rightIndex = -1;
			SyntacticTree right = null;

			for (int i = 0; i < tree.rightChildren.size(); ++i) {
				if (right == null && tree.rightChildren.get(i).satisfy(_right)) {
					right = tree.rightChildren.get(i);
					rightIndex = i;
					break;
				}
			}

			assert rightIndex >= 0;

			return tree.rightChildren.get(rightIndex);
		}

	}

	public static class ActionLeftToLSibling extends Action {
		int leftIndex;
		int siblingIndex;
		AnomalySyntacticTree criteria;

		public ActionLeftToLSibling(int id, int sibling, AnomalySyntacticTree criteria) {
			this.leftIndex = id;
			this.siblingIndex = sibling;
			this.criteria = criteria;
		}

		public AnomalySyntacticTree invoke(AnomalySyntacticTree tree) {
			AnomalySyntacticTree left = tree.leftChildren.get(leftIndex);

			ArrayList<AnomalySyntacticTree> list = new ArrayList<AnomalySyntacticTree>();
			for (int i = 0; i <= siblingIndex; ++i) {
				list.add(left.leftChildren.remove(0));
			}

			tree.leftChildren.addAll(leftIndex + 1, list);

			left.validateSize();

			return tree;
		}

		public SyntacticTree invoke(SyntacticTree tree) {
			AnomalySyntacticTree _left = criteria.leftChildren.get(leftIndex);
			AnomalySyntacticTree _sibling = _left.leftChildren.get(siblingIndex);

			int leftIndex = -1;
			int siblingIndex = -1;
			SyntacticTree left = null;
			SyntacticTree sibling = null;
			for (int i = 0; i < tree.leftChildren.size(); ++i) {
				if (left == null && tree.leftChildren.get(i).satisfy(_left)) {
					left = tree.leftChildren.get(i);
					leftIndex = i;
					for (int j = 0; j < left.leftChildren.size(); ++j) {
						if (sibling == null && left.leftChildren.get(j).satisfy(_sibling)) {
							sibling = left.leftChildren.get(j);
							siblingIndex = j;
							break;
						}
					}

					if (sibling == null) {
						left = null;
						leftIndex = -1;
					} else
						break;
				}
			}

			assert leftIndex >= 0 && siblingIndex >= 0;

			ArrayList<SyntacticTree> list = new ArrayList<SyntacticTree>();
			for (int i = 0; i <= siblingIndex; ++i) {
				SyntacticTree t = left.leftChildren.remove(0);
				t.parent = tree;
				list.add(t);
			}

			tree.leftChildren.addAll(leftIndex, list);

			left.validateSize();

			return tree;
		}

	}

	public static class ActionLeftToRSibling extends Action {
		int rightIndex;
		int siblingIndex;
		AnomalySyntacticTree criteria;

		public ActionLeftToRSibling(int id, int sibling, AnomalySyntacticTree criteria) {
			this.rightIndex = id;
			this.siblingIndex = sibling;
			this.criteria = criteria;
		}

		public AnomalySyntacticTree invoke(AnomalySyntacticTree tree) {
			AnomalySyntacticTree right = tree.rightChildren.get(rightIndex);

			ArrayList<AnomalySyntacticTree> list = new ArrayList<AnomalySyntacticTree>();
			for (int i = 0; i <= siblingIndex; ++i) {
				list.add(right.leftChildren.remove(0));
			}

			tree.rightChildren.addAll(rightIndex + 1, list);

			right.validateSize();

			return tree;
		}

		public SyntacticTree invoke(SyntacticTree tree) {
			AnomalySyntacticTree _right = criteria.rightChildren.get(rightIndex);
			AnomalySyntacticTree _sibling = _right.leftChildren.get(siblingIndex);

			int rightIndex = -1;
			int siblingIndex = -1;
			SyntacticTree right = null;
			SyntacticTree sibling = null;
			for (int i = 0; i < tree.rightChildren.size(); ++i) {
				if (right == null && tree.rightChildren.get(i).satisfy(_right)) {
					right = tree.rightChildren.get(i);
					rightIndex = i;
					for (int j = 0; j < right.leftChildren.size(); ++j) {
						if (sibling == null && right.leftChildren.get(j).satisfy(_sibling)) {
							sibling = right.leftChildren.get(j);
							siblingIndex = j;
							break;
						}
					}

					if (sibling == null) {
						right = null;
						rightIndex = -1;
					} else
						break;
				}
			}

			assert rightIndex >= 0 && siblingIndex >= 0;

			ArrayList<SyntacticTree> list = new ArrayList<SyntacticTree>();
			for (int i = 0; i <= siblingIndex; ++i) {
				SyntacticTree t = right.leftChildren.remove(0);
				t.parent = tree;
				list.add(t);
			}

			tree.rightChildren.addAll(rightIndex, list);

			right.validateSize();

			return tree;
		}

	}

	public static class ActionRightToLSibling extends Action {
		int leftIndex;
		int siblingIndex;
		AnomalySyntacticTree criteria;

		public ActionRightToLSibling(int id, int sibling, AnomalySyntacticTree criteria) {
			this.leftIndex = id;
			this.siblingIndex = sibling;
			this.criteria = criteria;
		}

		public AnomalySyntacticTree invoke(AnomalySyntacticTree tree) {
			AnomalySyntacticTree left = tree.leftChildren.get(leftIndex);

			ArrayList<AnomalySyntacticTree> list = new ArrayList<AnomalySyntacticTree>();
			while (siblingIndex < left.rightChildren.size()) {
				list.add(left.rightChildren.remove(siblingIndex));
			}

			tree.leftChildren.addAll(leftIndex + 1, list);

			left.validateSize();

			return tree;
		}

		public SyntacticTree invoke(SyntacticTree tree) {
			AnomalySyntacticTree _left = criteria.leftChildren.get(leftIndex);
			AnomalySyntacticTree _sibling = _left.rightChildren.get(siblingIndex);

			int leftIndex = -1;
			int siblingIndex = -1;
			SyntacticTree left = null;
			SyntacticTree sibling = null;
			for (int i = 0; i < tree.leftChildren.size(); ++i) {
				if (left == null && tree.leftChildren.get(i).satisfy(_left)) {
					left = tree.leftChildren.get(i);
					leftIndex = i;
					for (int j = 0; j < left.rightChildren.size(); ++j) {
						if (sibling == null && left.rightChildren.get(j).satisfy(_sibling)) {
							sibling = left.rightChildren.get(j);
							siblingIndex = j;
							break;
						}
					}

					if (sibling == null) {
						left = null;
						leftIndex = -1;
					} else
						break;
				}
			}

			assert leftIndex >= 0 && siblingIndex >= 0;

			ArrayList<SyntacticTree> list = new ArrayList<SyntacticTree>();
			while (siblingIndex < left.rightChildren.size()) {
				SyntacticTree t = left.rightChildren.remove(siblingIndex);
				t.parent = tree;
				list.add(t);
			}

			tree.leftChildren.addAll(leftIndex + 1, list);

			left.validateSize();

			return tree;
		}
	}

	public static class ActionRightToLeft extends Action {
		int rightIndex;

		AnomalySyntacticTree criteria;

		public ActionRightToLeft(int id, AnomalySyntacticTree criteria) {
			this.rightIndex = id;
			this.criteria = criteria;
		}

		public AnomalySyntacticTree invoke(AnomalySyntacticTree tree) {
			AnomalySyntacticTree root = tree.parent;
			ArrayList<AnomalySyntacticTree> res = new ArrayList<AnomalySyntacticTree>();
			AnomalySyntacticTree t = tree.rightChildren.get(rightIndex);

			for (; rightIndex < tree.rightChildren.size();) {
				AnomalySyntacticTree sibling = tree.rightChildren.remove(rightIndex);
				res.add(sibling);

				sibling.parent = root;
			}

			tree.parent = t;
			t.leftChildren.add(0, tree);

			int i;
			if (root == null) {
				for (i = 1; i < res.size(); ++i) {
					res.get(i).parent = t;
					t.rightChildren.add(res.get(i));
				}
				t.validateSize();
				return t;
			}

			t.validateSize();
			i = root.rightChildren.indexOf(tree);
			if (i >= 0) {
				root.rightChildren.remove(i);
				root.rightChildren.addAll(i, res);
				return null;
			}

			i = root.leftChildren.indexOf(tree);

			if (i >= 0) {
				root.leftChildren.remove(i);
				root.leftChildren.addAll(i, res);
				return null;
			}
			return null;

		}

		public SyntacticTree invoke(SyntacticTree tree) {
			AnomalySyntacticTree _right = criteria.rightChildren.get(rightIndex);

			int rightIndex = -1;
			for (int i = 0; i < tree.rightChildren.size(); ++i) {
				if (tree.rightChildren.get(i).satisfy(_right)) {
					rightIndex = i;
					break;
				}
			}

			assert rightIndex >= 0;

			SyntacticTree root = tree.parent;
			ArrayList<SyntacticTree> res = new ArrayList<SyntacticTree>();
			SyntacticTree right = tree.rightChildren.get(rightIndex);

			for (; rightIndex < tree.rightChildren.size();) {
				SyntacticTree sibling = tree.rightChildren.remove(rightIndex);
				res.add(sibling);

				sibling.parent = root;
			}

			tree.parent = right;
			right.leftChildren.add(0, tree);

			int i;
			if (root == null) {
				for (i = 1; i < res.size(); ++i) {
					res.get(i).parent = right;
					right.rightChildren.add(res.get(i));
				}
				right.validateSize();
				return right;
			}

			right.validateSize();
			i = root.rightChildren.indexOf(tree);
			if (i >= 0) {
				root.rightChildren.remove(i);
				root.rightChildren.addAll(i, res);
				return right;
			}

			i = root.leftChildren.indexOf(tree);

			if (i >= 0) {
				root.leftChildren.remove(i);
				root.leftChildren.addAll(i, res);
				return right;
			}
			return right;
		}
	}

	public static class ActionRSiblingToRight extends Action {
		int id;
		int bruder;
		AnomalySyntacticTree criteria;

		public ActionRSiblingToRight(int id, int bruder, AnomalySyntacticTree criteria) {
			this.id = id;
			this.bruder = bruder;
			this.criteria = criteria;
		}

		public AnomalySyntacticTree invoke(AnomalySyntacticTree tree) {
			AnomalySyntacticTree parent = tree.rightChildren.get(id);
			for (int i = id + 1; i <= bruder; ++i) {
				AnomalySyntacticTree t = tree.rightChildren.get(id + 1);
				t.parent = parent;
				parent.rightChildren.add(t);
				tree.rightChildren.remove(id + 1);
			}

			parent.validateSize();
			return tree;
		}

		public SyntacticTree invoke(SyntacticTree tree) {
			AnomalySyntacticTree _parent = criteria.rightChildren.get(id);
			AnomalySyntacticTree _kinder = criteria.rightChildren.get(bruder);

			SyntacticTree parent = null;
			SyntacticTree kinder = null;
			int parentIndex = -1;
			int kinderIndex = -1;
			for (int i = 0; i < tree.rightChildren.size(); ++i) {
				SyntacticTree t = tree.rightChildren.get(i);
				if (parent == null && t.satisfy(_parent)) {
					parent = t;
					parentIndex = i;

					for (int j = i + 1; j < tree.rightChildren.size(); ++j) {
						t = tree.rightChildren.get(j);
						if (t.satisfy(_kinder)) {
							kinder = t;
							kinderIndex = j;
							break;
						}
					}
					if (kinder == null) {
						parent = null;
						parentIndex = -1;
					} else
						break;
				}
			}

			assert parentIndex >= 0 && kinderIndex > parentIndex;

			for (int i = parentIndex + 1; i <= kinderIndex; ++i) {
				SyntacticTree t = tree.rightChildren.get(parentIndex + 1);
				t.parent = parent;
				parent.rightChildren.add(t);
				tree.rightChildren.remove(parentIndex + 1);
			}

			parent.validateSize();
			return tree;
		}
	}

	public static class ActionLSiblingToRight extends Action {
		int id;
		int bruder;
		AnomalySyntacticTree criteria;

		public ActionLSiblingToRight(int id, int bruder, AnomalySyntacticTree criteria) {
			this.id = id;
			this.bruder = bruder;
			this.criteria = criteria;
		}

		public AnomalySyntacticTree invoke(AnomalySyntacticTree tree) {
			AnomalySyntacticTree parent = tree.leftChildren.get(id);
			for (int i = id + 1; i <= bruder; ++i) {
				AnomalySyntacticTree t = tree.leftChildren.get(id + 1);
				t.parent = parent;
				parent.rightChildren.add(t);
				tree.leftChildren.remove(id + 1);
			}

			parent.validateSize();
			return tree;
		}

		public SyntacticTree invoke(SyntacticTree tree) {
			AnomalySyntacticTree _parent = criteria.leftChildren.get(id);
			AnomalySyntacticTree _kinder = criteria.leftChildren.get(bruder);

			SyntacticTree parent = null;
			SyntacticTree kinder = null;
			int parentIndex = -1;
			int kinderIndex = -1;
			for (int i = 0; i < tree.leftChildren.size(); ++i) {
				SyntacticTree t = tree.leftChildren.get(i);
				if (parent == null && t.satisfy(_parent)) {
					parent = t;
					parentIndex = i;

					for (int j = i + 1; j < tree.leftChildren.size(); ++j) {
						t = tree.leftChildren.get(j);
						if (t.satisfy(_kinder)) {
							kinder = t;
							kinderIndex = j;
							break;
						}
					}
					if (kinder == null) {
						parent = null;
						parentIndex = -1;
					} else
						break;
				}
			}

			assert parentIndex >= 0 && kinderIndex > parentIndex;

			for (int i = parentIndex + 1; i <= kinderIndex; ++i) {
				SyntacticTree t = tree.leftChildren.get(parentIndex + 1);
				t.parent = parent;
				parent.rightChildren.add(t);
				tree.leftChildren.remove(parentIndex + 1);
			}

			parent.validateSize();
			return tree;
		}
	}

	public static class ActionLSiblingToLeft extends Action {
		int parentIndex;
		int leftIndex;
		AnomalySyntacticTree criteria;

		public ActionLSiblingToLeft(int id, int bruder, AnomalySyntacticTree criteria) {
			this.parentIndex = id;
			this.leftIndex = bruder;
			this.criteria = criteria;
		}

		public AnomalySyntacticTree invoke(AnomalySyntacticTree tree) {
			AnomalySyntacticTree parent = tree.leftChildren.get(parentIndex);
			for (int i = leftIndex; i < parentIndex; ++i) {
				AnomalySyntacticTree t = tree.leftChildren.get(leftIndex);
				t.parent = parent;
				parent.leftChildren.add(i - leftIndex, t);
				tree.leftChildren.remove(leftIndex);
			}

			parent.validateSize();
			return tree;
		}

		public SyntacticTree invoke(SyntacticTree tree) {
			AnomalySyntacticTree _parent = criteria.leftChildren.get(parentIndex);
			AnomalySyntacticTree _kinder = criteria.leftChildren.get(leftIndex);

			SyntacticTree parent = null;
			SyntacticTree kinder = null;
			int parentIndex = -1;
			int leftIndex = -1;
			for (int i = 0; i < tree.leftChildren.size(); ++i) {
				SyntacticTree t = tree.leftChildren.get(i);
				if (kinder == null && t.satisfy(_kinder)) {
					kinder = t;
					leftIndex = i;

					for (int j = i + 1; j < tree.leftChildren.size(); ++j) {
						t = tree.leftChildren.get(j);
						if (t.satisfy(_parent)) {
							parent = t;
							parentIndex = j;
							break;
						}
					}
					if (parent == null) {
						kinder = null;
						leftIndex = -1;
					} else
						break;
				}
			}

			assert parentIndex >= 0 && leftIndex < parentIndex;

			for (int i = leftIndex; i < parentIndex; ++i) {
				SyntacticTree t = tree.leftChildren.get(leftIndex);
				t.parent = parent;
				parent.leftChildren.add(i - leftIndex, t);
				tree.leftChildren.remove(leftIndex);
			}

			parent.validateSize();
			return tree;
		}
	}

	public static class ActionRSiblingToLeft extends Action {
		int id;
		int bruder;
		AnomalySyntacticTree criteria;

		public ActionRSiblingToLeft(int id, int bruder, AnomalySyntacticTree criteria) {
			this.id = id;
			this.bruder = bruder;
			this.criteria = criteria;
		}

		public AnomalySyntacticTree invoke(AnomalySyntacticTree tree) {
			AnomalySyntacticTree parent = tree.rightChildren.get(id);
			for (int i = bruder; i < id; ++i) {
				AnomalySyntacticTree t = tree.rightChildren.get(bruder);
				t.parent = parent;
				parent.leftChildren.add(i - bruder, t);
				tree.rightChildren.remove(bruder);
			}

			parent.validateSize();
			return tree;
		}

		public SyntacticTree invoke(SyntacticTree tree) {
			AnomalySyntacticTree _parent = criteria.rightChildren.get(id);
			AnomalySyntacticTree _kinder = criteria.rightChildren.get(bruder);

			SyntacticTree parent = null;
			SyntacticTree kinder = null;
			int parentIndex = -1;
			int kinderIndex = -1;
			for (int i = 0; i < tree.rightChildren.size(); ++i) {
				SyntacticTree t = tree.rightChildren.get(i);
				if (kinder == null && t.satisfy(_kinder)) {
					kinder = t;
					kinderIndex = i;

					for (int j = i + 1; j < tree.rightChildren.size(); ++j) {
						t = tree.rightChildren.get(j);
						if (t.satisfy(_parent)) {
							parent = t;
							parentIndex = j;
							break;
						}
					}
					if (parent == null) {
						kinder = null;
						kinderIndex = -1;
					} else
						break;
				}
			}

			assert parentIndex >= 0 && kinderIndex < parentIndex;

			for (int i = kinderIndex; i < parentIndex; ++i) {
				SyntacticTree t = tree.rightChildren.get(kinderIndex);
				t.parent = parent;
				parent.leftChildren.add(i - kinderIndex, t);
				tree.rightChildren.remove(kinderIndex);
			}

			parent.validateSize();
			return tree;
		}
	}

	public static class ActionLeftToRight extends Action {
		int leftIndex;
		AnomalySyntacticTree criteria;

		public ActionLeftToRight(int id, AnomalySyntacticTree criteria) {
			this.leftIndex = id;
			this.criteria = criteria;
		}

		public AnomalySyntacticTree invoke(AnomalySyntacticTree tree) {
			AnomalySyntacticTree root = tree.parent;
			ArrayList<AnomalySyntacticTree> res = new ArrayList<AnomalySyntacticTree>();
			AnomalySyntacticTree t = tree.leftChildren.get(leftIndex);

			for (int j = 0; j <= leftIndex; ++j) {
				AnomalySyntacticTree sibling = tree.leftChildren.remove(0);
				res.add(sibling);

				sibling.parent = root;
			}

			tree.parent = t;
			t.rightChildren.add(tree);

			if (root == null) {
				res.remove(res.size() - 1);
				for (AnomalySyntacticTree kinder : res) {
					kinder.parent = t;
				}

				t.leftChildren.addAll(0, res);
				t.validateSize();
				return t;
			}

			t.validateSize();
			int i = root.rightChildren.indexOf(tree);
			if (i >= 0) {
				root.rightChildren.remove(i);
				root.rightChildren.addAll(i, res);
				return null;
			}

			i = root.leftChildren.indexOf(tree);

			if (i >= 0) {
				root.leftChildren.remove(i);
				root.leftChildren.addAll(i, res);
				return null;
			}
			return null;
		}

		public SyntacticTree invoke(SyntacticTree tree) {
			AnomalySyntacticTree _left = criteria.leftChildren.get(leftIndex);

			int leftIndex = -1;
			SyntacticTree left = null;
			for (int i = 0; i < tree.leftChildren.size(); ++i) {
				if (tree.leftChildren.get(i).satisfy(_left)) {
					leftIndex = i;
					break;
				}
			}

			assert leftIndex >= 0;
			left = tree.leftChildren.get(leftIndex);

			SyntacticTree root = tree.parent;
			ArrayList<SyntacticTree> res = new ArrayList<SyntacticTree>();

			for (int j = 0; j <= leftIndex; ++j) {
				SyntacticTree sibling = tree.leftChildren.remove(0);
				res.add(sibling);

				sibling.parent = root;
			}

			tree.parent = left;
			left.rightChildren.add(tree);

			if (root == null) {
				res.remove(res.size() - 1);
				for (SyntacticTree kinder : res) {
					kinder.parent = left;
				}

				left.leftChildren.addAll(0, res);
				left.validateSize();
				return left;
			}

			left.validateSize();
			int i = root.rightChildren.indexOf(tree);
			if (i >= 0) {
				root.rightChildren.remove(i);
				root.rightChildren.addAll(i, res);
				return left;
			}

			i = root.leftChildren.indexOf(tree);

			if (i >= 0) {
				root.leftChildren.remove(i);
				root.leftChildren.addAll(i, res);
				return left;
			}
			return left;
		}
	}

	// [请问(VT)(NN)] => 请问(VT(NN))
	public void bubble(AnomalySyntacticTree substituent, ArrayList<Action> list) {
		int id = substituent.id;
		if (id < this.id) {
			AnomalySyntacticTree t = null;

			int i = leftChildren.size() - 1;
			for (; i >= 0; --i) {

				if (leftChildren.get(i).id == id) {
					t = leftChildren.get(i);
					break;
				}
			}

			if (t != null) {
				Action action = new ActionLeftToRight(i, this);
				list.add(action);
				AnomalySyntacticTree src = action.invoke(this.clone());
				if (!src.equals(substituent)) {
//					Utility.print(src);
//					Utility.print(substituent);
					src.bubble(substituent, list);
				}

			} else {
				Action action = new ActionLeftToRight(0, this);
				list.add(action);
				AnomalySyntacticTree src = action.invoke(this.clone());

//				Utility.print(src);
//				Utility.print(substituent);
				src.bubble(substituent, list);
			}
		} else if (id > this.id) { // [若使|若是|若|要是|假设|假定|假如|假使|假若|如果|如|倘使|倘若 ((的)话)] =>
									// (((若使|若是|若|要是|假设|假定|假如|假使|假若|如果|如|倘使|倘若)的)话)
			// [如果 (((VT|VI|VC|VA)的)话)] => (((如果(VT|VI|VC|VA))的)话)
			// if___________
			// _______NN
			// DE
			// _______NN
			// ____DE
			// if

			AnomalySyntacticTree t = null;
			int i = 0;
			for (; i < rightChildren.size(); ++i) {
				if (rightChildren.get(i).id == id) {
					t = rightChildren.get(i);
					break;
				}
			}

			if (t != null) {
				Action action = new ActionRightToLeft(i, this);
				list.add(action);
				AnomalySyntacticTree src = action.invoke(this.clone());
				if (!src.equals(substituent)) {
//					Utility.print(src);
//					Utility.print(substituent);
					src.bubble(substituent, list);
				}
			} else {
				// [如果(VT|VI|VC|VA((的)话)) ] => (((如果(VT|VI|VC|VA))的)话)
				Action action = new ActionRightToLeft(rightChildren.size() - 1, this);
				list.add(action);
				AnomalySyntacticTree src = action.invoke(this.clone());

//				Utility.print(src);
//				Utility.print(substituent);
				src.bubble(substituent, list);
			}
		} else {
			Action action;
			AnomalySyntacticTree src;

			if (leftChildren.size() > substituent.leftChildren.size()) {

				if (substituent.leftChildren.get(0).leftChildren.size() > 0) {
					action = new ActionLSiblingToLeft(1, 0, this);
				} else {
					action = new ActionLSiblingToRight(0, leftChildren.size() - 1, this);
				}

				list.add(action);
				src = action.invoke(this.clone());
				if (!src.equals(substituent)) {
//					Utility.print(src);
//					Utility.print(substituent);
					src.bubble(substituent, list);
				}

				return;
			} else if (leftChildren.size() < substituent.leftChildren.size()) {
				if (leftChildren.get(0).leftChildren.size() > 0) {
					action = new ActionLeftToLSibling(0, 0, this);
				} else {
					action = new ActionRightToLSibling(0, 0, this);
				}
				list.add(action);
				src = action.invoke(this.clone());
				if (!src.equals(substituent)) {
//					Utility.print(src);
//					Utility.print(substituent);
					src.bubble(substituent, list);
				}

				return;
			}
			// [请问(VT)(NN)] => 请问(VT(NN))
			if (rightChildren.size() > substituent.rightChildren.size()) {
				// [VT|VI|VC|VA(VT)(PN)(IJ)] => [VT|VI|VC|VA(VT(PN)(IJ))]
				if (substituent.rightChildren.get(0).leftChildren.size() > 0) {
					action = new ActionRSiblingToLeft(1, 0, this);
				} else {
					action = new ActionRSiblingToRight(0, rightChildren.size() - 1, this);
				}

				list.add(action);
				src = action.invoke(this.clone());
				if (!src.equals(substituent)) {
//					Utility.print(src);
//					Utility.print(substituent);
					src.bubble(substituent, list);
				}

				return;
			} else if (rightChildren.size() < substituent.rightChildren.size()) {
				// [VT (PN|NN(IJ)) ] => [VT (PN|NN) (IJ) ]
				if (rightChildren.get(0).leftChildren.size() > 0) {
					action = new ActionLeftToRSibling(0, 0, this);
				} else {
					action = new ActionRightToRSibling(0, 0, this);
				}

				list.add(action);
				src = action.invoke(this.clone());
				if (!src.equals(substituent)) {
//					Utility.print(src);
//					Utility.print(substituent);
					src.bubble(substituent, list);
				}
				return;
			}

			for (int i = 0; i < leftChildren.size(); ++i) {
				if (!leftChildren.get(i).equals(substituent.leftChildren.get(i))) {
//					Utility.print(leftChildren.get(i));
//					Utility.print(substituent.leftChildren.get(i));
					action = new ActionLeft(i, this);
					list.add(action);

					src = action.invoke(this.clone());
					src.bubble(action.invoke(substituent.clone()), list);
					return;

				}
			}

			for (int i = 0; i < rightChildren.size(); ++i) {
				if (!rightChildren.get(i).equals(substituent.rightChildren.get(i))) {
//					Utility.print(rightChildren.get(i));
//					Utility.print(substituent.rightChildren.get(i));
					action = new ActionRight(i, this);
					list.add(action);
					src = action.invoke(this.clone());
					src.bubble(action.invoke(substituent.clone()), list);
					return;
				}
			}
			assert false;
		}
	}

}
