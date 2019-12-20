<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">

var btnSave;
var btnDelete;
var btnBack;
var usrS02Form;
var boxUserName;
var boxPwd;
var boxRePwd;
var boxFullName;
var boxEmail;
var selectRole;
var acessWip;
var hidResult;
var hidCreateDate;
var hidCurrentUser;
var chkboxPwd;

function jqCheckAll(id, name)
{
	$("."+name).attr('checked', $('#' + id).is(':checked'));
}

function fnDelete(){
	btnDelete.click(function(){
		if (boxUserName.val() == hidCurrentUser.val()) {
			message.setErrors([{"code":"err.usr.002","arguments":["",""]}]);
			return;
		}
		if( confirm("<spring:message code='cfm.cmm.002'/>") ){
			usrS02Form.attr("action","USR_S02_delete.html");
			/* usrS02Form.submit(); */
			document.getElementById("usrS02Form").submit();
		}
	});
}

function fnBack(){
	btnBack.click(function(){
		usrS02Form.attr("action","USR_S01_search.html?button=back");
		/* usrS02Form.submit(); */
		document.getElementById("usrS02Form").submit();
	});
}

function checkUsername(){
	var params = usrS02Form.serialize();
	postJSON("USR_S02_check", params, function( result ){
		if( result.userName == boxUserName.val()) {
			message.clear();
			message.setErrors([{"code":"err.cmm.011","arguments":["Username "+result.userName,""]}]);
		}else{
			message.clear();
		}
	});
}

function fnCheckInput(){
	var msg = [];
	var emailExp = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;
	var i=0;
	
	if (boxUserName.val() == "") {
		msg.push({"code":"err.cmm.001","arguments":["User Name",""]});
	}else if (boxUserName.val().length < 4) {
		msg.push({"code":"err.usr.003","arguments":["",""]});
	}
	
	if (hidCreateDate.val()== "" || chkboxPwd.attr("checked") == true) {
		if (boxPwd.val() == "") {
			msg.push({"code":"err.cmm.001","arguments":["Password",""]});
		}else if (boxPwd.val().length < 4) {
			msg.push({"code":"err.usr.004","arguments":["",""]});
		}
		
		if (boxRePwd.val() == "") {
			msg.push({"code":"err.cmm.001","arguments":["Retype Password",""]});
		}else if (boxPwd.val() != boxRePwd.val()) {
			msg.push({"code":"err.usr.001","arguments":["",""]});
		}
	}
	
	if (boxFullName.val() == "") {
		msg.push({"code":"err.cmm.001","arguments":["Full Name",""]});
	}
	
	if (boxEmail.val() != "" && !boxEmail.val().match(emailExp)) {
		msg.push({"code":"err.cmm.012","arguments":["Email",""]});
	}
	
	acessWip.each(function(){
		this.acessWip = $(this);
		if (this.acessWip.attr("checked"))i++;
	});
	if (i==0) {
		msg.push({"code":"err.cmm.001","arguments":["Acess Wip",""]});
	}
	
	if (selectRole.attr("selectedIndex") == 0) {
		msg.push({"code":"err.cmm.001","arguments":["Role",""]});
	}
	
	if (msg.length > 0) {
		message.setErrors(msg);
		return false;
	}else{
		message.clear();
	}
	
	if(confirm("<spring:message code='cfm.cmm.001'/>")){
		usrS02Form.attr("action","USR_S02_save.html");
		/* usrS02Form.submit(); */
		document.getElementById("usrS02Form").submit();
	}
}

function fnSave(){
	btnSave.click(function(){
		if (hidCreateDate.val()== "" ) {
			var params = usrS02Form.serialize();
			postJSON("USR_S02_check", params, function( result ){
				if( result.userName == boxUserName.val()) {
					message.clear();
					message.setErrors([{"code":"err.cmm.011","arguments":["Username "+result.userName,""]}]);
					return;
				}else{
					fnCheckInput();
				}
			});
		}else{
			fnCheckInput();
		}
	});
}

