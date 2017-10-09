<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResourcesSlickgrid.jsp"%>
<script type="text/javascript" src='script/plugin/slickgrid-2.0/custom/NIR_S01.slick.checkboxselectcolumn.js'></script>
<script src="page/NIR/NIR_S01.js" language="javascript"></script>
</head>
<body>
	<h1>
		<spring:message code='menu.NirvanaSyncHistory' />
	</h1>
	<ul id="navlist">
		<li><a href="NIR_S01.html">Master Data Sync</a></li>
	</ul>
	<page:message item="${searchCriteria}" />

	<form:form id="nirS01Form" method="post" action="NIR_S01_search.html" commandName="searchCriteria">
		<table width="100%" border="0" cellspacing="1">
			<tr>
				<td colspan="2">
					<table class="ui-widget ui-widget-content" cellspacing="1" width="100%" border="0">
						<tr>
							<th>Sync Date</th>
							<td colspan="3">
								<form:input title="Sync Date From" path="syncDateFrom" id="syncDateFrom" cssClass="date" tabindex="1" />
								<form:input title="Sync Date To" path="syncDateTo" id="syncDateTo" cssClass="date" tabindex="2" />
							</td>
						</tr>
						<tr>
							<th width="15%">Transaction Type</th>
							<td width="35%">
								<form:select path="transType" id="transType" tabindex="3">
									<form:option value="">-- All Type --</form:option>
									<form:option value="I">Insert</form:option>
									<form:option value="U">Update</form:option>
									<form:option value="D">Delete</form:option>
								</form:select>
							</td>
							<th width="15%">Status</th>
							<td width="35%">
								<form:select path="syncStatus" id="syncStatus" tabindex="4">
									<form:option value="">-- All Status --</form:option>
									<form:option value="N">New</form:option>
									<form:option value="S">Success</form:option>
									<form:option value="E">Error</form:option>
									<form:option value="F">Fixed</form:option>
								</form:select>
							</td>
						</tr>
						<tr>
							<th>FG No</th>
							<td>
								<form:input path="fgNo" tabindex="5" />
							</td>
							<th>FG Name</th>
							<td>
								<form:input path="fgName" tabindex="6" />
							</td>
						</tr>
						<tr>
							<th>Customer Code</th>
							<td>
								<form:input path="customerCode" tabindex="7" />
							</td>
							<th>Customer Name</th>
							<td>
								<form:input path="customerName" tabindex="8" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right">
					<input name="btnSearch" id="btnSearch" type="button" value="Search" style="width: 100px" />
				</td>
			</tr>
		</table>

		<div id="nirvanaResult">
			<div class="grid-header">
				<label>Master data Sync</label>
				<label id="searchIcon" style="float: right; margin-right: 10px">Search</label>
				<span id="searchIcon" class="ui-icon ui-icon-search" style="float: right" title="Search panel"></span>
			</div>
			<div>
				<div id="nirvanaMasterGrid" class="grid-detail"
					style="width: 99.8%; height: 350px; overflow: hidden; outline: 0px none; position: relative;"></div>
			</div>
			<div align="right">
				<input name="btnSave" type="button" class="submit_button" id="btnSave" value="Change Status to Fixed" />
			</div>
		</div>
	</form:form>
</body>
</html>