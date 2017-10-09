<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
	<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var dailyMCWKForm;
	var boxWIPCode;
	
	$(document).ready(function(){
		dailyMCWKForm = $("form#dailyMCWKForm");
		boxWIPCode = $("select#boxWIPCode");
	
		$("input#btnExport").click(function(){
			if (emptyCreteria()) {
				return;
			}
			if( $("input#reportDateToId").val() != "" ) {
				if( $("input#reportDateFrId").datepicker("getDate") > $("input#reportDateToId").datepicker("getDate") ) {
					message.setErrors([{"code":"err.cmm.008","arguments":["Report Date (To)","Report Date (From)"]}]);
					return;
				}
			}
			message.clear();
			
			// count total data
			var shifts = "";
			$("input#shiftsDay:checked").each(function(index){
				shifts += "&shifts="+$(this).val();
			});
			$("input#shiftsNight:checked").each(function(index){
				shifts += "&shifts="+$(this).val();
			});
			
			var params = {
			               "wip" : $("select#wipSel").val()
			              ,"machineNo" : $("input#machineNoId").val()
			              ,"customerId" : $("select#customerIdSel").val()
			              ,"partNo" : $("input#partNoId").val() 
			              ,"partName" : $("input#partNameId").val() 
			              ,"reportDateFr" : $("input#reportDateFrId").val()
			              ,"reportDateTo" : $("input#reportDateToId").val()
			              ,"reportType" : $("select#reportTypeId").val()
			};
			
			// <!-- notify before export report -->
			downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));
			
			getJSON("DAL_R05_export_count", $.param(params) + shifts, function( result ){
				
				if(result.count > result.maxRecord && result.size == result.maxRecord){
					alert("<spring:message code='cfm.excel.001'/>".replace(/\{0\}/g, result.maxRecord).replace(/\{1\}/g, result.count));
				}
				
				// <!-- CALL: 'DAL_S05Controller'. -->
				dailyMCWKForm.attr("action", "DAL_S05_export.xls");
				dailyMCWKForm.submit();
				dailyMCWKForm.attr("action", "DAL_S05_search.html");
			});
			return false;
			
		}); 
		$("input#btnSearch").click(function(){
			if (emptyCreteria()) {
				return;
			}
			if( $("input#reportDateToId").val() != "" ) {
				if( $("input#reportDateFrId").datepicker("getDate") > $("input#reportDateToId").datepicker("getDate") ) {
					message.setErrors([{"code":"err.cmm.008","arguments":["Report Date (To)","Report Date (From)"]}]);
					return;
				}
			}
			message.clear();
			dailyMCWKForm.attr("action", "DAL_S05_search.html");
			dailyMCWKForm.submit();
		});
	});
	
	function emptyCreteria(){
		if ( $("input#reportDateFrId").val() == "" && $("input#reportDateToId").val() == "" ) {
			message.setErrors([{"code":"err.cmm.001","arguments":["Report Date",null]}]);
			return true;
		}
	}
	
	function deleteDailyMCWK( row ) {
		var rowNo = row.find("td:first-child").html().trim();
		if( !confirm("<spring:message code='cfm.cmm.003'/>".replace(/\{0\}/g, rowNo)) )
			return;
	
		dailyMCWKForm
			.attr("action", "DAL_S05_delete.html")
			.append("<input type='hidden' name='dailyMCWKId' value='"+ row.attr("id") +"'/>")
			.submit();
	}
