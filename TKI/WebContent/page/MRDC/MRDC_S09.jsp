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
	var partNo;
	var partName;
	var btnExport;
	var mrdc_s09Form;
	var boxYearMonthDateFr;
	var boxYearMonthDateTo;
	
	$(document).ready(function(){
		wip					= $("select#wip");
		partNo				= $("input#partNo");
		partName			= $("input#partName");
		btnExport			= $("input#btnExport");
		mrdc_s09Form		= $("form#mrdc_s09Form");
		boxYearMonthDateFr	= $("input#boxYearMonthDateFr");
		boxYearMonthDateTo	= $("input#boxYearMonthDateTo");
		
		btnExport.click(function(){
			var errors = [];
			if((boxYearMonthDateFr.val() == "")&&(boxYearMonthDateTo.val() == "")){
				errors.push({"code": "err.cmm.001", "arguments": ["Operation Date"]});
			}else if((boxYearMonthDateFr.val() != "")&&(boxYearMonthDateTo.val() != "")){
				if(boxYearMonthDateFr.datepicker("getDate").getTime() > boxYearMonthDateTo.datepicker("getDate").getTime()) {
					errors.push({"code":"err.cmm.008", "arguments":["Operation Date To","Operation Date From"]});
				}
			}
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
			message.clear();
			if(wip.val() != ''){
				$('#wipName').val(wip.find("option:selected").text());
			}
			downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));
			
			var params = {
		               "wip" : wip.val()
		              ,"partNo" 	: partNo.val() 
		              ,"partName" 	: partName.val() 
		              ,"reportDateFr" 	: boxYearMonthDateFr.val()
		              ,"reportDateTo" 	: boxYearMonthDateTo.val()
		              ,"sorting" 	: $("select#sorting").val()
			};
			
			getJSON("MRDC_R09_export_count", $.param(params), function( result ){
				
				if(result.count > result.maxRecord && result.size == result.maxRecord){
					alert("<spring:message code='cfm.excel.001'/>".replace(/\{0\}/g, result.maxRecord).replace(/\{1\}/g, result.count));
				}
				
				mrdc_s09Form.submit();
			});
			return false;
		});
		
		// <!-- Initial: Auto Completion. -->
		var partNoList = {
			source: function( request, response ) {
				getJSON("partAutoComplete",  
						{"partNo" : partNo.val(),"partName" : partName.val(),"wip" : wip.val(),"customerId": -1}, 
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
						{"partNo" : partNo.val(),"partName" : partName.val(),"wip": wip.val(),"customerId": -1}, 
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
	<h1 class="header"><spring:message code='menu.DiecastActualResultCheckList'/></h1>
	<page:message item="${tDailyMC}" />
	<form:form id="mrdc_s09Form" method="post" action="MRDC_S09_export.html" commandName="tDailyMC">
		<table width="100%" border="0" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
			<tr>
				<th width="20%" class="label">Operation Date (From-To)<span class="textred">*</span></th>
				<td width="30%">
					<input type='hidden' name='wipName' id="wipName" value=''/>
					<form:input path="reportDateFr" id="boxYearMonthDateFr" cssClass="date"/>
    				<form:input path="reportDateTo" id="boxYearMonthDateTo" cssClass="date"/>
				</td>
				<th width="20%" class="label">Process</th>
				<td width="30%"><form:select path="wip" id="wip" items="${wip}"/></td>
			</tr>
			<tr>
				<th width="20%" class="label">Part No</th>
				<td width="30%"><form:input path="partNo" id="partNo"/></td>
				<th width="20%" class="label">Part Name</th>
				<td width="30%"><form:input path="partName" id="partName" size="80"/></td>
			</tr>
			<tr>
				<th width="20%" class="label">Sorting</th>
				<td colspan="3">
					<form:select path="sorting" id="sorting">
						<form:option value="1">Report Date</form:option>
						<form:option value="2">Part No.</form:option>
					</form:select>
				</td>
			</tr>
		</table>
		<br/>
		<div align="right">
			<input name="btnExport" type="button" id="btnExport" value="Summary Report"/>
		</div>
	</form:form>
</body>
</html>