<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
	<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var isFirst = true;
	var dailyMCWKForm;
	var boxPartNo;
	var boxReasonCat   = $("<select></select>");
	var boxReasonInCat = $("<select><option>-- Select Reason --</option></select>");
	var boxCustomer;
	var boxMachineNo;
	var boxReportDate;
	var boxReportType;
	var boxShift;
	var boxStaff;
	var boxWIP;
	var btnAddNGReason;
	var btnAddStopMC;
	var btnCancel;
	var btnDisplay;
	var btnDelete;
	var btnSave;
	var tblNGReason;
	var tblStopMC;
	var shiftTime = [];
	var ngReasons = {};
	var dailyMCWKdetailList;
	
	comboBox.setCustomer(boxCustomer = $("<select id='customerId'></select>"));
	comboBox.setPartNo  (boxPartNo   = $("<select id='partId'></select>"));
	
	$(document).ready(function(){
		dailyMCWKForm   = $("form#dailyMCWKForm");
		//boxCustomer     = $("select#boxCustom");
		boxMachineNo    = $("select#boxMachineNo");
		boxReportDate   = $("input#reportDate");
		boxReportType   = $("select[name=reportType]");
		boxShift        = $("input[name=shift]");
		boxStaff        = $("input[name=staff]");
		boxWIP          = $("select#boxWIP");
		btnAddNGReason  = $("input#btnAddNGReason");
		btnAddStopMC    = $("input#btnAddStopMC");
		btnCancel       = $("input#btnCancel");
		btnDisplay      = $("input#btnDisplay");
		btnDelete       = $("input#btnDelete");
		btnSave         = $("input#btnSave");
		tblNGReason     = $("table#tblNGReason");
		tblStopMC       = $("table#tblStopMC");
		dailyMCWKdetailList = $(".dailyMCWKdetailList").size();
	
		// <!-- Initial: Auto Completion. -->
		var workOrderList = {
			source: function( request, response ) {
				var partId     = $(this).closest("tr").find("select#partId");
				var customerId = $(this).closest("tr").find("select#customerId");
	
				getJSON("DAL_S06_workOrderNoList", {
					         "wip": boxWIP.val()
					, "reportType": boxReportType.val()
					,"workOrderNo": request.term
					, "customerId": customerId.val() < 0 ? "" : customerId.val()
					,     "partId": partId.val() < 0 ? "" : partId.val()
				}, response);
			},
			select: function( event, ui ) {
				var tr = $(this).closest("tr");
				var custom = tr.find("select#customerId");
				var partId = tr.find("select#partId");
				var ordQty = ui.item.workOrderQty || 0;
				var sumQty = tr.find("input:eq(5)"); 
	
				tr.find("select#partId").val( partId );
				tr.find("td:eq(6)").html( ordQty );
				sumQty.change();
				if (custom.val() == ui.item.customerId) {
					partId.val(ui.item.partId);
				} else {
					partId.data("partId", ui.item.partId);
					if( ui.item.customerId )
						custom.val(ui.item.customerId).change();
				}
			},
			minLength: 1
		};
	
		// <!-- Initial: Set 'minDate'. -->
		boxReportDate.datepicker( "option", "minDate", '${minReportDate}' );
		boxReportDate.datepicker( "option", "maxDate", '0d' );
	
		dailyMCWKForm.submit(function(){ return false; });
	
		btnSave.click(function(){

			if( !confirm("<spring:message code='cfm.cmm.001'/>") )
				return;

			btnAddNGReason.attr("disabled", true);
			btnAddStopMC.attr("disabled", true);
			btnSave.attr("disabled", true);
			btnCancel.attr("disabled", true);
			
			// <!-- Provide the data. -->
			var errors = [];
			var index = 0;
			var reasonHeaders = $("tbody > tr:eq(1) > th:gt(4)", tblNGReason);
			$("tbody > tr:gt(1)", tblNGReason).each(function(){
				var selects  = $("select", this);
				var inputs   = $("input", this);
				var boxDTime = selects.eq(0);
				var boxCus   = selects.eq(1);
				var boxPrtNo = selects.eq(2);
				var inpWKOrd = inputs.eq(0);
				var inpWKQty = parseInt($(this).find("td:eq(5)").html() || 0);
				var inpACQty = parseInt(inputs.eq(5).val() || 0);
				var inpPDNum = parseInt(inputs.eq(4).val() || 0);
				var inpNGNum = parseInt(inputs.eq(3).val() || 0);
				var inpOKNum = parseInt(inputs.eq(2).val() || 0);
				var inpTimeMin = parseInt(inputs.eq(6).val() || 0);
				var inpRSSum = 0;
	
				// <!-- Assigning the Element Name. -->
				boxDTime.attr("name", "detailList["+ index +"].reportTime");
				boxCus.attr("name", "detailList["+ index +"].customerId");
				boxPrtNo.attr("name", "detailList["+ index +"].partId");
	
				inpWKOrd.attr("name", "detailList["+ index +"].workOrderNo");
				inputs.eq(1).attr("name", "detailList["+ index +"].lotNo");
				inputs.eq(2).attr("name", "detailList["+ index +"].ok");
				inputs.eq(3).attr("name", "detailList["+ index +"].ng");
				inputs.eq(4).attr("name", "detailList["+ index +"].pd");
				inputs.eq(5).attr("name", "detailList["+ index +"].qty");
				inputs.eq(6).attr("name", "detailList["+ index +"].timeUsed");
				inputs.eq(7).attr("name", "detailList["+ index +"].manPower");
				
				// Add class KEYIN
				inputs.eq(1).attr("class", "keyin");
				inputs.eq(2).attr("class", "keyin");
				inputs.eq(3).attr("class", "keyin");
				inputs.eq(4).attr("class", "keyin");
				inputs.eq(5).attr("class", "keyin");
				inputs.eq(7).attr("class", "keyin");
				
				
				for( var column = 8; column < inputs.length; column++ ) {
					// <!-- Finding the 'reasonId'. -->
					var reasonId = reasonHeaders.eq(column-8).attr("id");
					inpRSSum += parseInt(inputs.eq(column).attr("name", "detailList["+ index +"].ngReasonMap["+ reasonId +"]").val() || 0);
					inputs.eq(column).attr("class", "keyin");
				}
	
				// <!-- Validation: if incomplete. -->
				if( boxDTime.attr("selectedIndex") === 0 )
					errors.push({"code": "err.cmm.002", "arguments": [(index+1),"Time"]});
				if( !inpWKOrd.val() )
					errors.push({"code": "err.cmm.002", "arguments": [(index+1), "Work Order No."]});
				if( boxPrtNo.attr("selectedIndex") === 0 )
					errors.push({"code": "err.cmm.002", "arguments": [(index+1),"Partno/Partname"]});
				if( isNaN(inpACQty) )
					errors.push({"code": "err.dal.002"});
				if( inpACQty === 0 && inpPDNum === 0 && inpNGNum === 0 && inpOKNum === 0 )
					errors.push({"code": "err.cmm.002", "arguments": [(index+1),"Atleast 1 of Qty"]});
				if( inpTimeMin === 0 )
					errors.push({"code": "err.cmm.009", "arguments": [(index+1),"Time (min)"]});
				if( inpNGNum != inpRSSum )
					errors.push({"code": "err.dal.007", "arguments": [(index+1)]});
	
				index++;
			});
	
			var mainHeaderRow = $("table#tblStopMC > tbody > tr:eq(0) > th:gt(0)");
			$("table#tblStopMC > tbody > tr").each(function(rowIndex){
				var row   = $(this);
				var rowId = row.attr("id");
	
				// <!-- Assign Elements Name for Data Binding. -->
				var selectBox = row.find("select:last");
				var reason    = selectBox.val();
				if( selectBox.attr("selectedIndex") > 0 ) {
					if( !row.find("input[value!='']").exists() ) {
						errors.push({"code": "err.cmm.001", "arguments": ["Machine Stop (minutes)"]});
					} else {
						row.find("input").each(function(index){
							$(this).attr("name", "stopMCList["+ parseInt(mainHeaderRow.eq(index).html()) +"]["+ reason +"]");
						});
					}
				} else {
					if( row.find("input[value!='']").exists() ) {
						errors.push({"code": "err.cmm.001", "arguments": ["Category and Reason"]});
					}					
				}
			});
			
			// Check stop machine 
			var tblStopMC = $("#tblStopMC");
			var indexStop=0;
			var flag = 0;
			var sumStop = Array();
			tblStopMC.find("tr:eq(0)").find("th:not(:eq(0))").each(function(){
				tblStopMC.find("tr:not(:eq(0))").each(function(){ 
					flag = 0;
					sumStop[index] = 0;
					var slt = $(this).find("select");
					slt.each(function(){
						if($(this).val() > 0){
							flag++;
						}
					});
					if(flag == slt.size() ){			
						var tr = $(this);
						tr.find("td:eq("+indexStop+")").each(function(oo){									
							var td = $(this);
							var v = parseInt(td.find("input").val() || 0);
							sumStop[indexStop] = (isNaN(sumStop[indexStop]) ? 0:sumStop[indexStop])+v;
						});
					}
				});
				if(sumStop[indexStop] > 60){
					var MCReasonHeader = tblStopMC.find("tr:eq(0)").find("th:eq("+(indexStop+1)+")").text();								
					errors.push({"code": "err.dal.004", "arguments": [MCReasonHeader]});
				}
				indexStop++;							
			});
	
			if( errors.length > 0 ) {
				message.setErrors(errors);
				$("#contentHTML").find("input, select, textarea").removeAttr("disabled");
				return false;
			}
			message.clear();
	
			// <!-- Seding the data. -->
			var params = dailyMCWKForm.serialize();
			var paramsPlus = "";

			if(dailyMCWKForm.serialize().indexOf("wip")== -1){
				paramsPlus += "&" + $.param({
			           "wip": boxWIP.val()
					});
			}
			if(dailyMCWKForm.serialize().indexOf("machineId")== -1){
				paramsPlus += "&" + $.param({
					     "machineId": boxMachineNo.val()
					});
			}
			if(dailyMCWKForm.serialize().indexOf("reportDate")== -1){
				paramsPlus += "&" + $.param({
						"reportDate": boxReportDate.val()
					});
			}
			if(dailyMCWKForm.serialize().indexOf("reportType")== -1){
				paramsPlus += "&" + $.param({
						"reportType": boxReportType.val()
					});
			}
			if(dailyMCWKForm.serialize().indexOf("shift")== -1){
				paramsPlus += "&" + $.param({
						     "shift": boxShift.filter(":checked").val()
					});
			}
			if(dailyMCWKForm.serialize().indexOf("staff")== -1){
				paramsPlus += "&" + $.param({
						     "staff": boxStaff.val()
					});
			}

			params = dailyMCWKForm.serialize() +'&'+ paramsPlus;
			
			postJSON("DAL_S06_check", params, function( response ){
				if( response.errors && response.errors.length > 0 ) {
					message.setErrors(response.errors);
					$("#contentHTML").find("input, select, textarea").removeAttr("disabled");
					return;
				}else{
					disableSaveBtn();
				}
	
				postJSON("DAL_S06_save", params, function( result ){
					$("input, select, textarea").attr("disabled", true);
	
					if( result.errors && result.errors.length > 0 ) {
						message.setErrors(result.errors);
						return;
					}
					disableSaveBtn();
					message.setInfos ( result.infos  );
					
					//---- clear content & enable search ---
					setCriteriaUsability( true );
				});
			});
			return false;
		});
	
		boxWIP.change(function(){
			comboBox.setMachineNameActive(boxMachineNo, $(this));
			comboBox.setReasonCat(boxReasonCat, $(this));
	
			ngReasons = {};
			var initial = function( result ){
				var mainHeaderRow   = $("tbody > tr:eq(0) > th", tblNGReason);
				var reasonHeaderRow = $("tbody > tr:eq(1)", tblNGReason);
	
				// <!-- Clear to Init. -->
				if( mainHeaderRow.length == 12 )
					mainHeaderRow.eq(10).remove();
				reasonHeaderRow.empty()
					.append( $("<th></th>").html( "OK" ) )
					.append( $("<th></th>").html( "NG" ) )
					.append( $("<th></th>").html( "Pending" ) )
					.append( $("<th></th>").html( "Qty" ) );
	
				if( !result || result.length < 1 ) {
					return;
				}
	
				// <!-- Start Processing. -->
				/*for( var i = 0; i < result.length ; i++ ) {
					ngReasons[result[i].reasonCode] = result[i].reasonId;
				}*/
	
				// <!-- Start Insert Column into Table. -->
				$("<th>NG Reason Qty <span class='textred'>*</span></th>").attr("colspan", result.length+1).insertBefore( mainHeaderRow.last() );
				reasonHeaderRow.append( $("<th>Total</th>"));
				/*for( key in ngReasons ) {
					reasonHeaderRow.append( $("<th></th>").attr("id", ngReasons[key]).attr("title",).html( key ) );
				}*/
				for( var i = 0; i < result.length ; i++ ) {
					reasonHeaderRow.append( $("<th></th>").attr("id", result[i].reasonId).attr("title",result[i].reasonName).html( result[i].reasonCode ) );
				}
			};
	
			getJSON("getReasonNGList", { "wip": $(this).val() }, initial);
		});
	
		boxCustomer.add(boxReportType).change(function(){
			if( boxCustomer.val() && boxReportType.val() )
				comboBox.setPartNameNo( boxPartNo, boxWIP, boxCustomer, boxReportType );
		});
		
		$("select[id=customerId]").add(boxCustomer).change(function(){
			var partNo = $(this).parent().parent().find("#partId");
			var params = { "customerId": "", "wip": "", "reportType": boxReportType.val() };
			if( $(this).val() )  params.customerId = $(this).val();
			if( boxWIP.val() )   params.wip        = boxWIP.val();
			getJSON("DAL_S06_getPartNameNo",params,function(result){
				partNo.empty();
				$.each(result,function(val, text){
					partNo.append( $("<option></option>").val(val).html(text) );
				});
	
				var partId = partNo.data("partId");
				if (partId) {
					partNo.val(partId).removeData("partId");
				}
			});
		});
	
		boxShift.click(function(){
			var dayTime         = [ 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24];
			var nhtTime         = [21, 22, 23, 24,  1,  2,  3,  4,  5,  6,  7,  8];
			//var time            = [ 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,21, 22, 23, 24,  1,  2,  3,  4,  5,  6,  7,  8];
			var mainHeaderRow   = $("tbody > tr:eq(0)", tblStopMC);
	
			shiftTime = $(this).val() === 'N' ? nhtTime : dayTime;
			//shiftTime = time;
	
			mainHeaderRow.find("th:not(:eq(0))").remove();
			for( var i in shiftTime ){
				mainHeaderRow.append( $("<th></th>").html( shiftTime[i] +".00" ) );
			}
		});
	
		btnAddNGReason.click(function(){
			var mainHeaderRow = $("tbody > tr:eq(0) > th", tblNGReason);
			var cntColumn = 0;
			var rowTemplt = $("<tr></tr>");
			mainHeaderRow.each(function(){
				cntColumn += $(this).attr("colspan");
			});
	
			// <!-- Initial Event. -->
			var wrkOdr   = $("<input size='12' maxlength='11' />").autocomplete(workOrderList);
			var partNo   = boxPartNo.clone(true);
			var custom   = boxCustomer.clone(true);
	
			// <!-- Generating Row Template. -->
			rowTemplt.append( $("<td align='center'></td>").html( $("tbody > tr", tblNGReason).length - 1 ) );
			rowTemplt.append( $("<td align='center'></td>").append( getShiftTimeSelection( shiftTime ) ) );
			rowTemplt.append( $("<td align='center'></td>").append( wrkOdr ) );
			rowTemplt.append( $("<td align='center'></td>").html( "<input size='4' maxlength='3' />" ) );
			rowTemplt.append( $("<td align='center'></td>").append( custom ) );
			rowTemplt.append( $("<td align='left'></td>").append( partNo ) );
			rowTemplt.append( $("<td align='center'></td>").html( "&nbsp;" ) );
			rowTemplt.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7' />").keypress(IntegerFilter) ) );
			rowTemplt.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7' />").keypress(IntegerFilter) ) );
			rowTemplt.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7' />").keypress(IntegerFilter) ) );
			rowTemplt.append( $("<td align='center'></td>").html( "<input size='4' readonly='readonly' tabindex=-1 />" ) );
			rowTemplt.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='10'  value='60'/>").keypress(IntegerFilter) ) );
			rowTemplt.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='10' />").keypress(IntegerFilter) ) );//Man Power
			if (cntColumn > 13) {
				rowTemplt.append( $("<td align='center' id=total ></td>").html("&nbsp;"));
			}
			for( var i = 0; i < cntColumn - 15; i++ ) {
				rowTemplt.append( $("<td align='center'></td>")
				  .html( 
					$("<input name='ngReasonQty' id='ngReasonQty' size='4' maxlength='7' />")
					.keypress(IntegerFilter)
					.keyup(function(){
						var total=0;
						rowTemplt.find("[id=ngReasonQty]").each(function(){
							total+=parseInt($(this).val() || 0,10);
						});
						rowTemplt.find("[id=total]").html(total);
					})
				  )
				);
			}
			rowTemplt.append( $("<td align='center'></td>").html( "<a href='javascript:void(0);' onclick='deleteRow(this);'><img src='image/icon/delete.gif'/></a>" ) );
			setQtyCalculation( rowTemplt );
	
			$("tbody", tblNGReason).append(rowTemplt);
		});
	
		btnAddStopMC.click(function(){
			var reasonCat = boxReasonCat.clone(true);
			var reasonDet = boxReasonInCat.clone(true);
			var rowTemplt = $("<tr></tr>").append();
	
			// <!-- Assign Behavior. -->
			reasonCat.change(function(){
				comboBox.setReasonInCat(reasonDet, boxWIP.val(), reasonCat.val());
			});
	
			rowTemplt
				.append( $("<th align='left'></th>").append("Category<br>").append( reasonCat ).append("<br>Reason<br>").append( reasonDet ).append( $("<a href='javascript:void(0);' onclick=' deleteRow(this); '><img src='image/icon/delete.gif'/></a>") ) )
				.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7' />").keypress(PositiveIntegerFilter) ) )
				.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
				.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
				.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
				.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
				.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
				.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
				.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
				.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
				.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
				.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
				.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) );
			if(boxShift.filter(":checked").val()==='D'){
				rowTemplt
					.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7' />").keypress(PositiveIntegerFilter) ) )
					.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
					.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) )
					.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7'/>").keypress(PositiveIntegerFilter) ) );
			}
			$("tbody", tblStopMC).append(rowTemplt);
		});
	
		btnDisplay.click(function(){
			var errors = [];
			if( boxWIP.attr("selectedIndex") === 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["WIP"]});
			if( boxMachineNo.attr("selectedIndex") === 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["Machine No"]});
			/*if( boxCustomer.attr("selectedIndex") === 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["Customer"]});*/
			if( !boxReportDate.val() )
				errors.push({"code": "err.cmm.001", "arguments": ["Report Date"]});
			if( boxReportType.attr("selectedIndex") === 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["Report Type"]});
			if( !boxShift.is(":checked") )
				errors.push({"code": "err.cmm.001", "arguments": ["Shift"]});
			
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
	
			message.clear();
	
			// <!-- Initial Table. -->
			initialContent();
			clearContent();
			setCriteriaUsability( false );
			
			return true;
		});
	
		btnDelete.click(function(){
			var dailyMCWKId = dailyMCWKForm.find("input[name=dailyMCWKId]").val();
			if( dailyMCWKId ) {
				if( confirm("<spring:message code='cfm.cmm.002'/>") )
					document.location = "DAL_S06_delete.html?dailyMCWKId="+ dailyMCWKId;
			} else {
				document.location.reload(true);
			}
			return false;
		});
	
		// <!-- On 'Edit' Mode. -->
		if( dailyMCWKForm.find("div").is(":visible") ) {
			setCriteriaUsability( false );
			boxShift.filter(":checked").click();
			// comboBox.setPartNameNo( boxPartNo, boxWIP, boxCustomer );
			getJSON("boxPartNameNo",{ "wip": boxWIP.val(), "customerId": boxCustomer.val(), "reportType": boxReportType.val() },function(result){
				boxPartNo.empty();
				$.each(result,function(val, text){
					boxPartNo.append( $("<option></option>").val(val).html(text) );
				});
	
				if(dailyMCWKdetailList < 1) {
					btnAddNGReason.click();
				}
			});
			//comboBox.setReasonCat ( boxReasonCat, boxWIP );
			getJSON("boxReasonCat",{ "wip": boxWIP.val()},function(result){
				boxReasonCat.empty();
				$.each(result,function(val, text){
					boxReasonCat.append( $("<option></option>").val(val).html(text) );
				});
				
				if(dailyMCWKdetailList < 1) {
					btnAddStopMC.click();
				}
			});
	
			// <!-- Initial Color Status. -->
			$("tbody > tr:gt(1)", tblNGReason).each(function(){
				$(this).find("input:eq(0)").autocomplete(workOrderList);
				setQtyCalculation( $(this) );
			});
		}
		
		// <!-- sum total ngReasonQty -->
		$("input[id=ngReasonQty]")
			.keypress(IntegerFilter)
			.keyup(function(){
				var rowTemplt = $("tbody > tr:gt(0)", tblNGReason);
				var reason = $("input[id=ngReasonQty]");
				rowTemplt.each(function(){
				var total=0;
					$(this).find("input[id=ngReasonQty]").each(function(){
						total+=parseInt($(this).val() || 0,10);
					});
					$(this).find("td[id=total]").html(total);
				});
			});
		
		function initialContent(){
			if(isFirst){
	 			$("tbody > tr:gt(1)", tblNGReason).remove(); btnAddNGReason.click();
	 			$("tbody > tr:gt(0)", tblStopMC).remove();   btnAddStopMC.click();
				isFirst = false;
			}
			dailyMCWKForm.find("div").show();
		}
		
		function clearContent(){
// 			btnAddNGReason.removeAttr("disabled");
// 			btnAddStopMC.removeAttr("disabled");
// 			btnSave.removeAttr("disabled");
// 			btnCancel.removeAttr("disabled");
			$("#dailyMCWKForm").find("input, select, textarea").removeAttr("disabled");
			$(".keyin").val("");
			$("#contentHTML").find("[id=total]").html("0");
		}
	
	});
	
	function disableSaveBtn(){
		btnSave.attr("disabled", true);
	}
	
	function enableSaveBtn(){
		btnSave.attr("disabled", false);
	}
	
	function deleteRow( obj ) {
		$(obj).closest("tr").remove();
	}
	
	function setCriteriaUsability( flag ) {
		if( flag ) {
			boxWIP.removeAttr("disabled");
			boxMachineNo.removeAttr("disabled");
			//boxCustomer.removeAttr("disabled");
			boxReportDate.datepicker("enable");
			boxReportType.removeAttr("disabled");
			boxShift.removeAttr("disabled");
			boxStaff.removeAttr("disabled");
			btnDisplay.removeAttr("disabled");
		} else {
			boxWIP.attr("disabled", true);
			boxMachineNo.attr("disabled", true);
			//boxCustomer.attr("disabled", true);
			boxReportDate.datepicker("disable");
			boxReportType.attr("disabled", true);
			boxShift.attr("disabled", true);
			boxStaff.attr("disabled", true);
			btnDisplay.attr("disabled", true);
		}
	}
	
	function setQtyCalculation( tr ) {
		// <!-- Initial Event. -->
		var inputs   = tr.find("input");
		var inpWKQty = tr.find("td:eq(5)");
		var inpOKQty = inputs.eq(2);
		var inpNGQty = inputs.eq(3);
		var inpPDQty = inputs.eq(4);
		var inpTTQty = inputs.eq(5);
		var evtSMQty = function(){
			inpTTQty.val( parseInt(inpOKQty.val() || 0) + parseInt(inpNGQty.val() || 0) + parseInt(inpPDQty.val() || 0) ).change();
		};
	
		// <!-- Assigning Behavior. -->
		inpOKQty.keyup(evtSMQty);
		inpNGQty.keyup(evtSMQty);
		inpPDQty.keyup(evtSMQty);
		inpTTQty.change(function(){
			var wrkQty = parseInt(inpWKQty.html().replace(/&nbsp;/, '') || 0);
			var sumQty = parseInt(inpTTQty.val() || 0);
	
			// <!-- Validation: WARNING if 'qty' is more than 'workOrderQty'. -->
			if( wrkQty < sumQty )
				inpTTQty.css("background-color", "red");
			else
				inpTTQty.css("background-color", "");
		}).change();
	}
	
	function getShiftTimeSelection( array ) {
		var elem = $("<select style='width:100px'></select>");
	
		elem.append( $("<option></option>").html("-- Select Time --") );
		for( var i in array ) {
			elem.append( $("<option></option>").val( array[i] ).html( array[i]-1 +".00 - "+ array[i] +".00" ) );
		}
		elem.append( $("<option></option>").val( 0 ).html("(Summary)" ) );
		return elem;
	}
	
	function deleteRow(obj){
		$(obj).closest("tr").remove(); 
		var tblNGReason= $("#tblNGReason");
		var index = 1;
		tblNGReason.find("tr:gt(1)").each(function(){
			var td = $(this).find("td:eq(0)");
			td.html(index++);
		});
	}
