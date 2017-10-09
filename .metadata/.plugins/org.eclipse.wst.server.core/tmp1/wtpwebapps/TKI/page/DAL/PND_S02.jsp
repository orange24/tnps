<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
var boxWIP;
var boxCustom;
var btnSearch;
var pndS02Form;

function fnSearch(){
	btnSearch.click(function(){
		var msg = [];
		/*if (boxWIP.attr("selectedIndex") == 0) {
			msg.push({"code":"err.cmm.001","arguments":["WIP",""]});
		}
		if (boxCustom.attr("selectedIndex") == 0) {
			msg.push({"code":"err.cmm.001","arguments":["Customer",""]});
		}*/
		if( msg.length > 0 ) {
			message.setErrors(msg);
			return false;
		}
		
		message.clear();
		DateUtil.compare($("#dateFrom"),$("#dateTo"));
		if (!message.isNoError()) {
			return false;
		}
		
		pndS02Form.attr("action","PND_S02_search.html");
		pndS02Form.submit();
	});
}

//JQuery
	//initialze script run
	$(document).ready(function() {
		
		boxWIP=$("select#boxWIP");
		boxCustom=$("select#boxCustom");
		btnSearch=$("#btnSearch");
		pndS02Form=$("form#pndS02Form");
		
		boxWIP.focus();	
		
		fnSearch();
		
	});	
	
</script>
</head>
<body>
	<h1><spring:message code='menu.PendingAdjustment'/></h1>
	<ul id="navlist">
		<li><a href="PND_S01.html" >Pending Adjustment</a></li>
		<li><a href="PND_S02.html" id="current">Pending Adjust History</a></li>
	</ul>
	<page:message item="${searchCriteria}" />

	<form:form action="PND_S02_search.html" method="post" commandName="searchCriteria" id="pndS02Form">
		<table width="100%" border="0" cellspacing="1">
			<tr>
				<td colspan="2">
					<table class="ui-widget ui-widget-content " cellspacing="1" width="100%" border="0">
					    <tr>
							<th>WIP</th>
							<td><form:select path="wip" id="boxWIP" items="${wipMap}" /></td>
							<th>Customer</th>
							<td><form:select path="customerId" id="boxCustom" items="${customerMap}"/></td>
					    </tr>
					    <tr>
							<th>Part No</th>
							<td><form:input path="partNo" /> </td>
							<th>Part Name</th>
							<td><form:input path="partName" /> </td>
					    </tr>
					    <tr>
							<th >WO. No.</th>
							<td ><form:input path="workorderNo" /></td>
							<th >Adjust Date</th>
							<td >
								From <form:input title="Date From" id="dateFrom" path="reportDateFr" cssClass="date" />
							  To <form:input title="Date To" id="dateTo" path="reportDateTo" cssClass="date" />
							</td>
					    </tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right" >
					<security:authorize ifAnyGranted="PND_S02_EXPORT">
						<input class="submit_button" name="btnExport" id="btnExport" type="button" value="Export Pending History" />
					</security:authorize>
					<input class="submit_button" name="btnSearch" id="btnSearch" type="button" value="Search" style="width:100px"/>
				</td>
			</tr>
		</table>
		<br />
		<c:if test="${fn:length(searchCriteria.adjustList) > 0}">
			<table cellpadding="3" cellspacing="1" border="1" width="100%" class="ui-widget ui-widget-content" >
				<tr>
					<td colspan="12">
						<div style="float:left" ><page:display item="${searchCriteria}"/></div>
						<div style="float:right"><page:number item="${searchCriteria}"/></div>
				  	</td>
				</tr>
				<tr>
				   	<th rowspan="2">No.</th>
					<th rowspan="2">Customer</th>
					<th rowspan="2">Part No</th>
					<th rowspan="2">Part Name</th>
					<th rowspan="2">Work Order No.</th>
					<th rowspan="2">WIP</th>
					<th colspan="4">Actual Product Qty</th>
					<th rowspan="2">Adjust By</th>
					<th rowspan="2">Adjust Date</th>
				</tr>
				<tr>
					<th>Pending</th>
					<th>OK</th>
				  	<th>NG</th>
				  	<th>Rework (WIP : Qty)</th>
				</tr>
				<c:forEach items="${searchCriteria.adjustList}" var="pndHist" varStatus="status" begin="0" step="1">
					<tr >
					  	<td align="center"><page:rowno item="${searchCriteria}" index="${status.index}"/>&nbsp;</td>
						<td align="center">${pndHist.customerCode}&nbsp;</td>
						<td align="left">${pndHist.partNo}&nbsp;</td>
						<td align="left">${pndHist.partName}&nbsp;</td>
						<td align="center">${pndHist.workorderNo}&nbsp;</td>
						<td align="center">${pndHist.wipName}&nbsp;</td>
						<td align="center">${pndHist.pdQty}&nbsp;</td>
						<td align="center">${pndHist.ok}&nbsp;</td>
						<td align="center">${pndHist.ng}&nbsp;</td>
						<td align="center">
							<c:forEach items="${pndHist.reworkList}" var="rework" varStatus="st" begin="0" step="1">
								${rework.wipName}:${rework.reworkQty}<br/>
							</c:forEach>&nbsp;
						</td>
						<td align="center">${pndHist.updateBy}&nbsp;</td>
						<td align="center"><fmt:formatDate value="${pndHist.lastUpdate}" pattern="dd/MM/yyyy"/>&nbsp;</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="12">
						<div style="float:left" ><page:display item="${searchCriteria}"/></div>
						<div style="float:right"><page:number item="${searchCriteria}"/></div>
				 	</td>
				</tr>
			</table>
		</c:if>
	</form:form>
</body>
</html>