function changePwd(){
	chkboxPwd.click(function(){
		if (chkboxPwd.attr("checked") == true) {
			boxPwd.attr("disabled",false);
			boxPwd.css("background-color", "#FFFFFF");
			boxRePwd.attr("disabled",false);
			boxRePwd.css("background-color", "#FFFFFF");
			boxPwd.focus();
		}else{
			boxPwd.attr("disabled",true);
			boxPwd.css("background-color", "#E8E8E8");
			boxRePwd.attr("disabled",true);
			boxRePwd.css("background-color", "#E8E8E8");
		}
	});
}

//JQuery
//initialze script run
	$(document).ready(function() {
		
		btnSave = $("#btnSave");
		btnDelete = $("#btnDelete");
		btnBack = $("#btnBack");
		usrS02Form = $("#usrS02Form");
		boxUserName = $("#boxUserName");
		boxPwd = $("#boxPwd");
		boxRePwd = $("#boxRePwd");
		boxFullName = $("#boxFullName");
		boxEmail = $("#boxEmail");
		selectRole = $("#selectRole");
		acessWip = $(".acessWip");
		hidResult = $("#hidResult");
		hidCreateDate = $("#hidCreateDate");
		hidCurrentUser = $("#hidCurrentUser");
		chkboxPwd = $("#chkboxPwd");
		
		fnSave();
		fnDelete();
		fnBack();
		changePwd();
		boxUserName.focus();
		
		boxUserName.keyup(function(event){
			//inputText.filterInput(2,event,false);
			var $th = $(this);
			var max = $th.val().length;
		    $th.val( $th.val().replace(/[^a-zA-Z0-9]/g, function(str) {return $(this).val().substr(max-1, max);} ) );
		    if (!boxUserName.attr("readonly")) {
		    	checkUsername();
		    }
		});
		
		//<!-- complete save: disable all -->
		if (hidResult.val()=="save") {
			$("input, select, textarea").attr("disabled", true);
		}
		//<!-- check for page edit: disable boxUserName -->
		if (hidCreateDate.val()!="") {
			boxPwd.attr("disabled",true);
			boxPwd.css("background-color", "#E8E8E8");
			boxRePwd.attr("disabled",true);
			boxRePwd.css("background-color", "#E8E8E8");
			boxUserName.attr("readonly", true);
			boxUserName.css("background-color", "#E8E8E8");
			boxFullName.focus();
		}
	});	
</script>
</head>
<body>

<form:form id="usrS02Form" action="USR_S02.html" method="post" commandName="mUser" >
<h1><spring:message code='menu.User'/></h1>
<ul id="navlist">
	<li><a href="USR_S01.html">User Search/List</a></li>	
	<li><a href="USR_S02.html"  id="current">User Add/Edit</a></li>		
</ul>

<page:message item="${mUser}" />

