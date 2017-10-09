var fgpartGrid;
var partGrid;
var fgpartColumns;
var fgNo;
var fgName;
var fglinenum;
var partlinenum;
var customerNameSel;
var fgpartData     = [];
var partData       = [];
var errors         = [];
var fgarray        = [];
var partnoarray    = [];
var list_of_values = [];
var showMessage    = [];
var list_of_partNo = [];
var unique_values  = {};
var unique_partNo  = {};

$(function($){	
  //data
   getFgNoNameList();
   getFgPartMappingData();
   //view
   createFgGrid();
   $("#searchGrid").hide();
   //event
   searchFgPartMapping();
   saveFgPartMapping();
});

function getFgPartMappingData(){
	customerNameSel = $("#customerNameSel");
	fgNo            = $("#fgNo");
	fgName          = $("#fgName");
	fgpartData      = { url : "PRT_S02_searchList.html",
	param           : {
    customerId      : customerNameSel.val(),
	fgNo            : fgNo.val(),
	fgName          : fgName.val()
		}
	};
}

/*create FgGrid*/
function  createFgGrid(){
  var editOptions    = $.extend({},common.SlickGrid.newGrid().getOptions(),{
		showHeaderRow      : true,
		headerRowHeight    : 30,
	    enableAddRow       : true
	  });// Extend default options from common grid
	// <!-- Customer Grid -->
  fgpartColumns = [{
	id             : "statusFlag",
	name           : "",
	field          : "statusFlag",
	width          : 40,
	resizable      : true,
	formatter      : common.SlickGrid.Formatters.FlagFormatter,
	filter		: common.SlickGrid.filterColumnsOptions.ClearFilterButton
  },{
	id 			   : "rowNo",
	name 		   : "No.",
	field		   : "rowNo",
	width 		   : 40,
	resizable 	   : true,
	formatter 	   : common.SlickGrid.Formatters.RowNumFormatter,
	sortable	   : true
 },{
	id             : "customerCode",
	name           : "Customer",
	field          : "customerCode",
	width          : 110,
	resizable      : true,
	sortable       : true,
	filter         : common.SlickGrid.filterColumnsOptions.Fulltext
 },{
	id             : "fg",
	name           : "FG No. : FG Name",
	field          : "fg",
	width          : 300,
	headerCssClass : "editable-column required-column",
	resizable      : true,
	sortable       : true,
	filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
	editor         : common.SlickGrid.Editor.DropDownEditor,
	editorArgs     : {
		                   dataList : getFgNoNameList(),
		                    value   : "fgId",
		                    label   : "fg"
		              }
  },{
	id             : "partNo",
	name           : "Part No.",
	field          : "partNo",
	width          : 180,
	headerCssClass : "editable-column required-column",
	resizable      : true,
	sortable       : true,
	filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
	editor         : common.SlickGrid.Editor.DialogEditor,
	editorArgs     : {
	dialogContent  :   $("#partDialog"),
	setFields      :   ["partId","partNo", "partName","SNP(WIP)","Material"],
	width          :    700,
	height         :    500
	      }
  },{
	id             : "partName",
	name           : "Part Name",
	field          : "partName",
	width          : 250,
	resizable      : true,
	sortable       : true,
	filter         : common.SlickGrid.filterColumnsOptions.Fulltext
  },{
	id             : "deleteField",
	name           : "Delete",
	field          : "deleteField",
	width          : 50,
	cssClass       : "cell-c",
	resizable      : true,
	formatter      : common.SlickGrid.Formatters.DelFormatter,
	editor         : common.SlickGrid.Editor.DelEditor
  }];
	fgpartGrid     = common.SlickGrid.newGrid();
	fgpartGrid     = fgpartGrid.setOptions(editOptions);
	fgpartGrid     = fgpartGrid.setGrid({
		"id"       : "fgpartGrid",
		"columns"  : fgpartColumns,
		"data"     : fgpartData,
		"key"		: "fgPart"
    });
	createCellChange();
}

/* create cell change  */
function createCellChange(){ 
	dataView = fgpartGrid.getDataView();
	fgpartGrid.getGrid().onBeforeEditCell.subscribe(function(e,args){
		message.clear();
		errors.length    = 0;
		dataView.getLength();
		var selectedfgId = "";
	    var cell         = args.cell;
			 if(cell == args.grid.getColumnIndex("partNo")){ 
				 var typecheck   = typeof(args.item);
					if(typecheck == 'undefined'){
						errors.push({"code": "err.prt.001","arguments": []});
						message.setErrors(errors);
						return false;
					}else{
						if(args.item.fgId == ""){
							errors.push({"code": "err.prt.001","arguments": []});
							message.setErrors(errors);
							return false;
						}else{
							selectedfgId = args.item.fgId;
							createPartGrid(selectedfgId);
						}
					}
			 }
	 	});
	
	fgpartGrid.getGrid().onAddNewRow.subscribe(function(e,args){
		setCustomerCodeToGrid(args,dataView);
		});
	fgpartGrid.getGrid().onCellChange.subscribe(function (e, args) {
		setCustomerCodeToGrid(args,dataView);
	  });
}

function setCustomerCodeToGrid(args,dataView){
	 var item = args.item;
	 item.customerCode =  $('#customerNameSel :selected').text();
	 dataView.updateItem(item.fgPart, item);
	 dataView.endUpdate();	
}