</script>
</head>
<body>
<h1><spring:message code='menu.DailyActual(Machine)'/></h1>
	<ul id="navlist">
		<li><a href="DAL_S05.html" id="current">Daily Actual (Machine) Search/List</a></li>
		<li><a href="DAL_S06.html" >Daily Actual (Machine) Add/Edit</a></li>
	</ul>
	<page:message item="${dailyMCWK}" />

	<!-- Search Criteria -->
    <form:form id="dailyMCWKForm" methodParam="post" action="DAL_S05_search.html" commandName="dailyMCWK">
    <table width="100%" border="0" cellpadding="3" cellspacing="1">
    <tr>
    	<td colspan="2">
    		<table class="ui-widget ui-widget-content" cellpadding="3" cellspacing="1" width="100%" border="0">
	    		<tr>
					<th width="20%">Report Date (From - To) <span class="textred">*</span></th>
					<td width="35%"><form:input path="reportDateFr" cssClass="date" title="Report Date (From)" id="reportDateFrId" /> - <form:input path="reportDateTo" cssClass="date" title="Report Date (To)" id="reportDateToId" /></td>
					<th width="13%">Report Type</th>
					<td width="32%">
						<form:select path="reportType" id="reportTypeId" >
							<form:option value="">-- All Types --</form:option>
							<form:options items="${reportTypeList}" itemLabel="typeName" itemValue="typeCode"/>
						</form:select>
					</td>
				</tr>
	    		<tr>
	    			<th>WIP</th>
	    			<td><form:select path="wip" items="${wipMap}" id="wipSel" ></form:select></td>
	    			<th>Customer</th>
					<td><form:select path="customerId" items="${customerMap}" id="customerIdSel" ></form:select></td>
	    		</tr>
	    		<tr>
	    			<th>Shift</th>
	    			<td>
	    				<form:checkbox path="shifts" value="D" id="shiftsDay" /> Day
	    				<form:checkbox path="shifts" value="N" id="shiftsNight" /> Night
					</td>
					<th>Machine No.</th>
	    			<td><form:input path="machineNo" id="machineNoId" /></td>
				</tr>
				<tr>
					<th>Part No</th>
					<td><form:input path="partNo" id="partNoId" /></td>
					<th>Part Name</th>
					<td><form:input path="partName" id="partNameId" /></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="right">
		<security:authorize ifAnyGranted="DAL_R02_EXPORT">
			<input type="button" value="Summary Report" id="btnExport" style="width:150px"/>
		</security:authorize>
			<input type="button" value="Search" id="btnSearch" style="width:100px"/>
		</td>
	</tr>
	</table>

	<c:if test="${not empty dailyMCWK.dailyMCWKList}">
	<table cellpadding="3" cellspacing="1" border="1" width="100%" class="ui-widget ui-widget-content">
	<tr>
		<td colspan="9">
			<div style="float:left" ><page:display item="${dailyMCWK}"/></div>
			<div style="float:right"><page:number item="${dailyMCWK}"/></div>
		</td>
	</tr>
	<tr>
		<th>No.</th>
		<th>WIP</th>
		<th>Machine No.</th>
		<th>Report Date</th>
		<th>Shift</th>
		<th>Report Type</th>
		<th>Last Action By</th>
		<th>Last Action Date</th>
		<th>Action</th>
	</tr>
	<c:forEach var="detail" items="${dailyMCWK.dailyMCWKList}" varStatus="prop" begin="0" step="1">
		<tr id="${detail.dailyMCWKId}">
			<td align="center"><page:rowno item="${dailyMCWK}" index="${prop.index}"/></td>
			<td align="center">${detail.wipName}</td>
			<td align="center">${detail.machineNo}</td>
			<td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${detail.reportDate}"/></td>
			<td align="center">${detail.shiftName}</td>
			<td align="center">${detail.reportName}&nbsp;</td>
			<td align="center">${detail.updateBy}</td>
			<td align="center"><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${detail.lastUpdate}"/></td>
			<td align="center">
				<a href="DAL_S06_edit.html?dailyMCWKId=${detail.dailyMCWKId}"><img src="image/icon/update.gif" border="0"/></a>
				<c:if test="${ detail.createDate ge minDate }">
				<a href="javascript:void(0);" onclick=" deleteDailyMCWK( $(this).closest('tr') );"><img src="image/icon/delete.gif" border="0"/></a>
				</c:if>
				&nbsp;
			</td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="9">
			<div style="float:left" ><page:display item="${dailyMCWK}"/></div>
			<div style="float:right"><page:number item="${dailyMCWK}"/></div>
		</td>
	</tr>
	</table>
	</c:if>
	</form:form>

</body>
</html>
