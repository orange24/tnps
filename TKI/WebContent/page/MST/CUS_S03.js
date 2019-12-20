var fgGrid;
var fgColumns;
var customerLineGrid;
var customerLineColumns;
var fgData = [];
var fgMaster = [];
var errors = [];
var customerCode;
$(function($) {
	// data
	custLineGrid = $("#custLineGrid");
	customerNameSel = $("#customerNameSel");
	// view
	createCustomerLine();
	custLineGrid.hide();
	// event
	searchCustomerLine();
	saveCustomerLine();
});

function createCustomerLine() {
	var editOptions = $.extend({}, common.SlickGrid.newGrid().getOptions(), {
		enableAddRow : true
	});
	// Extend default options from common grid
	// <!-- CustomerFG Grid -->
	customerLineColumns = [ {
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
		id : "customerLineId",
		name : "Line Id",
		field : "customerLineId",
		width : 80,
		resizable : true,
		sortable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id : "customerLineName",
		name : "Line Name",
		field : "customerLineName",
		width : 200,
		resizable : true,
		sortable : true,
		headerCssClass : "editable-column",
		filter : common.SlickGrid.filterColumnsOptions.Fulltext,
		editor 		: common.SlickGrid.Editor.TextEditor
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
	customerLineGrid = common.SlickGrid.newGrid();
	customerLineGrid = customerLineGrid.setOptions(editOptions);
	customerLineGrid = customerLineGrid.setGrid({
		"id" : "customerLineGrid",
		"columns" : customerLineColumns,
		"data" : fgData,
		"key" : "customer"
	});

	dataView = customerLineGrid.getDataView();

	customerLineGrid.getGrid().onAddNewRow.subscribe(function(e, args) {
		var item = args.item;
		item.customerId = $('#customerNameSel :selected').val();
		item.customerCode = $('#customerNameSel :selected').text();
		dataView.updateItem(item.customer, item);
		dataView.endUpdate();
	});

	customerLineGrid.getGrid().onCellChange.subscribe(function(e, args) {
		var item = args.item;
		item.customerId = $('#customerNameSel :selected').val();
		item.customerCode = $('#customerNameSel :selected').text();
		dataView.updateItem(item.customer, item);
		dataView.endUpdate();
	});
	
}

function getFgCustomertMappingData() {
	customerNameSel = $("#customerNameSel");

	customerLineGrid.reloadData({
		"data" : {
			url : "CUS_S03_getCustomerLineList.html",
			param : {
				customerId : customerNameSel.val()
			}
		}
	});

	customerLineGrid.getGrid().hideHeaderRowColumns();
}
function searchCustomerLine() {
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
			custLineGrid.show();
			getFgCustomertMappingData();
		}
	});
}
function requiredFieldValidator(value) {
	if (value == null || value == undefined || !value.length) {
		return false;
	}
	else {
		return  true;
	}
}
function saveCustomerLine() {
	var success = function(bean) {
		message.showMessage(bean);
		customerLineGrid.reloadData(bean);
	};
	$("[id=btnSave]").click(function() {
		message.clear();
		errors.length = 0;
		
		customerLineGrid.getGrid().getEditorLock().commitCurrentEdit();
		customerLineGrid.resetFilter();		
		
		var cusLineArray = [];
		var linenum = 0;
		var itemList = $.map(customerLineGrid.getData().getItems(), function(n, i) {
			linenum++;
			if( n.statusFlag != null ){ 				
				//Line Error
				if(!requiredFieldValidator(n.customerLineName) && n.statusFlag != 'd'){
					errors.push({"code": "err.cmm.002", "arguments": [linenum,"Line Name"]});
				} 
				return n; 
			}	
		});

		console.log(itemList);

		if (message.isNoError() && itemList != "") {
			if (confirm(message.getMessage("cfm.cmm.001"))) {
				postJSONObject("CUS_S03_saveList", itemList, success);
			}
		}
	});
}
