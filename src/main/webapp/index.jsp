<%@ page import="com.servlet.Jsp"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="index.html"%>
<%
	request.setCharacterEncoding("utf-8");
%>

<script>
	var table = '${param.table}'.toLowerCase();
	if (table) {
		changeTable(table);
	}

	var javaScript = '${param.javaScript}';
	if (javaScript) {
		console.log("executing javaScript: " + javaScript);
		eval(javaScript);
	}
</script>

<%
	if (request.getParameter("submit_query") != null) {
		String table = request.getParameter("table");

		String table_jsp = "jsp/";
		String segment[] = table.split("_");
		if (segment.length == 3) {
			table_jsp += String.join("_", segment[0], segment[1]) + ".jsp";
		} else {
			table_jsp += table + ".jsp";
		}
		System.out.println("table_jsp = " + table_jsp);
%>
<jsp:include page='<%=table_jsp%>' flush='true' />
<%
	out.print(Jsp.javaScript("mysql.table.value = '%s'", table));
	} else if (request.getParameter("tbl_keyword_cn_submit") != null) {
		request.setAttribute("lang", "cn");
%>
<jsp:include page="jsp/tbl_keyword_submit.jsp" flush="true" />
<%
	} else if (request.getParameter("tbl_keyword_en_submit") != null) {
		request.setAttribute("lang", "en");
%>
<jsp:include page="jsp/tbl_keyword_submit.jsp" flush="true" />
<%
	} else if (request.getParameter("tbl_keyword_training") != null) {
%>
<jsp:include page="jsp/tbl_keyword_training.jsp" flush="true" />
<%
	} else if (request.getParameter("tbl_segment_cn_submit") != null) {
%>
<jsp:include page="jsp/tbl_segment_submit.jsp" flush="true" />
<%
	} else if (request.getParameter("tbl_segment_cn_training") != null) {
%>
<jsp:include page="jsp/tbl_segment_training.jsp" flush="true" />
<%
	} else if (request.getParameter("tbl_paraphrase_cn_submit") != null) {
		request.setAttribute("lang", "cn");
%>
<jsp:include page="jsp/tbl_paraphrase_submit.jsp" flush="true" />
<%
	} else if (request.getParameter("tbl_paraphrase_en_submit") != null) {
		request.setAttribute("lang", "en");
%>
<jsp:include page="jsp/tbl_paraphrase_submit.jsp" flush="true" />
<%
	} else if (request.getParameter("tbl_paraphrase_training") != null) {
%>
<jsp:include page="jsp/tbl_paraphrase_training.jsp" flush="true" />
<%
	}
%>

