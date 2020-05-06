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
	String lang = request.getParameter("lang");

	String text = request.getParameter("text");
	String relation_text = request.getParameter("relation_text");

	String paraphrase = request.getParameter("paraphrase");
	String relation_paraphrase = request.getParameter("relation_paraphrase");

	String score = request.getParameter("score");
	String relation_score = request.getParameter("relation_score");

	String score_replacement = request.getParameter("score_replacement");

	int training = Jsp.getTraining(request);
	String rand = request.getParameter("rand");
	int limit = Jsp.getLimit(request);

	String lines[] = {"mysql.cmd.value = '%s'",

			"mysql.text.value = '%s'", "mysql.relation_text.value = '%s'",
			"changeInputlength(mysql.relation_text, true)",

			"mysql.paraphrase.value = '%s'", "mysql.relation_paraphrase.value = '%s'",
			"changeInputlength(mysql.relation_paraphrase, true)",

			"mysql.score.value = '%s'", "mysql.relation_score.value = '%s'",
			"changeInputlength(mysql.relation_score, true)",

			"mysql.training.value = %d", "mysql.rand.checked = %s", "mysql.limit.value = %s",
			"if (mysql.cmd.value != 'select') onchange_cmd(mysql.cmd)"};

	out.print(Jsp.javaScript(String.join(";", lines), cmd,

			Utility.quote(text), relation_text,

			Utility.quote(paraphrase), relation_paraphrase,

			score, relation_score,

			training, rand == null ? "false" : "true", limit < 0 ? "" : String.valueOf(limit)));

	System.out.println("limit = " + limit);

	String sql;
	List<String> conditions = new ArrayList<String>();
	if (!text.isEmpty()) {
		conditions.add(Jsp.process_text("text", relation_text, text));
	}

	if (!paraphrase.isEmpty()) {
		conditions.add(Jsp.process_text("paraphrase", relation_paraphrase, paraphrase));
	}

	if (!score.isEmpty()) {
		conditions.add(Jsp.process_text("score", relation_score, Integer.parseInt(score)));
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
			sql = String.format("%s tbl_%s_%s set score = %s %s", cmd, table, lang, score_replacement, condition);
			out.print(String.format("<p class=update ondblclick='mysql_execute(this)'>%s</p>",
					Utility.str_html(sql)));
			
			sql = String.format("select * from tbl_%s_%s %s", table, lang, condition);
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
					int score_replacement_value = Integer.valueOf(score_replacement);
					for (Map<String, Object> dict : list) {
						int score_value = (Integer) dict.get("score");
						out.print(Jsp.createParaphraseEditor((String) dict.get("text"),
								(String) dict.get("paraphrase"), score_replacement_value,
								(boolean) (Boolean) dict.get("training"), score_replacement_value != score_value));
					}
					break;
				case "delete" :
					for (Map<String, Object> dict : list) {
						out.print(Jsp.createParaphraseEditor((String) dict.get("text"),
								(String) dict.get("paraphrase"), (Integer) dict.get("score"),
								(boolean) (Boolean) dict.get("training"), false));
					}
					break;
				default :
					for (Map<String, Object> dict : list) {
						out.print(Jsp.createParaphraseEditor((String) dict.get("text"),
								(String) dict.get("paraphrase"), (Integer) dict.get("score"),
								(boolean) (Boolean) dict.get("training"), false));
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
	fill_tbl_paraphrase();
</script>
<%
	}
%>

