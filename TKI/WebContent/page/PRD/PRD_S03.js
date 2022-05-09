var prdS03Form ;
var prdS03Grid ;
var options ;
var columns ;
var printerName ;

$(function($){
	prdS03Grid = $('#prdS03Grid');
	prdS03Form = $('#prdS03Form');
	//createGrid();
	hideGrid();
	createPrinter();
	/* Create Model */
	getPrinter();
	/* Create events */
	$('#cboWip').change(function(){ getMachine(); });
	$('#btnSearch').click(function(){ search(false); });
	$('#btnSave').click(function(){ save(); });
	$('#btnPrint').click(function(){ print(); });
	$('#btnPrintPDF').click(function(){ printPDF(); });
	$('#btnClear').click(function(){ enableSearchForm();});
	$('#btnSelectAndPrint').click(function(){ selectPrinter();});
});
function onRowCountChangedForSumQty(){
	prdS03Grid.getDataView().onRowCountChanged.subscribe( function(e, args) {
		$('#tdQtyTotal').text(sumQtyTotal());
	});
}
function sumQtyTotal(){
	var qtyTotal = 0;
	$.map(prdS03Grid.getFilteredRows(), function(object, index){ 
		qtyTotal += object.quantity;
	});
	return qtyTotal;
}
function getPrinter(){
	postJSONObject("boxPrinterName", {}, function(list){ 
		$.each(list,function(index,object){
			options = document.getElementById('cboPrinter').options;
			options[options.length] = new Option(object.displayName, object.printerName);
	    });
	});
}

function save(){
	prdS03Grid.getGrid().getEditorLock().commitCurrentEdit();
	prdS03Grid.resetFilter();
	var saveData = [];
	message.clear();
	if(confirm(message.getMessage('cfm.cmm.001'))){
		saveData = $.map(prdS03Grid.getDataView().getItems(), function(object, index){ 
			if(object.statusFlag != null) {
				return object ;
			}
		});
		errors = [];
		if(saveData.length > 0){
			postJSONObject("PRD_S03_save", saveData, function(bean){ 
				var isPrint = false;
				search(isPrint);
				message.showMessage(bean);
			});
		}
	}
}

function search(isPrint){

	showLoading();
	
	if(!isPrint){
		if(isValidMandatory()){
			postJSONObject("PRD_S03_search", getParam(), function(jsonList){ 
				createGrid();
				fillData(jsonList);
				showGrid();
			});
			disableSearchForm();
			

			postJSONObject("PRD_S03_get_wip", getParam(), function(jsonList){ 
				showProcessList(jsonList);
			});
		}
	}else{
		postJSONObject("PRD_S03_search", getParam(), function(jsonList){ 
			//createGrid();
			fillData(jsonList);
			showGrid();
		});
		disableSearchForm();
	}
}


function printPDF(){ 
		if(undefined == printerName){
			$("#printer").dialog("open");
		} else {
			prdS03Grid.getGrid().getEditorLock().commitCurrentEdit();		
			var param = $.map(prdS03Grid.getFilteredRows(), function(object, index){ 
				object.printerName = $('#cboPrinter :selected').val();
				return object ;
			});
			
			postJSONObject("PRD_S03_print", param, function(blob){
				printerName = bean.printerName;
				message.showMessage(bean);
				var isPrint = true;
				search(isPrint);
			});	
			prdS03Grid.resetFilter();
		}
			
}

function printPDF(){ 
			showLoading();
			setTimeout(function(){ hideLoading(); }, 4000);
			prdS03Grid.getGrid().getEditorLock().commitCurrentEdit();		
			var param = $.map(prdS03Grid.getFilteredRows(), function(object, index){ 
				object.printerName = $('#cboPrinter :selected').val();
				return object ;
			});
			
			$.ajax({
		        type: "POST",
		        url: 'PRD_S03_print_pdf.html',
		        'contentType': 'application/json',
		        'data': JSON.stringify(param),
		        'dataType': 'json'
		    }).done(function (data) {
		    	/*
		        var blob = new Blob([data]);
		        var link = document.createElement('a');
		        link.href = window.URL.createObjectURL(blob);
		        link.download = "Sample.pdf";
		        link.click();
		        */
		        
				hideLoading();
		        var $a = $( '<a />' ), url = URL.createObjectURL( "PRD_S03_PDF/"+data.response );
		        $a.attr({
		          'href' : url,
		          'download' : 'LotControl'+new Date()+'.pdf'
		        })
		        .trigger( 'click' );
		        URL.revokeObjectURL( url );
		        
		    }).error(function (data) {
				hideLoading();
		        var url = "PRD_S03_PDF.pdf?file="+data.responseText ;
		    	
		    	var downloadLink = document.createElement("a");
		    	downloadLink.href = url;
		    	downloadLink.download ='LotControl'+new Date()+'.pdf';
		    	downloadLink.target = "_blank";

		    	document.body.appendChild(downloadLink);
		    	downloadLink.click();
		    	document.body.removeChild(downloadLink);
		    	
//		        var url = "PRD_S03_PDF.html?file="+data.responseText ;
//		        w=window.open(url); 
//		        w.print(); 
//		        w.close(); 
		    });
}


