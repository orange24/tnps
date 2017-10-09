<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var wip;
	var boxMachine;
	var btnExport;
	var mrdc_s17Form;
	var reportDateFr;
	var reportDateTo;
	
	$(document).ready(function(){
		wip				= $("select#wip");
		boxMachine		= $("select#boxMachine");
		btnExport		= $("input#btnExport");
		mrdc_s17Form	= $("form#mrdc_s17Form");
		reportDateFr	= $("input#reportDateFr");
		reportDateTo	= $("input#reportDateTo");
		
		wip.change(function(){
			comboBox.setMachineName(boxMachine, $(this));
		});
		
		btnExport.click(function(){
			var errors = [];
			if((reportDateFr.val() == "")&&(reportDateTo.val() == "")){
				errors.push({"code": "err.cmm.001", "arguments": ["Report Date"]});
			}else if((reportDateFr.val() != "")&&(reportDateTo.val() != "")){
				if(reportDateFr.datepicker("getDate").getTime() > reportDateTo.datepicker("getDate").getTime()) {
					errors.push({"code":"err.cmm.008", "arguments":["Report Date To","Report Date From"]});
				}
			}
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
			message.clear();
			downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));
			
			var params = {
		               "machineId" 		: boxMachine.val()
		              ,"reportDateFr" 	: reportDateFr.val() 
		              ,"reportDateTo" 	: reportDateTo.val() 
		              ,"wip" 			: wip.val()
		              ,"sorting" 		: $("select#sorting").val()
			};
			
			getJSON("MRDC_R17_export_count", $.param(params), function( result ){
				
				if(result.count > result.maxRecord && result.size == result.maxRecord){
					alert("<spring:message code='cfm.excel.001'/>".replace(/\{0\}/g, result.maxRecord).replace(/\{1\}/g, result.count));
				}
				
				mrdc_s17Form.submit();
			});
			return false;
		});		
	});
</script>
</head>
<body>
	<h1 class="header"><spring:message code='menu.DailyMachineStopList'/></h1>
	<page:message item="${tDailyMC}" />
	<form:form id="mrdc_s17Form" method="post" action="MRDC_S17_export.html" commandName="tDailyMC">
		<table width="100%" border="0" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
			<tr>
				<th width="20%" class="label">Report Date (From-To)<span class="textred">*</span></th>
				<td width="30%">
					<form:input path="reportDateFr" id="reportDateFr" cssClass="date"/>
    				<form:input path="reportDateTo" id="reportDateTo" cssClass="date"/>
				</td>
				<th width="20%" class="label">Sorting</th>
				<td colspan="3">
					<form:select path="sorting" id="sorting">
						<form:option value="1">Report Date</form:option>
						<form:option value="2">Part No.</form:option>
					</form:select>
				</td>
			</tr>
			<tr>
				<th width="20%">Process</th>
				<td width="30%"><form:select path="wip" id="wip" items="${wip}"/></td>
				<th width="20%">Machine</th>
				<td width="30%"><form:select path="machineId" items="${machineMap}" id="boxMachine"/></td>
			</tr>
		</table>
		<br/>
		<div align="right">
			<input name="btnExport" type="button" id="btnExport" value="Summary Report"/>
		</div>
	</form:form>
</body>
</html>