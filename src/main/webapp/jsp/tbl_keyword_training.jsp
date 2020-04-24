<%@page import="com.util.Native"%>
<%@page import="com.servlet.Jsp"%>
<%@page import="com.servlet.Python"%>
<%@ page import="com.util.MySQL"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>

<%
	String lang = request.getParameter("lang");
	System.out.println("lang = " + lang);

	String epochs = request.getParameter("epochs");
	System.out.println("epochs = " + epochs);

	String batch_size = request.getParameter("batch_size");
	System.out.println("batch_size = " + batch_size);

	String training = request.getParameter("training");
	System.out.println("training = " + training);

	String pretraining_weight = request.getParameter("pretraining_weight");
	System.out.println("pretraining_weight = " + pretraining_weight);

	String limit = request.getParameter("limit");
	System.out.println("limit = " + limit);

	out.print("<br>epochs = " + epochs);
	out.print("<br>batch_size = " + batch_size);
	out.print("<br>limit = " + limit);
	out.print("<br>training = " + training);
	out.print("<br>pretraining_weight = " + pretraining_weight);
	out.print("<br>training report:<br>");

	String result = Python.training("tbl_keyword_" + lang, epochs, batch_size, limit, training, pretraining_weight);
	out.print("<br>" + result);
	out.print("<br>press enter to view the discrepant training / testing instances!");
	synchronized (Native.class) {
		switch (lang) {
			case "en" :
				Native.reinitializeKeywordEN();
				break;
			case "cn" :
				Native.reinitializeKeywordCN();
				break;
			default :
				break;
		}

	}

	out.print(Jsp.javaScript("changeTable('tbl_keyword_%s')", lang));
%>

<script>
	mysql.training.value = 2;
	mysql.label.value = 1;
	mysql.rand.checked = true;
	mysql.limit.value = 256;
	//	mysql.submit();
</script>
