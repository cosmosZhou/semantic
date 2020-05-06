package com.nlp.syntax;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nlp.syntax.AnomalyInspecter.Lexeme;
import com.util.Utility;

public class Compiler {

	public static abstract class HNode {
		abstract HNode append_left_parenthesis() throws Exception;

		static public HNode compile(String infix) throws Exception {
			HNode caret = new HNodeCaret();
			for (int i = 0; i < infix.length();) {
				switch (infix.charAt(i)) {
				case ' ':
				case '\t':
					break;
				case '(':
					caret = caret.append_left_parenthesis();
					break;
				case ')':
					HNode old = caret;
					for (;;) {
						if (caret == null) {
							System.out.println(infix.substring(0, i));
							System.out.println(infix.substring(i));
							log.info("unnecessary right parenthesis at position " + i);
							break;
						}
						old = caret;
						caret = caret.parent;
						if (caret instanceof HNodeParenthesis) {
							break;
						}
					}
					if (caret == null) {
						caret = old;
					}

					break;
				default:
					int beg = i;
					for (; i < infix.length(); ++i) {
						switch (infix.charAt(i)) {
						case '(':
						case ')':
							//						case ' ':
							break;
						default:
							continue;
						}
						break;
					}

					caret = caret.append_lexeme(infix.substring(beg, i));
					continue;
				}
				++i;
			}

			for (;;) {
				if (caret.parent != null)
					caret = caret.parent;
				else
					break;
			}

			return caret;
		}

		void replace(HNode old, HNode replacement) throws Exception {
		}

		void print() {
			HNode caret = this;
			while (caret != null) {
				System.out.println(caret);
				caret = caret.parent;
			}
			System.out.println();
		}

		abstract HNode append_lexeme(String lexeme) throws Exception;

		abstract SyntacticTree toSyntacticTree(SyntacticTree parent) throws Exception;

		abstract AnomalySyntacticTree toAnomalySyntacticTree(AnomalySyntacticTree parent) throws Exception;


		List<SyntacticTree> toSyntacticTreeList(SyntacticTree parent) throws Exception {
			List<SyntacticTree> arr = new ArrayList<SyntacticTree>();
			SyntacticTree node = this.toSyntacticTree(parent);
			arr.add(node);
			return arr;
		}

		List<AnomalySyntacticTree> toAnomalySyntacticTreeList(AnomalySyntacticTree parent) throws Exception {
			List<AnomalySyntacticTree> arr = new ArrayList<AnomalySyntacticTree>();
			AnomalySyntacticTree node = this.toAnomalySyntacticTree(parent);
			arr.add(node);
			return arr;
		}

		abstract public String toString();

		HNode parent;
	}

	static class HNodeCaret extends HNode {
		static final int stack_precedence = 14;
		static final int input_precedence = 3;

		HNode append_left_parenthesis() throws Exception {
			HNodeCaret caret = new HNodeCaret();
			HNodeParenthesis parenthesis = new HNodeParenthesis(caret, parent);
			if (parent != null) {
				parent.replace(this, parenthesis);
			}
			return caret;
		}

		HNode append_lexeme(String lexeme) throws Exception {
			HNode caret = new HNodeLexeme(lexeme, parent);
			if (this.parent != null) {
				parent.replace(this, caret);
			}
			return caret;
		}

		SyntacticTree toSyntacticTree(SyntacticTree parent) throws Exception {
			log.info("HNodeCaret cannot be converted to SyntacticTree in toDependencyTreeList(SyntacticTree parent)");
			return null;
		}

		AnomalySyntacticTree toAnomalySyntacticTree(AnomalySyntacticTree parent) throws Exception {
			log.info(
					"HNodeCaret cannot be converted to AnomalySyntacticTree in toDependencyTreeList(AnomalySyntacticTree parent)");
			return null;
		}

		public String toString() {
			return "null";
		}

		@Override
		List<SyntacticTree> toSyntacticTreeList(SyntacticTree parent) throws Exception {
			log.info("List<SyntacticTree> toSyntacticTreeList(SyntacticTree parent)");
			return new ArrayList<>();
		}

		@Override
		List<AnomalySyntacticTree> toAnomalySyntacticTreeList(AnomalySyntacticTree parent) throws Exception {
			log.info("List<AnomalySyntacticTree> toAnomalySyntacticTreeList(AnomalySyntacticTree parent)");
			return new ArrayList<>();
		}
	}

	static class HNodeLexeme extends HNode {
		static final int stack_precedence = 14;
		static final int input_precedence = 15;
		String lexeme;

