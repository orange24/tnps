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
	var year;
	var month;
	var partNo;
	var partName;
	var wip;
	var customerId;
	var btnExport;
	var mrdc_s20Form;
	
	$(document).ready(function(){
		year = $("select#year");
		month = $("select#month");
		partNo		= $("input#boxPartNo");
		partName	= $("input#boxPartName");
		wip			= $("select#wip");
		customerId	= $("select#customerId");
		btnExport	= $("input#btnExport");
		mrdc_s20Form= $("form#mrdc_s20Form");
		
		btnExport.click(function(){
			downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));
			mrdc_s20Form.attr("action","MRDC_R20_export.html");
			mrdc_s20Form.submit();
		});
		
		// <!-- Initial: Auto Completion. -->
		var partNoList = {
			source: function( request, response ) {
				getJSON("partAutoComplete",  
						{"wip" : wip.val(),"partNo" : partNo.val(),"customerId": customerId.val(),"partName" : partName.val()}, 
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
						{"wip" : wip.val(),"partNo" : partNo.val(),"partName" : partName.val(),"customerId": customerId.val()}, 
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
	<h1 class="header"><spring:message code='menu.CapacityCheck'/></h1>
	<page:message item="${deliveryPlan}" />
	<form:form id="mrdc_s20Form" method="post" action="MRDC_S20_search.html" commandName="deliveryPlan">
		<table width="100%" border="0" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
			<tr>
				<th width="16%">Year/Month <span class="textred">*</span></th>
				<td width="34%"><form:select path="year" id="year" items="${yearMap}"></form:select> / <form:select path="month" id="month" items="${monthMap}"></form:select></td>
				<th width="20%" class="label">Customer</th>
				<td width="30%"><form:select path="customerId" id="customerId" items="${customerMap}"/></td>
			</tr>
			<tr>
				<th width="16%">Process</th>
				<td width="34%"><form:select path="wip" id="wip" items="${wip}"/></td>
				<th width="20%" class="label">Sorting</th>
				<td width="30%">
					<form:select path="sorting" id="sorting">
						<form:option value="1">Customer</form:option>
						<form:option value="2">Part</form:option>
					</form:select>
				</td>
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
				<input name="btnExport" type="button" id="btnExport" value="Summary Report"/>
			</div>
		<!-- /security:authorize> -->
	</form:form>
</body>
</html>