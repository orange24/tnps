<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var customerId;
	var partNo;
	var partName;
	var yearFr;
	var yearTo;
	var monthFr;
	var monthTo;
	var btnExport;
	var mrdc_s14Form;
	
	$(document).ready(function(){
		customerId	= $("select#customerId");
		partNo		= $("input#partNo");
		partName	= $("input#partName");
		yearFr = $("select#yearFr");
		yearTo = $("select#yearTo");
		monthFr = $("select#monthFr");
		monthTo = $("select#monthTo");
		btnExport	= $("input#btnExport");
		mrdc_s14Form= $("form#mrdc_s14Form");
		
		btnExport.click(function(){
			var errors = [];
			/*if( customerId.val() < 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["Customer"]});*/
			if( parseInt(yearFr.val(),0) > parseInt(yearTo.val(),0) )
				errors.push({"code": "err.cmm.008", "arguments": ["Delivery Year/Month (To)","Delivery Year/Month (From)"]});
			if( parseInt(yearFr.val(),0) == parseInt(yearTo.val(),0) && parseInt(monthFr.val(),0) > parseInt(monthTo.val(),0) )
				errors.push({"code": "err.cmm.008", "arguments": ["Delivery Year/Month (To)","Delivery Year/Month (From)"]});
			
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
			message.clear();
			
			if (message.isNoError()) {
				downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));
				
				var params = {
			               "customerId" : customerId.val()
			              ,"partNo" 	: partNo.val() 
			              ,"partName" 	: partName.val() 
			              ,"yearFr" 	: yearFr.val()
			              ,"yearTo" 	: yearTo.val()
			              ,"monthFr" 	: monthFr.val()
			              ,"monthTo" 	: monthTo.val()
				};
				
				getJSON("MRDC_R14_export_count", $.param(params), function( result ){
					
					if(result.count > result.maxRecord && result.size == result.maxRecord){
						alert("<spring:message code='cfm.excel.001'/>".replace(/\{0\}/g, result.maxRecord).replace(/\{1\}/g, result.count));
					}
					
					var param = ["customerCode="+ customerId.find("option:selected").text()];
					mrdc_s14Form.attr("action","MRDC_R14_export.html?"+param.join("&"));
					mrdc_s14Form.submit();
				});
				return false;
			}
		});
		
		// <!-- Initial: Auto Completion. -->
		var partNoList = {
			source: function( request, response ) {
				getJSON("partAutoComplete",  
						{"partNo" : partNo.val(),"customerId": customerId.val(),"partName" : partName.val()}, 
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
</script>
</head>
<body>
	<h1 class="header"><spring:message code='menu.ResidualSalesOrderTransitionList'/></h1>
	<page:message item="${deliveryPlan}" />
	<form:form id="mrdc_s14Form" method="post" action="MRDC_R14_export.html" commandName="deliveryPlan">
		<table width="100%" border="0" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
			<tr>
				<th width="16%" class="label">Delivery Year/Month (From) <span class="textred">*</span></th>
				<td width="34%"><form:select path="yearFr" id="yearFr" items="${yearMap}"></form:select> / <form:select path="monthFr" id="monthFr" items="${monthMap}"></form:select></td>
				<th width="20%">Delivery Year/Month (To) <span class="textred">*</span></th>
				<td width="30%"><form:select path="yearTo" id="yearTo" items="${yearMap}"></form:select> / <form:select path="monthTo" id="monthTo" items="${monthMap}"></form:select></td>
			</tr>
			<tr>
				<th width="16%">Customer</th>
				<td width="32%"><form:select path="customerId" id="customerId" items="${customerMap}"/></td>
				<th width="20%">Output Category</th>
	    		<td width="32%">
	    			<form:select path="outputCategory" id="outputCategory">
						<form:option value="1">1. Qty</form:option>
						<form:option value="2">2. Price</form:option>
						<form:option value="3">3. Both (Qty / Price)</form:option>
					</form:select>
	    		</td>
			</tr>
			<tr>
	        	<th width="20%">Part No</th>
	    		<td width="32%"><form:input path="partNo" id = "partNo"/></td>
	    		<th width="20%">Part Name</th>
	    		<td width="32%"><form:input path="partName" id = "partName" size="80"/></td>
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