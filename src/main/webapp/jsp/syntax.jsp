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
	String cmd = request.getParameter("cmd");
	String table = request.getParameter("table");
	String text = request.getParameter("text");

	int limit = Jsp.getLimit(request);

	int training = Jsp.getTraining(request);
	String rand = request.getParameter("rand");

	String relation_text = request.getParameter("relation_text");

	String lines[] = {"mysql.text.value = '%s'", "mysql.relation_text.value = '%s'",
			"changeInputlength(mysql.relation_text, true)", "mysql.rand.checked = %s", "mysql.limit.value = %s",
			"mysql.training.value = %d"};

	out.print(Jsp.javaScript(String.join(";", lines), Utility.quote(text), relation_text,
			rand == null ? "false" : "true", limit < 0 ? "" : String.valueOf(limit), training));

	String lang = request.getParameter("lang");

	System.out.println("limit = " + limit);

	String sql;
	List<String> conditions = new ArrayList<String>();
	if (!text.isEmpty()) {
		conditions.add(Jsp.process_text("text", relation_text, text));
	}

	boolean discrepant = false;
	if (training >= 0) {
		if (training > 1)
			discrepant = true;
		else
			conditions.add("training = " + training);
	}

	String condition = conditions.isEmpty() ? "" : "where " + String.join(" and ", conditions) + " ";
	if (rand != null)
		condition += "order by rand() ";

	if (!discrepant && limit >= 0)
		condition += "limit " + limit;

	//	if (cmd.equals("update")) {
	//		sql = String.format("%s %s set infix = %s %s", cmd, table, infix, condition);
	//	} else {
	sql = String.format("%s from tbl_%s_%s %s", cmd, table, lang, condition);
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
			public Object sift(ResultSet res) throws SQLException {
				return res.getString("infix").equals(Native.infixCN(res.getString("text")));
			}
		} : new MySQL.Filter() {
			public Object sift(ResultSet res) throws SQLException {
				return res.getString("infix").equals(Native.infixEN(res.getString("text")));
			}
		}, limit);
	} else
		list = MySQL.instance.select(sql);
	if (!discrepant) {
		out.print(String.format("<p class=select ondblclick='mysql_execute(this)'>%s</p>",
				Utility.str_html(sql)));
	}
	out.print("count(*) = " + list.size());
%>
<form name=form method=post>
	<%
		for (Map<String, Object> dict : list) {
			text = (String) dict.get("text");
			String infix = (String) dict.get("infix");
			out.print(Jsp.createSyntaxEditor(text, infix, (boolean) (Boolean) dict.get("training")));
		}
	%>
	<input type=submit name='<%=table%>_<%=lang%>_submit' value=submit>
</form>
<script>
	fill_tbl_syntax();
</script>