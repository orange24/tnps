<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
var boxRoleName;
var boxRoleDesc;
var menuChkBox;
var commandChkBox;
var btnSave;
var btnDelete;
var btnBack;
var rolS02Form;
var hidResult;
var hidCreateDate;
var save = "save";

function jqCheckAll(id, name){$("."+name).attr('checked', $('#' + id).is(':checked'));}

function fnCheckInput(){
	
	var i=0;
	var j=0;
	var msg = [];
	
	if (boxRoleName.val() == "") {
		msg.push({"code":"err.cmm.001","arguments":["Role Name",""]});
	}
	
	menuChkBox.each(function(){
		this.menuChkBox = $(this);
		if (this.menuChkBox.attr("checked"))i++;
	});
	if (i==0) msg.push({"code":"err.cmm.001","arguments":["Access Menu",""]});
		
	commandChkBox.each(function(){
		this.commandChkBox = $(this);
		if (this.commandChkBox.attr("checked"))j++;
	});
	if (j==0) msg.push({"code":"err.cmm.001","arguments":["Acess Command",""]});
	
	if (msg.length > 0) {
		message.setErrors(msg);
		return false;
	}
	
	if(confirm("<spring:message code='cfm.cmm.001'/>")){
		rolS02Form.attr("action","ROL_S02_save.html");
		rolS02Form.submit();
	}
}

