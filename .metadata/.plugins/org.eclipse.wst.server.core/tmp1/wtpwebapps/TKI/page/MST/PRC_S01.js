var processGrid;
var processColumns;
var linenum;
var exportForm;
var processData = [];
var wiparray = [];
var errors = [];
var list_of_values = [];
var showMessage = [];
var datalist = [];
var wipTypeList = [];
var unique_values = {};

$(function($) {
	prcSo1Form = $("#prcSo1Form");
	exportForm = $("#exportForm");
	// data
	// getWipTypes();
	// view
	createGrid();
	// event
	SaveProcessMaster();
	exportProcessMaster();
});

function SaveProcessMaster() {
	$("[id=btnSave]").click(
			function() {
				processGrid.getGrid().getEditorLock().commitCurrentEdit();
				processGrid.resetFilter();

				message.clear();
				showMessage.length = 0;
				unique_values = {};
				wiparray.length = 0;
				errors.length = 0;
				list_of_values.length = 0;
				linenum = 0;
				var wipList = $.map(processGrid.getData().getItems(), function(n, i) {
					linenum++;
					if ((n.wip == "" || !(n.wip)) && n.statusFlag != 'd') {
						errors.push({
							"code" : "err.cmm.002",
							"arguments" : [ linenum, "ProcessCode" ]
						});
					} else if ((n.wipName == "" || !(n.wipName))
							&& n.statusFlag != 'd') {
						errors.push({
							"code" : "err.cmm.002",
							"arguments" : [ linenum, "ProcessName" ]
						});
					} else if ((n.wipTypeName == "" || !(n.wipTypeName))
							&& n.statusFlag != 'd') {
						errors.push({
							"code" : "err.cmm.002",
							"arguments" : [ linenum, "WIP Type" ]
						});
					} else {
						if (n.statusFlag != null) {
							if (n.statusFlag == 'i' && n.isCalc == undefined) {
								n.isCalc = false;
							}
							return n;
						}
					}
				});
				if (errors.length > 0) {
					message.setErrors(errors);
					return;
				}
				if (errors.length == 0 && wipList != "") {
					if (confirm(message.getMessage("cfm.cmm.001"))) {
						showLoading();
						var uri = "PRC_S01_saveList";
						var data = wipList;
						var success = function(bean) {
							processGrid.reloadData();
							message.showMessage(bean);
						};
						postJSONObject(uri, data, success);
					}
				}
			});
}

function exportProcessMaster() {
	$("[id=btnExport]").click(
		function() {
			message.clear();
			datalist.length = 0;
			var noData = processGrid.getFilteredRows().length === 0;
			if (noData) {
				message.setErrors([ {
					"code" : "err.cmm.018",
					"arguments" : []
				} ]);
			} else {
				exportForm.empty();
				var hd = $(processGrid.getGrid().getHeaderRow());
				hd.find(".slick-headerrow-column").each(
					function(idx, obj) {
						var o = processGrid.getGrid()
								.getColumns()[idx];
						var ip = $(this).find(
								"input,select");
						if (ip.exists() && ip.val()) {
							exportForm.append($('<input type="hidden" name="' + o.field + '" value="' + ip.val() + '"/>'));
						}
					});
				downloadNotify($("<div title='" + message.getMessage("downloadAlertContent") + "'/>'>" 
						+ message.getMessage("downloadAlertContent") + "</div>"));
				exportForm.submit();
			}
	});
}

function createGrid() {
	var editOptions = $.extend({}, common.SlickGrid.newGrid().getOptions(), {
		showHeaderRow : true,
		headerRowHeight : 30,
		enableAddRow : true
	});// Extend default options from common grid

	// <!-- ProcessMaster Grid -->
	processColumns = [ {
		id : "statusFlag",
		name : "",
		field : "statusFlag",
		width : 30,
		maxlength : 30,
		cssClass : "cell-c",
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
		id : "wip",
		name : "Process Code",
		field : "wip",
		headerCssClass : "editable-column required-column",
		width : 120,
		maxlength : 20,
		cssClass : "cell-l",
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		editor : common.SlickGrid.Editor.TextEditor,
		validator : common.Validator.UniqueValidator
	}, {
		id : "wipName",
		name : "Process Name",
		field : "wipName",
		headerCssClass : "editable-column required-column",
		width : 120,
		maxlength : 20,
		cssClass : "cell-l",
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		editor : common.SlickGrid.Editor.TextEditor

	}, {
		id : "isCalc",
		name : "WIP Calc.",
		field : "isCalc",
		cssClass : "cell-l",
		headerCssClass : "editable-column required-column",
		width : 100,
		resizable : true,
		sortable : true,
		formatter : common.SlickGrid.Formatters.YesNo,
		filter : common.SlickGrid.filterColumnsOptions.Checkbox,
		editor : common.SlickGrid.Editor.Checkbox

	}, {
		id : "wipTypeName",
		name : "WIP Type",
		field : "wipTypeName",
		headerCssClass : "editable-column required-column",
		width : 120,
		cssClass : "cell-l",
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		editor : common.SlickGrid.Editor.DropDownEditor,
		editorArgs : {
			dataList : getWipTypes(),
			value : "wipType",
			label : "wipTypeName"
		}
	}, {
		id : "remark",
		name : "Remark",
		field : "remark",
		headerCssClass : "editable-column",
		width : 120,
		cssClass : "cell-l",
		maxlength : 255,
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		editor : common.SlickGrid.Editor.TextEditor
	},{
		id             : "isenable",
		name           : "Enable",
		field          : "isenable",
		width          : 80,
		headerCssClass : "editable-column required-column",
		cssClass       : "cell-c",
		resizable      : true,
		formatter      : common.SlickGrid.Formatters.YesNo,
		filter         : common.SlickGrid.filterColumnsOptions.Checkbox,
		editor         : common.SlickGrid.Editor.Checkbox,
		sortable	   : true
	}, {
		id : "createDate",
		name : "Create Date",
		field : "createDate",
		width : 120,
		cssClass : "cell-c",
		resizable : true,
		sortable : true,
		formatter : common.SlickGrid.Formatters.DateTimeFormatter,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext

	}, {
		id : "createBy",
		name : "Create By",
		field : "createBy",
		width : 120,
		maxlength : 30,
		cssClass : "cell-l",
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id : "lastUpdate",
		name : "Last Update",
		field : "lastUpdate",
		width : 120,
		cssClass : "cell-c",
		resizable : true,
		sortable : true,
		formatter : common.SlickGrid.Formatters.DateTimeFormatter,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext

	}, {
		id : "updateBy",
		name : "Update By",
		field : "updateBy",
		width : 120,
		cssClass : "cell-l",
		maxlength : 30,
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

	processGrid = common.SlickGrid.newGrid();
	processGrid = processGrid.setOptions(editOptions);
	processGrid = processGrid.setGrid({
		"id" : "processGrid",
		"columns" : processColumns,
		"data" : {
			url : "PRC_S01_searchList.html"
		}
	});
	createCellChange();
}

function createCellChange(){
	processGrid.getGrid().onAddNewRow.subscribe( function(e, args) {
		if(args.item != undefined || args.item != null){ 
			if (args.item.statusFlag != null){
				if(args.item.isenable == undefined){
					args.item.isenable = true;
					processGrid.getDataView().updateItem(args.item.rowNum, args.item);
				}
			}
		}
	});
}

function getWipTypes() {
	var result = [];
	postJSONSync("PRC_S01_wipTypeNameList", {}, function(data) {
		result = data;
	});
	return result;
}
