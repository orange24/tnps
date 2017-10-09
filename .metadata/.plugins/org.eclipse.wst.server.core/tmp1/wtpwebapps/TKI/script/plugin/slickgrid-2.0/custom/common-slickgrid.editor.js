jQuery( function($) {
	jQuery.fn.getCursorPosition = function() {
		var el = $(this).get(0);
		var pos = 0;
		if ('selectionStart' in el) {
			pos = el.selectionStart;
		} else if ('selection' in document) {
			el.focus();
			var Sel = document.selection.createRange();
			var SelLength = document.selection.createRange().text.length;
			Sel.moveStart('character', -el.value.length);
			pos = Sel.text.length - SelLength;
		}
		return pos;
	};

	function Editor() {
		/**
		 * Parameter args
		 *  - column : column config
		 *  - container (div) :
		 *  - grid
		 *  - gridPosition : top, left, bottom, ...
		 *  - item : item data
		 *  - position : 
		 *  - cancelChanges (fn)
		 *  - commitChanges (fn)
		 */
		//---------------------------------------------------------------------------- DelEditor
		this.DelEditor = function(args) {
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
		};

		//---------------------------------------------------------------------------- DateEditor
		this.DateEditor = function(args) {
			var $input;
			var defaultValue;
			var scope = this;
			var calendarOpen = false;

			this.init = function() {
				var date;
				if (args.item[args.column.field]) {
					date = new Date();
					date.setTime(args.item[args.column.field]);
				} else {
					date = new Date();
				}

				$input = $("<INPUT type=text class='editor-text' readonly='readonly'/>");
				$input.datepicker( {
					showButtonPanel : true,
					changeMonth : true,
					changeYear : true,
					showDate : true,
					showHour : false,
					showMinute : false,
					showSecond : false,
					showOtherMonths : true,
					selectOtherMonths : true,
					timeOnly : false,
					dateFormat : 'yy-mm-dd',
					timeFormat : '',
					timeText : '',
					currentText : 'Today',
					defaultDate : date,
					hour : date.getHours(),
					minute : date.getMinutes(),
					second : date.getSeconds()
				});

				$input.appendTo(args.container);
				$input.focus().select();

				if (args.column.maxlength) {
					$input.attr("maxlength", args.column.maxlength);
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
			};

			this.destroy = function() {
				$.datepicker.dpDiv.stop(true, true);
				$input.datepicker("hide");
				$input.datepicker("destroy");
				$input.remove();
			};

			this.show = function() {
				if (calendarOpen) {
					$.datepicker.dpDiv.stop(true, true).show();
				}
			};

			this.hide = function() {
				if (calendarOpen) {
					$.datepicker.dpDiv.stop(true, true).hide();
				}
			};

			this.position = function(position) {
				if (!calendarOpen) {
					return;
				}
				$.datepicker.dpDiv.css("top", position.top + 30).css("left", position.left);
			};

			this.focus = function() {
				$input.focus();
			};

			this.loadValue = function(item) {
				defaultValue = item[args.column.field];

				if (defaultValue) {
					var date = new Date(defaultValue);
					var newVal = Converter.dateFormat('yyyy-MM-dd', date);
					var newVal2 = Converter.dateFormat('yyyy/MM/dd', date);
					$input.val(newVal);
					$input[0].defaultValue = newVal2;
					$input.select();

				} else {
					$input.val('');
					$input[0].defaultValue = '';
					$input.select();
				}
			};

			this.serializeValue = function() {
				return $input.val();
			};

			this.applyValue = function(item, state) {
				var d = (state + "").replace(/-/g, "/");
				var date = new Date(d);
				item[args.column.field] = date.getTime();
			};

			this.isValueChanged = function() {
				var date = $input.val().replace(/\-/g, '/');
				var timeStamp = (new Date(date)).getTime();

				return (!(timeStamp == "" && defaultValue == null)) && (timeStamp != defaultValue);
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

		//---------------------------------------------------------------------------- IM-DateEditor
		/**
		 * Fix column option
		 *  - width : 95
		 */
		this.ImDateEditor = function(args) {
			var $input;
			var $imCal;
			var defaultValue;
			var scope = this;
			var calendarOpen = false;
			var holidays = [];

			this.init = function() {
				var $div = $("<div/>");
				$input = $(
						"<input type='text' class='editor-text' readonly='readonly' id='calendarDate' name='calendarDate' style='width: 75px;float:left;'/>")
						.appendTo($div);
				$imCal = $("#imCalendar").clone().attr("id", "imCal").css("float", "right").css("display", "")
						.appendTo($div);
				$div.appendTo(args.container);

				if (args.item[args.column.field]) {
					$input.val(args.item[args.column.field]);
				} else {
					//$input.val(Converter.dateFormat('yyyy/MM/dd', new Date()));
				}
				$input.focus().select();

				if (args.column.maxlength) {
					$input.attr("maxlength", args.column.maxlength);
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
			};

			this.destroy = function() {
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
				return $input.val();
			};

			this.applyValue = function(item, state) {
				item[args.column.field] = state;
			};

			this.isValueChanged = function() {
				var timeStamp = $input.val();

				return (!(timeStamp == "" && defaultValue == null)) && (timeStamp != defaultValue);
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

		//---------------------------------------------------------------------------- DateTimeEditor
		this.DateTimeEditor = function(args) {
			var $input;
			var defaultValue;
			var scope = this;
			var calendarOpen = false;

			this.init = function() {
				var date;
				if (args.item[args.column.field]) {
					date = new Date();
					date.setTime(args.item[args.column.field]);
				} else {
					date = new Date();
				}

				$input = $("<INPUT type=text class='editor-text' readonly='readonly' />");
				$input.datetimepicker( {
					showButtonPanel : true,
					changeMonth : true,
					changeYear : true,
					showDate : true,
					showHour : true,
					showMinute : true,
					showSecond : true,
					showOtherMonths : true,
					selectOtherMonths : true,
					timeOnly : false,
					dateFormat : 'yy-mm-dd',
					timeFormat : 'hh:mm:ss',
					stepHour : 1,
					stepMinute : 1,
					stepSecond : 1,
					defaultDate : date,
					hour : date.getHours(),
					minute : date.getMinutes(),
					second : date.getSeconds()
				});

				$input.appendTo(args.container);
				$input.focus().select();
				// $input.width($input.width() - 18);
				if (args.column.maxlength) {
					$input.attr("maxlength", args.column.maxlength);
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

				$.datepicker.dpDiv.keydown( function(e) {
					if (e.keyCode == $.ui.keyCode.ESCAPE) {
						args.cancelChanges();
					}
				});
			};

			this.destroy = function() {
				$.datepicker.dpDiv.stop(true, true);
				$input.datepicker("hide");
				$input.datepicker("destroy");
				$input.remove();
			};

			this.show = function() {
				if (calendarOpen) {
					$.datepicker.dpDiv.stop(true, true).show();
				}

			};

			this.hide = function() {
				if (calendarOpen) {
					$.datepicker.dpDiv.stop(true, true).hide();
				}
			};

			this.position = function(position) {
				if (!calendarOpen) {
					return;
				}
				$.datepicker.dpDiv.css("top", position.top + 30).css("left", position.left);
			};

			this.focus = function() {
				$input.focus();
			};

			this.loadValue = function(item) {
				defaultValue = item[args.column.field];

				if (defaultValue) {
					var date = new Date(defaultValue);
					var newVal = Converter.dateFormat('yyyy-MM-dd HH:mm:ss', date);
					var newVal2 = Converter.dateFormat('yyyy/MM/dd HH:mm:ss', date);
					$input.val(newVal);
					$input[0].defaultValue = newVal2;
					$input.select();

				} else {
					$input.val('');
					$input[0].defaultValue = '';
					$input.select();
				}
			};

			this.serializeValue = function() {
				return $input.val();
			};

			this.applyValue = function(item, state) {
				var d = (state + "").replace(/-/g, "/");
				var date = new Date(d);
				item[args.column.field] = date.getTime();
			};

			this.isValueChanged = function() {
				var date = $input.val().replace(/\-/g, '/');
				var timeStamp = (new Date(date)).getTime();

				return (!(timeStamp == "" && defaultValue == null)) && (timeStamp != defaultValue);
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

		//---------------------------------------------------------------------------- TimeEditor
		this.TimeEditor = function(args) {
			var $input;
			var defaultValue;
			var scope = this;
			var calendarOpen = false;

			this.init = function() {
				var date;

				if (args.item[args.column.field]) {
					var t = args.item[args.column.field].split(':');
					date = new Date();
					date.setHours(t[0]);
					date.setMinutes(t[1]);
					date.setSeconds(t[2]);
				} else {
					date = new Date();
				}

				$input = $("<INPUT type=text class='editor-text' readonly='readonly' />");
				$input.datetimepicker( {
					showButtonPanel : true,
					showHour : true,
					showMinute : true,
					showSecond : true,
					timeOnly : true,
					timeFormat : 'hh:mm:ss',
					stepHour : 1,
					stepMinute : 1,
					stepSecond : 1,
					defaultDate : date,
					hour : date.getHours(),
					minute : date.getMinutes(),
					second : date.getSeconds()
				});

				$input.appendTo(args.container);
				$input.focus().select();
				if (args.column.maxlength) {
					$input.attr("maxlength", args.column.maxlength);
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
			};

			this.destroy = function() {
				$.datepicker.dpDiv.stop(true, true);
				$input.datepicker("hide");
				$input.datepicker("destroy");
				$input.remove();
			};

			this.show = function() {
				if (calendarOpen) {
					$.datepicker.dpDiv.stop(true, true).show();
				}
			};

			this.hide = function() {
				if (calendarOpen) {
					$.datepicker.dpDiv.stop(true, true).hide();
				}
			};

			this.position = function(position) {
				if (!calendarOpen) {
					return;
				}
				$.datepicker.dpDiv.css("top", position.top + 30).css("left", position.left);
			};

			this.focus = function() {
				$input.focus();
			};

			this.loadValue = function(item) {
				var date;
				defaultValue = item[args.column.field];
				if (defaultValue) {
					var t = defaultValue.split(':');
					date = new Date();
					date.setHours(t[0]);
					date.setMinutes(t[1]);
					date.setSeconds(t[2]);
				} else {
					date = new Date();
				}

				if (defaultValue) {
					var date = new Date(date);
					var newVal = Converter.dateFormat('HH:mm:ss', date);
					$input.val(newVal);
					$input[0].defaultValue = newVal;
					$input.select();

				} else {
					$input.val('');
					$input[0].defaultValue = '';
					$input.select();
				}
			};

			this.serializeValue = function() {
				return $input.val();
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

				return {
					valid : true,
					msg : null
				};
			};

			this.init();
		};

		//---------------------------------------------------------------------------- RadioEditor
		this.RadioEditor = function(args) {
			var $select;
			var defaultValue;
			var scope = this;

			this.init = function() {
				$select = $("<INPUT type=radio value='true' class='editor-checkbox' name='" + args.column.field + "'>");
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
				defaultValue = item[args.column.field] ? true : false;
				if (defaultValue) {
					$select.attr("checked", "checked");
				} else {
					$select.removeAttr("checked");
				}
			};

			this.serializeValue = function() {
				return $select.is(":checked");
			};

			this.applyValue = function(item, state) {
				var chk = state ? true : false;
				var dv = args.grid.getData();
				var items = dv.getItems();
				for ( var i = 0; i < items.length; i++) {
					items[i][args.column.field] = false;
					dv.updateItem(items[i].id, items[i]);
				}
				item[args.column.field] = chk;
			};

			this.isValueChanged = function() {
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

		//---------------------------------------------------------------------------- Checkbox
		this.Checkbox = function(args) {
			var $select;
			var defaultValue;
			var scope = this;

			this.init = function() {
				$select = $("<INPUT type=checkbox value='true' class='editor-checkbox' hideFocus>");
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
				} else {
					$select.removeAttr("checked");
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

		//---------------------------------------------------------------------------- TextEditor
		this.TextEditor = function(args) {
			var $input;
			var defaultValue;
			var scope = this;

			this.init = function() {
				$input = $("<INPUT type=text class='editor-text' />").appendTo(args.container).focus().select();
				if (args.column.maxlength) {
					$input.attr("maxlength", args.column.maxlength);
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
			};

			this.destroy = function() {
				$input.remove();
			};

			this.focus = function() {
				$input.focus();
			};

			this.getValue = function() {
				return $input.val();
			};

			this.setValue = function(val) {
				$input.val(val);
			};

			this.loadValue = function(item) {
				defaultValue = item[args.column.field] || "";
				$input.val(defaultValue);
				$input[0].defaultValue = defaultValue;
				$input.select();
			};

			this.serializeValue = function() {
				return $input.val();
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

				return {
					valid : true,
					msg : null
				};
			};

			this.init();
		};

		//---------------------------------------------------------------------------- IntegerEditor
		this.IntegerEditor = function(args) {
			var $input;
			var defaultValue;
			var scope = this;

			this.init = function() {
				$input = $("<INPUT type=text class='editor-text' />");

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

		//---------------------------------------------------------------------------- NumericEditor
		this.NumericEditor = function(args) {
			var $input;
			var defaultValue;
			var scope = this;
			var totalLength, decimalLength;

			this.init = function() {
				$input = $("<INPUT type=text class='editor-text' />");
				if (args.column.maxlength) {
					totalLength = args.column.maxlength.totalLength;
					decimalLength = args.column.maxlength.decimalLength;
					$input.ForceNumericOnly(true, totalLength, decimalLength);
				} else {
					$input.ForceNumericOnly(true);
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
				var v = (!isNaN($input.val()) || $input.val()) ? $input.val() : 0;
				return parseFloat(common.Converter.numberFormat("0.00####", v));
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
						msg : "Please enter a valid Numeric"
					};
				}

				return {
					valid : true,
					msg : null
				};
			};

			this.init();
		};

		/*
		 * An example of a "detached" editor. The UI is added onto document BODY
		 * and .position(), .show() and .hide() are implemented. KeyDown events
		 * are also handled to provide handling for Tab, Shift-Tab, Esc and
		 * Ctrl-Enter.
		 */
		this.LongTextEditor = function(args) {
			var $input, $wrapper;
			var defaultValue;
			var scope = this;

			this.init = function() {
				var $container = $("body");

				$wrapper = $(
						"<DIV style='z-index:10000;position:absolute;background:white;padding:5px;border:3px solid gray; -moz-border-radius:10px; border-radius:10px;'/>")
						.appendTo($container);

				$input = $(
						"<TEXTAREA hidefocus rows=5 style='backround:white;width:250px;height:80px;border:0;outline:0'>")
						.appendTo($wrapper);

				$("<DIV style='text-align:right'><BUTTON>Save</BUTTON><BUTTON>Cancel</BUTTON></DIV>")
						.appendTo($wrapper);

				$wrapper.find("button:first").bind("click", this.save);
				$wrapper.find("button:last").bind("click", this.cancel);
				$input.bind("keydown", this.handleKeyDown);

				scope.position(args.position);
				$input.focus().select();
				if (args.column.maxlength) {
					$input.attr("maxlength", args.column.maxlength);
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
			};

			this.handleKeyDown = function(e) {
				if (e.which == $.ui.keyCode.ENTER && e.ctrlKey) {
					scope.save();
				} else if (e.which == $.ui.keyCode.ESCAPE) {
					e.preventDefault();
					scope.cancel();
				} else if (e.which == $.ui.keyCode.TAB && e.shiftKey) {
					e.preventDefault();
					args.grid.navigatePrev();
				} else if (e.which == $.ui.keyCode.TAB) {
					e.preventDefault();
					args.grid.navigateNext();
				}
			};

			this.save = function() {
				args.commitChanges();
				args.grid.resetActiveCell();// for fix bug after click save will
				// goto next cell
			};

			this.cancel = function() {
				$input.val(defaultValue);
				args.cancelChanges();
			};

			this.hide = function() {
				$wrapper.hide();
			};

			this.show = function() {
				$wrapper.show();
			};

			this.position = function(position) {
				$wrapper.css("top", position.top - 5).css("left", position.left - 5)
			};

			this.destroy = function() {
				$wrapper.remove();
			};

			this.focus = function() {
				$input.focus();
			};

			this.loadValue = function(item) {
				$input.val(defaultValue = item[args.column.field]);
				$input.select();
			};

			this.serializeValue = function() {
				return $input.val();
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
				return {
					valid : true,
					msg : null
				};
			};

			this.init();
		};

		this.DepartmentEditor = function(args) {
			var $input;
			var $binImg;
			var $searchImg;
			var defaultValue;
			var scope = this;
			var calendarOpen = false;

			this.init = function() {
				$input = $(
						"<INPUT id='departmentName' class='editor-text' readonly='readonly' style='float: left; width: 75%;' />")
						.attr("departmentCode", args.item.departmentCode ? args.item.departmentCode : "");
				$input.appendTo(args.container);

				$binImg = $("<img src='/imart/images/icons/16x16/fugue-icons/shadow/cross-script.png' border='0' style='float: right; margin: 3px; cursor: pointer;' />");
				$binImg.appendTo(args.container);
				$binImg.bind("click", function() {
					$binImg.prev("input").val("");
					$input.attr("departmentCode", "");
					args.item.departmentName = null;
					args.item.departmentCode = null;
				})

				$searchImg = $("<img src='/imart/images/icons/16x16/fugue-icons/shadow/magnifier.png' style='float: right; margin: 3px; cursor: pointer;' />");
				$searchImg.appendTo(args.container);
				$searchImg.bind("click", function() {
					openCompanyWindow();
				})

				$input.focus().select();
			};

			this.destroy = function() {
				$input.remove();
				$binImg.remove();
				$searchImg.remove();
			};

			this.focus = function() {
				$input.focus();
			};

			this.loadValue = function(item) {
				defaultValue = item[args.column.field];
				$input.val(item["departmentName"]);
				$input[0].defaultValue = defaultValue;
				$input.select();
			};

			this.serializeValue = function() {
				return $input.val();
			};

			this.applyValue = function(item, state) {
				item[args.column.field] = state;
				item["departmentCode"] = $input.attr("departmentCode");
				item["departmentName"] = $input.val();
			};

			this.isValueChanged = function() {
				var departmentCode = $input.attr("departmentCode");
				return (!(departmentCode == "" && defaultValue == null)) && (departmentCode != defaultValue);
			};

			this.validate = function() {
				return {
					valid : true,
					msg : null
				};
			};

			this.init();
		};

		/**
		 * Mandatory Attribute
		 * <ul>
		 * <li>ref : array of object that contain name of attribute and index
		 * ex. [{"id":"itemDescription","index":1}]</li>
		 * <li>columns : array of object that defined header name ex. [{name:
		 * 'Item Code', width: '100px'}, {name: 'Item Description',
		 * width:'350px'}]</li>
		 * <li>source : function that return data or data object ex. [['Item
		 * AA','Dell notebook 14"']]</li>
		 * </ul>
		 * 
		 * ex. source function <br>
		 * <pre>
		 * function sourceItemCodesAjax(request, response) {
				$.getJSON(
					contextPath + "item/getItemInfoList/",
					{
						itemCode : request.term,
						itemDescription : request.term
					},
					function(data) {
						var source = [];
						if (data.itemList) {
							$.each(data.itemList, function(i, item) {
								var ary = new Array();
								ary.push(item.itemCode, item.itemDescription);
								source.push(ary);
							});
						}
						response(source);
					}
				);
			}
			</pre>
		 */
		this.McAutoCompleteEditor = function(args) {
			var $input;
			var defaultValue;
			var scope = this;
			var selectedItem = {};

			this.init = function() {
				$input = $("<INPUT id='tags' class='editor-text' />");

				if (args.column.maxlength) {
					$input.attr("maxlength", args.column.maxlength);
				}

				$input.appendTo(args.container);
				$input.focus().select();
				$input.mcautocomplete( {
					showHeader : true,
					columns : args.column.columns,
					source : args.column.source,
					select : function(event, ui) {
						this.value = (ui.item ? ui.item[0] : '');
						selectedItem = ui.item;
						return false;
					}
				});
			};

			this.destroy = function() {
				$input.mcautocomplete("destroy");
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
				return $input.val();
			};

			this.applyValue = function(item, state) {
				item[args.column.field] = state;
				if (args.column.ref) {
					$.each(args.column.ref, function(i, ref) {
						item[ref.id] = selectedItem[ref.index];
					});
				}
			};

			this.isValueChanged = function() {
				return (!($input.val() == "" && defaultValue == null)) && ($input.val() != defaultValue);
			};

			this.validate = function() {
				return {
					valid : true,
					msg : null
				};
			};

			this.init();
		};

		/**
		 * option
		 *  - uploadFormId : name of form that want to append input file
		 */
		this.FileEditor = function(args) {
			var $input;
			var defaultValue;
			var scope = this;
			var $uploadForm;

			this.init = function() {
				if (args.column.uploadFormId) {
					$uploadForm = $("#" + args.column.uploadFormId);
				} else {
					$uploadForm = $("#uploadForm");
				}
				if (args.item[args.column.field]) {
					$(args.container).append(
							"<div style='float: left; width:90%'>" + args.item[args.column.field] + "</div>")
					$(
							"<img src='/imart/images/icons/16x16/fugue-icons/shadow/cross-script.png' border='0' style='float: right; margin: 3px; cursor: pointer;' />")
							.bind(
									"click",
									function(e) {
										args.item[args.column.field] = "";
										$(args.item[args.column.field + "input"]).remove();
										$input = $("<INPUT type=file name='formFiles' class='editor-text' />")
												.appendTo($(args.container).empty()).focus().select();
									}).appendTo(args.container);
				} else {
					$input = $("<INPUT type=file name='formFiles' class='editor-text' />").appendTo(args.container)
							.focus().select();
				}
			};

			this.destroy = function() {
				if ($input && !$input.val()) {
					$input.remove();
				} else {
					$(args.container).empty();
				}
			};

			this.focus = function() {
				$input.focus();
			};

			this.getValue = function() {
				return $input.val();
			};

			this.setValue = function(val) {
				$input.val(val);
			};

			this.loadValue = function(item) {

			};

			this.serializeValue = function() {
				if ($input)
					return $input.val();
				else
					return args.item[args.column.field];
			};

			this.applyValue = function(item, state) {
				if ($input && $input.val()) {
					item[args.column.field] = state;
					item[args.column.field + "input"] = $input;
					$uploadForm.append($input);
				}
			};

			this.isValueChanged = function() {
				return ($input && !($input.val() == "" && defaultValue == null)) && ($input.val() != defaultValue);
			};

			this.validate = function() {
				return {
					valid : true,
					msg : null
				};
			};

			this.init();
		};

		/**
		 * Pop-up dialog when click on the specified column.
		 * 
		 * @param editorArgs - object of arguments that used for create dialog. editorArgs object consists of following
		 *            properties<br>
		 *            <ul>
		 *            <li>dialogContent: content to display in dialog</li>
		 *            <li>setFields: the array of properties name included in the parent grid data; get the selected row data from dialog 
		 *            and set values to these fields of parent grid.
		 *            <li>width: dialog width</li>
		 *            <li>height: dialog height</li>
		 *            </ul>
		 * 
		 * <u>For example</u><br>
		 * Write dialog content in JSP file.<br>
		 * 
		 * <pre>
		 * {@code
		 * <div id='masterItemInfoDialog' title='Master Item Information' />' style='display: none;'>
		 * 		<div class='grid-header'>
		 * 			<label>Master Item Information</label>
		 * 			<label id='masterItemInfoSearchIcon' style='float:right; margin-right:10px'>Search</label>
		 * 		<span id='masterItemInfoSearchIcon' class='ui-icon ui-icon-search'  style='float:right' title='Search panel'></span>
		 * 		</div>
		 * 		<div>
		 * 			<div id='masterItemInfoGrid' class='grid-detail'
		 * 				style='width: 99.8%; height: 350px; overflow: hidden; outline: 0px none; position: relative;'></div>
		 * 		<div>
		 * </div>
		 * }
		 * </pre>
		 * 
		 * Create grid in javascript file.<br>
		 * 
		 * <pre>
		 * columns = [ {
		 * 	id : 'itemCategoryCode',
		 * 	name : 'Item Category',
		 * 	field : 'itemCategoryCode',
		 * 	editor : common.SlickGrid.Editor.DialogEditor,
		 * 	editorArgs : {
		 * 		dialogContent : $('#masterItemInfoDialog'),
		 * 		setFields : ["itemCategoryId", "itemCategoryCode", "itemId", "itemCode", "itemDescription", "uomId", "uomCode"],
		 * 		width : 700,
		 * 		height : 500
		 * 	}
		 * } ]
		 * </pre>
		 */
		this.DialogEditor = function(args) {
			var $input;
			var defaultValue = {};
			var scope = this;
			var grid;
			var isCancel = false;

			this.init = function() {
				$input = $("<INPUT type=text class='editor-text' readonly='readonly' />").appendTo(args.container)
						.focus().select();

				var editorArgs = args.column.editorArgs;
				var $dialogContent = editorArgs.dialogContent;
				grid = eval($dialogContent.find("div.grid-detail").attr("id"));

				$.ui.dialog.overlay.events = '';
				//grid.reloadData();
				
				if(editorArgs.buttons == undefined){
					editorArgs.buttons = {
						Select : function() {
							var selectedRowIndex = grid.getGrid().getSelectedRows();

							if (selectedRowIndex.length > 0) {
								var selectedRow = grid.getDataView().getItem(selectedRowIndex);
								$input.val(selectedRow[args.column.field]).focus();
								args.grid.getEditorLock().activate(args.grid.getEditController());
								args.grid.getEditController().commitCurrentEdit();
								$(this).dialog("close");

							} else {
								alert("Please select at least one row.");
							}
						},
						Cancel : function() {
							isCancel = true;
							args.grid.getEditorLock().activate(args.grid.getEditController());
							$(this).dialog("close");
						}
					};
				}
				
				$dialogContent.dialog( {
					bgiframe : true,
					modal : true,
					autoOpen : false,
					height : editorArgs.height,
					width :  editorArgs.width,
					resizable : false,
					buttons : editorArgs.buttons
				});

				grid.getGrid().onDblClick.subscribe( function() {
					var selectedRowIndex = grid.getGrid().getSelectedRows();
					var selectedRow = grid.getDataView().getItem(selectedRowIndex);
					$input.val(selectedRow[args.column.field]).focus();

					isCancel = false;
					args.grid.getEditorLock().activate(args.grid.getEditController());
					$dialogContent.dialog("close");
				});

				args.grid.getEditorLock().deactivate(args.grid.getEditController());
				$dialogContent.dialog("open");
				grid.getGrid().hideHeaderRowColumns();
				grid.resetFilter();

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
				args.grid.getEditorLock().activate(args.grid.getEditController());
				args.column.editorArgs.dialogContent.dialog("close");
			};

			this.focus = function() {
				$input.focus();
			};

			this.getValue = function() {
				return $input.val();
			};

			this.setValue = function(val) {
				$input.val(val);
			};

			this.loadValue = function(item) {
				$.extend(defaultValue, item);
				$input.val(defaultValue[args.column.field]);
				$input[0].defaultValue = defaultValue[args.column.field];
				$input.select();
			};

			this.serializeValue = function() {
				var selectedRowIndex = grid.getGrid().getSelectedRows();

				if (selectedRowIndex.length > 0) {
					var selectedRow = grid.getDataView().getItem(selectedRowIndex);
					var serializeValue = {};

					$.each(args.column.editorArgs.setFields, function(index, field) {
						serializeValue[field] = selectedRow[field];
					});
				}

				return serializeValue;
			};

			this.applyValue = function(item, state) {
				$.each(state, function(key, value) {
					item[key] = state[key];
				});
			};

			this.isValueChanged = function() {
				var isChanged = false;
				var selectedRowIndex = grid.getGrid().getSelectedRows();

				if (!isCancel && selectedRowIndex.length > 0) {
					var selectedRow = grid.getDataView().getItem(selectedRowIndex);

					$.each(selectedRow, function(key, value) {
						if (grid.getKey() != key.toString()) {
							if (defaultValue[key] != value) {
								isChanged = true;
								return false;
							}
						}
					});
				}

				return (!($input.val() == "" && defaultValue == null)) && isChanged;
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
		 * Display drop-down in grid according to the specified data.
		 * 
		 * @param editorArgs - object of arguments that used for create drop-down. editorArgs object consists of following
		 *            properties<br>
		 *            <ul>
		 *            <li>dataList: data to display in dialog</li>
		 *            <li>value: the name of a property included in the <code>dataList</code>, and is used to retrieve the
		 *            value that will be returned to the server if this option is selected.</li>
		 *            <li>label: the name of a property included in the <code>dataList</code>, and is used to retrieve the
		 *            label that will be displayed to the user for this option</li>
		 *            </ul>
		 * 
		 * <u>For example</u>
		 * <pre>
		 * columns = [ {
		 * 		id : 'lineTypeCode',
		 * 		name : 'Line Type',
		 * 		field : 'lineTypeCode',
		 * 		editor : common.SlickGrid.Editor.DropDownEditor,
		 * 		editorArgs : {
		 * 			dataList : [{lineTypeId: 1, lineTypeCode: 'LineTypeA', lineTypeDescription: 'Line Type A' },
		 * 				 		{lineTypeId: 2, lineTypeCode: 'LineTypeB', lineTypeDescription: 'Line Type B' },
		 * 				 		{lineTypeId: 3, lineTypeCode: 'LineTypeC', lineTypeDescription: 'Line Type C' },
		 * 				 		{lineTypeId: 4, lineTypeCode: 'LineTypeD', lineTypeDescription: 'Line Type D' }],
		 * 			value : 'lineTypeId',
		 * 			label : 'lineTypeCode'
		 * 		}
		 * } ]
		 * </pre>
		 */
		this.DropDownEditor = function(args) {
			var $select;
			var defaultValue;
			var scope = this;

			this.init = function() {
				$select = $("<select></select>").addClass("editor-select");
				$select.append($("<option></option>").val("").html(""));

				$.each(args.column.editorArgs.dataList, function(index, item) {
					$select.append($("<option></option>").val(item[args.column.editorArgs.value]).html(
							item[args.column.editorArgs.label]));
				});

				$select.appendTo(args.container);
				$select.focus();

				//---------------------------------------------------------------- nav.
				$select.bind("keydown.nav",
						function(e) {
							if (e.keyCode == $.ui.keyCode.ENTER) {
								args.grid.navigateNext();
								e.stopImmediatePropagation();
							} else if (e.keyCode === $.ui.keyCode.LEFT && $(this).getCursorPosition() == 0) {
								args.grid.navigatePrev();
								e.stopImmediatePropagation();
							} else if (e.keyCode === $.ui.keyCode.RIGHT
									&& $(this).getCursorPosition() == $select.val().length) {
								args.grid.navigateNext();
								e.stopImmediatePropagation();
							} else if (e.keyCode === $.ui.keyCode.LEFT || e.keyCode === $.ui.keyCode.RIGHT) {
								e.stopImmediatePropagation();
							}
						});
				//---------------------------------------------------------------- nav.	
			};

			this.destroy = function() {
				$select.remove();
			};

			this.focus = function() {
				$select.focus();
			};

			this.loadValue = function(item) {
				defaultValue = item[args.column.editorArgs.value];
				$select.val(defaultValue);
			};

			this.serializeValue = function() {
				return {
					value : $select.val(),
					label : $select.find("option:selected").text()
				};
			};

			this.applyValue = function(item, state) {
				item[args.column.editorArgs.value] = state.value;
				item[args.column.editorArgs.label] = state.label;
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
		}
	}

	$.extend((window.common || (window.common = {
		"SlickGrid" : {}
	})).SlickGrid, {
		"Editor" : new Editor()
	});
});