package com.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.util.Utility;

public class Jsp {
	public static String createSyntaxEditor(String text, String infix, boolean training) {
		@SuppressWarnings("unchecked")
		HashMap<String, String> dict = Utility.dejsonify(
				Python.eval(String.format("compiler.compile('%s').__str__(return_dict=True)", infix)), HashMap.class);

		String infix_simplified = dict.get("infix");
		String seg = dict.get("seg");
		String pos = dict.get("pos");
		String dep = dict.get("dep");

		String lines[] = { "<div name=structure_division>",
				String.format("<br><input type=hidden name=text value = '%s'>%s<br>", text, text),
				String.format(
						"<input type=hidden name=infix value = '%s'><p class=monospace-p name=infixExpression>%s</p>",
						infix, infix),
				String.format("<p class=monospace-p name=tree>%s</p>",
						dict.get("tree").replaceAll(" ", "&ensp;").replaceAll("\\n", "<br>")),
				String.format(
						"<input type=text name=infix_simplified style='%s' class=monospace value='%s' onchange='modify_structure(this)'><br>",
						String.format("width:%dem;", (Utility.strlen(infix_simplified) / 2 + 1)),
						infix_simplified.replaceAll(" ", "&ensp;")),
				String.format(
						"<input type=text name=seg style='%s' class=monospace value='%s' onchange='modify_structure(this)'><br>",
						String.format("width:%dem;", (Utility.strlen(seg) / 2 + 1)), seg.replaceAll(" ", "&ensp;")),
				String.format(
						"<input type=text name=pos style='%s' class=monospace value='%s' onchange='modify_structure(this)'><br>",
						String.format("width:%dem;", (Utility.strlen(pos) / 2 + 1)), pos.replaceAll(" ", "&ensp;")),
				String.format(
						"<input type=text name=dep style='%s' class=monospace value='%s' onchange='modify_structure(this)'><br>",
						String.format("width:%dem;", (Utility.strlen(dep) / 2 + 1)), dep.replaceAll(" ", "&ensp;")),
				String.format("</div><input type=hidden name=training value=%d>", training ? 1 : 0) };

		return String.join("", lines);
	}

	public static String createSegmentEditor(String text, String seg, boolean training) {
		return createSegmentEditor(text, seg, training, false);
	}

	public static String createSegmentEditor(String text, String seg, boolean training, boolean changed) {
		return createSegmentEditor(text, seg, null, training, changed);
	}

	public static String createSegmentEditor(String text, String seg, String mark, boolean training, boolean changed) {
		return createSegmentEditor(text, text, seg, mark, training, changed);
	}

	public static String createSegmentEditor(String text_replacement, String text, String seg, String mark,
			boolean training) {
		return createSegmentEditor(text_replacement, text, seg, mark, training, false);
	}

	public static String createSegmentEditor(String text_replacement, String text, String seg, String mark,
			boolean training, boolean changed) {
		String lines[] = {
				String.format("<input type=hidden name=text value='%s'>%s<br>", Utility.quote_html(text_replacement),
						Utility.str_html(text)),
				String.format(
						"<input type=text name=seg style='%s' %s='%s' class=monospace onchange='onchange_segment(this)' onkeydown='onkeydown_segment(this, event)'>",
						String.format("width:%dem;", Math.max(32, Utility.strlen(seg) / 2 + 1)),
						text_replacement.isEmpty() ? "placeholder" : "value", Utility.quote_html(seg)),
				String.format("<input type=hidden name=training value=%s>", (changed ? "+" : "") + (training ? 1 : 0)),
				mark == null ? "<br><br>" : "<br> " + mark.replaceAll(" ", "&ensp;") + "<br>", };

		return String.join("", lines);
	}

	static String[] categorySelector = { "untechnical", "technical" };

	public static String createPretrainingEditor(int id, String title, String text, String mark, boolean changed) {
		int strlen_title = Math.max(8, Utility.strlen(title) / 2 + 1);
		String lines[] = { String.format("<input type=hidden name=id value=%d>", id),
				String.format("<input type=text name=title style='%s' class=monospace value='%s'><br>",
						String.format("width:%dem;", strlen_title), Utility.quote_html(title)),
				String.format("<input type=text name=text style='%s' class=monospace value='%s'>",
						String.format("width:%dem;", Math.max(64, Utility.strlen(text) / 2 + 1)),
						Utility.quote_html(text)),
				String.format("<input type=hidden name=training value=%s>", (changed ? "+" : "") + 1),
				mark == null ? "<br><br>" : "<br> " + mark.replaceAll(" ", "&ensp;") + "<br>", };

		return String.join("", lines);
	}

	public static String createParaphraseEditor(String x, String y, int score, boolean training, boolean changed) {
		String lines[] = { String.format("<div name=div><input type=hidden name=x value='%s'>", Utility.quote_html(x)),
				String.format("<input type=hidden name=y value='%s'>", Utility.quote_html(y)),
				String.format("%s / %s = ", Utility.str_html(x), Utility.str_html(y)),
				String.format(
						"<input type=text name=score style='width:2.5em;' value=%d onkeyup='input_positive_integer(this)' onafterpaste='input_positive_integer(this)' onchange='changeColor(this, this.nextElementSibling)'>",
						score),
				String.format("<input type=hidden name=training value=%s>", (changed ? "+" : "") + 1), "</div><br>" };

		return String.join("", lines);
	}

	public static String createKeywordEditor(String text, int label, int training) {
		return createKeywordEditor(text, label, training, false);
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

	public static String createKeywordEditor(String text, int label, int training, boolean changed) {
		String lines[] = {
				String.format("<input type=hidden name=text value='%s'>%s = ", Utility.quote_html(text),
						Utility.str_html(text)),
				Jsp.create_keyword_selector(label),
				changed ? String.format("<input type=hidden name=training value='+%d'><br><br>", training)
						: String.format("<input type=hidden name=training value=%d><br><br>", training) };

		return String.join("", lines);
	}

	public static String createKeywordEditor(String text, String href, int label, int training, boolean changed) {
		String lines[] = {
				String.format("<input type=hidden name=text value='%s'>%s = ", Utility.quote_html(text), href),
				Jsp.create_keyword_selector(label),
				changed ? String.format("<input type=hidden name=training value='+%d'><br><br>", training)
						: String.format("<input type=hidden name=training value=%d><br><br>", training) };

		return String.join("", lines);
	}

	public static String createKeywordSelector(String selected) {
		return generateSelectorIndexed(categorySelector, selected, "label",
				"changeColor(this, this.nextElementSibling)");
	}

	public static String create_keyword_selector(int selected) {
		return generateSelectorIndexed(categorySelector, selected, "label",
				"changeColor(this, this.nextElementSibling)");
	}

	static public String generateSelector(String selector[], String fieldName, Object... args) {
		StringBuffer buffer = new StringBuffer();
		String conditions[] = { "name='%s'", "onchange='%s'", "style='text-align:center;'%s'", "onfocus='%s'",
				"class='%s'" };

		String format = "<select ";
		for (int i = 0; i < args.length; i++) {
			format += conditions[i];
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

		String format = "<select ";
		for (int i = 0; i < args.length; i++) {
			format += conditions[i];
		}
		format += ">";

		buffer.append(String.format(format, args));

		boolean found = false;

		int index = 0;
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

		String format = "<select ";
		for (int i = 0; i < args.length; i++) {
			format += conditions[i];
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

	public static void main(String[] args) {
	}
}