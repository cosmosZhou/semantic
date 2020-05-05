<%@page import="com.servlet.Jsp"%>
<%@ page import="com.util.MySQL"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>

<%
	String lang = (String) request.getAttribute("lang");
	out.print(Jsp.join(MySQL.instance.insert("tbl_lexicon_" + lang, request.getParameterValues("text"),
			request.getParameterValues("reword"), request.getParameterValues("label"),
			request.getParameterValues("training"))));

	out.print(Jsp.javaScript("onchange_table('lexicon', '%s')", lang));
	if (request.getAttribute("origin") == null) {
%>
<br>
model configuration:
<form name=form method=post>
	<input type=hidden name=lang value=<%=lang%>> <br>epochs =
	<input type=text name=epochs value=2
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
		onafterpaste='input_nonnegative_integer(this)'> <br>incremental
	<input type=checkbox name=incremental checked> <br> <input
		type=submit name=lexicon_training value=training>
</form>

<%
	}
%>
