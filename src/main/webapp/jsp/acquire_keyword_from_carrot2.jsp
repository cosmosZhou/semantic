<%@page import="java.util.Comparator"%>
<%@page import="java.util.ArrayList"%>
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
		out.print(Jsp.javaScript("for (var i = 1; i < %d; ++i) insert_solr_item();", texts.length));

		for (int i = 0; i < texts.length; ++i) {
			out.print(Jsp.javaScript("mysql.children[1 + %d].children[0].value = '%s';", i, langs[i]));
			out.print(Jsp.javaScript("mysql.children[1 + %d].children[1].value = '%s';", i, texts[i]));
			out.print(Jsp.javaScript("mysql.children[1 + %d].children[2].value = '%s';", i, rows[i]));
		}
		out.print(Jsp.javaScript("concurrency_test()"));
		return;
	}

	String text = request.getParameter("text");
	if (text == null || text.isEmpty()) {
		return;
	}

	String lang = request.getParameter("lang");
	String table = "keyword";

	int rows = Integer.valueOf(request.getParameter("rows"));

	System.out.println("lang = " + lang);
	System.out.println("rows = " + rows);

	out.print(Jsp.javaScript("mysql.text.value = '%s'", text));

	final ClusteringResult result = CarrotManager.instance.getClusteringResult(lang, text, rows);
	out.print("time cost in solr (in seconds) = " + result.solr_duration + "<br>");
	out.print("time cost in clustering (in seconds) = " + result.clustering_duration + "<br>");
	out.print("time cost in postprocessing (in seconds) = " + result.postprocessing_duration + "<br>");
	out.print("number of patents from solr = " + result.numFromSolr + "<br>");

	Random rnd = new Random();
	if (request.getAttribute("jsp").equals("WordCloud.jsp")) {
		out.print("number of key phrases extracted = " + result.list.length + "<br>");
%>

<br>
<form name=form method=post>
	<%
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
						"<span style='display:none;color:red;transform:translate(200%%, 0%%);position:absolute;zIndex:999999999999;'>%d documents found!</span>",
						result.map.get(_text).size()));

				String href;
				switch (lang) {
					case "cn" :
						href = String.format(
								"index.jsp?table=segment&lang=cn&javaScript=insert_segment_from_solr(\"%s\", %d)",
								_text, result.cache_index);
						break;
					case "en" :
						href = String.format("index.jsp?javaScript=insert_segment_en_from_solr(\"%s\", %d)", _text,
								result.cache_index);
						break;
					default :
						href = "javaScript:void(0);";
				}
				
				href = String.format(
						"<a href='%s' onmouseover='onmouseover_solrText(this.parentElement.previousElementSibling, event)' onmouseout='this.parentElement.previousElementSibling.style.display = \"none\"'>%s</a>",
						href, _text);
				out.print(Jsp.createKeywordEditor(_text, href, label, rnd.nextInt(2) == 1, changed));
			}
			out.print(String.format("<input type=submit name=%s_%s_submit value=submit>", table, lang));
	%>
</form>

<script>
	for (let div of form.children) {
		if (div.nodeName != 'DIV')
			continue;
				
		var label = div.children[1];
		if (parseInt(label.value) == 0) {
			label.style.color = 'red';
		}
	}
</script>
<%
	} else {
		out.print(
				"time cost in hyponym_detection (in seconds) = " + result.hyponym_detection_duration + "<br>");
		out.print("number of clusters aggregated = " + result.cluster.size() + "<br>");
%>
<form name=form method=post>
	<%
		ArrayList<String> list = new ArrayList<String>(result.cluster.keySet());

			list.sort(new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return Integer.compare(result.cluster.get(o2).size(), result.cluster.get(o1).size());
				}
			});

			for (String hypernym : list) {
				List<String> hyponyms = result.cluster.get(hypernym);
				out.print(hypernym + " : " + String.join(", ", hyponyms));
				for (String hyponym : hyponyms) {
					String x, y;
					if (hypernym.compareToIgnoreCase(hyponym) > 0) {
						y = hypernym;
						x = hyponym;
					} else {
						x = hypernym;
						y = hyponym;
					}

					List<Map<String, Object>> sqlResult = MySQL.instance.select_from(
							"select label from tbl_lexicon_%s where text = '%s' and derivant = '%s'", lang, x, y);

					boolean changed;
					String label = "";
					if (sqlResult.isEmpty()) {
						changed = true;
					} else {
						changed = false;
						label = (String) sqlResult.get(0).get("label");
					}

					out.print(Jsp.createLexiconEditor(x, y, label, rnd.nextInt(2) == 1, changed));
				}

			}
			out.print(String.format("<input type=submit name=lexicon_%s_submit value=submit>", lang));
	%>
</form>

<script>
	
	var lang = '<%=lang%>';
	
	for (let div of form.children) { 
		if (div.nodeName != 'DIV')
			continue;
		var text = div.children[0].value;
		var derivant = div.children[1].value;
		var label = div.children[2];
		
		if (div.lastChild.value.startsWith('+')){
			request_post('algorithm/lexicon', {lang: lang, text: text, derivant: derivant}).done((label=>
			res=> {
				label.value = res.label;
				console.log("res from Java = ");
				console.log(res);
				if (!res.changed){
					label.nextElementSibling.value = label.nextElementSibling.value.substr(1);
				}
			})(label));			
		}
		else{
			request_post('algorithm/lexicon', {lang: lang, text: text, derivant: derivant}).done((label=>
			res=> {
				console.log("res from Java = ");
				console.log(res);
				if (res != label.value){
					label.style.color = 'red';
				}
			})(label));
		}
	}
	
</script>
<%
	}
%>
