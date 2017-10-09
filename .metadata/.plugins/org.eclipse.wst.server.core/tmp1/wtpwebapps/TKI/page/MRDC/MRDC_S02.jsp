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
	var materialId;
	var partNo;
	var partName;
	var category;
	var btnExport;
	var mrdc_s02Form;
	
	$(document).ready(function(){
		customerId	= $("select#customerId");
		materialId	= $("select#materialId");
		category	= $("select#category");
		partNo		= $("input#boxPartNo");
		partName	= $("input#boxPartName");
		btnExport	= $("input#btnExport");
		mrdc_s02Form= $("form#mrdc_s02Form");
		
		btnExport.click(function(){
			/*var errors = [];
			if( customerId.val() < 0 && materialId.val() < 0 && category.val() == "" && partNo.val() == "" && partName.val() == "")
				errors.push({"code": "err.cmm.001", "arguments": ["Search Criteria"]});
			
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}*/
			message.clear();
			
			if (message.isNoError()) {
				downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));
				
				var params = {
			               "customerId" : customerId.val()
			              ,"partNo" 	: partNo.val() 
			              ,"partName" 	: partName.val() 
			              ,"category" 	: category.val()
			              ,"materialId" : materialId.val()
				};
					
				getJSON("MRDC_R02_export_count", $.param(params), function( result ){
					
					if(result.count > result.maxRecord && result.size == result.maxRecord){
						alert("<spring:message code='cfm.excel.001'/>".replace(/\{0\}/g, result.maxRecord).replace(/\{1\}/g, result.count));
					}
					
					var param = [
					             "customerCode="+ customerId.find("option:selected").text(),
					             ];
					
					mrdc_s02Form.attr("action","MRDC_R02_export.html?"+param.join("&"));
					mrdc_s02Form.submit();
				});
				return false;
			}
		});
		
		/*customerId.change(function(){
			getJSON("boxPartNameNo", { "customerId" : customerId.val(),"wip" : "" }, function(result){
				partId.empty();
				$.each(result,function(val, text){
					partId.append( $("<option></option>").val(val).html(text) );
				});
			});
		});*/
		
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
	<h1 class="header"><spring:message code='menu.SalesUnitPriceList'/></h1>
	<page:message item="${dailyWK}" />
	<form:form id="mrdc_s02Form" method="post" action="MRDC_R02_export.html" commandName="dailyWK">
		<table width="100%" border="0" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
			<tr>
				<th width="16%" class="label">Customer</th>
				<td width="" colspan="3"><form:select path="customerId" id="customerId" items="${customerMap}"/></td>
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
				<input name="btnExport" type="button" id="btnExport" value="Summary Report"/>
			</div>
		<!-- /security:authorize> -->
	</form:form>
</body>
</html>