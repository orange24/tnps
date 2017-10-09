<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp"%>
<script language="javascript">
	var dailyWKForm;
	var boxWIPCode;
	var wip;
	var reportDateFr;
	var reportDateTo;
	var partNo;
	var partName;
	var reportType;

	$(document).ready(function() {
		dailyWKForm = $("form#dailyWKForm");
		boxWIPCode = $("select#boxWIPCode");
		wip = $("select#wip");
		reportDateFr = $("input#reportDateFr");
		reportDateTo = $("input#reportDateTo");
		partNo = $("input#partNo");
		partName = $("input#partName");
		reportType = $("select#reportType");

		/*dailyWKForm.submit(function(){
			message.clear();
			DateUtil.compare( $("input[name=reportDateFr]"), $("input[name=reportDateTo]") );
			if (wip.val()==""
					&& reportDateFr.val() ==""
					&& reportDateTo.val() == ""
					&& partNo.val() == ""
					&& partName.val() == ""
					&& !$("input[name=shifts]:checked").val()
					&& reportType.val()=="") {
				message.setError("err.cmm.001", ["Criteria"]);
				return message.isNoError();
			}
			if( !$("input[name=shifts]:checked").val() )
				message.setError("err.cmm.001", ["Shift"]);
			return message.isNoError();
		});*/

		$("input#btnSearch").click(function() {
			if (checkInput())
				return;
			message.clear();
			// <!-- CALL: 'DAL_S03Controller'. -->
			dailyWKForm.attr("action", "DAL_S03_search.html");
			dailyWKForm.submit();
		});

		$("input#btnExport").click(function() {
			if (checkInput())
				return;
			message.clear();

			// count total data
			var shifts = "";
			$("input[name=shifts]:checked").each(function(index) {
					shifts += "&shifts=" + $(this).val();
			});

			var params = {
				"wip" : wip.val(),
				"partNo" : partNo.val(),
				"partName" : partName.val(),
				"reportDateFr" : reportDateFr.val(),
				"reportDateTo" : reportDateTo.val(),
				"reportType" : reportType.val()
			};

			downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));

			getJSON("DAL_R02_export_count",$.param(params) + shifts,function(result) {
				if (result.count > result.maxRecord
						&& result.size == result.maxRecord) {
					alert("<spring:message code='cfm.excel.001'/>".replace(/\{0\}/g, result.maxRecord).replace(/\{1\}/g, result.count));
				}

				// <!-- CALL: 'DAL_S03Controller'. -->
				dailyWKForm.attr("action", "DAL_S03_export.xls");
				dailyWKForm.submit();
				dailyWKForm.attr("action", "DAL_S03_search.html");
			});
			return false;
		});
	});

	function deleteDailyWK(row) {
		var rowNo = row.find("td:first-child").html().trim();
		if (!confirm("<spring:message code='cfm.cmm.003'/>".replace(/\{0\}/g, rowNo)))
			return;

		dailyWKForm
			.attr("action", "DAL_S03_delete.html")
			.append("<input type='hidden' name='dailyWKId' value='"+ row.attr("id") +"'/>")
			.submit();
	}
	function checkInput() {
		var errors = [];
		if (reportDateFr.val() == "" && reportDateTo.val() == "") {
			errors.push({
				"code" : "err.cmm.001",
				"arguments" : [ "Report Date" ]
			});
		}
		if (errors.length > 0) {
			message.setErrors(errors);
			return true;
		}
	}