		public HNodeLexeme(String lexeme, HNode parent) {
			this(lexeme);
			this.parent = parent;
		}

		public HNodeLexeme(String lexeme) {
			this.lexeme = lexeme;
		}

		HNode append_left_parenthesis() throws Exception {
			HNode caret = new HNodeCaret();
			HNodePrefix replacement = new HNodePrefix(lexeme, new HNodeParenthesis(caret), parent);
			if (parent != null) {
				parent.replace(this, replacement);
			}
			return caret;
		}

		SyntacticTree toSyntacticTree(SyntacticTree parent) throws Exception {
			String[] tag = lexeme.split("/");
			String pos = tag.length > 1 ? tag[1].trim() : null;
			String dep = tag.length > 2 ? tag[2].trim() : null;
	        int index =  tag.length > 3 ? Integer.parseInt(tag[3].trim()) : -1;
			return new SyntacticTree(index, tag[0].trim(), pos, dep, parent);
		}

		AnomalySyntacticTree toAnomalySyntacticTree(AnomalySyntacticTree parent) throws Exception {
			Lexeme[] lex = AnomalyInspecter.parseComponent(lexeme);

			return new AnomalySyntacticTree(-1, lex[0], lex[1], lex[2], parent);
		}

		public String toString() {
			return SyntacticTree.protectParenthesis(lexeme);
		}

		@Override
		HNode append_lexeme(String lexeme) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
	}

	static class HNodeParenthesis extends HNode {
		static final int stack_precedence = 0;
		static final int input_precedence = 16;
		HNode ptr;

		public HNodeParenthesis(HNode hNode, HNode parent) {
			ptr = hNode;
			ptr.parent = this;
			this.parent = parent;
		}

		public HNodeParenthesis(HNode hNode) {
			ptr = hNode;
			ptr.parent = this;
		}

		HNode append_lexeme(String lexeme) throws Exception {
			if (parent instanceof HNodeMultiplication) {
				return parent.append_lexeme(lexeme);
			}

			HNode parent = this.parent;
			HNodeSuffix suffix = new HNodeSuffix(lexeme, this, parent);
			if (parent != null) {
				parent.replace(this, suffix);
			}
			return suffix;
		}

		HNode append_left_parenthesis() throws Exception {
			if (parent != null && parent instanceof HNodeMultiplication) {
				return parent.append_left_parenthesis();
			}

			HNodeCaret caret = new HNodeCaret();
			ArrayList<HNode> array = new ArrayList<HNode>();
			array.add(this);
			array.add(new HNodeParenthesis(caret));
			HNode parent = this.parent;
			HNodeMultiplication mul = new HNodeMultiplication(array, parent);
			if (parent != null) {
				parent.replace(this, mul);
			}
			return caret;
		}

		SyntacticTree toSyntacticTree(SyntacticTree parent) throws Exception {
			return ptr.toSyntacticTree(parent);
		}

		AnomalySyntacticTree toAnomalySyntacticTree(AnomalySyntacticTree parent) throws Exception {
			return ptr.toAnomalySyntacticTree(parent);
		}

		List<SyntacticTree> toSyntacticTreeList(SyntacticTree parent) throws Exception {
			return ptr.toSyntacticTreeList(parent);
		}

		List<AnomalySyntacticTree> toAnomalySyntacticTreeList(AnomalySyntacticTree parent) throws Exception {
			return ptr.toAnomalySyntacticTreeList(parent);
		}

		public String toString() {
			return '(' + ptr.toString() + ')';
		}

		void replace(HNode old, HNode replacement) throws Exception {
			if (ptr != old)
				throw new Exception("");
			ptr = replacement;
		}
	}

	static class HNodeMultiplication extends HNode {
		static final int stack_precedence = 13;
		static final int input_precedence = 14;

		public HNodeMultiplication(ArrayList<HNode> array, HNode parent) {
			this.parent = parent;
			this.array = array;
			for (HNode ptr : array) {
				ptr.parent = this;
			}
		}

		HNode append_left_parenthesis() {
			HNodeCaret caret = new HNodeCaret();
			array.add(new HNodeParenthesis(caret, this));
			return caret;
		}

		HNode append_lexeme(String lexeme) throws Exception {
			HNode parent = this.parent;
			HNodeSuffix suffix = new HNodeSuffix(lexeme, this, parent);
			if (parent != null) {
				parent.replace(this, suffix);
			}

			return suffix;
		}

		ArrayList<HNode> array;

