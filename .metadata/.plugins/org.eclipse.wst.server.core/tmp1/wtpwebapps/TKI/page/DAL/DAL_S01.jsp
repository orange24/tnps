<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp"%>
<script language="javascript">
	var dailyMCForm;
	var boxWIPCode;
	var wip;
	var reportDateFr;
	var reportDateTo;
	var partNo;
	var partName;
	var reportType;
	var machineNo;

	$(document).ready(function() {
		dailyMCForm = $("form#dailyMCForm");
		boxWIPCode = $("select#boxWIPCode");
		wip = $("select#wip");
		reportDateFr = $("input#reportDateFr");
		reportDateTo = $("input#reportDateTo");
		partNo = $("input#partNo");
		partName = $("input#partName");
		reportType = $("select#reportType");
		machineNo = $("input#machineNo");

		dailyMCForm.submit(function() {
			message.clear();
			DateUtil.compare($("input[name=reportDateFr]"), $("input[name=reportDateTo]"));
			return message.isNoError();
		});

		$("input#btnSearch").click(function() {
			var errors = [];
			if (reportDateFr.val() == "" && reportDateTo.val() == "") {
				errors.push({
					"code" : "err.cmm.001",
					"arguments" : [ "Report Date" ]
				});
			}
			if (errors.length > 0) {
				message.setErrors(errors);
				return false;
			}
			message.clear();

			// <!-- CALL: 'DAL_S01Controller'. -->
			dailyMCForm.attr("action", "DAL_S01_search.html");
		});

		$("input#btnExport").click(function() {
			var errors = [];
			if (reportDateFr.val() == "" && reportDateTo.val() == "") {
				errors.push({
					"code" : "err.cmm.001",
					"arguments" : [ "Report Date" ]
				});
			}
			var dateFrom = $("input[name=reportDateFr]");
			var dateTo = $("input[name=reportDateTo]");
			if (dateFrom.val() != "" && dateTo.val() != "") {
				if (dateFrom.datepicker("getDate").getTime() > dateTo.datepicker("getDate").getTime()) {
					errors.push({
						"code" : "err.cmm.008",
						"arguments" : [dateTo.attr("title"), dateFrom.attr("title")]
					});
				}
			}

			if (errors.length > 0) {
				message.setErrors(errors);
				return false;
			}
			message.clear();

			// count total data
			var params = {
				"wip" : boxWIPCode.val(),
				"machineNo" : machineNo.val(),
				"partNo" : partNo.val(),
				"partName" : partName.val(),
				"reportDateFr" : reportDateFr.val(),
				"reportDateTo" : reportDateTo.val(),
				"reportType" : reportType.val()
			};

			// <!-- notify before export report -->
			downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));

			getJSON("DAL_R01_export_count",params,function(result) {
				if (result.count > result.maxRecord && result.size == result.maxRecord) {
					alert("<spring:message code='cfm.excel.001'/>".replace(/\{0\}/g, result.maxRecord).replace(/\{1\}/g, result.count));
				}

				// <!-- CALL: 'DAL_S01Controller'. -->
				dailyMCForm.attr("action", "DAL_R01_export.xls");
				dailyMCForm.submit();
				dailyMCForm.attr("action", "DAL_S01_search.html");
			});
			return false;
		});
	});

	function deleteDailyMC(row) {
		var rowNo = row.find("td:first-child").html().trim();
		if (!confirm("<spring:message code='cfm.cmm.003'/>".replace(/\{0\}/g, rowNo)))
			return;

		dailyMCForm.attr("action", "DAL_S01_delete.html").append("<input type='hidden' name='dailyMCId' value='"+ row.attr("id") + "'/>").submit();
	}