</script>
<style type="text/css">
<!--
.style1 {
	color: #FF0000
}
-->
</style>
</head>
<body>
	<div id="downloadDialog" style="display: none" title=""></div>
	<h1>
		<spring:message code='menu.DailyActual(Worker)' />
	</h1>
	<ul id="navlist">
		<li>
			<a href="DAL_S03.html" id="current">Daily Actual (Worker) Search/List</a>
		</li>
		<li>
			<a href="DAL_S04.html">Daily Actual (Worker) Add/Edit</a>
		</li>
	</ul>
	<page:message item="${dailyWK}" />

	<!-- Search Criteria -->
	<form:form id="dailyWKForm" methodParam="post" commandName="dailyWK">
		<table width="100%" border="0" cellpadding="3" cellspacing="1">
			<tr>
				<td colspan="2">
					<table class="ui-widget ui-widget-content" cellpadding="3" cellspacing="1" width="100%" border="0">
						<tr>
							<th width="20%">Report Date (From - To) <span
								class="textred">*</span>
							</th>
							<td width="35%">
								<form:input path="reportDateFr" cssClass="date" title="Report Date (From)" /> 
								- 
								<form:input path="reportDateTo" cssClass="date" title="Report Date (To)" />
							</td>
							<th width="13%">WIP</th>
							<td width="32%">
								<form:select path="wip" tabindex="1" items="${wipMap}"></form:select>
							</td>
						</tr>
						<tr>
							<th>Part No.</th>
							<td><form:input path="partNo" />
							</td>
							<th>Part Name</th>
							<td><form:input path="partName" />
							</td>
						</tr>
						<tr>
							<th>Shift</th>
							<td>
								<form:checkbox path="shifts" value="D" /> Day 
								<form:checkbox path="shifts" value="N" /> Night
							</td>
							<th>Report Type</th>
							<td>
								<form:select path="reportType">
									<form:option value="">-- All Types --</form:option>
									<form:options items="${reportTypeList}" itemLabel="typeName" itemValue="typeCode" />
								</form:select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					<security:authorize ifAnyGranted="DAL_R03_EXPORT">
						<input type="button" value="Summary Report" id="btnExport" style="width: 120px" />
					</security:authorize>
					<input type="button" value="Search" id="btnSearch" style="width: 100px" />
				</td>
			</tr>
		</table>

		<c:if test="${not empty dailyWK.dailyWKList}">
			<table cellpadding="3" cellspacing="1" border="1" width="100%"
				class="ui-widget ui-widget-content">
				<tr>
					<td colspan="8">
						<div style="float: left">
							<page:display item="${dailyWK}" />
						</div>
						<div style="float: right">
							<page:number item="${dailyWK}" />
						</div>
					</td>
				</tr>
				<tr class="table_title">
					<th>No.</th>
					<th>WIP</th>
					<th>Report Date</th>
					<th>Shift</th>
					<th>Report Type</th>
					<th>Last Action By</th>
					<th>Last Action Date</th>
					<th>Action</th>
				</tr>
				<c:forEach var="detail" items="${dailyWK.dailyWKList}" varStatus="prop" begin="0" step="1">
					<tr id="${detail.dailyWKId}">
						<td class="border_all" align="center">
							<page:rowno item="${dailyWK}" index="${prop.index}" />
						</td>
						<td class="border_all" align="center">${detail.wipName}</td>
						<td class="border_all" align="center">
							<fmt:formatDate pattern="dd/MM/yyyy" value="${detail.reportDate}" />
						</td>
						<td class="border_all" align="center">${detail.shiftName}</td>
						<td class="border_all" align="center">${detail.reportName}</td>
						<td class="border_all" align="center">${detail.updateBy}</td>
						<td class="border_all" align="center">
							<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${detail.lastUpdate}" />
						</td>
						<td class="border_all" align="center">
							<a href="DAL_S04_edit.html?dailyWKId=${detail.dailyWKId}">
								<img src="image/icon/update.gif" border="0" />
							</a> 
							<c:if test="${ detail.createDate ge minDate }">
								<a href="javascript:void(0);" onclick=" deleteDailyWK( $(this).closest('tr') );">
									<img src="image/icon/delete.gif" border="0" />
								</a>
							</c:if> &nbsp;
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="9">
						<div style="float: left">
							<page:display item="${dailyWK}" />
						</div>
						<div style="float: right">
							<page:number item="${dailyWK}" />
						</div>
					</td>
				</tr>
			</table>
		</c:if>
	</form:form>
</body>
</html>
