<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
	<title></title>
<%@ include file="../importResources.jsp" %>
<script type="text/javascript">
	var userForm;
	var userName;
	var oldPassword;
	var newPassword;
	var chkPassword;

	$(document).ready(function(){
		userForm    = $("form#userForm");
		userName    = $("input[name='userName']");
		oldPassword = $("input[name='oldPassword']");
		newPassword = $("input[name='newPassword']");
		chkPassword = $("input[name='chkPassword']");

		userForm.submit(function(){

			var usrNme = userName.val();
			var oldPwd = oldPassword.val();
			var newPwd = newPassword.val();
			var chkPwd = chkPassword.val();
			var params = "";
			var errors = [];

			// <!-- VALIDATION: Check if invalid. -->
			if( oldPwd.length == 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["Old Password"]});
			if( newPwd.length == 0 ){
				errors.push({"code": "err.cmm.001", "arguments": ["New Password"]});
			}else{
				if((newPwd.length < 4)||(newPwd.length > 20)){
					errors.push({"code": "err.usr.004", "arguments": [""]});
				}
			}
			if(chkPwd.length == 0 ){
				errors.push({"code": "err.cmm.001", "arguments": ["Retype New Password"]});
			}
			if( oldPwd == newPwd )
				errors.push({"code": "err.usr.005", "arguments": [""]});
			if( chkPwd != newPwd )
				errors.push({"code": "err.usr.001", "arguments": [""]});
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}

			message.clear();

			params = $.param({
				"userName" : usrNme,
				"password" : newPwd,
				"oldPswrd" : oldPwd
			});
			postJSON("CMM_S02_save", params, function( result ){
				if( result.errors && result.errors.length > 0 ) {
					message.setErrors(result.errors);
					return;
				}

				$("input, select, textarea").attr("disabled", true);

				message.setInfos ( result.infos  );
			});
			return false;
		});
	});
</script>
</head>
<body>
	<h1>Change Password</h1>
	<page:message item="${user}" />

	<form:form id="userForm" commandName="user">
	<table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
	<tr>
		<th width="200">User Name</th>
		<td>
			${user.userName}
			<form:hidden path="userName"/>
		</td>
	</tr>
	<tr>
		<th>Old Password<span class="ui-state-error-text">*</span></th>
		<td>
			<input type="password" name="oldPassword"/>
		</td>
	</tr>
	<tr>
		<th>New  Password<span class="ui-state-error-text">*</span></th>
		<td>
			<input type="password" name="newPassword"/>
		</td>
	</tr>
	<tr>
		<th class="label">Retype New Password<span class="ui-state-error-text">*</span></th>
		<td>
			<input type="password" name="chkPassword"/>
		</td>
	</tr>	
	</table>
	<table width="100%">
		<tr align="right">
			<td align="left"><input type="submit" value="Save"/></td>
			<td align="right"><span class="ui-state-error-text">*</span> Required Field</td>
		</tr>
	</table>
	</form:form>

</body>
</html>
