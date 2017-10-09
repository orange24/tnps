var fgGrid;
var fgColumns;
var linenum;
var exportForm;
var errors         = [];
var fgNoarray      = [];
var list_of_values = [];
var unique_values  = {};
var showMessage    = [];

$(function($){	
	// data
	fgMstForm = $("#fgMstForm");
	exportForm = $("#exportForm");
	getUomList();
	getClassifyBusinessList();
	getPlaceList();
	getSubBusinessList();
	// view
	createFgMasterGrid();
	// event
	saveFgMaster();
	exportFgMaster();
});
function createFgMasterGrid(){
	var editOptions = $.extend({},common.SlickGrid.newGrid().getOptions(),{
		enableAddRow : false
	});// Extend default options from common grid
	// <!-- FgMasterGrid Grid -->
	fgColumns = [{
		id             : "statusFlag",
		name           : "",
		field          : "statusFlag",
		width          : 30,
		resizable      : true,
		formatter      : common.SlickGrid.Formatters.FlagFormatter,
		filter 		   : common.SlickGrid.filterColumnsOptions.ClearFilterButton,
		sortable	   : true
	},{
		id 			   : "rowNo",
		name 		   : "No.",
		field		   : "rowNo",
		width 		   : 50,
		resizable 	   : true,
		formatter 	   : common.SlickGrid.Formatters.RowNumFormatter
	},{
		id             : "fgId",
		name           : "FG ID",
		field          : "fgId",
		width          : 60,
		resizable      : true,
		filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	   : true 
	},{
		id             : "fgNo",
		name           : "FG No.",
		field          : "fgNo",
		width          : 120,
		resizable      : true,
		filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	   : true
	},{
		id             : "fgName",
		name           : "FG Name",
		field          : "fgName",
		width          : 170,
		resizable      : true,
		filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	   : true
	},{
		id             : "uom",
		name           : "UOM",
		field          : "uom",
		width          : 80,
		resizable      : true,
		filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	   : true
	},{
		id             : "snpFG",
		name           : "SNP(FG)",
		field          : "snpFG",
		width          : 90,
		cssClass       : "cell-r",
		headerCssClass : "editable-column required-column",
		resizable      : true,
		filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
		editor         : common.SlickGrid.Editor.IntegerEditor,
		sortable	   : true,
		formatter	   : common.SlickGrid.Formatters.IntegerFormatter
	},{
		id             : "weight",
		name           : "Weight",
		field          : "weight",
		width          : 90,
		cssClass       : "cell-r",
		resizable      : true,
		filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	   : true,
		formatter 	   : common.SlickGrid.Formatters.DoubleFormatter
	},{
		id             : "price",
		name           : "Price",
		field          : "price",
		width          : 80,
		cssClass       : "cell-r",
		resizable      : true,
		filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	   : true,
		formatter 	   : common.SlickGrid.Formatters.DoubleFormatter
	},{
		id             : "currency",
		name           : "Currency",
		field          : "currency",
		width          : 95,
		resizable      : true,
		filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
		sortable	   : true
	},{
		id             : "vendorFgNo",
		name           : "Vendor FG No.",
		field          : "vendorFgNo",
		width          : 150,
		headerCssClass : "editable-column",
		resizable      : true,
		filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
		editor         : common.SlickGrid.Editor.TextEditor,
		sortable	   : true
	},{
		id             : "classifyBiz",
		name           : "Classify Business",
		field          : "classifyBiz",
		width          : 130,
		headerCssClass : "editable-column",
		resizable      : true,
		filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
		editor         : common.SlickGrid.Editor.DropDownEditor,
		editorArgs     : {
		                   dataList : getClassifyBusinessList(),
		                    value   : "classifyBusinessId",
		                    label   : "classifyBiz"
		                 },
		sortable	   : true
	},{
		id             : "place",
		name           : "Place",
		field          : "place",
		width          : 80,
		headerCssClass : "editable-column",
		resizable      : true,
		filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
		editor         : common.SlickGrid.Editor.DropDownEditor,
		editorArgs     : {
		                   dataList : getPlaceList(),
		                    value   : "placeId",
		                    label   : "place"
		                 },
		sortable	   : true
	},{
		id             : "subBusiness",
		name           : "Sub-Business",
		field          : "subBusiness",
		width          : 120,
		headerCssClass : "editable-column",
		resizable      : true,
		filter         : common.SlickGrid.filterColumnsOptions.Fulltext,
		editor         : common.SlickGrid.Editor.DropDownEditor,
		editorArgs     : {
		                   dataList : getSubBusinessList(),
		                    value   : "subBusinessId",
		                    label   : "subBusiness"
		                 },
		sortable	   : true
	},{
		id             : "isenable",
		name           : "Enable",
		field          : "isenable",
		width          : 80,
		headerCssClass : "editable-column required-column",
		cssClass       : "cell-c",
		resizable      : true,
		formatter      : common.SlickGrid.Formatters.YesNo,
		filter         : common.SlickGrid.filterColumnsOptions.Checkbox,
		editor         : common.SlickGrid.Editor.Checkbox,
		sortable	   : true
	},{
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
		id             : "deleteField",
		name           : "Delete",
		field          : "deleteField",
		width          : 50,
		cssClass       : "cell-c",
		resizable      : true,
		formatter      : common.SlickGrid.Formatters.DelFormatter,
		editor         : common.SlickGrid.Editor.DelEditor
	}];
	   fgGrid  = common.SlickGrid.newGrid();
	   fgGrid  = fgGrid.setOptions(editOptions);
	   fgGrid  = fgGrid.setGrid({
		          "id"   : "fgGrid",
		       "columns" : fgColumns,
		         "data"  : {url : "S_FGM_S01_searchList.html"}
	});
	createCellChange();
}
/**
 * Get JSON objects from back end before cell does not update
 * Reset default values after adding new row
 * Reset default values after changing cell 
 * @param none
 * @returns none
 */
