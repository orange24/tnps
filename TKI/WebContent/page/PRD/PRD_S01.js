/* Declare global variables for PRD_S01 screen */
var form ;
var options ;
var partColumns;
var editOptions ;
var prdGrid ;
var errors ;
var temp ;
var wip ;
var wipText ;
var dcPlanDate ;
var machineList  = [];
var customerList = [];
var partList 	 = [];
var shiftList    = [];
var moldList 	 = [];
var moldNoList 	 = [];

/*Create constant final variable for date*/
var dateRegex = new RegExp(/\b\d{1,2}[\/]\d{1,2}[\/]\d{4}\b/);

/**
 * Make sure ready part of the script for initial data
 * Declare the form
 * Create model for the screen
 * Create the view
 * Make sure to call the event for successive operations
 */
$(function($){
	/* Create form variable  */
	form = $('form#prdS01Form');
	/*Create Model*/
	getWip();
	getMachine();
	getShiftList();
	createGrid();
	/*Create View*/
	hideGrid();
	/*Create Event*/
	$('#cboWip').change(function(){ getMachine(); });
	$('#btnSearch').click(function(){ search(); });
	$('#btnSave').click(function(){ save(); });
});

/**
 * Check die cast plan date is valid to search
 * @param none
 * @returns the decision to search
 */
function isDate(){
	return dateRegex.test($('#txbDieCastPlanDate').val());
}

/**
 * Get WIP objects from back end when page has loaded
 * Send WIP JSON objects to another methods 
 * @param none
 * @returns none
 */
function getWip(){
	postJSONSync('PRD_S01_getWip', {}, function(jsonList){setWip(jsonList);});
}

/**
 * Set WIP objects to WIP list box
 * @param the collections of WIP JSON objects
 * @returns none
 */
function setWip(list){
	$.each(list,function(index,object){
		options = document.getElementById('cboWip').options;
		options[options.length] = new Option(object.wip, object.wip);
    });
}

/**
 * Get machine objects from the back end
 * Send machine objects to another methods to clear and change the values of machine list box
 * @param none
 * @returns none
 */
function getMachine(){
	postJSONSync('PRD_S01_getMachine', {'param' : $('#cboWip').val()}, function(jsonList){ setMachine(jsonList); });
}

/**
 * Set machine objects to list box
 * Set options by options based on key and value
 * @param the collections of machine JSON objects
 */
function setMachine(list){
	clearOptions();
	$.each(list,function(index,object){
		options = document.getElementById('cboMachine').options;
		options[options.length] = new Option(object.machineNo, object.machineId);
    });
}

/**
 * Clear machine list box after changing WIP list box
 * @param none
 * @returns none
 */
function clearOptions(){
	var option = document.getElementById('cboMachine');
	while (option.options.length > 1) {
		option.remove(1);
	}
}

/**
 * Search die cast plan information after clicking btnSearch
 * @param none
 * @returns none
 */
function search(){
	/* Assign wip value for consistency to search ST and SNP */
	wip       = $('#cboWip').val();
	wipText   = $('#cboWip :selected').text();
	dcPlanDate= $('#txbDieCastPlanDate').val();
	if(isDate()){
		message.clear();
		var columnList = prdGrid.getGrid().getColumns();
		$.each(columnList, function(index, column) {
            if (column.id == "machineNo") {
                        getMachineMaster();
                        column.editorArgs.dataList = machineList;
            }
		});
		
		prdGrid.getGrid().invalidateAllRows();
		
		fillData();
		showGrid();
	}else{
		showDateErrors();
	}
	prdGrid.getGrid().hideHeaderRowColumns();
}

/**
 * Pass JSON objects to back end after clicking btnSave
 * Check JSON objects are valid or not for making CUD operation @ back end
 * @param none
 * @returns none 
 */
