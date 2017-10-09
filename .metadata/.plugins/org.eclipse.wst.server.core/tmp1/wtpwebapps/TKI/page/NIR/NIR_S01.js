var nirvanaResult;
var nirvanaMasterGrid;
var errors = [];

$(function($) {
	nirvanaResult = $("#nirvanaResult");
	nirvanaMasterGrid = $("#nirvanaMasterGrid");
	createNirvanaMasterGrid();
	nirvanaResult.hide();
	// Bind event
	bindOnClickSearch();
	bindOnClickSave();
});

/**
 * Bind event on clicked search button to find Nirvana master sync according criteria on screen.
 */
function bindOnClickSearch() {
	// check search button
	$("#btnSearch").click(function() {
		message.clear();
		nirvanaResult.show();
		nirvanaMasterGrid.reloadData({
			"data" : {
				url : "NIR_S01_search.html",
				param : {
					syncDateFrom : $("#syncDateFrom").val(),
					syncDateTo : $("#syncDateTo").val(),
					transType : $("#transType").val(),
					syncStatus : $("#syncStatus").val(),
					fgNo : $("#fgNo").val(),
					fgName : $("#fgName").val(),
					customerCode : $("#customerCode").val(),
					customerName : $("#customerName").val(),
				}
			}
		});
		// Clear checkbox.
		nirvanaMasterGrid.getGrid().hideHeaderRowColumns();
		nirvanaMasterGrid.getGrid().setSelectedRows([]);
	});
}

/**
 * Bind event on clicked save button to fixing data status error to fixed.
 */
function bindOnClickSave() {
	var success = function(bean) {
		message.showMessage(bean);
		nirvanaMasterGrid.reloadData(bean);
		// Clear checkbox.
		nirvanaMasterGrid.getGrid().hideHeaderRowColumns();
		nirvanaMasterGrid.getGrid().setSelectedRows([]);
	};
	$("#btnSave").click(function() {
		message.clear();
		errors.length = 0;
		var selectedData = [];
		var selectedRowsIndex = nirvanaMasterGrid.getGrid().getSelectedRows();
		if (selectedRowsIndex.length > 0) {
			if (selectedRowsIndex.length == nirvanaMasterGrid.getGrid().getDataLength()) {
				// filter status data is error only to save.
				selectedData = $.map(nirvanaMasterGrid.getDataView().getItems(), function(object, index) {
					if ("Error" === object.syncStatus) {
						return object;
					}
				});
			} else {
				$.each(selectedRowsIndex, function(key, value) {
					selectedData.push(nirvanaMasterGrid.getGrid().getDataItem(value));
				});
			}
		} else {
			errors.push({
				"code" : "err.cmm.013",
				"arguments" : []
			});
			message.setErrors(errors);
			return;
		}

		if (message.isNoError() && selectedData != "") {
			if (confirm(message.getMessage("cfm.cmm.001"))) {
				postJSONObject("NIR_S01_save", selectedData, success);
			}
		}
	});
}

/**
 * Create result search from Nirvana master sync.
 */
function createNirvanaMasterGrid() {
	var checkboxSelector = new NIR_S01.CheckboxSelectColumn({
		cssClass : "slick-cell-checkboxsel"
	});
	var nirvanaMasterColumns = [ checkboxSelector.getColumnDefinition(), {
		id : "rowNo",
		name : "No.",
		field : "rowNo",
		width : 40,
		resizable : true,
		formatter : common.SlickGrid.Formatters.RowNumFormatter,
		sortable : true
	}, {
		id : "dataNo",
		name : "Data No.",
		field : "dataNo",
		width : 90,
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable : true
	}, {
		id : "syncStatus",
		name : "Status",
		field : "syncStatus",
		width : 90,
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable : true
	}, {
		id : "transType",
		name : "Action",
		field : "transType",
		width : 90,
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable : true
	}, {
		id : "syncDate",
		name : "Transaction Date",
		field : "syncDate",
		width : 120,
		cssClass : "cell-c",
		resizable : true,
		formatter : common.SlickGrid.Formatters.DateTimeFormatter,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable : true
	}, {
		id : "errDesc",
		name : "Error Description",
		field : "errDesc",
		width : 150,
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable : true
	}, {
		id : "customerCode",
		name : "Customer Code",
		field : "customerCode",
		width : 130,
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable : true
	}, {
		id : "customerName",
		name : "Customer Name",
		field : "customerName",
		width : 150,
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable : true
	}, {
		id : "fgNo",
		name : "FG No",
		field : "fgNo",
		width : 120,
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable : true
	}, {
		id : "fgName",
		name : "FG Name",
		field : "fgName",
		width : 150,
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable : true
	}, {
		id : "uomId",
		name : "UOM",
		field : "uomId",
		width : 120,
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable : true
	}, {
		id : "weight",
		name : "Weight",
		field : "weight",
		width : 90,
		cssClass : "cell-r",
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable : true,
		formatter : common.SlickGrid.Formatters.DoubleFormatter
	}, {
		id : "price",
		name : "Price",
		field : "price",
		width : 80,
		cssClass : "cell-r",
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable : true,
		formatter : common.SlickGrid.Formatters.DoubleFormatter
	}, {
		id : "currency",
		name : "Currency",
		field : "currency",
		width : 120,
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable : true
	}, {
		id : "createDate",
		name : "Create Date",
		field : "createDate",
		width : 120,
		cssClass : "cell-c",
		resizable : true,
		formatter : common.SlickGrid.Formatters.DateTimeFormatter,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable : true
	}, {
		id : "lastUpdate",
		name : "Update Date",
		field : "lastUpdate",
		width : 120,
		cssClass : "cell-c",
		resizable : true,
		formatter : common.SlickGrid.Formatters.DateTimeFormatter,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable : true
	}, {
		id : "updateBy",
		name : "Update By",
		field : "updateBy",
		width : 120,
		cssClass : "cell-l",
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable : true
	} ];

	nirvanaMasterGrid = common.SlickGrid.newGrid();
	nirvanaMasterGrid = nirvanaMasterGrid.setOptions(common.SlickGrid.options.View);
	nirvanaMasterGrid = nirvanaMasterGrid.setGrid({
		"id" : "nirvanaMasterGrid",
		"columns" : nirvanaMasterColumns,
		"data" : []
	// ,"key" : "rowNo"
	});
	nirvanaMasterGrid.setSelectionModel(new Slick.RowSelectionModel({
		selectActiveRow : false
	}));
	nirvanaMasterGrid.registerPlugin(checkboxSelector);

	nirvanaMasterGrid.getGrid().onBeforeEditCell.subscribe(function(e, args) {
		if (args.row == 1) {
			return false;
		}
	});
}