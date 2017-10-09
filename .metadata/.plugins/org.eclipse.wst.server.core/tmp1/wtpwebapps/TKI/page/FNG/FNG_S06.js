var lotSeqGrid;
var columns;
var dataView;
var errors = [];
var fngForm;
var txtLotNoData;
var txtLotNo;
var txtProcess;
var selFGNo;
var selCustomer;
var txtSnp;
var txtQty;
var txtVendorcode;
var txtVendorFgNo;
var btnPreview;
var normalLabel;
var specialLabel;
var btnSavePrint;
var customerIdByAuto;
var fgIdByAuto;
var hideTagId,hideLotNo,hideCustomerId,hideFgId,hideSnp,hideQty,hideLabelType,hideVendorCode,hideFgNo;
var printDialog;
var selPrinter;
var cancelDialog;
var btnPrintDialog;
var btnClear;
var mold;
var moldNo;

$(function(){
	//declaration
	fngForm 		= $('#fngForm');
	txtLotNo 		= $('#lotNo');
	txtProcess 		= $('#wip');
	mold	 		= $('#mold');
	moldNo	 		= $('#moldNo');
	selCustomer 	= $('#customerId');
	selFGNo			= $('#fgId');
	txtSnp 			= $('#snp_wip');
	txtQty 			= $('#qty');
	normalLabel 	= $('#NormalLabelType');
	specialLabel	= $('#SpecialLabelType');
	txtVendorcode 	= $('#vendorcode');
	txtVendorFgNo 	= $('#vendorFgNo');
	btnPreview 		= $('#searchBtn');
	btnSavePrint 	= $('#btnSavePrint');
	printDialog 	= $('#printDialog');
	selPrinter 		= $('#cboPrinter');
	cancelDialog 	= $('#btnCancelDialog');
	btnPrintDialog	= $('#btnPrintDialog');
	btnClear		= $('#btnClear');
	txtPrintQtyRemain = $('#printQtyRemain');
	
	//hidden field
	hideTagId 		= $('#tagId');
	hideLotNo  		= $('#hideLotNo');
	hideCustomerId 	= $('#hideCustomerId');
	hideFgId  		= $('#hideFgId');
	hideSnp  		= $('#hideSnp');
	hideQty  		= $('#hideQty');
	hideLabelType  	= $('#hideLabelType');
	hideVendorCode  = $('#hideVendorCode');
	hideVendorFgNo 	= $('#hideVendorFgNo');
	
	hideGrid();
	//data
	getLotNoAutoComplete();
	
	//view	
	printDialog.hide();
	txtLotNo.focus();
	
	//event
	btnPreview.click(preview);
	btnSavePrint.click(saveAndPrint);
	cancelDialog.click(closePrintDialog);
	btnPrintDialog.click(printLabel);
	btnClear.click(clearField);
});
function clearField(){
	location.href = "FNG_S06.html";
}
function printLabel(){
	$('#printerName').val(selPrinter.val());
	var printName = $('#cboPrinter :selected').text();
	if($('#cboPrinter :selected').val() == 'null'){
		printName = null;
		alert(message.getMessage('err.cmm.027'));
	}else{
		closePrintDialog();
		sendAlldata();
	}
}

