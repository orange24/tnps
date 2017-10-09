<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var frmPart;
	var boxCustomer;
	var boxWip;
	var txtPartNo;
	var txtPartName;
	var btnSearch;
	var btnSync;
	var chbPartAll;
	var chbParts;

	$(document).ready(function() {
		frmPart = $("#itmForm");
		boxCustomer = $("#customerId");
		boxWip = $("#wip");
		txtPartNo = $("#partNo");
		txtPartName = $("#partName");
		btnSearch = $("#btnSearch");
		btnSync = $("#btnSync");
		chbPartAll = $("#chbPartAll");
		chbParts = $("input[id=chbPart]");

		chbPartAll.click(function(){
			chbParts.attr("checked", chbPartAll.attr("checked"));
		});

		btnSync.click(function(){
			//validate
			if (!$("input[id=chbPart]:checked").exists()){
				message.setError("err.cmm.001", ["Sync Part"]);
				return false;
			}
			if (confirm("<spring:message code='cfm.cmm.004'/>")) {
				frmPart.attr("action", "ITM_S02_sync.html").submit();
			}
		});
	});
</script>
</head>
<body>
	<h1><spring:message code='menu.FG/PartMaster'/></h1>
	<ul id="navlist">
		<li><a href="ITM_S01.html" >FG/Part Search/List</a></li>
		<li><a href="ITM_S02.html" id="current">FG/Part Sync From TPics</a></li>
	</ul>
	
	<page:message item="${mPart}"/>
	
	<form:form id="itmForm" method="post" action="ITM_S02_search.html" commandName="mPart">
		<table class="ui-widget ui-widget-content" cellpadding="3" cellspacing="1" width="100%" border="0">
			<tr>
				<th width="13%">Customer (TPics)</th>
				<td width="87%" colspan="3">
			    	<form:select path="customerCode" id="customerId" cssStyle="width:180px;" tabindex="1">
						<option value="">-- All Customer --</option>
						<form:options items="${customerMap}" itemLabel="customerCode" itemValue="customerCode"/>
					</form:select>
			   	</td>
			</tr>
			<tr>
			   	<th>FG No. (TPics)</th>
				<td><form:input path="fgNo" id="fgNo" tabindex="3"/></td>
				<th width="13%">FG Name (TPics)</th>
			   	<td width="37%"><form:input path="fgName" id="fgName" tabindex="4"/></td>
			</tr>
			<tr>
			  	<th>Part No. (TPics)</th>
			  	<td><form:input path="partNo" id="partNo" tabindex="5"/></td>
				<th><div align="center">Part Name (TPics)</div></th>
				<td><form:input path="partName" id="partName" tabindex="6"/></td>
			</tr>
		</table>
	  	<br/>
	  	<security:authorize ifAnyGranted="ITM_S02_SEARCH">
			<div align="right">
	      		<input id="btnSearch" type="submit" class="submit_button" value="Search" tabindex="7"/>
	    	</div>
	  	</security:authorize>
	  	<br />
		<c:if test="${not empty mPart.partList}">
			<table id="dynamictbl" border="1" cellpadding="3" cellspacing="0" width="100%" class="ui-widget ui-widget-content">
				<tr>
				  	<td colspan="7">
				    	<div style="float:left"><page:display item="${mPart}"/></div>
						<div style="float:right"><page:number item="${mPart}"/></div>
				  	</td>
				</tr>
				<tr>
					<th><input type="checkbox" id="chbPartAll" tabindex="8"/></th>
					<th>Customer</th>
					<th>FG No.</th>
					<th>FG Name</th>
					<th>Part No.</th>
					<th>Part Name</th>
					<th>Process</th>
			   	</tr>
			   	<c:forEach items="${mPart.partList}" var="item" varStatus="itemStatus">
					<tr>
						<td align="center">
							<input type="checkbox" name="partList[${itemStatus.index}].choose" id="chbPart" value="1"/>
							<input type="hidden" name="partList[${itemStatus.index}].customerCode" value="${item.customerCode}"/>
							<input type="hidden" name="partList[${itemStatus.index}].customerName" value="${item.customerName}"/>
							<input type="hidden" name="partList[${itemStatus.index}].fgNo" value="${item.fgNo}"/>
							<input type="hidden" name="partList[${itemStatus.index}].fgName" value="${item.fgName}"/>
							<input type="hidden" name="partList[${itemStatus.index}].partNo" value="${item.partNo}"/>
							<input type="hidden" name="partList[${itemStatus.index}].partName" value="${item.partName}"/>
						</td>
						<td>${item.customerCode}</td>
						<td>${item.fgNo}</td>
						<td>${item.fgName}</td>
						<td>${item.partNo}</td>
						<td>${item.partName}</td>
						<td>
						  	<c:forEach items="${item.wipList}" var="wipRow" varStatus="wipStatus">
								${wipStatus.count}.&nbsp;${wipRow.wip.wip} : ${wipRow.wip.wipName}<br/>
								<input type="hidden" name="partList[${itemStatus.index}].wipList[${wipStatus.index}].wip.wip"      value="${wipRow.wip.wip}"/>
								<input type="hidden" name="partList[${itemStatus.index}].wipList[${wipStatus.index}].wip.wipName"  value="${wipRow.wip.wipName}"/>
								<input type="hidden" name="partList[${itemStatus.index}].wipList[${wipStatus.index}].wip.wipType"  value="${wipRow.wip.wipType}"/>
								<input type="hidden" name="partList[${itemStatus.index}].wipList[${wipStatus.index}].wip.wipGroup" value="${wipRow.wip.wipGroup}"/>
								<input type="hidden" name="partList[${itemStatus.index}].wipList[${wipStatus.index}].wip.wipOrder" value="${wipStatus.count}"/>
								<input type="hidden" name="partList[${itemStatus.index}].wipList[${wipStatus.index}].tpicsOrder"   value="${wipRow.tpicsOrder}"/>
							</c:forEach>
					  	</td>
					</tr>
				</c:forEach>
				<tr>
			  		<td colspan="7">
			    		<div style="float:left"><page:display item="${mPart}"/></div>
						<div style="float:right"><page:number item="${mPart}"/></div>
			       </td>
			 	</tr>
			</table>
			<br/>
			<security:authorize ifAnyGranted="ITM_S02_SYNC">
				<div align="left">
					<input name="btnSync" type="button" class="submit_button" id="btnSync" value="Sync Part"/>
				</div>
			</security:authorize>
		</c:if>
	</form:form>
</body>
</html>