function save(){     
	var saveData = [];
	temp = [];
	prdGrid.getGrid().getEditorLock().commitCurrentEdit();
	prdGrid.resetFilter();
	
	if(confirm(message.getMessage('cfm.cmm.001'))){
		var data = prdGrid.getDataView().getItems();
		saveData = $.map(prdGrid.getDataView().getItems(), function(object, index){ 
			if(object.statusFlag != null) {
				return object ;
			}
		});
		if(saveData.length > 0){
			if(isValidForSave(saveData) && isSeqSort(data)){
				disableSavebtn();
				postJSONObject('PRD_S01_saveData', saveData, function(result){
					message.showMessage(result);
					prdGrid.reloadData();
					enableSavebtn(); 
				});
			}
			//disableSavebtn();
		}
	}
	
}
/**
 * Check Seq number are sorting
 * @param dataRows
 * @returns boolean variable after check 
 */
function isSeqSort(data){
	var tmpMachineShiftSeq = [];
	
	$.map(data, function(object, index){ 
		if(object.statusFlag != 'd') {
			tmpMachineShiftSeq.push(object.machineNo+' '+object.shift+' '+object.seq);
		}
	});
	tmpMachineShiftSeq.sort();

	var countGroup = 1;
	var numLength = tmpMachineShiftSeq.length;
	var i = 0;

	 if(numLength > 0){
		 do{
	         if(i == 0){
	             if(parseInt(tmpMachineShiftSeq[i].substring(tmpMachineShiftSeq[i].length-1)) == countGroup){
	                 countGroup++;
//	                 console.log(tmpMachineShiftSeq[i]+" true1");
	             }else{
	            	 errors.push({'code': 'err.prd.006', 'arguments': []});
	            	 break;
//	            	 console.log(tmpMachineShiftSeq[i]+" false1");
	             }
	         }else if(i == numLength - 1){
	        	 if(tmpMachineShiftSeq[i-1].substring(0, 8) != (tmpMachineShiftSeq[i].substring(0, 8))){
	        		 countGroup = 1;
//	                 console.log("reset count");
	                 if(parseInt(tmpMachineShiftSeq[i].substring(tmpMachineShiftSeq[i].length-1)) == countGroup){
//	                	 console.log(tmpMachineShiftSeq[i]+"true5");
	                 }else{
	                	 errors.push({'code': 'err.prd.006', 'arguments': []});
	                	 break;
//	                	 console.log(tmpMachineShiftSeq[i]+" false5");
	                 }
	        	 }else{
	        		 if(parseInt(tmpMachineShiftSeq[i].substring(tmpMachineShiftSeq[i].length-1)) == countGroup){
	                     countGroup++;
//	                     console.log(tmpMachineShiftSeq[i]+" true6");
	                 }else{
	                	 errors.push({'code': 'err.prd.006', 'arguments': []});
	                	 break;
//	                	 console.log(tmpMachineShiftSeq[i]+" false6");
	                 }
	        	 } 
	         }else{
	        	 if(tmpMachineShiftSeq[i-1].substring(0, 8) != (tmpMachineShiftSeq[i].substring(0, 8))){
	        		 countGroup = 1;
//	                 console.log("reset count");
	                 if(parseInt(tmpMachineShiftSeq[i].substring(tmpMachineShiftSeq[i].length-1)) == countGroup){
	                     countGroup++;
//	                     console.log(tmpMachineShiftSeq[i]+" true3");
	                 }else{
	                	 errors.push({'code': 'err.prd.006', 'arguments': []});
//	                	 console.log(tmpMachineShiftSeq[i]+" false3");
	                	 break;
	                 }
	             }else{
	            	 if(parseInt(tmpMachineShiftSeq[i].substring(tmpMachineShiftSeq[i].length-1)) == countGroup){
	                     countGroup++;
//	                     console.log(tmpMachineShiftSeq[i]+" true4");
	                 }else{
	                	 errors.push({'code': 'err.prd.006', 'arguments': []});
	                	 break;
//	                	 console.log(tmpMachineShiftSeq[i]+" false4");
	                 }
	             }
	         }
	         i++;
	     }while(i<numLength);
	 } 
	
	if(errors.length > 0){
		message.setErrors(errors);
		return false ;
	}
	return true;
}

/**
 * Set initial property to screen
 * Disable save button when the click save again
 * @param none
 * @returns none
 */
