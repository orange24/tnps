<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var dailyWKForm;
	var boxCustomer;
	var boxPartNo;
	var boxLossTimeReason;
	var boxReportDate;
	var boxReportType;
	var boxShift;
	var boxWIP;
	var btnAddRow;
	var btnCancel;
	var btnDisplay;
	var btnDelete;
	var btnSave;
	var tblDetail;
	var tblDetailPart;
	var ngReasons = {};
	var dailyWKdailyWKDetailList;
	var currentLossTimeRow = null; // row ที่กำลัง edit loss time

	$(document).ready(function(){
		dailyWKForm     = $("form#dailyWKForm");
		boxReportDate   = $("input#reportDate");

		// ── init Loss Time popup dialog ─────────────────────────────────
		$("#lossTimeDialog").dialog({
			autoOpen  : false,
			modal     : true,
			width     : 480,
			title     : "Loss Time Reasons",
			buttons   : {
				"OK"     : function() { saveLossTimeDialog(); },
				"Cancel" : function() { $(this).dialog("close"); }
			}
		});
		boxReportType   = $("select[name=reportType]");
		boxShift        = $("input[name=shift]");
		boxWIP          = $("select#boxWIP");
		btnAddRow       = $("input#btnAddRow");
		btnCancel       = $("input#btnCancel");
		btnDisplay      = $("input#btnDisplay");
		btnDelete       = $("input#btnDelete");
		btnSave         = $("input#btnSave");
		tblDetail       = $("table#tblDetail");
		tblDetailPart   = $("table#tblDetailPart");
		dailyWKdailyWKDetailList = $(".dailyWKdailyWKDetailList").size();
		// <!-- Initial: Auto Completion. -->
		var workOrderList = {
			source: function( request, response ) {
				var customerId       = $(this).closest("tr").find("select#customerId");
				var partId           = $(this).closest("tr").find("select#partId");
	
				getJSON("DAL_S04_workOrderNoList", {
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
				var ordQty = tr.find("td:eq(5)");
				var sumQty = tr.find("input:eq(5)"); 
	
				ordQty.html( ui.item.workOrderQty || 0 );
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

		dailyWKForm.submit(function() { return false; });

		btnSave.click(function(){
			// <!-- Providing the data. -->
			var errors = [];
			var index = 0;
			var lossTimeData = []; // เก็บ losstime items ไว้ก่อน (formDataArray ยังไม่ถูก declare)
			var reasonHeaders = $("tbody > tr:eq(1) > th:gt(4)", tblDetailPart);
			$("tbody > tr:gt(1)", tblDetailPart).each(function(){
				var selects = $("select", this);
				var inputs = $("input:not([type=hidden])", this);
				var boxCustm = selects.eq(0);
				var boxPrtNo = selects.eq(1);
				var inpWKOrd = inputs.eq(0);
				var inpWKQty = parseInt($(this).find("td:eq(5)").html() || 0);
				var inpACQty = parseInt(inputs.eq(5).val() || 0);
				var inpPDNum = parseInt(inputs.eq(4).val() || 0);
				var inpNGNum = parseInt(inputs.eq(3).val() || 0);
				var inpOKNum = parseInt(inputs.eq(2).val() || 0);
				var inpTimeMin = parseInt(inputs.eq(6).val() || 0);
				var inpRSSum = 0;

				// <!-- Assigning the Element Name. -->
				boxCustm.attr("name", "dailyWKDetailList["+ index +"].customerId");
				boxPrtNo.attr("name", "dailyWKDetailList["+ index +"].partId");

				inpWKOrd.attr("name", "dailyWKDetailList["+ index +"].workOrderNo");
				inputs.eq(1).attr("name", "dailyWKDetailList["+ index +"].lotNo");
				inputs.eq(2).attr("name", "dailyWKDetailList["+ index +"].ok");
				inputs.eq(3).attr("name", "dailyWKDetailList["+ index +"].ng");
				inputs.eq(4).attr("name", "dailyWKDetailList["+ index +"].pd");
				inputs.eq(5).attr("name", "dailyWKDetailList["+ index +"].qty");
				inputs.eq(6).attr("name", "dailyWKDetailList["+ index +"].timeUsed");
				inputs.eq(7).attr("name", "dailyWKDetailList["+ index +"].manPower");
				inputs.eq(8).attr("name", "dailyWKDetailList["+ index +"].lossTime");
				inputs.eq(9).attr("name", "dailyWKDetailList["+ index +"].staff");

				// เก็บ lossTimeList items ไว้ใน temp array ก่อน (merge เข้า formDataArray ทีหลัง)
				var ltIndex = 0;
				$(this).find(".lossTimeItem").each(function() {
					var rid  = $(this).find(".ltReasonId").val();
					var time = $(this).find(".ltTime").val();
					if (rid && time) {
						lossTimeData.push({ name: "dailyWKDetailList["+ index +"].lossTimeList["+ ltIndex +"].lossTimeReasonId", value: rid });
						lossTimeData.push({ name: "dailyWKDetailList["+ index +"].lossTimeList["+ ltIndex +"].lossTime",         value: time });
						ltIndex++;
					}
				});
				for( var column = 10; column < inputs.length; column++ ) {
					// <!-- Finding the 'reasonId'. -->
					var reasonId = reasonHeaders.eq(column-10).attr("id");
					inpRSSum += parseInt(inputs.eq(column).attr("name", "dailyWKDetailList["+ index +"].ngReasonMap["+ reasonId +"]").val() || 0);
				}

				// <!-- Validation: if incomplete. -->
				if( !inpWKOrd.val() )
					errors.push({"code": "err.cmm.002", "arguments": [(index+1), "Work Order No."]});
				if( boxCustm.val() == '-2147483648')
					errors.push({"code": "err.cmm.002", "arguments": [(index+1),"Customer"]});
				if( boxPrtNo.val() == '-2147483648' )
					errors.push({"code": "err.cmm.002", "arguments": [(index+1),"Partno/Partname"]});
				if( isNaN(inpACQty) )
					errors.push({"code": "err.dal.002"});
				if( inpACQty === 0 && inpPDNum === 0 && inpNGNum === 0 && inpOKNum === 0 )
					errors.push({"code": "err.cmm.009", "arguments": [(index+1),"Atleast 1 of Qty"]});
				if( inpTimeMin === 0 )
					errors.push({"code": "err.cmm.009", "arguments": [(index+1),"Time (min)"]});
				//if( inpACQty > inpWKQty )
				//	errors.push({"code": "err.dal.006", "arguments": [(index+1)]});
				if( inpNGNum != inpRSSum )
					errors.push({"code": "err.dal.007", "arguments": [(index+1)]});

				index++;
			});

			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
			message.clear();

			// <!-- Seding the data. -->
			/* var params = $.param({
			           "wip": boxWIP.val(),
				"reportDate": boxReportDate.val(),
				"reportType": boxReportType.val(),
				     "shift": boxShift.filter(":checked").val()
			}) + '&' + dailyWKForm.serialize();
			 */
			var formDataArray = dailyWKForm.serializeArray();
			formDataArray.push({ name: "wip", value: boxWIP.val() });
			formDataArray.push({ name: "reportDate", value: boxReportDate.val() });
			formDataArray.push({ name: "reportType", value: boxReportType.val() });
			formDataArray.push({ name: "shift", value: boxShift.filter(":checked").val() });
			// merge lossTimeList items ที่เก็บไว้ก่อนหน้า
			for (var li = 0; li < lossTimeData.length; li++) {
				formDataArray.push(lossTimeData[li]);
			}

			disableSaveBtn();
			postJSON("DAL_S04_check", formDataArray, function( response ){
				if( response.errors && response.errors.length > 0 ) {
					message.setErrors(response.errors);
					enableSaveBtn();
					return;
				}

				if( !confirm("<spring:message code='cfm.cmm.001'/>") )
					return;

				postJSON("DAL_S04_save", formDataArray, function( result ){
					$("input, select, textarea").attr("disabled", true);

					if( result.errors && result.errors.length > 0 ) {
						message.setErrors(result.errors);
						enableSaveBtn();
						return;
					}
					message.setInfos ( result.infos  );
				});
			});
			return false;
		});

		boxWIP.change(function(){
			ngReasons = {};
			var initial = function( result ){
				var mainHeaderRow   = $("tbody > tr:eq(0) > th", tblDetailPart);
				var reasonHeaderRow = $("tbody > tr:eq(1)", tblDetailPart);

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
				reasonHeaderRow.append( $("<th>Total</th>") );
				/*for( key in ngReasons ) {
					reasonHeaderRow.append( $("<th></th>").attr("id", ngReasons[key]).html( key ) );
					console.log(ngReasons[key]);
				}*/
				for( var i = 0; i < result.length ; i++ ) {
					reasonHeaderRow.append( $("<th></th>").attr("id", result[i].reasonId).attr("title", result[i].reasonName).html( result[i].reasonCode ) );
				}
			};

			getJSON("getReasonNGList", { "wip": $(this).val() }, initial);
		});

		btnAddRow.click(function(){
			var mainHeaderRow = $("tbody > tr:eq(0) > th", tblDetailPart);
			var cntColumn = 0;
			var rowTemplt = $("<tr></tr>");
			mainHeaderRow.each(function(){
				cntColumn += $(this).attr("colspan");
			});

			// <!-- Initial Event. -->
			var wrkOdr = $("<input size='12' maxlength='11' />").autocomplete(workOrderList);
			var custom = boxCustomer.clone(true);
			var partNo = boxPartNo.clone(true);

			// <!-- Generating Row Template. -->
			rowTemplt.append( $("<td align='center'></td>").html( $("tbody > tr", tblDetailPart).length - 1 ) );
			rowTemplt.append( $("<td align='center'></td>").append( wrkOdr ) );
			rowTemplt.append( $("<td align='center'></td>").html( "<input size='4' maxlength='7' />" ) );
			rowTemplt.append( $("<td align='center'></td>").append( custom ) );
			rowTemplt.append( $("<td align='left'></td>").append( partNo ) );
			rowTemplt.append( $("<td align='center'></td>").html( "&nbsp;" ) );
			rowTemplt.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7' />").keypress(IntegerFilter) ) );
			rowTemplt.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7' />").keypress(IntegerFilter) ) );
			rowTemplt.append( $("<td align='center'></td>").html( $("<input size='4' maxlength='7' />").keypress(PositiveIntegerFilter) ) );
			rowTemplt.append( $("<td align='center'></td>").html( "<input size='4'  readonly='readonly' tabindex=-1 />" ) );
			rowTemplt.append( $("<td align='center'></td>").html( "<input size='4'  maxlength='10' />" ).keypress(IntegerFilter) );
			rowTemplt.append( $("<td align='center'></td>").html( "<input size='4'  maxlength='10' />" ).keypress(IntegerFilter) );
			rowTemplt.append( $("<td align='center'></td>").html( "<input size='4' readonly='readonly' tabindex='-1' />" ) );
			rowTemplt.append(
				$("<td align='center'></td>")
					.append( $("<button type='button'>Edit</button>").click(function(){ openLossTimeDialog(this); }) )
					.append( $("<br/>") )
					.append( $("<span class='lossTimeSummary' style='font-size:11px;color:#555;'></span>") )
					.append( $("<span class='lossTimeItemContainer'></span>") )
			);
			rowTemplt.append( $("<td align='center'></td>").html( "<input size='12' maxlength='50'/>" ) );
			if (cntColumn > 16) {
				rowTemplt.append( $("<td align='center'></td>").attr('id','total').html('&nbsp;') );
			}
			for( var i = 0; i < cntColumn - 17; i++ ) {
				rowTemplt.append( 
					$("<td align='center'></td>")
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
			rowTemplt.append( $("<td align='center'></td>").html( "<a href='javascript:void(0);' onclick='deleteRow(this)'><img src='image/icon/delete.gif'/></a>" ) );
			setQtyCalculation( rowTemplt );

			$("tbody", tblDetailPart).append(rowTemplt);
		});

		btnDisplay.click(function(){
			var errors = [];
			if( boxWIP.attr("selectedIndex") === 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["WIP"]});
			if( !boxReportDate.val() )
				errors.push({"code": "err.cmm.001", "arguments": ["Report Date"]});
			if( !boxShift.is(":checked") )
				errors.push({"code": "err.cmm.001", "arguments": ["Shift"]});
			if( boxReportType.attr("selectedIndex") === 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["Report Type"]});
			
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
	
			// <!-- Validation Successfully. -->
			message.clear();
			setCriteriaUsability( false );
	
			btnAddRow.click();
			tblDetail.css("display", "");
			return true;
		});
	
		btnDelete.click(function(){
			var dailyWKId = dailyWKForm.find("input[name=dailyWKId]").val();
			if( dailyWKId ) {
				if( confirm("<spring:message code='cfm.cmm.002'/>") )
					document.location = "DAL_S04_delete.html?dailyWKId="+ dailyWKId;
			} else {
				document.location.reload(true);
			}
			return false;
		});
	
		// <!-- On 'Edit' Mode. -->
		if( tblDetail.is(":visible") ) {
			setCriteriaUsability( false );
			$("tbody > tr:gt(1)", tblDetailPart).each(function(){
				$(this).find("input:eq(0)").autocomplete(workOrderList);
				setQtyCalculation( $(this) );
			});
			if(dailyWKdailyWKDetailList < 1){	
				getJSON("boxCustomer1",{},function(result){
					boxCustomer.empty();
					$.each(result,function(val, text){
						boxCustomer.append( $("<option></option>").val(val).html(text) );
					});
					btnAddRow.click();
				});

				getJSON("getLossTimeReasonList",{},function(result){
					console.log("getLossTimeReasonList", result);
					boxLossTimeReason.empty();
					$.each(result,function(val, text){
						boxLossTimeReason.append( $("<option></option>").val(val).html(text) );
					});
				});
			}
		}

		$("select[id=customerId]").add(boxCustomer).change(function(){
			var partNo = $(this).parent().parent().find("#partId");
			var params = { "customerId": "", "wip": "", "reportType": boxReportType.val() };
			if( $(this).val() )  params.customerId = $(this).val();
			if( boxWIP.val() )   params.wip        = boxWIP.val();
			getJSON("DAL_S04_getPartNameNo",params,function(result){
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
		
		// <!-- sum total ngReasonQty -->
		$("input[id=ngReasonQty]").keyup(function(){
			var rowTemplt = $("tbody > tr:gt(0)", tblDetailPart);
			var reason = $("input[id=ngReasonQty]");
			rowTemplt.each(function(){
			var total=0;
				$(this).find("input[id=ngReasonQty]").each(function(){
					total+=parseInt($(this).val() || 0,10);
					$(this).keypress(IntegerFilter);
				});
				$(this).find("td[id=total]").html(total);
			});
		});
	});
	
	function disableSaveBtn(){
		btnSave.attr("disabled", true);
	}
	
	function enableSaveBtn(){
		btnSave.attr("disabled", false);
	}
	
	function setCriteriaUsability( flag ) {
		if( flag ) {
			boxWIP.removeAttr("disabled");
			boxReportDate.datepicker("enable");
			boxReportType.removeAttr("disabled");
			boxShift.removeAttr("disabled");
			btnDisplay.removeAttr("disabled");
		} else {
			boxWIP.attr("disabled", true);
			boxReportDate.datepicker("disable");
			boxReportType.attr("disabled", true);
			boxShift.attr("disabled", true);
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
		var inpTime = inputs.eq(6);
		var inpManPower = inputs.eq(7);
		var inpReason = tr.find("input#ngReasonQty");
		var evtSMQty = function(){
			inpTTQty.val( parseInt(inpOKQty.val() || 0) + parseInt(inpNGQty.val() || 0) + parseInt(inpPDQty.val() || 0) ).change();
		};
	
		// <!-- Assigning Behavior. -->
		inpOKQty.keypress(IntegerFilter).keyup(evtSMQty);
		inpNGQty.keypress(IntegerFilter).keyup(evtSMQty);
		inpPDQty.keypress(IntegerFilter).keyup(evtSMQty);
		inpTTQty.change(function(){
			var wrkQty = parseInt(inpWKQty.html().replace(/&nbsp;/, '') || 0);
			var sumQty = parseInt(inpTTQty.val() || 0);
	
			// <!-- Validation: WARNING if 'qty' is more than 'workOrderQty'. -->
			if( wrkQty < sumQty )
				inpTTQty.css("background-color", "red");
			else
				inpTTQty.css("background-color", "");
		}).change();
		inpTime.keypress(IntegerFilter);
		inpManPower.keypress(IntegerFilter);
		inpReason.keypress(IntegerFilter);
	}
	
	function deleteRow(obj){
		$(obj).closest("tr").remove();
		var tblDetailPart= $("#tblDetailPart");
		var index = 1;
		tblDetailPart.find("tr:gt(1)").each(function(){
			var td = $(this).find("td:eq(0)");
			td.html(index++);
		});
	}

	/* ── Loss Time Popup ──────────────────────────────────────────────── */

	function openLossTimeDialog(btn) {
		currentLossTimeRow = $(btn).closest("tr");
		var tbody = $("#lossTimePopupTable tbody");
		tbody.empty();

		// โหลด items ที่มีอยู่แล้วใน row (จาก hidden inputs)
		var items = [];
		currentLossTimeRow.find(".lossTimeItem").each(function() {
			items.push({
				reasonId : $(this).find(".ltReasonId").val(),
				time     : $(this).find(".ltTime").val()
			});
		});

		if (items.length === 0) {
			addLossTimePopupRow();
		} else {
			$.each(items, function(i, item) {
				addLossTimePopupRow(item.reasonId, item.time);
			});
		}
		$("#lossTimeDialog").dialog("open");
	}

	function addLossTimePopupRow(reasonId, time) {
		var reasonSel = boxLossTimeReason.clone(true).removeAttr("id");
		if (reasonId) reasonSel.val(reasonId);

		var timeInp = $("<input type='text' size='6' maxlength='6' />")
			.keypress(IntegerFilter)
			.val(time || "");

		var delBtn = $("<a href='javascript:void(0);'>")
			.html("<img src='image/icon/delete.gif' width='16' height='16'/>")
			.click(function() { $(this).closest("tr").remove(); });

		var tr = $("<tr>")
			.append($("<td>").append(reasonSel))
			.append($("<td align='center'>").append(timeInp))
			.append($("<td align='center'>").append(delBtn));
		$("#lossTimePopupTable tbody").append(tr);
	}

	function saveLossTimeDialog() {
		var items = [];
		var total = 0;
		var valid = true;

		$("#lossTimePopupTable tbody tr").each(function() {
			var reasonId = $(this).find("select").val();
			var time     = parseInt($(this).find("input").val() || 0, 10);
			if (!reasonId || reasonId == "" || isNaN(time) || time < 0) {
				valid = false; return false;
			}
			items.push({ reasonId: reasonId, time: time });
			total += time;
		});

		if (!valid) { alert("กรุณากรอกเหตุผลและ Loss Time ให้ครบทุกแถว"); return; }

		// อัพเดต hidden inputs ใน row
		currentLossTimeRow.find(".lossTimeItemContainer").empty();
		$.each(items, function(j, item) {
			var container = $("<span class='lossTimeItem'>")
				.append($("<input type='hidden' class='ltReasonId'>").val(item.reasonId))
				.append($("<input type='hidden' class='ltTime'>").val(item.time));
			currentLossTimeRow.find(".lossTimeItemContainer").append(container);
		});

		// อัพเดต lossTime input (inputs.eq(8)) และ label แสดงผล
		var inputs = currentLossTimeRow.find("input:not([type=hidden])");
		inputs.eq(8).val(total);

		// แสดง summary ของเหตุผล
		var names = [];
		currentLossTimeRow.find(".lossTimeItemContainer .lossTimeItem").each(function() {
			var rid = $(this).find(".ltReasonId").val();
			var sel = $("#lossTimePopupTable").find("select");
			names.push($("#lossTimePopupTable tbody tr").eq(names.length).find("select option:selected").text());
		});
		currentLossTimeRow.find(".lossTimeSummary").html(items.length + " reason(s)");

		$("#lossTimeDialog").dialog("close");
	}

	/* ── End Loss Time Popup ──────────────────────────────────────────── */

	// <!-- Initial Processing. -->
	comboBox.setCustomer(boxCustomer = $("<select id='customerId'></select>"));
	comboBox.setPartNo(boxPartNo   = $("<select id='partId'></select>"));
	comboBox.setLossTimeReason(boxLossTimeReason   = $("<select id='lossTimeReasonId'></select>"));
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
<h1><spring:message code='menu.DailyActual(Worker)'/></h1>
	<ul id="navlist">
		<li><a href="DAL_S03.html" >Daily Actual (Worker) Search/List</a></li>
		<li><a href="DAL_S04.html" id="current">Daily Actual (Worker) Add/Edit</a></li>
	</ul>
	<page:message item="${dailyWK}" />

	<form:form id="dailyWKForm" commandName="dailyWK">
		<form:hidden path="dailyWKId"/>
<table class="ui-widget ui-widget-content" cellpadding="3" cellspacing="1" width="100%" border="0">
  <tr>
    <th width="14%">WIP <span class="textred">*</span></th>
    <td width="31%"><form:select path="wip" items="${wipMap}" id="boxWIP"></form:select></td>
    <th width="15%">Report Date <span class="textred">*</span></th>
    <td width="23%"><form:input path="reportDate" cssClass="date"/></td>
    <td width="17%"></td>
    </tr>
  <tr>
    <th>Shift <span class="textred">*</span></th>
    <td>
      	<form:radiobutton path="shift" value="D"/> Day
      	<form:radiobutton path="shift" value="N"/> Night
    </td>
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
<br />

	<table id="tblDetail" cellpadding="3" cellspacing="1" border="0" width="100%" class="tableBorder2"
		<c:if test="${empty dailyWK.dailyWKId}">
		style="display:none;"
		</c:if>
	>
		<tr>
			<td>
				<span class="textred">* Required Field</span>
				<table id="tblDetailPart" width="100%" border="1" align="center" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content" id="dynamictbl">
					<tr>
						<th rowspan="2">No.</th>
						<th rowspan="2">Work Order No. <span class="textred">*</span></th>
						<th rowspan="2">Lot<br /> (3 Digits) <br /></th>
						<th rowspan="2">Customer <span class="textred">*</span></th>
						<th rowspan="2">Part Name : Part No <span class="textred">*</span></th>
						<th rowspan="2">Work Order Qty<span class="textred">*</span></th>
						<th colspan="4">Actual Product<span class="textred">*</span></th>
						<th rowspan="2">Time<br />(min) <span class="textred">*</span></th>
						<th rowspan="2">Man<br /> Power</th>
						<th rowspan="2">Loss Time (min)</th>
						<th rowspan="2">Reason</th>
						<th rowspan="2">Worker</th>
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
					<c:forEach var="detail" items="${dailyWK.dailyWKDetailList}" varStatus="prop" begin="0" step="1">
					<tr class="dailyWKdailyWKDetailList">
						<td align="center"><page:rowno item="${dailyWK}" index="${prop.index}"/></td>
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
						<td align="center"><input size="4"  maxlength="7"  value="${detail.ok}"/></td>
						<td align="center"><input size="4"  maxlength="7"  value="${detail.ng}"/></td>
						<td align="center"><input size="4"  maxlength="7"  value="${detail.pd}"/></td>
						<td align="center"><input size="4"  readonly="readonly" value="${detail.qty}" tabindex=-1/></td>
						<td align="center"><input size="4"  maxlength="10" value="<fmt:formatNumber pattern="#,##0" value="${detail.timeUsed}"/>"/></td>
						<td align="center"><input size="4"  maxlength="10" value="<fmt:formatNumber pattern="#,##0" value="${detail.manPower}"/>"/></td>
						<td align="center"><input size="4" maxlength="10" readonly="readonly" tabindex="-1" value="<fmt:formatNumber pattern="#,##0" value="${detail.lossTime}"/>"/></td>
						<td align="center">
							<button type="button" onclick="openLossTimeDialog(this)">Edit</button><br/>
							<span class="lossTimeSummary" style="font-size:11px;color:#555;">
								<c:if test="${not empty detail.lossTimeList}">
									${fn:length(detail.lossTimeList)} reason(s)
								</c:if>
							</span>
							<span class="lossTimeItemContainer">
								<c:forEach var="lossItem" items="${detail.lossTimeList}">
									<span class="lossTimeItem">
										<input type="hidden" class="ltReasonId" value="${lossItem.lossTimeReasonId}"/>
										<input type="hidden" class="ltTime"     value="${lossItem.lossTime}"/>
									</span>
								</c:forEach>
							</span>
						</td>
						<td align="center"><input size="12" maxlength="50" value="${detail.staff}"/></td>
						<c:if test="${fn:length(detail.ngReasonMap) > 0}">
						<td align="center" id="total" >
							<c:set value="${0}" var="total"></c:set>
							<c:forEach var="reason" items="${detail.ngReasonMap}">
								<c:set value="${total + (reason.value)}" var="total"></c:set>
							</c:forEach>
							<c:out value="${total}"></c:out>
						</td>
						</c:if>
						<c:forEach var="reason" items="${detail.ngReasonMap}">
						<td align="center"><input size="4"  maxlength="7" id="ngReasonQty"  value="${reason.value}"/></td>
						</c:forEach>
						<td align="center">
							<c:if test="${dailyWK.createDate ge minDate}">
								<a href="javascript:void(0);" onclick="deleteRow(this);"><img src="image/icon/delete.gif" width="16" height="16" class="delete"/></a>
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<td>
			<c:if test="${dailyWK.createDate ge minDate}">
				<input type="button" id="btnAddRow" value="Add New Row"/>
				<input type="button" id="btnSave"   value="Save"/>
				<c:if test="${not empty dailyWK.dailyWKId}">
					<input type="button" id="btnDelete" value="Delete"/>
				</c:if>
			</c:if>
				<input type="button" id="btnCancel" value="Back" onclick="window.location='DAL_S03_search.html?button=back'"/>
			</td>
		</tr>
	</table>
	</form:form>

	<!-- Loss Time Popup Dialog -->
	<div id="lossTimeDialog" style="display:none;">
		<table id="lossTimePopupTable" width="100%" border="1" cellpadding="4" cellspacing="0" class="ui-widget ui-widget-content">
			<thead>
				<tr>
					<th>Loss Time Reason</th>
					<th>Loss Time (min)</th>
					<th></th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
		<br/>
		<button type="button" onclick="addLossTimePopupRow()">+ Add Reason</button>
	</div>

</body>
</html>
