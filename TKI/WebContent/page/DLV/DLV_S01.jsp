<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
	<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">

var dlvS01Form;
var btnSearch;
var boxCustomer;

	//initialze script run
	$(document).ready(function() {  
		
		// <!--check search button -->
		btnSearch = $("#btnSearch");
		dlvS01Form = $("#dlvS01Form");
		boxCustomer = $("select#boxCustomer");
		
		btnSearch.click(function(){
			var errors = [];
			if( boxCustomer.attr("selectedIndex")  === 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["Customer"]});
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
			message.clear();
			dlvS01Form.attr("action","DLV_S01_search.html");
			dlvS01Form.find("select[id=pageNumber]").attr("disabled", true);
			/* dlvS01Form.submit(); */
			document.getElementById("dlvS01Form").submit();
		});	
	});
	
	function copyPlan(deliveryPlanId,revision){
		if( !confirm("<spring:message code='cfm.dlv.001'/>".replace(/\{0\}/g, revision)) )
			return;
		
		var params = ["deliveryPlanId="+deliveryPlanId,"revision="+revision];
		dlvS01Form.attr("action","DLV_S01_copy.html?"+params.join("&"));
		dlvS01Form.find("select[id=pageNumber]").attr("disabled", true);
		/* dlvS01Form.submit(); */
		document.getElementById("dlvS01Form").submit();
	}

	function deleteByPlan(deliveryPlanId,revision){
		if( !confirm("<spring:message code='cfm.dlv.002'/>".replace(/\{0\}/g, revision)) )
			return;
		
		var params = ["deliveryPlanId="+deliveryPlanId];
		dlvS01Form.attr("action","DLV_S01_deleteByPlan.html?"+params.join("&"));
		dlvS01Form.find("select[id=pageNumber]").attr("disabled", true);
		/* dlvS01Form.submit(); */
		document.getElementById("dlvS01Form").submit();
	}
</script>
</head>
<body>
	<form:form method="post" id="dlvS01Form" commandName="deliveryPlan" action="DLV_S01_search.html">
		<h1><spring:message code='menu.DeliveryPlanEntry'/></h1>
	
		<ul id="navlist">
			<li>
				<a href="DLV_S01.html" id="current">Delivery Plan Search/List</a>
				<a href="DLV_S03.html"> Daily Customer Delivery</a>
			</li>
		</ul>

		<page:message item="${deliveryPlan}"/>
	
		<table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
	        <tr>
				<th width="12%">Customer <span class="textred">*</span></th>
				<td width="34%"><form:select path="customerId" id="boxCustomer" items="${customerMap}"></form:select></td>
				<th width="16%"><span class="label">Delivery Month</span></th>
				<td width="38%">
					<form:select path="year" id="year" items="${yearMap}"></form:select>
					<form:select path="month" id="month" items="${monthMap}"></form:select>
				</td>
	        </tr>
	    </table>
	    <br/>
	    <div align="right">
			<input  name="btnSearch" id="btnSearch" type="button" value="Search" style="width:100px"/>
	    </div>
	    <br/>
		<c:if test="${not empty deliveryPlan.planList}">
			<table width="100%" border="1" align="center" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content" id="dynamictbl2">
				<tr>
					<td colspan="6">
						<div style="float: left"><page:display item="${deliveryPlan}" /></div>
						<div style="float: right"><page:number item="${deliveryPlan}" /></div>
					</td>
				</tr>
				<tr>
					<th align="center" width="25%">Customer</th>
					<th align="center" width="15%">Month</th>
					<th align="center" width="15%">Revise</th>
					<th align="center">Last Update Date</th>
					<th align="center">Last Update By</th>
					<th align="center">Action</th>
				</tr>
				<c:forEach items="${deliveryPlan.planList}" var="plan" varStatus="status" begin="0" step="1">
					<tr>
						<td align="center">${plan.customerName}&nbsp;</td>
						<td align="center">${monthMap[deliveryPlan.month]} ${deliveryPlan.year}</td>
						<td align="center">${plan.revision}</td>
						<td align="center"><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${plan.lastUpdate}"/></td>
						<td align="center">${plan.updateBy}</td>
						<td align="center">
							<div align="left" style="width:60px">
								<img src="image/icon/page_find.gif" alt="View" width="16" height="16" border="0" 
									onclick="location.href='DLV_S02.html?customerId='+ $('select#boxCustomer option:selected').val() 
			      						+'&customerName='+ $('select#boxCustomer option:selected').text() 
			      						+'&month='+ $('select#month option:selected').val() 
			      						+'&revision='+ ${plan.revision} 
			      						+'&deliveryPlanId='+ ${plan.deliveryPlanId} 
			      						+'&year='+ $('select#year option:selected').val(); "
			      				/>
								<img src="image/icon/dhtmlgoodies_sheet.gif" width="16" height="16"	border="0" onclick="copyPlan(${plan.deliveryPlanId},${plan.revision})"/>
								<security:authorize ifAnyGranted="DLV_S01_DELETE">
								<c:if test="${status.index == 0}">
									<img src="image/icon/delete.gif" border="0" onclick="deleteByPlan(${plan.deliveryPlanId},${plan.revision})"/>
								</c:if>
								</security:authorize>
							</div>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="6">
						<div style="float: left"><page:display item="${deliveryPlan}" /></div>
						<div style="float: right"><page:number item="${deliveryPlan}" /></div>
					</td>
				</tr>
			</table>
			<img src="image/icon/page_find.gif" alt="View" width="16" height="16" border="0" />  View Detail
			<img src="image/icon/dhtmlgoodies_sheet.gif" width="16" height="16" border="0" /> Copy to new revise
		</c:if>
		<c:if test="${empty deliveryPlan.planList && not empty deliveryPlan.customerId}">
			<table width="100%" border="0" align="center" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">				
				<tr>
					<th align="center" width="25%">Customer</th>
					<th align="center" width="15%">Month</th>
					<th align="center" width="15%">Revise</th>
					<th align="center">Last Update Date</th>
					<th align="center">Last Update By</th>
					<th align="center">Action</th>
				</tr>
				<tr>
					<td colspan="6" align="center">
						No Delivery Plan Found. Please create new delivery plan first.
					</td>
				</tr>
				<tr>
					<td colspan="6">
						<div align="center">
      						<input  name="btnCreate" id="btnCreate" type="button" value="Create New Delivery Plan" 
      						onclick="location.href='DLV_S02.html?customerId='+ $('select#boxCustomer option:selected').val() 
      						+'&customerName='+ $('select#boxCustomer option:selected').text() 
      						+'&month='+ $('select#month option:selected').val()
      						+'&year='+ $('select#year option:selected').val()
      						+'&revision=0'
	      					+'&deliveryPlanId='
      						; "/>
   	 					</div>
					</td>
				</tr>
			</table>
		</c:if>
	</form:form>
</body>
</html>
