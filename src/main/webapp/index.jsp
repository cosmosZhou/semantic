<%@page import="com.servlet.Frontier"%>
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
	out.print(Frontier.javaScript("mysql.table.value = '%s'", table));
	} else if (request.getParameter("submit_tbl_keyword_cn") != null) {
%>
<jsp:include page="jsp/submit_tbl_keyword_cn.jsp" flush="true" />
<%
	} else if (request.getParameter("submit_tbl_keyword_en") != null) {
%>
<jsp:include page="jsp/submit_tbl_keyword_en.jsp" flush="true" />
<%
	} else if (request.getParameter("submit_tbl_segment_cn") != null) {
%>
<jsp:include page="jsp/submit_tbl_segment_cn.jsp" flush="true" />
<%
	}
%>

