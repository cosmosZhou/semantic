package com.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.nlp.syntax.Compiler;
import com.nlp.syntax.SyntacticTree;
import com.util.Utility;

public class Jsp {
	public static String editorWrapper(String[] elements, boolean training, boolean changed) {
		String training_str;

		if (changed)
			training_str = "+";
		else
			training_str = "";

		if (training)
			training_str += "1";
		else
			training_str += "0";

		return String.format("<div>%s<input type=hidden name=training value=%s></div><br>", String.join("", elements),
				training_str);
	}

	public static String[] getParameterNames(HttpServletRequest request) {
		ArrayList<String> list = new ArrayList<String>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length > 0) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					list.add(paramName);
				}
			}
		}
		return list.toArray(new String[list.size()]);
	}

	public static String[] getParameterNames(HttpServletRequest request, String regex) {
		ArrayList<String> list = new ArrayList<String>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			System.out.println("paramName = " + paramName);
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length > 0) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					if (Pattern.compile(regex).matcher(paramName).find())
						list.add(paramName);
				}
			}
		}
		return list.toArray(new String[list.size()]);
	}

	public static String createSyntaxEditor(String text, String infix, boolean training) throws Exception {
		return editorWrapper(createSyntaxEditor(text, infix), training, false);
	}

	public static String[] createSyntaxEditor(String text, String infix) throws Exception {
		SyntacticTree syntaxTree = Compiler.compile(infix);

		String[] args = new String[4];
		String tree = syntaxTree.toString(null, args);
		String infix_simplified = args[0];
		String seg = args[1];
		String pos = args[2];
		String dep = args[3];

		int length = Utility.max(Utility.strlen(infix_simplified), Utility.strlen(seg), Utility.strlen(pos),
				Utility.strlen(dep));
		length = length / 2 + 1;
		
		String lines[] = { String.format("<input type=hidden name=text value='%s'>%s", text, text),
				String.format("<input type=hidden name=infix value='%s'>", Utility.quote_html(infix)),
				String.format("<p class=monospace-p name=tree>%s</p>",
						tree.replaceAll(" ", "&ensp;").replaceAll("\\n", "<br>")),
				String.format(
						"<input type=text name=infix_simplified style='%s' class=monospace value='%s' onchange='onchange_infix(this)'><br>",
						String.format("width:%dem;", length),
						Utility.quote_html(infix_simplified).replaceAll(" ", "&ensp;")),
				String.format(
						"<input type=text name=seg style='%s' class=monospace value='%s' onchange='onchange_seg(this)'><br>",
						String.format("width:%dem;", length), Utility.quote_html(seg).replaceAll(" ", "&ensp;")),
				String.format(
						"<input type=text name=pos style='%s' class=monospace value='%s' onchange='modify_structure(this)'><br>",
						String.format("width:%dem;", length), pos.replaceAll(" ", "&ensp;")),
				String.format(
						"<input type=text name=dep style='%s' class=monospace value='%s' onchange='modify_structure(this)'>",
						String.format("width:%dem;", length), dep.replaceAll(" ", "&ensp;")) };

		return lines;
	}

	public static String createSegmentEditor(String text, String seg, boolean training) {
		return createSegmentEditor(text, seg, training, false);
	}

	public static String createSegmentEditor(String text, String seg, boolean training, boolean changed) {
		return createSegmentEditor(text, seg, null, training, changed);
	}

	public static String createSegmentEditor(String text, String seg, String mark, boolean training, boolean changed) {
		return editorWrapper(createSegmentEditor(text, text, seg, mark), training, changed);
	}

	public static String createSegmentEditor(String text_replacement, String text, String seg, String mark,
			boolean training) {
		return editorWrapper(createSegmentEditor(text_replacement, text, seg, mark), training, false);
	}

	public static String createSegmentEditor(String text_replacement, String text, String seg, String mark,
			boolean training, boolean changed) {
		return editorWrapper(createSegmentEditor(text_replacement, text, seg, mark), training, changed);
	}

	public static String[] createSegmentEditor(String text_replacement, String text, String seg, String mark) {
		String lines[] = {
				String.format("<input type=hidden name=text value='%s'>%s<br>", Utility.quote_html(text_replacement),
						Utility.str_html(text)),
				String.format(
						"<input type=text name=seg style='%s' %s='%s' class=monospace onchange='onchange_segment(this)' onkeydown='onkeydown_segment(this, event)'>",
						String.format("width:%dem;", Math.max(32, Utility.strlen(seg) / 2 + 1)),
						text_replacement.isEmpty() ? "placeholder" : "value", Utility.quote_html(seg)),
				mark == null ? "<br>" : "<br> " + mark.replaceAll(" ", "&ensp;"), };

		return lines;
	}

	public static String createPretrainingEditor(int id, String title, String text, String mark, boolean training) {
		return createPretrainingEditor(id, title, text, mark, training, false);
	}

	public static String createPretrainingEditor(int id, String title, String text, String mark, boolean training,
			boolean changed) {
		return editorWrapper(createPretrainingEditor(id, title, text, mark), training, changed);
	}

	public static String[] createPretrainingEditor(int id, String title, String text, String mark) {
		int strlen_title = Math.max(8, Utility.strlen(title) / 2 + 1);
		String lines[] = { String.format("<input type=hidden name=id value=%d>", id),
				String.format("<input type=text name=title style='%s' class=monospace value='%s'><br>",
						String.format("width:%dem;", strlen_title), Utility.quote_html(title)),
				String.format("<input type=text name=text style='%s' class=monospace value='%s'>",
						String.format("width:%dem;", Math.max(64, Utility.strlen(text) / 2 + 1)),
						Utility.quote_html(text)),
				mark == null ? "<br>" : "<br> " + mark.replaceAll(" ", "&ensp;"), };

		return lines;
	}

	public static String createParaphraseEditor(String x, String y, int score, boolean training, boolean changed) {
		return editorWrapper(createParaphraseEditor(x, y, score), training, changed);
	}

	public static String[] createParaphraseEditor(String x, String y, int score) {
		String lines[] = { String.format("<input type=hidden name=text value='%s'>", Utility.quote_html(x)),
				String.format("<input type=hidden name=paraphrase value='%s'>", Utility.quote_html(y)),
				String.format("%s / %s = ", Utility.str_html(x), Utility.str_html(y)),
				String.format(
						"<input type=text name=score style='width:2.5em;' value=%d onkeyup='input_positive_integer(this)' onafterpaste='input_positive_integer(this)' onchange='changeColor(this, this.nextElementSibling)'>",
						score) };

		return lines;
	}

	public static String createLexiconEditor(String x, String y, String label, boolean training, boolean changed) {
		return editorWrapper(createLexiconEditor(x, y, label), training, changed);
	}

	public static String[] createLexiconEditor(String x, String y, String label) {
		String lines[] = { String.format("<input type=hidden name=text value='%s'>", Utility.quote_html(x)),
				String.format("<input type=hidden name=derivant value='%s'>", Utility.quote_html(y)),
				String.format("%s / %s = ", Utility.str_html(x), Utility.str_html(y)),
				Jsp.createLexiconSelector(label) };

		return lines;
	}

	public static String createTranslationEditor(String x, String y, boolean training, boolean changed) {
		return editorWrapper(createTranslationEditor(x, y), training, changed);
	}

	public static String[] createTranslationEditor(String x, String y) {
		String lines[] = { String.format("<input type=hidden name=text value='%s'>", Utility.quote_html(x)),
				String.format("%s<br>", Utility.str_html(x)),
				String.format(
						"<input type=text name=translation style='%s' value='%s' onchange='changeColor(this, this.nextElementSibling)'>",
						String.format("width:%dem;", Utility.strlen(y) / 2 + 1), Utility.quote_html(y)) };

		return lines;
	}

	public static String createKeywordEditor(String text, int label, boolean training) {
		return editorWrapper(createKeywordEditor(text, label), training, false);
	}

	public static String createKeywordEditor(String text, int label, boolean training, boolean changed) {
		return editorWrapper(createKeywordEditor(text, label), training, changed);
	}

	public static String createKeywordEditor(String text, String href, int label, boolean training, boolean changed) {
		return editorWrapper(createKeywordEditor(text, href, label), training, changed);
	}

	public static String join(String[] array) throws IOException {
		for (int i = 0; i < array.length; ++i) {
			array[i] = Utility.str_html(array[i]);
		}
		return String.join("<br>", array);
	}

	public static String join(List<String> array) throws IOException {
		for (int i = 0; i < array.size(); ++i) {
			array.set(i, Utility.str_html(array.get(i)));
		}
		return String.join("<br>", array);
	}

	public static String[] createKeywordEditor(String text, int label) {
		String lines[] = { String.format("<input type=hidden name=text value='%s'>%s = ", Utility.quote_html(text),
				Utility.str_html(text)), Jsp.create_keyword_selector(label) };

		return lines;
	}

	public static String[] createKeywordEditor(String text, String href, int label) {
		String lines[] = {
				String.format("<input type=hidden name=text value='%s'>%s = ", Utility.quote_html(text), href),
				Jsp.create_keyword_selector(label) };

		return lines;
	}

	public static String createKeywordSelector(String selected) {
		String[] categorySelector = { "untechnical", "technical" };
		return generateSelectorIndexed(categorySelector, selected, "label",
				"changeColor(this, this.nextElementSibling)");
	}

	public static String createLexiconSelector(String selected) {
		String[] categorySelector = { "hypernym", "hyponym", "synonym", "antonym", "related", "unrelated" };
		return generateSelector(categorySelector, selected, "label", "changeColor(this, this.nextElementSibling)");
	}

	public static String create_keyword_selector(int selected) {
		String[] categorySelector = { "untechnical", "technical" };
		return generateSelectorIndexed(categorySelector, selected, "label",
				"changeColor(this, this.nextElementSibling)");
	}

	static public String generateSelector(String selector[], String fieldName, Object... args) {
		StringBuffer buffer = new StringBuffer();
		String conditions[] = { "name='%s'", "onchange='%s'", "style='text-align:center;'%s'", "onfocus='%s'",
				"class='%s'" };

		String format = "<select";
		for (int i = 0; i < args.length; i++) {
			format += " " + conditions[i];
		}
		format += ">";

		buffer.append(String.format(format, args));

		boolean found = false;

		for (String field : selector) {
			String selected;
			if (field.equals(fieldName)) {
				selected = "selected";
				found = true;
			} else
				selected = "";

			buffer.append(String.format("<option value='%s' %s>%s</option>", field, selected, field));
		}

		if (!found) {
			buffer.append(String.format("<option value='%s' selected>%s</option>", fieldName, fieldName));
		}
		buffer.append("</select>");
		return buffer.toString();
	}

	static public String generateSelectorIndexed(String selector[], String fieldName, Object... args) {
		StringBuffer buffer = new StringBuffer();
		String conditions[] = { "name='%s'", "onchange='%s'", "style='text-align:center;'%s'", "onfocus='%s'",
				"class='%s'" };

		String format = "<select";
		for (int i = 0; i < args.length; i++) {
			format += " " + conditions[i];
		}
		format += ">";

		buffer.append(String.format(format, args));

		boolean found = false;

		int index = 1;
		for (String field : selector) {
			String selected;
			if (field.equals(fieldName)) {
				selected = "selected";
				found = true;
			} else
				selected = "";

			buffer.append(String.format("<option value=%d %s>%s</option>", index++, selected, field));
		}

		if (!found) {
			buffer.append(String.format("<option value=-1 selected>%s</option>", fieldName));
		}
		buffer.append("</select>");
		return buffer.toString();
	}

	static public String generateSelectorIndexed(String selector[], int fieldName, Object... args) {
		StringBuffer buffer = new StringBuffer();
		String conditions[] = { "name='%s'", "onchange='%s'", "style='text-align:center;%s'", "onfocus='%s'",
				"class='%s'" };

		String format = "<select";
		for (int i = 0; i < args.length; i++) {
			format += " " + conditions[i];
		}
		format += ">";

		buffer.append(String.format(format, args));

		boolean found = false;

		int index = 0;
		for (String field : selector) {
			String selected;
			if (index == fieldName) {
				selected = "selected";
				found = true;
			} else
				selected = "";

			buffer.append(String.format("<option value=%d %s>%s</option>", index++, selected, field));
		}

		if (!found) {
			buffer.append(String.format("<option value='' selected></option>"));
		}
		buffer.append("</select>");
		return buffer.toString();
	}

	public static int getLimit(HttpServletRequest request) {
		return getInt(request, "limit");
	}

	public static int getTraining(HttpServletRequest request) {
		return getInt(request, "training");
	}

	public static int getInt(HttpServletRequest request, String field) {
		String str = request.getParameter(field);
		if (str == null || str.isEmpty())
			return -1;
		else
			return Integer.valueOf(str);
	}

	public static String javaScript(String statement, Object... args) {
		return String.format("<script>%s</script>", String.format(statement, args));
	}

	public static String process_text(String field, String relation, String text) {
		text = com.util.Utility.quote_mysql(text);
		if (relation.contains("like")) {
			text = String.format("%%%s%%", text);
		}
//		else if (relation.contains("regexp")) {			
//			text = text.replace("\\", "\\\\");
//		}

		return String.format("%s %s '%s'", field, relation, text);
	}

	public static String process_text(String field, String relation, int value) {
		return String.format("%s %s %s", field, relation, value);
	}

	public static void main(String[] args) {
	}
}