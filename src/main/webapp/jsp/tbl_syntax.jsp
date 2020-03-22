<%@page import="java.sql.SQLException"%>
<%@page import="com.util.Native"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="com.util.Utility"%>
<%@page import="com.util.MySQL"%>
<%@page import="com.servlet.Frontier"%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
	String cmd = request.getParameter("cmd");
	String table = request.getParameter("table");
	int limit = Integer.valueOf(request.getParameter("limit"));
	int training = Integer.valueOf(request.getParameter("training"));
	String text = request.getParameter("text");
	text = text.replace("\\", "\\\\");

	String relation_text = request.getParameter("relation_text");

	String lines[] = {"mysql.text.value = '%s'", "mysql.relation_text.value = '%s'",
			"changeInputlength(mysql.relation_text, true)", "mysql.limit.value = %d",
			"mysql.training.value = %d"};

	out.print(Frontier.javaScript(String.join(";", lines), text, relation_text, limit, training));

	String lang = table.split("_")[2];

	if (cmd.startsWith("select*")) {
		cmd = "select text, infix, training";
	}

	System.out.println("limit = " + limit);

	String sql;
	List<String> conditions = new ArrayList<String>();
	if (!text.isEmpty()) {
		conditions.add(Frontier.process_text("text", relation_text, text));
	}

	boolean discrepant = false;
	if (training >= 0) {
		if (training > 1)
			discrepant = true;
		else
			conditions.add("training = " + training);
	}

	String condition = conditions.isEmpty() ? "" : "where " + String.join(" and ", conditions) + " ";
	if (request.getParameter("rand") != null)
		condition += "order by rand() ";

	if (!discrepant)
		condition += "limit " + limit;

	//	if (cmd.equals("update")) {
	//		sql = String.format("%s %s set infix = %s %s", cmd, table, infix, condition);
	//	} else {
	sql = String.format("%s from %s %s", cmd, table, condition);
	//	}

	System.out.println(sql);
	if (!cmd.startsWith("select")) {
		out.print(sql);
		MySQL.instance.execute(sql);
		return;
	}

	List<Map<String, Object>> list;
	if (discrepant) {
		list = MySQL.instance.select(sql, lang.equals("cn") ? new MySQL.Filter() {
			public boolean sift(ResultSet res) throws SQLException {
				return res.getString("infix").equals(Native.infixCN(res.getString("text")));
			}
		} : new MySQL.Filter() {
			public boolean sift(ResultSet res) throws SQLException {
				return res.getString("infix").equals(Native.infixEN(res.getString("text")));
			}
		}, limit);
	} else
		list = MySQL.instance.select(sql);
	out.print("count(*) = " + list.size());
	if (!discrepant) {
		out.print(String.format("<p ondblclick='mysql_execute(this)'>%s</p>", sql));
	}
%>

<div>
	insert into
	<%=table%>(text, label) values( <input type=button
		onClick='add_syntax_item(this.parentElement.nextElementSibling, "")'
		value=text> / <input id=syntax_file name=syntax_file type=file
		onchange='handleFiles(this, add_syntax_item)' value='text'
		accept='.txt' style='width: 5em;' title=''>, ...)<br> <br>
</div>
<form name=form method=post>
	<%
		for (Map<String, Object> dict : list) {
			text = (String) dict.get("text");
			String infix = (String) dict.get("infix");
			out.print(Frontier.createSyntaxEditor(text, infix,(boolean) (Boolean) dict.get("training")));
		}
	%>
	<input type=submit name='submit_<%=table%>' value=submit>
</form>
<script>
	fill_tbl_syntax();
</script>