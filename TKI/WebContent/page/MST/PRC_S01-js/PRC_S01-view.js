var processGrid;
var processColumns;

$( function($) {
	var checkboxSelector = new Slick.CheckboxSelectColumn( {
		cssClass : "slick-cell-checkboxsel"
	});
	var editOptions = $.extend( {}, common.SlickGrid.newGrid().getOptions(), {
		showHeaderRow : true,
		headerRowHeight : 30,
		enableAddRow : true
	});// Extend default options from common grid
	
	// <!-- Customer Grid -->
	processColumns = [{
		id : "flag",
		name : "",
		field : "flag",
		width : 50,
		cssClass : "cell-c",
		resizable : true,
		formatter: editFormatter
	},{
		id : "processCode",
		name : "Process Code",
		field : "processCode",
		headerCssClass : "editable-column required-column",
		width : 100,
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id : "processName",
		name : "Process Name",
		field : "processName",
		headerCssClass : "editable-column required-column",
		width : 200,
		resizable : true,
		filter : common.SlickGrid.filterColumnsOptions.Fulltext
	},{
		id : "wipCal",
		name : "WIP Calc.",
		field : "wipCal",
		cssClass : "cell-c",
		headerCssClass : "editable-column required-column",
		width : 100,
		resizable : true
	}, {
		id : "wipType",
		name : "WIP Type",
		field : "wipType",
		headerCssClass : "editable-column required-column",
		width : 120,
		resizable : true,
		editor : otShiftPatternEditor
	},{
		id : "remark",
		name : "Remark",
		field : "remark",
		headerCssClass : "editable-column",
		width : 120,
		resizable : true
	}, {
		id : "createDate",
		name : "Create Date",
		field : "createDate",
		width : 120,
		cssClass : "cell-c",
		resizable : true
	},{
		id : "createBy",
		name : "Create By",
		field : "createBy",
		width : 120,
		resizable : true
	},{
		id : "updateDate",
		name : "Update Date",
		field : "updateDate",
		width : 120,
		cssClass : "cell-c",
		resizable : true
	},{
		id : "updateBy",
		name : "Update By",
		field : "updateBy",
		width : 120,
		resizable : true
	},{
		id : "deleteField",
		name : "Delete",
		field : "deleteField",
		width : 50,
		cssClass : "cell-c",
		resizable : true,
		formatter : delFormatter,
		editor : common.SlickGrid.Editor.DelEditor
	}];
	
	processGrid = common.SlickGrid.newGrid();
	processGrid = processGrid.setOptions(editOptions);
	processGrid = processGrid.setGrid( {
		"id" : "processGrid",
		"columns" : processColumns,
		"data" : processData
	});
	processGrid.registerPlugin(checkboxSelector);
});

function delFormatter(row, cell, value, columnDef, dataContext) {
  return "<img src='../images/delete.gif'/>";
} 

function editFormatter(row, cell, value, columnDef, dataContext) {
  var random = Math.round(Math.random() * 100);
  if(random % 2 == 0){
	  return "";
  }else{
	  if(random < 20){
		  return "<img src='../images/delete_x.gif'/>";
	  }else{
		  return "<img src='../images/update.gif'/>";
	  }
	  
  }
  
}

function otShiftPatternEditor(args) {
	var $select = null;
	var defaultValue = null;
	
	if(args.column.id == "wipType"){
		$select = $("<select><option value = ''></option>" +
					"<option value='Diecast'>Diecast</option>" +
					"<option value='Machine (+Worker)'>Machine (+Worker)</option>" +
					"<option value='Worker'>Worker</option>" +
				"</select>");
	}
	
	this.init = function() {
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
		$select.val(defaultValue);
	};

	this.serializeValue = function() {
		return $select.val();
	};

	this.applyValue = function(item, state) {
		item[args.column.field] = state;
	};

	this.isValueChanged = function() {
		return ($select.val() != defaultValue);
	};

	this.validate = function() {
		return {
			valid : true,
			msg : null
		};
	};

	this.init();
};
