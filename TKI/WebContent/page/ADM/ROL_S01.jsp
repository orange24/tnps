<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

var btnSearch;
var boxRoleName;
var rolS01Form;

function fnDelete( row ) {
	var rowNo = row.find("td:first-child").html().trim();
	if( !confirm("<spring:message code='cfm.cmm.003'/>".replace(/\{0\}/g, rowNo)) )
		return;

	rolS01Form.attr("action", "ROL_S01_delete.html");
	rolS01Form.append("<input type='hidden' name='roleList[0].roleId' value='"+ row.attr("id") +"'/>");
	rolS01Form.submit();
}

//JQuery
//initialze script run
$(document).ready(function() {
	
	boxRoleName = $("#boxRoleName");
	btnSearch 	= $("#btnSearch");
	rolS01Form 	= $("#rolS01Form");
	
	boxRoleName.focus();
	
	btnSearch.click(function(){
		rolS01Form.find("select[id=pageNumber]").attr("disabled", true);
		rolS01Form.attr("action","ROL_S01_search.html");
		rolS01Form.submit();
	});
	
	boxRoleName.keypress(function(event){
		if (event.keyCode == 13) {
			return false;
		}
	});
	
});	
</script>
</head>
<body>
<form:form id="rolS01Form" method="post" action="ROL_S01_search.html" commandName="searchCriteria">
	<h1><spring:message code='menu.Role'/></h1>
	<ul id="navlist">
		<li><a href="ROL_S01.html" id="current">Role List</a></li>	
		<li><a href="ROL_S02.html" >Role Add/Edit</a></li>
	</ul>
	<page:message item="${searchCriteria}" />
	<table width="100%" border="0" cellpadding="3" cellspacing="1">
		<tr>
			<td>
				<table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
				  	<tr>
				   		<th >Role Name</th>
			   			<td ><form:input path="roleName" id="boxRoleName"/></td>
				       	<td >&nbsp;</td>
				       	<td >&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right">
				<security:authorize ifAnyGranted="ROL_S01_SEARCH"><input type="button" id="btnSearch" value="Search" /></security:authorize>
			</td>
		</tr>
	</table>
	<br />
	<c:if test="${fn:length(searchCriteria.roleList) > 0}">
		<table width="100%" border="1" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
			<tr>
				<td colspan="4">
					<div style="float:left"><page:display item="${searchCriteria}" /></div>
					<div style="float:right"><page:number item="${searchCriteria}" /></div>
				</td>
			</tr>
			<tr >
				<th >No.</th>
				<th >Role Name</th>
				<th >Role Description</th>
				<th >Action</th>
			</tr>
		  	<c:forEach items="${searchCriteria.roleList}" var="role" varStatus="status" step="1" >
				<tr id="${role.roleId}" >
					<td align="center"><page:rowno item="${searchCriteria}" index="${status.index}"/></td>
					<td align="left">${role.roleName}&nbsp;</td>
					<td align="left">${role.roleDesc}&nbsp;</td>
					<td align="center" >
					 	<security:authorize ifAnyGranted="ROL_S01_EDIT">
					 		<a href="ROL_S02_edit.html?roleId=${role.roleId}"><img src="image/icon/update.gif" border="0"/></a>
					 	</security:authorize> 
					 	<security:authorize ifAnyGranted="ROL_S01_DELETE">
					 		<c:if test="${!role.isLocked}">
					  			<a href="javascript:void(0);" onclick="fnDelete( $(this).closest('tr') );" >
					  			<img src="image/icon/delete.gif" border="0"/></a>
					 		</c:if>
					 	</security:authorize>
					  	&nbsp;
				  	</td>
				</tr>
			</c:forEach>
		  <tr>
		  	<td colspan="4">
		  		<div style="float:left"><page:display item="${searchCriteria}" /></div>
		  		<div style="float:right"><page:number item="${searchCriteria}" /></div>
		  	</td>
		  </tr>
		</table>
	</c:if>
</form:form>
</body>
</html>
