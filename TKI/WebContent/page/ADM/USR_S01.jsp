<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp"%>
<script language="javascript">
	var boxUserName;
	var btnSearch;
	var usrS01Form;
	var hidCurrentUser;

	function fnDelete(row) {
		message.clear();
		var rowNo = row.find("td:first-child").html().trim();
		var username = row.find("td:eq(1)").html().trim();

		if (username == hidCurrentUser.val()) {
			message.setErrors([ {
				"code" : "err.usr.002",
				"arguments" : [ "", "" ]
			} ]);
			return;
		}

		if (!confirm("<spring:message code='cfm.cmm.003'/>".replace(/\{0\}/g, rowNo)))
			return;

		usrS01Form.attr("action", "USR_S01_delete.html");
		usrS01Form.append("<input type='hidden' name='userList[0].userName' value='" + row.attr("id") + "'/>");
		usrS01Form.submit();
	}

	//JQuery
	//initialze script run
	$(document).ready(function() {

		boxUserName 	= $("#boxUserName");
		btnSearch 		= $("#btnSearch");
		usrS01Form 		= $("#usrS01Form");
		hidCurrentUser 	= $("#hidCurrentUser");

		boxUserName.focus();

		btnSearch.click(function() {
			$("#usrS01Form").find("select[id=pageNumber]").attr("disabled", true);
			$("#usrS01Form").attr("action", "USR_S01_search.html");
			document.getElementById("usrS01Form").submit();
		});

	});
</script>
</head>
<body>
	<form:form method="post" id="usrS01Form" commandName="searchCriteria"
		action="USR_S01_search.html">
		<h1>
			<spring:message code='menu.User' />
		</h1>
		<ul id="navlist">
			<li><a href="USR_S01.html" id="current">User Search/List</a>
			</li>
			<li><a href="USR_S02.html">User Add/Edit</a>
			</li>
		</ul>

		<page:message item="${searchCriteria}" />

		<table width="100%" border="0" cellpadding="3" cellspacing="1">
			<tr>
				<td><input type="hidden" value="${currentUser.userName}" id="hidCurrentUser" />
					<table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
						<tr>
							<th>User Name</th>
							<td><form:input path="userName" id="boxUserName" />
							</td>
							<th>Full Name</th>
							<td><form:input path="fullName" />
							</td>
						</tr>
						<tr>
							<th>Role</th>
							<td><form:select path="roleId" items="${roleMap}" />
							</td>
							<th>WIP</th>
							<td><form:select path="wip" items="${wipMap}" />
							</td>
						</tr>
					</table></td>
			</tr>
			<tr>
				<td align="right">
					<security:authorize ifAnyGranted="USR_S01_SEARCH">
						<input type="button" id="btnSearch" value="Search" />
					</security:authorize></td>
			</tr>
		</table>
		<br />
		<c:if test="${fn:length(searchCriteria.userList) >0}">
			<table width="100%" border="1" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
				<tr>
					<td colspan="8">
						<div style="float: left">
							<page:display item="${searchCriteria}" />
						</div>
						<div style="float: right">
							<page:number item="${searchCriteria}" />
						</div></td>
				</tr>
				<tr>
					<th>No.</th>
					<th>User Name</th>
					<th>Full Name</th>
					<th>Staff Code</th>
					<th>Email</th>
					<th>WIP</th>
					<th>Role</th>
					<th>Action</th>
				</tr>
				<c:forEach items="${searchCriteria.userList}" varStatus="status"
					var="user" step="1">
					<tr id="${user.userName}">
						<td align="center">
							<page:rowno item="${searchCriteria}" index="${status.index}" />
						</td>
						<td align="left">${user.userName}</td>
						<td align="left">${user.fullName}&nbsp;</td>
						<td align="left">${user.staffCode}&nbsp;</td>
						<td align="left">${user.email}&nbsp;</td>
						<td align="left"><c:if test="${not empty user.wip}">${user.wip} : ${user.wipName}</c:if>&nbsp;</td>
						<td align="left">${user.roleName}&nbsp;</td>
						<td align="center">
							<security:authorize ifAnyGranted="USR_S01_EDIT">
								<a href="USR_S02_copy.html?userName=${user.userName}">
									<img src="image/icon/copy.png" border="0" title="Copy" />
								</a>
								<a href="USR_S02_edit.html?userName=${user.userName}">
									<img src="image/icon/update.gif" border="0" title="Edit"/>
								</a>
							</security:authorize>
							<security:authorize ifAnyGranted="USR_S01_DELETE">
								<c:if test="${!user.isLocked}">
									<a href="javascript:void(0);" onclick="fnDelete( $(this).closest('tr') );">
										<img src="image/icon/delete.gif" border="0" title="Delete"/>
									</a>
								</c:if>
							</security:authorize> &nbsp;
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="8">
						<div style="float: left">
							<page:display item="${searchCriteria}" />
						</div>
						<div style="float: right">
							<page:number item="${searchCriteria}" />
						</div></td>
				</tr>
			</table>
		</c:if>
	</form:form>
</body>
</html>
