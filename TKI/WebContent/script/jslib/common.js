	$(document).ready(function(){

		$('body').ready(function() {
			$("#preparing-file-modal-page").hide();
			$("#preparing-file-modal-page-img").hide();
		});
		
		$('form').submit(function() {
			if(!this.action.contains('.xls') && !this.action.contains('export')){
				$("#preparing-file-modal-page").show();
				$("#preparing-file-modal-page-img").show();
			}
		});
		
		message = new Message();
		DateUtil = new DateUtil();
		var pageNumber 		= $("#pageNumber");

		// <!-- Initial the Date Picker. -->
		$("input.date").each(function(){
			DateUtil.setDatepicker($(this));
		});
		
		// <!-- Set event input only number -->
		$("input.posInt").keypress(function(event){
			return inputText.filterInput(1, event, false);
		});

		// <!-- If you don't want this event, use unbind('change') -->
		$("select[id=pageCount]").change(function(){
			comboBox.changePageCount($(this));
		});

		$("select[id=pageNumber]").change(function(){
			comboBox.changePageNumber($(this));
		});	

		$("a[id=firstPage]").click(function(){
			comboBox.firstLastPageNumber(pageNumber,"first");
		});
		
		$("a[id=prevPage]").click(function(){
			comboBox.prevPageNumber(pageNumber);
		});

		$("a[id=nextPage]").click(function(){
			comboBox.nextPageNumber(pageNumber);
		});
		
		$("a[id=lastPage]").click(function(){
			comboBox.firstLastPageNumber(pageNumber, "last");
		});
	}).keydown(function(e){

		var element  = e.target.nodeName.toLowerCase();
		var readOnly = e.target.readOnly == true;
		var disabled = e.target.disabled == true;

		if( element != 'input' && element != 'textarea' ) {
			if( e.keyCode === 8 ) return false;
		} else {
			if( readOnly || disabled ) return false;
		}
	});

	function showLoading(){
		$("#preparing-file-modal").show();
		$("#preparing-file-modal-img").show();
	}

	function hideLoading(){
		$("#preparing-file-modal").hide();
		$("#preparing-file-modal-img").hide();
		$("#preparing-file-modal-page").hide();
		$("#preparing-file-modal-page-img").hide();
	}
	
	var comboBox = new ComboBox(); 
	function ComboBox(){

	    this.setCustomer = function( selectElement ) {
			if( !selectElement || !selectElement.exists() )
				return;

			getJSON("boxCustomer1",{},function(result){
				selectElement.empty();
				$.each(result,function(val, text){
					selectElement.append( $("<option></option>").val(val).html(text) );
				});
			});
		};

	    this.setMachineName = function( selectElement ) {
			if( !selectElement || !selectElement.exists() )
				return;

			var params = {};
			for( var i = 1; i < arguments.length; i++ ) {
				var arg = arguments[i];
				params[ arg.attr("name") ] = arg.val(); 
			}
			getJSON("machineList",params,function(result){
				selectElement.find("option:not(:first)").remove();
				$.each(result,function(index, item){
					selectElement.append( $("<option></option>").val(item.machineId).html(item.machineName) );
				});
			});
		};
		
		this.setMachineNameActive = function( selectElement ) {
			if( !selectElement || !selectElement.exists() )
				return;

			var params = {};
			for( var i = 1; i < arguments.length; i++ ) {
				var arg = arguments[i];
				params[ arg.attr("name") ] = arg.val(); 
			}
			getJSON("machineListActive",params,function(result){
				selectElement.find("option:not(:first)").remove();
				$.each(result,function(index, item){
					selectElement.append( $("<option></option>").val(item.machineId).html(item.machineName) );
				});
			});
		};

	    this.setMachineNo = function( selectElement ) {
			if( !selectElement || !selectElement.exists() )
				return;

			var params = {};
			for( var i = 1; i < arguments.length; i++ ) {
				var arg = arguments[i];
				params[ arg.attr("name") ] = arg.val(); 
			}
			getJSON("machineList",params,function(result){
				selectElement.find("option:not(:first)").remove();

				var machineNoMap = {}; 
				$.each(result,function(index, item){
					if( !machineNoMap[item.machineNo] ) {

						selectElement.append( $("<option></option>").val(item.machineId).html(item.machineNo) );
						machineNoMap[item.machineNo] = true;
					}
				});
			});
		};

	    this.setPartNo = function( selectElement, wip, customerId ) {
			if( !selectElement || !selectElement.exists() )
				return;

			var params = { "customerId": "", "wip": "" };
			if( customerId ) params.customerId = customerId;
			if( wip )        params.wip        = wip;
			getJSON("boxPartNo",params,function(result){
				selectElement.empty();
				$.each(result,function(val, text){
					selectElement.append( $("<option></option>").val(val).html(text) );
				});
			});
		};

	    this.setPartNameNo = function( selectElement ) {
			if( !selectElement || !selectElement.exists() )
				return;

			var params = {};
			for( var i = 1; i < arguments.length; i++ ) {
				var arg = arguments[i];
				params[ arg.attr("name") ] = arg.val(); 
			}
			getJSON("boxPartNameNo",params,function(result){
				selectElement.empty();
				$.each(result,function(val, text){
					selectElement.append( $("<option></option>").val(val).html(text) );
				});
			});
		};

		this.setReasonCat = function( selectElement ) {
			if( !selectElement || !selectElement.exists() )
				return;

			var params = {};
			for( var i = 1; i < arguments.length; i++ ) {
				var arg = arguments[i];
				params[ arg.attr("name") ] = arg.val(); 
			}
			getJSON("boxReasonCat",params,function(result){
				selectElement.empty();
				$.each(result,function(val, text){
					selectElement.append( $("<option></option>").val(val).html(text) );
				});
			});
		};

		this.setReasonInCat = function( selectElement, wip, parentReasonId ) {
			if( !selectElement || !selectElement.exists() )
				return;

			if( wip && parentReasonId )
				getJSON("boxReasonInCat",{ "parentReasonId": parentReasonId, "wip": wip },function(result){
					selectElement.empty();
					$.each(result,function(val, text){
						selectElement.append( $("<option></option>").val(val).html(text) );
					});
				});
		};

		this.setReasonNG = function( selectElement, wip ) {
			if( !selectElement || !selectElement.exists() )
				return;

			var params = {};
			for( var i = 1; i < arguments.length; i++ ) {
				var arg = arguments[i];
				params[ arg.attr("name") ] = arg.val(); 
			}
			getJSON("boxReasonNG",params,function(result){
				selectElement.empty();
				$.each(result,function(val, text){
					selectElement.append( $("<option></option>").val(val).html(text) );
				});
			});
		};

		this.changePageCount = function( selectElement ) {
			if( !selectElement || !selectElement.exists() )
				return;
			
			var count = selectElement.val();
			document.forms[0].reset();
			$("select[id=pageNumber] option:first").attr("selected", true);
			$("select[id=pageCount]").val(count);
			var formObject = eval($("form").attr("id"));
			formObject.submit();
		};

		this.changePageNumber = function( selectElement ) {
			if( !selectElement || !selectElement.exists() )
				return;
			
			var number = selectElement.val();
			document.forms[0].reset();
			$("select[id=pageNumber]").val(number);
			var formObject = eval($("form").attr("id"));
			formObject.submit();
		};

		this.firstLastPageNumber = function( selectElement, pos ) {
			if( !selectElement || !selectElement.exists() )
				return;

			selectElement.find("option:selected").removeAttr('selected')
				.end().find("option:"+pos).attr('selected', 'selected').change();
		};
		
		this.prevPageNumber = function( selectElement ) {
			if( !selectElement || !selectElement.exists() )
				return;

			selectElement.find("option:selected").removeAttr('selected')
				.prev('option').attr('selected', 'selected').change();
		};

		this.nextPageNumber = function( selectElement ) {
			if( !selectElement || !selectElement.exists() )
				return;
			
			selectElement.find("option:selected").removeAttr('selected')
				.next('option').attr('selected', 'selected').change();
		};
		
	    return this;
	};
	
	var inputText = new inputText();
	function inputText(){
		this.checkError = function(form){
			var noErrors = true;
			var errMsg = new Array();
			form.find(".chk").each(function(){
				var r = $(this);
				if (r.hasClass("mandatory")) {
					if (!r.val() ||
						((r.attr("type") == 'radio' || r.attr("type") == 'checkbox') 
								&& $("input[id="+r.attr("id")+"]:checked").length == 0) ) {
						errMsg[errMsg.length] = r.data("displayName");						
						return false;
					}
				}
			});
			return errMsg;
		};
		
		/*==========================================================================#
		# * Function for adding a Filter to an Input Field                          #
		# * @param  : [filterType  ] Type of filter 0=>Alpha, 1=>Num, 2=>AlphaNum   #
		# * @param  : [evt         ] The Event Object                               #
		# * @param  : [allowDecimal] To allow Decimal Point set this to true        #
		# * @param  : [allowCustom ] Custom Characters that are to be allowed       #
		example;
		  filterInput(1,event,true);
		#==========================================================================*/
		this.filterInput = function(filterType, evt, allowDecimal, allowCustom){
		    var keyCode, Char, inputField, filter = '';
		    var alpha = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
		    var num   = '0123456789';
		    // Get the Key Code of the Key pressed if possible else - allow
		    if(window.event){
		        keyCode = window.event.keyCode;
		        evt = window.event;
		    }else if (evt)keyCode = evt.which;
		    else return true;
		    // Setup the allowed Character Set
		    if(filterType == 0) filter = alpha;
		    else if(filterType == 1) filter = num;
		    else if(filterType == 2) filter = alpha + num;
		    if(allowCustom)filter += allowCustom;
		    if(filter == '')return true;
		    // Get the Element that triggered the Event
		    inputField = evt.srcElement ? evt.srcElement : evt.target || evt.currentTarget;
		    // If the Key Pressed is a CTRL key like Esc, Enter etc - allow
		    if((keyCode==null) || (keyCode==0) || (keyCode==8) || (keyCode==9) || (keyCode==13) || (keyCode==27) )return true;
		    // Get the Pressed Character
		    Char = String.fromCharCode(keyCode);
		    // If the Character is a number - allow
		    if((filter.indexOf(Char) > -1)) return true;
		    // Else if Decimal Point is allowed and the Character is '.' - allow
		    else if(filterType == 1 && allowDecimal && (Char == '.') && inputField.value.indexOf('.') == -1)return true;
			else if(filterType == 1 && allowDecimal && (Char == '-') && inputField.value.indexOf('-') == -1)return true;
		    else return false;
		};
		
		// check maxlength of textarea
		this.textareaMaxLength = function(selectElement,maxLength){
			selectElement.keyup(function(){
		       var max = maxLength || 500;
		       var txa = $(this);
		       if(txa.val().length > max){
		          txa.val(txa.val().substr(0, max));
		       }
			});
		};
		
		return this;
	};
	
	var message;
	function Message(){
		this.elmMessages = $("ul#infMessages").closest("div"),
		this.infMessages = $("ul#infMessages"),
		this.errMessages = $("ul#errMessages"),

		this.setError = function(code, args){
			if (code) {
				this.errMessages.append( $("<li/>").html(this.getMessage(code, args)) );
				this.elmMessages.css("display", "");
			}
			hideLoading();
		};

		this.setErrors = function(errors){
			this.clear();
			if (errors && errors.constructor == Array.prototype.constructor) {
				for (i in errors) {
					this.setError(errors[i].code,errors[i].arguments);
				}
			}

			$("html, body").animate({scrollTop:0}, "fast");
			hideLoading();
		};

		this.setInfo = function(code, args){
			if (code) {
				this.infMessages.append( $("<li/>").html(this.getMessage(code, args)) );
				this.elmMessages.css("display", "");
			}
			hideLoading();
		};

		this.setInfos = function(infos){
			this.clear();
			if (infos && infos.constructor == Array.prototype.constructor) {
				for (i in infos) {
					this.setInfo(infos[i].code,infos[i].arguments);
				}
			}

			$("html, body").animate({scrollTop:0}, "fast");
			hideLoading();
		};

		this.getMessage = function(code, args){
			var msg = "";
			if (code) {
				var msg = top.messageCodes[code];
				if (args) {
					for (i=0;i<args.length;i++) {
						msg = msg.replace("{"+i+"}", args[i]);
					}
				}
			}
			return msg;
		};
		
		this.showMessage = function(bean) {
			if($(bean.errors).exists()){
				this.setErrors( bean.errors );
			}
			if($(bean.infos).exists()){
				this.setInfos( bean.infos );
			}
		};
		
		this.showAllMessage = function(bean) {
			message.clear();
			if($(bean.infos).exists()){
				var infos = bean.infos ;
				if (infos && infos.constructor == Array.prototype.constructor) {
					for (i in infos) {
						this.setInfo(infos[i].code,infos[i].arguments);
					}
				}
			}
			if($(bean.errors).exists()){
				var errors = bean.errors;	
				if (errors && errors.constructor == Array.prototype.constructor) {
					for (i in errors) {
						this.setError(errors[i].code,errors[i].arguments);
					}
				}		
			}
			
			$("html, body").animate({scrollTop:0}, "fast");
			hideLoading();
		};

		this.clear = function(){
			this.elmMessages.css("display", "none");
			this.errMessages.empty();
			this.infMessages.empty();

			return this;
		};

		this.isNoError = function(){
			$("html, body").animate({scrollTop:0}, "fast");

			return !this.errMessages.children().exists();
		};
		
		return this;
	}
	
	var DateUtil;
	function DateUtil(){
		this.setDatepicker = function(objDate){
			// id in each input text box MUST unique
			objDate.attr("readOnly",true).datepicker({
			  	mandatory: true,
				closeAtTop: true, 
		        dateFormat: 'dd/mm/yy',
		        buttonImage: 'image/icon/calendar.gif',
		        buttonImageOnly: true,
		        showBigPrevNext: true,
		        showOn: 'button',
		        defaultDate: null, 
				prevText: '&lt;',
				nextText: '&gt;',
		        changeYear: true,
		        changeMonth: true,        
		        hideIfNoPrevNext: true
			}).next().before("&nbsp;").after("<img class='delDatepicker' src='image/icon/delete.gif'/>")
			.next().before("&nbsp;").click(function(){
				if( !objDate.attr("disabled") )
					$(this).prev().prev().val("");
					$(this).prev().prev().focus();
			});
		};
		
		this.compare = function(dateFrom,dateTo){
			if (dateFrom.val() && dateTo.val() &&
				dateFrom.datepicker("getDate").getTime() > dateTo.datepicker("getDate").getTime()) {
					message.setError("err.cmm.008", [dateTo.attr("title"),dateFrom.attr("title")]);				
			}
		};
		
		return this;
	}

	function getJSON(uri, params, success) {
		$.get(uri +".html", params,
		function( result ) {
			success( result );
		}, "json");
	}

	function postJSON(uri, params, success) {
		$.post(uri +".html", params,
		function( result ) {
			success( result );
		}, "json");
	}
	
	function postJSONSync(uri, params, success) {
		$.ajax({
	        type: "POST",
			async:false,
	        url: uri +".html",
	        data: params,
	        dataType: "json",
	        success: function(data){success(data);}
	    });
	}

	function postJSONObject(uri, data, success) {
	    return jQuery.ajax({
	        'type': 'POST',
	        'url': uri +".html",
	        'contentType': 'application/json',
	        'data': JSON.stringify(data),
	        'dataType': 'json',
	        'success': success
	    });
	};

	function IntegerFilter( e ) {
		var code = (e.which || e.keyCode);
		var char = String.fromCharCode(code);
		var elmt = $(e.target ? e.target : e.srcElement).val();

		if( code === 8 ) // 'Backspace' key press.
			return true;
		if( code === 9 ) // 'TAB' key press.
			return true;
		if( code !== 8 && ((char === '-' && (elmt.length !== 0)) || "-0123456789".indexOf(char) < 0) )
			return false;
		return true;
	}

	function PositiveIntegerFilter( e ) {
		var code = (e.which || e.keyCode);

		if( code === 8 ) // 'Backspace' key press.
			return true;
		if( code === 9 ) // 'TAB' key press.
			return true;
		if( code !== 8 && "0123456789".indexOf( String.fromCharCode(code) ) < 0 )
			return false;
		return true;
	}

	function DoubleFilter( e ) {
		var cd = (e.which || e.keyCode);
		var ch = String.fromCharCode(cd);
		var vl = $(e.target ? e.target : e.srcElement).val();

		if( cd === 8 ) // 'Backspace' key press.
			return true;
		if( cd === 9 ) // 'TAB' key press.
			return true;
		if( cd !== 8 && ((ch === '-' && (vl.indexOf('-') > -1 || vl.length !== 0)) || (ch === '.' && (vl.length === 0 || vl.indexOf('.') > -1))) || "-.0123456789".indexOf(ch) < 0 )
			return false;
		return true;
	}
	
	function PositiveDoubleFilter( e ){
		var cd = (e.which || e.keyCode);
		var ch = String.fromCharCode(cd);
		var vl = $(e.target ? e.target : e.srcElement).val();

		if( cd === 8 ) // 'Backspace' key press.
			return true;
		if( cd === 9 ) // 'TAB' key press.
			return true;
		if(cd !== 8 && (ch === '.' && (vl.length === 0 || vl.indexOf('.') > -1)) || ".0123456789".indexOf(ch) < 0)
			return false;
		return true;
	}

	function moveList( selectIdFr, selectIdTo ) {
		$("select#"+ selectIdFr +" option:selected").appendTo("select#"+ selectIdTo);

		//<!-- Check: Duplicate. -->
		var parts = {};
		$("select#"+ selectIdTo +" option").each(function(){
			var option = $(this);
			if( parts[option.val()] )
				option.remove();
			else
				parts[option.val()] = true;
		}).removeAttr('selected');
	}

	function downloadNotify( downloadDialog ) {

		downloadDialog.dialog({
			 modal: true
			,show: {effect:'drop', direction:'down'}
			,closeOnEscape: false
			,open: function(event, ui) { $(".ui-dialog-titlebar-close").hide();}
		});
		var downloadCmplt = function() {
			clearInterval(downloadTimer);
			document.cookie = "downloadToken=; expires="+ new Date().toGMTString();
			downloadDialog.dialog('close');
		};
		var downloadTimer = setInterval(function(){
			var isFoundDownloadToken = document.cookie.indexOf("downloadToken") > -1;
			if( isFoundDownloadToken ) {
				downloadCmplt();
			}
		}, 1000);
	}
	
	/**
	This function is used for limit amount of digit for integer and decimal.
	If uses this function with onkeypress. decimalDigit must be minus 1. 
	Sample. 1234567890.1234 can specify filter as onkeypress="return filterInputDecimal(event,9,3).<b> 
	If uses this function with onkeyup. intDigit and decimalDigit don't have to minus 1.
	**/
	function filterInputDecimal(evt, intDigit, decimalDigit){
	    
	    var keyCode, inputChar, inputField;
	    var filter   = '0123456789.';
	    // Get the Key Code of the Key pressed if possible else - allow
	    if(window.event){
	        keyCode = window.event.keyCode;
	        //alert("keyCode : "+keyCode);
	        evt = window.event;
	    }else if (evt)keyCode = evt.which;
	    else return true;

	    // Get the Element that triggered the Event
	    inputField = evt.srcElement ? evt.srcElement : evt.target || evt.currentTarget;
	    
	    // If the Key Pressed is a CTRL key like Esc, Enter etc - allow
	    if((keyCode==null) || (keyCode==0) || (keyCode==8) || (keyCode==9) || (keyCode==13) || (keyCode==27) )return true;
	    
	    // Get the Pressed Character
	    inputChar = String.fromCharCode(keyCode);
	     var inputVal = inputField.value;
	    if(inputChar == '.'){
	        if(inputVal.indexOf('.') == -1){
	    		return true;
	    	}else{
	    		return false;
	    	}
	    }
	  
	    // If the Character is a number - allow
	    if(filter.indexOf(inputChar) > -1) {
	    //alert(inputVal);
	        if(inputVal.indexOf('.') > -1){
	          var strIntDigit = inputVal.substring(0,inputVal.indexOf('.'));
	          var decStartPoint = parseInt(inputVal.indexOf('.'))+1;
	          //alert(decStartPoint);
	          var strDecimalDigit = inputVal.substring(decStartPoint,inputVal.length);
	         // alert(strDecimalDigit);
		          if(strIntDigit.length > (intDigit)){
		          	 return false;
		          }
		           
		          if(strDecimalDigit.length > (decimalDigit)){
		          	 return false;
		          }    
	        }else if(inputVal.length > (intDigit-1)){
	    	//alert('out of integer digit');
	    	return false;
	    	}
	   }else{
	   	return false;
	   }
	}
	// addCommas
	function addCommas(obj)
	{
		obj.value = obj.value.replace(/[,]/g,"");
		nStr = obj.value;
		nStr += '';
		x = nStr.split('.');
		x1 = x[0];
		x2 = x.length > 1 ? '.' + x[1] : '';
		var rgx = /(\d+)(\d{3})/;
		while (rgx.test(x1)) {
			x1 = x1.replace(rgx, '$1' + ',' + '$2');
		}
		obj.value = x1 + x2;
		return obj;
	}
	$.strPad = function(i,l,s) {
		var o = i.toString();
		if (!s) { s = '0'; }
		while (o.length < l) {
			o = s + o;
		}
		return o;
	};
	/**
	 * DO NOT EDIT
	 */
	String.prototype.trim = function() { return this.replace(/^\s*|\s*(\r)?\n/g,""); };
	jQuery.fn.exists = function() { return jQuery(this).length > 0; };