<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page
	import="com.patsnap.core.analysis.manager.CarrotManager.ClusteringResult"%>
<%@page import="com.util.Native"%>
<%@page import="com.servlet.Jsp"%>
<%@page import="java.util.Random"%>
<%@page import="com.util.HttpClient"%>
<%@page import="com.util.MySQL"%>
<%@page import="com.patsnap.core.analysis.manager.CarrotManager"%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
	String texts[] = request.getParameterValues("text");
	if (texts.length > 1) {
		String langs[] = request.getParameterValues("lang");
		String rows[] = request.getParameterValues("rows");

		System.out.println(String.join(", ", texts));
		out.print(Jsp.javaScript("for (var i = 1; i < %d; ++i) add_solr_item();", texts.length));

		for (int i = 0; i < texts.length; ++i) {
			out.print(Jsp.javaScript("mysql.children[1 + %d].children[0].value = '%s';", i, langs[i]));
			out.print(Jsp.javaScript("mysql.children[1 + %d].children[1].value = '%s';", i, texts[i]));
			out.print(Jsp.javaScript("mysql.children[1 + %d].children[2].value = '%s';", i, rows[i]));
		}
		out.print(Jsp.javaScript("concurrency_test()"));
		return;
	}

	String text = request.getParameter("text");
	if (text != null && !text.isEmpty()) {
		String lang = request.getParameter("lang");
		String table = "keyword_" + lang;

		int rows = Integer.valueOf(request.getParameter("rows"));

		System.out.println("lang = " + lang);
		System.out.println("rows = " + rows);

		out.print(Jsp.javaScript("mysql.text.value = '%s'", text));

		ClusteringResult result = CarrotManager.instance.getClusteringResult(lang, text, rows);
		out.print("time cost in solr (in seconds) = " + result.solr_duration + "<br>");
		out.print("time cost in clustering (in seconds) = " + result.clustering_duration + "<br>");
		out.print("time cost in postprocessing (in seconds) = " + result.postprocessing_duration + "<br>");
		out.print(
				"time cost in hyponym_detection (in seconds) = " + result.hyponym_detection_duration + "<br>");
		out.print("number of patents from solr = " + result.numFromSolr + "<br>");
		out.print("number of key phrases extracted = " + result.list.length + "<br>");
%>

<br>
<form name=keyword method=post>
	<%
		Random rnd = new Random();
			for (String _text : result.list) {
				List<Map<String, Object>> sqlResult = MySQL.instance
						.select_from("select label from tbl_%s_%s where text = '%s'", table, lang, _text);

				boolean changed;
				int label = -1;
				if (sqlResult.isEmpty()) {
					if (lang.equals("cn")) {
						label = Native.keywordCN(_text);
					} else if (lang.equals("en")) {
						label = Native.keywordEN(_text);
					}
					changed = true;
				} else {
					changed = false;
					label = (Integer) sqlResult.get(0).get("label");
				}

				out.print(String.format(
						"<div style='display:none;color:red;transform:translate(200%%, 0%%);position:absolute;zIndex:999999999999;'>%d documents found!</div>",
						result.map.get(_text).size()));

				String href;
				switch (lang) {
					case "cn" :
						href = String.format(
								"index.jsp?table=segment_cn&javaScript=add_segment_from_solr(\"%s\", %d)", _text,
								result.cache_index);
						break;
					case "en" :
						href = String.format("index.jsp?javaScript=add_segment_en_from_solr(\"%s\", %d)", _text,
								result.cache_index);
						break;
					default :
						href = "javaScript:void(0);";
				}

				href = String.format(
						"<a href='%s' onmouseover='onmouseover_solrText(this, event)' onmouseout='onmouseout_solrText(this, event)'>%s</a>",
						href, _text);
				out.print(Jsp.createKeywordEditor(_text, href, label, rnd.nextInt(2), changed));
			}
			out.print(String.format("<input type=submit name=%s_submit value=submit>", table));
	%>

</form>
<%
	}
%>
<script>
	var children = keyword.children;
	var columnSize = 7;
	console.log("children.length = " + children.length);
	for (var j = 3; j < children.length; j += columnSize) {
		var label = parseInt(children[j].value);
		console.log("label = " + label);
		if (label == 0) {
			children[j].style.color = 'red';
		}
	}
</script>