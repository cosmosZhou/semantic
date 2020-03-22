<%@page import="com.servlet.Frontier"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="solr.html"%>
<%
	request.setCharacterEncoding("utf-8");

	if (request.getParameter("submit_query") != null) {
		String text = request.getParameter("text");
		out.print(Frontier.javaScript("mysql.text.value = '%s'", text));
%>
<jsp:include page="jsp/acquire_keyword_from_carrot2.jsp" flush="true" />

<%
	} else if (request.getParameter("submit_tbl_keyword_cn") != null) {
%>
<jsp:include page="jsp/submit_tbl_keyword_cn.jsp" flush="true" />
<a href="index.jsp?table=tbl_keyword_cn">select * from
	tbl_keyword_cn;</a>
<%
	} else if (request.getParameter("submit_tbl_keyword_en") != null) {
%>
<jsp:include page="jsp/submit_tbl_keyword_en.jsp" flush="true" />
<br>
<a href="index.jsp?table=tbl_keyword_en">select * from
	tbl_keyword_en;</a>
<%
	}
%>

<script>
	var lang = '${param.lang}'.toLowerCase();
	if (lang) {
		mysql.lang.value = lang;
	}
</script>

