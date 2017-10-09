<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp"%>
<style>
.b1{	
	font-weight: bold;
}
</style>
<script language="javascript">
	var isPlanExists;
	var resultFg;
	var btnDelete;
	var btnAddNewTime;
	var boxFg;
	var btnSearch;
	var btnSave;
	var customerId;
	var dlvS02Form;
	var dayOfMonth = "${deliveryPlan.dayOfMonth}";
	var month = "${deliveryPlan.month}";
	var year = "${deliveryPlan.year}";
	var plan = {};
	var dateList = {};
	var time0 = {};// check default time
	var backOrder = "";
	var balanceOrder = "";
	
	//value Balance Order & Delivery Plan(Back) for display column "0"
	<c:if test="${not empty plan}">
		plan = ${plan};
		dateList = plan["dateList"];
		

			for(var index=1, max = dateList.length; index < max; index++){
				time0 = dateList[index]["timeList"];
				if(time0.length > 0){break;}
			}
	 	if(dateList.length>0){
			if(dateList[0]["balanceOrderQty"] == null){
				backOrder = "";
				balanceOrder = "";
			}else{
				if(dateList[0]["balanceOrderQty"] == 0){
					// set "" (blank) for making it same the report
					backOrder = "";
					balanceOrder = "";
				}else if(dateList[0]["balanceOrderQty"] < 0){
					var tempBO = dateList[0]["balanceOrderQty"];
					var b = (tempBO + "").split("-");
					backOrder = b[1];
					balanceOrder = tempBO;
				}else{
					backOrder = "<b><font color = 'red'>(" + dateList[0]["balanceOrderQty"] + ")</font></b>";
					balanceOrder = dateList[0]["balanceOrderQty"];
				}
			}
		}else{
				backOrder = "";
				balanceOrder = "";
 		}
	</c:if>

	var arrActual    = new Array(dayOfMonth);// fgOut
	var arrBDelivery = new Array(dayOfMonth);// balanceDeliveryQty
	var arrBOrder    = new Array(dayOfMonth);// balanceOrderQty
	var arrForCast   = new Array(dayOfMonth);// forCastQty
	var arrCommit    = new Array(dayOfMonth);// tkiCommitQty
	var arrCustReq   = new Array(dayOfMonth);// custReqQty
	var arrDBack     = new Array(dayOfMonth);// deliveryQtyBack
	var arrDNormal   = new Array(dayOfMonth);// deliveryQtyNormal
	var arrDTotal    = new Array(dayOfMonth);// deliveryQtyTotal
	var arrProPlan   = new Array(dayOfMonth);// productionPlanQty
	var arrBDeliveryObj  = new Array(dayOfMonth);
	var arrBOrderObj     = new Array(dayOfMonth);
	var arrDNormalObj    = new Array(dayOfMonth);
	var arrDTotalObj     = new Array(dayOfMonth);
	var arrProPlanObj    = new Array(dayOfMonth);
	
	var trDate;		// Date
	var trForCast;	// forCast
	var trCustReq;	// custReq
	var trCommit;	// commit
	var trProPlan;	// productionPlan
	var trDNormal;	// deliveryPlanNormal
	var trDBack;	// deliveryPlanBack
	var trDTotal;	// deliveryPlanNormal
	var trActDel;	// actualDelivery
	var trBalDel;	// balanceDelivery
	var trBalOrd;	// balanceOrder
	var trReason;	// reason
	var trDateT;	// date Time
	var trTime;		// time
	var trSelectTime; // selectTime
	var txtBalanceOrder; // text BalanceOrder
	
	$(document).ready(function() {
		isPlanExists   = $("input#isPlanExists");
		btnDelete      = $("#btnDelete");
		btnAddNewTime  = $("input#btnAddNewTime");
		btnSave        = $("#btnSave");
		btnSearch      = $("#btnSearch");
		boxFg          = $("select#boxFg");
		customerId     = ${deliveryPlan.customerId};
		dlvS02Form     = $("#dlvS02Form");
			
		//  converse monthMap is object of javascript 
		var m = "${monthMap}".replace("{",'').replace("}",'').split(",");
		var monthMap = {};
		for( var v in m){  var a = m[v].split("="); monthMap[(a[0]+'').replace(' ','')]=a[1]; }
				
		//  init Reason 
		var rMap = "${reasonMap}".split(";");
		var rArr = {};
		var selectReason = $("<select><option> </option></select>");
		for(i=0, max = rMap.length-1;i<max;i++){
			rArr = rMap[i].split(":");
			selectReason.append( $("<option></option>").val(rArr[0]).html(rArr[1]) );
		}
				
		//  init search boxFg 	
		if (customerId) {
			var paramFg = [
							 "customerId="+ customerId
							,"revision=${deliveryPlan.revision}"
							,"month=${deliveryPlan.month}"
							,"year=${deliveryPlan.year}"
			];
		
			postJSON("DLV_S02_boxFgNameNo", paramFg.join("&"), function(result) {
				resultFg = result;
				fgList();
			});
		}
		
		// checkbox fg
		isPlanExists.click(function(){
			fgList();
			boxFg.val("");// val from click search
			$("#divDetail").css("display","none");
		});
		
		//  Put information and textbox into rows 
		trDate    = $("table:eq(2) > tbody > tr:eq(1)"); // Date
		trForCast = $("table:eq(2) > tbody > tr:eq(2)"); // forCast
		trCustReq = $("table:eq(2) > tbody > tr:eq(3)"); // custReq
		trCommit  = $("table:eq(2) > tbody > tr:eq(4)"); // commit
		trProPlan = $("table:eq(2) > tbody > tr:eq(5)"); // productionPlan
		trDNormal = $("table:eq(2) > tbody > tr:eq(6)"); // deliveryPlanNormal
		trDBack   = $("table:eq(2) > tbody > tr:eq(7)"); // deliveryPlanBack
		trDTotal  = $("table:eq(2) > tbody > tr:eq(8)"); // deliveryPlanNormal
		trActDel  = $("table:eq(2) > tbody > tr:eq(9)"); // actualDelivery
		trBalDel  = $("table:eq(2) > tbody > tr:eq(10)");// balanceDelivery
		trBalOrd  = $("table:eq(2) > tbody > tr:eq(11)");// balanceOrder
		trReason  = $("table:eq(2) > tbody > tr:eq(12)");// reason
		trDateT   = $("table:eq(2) > tbody > tr:eq(16)");// date Time
		trSelectTime   = $("table:eq(1) > tbody > tr:eq(15)");// SelectTime
		trTime    = $("<tr id='timeList'><td align='center' class='b1'>&nbsp;</td><td>&nbsp;</td></tr>");// time

		$("table:eq(2) > tbody > tr:eq(0)  > th:eq(0)").attr("colspan", dayOfMonth+ 2).html(monthMap[month]+ "&nbsp;" + year);// Title Day
		$("table:eq(2) > tbody > tr:eq(13) > td:eq(0)").attr("colspan",dayOfMonth + 4);// tr Empty
		$("table:eq(2) > tbody > tr:eq(14) > td:eq(0)").attr("colspan",dayOfMonth+ 4);// header time
		$("table:eq(2) > tbody > tr:eq(15) > th:eq(0)").attr("colspan",dayOfMonth + 2).html(monthMap[month]+ "&nbsp;" + year);// Title Time
//Comment by Toi : change use to textbox 		trBalOrd.find("td:eq(1)").html(backOrder);
		// balanceOrder of last month
		$('#txtBalanceOrder').val(balanceOrder);
		// add delivery (back) at date 0 = backorder last day of previous month  P'Pok Confirm
		trDBack.find("td:eq(1)").html("<strong>" + backOrder + "<strong>");// DeliveryPlan(Back)  of last month
		
		for(var index=0, max = dateList.length-1;index<max;index++){
			trDate.append("<th align='center'>"+ (index+1) +"</th>");
			trForCast.append($("<td align='center'></td>").append(initAction(trForCast,checkDefaultValue("forCastQty",(index+1)),arrForCast,index)));
			trCustReq.append($("<td align='center'></td>").append(initAction(trCustReq,checkDefaultValue("custReqQty",(index+1)),arrCustReq,index)));
			trCommit.append($("<td align='center'></td>").append(initAction(trCommit,checkDefaultValue("tkiCommitQty",(index+1)),arrCommit,index)));
			trProPlan.append("<td align='center' class='b1'>" +checkDefaultValue("productionQty",(index+1))+ "</td>");							
			trDNormal.append("<td align='center' class='b1'>" +checkDefaultValue("deliveryQtyNormal",(index+1))+ "</td>");
			trDBack.append($("<td align='center'></td>").append(initAction(trDBack,checkDefaultValue("deliveryQtyBack",(index+1)), arrDBack,index)));
			trDTotal.append("<td align='center' class='b1'>" +checkDefaultValue("deliveryQtyTotal",(index+1))+ "</td>");
			trActDel.append("<td align='center' class='b1'>" +checkActualDefaultValue("fgOut",(index+1))+ "</td>");
			trBalDel.append("<td align='center' class='b1'>" +checkDefaultValue("balanceDeliveryQty",(index+1))+ "</td>");
			trBalOrd.append("<td align='center' class='b1'>" +checkDefaultValue("balanceOrderQty",(index+1))+ "</td>");
			trReason.append($("<td align='center'></td>").append($(selectReason.clone(true)).val(checkDefaultValue("reason",(index+1)))));
			trDateT.append("<th align='center'>"+ (index+1) +"</th>");
			trTime.append($("<td align='center'></td>").append(initActionTime(index)));
		}

		//  init btnAddNewTime click  
		trSelectTime.remove();
		btnAddNewTime.click(function(){
			trSelectTime.clone(true).insertBefore($("#btnRow"));
			var lastTimeRow = $("table:eq(2)").find("tr:last");
			var tempRow = trTime.clone(true);
			tempRow.insertAfter(lastTimeRow);
			lastTimeRow = tempRow;
			sumTotalTime("");
		});
		
		//  init default Time 
		//if((plan != undefined || plan.length > 0) && time0.length > 0){
		if(time0.length > 0){
			var prevTimeHr, prevTimeMin;
			var newLnFlag = false;
			var txbs;
			var trIndex = -1;
			for(var i=0, max=time0.length; i < max; i++){
				var timeHr = time0[i]["timeHr"];
				var timeMin = time0[i]["timeMin"];
				if(
					((prevTimeHr == undefined || prevTimeMin == undefined) || // first row
					(prevTimeHr != timeHr || prevTimeMin != timeMin)) && // start new line
					(timeHr != null || timeMin != null)
				){
					btnAddNewTime.click();
					prevTimeHr = timeHr;
					prevTimeMin = timeMin;
					newLnFlag = true;
					trIndex++;
				}
					
				var trName = $("tr[id=selectTimeList]:eq("+trIndex+")");
				trName.each(function(){
					if(newLnFlag){
						if(trIndex == 0){ $(this).find("img").remove(); $("#timeList:eq(0)").find(":text").attr("disabled",true); }
									
						var select = trName.find("select");
						select.each(function(ci){
							if(ci==0){
								$(this).val(timeHr);
							}else if(ci==1){
								$(this).val(timeMin);
							}
						});
					}
					newLnFlag = false;
				});
						
			}
		}else{
			var timePlan = "${timePlan}".split(";");
			var time = {};
			for(i=0, max = timePlan.length-1;i<max;i++){
				btnAddNewTime.click();
				time = timePlan[i].split(".");
				$('select[id="timeHr"]').eq(i).val(time[0]);
				$('select[id="timeMin"]').eq(i).val(time[1]);
				if(i == 0){
					var tr1 = $("#selectTimeList:eq(0)");
					var tr2 = $("#timeList:eq(0)");
					tr1.find("img").remove();
					tr2.find(":text").attr("disabled",true);
				}
			}
		}
				
		//  init value column Total 
		if(dateList.length > 0){
			initValArr();
			calcData(trCustReq,-1,arrCustReq);// send custReq because need calcBalanceDelivery when load form

			//  default deliveryPlan(Normal) and deliveryPlan(Back) 
			trDNormal.find("td:gt(1)").each(function(i){
				var td = $(this);
				arrDNormal[i] = dateList[i+1]["deliveryQtyNormal"] == null ? "" :dateList[i+1]["deliveryQtyNormal"];
				arrDBack[i] = dateList[i+1]["deliveryQtyBack"] == null ? "" : dateList[i+1]["deliveryQtyBack"];
				td.html(arrDNormal[i]);
				trDBack.find(":text:eq("+i+")").val(arrDBack[i]);
			});
			calcSumRw(trDBack);
			
			//  default timeList 
			var trTempTime = $("tr[id=timeList]");
			trTempTime.each(function(index){
				var tr = $(this);
				tr.find(":text").each(function(i){
					if(dateList[i+1]["timeList"].length > 0){
						var deliveryTimeQty = "";
						if(deliveryTimeQty = dateList[i+1]["timeList"][index] != undefined){
							deliveryTimeQty = dateList[i+1]["timeList"][index]["deliveryQty"] == null   ? "" : dateList[i+1]["timeList"][index]["deliveryQty"];
							$(this).val(deliveryTimeQty);	
						};
					};
				});
			});
			sumTotalTime("","","init");
		}
		
		// use search and save
		var deliv = [];
		deliv[0] = "customerId=${deliveryPlan.customerId}"; 
		deliv[1] = "deliveryPlanId=${deliveryPlan.deliveryPlanId}";
		deliv[2] = "month=${deliveryPlan.month}";
		deliv[3] = "year=${deliveryPlan.year}";
		deliv[4] = "fgId=${deliveryPlan.fgId}";	
		
		// export excel
		$("input#btnExport").click(function(){
			var params = [
							 deliv[0]
							,"customerName=${deliveryPlan.customerName}"
							,deliv[1]
							,"revision=${deliveryPlan.revision}"
							,deliv[2]
							,deliv[3]
						];

			downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));

			// <!-- CALL: 'DLV_S02Controller'. -->
			dlvS02Form.attr("action", "DLV_S01_export.xls?"+params.join("&"));
			/* dlvS02Form.submit(); */
			document.getElementById("dlvS02Form").submit();
		});
		
		// search button 
		btnSearch.click(function() {
			var errors = [];
			if (boxFg.attr("selectedIndex") === 0)
				errors.push({"code" : "err.cmm.001","arguments" : [ "FG Name : FG No" ]});
			if (errors.length > 0) {
				message.setErrors(errors);
				return false;
			}
			message.clear();
			var params = [
						 deliv[0]
						,"customerName=${deliveryPlan.customerName}"
						,deliv[1]
						,"revision=${deliveryPlan.revision}"
						,deliv[2]
						,deliv[3]
					];
			var fgArr = deliv[4].split("=");
			if(fgArr[1] != boxFg.val()){deliv[4] = "fgId=";	}
			dlvS02Form.attr("action", "DLV_S02_search.html?"+params.join("&"));
			/* dlvS02Form.submit(); */
			document.getElementById("dlvS02Form").submit();
		});
				
		// save button
		btnSave.click(function(){
			var errors = [];
			checkTimeDuplicate(errors);
			checkDeliveryPlanBack(errors);
			checkReason(errors);
			//checkTime(errors);
			checkSumTimeOfDate(errors);
					
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
			message.clear();
						
			if( !confirm("<spring:message code='cfm.cmm.001'/>") )
				return;
 
			//  Data Binding. 
			var table = $("table:eq(2)");
			var param = [];

			//  Data Binding: t_deliveryplan_date 
			var funct = function(index){ param.push("dateList["+ (index+1) +"]."+ $(this).closest("tr").attr("id") +"="+ ($(this).val().trim()== "" ? "" : $(this).val())); };
			var functText = function(index){
				var val = $(this).text();
				if(val.indexOf("(") >= 0){
					val = val.replace("(","").replace(")","");
					val = val*(-1);
				}
				val = (val+"").trim() == "" ? "" : val;
				param.push("dateList["+ (index+1) +"]."+ $(this).closest("tr").attr("id") +"="+ val); 
			};
			table.find("tr:eq(1) > th:gt(1)").each(function(index){ // SET: fgId, deliveryDate, detailPlanId
				var day = $(this).html();
				var mth = "${deliveryPlan.month + 1}";
				param.push("dateList["+ (index+1) +"].fgId=${deliveryPlan.fgId}");
				param.push("dateList["+ (index+1) +"].deliveryDate="+ (day.length==1?'0':'') + day +"/"+ (mth.length==1?'0':'') + mth +"/${deliveryPlan.year}");
				param.push("dateList["+ (index+1) +"].detailPlanId="+ (dateList[index+1]["detailPlanId"] || ""));
			});
			table.find("tr[id=forCastQty] > td > input:text").each(funct);
			table.find("tr[id=custReqQty] > td > input:text").each(funct);
			table.find("tr[id=tkiCommitQty] > td > input:text").each(funct);
			table.find("tr[id=productionQty] > td:gt(1)").each(functText);
			table.find("tr[id=deliveryQtyNormal] > td:gt(1)").each(functText);
			table.find("tr[id=deliveryQtyBack] > td > input:text").each(funct);
			table.find("tr[id=deliveryQtyTotal] > td:gt(1)").each(functText);
			table.find("tr[id=balanceDeliveryQty] > td:gt(1)").each(funct);
			//set balance order column 0 to list for save(t_deliveryplan_date_0)
			table.find("tr[id=balanceOrderQty] > td > input[id=txtBalanceOrder]").each(function(){
				var value = ($(this).val().trim()== "" ? "" : $(this).val());
				param.push("dateList[0].balanceOrderQty=" + value);
			});
			table.find("tr[id=balanceOrderQty] > td:gt(1)").each(functText);
			table.find("tr[id=reason] > td > select").each(funct);

			//  Data Binding: t_deliveryplan_time 
			table.find("tr[id=timeList]").each(function(rowIndex){
				var row     = $(this);
				var timeHr  = $("tr[id=selectTimeList]:eq("+rowIndex+")").find("select#timeHr").val();
				var timeMin = $("tr[id=selectTimeList]:eq("+rowIndex+")").find("select#timeMin").val();
				row.find("td > input:text").each(function(colIndex){
					param.push("dateList["+ (colIndex+1) +"].timeList["+ rowIndex +"].timeHr="+ timeHr);
					param.push("dateList["+ (colIndex+1) +"].timeList["+ rowIndex +"].timeMin="+ timeMin);
					param.push("dateList["+ (colIndex+1) +"].timeList["+ rowIndex +"].deliveryQty="+ $(this).val());
				});
			});

			//  SUBMIT: 
			postJSON("DLV_S02_save", deliv.join("&") +"&"+ param.join("&"), function( result ){
				if( result.errors && result.errors.length > 0 ) {
					message.setErrors(result.errors);
					return;
				}

				message.setInfos ( result.infos  );

				deliv[0] = "customerId="+ result.customerId;
				deliv[1] = "deliveryPlanId="+ result.deliveryPlanId;
				deliv[2] = "month="+ result.month;
				deliv[3] = "year="+ result.year;
				deliv[4] = "fgId="+ result.fgId;

				// Disable Delete Button.
				var paramDelete = 
								[
									deliv[0],
									deliv[1],
					             	deliv[2],
					             	deliv[3],
									deliv[4]
								];
				postJSON("DLV_S02_checkButtonDelete", paramDelete.join("&"), function( resultD ){
					btnDelete.css("display", (resultD.insertFlag == 'Y' && resultD.countDate > 0)? "" : "none");
				});
				
				var paramsFg = 
							[
								deliv[0],
				             	deliv[2],
				             	deliv[3],
							];
				postJSON("DLV_S02_boxFgNameNo", paramFg.join("&"), function(result) {
					resultFg = result;
					fgList();
				});
			});
		});
				
		// Delete button 
		btnDelete.click(function() {
			boxFg.val("${deliveryPlan.fgId}");//
			var fgTxt = boxFg.find("option:selected").text();//
			var fgName = fgTxt.split(":");
		
			if( !confirm("<spring:message code='cfm.dlv.003'/>".replace(/\{0\}/g, fgName[0])) )
				return;
			
			var params = [
			              "deliveryPlanId=${deliveryPlan.deliveryPlanId}",
						  "fgId=${deliveryPlan.fgId}"
			              ];
			dlvS02Form.attr("action","DLV_S02_deleteByFg.html?"+params.join("&"));
			/* dlvS02Form.submit(); */
			document.getElementById("dlvS02Form").submit();
		});

		btnDelete.css("display", "${deliveryPlan.insertFlag == 'Y' && deliveryPlan.countDate > 0}" == "true" ? "" : "none");
	});
	
	function changeBalanceOrder(){
		var txtBalanceOrder = parseInt($('#txtBalanceOrder').val(),10) || 0;
		var color = "";
		var value = "";
		// copy "balance order" column 0 to "delivery plan back" column 0
		value = txtBalanceOrder;
		if(txtBalanceOrder < 0){
			var b = (txtBalanceOrder + "").split("-");
			value = b[1];
		}else if(txtBalanceOrder > 0){
			value = "<font color = 'red'>(" + txtBalanceOrder + ")</font>";
		}else{
			value = txtBalanceOrder;
		}
		trDBack.find("td:eq(1)").html("<strong>" + value + "<strong>");
			
		dateList[0]["balanceOrderQty"] = txtBalanceOrder;
		//for recalculate data
		calcData(trCustReq,-1,arrCustReq);
		calcSumRw(trDBack);
	}
		
	function calcBalanceDelivery(index,name){
		var balFirst = 0;
		var balN = 0;
		var color = "";
		trName = name.find(":text");
		trName.each(function(i){
			if(i == 0 ){
				balN = balFirst;
			}else{
				var col = arrBDelivery[i-1] + "";
				if(col.indexOf("(") >= 0){
					var x = parseInt(col.replace("(","").replace(")",""),10) || 0;
					balN = x*(-1);
				}else{
					balN = (col == "" ? 0 : col);
				}
			}
			var custReq = parseInt((arrCustReq[i] == "" ? 0 : arrCustReq[i]),10);
			var act = parseInt((arrActual[i] == "" ? 0 : arrActual[i]),10);
			
			balN = parseInt(balN,10) || 0;
			bal = balN - (isNaN(custReq) ? 0 : custReq) + (isNaN(act) ? 0 : act);
			bal = isNaN(bal) ? "" :bal;
			if(bal < 0){
				var b = (bal+"").split("-");
				bal = "("+b[1]+")";
				color = "red";
			}else{
				color = "";
			}
			arrBDelivery[i] = bal;
			arrBDeliveryObj[i].html(arrBDelivery[i]).css("color",color);
		});
	}
		
	// calc data 
	function calcData(name,index,nameArr){
		var trName;
		var proPlan = " ";
		var delTotal = " ";
		var balN = 0;
		var bal;
		var color = "";
		var tempDTotal = arrDTotal[index];
		trName = name.find(":text");
		
		trName.each(function(i){
			proPlan = " ";
			delTotal = " ";
			color = "";
				
			//  update val at is change 
			if(index == i){
				if($(this).val().length == 0){
					nameArr[i] = $(this).val();
				}else{
					nameArr[i] = parseInt($(this).val(),10) || 0;						
				}
			}
			
			if(arrCommit[i].length != 0 ){
				proPlan = arrCommit[i];
				arrDNormal[i] = proPlan;
				arrDNormalObj[i].html(arrDNormal[i]);
			}else if(arrCommit[i].length == 0 && arrCustReq[i].length != 0 ){
				proPlan = arrCustReq[i];
				arrDNormal[i] = proPlan;
				arrDNormalObj[i].html(arrDNormal[i]);
			}else{
				proPlan = arrForCast[i];
			}
			arrProPlan[i] = proPlan;
			
			// calculate Delivery Plan(Back, Total)
			if(index > -1 && name != trForCast){
				var tempBack = trDBack.find(":text:eq("+index+")").val();
				var delBack = parseInt(tempBack,10) || 0;
				var delNormal = parseInt(arrDNormal[index],10) || 0;
				
				//Set DeliveryPlan(Back) value
				arrDBack[index] = delBack;
				
				//Recalculate(total = normal + back) and set DeliveryPlan(Total) value 
				arrDTotal[index] = delNormal + delBack;
				arrDTotalObj[index].html(arrDTotal[index]);
			}
			
			//  calc Balance Order
			var balQty = parseInt(dateList[0]["balanceOrderQty"],10) || 0;// balance From DB of last month 
			if(i == 0 ){
				balN = balQty;
			}else{
				var col = arrBOrder[i-1] + "";
				if(col.indexOf("(") >= 0){
					var x = parseInt(col.replace("(","").replace(")",""),10) || 0;
					balN = x*(-1);
				}else{
					balN = (col == "" ? 0 : col);
				}
			}
			var del = parseInt((arrDTotal[i] == "" ? 0 : arrDTotal[i]),10);
			var act = parseInt((arrActual[i] == "" ? 0 : arrActual[i]),10);
			
			balN = parseInt(balN,10) || 0;
			bal = balN - (isNaN(del) ? 0 : del) + (isNaN(act) ? 0 : act);
			bal = isNaN(bal) ? "" :bal;
			if(bal < 0){
				var b = (bal + "").split("-");
				bal = "(" + b[1] + ")";
				color = "red";
			}
			
			arrBOrder[i] = bal;
			arrProPlanObj[i].html(arrProPlan[i]);
			arrDTotalObj[i].html(arrDTotal[i]);
			arrBOrderObj[i].html(arrBOrder[i]).css("color",color);
		});
		
		if(tempDTotal != arrDTotal[index]) calcQtyTime(arrDTotal[index],index);//default time first recode
		if(name == trCustReq) calcBalanceDelivery(index,name);
		calcSumRw(name);
	}
	
	// default value Time in case deliveryPlanTotal change
	function calcQtyTime(deliveryTotal,index){
		var tr = $("tr[id=timeList]");
		tr.each(function(i){
			if(i == 0){ $(this).find(":text:eq("+index+")").val(deliveryTotal); }
			else{ $(this).find(":text:eq("+index+")").val(""); }
		});	
		sumTotalTime(index,"");
	}
	
	// sum Total 
	function calcSumRw(name){
		var totalRwF = 0;
		var totalRwC = 0;
		var totalRwT = 0;
		var totalRwP = 0;
		var totalRwD = 0;
		var totalRwA = 0;
		var totalRwB = 0;  // BalanceOrder
		var totalRwDN = 0;
		var totalRwDB = 0;
		var totalRwBD = 0; // BalanceDelivery
		var bdLast = 0; // BalanceDelivery(n-1) = 0
		var trName;
		var color = "";
		var bOrder = dateList[0]["balanceOrderQty"] == null ? 0 : dateList[0]["balanceOrderQty"]; // balanceOrder(0)
		
		trName = name.find(":text");
		trName.each(function(i){
			totalRwF += parseInt(arrForCast[i],10) || 0;
			totalRwC += parseInt(arrCustReq [i],10) || 0;
			totalRwT += parseInt(arrCommit[i],10) || 0;
			totalRwP += parseInt(arrProPlan[i],10) || 0;
			totalRwDN += parseInt(arrDNormal [i],10) || 0;
			totalRwDB += parseInt(arrDBack [i],10) || 0;
			totalRwD += parseInt(arrDTotal [i],10) || 0;
			totalRwA += parseInt(arrActual[i],10) || 0;
		});
			
		totalRwBD = bdLast - totalRwC + totalRwA;
		totalRwB  = bOrder - totalRwD + totalRwA;
		
		$("tr[id=forCastQty] > td:eq(0)").html("<strong>"+totalRwF+"</strong>");
		$("tr[id=custReqQty] > td:eq(0)").html("<strong>"+totalRwC+"</strong>");
		$("tr[id=tkiCommitQty] > td:eq(0)").html("<strong>"+totalRwT+"</strong>");
		$("tr[id=productionQty] > td:eq(0)").html("<strong>"+totalRwP+"</strong>");
		$("tr[id=deliveryQtyNormal] > td:eq(0)").html("<strong>"+totalRwDN+"</strong>");
		$("tr[id=deliveryQtyTotal] > td:eq(0)").html("<strong>"+totalRwD+"</strong>");
		$("tr[id=fgOut] > td:eq(0)").html("<strong>"+totalRwA+"</strong>");

		// add delivery (back) at date 0 = backorder last day of previous month  P'Pok Confirm
		var back = 0;
		var tempBack = $("tr[id=deliveryQtyBack] > td:eq(1)").text();
		color = "";
		if(tempBack.indexOf("(") >= 0){
			back = tempBack.replace("(", "").replace(")", "");
			back = back * (-1);
		}else if("" != tempBack){
			back = parseInt(tempBack, 10);
		}
		totalRwDB -= back;
		if(totalRwDB > 0){
			totalRwDB = "(" + totalRwDB + ")";
			color = "red";
		}else if(totalRwDB < 0){
			var b = (totalRwDB + "").split("-");
			totalRwDB = b[1];
		}
		$("tr[id=deliveryQtyBack] > td:eq(0)").html("<strong>" + totalRwDB + "</strong>").css("color", color);
		
		// Balance Delivery
		color = "";
		if(totalRwBD < 0){
			var b = (totalRwBD+"").split("-");
			totalRwBD = "("+b[1]+")";
			color = "red";
		}
		$("tr[id=balanceDeliveryQty] > td:eq(0)").html("<strong>"+totalRwBD+"</strong>").css("color",color);
		
		//Balance Order
		color = "";
		if(totalRwB < 0){
			var b = (totalRwB+"").split("-");
			totalRwB = "("+b[1]+")";
			color = "red";
		}
		$("tr[id=balanceOrderQty] > td:eq(0)").html("<strong>"+totalRwB+"</strong>").css("color",color);
		
	}
	
	// calc Total Time of date = deliveryPlanTotal
	function calcTotalTime(index,input){
		var sum = 0;
		var total = 0;
		var arr = new Array();
		var tr = $("tr[id=timeList]:gt(0)");
		tr.each(function(i){
			var text = $(this).find(":text:eq("+index+")");
			arr[i] = text;
			text = parseInt(text.val(),10) || 0;
			sum += text;
		});
		
		var delTotal = (arrDTotal[index]+"").trim() == "" || isNaN(arrDTotal[index]) ? 0 : parseInt(arrDTotal[index],10);
		if( delTotal > sum) total = delTotal - sum ;
		else total = sum - delTotal;
		
		var chk = sum + total;
		var flag = ((arrDTotal[index]+"").trim() == "" || arrDTotal[index] == undefined);
		
		if(chk != delTotal){
			input.css("background-color","red");				
			//input.focus();
		}else{
			for(var i=0;i<arr.length;i++){
				arr[i].css("background-color","");
			}
			
			if(flag) total = "";
			$("tr[id=timeList]:eq(0)").find(":text:eq("+index+")").val(total);
		}
	}
	
	// calc Total Time of all date = deliveryPlanTotal in case delete row  
	function calcTotalTimeAll(){
		for(var i=0, max = dateList.length-1; i < max; i++ ){
			calcTotalTime(i);
		}
	}
	
	// show default value from DB 	
	function checkDefaultValue(name,index){
		//  index = index+1 because result return data day of last month 
		return dateList == undefined || dateList[index] == undefined || dateList[index][name] == null || dateList[index][name] == undefined ? "" : dateList[index][name];
	}
	
	// show default value from DB 	
	function checkActualDefaultValue(name,index){
		//  index = index+1 because result return data day of last month 
		return dateList == undefined || dateList[index] == undefined || dateList[index][name] == null || dateList[index][name] == undefined ? "0" : dateList[index][name];
	}
	
	// check DeliveryPlan(Back) (sum delivery normal and back of date = deliveryPlanTotal)
	function checkDeliveryPlanBack(errors){
		var sum = 0;
		var total = 0;
		var delBack = 0;
		var delTotal = 0;
		var inputs = trDBack.find(":text");
		
		inputs.each(function(index){
			delBack = parseInt($(this).val(),10) || 0;
			delTotal = parseInt(arrDTotal[index],10) || 0;
			
			if(delBack > delTotal){ total = delBack - delTotal; }
			else { total = delTotal - delBack; }
			
			sum = total + delBack;
			
			if(sum != delTotal){
				errors.push({"code": "err.dlv.004", "arguments": [index+1]});
			}
		});
		
		return errors;
	}
	
	// check time duplicate
	function checkTimeDuplicate(errors){
		var trName = $("tr[id=selectTimeList]");
		var timeHr = "";
		var timeMin = "";
		var timeSet = {};
		var timeVal = "";
		
		trName.each(function(index){
			var select = $(this).find("select");
			select.each(function(i){
				if(i==0){
					timeHr = $(this).val();
				}else if(i==1){
					timeMin = $(this).val();
					timeMin = (timeMin.toString().length < 2 ? "0" : "") + timeMin;
				}
			});
			timeVal = timeHr + ":" + timeMin;
			if(timeSet[timeVal] == undefined){// no duplicate
				timeSet[timeVal] = false;
			}else{// duplicate
				if(timeSet[timeVal] == false){
					errors.push({"code": "err.dlv.006", "arguments": [timeVal]});
					timeSet[timeVal] = true;
				}
			}
		});
		return errors;
	}
	
	// check Reason no have value if DeliveryPlan(Total) no value
	function checkReason(errors){
		var sum=Array(); 
		$("tr[id=reason]").find("select").each(function(index){
			var select = $(this).val();
			if((arrDTotal[index]+"").trim() == "" && (select+"").trim() != ""){
				errors.push({"code": "err.dlv.005", "arguments": [index+1]});
			}
		});
		return errors;
	}

	// check sum Time of date = deliveryPlanTotal  
	function checkSumTimeOfDate(errors){
		var sum=Array(); 
		$("tr[id=timeList]:eq(0)").find(":text").each(function(index){
			sum[index] = 0;
			var trName = $("tr[id=timeList]");
			trName.each(function(){
			var tr =$(this);
				tr.find("td:eq("+(2+index)+")").each(function(i){
				var td = $(this);
				var val = parseInt(td.find(":text").val(),10) || 0;
				sum[index] += val; 
				});
			});
			var col = (arrDTotal[index]+"").trim() == "" || isNaN(arrDTotal[index]) ? 0 : parseInt(arrDTotal[index],10);
			if(sum[index] != col){
				errors.push({"code": "err.dlv.002", "arguments": [index+1]});
			}
		});
		return errors;
	}
	
	// check sum total time < 0
	function checkTime(errors){
		var trName = $("tr[id=timeList]");
		var sum = 0;
		var flag = 0;
		trName.each(function(i){
			sum = 0;
			var td = $(this).find(":text");
			td.each(function(){
				var val = parseInt($(this).val(),10) || 0;
				sum += val;
			});
			if(sum < 1){
				var timeHr = "";
				var timeMin = "";
				var select = $(this).find("select");
				select.each(function(ci){
					if(ci==0){
						timeHr = $(this).val();
					}else if(ci==1){
						timeMin = $(this).val();
					}
				});
				timeMin = (timeMin.toString().length < 2 ? "0" : "") + timeMin;
				errors.push({"code": "err.dlv.001", "arguments": [timeHr + ":" +timeMin]});
			}
		});
		return errors;
	}
		
	function deleteRow( obj ) {
		var rowIndex = $(obj).closest("tr").index();
		$("table:eq(1)").find("tr:eq("+rowIndex+")").remove();
		$("table:eq(2)").find("tr:eq("+(rowIndex+2)+")").remove();
		calcTimeDelete();
		sumTotalTime("");
	}
	
	// parseInt trime zero( 010 = 10) 
	function ParseIntTrimZero(obj){
		var val = $.trim(obj.value);
		if(val)
			obj.value = parseInt(val || 0,10);
	}
	
	// init action of textbox 	
	function initAction(trObj,value,nameArr,index){
		var inp = $("<input size='1' maxlength='7' value='"+value+"'/>");
		inp.keypress(PositiveIntegerFilter);
		inp.change(function(){
			ParseIntTrimZero(this);
			calcData(trObj,index,nameArr);
		});
		return inp;
	}
	
	// init action textbox of time 	
	function initActionTime(index){
		var inp = $("<input size='1' maxlength='7'/>");
		inp.keypress(PositiveIntegerFilter);
		inp.change(function(){
			ParseIntTrimZero(this);
			sumTotalTime(index,$(this));
		});
		return inp;
	}
		
	// initial Array 
	function initValArr(){
		var trName;
		trName = trForCast.find(":text");
		trName.each(function(i){
			var val = $(this).val();
			arrForCast[i] = val;
		});
			
		trName = trCustReq.find(":text");
		trName.each(function(i){
			var val = $(this).val();
			arrCustReq [i] = val;
		});

		trName = trCommit.find(":text");
		trName.each(function(i){
			var val = $(this).val();
			arrCommit[i] = val;
		});
			
		trName = trDBack.find(":text");
		trName.each(function(i){
			var val = $(this).val();
			arrDBack[i] = val;
		});
			
		trName = trProPlan.find("td:gt(1)");
		trName.each(function(i){
			var val = $(this).text();
			arrProPlan[i] = val;
			arrProPlanObj[i] = $(this);
		});
			
		trName = trDNormal.find("td:gt(1)");
		trName.each(function(i){
			var val = $(this).text();
			arrDNormal[i] = val;
			arrDNormalObj[i] = $(this);
		});
			
			
		trName = trDTotal.find("td:gt(1)");
		trName.each(function(i){
			var val = $(this).text();
			arrDTotal[i] = val;
			arrDTotalObj[i] = $(this);
		});
		
		trName = trActDel.find("td:gt(1)");
		trName.each(function(i){
			var val = $(this).text();
			arrActual[i] = val;
		});
			
		trName = trBalDel.find("td:gt(1)");
		trName.each(function(i){
			var val = $(this).text();
			arrBDelivery[i] = val;
			arrBDeliveryObj[i] = $(this);
		});
			
		trName = trBalOrd.find("td:gt(1)");
		trName.each(function(i){
			var val = $(this).text();
			arrBOrder[i] = val;
			arrBOrderObj[i] = $(this);
		});
	}
	
	// sum Total Time 
	function sumTotalTime(index,input,flag){
		if(flag != "init"){
			if(index > -1) { calcTotalTime(index,input); }
			else{ calcTotalTimeAll(); }
		}
		
		var trName = $("tr[id=timeList]");
		var sum = 0;
		trName.each(function(){
			sum = 0;
			var td = $(this).find(":text");
			td.each(function(){
				var val = parseInt($(this).val(),10) || 0;
				sum += val;
			});
			$(this).find("td:eq(0)").html(sum);
		});
	}
	
	// calc Total Time of date = deliveryPlanTotal case delete
	function calcTimeDelete(){
		var sum=Array(); 
		$("tr[id=timeList]:eq(0)").find(":text").each(function(index){
			var inp = $(this);
			sum[index] = 0;
			var trName = $("tr[id=timeList]");
			trName.each(function(){ 
			var tr =$(this);
				tr.find("td:eq("+(2+index)+")").each(function(i){
				var td = $(this);
				var val = parseInt(td.find(":text").val(),10) || 0;
				sum[index] += val; 
				});
			});
			var col = (arrDTotal[index]+"").trim() == "" || isNaN(arrDTotal[index]) ? 0 : parseInt(arrDTotal[index],10);
			if(sum[index] != col && sum[index] < col){
				var temp = col - sum[index];
				var t = (inp.val()+"").trim() == "" || isNaN(inp.val()) ? 0 : parseInt(inp.val(),10);
				temp = temp + t;
				inp.val(temp);
			}
		});
	}
	
	function fgList(){
		var fgText = $("table:eq(1) > tbody > tr:eq(1) > td:eq(0)");// FG No(FG Name)
		var isChecked = $("input#isPlanExists").attr("checked");
			boxFg.empty().append("<option value=''>--Please Select--</option>");
			$.each(resultFg, function( i, obj ){
				if( !(isChecked && obj.isPlan !== "true"))
				{
					var label = obj.partName + " : " + obj.partNo;
					boxFg.append($("<option></option>").val(obj.partId).html(label));
				}
			});

			boxFg.val("${deliveryPlan.fgId}");// val from click search
			fgText.html("<strong>"+boxFg.find("option:selected").text()+"</strong>");// show at FG No(FG Name)
	}
			
