jQuery( function($) {
	function SlickGrid() {
		this.format = {
			"Date" : "dd/MM/yyyy",
			"DateTime" : "dd/MM/yyyy HH:mm:ss",
			"Double" : "#,##0.00",
			"Integer" : "#,##0.##"
		};
		
		this.filterColumnsOptions = {
			"Complete" : "Complete",
			"Prefix" : "Prefix",
			"Fulltext" : "Fulltext",
			"Checkbox" : "Checkbox",
			"StatusFlag" : "StatusFlag",
			"ClearFilterButton" : "ClearFilterButton"
		};

		this.filterOptions = {
			"CaseSensitive" : "CaseSensitive",
			"CaseInSensitive" : "CaseInSensitive"
		};

		this.setSearchBtn = function(grid) {
			var isOpened = $(grid.getHeaderRow()).is(":visible");

			if (!isOpened) {
				grid.showHeaderRowColumns();
				var viewport = $(grid.getHeaderRow()).parents().find(".slick-viewport:eq(0)");
				var left = viewport.scrollLeft();
				viewport.scrollLeft(left + 1);
				if (viewport.scrollLeft() == left) {
					viewport.scrollLeft(left - 1);
				}
			} else {
				grid.hideHeaderRowColumns();
			}
		};

		this.newGrid = function() {
			//Default Option
			var options = {
				explicitInitialization : false,
				rowHeight : 25,
				defaultColumnWidth : 80,
				enableAddRow : false,
				leaveSpaceForNewRows : false,
				editable : true,
				autoEdit : true,
				enableCellNavigation : true,
				enableColumnReorder : false,
				asyncEditorLoading : false,
				asyncEditorLoadDelay : 100,
				forceFitColumns : false,
				enableAsyncPostRender : false,
				asyncPostRenderDelay : 60,
				autoHeight : false,
				editorLock : Slick.GlobalEditorLock,
				showHeaderRow : true,
				headerRowHeight : 30,
				showTopPanel : false,
				topPanelHeight : 25,
				formatterFactory : null,
				editorFactory : null,
				cellFlashingCssClass : "flashing",
				selectedCellCssClass : "selected",
				multiSelect : false,
				enableTextSelectionOnCells : false,
				fullWidthRows : false,
				multiColumnSort : false
				// ,dataItemColumnValueExtractor: null
				,
				dataItemColumnValueExtractor : function(item, columnDef) {
					var names = columnDef.field.split('.'), val = item[names[0]];
					for ( var i = 1; i < names.length; i++) {
						if (val && typeof val == 'object' && names[i] in val) {
							val = val[names[i]];
						} else {
							val = '';
						}
					}
					return val;
				}
			};

			var panelId = undefined;
			var pagerId;
			var key = null;
			var change = false;
			var grid = null;
			var dataView = null;
			var cfgStore = null;
			var pager = null;
			var columnpicker = null;
			var columns = [];
			var columnFilters = {};
			var data = [];
			var enableProgressBar = true;
			var imgProgressbar = "";
			var progressbar = "";
			// Set zone
			function setImgProgressbar(cfg) {
				if (cfg) {
					imgProgressbar = cfg;
				}
				progressbar = "<div align='center' style='background-color:#ffffff'><img src='"
						+ imgProgressbar
						+ "'/></div>";
				return this;
			}

			function setKey(k) {
				key = k;
				return this;
			}

			function setOptions(c) {
				options = c;
				return this;
			}

			function setColumns(c) {
				if (c) {
					columns = c;
				}
				return this;
			}

			function setContextPath(c) {
				contextPath = c;
				return this;
			}

			function setFlag(item) {
				if (item.statusFlagOrg == undefined) {
					item.statusFlagOrg = item.statusFlag;
				}//for keep original statusFlag

				change = true;
				if (item.statusFlag == "" || item.statusFlag == null) {//last state is normal will change state to update
					item.statusFlag = "e";
				} else if (item.statusFlag == "n") {//last state is insert will change state to insert
					item.statusFlag = "i";
				}
				//else{ item.statusFlag=item.statusFlagOrg; }
				return this;
			}

			// Get zone
			function getContextPath() {
				return contextPath;
			}

			function getColumnpicker() {
				return columnpicker;
			}

			function getKey() {
				return key;
			}

			function getColumns() {
				return columns;
			}

			function getOptions() {
				return options;
			}

			function getPager() {
				return pager;
			}

			function getGrid() {
				return grid;
			}

			function getDataView() {
				return dataView;
			}

			/**
			 * Grid Table Configuration Global function onBeforeCellChange,
			 * onAfterCellChange onBeforeSort, onAfterSort
			 */
			// ####################################################################
			// Start Default grid
			// --------------------------------------------------------------------
			// Start for options showHeaderRow=true
			function updateHeaderRow() {
				for ( var i = 0; i < columns.length; i++) {
					var header = grid.getHeaderRowColumn(columns[i].id);
					if (columns[i].id !== "selector"
							&& (columns[i].filter === common.SlickGrid.filterColumnsOptions.Complete
									|| columns[i].filter === common.SlickGrid.filterColumnsOptions.Prefix || columns[i].filter === common.SlickGrid.filterColumnsOptions.Fulltext)) {
						$(header).empty();
						$("<input/>").attr("title", columns[i].filter + " filter").data("columnId", columns[i].id).val(
								columnFilters[columns[i].id]).css("width", "98%").appendTo(header);
					} else if (columns[i].id !== "selector"
							&& columns[i].filter === common.SlickGrid.filterColumnsOptions.Checkbox) {
						$(header).empty();
						$("<select></select>").append("<option value='' title='All'>All</option>").append(
								"<option value='true' class='check-icon' title='Checked'> Yes</option>").append(
								"<option value='false' class='uncheck-icon' title='Uncheck'> No</option>").data(
								"columnId", columns[i].id).val(columnFilters[columns[i].id]).appendTo(header);
					} else if (columns[i].id !== "selector"
							&& columns[i].filter === common.SlickGrid.filterColumnsOptions.StatusFlag) {
						$(header).empty();
						$("<select></select>").append("<option value='' title='All'></option>").append(
								"<option value='n' title='New'></option>").append(
								"<option value='i' title='Insert' class='insert'></option>").append(
								"<option value='e' title='Edit' class='edit'></option>").append(
								"<option value='d' title='Delete' class='delete'></option>").data("columnId",
								columns[i].id).appendTo(header);
					} else if (columns[i].id !== "selector"
							&& columns[i].filter === common.SlickGrid.filterColumnsOptions.ClearFilterButton) {
						$(header).empty();
						$("<div/>").addClass("clear-filter pointer").attr("title", "Clear Filter").click( function(e) {
							$(this).parent().parent().find("input,select").val("").change();
						}).appendTo(header);
					} else {
						$(header).css("height", $(header).parent().css("height"));
					}
					// Fix bug in case of user key tab on filter object and out of width scope will got wrong css position
					$(header).find("input,select,div").bind("blur", function(e) {
						var viewport = $(this).parents().find(".slick-viewport:eq(0)");
						if (viewport) {
							var scrollLeft = viewport.scrollLeft();
							var width = parseInt($(this).css("width"));
							viewport.scrollLeft(scrollLeft + width);
						}
					});
				}
			}

			function filter(item) {
				for ( var columnId in columnFilters) {
					if (columnId !== undefined && columnFilters[columnId] !== "") {
						var c = grid.getColumns()[grid.getColumnIndex(columnId)];
						var vI = "", vF = "";
						// (new Date("2012/03/10 17:10:00")).getTime()
						// Converter.dateFormat('yyyy-MM-dd HH:mm:ss', new
						// Date(parseInt(value)))
						if (c.filterOptions === common.SlickGrid.filterOptions.CaseSensitive) {
							vI = (item[c.field] != undefined && item[c.field] != null ? item[c.field] : '').toString();
							vF = (columnFilters[columnId] != undefined && columnFilters[columnId] != null
									? columnFilters[columnId] : '').toString();
						} else if (c.filterOptions === common.SlickGrid.filterOptions.CaseInSensitive
								|| !c.filterOptions) {
							vI = (item[c.field] != undefined && item[c.field] != null ? item[c.field] : '').toString()
									.toLowerCase();
							vF = (columnFilters[columnId] != undefined && columnFilters[columnId] != null
									? columnFilters[columnId] : '').toString().toLowerCase();
						}

						//----------------------------------------------------------------------
						if (c.formatter === common.SlickGrid.Formatters.DoubleFormatter) {
							vI = common.SlickGrid.Formatters.DoubleFormatter(null, null, vI, null, null);
						} else if (c.formatter === common.SlickGrid.Formatters.DateFormatter) {
							vI = common.SlickGrid.Formatters.DateFormatter(null, null, vI, null, null);
						} else if (c.formatter === common.SlickGrid.Formatters.DateTimeFormatter) {
							vI = common.SlickGrid.Formatters.DateTimeFormatter(null, null, vI, null, null);
						} else if (c.formatter === common.SlickGrid.Formatters.Checkmark
								&& c.filter === common.SlickGrid.filterColumnsOptions.Checkbox) {
							vI = (vI != '')
									&& ((vI) == "true"
											|| (vI) == "t"
											|| (vI) == "yes"
											|| (vI) == "y"
											|| (vI) == "check"
											|| (vI === true) || !isNaN(vI)) ? true : false;
							vF = (vF).toLowerCase() == "true";
						}

						if ((c.filter === common.SlickGrid.filterColumnsOptions.Complete && (vI) !== vF)
								|| (c.filter === common.SlickGrid.filterColumnsOptions.Prefix && (vI).indexOf(vF) !== 0)
								|| (c.filter === common.SlickGrid.filterColumnsOptions.Fulltext && (vI).indexOf(vF) == -1)
								|| (c.filter === common.SlickGrid.filterColumnsOptions.Checkbox && (vI) !== (vF))
								|| (c.filter === common.SlickGrid.filterColumnsOptions.StatusFlag && (vI) !== (vF))) {
							return false;
						}
						//----------------------------------------------------------------------
					}
				}
				return true;
			}

			// --------------------------------------------------------------------
			// End for options showHeaderRow=true

			function setGrid(cfg) {
				cfgStore = cfg;
				if (!cfg) {
					alert("Has problem in your grid configulation or data !!");
					return getPublicObject();
				} else if (cfg.key) {
					key = cfg.key;
				} else {
					key = (cfg.data.key) ? cfg.data.key : "_id";
				}//default is `id`
				if (cfg.imgProgressbar) {
					setImgProgressbar(cfg.imgProgressbar);
				}

				if (!cfg) {
					return this;
				}
				if (cfg.columns) {
					columns = cfg.columns;
				}
				if (cfg.id) {
					panelId = cfg.id;
				} else {
					alert("Your grid `id` is empty! Please set your grid ID first");
				}

				dataView = new Slick.Data.DataView();
				dataView.getItemMetadata = DefaultItemMetadata;
				grid = new Slick.Grid("[id=" + panelId + "]", dataView, columns, options);
				grid.setSelectionModel(new Slick.RowSelectionModel());
				if (cfg.pagerId) {
					pagerId = cfg.pagerId;
					pager = new Slick.Controls.Pager(dataView, grid, $("[id=" + pagerId + "]"));
				}

				columnpicker = new Slick.Controls.ColumnPicker(columns, grid, options);
				if (enableProgressBar && imgProgressbar != "") {
					$("[id=" + panelId + "]").html(progressbar);
				}

				grid.onCellChange.subscribe( function(e, args) {
					setFlag(args.item);
					dataView.beginUpdate();
					dataView.updateItem(args.item[key], args.item);
					dataView.endUpdate();
					change = true;
				});

				// ----------------------------------------------------------------
				// start sort
				grid.onSort.subscribe( function(e, args) {
					sortdir = args.sortAsc ? 1 : -1;
					sortcol = args.sortCol.field;
					var comparer = function(a, b) {
						var x = a[sortcol], y = b[sortcol];
						var isNumber = x && y && !isNaN(x) && !isNaN(y);
						if (isNumber) {
							x = parseFloat(x, 10);
							y = parseFloat(y, 10);
							return (x == y ? 0 : (x > y ? 1 : -1));
						} else {
							x = (!x) ? '' : (x + '').toLowerCase();
							y = (!y) ? '' : (y + '').toLowerCase();
							return (x == y ? 0 : (x > y ? 1 : -1));
						}
					};

					// ......................................... global function
						if (typeof onBeforeSort == "function") {
							onBeforeSort(e, args);
						}

						if ($.browser.msie && $.browser.version <= 8) {
							// using temporary Object.prototype.toString override
							// more limited and does lexicographic sort only by
							// default, but can be much faster

							// use numeric sort of % and lexicographic for
							// everything else
							// dataView.fastSort((sortcol == "percentComplete")
							// ? percentCompleteValueFn : sortcol,
							// args.sortAsc);
						} else {
							// using native sort with comparer
							// preferred method but can be very slow in IE with
							// huge datasets
							dataView.sort(comparer, args.sortAsc);
						}

						//......................................... global function
						if (typeof onAfterSort == "function") {
							onAfterSort(e, args);
						}
					});

				// ----------------------------------------------------------------
				// start select all rows
				grid.onKeyDown.subscribe( function(e) {
					// select all rows on ctrl-a
						if (e.which != 65 || !e.ctrlKey) {
							return false;
						}

						var rows = [];
						for ( var i = 0; i < dataView.getLength(); i++) {
							rows.push(i);
						}

						grid.setSelectedRows(rows);
						e.preventDefault();
					});
				// ----------------------------------------------------------------
				// end select all rows

				// wire up model events to drive the grid
				// ----------------------------------------------------------------
				// start dataView.onRowCountChanged
				dataView.onRowCountChanged.subscribe( function(e, args) {
					grid.updateRowCount();
					grid.render();
				});
				// ----------------------------------------------------------------
				// end dataView.onRowCountChanged

				dataView.onRowsChanged.subscribe( function(e, args) {
					grid.invalidateRows(args.rows);
					grid.render();
				});

				if (options.enableAddRow) {
					dataView.onPagingInfoChanged.subscribe( function(e, pagingInfo) {
						var isLastPage = pagingInfo.pageSize * (pagingInfo.pageNum + 1) - 1 >= pagingInfo.totalRows;
						var enableAddRow = isLastPage || pagingInfo.pageSize == 0;
						var options = grid.getOptions();

						if (options.enableAddRow != enableAddRow) {
							grid.setOptions( {
								enableAddRow : enableAddRow
							});
						}
					});
				}

				//------------------------------------------------------------- updateHeaderRow
				if (options.showHeaderRow) {
					$(grid.getHeaderRow()).delegate(":input", "change keyup", function(e) {
						columnFilters[$(this).data("columnId")] = $.trim($(this).val());
						dataView.refresh();
					});

					grid.onColumnsReordered.subscribe( function(e, args) {
						updateHeaderRow();
					});

					grid.onColumnsResized.subscribe( function(e, args) {
						updateHeaderRow();
					});
					updateHeaderRow();
				}
				//------------------------------------------------------------- updateHeaderRow

				// -------------------------------------------------------------
				// START options.enableAddRow=true
				if (options.enableAddRow) {
					grid.onAddNewRow.subscribe( function(e, args) {
						var item = args.item;
						var o = {};
						if (key) {
							if (dataView.getItems().length) {
								var k = dataView.getItems()[dataView.getItems().length - 1][key];
							} else {
								var k = key == "_id" ? 0 : (new Date()).getTime();
							}
							if (args.item[key]) {
								//not set
						} else {
							o[key] = isNaN(k) ? k + "" + 1 : parseInt(k) + 1;// add
							// new
							// key
						}
						item = $.extend(true, item, o);
					} else {
						o["_id"] = parseInt(dataView.getItems()[dataView.getItems().length - 1]._id) + 1;
						item = $.extend(true, item, o);
					}
					item.statusFlag = "i";
					item.rowNum = dataView.getItems().length + 1;
					dataView.addItem(item);
					grid.invalidateRow(dataView.getItems().length);
					grid.updateRowCount();
					grid.render();
					dataView.refresh();
				})	;
				}
				//------------------------------------------------------------- END options.enableAddRow=true

				$("[id=" + panelId + "]").show().css("background", "white").css("outline", "0").css("border",
						"1px solid gray");
				setDataByURL(cfg);
				grid.hideHeaderRowColumns();
				return getPublicObject();
			}

			function setPreparingDialog() {
				$("#preparing-file-modal").show();
				$("#preparing-file-modal-img").show();
			}

			function clearPreparingDialog() {
				$("#preparing-file-modal").hide();
				$("#preparing-file-modal-img").hide();
			}

			function setErrorDialog() {
				$("#error-modal").dialog( {
					modal : true,
					closeOnEscape : false
				});
			}

			function clearErrorDialog() {
				$("#error-modal").dialog("destroy");
			}

			function setDataByURL(cfg) {
				setPreparingDialog();

				if (cfg.data && cfg.data.url) {
					//Sample {"url":"","param":{},"method":"post","type":"json"}
					//dataObject for data array
					// Sample data function
					/**
					 * "dataFunction":function(cfg,e){ if(cfg.data.dataObject){
					 * for(var i in e[cfg.data.dataObject]){
					 * e[cfg.data.dataObject][i]=$.extend(true,e[cfg.data.dataObject][i],{"tableId":e.tableId}); }
					 * return e[cfg.data.dataObject]; } else{ for(var i in e){
					 * e[i]=$.extend(true,e[i],{"tableId":e.tableId}); } return
					 * e; } }
					 */
					// grid.invalidateRow(dataView.getItems().length);//for
					// control addnew row
					var method = cfg.data.method ? cfg.data.method : "get";
					if (method == "get" || method == "GET") {
						$.get(cfg.data.url, (cfg.data.param ? cfg.data.param : {}),
								(cfg.data.type ? cfg.data.type : "json")).success( function(e) {
							var d = cfg.data.dataObject ? eval('e.' + cfg.data.dataObject)/* e[cfg.data.dataObject] */
							: e;
							if (typeof cfg.data.dataFunction == "function") {
								d = cfg.data.dataFunction(cfg, e);
							}
							setData(d, key);
							clearPreparingDialog();
						}).error( function(e) {
							clearPreparingDialog();
							setErrorDialog();
						}).complete( function(e) {
						});
					} else if (method == "post" || method == "POST") {
						$.ajax(
								{
									'type' : 'POST',
									'url' : cfg.data.url,
									'data' : (cfg.data.param ? (cfg.data.json ? JSON.stringify(cfg.data.param)
											: cfg.data.param) : {}),
									'dataType' : (cfg.data.type ? cfg.data.type : "json"),
									'contentType' : (cfg.data.json ? 'application/json'
											: 'application/x-www-form-urlencoded')
								}).success( function(e) {
							var d = cfg.data.dataObject ? e[cfg.data.dataObject] : e;
							if (typeof cfg.data.dataFunction == "function") {
								d = cfg.data.dataFunction(cfg, e);
							}
							setData(d, key);
							clearPreparingDialog();
						}).error( function(e) {
							clearPreparingDialog();
							setErrorDialog();
						}).complete( function(e) {
						});
					}
				} else if (cfg.data) {
					setData(cfg.data, key);
					clearPreparingDialog();
				}
			}

			function isKey(id) {
				var found = false;
				if (!id) {
					alert("Please set your key column first");
				}
				if (id != "_id") {
					columns.forEach( function(a) {
						if (a.id == id) {
							found = true;
							return;
						}
					});
					// if(!found){ alert("Key `"+id+"` not found in your
					// columns"); key="id";}
				} else {
					found = true;
				}
				return found;
			}

			function setData(data1, id) {
				//if(!isKey(id ? id:key)){return false;}
				if (data1) {
					if (typeof data1 == "string") {
						data = JSON.parse(data1);
					} else {
						data = data1;
					}

					dataView.getItems().splice(0, dataView.getItems().length);
					dataView.refresh();

					for ( var i in data) {
						var index = parseInt(i) + 1;
						data[i] = $.extend(true, data[i], {
							"statusFlagOrg" : data[i].statusFlag,
							"rowNum" : index
						});
						if (!data[i][key]) {
							data[i][key] = index;
						}
					}//extend data with statusFlagOrg in merge option
					// initialize the model after all the events have been
					// hooked up
					dataView.beginUpdate();
					dataView.setItems(data, id ? id : key);
					if (options.showHeaderRow) {
						dataView.setFilter(filter);
					}
					dataView.endUpdate();
					dataView.syncGridSelection(grid, true);
				}
				grid.render();
			}

			function setDataByItems(row, cell, items) {
				grid.onCellChange.notify( {
					"cell" : cell,
					"row" : row,
					"grid" : grid,
					"item" : items
				});
				dataView.refresh();
				grid.render();
			}

			function isChange() {
				return change;
			}

			function setChange(ch) {
				change = ch;
			}

			function DefaultItemMetadata(rowIndex) {
				// <!-- Defining: Item. -->
				var item = this.getItem(rowIndex);
				// <!-- Returning: Meta Data. -->
				return {
					"cssClasses" : item && item.statusFlag && item.statusFlag.toLowerCase() === "d" ? "line-through"
							: ""
				};
			}

			function setSelectionModel(rowSelectionModel) {
				grid.setSelectionModel(rowSelectionModel);
			}

			function registerPlugin(checkboxSelector) {
				grid.registerPlugin(checkboxSelector)
			}

			// Public get&set
			function getPublicObject() {
				return {
					"getGrid" : getGrid,
					"getData" : function() {
						return grid.getData();
					},
					"getDataView" : getDataView,
					"getPager" : getPager,
					"getColumnpicker" : getColumnpicker,
					"getOptions" : getOptions,
					"getKey" : getKey,
					"getFilteredRows" : function() {
						var data = [];
						for ( var i = 0; i < grid.getDataLength(); i++) {
							data.push(grid.getDataItem(i));
						}
						return data;
					},
					"isChange" : isChange,
					"setImgProgressbar" : setImgProgressbar,
					"setData" : setData,
					"setDataByItems" : setDataByItems,
					"setChange" : setChange,
					"setFlag" : setFlag,
					"isKey" : isKey,
					"setKey" : setKey,
					"setPreparingDialog" : setPreparingDialog,
					"clearPreparingDialog" : clearPreparingDialog,
					"setErrorDialog" : setErrorDialog,
					"clearErrorDialog" : clearErrorDialog,
					"reloadData" : function(cfgArg) {
						$.extend(cfgStore, cfgArg);
						setDataByURL(cfgStore);
					},
					"resetFilter" : function() {
						$(grid.getHeaderRow()).find("input,select").val("").change();
					},
					"setSelectionModel" : setSelectionModel,
					"registerPlugin" : registerPlugin,
					"updateHeaderRow" : updateHeaderRow
				};
			}
			//#################################################################### End Default grid
			return {
				"setGrid" : setGrid,
				"setOptions" : setOptions,
				"getOptions" : getOptions,
				"setImgProgressbar" : setImgProgressbar,
				"setContextPath" : setContextPath,
				"getContextPath" : getContextPath,
				"setPreparingDialog" : setPreparingDialog,
				"clearPreparingDialog" : clearPreparingDialog,
				"setErrorDialog" : setErrorDialog,
				"clearErrorDialog" : clearErrorDialog,
				"setSelectionModel" : setSelectionModel,
				"registerPlugin" : registerPlugin
			};
		};

		this.options = {
			"View" : $.extend( {}, this.newGrid().getOptions(), {
				editable : false,
				enableAddRow : false
			}),
			"Insertable" : $.extend( {}, this.newGrid().getOptions(), {
				editable : true,
				enableAddRow : true
			}),
			"Editable" : $.extend( {}, this.newGrid().getOptions(), {
				editable : true,
				enableAddRow : false
			})
		}
	}

	$.extend((window.common || (window.common = {})), {
		"SlickGrid" : new SlickGrid()
	});
});