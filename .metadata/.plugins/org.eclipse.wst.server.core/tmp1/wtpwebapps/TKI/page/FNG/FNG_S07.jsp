<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResourcesSlickgrid.jsp" %>
<script src="page/FNG/FNG_S07.js" language="javascript"></script>
</head>
<body>
	<h1 class="header"><spring:message code='menu.PrintTagLabel'/></h1>
	<ul id="navlist">
		<li><a href="FNG_S06.html">Print Tag Label</a></li>
		<li><a href="FNG_S07.html" id="current">Print Tag Changed History</a></li>
	</ul>
	<form:form id="fngS07Form" method="POST" action="FNG_S07_Export.xls" commandName="searchCriteria">
		
		<page:message item="${searchCriteria}" />
		
		<table class="ui-widget ui-widget-content" width="100%" border="0" cellpadding="3" cellspacing="1" >
			<tr>
				<th width="13%">Lot No.</th>
				<td width="37%"><form:input path="lotNo" id="_lotNo"/></td>
				
			</tr>
			<tr>
				<th width="13%">Customer From</th>
				<td width="37%"><form:select path="customerFrom" id="_customerFrom" items="${customerMap}"/></td>
				<th width="13%">Customer To</th>
				<td width="37%"><form:select path="customerTo" id="_customerTo" items="${customerMap}"/></td>
			</tr>
			<tr>
				<th width="13%">FG No. From</th>
				<td width="37%"><form:input path="fgNoFrom" id="_fgNoFrom"/></td>
				<th width="13%">FG No. To</th>
				<td width="37%"><form:input path="fgNoTo" id="_fgNoTo"/></td>
			</tr>
			<tr>
				<th width="13%">FG Name From</th>
				<td width="37%"><form:input path="fgNameFrom" id="_fgNameFrom"/></td>
				<th width="13%">FG Name To</th>
				<td width="37%"><form:input path="fgNameTo" id="_fgNameTo"/></td>
			</tr>
		</table>
		<br />
		<div align="right">
			<input name="btnExport" type="button" id="btnExport" value="Export" />
			<input name="btnSearch" type="button" id="btnSearch" value="Search" />
		</div>
	
	<br />
		<div>
			<div id="itemInformationGrid" class="grid-detail"
				style="width: 99.8%; height: 350px; overflow: hidden; outline: 0px none; position: relative;"></div>
		</div>
		<br />
	</form:form>

</body>
</html>