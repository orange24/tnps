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
var rewS02Form;

//JQuery
//initialze script run
$(document).ready(function() {
	
	btnSearch = $("#btnSearch");
	rewS01Form = $("#rewS01Form");
	hidStatus = $("#hidStatus");
	statusSel = $("#statusSel");
	rewS02Form = $("#rewS02Form");
	
	btnSearch.click(function(){
		rewS02Form.attr("action","REW_S02_search.html");
		rewS02Form.submit();
	});
	
	if (hidStatus.val() != "") statusSel.val(hidStatus.val());
	
	$("#selWipFr").focus();
 	
});	
</script>
</head>
<body>

<form:form method="post" action="REW_S02_search.html" commandName="searchCriteria" id="rewS02Form">
<h1><spring:message code='menu.WIPReworkAdjustment'/></h1>
<ul id="navlist">
  <li><a href="REW_S01.html" >WIP Rework Adjustment</a></li>
  <li><a href="REW_S02.html" id="current">WIP Rework History</a></li>
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
  	<td colspan="12">
	  <div style="float:left" ><page:display item="${searchCriteria}"/></div>
	  <div style="float:right"><page:number item="${searchCriteria}"/></div>
	</td>
  </tr>
  <tr >
   <th rowspan="2" >No.</th>
   <th rowspan="2" >Rework  WIP</th>
   <th rowspan="2" >Rework Date</th>
   <th rowspan="2" >Customer</th>
   <th rowspan="2" >Part No.</th>
   <th rowspan="2" >Part Name</th>
   <th rowspan="2" >Rework Qty</th>
   <th colspan="2" >Adjust Rework</th>
   <th rowspan="2" >Adjust By</th>
   <th rowspan="2" >Adjust Date</th>
  </tr>
  <tr >
    <th >OK</th>
  	<th >NG</th>
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
        <td align="center" >${tReworkAdjust.ok}&nbsp;</td>
        <td align="center" >${tReworkAdjust.ng}&nbsp;</td>
        <td align="center" >${tReworkAdjust.updateBy}&nbsp;</td>
        <td align="center" >
        	<fmt:formatDate value="${tReworkAdjust.reportDate}" pattern="dd/MM/yyyy"/>&nbsp;
        </td>
    </tr>
  </c:forEach>
  <tr>
  	<td colspan="12">
	  <div style="float:left" ><page:display item="${searchCriteria}"/></div>
	  <div style="float:right"><page:number item="${searchCriteria}"/></div>
	</td>
  </tr>
</table>
</c:if>
</form:form>
</body>
</html>
