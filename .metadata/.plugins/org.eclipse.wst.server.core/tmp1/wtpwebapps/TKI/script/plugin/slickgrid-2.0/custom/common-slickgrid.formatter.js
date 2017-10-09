jQuery( function($) {
	function Formatter() {
		var dataView = {};
		var grid = {};
		this.clear = function() {
			alert("Cleared");
		};

		this.setContextPath = function(contextPath) {
			window.contextPath = contextPath;
		};
		this.setDataView = function(dataView) {
			this.dataView = dataView;
		};
		this.setGrid = function(grid) {
			this.grid = grid;
		};
		//------------------------------------------------------------------------------------
		this.PercentComplete = function(row, cell, value, columnDef, dataContext) {
			if (value == null || value === "") {
				return "-";
			} else if (value < 50) {
				return "<span style='color:red;font-weight:bold;'>" + value + "%</span>";
			} else {
				return "<span style='color:green'>" + value + "%</span>";
			}
		};

		//------------------------------------------------------------------------------------
		this.PercentCompleteBar = function(row, cell, value, columnDef, dataContext) {
			if (value == null || value === "") {
				return "";
			}

			var color;

			if (value < 30) {
				color = "red";
			} else if (value < 70) {
				color = "silver";
			} else {
				color = "green";
			}

			return "<span class='percent-complete-bar' style='background:" + color + ";width:" + value + "%'></span>";
		};

		//------------------------------------------------------------------------------------
		this.PasswordFormatter = function(row, cell, value, columnDef, dataContext) {
			if (value || dataContext.newPassword)
				return "*****";
			else
				return "";
		};

		//------------------------------------------------------------------------------------
		this.YesNo = function(row, cell, value, columnDef, dataContext) {
			return value ? "Yes" : "No";
		};

		//------------------------------------------------------------------------------------
		this.Checkmark = function(row, cell, value, columnDef, dataContext) {
			if (value === true || value == "true") {
				return "<span class='check-icon'></span>";
			} else {
				return "<span class='uncheck-icon'></span>";
			}
		};

		//------------------------------------------------------------------------------------
		this.RadioFormatter = function(row, cell, value, columnDef, dataContext) {
			return value ? "<INPUT type=radio value='true' class='editor-checkbox' name='f_" + columnDef.id
					+ "' id='f_" + columnDef.id + row + "' checked=true>"
					: "<INPUT type=radio value='true' class='editor-checkbox' name='f_" + columnDef.id + "' id='f_"
							+ columnDef.id + row + "'>";
		};

		//------------------------------------------------------------------------------------
		this.FlagFormatter = function(row, cell, value, columnDef, dataContext) {
			if (value == "i") {
				return "<span class='insert-icon' title='Insert record'></span>";
			} else if (value == "e") {
				return "<span class='edit-icon' title='Edit record'></span>";
			} else if (value == "d") {
				return "<span class='delete-icon' title='Delete record'></span>";
			} else {
				return "";
			}
		};

		//------------------------------------------------------------------------------------
		this.DelFormatter = function(row, cell, value, columnDef, dataContext) {
			var css = dataContext.statusFlag == "d" ? "checkout-icon pointer" : "delete-row-icon pointer";
			return "<span class='" + css + "'></span>";
		};

		//------------------------------------------------------------------------------------
		this.DateFormatter = function(row, cell, value, columnDef, dataContext) {
			if (value) {
				return common.Converter.dateFormat(common.SlickGrid.format.Date, new Date(parseInt(value)));
			} else {
				return "";
			}
		};

		//------------------------------------------------------------------------------------
		this.DateTimeFormatter = function(row, cell, value, columnDef, dataContext) {
			if (value) {
				return common.Converter.dateFormat(common.SlickGrid.format.DateTime, new Date(parseInt(value)));
			} else {
				return "";
			}
		};

		//------------------------------------------------------------------------------------
		this.RowNumFormatter = function(row, cell, value, columnDef, dataContext) {
			return (row + 1) + ".";
		};

		//------------------------------------------------------------------------------------
		this.DoubleFormatter = function(row, cell, value, columnDef, dataContext) {
			return value ? common.Converter.numberFormat(common.SlickGrid.format.Double, value) : "";
		};
		
		//------------------------------------------------------------------------------------
		this.IntegerFormatter = function(row, cell, value, columnDef, dataContext) {
			return !isNaN(value) ? common.Converter.numberFormat(common.SlickGrid.format.Integer, value) : "";
		};

		//------------------------------------------------------------------------------------
		this.BooleanFormatter = function(row, cell, value, columnDef, dataContext) {
			if (value)
				return 1;
			else
				return 0;
		};

		// ------------------------------------------------------------------------------------
		this.DepartmentFormatter = function(row, cell, value, columnDef, dataContext) {
			return dataContext.departmentName ? dataContext.departmentName : "";
		};
	}

	$.extend((window.common || (window.common = {
		"SlickGrid" : {}
	})).SlickGrid, {
		"Formatters" : new Formatter()
	});
});