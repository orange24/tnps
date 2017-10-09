<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
	<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var dailyMCForm;
	var boxCustomer;
	var boxMachineNo;
	var boxMoldName;
	var boxMoldNo;
	var boxPartNo;
	var boxReasonNG    = $("<select></select>");
	var boxReasonCat   = $("<select></select>");
	var boxReasonInCat = $("<select><option>-- Select Reason --</option></select>");
	var boxReportDate;
	var boxReportType;
	var boxWIP;
	var inpCAVUse;
	var inpCAVDef;
	var btnAddLotNo;
	var btnAddNGReason;
	var btnAddStopMC;
	var btnCancel;
	var btnDisplay;
	var btnDelete;
	var btnSave;
	var tblDetail;
	var tblDetailPart;
	var mrkLotNo;
	var mrkNGReason;
	var mrkMCReason;
	var srcNGReasonRow = $("<tr id='NGReason'></tr>");
	var srcMCReasonRow = $("<tr id='MCReason'></tr>");
	var thCavNo = $("th#thCavNo");
	var contentOriginal;

	$(document).ready(function(){
		dailyMCForm     = $("form#dailyMCForm");
		boxCustomer     = $("select#boxCustom");
		boxMachineNo    = $("select#boxMachineNo");
		boxMoldName       = $("select#boxMoldName");
		boxMoldNo       = $("select#boxMold");
		boxPartNo       = $("select#boxPartNo");
		boxReportDate   = $("input#reportDate");
		boxReportType   = $("select[name=reportType]");
		boxWIP          = $("select#boxWIP");
		inpCAVUse       = $("input#cavUsed");
		inpCAVDef       = $("input#inpMoldCAV");
		btnAddLotNo     = $("input#btnAddLotNo");
		btnAddNGReason  = $("input#btnAddNGReason");
		btnAddStopMC    = $("input#btnAddStopMC");
		btnCancel       = $("input#btnCancel");
		btnDisplay      = $("input#btnDisplay");
		btnDelete       = $("input#btnDelete");
		btnSave         = $("input#btnSave");
		tblDetail       = $("table#tblDetail");
		tblDetailPart   = $("table#tblDetailPart");
		mrkLotNo        = $("tbody > tr:eq(6) > td", tblDetailPart);
		mrkNGReason     = $("tbody > tr[id=MCReasonHeader]", tblDetailPart);
		mrkMCReason     = $("tbody > tr[id=RemarkHeader]", tblDetailPart);
		thCavNo = $("th#thCavNo");
// 		contentOriginal 	= $("div#contentHTML").parseHTML();
// 		alert(contentOriginal);
		
		// <!-- Initial: Auto Completion. -->
		var workOrderList = {
			source: function( request, response ) {
				getJSON("DAL_S02_workOrderNoList", {
					         "wip": boxWIP.val()
					, "reportType": boxReportType.val()
					,"workOrderNo": request.term					
				}, response);
			}
		};
		boxMoldNo.prepend("<option>-- Select Mold No --</option>");
		// <!-- Initial: Set 'minDate'. -->
		boxReportDate.datepicker( "option", "minDate", '${minReportDate}' );
		boxReportDate.datepicker( "option", "maxDate", '0d' );

		dailyMCForm.submit(function(){ return false; });
		
		btnSave.click(function(){

			if( !confirm("<spring:message code='cfm.cmm.001'/>") )
				return;

			$("input#btnSave").attr("disabled", true);
			
			var cavDefault   = parseInt(inpCAVDef.val(),10);
			var cavUsed      = parseInt(inpCAVUse.val(),10);
			var dayQty       = parseInt($("input[name='details[24].qty']").val(),10);
			var ngtQty       = parseInt($("input[name='details[25].qty']").val(),10);
			var lotNo		 = $("input[name='lotNo']").val() || "";
			var errors = [];
			if( boxCustomer.attr("selectedIndex") === 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["Customer"]});
			if( boxPartNo.attr("selectedIndex") === 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["Part No"]});
			if( boxMoldName.attr("selectedValue") == "-- Select Mold Name --" )
				errors.push({"code": "err.cmm.001", "arguments": ["Mold Name"]});
			if( boxMoldNo.attr("selectedIndex") === 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["Mold No"]});
			if( !cavUsed )
				errors.push({"code": "err.cmm.001", "arguments": ["Cav. Use"]});	
			if( lotNo.trim().length == 0)
				errors.push({"code": "err.cmm.001", "arguments": ["Workorder No"]});
			if( cavUsed > cavDefault )
				errors.push({"code": "err.dal.001", "arguments": []});
			if( (cavUsed || 0) < 1 )
				errors.push({"code": "err.cmm.014", "arguments": ["Cav. Use"]});

			//Check QTY
			var tblDetailPart = $("#tblDetailPart");
			var isNotZero = false;
			var qty;
			tblDetailPart.find("input[name$='qty']").each(function(){
				qty = parseInt($(this).val()) || 0;
				if (qty !== 0) {
					isNotZero = true;
			        return false;
				}
			});
			if( !isNotZero ){
				errors.push({"code": "err.dal.002", "arguments": []});
			}
			
			/*check work order No. and machine No.*/
			var machineNoLength = $("#txtHidMachineNo").val().length;
			var tblDetailPart = $("#tblDetailPart");
			var input = tblDetailPart.find("input[name=lotNo]:eq(0)");
			if($("#txtHidMachineNo").val().substring(machineNoLength-2,machineNoLength) != input.val().substring(1,3)){
				errors.push({"code": "err.dal.009", "arguments": [$("#txtHidMachineNo").val()]});
			}
			// <!-- check NG Reason. -->
			var index=0;
			var sum=Array();
			var sumNg=Array();
			
			tblDetailPart.find("input[name$='ng']").each(function(){
				sumNg[index] = parseInt($(this).val() || 0);
				sum[index] = 0;
				tblDetailPart.find("[id=NGReason]").each(function(o){ 
					var select = $(this).find("select :selected");
					if(select.val() > 0){
						var tr = $(this);
						tr.find("td:eq("+index+")").each(function(oo){									
							var td = $(this);
							var v = parseInt(td.find("input").val() || 0);
							sum[index]+=v;
						});
					}
				});
				if(sumNg[index]!=sum[index]){
					var ngReasonHeader = tblDetailPart.find("tr[id=ngReasonHeader]").find("th:eq("+(index+1)+")").text();								
					errors.push({"code": "err.dal.003", "arguments": [ngReasonHeader]});
				}
				index++;							
			});
			
			// Check stop machine 
				var indexStop=0;
				var flag = 0;
				var sumStop = Array();
				tblDetailPart.find("input[name$='ng']").each(function(){
					tblDetailPart.find("[id=MCReason]").each(function(){ 
						flag = 0;
						sumStop[index] = 0;
						$(this).find("select").each(function(){
							if($(this).val() > 0){
								flag++;
							}
						});
						if(flag == 2){			
							var tr = $(this);
							tr.find("td:eq("+indexStop+")").each(function(oo){									
								var td = $(this);
								var v = parseInt(td.find("input").val() || 0);
								sumStop[indexStop] = (isNaN(sumStop[indexStop]) ? 0:sumStop[indexStop])+v;
							});
						}
					});
					if(sumStop[indexStop] > 60){
						var MCReasonHeader = tblDetailPart.find("tr[id=MCReasonHeader]").find("th:eq("+(indexStop+1)+")").text();								
						errors.push({"code": "err.dal.004", "arguments": [MCReasonHeader]});
					}
					indexStop++;							
				});
				
			// check NGReason Case select but no input OR Case input but no select 
				var tblDetailPart = $("#tblDetailPart");
				tblDetailPart.find("[id=NGReason]").each(function(o){
					var tr = $(this);
					var select =tr.find("select");
					var totalDay = tr.find("td:eq(12)").find("input").val() || 0;
					var totalNigth = tr.find("td:eq(25)").find("input").val() || 0;
					var sumNG = parseInt(totalDay) + parseInt(totalNigth );  
					if(sumNG !== 0){    
						if(select.val()  < 1){
							errors.push({"code": "err.cmm.001", "arguments": ["NGReason"]});
						}
					}else{
						if(select.val()  > 0){
							errors.push({"code": "err.cmm.001", "arguments": ["NGReason (By Hour)"]});
						}
					}
				});	
				
			// check StopMachine Case select but no input OR Case input but no select 
				var tblDetailPart = $("#tblDetailPart");
				var flag = 0;
				var sumStop = 0;
				tblDetailPart.find("[id=MCReason]").each(function(o){
					flag = 0;
					sumStop = 0;
					var tr = $(this);					
					var select =tr.find("select");
					select.each(function(){
						if($(this).val() > 0){
							flag++;
						}
					});					
					if(flag > 0){					
						tr.find("td").each(function(oo){									
							var td = $(this);
							var v = parseInt(td.find("input").val() || 0);					
							sumStop += v;
						});					
						if(sumStop < 1){
							errors.push({"code": "err.cmm.001", "arguments": ["Stop Machine (minute)"]});
						}
						if(flag != select.size()){
							errors.push({"code": "err.cmm.001", "arguments": ["Stop Machine (Reason)"]});
						}
					}else{					
						tr.find("td").each(function(oo){									
							var td = $(this);
							var v = parseInt(td.find("input").val() || 0);					
							sumStop += v;
						});					
						if(sumStop > 0){
							errors.push({"code": "err.cmm.001", "arguments": ["Stop Machine"]});
						}					
					}				
				});
				
				// check duplicate NGReason
				var tblDetailPart = $("#tblDetailPart");
				var ngArr = new Array();
				var dupFlag = false;
				tblDetailPart.find("[id=NGReason]").each(function(index){
					var select = $(this).find("select :selected");
					ngArr[index] = select.val();
				});
				for(var i = 0; i < ngArr.length; i++){
					for(var j = 0; j < ngArr.length; j++){
						if(j != i){
							if(ngArr[j] == ngArr[i]){
								dupFlag = true;
							}
						}
					}
				}
				if(dupFlag == true){
					errors.push({"code": "err.cmm.011", "arguments": ["NG Reason Qty"]});
				}
				
			if( errors.length > 0 ) {
				message.setErrors(errors);
				$("#contentHTML").find("input, select, textarea").removeAttr("disabled");
				return false;
			}

			message.clear();

			// <!-- Provide the data. -->
			$("tbody > tr", tblDetailPart).each(function(){
				var row   = $(this);
				var rowId = row.attr("id");

				// <!-- Assign Elements Name for Data Binding. -->
				if( rowId == 'NGReason') {
					var selectBox = row.find("select:first");
					var reason    = selectBox.val();
					if( selectBox.attr("selectedIndex") !== 0 )
						row.find("input").each(function(index){
							var name;
							     if( index < 12 )  { name = "details["+ (index+8) +"].reasons["+ reason +"]"; }
							else if( index == 12 ) { name = "details[24].reasons["+ reason +"]"; }
							else if( index < 17 )  { name = "details["+ (index+7) +"].reasons["+ reason +"]"; }
							else if( index < 25 )  { name = "details["+ (index-17) +"].reasons["+ reason +"]"; }
							else if( index == 25 ) { name = "details[25].reasons["+ reason +"]"; }
			
							$(this).attr("name", name);
						});
				}
				if( rowId == 'MCReason') {
					var selectBox = row.find("select:last");
					var reason    = selectBox.val();
					if( selectBox.attr("selectedIndex") !== 0 )
						row.find("input").each(function(index){
							var name;
							if( index < 16 )  { name = "details["+ (index+8) +"].stops["+ reason +"]"; }
							else              { name = "details["+ (index-16) +"].stops["+ reason +"]"; }

							$(this).attr("name", name);
						});
				}
			});

			// <!-- Seding the data. -->
			var params;
			var paramsPlus = "";
			if(dailyMCForm.serialize().indexOf("wip")== -1){
				paramsPlus += "&" + $.param({
					       "wip": boxWIP.val()
				});
			}
			if(dailyMCForm.serialize().indexOf("machineId")== -1){
				paramsPlus += "&" + $.param({
					 "machineId": boxMachineNo.val()
				});
			}
			if(dailyMCForm.serialize().indexOf("reportDate")== -1){
				paramsPlus = paramsPlus += "&" + $.param({
					"reportDate": boxReportDate.val()
				});
			}
			if(dailyMCForm.serialize().indexOf("reportType")== -1){
				paramsPlus += "&" + $.param({
					"reportType": boxReportType.val()
				});
			}
			
			params = dailyMCForm.serialize() + paramsPlus;
			
			postJSON("DAL_S02_check", params, function( response ){
				$("input, select, textarea").attr("disabled", true);
				if( response.errors && response.errors.length > 0 ) {
					message.setErrors(response.errors);
					return;
				}

				postJSON("DAL_S02_save", params, function( result ){
					if( result.errors && result.errors.length > 0 ) {
						message.setErrors(result.errors);
						return;
					}

					message.setInfos ( result.infos  );
					setCriteriaUsability( true );
					clearContent();
				});
			});

			return false;
		});

		boxCustomer.change(function() {
			var params = {
				        "wip" : boxWIP.val()
				,"customerId" : boxCustomer.val()
				,"reportType" : boxReportType.val()
			};

			getJSON("boxPartNameNo",params,function(result){
				inpCAVUse.val("");
				inpCAVDef.val("");
				thCavNo.html("Cav No.");
				boxMoldName.find(":not(:first)").remove();
				boxMoldNo.find(":not(:first)").remove();
				boxPartNo.empty();
				$.each(result,function(val, text){
					boxPartNo.append( $("<option></option>").val(val).html(text) );
				});

				// <!-- Set: Option Selected. -->
				var partId = boxPartNo.data("partId");
				if( partId ) {
					boxPartNo.val(partId).change();
				}

				// <!-- Sort Data list -->
// 				var selectList = boxPartNo.find('option');
// 				selectList.sort(function(a,b){
// 				    a = a.value;
// 				    b = b.value;

// 				    return a-b;
// 				});
// 				boxPartNo.html(selectList);
				
			});
		});
		
		boxMoldName.change(function(){
			inpCAVUse.val("");
			inpCAVDef.val("");
			thCavNo.html("Cav No.");
			var params = {"moldId" : $(this).val(),"reportDate" : null};
			
			getJSON("boxMoldNo",params,function(result){
				boxMoldNo.empty();
				boxMoldNo.append($("<option>-- Select Mold No --</option>"));
				$.each(result,function(ind, obj){
					boxMoldNo.append( $("<option></option>").val(obj.moldNo).html(obj.moldNo).data("qtyShot", obj.qtyShot).data("cavNo", obj.cavNo) );
				});
				// <!-- Set: Option Selected. -->
				var partId = boxPartNo.data("partId");
				if( partId ) {
					boxMoldNo.find("option:eq(1)").attr("selected", true).end().change();
					boxPartNo.data("partId", "");
				}
				
				// <!-- Sort Data list -->
// 				var selectList = boxMoldNo.find('option');
// 				selectList.sort(function(a,b){
// 				    a = a.value;
// 				    b = b.value;

// 				    return a-b;
// 				});
// 				boxMoldNo.html(selectList);
				
			});
		});

		boxMoldNo.change(function() {
			var qtyShot = $(this).find("option:selected").data("qtyShot");
			var cavNo = $(this).find("option:selected").data("cavNo");
			inpCAVUse.val(qtyShot == null ? "" : qtyShot);
			inpCAVDef.val(qtyShot == null ? "" : qtyShot);
			thCavNo.html("Cav No.<br/>"+(cavNo == null ? "" : cavNo));
			/*
			var params = {
				"moldId" : $("#boxMoldName").val()
				, "moldNo" : $(this).val()
				, "partId" : $("#boxPartNo").val()
				, "customerId" : $("#boxCustom").val()
				, "machineId" : $("#boxMachineNo").val()
				, "wip" : $("#boxWIP").val()
				};
			getJSON("DAL_S02_getLotByMold", params, function(result){
				$("#lotNo").val(result.lotNo);
			});
			*/
			
		});

		boxPartNo.change(function(){
			inpCAVUse.val("");
			inpCAVDef.val("");
			thCavNo.html("Cav No.");
			var params = {"partId" : $(this).val(),"reportDate" : ''};
			
			getJSON("boxMoldName",params,function(result){
				boxMoldNo.find(":not(:first)").remove();
				boxMoldName.empty();
				$.each(result,function(val, text){
					boxMoldName.append( $("<option></option>").val(val).html(text) );
				});
				// <!-- Set: Option Selected. -->
				var partId = boxPartNo.data("partId");
				if( partId ) {
					boxMoldName.find("option:eq(1)").attr("selected", true).end().change();
				}

				// <!-- Sort Data list -->
// 				var selectList = boxMoldName.find('option');
// 				selectList.sort(function(a,b){
// 				    a = a.value;
// 				    b = b.value;

// 				    return a-b;
// 				});
// 				boxMoldName.html(selectList);
			});
		});

		boxWIP.change(function(){
			comboBox.setMachineNameActive(boxMachineNo, $(this));
			comboBox.setReasonNG (boxReasonNG, $(this));
			comboBox.setReasonCat(boxReasonCat, $(this));
		});

		btnAddNGReason.click(function(){
			var reasonRow = srcNGReasonRow.clone(true);
			reasonRow.prepend(
				$("<th colspan='2' align='left'></th>")
				.append( boxReasonNG.clone(true) )
				.append(" <a href='javascript:void(0);' onclick=' deleteRow(this); '><img src='image/icon/delete.gif'/></a>")
			).insertBefore( $("tr[id=showNGReason]") );

			// <!-- Assign: Key Up Event. -->
			var inputNGRs = $(":text", reasonRow);
			inputNGRs.keyup(function(){ SumNGReasonFunc( inputNGRs, $(this), 0, 0 ); });
		});

		btnAddStopMC.click(function(){
			var reasonCat   = boxReasonCat.clone(true);
			var reasonInCat = boxReasonInCat.clone(true);

			// <!-- Assign Behavior. -->
			reasonCat.change(function(){
				comboBox.setReasonInCat(reasonInCat, boxWIP.val(), reasonCat.val());
			});
			srcMCReasonRow.clone(true).prepend(
				$("<th colspan='2' align='left'></th>")
				.append("Category<br>")
				.append( reasonCat )
				.append("<br>Reason<br>")
				.append( reasonInCat )
				.append(" <a href='javascript:void(0);' onclick=' deleteRow(this); '><img src='image/icon/delete.gif'/></a>")
			).insertBefore( $("tr[id=showStopMC]") );
		});
		
		btnDisplay.click(function(){
			var errors = [];
			if( boxWIP.attr("selectedIndex") === 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["WIP"]});
			if( boxMachineNo.attr("selectedIndex") === 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["Machine No"]});
			if( !boxReportDate.val() )
				errors.push({"code": "err.cmm.001", "arguments": ["Report Date"]});
			if( boxReportType.attr("selectedIndex") === 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["Report Type"]});
			
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}

			message.clear();
			setCriteriaUsability( false );

			// <!-- Initial Table. -->
			initialTable();
			
			getJSON("DAL_S02_getMachine",{"machineId":boxMachineNo.val()},function(result){
				$("#txtHidMachineNo").val(result.machineNo);
			});

			return true;
		});
		
		btnDelete.click(function(){
			var dailyMCId = dailyMCForm.find("input[name=dailyMCId]").val();
			if( dailyMCId ) {
				if( confirm("<spring:message code='cfm.cmm.002'/>") )
					document.location = "DAL_S02_delete.html?dailyMCId="+ dailyMCId;
			} else {
				document.location.reload(true);
			}
			return false;
		});
		
		// <!-- Assigning Behavior. -->
		// <!-- DO NOT EDIT: Please confirm with P'Pok first. -->
		var inpOKs = tblDetailPart.find("tr:eq(2) > td > input");
		var inpNGs = tblDetailPart.find("tr:eq(3) > td > input");
		var inpPDs = tblDetailPart.find("tr:eq(4) > td > input");
		var inpQty = tblDetailPart.find("tr:eq(5) > td > input");
		var smFunc = function( inputs, input, smDay, smNht ) {
			var index = inputs.index( input );
			var valOK = parseInt(inpOKs.eq(index).val() || 0);
			var valNG = parseInt(inpNGs.eq(index).val() || 0);
			var valPD = parseInt(inpPDs.eq(index).val() || 0);

			inpQty.eq(index).val( valOK + valNG + valPD );

			if( smDay == 0 ) inputs.slice( 0, 12).each(function(){ smDay += parseInt($(this).val() || 0); });
			if( smNht == 0 ) inputs.slice(13, 25).each(function(){ smNht += parseInt($(this).val() || 0); });
			if( index < 12 ) {
					inputs.eq(12).val( smDay );
					smFunc( inputs, inputs.eq(12), smDay, smNht );
			} else if( index > 12 && index < 25 ) {
					inputs.eq(25).val( smNht );
					smFunc( inputs, inputs.eq(25), smDay, smNht );
			}

			// <!-- Specific 'background-color' Status. -->
			if( index == 12 || index == 25 ) {
				var val    = parseInt(input.val() || 0);
				var isDay  = index == 12;
				var isNht  = index == 25;
				var isNotEmptySMDay = (!(smDay == 0) && smDay != val);
				var isNotEmptySMNht = (!(smNht == 0) && smNht != val);
				var bgColor = ( (isDay && isNotEmptySMDay) || (isNht && isNotEmptySMNht) ? "red" : "" );
				input.data("bgColor", bgColor).css("background-color", bgColor);

				var bgOK       = inpOKs.eq(index).data("bgColor") || "";
				var bgNG       = inpNGs.eq(index).data("bgColor") || "";
				var bgPD       = inpPDs.eq(index).data("bgColor") || "";
				var isNormally = bgOK == '' && bgOK == bgNG && bgOK == bgPD;
				inpQty.eq(index).css("background-color", ( !isNormally ? "red" : "" ));
			}
		};
		inpOKs.keyup(function(){ smFunc( inpOKs, $(this), 0, 0 ); });
		inpNGs.keyup(function(){ smFunc( inpNGs, $(this), 0, 0 ); });
		inpPDs.keyup(function(){ smFunc( inpPDs, $(this), 0, 0 ); });
		

		// <!-- Specify the numberic input type. -->
		$("input[name$='pd']").keypress(PositiveIntegerFilter);
		$("input[name$='ok']").keypress(IntegerFilter);
		$("input[name$='ng']").keypress(IntegerFilter);

		// <!-- On 'Edit' Mode. -->
		if( tblDetail.is(":visible") ) {
			setCriteriaUsability( false );
			comboBox.setReasonNG (boxReasonNG, boxWIP);
			comboBox.setReasonCat(boxReasonCat, boxWIP);

			// <!-- Initial Color Status. -->
			smFunc( inpOKs, inpOKs.eq(12), 0, 0 ); smFunc( inpOKs, inpOKs.eq(25), 0, 0 );
			smFunc( inpNGs, inpNGs.eq(12), 0, 0 ); smFunc( inpNGs, inpNGs.eq(25), 0, 0 );
			smFunc( inpPDs, inpPDs.eq(12), 0, 0 ); smFunc( inpPDs, inpPDs.eq(25), 0, 0 );

			$("#tblDetailPart").find("[id=NGReason]").each(function(){  
				var tr = $(this);
				var inputNGRs = tr.find("td > input");
				inputNGRs.keypress(IntegerFilter).keyup(function(){ SumNGReasonFunc( inputNGRs, $(this), 0, 0 ); });
			});
			$("#tblDetailPart").find("[id=MCReason]").each(function(){  
				var tr = $(this);
				var inputNGRs = tr.find("td > input");
				inputNGRs.keypress(IntegerFilter);
			});
		}
		
		setLotNo();
		
		function initialTable(){
			mrkLotNo.find("div").remove();
			$("div#lotTmp").remove();
			var lotNo = $("<input id='lotNo' name='lotNo' maxlength = '11' size='11' style='width:120px'/> ").autocomplete(workOrderList);
			var lotItm = $("<div id='lotTmp'></div>");
			lotItm.append(lotNo);
			lotItm.insertBefore(btnAddLotNo);
			setLotNo();
			$("tbody > tr[id=NGReason]", tblDetailPart).remove(); btnAddNGReason.click();
			$("tbody > tr[id=MCReason]", tblDetailPart).remove(); btnAddStopMC.click();
			tblDetail.css("display", "");
			$("#contentHTML").find("input, select, textarea").removeAttr("disabled");
		}
		
		function clearContent(){
			$('#boxCustom > option').eq(0).attr('selected','selected');
			boxCustomer.change();
			$("#srcForm").find("input, select, textarea").removeAttr("disabled");
			tblDetailPart.find("input[type=text], textarea").val("");
		}
	});
		
	
	
	function setLotNo(){
		//Lotno onblur
		var tblDetailPart = $("#tblDetailPart");
		var input = tblDetailPart.find("input[name=lotNo]:eq(0)");
			input.unbind("blur").blur(function(){
				postJSON("DAL_S02_lotno", { "lotNo": input.val(),"wip": boxWIP.val(),"reportType": boxReportType.val() }, function( result ){
					if( !result ) return;
					boxPartNo.data("partId", result.partId);
					boxCustomer.val(result.customerId).change();
					boxMoldName.data("moldId", result.moldId);
					boxMoldNo.data("moldNo", result.moldNo);
				});
			});		
	}
	
	function setCriteriaUsability( flag ) {
		if( flag ) {
			boxWIP.removeAttr("disabled");
			boxMachineNo.removeAttr("disabled");
			boxReportDate.datepicker("enable");
			boxReportType.removeAttr("disabled");
			btnDisplay.removeAttr("disabled");
		} else {
			boxWIP.attr("disabled", true);
			boxMachineNo.attr("disabled", true);
			boxReportDate.datepicker("disable");
			boxReportType.attr("disabled", true);
			btnDisplay.attr("disabled", true);
		}
	}

	function deleteDiv( obj ) {
		$(obj).closest("div").remove();
		setLotNo();
	}

	function deleteRow( obj ) {
		$(obj).closest("tr").remove();
	}

	// <!-- Sum Total NGReason. -->
	function SumNGReasonFunc( inputs, input, smDay, smNht ) {
		var index = inputs.index( input );				
		
		if( smDay == 0 ) inputs.slice( 0, 12).each(function(){ smDay += parseInt($(this).val() || 0); });
		if( smNht == 0 ) inputs.slice(13, 25).each(function(){ smNht += parseInt($(this).val() || 0); });
		
		if( index < 12 ) {
				inputs.eq(12).val( smDay == 0 ? "" : smDay );
				SumNGReasonFunc( inputs, inputs.eq(12), smDay, smNht );
		} else if( index > 12 && index < 25 ) {
				inputs.eq(25).val( smNht == 0 ? "" : smNht );
				SumNGReasonFunc( inputs, inputs.eq(25), smDay, smNht );
		}
		// <!-- Specific 'background-color' Status. -->
		if( index == 12 || index == 25 ) {
			var val    = parseInt(input.val() || 0);
			var isDay  = index == 12;
			var isNht  = index == 25;
			var isNotEmptySMDay = (!(smDay == 0) && smDay != val);
			var isNotEmptySMNht = (!(smNht == 0) && smNht != val);
			var bgColor = ( (isDay && isNotEmptySMDay) || (isNht && isNotEmptySMNht) ? "red" : "" );
			input.data("bgColor", bgColor).css("background-color", bgColor);
		}
	};

	// <!-- Initial Processing. -->
	srcNGReasonRow
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(IntegerFilter) ) );
	srcMCReasonRow
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").html( "&nbsp;" ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").append( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
		.append( $("<td align='center'></td>").html( "&nbsp;" ) );

</script>
<style type="text/css">
<!--
	.style1 {
		color: #FF0000
	}
	.ui-autocomplete {
		max-height: 100px;
		overflow-y: auto;
	}
	/* IE 6 doesn't support max-height
	 * we use height instead, but this forces the menu to always be this tall
	 */
	* html .ui-autocomplete {
		height: 100px;
	}
-->
</style>
</head>
<body>

<h1><spring:message code='menu.DailyActual(DC)'/></h1>
	<ul id="navlist">
		<li><a href="DAL_S01.html" >Daily Actual (DC) Search/List</a></li>
		<li><a href="DAL_S02.html" id="current">Daily Actual (DC) Add/Edit</a></li>
	</ul>
	<page:message item="${dailyMC}" />

	<form:form id="dailyMCForm" commandName="dailyMC">
	<div id="srcForm">
		<form:hidden path="dailyMCId"/>
		<table class="ui-widget ui-widget-content" cellpadding="3" cellspacing="1" width="100%" border="0">
		<tr>
			<th width="14%">WIP <span class="textred">*</span></th>
			<td width="28%"><form:select path="wip" items="${wipMap}" id="boxWIP"></form:select></td>
			<th width="16%">Machine No <span class="textred">*</span></th>
			<td width="26%"><form:select path="machineId" items="${machineMap}" id="boxMachineNo"></form:select></td>
			<td width="16%" ></td>
		</tr>
		<tr>
			<th>Report Date <span class="textred">*</span></th>
			<td><form:input path="reportDate" cssClass="date"/></td>
			<th>Report Type <span class="textred">*</span></th>
			<td>
    			<form:select path="reportType">
    				<form:option value="">-- Select Type --</form:option>
    				<form:options items="${reportTypeList}" itemLabel="typeName" itemValue="typeCode"/>
    			</form:select>
    		</td>
      <td align="right"><input type="button" id="btnDisplay" value="OK"/></td>
    </tr>
  </table>
  </div>
  
  <br />
<div id="contentHTML">

	<input type="hidden" id="txtHidMachineNo" />
	<table id="tblDetail" cellpadding="3" cellspacing="1" border="0" width="100%" class="tableBorder2"
		<c:if test="${empty dailyMC.dailyMCId}">
		style="display:none;"
		</c:if>
	>
    <tr>
      <td>
      <table id="tblDetailPart" width="100%" border="1" align="center" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content ">
        <tr class="table_title" >
          <th rowspan="2" align="left">Customer <span class="textred" >*</span><br />
            <form:select path="customerId" items="${customerMap}" id="boxCustom"></form:select>
            <br />
          </th>
          <th rowspan="2" align="center" >&nbsp;</th>
          <th colspan="13" align="center" >
          	<div align="left">Day Shift<br/> Worker  :
          		<form:input path="dayWorker" size="15"/>
          	</div>
          </th>
          <th colspan="13" align="center" >
          	<div align="left">Night Shift<br/> Worker  :
          		<form:input path="nightWorker" size="15"/>
          	</div>
          </th>
          </tr>
        <tr>
          <th>9.00</th>
          <th>10.00</th>
          <th>11.00</th>
          <th>12.00</th>
          <th>13.00</th>
          <th>14.00</th>
          <th>15.00</th>
          <th>16.00</th>
          <th>17.00</th>
          <th>18.00</th>
          <th>19.00</th>
          <th>20.00</th>
          <th>TOTAL</th>
          <th>21.00</th>
          <th>22.00</th>
          <th>23.00</th>
          <th>24.00</th>
          <th>1.00</th>
          <th>2.00</th>
          <th>3.00</th>
          <th>4.00</th>
          <th>5.00</th>
          <th>6.00</th>
          <th>7.00</th>
          <th>8.00</th>
          <th>TOTAL</th>
        </tr>
        <tr>
          <th align="left">Part No <span class="textred" >*</span><br />
            <form:select path="partId" items="${partMap}" id="boxPartNo"></form:select>
          </th>
          <th>OK</th>
          <td align="center"><form:input path="details[8].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[9].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[10].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[11].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[12].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[13].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[14].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[15].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[16].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[17].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[18].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[19].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[24].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[20].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[21].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[22].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[23].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[0].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[1].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[2].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[3].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[4].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[5].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[6].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[7].ok" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[25].ok" size="4" maxlength="7"/></td>
          </tr>
        <tr >
          <th align="left">Mold Name. <span class="textred" >*</span><br />
              <form:select path="moldId" items="${moldName}" id="boxMoldName"></form:select>
          </th>
          <th>NG</th>
          <td align="center"><form:input path="details[8].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[9].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[10].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[11].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[12].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[13].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[14].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[15].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[16].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[17].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[18].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[19].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[24].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[20].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[21].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[22].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[23].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[0].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[1].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[2].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[3].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[4].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[5].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[6].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[7].ng" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[25].ng" size="4" maxlength="7"/></td>
        </tr>
        <tr >
          <th align="left">Mold No. <span class="textred" >*</span><br />
              <form:select path="moldNo" items="${moldNo}" id="boxMold"></form:select>
          </th>
          <th>Pending</th>
          <td align="center"><form:input path="details[8].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[9].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[10].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[11].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[12].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[13].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[14].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[15].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[16].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[17].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[18].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[19].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[24].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[20].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[21].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[22].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[23].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[0].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[1].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[2].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[3].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[4].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[5].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[6].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[7].pd" size="4" maxlength="7"/></td>
          <td align="center"><form:input path="details[25].pd" size="4" maxlength="7"/></td>
          </tr>
        <tr >
          <th align="left">Cav (Use/Default) <span class="textred" >*</span><br />
              <form:input path="cavUsed" id="cavUsed" size="5"/>
              / 
              <form:input path="cavDefault" cssClass="bg_color_gray" id="inpMoldCAV" size="5" readonly="true"/>
          </th>
          <th>Qty</th>
          <td align="center"><form:input readonly="true" path="details[8].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[9].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[10].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[11].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[12].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[13].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[14].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[15].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[16].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[17].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[18].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[19].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[24].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[20].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[21].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[22].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[23].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[0].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[1].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[2].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[3].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[4].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[5].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[6].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[7].qty" size="4" maxlength="7"/></td>
          <td align="center"><form:input readonly="true" path="details[25].qty" size="4" maxlength="7"/></td>
          </tr>
          <tr >
          	<th colspan="2"align="left" id="thCavNo">Cav No.<br />${dailyMC.cavNo}</th>
            <td colspan="26">&nbsp;</td>
          </tr>
        <tr >
          <th colspan="2" align="right">Workorder No <span class="textred" >*</span></th>
          <td colspan="26">
          	<c:forEach var="item" items="${dailyMC.lotNo}">
            	<div><input name="lotNo" size="11" tabindex="2" value="${item}" maxlength="11"/></div>
            </c:forEach>
            <c:if test="${dailyMC.createDate ge minDate}">
            	<input type="hidden" id="btnAddLotNo"/>
            </c:if>
          </td>
        </tr>

        <tr id="ngReasonHeader">
          <th colspan="2">NG Reason Qty</th>
          <th>9.00</th>
          <th>10.00</th>
          <th>11.00</th>
          <th>12.00</th>
          <th>13.00</th>
          <th>14.00</th>
          <th>15.00</th>
          <th>16.00</th>
          <th>17.00</th>
          <th>18.00</th>
          <th>19.00</th>
          <th>20.00</th>
          <th>TOTAL</th>
          <th>21.00</th>
          <th>22.00</th>
          <th>23.00</th>
          <th>24.00</th>
          <th>1.00</th>
          <th>2.00</th>
          <th>3.00</th>
          <th>4.00</th>
          <th>5.00</th>
          <th>6.00</th>
          <th>7.00</th>
          <th>8.00</th>
          <th>TOTAL</th>
        </tr>

		<c:forEach var="key" items="${reasonNGKeySet}">
			<tr id="NGReason">
				<th colspan="2" align="left">
					<select id="boxReasonNG">
						<c:forEach var="item" items="${reasonNGMap}">
							<c:if test="${item.key == key}">
								<option value="${item.key}" selected="selected">${item.value}</option>
							</c:if>
							<c:if test="${item.key != key}">
								<option value="${item.key}">${item.value}</option>
							</c:if>
						</c:forEach>
					</select>
					<c:if test="${dailyMC.createDate ge minDate}">
						<a href="javascript:void(0);" onclick=" deleteRow(this); "><img src="image/icon/delete.gif" width="16" height="16" /></a>
					</c:if>
				</th>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[8].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[9].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[10].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[11].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[12].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[13].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[14].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[15].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[16].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[17].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[18].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[19].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[24].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[20].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[21].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[22].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[23].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[0].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[1].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[2].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[3].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[4].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[5].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[6].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[7].reasons[key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[25].reasons[key]}"/></td>
			</tr>
		</c:forEach>

		<c:if test="${dailyMC.createDate ge minDate}">
	        <tr id="showNGReason">
	        	<td colspan="28"><input type="button" id="btnAddNGReason" value="Add NG Reason"/></td>
	        </tr>
        </c:if>

        <tr id="MCReasonHeader">
          <th colspan="2">Machine Stop (minutes)</th>
          <th>9.00</th>
          <th>10.00</th>
          <th>11.00</th>
          <th>12.00</th>
          <th>13.00</th>
          <th>14.00</th>
          <th>15.00</th>
          <th>16.00</th>
          <th>17.00</th>
          <th>18.00</th>
          <th>19.00</th>
          <th>20.00</th>
          <th>&nbsp;</th>
          <th>21.00</th>
          <th>22.00</th>
          <th>23.00</th>
          <th>24.00</th>
          <th>1.00</th>
          <th>2.00</th>
          <th>3.00</th>
          <th>4.00</th>
          <th>5.00</th>
          <th>6.00</th>
          <th>7.00</th>
          <th>8.00</th>
          <th>&nbsp;</th>
        </tr>
        
		<c:forEach var="res" items="${reasonInCatKeySet}">
			<tr id="MCReason">
				<th colspan="2" align="left">
					Category<br />
					<select onchange=" comboBox.setReasonInCat($(this).nextAll('select'), $('select#boxWIP').val(), this.value); ">
						<c:forEach var="cat" items="${reasonCatMap}">
							<c:if test="${cat.key == res.value}">
								<option value="${cat.key}" selected="selected">${cat.value}</option>
							</c:if>
							<c:if test="${cat.key != res.value}">
								<option value="${cat.key}">${cat.value}</option>
							</c:if>
						</c:forEach>
					</select><br />
					Reason <br />
					<select>
						<c:forEach var="cat" items="${reasonCatKeySet[res.key]}">
							<c:if test="${cat.key == res.key}">
								<option value="${cat.key}" selected="selected">${cat.value}</option>
							</c:if>
							<c:if test="${cat.key != res.key}">
								<option value="${cat.key}">${cat.value}</option>
							</c:if>
						</c:forEach>
					</select>
					<c:if test="${dailyMC.createDate ge minDate}">
						<a href="javascript:void(0);" onclick=" deleteRow(this); "><img src="image/icon/delete.gif" width="16" height="16" /></a>
					</c:if>
				</th>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[8].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[9].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[10].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[11].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[12].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[13].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[14].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[15].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[16].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[17].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[18].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[19].stops[res.key]}"/></td>
				<td align="center">&nbsp;</td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[20].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[21].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[22].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[23].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[0].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[1].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[2].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[3].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[4].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[5].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[6].stops[res.key]}"/></td>
				<td align="center"><input size="4" maxlength="7" value="${dailyMC.details[7].stops[res.key]}"/></td>
				<td align="center">&nbsp;</td>
			</tr>
		</c:forEach>
		
		<c:if test="${dailyMC.createDate ge minDate}">
			<tr id="showStopMC">
	        	<td colspan="28"><input type="button" id="btnAddStopMC"   value="Add Machine Stop"/></td>
	        </tr>
        </c:if>
        
		<tr id="RemarkHeader">
			<th colspan="2" align="center">Remark</th>
			<td colspan="26"><form:textarea path="remark" cols="100" rows="5"></form:textarea></td>
		</tr>
		</table>
       </td>
    </tr>
    <tr>
      <td>
      	<c:if test="${dailyMC.createDate ge minDate}">
	        <input type="button" id="btnSave"   value="Save"/>
	        <c:if test="${not empty dailyMC.dailyMCId}">
				<input type="button" id="btnDelete" value="Delete"/>
			</c:if>    
		</c:if>    
        <input type="button" id="btnCancel" value="Back" onclick="window.location='DAL_S01_search.html?button=back'"/>
      </td>
    </tr>
  </table>
  

</div>
</form:form>
	
</body>
</html>
