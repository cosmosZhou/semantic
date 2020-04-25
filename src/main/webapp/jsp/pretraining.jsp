<%@page import="com.servlet.TextMatcher"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.util.Native"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="com.util.Utility"%>
<%@page import="com.util.MySQL"%>
<%@page import="com.servlet.Jsp"%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
	//	request.setCharacterEncoding("utf-8");
	String cmd = request.getParameter("cmd");
	String table = request.getParameter("table");
	table = "bert." + table;

	String text = request.getParameter("text");
	String relation_text = request.getParameter("relation_text");

	String title = request.getParameter("title");
	String relation_title = request.getParameter("relation_title");
	String replacement = request.getParameter("replacement");

	String rand = request.getParameter("rand");
	int limit = Jsp.getLimit(request);

	String title_script = "mysql.title.value = '%s';";
	if (relation_title != null) {
		title_script += String.format(
				"mysql.relation_title.value = '%s'; changeInputlength(mysql.relation_title, true)",
				relation_title);
	}

	String lines[] = { "mysql.cmd.value = '%s'", "mysql.text.value = '%s'", "mysql.relation_text.value = '%s'",
			title_script, "mysql.rand.checked = %s", "mysql.limit.value = %s",
			"if (mysql.cmd.value != 'select*') onchangeTable(mysql.cmd)",
			"if (mysql.cmd.value == 'update') mysql.replacement.value = '%s'" };

	out.print(Jsp.javaScript(String.join(";", lines), cmd, Utility.quote(text), relation_text,
			Utility.quote(title), rand == null ? "false" : "true", limit < 0 ? "" : String.valueOf(limit),
			replacement != null ? Utility.quote(replacement) : null));

	String lang = table.split("_")[1];

	System.out.println("limit = " + limit);

	String sql;
	TextMatcher matcher = null;
	List<String> conditions = new ArrayList<String>();
	if (!text.isEmpty()) {
		if (relation_text != null)
			conditions.add(Jsp.process_text("text", relation_text, text));
		else {
			String old;
			if (!replacement.isEmpty()) {
				old = text;
			} else {
				old = text;
				replacement = "";
			}
			System.out.println("old = " + old);
			System.out.println("replacement = " + replacement);
			matcher = new TextMatcher(old, replacement);

			conditions.add(Jsp.process_text("text", "regexp", matcher.text));
		}
	}

	if (!title.isEmpty()) {
		conditions.add(Jsp.process_text("title", relation_title, title));
	}

	String condition = conditions.isEmpty() ? "" : "where " + String.join(" and ", conditions) + " ";
	if (rand != null)
		condition += "order by rand() ";

	if (limit >= 0)
		condition += "limit " + limit;

	switch (cmd) {
	case "update":
		if (matcher != null) {
			sql = String.format("%s %s set text = regexp_replace(text, '%s', '%s') %s", cmd, table,
					Utility.quote_mysql(matcher.text), Utility.quote_mysql(matcher.text_replacement),
					condition);

			out.print(String.format("<p class=update ondblclick='mysql_execute(this)'>%s</p>",
					Utility.str_html(sql)));
			sql = String.format("select* from tbl_%s %s", table, condition);
		} else {
			cmd = "select*";
			sql = String.format("%s from tbl_%s %s", cmd, table, condition);
		}

		break;
	case "delete":
		sql = String.format("%s from tbl_%s %s", cmd, table, condition);
		out.print(String.format("<p class=delete ondblclick='mysql_execute(this)'>%s</p>",
				Utility.str_html(sql)));
		sql = String.format("select* from tbl_%s %s", table, condition);
		break;
	default:
		sql = String.format("%s from tbl_%s %s", cmd, table, condition);
	}

	List<Map<String, Object>> list = MySQL.instance.select(sql);

	out.print(String.format("<p class=select ondblclick='mysql_execute(this)'>%s</p>", Utility.str_html(sql)));
%>

<div>
	insert into tbl_<%=table%>(text, title) values( <input type=button
		onClick='add_pretraining_item(this.parentElement.nextElementSibling, "")'
		value=text> / <input id=syntax_file name=syntax_file type=file
		onchange='handleFiles(this, add_pretraining_item)' value='text'
		accept='.txt' style='width: 5em;' title=''>, ...)<br> <br>
</div>
<%
	if (!list.isEmpty()) {
		out.print("count(*) = " + list.size());
%>
<form name=form method="post" class=monospace-form>
	<%
		boolean changed = matcher != null;
			boolean deleted = cmd.equals("delete");
			switch (cmd) {
			case "update":
				for (Map<String, Object> dict : list) {
					int id = (Integer) dict.get("id");
					text = (String) dict.get("text");
					title = (String) dict.get("title");

					String[] text_result = matcher.transform(text);
					out.print(Jsp.createPretrainingEditor(id, title, text_result[0], text_result[1], changed));
				}
				break;
			//			case "delete":
			//				for (Map<String, Object> dict : list) {
			//					out.print(Jsp.createPretrainingEditor((Integer) dict.get("id"), (String) dict.get("text"),
			//							(String) dict.get("title"), false));
			//				}
			//				break;
			default:
				for (Map<String, Object> dict : list) {
					out.print(Jsp.createPretrainingEditor((Integer) dict.get("id"), (String) dict.get("title"),
							(String) dict.get("text"), null, false));
				}

			}
	%>
	<input type=submit name='<%=table%>_submit' value=submit>
</form>

<%
	}
%>