function sendAlldata(){
	var datas = lotSeqGrid.getDataView().getItems();
	var selectedRowsNum = lotSeqGrid.getGrid().getSelectedRows();

	for(var i=0; i<selectedRowsNum.length; i++){
		datas[selectedRowsNum[i]].flagPrint = '1';
	}
	var params = {	"printerName"		:	$('#printerName').val(),
					"tagId"				:	hideTagId.val(),
					"lotNo" 			: 	hideLotNo.val(),
					"wip"				:	txtProcess.val(),
					"customerId" 		:	hideCustomerId.val(),
					"fgId" 				:	hideFgId.val(),
					"snp_wip"			:	hideSnp.val(),
					"qty" 				:	hideQty.val(),
					"labelType"			:	hideLabelType.val(),
					"vendorCode"		: 	getVendorIsEmpty(hideVendorCode),
					"vendorFgNo"		: 	getVendorIsEmpty(hideVendorFgNo),
					"lotSequenceList"	: 	datas
				};
	var successFn = function(bean){
		//get detail of lotNo again
		getDataByLot(params);

		message.showMessage(bean);
		//hideTagId.val(bean.tagId);
		if(isDeleteAllRows()){
			lotSeqGrid.reloadData();
		}else{
			preview();
		}
	};
	postJSONObject("FNG_S06_printLabel",params,successFn);	
}
function getVendorIsEmpty(arg){
	if(arg.val() == ""){
		return null;
	}else{
		return arg.val();
	}
}
function closePrintDialog(){
	printDialog.dialog( "close" );
}
function isDeleteAllRows(){
	var count = 0;
	var lengthOfGrid = lotSeqGrid.getDataView().getLength();
	$.map(lotSeqGrid.getDataView().getItems(), function(n, i) {
		if(n.statusFlag == 'd'){
			count++;
		}
	});
	if(count == lengthOfGrid){
		//delete all
		return true;
	}else{
		//not delete all
		return false;
	}
}
function isUncheckedAllRows(){
	//var datas = lotSeqGrid.getDataView().getItems();
	var selectedRowsNum = lotSeqGrid.getGrid().getSelectedRows();
	if(0 == selectedRowsNum.length){
		return true;
	}else{
		return false;
	}
}
function saveAndPrint(){
	lotSeqGrid.getGrid().getEditorLock().commitCurrentEdit();
	message.clear();
	errors = [];
	
	var isDelete = isDeleteAllRows();
	var sumQty = 0;
	$.map(lotSeqGrid.getDataView().getItems(), function(n, i) {
		if(n.statusFlag != 'd' && !isDelete){
			sumQty += (n.lotSeqQty*1);
		}
	});
	if(sumQty != txtQty.val() && !isDelete){
		errors.push({"code": "err.fng.007", "arguments": []});
		message.setErrors(errors);
	}

	if(message.isNoError()){//have a null.
		if(confirm(message.getMessage("cfm.cmm.006"))){
			if(isDelete){
				sendAlldata();
			}else if(isUncheckedAllRows()){
				sendAlldata();
			}else{
				var chk =  chkSelectedPrinter();
				if(chk){
					getAllPrinter();
					printDialog.dialog({ 
						height: 150,
						width: 400,
						modal: true
					});
				}else{
					sendAlldata();
				}
			}
		}
	}
}
function chkSelectedPrinter(){
	if($('#printerName').val() == null || $('#printerName').val() == '' || $('#printerName').val() == 'null'){
		return true;
	}else{
		return false;
	}
}
function getAllPrinter(){
	postJSONSync("boxPrinterName",{},function(list){
		selPrinter.empty();
		$.each(list,function(index,object){
			options = document.getElementById('cboPrinter').options;
			options[options.length] = new Option(object.displayName, object.printerName);
	    });
	});
}
function preview(){
	//set upper case 
	var lotNoTmp = txtLotNo.val().toUpperCase();
	txtLotNo.val(lotNoTmp);
	
	validateBeforePreview();
	if(errors.length == 0){
		$('#itemInformationGrid').show();
		btnSavePrint.show();
		createGrid();
		
		//set hidden value when click preview button
		hideLotNo.val(lotNoTmp);
		hideCustomerId.val(selCustomer.val());
		hideFgId.val(selFGNo.val());
		hideSnp.val(txtSnp.val());
		hideQty.val(txtQty.val());
		hideLabelType.val(labelTypeSelected());
		hideVendorCode.val(txtVendorcode.val());
		hideVendorFgNo.val(txtVendorFgNo.val());
		
	}
}
function labelTypeSelected(){
	if(specialLabel.is(':checked')  === true){
		return specialLabel.val();
	}else{
		return normalLabel.val();
	}
}
function validateBeforePreview(){
	message.clear();
	errors = [];
	if(txtLotNo.val() == null || txtLotNo.val() == "" ){
		errors.push({'code': 'err.cmm.001', 'arguments': ["Lot No."]});	
	}
	if(selFGNo.val()== null || selFGNo.val() == ""){
		errors.push({'code': 'err.cmm.001', 'arguments': ["FG No : FG Name"]});	
	}
	if(txtSnp.val() == null || txtSnp.val() == "" ){
		errors.push({'code': 'err.cmm.001', 'arguments': ["SNP"]});	
	}else if((txtSnp.val()*1) <= 0){
		errors.push({"code": "err.cmm.014", "arguments": ["SNP"]});
	}
	if(txtQty.val() == null || txtQty.val() == "" ){
		errors.push({'code': 'err.cmm.001', 'arguments': ["Qty"]});	
	}else if((txtQty.val()*1) <= 0){
		errors.push({"code": "err.cmm.014", "arguments": ["QTY"]});
	}
	if(specialLabel.is(':checked')  === true){
		if(txtVendorcode.val() == null || txtVendorcode.val() == "" ){
			errors.push({'code': 'err.cmm.001', 'arguments': ["Vendor Code"]});	
		}
		if(txtVendorFgNo.val() == null || txtVendorFgNo.val() == ""){
			errors.push({'code': 'err.cmm.001', 'arguments': ["Vendor FG No."]});	
		}
	}
	message.setErrors(errors);
}
function getLotNoAutoComplete(){
	txtLotNoData = {				
			source: function( request, response ) {
				var lotNo = $('#lotNo').val();
				if(lotNo.length==14){
					var params 	= { "lotNo": lotNo };
					getDataByLot(params);
					hideGrid();
				}else{
					var params = { "lotNo": lotNo};
					getJSON("FNG_S06_getLotNo", params, 
							function(result){
								response($.map(result,function(item){
									return { 
										label: item.lotNo,
										value: item.lotNo
									};
							}));
					});
				}
			}
			,minLength	: 1,
			delay		: 1000,
			select		: function (event, ui) {
				detailSelection(event, ui);
	        }
	};
	txtLotNo.autocomplete(txtLotNoData).change(detailSelection);
}

