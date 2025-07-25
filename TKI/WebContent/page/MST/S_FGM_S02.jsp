<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
<%@ include file="../importResourcesSlickgrid.jsp"%>
<script type="text/javascript" src='page/MST/S_FGM_S02.js'></script>
<title>Insert title here</title>
</head>
<body>
	<h1 class="header">
		<spring:message code='menu.FGMaster' />
	</h1>
	<form:form id="fgMstForm" commandName="fgMaster">
		<div id="gridJs"></div>
		<table cellpadding="3" cellspacing="1" border="0" width="100%">
			<tr>
				<td>
					<div id="navcontainer">
						<ul id="navlist">
							<!-- CSS Tabs -->
							<li><a href="S_FGM_S01.html">FG Master</a></li>
							<li><a href="S_FGM_S02.html" id="current">Customer FG Mapping</a></li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
		<table width="100%" border="0">
			<tr>
				<td><page:message item="${fgMaster}" /></td>
			</tr>
		</table>
		<table width="100%" border="0" cellpadding="3" cellspacing="1">
			<tr>
				<td colspan="2">
					<table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
						<tr>
							<th class="label" width="20%">Customer <span class="textred">*</span></th>
							<td><form:select tabindex="1" path="customerId" items="${custMap}" id="customerNameSel"></form:select></td>
					</table>
					<div align="right"></div></td>
			</tr>
			<tr>
				<td width="44%"></td>
				<td width="56%"><div align="right">
						<input name="btnSearch" type="button" class="submit_button" id="btnSearch" value="Search" />
				</div></td>
			</tr>
		</table>
		<div id="custFgGrid">
			<div class="grid-header">
				<label>Customer FG Mapping</label>
				<label id="searchIcon" style="float:right; margin-right:10px">Search</label>
				<span id="searchIcon" class="ui-icon ui-icon-search"  style="float:right" title="Search panel"></span>
			</div>
			<div>
				<div id="customerfgGrid" class="grid-detail"
					style="width: 99.8%; height: 300px; overflow: hidden; outline: 0px none; position: relative;"></div>
			</div>

			<div align="right">
	  			<input name="btnSave" type="button" class="submit_button" id="btnSave" value="Save"  />
    		</div>
   		</div>
   		<br/>
		<div id="fgDialog" title="FG Master" style="display: none;">
			<div class="grid-header">
				<label>FG</label> 
				<label id="searchIcon" style="float: right; margin-right: 10px">Search</label> 
				<span id="searchIcon" class="ui-icon ui-icon-search" style="float: right" title="Search panel"></span>
			</div>
			<div>
				<div id="fgGrid" class="grid-detail"
					style="width: 99.8%; height: 360px; overflow: hidden; outline: 0px none; position: relative;"></div>
			</div>
		</div>
   		
	</form:form>
</body>
</html>