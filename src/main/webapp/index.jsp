<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="com.servlet.Jsp"%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="index.html"%>
<%
	request.setCharacterEncoding("utf-8");
%>

<script>
	var table = '${param.table}';
	if (table) {
		var lang = '${param.lang}';
		onchange_table(table, lang);
	}

	var javaScript = '${param.javaScript}';
	if (javaScript) {
		console.log("executing javaScript: " + javaScript);
		eval(javaScript);
	}
</script>

<%
	String[] link = Jsp.getParameterNames(request, "submit|(.+training)");
	System.out.println(String.join(", ", link));
	if (link.length != 1)
		return;
	String task = link[0];
	String jsp;
	System.out.println("task = " + task);

	if (task.equals("submit_query")) {
		task = request.getParameter("table");
		System.out.println("task = " + task);
	} else {
		Matcher m = Pattern.compile("(.*)submit").matcher(task);
		if (m.find()) {
			String args[] = m.group(1).split("_");
			task = args[0] + "_submit";
			String lang = args[1];
			request.setAttribute("lang", lang);
		}
	}
	jsp = String.format("jsp/%s.jsp", task);
	System.out.println("jsp = " + jsp);	
%>
<jsp:include page="<%=jsp%>" flush='true' />
