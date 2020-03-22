<%@page import="com.util.Native"%>
<%@page import="com.servlet.Frontier"%>
<%@page import="java.util.Random"%>
<%@page import="com.util.HttpClient"%>
<%@page import="com.util.MySQL"%>
<%@page import="com.patsnap.core.analysis.manager.CarrotManager"%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
	String lang = request.getParameter("lang");
	String table = "tbl_keyword_" + lang;

	int rows = Integer.valueOf(request.getParameter("rows"));

	System.out.println("lang = " + lang);
	System.out.println("rows = " + rows);
	String text = request.getParameter("text");
	if (text != null && !text.isEmpty()) {
		String solr_url = HttpClient.solr_keyword_address(lang, text, rows);
		out.print(Frontier.javaScript("mysql.text.value = '%s'", text));
%>

<a href="<%=solr_url%>">results acquired from solr engine:</a>
<br>
<br>
<form name=keyword method=post>
	<%
		Random rnd = new Random();

		for (String _text : CarrotManager.instance.getClusteringResult(lang, text, rows)) {
			int label = MySQL.instance.select_from(table, _text);
			boolean changed;
			if (label < 0) {
				if (lang.equals("cn")) {
					label = (int) Math.floor(Native.keywordCN(_text) * 2);
				} else if (lang.equals("en")) {
					label = (int) Math.floor(Native.keywordEN(_text) * 2);
				}
				changed = true;
			} else
				changed = false;

			out.print(Frontier.createKeywordEditor(_text, label, rnd.nextInt(2), changed));
		}
		out.print(String.format("<input type=submit name=submit_%s value=submit>", table));
	%>

</form>
<%
	}
%>
<script>
	var children = keyword.children;
	var columnSize = 5;
	console.log("children.length = " + children.length);
	for (var j = 1; j < children.length; j += columnSize) {
		var label = parseInt(children[j].value);
		console.log("label = " + label);
		if (label == 0) {
			children[j].style.color = 'red';
		}
	}
</script>