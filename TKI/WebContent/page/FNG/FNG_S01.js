var btnAdd;
var btnOk;
var btnSave;
var trLastRow;
var tblDynamic;
var tblInput;
var reportDate;
var hidRptDate;
var fngForm;
var fgType;
var txtLotSeqOptions;
var customerAndFgObj = [];
var lotSeqNoKeyObj = [];
var isGetDetail = false;

$(function(){
	btnAdd		= $('#btnAdd');
	btnOk		= $("#btnOk");
	btnSave		= $("#btnSave");	
	tblDynamic	= $("#dynamictbl");
	tblInput 	= $("#tblInput");
	reportDate 	= $("#reportDate");
	hidRptDate 	= $("#hidRptDate");
	fngForm 	= $("#fngForm");
	reportType 	= $("#reportType");

	//reportDate.datepicker( "option", "maxDate", '0d' );
	
	$(":radio[id=rdiIn]").change(changeFgInOut);
	changeFgInOut();
	if(btnOk.attr("disabled")){
		reportDate.datepicker("disable");
		reportType.attr("disabled", true);
	}
	
	txtLotSeqOptions = {
			source: function( request, response ) {
				var tr 			= this.element.parent().parent();
				var params 		= { "lotSeqNo": request.term };

				if(request.term.length == 19){
					// add new row after get lotSeq
//					var last = tblDynamic.find('tbody>tr:last');
//					if(!last.find("td").find("input").val() == ''){
//						btnAdd.click();
//					}
					getLotSeqDetail(tr , request.term);
					isGetDetail = true;
				}else{
//					getJSON("FNG_S01_getOnlyLotSeqNo", params, 
//						function(result){
//							response($.map(result,function(item){
//								return { 
//									label: item.lotSeqNo,
//									value: item.lotSeqNo
//								};
//							}));
//					});
				}
			},
			select: function( event, ui ) {
				var tr = $(this).parent().parent();
				getLotSeqDetail(tr , ui.item.value);
				tr.find("#fgId").parent().parent().find("input:gt(2):not(:disabled)").focus();
			},
			
			minLength: 1,
			delay: 1000
		};
	
	
	
	
	//delete row
	$('table#dynamictbl td img.delete').click(function(){
		var rowNo = $("table#dynamictbl td img.delete").index($(this)) + 1;
		if(confirm(message.getMessage("cfm.cmm.003",[rowNo]))){
			if ($('table#dynamictbl td img.delete').length==1) {
				btnAdd.click();
				isGetDetail = false;
			} 

			$(this).parent().parent().remove();
			$("td[id=index]").each(function(i){
				$(this).html((i+1));
			});
		}
		//reset row number 
		var dynamictbl= $("table#dynamictbl");
		dynamictbl.find("tr:gt(1)").each(function(index){
			var td = $(this).find("td:eq(0)");
			td.html(index+1);
		});
		
		getTotalFgIn();
		getTotalFgOut();
	});
			
	//add row
	btnAdd.click(function(){
		var newRow = trLastRow.clone(true);
		tblDynamic.append(
			newRow
				.find("#idx")
				.html($('table#dynamictbl td img.delete').length+1)
				.end()
				.find("#lotSeqNo")
				.unbind("change")
				.change(detailSelection)
				.autocomplete(txtLotSeqOptions)
				.end()
		);
		$("input#lotSeqNo", newRow).focus();
	});

	btnSave.click(function(){
		alert("Save");
		message.clear();
		save();
	});
	
	btnOk.click(function(){
		message.clear();
		if (!reportDate.val()) {
			message.setError("err.cmm.001",["Report Date"]);
		}
		if (!$(":radio[id=rdiIn]:checked").exists()) {
			message.setError("err.cmm.001",["FG Stock Type"]);
		}
		if (message.isNoError()) {
			fngForm.attr("action", "FNG_S01_AddRow.html");
			return true;
		} else
			return false;
		}
	);
	
		// last row
		var last = tblDynamic.find('tbody>tr:last');
		last.find("#customerCode option:first").val("");
		trLastRow = last.clone(true);
		fgType = ($(":radio[id=rdiIn]:checked").val()==="fgin") ? "fgOut" : "fgIn" ;
		trLastRow.find("#"+fgType).attr("disabled", true);
		
		last
			.find("#idx")
			.html(1)
			.end()
			.find("#lotSeqNo")
			.unbind("change")
			.change(detailSelection)
			.autocomplete(txtLotSeqOptions)
			.end();
		last.find("#"+fgType).attr("disabled", true);
		
		$('#lotSeqNo').focus();
});		
	function changeFgInOut(){
		if($(":radio[id=rdiIn]:checked").val()==="fgin"){
			//reportDate.datepicker("option", "minDate", '${minReportDate}');
			reportDate.datepicker( "option", "maxDate", '0d' );
		}else{
			reportDate.datepicker("option", "minDate", null);
			reportDate.datepicker( "option", "maxDate", null );
		}
	}
	function resetRow(){
		$('table#dynamictbl').find('tr:gt(2)').remove();
		btnAdd.click();
		isGetDetail = false;

		$('table#dynamictbl').find('tr:first').next().next().remove();
		var tr = $('table#dynamictbl').find('tr:gt(1)');
		tr.find("td:first").html("1");

		getTotalFgIn();
		getTotalFgOut();
	}
	function getTotalFgIn(){
		var fgInList = document.getElementsByName("fgIn");
		var sumFgIn = 0;
		for(var i=0;i<fgInList.length;i++){
			if(fgInList[i].value == null || fgInList[i].value == undefined){
				fgInList[i].value = 0;
			}
			sumFgIn += (fgInList[i].value*1);
		}
		$('#sumFgIn').text(sumFgIn);
	}
	function getTotalFgOut(){
		var fgOutList = document.getElementsByName("fgOut");
		var sumFgOut = 0;
		for(var i=0;i<fgOutList.length;i++){
			if(fgOutList[i].value == null || fgOutList[i].value == undefined){
				fgOutList[i].value = 0;
			}
			sumFgOut += (fgOutList[i].value*1);
		}
		$('#sumFgOut').text(sumFgOut);
	}
	function checkInput(errors){
		var inputTr = tblDynamic.find("tr:gt(1)");
		var line = 1;
		
		inputTr.each(function(){
			var tr 		= $(this);
			var lotSeqNo= (tr.find("#lotSeqNo").val()).substring(15,19);
			var lotNo 	= tr.find("#lotSeqNo").val().substring(0,14); 
			var fgIn 	= parseInt(tr.find("#fgIn").val(),10);
			var	fgQty 	= parseInt(tr.find("#fgIn[value!=''],#fgOut[value!='']").val(),10);
			var fgOut 	= parseInt(tr.find("#fgOut").val(),10);
			var lotQty 	= parseInt(tr.find("#lotQty").html(),10);
				
//			tr.find("input:text:not(:disabled + #lotSeqNo),select").each(function(){
//				if((lotSeqNo != "") || (line != inputTr.length)){
//					if (!this.value){
//						errors.push({"code":"err.cmm.002","arguments":[line,$(this).attr("title")]});
//					}	
//				}
//			});
			if((lotSeqNo != "") || (line != inputTr.length)){
				if (fgQty == 0) {
					errors.push({"code":"err.cmm.009","arguments":[line,"Stock Qty In/Out"]});
				} else if (!isNaN(lotQty) && lotQty < fgQty) {
					errors.push({"code":"err.fng.001","arguments":[line]});
				}
			}
			line++;
		});
	}

	function chkNumberFormat(errors){
		var inputTr = tblDynamic.find("tr:gt(1)");
		var fgType = ($(":radio[id=rdiIn]:checked").val()==="fgin") ? "fgIn" : "fgOut" ;
		
		inputTr.each(function(index1){
			var tr1 	= $(this);
			var fgInOut1 	= tr1.find("#"+fgType).val();
			
			regex = /^\-?\d{0,9}$/;
			if(!regex.test(fgInOut1)) {
				errors.push({"code":"err.cmm.019","arguments":[(index1+1),"FG In/Out"]});
			}
			
		});
	}
	
	function checkDupLotNoScreen(errors){
		var lotList = {};
		var inputTr = tblDynamic.find("tr:gt(1)");
		
		inputTr.each(function(index1){
			var tr1 	= $(this);
			var lotNo1 	= tr1.find("#lotSeqNo").val();
			inputTr.each(function(index2){
				if(index1 < index2 && index1 != index2){
					var tr2 	= $(this);
					var lotNo2 	= tr2.find("#lotSeqNo").val();
					if(lotNo1 == lotNo2){
						var lot = {
								"lotSeqNo":lotNo2
								,"line":index2+1
							};
						lotList[index2+1] = lot;
					}
				}
			});
		});
		
		inputTr.each(function(index){
			var tr 	= $(this);
			var lotNo 	= tr.find("#lotSeqNo").val();
			if("-" != lotNo && undefined != lotList[index+1]){
				if(lotNo == lotList[index+1].lotNo){
					errors.push({"code":"err.fng.005","arguments":[lotList[index+1].line,lotList[index+1].lotNo]});
				}
			}
		});
	}
	
	function save(){
		var errors = [];
		var lotList = [];
		var inputTr = tblDynamic.find("tr:gt(1)");
		var fgType = ($(":radio[id=rdiIn]:checked").val()==="fgin") ? "fgIn" : "fgOut" ;
		var params = [];
		var line = 0;

		
		if(confirm(message.getMessage("cfm.cmm.001"))){
			showLoading();
			inputTr.each(function(index){
				var tr1 	= $(this);
				var lastLotSeqNo = tr1.find("#lotSeqNo").val().substring(0,14);
				if(inputTr.length == 1 && lastLotSeqNo == ""){// There is no any data in table
					errors.push({'code': 'err.cmm.003', 'arguments': []});
				}
				if((line != (inputTr.length-1)) && lastLotSeqNo != ""){
					params.push("details["+ (index) +"].workOrderNo=");
					params.push("details["+ (index) +"].fullLotSeqNo="+tr1.find("#lotSeqNo").val());
					params.push("details["+ (index) +"].lotNo="+(tr1.find("#lotSeqNo").val()).substring(0,14));
					params.push("details["+ (index) +"].lotSeqNo="+(tr1.find("#lotSeqNo").val()).substring(15,19));
					params.push("details["+ (index) +"].customerId="+tr1.find("#hideCustId").val());
					params.push("details["+ (index) +"].customerCode="+tr1.find("#hideCustCode").val());
					params.push("details["+ (index) +"].fgId="+tr1.find("#hideFgId").val());
					params.push("details["+ (index) +"].fgNo="+tr1.find("#hideFgNo").val());
					params.push("details["+ (index) +"].fgName="+tr1.find("#fgId").text());
					params.push("details["+ (index) +"].reportType="+ $("#reportType").val());
					params.push("details["+ (index) +"].reportTypeName="+ $("#reportType option:selected").text());
					params.push("details["+ (index) +"].reportDate="+$("#reportDate").val());
					params.push("details["+ (index) +"]."+ fgType +"="+tr1.find("#"+ fgType).val());
					params.push("details["+ (index) +"].fgType="+ fgType);
					lotList.push(tr1.find("#lotSeqNo").val());
				}else if(line == (inputTr.length-1) && lastLotSeqNo != ""){
					params.push("details["+ (index) +"].workOrderNo=");
					params.push("details["+ (index) +"].fullLotSeqNo="+tr1.find("#lotSeqNo").val());
					params.push("details["+ (index) +"].lotNo="+(tr1.find("#lotSeqNo").val()).substring(0,14));
					params.push("details["+ (index) +"].lotSeqNo="+(tr1.find("#lotSeqNo").val()).substring(15,19));
					params.push("details["+ (index) +"].customerId="+tr1.find("#hideCustId").val());
					params.push("details["+ (index) +"].customerCode="+tr1.find("#hideCustCode").val());
					params.push("details["+ (index) +"].fgId="+tr1.find("#hideFgId").val());
					params.push("details["+ (index) +"].fgNo="+tr1.find("#hideFgNo").val());
					params.push("details["+ (index) +"].fgName="+tr1.find("#fgId").text());
					params.push("details["+ (index) +"].reportType="+ $("#reportType").val());
					params.push("details["+ (index) +"].reportTypeName="+$("#reportType option:selected").text());
					params.push("details["+ (index) +"].reportDate="+$("#reportDate").val());
					params.push("details["+ (index) +"]."+ fgType +"="+tr1.find("#"+ fgType).val());
					params.push("details["+ (index) +"].fgType="+ fgType);
					lotList.push(tr1.find("#lotSeqNo").val());
				}
				line++;
			});
			
//			var index = 0;
//			var uniqueNames = [];
//			$.each(lotList, function(i, el){
//				index++;
//			    if($.inArray(el, uniqueNames) === -1){ 
//			    	uniqueNames.push(el);
//			    }else{
//			    	errors.push({'code': 'err.cmm.020', 'arguments': [index,'Lot Seq.']});
//			    }
//			});
			
			checkInput(errors);
			//checkDupLotNoScreen(errors);
			chkNumberFormat(errors);
			
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}

			postJSON("FNG_S01_CheckLotNoAndSave", params.join("&"), function( result ){
				
				if( errors.length > 0 ) {
					message.setErrors(errors);
					return false;
				}
				
				if (result.infos.length > 0) {					
					message.showMessage(result);
					if(result.errors.length > 0){
//						message.clear();
						message.showAllMessage(result);
					}
					//enableSaveBtn();
					//resetRow();
				} else if (result.errors.length > 0) {
					message.clear();
					message.setErrors(result.errors);
					enableSaveBtn();
				}
			});	
		}
	}

	function disableSaveBtn(){
		btnSave.attr("disabled", true);
	}
	
	function enableSaveBtn(){
		btnSave.attr("disabled", false);
	}

	function validateWorkOrder( e ) {
		var keyCode = (e.keyCode || e.which);
		var element = $(e.target || e.srcElement);
		var value   = e.value;
		if( value.length > 13 ) {
			
			if( /[a-zA-Z0-9]{9}[-][0-9]{4}[-][0-9]{4}/g.test(value) ) {
				// <!-- For deactivation 'autocomplete' event. -->
				element.blur();
				var lotSeqNoInput = element.closest("tr").find("input#lotSeqNo");
				lotSeqNoInput.val(lotSeqNo);
				element.val(lotSeqNo).change();
				btnAdd.click();
				
				return false;
			} else {
				element.val("").focus();
				alert("The system does not received data, Please scan barcode in this Line again.");				
			}
		}
		return true;
	}

	function detailSelection( e ) {
		var element = $(e.target || e.srcElement);		
		var tr 		= $($(this).parent().parent());
		if(!isGetDetail){
			getLotSeqDetail(tr , element.val());
			isGetDetail = false;
		}
	}
	
	function getLotSeqDetail(tr , lotSeqNoValue){

		getJSON("FNG_S01_txtWorkOrderDetail", { "lotSeqNo": lotSeqNoValue }, 
				function(result){
					
					if (result) {
						// set value to other field
						var cust 	= tr.find("#customerCode");
						var fg   	= tr.find("#fgId");
						var fgId	= tr.find('#hideFgId');
						var fgNo	= tr.find('#hideFgNo');
						var custId	= tr.find('#hideCustId');
						var custCode	= tr.find('#hideCustCode');
						var lotQty 	= fg.parent().parent().find("input:gt(2):not(:disabled)");
											
						if( result.errors && result.errors.length > 0 ) {
							message.setErrors(result.errors);
							return false;
						}					
					
						//Set default customer value.
						lotQty.val(result.lotSeqQty);
						customerCodeDefault = result.customerCode;
						custId.val(result.customerId);
						custCode.val(result.customerCode);
						cust.text(customerCodeDefault);
						fgId.val(result.fgId);
						fgNo.val(result.fgNo);
						fg.text(result.fgNo + " : "+ result.fgName);
						
						//cal fg In/Out
						getTotalFgIn();
						getTotalFgOut();
					}
			});
	}
	function fngIntegerFilter(e) {
		var code = (e.which || e.keyCode);
		var char = String.fromCharCode(code);
		var elmt = $(e.target ? e.target : e.srcElement).val();
		
		if( code === 8 ){ // 'Backspace' key press.
			return true;
		}
		if( code === 9 ){ // 'TAB' key press.
			return true;
		}
		if( code === 37 || code === 39){ // 'left arrow' or 'right arrow' key press.
			return true; 
		}
		
		var regex = /\-+/;
		if(regex.test(elmt) && char === '-') {
			return false;
		}

		regex = /^\-?\d{0,9}$/;
		if(!regex.test(char)) {
			return false;
		}
		
		return true;

	}
	
	function checkscanlot(inp){
		 var lot = $(inp).val();
		 if(lot.length == 19){
			var last = tblDynamic.find('tbody>tr:last');
			if(!last.find("td").find("input").val() == ''){
				 btnAdd.click();
			}
		 }
	}