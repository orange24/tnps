<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">

var btnSearch;
var mldS03Form;
var customerIdSel;
var partNoSel;
var moldNameSel;
var moldNoSel;
var startDateBox;
var endDateBox;

$(document).ready(function() {
	
	btnSearch 	  = $("#btnSearch");
	mldS03Form 	  = $("#mldS03Form");
	customerIdSel = $("#customerIdSel");
	partNoSel 	  = $("#partNoSel");
	moldNameSel   = $("#moldNameSel");
	moldNoSel 	  = $("#moldNoSel");
	startDateBox  = $("#startDateBox");
	endDateBox 	  = $("#endDateBox");
	
	customerIdSel.change(function(){
		var params = {
			"customerId" : customerIdSel.val()
		};
		getJSON("boxPartAll",params,function(result){
			partNoSel.empty();
			$.each(result,function(val, text){
				partNoSel.append( $("<option></option>").val(val).html(text) );
			});
		});
		getJSON("boxMoldNameAll",params,function(result){
			moldNameSel.empty();
			$.each(result,function(val, text){
				moldNameSel.append( $("<option></option>").val(val).html(text) );
			});
		});
 		getJSON("txtMoldNo",params,function(result){
 			moldNoSel.empty();
 			$.each(result,function(val, text){
 				moldNoSel.append( $("<option></option>").val(val).html(text) );
 			});
 		});
		
	});
	
	partNoSel.change(function(){
		var params = {
				"customerId" : customerIdSel.val(),
				"partId" : $(this).val(),
				"moldId" : moldNameSel.val()
		};
		/*getJSON("boxMoldNameAll",params,function(result){
			moldNameSel.empty();
			$.each(result,function(val, text){
				moldNameSel.append( $("<option></option>").val(val).html(text) );
			});
		});
		getJSON("txtMoldNo",params,function(result){
			moldNoSel.empty();
			$.each(result,function(val, text){
				moldNoSel.append( $("<option></option>").val(val).html(text) );
			});
		});*/
	});
	
	moldNameSel.change(function(){
		var params = {
				"customerId" : customerIdSel.val(),
				"partId" : partNoSel.val(),
				"moldId" : $(this).val()
		};
		getJSON("boxPartAll",params,function(result){
			partNoSel.empty();
			$.each(result,function(val, text){
				partNoSel.append( $("<option></option>").val(val).html(text) );
			});
		});
		getJSON("txtMoldNo",params,function(result){
			moldNoSel.empty();
			$.each(result,function(val, text){
				moldNoSel.append( $("<option></option>").val(val).html(text) );
			});
		});
	});
	
	btnSearch.click(function(){
		var msg = [];
		/*if (customerIdSel.val() == -2147483648) {
			msg.push({"code": "err.cmm.001", "arguments": ["Customer",null]});
		}
		if (moldNameSel.val() == -2147483648) {
			msg.push({"code": "err.cmm.001", "arguments": ["Mold Name",null]});
		}
		if (moldNoSel.val() == "") {
			msg.push({"code": "err.cmm.001", "arguments": ["Mold No.",null]});
		}*/
		if (startDateBox.val() == "" && endDateBox.val() == "") {
			msg.push({"code": "err.cmm.001", "arguments": ["History From - To",null]});
		}
		if( startDateBox.val() != "" && endDateBox.val() != "" && startDateBox.datepicker("getDate") > endDateBox.datepicker("getDate") ) {
			msg.push({"code": "err.cmm.008", "arguments": ["History (To)","History (From)"]});
		}
		if (msg.length > 0) {
			message.setErrors(msg);
			return;
		}
		
		mldS03Form.attr("action","MLD_S03_search.html");
		mldS03Form.submit();
	});
});	
</script>
</head>
<body>
	<form:form method="post" action="MLD_S03_search.html" id="mldS03Form" commandName="moldHist" >
		<h1><spring:message code='menu.MoldMaster'/></h1>
		<div id="navcontainer">
		  	<ul id="navlist">
			    <!-- CSS Tabs -->
			    <li><a href="MLD_S01.html" >Mold Search/List</a></li>
			    <security:authorize ifAnyGranted="MLD_S02_ADD_EDIT"><li><a href="MLD_S02.html" >Mold Add/Edit</a></li></security:authorize>
			    <li><a href="MLD_S03.html" id="current">Mold History Search/List</a></li>
		  	</ul>
		</div>
		<page:message item="${moldHist}" />
		<br/>
		<table width="100%" border="0" cellpadding="3" cellspacing="1">
	  	<tr>
	    	<td >
		   		<table class="ui-widget ui-widget-content" cellpadding="3" cellspacing="1" width="100%" border="0">
		      		<tr>
				        <th width="19%">Customer</th>
				        <td ><form:select path="customerId" items="${custMap}" id="customerIdSel" ></form:select> </td>
				        <th >Mold Name</th>
				        <td ><form:select path="moldId" items="${moldName}" id="moldNameSel" /></td>
			      	</tr>
		      		<tr>
				        <th >Mold No.</th>
				        <td ><form:select path="moldNo" items="${moldNo}" id="moldNoSel" /></td>
				        <th width="17%">Part Name : Part No</th>
				        <td width="36%"><form:select path="partId" items="${partMap}" id="partNoSel" /></td>
		      		</tr>
		      		<tr>
				        <th >History From - To <span class="textred">*</span></th>
				        <td >
				        	<form:input path="startDateHist" cssClass="date" id="startDateBox" /> - 
				        	<form:input path="endDateHist" cssClass="date" id="endDateBox" />
				        </td>
				        <th >&nbsp;</th>
				        <td >&nbsp;</td>
		      		</tr>
		   		</table>
	    	</td>
	  	</tr>
	  	<tr>
		    <td width="100%" align="right">
		      <input type="button" value="Search" style="width:100px" id="btnSearch" />
		    </td>
	  	</tr>
	</table>
	<br />
		<c:if test="${fn:length(moldHist.tMoldHistList) > 0 }">
			<table cellpadding="3" cellspacing="1" border="0" width="100%" class="tableBorder2">
				<tr>
					<td>
						<table width="100%" border="1" align="center" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content " >
				        	<tr>
				    			<td colspan="16">
				  					<div style="float:left" ><page:display item="${moldHist}"/></div>
									<div style="float:right"><page:number item="${moldHist}"/></div>
				  				</td>
							</tr>
				      		<tr >
						        <th width="5%" align="center" >No.</th>
						        <th width="10%" align="center" >Customer</th>
						        <th width="15%" align="center" >Mold Name</th>
						        <th width="10%" align="center" >Mold No.</th>
						        <th width="30%" align="center" >Part</th>
						        <th width="10%" align="center" >Report Date</th>
						        <th width="10%" align="center" >DC Shot</th>
						        <th width="10%" align="center" >FG Sold Shot</th>
						  	</tr>
							<c:set var="totaldc" value="0" ></c:set>
							<c:set var="totalfg" value="0" ></c:set>
							<c:forEach items="${moldHist.tMoldHistList}" varStatus="status" var="moldHistory" begin="0" step="1">
								<tr>
									<td align="center" ><page:rowno item="${moldHist}" index="${status.index}"/></td>
									<td align="left" >${moldHistory.customerCode}</td>
									<td align="left" >${moldHistory.moldName}</td>
									<td align="left" >${moldHistory.moldNo}</td>
									<td align="left" >${moldHistory.partName} : ${moldHistory.partNo}</td>
									<td align="center" ><fmt:formatDate value="${moldHistory.reportDate }" pattern="dd/MM/yyyy" /></td>
									<td align="right" ><fmt:formatNumber pattern="#,##0" value="${moldHistory.totalDCShot }" ></fmt:formatNumber></td>
									<td align="right" ><fmt:formatNumber pattern="#,##0" value="${moldHistory.totalFGSold }" ></fmt:formatNumber></td>
								</tr>
							</c:forEach>
				     		<tr>
								<td colspan="16">
									<div style="float:left" ><page:display item="${moldHist}"/></div>
									<div style="float:right"><page:number item="${moldHist}"/></div>
								</td>
					    	</tr>
						</table>
					</td>
				</tr>
			</table>
		</c:if>
	</form:form>
</body>
</html>
