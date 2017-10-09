<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script src="page/FNG/FNG_S05.js" language="javascript"></script>
</head>
<body>
<h1><spring:message code='menu.FGStockAdjustment'/></h1>
	<ul id="navlist">
		<li><a href="FNG_S04.html">FG Stock Adjustment</a></li>
		<li><a href="FNG_S05.html" id="current">FG Stock Adjustment History</a></li>
	</ul>
	<page:message item="${tfgStock}" />
	<form:form id="fngS05Form" methodParam="post" action="FNG_S05_search.html" commandName="tfgStock">
		<table class="ui-widget ui-widget-content" width="100%" border="0" cellpadding="3" cellspacing="1" >
			<tr>
				<th width="13%">Customer</th>
				<td width="37%"><form:select path="customerId" id="selectCustomer" items="${customerMap}"/></td>
				<th width="13%">Stock Date</th>
				<td width="37%">
					<form:input path="reportDateFrom" cssClass="date" title="Stock Date (From)"/> - <form:input path="reportDateTo" cssClass="date" title="Stock Date (To)"/>
				</td>
			</tr>
			<tr>
				<th width="13%">FG No.</th>
				<td width="37%"><form:input path="fgNo" /></td>
				<th width="13%">FG Name</th>
				<td width="37%"><form:input path="fgName" /></td>
			</tr>
			<tr>
				<th width="13%">Adjust Date(From-To)</th>
				<td width="37%">
					<form:input path="createDateFrom" cssClass="date" title="Adjust Date (From)"/> - <form:input path="createDateTo" cssClass="date" title="Adjust Date (To)"/>
				</td>
				<th width="13%"></th>
				<td width="37%"></td>
			</tr>
		</table>
		<br />
		<div align="right">
			<input name="btnSearch" type="button" id="btnSearch" value="Search" />
		</div>
	
	<br />
	<c:if test="${fn:length(tfgStock.tfgStockList) > 0}">
		<table width="100%" border="1" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
			<tr>
				<td colspan="9">
			  		<div style="float:left" ><page:display item="${tfgStock}" /></div>
			  		<div style="float:right" ><page:number item="${tfgStock}" /></div>
			  	</td>
			</tr>
			<tr >
				<th align="center">Customer</th>
    			<th align="center"><span >FG No.</span></th>
				<th align="center"><span >FG Name</span></th>
				<th align="center"><span >Stock Date</span></th>
    			<th align="center" width="9%"><p>Before  Adjust Stock<br />(Balance)</p>      </th>
    			<th align="center" width="9%">Adjust  Stock Qty <br />(Balance)</th>
    			<th align="center">Adjust Reason</th>
    			<th align="center">Adjust By</th>
    			<th align="center">Adjust Date </th>
			</tr>
			<c:forEach items="${tfgStock.tfgStockList}" varStatus="status" var="stock" begin="0" step="1">
				<tr>
					<td align="center">${stock.customerCode}&nbsp;</td>
					<td align="center">${stock.fgNo}&nbsp;</td>
					<td align="center">${stock.fgName}&nbsp;</td>
					<td align="center"><fmt:formatDate value="${stock.reportDate}" pattern="dd/MM/yyyy"/>&nbsp;</td>
					<td align="right"><fmt:formatNumber value="${stock.fgBalance}"/>&nbsp;</td>
					<td align="right"><fmt:formatNumber value="${stock.fgAdjust}"/>&nbsp;</td>
					<td align="left">${stock.adjustReason}&nbsp;</td>
					<td align="center">${stock.createBy}&nbsp;</td>
					<td align="center"><fmt:formatDate value="${stock.createDate}" pattern="dd/MM/yyyy HH:mm:ss"/>&nbsp;</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="9">
			  		<div style="float:left" ><page:display item="${tfgStock}" /></div>
			  		<div style="float:right" ><page:number item="${tfgStock}" /></div>
			  	</td>
			</tr>
		</table>
	</c:if>
	
	</form:form>
</body>
</html>