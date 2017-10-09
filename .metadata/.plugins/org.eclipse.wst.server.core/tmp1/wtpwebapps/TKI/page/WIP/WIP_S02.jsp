<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
	<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var wipStockForm;
	var boxCustomer;
	var boxPartNo;
	var boxWIPCode;
	var btnExport;
	var btnSearch;

	$(document).ready(function(){
		wipStockForm = $("form#wipStockForm");
		boxCustomer  = $("select#boxCustom");
		boxPartNo    = $("select#boxPartNo");
		boxWIPCode   = $("select#boxWIP");
		btnExport   = $("#btnExport");
		btnSearch   = $("#btnSearch");
		
		btnExport.click(function(){
			message.clear();
			wipStockForm.attr("action","WIP_S02_export.html");
			wipStockForm.submit();
		});
		
		btnSearch.click(function(){
			message.clear();
			wipStockForm.attr("action","WIP_S02_search.html");
			wipStockForm.submit();
		});

		boxCustomer.change(initPartNo);
		boxPartNo.change(selectWIP);
		boxCustomer.change(selectWIP);
	});

	function initPartNo() {
		if( !boxPartNo || !boxPartNo.exists() )
			return;
		
		var params = { "customerId": "", "wip": "" };
		if( boxCustomer.val() ) params.customerId = boxCustomer.val();
		getJSON("boxPartNameNoAll",params,function(result){
			boxPartNo.empty();
			$.each(result,function(val, text){
				boxPartNo.append( $("<option></option>").val(val).html(text) );
			});
		});
	}
	function selectWIP() {
		boxWIPCode.empty();
		var params = { "customerId": "", "partId": "" };
		if( boxCustomer.val() ) params.customerId = boxCustomer.val();
		if( boxPartNo.val() )params.partId        = boxPartNo.val();
		getJSON("boxWIP",params,function(result){
			boxWIPCode.empty();
			$.each(result,function(val, text){
				boxWIPCode.append( $("<option></option>").val(val).html(text) );
			});
		});
	}
</script>
</head>
<body>
	<h1 class="header"><spring:message code='menu.WIPStockStatus'/></h1>
	<page:message item="${wip}" />

	<form:form id="wipStockForm" method="post" action="WIP_S02_search.html" commandName="wip">
		<table width="100%" border="0" cellpadding="3" cellspacing="1">
			<tr>
		    	<td>
		    		<table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
		        		<tr>
		          			<th width="12%"><span class="label">Customer </span></th>
		          			<td width="34%"><form:select path="customerId" id="boxCustom" items="${customerMap}"></form:select></td>
		          			<th width="16%"><span class="label">Part</span></th>
		          			<td width="38%"><form:select path="partId" id="boxPartNo" items="${partMap}"></form:select></td>
		        		</tr>
		        		<tr>
			          		<th><span class="label"> WIP</span></th>
			          		<td><form:select path="wip" id="boxWIP" items="${wipMap}"></form:select></td>
			          		<th><span class="label">Month</span></th>
			          		<td>
			          			<form:select path="month" items="${monthMap}"></form:select>
			          			<form:select path="year" items="${yearMap}"></form:select>
			          		</td>
		        		</tr>
		    		</table>
		    	</td>
		  	</tr>
		  	<tr>
		    	<td align="right">
		    		<security:authorize ifAnyGranted="WIP_R02_EXPORT">
		    			<input type="button" id="btnExport" value="Summary Report"  />
		    		</security:authorize>
		    		<input type="button" id="btnSearch" value="Search"  />
		   		</td>
		  	</tr>
		</table>
	</form:form>