function chkLabelType(val){
	if(val == "S"){
		specialLabel.click();
	}else{
		normalLabel.click();
	}
}

function getDataByLot(params){
	getJSON("FNG_S06_getDetailByLotNo", params, 
			function(result){
					if(result != null){
						hideTagId.val(result.tagId);
						txtProcess.val(result.wip);
						mold.html(result.moldName);
						moldNo.html(result.moldNo);
						txtSnp.val(result.snp_wip);
						txtVendorcode.val(result.vendorCode);
						txtVendorFgNo.val(result.vendorFgNo);
						txtQty.val(result.qty);
						customerIdByAuto = result.customerId;
						fgIdByAuto = result.fgId;	
						txtPrintQtyRemain.val(result.printQtyRemain);
						
						getCustomerData({});
						getDataForFGPart({"customerId":selCustomer.val()});
						selCustomer.change(function(){
							getDataForFGPart({"customerId":selCustomer.val()});
						});
						chkLabelType(result.labelType);
						if(txtVendorFgNo.val() != "" ){
							specialLabel.click();
						}
					}else{
						//clear all data of input field
						hideTagId.val("");
						txtProcess.val("");
						txtSnp.val("");
						txtVendorcode.val("");
						txtVendorFgNo.val("");
						txtQty.val("");
						txtPrintQtyRemain.val("");
						mold.val("");
						
						hideLotNo.val("");
						hideCustomerId.val("");
						hideFgId.val("");
						hideSnp.val("");
						hideQty.val("");
						hideLabelType.val("");
						hideVendorCode.val("");
						hideVendorFgNo.val("");
						mold.html("");
						moldNo.html("");
						
						selCustomer.empty();
						selFGNo.empty();
						normalLabel.click();
						
						hideGrid();
					}
			}
	);
}
function detailSelection(event, ui){
	var lotNo;
	if(ui != undefined){
		lotNo = ui.item.value;
	}else{
		lotNo = txtLotNo.val();
	}
	var params 	= { "lotNo": lotNo };
	getDataByLot(params);
	hideGrid();
}
function getCustomerData(params){
	postJSONSync("FNG_S06_boxCustomer",params,function(result){
		selCustomer.empty();
			$.each(result,function(val, text){
				selCustomer.append( $("<option></option>").val(val).html(text) );
			});	
			selCustomer.val(customerIdByAuto);
	});
}
function getDataForFGPart(params){
	postJSONSync("boxFgNoNameByCustomer",params,function(result){
		selFGNo.empty();
			$.each(result,function(val, text){
				selFGNo.append( $("<option></option>").val(text.fgId).html(text.fg));
			});
			selFGNo.val(fgIdByAuto);
	});
}
function validateLotNo(e){
	var keyCode = (e.keyCode || e.which);
	var element = $(e.target || e.srcElement);
	var value   = txtLotNo.val();
	
	if( value.length > 13 ) {// check length again
		txtSnp.blur();
		if( /[a-zA-Z0-9]{9}[-][0-9]{4}[-][0-9]{4}/g.test(value) ) {
			// <!-- For deactivation 'autocomplete' event. -->
			element.blur();
		} else {
			element.val("").focus();
			alert("The system does not received data, Please scan barcode in this Line again.");				
		};
	}
	return true;
}

