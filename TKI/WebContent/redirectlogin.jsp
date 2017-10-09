<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script>
	<% if ("2".equals(request.getParameter("login_error"))) {%>
		parent.location = "<%=request.getContextPath()%>/timeout.jsp";
	<% } else { %>
		parent.location = "<%=request.getContextPath()%>/login.jsp";
	<% } %>
</script>
<style>
</style>
</head>
<body>
</body>
</html>