function submitform() {
    var url = 'PRD_S03_print.html';
    
    prdS03Grid.getGrid().getEditorLock().commitCurrentEdit();		
	var param = $.map(prdS03Grid.getFilteredRows(), function(object, index){ 
		object.printerName = $('#cboPrinter :selected').val();
		return object ;
	});
	

    var data = $(JSON.stringify(param));

    $('<form enctype="application/json" action="'+url+'" method="POST">' + 
      '<input type="hidden" name="json" value="' + (data) + '">' +
      '</form>').submit();

//    $('#prdS03Form').attr('action', url);
//    $('#prdS03Form').attr('enctype', "application/json" );
//    $('#prdS03Form').attr('Content-type', "application/json" );
//    var data = JSON.stringify(param)
//    $('<input type="hidden" name="json"/>').val(param).appendTo('#prdS03Form');
//    $("#prdS03Form").submit();
//    document.getElementById("prdS03Form").submit();
}

function selectPrinter(){
		printerName = undefined;
		if(undefined == printerName){
			$("#printer").dialog("open");
		}
}

function getDataList(){
	postJSONObject("PRD_S03_search", getParam(), function(jsonList){ 
		fillData(jsonList);
		showGrid();
	});
}

function getParam(){
	var machineLineFrom = '';
	var machineLineTo   = '';
	if($('#cboMachineLineFrom :selected').val() != '--All--'){
		machineLineFrom = $('#cboMachineLineFrom :selected').val();
	}if($('#cboMachineLineTo :selected').val() != '--All--'){
		machineLineTo = $('#cboMachineLineTo :selected').val();
	}
	var param = {
		'wip' 			: $('#cboWip').val(),
		'startLotNo' 	: $('#txbStartLotNo').val(),
		'endLotNo' 		: $('#txbEndLotNo').val(),
		'machineFrom' 	: machineLineFrom,
		'machineTo' 	: machineLineTo,
		'dcPlanDateFrom': $('#txbDieCastDateFrom').val(),
		'dcPlanDateTo' 	: $('#txbDieCastDateTo').val(),
		'shift' 		: $('#cboShift').val(),
		'customerId' 	: $('#cboCustomer').val(),
		'partNo' 		: $('#txbPartNo').val(),
		'partName' 		: $('#txbPartName').val(),
		'printStatus' 	: $('#cboPrint').val()
	};
	return param;
}

function isValidMandatory(){
	var validate = [];
	errors = [];
	message.clear();
	if($.trim($('#txbStartLotNo').val()) != ''){
		validate.push('valid');
	}else{
		validate.push('invalid');
	}
	if($.trim($('#txbEndLotNo').val()) != ''){
		validate.push('valid');
	}else{
		validate.push('invalid');
	}
	if($.trim($('#txbDieCastDateFrom').val()) != ''){
		validate.push('valid');
	}else{
		validate.push('invalid');
	}
	if($.trim($('#txbDieCastDateTo').val()) != ''){
		validate.push('valid');
	}else{
		validate.push('invalid');
	}
	var index = $.inArray('valid', validate);
	var idx ;
	if(index != -1){
		if((index % 2) == 0){
			idx = index + 1 ;
		}else{
			idx = index - 1 ;
		}
		if(validate[idx] != 'valid'){
			if(idx == 0){
				errors.push({'code': 'err.cmm.001', 'arguments': ['Start Lot No']});
			}else if(idx == 1){
				errors.push({'code': 'err.cmm.001', 'arguments': ['End Lot No']});
			}else if(idx == 2){
				errors.push({'code': 'err.cmm.001', 'arguments': ['Date From']});
			}else if(idx == 3){
				errors.push({'code': 'err.cmm.001', 'arguments': ['Date To']});
			}
		}
	}else{
		errors.push({'code': 'err.cmm.001', 'arguments': ['Start Lot No']});
		errors.push({'code': 'err.cmm.001', 'arguments': ['End Lot No']});
		errors.push({'code': 'err.cmm.001', 'arguments': ['Date From']});
		errors.push({'code': 'err.cmm.001', 'arguments': ['Date To']});
	}
	if(errors.length > 0){
		message.setErrors(errors);
		return false;
	}
	return true;
}

