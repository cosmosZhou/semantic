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

	String seg = request.getParameter("seg");
	if (seg == null)
		seg = "";
	String relation_seg = request.getParameter("relation_seg");

	String replacement = request.getParameter("replacement");

	int training = Jsp.getTraining(request);
	String rand = request.getParameter("rand");
	int limit = Jsp.getLimit(request);

	String seg_script = "mysql.seg.value = '%s';";
	if (relation_seg != null) {
		seg_script += String.format(
				"mysql.relation_seg.value = '%s'; changeInputlength(mysql.relation_seg, true)", relation_seg);
	}

	String lines[] = {"mysql.cmd.value = '%s'", "mysql.text.value = '%s'", "mysql.relation_text.value = '%s'",
			"changeInputlength(mysql.relation_text, true)", seg_script, "mysql.training.value = %d",
			"mysql.rand.checked = %s", "mysql.limit.value = %s",
			"if (mysql.cmd.value != 'select*') onchangeTable(mysql.cmd)",
			"if (mysql.cmd.value == 'update') mysql.replacement.value = '%s'"};

	out.print(Jsp.javaScript(String.join(";", lines), cmd, Utility.quote(text), relation_text,
			Utility.quote(seg), training, rand == null ? "false" : "true",
			limit < 0 ? "" : String.valueOf(limit), replacement != null ? Utility.quote(replacement) : null));

	String lang = table.split("_")[1];

	System.out.println("limit = " + limit);

	String sql;
	List<String> conditions = new ArrayList<String>();
	if (!text.isEmpty()) {
		conditions.add(Jsp.process_text("text", relation_text, text));
	}

	StringMatcher matcher = null;
	if (!seg.isEmpty()) {
		if (relation_seg != null)
			conditions.add(Jsp.process_text("seg", relation_seg, seg));
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
				old = seg;
				replacement = "";
			}
			System.out.println("old = " + old);
			System.out.println("replacement = " + replacement);
			matcher = new StringMatcher(old, replacement);

			conditions.add(Jsp.process_text("seg", "regexp", matcher.seg));
		}
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
				}
				out.print(String.format("<p class=update ondblclick='mysql_execute(this)'>%s</p>",
						Utility.str_html(sql)));
				sql = String.format("select* from tbl_%s %s", table, condition);
			} else {
				cmd = "select*";
				sql = String.format("%s from tbl_%s %s", cmd, table, condition);
			}

			break;
		case "delete" :
			sql = String.format("%s from tbl_%s %s", cmd, table, condition);
			out.print(String.format("<p class=delete ondblclick='mysql_execute(this)'>%s</p>",
					Utility.str_html(sql)));
			sql = String.format("select* from tbl_tbl_%s %s", table, condition);
			break;
		default :
			sql = String.format("%s from tbl_%s %s", cmd, table, condition);
	}

	List<Map<String, Object>> list;
	if (discrepant) {
		MySQL.Filter filter;
		switch (lang) {
			case "cn" :
				filter = new MySQL.Filter() {
					public Object sift(ResultSet res) throws SQLException {
						String text = res.getString("text");
						String seg = res.getString("seg");
						String _seg = String.join(" ", Native.segmentCN(text));
						if (seg.equals(_seg))
							return null;

						int length = Math.min(seg.length(), _seg.length());
						int i = 0;
						for (; i < length; ++i) {
							if (seg.charAt(i) != _seg.charAt(i)) {
								break;
							}
						}

						int j = i;

						if (seg.charAt(i) == ' ') {
							for (--i; i >= 0 && seg.charAt(i) != ' '; --i);
							for (; j < _seg.length() && _seg.charAt(j) != ' '; ++j);
						} else {
							for (; i >= 0 && seg.charAt(i) != ' '; --i);
							for (++j; j < _seg.length() && _seg.charAt(j) != ' '; ++j);
						}

						if (i < 0) {
							i = 0;
						}

						return Utility.toString(Utility.strlen(seg.substring(0, i)), ' ')
								+ _seg.substring(i, j);
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
	insert into tbl_<%=table%>(text, seg) values( <input type=button
		onClick='add_segment_item(this.parentElement.nextElementSibling, "")'
		value=text> / <input id=syntax_file name=syntax_file type=file
		onchange='handleFiles(this, add_segment_item)' value='text'
		accept='.txt' style='width: 5em;' title=''>, ...)<br> <br>
</div>
<%
	if (!list.isEmpty()) {
		out.print("count(*) = " + list.size());
%>
<form name=form method="post" onsubmit="onsubmit_segment(this)"
	class=monospace-form>
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
							out.print(Jsp.createSegmentEditor(text_replacement, text, seg_result[0], seg_result[1],
									bTraining, changed));
						} else {
							out.print(Jsp.createSegmentEditor(text, seg_result[0], seg_result[1], bTraining,
									changed));
						}
					}
					break;
				case "delete" :
					for (Map<String, Object> dict : list) {
						out.print(Jsp.createSegmentEditor("", (String) dict.get("text"), (String) dict.get("seg"),
								null, (boolean) (Boolean) dict.get("training"), false));
					}
					break;
				default :
					for (Map<String, Object> dict : list) {
						if (dict.containsKey("mark"))
							out.print(Jsp.createSegmentEditor((String) dict.get("text"), (String) dict.get("seg"),
									(String) dict.get("mark"), (boolean) (Boolean) dict.get("training"), false));
						else
							out.print(Jsp.createSegmentEditor((String) dict.get("text"), (String) dict.get("seg"),
									(boolean) (Boolean) dict.get("training")));
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
	fill_tbl_segment();
</script>
<%
	}
%>

