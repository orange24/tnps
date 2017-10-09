<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp"%>
<script language="javascript">
	var boxCustomer;
	var fgStockForm;

	$(document)
			.ready(
					function() {
						boxCustomer = $("select#boxCustom");
						fgStockForm = $("form#fgStockForm");

						fgStockForm.submit(function() {
							message.clear();
							return true;
						});

						$("input#btnCreate")
								.click(
										function() {
											message.clear();
											$("#customerCode").val(boxCustomer.find("option:selected").text());
											// <!-- notify before export report -->
											downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));

											fgStockForm.attr("action", "FNG_R02_export.xls");
											fgStockForm.submit();
											fgStockForm.attr("action", "FNG_S03_search.html");
										});
					});
</script>
</head>
<body>
	<h1 class="header">
		<spring:message code='menu.FGStockStatus' />
	</h1>
	<page:message item="${fng}" />

	<form:form id="fgStockForm" method="post" action="FNG_S03_search.html" commandName="fng">
		<table width="100%" border="0" cellpadding="3" cellspacing="1">
			<tr>
				<td colspan="2">
					<table class="ui-widget ui-widget-content" cellpadding="3" cellspacing="1" width="100%" border="0">
						<tr>
							<th width="12%" class="label">Customer</th>
							<td width="34%">
								<form:select path="customerId" id="boxCustom" items="${customerMap}" />
								<input type="hidden" name="customerCode" id="customerCode" value="" />
							</td>
							<th width="16%" class="label">FG Month</th>
							<td width="38%">
								<form:select path="month" items="${monthMap}" />
								<form:select path="year" items="${yearMap}" />
							</td>
						</tr>
						<tr>
							<th class="label">FG Name</th>
							<td>
								<form:input path="fgName" tabindex="3" />
							</td>
							<th class="label">FG No</th>
							<td>
								<form:input path="fgNo" tabindex="3" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right">

					<input name="btnCreate" type="button" id="btnCreate" value="Summary Report" />

					<input name="btnCreate2" type="submit" id="btnCreate2" value="Search" />
				</td>
			</tr>
		</table>
		<br />
		<c:if test="${not empty fng.stocksMap}">
			<table width="1200" border="1" align="center" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content "
				id="dynamictbl">
				<tr>
					<td colspan="${fng.endDay + 5}">
						<div style="float: left">
							<page:display item="${fng}" />
						</div>
						<div style="float: right">
							<page:number item="${fng}" />
						</div>
					</td>
				</tr>
				<tr>
					<th rowspan="2" align="center" class="submit_button">Customer</th>
					<th colspan="3" rowspan="2" align="center" class="submit_button">FG Name : FG No</th>
					<th colspan="${fng.endDay + 1}" align="center" class="submit_button">${monthMap[fng.month]}
						${yearMap[fng.year]}</th>
				</tr>
				<tr>
					<c:forEach varStatus="index" begin="0" end="${fng.endDay}">
						<th width="60" align="center" class="submit_button">${index.current}</th>
					</c:forEach>
				</tr>
				<c:forEach var="stockMap" items="${fng.stocksMap}" varStatus="loop" begin="0" step="1">
					<tr>
						<td rowspan="7" align="center" class="border_all">${stockMap.value[0].customerCode}</td>
						<td rowspan="7" align="center" class="border_all">
							<a
								href="WIP_S02_search.html?customerId=${stockMap.value[0].customerId}&partId=${stockMap.value[0].partId}&month=${fng.month}&year=${fng.year}">${stockMap.value[0].fgName}
								: ${stockMap.value[0].fgNo}</a>
						</td>
						<th rowspan="3" align="center" class="border_all">Delivery</th>
						<th align="center" class="border_all">Plan</th>
						<c:forEach varStatus="day" begin="0" end="${fng.endDay}" step="1">
							<td align="center">
								&nbsp;
								<c:forEach var="stock" items="${stockMap.value}">
									<c:if test="${stock.reportDay == day.current}">
									${stock.deliveryQty}
								</c:if>
								</c:forEach>
							</td>
						</c:forEach>
					</tr>
					<tr>
						<th align="center" class="border_all">Actual</th>
						<c:forEach varStatus="day" begin="0" end="${fng.endDay}" step="1">
							<td align="center">
								&nbsp;
								<c:forEach var="stock" items="${stockMap.value}">
									<c:if test="${stock.reportDay == day.current}">
									${stock.actualQty}
								</c:if>
								</c:forEach>
							</td>
						</c:forEach>
					</tr>
					<tr>
						<th align="center" class="border_all">Balance</th>
						<c:forEach varStatus="day" begin="0" end="${fng.endDay}" step="1">
							<td align="center" nowrap="nowrap">
								&nbsp;
								<c:forEach var="stock" items="${stockMap.value}">
									<c:if test="${stock.reportDay == day.current}">
									${stock.balanceQty}
								</c:if>
								</c:forEach>
							</td>
						</c:forEach>
					</tr>
					<tr>
						<th rowspan="4" align="center" class="border_all">FG</th>
						<th align="center" class="border_all">In</th>
						<c:forEach varStatus="day" begin="0" end="${fng.endDay}" step="1">
							<c:set var="column" value="<td align='center'>&nbsp;   </td>" />
							<c:forEach var="stock" items="${stockMap.value}">
								<c:if test="${stock.reportDay == day.current}">
									<c:if test="${stock.status == 1}">
										<c:set var="column" value="${fn:replace(column, 'td ', 'td bgcolor=#9AE9A0 ')}" />
									</c:if>
									<c:if test="${stock.status == 2}">
										<c:set var="column" value="${fn:replace(column, 'td ', 'td bgcolor=#FFFF99 ')}" />
									</c:if>
									<c:if test="${stock.status == 3}">
										<c:set var="column" value="${fn:replace(column, 'td ', 'td bgcolor=#FF8080 ')}" />
									</c:if>
									<c:set var="column" value="${fn:replace(column, '   ', stock.fgIn)}" />
								</c:if>
							</c:forEach>
						${column}
					</c:forEach>
					</tr>
					<tr>
						<th align="center" class="border_all">Out</th>
						<c:forEach varStatus="day" begin="0" end="${fng.endDay}" step="1">
							<c:set var="column" value="<td align='center'>&nbsp;   </td>" />
							<c:forEach var="stock" items="${stockMap.value}">
								<c:if test="${stock.reportDay == day.current}">
									<c:if test="${stock.status == 1}">
										<c:set var="column" value="${fn:replace(column, 'td ', 'td bgcolor=#9AE9A0 ')}" />
									</c:if>
									<c:if test="${stock.status == 2}">
										<c:set var="column" value="${fn:replace(column, 'td ', 'td bgcolor=#FFFF99 ')}" />
									</c:if>
									<c:if test="${stock.status == 3}">
										<c:set var="column" value="${fn:replace(column, 'td ', 'td bgcolor=#FF8080 ')}" />
									</c:if>
									<c:set var="column" value="${fn:replace(column, '   ', stock.fgOut)}" />
								</c:if>
							</c:forEach>
						${column}
					</c:forEach>
					</tr>
					<tr>
						<th align="center" class="border_all">Balance FG</th>
						<c:forEach varStatus="day" begin="0" end="${fng.endDay}" step="1">
							<c:set var="column" value="<td align='center'>&nbsp;   </td>" />
							<c:forEach var="stock" items="${stockMap.value}">
								<c:if test="${stock.reportDay == day.current}">
									<c:if test="${stock.status == 1}">
										<c:set var="column" value="${fn:replace(column, 'td ', 'td bgcolor=#9AE9A0 ')}" />
									</c:if>
									<c:if test="${stock.status == 2}">
										<c:set var="column" value="${fn:replace(column, 'td ', 'td bgcolor=#FFFF99 ')}" />
									</c:if>
									<c:if test="${stock.status == 3}">
										<c:set var="column" value="${fn:replace(column, 'td ', 'td bgcolor=#FF8080 ')}" />
									</c:if>
									<c:set var="column" value="${fn:replace(column, '   ', stock.fgBalance)}" />
								</c:if>
							</c:forEach>
						${column}
					</c:forEach>
					</tr>
					<tr>
						<th align="center" class="border_all">Adjust</th>
						<c:forEach varStatus="day" begin="0" end="${fng.endDay}" step="1">
							<td align="center">
								&nbsp;
								<c:forEach var="stock" items="${stockMap.value}">
									<c:if test="${stock.reportDay == day.current}">
									${stock.fgAdjust}
								</c:if>
								</c:forEach>
							</td>
						</c:forEach>
					</tr>
					<tr>
						<th height="1" colspan="${fng.endDay + 5}" align="center" class="border_all">&nbsp;</th>
					</tr>
				</c:forEach>
				<tr>
					<td height="1" colspan="${fng.endDay + 5}" align="right">
						<table width="400" border="0" cellpadding="0" cellspacing="5">
							<tr>
								<td width="30" bgcolor="#9AE9A0" style="border: 1px solid #000;" scope="col">&nbsp;</td>
								<td scope="col">Stock can delivery for cover next ${fng.stockHoldDay} days delivery plan</td>
							</tr>
							<tr>
								<td bgcolor="#FFFF99" style="border: 1px solid #000;" scope="col">&nbsp;</td>
								<td scope="col">Stock can delivery for less than next ${fng.stockHoldDay} days delivery plan</td>
							</tr>
							<tr>
								<td bgcolor="#FF8080" style="border: 1px solid #000;" scope="col">&nbsp;</td>
								<td scope="col">Stock not enough for delivery</td>
							</tr>
							<tr>
								<td scope="col" style="border: 1px solid #000;">&nbsp;</td>
								<td scope="col">Stock enough for delivery but next 30 days not have plan to deliver</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="${fng.endDay + 5}">
						<div style="float: left">
							<page:display item="${fng}" />
						</div>
						<div style="float: right">
							<page:number item="${fng}" />
						</div>
					</td>
				</tr>
			</table>
		</c:if>
	</form:form>
</body>
</html>
