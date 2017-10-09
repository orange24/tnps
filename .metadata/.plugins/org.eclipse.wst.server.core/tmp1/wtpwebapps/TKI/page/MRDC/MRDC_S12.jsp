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
	
	var workorderNo;
	var partNo;
	var partName;
	var wip;
	var machineNo;
	var customerId;
	var reportDateFr;
	var reportDateTo;
	var btnExport;
	var mrdc_s12Form;
	
	$(document).ready(function(){
		
		workorderNo	= $("input#boxWorkOrderNo");
		partNo		= $("input#boxPartNo");
		partName	= $("input#boxPartName");
		wip			= $("select#wip");
		machineNo	= $("select#boxMachineNo");
		customerId	= $("select#customerId");
		reportDateFr = $("input#reportDateFr");
		reportDateTo = $("input#reportDateTo");
		btnExport	= $("input#btnExport");
		mrdc_s12Form= $("form#mrdc_s12Form");
		
		btnExport.click(function(){
			var errors = [];
			/*if(	   wip.val() == "" 
				&& reportDate.val() == "" 
				&& machineNo.val() < 0 
				&& workorderNo.val() == "" 
				&& partNo.val() == "" 
				&& partName.val() == ""){
			*/
			/*if( customerId.val() < 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["Customer"]});*/
			if(reportDateFr.val() == "" && reportDateTo.val() == "" )
				errors.push({"code": "err.cmm.001", "arguments": ["Operation Date"]});
			
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
			message.clear();
			
			DateUtil.compare( $("input[name=reportDateFr]"), $("input[name=reportDateTo]") );
			
			if (message.isNoError()) {
				downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));
				
				var params = {
			               "customerId" 	: customerId.val()
			              ,"partNo" 		: partNo.val() 
			              ,"partName" 		: partName.val() 
			              ,"wip" 			: wip.val()
			              ,"machineId" 		: machineNo.val()
			              ,"reportDateFr" 	: reportDateFr.val()
			              ,"reportDateTo" 	: reportDateTo.val()
			              ,"workOrderNo" 	: workorderNo.val()
				};
				
				getJSON("MRDC_R12_export_count", $.param(params), function( result ){
					
					if(result.count > result.maxRecord && result.size == result.maxRecord){
						alert("<spring:message code='cfm.excel.001'/>".replace(/\{0\}/g, result.maxRecord).replace(/\{1\}/g, result.count));
					}
					
					var param = ["customerCode="+ customerId.find("option:selected").text()];
					mrdc_s12Form.attr("action","MRDC_R12_export.html?"+param.join("&"));
					mrdc_s12Form.submit();
				});
				return false;
			}
		});
		
		wip.change(function(){
			comboBox.setMachineName(machineNo, $(this));
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
		
		var partId = -1;
		workorderNo.keydown(function(){
			partId = -1;
			getJSON("queryPartId",  
				{"wip" : wip.val(),"partNo" : partNo.val(),"partName" : partName.val(),"customerId": customerId.val()}, 
				function(result){
						partId = result.partId;
				});
		});
		
		var workOrderNoList = {
			source: function( request, response ) {
				getJSON("workOrderAutoComplete",
						{"partId" : partId,"workOrderNo" : workorderNo.val()} ,
					function(result){
						response($.map(result,function(item){
							return {
								label: item.workOrderNo,
								workorderNo: item.workOrderNo
							};
					}));
				});
			},
				minLength: 1,
				delay: 1000
		};
		workorderNo.autocomplete(workOrderNoList);
	});

</script>
</head>
<body>
	<h1 class="header"><spring:message code='menu.ActualResultsListperProduct'/></h1>
	<page:message item="${dailyWK}" />
	<form:form id="mrdc_s12Form" method="post" action="MRDC_R12_export.html" commandName="dailyWK">
		<table width="100%" border="0" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
			<tr>
				<th width="16%">Operation Date (From - To) <span class="textred">*</span></th>
				<td width="34%"><form:input path="reportDateFr" cssClass="date" title="Operation Date (From)"/> - <form:input path="reportDateTo" cssClass="date" title="Operation Date (To)"/></td>
				<th width="20%">Customer</th>
				<td width="30%"><form:select path="customerId" id="customerId" items="${customerMap}"/></td>
			</tr>
			<tr>
				<th width="16%">Process</th>
				<td width="34%"><form:select path="wip" id="wip" items="${wip}"/></td>
				<th width="20%">Machine No</th>
				<td width="30%"><form:select path="machineId" items="${machineMap}" id="boxMachineNo"/></td>
			</tr>
			<tr>
				<th width="16%" class="label">Part No</th>
				<td width="34%"><form:input path="partNo" id="boxPartNo"/></td>
				<th width="20%" class="label">Part Name</th>
				<td width="30%"><form:input path="partName" id="boxPartName" size="80"/></td>
			</tr>
			<tr>
				<th width="16%">Career Sheet No(Work Order No)</th>
				<td width="34%" colspan="3"><form:input path="workOrderNo" id = "boxWorkOrderNo"/></td>
			</tr>
		</table>
		<br/>
		<!-- security:authorize ifAnyGranted="MRDC_R12_EXPORT"> -->
			<div align="right">
				<input name="btnExport" type="button" id="btnExport" value="Summary Report"/>
			</div>
		<!-- /security:authorize> -->
	</form:form>
</body>
</html>