function hideGrid(){
	$('#itemInformationGrid').hide();
	btnSavePrint.hide();
}

function getLotSeqData(){
	var params = {"lotNo":txtLotNo.val(), "tagId":hideTagId.val(), "wipName":txtProcess.val(), "customerId":selCustomer.val(), "fgId":selFGNo.val(), "snp_wip":txtSnp.val(), "qty":txtQty.val() }; 
	var data = postJSONObject("FNG_S06_preview", params, 
			function(result){
				lotSeqGrid.getDataView().setItems(result);
				document.getElementById('slickGridChkAll').click();
	});
	return data;
}

function createGrid(){
	var checkboxSelector = new Slick.CheckboxSelectColumn( {
		cssClass 		: "slick-cell-checkboxsel"
		,width 			: 60
		,headerCssClass : "print-column"
	});

	columns = [
	 {
		id 				: "statusFlag",
		name 			: "",
		field 			: "statusFlag",
		width 			: 30,
		resizable 		: true,
		formatter		: common.SlickGrid.Formatters.FlagFormatter,
		filter			: common.SlickGrid.filterColumnsOptions.ClearFilterButton,
		sortable		: true
	},
	    checkboxSelector.getColumnDefinition()
	 , {
		id 				: "seq",
		name 			: "Seq.",
		field 			: "seq",
		width 			: 60,
		cssClass 		: "cell-c",
		resizable 		: true,
		sortable 		: true
	}, {
		id 				: "lotSeqNo",
		name 			: "Lot Seq. No.",
		field 			: "lotSeqNo",
		width 			: 150,
		resizable 		: true,
		sortable 		: true
	}, {
		id 				: "lotSeqQty",
		name 			: "Qty",
		field 			: "lotSeqQty",
		width 			: 80,
		cssClass 		: "cell-r",
		headerCssClass 	: "editable-column required-column",
		resizable 		: true,
		sortable 		: true,
		editor			: IntegerEditorFn
	},{
		id 				: "printStatus",
		name 			: "Print Status",
		field 			: "printStatus",
		width 			: 85,
		cssClass 		: "cell-c",
		resizable 		: true,
		sortable 		: true,
		formatter 		: common.SlickGrid.Formatters.YesNo
	},{
		id 				: "deleteField",
		name 			: "Delete",
		field 			: "deleteField",
		width 			: 50,
		cssClass 		: "cell-c",
		resizable 		: true,
		formatter 		: DelFormatterFn,
		editor 			: DelEditorFn
	}];

	var options = $.extend( {}, common.SlickGrid.newGrid().getOptions(), {
		showHeaderRow : true,
		headerRowHeight : 30,
		enableAddRow : true
	});// Extend default options from common grid
	
	lotSeqGrid = common.SlickGrid.newGrid();
	lotSeqGrid = lotSeqGrid.setOptions(options);
	lotSeqGrid = lotSeqGrid.setGrid( {
		"id" : "itemInformationGrid",
		"columns" : columns,
		"check" : "<input type='checkbox' name='chkPrint' />",
		"data" : getLotSeqData(),
		"key" : "seq"
	});
	lotSeqGrid.setSelectionModel(new Slick.RowSelectionModel( {
		selectActiveRow : false
	}));
	lotSeqGrid.registerPlugin(checkboxSelector);
	dataView = lotSeqGrid.getDataView();
	lotSeqGrid.getGrid().onAddNewRow.subscribe(function(e, args){
		var item = args.item;
		dataView.beginUpdate();
		item.lotSeqNo = txtLotNo.val()+"-1" +$.strPad(item.seq, 3);
		item.flagPrint = 0;
		item.printStatus = 0;
		dataView.updateItem(item.seq, item);
		dataView.endUpdate();		
	});
}

