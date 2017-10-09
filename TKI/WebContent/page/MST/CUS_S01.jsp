<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
	<title></title>
<%@ include file="../importResourcesSlickgrid.jsp"%>
<script src="page/MST/CUS_S01.js" language="javascript"></script>
</head>
<body>
	<h1 class="header"><spring:message code='menu.CustomerMaster'/></h1>
	<form:form id="cus_s01Form" commandName="mCustomer"
		action="CUS_S01_search.html" method="POST">
		
		
		<page:message item="${mCustomer}" /> 
		<br />
		<div class="grid-header">
			<label>Customer</label> 
			<label id="searchIcon" style="float: right; margin-right: 10px">Search</label> 
			<span id="searchIcon" class="ui-icon ui-icon-search" style="float: right" title="Search panel"></span>
		</div>
		<div>
			<div id="customerGrid" class="grid-detail"
				style="width: 99.8%; height: 350px; overflow: hidden; outline: 0px none; position: relative;"></div>
		</div>
		<div align="right">
			<input name="btnExport" type="button" class="submit_button"
				id="btnExport" value="Export Customer" /> <input
				name="btnSave" type="button" class="submit_button"
				id="btnSave" value="Save" />
		</div>
		<br />
	</form:form>
	
	<form:form id="exportForm" commandName="mCustomer"
		action="CUS_S01_export.xls" method="POST">
	</form:form>
</body>
</html>
