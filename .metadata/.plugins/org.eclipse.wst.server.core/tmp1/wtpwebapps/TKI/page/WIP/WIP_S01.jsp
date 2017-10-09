<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
<html>
<head>
	<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var wipDeadlineForm;
	var boxWip;
	var btnSearch;

	$(document).ready(function() {
		wipDeadlineForm = $("form#wipDeadlineForm");
		boxWip   		= $("select#boxWip");
		btnSearch      	= $("#btnSearch");
		
		$("#boxCustomer option:first").val("");
		
		btnSearch.click(function() {
			message.clear();
			wipDeadlineForm.attr("action", "WIP_S01_search.html");
			wipDeadlineForm.submit();
		});
		
		// export excel
		$("input#btnExport").click(function(){
			message.clear();
			downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));

			// <!-- CALL: 'WIP_S01Controller'. -->
			$("input#customerCode").remove();
			$("input#wipName").remove();
			var wipName = $('select#boxWip option:selected').text();
			var customer = $('select#boxCustomer option:selected').text();
			if($('select#boxWip option:selected').val() == ""){
				var w = wipName.replace(/-/g,"");
				wipName = w;
			}
			if($('select#boxCustomer option:selected').val() == ""){
				customer = "All";
			}

			wipDeadlineForm.attr("action", "WIP_S01_export.xls");
			wipDeadlineForm.append("<input type='hidden' id='customerCode' name='customerCode' value='"+ customer +"'/>");
			wipDeadlineForm.append("<input type='hidden' id='wipName' name='wipName' value='"+ wipName +"'/>");
			wipDeadlineForm.submit();
			wipDeadlineForm.attr("action", "WIP_S01_search.html");
		});
	});	
</script>
</head>
<body>
	<h1 class="header"><spring:message code='menu.WIPDeadline'/></h1>
	<page:message item="${deadline}" />
	<form:form id="wipDeadlineForm" method="post" action="WIP_S01_search.html" commandName="deadline">
		<table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
			<tr>
				<th width="15%">WIP</th>
				<td width="35%"><form:select path="wip" id="boxWip" items="${wipMap}"></form:select></td>
				<th>Customer</th>
				<td><form:select path="customerId" id="boxCustomer" items="${customerMap}"></form:select></td>
			</tr>
			<tr>
				<th>Part No</th>
				<td><form:input path="partNo"/></td>
				<th>Part Name</th>
				<td><form:input path="partName"/></td>
			</tr>
		</table>
		<br/>
		 <div align="right">
			<security:authorize ifAnyGranted="WIP_R01_EXPORT">
				<input type=button value="Summary Report" id="btnExport"/>
			</security:authorize>
			<input type="button" value="Search" id="btnSearch" style="width:100px"/>		    	
		</div>
		<br/>
		
