<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Thai Kikuwa Induntry Co., Ltd.</title>
<%@ include file="page/importResources.jsp" %>
<script language="javascript">
//JQuery
var username;
var password;
var btnLogin;
var btnCancel;

$(document).ready(function(){
	var secMsg = '${SPRING_SECURITY_LAST_EXCEPTION.message}';
	if (secMsg) {
		message.errMessages.append( $("<li/>").html(secMsg) );
		message.elmMessages.css("display", "");
	}
	//message.setError();
	username = $("#username");
	password = $("#password");
	btnLogin = $("#btnLogin");
	btnCancel = $("#btnCancel");
	
	btnLogin.click(function(){
		var errors = [];
		if( !username.val() )
			errors.push({"code": "err.cmm.001", "arguments": ["Username"]});
		if( !password.val() )
			errors.push({"code": "err.cmm.001", "arguments": ["Password"]});
		if( errors.length > 0 ) {
			message.setErrors(errors);
			return false;
		}

		message.clear();
		return true;
	});
	
	$("form").submit(function(){
		hideLoading();
		return message.isNoError();
	});
	
	btnCancel.click(function(){
		message.clear();
	});
			
});
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>
<body>

	<div style="border: #000 solid 1px; position: absolute; top: 0; right: 0; bottom: 0; left: 0; background:url('image/login/bg.png'); background-repeat: no-repeat;">

	<form id="loginForm" name="loginForm" action="j_spring_security_check" method="post">

		<table width="300" border="0" cellpadding="3" cellspacing="0" style="margin: 180px auto;">
		<tr>
			<td colspan="2">
				<img src="image/login/logo.gif" alt="" width="246" height="33"/>
				<br/>
				<br/>
				<page:message item="${beanInput}" />
			</td>
		</tr>
		<tr>
			<td width="80" align="right"><strong>Username :</strong></td>
			<td align="left">
				<input name="j_username" id="username" type="text" size="25"/>
			</td>
		</tr>
		<tr>
			<td align="right"><strong>Password : </strong></td>
			<td align="left">
				<input name="j_password" id="password" type="password" size="25"/>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<br/>
				<br/>
				<input id="btnLogin" type="submit" value="Login"/>
				&nbsp;
				<input id="btnCancel" type="reset" value="Cancel"/>
			</td>
		</tr>
		</table>

	</form>

	<div align="right" style="position: absolute; right: 0; bottom: 0;">
		&copy; 2010 Thai Kikuwa Industry Co., Ltd.<br/>
		Power By NTTDATA (Thailand) Co., Ltd.<br/>
		<strong><spring:message code="softwareVersion"/></strong>
	</div>

	</div>

</body>
</html>
