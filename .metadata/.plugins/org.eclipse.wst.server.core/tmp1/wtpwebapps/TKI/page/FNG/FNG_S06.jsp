<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>

<html>
<head>
<title></title>
<%@ include file="../importResourcesSlickgrid.jsp"%>
<script src="page/FNG/FNG_S06.js" language="javascript"></script>
<style>
.print-column span.slick-column-name:after {
	content: "Print";
	position: absolute;
}
</style>
</head>
<body>
<h1 class="header"><spring:message code='menu.PrintTagLabel'/></h1>
	<form:form id="fngForm" action="FNG_S06_preview.html"
		commandName="searchCriteria" method="POST">
	
		<ul id="navlist">
			<li><a href="FNG_S06.html" id="current">Print Tag Label</a></li>
			<li><a href="FNG_S07.html">Print Tag Changed History</a></li>
		</ul>
	
		<page:message item="${bean}" />

		<input type="hidden" id="tagId" />
		<input type="hidden" id="hideLotNo" />
		<input type="hidden" id="hideCustomerId" />
		<input type="hidden" id="hideFgId" />
		<input type="hidden" id="hideSnp" />
		<input type="hidden" id="hideQty" />
		<input type="hidden" id="hideLabelType" />
		<input type="hidden" id="hideVendorCode" />
		<input type="hidden" id="hideVendorFgNo" />

		<form:hidden path="printerName" id="printerName"/>

		<table width="100%" border="0" cellpadding="3" cellspacing="1">
			<tr>
				<td colspan="2">
					<table class="ui-widget ui-widget-content " cellpadding="3"
						cellspacing="1" width="100%" border="0">
						<tr>
							<th width="17%"><span class="label">Lot No.<span
									class="textred">*</span></span></th>
							<td width="35%"><input type="text" id="lotNo"
								class="ui-autocomplete-input" tabindex="12" autocomplete="off"
								role="textbox" aria-autocomplete="list" aria-haspopup="true" maxlength="14" />
							</td>
							<th width="17%"><span class="label">Process</span></th>
							<td><input type="text" id="wip" readonly="readonly"
								disabled="disabled" class="styleFontBold" /></td>
						</tr>
						<tr>
							<th width="17%"><span class="label">Customer</span><span
								class="textred">*</span></th>
							<td><select name="customerId" id="customerId">
							</select></td>
							<th width="17%"><span class="label">FG No : FG Name</span><span
								class="textred">*</span></th>
							<td><select name="fgNo" id="fgId">
							</select></td>
							
						</tr>
						
						<tr>
							<th width="17%"><span class="label">Mold Name</span></th>
							<td><div id="mold"></div>
							</td>
							<th width="17%"><span class="label">Mold No</span> </th>
							<td><div id="moldNo"></div> </td>
							
						</tr>
						
						<tr>
							<th width="17%"><span class="label">SNP</span><span
								class="textred">*</span></th>
							<td><input type="text" id="snp_wip"
								class="styleFontBold posInt" /></td>
							<th width="17%"><span class="label">Qty</span><span
								class="textred">*</span></th>
							<td><input type="text" id="qty" class="styleFontBold posInt" />
							</td>
						</tr>
						<tr>
							<th width="17%"><span class="label">Label Type</span><span
								class="textred">*</span></th>
							<td><input type="radio"
								name="labelType" checked="checked" value="N"
								id="NormalLabelType" />Normal &nbsp; <input type="radio"
								name="labelType" value="S" id="SpecialLabelType" />Special
							</td>
							<th width="17%"><span class="label">Print QTY remain</span></th>
							<td><input type="text" id="printQtyRemain" class="styleFontBold" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<th width="17%"><span class="label">Vendor Code</span><span
								class="textred">*</span></th>
							<td><input type="text" id="vendorcode" class="styleFontBold" />
							</td>
							<th width="17%"><span class="label">Vendor FG No.</span><span
								class="textred">*</span></th>
							<td><input type="text" id="vendorFgNo"
								class="styleFontBold" /></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<div align="left">For Vendor code and Vendor Partno is
						mandatory only Label type = "Special"</div>
				</td>
				<td>
					<div align="right">
						<input name="btnClear" type="button" 
							id="btnClear" value="Clear" />
						<input name="btnPreview" type="button" class="submit_button"
							id="searchBtn" value="Preview" />
					</div>
				</td>
			</tr>
		</table>
		<br />
		<div id="itemInformationGrid" class="grid-detail"
			style="width: 99.8%; height: 180px; overflow: hidden; outline: 0px none; position: relative;"></div>
		<br />
		<div align="right">
			<input name="btnSave" type="button" class="submit_button"
				id="btnSavePrint" value="Save And Print" />
		</div>
		<br />
		
		<div id="printDialog" title="Printer" >
			<div class="grid-header">
				<label>Select Printer</label> 
			</div>
			<div>
				<div align="center" class="grid-detail">
					<table width="100%">
				<tr>
					<td align="right" width="30%"><label>Printer</label> &nbsp;&nbsp;&nbsp; </td>
					<td align="left" width="70%">
						<select id="cboPrinter"></select>
					</td>
				</tr>
				</table>
				</div>
				<br/>
				<div align="right">
						<input name="btnPrintDialog" type="button" class="submit_button"
							id="btnPrintDialog" value="Print" />
						<input name="btnCancelDialog" type="button" class="submit_button"
							id="btnCancelDialog" value="Cancel" />
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>
