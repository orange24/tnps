<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<%@ page import="java.util.*" %>
<%@ page import="org.springframework.web.servlet.LocaleResolver" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Thai Kikuwa Induntry Co., Ltd.</title>
<link rel="shortcut icon" type="image/png" href="image/favicon.png"/>
<script type="text/javascript">
<%
	String bundleName = "th.co.nttdata.errorcodes";
	RequestContext rq = new RequestContext(request);

	ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName);
	StringBuilder buffer = new StringBuilder();
	for( String key : resourceBundle.keySet() ) {
		buffer.append(",\n\t\t\"").append(key).append("\" : \"").append(rq.getMessage(key)).append("\"");
	}

	bundleName = "th.co.nttdata.messages";
	resourceBundle = ResourceBundle.getBundle(bundleName);
	for( String key : resourceBundle.keySet() ) {
		buffer.append(",\n\t\t\"").append(key).append("\" : \"").append(rq.getMessage(key)).append("\"");
	}
%>
	var messageCodes = { <%= buffer.substring(1) %>
	};

</script>
</head>

<frameset rows="114,*" cols="*" frameborder="NO" framespacing="0">
	<frame src="top.html" name="topFrame" scrolling="no" noresize="noresize" id="topFrame" title="topFrame" />
	<frameset id="masterFrameset" rows="*" cols="211,*,18" frameborder="NO" framespacing="0">
		<frame src="menu.html" name="leftFrame" scrolling="auto" noresize="noresize" id="leftFrame" title="leftFrame" />
		<frame src="main.html" name="mainFrame" id="mainFrame" title="mainFrame" frameborder="0" />
		<frame src="right.html" name="rightBG" scrolling="no" noresize="noresize" id="rightBG" title="rightBG" />
	</frameset>
</frameset>
</html>