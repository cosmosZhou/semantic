<%@ page import="com.util.MySQL"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>

<a href="algorithm/training/tbl_keyword_cn">training</a><br>
<%
	String text[] = request.getParameterValues("text");
	String label[] = request.getParameterValues("label");
	String training[] = request.getParameterValues("training");

	out.print(MySQL.instance.insert("tbl_keyword_cn", text, label, training));
%>

<script>
	changeTable('tbl_keyword_cn');
</script>