function disableSavebtn(){
	$("#btnSave").attr("disabled", true);
}
function enableSavebtn(){
	$("#btnSave").attr("disabled", false);
}

/**
 * Validate input fields before sending to back end
 * @param the collection of JSON object to make CUD operations @ back end
 * @returns the decision for sending JSON objects to back end
 */
function isValidForSave(data){
	errors = [];
	temp = [];
	message.clear();
	
	$.each(data, function(index, object) {
		if(object.statusFlag != 'd'){
			if($.trim(object.machineNo) == '' || object.machineNo == undefined){
				errors.push({'code': 'err.cmm.002', 'arguments': [ object.rowNum, 'Machine']});
			}
			if($.trim(object.seq) == '' || object.seq == undefined){
				errors.push({'code': 'err.cmm.002', 'arguments': [ object.rowNum, 'Seq']});
			}else if(isNaN(parseInt(object.seq))){
				errors.push({'code': 'err.cmm.019', 'arguments': [object.rowNum, 'Seq']});
			}else if(!(parseInt(object.seq) > 0 && parseInt(object.seq) < 10)){
				errors.push({'code': 'err.cmm.021', 'arguments': [object.rowNum, 'Seq', 1, 9]});
			}
			if($.trim(object.shift) == '' || object.shift == undefined){
				errors.push({'code': 'err.cmm.019', 'arguments': [object.rowNum, 'Shift']});
				// Change name of grid object from customerName to customerCode.
			}
			if($.trim(object.customerCode) == '' || object.customerCode == undefined){
				errors.push({'code': 'err.cmm.002', 'arguments': [object.rowNum, 'Customer']});
			}
			if($.trim(object.partName) == '' || object.partName == undefined){
				errors.push({'code': 'err.cmm.002', 'arguments': [object.rowNum, 'Part No : Part Name']});
			}
			if($.trim(object.quantity) == '' || object.quantity == undefined){
				errors.push({'code': 'err.cmm.002', 'arguments': [object.rowNum, 'Qty']});
			}else if(isNaN(parseInt(object.quantity))){
				errors.push({'code': 'err.cmm.019', 'arguments': [object.rowNum, 'Qty']});
			}else if(parseInt(object.quantity) <= 0){
				errors.push({'code': 'err.cmm.009', 'arguments': [object.rowNum, 'Qty']});
			}
			if($.trim(object.reasonName) == '' || object.reasonName == undefined){
				errors.push({'code': 'err.cmm.002', 'arguments': [object.rowNum, 'Remark']});
			}
			if($.trim(object.moldName) == '' || object.moldName == undefined){
				errors.push({'code': 'err.cmm.002', 'arguments': [object.rowNum, 'Mold Name']});
			}
			if($.trim(object.moldNo) == '' || object.moldNo == undefined){
				errors.push({'code': 'err.cmm.002', 'arguments': [object.rowNum, 'Mold No']});
			}
//			if(object.quantity*1 < object.qtyDefault){
//				errors.push({'code': 'err.prd.005', 'arguments': [object.rowNum]});
//			}	
			/*check qty divide snp that not over 999*/
			
			if(!isNaN(parseInt(object.snpWip)) && !isNaN(parseInt(object.quantity))){
				if(parseInt(object.snpWip) != 0){
					var record = parseInt(object.quantity) / parseInt(object.snpWip);
					if(record > 999){
						errors.push({'code': 'err.prd.007', 'arguments': [object.rowNum]});
					}
				}
			}
		}
	});
	
	if(errors.length > 0){
		message.setErrors(errors);
		return false ;
	}
	
	$.each(prdGrid.getDataView().getItems(), function(index, object) { 
		if(object.statusFlag != 'd' && object.statusFlag != null){
			if(object.statusFlag == 'i' && object.genStatus){
				errors.push({'code':'err.cmm.005','arguments': [object.rowNum, 'Gen. Status']}); // need to create new error code
			}
			var date ;
			var isDupliacate = false;
			$.each(prdGrid.getDataView().getItems(), function(idx, item) { 
				if( index != idx  && item.statusFlag != 'd' ){
					if(object.statusFlag == 'i' ){
						var dateArr = object.dcPlanDate.split('/');
						date = dateArr[2] + '-' + dateArr[1] + '-' + dateArr[0] ;
					}else{ 
						date = item.dcPlanDate; 
					}
					if(!isDupliacate && (date == item.dcPlanDate) && (object.wip == item.wip) && (object.machineId == item.machineId)
							&& (object.seq == item.seq) && (object.customerId == item.customerId) && (object.partId == item.partId)
							&& (object.shift == item.shift)){
							errors.push({'code': 'err.cmm.020', 'arguments': [object.rowNum, 'DIE CAST Plan Entry']});
							isDupliacate= true;
					}
				}
			});
		}
	});
	
	if(errors.length > 0){
		message.setErrors(errors);
		return false ;
	}
	
	return true ;
}