		SyntacticTree toSyntacticTree(SyntacticTree parent) throws Exception {
			log.info("HNodeMultiplication::toDependencyTree() is ambiguous.");
			SyntacticTree tree = array.get(0).toSyntacticTree(parent);
			List<SyntacticTree> list = new ArrayList<SyntacticTree>();
			for (int i = 1; i < array.size(); ++i) {
				list.add(array.get(i).toSyntacticTree(tree));
			}

			tree.rightChildren.addAll(list);
			return tree;
		}

		AnomalySyntacticTree toAnomalySyntacticTree(AnomalySyntacticTree parent) throws Exception {
			log.info("HNodeMultiplication::toDependencyTree() is ambiguous.");
			AnomalySyntacticTree tree = array.get(0).toAnomalySyntacticTree(parent);
			List<AnomalySyntacticTree> list = new ArrayList<AnomalySyntacticTree>();
			for (int i = 1; i < array.size(); ++i) {
				list.add(array.get(i).toAnomalySyntacticTree(tree));
			}

			tree.rightChildren.addAll(list);
			return tree;
		}

		List<SyntacticTree> toSyntacticTreeList(SyntacticTree parent) throws Exception {
			List<SyntacticTree> list = new ArrayList<SyntacticTree>();
			for (HNode ptr : array) {
				if (ptr instanceof HNodeParenthesis) {
					SyntacticTree node = ptr.toSyntacticTree(parent);
					if (node != null)
						list.add(ptr.toSyntacticTree(parent));
				} else
					throw new Exception("SyntacticTree toDependencyTreeList()");
			}
			return list;
		}

		List<AnomalySyntacticTree> toAnomalySyntacticTreeList(AnomalySyntacticTree parent) throws Exception {
			List<AnomalySyntacticTree> list = new ArrayList<AnomalySyntacticTree>();
			for (HNode ptr : array) {
				if (ptr instanceof HNodeParenthesis) {
					AnomalySyntacticTree node = ptr.toAnomalySyntacticTree(parent);
					if (node != null)
						list.add(ptr.toAnomalySyntacticTree(parent));
				} else
					throw new Exception("AnomalySyntacticTree toAnomalySyntacticTreeList()");
			}
			return list;
		}

		public String toString() {
			String str = "";
			for (HNode nd : array) {
				str += nd.toString();
			}
			return str;
		}

		void replace(HNode old, HNode replacement) throws Exception {
			int i = array.indexOf(old);
			if (i < 0)
				throw new Exception("void replace(HNode old, HNode replacement) throws Exception");
			array.set(i, replacement);
		}
	}

	static class HNodeBinary extends HNodeLexeme {
		public HNodeBinary(String lexeme, HNode left, HNode right, HNode parent) {
			super(lexeme);
			this.left = left;
			this.right = right;
			this.parent = parent;
			left.parent = right.parent = this;
		}

		static final int stack_precedence = 11;
		static final int input_precedence = 10;

		HNode append_left_parenthesis() throws Exception {
			throw new Exception("HNode append_left_parenthesis()");
		}

		HNode append_lexeme(String lexeme) {
			// this.parent.
			return null;
		}

		HNode left, right;

		public String toString() {
			return left.toString() + super.toString() + right.toString();
		}

		void replace(HNode old, HNode replacement) throws Exception {
			if (left == old) {
				left = replacement;
			} else if (right == old) {
				right = replacement;
			} else
				throw new Exception("void replace(HNode old, HNode replacement) throws Exception");
		}

		SyntacticTree toSyntacticTree(SyntacticTree parent) throws Exception {
			SyntacticTree tree = super.toSyntacticTree(parent);
			tree.leftChildren = left.toSyntacticTreeList(tree);
			tree.rightChildren = right.toSyntacticTreeList(tree);
			return tree;
		}

		AnomalySyntacticTree toAnomalySyntacticTree(AnomalySyntacticTree parent) throws Exception {
			AnomalySyntacticTree tree = super.toAnomalySyntacticTree(parent);
			tree.leftChildren = left.toAnomalySyntacticTreeList(tree);
			tree.rightChildren = right.toAnomalySyntacticTreeList(tree);
			return tree;
		}
	}

	static class HNodePrefix extends HNodeLexeme {
		public HNodePrefix(String lexeme, HNode caret) {
			super(lexeme);
			ptr = caret;
			ptr.parent = this;
		}

		public HNodePrefix(String lexeme, HNode caret, HNode parent) {
			this(lexeme, caret);
			this.parent = parent;
		}

		static final int stack_precedence = 11;
		static final int input_precedence = 10;

		HNode append_left_parenthesis() throws Exception {
			throw new Exception("HNode append_left_parenthesis()");
		}