</script>
</head>
<body>
<h1><spring:message code='menu.DailyActual(Machine)'/></h1>
	<ul id="navlist">
		<li><a href="DAL_S05.html" >Daily Actual (Machine) Search/List</a></li>
		<li><a href="DAL_S06.html" id="current">Daily Actual (Machine) Add/Edit</a></li>
	</ul>
	<page:message item="${dailyMCWK}" />

	<form:form id="dailyMCWKForm" commandName="dailyMCWK">
		<form:hidden path="dailyMCWKId"/>

		<table class="ui-widget ui-widget-content" cellpadding="3" cellspacing="1" width="100%" border="0">
		<tr>
			<th width="10%">WIP <span class="textred">*</span></th>
			<td width="24%"><form:select path="wip" items="${wipMap}" id="boxWIP"></form:select></td>
			<th width="12%">Machine No <span class="textred">*</span></th>
			<td width="26%"><form:select path="machineId" items="${machineMap}" id="boxMachineNo"></form:select></td>
			<th width="9%" >Shift <span class="textred">*</span></th>
			<td width="19%" colspan="2">
				<form:radiobutton path="shift" value="D"/> Day
				<form:radiobutton path="shift" value="N"/> Night
			</td>
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
			<th>Worker</th>
			<td><form:input path="staff"/></td>
			<td align="right"><input type="button" id="btnDisplay" value="OK"/></td>
		</tr>
		</table><br />

	<div id="contentHTML"
		<c:if test="${empty dailyMCWK.dailyMCWKId}">
			style="display:none;"
		</c:if>
	>

		<span class="textred">* Required Field</span>
		<table id="tblNGReason" border="1" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content">
		<tr>
			<th rowspan="2">No.</th>
			<th rowspan="2">Time <span class="textred">*</span></th>
			<th rowspan="2">Work Order No. <span class="textred">*</span></th>
			<th rowspan="2">Lot<br />(3 Digits)</th>
			<th rowspan="2">Customer <span class="textred">*</span></th>
			<th rowspan="2">Part Name : Part No <span class="textred">*</span></th>
			<th rowspan="2">Work Order Qty</th>
			<th colspan="4">Actual Product <span class="textred">*</span></th>
			<th rowspan="2">Time<br />(min) <span class="textred">*</span></th>
			<th rowspan="2">Man Power</th>
			<c:if test="${fn:length(reasonNGList) > 0}">
			<th colspan="${fn:length(reasonNGList)+1}">NG Reason Qty <span class="textred">*</span></th>
			</c:if>
			<th rowspan="2">Action</th>
		</tr>
		<tr>
			<th>OK</th>
			<th>NG</th>
			<th>Pending</th>
			<th>Qty</th>
			<c:if test="${fn:length(reasonNGList) > 0}">
			<th>Total</th>
			</c:if>
			<c:forEach var="reason" items="${reasonNGList}">
			<th id="${reason.reasonId}" title="${reason.reasonName}" >${reason.reasonCode}</th>
			</c:forEach>
		</tr>
		<c:forEach var="detail" items="${dailyMCWK.detailList}" varStatus="prop" begin="0" step="1">
		<tr class="dailyMCWKdetailList">
			<td align="center"><page:rowno item="${dailyMCWK}" index="${prop.index}"/></td>
			<td align="center">
				<select style='width:100px'>
					<option>-- Select Time --</option>
					<c:if test="${dailyMCWK.shift == 'D'}">
					<option value= "9"<c:if test="${detail.reportTime ==  9}"> selected="selected"</c:if>> 8.00 -  9.00</option>
					<option value="10"<c:if test="${detail.reportTime == 10}"> selected="selected"</c:if>> 9.00 - 10.00</option>
					<option value="11"<c:if test="${detail.reportTime == 11}"> selected="selected"</c:if>>10.00 - 11.00</option>
					<option value="12"<c:if test="${detail.reportTime == 12}"> selected="selected"</c:if>>11.00 - 12.00</option>
					<option value="13"<c:if test="${detail.reportTime == 13}"> selected="selected"</c:if>>12.00 - 13.00</option>
					<option value="14"<c:if test="${detail.reportTime == 14}"> selected="selected"</c:if>>13.00 - 14.00</option>
					<option value="15"<c:if test="${detail.reportTime == 15}"> selected="selected"</c:if>>14.00 - 15.00</option>
					<option value="16"<c:if test="${detail.reportTime == 16}"> selected="selected"</c:if>>15.00 - 16.00</option>
					<option value="17"<c:if test="${detail.reportTime == 17}"> selected="selected"</c:if>>16.00 - 17.00</option>
					<option value="18"<c:if test="${detail.reportTime == 18}"> selected="selected"</c:if>>17.00 - 18.00</option>
					<option value="19"<c:if test="${detail.reportTime == 19}"> selected="selected"</c:if>>18.00 - 19.00</option>
					<option value="20"<c:if test="${detail.reportTime == 20}"> selected="selected"</c:if>>19.00 - 20.00</option>
					<option value="21"<c:if test="${detail.reportTime == 21}"> selected="selected"</c:if>>20.00 - 21.00</option>
					<option value="22"<c:if test="${detail.reportTime == 22}"> selected="selected"</c:if>>21.00 - 22.00</option>
					<option value="23"<c:if test="${detail.reportTime == 23}"> selected="selected"</c:if>>22.00 - 23.00</option>
					<option value="24"<c:if test="${detail.reportTime == 24}"> selected="selected"</c:if>>23.00 - 24.00</option>
					<option value="0"<c:if test="${detail.reportTime == 0}"> selected="selected"</c:if>>(Summary)</option>
					</c:if>
					<c:if test="${dailyMCWK.shift == 'N'}">
					<option value="21"<c:if test="${detail.reportTime == 21}"> selected="selected"</c:if>>20.00 - 21.00</option>
					<option value="22"<c:if test="${detail.reportTime == 22}"> selected="selected"</c:if>>21.00 - 22.00</option>
					<option value="23"<c:if test="${detail.reportTime == 23}"> selected="selected"</c:if>>22.00 - 23.00</option>
					<option value="24"<c:if test="${detail.reportTime == 24}"> selected="selected"</c:if>>23.00 - 24.00</option>
					<option value= "1"<c:if test="${detail.reportTime ==  1}"> selected="selected"</c:if>> 0.00 -  1.00</option>
					<option value= "2"<c:if test="${detail.reportTime ==  2}"> selected="selected"</c:if>> 1.00 -  2.00</option>
					<option value= "3"<c:if test="${detail.reportTime ==  3}"> selected="selected"</c:if>> 2.00 -  3.00</option>
					<option value= "4"<c:if test="${detail.reportTime ==  4}"> selected="selected"</c:if>> 3.00 -  4.00</option>
					<option value= "5"<c:if test="${detail.reportTime ==  5}"> selected="selected"</c:if>> 4.00 -  5.00</option>
					<option value= "6"<c:if test="${detail.reportTime ==  6}"> selected="selected"</c:if>> 5.00 -  6.00</option>
					<option value= "7"<c:if test="${detail.reportTime ==  7}"> selected="selected"</c:if>> 6.00 -  7.00</option>
					<option value= "8"<c:if test="${detail.reportTime ==  8}"> selected="selected"</c:if>> 7.00 -  8.00</option>
					<option value= "0"<c:if test="${detail.reportTime ==  0}"> selected="selected"</c:if>> (Summary)</option>
					</c:if>
				</select>
			</td>
			<td align="center"><input size="12" maxlength="11" value="${detail.workOrderNo}"/></td>
			<td align="center"><input size="4"  maxlength="3"  value="${detail.lotNo}"/></td>
			<td align="center">
				<select id='customerId'>
					<c:forEach var="custom" items="${customerMap}">
						<c:if test="${custom.key == detail.customerId}">
							<option value="${custom.key}" selected="selected">${custom.value}</option>
						</c:if>
						<c:if test="${custom.key != detail.customerId}">
							<option value="${custom.key}">${custom.value}</option>
						</c:if>
					</c:forEach>
				</select>
			</td>
			<td align="left">
				<select id='partId'>
					<c:forEach var="partno" items="${partKeySet[ detail.customerId ]}">
						<c:if test="${partno.key == detail.partId}">
							<option value="${partno.key}" selected="selected">${partno.value}</option>
						</c:if>
						<c:if test="${partno.key != detail.partId}">
							<option value="${partno.key}">${partno.value}</option>
						</c:if>
					</c:forEach>
				</select>
			</td>
			<td align="center">${detail.workOrderQty}</td>
			<td align="center"><input size="4" maxlength="7" class="keyin" value="${detail.ok}" onkeypress="return IntegerFilter(event)" /></td>
			<td align="center"><input size="4" maxlength="7" class="keyin" value="${detail.ng}" onkeypress="return IntegerFilter(event)" /></td>
			<td align="center"><input size="4" maxlength="7" class="keyin" value="${detail.pd}" onkeypress="return IntegerFilter(event)" /></td>
			<td align="center"><input size="4" class="keyin" value="${detail.qty}" readonly="readonly" tabindex=-1 /></td>
			<td align="center"><input size="4" maxlength="10" value="<fmt:formatNumber pattern="#,##0" value="${detail.timeUsed}"/>" onkeypress="return IntegerFilter(event)" /></td>
			<td align="center"><input size="4" maxlength="10" class="keyin"  value="<fmt:formatNumber pattern="#,##0" value="${detail.manPower}"/>" onkeypress="return IntegerFilter(event)" /></td>
			<c:if test="${fn:length(detail.ngReasonMap) > 0}">
			<td align="center" id=total >
				<c:set value="${0}" var="total"></c:set>
				<c:forEach var="reason" items="${detail.ngReasonMap}">
					<c:set value="${total + (reason.value)}" var="total"></c:set>
				</c:forEach>
				<c:out value="${total}"></c:out>
			</td>
			</c:if>
			<c:forEach var="reason" items="${detail.ngReasonMap}">
			<td align="center"><input size="4"  class="keyin"  maxlength="7" name="ngReasonQty" id="ngReasonQty"  value="${reason.value}" onkeypress="IntegerFilter()" /></td>
			</c:forEach>
			<td align="center">
				<c:if test="${ dailyMCWK.createDate ge minDate }">
					<a href="javascript:void(0);" onclick="deleteRow(this)"><img src="image/icon/delete.gif" width="16" height="16" class="delete"/></a>
				</c:if>	
			</td>
		</tr>
		</c:forEach>
        </table>
        
		<c:if test="${ dailyMCWK.createDate ge minDate }">
        	<input type="button" id="btnAddNGReason" value="Add New Row For Actual Input" style="margin: 10px 0px 20px 0px;"/>
		</c:if>
		<br/>
		<table id="tblStopMC" border="1" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content">
		<tr>
			<th>Machine Stop (minutes)</th>
			<c:if test="${dailyMCWK.shift == 'D'}">
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
			<th>21.00</th>
			<th>22.00</th>
			<th>23.00</th>
			<th>24.00</th>			
			</c:if>
			<c:if test="${dailyMCWK.shift == 'N'}">
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
			</c:if>
		</tr>
		<c:forEach var="res" items="${reasonInCatKeySet}">
			<tr>
				<th align="left">
					Category<br/>
					<select onchange=" comboBox.setReasonInCat($(this).nextAll('select'), $('select#boxWIP').val(), this.value); ">
						<c:forEach var="cat" items="${reasonCatMap}">
							<c:if test="${cat.key == res.value}">
								<option value="${cat.key}" selected="selected">${cat.value}</option>
							</c:if>
							<c:if test="${cat.key != res.value}">
								<option value="${cat.key}">${cat.value}</option>
							</c:if>
						</c:forEach>
					</select><br/>
					Reason <br/>
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
					<c:if test="${ dailyMCWK.createDate ge minDate }">
						<a href="javascript:void(0);" onclick=" deleteRow(this); "><img src="image/icon/delete.gif" width="16" height="16" /></a>
					</c:if>
				</th>
				<c:forEach var="stopMC" items="${dailyMCWK.stopMCList}">
				<td align="center"><input size="4" value="${stopMC[res.key]}"/></td>
				</c:forEach>
			</tr>
		</c:forEach>
		</table>
		<c:if test="${ dailyMCWK.createDate ge minDate }">
			<input type="button" id="btnAddStopMC" value="Add New Row For Stop Machine" style="margin: 10px 0px 20px 0px;"/><br />
			<br/>
			<input type="button" id="btnSave"   value="Save"/>
			<c:if test="${not empty dailyMCWK.dailyMCWKId}">
				<input type="button" id="btnDelete" value="Delete"/>
			</c:if>
		</c:if>
		<c:if test="${ dailyMCWK.createDate le minDate && not empty dailyMCWK.dailyMCWKId}">
			<br/>
		</c:if>
		<input type="button" id="btnCancel" value="Back" onclick="window.location='DAL_S05_search.html?button=back'"/>
	</div>
	</form:form>

</body>
</html>
