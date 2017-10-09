// JQuery
var fngForm;
var dateFrom;
var dateTo;
var reportType;
var customerId;
var fgNo;
var fgName;
var selectType;

$(function() {
	fngForm = $("#fngForm");
	dateFrom = $("#dateFrom");
	dateTo = $("#dateTo");
	reportDate = $("#reportDate");
	customerId = $("#customerId");
	fgNo = $("#fgNo");
	fgName = $("#fgName");
	selectType = $("#selectType");

	$("#customerId option:first").val("");

	$(document).ready(function() {
		$("#dateFrom").focus();
	});

	$("#btnSearch").click(function() {
		message.clear();
		DateUtil.compare(dateFrom, dateTo);
		if (message.isNoError()) {
			$("select[id=pageNumber]").attr("disabled", true);
			$("select[id=pageCount]").attr("disabled", true);
			$("form").submit();
		}
	});

	$("input#btnExport").click(
			function() {
				message.clear();
				DateUtil.compare(dateFrom, dateTo);
				if (dateFrom.val() === ""
						&& dateTo.val() === ""
						&& customerId.val() === ""
						&& fgNo.val() === ""
						&& fgName.val() === ""
						&& selectType.val() === "") {
					message.setErrors([ {
						"code" : "err.cmm.001",
						"arguments" : [ "Search Criteria" ]
					} ]);
				}
				if (message.isNoError()) {
					// <!-- notify before export report -->
					downloadNotify($("<div title='"
							+ message.getMessage("downloadAlertContent")
							+ "'/>"
							+ message.getMessage("downloadAlertContent")
							+ "</div>"));

					fngForm.attr("action", "FNG_S02_export.xls");
					fngForm.submit();
					fngForm.attr("action", "FNG_S02_search.html");
				}
			});

	$("input#btnExport2").click(
			function() {
				message.clear();
				DateUtil.compare(dateFrom, dateTo);
				if (dateFrom.val() === ""
						&& dateTo.val() === ""
						&& customerId.val() === ""
						&& fgNo.val() === ""
						&& fgName.val() === ""
						&& selectType.val() === "") {
					message.setErrors([ {
						"code" : "err.cmm.001",
						"arguments" : [ "Search Criteria" ]
					} ]);
				}
				if (message.isNoError()) {
					// <!-- notify before export report -->
					downloadNotify($("<div title='"
							+ message.getMessage("downloadAlertContent")
							+ "'/>"
							+ message.getMessage("downloadAlertContent")
							+ "</div>"));

					fngForm.attr("action", "FNG_S03_export.xls");
					fngForm.submit();
					fngForm.attr("action", "FNG_S02_search.html");
				}
			});

	$("input#btnExportNirvana").click(
			function() {
				message.clear();
				DateUtil.compare(dateFrom, dateTo);
				if (dateFrom.val() === ""
						&& dateTo.val() === ""
						&& customerId.val() === ""
						&& fgNo.val() === ""
						&& fgName.val() === ""
						&& selectType.val() === "") {
					message.setErrors([ {
						"code" : "err.cmm.001",
						"arguments" : [ "Search Criteria" ]
					} ]);
				}
				if (message.isNoError()) {
					// <!-- notify before export report -->
					downloadNotify($("<div title='"
							+ message.getMessage("downloadAlertContent")
							+ "'/>"
							+ message.getMessage("downloadAlertContent")
							+ "</div>"));

					fngForm.attr("action", "NIR_R02_export.xls");
					fngForm.submit();
					fngForm.attr("action", "FNG_S02_search.html");
				}
			});
});