function createCellChange(){
	fgGrid.getGrid().onAddNewRow.subscribe( function(e, args) {
		if(args.item != undefined || args.item != null){ 
			if (args.item.statusFlag != null){
				if(args.item.isenable == undefined){
					args.item.isenable = true;
					fgGrid.getDataView().updateItem(args.item.rowNum, args.item);
				}
			}
		}
	});
}
function  saveFgMaster(){
	var success = function(bean){
		message.showMessage(bean);
		fgGrid.reloadData(bean);
};
  $("[id=btnSave]").click(function(){
	  fgGrid.getGrid().getEditorLock().commitCurrentEdit();
	  fgGrid.resetFilter();
	  message.clear();
	  linenum = 1;
	  fgNoarray.length      = 0;
	  list_of_values.length = 0;
	  errors.length         = 0;
	  showMessage.length    = 0;
	  unique_values         = {};
	  var fgList = $.map(fgGrid.getData().getItems(),function(n,i){
		  fgNoarray.push(n.fgNo);
		   if(n.statusFlag != null  &&  n.statusFlag  != 'd'){
			     if(n.fgNo == null || !(n.fgNo)){
			         errors.push({"code": "err.cmm.002", "arguments": [i+1,"FGNo"]});
		            }
		         else if(n.fgName == null  ||  !(n.fgName)){
			        errors.push({"code": "err.cmm.002", "arguments": [i+1,"FGName"]});
			        }
		         else if(n.uom == null ||  !(n.uom)){
				     errors.push({"code": "err.cmm.002", "arguments": [i+1,"UOM"]});
			        }
		         else if(n.snpFG == null  ||  !(n.snpFG)){
				     errors.push({"code": "err.cmm.002", "arguments": [i+1,"SNP(FG)"]});
			        }
		         else if(n.weight == null  ||  !(n.weight)){
				     errors.push({"code": "err.cmm.002", "arguments": [i+1,"Weight"]});
			        }
		         else if(n.price == null  ||  !(n.price)){
				     errors.push({"code": "err.cmm.002", "arguments": [i+1,"Price"]});
			        }
		         else if(n.currency == null  ||  !(n.currency)){
				     errors.push({"code": "err.cmm.002", "arguments": [i+1,"Currency"]});
			        }
		         else{
		        	 if(n.statusFlag == 'i'){
		        		 if(n.isenable == undefined){
		        			 n.isenable = 0;
		        		 } 
		        	 }
		        	 return n;
		         }
		    }else{
	        	 if($(n.statusFlag == 'd').exists()) 
	        		 return n;
			    }
		});
	  
	  /* to check duplicate FGNo*/  		
	  $.each(fgNoarray,function(index,object){ 
	 if (!unique_values[object]){
	      unique_values[object] = true;
	      list_of_values.push(object);
	 }else if(unique_values[object]){
	        errors.push({"code": "err.cmm.020", "arguments": [index+1,"FGNO"]});
	      }
 });
		message.setErrors(errors);
		if(message.isNoError() && fgList != ""){
		 if(confirm(message.getMessage("cfm.cmm.001"))){
			postJSONObject("S_FGM_S01_saveList",fgList,success);	
		}
	}
  });
 }
function  exportFgMaster(){
	$("[id=btnExport]").click(function(){
		message.clear();
		var noData = fgGrid.getFilteredRows().length === 0;
		if(noData){
            message.setErrors([{"code": "err.cmm.018", "arguments": []}]);
        }else{
        	exportForm.empty();
			  var hd = $(fgGrid.getGrid().getHeaderRow());
     		  hd.find(".slick-headerrow-column").each(function(idx,obj){
				var  o = fgGrid.getGrid().getColumns()[idx];
				var  ip = $(this).find("input,select");
				if( ip.exists() && ip.val()){
					exportForm.append($('<input type="hidden" name="' + o.field + '" value="' + ip.val() + '"/>'));
				}
			});
			downloadNotify($("<div title='"+message.getMessage("downloadAlertContent")+"'/>'>"+message.getMessage("downloadAlertContent")+"</div>"));
			exportForm.submit();
	}
	});
}
function getCurrencyList(){
	var currencyList = [];
	params        = {};
	postJSONSync("S_FGM_S01_getCurrencyList",params,function(data){
		currencyList  = data;
	});
	return currencyList;
}
function getUomList(){
  var  uomList  = [];
  params        = {};
  postJSONSync("S_FGM_S01_getUomList",params,function(data){
	  uomList  = data;
  });
  return uomList;
}
function getClassifyBusinessList(){
 var  classifyBusinessList = [];
	  params               = {};
	  postJSONSync("S_FGM_S01_getClassifyBusiness",params,function(data){
		  classifyBusinessList = data;
	  });
	  return classifyBusinessList;
	}
function getPlaceList(){
	  var  placeList = [];
	  params         = {};
	  postJSONSync("S_FGM_S01_getPlaceList",params,function(data){
		  placeList = data;
	  });
	  return placeList;
	}
function getSubBusinessList(){
	  var  subBusinessList = [];
	  params               = {};
	  postJSONSync("S_FGM_S01_getSubBusinessList",params,function(data){
		  subBusinessList = data;
	  });
	  return subBusinessList;
	}