/**
 * Show die cast date error message
 * @param none
 * @returns none
 */
function showDateErrors(){
	errors = [];
	message.clear();
	if($('#txbDieCastPlanDate').val() == '') {
		errors.push({'code': 'err.cmm.001', 'arguments': ['Diecast Plan Date']});
	}else{
		errors.push({'code': 'err.cmm.012', 'arguments': ['Diecast Plan Date']});
	}
	message.setErrors(errors);
}

/**
 * Get JSON object for machine information to set machine list box
 * @param none
 * @returns none
 */
function getMachineMaster(){
	machineList = [];
	var params = { 
			wip : $('#cboWip').val(),
			diecastPlanDate : $('#txbDieCastPlanDate').val()
	};
	if($('#cboMachine').val() == ''){
		postJSONSync('PRD_S01_getMachineMasterByDiecastPlanDate', params, function(jsonList){ machineList = jsonList; });
	}else{
		machineList.push({machineId : $('#cboMachine').val(), machineNo : $('#cboMachine :selected').text()});
	}
	
	return machineList;
}

/**
 * Get JSON object for reason information to set @ remark cell
 * @param none
 * @returns the collection of JSON objects for remark cell
 */
function getReasonMaster(){
	var data = [];
	postJSONSync('PRD_S01_getReasonMaster', {}, function(jsonList){ data = jsonList; });
	return data ;
}

/**
 * Get JSON objects for customer cell
 * @param machineId identity
 * @returns the collection of JSON objects for customer cell
 */
function getCustomerMaster(){
	var data = [];
	var param = {};
	postJSONSync('PRD_S01_getCustomerMaster', param, function(jsonList){ data = jsonList; });
	return data ;
}

/**
 * Get JSON objects for part cell
 * @param machineId identity
 * @param customerId identity
 * @returns the collection of JSON objects for part cell
 */
function getPartMaster(machineId, customerId){
	var data = [];
	var param = {
			'customerId' : customerId, 
			'machineId'  : machineId
		};
	postJSONSync('PRD_S01_getPartMaster', param, function(jsonList){ data = jsonList; });
	return data ;
}

/**
 * Get JSON objects for SNP cell
 * @param partId identity
 * @returns the collection of JSON objects for SNP cell
 */
function getSnpByWip(partId){
	var data = [];
	var param = {
			'partId' : partId
		};
	postJSONSync('PRD_S01_getSnpByWip', param, function(jsonList){ data = jsonList; });
	return data ;
}

function getMoldNoByMold(moldId){
	var data = [];
	var param = {
			'moldId' : moldId,
		};
	postJSONSync('PRD_S01_getMoldNoByMold', param, function(jsonList){ data = jsonList; });
	return data ;	
}

function getCavByMold(moldId, moldNo){
	var data = [];
	var param = {
			'moldId' : moldId,
			'moldNo' : moldNo
		};
	postJSONSync('PRD_S01_getCavByMold', param, function(jsonList){ data = jsonList; });
	return data ;	
}

function getMoldByPart(partId){
	var data = [];
	var param = {
			'partId' : partId
		};
	postJSONSync('PRD_S01_getMoldByPart', param, function(jsonList){ data = jsonList; });
	return data ;
}

/**
 * Get JSON objects for ST cell
 * @param partId identity
 * @returns the collection of JSON objects for ST cell
 */
