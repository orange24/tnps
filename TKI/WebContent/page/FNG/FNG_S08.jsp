<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script src="page/FNG/FNG_S08.js" language="javascript"></script>
<style>
#seqTbl tr.grp td { background: #e4e4f0; text-align: left; font-weight: bold; }
#seqTbl tr.sel td { background: #fff6cc; }
#seqTbl td { text-align: center; }
#mlTbl td { text-align: center; }
</style>
</head>
<body>
<h1 class="header"><spring:message code='menu.PrintMultiLotTag'/></h1>
<form:form id="fngForm" commandName="searchCriteria" method="POST" onsubmit="return false;">

	<page:message item="${bean}" />

	<form:hidden path="printerName" id="printerName"/>

	<table width="100%" border="0" cellpadding="3" cellspacing="1">
		<tr>
			<td colspan="2">
				<table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
					<tr>
						<th width="17%"><span class="label">Lot No.<span class="textred">*</span></span></th>
						<td width="35%"><input type="text" id="lotNo" class="ui-autocomplete-input" autocomplete="off" maxlength="14" /></td>
						<th width="17%"><span class="label">Process</span></th>
						<td><input type="text" id="wip" readonly="readonly" disabled="disabled" class="styleFontBold" /></td>
					</tr>
					<tr>
						<th><span class="label">Customer</span></th>
						<td><div id="customerName">&nbsp;</div></td>
						<th><span class="label">FG No : FG Name</span></th>
						<td><div id="fgName">&nbsp;</div></td>
					</tr>
					<tr>
						<th><span class="label">SNP</span><span class="textred">*</span></th>
						<td><input type="text" id="snp_wip" class="styleFontBold posInt" /></td>
						<th><span class="label">Qty</span><span class="textred">*</span></th>
						<td><input type="text" id="qty" class="styleFontBold posInt" /></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td><div align="left">Customer, FG and WIP of every lot must be the same as the first lot. Special label lots are not supported.</div></td>
			<td>
				<div align="right">
					<input type="button" id="btnClear" value="Clear" />
					<input type="button" class="submit_button" id="btnPreview" value="Preview" />
				</div>
			</td>
		</tr>
	</table>
	<br />
	<table width="100%" border="1" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content" id="seqTbl">
		<thead>
			<tr>
				<th width="8%">Print</th>
				<th>Lot Seq.</th>
				<th width="15%">Qty</th>
				<th width="20%">Print Status</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	<br />
	<div align="right">
		<span id="selCounter" class="text-bold">Selected 0/4 seq, total 0 pcs</span>
		&nbsp;&nbsp;
		<input type="button" class="submit_button" id="btnPrintSingle" value="Save And Print (one tag per seq)" />
		<input type="button" class="submit_button" id="btnPrintMulti" value="Print Together (QR tag)" />
	</div>
	<br />

	<table width="100%" border="0" cellpadding="3" cellspacing="1">
		<tr>
			<td>
				<table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
					<tr>
						<th width="17%"><span class="label">QR Tag No.</span></th>
						<td width="35%"><input type="text" id="refNo" maxlength="12" /></td>
						<td align="right">
							<input type="button" class="submit_button" id="btnFind" value="Search" />
							<input type="button" class="submit_button" id="btnReprint" value="Reprint" />
							<input type="button" class="submit_button" id="btnCancelTag" value="Cancel QR Tag" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width="100%" border="1" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content" id="mlTbl">
		<thead>
			<tr>
				<th>QR Tag No.</th>
				<th>Status</th>
				<th>Print Count</th>
				<th>Customer</th>
				<th>FG No.</th>
				<th>WIP</th>
				<th>Lot Seq.</th>
				<th>Qty</th>
				<th>DC</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>

	<div id="printDialog" title="Printer">
		<div class="grid-header"><label>Select Printer</label></div>
		<div>
			<div align="center" class="grid-detail">
				<table width="100%">
					<tr>
						<td align="right" width="30%"><label>Printer</label> &nbsp;&nbsp;&nbsp; </td>
						<td align="left" width="70%"><select id="cboPrinter"></select></td>
					</tr>
				</table>
			</div>
			<br/>
			<div align="right">
				<input type="button" class="submit_button" id="btnPrintDialog" value="Print" />
				<input type="button" class="submit_button" id="btnCancelDialog" value="Cancel" />
			</div>
		</div>
	</div>
</form:form>
</body>
</html>
