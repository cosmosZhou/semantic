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
<%@page import="com.servlet.Frontier"%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
	//	request.setCharacterEncoding("utf-8");
	String cmd = request.getParameter("cmd");
	String table = request.getParameter("table");
	int limit = Frontier.getLimit(request);
	int training = Frontier.getTraining(request);
	String text = request.getParameter("text");
	String seg = request.getParameter("seg");
	String replacement = request.getParameter("replacement");

	String relation_text = request.getParameter("relation_text");
	String relation_seg = request.getParameter("relation_seg");

	String lines[] = {"mysql.cmd.value = '%s'", "mysql.text.value = '%s'", "mysql.relation_text.value = '%s'",
			"changeInputlength(mysql.relation_text, true)", "mysql.seg.value = '%s'",
			"mysql.training.value = %d", "mysql.limit.value = %s",
			"if (mysql.cmd.value != 'select*') onchangeTable(mysql.cmd)",
			"if (mysql.cmd.value == 'update') mysql.replacement.value = '%s'"};

	out.print(Frontier.javaScript(String.join(";", lines), cmd, Utility.quote_js(text), relation_text,
			Utility.quote_js(seg), training, limit < 0 ? "" : String.valueOf(limit),
			replacement != null ? Utility.quote_js(replacement) : null));

	String lang = table.split("_")[2];

	System.out.println("limit = " + limit);

	String sql;
	List<String> conditions = new ArrayList<String>();
	if (!text.isEmpty()) {
		conditions.add(Frontier.process_text("text", relation_text, text));
	}

	StringMatcher matcher = null;
	if (!seg.isEmpty()) {
		if (relation_seg != null)
			conditions.add(Frontier.process_text("seg", relation_seg, seg));
		else {
			String old;
			if (!replacement.isEmpty()) {
				old = seg;
			} else if (seg.contains("-")) {
				old = "(^|(?<=\\W))" + seg.replace('-', ' ') + "($|(?=\\W))";
				replacement = "(^|(?<=\\W))" + seg.replace("-", "") + "($|(?=\\W))";
			} else if (seg.contains("|")) {
				old = "(?<=\\w\\w)(" + seg + ")|(?<=" + seg + ")(\\w\\w)";
				replacement = " $1$2";
			} else {
				old = null;
				replacement = null;
			}
			System.out.println("old = " + old);
			System.out.println("replacement = " + replacement);
			matcher = new StringMatcher(old, replacement);

			conditions.add(Frontier.process_text("seg", "regexp", matcher.seg));
		}
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

	if (!discrepant && limit >= 0)
		condition += "limit " + limit;

	switch (cmd) {

		case "update" :

			if (matcher != null) {
				if (matcher.text != null) {
					sql = String.format(
							"%s %s set text = regexp_replace(text, '%s', '%s'), seg = regexp_replace(seg, '%s', '%s') %s",
							cmd, table, Utility.quote_mysql(matcher.text),
							Utility.quote_mysql(matcher.text_replacement), Utility.quote_mysql(matcher.seg),
							Utility.quote_mysql(matcher.seg_replacement), condition);
				} else {
					sql = String.format("%s %s set seg = regexp_replace(seg, '%s', '%s') %s", cmd, table,
							Utility.quote_mysql(matcher.seg), Utility.quote_mysql(matcher.seg_replacement),
							condition);

					out.print(String.format("<p class=update ondblclick='mysql_execute(this)'>%s</p>", sql));
					sql = String.format("select* from %s %s", table, condition);
				}
			} else {
				cmd = "select*";
				sql = String.format("%s from %s %s", cmd, table, condition);
			}

			break;
		case "delete" :
			sql = String.format("%s from %s %s", cmd, table, condition);
			out.print(String.format("<p class=update ondblclick='mysql_execute(this)'>%s</p>", sql));
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
					public boolean sift(ResultSet res) throws SQLException {
						String text = res.getString("text").replace(" ", "");
						String seg = res.getString("seg").replace(" ", "");
						return text.equals(seg);
						//						return res.getString("seg").equals(Native.segmentCN(res.getString("text")));
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
		out.print(String.format("<p class=select ondblclick='mysql_execute(this)'>%s</p>", sql));
	}
	out.print("count(*) = " + list.size());
%>

<div>
	insert into
	<%=table%>(text, seg) values( <input type=button
		onClick='add_segment_item(this.parentElement.nextElementSibling, "")'
		value=text> / <input id=syntax_file name=syntax_file type=file
		onchange='handleFiles(this, add_segment_item)' value='text'
		accept='.txt' style='width: 5em;' title=''>, ...)<br> <br>
</div>
<form name=form method="post" onsubmit="onsubmit_segment(this)">
	<%
		boolean changed = matcher != null;
		boolean deleted = cmd.equals("delete");
		switch (cmd) {
			case "update" :
				for (Map<String, Object> dict : list) {
					text = (String) dict.get("text");
					seg = (String) dict.get("seg");
					boolean bTraining = (boolean) (Boolean) dict.get("training");

					String[] seg_result = matcher.transform(seg);
					if (matcher.text != null) {
						String text_replacement = matcher.transform_text(text);
						out.print(Frontier.createSegmentEditor(text_replacement, text, seg_result[0], seg_result[1],
								bTraining, changed));
					} else {
						out.print(Frontier.createSegmentEditor(text, seg_result[0], seg_result[1], bTraining,
								changed));
					}
				}
				break;
			case "delete" :
				for (Map<String, Object> dict : list) {
					out.print(Frontier.createSegmentEditor("", (String) dict.get("text"), (String) dict.get("seg"),
							null, (boolean) (Boolean) dict.get("training"), false));
				}
				break;
			default :
				for (Map<String, Object> dict : list) {
					out.print(Frontier.createSegmentEditor((String) dict.get("text"), (String) dict.get("seg"),
							(boolean) (Boolean) dict.get("training")));
				}

		}
	%>
	<input type=submit name='submit_<%=table%>' value=submit>
</form>
<script>
	fill_tbl_segment();
</script>