function IntegerEditorFn(args) {
	var $input;
	var defaultValue;
	var scope = this;

	this.init = function() {
		if(args.item.isReadOnly == 1 || args.item.isReadOnly == "1"){
			$input = $("<INPUT type=text class='editor-text' disabled='true' />");
		}else{
			$input = $("<INPUT type=text class='editor-text' />");
		}

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

		$input.appendTo(args.container);
		$input.focus().select();
		if (args.column.maxlength) {
			$input.attr("maxlength", args.column.maxlength);
		}
	};

	this.destroy = function() {
		$input.remove();
	};

	this.focus = function() {
		$input.focus();
	};

	this.loadValue = function(item) {
		defaultValue = item[args.column.field];
		$input.val(defaultValue);
		$input[0].defaultValue = defaultValue;
		$input.select();
	};

	this.serializeValue = function() {
		return parseInt($input.val(), 10) || 0;
	};

	this.applyValue = function(item, state) {
		item[args.column.field] = state;
	};

	this.isValueChanged = function() {
		return (!($input.val() == "" && defaultValue == null)) && ($input.val() != defaultValue);
	};

	this.validate = function() {
		if (args.column.validator) {
			var validationResults = args.column.validator($input.val(), args);
			if (!validationResults.valid) {
				return validationResults;
			}
		}

		if (isNaN($input.val())) {
			return {
				valid : false,
				msg : "Please enter a valid integer"
			};
		}

		return {
			valid : true,
			msg : null
		};
	};

	this.init();
};

function DelFormatterFn(row, cell, value, columnDef, dataContext) {
	var css;
	if(dataContext.isReadOnly == 1 || dataContext.isReadOnly == "1"){
		css = "";
	}else{
		if(dataContext.statusFlag == "d"){
			css = "checkout-icon pointer";
		}else{
			css = "delete-row-icon pointer";
		}
	}
	return "<span class='" + css + "'></span>";
}

function DelEditorFn(args){
	var $input;
	var defaultValue;
	var scope = this;
	var grid = args.grid;
	var item = args.item;
	var valueChanged = false;
	this.init = function() {
		$input = $("<button/>").appendTo(args.container);
		$input.bind("click", function(e) {
			var rc = grid.getActiveCell();
			item.flagOrg = (item.flagOrg == undefined) ? item.statusFlag : item.flagOrg;
			item.statusFlag = (item.statusFlag == 'd') ? (item.flagOrg == 'd' ? '' : item.flagOrg) : 'd';
			
			if (item.statusFlag == 'd') {
				$(this).addClass("checkout-icon").removeClass("delete-row-icon");
				$(grid.getActiveCellNode()).parent().addClass("line-through");
				$input.remove();
			} else {
				$(this).addClass("delete-row-icon").remove("checkout-icon");
				$(this).parent().removeClass("line-through");
				$(grid.getActiveCellNode()).parent().removeClass("line-through");
			}

			grid.updateRow(rc.row);
			grid.getEditorLock().commitCurrentEdit();//after click will change effect immediately
				valueChanged = true;
				grid.gotoCell(rc.row, rc.cell);

			}).addClass(item.statusFlag == 'd' ? "checkout-icon pointer" : "delete-row-icon pointer").focus()
				.select();

		$p = $(this).parent();
		$input.css("width", $p.css("width")).css("height", $p.css("height"));
		$input.css("outline", "0");
		$input.css("padding", "0");
		$input.css("border", "0");

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
		$input.remove();
	};
	this.focus = function() {
		$input.focus();
	};
	this.getValue = function() {
		return item.statusFlag;
	};
	this.setValue = function(val) {
	};
	this.loadValue = function(item) {
		defaultValue = item.isReadOnly;
		if(defaultValue == 1 || defaultValue == "1"){
			$input.removeClass("delete-row-icon");
			$input.removeClass("pointer");
			$input.remove();
		}
		defaultValue = (item && item[args.column.field]) || "";
	};
	this.serializeValue = function() {
		return item.statusFlag;
	};
	this.applyValue = function(item, state) {
		item[args.column.field] = state;
	};
	this.isValueChanged = function() {
		return item.statusFlag ? true : false;
	};
	this.validate = function() {
		return {
			valid : true,
			msg : null
		};
	};
	this.init();
}