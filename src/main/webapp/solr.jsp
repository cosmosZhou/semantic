<%@page import="com.servlet.Jsp"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="solr.html"%>
<%
	request.setCharacterEncoding("utf-8");

	if (request.getParameter("submit_query") != null) {
%>
<jsp:include page="jsp/acquire_keyword_from_carrot2.jsp" flush="true" />
<%
	} else if (request.getParameter("keyword_cn_submit") != null) {
		request.setAttribute("lang", "cn");
		request.setAttribute("origin", "solr");
%>
<jsp:include page="jsp/keyword_submit.jsp" flush="true" /><br>
<a href="index.jsp?table=keyword_cn">select * from
	tbl_keyword_cn;</a>
<%
	} else if (request.getParameter("keyword_en_submit") != null) {
		request.setAttribute("lang", "en");
		request.setAttribute("origin", "solr");
%>
<jsp:include page="jsp/keyword_submit.jsp" flush="true" /><br>
<a href="index.jsp?table=keyword_en">select * from
	tbl_keyword_en;</a>
<%
	}
%>

<script>
	var lang = '${param.lang}'.toLowerCase();
	if (lang) {
		mysql.lang.value = lang;
	}

	var text = '${param.text}';
	if (text) {
		mysql.text.value = text;
	}

	mysql.text.focus();
</script>

