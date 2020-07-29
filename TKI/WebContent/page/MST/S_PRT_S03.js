var fgPartGrid;
var columns;
var customerGrid;
var customerColumns;
var fgGrid;
var fgColumns;
var partGrid;
var partColumns;
var processGrid;
var processColumns;
var fgPartData = [];
var errors = [];
var processData = [];
var result = [];
var processarray = [];
var sequencearray = [];
var list_of_processvalues = [];
var list_of_sequencesvalues = [];
var unique_processvalues = {};
var unique_sequencesvalues = {};
var partRoutingPartId;
var btnSearchCopy;
var copyPartList= {};

$(function($) {
	// data
	fgPartRoutingGrid = $("#fgPartGrid");
	partRoutingMasterForm = $("#partRoutingMasterForm");
	exportForm = $("#exportForm");
	gridheader = $("#gridheader");
	searchfgNo = $("#txtfgNo");
	searchfgName = $("#txtfgName");
	searchpartNo = $("#txtpartNo");
	searchpartName = $("#txtpartName");
	customerNameSel = $("#customerNameSel");
	btnSearchCopy = $('#btnSearchCopy');
	getProcessList();
	getCopyPartList();
	// view
	createpartRoutingGrid();
	createPartDestinationGrid();
	fgPartRoutingGrid.hide();
	gridheader.hide();
	// event
	searchPartRouting();
	exportPartRouting();
	btnSearchCopy.click(searchCopyCustomer);
});
function getCopyPartList(){
	copyPartList = { url:"S_PRT_S03_selectCopyForPartRoutingList.html",
			param: {}
	};
}
function searchCopyCustomer(){
	clearMessage();
	if($('#cboCopyCustomer').val() == -2147483648){
		alert(message.getMessage("err.cmm.001",["Customer"]));
		return false;
	}else{
		copyPartList = { url:"S_PRT_S03_selectCopyForPartRoutingList.html",
				param: { "customerId" : $('#cboCopyCustomer').val()}
		};
		createPartDestinationGrid();
	}
}
function createpartRoutingGrid() {
	columns = [{
				id : "statusFlag",
				name : "",
				field : "statusFlag",
				cssClass : "cell-c",
				width : 30,
				resizable : true
			}, {
				id : "customerCode",
				name : "Customer",
				field : "customerCode",
				width : 100,
				cssClass : "cell-l",
				resizable : true,
				sortable : true,
				filter : common.SlickGrid.filterColumnsOptions.Fulltext
			}, {
				id : "fgNo",
				name : "FG No.",
				field : "fgNo",
				width : 150,
				cssClass : "cell-l",
				resizable : true,
				sortable : true,
				filter : common.SlickGrid.filterColumnsOptions.Fulltext
			}, {
				id : "fgName",
				name : "FG Name",
				field : "fgName",
				width : 180,
				cssClass : "cell-l",
				resizable : true,
				sortable : true,
				filter : common.SlickGrid.filterColumnsOptions.Fulltext
			},{
				id : "partNo",
				name : "Part No.",
				field : "partNo",
				width : 180,
				cssClass : "cell-l",
				resizable : true,
				sortable : true,
				filter : common.SlickGrid.filterColumnsOptions.Fulltext
			}, {
				id : "partName",
				name : "Part Name",
				field : "partName",
				width : 180,
				cssClass : "cell-l",
				resizable : true,
				sortable : true,
				filter : common.SlickGrid.filterColumnsOptions.Fulltext
			}, {
				id : "processList",
				name : "Process",
				field : "processList",
				width : 180,
				headerCssClass : "editable-column required-column",
				cssClass : "cell-l",
				resizable : true,
				sortable : true,
				formatter : processFormatter,
				editor : common.SlickGrid.Editor.DialogEditor,
				toolTip : "Test tooltip",
				editorArgs : {
					dialogContent : $("#processDialog"),
					setFields : [ "process" ],
					width : 650,
					height : 500,
					buttons : {
						Save : function() {
							saveProcessGrid();
						},
						Cancel : function() {
							$(this).dialog("close");
							clearMessage();
						}
					}
				},
				filter : common.SlickGrid.filterColumnsOptions.Fulltext
			}, {
				id : "remark",
				name : "Remark",
				field : "remarkList",
				width : 100,
				cssClass : "cell-l",
				resizable : true,
				sortable : true,
				formatter : remarkListFormatter,
				filter : common.SlickGrid.filterColumnsOptions.Fulltext
			}, {
				id : "wipCalc",
				name : "WIP Calc.",
				field : "wipCalcList",
				width : 100,
				cssClass : "cell-l",
				resizable : true,
				sortable : true,
				formatter : wipCalcListFormatter,
				filter : common.SlickGrid.filterColumnsOptions.Fulltext
			}, {
				id             : "createDate",
				name           : "Create Date",
				field          : "createDate",
				width          : 120,
				cssClass       : "cell-c",
				resizable      : true,
				formatter      : common.SlickGrid.Formatters.DateTimeFormatter,
				filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
				sortable	   : true
			},{
				id             : "createBy",
				name           : "Create By",
				field          : "createBy",
				width          : 120,
				resizable      : true,
				filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
				sortable	   : true
			},{
				id             : "lastUpdate",
				name           : "Update Date",
				field          : "lastUpdate",
				width          : 120,
				cssClass       : "cell-c",
				resizable      : true,
				formatter      : common.SlickGrid.Formatters.DateTimeFormatter,
				filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
				sortable	   : true
			},{
				id             : "updateBy",
				name           : "Update By",
				field          : "updateBy",
				width          : 120,
				resizable      : true,
				filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
				sortable	   : true
			},{
				id : "copyField",
				name : "Copy",
				field : "copyField",
				width : 50,
				cssClass : "cell-c",
				resizable : true,
				formatter : copyFormatter,
				editor : DialogEditorFn,
				editorArgs : {
					dialogContent : $("#partDialog"),
					width : 600,
					height : 530,
					close: function() { clearMessage(); $(this).dialog("close"); },
					buttons : {
						Select : function() {
							selectPartDestination();
							},
						Cancel : function() {
								clearMessage();
								$(this).dialog("close");
						}
					}
				}
			} ];

	// Extend default options from common grid
	var editOptions = $.extend({}, common.SlickGrid.newGrid().getOptions(), {
		enableAddRow : false,
		rowHeight : 120
	});

	fgPartGrid = common.SlickGrid.newGrid();
	fgPartGrid = fgPartGrid.setOptions(editOptions);
	fgPartGrid = fgPartGrid.setGrid({
		"id" : "fgPartGrid",
		"columns" : columns,
		"data" : fgPartData
	});

	fgPartGrid.getGrid().onBeforeEditCell.subscribe(function(e, args) {
		var cell = args.cell;
		if (cell == args.grid.getColumnIndex("processList")) {
			clearMessage();
			var partId = args.item.partId;
			createProcessGrid(partId);
		} else if (cell == args.grid.getColumnIndex("copyField")) {
			var customerId = customerNameSel.val();
			var partId = args.item.partId;
			partRoutingPartId = args.item.partId;
			var fgNo = $("#txtfgNo").val();
			var fgName = $("#txtfgName").val();
			var partNo = $("#txtpartNo").val();
			var partName = $("#txtpartName").val();
			createPartDestinationGrid(customerId, fgNo, fgName, partId, partNo,
					partName);
		}
	});
}