function getSTByPartIdAndWip(partId){
	var data = [];
	var param = {
			'wip'    : wip,
			'partId' : partId
		};
	postJSONSync('PRD_S01_getSTByPartIdAndWip', param, function(jsonList){ data = jsonList; });
	return data ;
}

/**
 * Get shift values based on JSON objects
 * Set the JSON values based on shift
 * @param none
 * @returns none
 */
function getShiftList(){
	/* Set default values to shift JSON list */
	shiftList.push({"shift" : "D", "shiftText" : "D"});
	shiftList.push({"shift" : "N", "shiftText" : "N"});
}

/**
 * Create the grid to show on the screen
 * @param none
 * @returns none
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
		width 		: 40,
		cssClass 	: "cell-r",
		resizable 	: true,
		sortable	: true,
		formatter	: common.SlickGrid.Formatters.RowNumFormatter
	},{
		id 			: "wip",
		// Change W/C to WIP
		name 		: "WIP",
		field 		: "wip",
		width 		: 60,
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "machineNo",
		name 		: "Machine",
		field 		: "machineNo",
		width 		: 100,
		resizable 	: true,
		sortable	: true,
		headerCssClass: "editable-column required-column",
		editor		: common.SlickGrid.Editor.DropDownEditor,
		editorArgs  : {
	        dataList: machineList,
	        value 	: "machineId",
	        label   : "machineNo" 
	    },
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "seq",
		name 		: "Seq",
		field 		: "seq",
		width 		: 80,
		cssClass 	: "cell-r",
		resizable 	: true,
		sortable	: true,
		maxlength   : 1,
		headerCssClass: "editable-column required-column",
		editor		: common.SlickGrid.Editor.IntegerEditor,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "shift",
		name 		: "Shift",
		field 		: "shift",
		width 		: 70,
		resizable 	: true,
		sortable	: true,
		headerCssClass: "editable-column required-column",
		editor		: common.SlickGrid.Editor.DropDownEditor,
		editorArgs  : {
	        dataList: shiftList,
	        value 	: "shift",
	        label   : "shiftText" 
	    },
		formatter   : ShiftFormatter,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		// Change name of grid object from customerName to customerCode.
		id 			: "customerCode",
		name 		: "Customer",
		field 		: "customerCode",
		width 		: 170,
		resizable 	: true,
		sortable	: true,
		headerCssClass: "editable-column required-column",
		editor		: common.SlickGrid.Editor.DropDownEditor,
		editorArgs  : {
	        dataList: getCustomerMaster(),
	        value 	: "customerId",
	        label   : "customerCode" 
	    },
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "partName",
		name 		: "Part No : Part Name",
		field 		: "partName",
		width 		: 170,
		resizable 	: true,
		sortable	: true,
		headerCssClass: "editable-column required-column",
		editor		: common.SlickGrid.Editor.DropDownEditor,
		editorArgs  : {
	        dataList: partList,
	        value 	: "partId",
	        label   : "partName" 
	    },
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "moldId",
		name 		: "Mold Name",
		field 		: "moldName",
		width 		: 160,
		resizable 	: true,
		sortable	: true,
		headerCssClass: "editable-column required-column",
		editor		: common.SlickGrid.Editor.DropDownEditor,
		editorArgs  : {
	        dataList: moldList,
	        value 	: "moldId",
	        label   : "moldName" 
	    },
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "moldNo",
		name 		: "Mold No",
		field 		: "moldNo",
		width 		: 100,
		resizable 	: true,
		sortable	: true,
		headerCssClass: "editable-column required-column",
		editor		: common.SlickGrid.Editor.DropDownEditor,
		editorArgs  : {
	        dataList: moldNoList,
	        value 	: "moldNo",
	        label   : "moldNo" 
	    },
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "cavNo",
		name 		: "CAV. No.",
		field 		: "cavNo",
		width 		: 70,
		cssClass 	: "cell-r",
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "quantity",
		name 		: "QTY",
		field 		: "quantity",
		width 		: 70,
		cssClass 	: "cell-r",
		resizable 	: true,
		sortable	: true,
		maxlength   : 5,
		headerCssClass: "editable-column required-column",
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext,
		editor 		: common.SlickGrid.Editor.IntegerEditor
	}, {
		id 			: "snpWip",
		name 		: "SNP",
		field 		: "snpWip",
		width 		: 70,
		cssClass 	: "cell-r",
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "st",
		name 		: "ST",
		field 		: "st",
		width 		: 70,
		cssClass 	: "cell-r",
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "reasonName",
		name 		: "Remark",
		field 		: "reasonName",
		width 		: 90,
		resizable 	: true,
		sortable	: true,
		headerCssClass: "editable-column required-column",
		editor		: common.SlickGrid.Editor.DropDownEditor,
		editorArgs  : {
	        dataList: getReasonMaster(),
	        value 	: "reasonId",
	        label   : "reasonName" 
	    },
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "genStatus",
		name 		: "Gen. Status",
		field 		: "genStatus",
		width 		: 110,
		cssClass 	: "cell-c",
		resizable 	: true,
		sortable	: true,
		headerCssClass: "editable-column",
		editor 		: checkboxFng,
		formatter   : function(row, cell, value, columnDef, dataContext) {
			var result = value ? "Yes" : "No";
			if(dataContext.statusFlag == "i"){
				result = "No";
			} 
			return result;
		},
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "createDate",
		name 		: "Create Date",
		field 		: "createDate",
		width 		: 90,
		cssClass 	: "cell-c",
		resizable 	: true,
		sortable	: true,
		formatter	: common.SlickGrid.Formatters.DateTimeFormatter,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "createdBy",
		name 		: "Created By",
		field 		: "createdBy",
		width 		: 90,
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "lastUpdate",
		name 		: "Update Date",
		field 		: "lastUpdate",
		width 		: 90,
		cssClass 	: "cell-c",
		resizable 	: true,
		sortable	: true,
		formatter	: common.SlickGrid.Formatters.DateTimeFormatter,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "updatedBy",
		name 		: "Update By",
		field 		: "updatedBy",
		width 		: 90,
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "deleteField",
		name 		: "Delete",
		field 		: "deleteField",
		width 		: 50,
		cssClass 	: "cell-c",
		resizable 	: true,
		formatter 	: common.SlickGrid.Formatters.DelFormatter,
		editor 		: common.SlickGrid.Editor.DelEditor
	}];
	
	prdGrid = common.SlickGrid.newGrid();
	prdGrid = prdGrid.setOptions(editOptions);
	prdGrid = prdGrid.setGrid( {
		"id" : "prdGrid",
		"columns" : partColumns,
		"data" : []
	});
	createCellChange();
}

/**
 * Check box on 'Gen. Status' column.
 * @param args
 * @returns
 */