</script>

</head>
<body>
<form:form method="post" id="dlvS02Form" commandName="deliveryPlan" action="DLV_S02_search.html">
	<input type="hidden" name="customerId" value="${deliveryPlan.customerId}"/>
	<input type="hidden" name="year" value="${deliveryPlan.year}"/>
	<input type="hidden" name="month" value="${deliveryPlan.month}"/>
	<h1><spring:message code='menu.DeliveryPlanEntry'/></h1>

	<ul id="navlist">
	  <li>
		<a href="DLV_S01.html" id="current">Delivery Plan Search/List</a>
		<a href="DLV_S03.html"> Daily Customer Delivery</a>
	 </li>     
	</ul>  
	<page:message item="${deliveryPlan}" />

	<table class="ui-widget ui-widget-content" cellpadding="3" cellspacing="1" width="100%" border="0">
		<tr>
			<th width="12%">Customer</th>
			<td width="30%">${deliveryPlan.customerName}</td>
			<th width="16%">Delivery Month</th>
			<td width="42%">${monthMap[deliveryPlan.month]} ${deliveryPlan.year}</td>
		</tr>
		<tr>
			<th>Revise</th>
			<td>
				<c:if test="${not empty deliveryPlan.revision}">${deliveryPlan.revision}</c:if> 
	          	&nbsp;
			</td>
			<th>FG Name : FG No<span class="textred">*</span></th>
			<td>
				<select name="fgId" id="boxFg" title="FG Name:FG No">
					<option value="">--Please Select--</option>
				</select>
				<form:checkbox path="isPlanExists" id="isPlanExists"/>&nbsp;&nbsp;&nbsp;Have Plan
			</td>
		</tr>
	</table>
	<br/>
	<div align="right">
	<security:authorize ifAnyGranted="DLV_R01_EXPORT">
		<input type="button" value="Summary Report" id="btnExport" style="width:150px"/>
	</security:authorize>
		<input name="btnSearch" id="btnSearch" type="button" value="Search" style="width: 100px" />
	</div>
	<br/>
	<!-- Start table manage delivery plan -->
	<c:if test="${not empty deliveryPlan.dateList}">
		<div id="divDetail" style="display:block">
		<strong>Manage Delivery Plan</strong>
		<br/>
		<!-- 2 first column -->
		<table id="tbl1Detail" width="250" border="1" align="center" cellpadding="0" cellspacing="0" class="ui-widget ui-widget-content" style="position:absolute">
			<tr height="60">
				<th width="150" align="center">FG Name : FG No</th>
				<th width="100" align="center">&nbsp;</th>
			</tr>
			<tr height="30">
				<td rowspan="11" align="center"><strong>FG Name : FG No</strong></td>
				<td align="center"><strong> Forecast</strong></td>
			</tr>
			<tr height="30" bgcolor="white">
				<td align="center"><strong>Cust. Req.</strong></td>
			</tr>
			<tr height="30">
				<td align="center"><strong>Commit</strong></td>
			</tr>
			<tr height="30" bgcolor="white">
				<td align="center"><strong>Production Plan</strong></td>
			</tr>
			<tr height="30">
				<td align="center" nowrap="nowrap"><strong>DeliveryPlan(Normal)</strong></td>
			</tr>
			<tr height="30" bgcolor="white">
				<td align="center" nowrap="nowrap"><strong>DeliveryPlan(Back)</strong></td>
			</tr>
			<tr height="30">
				<td align="center" nowrap="nowrap"><strong>DeliveryPlan(Total)</strong></td>
			</tr>
			<tr height="30" bgcolor="white">
				<td align="center"><strong>Actual Delivery</strong></td>
			</tr>
			<tr height="30">
				<td align="center"><strong>BalanceDelivery</strong></td>
			</tr>
			<tr height="30" bgcolor="white">
				<td align="center"><strong>Balance Order</strong></td>
			</tr>
			<tr height="34">
				<td align="center"><strong>Reason</strong></td>
			</tr>
			<tr height="30"><td colspan="2">&nbsp;</td></tr>
			<tr height="30"><td colspan="2">Manage DeliveryTime for customer</td></tr>
			<tr height="60">
				<th align="center">&nbsp;</th>
				<th align="center">Time<br/>HH : MM</th>			
			</tr>
			<tr id="selectTimeList">
				<td align="center">
				<img src="image/icon/delete.gif" width="16" height="16" onclick="deleteRow(this)" 
				<c:if test="${deliveryPlan.insertFlag == 'N' || deliveryPlan.insertFlag == 'null' }">style="display:none;"</c:if> />
				</td>
				<td align="center">
					<select id="timeHr">
						<option value="0">0</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
					</select>
					<select id="timeMin">
						<option value="0">00</option>
						<option value="1">01</option>
						<option value="2">02</option>
						<option value="3">03</option>
						<option value="4">04</option>
						<option value="5">05</option>
						<option value="6">06</option>
						<option value="7">07</option>
						<option value="8">08</option>
						<option value="9">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
						<option value="24">24</option>
						<option value="25">25</option>
						<option value="26">26</option>
						<option value="27">27</option>
						<option value="28">28</option>
						<option value="29">29</option>
						<option value="30">30</option>
						<option value="31">31</option>
						<option value="32">32</option>
						<option value="33">33</option>
						<option value="34">34</option>
						<option value="35">35</option>
						<option value="36">36</option>
						<option value="37">37</option>
						<option value="38">38</option>
						<option value="39">39</option>
						<option value="40">40</option>
						<option value="41">41</option>
						<option value="42">42</option>
						<option value="43">43</option>
						<option value="44">44</option>
						<option value="45">45</option>
						<option value="46">46</option>
						<option value="47">47</option>
						<option value="48">48</option>
						<option value="49">49</option>
						<option value="50">50</option>
						<option value="51">51</option>
						<option value="52">52</option>
						<option value="53">53</option>
						<option value="54">54</option>
						<option value="55">55</option>
						<option value="56">56</option>
						<option value="57">57</option>
						<option value="58">58</option>
						<option value="59">59</option>
					</select>
				</td>
			</tr>
			<tr id="btnRow">
				<td colspan="2">
					<input name="btnAddNewTime" type="button" id="btnAddNewTime" value="Add New Time" 
					<c:if test="${deliveryPlan.insertFlag == 'N' || deliveryPlan.insertFlag == 'null' }">style="display:none;"</c:if> />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input name="btnSave" type="button" id="btnSave" value="Save"
					<c:if test="${deliveryPlan.insertFlag == 'N' || deliveryPlan.insertFlag == 'null' }">style="display:none;"</c:if> />
					<security:authorize ifAnyGranted="DLV_S02_DELETE">
					&nbsp;
					<input name="btnDelete" type="button" id="btnDelete" value="Delete" />
					</security:authorize>
				</td>
			</tr>
		</table>
			<div style="margin-left:250px;display:block;overflow-x:scroll;overflow-y:hidden">
				<table id="tbl2Detail" width="100%" border="1" align="center" cellpadding="0" cellspacing="0" class="ui-widget ui-widget-content">
					<tr height="30">
						<th align="center">&nbsp;</th>
					</tr>
					<tr height="30">
						<th align="center">Total</th>
						<th align="center">0</th>
					</tr>
					<tr height="30" id="forCastQty">
						<td align="center" id="totalForCast"></td>
						<td align="center">&nbsp;</td>
					</tr>
					<tr height="30" id="custReqQty" bgcolor="white">
						<td align="center" id="totalCustReq"></td>
						<td align="center">&nbsp;</td>
					</tr>
					<tr height="30" id="tkiCommitQty">
						<td align="center" id="totalCommit"></td>
						<td align="center">&nbsp;</td>
					</tr>
					<tr height="30" id="productionQty" bgcolor="white">
						<td align="center" id="totalProduct"></td>
						<td align="center">&nbsp;</td>
					</tr>
					<tr height="30" id="deliveryQtyNormal">
						<td align="center" id="totalDeliveryNormal"></td>
						<td align="center">&nbsp;</td>
					</tr>
					<tr height="30" id="deliveryQtyBack" bgcolor="white">
						<td align="center" id="totalDeliveryBack"></td>
						<td align="center">&nbsp;</td>
					</tr>
					<tr height="30" id="deliveryQtyTotal">
						<td align="center" id="totalDelivery"></td>
						<td align="center">&nbsp;</td>
					</tr>
					<tr height="30" id="fgOut" bgcolor="white">
						<td align="center" id="totalActual"></td>
						<td align="center">&nbsp;</td>
					</tr>
					<tr height="30" id="balanceDeliveryQty">
						<td align="center" id="totalBalanceDelivery"></td>
						<td align="center">&nbsp;</td>
					</tr>
					<tr height="30" id="balanceOrderQty" bgcolor="white">
						<td align="center" id="totalBalance"></td>
						<td align="center" id="balanceQty">
							<input type="text" id="txtBalanceOrder" name="txtBalanceOrder" size="5" 
								onchange="changeBalanceOrder();" onkeypress="return IntegerFilter(event)" 
								style="text-align: center;">
						</td>
					</tr>
					<tr height="34" id="reason">
						<td align="center"></td>
						<td align="center">&nbsp;</td>
					</tr>
					<tr height="30"><td>&nbsp;</td></tr>
					<tr height="30"><td>&nbsp;</td></tr>
					<tr height="30">
						<th align="center">&nbsp;</th>
					</tr>
					<tr height="30">
						<th align="center">Total</th>
						<th align="center">0</th>
					</tr>
				</table>
			</div>
		</div>
	</c:if>
</form:form>
</body>
</html>