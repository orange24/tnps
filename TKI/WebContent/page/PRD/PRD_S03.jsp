<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="../importResourcesSlickgrid.jsp" %>

<html>
<head>
<script type="text/javascript" src="page/PRD/PRD_S03.js"></script>
</head>
<body>
<h1 class="header"><spring:message code='menu.PrintDeleteLot'/></h1>
<form:form id="prdS03Form" action="PRD_S03_save.html" methodParam="post" commandName="mProduction">
<!-- Display error Messages -->
<table width="100%">
	 <tr><td><page:message item="${mProduction}" /></td></tr>
</table>

<table width="100%" border="0" cellpadding="3" cellspacing="1">
<tr>
	<td>
		<table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
			<tr>
				<th width="12%">
					<span class="label">WIP</span>
				</th>
				<td width="25%">
					<form:select id="cboWip" path="wip"  itemValue="wip" itemLabel="wip" items="${mProduction.wipList}"></form:select>
				</td>
				<th width="13%">
					<span class="label">Lot No<span class="textred">*</span></span>
				</th>
				<td width="30%">
					<form:input id="txbStartLotNo" path="startLotNo"/> - <form:input id="txbEndLotNo" path="endLotNo"/>
				</td>
				<td rowspan="2">
					<span class="textred">*</span> Please select either lot or date
				</td>
			</tr>
			
			<tr>
				<th><span class="label">Machine Line</span></th>
				<td>
					<form:select id="cboMachineLineFrom" path="machineFrom"  itemValue="machineNo" itemLabel="machineName" items="${mProduction.machineList}" cssStyle="width:78px;"></form:select>
						To
					<form:select id="cboMachineLineTo" path="machineTo"  itemValue="machineNo" itemLabel="machineName" items="${mProduction.machineToList}" cssStyle="width:78px;"></form:select>
				</td>
				<th>
					<span class="label">Date<span class="textred">*</span></span>
				</th>
				<td>
					<form:input id="txbDieCastDateFrom" path="dcPlanDateFrom" cssClass="date" size="15" /> - <form:input id="txbDieCastDateTo" path="dcPlanDateTo" cssClass="date" size="15" />
				</td>
			</tr>
		       
			<tr>
				<th><span class="label">Shift</span></th>
				<td>
					<form:select id="cboShift" path="shift" >
	          			<form:option value="">--All--</form:option>
	          			<form:option value="D">D</form:option>
	          			<form:option value="N">N</form:option>
	          		</form:select>
				</td>
				<th><span class="label">Customer</span></th>
				<td>
					<form:select id="cboCustomer" path="customerId"  itemValue="customerId" itemLabel="customerCode" items="${mProduction.customerList}"></form:select>
				</td>
			</tr>
		        
			<tr>
				<th><span class="label">Part No</span></th>
				<td>
					<form:input id="txbPartNo" path="partNo"/>
				</td>
				<th><span class="label">Part Name</span></th>
				<td>
					<form:input id="txbPartName" path="partName"/>
				</td>
			</tr>
		       
			<tr>
				<th><span class="label">Print</span></th>
				<td>
					<form:select id="cboPrint" path="printStatus">
	          			<form:option value="">--All--</form:option>
	          			<form:option value="1">Yes</form:option>
	          			<form:option value="0">No </form:option>
	          		</form:select>
				</td>
			</tr>
		</table>
	</td>
	</tr>
	
	<tr>
		<td>
			<div id="prdS03Button" align="right">
				<input id="btnSearch" type="button" name="btnSearch" class="submit_button" value="Search" />
				<input id="btnClear" type="button" name="btnClear"  class="submit_button" value="Clear" />
			</div>
		</td>
	</tr>
</table>
	<br/>

	<div id="gridHeader" class="grid-header">
		<label id="record"></label> record(s) found.
		<label id="searchIcon" style="float:right; margin-right:10px">Search</label>
		<span id="searchIcon" class="ui-icon ui-icon-search"  style="float:right" title="Search panel"></span>
	</div>
	<div>
		<div id="prdS03Grid" class="grid-detail" style="width: 99.8%; height: 270px; outline: 0px none; position: relative;"></div>
	</div>

	<div id="prdS03Buttons" align="right">
		<table border="1" width="100%" class="ui-widget ui-widget-content" >
			<tr>
				<td align="right" class="text-bold"><b>Qty Total</b></td>
				<td align="right" width="10%" class="text-bold" id="tdQtyTotal">0</td>
			</tr>
		</table>
		<input id="btnSave"  type="button" name="btnSave"  class="submit_button" value="Save" />
		<input id="btnPrintPDF" type="button" name="btnPrintPDF" class="submit_button" value="PDF"  />
		<input id="btnPrint" type="button" name="btnPrint" class="submit_button" value="Print"  />
		<input id="btnSelectAndPrint" type="button" name="btnSelectAndPrint" class="submit_button" value="Select and Print"  />
	</div>
	
	<div id="printer" title="Printer">
		<div id="printerHeader" class="grid-header">
			<label>Select Printer</label>
		</div>
		<div id="printerDetail" class="grid-detail">
		<br/>
			<table width="100%">
				<tr>
					<td align="right" width="30%"><label>Printer</label> &nbsp;&nbsp;&nbsp; </td>
					<td align="left" width="70%">
						<select id="cboPrinter"></select>
					</td>
				</tr>
			</table>
			<br/>
		</div>
		
		<div class="grid-header">
			<label>Print Option</label>
		</div>
		<div class="grid-detail">
		<br/>
			<table width="100%">
				<tr>
					<td align="left">
						<label>Date</label>  <input id="optionPrintDate" name="optionPrintDate"  class="date" size="15" />
						<label>Worker</label> <input id="optionPrintWorker" name="optionPrintWorker"/>
						<label>Qty</label>
						<label>OK</label> : <input id="optionPrintQtyOK" name="optionPrintQtyOK" size="5"/>
						<label>NG</label> : <input id="optionPrintQtyNG" name="optionPrintQtyNG" size="5"/>
					</td>
				</tr>
				<tr>
					<td align="left">
					<hr/>
					<div>Process List</div>
					<div id="processList">
						
					</div>
					</td>
				</tr>
			</table>
			<br/>
		</div>
		
	</div>
</form:form>
<iframe id="pdfFrame" name="pdfFrame" style="display:none;"></iframe>
</body>
</html>