function checkboxFng(args) {
	var $select;
	var defaultValue;
	var scope = this;
		
	this.init = function() {
		if(args.item.statusFlag == "i" || args.item.statusFlag == null || args.item.statusFlag == undefined){
			$select = $("<INPUT type=checkbox value='true' disabled='disabled' class='editor-checkbox' hideFocus>");
		}else{
			$select = $("<INPUT type=checkbox value='true' class='editor-checkbox' hideFocus>");
		}
		$select.appendTo(args.container);
		$select.focus();
	};

	this.destroy = function() {
		$select.remove();
	};

	this.focus = function() {
		$select.focus();
	};

	this.loadValue = function(item) {
		defaultValue = item[args.column.field];
		if (defaultValue === true || defaultValue == "true") {
			$select.attr("checked", "checked");
			$select.removeAttr("disabled");
		} else {
			$select.removeAttr("checked");
			if(item['genStatusDefault'] === false || item['genStatusDefault'] == "false"){
				$select.attr("disabled","disabled");
			}
		}
	};

	this.serializeValue = function() {
		return $select.is(":checked");
	};

	this.applyValue = function(item, state) {
		item[args.column.field] = state ? true : false;
	};

	this.isValueChanged = function() {
		if (defaultValue === true || defaultValue == "true") {
			defaultValue = true;
		} else {
			defaultValue = false;
		}
		return ($select.is(":checked") != defaultValue);
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

/**
 * Fill the grid to show
 * @param  none
 * @return none
 */
function fillData(){
	var param = {
			'date' 	  : $('#txbDieCastPlanDate').val(),
			'wip'  	  : $('#cboWip').val(),
			'machine' : $('#cboMachine').val()
		};
	prdGrid.reloadData({
		data : {
			"url" : "PRD_S01_search.html",
			"param" : param
		}
	});
	prdGrid.getGrid().invalidateAllRows();
}

/**
 * Get JSON objects from back end before cell does not update
 * Reset default values after adding new row
 * Reset default values after changing cell 
 * @param none
 * @returns none
 */
function createCellChange(){
	/* Get JSON objects before editing cell from back end */
	prdGrid.getGrid().onBeforeEditCell.subscribe( function(e, args) {
		if(prdGrid.getGrid().getActiveCell() != undefined || prdGrid.getGrid().getActiveCell() != null){
			var cell = prdGrid.getGrid().getActiveCell().cell;
		    if(args.item != undefined || args.item != null){
		    	var object = args.item;
		    	if (cell == args.grid.getColumnIndex("partName")) {
		    		if(object != null || object != undefined){
		 	    		if(object.machineId != undefined && object.customerId != undefined){
		 	    			if(object.machineId != '' && object.customerId != ''){
				 	    		args.column.editorArgs.dataList = getPartMaster(object.machineId, object.customerId);
		 	    			}else{ args.column.editorArgs.dataList = [];}
		 	    		}else{ args.column.editorArgs.dataList = []; }
		 	    	}
		 	    }

		    	if (cell == args.grid.getColumnIndex("moldId")) {
		    		if(object != null || object != undefined){
		 	    		if(object.partId != undefined){
		 	    			if(object.partId != ''){
				 	    		args.column.editorArgs.dataList = getMoldByPart(object.partId);
		 	    			}else{ args.column.editorArgs.dataList = [];}
		 	    		}else{ args.column.editorArgs.dataList = []; }
		 	    	}
		 	    }

		    	if (cell == args.grid.getColumnIndex("moldNo")) {
		    		if(object != null || object != undefined){
		 	    		if(object.moldId != undefined){
		 	    			if(object.moldId != ''){
				 	    		args.column.editorArgs.dataList = getMoldNoByMold(object.moldId);

					    		var jsonList = getCavByMold(args.item.moldId, args.item.moldNo);
								if(jsonList.length > 0) {args.item.cavNo = jsonList[0].cavNo;}
								
		 	    			}else{ args.column.editorArgs.dataList = [];}
		 	    		}else{ args.column.editorArgs.dataList = []; }
		 	    	}
		 	    }
		    }
		    else{
		    	//FIXME : after click on line have data then clicked new line data in dropdownlist incurrect.
//		    	args.column.editorArgs.dataList = [];
		    }
		}
	});
	
	/* Set default data while adding new row */
	prdGrid.getGrid().onAddNewRow.subscribe( function(e, args) {
		if(args.item != undefined || args.item != null){ 
			if (args.item.statusFlag != null){
				setDefaultValues(args);
				changeMachineDefaultValues(args);
				prdGrid.getDataView().updateItem(args.item.rowNum, args.item);
			}
		}
	});
	
	/* Set the values while cell change event */
	prdGrid.getGrid().onCellChange.subscribe( function(e, args) {
		if(args.item != undefined || args.item != null){
			if(prdGrid.getGrid().getActiveCell() != undefined || prdGrid.getGrid().getActiveCell() != null){
				var cell = prdGrid.getGrid().getActiveCell().cell;
				if (cell == args.grid.getColumnIndex("machineNo")){
					changeMachineDefaultValues(args);
					prdGrid.getDataView().updateItem(args.item.rowNum, args.item);
				}
				// Change name of grid object from customerName to customerCode.
				if (cell == args.grid.getColumnIndex("customerCode")){
					changeCustomerDefaultValues(args);
					prdGrid.getDataView().updateItem(args.item.rowNum, args.item);
				}
				if (cell == args.grid.getColumnIndex("partName")){
					if(args.item.partId != undefined && args.item.partId != ''){
						changePartDefaultValues(args);
						var jsonList = getSnpByWip(args.item.partId);
						if(jsonList.length > 0) {args.item.snpWip = jsonList[0].snpWip;}
						jsonList = getSTByPartIdAndWip(args.item.partId);
						if(jsonList.length > 0) {args.item.st = jsonList[0].st;}
						prdGrid.getDataView().updateItem(args.item.rowNum, args.item);
						
//						var jsonList = getMoldByPart(args.item.partId);
//						prdGrid.getDataView().updateItem(args.item.rowNum, args.item);
						
					}
				}
				if (cell == args.grid.getColumnIndex("moldId")){
					if(args.item.moldId != undefined && args.item.moldId != ''){
//						changePartDefaultValues(args);
//						var jsonList = getMoldNoByMold(args.item.moldId);
//						if(jsonList.length > 0) {args.item.moldNo = jsonList[0].moldNo;}
//						prdGrid.getDataView().updateItem(args.item.rowNum, args.item);
					}
				}
				if (cell == args.grid.getColumnIndex("moldNo")){
					if(args.item.moldId != undefined && args.item.moldNo != undefined && args.item.moldId != '' && args.item.moldNo != ''){
//						changePartDefaultValues(args);
						var jsonList = getCavByMold(args.item.moldId, args.item.moldNo);
						if(jsonList.length > 0) {args.item.cavNo = jsonList[0].cavNo;}
						prdGrid.getDataView().updateItem(args.item.rowNum, args.item);
					}
				}
			}
		}
	});
}

/**
 * Set the default values while adding new row
 * @param args to access the things
 * @returns none
 */
function setDefaultValues(args){
	args.item.shift     = 'D';
	args.item.shiftText = 'D';
	args.item.wip       = wip;
	args.item.dcPlanDate= dcPlanDate;
	postJSONSync('PRD_S01_getReasonMaster', {}, function(jsonList){ 
			$.each(jsonList,function(index,object){
				if(object.reasonName.toUpperCase() == 'normal'.toUpperCase()){
					args.item.reasonId = object.reasonId;
					args.item.reasonName = object.reasonName;
				}
			});
		});
	if($('#cboMachine').val() != ''){
		if(args.item.machineId != ''){
			args.item.machineId = $('#cboMachine').val();
			args.item.machineNo = $('#cboMachine :selected').text();
		}
	}
}

/**
 * Format the shift list box for each row
 * @param row
 * @param cell
 * @param value
 * @param column definition
 * @param data context
 * @returns the formatted value for the shift list box
 */
function ShiftFormatter(row, cell, value, columnDef, dataContext) {
	if(value == null || value == undefined ) {
		/* Update the dataContext for the default values */
		dataContext.shift     = 'D';
		dataContext.shiftText = 'D';
		return 'D';
	}
	/* Format the value from back end based on JSON parameters  */
	return (value == 'D') ? 'D' : (value == 'N') ? 'N' : '';
}

/**
 * Reset default values based on machine information changed event
 * @param args to reset the values 
 * @returns none
 */
function changeMachineDefaultValues(args){
	args.item.st = '';
	args.item.snpWip = '';
	args.item.partId = '';
	args.item.partName = '';
}

/**
 * Reset default values based on customer information changed event
 * @param args to reset the values 
 * @returns none
 */
function changeCustomerDefaultValues(args){
	args.item.st = '';
	args.item.snpWip = '';
	args.item.partId = '';
	args.item.partName = '';
}

/**
 * Reset default values based on part information changed event
 * @param args to reset the values 
 * @returns none
 */
function changePartDefaultValues(args){
	args.item.st = '';
	args.item.snpWip = '';
}

/**
 * Set initial property to screen
 * Hide the grid when the page is loaded
 * @param none
 * @returns none
 */
function hideGrid(){
	$('#gridHeader').hide();
	$('#btnSave').hide();
}

/**
 * Show the grid after clicking btnSearch
 * @param  none
 * @return none
 */
function showGrid(){
	$('#gridHeader').show();
	$('#btnSave').show();
}

