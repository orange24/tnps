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
<script src="page/WIP/WIP_S03.js" language="javascript"></script>
</head>
<body>
<h1><spring:message code='menu.WIPStockAdjustment'/></h1>
	<ul id="navlist">
		<li><a href="WIP_S03.html" id="current">WIP Stock Adjustment</a></li>
		<li><a href="WIP_S04.html" >WIP Stock Adjust History</a></li>
	</ul>
	<page:message item="${searchCriteria}" />

<form:form id="wipStockForm" method="post" action="WIP_S03_search.html" commandName="searchCriteria">
	<table width="100%" border="0" cellpadding="3" cellspacing="1">
		<tr>
			<td colspan="2">
				<table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
					<tr>
					  	<th>WIP</th>
					  	<td>
					  		<form:select path="wip" id="boxWIP" items="${wipMap}"></form:select>
						</td>
				      	<th>Customer</th>
				      	<td>
					      	<form:select path="customerId" id="boxCustom" items="${customerMap}"></form:select>
					  	</td>
					</tr>
					<tr>
					  	<th>Part No</th>
					  	<td><form:input path="partNo" tabindex="3" /></td>
						<th>Part Name</th>
						<td><form:input path="partName" tabindex="3" /></td>
					</tr>
					<tr>
				  		<th>Stock Date&nbsp;<span class="textred">*</span></th>
				  		<td>
							<form:input path="reportDate" id="reportDate" tabindex="2" size="10" cssClass="date" />
						</td>
				       	<td></td>
					</tr>
				</table>
			</td>
	  	</tr>
		<tr>
	    	<td align="left">
	    		<input name="btnExport" id="btnExport" type="button" value="Export" style="width:100px"/>
	    		<input name="btnImport" id="btnImport" type="button" value="Import" style="width:100px"/>
	    	</td>
	    	<td align="right">
	      		<input name="btnResetStock" id="btnResetStock" type="button" value="Reset Stock Value" style="width:130px"/>
	      		<input name="btnSearch" id="btnSearch" type="button" value="Search" style="width:100px"/>
	    	</td>
	  	</tr>
	</table>
	<c:if test="${fn:length(searchCriteria.adjustList) > 0}">
		<table cellpadding="3" cellspacing="1" border="1" width="100%" class="ui-widget ui-widget-content" >
		  	<tr>
		    	<td colspan="9">
					<div style="float:left" ><page:display item="${searchCriteria}"/></div>
					<div style="float:right"><page:number item="${searchCriteria}"/></div>
				</td>
		  	</tr>
		 	<tr >
			 	<th>No.</th>
			 	<th>WIP</th>
				<th>Customer</th>
				<th>Part No</th>
				<th>Part Name</th>
				<th>Stock Date</th>
				<th>WIP Stock Qty</th>
				<th>Adjust Stock Qty <span class="textred">*</span></th>
				<th>Adjust Reason <span class="textred">*</span></th>
		 	</tr>
		  	<c:forEach items="${searchCriteria.adjustList}" var="wipStock" varStatus="status" begin="0" step="1">
				<tr>
					<td align="center"><page:rowno item="${searchCriteria}" index="${status.index}"/>&nbsp;</td>
					<td align="center">${wipStock.wipName}&nbsp;</td>
					<td align="center">${wipStock.customerCode}&nbsp;</td>
					<td align="left">${wipStock.partNo}&nbsp;</td>
					<td align="left">${wipStock.partName}&nbsp;</td>
					<td align="center"><fmt:formatDate value="${wipStock.reportDate}" pattern="dd/MM/yyyy"/>&nbsp;</td>
					<td align="right"><fmt:formatNumber pattern="#,##0" value="${wipStock.currentStock}" />&nbsp;</td>
					<td align="center">
				  		<input name="adjustList[${status.count-1}].adjustStock" id="adjustList[${status.count-1}].adjustStock" 
				  			class="adjustStock" maxlength="10" size="5" tabindex="13" style="text-align: right;"/>
					</td>
					<td>
						<textarea name="adjustList[${status.count-1}].adjustReason" id="adjustList[${status.count-1}].adjustReason" class="adjustReason" cols="30" rows="3"></textarea>
						<input type="hidden" name="adjustList[${status.count-1}].wip" value="${wipStock.wip}"/>
						<input type="hidden" name="adjustList[${status.count-1}].customerCode" value="${wipStock.customerCode}"/>
						<input type="hidden" name="adjustList[${status.count-1}].customerId" value="${wipStock.customerId}"/>
						<input type="hidden" name="adjustList[${status.count-1}].partNo" value="${wipStock.partNo}"/>
						<input type="hidden" name="adjustList[${status.count-1}].partId" value="${wipStock.partId}"/>
						<input type="hidden" name="adjustList[${status.count-1}].partName" value="${wipStock.partName}"/>
						<input type="hidden" name="adjustList[${status.count-1}].currentStock" value="${wipStock.currentStock}"/>
						<input type="hidden" name="adjustList[${status.count-1}].reportDate" value="${wipStock.reportDate}"/>
					</td>
			 	</tr>
		  	</c:forEach>
		  	<tr>
				<td colspan="9">
				  <div style="float:left" ><page:display item="${searchCriteria}"/></div>
				  <div style="float:right"><page:number item="${searchCriteria}"/></div>
				</td>
		  	</tr>
		</table>
		<br/>
		<div style="float:right">
		  	<input name="btnSave" id="btnSave" type="button" value="Save"/>
		</div>
	</c:if>
</form:form>
	
<%-- Import Dialog -----------------------------------------------------------%>
<form:form id="uploadForm" method="post" action="WIP_S03_import.html" commandName="searchCriteria" enctype="multipart/form-data" title="WIP Stock Adjustment import file">
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
				&nbsp;&nbsp;&nbsp;&nbsp;<a href="WIP_S03_download_template.html">Download template format for import data</a>
			</td>
		</tr>
	</table>
</form:form>
</body>
</html>
