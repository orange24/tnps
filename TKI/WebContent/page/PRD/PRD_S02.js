/* Declare global variables for PRD_S02 screen */
var prdGrid ;
var recordFound ;
var columnpicker ;
var dcPlanDateFrom;
var dcPlanDateTo;
var wip ;
var machineId;
var shift ;
var customerId;
var reasonId;
var partNo;
var partName;
var generateLot;
var prdS02Form ;

$(function($){
	/* Creating form name */
	prdS02Form   = $('#prdS02Form');
	/* Create events */
	$('#cboWip').change(function(){ getMachine(); });
	$('#btnSearch').click(function(){ search(); });
	$('#btnExport').click(function(){ exportData(); });
	$('#btnGenerateWipLot').click(function(){ generateWipLot(); });
});

/**
 * Search die cast plan information after clicking btnSearch
 * @param none
 * @returns none
 */
function search(){
//	prdS02Form.submit();
	document.getElementById("prdS02Form").submit();
}

/**
 * Export the data to user
 * @param none
 * @returns none
 */
function exportData(){
	message.clear();
	errors = [];
	prdS02Form.attr("action","PRD_S02_export.html");
	downloadNotify($("<div title='"+ message.getMessage('downloadAlertTitle')+ "'>" + message.getMessage('downloadAlertContent')+"</div>"));
	prdS02Form.submit();
	prdS02Form.attr("action","PRD_S02_search.html");
}

/**
 * Generate work order and lot numbers
 * @param none
 * @returns none
 */
function generateWipLot(){
	if(confirm(message.getMessage('cfm.prd.001'))){
		prdS02Form.attr("action","PRD_S02_generate.html");
//		prdS02Form.submit();
		document.getElementById("prdS02Form").submit();
		prdS02Form.attr("action","PRD_S02_search.html");
	}
}

/**
 * Get machine objects from the back end
 * Send machine objects to another methods to clear and change the values of machine list box
 * @param none
 * @returns none
 */
function getMachine(){
	clearOptions('cboMachine');
	postJSONSync('PRD_S02_getMachine', {'param' : isNullWip() }, function(jsonList){ setMachine(jsonList); });
}
function isNullWip(){
	if($('#cboWip').val() == "--All--"){
		return null;
	}else{
		return $('#cboWip').val() ;
	}
}

/**
 * Set machine objects to list box
 * Set options by options based on key and value
 * @param the collections of machine JSON objects
 */
function setMachine(list){
	$.each(list,function(index,object){
		options = document.getElementById('cboMachine').options;
		options[options.length] = new Option(object.machineNo, object.machineId);
    });
}

/**
 * Set machine objects to list box
 * Set options by options based on key and value
 * @param the collections of machine JSON objects
 */
function setCustomer(list){
	$.each(list,function(index,object){
		options = document.getElementById('cboCustomer').options;
		options[options.length] = new Option(object.customerCode, object.customerId);
    });
}

/**
 * Clear machine list box 
 * @param none
 * @returns none
 */
function clearOptions(idx){
	var option = document.getElementById(idx);
	while (option.options.length > 1) {
		option.remove(1);
	}
}
