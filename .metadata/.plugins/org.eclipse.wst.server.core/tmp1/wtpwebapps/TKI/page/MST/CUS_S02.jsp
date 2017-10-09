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
var cusS02Form;
var customerCodeAll;

	//initialze script run
	$(document).ready(function() {
		
		btnSearch = $("#btnSearch");
		cusS02Form = $("#cusS02Form");
		btnSave = $("#btnSave");
		customerCodeAll = $(".customerCodeAll");		
		
		// click save button
		fnSave();
		
		// click search button
		btnSearch.click(function(){ 			
			cusS02Form
				.attr("action","CUS_S02_search.html")
				.find("select[id=pageNumber]").attr("disabled", true).end()
				.submit();
		});	
	});
	
	// click Sync Customer button
	function fnSave(){		
		btnSave.click(function(){	
			var i=0;
			customerCodeAll.each(function(){
				this.customerCodeAll = $(this);
				if (this.customerCodeAll.attr("checked"))i++;
			});
			if (i==0) {
				message.setErrors([{"code":"err.cmm.013","arguments":["",""]}]);
				return;
			}			
			message.clear();
			
			if( !confirm("<spring:message code='cfm.cmm.004'/>") )
				return;
			
			cusS02Form
				.attr("action","CUS_S02_save.html")
				.submit();
		});
	}
	
	function jqCheckAll(id, name){
		$("."+name).attr('checked', $('#' + id).is(':checked'));
	}
	
</script>
</head>
<body>
	<form:form method="post" id="cusS02Form" commandName="searchCriteria" action="CUS_S02_search.html">
	<h1><spring:message code='menu.CustomerMaster'/></h1>
		<ul id="navlist">
			<li>
				<a href="CUS_S01.html">Customer List</a>
				<a href="CUS_S02.html" id="current">Customer Sync From TPics</a>
			</li>
		</ul>
	
		<page:message item="${searchCriteria}" />
	
		<table width="100%" border="0" cellpadding="3" cellspacing="1">
			<tr>
				<td colspan="2">
					<table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
						<tr>
							<th width="14%">Customer Code<br/>(TPics)</th>
							<td width="38%"><form:input path="customerCode"	id="boxCustomerCode"/></td>
							<th width="15%">Customer Name<br/>(TPics)</th>
							<td width="33%"><form:input path="customerName"	id="boxCustomerName"/></td>
						</tr>
					</table>
				</td>
			</tr>
			<security:authorize ifAnyGranted="CUS_S02_SEARCH">
				<tr>
					<td colspan="2" align="right"><input name="btnSearch" id="btnSearch" type="button" value="Search" style="width:100px"/></td>
				</tr>
			</security:authorize>
		</table>
		<br/>
		<c:if test="${not empty searchCriteria.customerList}">
			<table width="100%" border="1" align="center" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content">
				<tr>
					<td colspan="4">
						<div style="float: left"><page:display item="${searchCriteria}"/></div>
						<div style="float: right"><page:number item="${searchCriteria}"/></div>
					</td>
				</tr>
				<tr>
					<th align="center"><input type="checkbox" name="checkAll" id="checkAll" onclick="jqCheckAll( this.id, 'customerCodeAll' )" /></th>
					<th align="center">Customer Code</th>
					<th align="center">Customer Name</th>
				</tr>
				<c:forEach items="${searchCriteria.customerList}" var="customer" varStatus="status" begin="0" step="1">
					<tr id="${status.index}">
						<td align="center">
							<input type="checkbox" name="customerList[${status.index}].customerCode" class="customerCodeAll"
								id="customerList[${status.index}].customerCode" value="${customer.customerCode}"/>
						</td>
						<td align="center">${customer.customerCode}</td>
						<td align="center">
							${customer.customerName}
							<input type="hidden" value="${customer.customerName}" name="customerList[${status.index}].customerName" id="customerList[${status.index}].customerName"/>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="4">
						<div style="float: left"><page:display item="${searchCriteria}"/></div>
						<div style="float: right"><page:number item="${searchCriteria}"/></div>
					</td>
				</tr>
			</table>
			<security:authorize ifAnyGranted="CUS_S02_SYNC">
				<p align="right"><input name="btnSave" type="button" id="btnSave" value="Sync Customer"/></p>
			</security:authorize>
		</c:if>
	</form:form>
</body>
</html>
