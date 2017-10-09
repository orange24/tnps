var customerGrid;
var customerColumns;
var customerData = [];
var cusCodeArray = [];
var unique_values  = {};
var list_of_values = [];
var btnSave;
var btnExport;
var cus_s01Form = $("#cus_s01Form");
var exportForm;
var validateField;
var countErrors = 0;
var errors = [];
var check;
$( function($) {
	exportForm = $("#exportForm");
	//data
	getCustomerData();
	//view
	createGrid();
	//event
	saveCustomer();
	exportCustomerData();
	
});
function exportCustomerData(){
	$("#btnExport").click(function() {
		if(customerGrid.getFilteredRows().length == 0){
			errors.push({'code': 'err.cmm.018', 'arguments': []});
			message.setErrors(errors);
			return false ;
		}else{
			exportForm.empty();
			var hd = $(customerGrid.getGrid().getHeaderRow());
			hd.find(".slick-headerrow-column").each(function(idx,obj){
				var  o = customerGrid.getGrid().getColumns()[idx];
				var  ip = $(this).find("input,select");
				if( ip.exists() && ip.val() ){
					exportForm.append($('<input type="hidden" name="' + o.field + '" value="' + ip.val() + '"/>'));
				}
			});
			downloadNotify($("<div title='"+message.getMessage("downloadAlertContent")+"'/>'>"+message.getMessage("downloadAlertContent")+"</div>"));
			exportForm.submit();
		}		
	});
}

function saveCustomer(){
	var successFn = function(bean){
		message.showMessage(bean);
		customerGrid.reloadData();
	};
	
	$("#btnSave").click(function() {
		customerGrid.getGrid().getEditorLock().commitCurrentEdit();
		customerGrid.resetFilter();
		message.clear();
		errors = [];
		//cusCodeArray.lenght = 0 ;
		cusCodeArray = [];
		unique_values  = {};
//		if(confirm(message.getMessage("cfm.cmm.001"))){
			var customerCodeKey = {};
			$.each(customerGrid.getDataView().getItems(), function(i, n) {
				var notExists = $(customerCodeKey[n.customerCode]).exists();
				if(notExists)
					customerCodeKey[n.customerCode] = i;
			});
			var datas = $.map(customerGrid.getDataView().getItems(), function(n, i) {
				if(n.statusFlag != 'd'){
					cusCodeArray.push(n.customerCode);
					var lengthOfArray = cusCodeArray.length;
				}
				
				if( n.statusFlag != null ){ 				
					//Line Error
					if(!requiredFieldValidator(n.customerCode) && n.statusFlag != 'd'){
						errors.push({"code": "err.cmm.002", "arguments": [i+1,"Customer Code"]});
					} 
					if(!requiredFieldValidator(n.customerName) && n.statusFlag != 'd'){	
						errors.push({"code": "err.cmm.002", "arguments": [i+1,"Customer Name"]});
					}
					return n; 
				}	
			});
			
			$.each(cusCodeArray, function(index, object) {
				 if (!unique_values[object]){
				      unique_values[object] = true;
				      list_of_values.push(object);
				 }else{
				        errors.push({"code": "err.cmm.020", "arguments": [index+1,"Customer Code"]});
				 }
			});
			message.setErrors(errors);
			//Convert dataGrid into data object			
			if(message.isNoError() && datas != ""){
				 if(confirm(message.getMessage("cfm.cmm.001"))){
				postJSONObject("CUS_S01_save", datas, successFn);	
				 }
			}
//		};
	});
}

function getCustomerData(){
	customerData = { url:"CUS_S01_init.html",
			param: {}
	};
}

function requiredFieldValidator(value) {
	if (value == null || value == undefined || !value.length) {
		return false;
	}
	else {
		return  true;
	}
}

