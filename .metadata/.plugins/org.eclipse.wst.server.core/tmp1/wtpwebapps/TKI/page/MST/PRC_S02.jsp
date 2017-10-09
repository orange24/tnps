<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp"%>
<script src="../js/jquery-ui-1.8.6.custom.min.js" type="text/javascript"></script>
<script language="javascript">

var btnSearch;
var btnSave;
var prcS02Form;
var wipAll;

	//initialze script run
	$(document).ready(function() {
		
		btnSearch = $("#btnSearch");
		prcS02Form = $("#prcS02Form");
		btnSave = $("#btnSave");
		wipAll = $(".wipAll");
		
		// click save button
		fnSave();
		
		// click search button
		btnSearch.click(function(){ 			
			prcS02Form
			.attr("action","PRC_S02_search.html")
			.find("select[id=pageNumber]").attr("disabled", true).end()
			.submit();
		});	
	});
	
	// click Sync Process button
	function fnSave(){		
		btnSave.click(function(){	
			var i=0;
			wipAll.each(function(){
				this.wipAll = $(this);
				if (this.wipAll.attr("checked"))i++;
			});
			if (i==0) {
				message.setErrors([{"code":"err.cmm.013","arguments":["",""]}]);
				return;
			}			
			message.clear();
			
			if( !confirm("<spring:message code='cfm.cmm.004'/>") )
				return;
			
			prcS02Form
			.attr("action","PRC_S02_save.html")
			.submit();
		});
	}
	
	function jqCheckAll(id, name)
	{
		$("."+name).attr('checked', $('#' + id).is(':checked'));
	}	
	
</script>
</head>
<body>
<form:form method="post" id="prcS02Form" commandName="searchCriteria" action="PRC_S02_search.html">
	<h1><spring:message code='menu.ProcessMaster'/></h1>
	<ul id="navlist">
		<li>
			<a href="PRC_S01.html">Process List</a> 
			<a href="PRC_S02.html" id="current">Process Sync From TPics</a>
		</li>
	</ul>

	<page:message item="${searchCriteria}" />

	<table width="100%" border="0" cellpadding="3" cellspacing="1">
		<tr>
			<td colspan="2">
			<table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
				<tr>
					<th width="14%">Process Code<br/>(TPics)</th>
					<td width="38%"><form:input path="wip" id="boxWip" /></td>
					<th width="15%">Process Name<br/>(TPics)</th>
					<td width="33%"><form:input path="wipName" id="boxWipName"/></td>
				</tr>
			</table>
			</td>
		</tr>
		<security:authorize ifAnyGranted="PRC_S02_SEARCH">
		<tr>
			<td colspan="2" align="right"><input name="btnSearch" id="btnSearch" type="button" value="Search" style="width: 100px"/></td>
		</tr>
		</security:authorize>
	</table>
	<br/>
	<c:if test="${not empty searchCriteria.wipList}">
		<table width="100%" border="1" align="center" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content">
			<tr>
				<td colspan="7">
					<div style="float: left"><page:display item="${searchCriteria}" /></div>
					<div style="float: right"><page:number item="${searchCriteria}" /></div>
				</td>
			</tr>
			<tr>
				<th align="center"><input type="checkbox" name="checkAll" id="checkAll" onclick="jqCheckAll( this.id, 'wipAll' )" /></th>
				<th align="center">Process Code</th>
				<th align="center">Process Name</th>
				<th align="center">Process Group</th>				
			</tr>
			<c:forEach items="${searchCriteria.wipList}" var="wip" varStatus="status" begin="0" step="1">
				<tr id="${status.index}">
					<td align="center">
						<input type="checkbox" name="wipList[${status.index}].wip" class="wipAll"
								id="wipList[${status.index}].wip" value="${wip.wip}"/>
					</td>
					<td align="center">${wip.wip}</td>
					<td align="center">
						${wip.wipName}
						<input type="hidden" value="${wip.wipName}" name="wipList[${status.index}].wipName" id="wipList[${status.index}].wipName"/>
					</td>
					<td align="center">
						${wip.wipGroup}
						<input type="hidden" value="${wip.wipGroup}" name="wipList[${status.index}].wipGroup" id="wipList[${status.index}].wipGroup"/>
					</td>					
				</tr>
			</c:forEach>
			<tr>
				<td colspan="7">
					<div style="float: left"><page:display item="${searchCriteria}" /></div>
					<div style="float: right"><page:number item="${searchCriteria}" /></div>
				</td>
			</tr>
		</table>
		<security:authorize ifAnyGranted="PRC_S02_SYNC">
			<p align="right"><input name="btnSave" type="button" id="btnSave" value="Sync Process"/></p>
		</security:authorize>
	</c:if>
</form:form>
</body>
</html>
