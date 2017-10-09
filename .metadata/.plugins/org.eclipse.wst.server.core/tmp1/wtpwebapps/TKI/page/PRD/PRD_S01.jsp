<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="../importResourcesSlickgrid.jsp" %>

<html>
<head>
<script type="text/javascript" src="page/PRD/PRD_S01.js"></script>
</head>
<body>
<h1 class="header"><spring:message code='menu.DIECASTPlanEntry'/></h1>
<form:form id="prdS01Form" action="PRD_S01.html" methodParam="post" commandName="mProduction">
	<!-- Show header Tabs -->
  	<table cellpadding="3" cellspacing="1" border="0" width="100%">
		<tr>
			<td >
				<div id="navcontainer">
					<ul id="navlist">
						<li><a href="PRD_S01.html" id="current">DIE CAST Plan Entry</a> </li>
						<li><a href="PRD_S02.html" >DIE CAST Plan Search</a> </li>
					</ul>
				</div>
			</td>
		</tr>
	</table>
	
	<!-- Display error messages -->
	<table width="100%" border="0">
		<tr><td ><page:message item="${mProduction}" /></td></tr>
	</table>

	<table width="100%" border="0" cellpadding="3" cellspacing="1">
		<tr>
		<td>
			<table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
				<tr>
					<th width="12%">
						<span class="label">Diecast Plan Date<span class="textred">*</span></span>
					</th>
					<td width="34%">
						<input id="txbDieCastPlanDate" type="text" class="date" />
					</td>
					<th width="16%">
						<span class="label">WIP<span class="textred">*</span></span>
					</th>
					<td width="38%">
						<select id="cboWip" name="cboWip" ></select>
					</td>
				</tr>
				<tr>
					<th width="12%">
						<span class="label">Machine<span class="textred">*</span></span>
					</th>
					<td width="34%">
						<select id="cboMachine" name="cboMachine">
							<option value=''>All Machine</option>
						</select>
					</td>
				</tr>
			</table>
		</td>
		</tr>
		<tr align="right">
			<td><input id="btnSearch" type="button" name="btnSearch" class="submit_button" value="Search" /></td>
		</tr>
	</table>

	<div id="gridHeader">
		<div class="grid-header">
			<label>Production Plan</label> 
			<label id="searchIcon" style="float: right; margin-right: 10px">Search</label> 
			<span id="searchIcon" class="ui-icon ui-icon-search" style="float: right" title="Search panel"></span>
		</div>
		<div>
			<div id="prdGrid" class="grid-detail"
				style="width: 99.8%; height: 350px; overflow: hidden; outline: 0px none; position: relative;"></div>
		</div>
		
		<div align="right">
			<input id="btnSave" name="btnSave" type="button" class="submit_button" value="Save" />
		</div>
	</div>
</form:form>
</body>
</html>