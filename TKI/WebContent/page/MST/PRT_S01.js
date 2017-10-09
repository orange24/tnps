/* Variables Declaration */
var partGrid;
var partColumns;
var partMCForm ;
var exportForm ;
var editOptions ;
var partArr = [];
var errors  = [];
var data    = [];
var checkArr= [];
var materialList = [] ;
var temp = [];

/**
 * Make sure ready part of the script for initial data
 * Declare the form
 * Create model for the screen
 * Create the view
 * Make sure to call the event for successive operations
 */
$( function($) {
	/* Creating action form variable  */
	partMCForm = $("form#partMCForm");
	exportForm = $("form#exportForm");
	/* Create Model */
	getMaterial();
	/* Create View */
	createGrid();
	/* Create Event */
	$('#btnExportPart').click(function() { exportData(); });
	$('#btnSavePart').click(function() { save(); });
});

/**
 * Get material list for selecting material in list box
 * Assign result values to materialList
 * @param none
 * @returns none
 */
function getMaterial(){
	postJSONSync("PRT_S01_getMaterial",{},function(result){
		materialList = result ;
	});
}

/**
 * Export part information 
 * @param none
 * @returns decision for show errors or not 
 * @returns decision for  export or not
 */
function exportData(){
	message.clear();
	if(partGrid.getFilteredRows().length == 0){
		errors.push({'code': 'err.cmm.018', 'arguments': []});
		message.setErrors(errors);
		return false ;
	}
	/* Get header row parameters to send back end */
	exportForm.empty();
	var header = $(partGrid.getGrid().getHeaderRow());
	header.find(".slick-headerrow-column").each(function(index, object){
		var obj = partGrid.getGrid().getColumns()[index];
		var item = $(this).find("input,select");
		if( item.exists() && item.val() ){
			exportForm.append($('<input type="hidden" name="' + obj.field + '" value="' + item.val() + '"/>'));
		}
	});
	downloadNotify($("<div title='"+message.getMessage('downloadAlertTitle')+"'>"+message.getMessage('downloadAlertContent')+"</div>"));
	exportForm.submit();
}

/**
 * Make CUD operations if no validation errors
 * @param none
 * @returns the decision for making operation
 */
function save(){
	var saveData = [];
	partArr = [];
	checkArr= [];
	temp 	= [];
	partGrid.getGrid().getEditorLock().commitCurrentEdit();
	
	saveData = $.map(partGrid.getDataView().getItems(), function(object, index){ 
		checkArr.push(object.partNo);
		if(object.statusFlag != null) {
			return object ;
		}
	});
	
	if(isValidForSave(saveData)){
		if(confirm(message.getMessage('cfm.cmm.001'))){
			if(saveData.length > 0){
				postJSONObject('PRT_S01_save', saveData, function(bean){
				message.showMessage(bean);
				partGrid.reloadData();
				});
			}
		}
	}
}

/**
 * Validate input fields before sending to back end
 * @param the collection of JSON object to make CUD operations @ back end
 * @returns the decision for sending JSON objects to back end
 */
function isValidForSave(data){
	errors  = [];
	message.clear();
	partGrid.resetFilter();
	
	/* Check mandatory fields */
	$.each(data, function(index, object) {
		if(object.statusFlag != 'd'){
			if($.trim(object.partNo) == '' || object.partNo == undefined){
				errors.push({'code': 'err.cmm.002', 'arguments': [ object.rowNum, 'Part No']});
			}
			if($.trim(object.partName) == '' || object.partName == undefined){
				errors.push({'code': 'err.cmm.002', 'arguments': [ object.rowNum, 'Part Name']});
			}
			if($.trim(object.lot) == '' || object.lot == undefined){
				errors.push({'code': 'err.cmm.002', 'arguments': [object.rowNum, 'SNP (WIP)']});
			}else if(isNaN(parseInt(object.lot))){
				errors.push({'code': 'err.cmm.019', 'arguments': [object.rowNum, 'SNP (WIP)']});
			}
			if($.trim(object.materialName) == '' || object.materialName == undefined){
				errors.push({'code': 'err.cmm.002', 'arguments': [ object.rowNum, 'Material']});
			}
			if($.trim(object.choose) == '' || object.choose == undefined){
				object.choose = false ;
			}
		}
	});
	
	if(errors.length > 0){
		message.setErrors(errors);
		return false ;
	}
	
	var indexArr = [];
	$.each(partGrid.getDataView().getItems(), function(index, object) {
		if($.grep(checkArr, function (element) {return element === object.partNo;}).length > 1){
			if(object.statusFlag != null && object.statusFlag != 'd'){
				if($.inArray(index, indexArr) == -1){
					indexArr.push(index);
					errors.push({'code': 'err.cmm.020', 'arguments': [object.rowNum, 'Part No']});
				}
			}
		}
	});
	
	if(errors.length > 0){
		message.setErrors(errors);
		return false ;
	}
	
	return true;
}

