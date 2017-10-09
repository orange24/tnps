<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script src="${pageContext.request.contextPath}/script/plugin/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$.get("${pageContext.request.contextPath}/j_spring_security_logout", null, function() {
		//alert("logout");
	});
</script>
</head>
<body>
	System error. Please contact System Administrator for support.<br>
	Go to system again. >> <a href="${pageContext.request.contextPath}">Link</a>
</body>
</html>