var machineList = [];
var errors = [];
var selectWip;
var btnSearch;
var mchS01Form;
var mappingPartGrid;
var machineGrid;
var sourcemachineId;
var row;
var wipcode;
var selectedWip;
var selectedCustomerId = "";

$(function($) {
	// data
	mchS01Form = $("#form");
	selectWip = $("select#selectWip");
	btnSearch = $("#btnSearch");
	mappingPartGrid = $("#mappingPartGrid");
	machineGrid = $("#machineGrid");
	btnMachineSearch = $("#btnMachineSearch");
	wip = $("#wip");

	createMappingPartGrid();
	createCopyMappingPartGrid();

	// event
	searchMaingrid();
});

/**
 * Search machine data by criteria.
 */
function  searchMaingrid(){
	btnSearch.click(function(){
		message.clear();
		mchS01Form.find("select[id=pageNumber]").attr("disabled", true);
		mchS01Form.attr("action","MCH_S01_search.html");
		mchS01Form.submit();
	});	
}

/**
 * Delete machine by row.
 * 
 * @param row : row index
 */
function fnDelete(row) {
	if (row != undefined && row != null) {
		var rowNo = row.find("td:first-child").html().trim();
		if (!confirm(message.getMessage("cfm.cmm.003", rowNo))) {
			return;
		}
		mchS01Form.attr("action", "MCH_S01_delete.html");
		mchS01Form
				.append("<input type='hidden' name='machineList[0].machineId' value='"
						+ row.attr("id") + "'/>");
		mchS01Form.submit();
	}
}

/**
 * Create mapping part grid for display dialog when user click on icon in Machine Part column.
 */
function createMappingPartGrid() {
	var options = $.extend({}, common.SlickGrid.newGrid().getOptions(), {
		showHeaderRow 	: true,
		headerRowHeight : 30,
		enableAddRow 	: true
	});// Extend default options from common grid

	machineColumns = [ {
		id             : "statusFlag",
		name           : "",
		field          : "statusFlag",
		width          : 40,
		resizable      : true,
		sortable	   : true,
		formatter      : common.SlickGrid.Formatters.FlagFormatter,
		filter 		   : common.SlickGrid.filterColumnsOptions.ClearFilterButton
	},{
		id 				: "rowNo",
		name 			: "No.",
		field			: "rowNo",
		width 			: 40,
		resizable 		: true,
		formatter 		: common.SlickGrid.Formatters.RowNumFormatter,
		sortable		: true
	}, {
	    id             : "customerCode",
	    name           : "Customer",
	    field          : "customerCode",
	    headerCssClass : "editable-column required-column",
		width          : 120,
		resizable      : true,
		sortable	   : true,
		filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
		editor         : common.SlickGrid.Editor.DropDownEditor,
		editorArgs     : {
		                   dataList : getCustomerList(),
		                    value   : "customerId",
		                    label   : "customerCode"
		                 }
	}, {
		id 			   : "part",
		name 		   : "Part No : Part Name",
		field		   : "part",
		headerCssClass : "editable-column required-column",
		width 		   : 320,
		resizable 	   : true,
		sortable	   : true,
		filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
		editor         : common.SlickGrid.Editor.DropDownEditor,
		editorArgs     : {
		                   dataList : getPartNoList(selectedCustomerId),
		                    value   : "partId",
		                    label   : "part"
		                 }
	}, {
		id             : "deleteField",
		name           : "Delete",
		field          : "deleteField",
		width          : 50,
		cssClass       : "cell-c",
		resizable      : true,
		formatter      : common.SlickGrid.Formatters.DelFormatter,
		editor         : common.SlickGrid.Editor.DelEditor
	} ];
	
	mappingPartGrid = common.SlickGrid.newGrid();
	mappingPartGrid = mappingPartGrid.setOptions(options);
	mappingPartGrid = mappingPartGrid.setGrid({
		"id"      : "mappingPartGrid",
		"columns" : machineColumns,
		"data" 	  : []
	});

	/* Get part list to drop down list when clicked on Part No : Part Name column on grid. */
	mappingPartGrid.getGrid().onBeforeEditCell.subscribe(function(e, args) {
		var cell = mappingPartGrid.getGrid().getActiveCell().cell;
		if (cell == args.grid.getColumnIndex("part")) {
			if(args.item){
				selectedCustomerId = args.item.customerId;
				args.column.editorArgs.dataList = getPartNoList(selectedCustomerId);
			}
		}
	});
}

