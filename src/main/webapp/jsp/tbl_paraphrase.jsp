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

	String x = request.getParameter("x");
	String relation_x = request.getParameter("relation_x");

	String y = request.getParameter("y");
	String relation_y = request.getParameter("relation_y");

	int training = Jsp.getTraining(request);
	String rand = request.getParameter("rand");
	int limit = Jsp.getLimit(request);

	String lines[] = {"mysql.cmd.value = '%s'", "mysql.x.value = '%s'", "mysql.relation_x.value = '%s'",
			"mysql.y.value = '%s'", "mysql.relation_y.value = '%s'",
			"changeInputlength(mysql.relation_x, true)", "changeInputlength(mysql.relation_y, true)",
			"mysql.training.value = %d", "mysql.rand.checked = %s", "mysql.limit.value = %s",
			"if (mysql.cmd.value != 'select*') onchangeTable(mysql.cmd)"};

	out.print(Jsp.javaScript(String.join(";", lines), cmd, Utility.quote(x), relation_x, Utility.quote(y),
			relation_y, training, rand == null ? "false" : "true", limit < 0 ? "" : String.valueOf(limit)));

	String lang = table.split("_")[2];

	System.out.println("limit = " + limit);

	String sql;
	List<String> conditions = new ArrayList<String>();
	if (!x.isEmpty()) {
		conditions.add(Jsp.process_text("x", relation_x, x));
	}

	if (!y.isEmpty()) {
		conditions.add(Jsp.process_text("y", relation_y, y));
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
			cmd = "select*";
			sql = String.format("%s from %s %s", cmd, table, condition);

			break;
		case "delete" :
			sql = String.format("%s from %s %s", cmd, table, condition);
			out.print(String.format("<p class=delete ondblclick='mysql_execute(this)'>%s</p>",
					Utility.str_html(sql)));
			sql = String.format("select* from %s %s", table, condition);
			break;
		default :
			sql = String.format("%s from %s %s", cmd, table, condition);
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
%>

<div>
	insert into
	<%=table%>(x, y, score) values( <input type=button
		onClick='add_paraphrase_item(this.parentElement.getElementsByTagName("div")[0], "")'
		value=text> / <input id=file name=file type=file
		onchange='handleFiles(this, add_paraphrase_item' value=text
		accept='.txt' style='width: 5em;' title=''>, ...)<br> <br>
</div>
<%
	if (!list.isEmpty()) {
		out.print("count(*) = " + list.size());
%>
<form name=form method="post" class=monospace-form>
	<%
		boolean deleted = cmd.equals("delete");
			switch (cmd) {
				case "update" :
					for (Map<String, Object> dict : list) {
						out.print(Jsp.createParaphraseEditor((String) dict.get("x"), (String) dict.get("y"),
								(Integer) dict.get("score"), (boolean) (Boolean) dict.get("training"), false));
					}
					break;
				case "delete" :
					for (Map<String, Object> dict : list) {
						out.print(Jsp.createParaphraseEditor((String) dict.get("x"), (String) dict.get("y"),
								(Integer) dict.get("score"), (boolean) (Boolean) dict.get("training"), false));
					}
					break;
				default :
					for (Map<String, Object> dict : list) {
						out.print(Jsp.createParaphraseEditor((String) dict.get("x"), (String) dict.get("y"),
								(Integer) dict.get("score"), (boolean) (Boolean) dict.get("training"), false));
					}

			}
	%>
	<input type=submit name='<%=table%>_submit' value=submit>
</form>

<%
	}
	if (cmd.equals("select*") && !discrepant) {
%>
<script>
	fill_tbl_paraphrase();
</script>
<%
	}
%>