function createGrid(){
	var editOptions = $.extend( {}, common.SlickGrid.newGrid().getOptions(), {
		showHeaderRow 	: true,
		headerRowHeight : 30,
		enableAddRow 	: false
	});// Extend default options from common grid
	
	// <!-- Customer Grid -->
	customerColumns = [{
		id 			: "statusFlag",
		name 		: "",
		field 		: "statusFlag",
		width 		: 30,
		resizable 	: true,
		formatter	: common.SlickGrid.Formatters.FlagFormatter,
		filter		: common.SlickGrid.filterColumnsOptions.ClearFilterButton,
		sortable	: true
	},{
		id 			: "rowNo",
		name 		: "No.",
		field		: "rowNo",
		width 		: 40,
		resizable 	: true,
		formatter 	: common.SlickGrid.Formatters.RowNumFormatter,
		sortable	: true
	},{
		id 			: "customerId",
		name 		: "Customer ID",
		field 		: "customerId",
		width 		: 90,
		resizable 	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	: true 
	},{
		id 			: "customerCode",
		name 		: "Customer Code",
		field 		: "customerCode",
		width 		: 130,
		resizable 	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	: true
	},{
		id 			: "customerName",
		name 		: "Customer Name",
		field 		: "customerName",
		width 		: 150,
		resizable 	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	: true
	},{
		id 			: "vendorCode",
		name 		: "Vendor Code",
		field 		: "vendorCode",		
		width 		: 110,
		headerCssClass : "editable-column",
		resizable 	: true,
		editor 		: common.SlickGrid.Editor.TextEditor,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	: true,
		maxlength 	: 30
	},{
		id 			: "remark",
		name 		: "Remark",
		field 		: "remark",
		width 		: 150,
		headerCssClass : "editable-column",
		resizable 	: true,
		editor 		: common.SlickGrid.Editor.TextEditor,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	: true,
		maxlength 	: 500
	},{
		id 			: "barcodeQty",
		name 		: "Barcode Qty",
		field 		: "isBarcodeQty",
		width 		: 110,
		cssClass 	: "cell-c",
		resizable 	: true,
		sortable	: true,
		headerCssClass: "editable-column",
		formatter	: common.SlickGrid.Formatters.YesNo,
		filter 		: common.SlickGrid.filterColumnsOptions.Checkbox,
		editor 		: common.SlickGrid.Editor.Checkbox
	}, {
		id 			: "createDate",
		name 		: "Create Date",
		field 		: "createDate",
		width 		: 120,
		cssClass 	: "cell-c",
		resizable 	: true,
		formatter	: common.SlickGrid.Formatters.DateTimeFormatter,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	: true 
	},{
		id 			: "createBy",
		name 		: "Create By",
		field 		: "createBy",
		width 		: 120,
		cssClass 	: "cell-l",
		resizable 	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	: true 
	}, {
		id 			: "lastUpdate",
		name 		: "Update Date",
		field 		: "lastUpdate",
		width 		: 120,
		cssClass 	: "cell-c",
		resizable 	: true,
		formatter 	: common.SlickGrid.Formatters.DateTimeFormatter,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	: true 
	},{
		id 			: "updateBy",
		name 		: "Update By",
		field 		: "updateBy",
		width 		: 120,
		cssClass 	: "cell-l",
		resizable 	: true,
		filter 		: common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	: true 
	},{
		id 			: "deleteField",
		name 		: "Delete",
		field 		: "deleteField",
		width 		: 60,
		cssClass 	: "cell-c",
		resizable 	: true,
		formatter 	: common.SlickGrid.Formatters.DelFormatter,
		editor 		: common.SlickGrid.Editor.DelEditor,
		sortable	: true 
	}];
	
	customerGrid = common.SlickGrid.newGrid();
	customerGrid = customerGrid.setOptions(editOptions);
	customerGrid = customerGrid.setGrid( {
		"id" 		: 	"customerGrid",
		"columns" 	: 	customerColumns,
		"data" 		: 	customerData
	});
}

 