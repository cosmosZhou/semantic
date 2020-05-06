<%@page import="com.servlet.StringMatcher"%>
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

	String text = request.getParameter("text");
	String relation_text = request.getParameter("relation_text");

	String translation = request.getParameter("translation");
	String relation_translation = request.getParameter("relation_translation");

	int training = Jsp.getTraining(request);
	String rand = request.getParameter("rand");
	int limit = Jsp.getLimit(request);

	String lines[] = {"mysql.cmd.value = '%s'", "mysql.text.value = '%s'", "mysql.relation_text.value = '%s'",
			"mysql.translation.value = '%s'", "mysql.relation_translation.value = '%s'",
			"changeInputlength(mysql.relation_text, true)",
			"changeInputlength(mysql.relation_translation, true)", "mysql.training.value = %d",
			"mysql.rand.checked = %s", "mysql.limit.value = %s",
			"if (mysql.cmd.value != 'select') onchange_cmd(mysql.cmd)"};

	out.print(Jsp.javaScript(String.join(";", lines), cmd, Utility.quote(text), relation_text,
			Utility.quote(translation), relation_translation, training, rand == null ? "false" : "true",
			limit < 0 ? "" : String.valueOf(limit)));

	String lang = request.getParameter("lang");

	System.out.println("limit = " + limit);

	String sql;
	List<String> conditions = new ArrayList<String>();
	if (!text.isEmpty()) {
		conditions.add(Jsp.process_text("text", relation_text, text));
	}

	if (!translation.isEmpty()) {
		conditions.add(Jsp.process_text("translation", relation_translation, translation));
	}

	boolean discrepant = false;
	if (training >= 0) {
		if (training == 2)
			discrepant = true;
		else
			conditions.add("training = " + training);
	}

	String condition = conditions.isEmpty() ? "" : "where " + String.join(" and ", conditions) + " ";
	if (rand != null)
		condition += "order by rand() ";

	if (!discrepant && limit >= 0)
		condition += "limit " + limit;

	switch (cmd) {
		case "update" :
			cmd = "select";
			sql = String.format("%s from tbl_%s_%s %s", cmd, table, lang, condition);

			break;
		case "delete" :
			sql = String.format("%s from tbl_%s_%s %s", cmd, table, lang, condition);
			out.print(String.format("<p class=delete ondblclick='mysql_execute(this)'>%s</p>",
					Utility.str_html(sql)));
			sql = String.format("select * from tbl_%s_%s %s", table, lang, condition);
			break;
		default :
			sql = String.format("%s * from tbl_%s_%s %s", cmd, table, lang, condition);
	}

	List<Map<String, Object>> list;
	if (discrepant) {
		MySQL.Filter filter;
		switch (lang) {
			case "cn" :
				filter = new MySQL.Filter() {
					public Object sift(ResultSet res) throws SQLException {
						return true;
					}
				};
				break;
			default :
				filter = null;
				break;
		}
		list = MySQL.instance.select(sql, filter, limit);
	} else
		list = MySQL.instance.select(sql);

	if (!discrepant) {
		out.print(String.format("<p class=select ondblclick='mysql_execute(this)'>%s</p>",
				Utility.str_html(sql)));
	}
	if (!list.isEmpty()) {
		out.print("count(*) = " + list.size());
%>
<form name=form method="post" class=monospace-form>
	<%
		boolean deleted = cmd.equals("delete");
			switch (cmd) {
				case "update" :
					for (Map<String, Object> dict : list) {
						out.print(Jsp.createTranslationEditor((String) dict.get("text"),
								(String) dict.get("translation"), (boolean) (Boolean) dict.get("training"), false));
					}
					break;
				case "delete" :
					for (Map<String, Object> dict : list) {
						out.print(Jsp.createTranslationEditor((String) dict.get("text"),
								(String) dict.get("translation"), (boolean) (Boolean) dict.get("training"), false));
					}
					break;
				default :
					for (Map<String, Object> dict : list) {
						out.print(Jsp.createTranslationEditor((String) dict.get("text"),
								(String) dict.get("translation"), (boolean) (Boolean) dict.get("training"), false));
					}

			}
	%>
	<input type=submit name='<%=table%>_<%=lang%>_submit' value=submit>
</form>

<%
	}
	if (cmd.equals("select") && !discrepant) {
%>
<script>
	fill_tbl_translation();
</script>
<%
	}
%>

