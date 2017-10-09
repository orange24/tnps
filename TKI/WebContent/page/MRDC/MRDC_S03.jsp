<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	/*var materialId;
	var category;*/
	var customerId;
	var partNo;
	var partName;
	var yearFr;
	var yearTo;
	var monthFr;
	var monthTo;
	var btnSearch;
	var mrdc_s031Form;
	
	$(document).ready(function(){
		/*materialId	= $("select#materialId");
		category	= $("select#category");*/
		customerId	= $("select#customerId");
		partNo		= $("input#boxPartNo");
		partName	= $("input#boxPartName");
		yearFr = $("select#yearFr");
		yearTo = $("select#yearTo");
		monthFr = $("select#monthFr");
		monthTo = $("select#monthTo");
		btnSearch	= $("input#btnSearch");
		mrdc_s031Form= $("form#mrdc_s031Form");
		
		btnSearch.click(function(){
			var errors = [];
			/*if( customerId.val() < 0  && materialId.val() < 0 && category.val() == "" && partNo.val() == "" && partName.val() == "")
				errors.push({"code": "err.cmm.001", "arguments": ["Search Criteria"]});*/
			if( parseInt(yearFr.val(),0) > parseInt(yearTo.val(),0) )
				errors.push({"code": "err.cmm.008", "arguments": ["Year/Month (To)","Year/Month (From)"]});
			if( parseInt(yearFr.val(),0) == parseInt(yearTo.val(),0) && parseInt(monthFr.val(),0) > parseInt(monthTo.val(),0) )
				errors.push({"code": "err.cmm.008", "arguments": ["Year/Month (To)","Year/Month (From)"]});
			
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
			message.clear();
			if (message.isNoError()) {
				mrdc_s031Form.attr("action","MRDC_S031_search.html");
				mrdc_s031Form.find("select[id=pageNumber]").attr("disabled", true);
				mrdc_s031Form.submit();
			}
		});
		
		// <!-- Initial: Auto Completion. -->
		var partNoList = {
			source: function( request, response ) {
				getJSON("partAutoComplete",  
						{"partNo" : partNo.val(),"partName" : partName.val(),"customerId": customerId.val()}, 
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
						{"partNo" : partNo.val(),"partName" : partName.val(),"customerId": customerId.val()}, 
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
	
	function MRDC_S032Search(){
		downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));
		mrdc_s031Form.attr("action","MRDC_S032_search.html");
		mrdc_s031Form.find("select[id=pageNumber]").attr("disabled", true);
		mrdc_s031Form.submit();
	}
</script>
</head>
<body>
	<h1 class="header"><spring:message code='menu.ProductInformationInquiry'/></h1>
	<page:message item="${deliveryPlan}" />
	<form:form id="mrdc_s031Form" method="post" action="MRDC_S031_search.html" commandName="deliveryPlan">
		<table width="100%" border="0" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
			<tr>
				<th width="16%" class="label">Customer</th>
				<td width="" colspan="3"><form:select path="customerId" id="customerId" items="${customerMap}"/></td>
			</tr>
			<tr>
				<th width="16%" class="label">Year/Month (From)</th>
				<td width="34%"><form:select path="yearFr" id="yearFr" items="${yearMap}"></form:select> / <form:select path="monthFr" id="monthFr" items="${monthMap}"></form:select></td>
				<th width="20%">Year/Month (To)</th>
				<td width="30%"><form:select path="yearTo" id="yearTo" items="${yearMap}"></form:select> / <form:select path="monthTo" id="monthTo" items="${monthMap}"></form:select></td>
			</tr>
			<tr>
				<th width="16%" class="label">Category Type</th>
				<td width="34%"><form:select path="category" id="category" items="${categoryMap}"/></td>
				<th width="20%">Material Type</th>
				<td width="30%"><form:select path="materialId" id="materialId" items="${MaterialTypeMap}"/></td>
			</tr>
			<tr>
				<th width="16%" class="label">Part No</th>
				<td width="34%"><form:input path="partNo" id="boxPartNo"/></td>
				<th width="20%" class="label">Part Name</th>
				<td width="30%"><form:input path="partName" id="boxPartName" size="80"/></td>
			</tr>
		</table>
		<br/>
		<!-- security:authorize ifAnyGranted="MRDC_R14_EXPORT"> -->
			<div align="right">
				<input  name="btnSearch" id="btnSearch" type="button" value="Search" style="width:100px"/>
			</div>
		<!-- /security:authorize> -->
		<br/>
		<c:if test="${not empty deliveryPlan.dateList}">
		<table width="100%" border="1" align="center" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content" >
		   <tr>
		    <td colspan="20">
				<div style="float:left"><page:display item="${deliveryPlan}"/></div>
				<div style="float:right"><page:number item="${deliveryPlan}"/></div>
			</td>
		   </tr>
		  <tr>
		  	<th align="center">Year/Month</th>
		    <th align="center">Customer</th>
		    <th align="center">Part No</th>
		    <th align="center">Part Name</th>
		    <th align="center">Category</th>
		    <th align="center">Material</th>
		    <th align="center">Total Qty</th>
		    <th align="center">OK Qty</th>
		    <th align="center">NG Qty</th>
		    <th align="center">Cav Qty</th>
		    <th align="center">SNP</th>
		    <th align="center">Unit Weight</th>
		    <th align="center">Forecast Qty</th>
		    <th align="center">Cust Req Qty</th>
		    <th align="center">TKI Commit Qty</th>
		    <th align="center">Production Plan Qty</th>
		    <th align="center">Deliverly Qty (Normal)</th>
		    <th align="center">Deliverly Qty (Back)</th>
		    <th align="center">Deliverly Qty (Total)</th>
		  </tr>
		  <c:set value="${0}" var="totalNormal"></c:set>
		  <c:set value="${0}" var="totalBack"></c:set>
		  <c:set value="${0}" var="totalTotal"></c:set>
		  <c:set value="${0}" var="rowCount"></c:set>
		  
		  <c:forEach items="${deliveryPlan.dateList}" var="detail" varStatus="status" begin="0" step="1"> 
		  <c:set value="${totalNormal + detail.deliveryQtyNormal}" var="totalNormal"></c:set>
		  <c:set value="${totalBack + detail.deliveryQtyBack}" var="totalBack"></c:set>
		  <c:set value="${totalTotal + detail.deliveryQtyTotal}" var="totalTotal"></c:set>
		  <c:set value="${status.index}" var="rowCount"></c:set>
		  <tr>
		  	<td align="center">${monthMap[detail.month]} ${detail.year}&nbsp;</td>
			<td align="left">${detail.customerCode}&nbsp;</td>
			<td align="left">${detail.partNo}&nbsp;</td>
			<td align="left">${detail.partName}&nbsp;</td>
			<td align="left">${detail.category}&nbsp;</td>
			<td align="left">${detail.materialName}&nbsp;</td>
			<td align=right><fmt:formatNumber pattern="#,##0" value="${detail.totalQty}" />&nbsp;</td>
			<td align="right"><fmt:formatNumber pattern="#,##0" value="${detail.ok}" />&nbsp;</td>
			<td align="right">
				<c:url var="productDetailUrl" value="MRDC_S032_search.html">
					<c:param name="customerId" value="${detail.customerId}"/>
					<c:param name="customerCode" value="${detail.customerCode}"/>
					<c:param name="customerName" value="${detail.customerName}"/>
					<c:param name="partId" value="${detail.partId}"/>
					<c:param name="partNo" value="${detail.partNo}"/>
					<c:param name="partName" value="${detail.partName}"/>
					<c:param name="year" value="${detail.year}"/>
					<c:param name="month" value="${detail.month}"/>
					<c:param name="ng" value="${detail.ng}"/>
				</c:url>
				<c:if test="${detail.ng != 0}">
					<a href="${productDetailUrl}" style="border-bottom: 1px solid blue"><fmt:formatNumber pattern="#,##0" value="${detail.ng}" /></a>
				</c:if>
				<c:if test="${detail.ng == 0}">
					<fmt:formatNumber pattern="#,##0" value="${detail.ng}" />
				</c:if>
			</td>
			<td align="right"><fmt:formatNumber pattern="#,##0" value="${detail.cavQty}" />&nbsp;</td>
			<td align="right"><fmt:formatNumber pattern="#,##0" value="${detail.snp}" />&nbsp;</td>
			<td align="right"><fmt:formatNumber pattern="#,##0.000" value="${detail.unitWeight}" />&nbsp;</td>
			<td align="right"><fmt:formatNumber pattern="#,##0" value="${detail.forCastQty}" />&nbsp;</td>
			<td align="right"><fmt:formatNumber pattern="#,##0" value="${detail.custReqQty}" />&nbsp;</td>
			<td align="right"><fmt:formatNumber pattern="#,##0" value="${detail.tkiCommitQty}" />&nbsp;</td>
			<td align="right"><fmt:formatNumber pattern="#,##0" value="${detail.productionQty}" />&nbsp;</td>
			<td align="right"><fmt:formatNumber pattern="#,##0" value="${detail.deliveryQtyNormal}" />&nbsp;</td>
			<td align="right"><fmt:formatNumber pattern="#,##0" value="${detail.deliveryQtyBack}" />&nbsp;</td>
			<td align="right"><fmt:formatNumber pattern="#,##0" value="${detail.deliveryQtyTotal}" />&nbsp;</td>
		  </tr>
		  </c:forEach>
		  <tr>
		  	<th align="right" colspan="17"><strong>Average</strong></th>
		  	<th align="right"><strong><fmt:formatNumber pattern="#,##0" value="${totalNormal/(rowCount+1)}" /></strong></th>
		  	<th align="right"><strong><fmt:formatNumber pattern="#,##0" value="${totalBack/(rowCount+1)}" /></strong></th>
		  	<th align="right"><strong><fmt:formatNumber pattern="#,##0" value="${totalTotal/(rowCount+1)}" /></strong></th>
		  </tr>
		  <tr>
		    <td colspan="20">
				<div style="float:left" ><page:display item="${deliveryPlan}"/></div>
				<div style="float:right"><page:number item="${deliveryPlan}"/></div>
			</td>
		  </tr>
		</table>
		</c:if>
	</form:form>
</body>
</html>