/**
 * Get data to Part mapping Dialog according to machine line.
 */
function searchPartMapping() {
	var params = {
		"machineId" : sourcemachineId
	};
	
	mappingPartGrid.reloadData({
		data : {
			url : "MCH_S01_PartMappingSearchList.html",
			param : params
		}
	});
	$.ui.dialog.overlay.events = "";
}
	
/**
 * Display Part mapping Dialog according to machine line.
 * 
 * @param id - row id
 * @return
 */
function OpenPopupMapping(id) {
	sourcemachineId = "";
	if (id != undefined || id != "") {
		sourcemachineId = id;
		
		searchPartMapping();
		
		/* Manage dialog*/
		clearMessage();
		$("#partMap").dialog({
			bgiframe : true,
			title    : "Mapping Part",
			modal    : true,
			autoOpen : true,
			height   : 500,
			width    : 600,
			resizable: false,
			buttons  : {
				Save : function() {
					savePartMapping();
				},
				Cancel : function() {
					clearMessage();
					$(this).dialog("close");
				}
			}
		});
		mappingPartGrid.getGrid().hideHeaderRowColumns();
	}
}

/**
 * Validate and save part mapping data.
 */
function savePartMapping() {
	mappingPartGrid.getGrid().getEditorLock().commitCurrentEdit();
	clearMessage();
	var partList = [];
	var linenum = 0;
	var itemData = mappingPartGrid.getData().getItems();
	listData = $.map(itemData, function(item, index) {
		partList.push(item.part);
		linenum++;
		if(item.statusFlag && 'd' != item.statusFlag){
			if (item.customerCode == null || !(item.customerCode)) {
				errors.push({
					"code" : "err.cmm.002",
					"arguments" : [ linenum, "Customer" ]
				});
			} else if (item.part == null || !(item.part)) {
				errors.push({
					"code" : "err.cmm.002",
					"arguments" : [ linenum, "Part No:Part Name" ]
				});
			}
		}
		item.sourceMachineId = sourcemachineId;
		item.machineId = sourcemachineId;
		return item;
	});
	
	/* check duplicate*/
	var index = 0;
	var uniqueNames = [];
	$.each(partList, function(i, el){
		index++;
	    if($.inArray(el, uniqueNames) === -1){ 
	    	uniqueNames.push(el);
	    }else{
	    	errors.push({'code': 'err.cmm.020', 'arguments': [index,'Part No : Part Name']});
	    }
	});
	
	if (errors.length > 0) {
		message.setErrors(errors);
	}
	if (errors.length == 0) {
		if (confirm(message.getMessage("cfm.cmm.001"))) {
			var uri = "MCH_S01savePartMappingList";
			var data = listData;
			var success = function(bean) {
				message.showMessage(bean);
				searchPartMapping();
				$('#partMap').dialog("close");
			};
			postJSONObject(uri, data, success);
		}
	}
}

/**
 * Create copy mapping part grid for display dialog when user click on icon in Machine Part column.
 */
function createCopyMappingPartGrid() {
	copypartMappingColumns = [ {
		id 		  : "statusFlag",
		name 	  : "",
		field 	  : "statusFlag",
		width 	  : 30,
		resizable : true,
		formatter : common.SlickGrid.Formatters.FlagFormatter,
		filter 	  : common.SlickGrid.filterColumnsOptions.ClearFilterButton,
		sortable  : true
	}, {
		id 		  : "wip",
		name 	  : "Wip",
		field  	  : "wip",
		width 	  : 100,
		resizable : true,
		filter 	  : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id 		  : "machineNo",
		name 	  : "Machine No.",
		field 	  : "machineNo",
		width 	  : 150,
		resizable : true,
		sortable  : true,
		filter 	  : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id 	 	  : "machineName",
		name 	  : "Machine Name",
		field 	  : "machineName",
		width 	  : 200,
		resizable : true,
		sortable  : true,
		filter    : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id 		  : "remark",
		name 	  : "remark",
		field 	  : "remark",
		width 	  : 200,
		resizable : true,
		sortable  : true,
		filter	  : common.SlickGrid.filterColumnsOptions.Fulltext
	} ];
	machineGrid = common.SlickGrid.newGrid();
	machineGrid = machineGrid.setGrid({
		"id" 	  : "machineGrid",
		"columns" : copypartMappingColumns,
		"key"	  : "machineNo",
		"data" 	  : []
	});
}

