var btnSearch;
var wipStockForm;
var btnSave;
var boxWIP;
var boxCustom;
var reportDate;
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
	$(".adjustStock").each(function() {
		this.adjustStock = $(this);
		this.adjustReason = $($(".adjustReason")[index]);

		if (this.adjustStock.val() == '' && this.adjustReason.val() != '') {
			msg.push({
				"code" : "err.cmm.002",
				"arguments" : [ index + 1, "Adjust Stock Qty" ]
			});
			hasErr = false;
		}
		if (this.adjustStock.val() != '' && this.adjustReason.val() == '') {
			msg.push({
				"code" : "err.cmm.002",
				"arguments" : [ index + 1, "Adjust Reason" ]
			});
			hasErr = false;
		}
		if (this.adjustStock.val() == '' && this.adjustReason.val() == '') {
			i++;
		}
		message.setErrors(msg);
		index++;
	});
	if (i == index) {
		msg.push({
			"code" : "err.cmm.003",
			"arguments" : [ "", "" ]
		});
		message.setErrors(msg);
		hasErr = false;
	}

	return hasErr;
}

function fnSave() {
	// check save button
	btnSave.click(function() {
		var pass = checkInput();
		// Confirm to save data?
		if (pass && confirm(message.getMessage('cfm.cmm.001'))) {
			wipStockForm.attr("action", "WIP_S03_save.html");
			wipStockForm.submit();
			disableSaveBtn();
		}

	});
	enableSaveBtn();
}

function fnSearch() {
	// check search button
	btnSearch.click(function() {
		if ('' == $("#reportDate").val()) {
			message.setErrors([ {
				"code" : "err.cmm.001",
				"arguments" : [ "Stock Date" ]
			} ]);
			return;
		}
		wipStockForm.attr("action", "WIP_S03_search.html");
		wipStockForm.submit();
	});
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
	$('#uploadForm').attr("action", "WIP_S03_import.html");
	$('#uploadForm').submit();

}

// JQuery
// initialze script run
$(document).ready(
		function() {
			btnResetStock = $("#btnResetStock");
			btnSearch = $("#btnSearch");
			wipStockForm = $("form#wipStockForm");
			btnSave = $("#btnSave");
			boxWIP = $("select#boxWIP");
			boxCustom = $("select#boxCustom");
			reportDate = $("#reportDate");
			btnExport = $("#btnExport");
			btnImport = $("#btnImport");

			$("#boxCustom option:first").val("");
			// <!-- Initial: Set 'maxDate'. -->
			reportDate.datepicker("option", "maxDate", '0d');

			// <!-- check max length textarea -->
			$("textarea").keyup(function() {
				var max = 500;
				if ($(this).val().length > max) {
					$(this).val($(this).val().substr(0, max));
				}
			});

			// <!-- if the letter is not digit then display error and don't type
			// anything -->
			$(".adjustStock").keypress(
					function(e) {
						if (e.which != 8 && e.which != 0
								&& (e.which < 48 || e.which > 57)) {
							return false;
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

			boxWIP.focus();

			fnSearch();
			fnSave();

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

			btnResetStock.click(function() {
				if (0 < $(".adjustStock").length) {
					if (confirm(message.getMessage('cfm.cmm.005'))) {
						$(".adjustStock").each(function() {
							this.adjustStock = $(this);
							this.adjustStock.val(0);
						});
					}
				} else {
					alert("Not have data for adjust.");
				}
			});

			btnExport.click(function() {
				message.clear();
				if ('' == $("#reportDate").val()) {
					message.setErrors([ {
						"code" : "err.cmm.001",
						"arguments" : [ "Stock Date" ]
					} ]);
					return;
				}
				downloadNotify($("<div title='"+message.getMessage("downloadAlertContent")+"'/>'>"+message.getMessage("downloadAlertContent")+"</div>"));				
				wipStockForm.attr("action", "WIP_S03_export.html");
				wipStockForm.submit();
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
