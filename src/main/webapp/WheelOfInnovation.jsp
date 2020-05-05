<%@page import="com.servlet.Jsp"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="solr.html"%>
<%
	request.setCharacterEncoding("utf-8");
	request.setAttribute("jsp", "WheelOfInnovation.jsp");
	if (request.getParameter("submit_query") != null) {
%>
<jsp:include page="jsp/acquire_keyword_from_carrot2.jsp" flush="true" />
<%
	} else if (request.getParameter("keyword_cn_submit") != null) {
		request.setAttribute("lang", "cn");
%>
<jsp:include page="jsp/keyword_submit.jsp" flush="true" /><br>
<a href="index.jsp?table=keyword&lang=cn">select * from
	tbl_keyword_cn;</a>
<%
	} else if (request.getParameter("keyword_en_submit") != null) {
		request.setAttribute("lang", "en");
%>
<jsp:include page="jsp/keyword_submit.jsp" flush="true" /><br>
<a href="index.jsp?table=keyword&lang=en">select * from
	tbl_keyword_en;</a>
<%
	} else if (request.getParameter("lexicon_en_submit") != null) {
		request.setAttribute("lang", "en");
%>
<jsp:include page="jsp/lexicon_submit.jsp" flush="true" /><br>
<a href="index.jsp?table=lexicon&lang=en">select * from
	tbl_lexicon_en;</a>
<%
	} else if (request.getParameter("lexicon_cn_submit") != null) {
		request.setAttribute("lang", "cn");
%>
<jsp:include page="jsp/lexicon_submit.jsp" flush="true" /><br>
<a href="index.jsp?table=lexicon&lang=cn">select * from
	tbl_lexicon_cn;</a>
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

