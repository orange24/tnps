<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ include file="../importResourcesSlickgrid.jsp"%>
<script type="text/javascript" src='page/MST/S_FGM_S01.js'></script>
<title>Insert title here</title>
</head>
<body>
	<h1 class="header"><spring:message code='menu.FGMaster'/></h1>
	<form:form id="fgMstForm" commandName="fgMasterExportList" action="S_FGM_S01_search.html">
		<div id="gridJs"></div>
		<table cellpadding="3" cellspacing="1" border="0" width="100%">
			<tr>
				<td><div id="navcontainer">
						<ul id="navlist">
							<li><a href="S_FGM_S01.html" id="current">FG Master</a></li>
							<li><a href="S_FGM_S02.html">Customer FG Mapping</a></li>
						</ul>
					</div></td>
			</tr>
		</table>
		<table width="100%" border="0">
			<tr>
				<td><page:message item="${fgMaster}" /></td>
			</tr>
		</table>

		<br />
		<div class="grid-header">
			<label>FG Master</label>
			<label id="searchIcon" style="float: right; margin-right: 10px">Search</label>
			<span id="searchIcon" class="ui-icon ui-icon-search" style="float: right" title="Search panel"></span>
		</div>
		<div>
			<div id="fgGrid" class="grid-detail"
				style="width: 99.8%; height: 350px; overflow: hidden; outline: 0px none; position: relative;"></div>
		</div>
		<div align="right">
			<input name="btnExport" type="button" class="submit_button" id="btnExport" value="Export FG" /> 
			<input name="btnSave" type="button" class="submit_button" id="btnSave" value="Save" />
		</div>
		<br />
	</form:form>
	
	<form:form id="exportForm" commandName="fgMasterExportList"
		action="FGM_R01_export.xls" method="POST">
	</form:form>
</body>
</html>