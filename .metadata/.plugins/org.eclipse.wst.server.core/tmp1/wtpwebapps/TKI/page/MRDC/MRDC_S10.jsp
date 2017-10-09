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

var MRDCS10Form;
var boxYearMonthDateFr;
var boxYearMonthDateTo;
var boxWip;
var partNo;
var partName;
var workorderNo;
var boxMachine;

	$(document).ready(function() {
		MRDCS10Form 		= $("#MRDCS10Form");
		boxYearMonthDateFr 	= $("#boxYearMonthDateFr");
		boxYearMonthDateTo 	= $("#boxYearMonthDateTo");
		boxWip				= $("select#boxWip");
		partNo				= $("input#partNo");
		partName			= $("input#partName");
		workorderNo			= $("input#workorderNo");
		boxMachine			= $("select#boxMachineNo");
		
		boxWip.change(function(){
			comboBox.setMachineName(boxMachine, $(this));
		});
		
		$("input#btnExport").click(function(){
			var errors = [];
			if((boxYearMonthDateFr.val() == "")&&(boxYearMonthDateTo.val() == "")){
				errors.push({"code": "err.cmm.001", "arguments": ["Operation Date"]});
			}
			if((boxYearMonthDateFr.val() != "")&&(boxYearMonthDateTo.val() != "")&& (boxYearMonthDateFr.datepicker("getDate").getTime() > boxYearMonthDateTo.datepicker("getDate").getTime())) {
				errors.push({"code":"err.cmm.008", "arguments":["Operation Date date To","Operation Date date From"]});
			}
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
			message.clear();
			$("#wipName").val(boxWip.find("option:selected").text());
			
			downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));
			
			var params = {
		               "wip" 			: boxWip.val()
		              ,"partNo" 		: partNo.val() 
		              ,"partName" 		: partName.val() 
		              ,"workOrderNo" 	: workorderNo.val()
		              ,"machineId" 		: boxMachine.val()
		              ,"reportDateFr" 	: boxYearMonthDateFr.val()
		              ,"reportDateTo" 	: boxYearMonthDateTo.val()
			};
			
			getJSON("MRDC_R10_export_count", $.param(params), function( result ){
				
				if(result.count > result.maxRecord && result.size == result.maxRecord){
					alert("<spring:message code='cfm.excel.001'/>".replace(/\{0\}/g, result.maxRecord).replace(/\{1\}/g, result.count));
				}
				
				MRDCS10Form.submit();
			});
			return false;
		});
		
		// <!-- Initial: Auto Completion. -->
		var partNoList = {
			source: function( request, response ) {
				getJSON("partAutoComplete",  
						{"partNo" : partNo.val(),"partName" : partName.val(),"wip" : boxWip.val(),"customerId": -1}, 
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
						{"partNo" : partNo.val(),"partName" : partName.val(),"wip": boxWip.val(),"customerId": -1}, 
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
				{"partNo" : partNo.val(),"partName" : partName.val(),"customerId": -1,"wip" : boxWip.val()}, 
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
	<h1 class="header"><spring:message code='menu.DailyDeliveryInspection'/></h1>
	<form:form method="post" id="MRDCS10Form" commandName="dailyInspection" action="MRDC_R10_export.html">
	<page:message item="${dailyInspection}"/>

	<table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
        <tr>
        	<th width="20%">Operation Date (From-To) <span class="textred">*</span></th>
    		<td width="30%">
    			<form:input path="reportDateFr" id="boxYearMonthDateFr" cssClass="date"/>
    			<form:input path="reportDateTo" id="boxYearMonthDateTo" cssClass="date"/>
    		</td>
    		<th width="20%">Process</th>
    		<td width="30%">
    			<form:select path="wip" items="${wipMap}" id="boxWip"/>
    			<form:hidden path="wipName" id="wipName" value=""/>
    		</td>
        </tr>
        <tr>
				<th width="20%" class="label">Part No</th>
				<td width="30%"><form:input path="partNo" id="partNo"/></td>
				<th width="20%" class="label">Part Name</th>
				<td width="30%"><form:input path="partName" id="partName" size="80"/></td>
			</tr>
        <tr>
    		<th width="20%">Machine</th>
    		<td width="30%"><form:select path="machineId" items="${machineMap}" id="boxMachineNo"/></td>    		
        	<th width="20%">Career Sheet No(Work Order No)</th>
    		<td width="30%"><form:input path="workOrderNo" id = "workorderNo"/></td>
        </tr>
    </table>
    <br/>
	   <div align="right">
	   	<input type="button" value="Summary Report" id="btnExport" style="width:150px"/>
	   </div>
    <br/>
</form:form>
</body>
</html>
