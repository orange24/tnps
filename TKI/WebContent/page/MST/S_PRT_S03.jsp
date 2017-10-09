<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<html>
<head>
<%@ include file="../importResourcesSlickgrid.jsp"%>
<script type="text/javascript" src='page/MST/S_PRT_S03.js'></script>
<title>Insert title here</title>
</head>
<body>
<h1 class="header"><spring:message code='menu.PartRoutingMaster'/></h1>
<div id="gridJs"></div>
<form:form id="partRoutingMasterForm"  commandName="prMaster">

  	<table width="100%" border="0">
  <tr>
     <td>
        <page:message item="${prMaster}" />  
     </td>
     <tr><td>
</td>
</tr>
</table>
<table width="100%" border="0" cellpadding="3" cellspacing="1">
  <tr>
    <td colspan="2"><table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
      <tr>
        <th width="12%" class="label">Customer&nbsp;<span class="textred">*</span></th><td>
        <form:select tabindex="1" path="customerId" items="${custMap}" id="customerNameSel" ></form:select></td>
        <td width="15%" class="label">&nbsp;</td>
        <td width="33%" >&nbsp;</td>
        </tr>
      <tr>
        <th class="label">FG No.</th>
        <td ><input type="text" name="txtfgNo" id="txtfgNo" /></td>
        <th >FG Name</th>
        <td ><input type="text" name="txtfgName" id="txtfgName" /></td>
      </tr>
      <tr>
        <th class="label">Part No.</th>
        <td ><input type="text" name="txtpartNo" id="txtpartNo" /></td>
        <th >Part Name</th>
        <td ><input type="text" name="txtpartName" id="txtpartName" /></td>
      </tr>
    </table>
      <div align="right"></div></td>
  </tr>
  <tr>
    <td width="44%"></td>
    <td width="56%"><div align="right">
      <input name="btnExport" type="button" class="submit_button" id="btnExport" value="Export Part Routing"  />
      <input name="btnSearch" type="button" class="submit_button" id="btnSearch" value="Search"  />
    </div></td>
  </tr>
</table>
<div class="grid-header" id="gridheader">
	<label>Part Routing</label>
	<label id="searchIcon" style="float:right; margin-right:10px">Search</label>
	<span  id="searchIcon" class="ui-icon ui-icon-search"  style="float:right" title="Search panel"></span>
</div>
<div>
	<div id="fgPartGrid" class="grid-detail"
		style="width: 99.8%; height: 350px; overflow: hidden; outline: 0px none; position: relative;"></div>
</div>

<div id="customerDialog" title="Customer Master" style="display: none;">
	<div class="grid-header">
		<label>Customer</label>
		<label id="searchIcon" style="float:right; margin-right:10px">Search</label>
		<span  id="searchIcon" class="ui-icon ui-icon-search"  style="float:right" title="Search panel"></span>
	</div>
	<div>
		<div id="customerGrid" class="grid-detail"
			style="width: 99.8%; height: 280px; overflow: hidden; outline: 0px none; position: relative;"></div>
	</div>
</div>

<div id="fgDialog" title="FG Master" style="display: none;">
	<div class="grid-header">
		<label>FG</label>
		<label id="searchIcon" style="float:right; margin-right:10px">Search</label>
		<span id="searchIcon" class="ui-icon ui-icon-search"  style="float:right" title="Search panel"></span>
	</div>
	<div>
		<div id="fgGrid" class="grid-detail"
			style="width: 99.8%; height: 280px; overflow: hidden; outline: 0px none; position: relative;"></div>
	</div>
</div>

<div id="partDialog" title="Copy Part Routing" style="display: none;">
		<table width="100%" border="0">
		  <tr>
		    <td >
		    <page:message item="${prMaster}" />  
		    </td>
		  </tr>
		</table>
			<table class="ui-widget ui-widget-content " cellpadding="3"
				cellspacing="1" width="100%" border="0">
				<tr>
					<td><form:select tabindex="1" path="customerId" items="${custMap}" name="copyCust"
							id="cboCopyCustomer"></form:select>
							</td>
					<td align="right"   id="btnCol">
						<input type="button" value="Search" id="btnSearchCopy" />
					</td>
				</tr>
			</table>
	<div class="grid-header">
		<label>Select Part Destination</label>
		<label id="searchIcon" style="float:right; margin-right:10px">Search</label>
		<span id="searchIcon" class="ui-icon ui-icon-search"  style="float:right" title="Search panel"></span>
	</div>
	<div>
		<div id="partGrid" class="grid-detail"
			style="width: 99.8%; height: 350px; overflow: hidden; outline: 0px none; position: relative;"></div>
	</div>
</div>

<div id="processDialog" title="Process Master" style="display: none;">
<table width="100%" border="0">
  <tr>
    <td >
    <page:message item="${partRoutingMaster}"/> 
    </td>
  </tr>
</table>

	<div class="grid-header" id="gridheader">
		<label>Process</label>		
	</div>
	<div>
		<div id="processGrid" class="grid-detail"
			style="width: 99.8%; height: 350px; overflow: hidden; outline: 0px none; position: relative;"></div>
	</div>
</div>
</form:form>
<form:form id="exportForm" commandName="prMaster"
		action="S_PRT_S03_export.xls" method="POST">
	</form:form>
</body>
</html>