/**
 * Display Copy Part mapping Dialog according to machine line.
 * 
 * @param id - row id
 * @return
 */
function OpenPopupCopyPartMapping(id) {
	sourcemachineId = "";
	if (id != undefined || id != "") {
		sourcemachineId = id;
		/* Manage dialog*/
		clearMessage();

		$("#machineDialog").dialog({
			bgiframe : true,
			modal : true,
			autoOpen : true,
			height : 500,
			width : 750,
			resizable : false,
			open : function() {
				$('#btn-accept').appendTo($('#btnCol'));
			},
			close : function() {
				$('#btn-accept').appendTo($('#btnCol'));
				reloadMainGrid();
			},
			buttons : [ {
				id : "btn-accept",
				text : "Search",
				"class" : "submit_button",
				click : function() {
					machineSearch();
				}
			}, {
				text : "Save",
				click : function() {
					saveSelectMachineDestination(sourcemachineId);
				}
			}, {
				text : "Cancel",
				click : function() {
					clearMessage();
					$(this).dialog("close");
				}
			} ]
		});

		machineGrid.getGrid().hideHeaderRowColumns();
	}
}

/**
 * Get Data to copy mapping part when clicked search button on copy mapping part grid.
 */
function machineSearch() {
	selectedWip = "";
	message.clear();
	selectedWip = $('#wipDialog').val();
	if ("" != selectedWip) {
		var params = {
				wipDialog : selectedWip,
				sourceMachineId : sourcemachineId
		};
		/* Get data by id to grid*/
		machineGrid.reloadData({
			data : {
				url : "MCH_S01_copyPartMappingSearchList.html",
				param : params
			}
		});
		$.ui.dialog.overlay.events = "";
		
	} else {
		machineGrid.reloadData();
		message.setErrors([{"code":"err.cmm.001","arguments":["WIP"]}]);
	}
}

function saveSelectMachineDestination(sourcemachineId) {
	clearMessage();

	if ($('#wipDialog').val() != "") {
		var selectedRowIndex = machineGrid.getGrid().getSelectedRows();
		if (selectedRowIndex.length > 0) {
				var selectedRow = machineGrid.getDataView().getItem(
						selectedRowIndex);
				var machineId = selectedRow["machineId"];
				var sourceMachineId = sourcemachineId;
				params = {
					"machineId" : machineId,
					sourceMachineId : sourceMachineId
				};
				var successFn = function(bean) {
					message.showMessage(bean);
					reloadMainGrid();
				};
				var uri = "MCH_S01saveList";
				if (errors.length == 0 && params != "") {
					if (confirm(message.getMessage("cfm.mch.001"))) {
						postJSON(uri, params, successFn);
					}
				}
		} else {
			errors.push({
				"code" : "err.cmm.013",
				"arguments" : []
			});
			message.setErrors(errors);
			return;
		}
	} else {
		errors.push({
			"code" : "err.cmm.013",
			"arguments" : []
		});
		message.setErrors(errors);
		return;
	}
}

/**
 * Get customer list to combo box on mapping part grid dialog.
 */
function getCustomerList() {
	var customnerList = [];
	params = {};
	postJSONSync("MCH_01_getCustomerList", params, function(data) {
		customnerList = data;
	});
	return customnerList;
}

/**
 * Get part list to combo box on mapping part grid dialog.
 */
function getPartNoList(selectedCustomerId) {
	var partNoList = [];
	if (selectedCustomerId != "") {
		params = {
			customerId : selectedCustomerId
		};
		postJSONSync("MCH_01_getPartList", params, function(data) {
			partNoList = data;
		});
	}
	return partNoList;
}

/**
 * Search machine list by criteria.
 */
function  reloadMainGrid(){
	clearMessage();
	mchS01Form.find("select[id=pageNumber]").attr("disabled", true);
	mchS01Form.attr("action","MCH_S01_search.html");
	mchS01Form.submit();
}

/**
 * Clear message.
 */
function clearMessage() {
	message.clear();
	errors.length = 0;
}