<c:if test="${not empty deadline.deadlinePartList}">
	<span class="textred"><b>${hour} Hours / Shift</b></span>
	<table width="1200" border="1" align="center" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content">
		<tr>
			<td colspan="${colspan + 5}">
				<div style="float:left" ><page:display item="${deadline}"/></div>
				<div style="float:right"><page:number item="${deadline}"/></div>
			</td>
		</tr>
		
    	<c:forEach var="partList" items="${deadline.deadlinePartList}" varStatus="statusPart" begin="0" step="1">
	    	<c:if test="${statusPart.index == 0 }">
		    	<tr>
					<th rowspan="3" align="center">Customer</th>
					<th rowspan="3" align="center">Part No : Part Name</th>
				  	<th rowspan="3" align="center">WIP</th>
				    <th rowspan="3" align="center">Cap.(P/Hr)</th>
				    <th rowspan="3" align="center">Stock</th>
					<c:forEach var="deadlineMonth" items="${deadlineMonthMap}" varStatus="statusMonth" begin="0" step="1">
						<th colspan="${deadlineMonth.colspanPerMonth }" align="center">${monthMap[deadlineMonth.month]} ${deadlineMonth.year}</th>
					</c:forEach>
				</tr>
				<tr>
					<c:forEach var="wipList" items="${partList.deadlineWIPList}" varStatus="statusWIPHearder" begin="0" end="0">
						<c:forEach var="deadDateHeaderList" items="${wipList.deadlineDateList}" varStatus="statusDateHearder" begin="0" step="2">
							<fmt:formatDate var="today" value="${now}" pattern="yyyyMMdd" />
							<fmt:formatDate var="reportDate" value="${deadDateHeaderList.reportDate}" pattern="yyyyMMdd" />
							<c:if test="${today == reportDate}">
								<th width="30" align="center" colspan="2" style="background-color: #1E90FF">
									<fmt:formatDate pattern="d" value="${deadDateHeaderList.reportDate }"/>
								</th>
							</c:if>
							<c:if test="${today != reportDate}">
								<th width="30" align="center" colspan="2">
									<fmt:formatDate pattern="d" value="${deadDateHeaderList.reportDate }"/>
								</th>
							</c:if>
						</c:forEach>
					</c:forEach>
				</tr>
				<tr>
				    <c:forEach var="wipList" items="${partList.deadlineWIPList}" varStatus="statusWIPHearder" begin="0" end="0">
						<c:forEach var="deadDateHeaderList" items="${wipList.deadlineDateList}" varStatus="statusDateHearder" begin="0" step="2">
							<fmt:formatDate var="today" value="${now}" pattern="yyyyMMdd" />
							<fmt:formatDate var="reportDate" value="${deadDateHeaderList.reportDate}" pattern="yyyyMMdd" />
							<c:if test="${today == reportDate}">
								<th width="30" align="center" style="background-color: #1E90FF">D</th>
								<th width="30" align="center" style="background-color: #1E90FF">N</th>
							</c:if>
							<c:if test="${today != reportDate}">
								<th width="30" align="center">D</th>
								<th width="30" align="center">N</th>
							</c:if>
						</c:forEach>
					</c:forEach>
		    	</tr>
	    	</c:if>
			<c:forEach var="wipList" items="${partList.deadlineWIPList}" varStatus="statusWIP" begin="0" step="1">
				<tr>
					<c:if test="${statusWIP.index == 0 }">
						<td align="center" rowspan="${fn:length(partList.deadlineWIPList) }">${partList.customerCode}</td>
						<td align="center" rowspan="${fn:length(partList.deadlineWIPList) }">${partList.partNo} : ${partList.partName}</td>
					</c:if>
				    <th align="center">${wipList.wip}</th>
				    <th align="center">
				    	<c:if test="${wipList.wip == '+NG' }">
				    		${ wipList.capacity }%
				    	</c:if>
				    	<c:if test="${wipList.wip != '+NG' }">
				    		${ wipList.capacity }
				    	</c:if>&nbsp;
				    </th>
				    <th align="center">${wipList.stock}</th>
				    <c:if test="${wipList.isWip != 0}">
				    	<c:forEach var="deadDateList" items="${wipList.deadlineDateList}" varStatus="statusDate" begin="0" step="1">
				    		<td align="center" bgcolor="${deadDateList.color}">${deadDateList.deadlineQty}&nbsp;</td>
				    	</c:forEach>
				    </c:if>
				    <c:if test="${wipList.isWip == 0}">
				    	<c:forEach var="deadDateList" items="${wipList.deadlineDateList}" varStatus="statusDate" begin="0" step="2">
				    		<td colspan="2" align="center" bgcolor="${deadDateList.color}">${deadDateList.deadlineQty}&nbsp;</td>
				    	</c:forEach>
				    </c:if>
				</tr>
		    </c:forEach>
		    <tr>
				<th align="center" colspan="${(colspan + 5)*2}"></th>
			</tr>
		</c:forEach>
		
		<tr>
			<td colspan="${(colspan + 5)*2}">
				<div style="float:left" ><page:display item="${deadline}"/></div>
				<div style="float:right"><page:number item="${deadline}"/></div>
			</td>
		</tr>
		<tr>
			<td colspan="${(colspan + 5)*2}">
				<table align="right" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="30" bgcolor="#9AE9A0">&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;
							 No production and display green color since today to date of prepare for production
						</td>
					</tr>
					<tr>
						<td height="5" colspan="2"></td>
					</tr>
					<tr>
						<td bgcolor="#FFFF99">&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;
							Prepare for production and display 6 shift for yellow  before red color 
						</td>
					</tr>
					<tr>
						<td height="5" colspan="2"></td>
					</tr>
					<tr>
						<td bgcolor="#FF8080">&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;
							To need production
						</td>
					</tr>
					<tr>
						<td height="5" colspan="2"></td>
					</tr>
					<tr>
						<td bgcolor="#1E90FF">&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;Today</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</c:if>
</form:form>
</body>
</html>
