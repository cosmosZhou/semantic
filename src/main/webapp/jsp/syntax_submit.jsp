<%@page import="com.servlet.Jsp"%>
<%@ page import="com.util.MySQL"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>

<%
	String lang = (String) request.getAttribute("lang");
	out.print(Jsp.join(MySQL.instance.insert("tbl_syntax_" + lang, request.getParameterValues("text"),
			request.getParameterValues("infix"), request.getParameterValues("training"))));
	
	out.print(Jsp.javaScript("onchange_table('syntax', '%s')", lang));
%>
<br>
model configuration:
<form name=form method=post>
	<br>epochs = <input type=text name=epochs value=5
		onkeyup='input_positive_integer(this)'
		onafterpaste='input_positive_integer(this)'> <br>batch_size
	= <input type=text name=batch_size value=256
		onkeyup='input_positive_integer(this)'
		onafterpaste='input_positive_integer(this)'> <br>limit =
	<input type=text name=limit value=0
		onkeyup='input_positive_integer(this)'
		onafterpaste='input_positive_integer(this)'> <br>training
	= <input type=text name=training value=1
		onkeyup='input_nonnegative_integer(this)'
		onafterpaste='input_nonnegative_integer(this)'> <br>pretraining_weight
	= <input type=text name=pretraining_weight value=0.5
		onkeyup='input_nonnegative_number(this)'
		onafterpaste='input_nonnegative_number'> <br> <input
		type=submit name=syntax_cn_training value=training>
</form>