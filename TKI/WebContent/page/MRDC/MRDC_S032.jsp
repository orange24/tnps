<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
</head>
<body>
	<h1>Product Information Inquiry NG Reason</h1>
	<page:message item="${deliveryPlan}" />
	<form:form id="mrdc_s032Form" method="post" action="MRDC_S032_search.html" commandName="deliveryPlan">
		<table width="100%" border="0" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
			<tr>
				<th width="16%" class="label">Customer : </th>
				<td width="34%">${deliveryPlan.customerCode}</td>
				<th width="20%">Year/Month : </th>
				<td width="30%">${monthMap[deliveryPlan.month]} ${deliveryPlan.year}</td>
			</tr>
			<tr>
				<th width="16%" class="label">Part No : </th>
				<td width="34%">${deliveryPlan.partNo}</td>
				<th width="20%">Part Name : </th>
				<td width="30%">${deliveryPlan.partName}</td>
			</tr>
		</table>
		<br/>
		<c:if test="${not empty deliveryPlan.date3List}">
		<table width="100%" border="1" align="center" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content" >
		   <tr>
		  	<th align="left" colspan="3">Summary NG Reason : </th>
		  </tr>
		  <tr>
		  	<th align="center">Reason Code</th>
		    <th align="center">Reason Name</th>
		    <th align="center">NG Qty</th>
		  </tr>
		  <c:set value="${0}" var="total"></c:set>
		  
		  <c:forEach items="${deliveryPlan.date3List}" var="detail" varStatus="status" begin="0" step="1"> 
		  <c:set value="${total + detail.ng}" var="total"></c:set>
		  <tr>
		  	<td align="left">${detail.reasonCode}</td>
			<td align="left">${detail.reasonName}</td>
			<td align=right><fmt:formatNumber pattern="#,##0" value="${detail.ng}" /></td>
		  </tr>
		  </c:forEach>
		  <c:if test="${deliveryPlan.ng > total  }">
		  <tr>
		  	<td align="left">N/A</td>
			<td align="left">Not Specify</td>
			<td align=right><fmt:formatNumber pattern="#,##0" value="${deliveryPlan.ng - total}" /></td>
		  </tr>
		  </c:if>
		  <tr>
		  	<th align="right" colspan="2"><strong>Total</strong></th>
		  	<th align="right"><strong><fmt:formatNumber pattern="#,##0" value="${deliveryPlan.ng}" /></strong></th>
		  </tr>
		</table>
		</c:if>
		<c:if test="${empty deliveryPlan.date3List}">
		<table width="100%" border="1" align="center" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content" >
		   <tr>
		  	<th align="left" colspan="3">Summary NG Reason : </th>
		  </tr>
		  <tr>
		  	<th align="center">Reason Code</th>
		    <th align="center">Reason Name</th>
		    <th align="center">NG Qty</th>
		  </tr>
		  <tr>
		  	<td align="left">N/A</td>
			<td align="left">Not Specify</td>
			<td align=right><fmt:formatNumber pattern="#,##0" value="${deliveryPlan.ng}" /></td>
		  </tr>
		  <tr>
		  	<th align="right" colspan="2"><strong>Total</strong></th>
		  	<th align="right"><strong><fmt:formatNumber pattern="#,##0" value="${deliveryPlan.ng}" /></strong></th>
		  </tr>
		</table>
		</c:if>
		<br/>
		<div align="left">
			<input type="button" id="btnBack" value="Back" onclick="history.go(-1)"  />
		</div>
	</form:form>
</body>
</html>