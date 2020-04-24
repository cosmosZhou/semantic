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
	int limit = Jsp.getLimit(request);
	int training = Jsp.getTraining(request);
	String text = request.getParameter("text");

	int label = Jsp.getInt(request, "label");
	String relation_text = request.getParameter("relation_text");
	String rand = request.getParameter("rand");

	String lines[] = {"mysql.cmd.value = '%s'", "if (mysql.cmd.value != 'select*') onchangeTable(mysql.cmd)",
			"mysql.text.value = '%s'", "mysql.relation_text.value = '%s'",
			"changeInputlength(mysql.relation_text, true)", "mysql.label.value = %d",
			"mysql.training.value = %d", "mysql.rand.checked = %s", "mysql.limit.value = %d"};

	out.print(Jsp.javaScript(String.join(";", lines), cmd, Utility.quote(text), relation_text, label, training,
			rand == null ? "false" : "true", limit));

	String lang = table.split("_")[2];

	System.out.println("limit = " + limit);

	String sql;
	List<String> conditions = new ArrayList<String>();
	if (!text.isEmpty()) {
		conditions.add(Jsp.process_text("text", relation_text, text));
	}

	boolean discrepant = false;
	if (training >= 0) {
		if (training == 2)
			discrepant = true;
		else
			conditions.add("training = " + training);
	}

	if (label >= 0) {
		if (cmd.equals("update"))
			conditions.add("label != " + label);
		else
			conditions.add("label = " + label);
	}

	String condition = conditions.isEmpty() ? "" : "where " + String.join(" and ", conditions) + " ";
	if (rand != null)
		condition += "order by rand() ";

	if (!discrepant)
		condition += "limit " + limit;

	if (cmd.equals("update")) {
		sql = String.format("%s %s set label = %s %s", cmd, table, label, condition);
	} else {
		sql = String.format("%s from %s %s", cmd, table, condition);
	}

	System.out.println(sql);
	if (!cmd.equals("select*")) {
		if (!discrepant)
			out.print(String.format("<p class=%s ondblclick='mysql_execute(this)'>%s</p>", cmd,
					Utility.str_html(sql)));
		sql = String.format("select* from %s %s", table, condition);
	}

	List<Map<String, Object>> list;
	if (discrepant) {
		MySQL.Filter filter;
		switch (lang) {
			case "cn" :
				filter = new MySQL.Filter() {
					public Object sift(ResultSet res) throws SQLException {
						double y_pred = Native.keywordCN(res.getString("text"));
						if (res.getInt("label") == y_pred)
							return null;
						return y_pred;
					}
				};
				break;
			case "en" :
				filter = new MySQL.Filter() {
					public Object sift(ResultSet res) throws SQLException {
						double y_pred = Native.keywordCN(res.getString("text"));
						if (res.getInt("label") == y_pred)
							return null;
						return y_pred;
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
		out.print("count(*) = " + list.size());
	}
%>

<div>
	insert into
	<%=table%>(text, label) values( <input type=button
		onClick='add_keyword_item(this.parentElement.nextElementSibling, "")'
		value=text> / <input id=keyword_file name=keyword_file
		type=file onchange='handleFiles(this, add_keyword_item)' value='text'
		accept='.txt' style='width: 5em;' title=''> / <a
		href='solr.jsp?lang=<%=lang%>'>solr</a>, ...)<br> <br>
</div>
<form name=keyword method=post>
	<%
		boolean changed = cmd.equals("update");
		for (Map<String, Object> dict : list) {
			text = (String) dict.get("text");
			training = (Integer) dict.get("training");

			if (!changed) {
				label = (Integer) dict.get("label");
			}

			out.print(Jsp.createKeywordEditor(text, label, training, changed));
		}
	%>
	<input type=submit name='<%=table%>_submit' value=submit>
</form>
<script>
	if (mysql.training.value != 2)
		fill_tbl_keyword();
</script>