/**
 * Get machine objects from the back end
 * Send machine objects to another methods to clear and change the values of machine list box
 * @param none
 * @returns none
 */
function getMachine(){
	clearOptions('#cboMachineLineFrom');
	clearOptions('#cboMachineLineTo');
	postJSONSync('PRD_S03_getMachine', {'param' : $('#cboWip').val()}, function(jsonList){ setMachine(jsonList); });
}

/**
 * Set machine objects to list box
 * Set options by options based on key and value
 * @param the collections of machine JSON objects
 */
function setMachine(list){
	$.each(list,function(index,object){
		machineFrom = document.getElementById('cboMachineLineFrom').options;
		machineTo   = document.getElementById('cboMachineLineTo').options;
		machineFrom[machineFrom.length] = new Option(object.machineName, object.machineNo);
		machineTo[machineTo.length]     = new Option(object.machineName, object.machineNo);
    });
}

/**
* Disable search form after clicking btnSearch
* @param  none
* @return none
*/
function disableSearchForm(){
	$("select#cboWip").attr("disabled", true);
	$("#txbStartLotNo").attr("disabled", true);
	$("#txbEndLotNo").attr("disabled", true);
	$("select#cboMachineLineFrom").attr("disabled", true);
	$("select#cboMachineLineTo").attr("disabled", true);
	$("#txbDieCastDateFrom").attr("disabled", true);
	$("#txbDieCastDateTo").attr("disabled", true);
	$("select#cboShift").attr("disabled", true);
	$("select#cboCustomer").attr("disabled", true);
	$("#txbPartNo").attr("disabled", true);
	$("#txbPartName").attr("disabled", true);
	$("select#cboPrint").attr("disabled", true);
	$("#txbDieCastDateFrom").datepicker("disable");
	$("#txbDieCastDateTo").datepicker("disable");
	}

/**
* Enable search form after clicking btnClear
* @param  none
* @return none
*/
function enableSearchForm(){
	$("select#cboWip").attr("disabled", false);
	$("#txbStartLotNo").attr("disabled", false);
	$("#txbEndLotNo").attr("disabled", false);
	$("select#cboMachineLineFrom").attr("disabled", false);
	$("select#cboMachineLineTo").attr("disabled", false);
	$("#txbDieCastDateFrom").attr("disabled", false);
	$("#txbDieCastDateTo").attr("disabled", false);
	$("select#cboShift").attr("disabled", false);
	$("select#cboCustomer").attr("disabled", false);
	$("#txbPartNo").attr("disabled", false);
	$("#txbPartName").attr("disabled", false);
	$("select#cboPrint").attr("disabled", false);
	$("#txbDieCastDateFrom").datepicker("enable");
	$("#txbDieCastDateTo").datepicker("enable");
}

/**
 * Clear machine list box 
 * @param none
 * @returns none
 */
function clearOptions(id){
	$(id).empty();
}

function createGrid(){
	options = $.extend( {}, common.SlickGrid.newGrid().getOptions(), {
		editable			: true,
	    showHeaderRow 		: true,
		headerRowHeight 	: 30
	});
	
	columns = [{
		id 			: "statusFlag",
		name 		: "",
		field 		: "statusFlag",
		cssClass 	: "cell-c",
		width 		: 40,
		resizable 	: true,
		formatter	: common.SlickGrid.Formatters.FlagFormatter ,
		filter		: common.SlickGrid.filterColumnsOptions.ClearFilterButton
	}, {
		id 			: "wip",
		name 		: "W/C",
		field 		: "wip",
		width 		: 60,
		resizable 	: true,
		sortable	: true,
		cssClass 	: "cell-r",
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "machineNo",
		name 		: "Machine",
		field 		: "machineNo",
		width 		: 100,
		resizable 	: true,
		sortable	: true,
		maxlength   : 30,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id 			: "dcPlanDate",
		name 		: "Date",
		field 		: "dcPlanDate",
		cssClass 	: "cell-c",
		width 		: 80,
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "shift",
		name 		: "Shift",
		field 		: "shift",
		width 		: 40,
		resizable 	: true,
		sortable	: true,
		cssClass 	: "cell-c",
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "workOrderNo",
		name 		: "W/O",
		field 		: "workOrderNo",
		width 		: 100,
		cssClass 	: "cell-l",
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id 			: "lotNo",
		name 		: "Lot No",
		field 		: "lotNo",
		width 		: 120,
		cssClass 	: "cell-l",
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "customerCode",
		name 		: "Customer",
		field 		: "customerCode",
		width 		: 100,
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id 			: "partNo",
		name 		: "Part No",
		field 		: "partNo",
		width 		: 150,
		cssClass 	: "cell-l",
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "partName",
		name 		: "Part Name",
		field 		: "partName",
		width 		: 180,
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "mold",
		name 		: "Mold No",
		field 		: "moldNo",
		width 		: 80,
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "quantity",
		name 		: "QTY",
		field 		: "quantity",
		width 		: 50,
		cssClass 	: "cell-r",
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id 			: "printingStatus",
		name 		: "Print",
		field 		: "printingStatus",
		width 		: 45,
		cssClass 	: "cell-c",
		resizable 	: true,
		sortable	: true,
		formatter	: common.SlickGrid.Formatters.YesNo,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	}, {
		id 			: "printDate",
		name 		: "Print Date",
		field 		: "printDate",
		width 		: 80,
		cssClass 	: "cell-c",
		resizable 	: true,
		sortable	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id 			: "deleteField",
		name 		: "Delete",
		field 		: "deleteField",
		width 		: 40,
		cssClass 	: "cell-c",
		resizable 	: true,
		formatter 	: common.SlickGrid.Formatters.DelFormatter,
		editor 		: common.SlickGrid.Editor.DelEditor
	}];
}