		HNode append_lexeme(String lexeme) {
			// this.parent.
			return null;
		}

		HNode ptr;

		public String toString() {
			return super.toString() + ptr.toString();
		}

		void replace(HNode old, HNode replacement) throws Exception {
			if (ptr != old)
				throw new Exception("");
			ptr = replacement;
		}

		SyntacticTree toSyntacticTree(SyntacticTree parent) throws Exception {
			SyntacticTree tree = super.toSyntacticTree(parent);
			tree.rightChildren = ptr.toSyntacticTreeList(tree);
			return tree;
		}

		AnomalySyntacticTree toAnomalySyntacticTree(AnomalySyntacticTree parent) throws Exception {
			AnomalySyntacticTree tree = super.toAnomalySyntacticTree(parent);
			tree.rightChildren = ptr.toAnomalySyntacticTreeList(tree);
			return tree;
		}
	}

	public static class HNodeSuffix extends HNodeLexeme {
		public HNodeSuffix(String lexeme, HNode ptr, HNode parent) {
			super(lexeme);
			this.parent = parent;
			this.ptr = ptr;
			ptr.parent = this;
		}

		public HNodeSuffix(String lexeme, HNode ptr) {
			super(lexeme);
			this.ptr = ptr;
			ptr.parent = this;
		}

		static final int stack_precedence = 11;
		static final int input_precedence = 10;

		HNode append_left_parenthesis() throws Exception {
			HNodeCaret caret = new HNodeCaret();

			HNodeBinary replacement = new HNodeBinary(lexeme, this.ptr, new HNodeParenthesis(caret), parent);
			if (parent != null) {
				parent.replace(this, replacement);
			}
			return caret;
		}

		HNode append_lexeme(String lexeme) {
			// this.parent.
			return null;
		}

		HNode ptr;

		public String toString() {
			return ptr.toString() + super.toString();
		}

		void replace(HNode old, HNode replacement) throws Exception {
			if (ptr != old)
				throw new Exception("");
			ptr = replacement;
		}

		SyntacticTree toSyntacticTree(SyntacticTree parent) throws Exception {
			SyntacticTree tree = super.toSyntacticTree(parent);
			tree.leftChildren = ptr.toSyntacticTreeList(tree);
			return tree;
		}

		AnomalySyntacticTree toAnomalySyntacticTree(AnomalySyntacticTree parent) throws Exception {
			AnomalySyntacticTree tree = super.toAnomalySyntacticTree(parent);
			tree.leftChildren = ptr.toAnomalySyntacticTreeList(tree);
			return tree;
		}
	}

	static public String[] parse(String infix) throws Exception {
		// equivalently: "\\s*\\(*((\\\\\\()|(\\\\\\))|[^\\(^\\)]+)\\)*");
		ArrayList<String> word = new ArrayList<String>();

		for (String[] s : Utility.regex(infix.replaceAll("\\s+", ""), " *\\(*((\\\\\\()|(\\\\\\))|[^\\(\\)]*)\\)*")) {
			String lexme = s[0];

			if (lexme.startsWith("\\")) {
				lexme = lexme.substring(1);
			}

			if (lexme.length() == 0)
				continue;
			word.add(lexme);

		}
		return Utility.toArray(word);
	}

	public static SyntacticTree compile(String infix, String[] pos) throws Exception {
		SyntacticTree dtree = HNode.compile(infix).toSyntacticTree(null);
		dtree.validateSize();
		dtree.validateIndex();
		dtree.setPOS(pos);

		dtree = SyntacticParser.instance.parse(dtree);
		return dtree;
	}

	public static SyntacticTree parseWithAdjustment(String infix, String[] pos) throws Exception {
		SyntacticTree tree = compile(infix, pos);
		tree = SyntacticParser.instance.parseWithAdjustment(tree);
		return tree;
	}

	static public SyntacticTree compile(String infix, String[] pos, String[] dep) throws Exception {
		HNode caret = HNode.compile(infix);

		SyntacticTree dtree = caret.toSyntacticTree(null);
		dtree.validateSize();
		dtree.validateIndex();
		dtree.setDEP(dep);
		dtree.setPOS(pos);
		return dtree;
	}

	static public SyntacticTree compile(String infix) throws Exception {
		HNode caret = HNode.compile(infix);

		SyntacticTree dtree = caret.toSyntacticTree(null);
		dtree.validateSize();
		
	    if (dtree.id < 0)
	        dtree.validateIndex();

		return dtree;
	}

	public static void main(String[] args) throws Exception {
	}

	private static Logger log = Logger.getLogger(Compiler.class);
}