<br />

	<c:if test="${not empty wip.stockMap}">
		<table width="1200" border="1" align="center" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content" >
			<tr >
				<th rowspan="2" align="center" class="submit_button" >Customer</th>
				<th rowspan="2" align="center" class="submit_button" >Part</th>
				<th rowspan="2" align="center" class="submit_button" >WIP</th>
				<th rowspan="2" align="center" class="submit_button" >&nbsp;</th>
				<th colspan="${wip.endDay + 1}" align="center" class="submit_button" ><div align="center">${monthMap[wip.month]} ${yearMap[wip.year]}</div></th>
			</tr>
			<tr >
				<c:forEach varStatus="index" begin="0" end="${wip.endDay}">
					<th width="60" align="center" class="submit_button" >${index.current}</th>
				</c:forEach>
			</tr>
			<c:forEach var="stockMap" items="${wip.stockMap}" varStatus="loop" begin="0" step="1">
				<tr >
					<td rowspan="6" align="center" >${stockMap.value[0].customer}</td>
					<td rowspan="6" align="center" >${stockMap.value[0].partName} : ${stockMap.value[0].partNo}</td>
					<td rowspan="6" align="center" >${stockMap.value[0].wipOrder}. ${stockMap.value[0].wipName}</td>
					<td align="center"><strong>OK</strong></td>
					<c:forEach varStatus="day" begin="0" end="${wip.endDay}" step="1">
						<td align="center">&nbsp;
							<c:forEach var="stock" items="${stockMap.value}">
								<c:if test="${stock.reportDay == day.current}">
									<fmt:formatNumber value="${stock.ok}" pattern="#,##0" />
								</c:if>
							</c:forEach>
						</td>
					</c:forEach>
				</tr>
				<tr >
					<td align="center"><strong>Pending</strong></td>
					<c:forEach varStatus="day" begin="0" end="${wip.endDay}" step="1">
						<td align="center">&nbsp;
							<c:forEach var="stock" items="${stockMap.value}">
								<c:if test="${stock.reportDay == day.current}">
									<fmt:formatNumber value="${stock.pd}" pattern="#,##0" />
								</c:if>
							</c:forEach>
						</td>
					</c:forEach>
				</tr>
				<tr >
					<td align="center"><strong>NG</strong></td>
					<c:forEach varStatus="day" begin="0" end="${wip.endDay}" step="1">
						<td align="center">&nbsp;
							<c:forEach var="stock" items="${stockMap.value}">
								<c:if test="${stock.reportDay == day.current}">
									<fmt:formatNumber value="${stock.ng}" pattern="#,##0" />
								</c:if>
							</c:forEach>
						</td>
					</c:forEach>
				</tr>
				<tr >
					<td align="center"><strong>NextWIP</strong></td>
					<c:forEach varStatus="day" begin="0" end="${wip.endDay}" step="1">
						<td align="center">&nbsp;
							<c:forEach var="stock" items="${stockMap.value}">
								<c:if test="${stock.reportDay == day.current}">
									<fmt:formatNumber value="${stock.nextWIPQty}" pattern="#,##0" />
								</c:if>
							</c:forEach>
						</td>
					</c:forEach>
				</tr>
				<tr >
					<td align="center"><strong>Stock</strong></td>
					<c:forEach varStatus="day" begin="0" end="${wip.endDay}" step="1">
						<td align="center">
							<c:forEach var="stock" items="${stockMap.value}">
								<c:choose>
									<c:when test="${day.current == 0 && stock.reportDay == 1}">
										<strong>
											<fmt:formatNumber value="${stock.prevStock}" pattern="#,##0" />
										</strong>
									</c:when>
									<c:when test="${stock.reportDay == day.current && empty stock.adjustStock}">
										<strong>
											<fmt:formatNumber value="${stock.currentStock}" pattern="#,##0" />
										</strong>
									</c:when>
									<c:when test="${stock.reportDay == day.current && not empty stock.adjustStock}">
										<fmt:formatNumber value="${stock.currentStock}" pattern="#,##0" />
									</c:when>
								</c:choose>
							</c:forEach>
						</td>
					</c:forEach>
				</tr>
				<tr >
					<td align="center"><strong>StockAdj.</strong></td>
					<c:forEach varStatus="day" begin="0" end="${wip.endDay}" step="1">
						<td align="center">&nbsp;
							<c:forEach var="stock" items="${stockMap.value}">
								<c:if test="${stock.reportDay == day.current}">
									<strong>
										<fmt:formatNumber value="${stock.adjustStock}" pattern="#,##0" />
									</strong>
								</c:if>
							</c:forEach>
						</td>
					</c:forEach>
				</tr>
				<tr ><th height="1" colspan="${wip.endDay + 5}" align="center" >&nbsp;</th></tr>
			</c:forEach>
		</table><br />
		Stock = Stock (N-1) + OK - Next WIP.<br />
		Next WIP = (OK + Pending + NG ) of next process.<br />
	</c:if>
</body>
</html>