function createProcessGrid(partId) {
	processColumns = [ {
		id : "statusFlag",
		name : "",
		field : "statusFlag",
		width : 40,
		resizable : true,
		formatter : common.SlickGrid.Formatters.FlagFormatter
	}, {
		id : "rowNo",
		name : "No.",
		field : "rowNo",
		width : 40,
		resizable : true,
		formatter : common.SlickGrid.Formatters.RowNumFormatter,
		sortable : true
	}, {
		id : "wipOrder",
		name : "Sequence",
		field : "wipOrder",
		cssClass : "cell-c",
		headerCssClass : "editable-column required-column",
		width : 100,
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		editor : common.SlickGrid.Editor.NumericEditor
	}, {
		id : "process",
		name : "Process",
		field : "process",
		width : 250,
		headerCssClass : "editable-column required-column",
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		editor : common.SlickGrid.Editor.DropDownEditor,
		editorArgs : {
			dataList : getProcessList(),
			value : "wip",
			label : "process"
		}
	}, {
		id 			: "remark",
		name 		: "Remark",
		field 		: "remark",
		width 		: 150,
		headerCssClass : "editable-column",
		resizable 	: true,
		editor 		: common.SlickGrid.Editor.TextEditor,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	: true,
		maxlength 	: 100
	}, {
		id : "isCalc",
		name : "WIP Calc.",
		field : "isCalc",
		cssClass : "cell-c",
		headerCssClass : "editable-column required-column",
		width : 100,
		resizable : true,
		formatter : common.SlickGrid.Formatters.YesNo,
		filter : common.SlickGrid.filterColumnsOptions.Checkbox,
		editor : common.SlickGrid.Editor.Checkbox
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
	var processoptions = $.extend({}, common.SlickGrid.newGrid().getOptions(),
			{
				enableAddRow : true
			});
	processGrid = processGrid.setOptions(processoptions);
	processGrid = processGrid.setGrid({
		"id" : "processGrid",
		"columns" : processColumns,
		"data" : {
			url : "S_PRT_S03_serarchProcessList.html",
			param : {
				partId : partId
			}
		}
	});
}

function createPartDestinationGrid() {
	partColumns = [ {
		id 			: "statusFlag",
		name 		: "",
		field 		: "statusFlag",
		width 		: 30,
		resizable 	: true,
		formatter	: common.SlickGrid.Formatters.FlagFormatter,
		filter		: common.SlickGrid.filterColumnsOptions.ClearFilterButton,
		sortable	: true
	},{
		id : "customerCode",
		name : "Customer",
		field : "customerCode",
		width : 100,
		cssClass : "cell-l",
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id : "fgNo",
		name : "FG No.",
		field : "fgNo",
		width : 120,
		cssClass : "cell-l",
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id : "fgName",
		name : "FG Name",
		field : "fgName",
		width : 170,
		cssClass : "cell-l",
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id : "partNo",
		name : "Part No.",
		field : "partNo",
		width : 120,
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id : "partName",
		name : "Part Name",
		field : "partName",
		width : 170,
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	} ];
	partGrid = common.SlickGrid.newGrid();
	partGrid = partGrid.setGrid({
		"id" : "partGrid",
		"columns" : partColumns,
		"data" : copyPartList
	});
}

function copyFormatter(row, cell, value, columnDef, dataContext) {
	return "<img src='image/icon/dhtmlgoodies_sheet.gif'/>";
}

function searchPartRouting() {
	$("[id=btnSearch]").click(function() {
		clearMessage();
		
//		customerNameSel = $("#customerNameSel");
//		if (customerNameSel.val() == -2147483648) {
//			errors.push({
//				"code" : "err.cmm.001",
//				"arguments" : [ "Customer" ]
//			});
//			message.setErrors(errors);
//			return;
//		} else {
			gridheader.show();
			fgPartRoutingGrid.show();
			getpartRoutingGridData();
			createpartRoutingGrid();
//		}
	});
}

function getpartRoutingGridData() {
	customerNameSel = $("#customerNameSel");
	fgNo = $("#txtfgNo");
	fgName = $("#txtfgName");
	partNo = $("#txtpartNo");
	partName = $("#txtpartName");
	fgPartData = {
		url : "S_PRT_S03_searchList.html",
		param : {
			customerId : customerNameSel.val(),
			fgNo : fgNo.val(),
			fgName : fgName.val(),
			partNo : partNo.val(),
			partName : partName.val()
		}
	};
}

function exportPartRouting() {
	$("[id=btnExport]").click(function() {
		clearMessage();
//		customerNameSel = $("#customerNameSel");
//		if (customerNameSel.val() == -2147483648) {
//			errors.push({
//				"code" : "err.cmm.001",
//				"arguments" : [ "Customer" ]
//			});
//			message.setErrors(errors);
//			return;
//		} else {
			exportForm.empty();
			var hd = $(fgPartGrid.getGrid().getHeaderRow());
			hd.find(".slick-headerrow-column").each(
				function(idx, obj) {
					var o = fgPartGrid.getGrid().getColumns()[idx];
					var ip = $(this).find("input,select");
					if (ip.exists() && ip.val()) {
						exportForm.append($('<input type="hidden" name="' + o.field + '" value="' + ip.val() + '"/>'));
					}
			});
			exportForm.append($('<input type="hidden" name="customerId"  value="' + customerNameSel.val() + '"/>'));
			exportForm.append($('<input type="hidden" name="searchfgNo"  value="' + searchfgNo.val() + '"/>'));
			exportForm.append($('<input type="hidden" name="searchfgName"  value="' + searchfgName.val() + '"/>'));
			exportForm.append($('<input type="hidden" name="searchpartNo"   value="' + searchpartNo.val() + '"/>'));
			exportForm.append($('<input type="hidden" name="searchpartName"  value="' + searchpartName.val() + '"/>'));
			downloadNotify($("<div title='" + message.getMessage("downloadAlertContent") + "'/>'>"
					+ message.getMessage("downloadAlertContent") + "</div>"));
			exportForm.submit();
//		}
	});
}

function processFormatter(row, cell, value, columnDef, dataContext) {
	var List = [];
	var resultData = "";
	var fullText = "";
	var count = 0;
	List.length = 0;
	var ListData = dataContext.processList;
	for (var i = 0; i < ListData.length; i++) {
		List.push(ListData[i]);
	}
	$.each(List, function(key, value) {
		if (count < 5) {
			resultData += value;
			resultData += ("<br>");
		} else if (count == 5) {
			var txt = "....";
			resultData += txt;
		}
		
		fullText += value;
		fullText += ("&#013;");
		count++;
	});
	
	resultData = "<span title='" + fullText + "'>" + resultData + "</span>";
	
	return resultData;
}
function remarkListFormatter(row, cell, value, columnDef, dataContext) {
	var List = [];
	var resultData = "";
	var fullText = "";
	var count = 0;
	List.length = 0;
	var ListData = dataContext.remarkList;
	
	for (var i = 0; i < ListData.length; i++) {
		List.push(ListData[i]);
	}
	
	$.each(List, function(key, value) {
		if (count < 5) {
			resultData += value;
			resultData += ("<br>");
		} else if (count == 5) {
			var txt = "....";
			resultData += txt;
			resultData += ("<br>");
		}
		
		fullText += value;
		fullText += ("&#013;");
		count++;
	});
	
	resultData = "<span title='" + fullText + "'>" + resultData + "</span>";
	
	return resultData;
}
function wipCalcListFormatter(row, cell, value, columnDef, dataContext) {
	var List = [];
	var resultData = "";
	var fullText = "";
	var count = 0;
	List.length = 0;
	var ListData = dataContext.wipCalcList;
	
	for (var i = 0; i < ListData.length; i++) {
		List.push(ListData[i]);
	}
	
	$.each(List, function(key, value) {
		if (count < 5) {
			resultData += value;
			resultData += ("<br>");
		} else if (count == 5) {
			var txt = "....";
			resultData += txt;
			resultData += ("<br>");
		}
		
		fullText += value;
		fullText += ("&#013;");
		count++;
	});
	
	resultData = "<span title='" + fullText + "'>" + resultData + "</span>";
	
	return resultData;
}

function getProcessList() {
	var ProcessList = [];
	params = {};
	postJSONSync("S_PRT_S03_seletProcess", params, function(data) {
		ProcessList = data;
	});
	return ProcessList;
}

function saveProcessGrid() {
	processGrid.getGrid().getEditorLock().commitCurrentEdit();
	clearMessage();
	
	var processlinenum = 0;
	var sequenceslinenum = 0;
	processarray.length = 0;
	var linenum = 0;
	sequencearray.length = 0;
	list_of_processvalues.length = 0;
	list_of_sequencesvalues.length = 0;
	unique_processvalues = {};
	unique_sequencesvalues = {};
	var listData = "";
	listData = $.map(processGrid.getData().getItems(), function(n, i) {
		linenum++;
		if (n.statusFlag != 'd') {
			processarray.push(n.process);
			sequencearray.push(n.wipOrder);
		}
		if (n.statusFlag != null && n.statusFlag != 'd') {
			if (n.process == null || !(n.process)) {
				errors.push({
					"code" : "err.cmm.002",
					"arguments" : [ linenum, "Process" ]
				});
			} else if (n.wipOrder == null || !(n.wipOrder)) {
				errors.push({
					"code" : "err.cmm.002",
					"arguments" : [ linenum, "Sequence" ]
				});
			} else if (isNaN(n.wipOrder)) {
				errors.push({
					"code" : "err.cmm.019",
					"arguments" : [ linenum, "Sequence" ]
				});
			} else {
				if (!(n.isCalc)) {
					n.isCalc = false;
				}
				return n;
			}
		} else if (n.statusFlag == 'd' || n.statusFlag == null) {
			return n;
		}
	});
	/* to check duplicate Process */
	$.each(processarray, function(key, value) {
		processlinenum++;
		if (!unique_processvalues[value]) {
			unique_processvalues[value] = true;
			list_of_processvalues.push(value);
		} else {
			errors.push({
				"code" : "err.cmm.020",
				"arguments" : [ processlinenum, "Process" ]
			});
		}
	});
	/* to check duplicate Sequences */
	$.each(sequencearray, function(key, value) {
		sequenceslinenum++;
		if (!unique_sequencesvalues[value]) {
			unique_sequencesvalues[value] = true;
			list_of_sequencesvalues.push(value);
		} else {
			errors.push({
				"code" : "err.cmm.020",
				"arguments" : [ sequenceslinenum, "Sequences" ]
			});
		}
	});
	if (errors.length > 0) {
		message.setErrors(errors);
	}
	if (errors.length == 0 && listData != "") {
		if (confirm(message.getMessage("cfm.cmm.001"))) {
			var uri = "S_PRT_S03_saveList";
			var data = listData;
			var success = function(bean) {
				message.showMessage(bean);
				processGrid.reloadData();
				fgPartGrid.reloadData();
			};
			postJSONObject(uri, data, success);
		}
	}
}

function selectPartDestination() {
	clearMessage();
	fgPartGrid.getGrid().getEditorLock().commitCurrentEdit();
	fgPartGrid.resetFilter();

	var selectedRowIndex = partGrid.getGrid().getSelectedRows();
	if (selectedRowIndex.length > 0) {
		if (confirm(message.getMessage("cfm.prt.001"))) {
			var selectedRow = partGrid.getDataView().getItem(selectedRowIndex);
			var destinationPartId = selectedRow["partId"];
			var partId = partRoutingPartId;
			params = {
				partId : partId,
				destinationPartId : destinationPartId
			};
			var success = function(bean) {
				message.showMessage(bean);
				fgPartGrid.reloadData();
			};
			var uri = "S_PRT_S03_savePartDestination";
			var params = {
				partId : partId,
				destinationPartId : destinationPartId
			};
			postJSON(uri, params, success);
		}
	} else {
		alert("Please select at least one row.");
	}
}

function clearMessage() {
	message.clear();
	errors.length = 0;
}

/*
 * Dialog Pop-up
 */
function DialogEditorFn(args) {
	var $input;
	var defaultValue = {};
	var scope = this;
	var grid;
	var isCancel = false;

	this.init = function() {
		$input = $("<img src='image/icon/dhtmlgoodies_sheet.gif'/>").appendTo(args.container)
				.focus().select();

		var editorArgs = args.column.editorArgs;
		var $dialogContent = editorArgs.dialogContent;
		grid = eval($dialogContent.find("div.grid-detail").attr("id"));

		$.ui.dialog.overlay.events = '';
		
		if(editorArgs.buttons == undefined){
			editorArgs.buttons = {
				Select : function() {
					var selectedRowIndex = grid.getGrid().getSelectedRows();

					if (selectedRowIndex.length > 0) {
						var selectedRow = grid.getDataView().getItem(selectedRowIndex);
						$input.val(selectedRow[args.column.field]).focus();
						args.grid.getEditorLock().activate(args.grid.getEditController());
						args.grid.getEditController().commitCurrentEdit();
						$(this).dialog("close");

					} else {
						alert("Please select at least one row.");
					}
				},
				Cancel : function() {
					isCancel = true;
					args.grid.getEditorLock().activate(args.grid.getEditController());
					$(this).dialog("close");
				}
			};
		}
		
		$dialogContent.dialog( {
			bgiframe : true,
			modal : true,
			autoOpen : false,
			height : editorArgs.height,
			width :  editorArgs.width,
			resizable : false,
			buttons : editorArgs.buttons
		});

//		grid.getGrid().onDblClick.subscribe( function() {
//			var selectedRowIndex = grid.getGrid().getSelectedRows();
//			var selectedRow = grid.getDataView().getItem(selectedRowIndex);
//			$input.val(selectedRow[args.column.field]).focus();
//
//			isCancel = false;
//			args.grid.getEditorLock().activate(args.grid.getEditController());
//			$dialogContent.dialog("close");
//		});

		args.grid.getEditorLock().deactivate(args.grid.getEditController());
		$dialogContent.dialog("open");
		grid.getGrid().hideHeaderRowColumns();
		grid.resetFilter();

		//---------------------------------------------------------------- nav.
		$input.bind("keydown.nav", function(e) {
			if (e.keyCode == $.ui.keyCode.ENTER) {
				args.grid.navigateNext();
				e.stopImmediatePropagation();
			} else if (e.keyCode === $.ui.keyCode.LEFT && $(this).getCursorPosition() == 0) {
				args.grid.navigatePrev();
				e.stopImmediatePropagation();
			} else if (e.keyCode === $.ui.keyCode.RIGHT && $(this).getCursorPosition() == $input.val().length) {
				args.grid.navigateNext();
				e.stopImmediatePropagation();
			} else if (e.keyCode === $.ui.keyCode.LEFT || e.keyCode === $.ui.keyCode.RIGHT) {
				e.stopImmediatePropagation();
			}
		});
		//---------------------------------------------------------------- nav.	
	};

	this.destroy = function() {
		clearMessage();
		$input.remove();
		args.grid.getEditorLock().activate(args.grid.getEditController());
		args.column.editorArgs.dialogContent.dialog("close");
	};

	this.focus = function() {
		$input.focus();
	};

	this.getValue = function() {
		return $input.val();
	};

	this.setValue = function(val) {
		$input.val(val);
	};

	this.loadValue = function(item) {
		$.extend(defaultValue, item);
		$input.val(defaultValue[args.column.field]);
		$input[0].defaultValue = defaultValue[args.column.field];
		$input.select();
	};

	this.serializeValue = function() {
		var selectedRowIndex = grid.getGrid().getSelectedRows();

		if (selectedRowIndex.length > 0) {
			var selectedRow = grid.getDataView().getItem(selectedRowIndex);
			var serializeValue = {};

			$.each(args.column.editorArgs.setFields, function(index, field) {
				serializeValue[field] = selectedRow[field];
			});
		}

		return serializeValue;
	};

	this.applyValue = function(item, state) {
		$.each(state, function(key, value) {
			item[key] = state[key];
		});
	};

	this.isValueChanged = function() {
		var isChanged = false;
		var selectedRowIndex = grid.getGrid().getSelectedRows();

		if (!isCancel && selectedRowIndex.length > 0) {
			var selectedRow = grid.getDataView().getItem(selectedRowIndex);

			$.each(selectedRow, function(key, value) {
				if (grid.getKey() != key.toString()) {
					if (defaultValue[key] != value) {
						isChanged = true;
						return false;
					}
				}
			});
		}

		return (!($input.val() == "" && defaultValue == null)) && isChanged;
	};

	this.validate = function() {
		if (args.column.validator) {
			var validationResults = args.column.validator($input.val(), args);
			if (!validationResults.valid) {
				return validationResults;
			}
		}

		return {
			valid : true,
			msg : null
		};
	};

	this.init();
};