<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script src="page/FNG/FNG_S04.js" language="javascript"></script>
</head>
<body>
<h1><spring:message code='menu.FGStockAdjustment'/></h1>
	<ul id="navlist">
		<li><a href="FNG_S04.html" id="current">FG Stock Adjustment</a></li>
		<li><a href="FNG_S05.html">FG Stock Adjustment History</a></li>
	</ul>
	<page:message item="${tfgStock}" />
	<form:form id="fngS04Form" methodParam="post" action="FNG_S04_search.html" commandName="tfgStock">
		<table class="ui-widget ui-widget-content" width="100%" border="0" cellpadding="3" cellspacing="1" >
			<tr>
				<th width="13%">Customer</th>
				<td width="37%">
					<form:select path="customerId" id="customerId" tabindex="1">
	              		<form:options items="${customerMap}"/>
	            	</form:select>
				</td>
				<th width="13%">Stock Date&nbsp;<span class="textred">*</span></th>
				<td width="37%">
					<form:input path="reportDate" id="reportDate" tabindex="2" size="10" cssClass="date" />
				</td>
			</tr>
			<tr>
				<th width="13%">FG No.</th>
				<td width="37%"><form:input path="fgNo" /></td>
				<th width="13%">FG Name</th>
				<td width="37%"><form:input path="fgName" /></td>
			</tr>
		</table>
		<table width="100%" border="0" cellpadding="3" cellspacing="1">
			<tr>
    			<td align="left">
		    		<input name="btnExport" id="btnExport" type="button" value="Export" style="width:100px"/>
		    		<input name="btnImport" id="btnImport" type="button" value="Import" style="width:100px"/>
	    		</td>
				<td align="right">
					<input name="btnResetStock" id="btnResetStock" type="button" value="Reset Stock Value" style="width:130px"/>
					<input name="btnSearch" type="button" id="btnSearch" value="Search" />
				</td>
	    	</tr>
		</table>
		<c:if test="${fn:length(tfgStock.tfgStockList) > 0}">
			<table cellpadding="3" cellspacing="1" border="0" width="100%" >
				<tr>
					<td>
						<table width="100%" border="1" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
							<tr>
								<td colspan="9">
							  		<div style="float:left" ><page:display item="${tfgStock}" /></div>
							  		<div style="float:right" ><page:number item="${tfgStock}" /></div>
							  	</td>
							</tr>
							<tr >
								<th align="center">Customer</th>
								<th align="center"><span >FG No.</span></th>
				    			<th align="center"><span >FG Name</span></th>
				    			<th align="center"><span >Stock Date</span></th>
				    			<th width="9%" align="center">Before  Adjust Stock<br />(Balance)</th>
				    			<th width="9%" align="center">Adjust  Stock Qty <br />(Balance) <span class="textred">*</span></th>
				    			<th align="center" class="submit_button">Adjust Reason  <span class="textred">*</span></th>
							</tr>
							<c:forEach items="${tfgStock.tfgStockList}" varStatus="status" var="stock" begin="0" step="1">
								<tr>
									<td align="center">
										${stock.customerCode}&nbsp;
										<form:hidden path="tfgStockList[${status.index}].customerId"/>
										<form:hidden path="tfgStockList[${status.index}].customerCode"/>
										<form:hidden path="tfgStockList[${status.index}].fgId"/>
									</td>
									<td align="center">
										${stock.fgNo}&nbsp;
										<form:hidden path="tfgStockList[${status.index}].fgNo"/>
									</td>
									<td align="center">
										${stock.fgName}&nbsp;
										<form:hidden path="tfgStockList[${status.index}].fgName"/>
									</td>
									<td align="center">
										<fmt:formatDate value="${stock.reportDate}" pattern="dd/MM/yyyy"/>&nbsp;
										<form:hidden path="tfgStockList[${status.index}].reportDate"/>
									</td>
									<td align="right">
										<fmt:formatNumber value="${stock.fgBalance}"/>&nbsp;
										<form:hidden path="tfgStockList[${status.index}].fgBalance"/>
									</td>
									<td align="center">
										<form:input path="tfgStockList[${status.index}].fgAdjust" id="tfgStockList[${status.index}].fgAdjust" 
											cssClass="adjustStock" size="5" onkeypress="return IntegerFilter(event)" cssStyle="text-align : right;"/>
									</td>
									<td>
										<form:textarea cols="30" rows="3" path="tfgStockList[${status.index}].adjustReason" 
											id="tfgStockList[${status.index}].adjustReason" cssClass="adjustReason"/>
									</td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="9">
							  		<div style="float:left" ><page:display item="${tfgStock}" /></div>
							  		<div style="float:right" ><page:number item="${tfgStock}" /></div>
							  	</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
		   			<td align="left">
						<input name="btnSave" type="button" id="btnSave" value="Save" />
					</td>
		 		</tr>
			</table>
		</c:if>
	</form:form>
	
	<%-- Import Dialog -----------------------------------------------------------%>
	<form:form id="uploadForm" method="post" action="FNG_S04_import.html" commandName="tfgStock" 
			enctype="multipart/form-data" title="FG Stock Adjustment importing file">
		<table cellpadding="3" cellspacing="0" width="100%" border="0">
			<tr>
				<td>
					Select file for import data
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;<input type="file" id="fileImport" name="fileImport" />
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;<a href="FNG_S04_download_template.html">Download template format for import data</a>
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>