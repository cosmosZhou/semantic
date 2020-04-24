<%@page import="com.util.Native"%>
<%@page import="com.servlet.Python"%>
<%@ page import="com.util.MySQL"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>

<%
	String epochs = request.getParameter("epochs");
	out.print("<br>epochs = " + epochs);

	String batch_size = request.getParameter("batch_size");
	out.print("<br>batch_size = " + batch_size);

	String limit = request.getParameter("limit");
	out.print("<br>limit = " + limit);

	String training = request.getParameter("training");
	out.print("<br>training = " + training);

	String pretraining_weight = request.getParameter("pretraining_weight");
	out.print("<br>pretraining_weight = " + pretraining_weight);

	out.print("<br>training report:<br>");

	String result = Python.training("tbl_segment_cn", epochs, batch_size, limit, training, pretraining_weight);
	out.print("<br>" + result);
	out.print("<br>press enter to view the discrepant training / testing instances!");
	synchronized (Native.class) {
		Native.reinitializeCWSTagger();
	}
%>

<script>
	changeTable('tbl_segment_cn');
	mysql.rand.checked = true;
	mysql.training.value = 2;
	mysql.limit.value = 256;
</script>
