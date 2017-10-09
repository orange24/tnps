<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
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
var wipS04Form;
var btnSearch;
var btnExport;
var boxCustom;
var boxWIP;
var reportDateFr;
var reportDateTo;
var createDateFr;
var createDateTo;
function fnSearch(){
	btnSearch.click(function(){
		message.clear();
		DateUtil.compare($("#createDateFr"),$("#createDateTo"));
		DateUtil.compare($("#reportDateFr"),$("#reportDateTo"));
		if (!message.isNoError()) {
			return false;
		}
		
		wipS04Form.attr("action","WIP_S04_search.html");
		wipS04Form.submit();
	});
}

function fnExport(){
	btnExport.click(function(){
		wipS04Form.attr("action","WIP_S04_export.html");
		wipS04Form.submit();
		wipS04Form.attr("action","WIP_S04_search.html");
	});
}

//JQuery
	//initialze script run
	$(document).ready(function() {
		
		reportDateFr = $("input[name=reportDateFr]");
		reportDateTo = $("input[name=reportDateTo]");
		createDateFr = $("input[name=createDateFr]");
		createDateTo = $("input[name=createDateTo]");
		wipS04Form   = $("#wipS04Form");
		btnSearch    = $("#btnSearch");
		btnExport    = $("#btnExport");
		boxCustom    = $("select#boxCustom");
		boxWIP       = $("select#boxWIP");
		
		// <!-- Initial: Set 'maxDate'. -->
		reportDateFr.datepicker( "option", "maxDate", '0d' );
		reportDateTo.datepicker( "option", "maxDate", '0d' );
		createDateFr.datepicker( "option", "maxDate", '0d' );
		createDateTo.datepicker( "option", "maxDate", '0d' );
		
		boxWIP.focus();	
		
  		fnSearch();
  		fnExport();
	});	
	
</script>
</head>
<body>
	<h1><spring:message code='menu.WIPStockAdjustment'/></h1>
	<ul id="navlist">
		<li><a href="WIP_S03.html" >WIP Stock Adjustment</a></li>
		<li><a href="WIP_S04.html" id="current">WIP Stock Adjust History</a></li>
	</ul>
	<page:message item="${searchCriteria}" />
	
	<form:form id="wipS04Form" method="post" action="WIP_S04_search.html" commandName="searchCriteria">
		<table width="100%" border="0" cellspacing="1">
		  	<tr>
			    <td colspan="2">
				    <table class="ui-widget ui-widget-content " cellspacing="1" width="100%" border="0">
				        <tr>
							<th>WIP</th>
							<td><form:select path="wip" id="boxWIP" items="${wipMap}"></form:select></td>
							<th>Customer</th>
							<td><form:select path="customerId" id="boxCustom" items="${customerMap}"></form:select></td>
				        </tr>
				        <tr>
							<th>Part No</th>
							<td><form:input path="partNo" tabindex="3" /></td>
							<th>Part Name</th>
							<td><form:input path="partName" tabindex="3" /></td>
				        </tr>
				        <tr>
							<th>Adjust Date(From-To)</th>
							<td> 
							  	<form:input title="Adjust Date From" path="createDateFr" id="createDateFr" cssClass="date" tabindex="3" /> 
								<form:input title="Adjust Date To" path="createDateTo" id="createDateTo" cssClass="date" tabindex="3" /></td>
							<th>Stock Date(From-To)&nbsp;</th>
				          	<td>
				          		<form:input title="Stock Date From" path="reportDateFr" id="reportDateFr" cssClass="date" tabindex="3" /> 
				            	<form:input title="Stock Date To" path="reportDateTo" id="reportDateTo" cssClass="date" tabindex="3" />
							</td>
				        </tr>
				    </table>
				</td>
			</tr>
			<tr>
			  	<td colspan="2" align="right">
			    	<security:authorize ifAnyGranted="WIP_R03_EXPORT">
						<input name="btnExport" type="button" id="btnExport" value="Summary Report"  />
					</security:authorize>
					<input class="submit_button" name="btnSearch" id="btnSearch" type="button" value="Search" style="width:100px"/>
			  	</td>
			</tr>
		</table>
		<br />
		<c:if test="${fn:length(searchCriteria.adjustList) > 0}">
			<table cellpadding="3" cellspacing="1" border="1" width="100%" class="ui-widget ui-widget-content" >
				<tr>
					<td colspan="11">
						<div style="float:left" ><page:display item="${searchCriteria}"/></div>
						<div style="float:right"><page:number item="${searchCriteria}"/></div>
					</td>
				</tr>
				<tr>
				   	<th>No.</th>
				   	<th>WIP</th>
					<th>Customer</th>
					<th>Part No</th>
					<th>Part Name</th>
					<th>Stock Date</th>
					<th>Before  Adjust Stock Qty</th>
					<th>After  Adjust Stock Qty</th>
					<th>Adjust Reason</th>
					<th>Adjust By</th>
					<th>Adjust Date</th>
				</tr>
				<c:forEach items="${searchCriteria.adjustList}" var="wipHis" varStatus="status" begin="0" step="1">
					<tr >
						<td align="center"><page:rowno item="${searchCriteria}" index="${status.index}"/>&nbsp;</td>
						<td align="center">${wipHis.wipName}&nbsp;</td>
						<td align="center">${wipHis.customerCode}&nbsp;</td>
						<td align="left">${wipHis.partNo}&nbsp;</td>
						<td align="left">${wipHis.partName}&nbsp;</td>
						<td align="left"><fmt:formatDate value="${wipHis.reportDate}" pattern="dd/MM/yyyy"/>&nbsp;</td>
						<td align="right"><fmt:formatNumber pattern="#,##0" value="${wipHis.currentStock}" />&nbsp;</td>
						<td align="right"><fmt:formatNumber pattern="#,##0" value="${wipHis.adjustStock}" />&nbsp;</td>
						<td align="left">${wipHis.adjustReason}&nbsp;</td>
						<td align="left">${wipHis.updateBy}&nbsp;</td>
						<td align="left"><fmt:formatDate value="${wipHis.lastUpdate}" pattern="dd/MM/yyyy HH:mm:ss"/>&nbsp;</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="11">
						<div style="float:left" ><page:display item="${searchCriteria}"/></div>
						<div style="float:right"><page:number item="${searchCriteria}"/></div>
					</td>
				</tr>
			</table>
		</c:if>
	</form:form>
</body>
</html>
