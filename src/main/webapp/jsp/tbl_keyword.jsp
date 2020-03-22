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
	int limit = Frontier.getLimit(request);
	int training = Frontier.getTraining(request);
	String text = request.getParameter("text");
	text = text.replace("\\", "\\\\");

	int label = Frontier.getInt(request, "label");
	String relation_text = request.getParameter("relation_text");

	String lines[] = {"mysql.text.value = '%s'", "mysql.relation_text.value = '%s'",
			"changeInputlength(mysql.relation_text, true)", "mysql.label.value = %d", "mysql.limit.value = %d",
			"mysql.training.value = %d"};

	out.print(Frontier.javaScript(String.join(";", lines), text, relation_text, label, limit, training));

	String lang = table.split("_")[2];

	System.out.println("limit = " + limit);

	String sql;
	List<String> conditions = new ArrayList<String>();
	if (!text.isEmpty()) {
		conditions.add(Frontier.process_text("text", relation_text, text));
	}

	if (label >= 0) {
		if (cmd.equals("update"))
			conditions.add("label != " + label);
		else
			conditions.add("label = " + label);
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

	if (cmd.equals("update")) {
		sql = String.format("%s %s set label = %s %s", cmd, table, label, condition);
	} else {
		sql = String.format("%s from %s %s", cmd, table, condition);
	}

	System.out.println(sql);
	if (cmd.equals("delete")) {
		out.print(sql);
		MySQL.instance.execute(sql);
		return;
	}

	if (cmd.equals("update")) {
		out.print(sql);
		sql = String.format("select* from %s %s", table, condition);
	}

	List<Map<String, Object>> list;
	if (discrepant) {
		MySQL.Filter filter;
		switch (lang) {
			case "cn" :
				filter = new MySQL.Filter() {
					public boolean sift(ResultSet res) throws SQLException {
						return res.getInt("label") == (int) (Native.keywordCN(res.getString("text")) * 2);
					}
				};
				break;
			case "en" :
				filter = new MySQL.Filter() {
					public boolean sift(ResultSet res) throws SQLException {
						return res.getInt("label") == (int) (Native.keywordCN(res.getString("text")) * 2);
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

	if (!discrepant && !cmd.equals("update")) {		
		out.print(String.format("<p ondblclick='mysql_execute(this)'>%s</p>", sql));
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

			out.print(Frontier.createKeywordEditor(text, label, training, changed));
		}
	%>
	<input type=submit name='submit_<%=table%>' value=submit>
</form>
<script>
	fill_tbl_keyword();
</script>