<%@page import="com.patsnap.core.analysis.manager.StopWordManager"%>
<%@page import="com.servlet.Jsp"%>
<%@ page import="com.util.MySQL"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>

<%
	String lang = (String) request.getAttribute("lang");
	String[] text = request.getParameterValues("text");
	String[] label = request.getParameterValues("label");
	String[] training = request.getParameterValues("training");
	
	assert text.length == label.length;
	assert text.length == training.length;
	out.print(Jsp.join(MySQL.instance.insert("tbl_keyword_" + lang, text, label, training)));
	out.print(Jsp.javaScript("onchange_table('keyword', '%s')", lang));

	switch (lang) {
	case "cn":
		for (int i = 0; i < label.length; ++i) {
			StopWordManager.Chinese.instance.put(text[i], Integer.parseInt(label[i]));
		}
		break;
	case "en":
		for (int i = 0; i < label.length; ++i) {
			StopWordManager.English.instance.put(text[i], Integer.parseInt(label[i]));
		}
		break;
	default:
		break;
	}
	if (request.getAttribute("jsp") == null) {
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
		onafterpaste='input_nonnegative_integer(this)'> <br>pretraining_weight
	= <input type=text name=pretraining_weight value=0.5
		onkeyup='input_nonnegative_number(this)'
		onafterpaste='input_nonnegative_number'><br> <input
		type=submit name=keyword_training value=training>
</form>


<%
	}
%>
