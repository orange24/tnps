<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<%@ include file="../importResourcesSlickgrid.jsp"%>
<script type="text/javascript" src='page/MST/PRC_S01.js'></script>


<style type="text/css">
<!--
.style1 {
	color: #FF0000
}
-->
</style>

</head>
<body>
<h1 class="header"><spring:message code='menu.ProcessMaster'/></h1>
	<form:form id="prcSo1Form" commandName="mwipDetail" action="PRC_S01_exportList.xls">
			<page:message item="${mWips}" /></td>
		<br />
		<div class="grid-header">
			<label>Process Master</label> 
			<label id="searchIcon" style="float: right; margin-right: 10px">Search</label> 
			<span id="searchIcon" class="ui-icon ui-icon-search" style="float: right" title="Search panel"></span>
		</div>
		<div>
			<div id="processGrid" class="grid-detail"
				style="width: 99.8%; height: 350px; overflow: hidden; outline: 0px none; position: relative;"></div>
		</div>
		<div align="right">
			<input name="btnExport" type="button" class="submit_button" id="btnExport" value="Export Process" /> 
			<input name="btnSave" type="button" class="submit_button" id="btnSave" value="Save" />
		</div>
	</form:form>

	<form:form id="exportForm" commandName="mwipDetail" action="PRC_S01_exportList.xls" method="POST">
	</form:form>
</body>
</html>
