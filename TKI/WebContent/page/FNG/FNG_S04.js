var btnSave;
var btnSearch;
var fngS04Form;
var boxReportDate;
var btnResetStock;
var btnExport;
var btnImport;

function checkInput() {
	var hasErr = true;
	var i = 0;
	var index = 0;
	var msg = [];
	$(".error_message").html("");
	if ('' == $("#reportDate").val()) {
		message.setErrors([ {
			"code" : "err.cmm.001",
			"arguments" : [ "Stock Date" ]
		} ]);
		return;
	}
	$(".adjustStock").each(
			function() {
				this.adjustStock = $(this);
				this.adjustReason = $($(".adjustReason")[index]);

				if ((this.adjustStock.val() === '')
						&& (this.adjustReason.val() != '')) {
					msg.push({
						"code" : "err.cmm.002",
						"arguments" : [ index + 1, "Adjust FG Stock" ]
					});
					hasErr = false;
				}
				if (this.adjustStock.val() != ''
						&& this.adjustReason.val() === '') {
					msg.push({
						"code" : "err.cmm.002",
						"arguments" : [ index + 1, "Adjust Reason" ]
					});
					hasErr = false;
				}
				if (this.adjustStock.val() === ''
						&& this.adjustReason.val() === '') {
					i++;
				}
				message.setErrors(msg);
				index++;
			});
	if (i === index) {
		msg.push({
			"code" : "err.cmm.003",
			"arguments" : [ "", "" ]
		});
		message.setErrors(msg);
		hasErr = false;
	}
	return hasErr;
}

function importFile() {
	var fileImport = $("#fileImport").val();

	message.clear();

	if ('' == fileImport) {
		message.setErrors([ {
			"code" : "err.cmm.001",
			"arguments" : [ "File Import" ]
		} ]);
		return;

	} else {
		if (fileImport.lastIndexOf(".xls") == -1
				&& fileImport.lastIndexOf(".xlsx") == -1) {
			message.setErrors([ {
				"code" : "err.cmm.024",
				"arguments" : [ "" ]
			} ]);
			return;
		}
	}
	$('#uploadForm').attr("action", "FNG_S04_import.html");
	$('#uploadForm').submit();

}

$(document).ready(
		function() {

			boxReportDate = $("#reportDate");
			btnResetStock = $("#btnResetStock");
			btnSave = $("#btnSave");
			btnSearch = $("#btnSearch");
			fngS04Form = $("#fngS04Form");
			btnExport = $("#btnExport");
			btnImport = $("#btnImport");

			$("#customerId option:first").val("");
			// <!-- Initial: Set 'maxDate'. -->
			boxReportDate.datepicker("option", "maxDate", '0d');

			btnResetStock.click(function() {
				if (0 < $(".adjustStock").length) {
					if (confirm("<spring:message code='cfm.cmm.005'/>")) {
						$(".adjustStock").each(function() {
							this.adjustStock = $(this);
							this.adjustStock.val(0);
						});
					}
				} else {
					alert("Not have data for adjust.");
				}
			});

			btnSearch.click(function() {
				if ('' == boxReportDate.val()) {
					message.setErrors([ {
						"code" : "err.cmm.001",
						"arguments" : [ "Stock Date" ]
					} ]);
					return;
				}
				message.clear();
				fngS04Form.attr("action", "FNG_S04_search.html");
				fngS04Form.submit();
			});

			btnSave.click(function() {
				var pass = checkInput();
				if (pass) {
					message.clear();
				}
				if (pass && confirm(message.getMessage("cfm.cmm.001"))) {
					disableSaveBtn();
					fngS04Form.attr("action", "FNG_S04_save.html");
					fngS04Form.submit();
					enableSaveBtn();
				}
			});

			$("#fileImport").change(
					function() {
						if ('' != $("#fileImport").val()) {
							var dlgFirstButton = $('.ui-dialog-buttonpane')
									.find('button:first');
							dlgFirstButton.removeClass('ui-state-disabled');
						}
					});

			// <!-- dialog for import file -->
			$("#uploadForm").dialog(
					{
						bgiframe : true,
						modal : true,
						autoOpen : false,
						height : 200,
						width : 360,
						resizable : false,
						open : function(event, ui) {
							$(".ui-dialog").css("z-index", 1002);
							$(".ui-widget-overlay").css("z-index", 1001);

							if ('' == $("#fileImport").val()) {
								var dlgFirstButton = $('.ui-dialog-buttonpane')
										.find('button:first');
								dlgFirstButton.addClass('ui-state-disabled');
							}
						},
						buttons : {
							OK : function() {
								importFile();
								$(this).dialog("close");
							},
							Cancel : function() {
								$(this).dialog("close");
							}

						}
					});

			btnExport.click(function() {
				if ('' == boxReportDate.val()) {
					message.setErrors([ {
						"code" : "err.cmm.001",
						"arguments" : [ "Stock Date" ]
					} ]);
					return;
				}
				message.clear();
				downloadNotify($("<div title='"+message.getMessage("downloadAlertContent")+"'/>'>"+message.getMessage("downloadAlertContent")+"</div>"));
				fngS04Form.attr("action", "FNG_S04_export.html");
				fngS04Form.submit();
			});

			btnImport.click(function() {
				$("#uploadForm").dialog("open");
			});
		});

function disableSaveBtn() {
	btnSave.attr("disabled", true);
}

function enableSaveBtn() {
	btnSave.attr("disabled", false);
}