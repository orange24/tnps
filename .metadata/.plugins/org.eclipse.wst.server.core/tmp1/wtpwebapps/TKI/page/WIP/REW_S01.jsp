<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">

var btnSearch;
var rewS01Form;
var hidStatus;
var statusSel;
var btnSave;
var chkBoxClosed;
var boxClosed;
var adjOK;
var adjNG;
var tbResult;
var dateFrom;
var dateTo;

function calAdjRework(){
	tbResult.each(function(index){
		var qty = parseInt($(this).find("td:eq(6)").html() || 0);
		var ok  = $(this).find("input:eq(0)");
		var ng  = $(this).find("input:eq(1)");
		var evt = function(){
			if( parseInt(ok.val() || 0) + parseInt(ng.val() || 0) > qty ) {
				ok.removeClass("highlightGreen highlightYellow").addClass("highlightRed");
				ng.removeClass("highlightGreen highlightYellow").addClass("highlightRed");
			}else if( parseInt(ok.val() || 0) + parseInt(ng.val() || 0) == qty ) {
				ok.removeClass("highlightRed highlightYellow").addClass("highlightGreen");
				ng.removeClass("highlightRed highlightYellow").addClass("highlightGreen");
			}else if( parseInt(ok.val() || 0) + parseInt(ng.val() || 0) < qty ) {
				ok.removeClass("highlightRed highlightGreen").addClass("highlightYellow");
				ng.removeClass("highlightRed highlightGreen").addClass("highlightYellow");
			}
			if(parseInt(ok.val() || 0)==0 && parseInt(ng.val() || 0)==0){
				ok.removeClass("highlightRed highlightGreen highlightYellow");
				ng.removeClass("highlightRed highlightGreen highlightYellow");
			}
		};

		ok.keyup(evt);
		ng.keyup(evt);
	});
}

function checkDigit(nameClass){
	// <!-- if the letter is not digit then display error and don't type anything -->
	nameClass.keypress(function(event){
		//return inputText.filterInput(1, event, false);
		return IntegerFilter(event);
	});
}

//JQuery
//initialze script run
$(document).ready(function() {
	
	btnSearch = $("#btnSearch");
	rewS01Form = $("#rewS01Form");
	hidStatus = $("#hidStatus");
	statusSel = $("#statusSel");
	btnSave = $("#btnSave");
	chkBoxClosed = $("#chkBoxClosed");
	boxClosed = $(".boxClosed");
	tbResult = $("table#detail > tbody > tr:gt(2):not(:last)");
	adjNG = $(".adjNG");
	adjOK = $(".adjOK");
	dateFrom = $("#dateFrom");
	dateTo = $("#dateTo");
	
	$("#selWipFr").focus();
	
	calAdjRework();
	checkDigit(adjOK);
	checkDigit(adjNG);
	
	btnSearch.click(function(){
		
		if( dateFrom.datepicker("getDate") > dateTo.datepicker("getDate") ) {
			message.setErrors([{"code":"err.cmm.008","arguments":["Rework Date (From)","Rework Date (To)"]}]);
			return;
		}
		
		rewS01Form.attr("action","REW_S01_search.html");
		rewS01Form.submit();
	});
	
	btnSave.click(function(){
		var count = 0;
		var listLine = [];
		var listLine2 = [];
		var msg = [];
		//var n = $("input:checked").length;
		
		tbResult.each(function(){
			var qty = parseInt($(this).find("td:eq(6)").html() || 0);
			var line = parseInt($(this).find("td:eq(0)").html() || 0);
			var ok  = $(this).find("input:eq(0)");
			var ng  = $(this).find("input:eq(1)");
			if (ok.val() != "" || ng.val() != "" ) {
								
				if (parseInt(ok.val() || 0) + parseInt(ng.val() || 0) > qty) {
					listLine.push([line]);
					//msg.push(({"code":"err.rew.001","arguments":[listLine,""]}));
				}
				
				if(parseInt(ok.val() || 0) + parseInt(ng.val() || 0) == 0){
					listLine2.push([line]);
					//msg.push(({"code":"err.rew.002","arguments":[listLine2,""]}));
				}
				
				count++;
			}

		});
		
		if (count<=0) {
			message.setErrors([{"code":"err.cmm.003","arguments":["",""]}]);
			return;
		}
		if (listLine.length >0) {
			msg.push(({"code":"err.rew.001","arguments":[listLine,""]}));
		}
		if (listLine2.length >0) {
			msg.push(({"code":"err.rew.002","arguments":[listLine2,""]}));
		}
		if (msg.length >0) {
			message.setErrors(msg);
			return;
		}
		
		message.clear();
		if (confirm("<spring:message code='cfm.cmm.001'/>")) {
			rewS01Form.attr("action","REW_S01_save.html");
			rewS01Form.submit();
		}
	});
	
	if (hidStatus.val() != "") statusSel.val(hidStatus.val());
	
 	$("#wipcode").focus();	
 	
});	
</script>
</head>
<body>