/**
 * Create the grid to show on the screen
 * @param none
 * @returns one
 */
function createGrid(){
	editOptions = $.extend( {}, common.SlickGrid.newGrid().getOptions(), {
		editable			: true,
	    enableAddRow		: true,
	    showHeaderRow 		: true,
		headerRowHeight 	: 30
	});
	
	partColumns = [{
		id 			: "statusFlag",
		name 		: "",
		field 		: "statusFlag",
		cssClass 	: "cell-c",
		width 		: 40,
		resizable 	: true,
		formatter	: common.SlickGrid.Formatters.FlagFormatter ,
		filter		: common.SlickGrid.filterColumnsOptions.ClearFilterButton
	},{
		id 			: "lineNumber",
		name 		: "No.",
		field 		: "lineNumber",
		width 		: 50,
		resizable 	: true,
		sortable	: true,
		cssClass 	: "cell-l",
		formatter	: common.SlickGrid.Formatters.RowNumFormatter
	},{
		id 			: "partId",
		name 		: "Part ID",
		field 		: "partId",
		width 		: 60,
		resizable 	: true,
		sortable	: true,
		cssClass 	: "cell-l",
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "partNo",
		name 		: "Part No.",
		field 		: "partNo",
		width 		: 130,
		resizable 	: true,
		sortable	: true,
		maxlength   : 30,
		headerCssClass: "editable-column required-column",
		editor		: common.SlickGrid.Editor.TextEditor,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "partName",
		name 		: "Part Name",
		field 		: "partName",
		width 		: 180,
		resizable 	: true,
		sortable	: true,
		maxlength   : 100,
		headerCssClass : "editable-column required-column",
		editor		: common.SlickGrid.Editor.TextEditor,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "lot",
		name 		: "SNP(WIP)",
		field 		: "lot",
		cssClass 	: "cell-r",
		width 		: 100,
		resizable 	: true,
		sortable	: true,
		maxlength   : 8, 
		headerCssClass: "editable-column required-column",
		editor         : common.SlickGrid.Editor.IntegerEditor,
		formatter	   : common.SlickGrid.Formatters.IntegerFormatter,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "materialName",
		name 		: "Material",
		field 		: "materialName",
		width 		: 120,
		resizable 	: true,
		sortable	: true,
		headerCssClass: "editable-column required-column",
		editor		: common.SlickGrid.Editor.DropDownEditor,
		editorArgs  : {
	        dataList: materialList,
	        value 	: "materialId",
	        label   : "materialName" 
	    },
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "choose",
		name 		: "Enable",
		field 		: "choose",
		width 		: 85,
		cssClass 	: "cell-c",
		resizable 	: true,
		sortable	: true,
		headerCssClass: "editable-column required-column",
		formatter	: common.SlickGrid.Formatters.YesNo,
		filter 		: common.SlickGrid.filterColumnsOptions.Checkbox,
		editor 		: common.SlickGrid.Editor.Checkbox
	}, {
		id 			: "createDate",
		name 		: "Create Date",
		field 		: "createDate",
		width 		: 100,
		cssClass 	: "cell-c",
		resizable 	: true,
		sortable	: true,
		formatter	: common.SlickGrid.Formatters.DateTimeFormatter,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "createdBy",
		name 		: "Create By",
		field 		: "createdBy",
		width 		: 100,
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id 			: "lastUpdate",
		name 		: "Update Date",
		field 		: "lastUpdate",
		width 		: 100,
		cssClass 	: "cell-c",
		resizable 	: true,
		sortable	: true,
		formatter	: common.SlickGrid.Formatters.DateTimeFormatter,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "updatedBy",
		name 		: "Update By",
		field 		: "updatedBy",
		width 		: 100,
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "deleteField",
		name 		: "Delete",
		field 		: "deleteField",
		width 		: 45,
		cssClass 	: "cell-c",
		resizable 	: true,
		formatter 	: common.SlickGrid.Formatters.DelFormatter,
		editor 		: common.SlickGrid.Editor.DelEditor
	}];
	fillData();
}

/**
 * Fill the grid to show data
 * @param  none
 * @return none
 */
function fillData(){
	partGrid = common.SlickGrid.newGrid();
	partGrid = partGrid.setOptions(editOptions);
	partGrid = partGrid.setGrid( {
		"id" : "partGrid",
		"columns" : partColumns,
		"data" : {"url" : "PRT_S01_search.html"}
	});
	createCellChange();
	partGrid.getGrid().invalidateAllRows();
}

/**
 * Get JSON objects from back end before cell does not update
 * Reset default values after adding new row
 * Reset default values after changing cell 
 * @param none
 * @returns none
 */
function createCellChange(){
	partGrid.getGrid().onAddNewRow.subscribe( function(e, args) {
		if(args.item != undefined || args.item != null){ 
			if (args.item.statusFlag != null){
				if(args.item.choose == undefined){
					args.item.choose = true;
					partGrid.getDataView().updateItem(args.item.rowNum, args.item);
				}
			}
		}
	});
}