/**
 * Fill the grid to show data
 * @param  none
 * @return none
 */
function fillData(dataList){
	prdS03Grid = common.SlickGrid.newGrid();
	prdS03Grid = prdS03Grid.setOptions(options);
	prdS03Grid = prdS03Grid.setGrid( {
		"id"      : "prdS03Grid",
		"columns" : columns,
		"data" 	  : dataList
	});
	$('#record').text(dataList.length);
}
/**
 * Set initial property to screen
 * Hide the grid when the page is loaded
 * @param none
 * @returns none
 */
function hideGrid(){
	$('#gridHeader').hide();
	$('#prdS03Grid').hide();
	$('#prdS03Buttons').hide();
}

/**
 * Show the grid after clicking btnSearch
 * @param  none
 * @return none
 */
function showGrid(){
	$('#gridHeader').show();
	$('#prdS03Grid').show();
	$('#prdS03Buttons').show();
	$('#tdQtyTotal').text(sumQtyTotal());
	onRowCountChangedForSumQty();
}

function sentData(){
	prdS03Grid.getGrid().getEditorLock().commitCurrentEdit();	
	var printSelected = $('#cboPrinter').val();
	var optionPrintDate = $('#optionPrintDate').val();
	var optionPrintWorker = $('#optionPrintWorker').val();
	var optionPrintQtyOK = $('#optionPrintQtyOK').val();
	var optionPrintQtyNG = $('#optionPrintQtyNG').val();
	
	var processList = [];
	$("input[name='process']:checked").each(function() {
		processList.push($(this).val());
	});
	
			if($('#cboPrinter').val() != null && $('#cboPrinter :selected').text() != ' -- Please Select Printer -- '){
				var param = $.map(prdS03Grid.getFilteredRows(), function(object, index){ 
					object.printerName = printSelected;
					object.optionPrintDate = optionPrintDate;
					object.optionPrintWorker = optionPrintWorker;
					object.optionPrintQtyOK = optionPrintQtyOK;
					object.optionPrintQtyNG = optionPrintQtyNG;
					object.processList = processList;
					return object ;
				});
				showLoading();
				postJSONObject("PRD_S03_print", param, function(bean){
					printerName = bean.printerName;
					message.showMessage(bean); 
					var isPrint = true;
					search(isPrint);
				});
				$("#printer").dialog("close");
			}else{
				alert(message.getMessage('err.cmm.027'));
			}
			prdS03Grid.resetFilter();
}

function chkPrinter(){
	if($('#cboPrinter').val() == ' -- Please Select Printer -- '){
		selectedPrintNm = null;
	}
	return selectedPrintNm;
}

function createPrinter(){
	$("#printer").dialog({
		autoOpen: false,
		height: 400,
		width: 600,
		modal: true,
		buttons:{
			   "Print"  : function(){
				sentData();
			}, "Cancel" : function(){
				$(this).dialog("close");
			}
		}			
	});
}


function showProcessList(dataList){
	   var container = $('#processList');
	   container.html("");
	   
		$.each(dataList,function(index,object){
		   var inputs = container.find('input');
		   var id = inputs.length+1;

		   $('<input />', { type: 'checkbox', id: 'process'+id, name: 'process', value: object.wip }).appendTo(container);
		   $('<label />', { 'for': 'process'+id, text: object.wipName }).appendTo(container);
		   $('<BR />').appendTo(container);
	    });
}

