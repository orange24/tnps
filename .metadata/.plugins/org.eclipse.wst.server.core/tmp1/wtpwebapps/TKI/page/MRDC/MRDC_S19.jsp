<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">

	var mrdc_s19Form;
	var process;
	var partNo;
	var partName;
	var btnSearch;
	
	$(document).ready(function(){
		mrdc_s19Form= $("form#mrdc_s19Form");
		process 	= $("select#wip");
		partNo		= $("input#boxPartNo");
		partName	= $("input#boxPartName");
		btnSearch	= $("input#btnSearch");
		
		btnSearch.click(function(){
			message.clear();			
			if (message.isNoError()) {
				mrdc_s19Form.submit();
			}
		});
		
		// <!-- Initial: Auto Completion. -->
		var partNoList = {
			source: function( request, response ) {
				getJSON("partAutoComplete",  
						{"partNo" : partNo.val(),"partName" : partName.val(),"wip" : process.val(),"customerId": -1}, 
						function(result){
							response($.map(result,function(item){
								return {
									label: item.partNo,
									partNo: item.partNo,
									partName : item.partName
								};
							}));
						});
			},
			select: function( event, ui ) {
				partName.val(ui.item.partName);
			},
			minLength: 1,
			delay: 1000
		};
		partNo.autocomplete(partNoList);
		
		var partNameList = {
			source: function( request, response ) {
				getJSON("partAutoComplete",  
						{"partNo" : partNo.val(),"partName" : partName.val(),"wip": process.val(),"customerId": -1}, 
						function(result){
							response($.map(result,function(item){
								return {
									label: item.partName,
									partNo: item.partNo,
									partName: item.partName
								};
							}));
					});
			},
			select: function( event, ui ) {
				partNo.val(ui.item.partNo);
			},
			minLength: 1,
			delay: 1000
		};
		partName.autocomplete(partNameList);
	});
</script>
</head>
<body>
	<h1 class="header"><spring:message code='menu.WIPStockInquiry'/></h1>
	<page:message item="${tWip}" />
	<form:form id="mrdc_s19Form" method="post" action="MRDC_S19_Search.html" commandName="tWip">
		<table width="100%" border="0" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
			<tr>
				<th width="16%" class="label">Year/Month<span class="textred">*</span></th>
				<td width="34%"><form:select path="year" id="year" items="${yearMap}"></form:select> / <form:select path="month" id="month" items="${monthMap}"></form:select></td>
				<th width="20%">Process</th>
				<td width="30%"><form:select path="wip" id="wip" items="${processMap}"/></td>
			</tr>
			<tr>
				<th>Part No</th>
				<td><form:input path="partNo" id="boxPartNo"/></td>
				<th>Part Name</th>
				<td><form:input path="partName" id="boxPartName" size="80"/></td>
			</tr>
			<tr>
				<th>Category</th>
				<td><form:select path="category" id="category" items="${categoryMap}"/></td>
				<th>Sorting</th>
				<td>
					<form:select path="sorting" id="sorting">
						<form:option value="1">Part No.</form:option>
						<form:option value="2">WIP</form:option>
					</form:select>
				</td>
			</tr>
		</table>
		<br/>
		<!-- security:authorize ifAnyGranted="MRDC_R19_SEARCH"> -->
			<div align="right">
				<input name="btnSearch" type="button" id="btnSearch" value="Search"/>
			</div>
		<!-- /security:authorize> -->
		<br/>
		<c:if test="${fn:length(tWip.stockList) > 0}">
			<table border="1" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
			  <tr>
			  	<td colspan="13">
			  		<div style="float:left" ><page:display item="${tWip}" /></div>
			  		<div style="float:right" ><page:number item="${tWip}" /></div>
			  	</td>
			  </tr>
			  <tr >
			  	<th>Part No.</th>
				<th>Part Name</th>
		        <th>Process</th>
		        <th>Category</th>
		        <th>Balance Carried Forward</th>
		        <th>In Qty</th>
		        <th>Out Qty</th>
		        <th>Stock Balance</th>
		        <th>Customer</th>
		        <th>Inventory Unit Price</th>
		        <th>Stock Value</th>
			  </tr>
			  <c:forEach items="${tWip.stockList}" varStatus="status" var="t_wip" begin="0" step="1">
				  <tr align="left">
				    <td>${t_wip.partNo}&nbsp;</td>
				    <td>${t_wip.partName}&nbsp;</td>
				    <td>${t_wip.wipName}&nbsp;</td>
				    <td>${t_wip.category}&nbsp;</td>
				    <td align="right"><fmt:formatNumber pattern="#,##0" value="${t_wip.carriedBalance}" />&nbsp;</td>
				    <td align="right"><fmt:formatNumber pattern="#,##0" value="${t_wip.inQty}"/>&nbsp;</td>
				    <td align="right"><fmt:formatNumber pattern="#,##0" value="${t_wip.outQty}"/>&nbsp;</td>
				    <td align="right"><fmt:formatNumber pattern="#,##0" value="${t_wip.stockBalance}"/>&nbsp;</td>
				    <td>${t_wip.customer}&nbsp;</td>
				    <td align="right"><fmt:formatNumber pattern="#,##0.0000" value="${t_wip.inventoryUnitPrice}"/>&nbsp;</td>
				    <td align="right"><fmt:formatNumber pattern="#,##0.00" value="${t_wip.stockValue}"/>&nbsp;</td>				    
				  </tr>
  			 </c:forEach>
  			 		<tr>
						<td colspan="13">
							<div style="float:left" ><page:display item="${tWip}" /></div>
							<div style="float:right" ><page:number item="${tWip}" /></div>
						</td>
 					</tr>
			 </table>
		</c:if>
	</form:form>	
</body>
</html>