/*create PartGrid*/
function createPartGrid(selectedfgId){
  partColumns = [{
	 id        : "partId",
	 field     : "partId",
	 width     : 30,
     resizable : false,
     formatter : hideFormatter,
     filter		: common.SlickGrid.filterColumnsOptions.ClearFilterButton
 },{
     id        : "partNo",
     name      : "Part No.",
	 field     : "partNo",
	 width     :  120,
	 resizable : true,
	 sortable	: true,
     filter    : common.SlickGrid.filterColumnsOptions.Fulltext
 },{
     id        : "partName",
	 name      : "Part Name",
	 field     : "partName",
	 width     : 120,
     resizable : true,
     sortable	: true,
     filter    : common.SlickGrid.filterColumnsOptions.Fulltext
  },{
	  id        : "materialId",
	  name      : "SNP(WIP)",
	  field     : "materialId",
	  cssClass  : "cell-r",
	  width     : 120,
	  resizable : true,
	  sortable	 : true,
	  filter    : common.SlickGrid.filterColumnsOptions.Fulltext
  },{
	  id        : "smaterialName",
	  name      : "Material",
	  field     : "smaterialName",
	  width     :  120,
	  resizable : true,
	  sortable	 : true,
      filter    : common.SlickGrid.filterColumnsOptions.Fulltext
  }];
      partGrid  = common.SlickGrid.newGrid();
	  partGrid  = partGrid.setGrid({
		  "id"      	: "partGrid",
		  "columns"  	: partColumns,
		  "data"     	: { 
			  				"url"      	:   "PRT_S02_selectPartList.html",
			  				"param"    	: {
			  				"fgId" 		: selectedfgId
			  				}
		  }
    });
}
function  searchFgPartMapping(){
 $("[id=btnSearch]").click(function(){
	message.clear();
	errors.length    = 0;
	prtS02Form      = $("#prtS02Form");
	customerNameSel = $("#customerNameSel");
	if(customerNameSel.val() == -2147483648){
		errors.push({"code": "err.cmm.001", "arguments": ["Customer"]});
		message.setErrors(errors);
		return;
    }else{
		$("#searchGrid").show();
	    getFgPartMappingData();
		createFgGrid();
	  }
   });
}
function saveFgPartMapping(){
 $("[id=btnSave]").click(function(){
	fgpartGrid.resetFilter();
	fgpartGrid.getGrid().getEditorLock().commitCurrentEdit();
	
    message.clear();
	unique_values         = {};
    unique_partNo         = {};
    showMessage.length    = 0;
    list_of_values.length = 0;
    errors.length         = 0;
    fglinenum             = 0;
    partlinenum           = 0;
	fgarray.length        = 0;
    partnoarray.length    = 0;
    list_of_partNo.length = 0;
    prtS02Form            = $("#prtS02Form");
    customerNameSel       = $("#customerNameSel");
	 if(customerNameSel.val() == -2147483648){
		  errors.push({"code": "err.cmm.001", "arguments": ["Customer"]});
		  message.setErrors(errors);
		return;
	}else{
		  var noData = fgpartGrid.getData().getItems().length === 0;
		  if(noData){
			  errors.push({"code": "err.cmm.003"});
			  message.setErrors(errors);
		 	return;
		    }else{
			  var  fgPartList = $.map(fgpartGrid.getData().getItems(), function(n,i){
				  n.fgNo = $('#fgNo').val();
				  n.fgName = $('#fgName').val();
				  n.customerId = $('#customerNameSel').val();
				 if(n.statusFlag != "d"){
					 fgarray.push(n.fgId+ ":" + n.partId);
				 }
				     if((n.fg == "" ||  !(n.fg)) && n.statusFlag != "d"){
				    	 	errors.push({"code": "err.cmm.002", "arguments": [i+1,"FG No. : FG Name"]});
				    	 	message.setErrors(errors);
				    	 	return;
					 }else if((n.partNo == "" ||  !(n.partNo)) && n.statusFlag != "d"){
					    	errors.push({"code": "err.cmm.002", "arguments": [i+1,"Part No."]});
					    	message.setErrors(errors);
					    	return;
					 }else 
					       return n;
			  		});
  /* to check duplicate FG No. : Part No */
  $.each(fgarray,function(key,value){ 
    if(!unique_values[value]){
		fglinenum++;
		unique_values[value] = true;
		list_of_values.push(value);
    }else{
		 errors.push({"code": "err.cmm.020", "arguments": [fglinenum,"FG No and Part No"]});
	     message.setErrors(errors);
		 return;
		}
	 });   
   }
 }
   if(errors.length == 0 && fgPartList.length > 0){
     if (confirm(message.getMessage("cfm.cmm.001"))){
		var uri     = "PRT_S02_saveList";
		var data    =  fgPartList;
		var success = function(){
			fgpartGrid.reloadData();
		    showMessage.push({"code": "inf.cmm.002"});
		    message.setInfos(showMessage);
		};
		  postJSONObject(uri, data, success);
	 }
   }	
  });
}
function  hideFormatter(row, cell, value, columnDef, dataContext){
	return "<div style='display : none;'>value</div>";
}
function getFgNoNameList(){
    var result       = [];
    customerNameSel  =   $("#customerNameSel");
    params           = $.param ({"customerId" : customerNameSel.val()});
    postJSONSync("PRT_S02_selectFGNoList",params,function(data){
        result = data;
    });
    return result;
}