function fnSave(){
	btnSave.click(function(){
		
		if (hidCreateDate.val()== "" ) {
			message.clear();
			var params = rolS02Form.serialize();
			postJSON("ROL_S02_check", params, function( result ){
				if( result == true) {
					message.setErrors([{"code":"err.cmm.011","arguments":["Role Name",""]}]);
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

function fnCheckRolName(){
	message.clear();
	var params = rolS02Form.serialize();
	postJSON("ROL_S02_check", params, function( result ){
		if( result == true) {
			message.setErrors([{"code":"err.cmm.011","arguments":["Role Name",""]}]);
			return;
		}
	});
}

function fnBack(){
	btnBack.click(function(){
		rolS02Form.attr("action","ROL_S01_search.html?button=back");
		rolS02Form.submit();
	});
}

function fnDelete(){
	btnDelete.click(function(){
		if (confirm("<spring:message code='cfm.cmm.002'/>")) {
			rolS02Form.attr("action","ROL_S02_delete.html");
			rolS02Form.submit();
		}
	});
}
//JQuery
//initialze script run
$(document).ready(function() {
	
	boxRoleName = $("#boxRoleName");
	boxRoleDesc = $("#boxRoleDesc");
	menuChkBox = $(".menuChkBox");
	commandChkBox = $(".commandChkBox");
	btnSave = $("#btnSave");
	btnDelete = $("#btnDelete");
	btnBack = $("#btnBack");
	rolS02Form = $("#rolS02Form");
	hidResult = $("#hidResult");
	hidCreateDate = $("#hidCreateDate");
	
	boxRoleName.focus();
	
	// <!-- check character textarea -->
	$("textarea").keyup(function(){
       var max = 500;
       if($(this).val().length > max){
          $(this).val($(this).val().substr(0, max));
       }
	});
	
	fnSave();
	fnBack();
	fnDelete();
	
	boxRoleName.keyup(function(){
		if (!boxRoleName.attr("readonly")) {
			fnCheckRolName();
	    }
	});
	boxRoleName.keypress(function(event){
		if (event.keyCode == 13) {
			return false;
		}
	});
	//<!-- complete save: disable all -->
	if (hidResult.val()== save) {
		$("input, select, textarea").attr("disabled", true);
	}
	
	//<!-- check for page edit: disable boxRoleName -->
	if (hidCreateDate.val()!= "" ) {
		boxRoleName.attr("readonly", true);
		boxRoleName.css("background-color", "#E8E8E8");
		boxRoleDesc.focus();
	}
	
});	
</script>
</head>
<body>
<form:form action="ROL_S02.html" method="post" commandName="mRole" id="rolS02Form">
<h1><spring:message code='menu.Role'/></h1>
<ul id="navlist">
	<li><a href="ROL_S01.html">Role List</a></li>	
	<li><a href="ROL_S02.html"  id="current">Role Add/Edit</a></li>		
</ul>

<page:message item="${mRole}" />

<table width="100%" border="0" cellpadding="3" cellspacing="1">
  <tr>
    <td colspan="2">
    	<input type="hidden" value="${result}" id="hidResult" />
    	<form:hidden path="createDate" id="hidCreateDate" />
      	<form:hidden path="lastUpdate" />
      	<input type="hidden" value="${mRole.roleId}" name="roleList[0].roleId" id="roleList[0].roleId" />
    	<table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
	        <tr>
	          <th width="12%" >Role Name</th>
	          <td width="23%" >
	          	<form:input path="roleName" id="boxRoleName" maxlength="20" />&nbsp;<span class="ui-state-error-text">*</span>
	          	<form:hidden path="roleId" />
	          </td>
	        </tr>
	        <tr>
	          <th >Role Description</th>
	          <td ><form:textarea path="roleDesc" id="boxRoleDesc" cols="45" rows="5" /></td>
	        </tr>
        	<tr>
          	  <th >Access Menu&nbsp;<span class="ui-state-error-text">*</span></th>
          	  <td >
          	  	<input type="checkbox" name="checkAllMenu" id="checkAllMenu" onclick="jqCheckAll( this.id, 'menuChkBox')" />Select All Menu
                <div style="height:120px; width:200px; overflow:auto; border:groove;" >
                	<c:forEach var="menu" varStatus="status" step="1" items="${menu}">
                		<input class="menuChkBox" type="checkbox" name="menuList[${status.count-1}].id" 
                		id="menuList[${status.count-1}].id" value="${menu.id}"
                		<c:if test="${menu.choose}">checked="checked"</c:if> />
                		${menu.label}<br/>
                	</c:forEach>
			    </div>
			  </td>
        	</tr>
        	<tr>
          	  <th >Access Command&nbsp;<span class="ui-state-error-text">*</span></th>
          	  <td >
          	  	<input type="checkbox" name="checkAllCommand" id="checkAllCommand" onclick="jqCheckAll( this.id, 'commandChkBox')" />Select All Command  
				<div style="height:120px; width:200px; overflow:auto; border:groove;">
					<c:forEach items="${command}" var="command" varStatus="st" step="1" >
				  		<input class="commandChkBox" type="checkbox" name="actionList[${st.count-1}].id" 
				  		name="actionList[${st.count-1}].id" value="${command.id}"
				  		<c:if test="${command.choose}">checked="checked"</c:if> />
				  		${command.label}<br/>
				  	</c:forEach>
				</div>
			  </td>
        	</tr>
        	<c:if test="${not empty mRole.createDate}">
	        	<tr>
		          <th >Create Date</th>
		          <td ><fmt:formatDate value="${mRole.createDate}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
		        </tr>
		        <tr>
		          <th >Last Update</th>
		          <td ><fmt:formatDate value="${mRole.lastUpdate}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
		        </tr>
	        </c:if>
		</table>
    </td>
  </tr>
  <tr>
  	<td align="left">
     <c:choose>
      	<c:when test="${not empty mRole.createDate}">
	      <security:authorize ifAnyGranted="ROL_S01_EDIT"><input type="button" id="btnSave" value="Save"/></security:authorize>
	      <security:authorize ifAnyGranted="ROL_S01_DELETE">
	      	<c:if test="${!mRole.isLocked}">
	      		<input type="button" id="btnDelete" value="Delete"/>
	      	</c:if>
	      </security:authorize>
	      <input type="button" id="btnBack" value="Back"/>
      	</c:when>
      	<c:otherwise>
	      <security:authorize ifAnyGranted="ROL_S02_SAVE"><input type="button" id="btnSave" value="Save"/></security:authorize>
      	</c:otherwise>
      </c:choose>
    </td>
    <td align="right"><span class="ui-state-error-text">*&nbsp;Required Field</span></td>
  </tr>
</table>
</form:form>
</body>
</html>
