var fgGrid;
var fgColumns;
var customerfgGrid;
var customerfgColumns;
var fgData = [];
var fgMaster = [];
var errors = [];
var customerCode;
$(function($) {
	// data
	custFgGrid = $("#custFgGrid");
	fgDialog = $("#fgDialog");
	fgmasterGrid = $("#fgGrid");
	customerNameSel = $("#customerNameSel");
	// getFgCustomertMappingData();
	// view
	createCustomerFgGrid();
	createFgGrid();
	custFgGrid.hide();
	// event
	searchCustomerFgMapping();
	saveCustomerFgMapping();
});

function createCustomerFgGrid() {
	var editOptions = $.extend({}, common.SlickGrid.newGrid().getOptions(), {
		enableAddRow : true
	});
	// Extend default options from common grid
	// <!-- CustomerFG Grid -->
	customerfgColumns = [ {
		id : "statusFlag",
		name : "",
		field : "statusFlag",
		width : 30,
		resizable : true,
		formatter : common.SlickGrid.Formatters.FlagFormatter,
		filter : common.SlickGrid.filterColumnsOptions.ClearFilterButton
	}, {
		id : "rowNo",
		name : "No.",
		field : "rowNo",
		width : 40,
		resizable : true,
		formatter : common.SlickGrid.Formatters.RowNumFormatter,
		sortable : true
	}, {
		id : "customerCode",
		name : "Customer",
		field : "customerCode",
		width : 80,
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id : "fgNo",
		name : "FG No.",
		field : "fgNo",
		width : 150,
		headerCssClass : "editable-column required-column",
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		editor : common.SlickGrid.Editor.DialogEditor,
		editorArgs : {
			dialogContent : $("#fgDialog"),
			setFields : [ "customerCode", "fgId", "fgNo", "fgName" ],
			width : 700,
			height : 500
		}
	}, {
		id : "fgName",
		name : "FG Name",
		field : "fgName",
		width : 200,
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id : "deleteField",
		name : "Delete",
		field : "deleteField",
		width : 50,
		cssClass : "cell-c",
		resizable : true,
		formatter : common.SlickGrid.Formatters.DelFormatter,
		editor : common.SlickGrid.Editor.DelEditor
	} ];
	customerfgGrid = common.SlickGrid.newGrid();
	customerfgGrid = customerfgGrid.setOptions(editOptions);
	customerfgGrid = customerfgGrid.setGrid({
		"id" : "customerfgGrid",
		"columns" : customerfgColumns,
		"data" : fgData,
		"key" : "customer"
	});

	dataView = customerfgGrid.getDataView();

	customerfgGrid.getGrid().onAddNewRow.subscribe(function(e, args) {
		var item = args.item;
		item.customerId = $('#customerNameSel :selected').val();
		item.customerCode = $('#customerNameSel :selected').text();
		dataView.updateItem(item.customer, item);
		dataView.endUpdate();
	});

	customerfgGrid.getGrid().onCellChange.subscribe(function(e, args) {
		var item = args.item;
		item.customerId = $('#customerNameSel :selected').val();
		item.customerCode = $('#customerNameSel :selected').text();
		dataView.updateItem(item.customer, item);
		dataView.endUpdate();
	});
}

function createFgGrid() {
	fgColumns = [ {
		id : "statusFlag",
		name : "",
		field : "statusFlag",
		width : 30,
		resizable : true,
		formatter : common.SlickGrid.Formatters.FlagFormatter,
		filter : common.SlickGrid.filterColumnsOptions.ClearFilterButton,
	}, {
		id : "fgNo",
		name : "FG No.",
		field : "fgNo",
		width : 150,
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id : "fgName",
		name : "FG Name",
		field : "fgName",
		width : 200,
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id : "uom",
		name : "UOM",
		field : "uom",
		width : 80,
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id : "snp",
		name : "SNP",
		field : "snp",
		width : 80,
		cssClass : "cell-r",
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id : "weight",
		name : "Weight",
		field : "weight",
		width : 80,
		cssClass : "cell-r",
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id : "classifyBusiness",
		name : "Classify Business",
		field : "classifyBusiness",
		width : 120,
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id : "place",
		name : "Place",
		field : "place",
		width : 80,
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id : "subBusiness",
		name : "Sub-Business",
		field : "subBusiness",
		width : 100,
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	} ];
	fgGrid = common.SlickGrid.newGrid();
	fgGrid = fgGrid.setGrid({
		"id" : "fgGrid",
		"columns" : fgColumns,
		"data" : {
			url : "S_FGM_S02_selectFGMasterList.html"
		}
	});
}
function getFgCustomertMappingData() {
	customerNameSel = $("#customerNameSel");

	customerfgGrid.reloadData({
		"data" : {
			url : "S_FGM_S02_searchList.html",
			param : {
				customerId : customerNameSel.val()
			}
		}
	});

	customerfgGrid.getGrid().hideHeaderRowColumns();
}
function searchCustomerFgMapping() {
	$("[id=btnSearch]").click(function() {
		message.clear();
		errors.length = 0;

		if (customerNameSel.val() == -2147483648) {
			errors.push({
				"code" : "err.cmm.001",
				"arguments" : [ "Customer" ]
			});
			message.setErrors(errors);
		} else {
			custFgGrid.show();
			getFgCustomertMappingData();
		}
	});
}
function saveCustomerFgMapping() {
	var success = function(bean) {
		message.showMessage(bean);
		customerfgGrid.reloadData(bean);
	};
	$("[id=btnSave]").click(function() {
		message.clear();
		errors.length = 0;
		
		customerfgGrid.getGrid().getEditorLock().commitCurrentEdit();
		customerfgGrid.resetFilter();		
		
		var fgArray = [];
		var linenum = 0;
		var fgList = $.map(customerfgGrid.getData().getItems(), function(n, i) {
			linenum++;
			if (n.fgNo == "" || !(n.fgNo)) {
				errors.push({
					"code" : "err.cmm.002",
					"arguments" : [ linenum, "FG NO." ]
				});
				message.setErrors(errors);

			} else {
				if (n.statusFlag != "d") {
					fgArray.push(n.customerId + ":" + n.fgId);
				}
				return n;
			}
		});

		/* To check duplicate FG No. per customer. */
		var unique_values = {};
		var list_of_values = [];
		linenum = 0;
		$.each(fgArray, function(key, value) {
			linenum++;
			if (!unique_values[value]) {
				unique_values[value] = true;
				list_of_values.push(value);
			} else {
				errors.push({
					"code" : "err.cmm.020",
					"arguments" : [ linenum, "FG No" ]
				});
				return;
			}
		});

		message.setErrors(errors);

		if (message.isNoError() && fgList != "") {
			if (confirm(message.getMessage("cfm.cmm.001"))) {
				postJSONObject("S_FGM_S02_saveList", fgList, success);
			}
		}
	});
}