</script>
</head>
<body>
	<h1>
		<spring:message code='menu.DailyActual(DC)' />
	</h1>
	<ul id="navlist">
		<li>
			<a href="DAL_S01.html" id="current">Daily Actual (DC) Search/List</a>
		</li>
		<li>
			<a href="DAL_S02.html">Daily Actual (DC) Add/Edit</a>
		</li>
	</ul>
	<page:message item="${dailyMC}" />

	<!-- Search Criteria -->
	<form:form id="dailyMCForm" methodParam="post" commandName="dailyMC">
		<table width="100%" border="0" cellpadding="3" cellspacing="1">
			<tr>
				<td colspan="2">
					<table class="ui-widget ui-widget-content" cellpadding="3" cellspacing="1" width="100%" border="0">
						<tr>
							<th width="20%">
								Report Date (From - To) <span class="textred">*</span>
							</th>
							<td width="39%">
								<form:input path="reportDateFr" cssClass="date" title="Report Date (From)" /> 
								- 
								<form:input path="reportDateTo" cssClass="date" title="Report Date (To)" />
							</td>
							<th width="13%">Report Type</th>
							<td width="28%">
								<form:select path="reportType">
									<form:option value="">-- All Types --</form:option>
									<form:options items="${reportTypeList}" itemLabel="typeName" itemValue="typeCode" />
								</form:select>
							</td>
						</tr>
						<tr>
							<th>WIP</th>
							<td>
								<form:select path="wip" tabindex="1" items="${wipMap}"></form:select>
							</td>
							<th>Machine No.</th>
							<td>
								<form:input path="machineNo" />
							</td>
						</tr>
						<tr>
							<th>Part No</th>
							<td>
								<form:input path="partNo" />
							</td>
							<th>Part Name</th>
							<td>
								<form:input path="partName" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					<security:authorize ifAnyGranted="DAL_R01_EXPORT">
						<input type="submit" value="Summary Report" id="btnExport" />
					</security:authorize>
					<input type="submit" value="Search" id="btnSearch" style="width: 100px" />
				</td>
			</tr>
		</table>

		<c:if test="${not empty dailyMC.dailyMCList}">
			<table width="100%" border="1" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content ">
				<tr>
					<td colspan="8">
						<div style="float: left">
							<page:display item="${dailyMC}" />
						</div>
						<div style="float: right">
							<page:number item="${dailyMC}" />
						</div></td>
				</tr>
				<tr class="submit_button">
					<th>No.</th>
					<th>WIP</th>
					<th>Machine No.</th>
					<th>Part No</th>
					<th>Part Name</th>
					<th>Report Date</th>
					<th>Report Type</th>
					<th>Last Action Date</th>
					<th>Action</th>
				</tr>
				<c:forEach var="detail" items="${dailyMC.dailyMCList}" varStatus="prop" begin="0" step="1">
					<tr id="${detail.dailyMCId}">
						<td align="center">
							<page:rowno item="${dailyMC}" index="${prop.index}" />
						</td>
						<td align="center">${detail.wip}:${detail.wipName}</td>
						<td align="center">${detail.machineNo}:${detail.machineName}</td>
						<td align="center">${detail.partNo}</td>
						<td align="center">${detail.partName}</td>
						<td align="center">
							<fmt:formatDate pattern="dd/MM/yyyy" value="${detail.reportDate}" />
						</td>
						<td align="center">${detail.reportName}</td>
						<td align="center"><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${detail.lastUpdate}" /></td>
						<td align="center">
							<a href="DAL_S02_edit.html?dailyMCId=${detail.dailyMCId}&reportDate=${detail.reportDate}">
								<img src="image/icon/update.gif" border="0" />
							</a>
							<c:if test="${detail.createDate ge minDate}">
								<a href="javascript:void(0);" onclick=" deleteDailyMC( $(this).closest('tr') );">
									<img src="image/icon/delete.gif" border="0" />
								</a>
							</c:if> &nbsp;</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="9">
						<div style="float: left">
							<page:display item="${dailyMC}" />
						</div>
						<div style="float: right">
							<page:number item="${dailyMC}" />
						</div></td>
				</tr>
			</table>
		</c:if>
	</form:form>
</body>
</html>
