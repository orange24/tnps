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
<script language="javascript">
var btnSearch;	
var btnSync;
var lotS02Form;
var workOrder;
var dateFrom;
var dateTo;

function jqCheckAll(id, name)
{
	$("."+name).attr('checked', $('#' + id).is(':checked'));
}

$(document).ready(function() {
	btnSearch = $("#btnSearch");
	btnSync = $("#btnSync");
	lotS02Form = $("#lotS02Form");
	workOrder = $(".workOrder");
	dateFrom = $("#dateFrom");
	dateTo = $("#dateTo");
	
	btnSearch.click(function(){
		
		if( dateFrom.datepicker("getDate") > dateTo.datepicker("getDate") ) {
			message.setErrors([{"code":"err.cmm.008","arguments":["WorkOrderDate (To)","WorkOrderDate (From)"]}]);
			return;
		}
		
		message.clear();
		lotS02Form.attr("action","LOT_S02_search.html");
		lotS02Form.submit();
	});
	
	btnSync.click(function(){
		var i=0;
		workOrder.each(function(){
			this.workOrder = $(this);
			if (this.workOrder.attr("checked"))i++;
		});
		if (i==0) {
			message.setErrors([{"code":"err.cmm.003","arguments":["",""]}]);
			return;
		}
		
		message.clear();
		if (confirm("<spring:message code='cfm.cmm.004'/>")) {
			lotS02Form.attr("action","LOT_S02_sync.html");
			lotS02Form.submit();
		}
	});
	
});	
</script>
</head>
<body>
<form:form id="lotS02Form" action="LOT_S02_search.html" method="post" commandName="mWorkOrderCriteria">
<h1><spring:message code='menu.LotMaster'/></h1>
<div id="navcontainer">
  <ul id="navlist">
    <li><a href="LOT_S01.html" >Lot Search/List</a></li>
    <li><a href="LOT_S02.html" id="current">Lot Sync From TPics</a></li>
  </ul>
</div>
<page:message item="${mWorkOrderCriteria}" />
<table width="100%" border="0" cellpadding="3" cellspacing="1">
  <tr>
    <td colspan="2"><table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
        <tr>
          <th width="15%" class="label">Work Order Date From - To<br />(TPics)</th>
          <td width="41%" >
          	<form:input title="Date From" path="workOrderDateFr" id="dateFrom" cssClass="date" tabindex="3" /> - 
	        <form:input title="Date To" path="workOrderDateTo" id="dateTo" cssClass="date" tabindex="3" />
          </td>
          <th width="11%" >Work Order No. (TPics)</th>
          <td width="33%" ><form:input path="workOrderNo" /> </td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td width="30%" align="right" colspan="2" ><input type="button" id="btnSearch" value="Search"  /></td>
  </tr>
</table>
<br />
<c:if test="${fn:length(mWorkOrderCriteria.mWorkOrderLst)>0}">
<table width="100%" border="1" align="center" cellpadding="3" cellspacing="0" id="dynamictbl" class="ui-widget ui-widget-content">
	<tr>
	  <td colspan="9">
  		<div style="float:left"><page:display item="${mWorkOrderCriteria}" /></div>
  		<div style="float:right"><page:number item="${mWorkOrderCriteria}" /></div>
  	  </td>
	</tr>
    <tr >
	  <th align="center">No.</th>
	  <th align="center"><input type="checkbox" name="checkbox" id="checkbox" onclick="jqCheckAll(this.id, 'workOrder')" /></th>
	  <th align="left">Work Order Date</th>
      <th align="left">Work Order No</th>
      <th align="left">Lot No. (Start - End)</th>
      <th align="left">Customer</th>
      <th align="left">Part No.</th>
      <th align="left">Part Name</th>
      <th align="right">Qty</th>
    </tr>
    <c:forEach items="${mWorkOrderCriteria.mWorkOrderLst}" var="mWorkOrder" varStatus="status">
    <tr >
        <td align="center">${status.count}</td>
        <td align="center">
        	<input type="checkbox" value="${mWorkOrder.partId}"
        	name="mWorkOrderLst[${status.index}].partId" id="mWorkOrderLst[${status.index}].partId" class="workOrder" />
        	<input type="hidden" value="${mWorkOrder.workOrderNo}" name="mWorkOrderLst[${status.index}].workOrderNo" />
        	<input type="hidden" value="${mWorkOrder.startLot}" name="mWorkOrderLst[${status.index}].startLot" />
        	<input type="hidden" value="${mWorkOrder.endLot}" name="mWorkOrderLst[${status.index}].endLot" />
        	<input type="hidden" value="${mWorkOrder.lotQty}" name="mWorkOrderLst[${status.index}].lotQty" />
        	<input type="hidden" value="${mWorkOrder.workOrderQty}" name="mWorkOrderLst[${status.index}].workOrderQty" />
        	<input type="hidden" value="<fmt:formatDate value="${mWorkOrder.workOrderDate}" pattern="dd/MM/yyyy HH:mm:ss" />.000"
        	 name="mWorkOrderLst[${status.index}].workOrderDate" />
        </td>
        <td align="left"><fmt:formatDate value="${mWorkOrder.workOrderDate}" pattern="dd/MM/yyyy"/>&nbsp;</td>
        <td align="left">${mWorkOrder.workOrderNo}</td>
        <td align="left">${mWorkOrder.startLot} - ${mWorkOrder.endLot}</td>
        <td align="left">${mWorkOrder.customerCode}</td>
        <td align="left">${mWorkOrder.partNo}&nbsp;</td>
	    <td align="left">${mWorkOrder.partName}&nbsp;</td>
	    <td align="right">${mWorkOrder.workOrderQty}&nbsp;</td>
    </tr>
    </c:forEach>
    <tr>
	  <td colspan="9">
  		<div style="float:left" ><page:display item="${mWorkOrderCriteria}" /></div>
  		<div style="float:right" ><page:number item="${mWorkOrderCriteria}" /></div>
  	  </td>
	</tr>
</table>
<div style="float:right"><input type="button" id="btnSync" value="Sync Lot"/></div>
</c:if>
</form:form>
</body>
</html>
