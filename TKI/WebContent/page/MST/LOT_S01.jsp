<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script>
var btnSearch;
var lotSo1Form;
var custSelId;
var dateFrom;
var dateTo;
var partNoId;
var partNameId;
var workOrderNoId;
//JQuery

function fnDelete( row ) {
	message.clear();
	var rowNo = row.find("td:first-child").html().trim();
	
	if( !confirm("<spring:message code='cfm.cmm.003'/>".replace(/\{0\}/g, rowNo)) )
		return;

	lotSo1Form.attr("action", "LOT_S01_delete.html");
	lotSo1Form.append("<input type='hidden' name='mWorkOrderLst[0].workOrderId' value='"+ row.attr("id") +"'/>");
	lotSo1Form.find("select[id=pageNumber]").attr("disabled", true);
	lotSo1Form.submit();
}

//initialze script run
$(document).ready(function() {
	btnSearch = $("#btnSearch");
	lotSo1Form = $("#lotSo1Form");
	custSelId = $("#custSelId");
	dateFrom = $("#dateFrom");
	dateTo = $("#dateTo");
	partNoId = $("#partNoId");
	partNameId = $("#partNameId");
	workOrderNoId = $("#workOrderNoId");
	
	var msg = [];
	
	btnSearch.click(function(){
		msg = [];
		/*if(custSelId.val() == -2147483648){
			msg.push({"code":"err.cmm.001","arguments":["Customer",""]});
		}*/
		if(custSelId.val() == -2147483648
				&& partNoId.val() == ""
				&& partNameId.val() == ""
				&& workOrderNoId.val() == ""
				&& dateFrom.val() == "" && dateTo.val() == "" ){
			msg.push({"code":"err.cmm.026"});
		}
		if( dateFrom.datepicker("getDate") > dateTo.datepicker("getDate") ) {
			msg.push({"code":"err.cmm.008","arguments":["WorkOrderDate (To)","WorkOrderDate (From)"]});
		}
		if (msg.length > 0) {
			message.setErrors(msg);
			return;
		}
		
		message.clear();
		lotSo1Form.attr("action","LOT_S01_search.html");
		lotSo1Form.find("select[id=pageNumber]").attr("disabled", true);
		lotSo1Form.submit();
	});
});	
</script>
</head>
<body>
<form:form id="lotSo1Form" action="LOT_S01_search.html" method="post" commandName="searchCriteria">
<h1><spring:message code='menu.LotMaster'/></h1>
<div id="navcontainer">
	<ul id="navlist">
		<li><a href="LOT_S01.html" id="current">Lot Search/List</a>		
		</li>
	</ul>
</div>    
<page:message item="${searchCriteria}" />
<table width="100%" border="0" cellpadding="3" cellspacing="1">
  <tr>
    <td colspan="2">
	    <table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
	      <tr>
	        <th >Customer</th>
	        <td ><form:select path="customerId" items="${custMap}" id="custSelId" ></form:select></td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <th >Part No.</th>
	        <td ><form:input path="partNo" id="partNoId" /></td>
	        <th >Part Name</th>
	        <td ><form:input path="partName" id="partNameId" /></td>
	      </tr>
	      <tr>
	        <th >Work Order No</th>
	        <td ><form:input path="workOrderNo" id="workOrderNoId" /></td>
	        <th >Work Order Date</th>
	        <td >
	        	<form:input title="Date From" path="workOrderDateFr" id="dateFrom" cssClass="date" tabindex="3" /> - 
	            <form:input title="Date To" path="workOrderDateTo" id="dateTo" cssClass="date" tabindex="3" />
	        </td>
	      </tr>
	    </table>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="right">
      <input name="btnSearch" type="button" class="submit_button" id="btnSearch" value="Search"  />
    </td>
  </tr>
</table>
<br />
<c:if test="${fn:length(searchCriteria.mWorkOrderLst)>0}">
<table width="100%" border="1" align="center" cellpadding="3" cellspacing="0" id="dynamictbl" class="ui-widget ui-widget-content">
   <tr>
  	<td colspan="9">
  		<div style="float:left" ><page:display item="${searchCriteria}" /></div>
  		<div style="float:right" ><page:number item="${searchCriteria}" /></div>
  	</td>
   </tr>
   <tr >
   	  <th align="center">No.</th>
      <th align="center">Work Order Date</th>
      <th align="center">Work Order No</th>
      <th align="center">Lot No. (Start - End)</th>
      <th align="center">Customer</th>
      <th align="center">Part No</th>
      <th align="center">Part Name</th>
      <th align="center">Qty</th>
      <th align="center">Action</th>
   </tr>
   <c:forEach items="${searchCriteria.mWorkOrderLst}" var="mWork" varStatus="status" >
	   <tr id="${mWork.workOrderId}" >
	      <td align="center"><page:rowno item="${searchCriteria}" index="${status.index}"/></td>
	      <td ><fmt:formatDate value="${mWork.workOrderDate}" pattern="dd/MM/yyyy"/> &nbsp;</td>
	      <td >${mWork.workOrderNo}</td>
	      <td >${mWork.startLot} - ${mWork.endLot}</td>
	      <td >${mWork.customerCode}</td>
	      <td >${mWork.partNo}</td>
	      <td >${mWork.partName}</td>
	      <td >${mWork.workOrderQty}&nbsp;</td>
	      <td align="center" >
	      	<a href="javascript:void(0);" onclick="fnDelete( $(this).closest('tr') );" ><img src="image/icon/delete.gif" border="0"/></a>
	      </td>
	   </tr>
   </c:forEach>
   <tr>
  	<td colspan="9">
  		<div style="float:left" ><page:display item="${searchCriteria}" /></div>
  		<div style="float:right" ><page:number item="${searchCriteria}" /></div>
  	</td>
   </tr>
  </table>
</c:if>
</form:form>
</body>
</html>