<table width="100%" border="0" cellpadding="3" cellspacing="1">
  <tr>
    <td colspan="2">
      <input type="hidden" value="${result}" id="hidResult" />
      <form:hidden path="createDate" id="hidCreateDate"  />
      <form:hidden path="lastUpdate"  />
      <input type="hidden" value="${mUser.userName}" name="userList[0].userName" id="userList[0].userName" />
      <input type="hidden" value="${currentUser.userName}" id="hidCurrentUser" />
      <table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
        <tr>
          <th width="12%">User Name</th>
          <td width="23%" >
          	<form:input path="userName" id="boxUserName" maxlength="20" />&nbsp;<span class="ui-state-error-text">*</span>
          	<br/>4-20 characters (a-z, A-Z, 0-9) 
          </td>
        </tr>
        <tr>
          <th>Password</th>
          <td >
          	<form:password path="password" id="boxPwd" maxlength="20" />&nbsp;<span class="ui-state-error-text">*</span>
          	<c:if test="${not empty mUser.createDate}">
          		&nbsp;<input type="checkbox" id="chkboxPwd" />&nbsp;Change Password
          	</c:if>
          	<br/>4-20 characters
          </td>
        </tr>
        <tr>
          <th >Retype Password</th>
          <td ><input type="password" name="rePwd" id="boxRePwd" maxlength="20" />&nbsp;<span class="ui-state-error-text">*</span></td>
        </tr>
        <tr>
          <th >Full Name</th>
          <td ><form:input path="fullName" id="boxFullName" maxlength="100" />&nbsp;<span class="ui-state-error-text">*</span></td>
        </tr>
        <tr>
          <th >Staff Code</th>
          <td ><form:input path="staffCode" id="boxStaffCode" maxlength="20" /></td>
        </tr>
        <tr>
          <th >Email</th>
          <td ><form:input path="email" id="boxEmail" maxlength="50" /></td>
        </tr>
        <tr>
          <th >WIP</th>
          <td ><form:select path="wip" id="selWIP" items="${wipMap}" /></td>
        </tr>
        <tr>
          <th >Access WIP&nbsp;<span class="ui-state-error-text">*</span></th>
          <td >
         	<input type="checkbox" name="checkAll" id="checkAll" onclick="jqCheckAll( this.id, 'acessWip' )" />Select All WIP
			<div style="height:120px; width:400px; overflow:auto; border:groove;">
				<c:forEach items="${acessWip}" var="wip" varStatus="status" step="1">
					<input type="checkbox" name="accessWip[${status.count-1}].wip" class="acessWip"
						id="accessWip[${status.count-1}].wip" value="${wip.wip}" 
						<c:if test="${wip.accessWip}">checked="checked"</c:if> />
					&nbsp;${wip.wip} : ${wip.wipName}<br/>
				</c:forEach>
			</div>
		  </td>
        </tr>
        <tr>
          <th >Customer Responsible&nbsp;<span class="ui-state-error-text">*</span></th>
          <td >
         	<input type="checkbox" name="checkAllCust" id="checkAllCust" onclick="jqCheckAll( this.id, 'custResp' )" />Select All Customer
			<div style="height:120px; width:400px; overflow:auto; border:groove;">
				<c:forEach items="${customerMap}" var="customer" varStatus="status" step="1">
					<input type="checkbox" name="custList[${status.count-1}].customerId" class="custResp"
						id="custList[${status.count-1}].customerId" value="${customer.customerId}" 
						<c:if test="${customer.isCustomer}">checked="checked"</c:if> />
					&nbsp;${customer.customerCode} : ${customer.customerName}<br/>
				</c:forEach>
			</div>
		  </td>
        </tr>
        <tr>
          <th >Role</th>
          <td ><form:select path="roleId" items="${roleMap}" id="selectRole" />&nbsp;<span class="ui-state-error-text">*</span></td>
        </tr>
        <c:if test="${not empty mUser.createDate}">
        <tr>
          <th >Create Date</th>
          <td >
          	<fmt:formatDate value="${mUser.createDate}" pattern="dd/MM/yyyy HH:mm:ss"/>
          </td>
        </tr>
        <tr>
          <th >Last Update</th>
          <td >
          	<fmt:formatDate value="${mUser.lastUpdate}" pattern="dd/MM/yyyy HH:mm:ss"/>
          </td>
        </tr>
        </c:if>
    </table>
   </td>
  </tr>
  <tr>
    <td align="left">
      <c:choose>
      	<c:when test="${not empty mUser.createDate}">
	      <security:authorize ifAnyGranted="USR_S01_EDIT"><input type="button" id="btnSave" value="Save"  /></security:authorize>
	      <security:authorize ifAnyGranted="USR_S01_DELETE">
	      	<c:if test="${!mUser.isLocked}">
	      		<input type="button" id="btnDelete" value="Delete"  />
	      	</c:if>
	      </security:authorize>
          <input type="button" id="btnBack" value="Back"  />
      	</c:when>
      	<c:otherwise>
	      <security:authorize ifAnyGranted="USR_S02_SAVE"><input type="button" id="btnSave" value="Save"  /></security:authorize> 
      	</c:otherwise>
      </c:choose>
    </td>
    <td align="right"><span class="ui-state-error-text">* Required Field</span></td>
  </tr>
</table>
</form:form>
</body>
</html>
