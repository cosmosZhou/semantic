<%@ page import="com.util.MySQL"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>

<a href="algorithm/training/tbl_segment_cn">training</a>
<br>
<%
	System.out.println("text = " + request.getParameterValues("text"));
	System.out.println("seg = " + request.getParameterValues("seg"));
	System.out.println("training = " + request.getParameterValues("training"));
	System.out.println("submit_tbl_segment_cn = " + request.getParameter("submit_tbl_segment_cn"));

	out.print(MySQL.instance.insert("tbl_segment_cn", request.getParameterValues("text"),
			request.getParameterValues("seg"), request.getParameterValues("training")));
%>

<script>
	changeTable('tbl_segment_cn');
</script>