<form:form method="post" action="REW_S01_search.html" commandName="searchCriteria" id="rewS01Form">
<h1><spring:message code='menu.WIPReworkAdjustment'/></h1>
<ul id="navlist">
  <li><a href="REW_S01.html" id="current">WIP Rework Adjustment</a></li>
  <li><a href="REW_S02.html" >WIP Rework History</a></li>
</ul>

<page:message item="${searchCriteria}" />

<table width="100%" border="0" cellpadding="3" cellspacing="1">
  <tr>
    <td >
    	<input type="hidden" value="${searchCriteria.status}" id="hidStatus" />
	    <table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
	        <tr>
	          <th width="12%"> Rework WIP<br />From --&gt; To</th>
	          <td width="40%" >
	            <form:select path="wipFr" items="${wipMap}" id="selWipFr" ></form:select>
	            ---&gt; 
	            <form:select path="wipTo" items="${wipMap}"></form:select>
	          </td>
	          <th width="14%" >Rework Date<br />From - To</th>
	          <td width="34%" >
	            <form:input title="Date From" path="reportDateFr" id="dateFrom" cssClass="date" tabindex="3" /> - 
	            <form:input title="Date From" path="reportDateTo" id="dateTo" cssClass="date" tabindex="3" />
	          </td>
	        </tr>
	        <tr>
	          <th >Customer</th>
	          <td ><form:select path="customerId" items="${customerMap}"></form:select></td>
	          <th >Status</th>
	          <td >
	          	<select name="status" id="statusSel">
		            <option value="0">All</option>
		            <option value="1">Rework Pending</option>
		            <option value="2">Rework Finish</option>
	          	</select>
	          </td>
	        </tr>
	        <tr>
	          <th >Part No</th>
	          <td ><form:input path="partNo" ></form:input></td>
	          <th >Part Name</th>
	          <td ><form:input path="partName" ></form:input></td>
	        </tr>
	    </table>
    </td>
  </tr>
  <tr>
    <td width="23%" align="right">
      <input class="submit_button" name="button" type="button" value="Search" style="width:100px" id="btnSearch"/>
    </td>
  </tr>
</table>
<br />
<c:if test="${fn:length(searchCriteria.adjustList) > 0 }">
<table id="detail" width="100%" border="1" align="center" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content " >
  <tr>
  	<td colspan="9">
	  <div style="float:left" ><page:display item="${searchCriteria}"/></div>
	  <div style="float:right"><page:number item="${searchCriteria}"/></div>
	</td>
  </tr>
  <tr >
    <th rowspan="2" align="center" >No.</th>
    <th rowspan="2" align="center" >Rework WIP</th>
    <th rowspan="2" align="center" >Rework Date</th>
    <th rowspan="2" align="center" >Customer</th>
 	<th rowspan="2" align="center" >Part No.</th>
    <th rowspan="2" align="center" >Part Name</th>
    <th rowspan="2" align="center" >Rework Qty</th>
    <th colspan="2" align="center" >Adjust Rework</th>
  </tr>
  <tr>
    <th align="center" >OK</th>
    <th align="center" >NG</th>
    <!-- <th align="center" >Closed</th> -->
  </tr>
  <c:forEach items="${searchCriteria.adjustList}" var="tReworkAdjust" varStatus="status" step="1">
  	<tr>
	  	<td align="center" >${status.count}&nbsp;</td>
	    <td align="center" >
	    	${tReworkAdjust.wipFr} <c:if test="${not empty tReworkAdjust.wipTo }">---&gt; ${tReworkAdjust.wipTo}</c:if>&nbsp;
	    </td>
	    <td align="center" ><fmt:formatDate value="${tReworkAdjust.reportDate}" pattern="dd/MM/yyyy"/>&nbsp;</td>
	    <td align="center" >${tReworkAdjust.customerCode}&nbsp;</td>
	    <td align="center" >${tReworkAdjust.partNo}&nbsp;</td>
	    <td align="left" >${tReworkAdjust.partName}&nbsp;</td>
	    <td align="center" >${tReworkAdjust.reworkQty}&nbsp;</td>
	    <td align="center" >
	    	<input name="adjustList[${status.count-1}].ok" type="text" 
	    	id="adjustList[${status.count-1}].ok" tabindex="15" size="2" class="adjOK" maxlength="10" />
	    </td>
	    <td align="center" >
	    	<input name="adjustList[${status.count-1}].ng" type="text" 
	    	id="adjustList[${status.count-1}].ng" tabindex="15" size="2" class="adjNG" maxlength="10" />
	    	<input type="hidden" name="adjustList[${status.count-1}].pdReworkId" value="${tReworkAdjust.pdReworkId}" />
	    </td>
	    <%--<td align="center" >
	    	 <input type="checkbox" id="chkBoxClosed[${status.count-1}]" class="boxClosed" name="adjustList[${status.count-1}].status" value="1" />  
	    </td>--%>
  	</tr>
  </c:forEach>
  <tr>
  	<td colspan="9">
	  <div style="float:left" ><page:display item="${searchCriteria}"/></div>
	  <div style="float:right"><page:number item="${searchCriteria}"/></div>
	</td>
  </tr>
</table>
<div style="float:right">
  <input name="btnSave" id="btnSave" type="button" value="Save"/>
</div>
</c:if>
</form:form>
</body>
</html>
