<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">

	var wipS05Form;
	var junkQtyMin;
	var junkQtyMax;
	var amountFr;
	var amountTo;
	var stockQtyMin;
	var stockQtyMax;
	var btnSearch;
	var btnClear;
	
	$(document).ready(function() {

		wipS05Form = $("form#wipS05Form");
		junkQtyMin 	= $("#junkQtyMin");
		junkQtyMax 	= $("#junkQtyMax");
		amountFr 	= $("#amountFr");
		amountTo 	= $("#amountTo");
		stockQtyMin	= $("#stockQtyMin");
		stockQtyMax = $("#stockQtyMax");
		btnSearch 	= $("#btnSearch");
		btnClear 	= $("#btnClear");
	
		btnSearch.click(function(){
			if(validateCriteria()){
				downloadNotify($("<div title='<spring:message code='waitingAlertTitle'/>'><spring:message code='waitingAlertContent'/></div>"));
				wipS05Form.attr("action","WIP_S05_search.html");
				wipS05Form.submit();
			}
		});
		
		btnClear.click(function(){
			var checkLength = $("[.junkAll]:checked").length;
			if(checkLength > 0){
				if(validateCriteria()){
					if (confirm("<spring:message code='cfm.cmm.001'/>")) {
						wipS05Form.attr("action","WIP_S05_adjust.html");
						wipS05Form.submit();
					}
				}
			}else{
				message.setErrors([{"code":"err.cmm.013"}]);
				return false;
			}
		});
		
		function validateCriteria(){
			var errors = [];
			message.clear();
			//validate criteria

			if(parseInt(junkQtyMax.val()) < parseInt(junkQtyMin.val())){
				errors.push({"code":"err.cmm.016","arguments":["Junk Qty From","Junk Qty To"]});
			}
			if(parseFloat(amountTo.val()) < parseFloat(amountFr.val())){
				errors.push({"code":"err.cmm.016","arguments":["Amount From","Amount To"]});
			}
			if(parseInt(stockQtyMax.val()) < parseInt(stockQtyMin.val())){
				errors.push({"code":"err.cmm.016","arguments":["Stock Qty From","Stock Qty To"]});
			}
			
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}else{
				return true;
			}
		}
	});
	
	function jqCheckAll(id, name){
		$("." + name).attr('checked', $('#' + id).is(':checked'));
	}
</script>
</head>
<body>
	<h1><spring:message code='menu.WIPJunkAdjustment'/></h1>

	<page:message item="${searchCriteria}" />
	
	<form:form id="wipS05Form" method="post" action="WIP_S05_search.html" commandName="searchCriteria">
		<table width="100%" border="0" cellspacing="1">
		  	<tr>
			    <td colspan="2">
				    <table class="ui-widget ui-widget-content " cellspacing="1" width="100%" border="0">
				        <tr>
							<th width="15%">Customer</th>
							<td width="35%">
								<form:select path="customerId" id="boxCustom" items="${customerMap}" tabindex="1" />
							</td>
							<th>WIP</th>
							<td>
								<form:select path="wip" id="boxWIP" items="${wipMap}" tabindex="2" />
							</td>
				        </tr>
				        <tr>
							<th>Part No</th>
							<td>
								<form:input path="partNo" tabindex="3" />
							</td>
							<th>Part Name</th>
							<td>
								<form:input path="partName" size="80" tabindex="4" />
							</td>
				        </tr>
				        <tr>
				        	<th>Work Order No</th>
				        	<td><form:input path="workOrderNo" maxlength="11" tabindex="5" /></td>
				        	<th>Lot No</th>
				        	<td><form:input path="lotNo" maxlength="3" size="5" tabindex="6" /></td>
				        </tr>
				        <tr>
							<th>Junk Qty</th>
							<td> 
							  	<form:input path="junkQtyMin" id="junkQtyMin" onkeypress="return IntegerFilter(event)" tabindex="7" />
							  	-
							  	<form:input path="junkQtyMax" id="junkQtyMax" onkeypress="return IntegerFilter(event)" tabindex="8" />
							</td>
							<th>Amount</th>
				          	<td>
				          		<form:input path="amountFr" id="amountFr" onkeypress="return DoubleFilter(event)" tabindex="9" />
				          		-
				          		<form:input path="amountTo" id="amountTo" onkeypress="return DoubleFilter(event)" tabindex="10" /> THB
							</td>
				        </tr>
				        <tr>
							<th>Stock Qty</th>
							<td> 
							  	<form:input path="stockQtyMin" id="stockQtyMin" onkeypress="return IntegerFilter(event)" tabindex="11" />
							  	-
							  	<form:input path="stockQtyMax" id="stockQtyMax" onkeypress="return IntegerFilter(event)" tabindex="12" />
							</td>
							<th>Stock Month</th>
							<td>
								<form:select path="monthYear" id="boxMonth" items="${monthMap}" tabindex="13" />
							</td>
				        </tr>
				    </table>
				</td>
			</tr>
			<tr>
			  	<td colspan="2" align="right">
					<input class="submit_button" name="btnSearch" id="btnSearch" type="button" value="Search" style="width:100px"/>
			  	</td>
			</tr>
		</table>
		<br />
		<c:if test="${fn:length(searchCriteria.junkList) > 0}">
			<table cellpadding="3" cellspacing="1" border="1" width="100%" class="ui-widget ui-widget-content" >
				<tr>
					<td colspan="11">
						<div style="float:left" ><page:display item="${searchCriteria}"/></div>
						<div style="float:right"><page:number item="${searchCriteria}"/></div>
					</td>
				</tr>
				<tr>
				   	<th width="3%">
				   		<input type="checkbox" name="chkAll" id="chkAll" onclick="jqCheckAll( this.id, 'junkAll' )" />
				   	</th>
					<th>Customer</th>
				   	<th width="10%">WIP</th>
					<th width="20%">Part</th>
					<th>Lot No</th>
					<th>Last Report Date</th>
					<th>Junk Qty</th>
					<th>Amount</th>
				</tr>
				<c:forEach items="${searchCriteria.junkList}" var="junk" varStatus="status" begin="0" step="1">
					<tr id="${status.index}">
						<td align="center">
							<c:choose>
								<c:when test="${1 == junk.isEnable}">
									<input type="checkbox" name="junkList[${status.index}].isChecked" 
										id="junkList[${status.index}].isChecked" class="junkAll"/>
								</c:when>
								<c:otherwise>
									<input type="checkbox" name="junkList[${status.index}].isChecked" 
										id="junkList[${status.index}].isChecked" disabled="disabled"/>
								</c:otherwise>
							</c:choose>
						</td>
						<td align="center">
							${junk.customerCode}&nbsp;
							<input type="hidden" name="junkList[${status.index}].customerId" 
								id="junkList[${status.index}].customerId" value="${junk.customerId}" />
						</td>
						<td align="center">
							${junk.wip}&nbsp;
							<input type="hidden" name="junkList[${status.index}].wip" 
								id="junkList[${status.index}].wip" value="${junk.wip}" />
						</td>
						<td>
							${junk.partNo} : ${junk.partName}
							<input type="hidden" name="junkList[${status.index}].partId" 
								id="junkList[${status.index}].partId" value="${junk.partId}" />
						</td>
						<td align="center">
							${junk.workOrderNo} ${junk.lotNo}&nbsp;
							&nbsp;
							<input type="hidden" name="junkList[${status.index}].workOrderNo" 
								id="junkList[${status.index}].workOrderNo" value="${junk.workOrderNo}"/>
							<input type="hidden" name="junkList[${status.index}].lotNo" 
								id="junkList[${status.index}].lotNo" value="${junk.lotNo}"/>
						</td>
						<td align="center">
							<fmt:formatDate value="${junk.lastReportDate}" pattern="dd/MM/yyyy"/>
							<input type="hidden" name="junkList[${status.index}].lastReportDate" 
								id="junkList[${status.index}].lastReportDate" value="${junk.lastReportDate}"/>
						</td>
						<td align="right">
							<fmt:formatNumber value="${junk.junkQty}" pattern="#,##0" />&nbsp;
							<input type="hidden" name="junkList[${status.index}].junkQty" 
								id="junkList[${status.index}].junkQty" value="${junk.junkQty}"/>
						</td>
						<td align="right">
							<fmt:formatNumber value="${junk.amount}" pattern="#,##0.00" />&nbsp;
							<input type="hidden" name="junkList[${status.index}].amount" 
								id="junkList[${status.index}].amount" value="${junk.amount}"/>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="11">
						<div style="float:left" ><page:display item="${searchCriteria}"/></div>
						<div style="float:right"><page:number item="${searchCriteria}"/></div>
					</td>
				</tr>
			</table>
			<table width="100%">
	  			<tr>
	    			<td align="right">
	      				<input name="btnClear" type="button" id="btnClear" value="Clear Junk Data" />
					</td>
	  			</tr>
			</table>
		</c:if>
		1. WIP Junk data will be search for report type = 'Usual' only.<br/>
		2. After clear junk data, wip stock in screen WIP Stock Status will not be changed until run batch WIP stock by at the end of day.<br/>
		3. After clear junk data, history will be displayed in screen WIP Stock Adjustment (Tab history).<br/>
		4. After clear junk data, cannot cancel for this clearing transaction.<br/>
		5. WIP Junk Qty can be minus (-) value from actual total quantities in next process are more than actual OK quantities in previous process.<br/>
		6. For search number; WIP Junk Qty and WIP Stock Qty can input only integer number (ex; 123). But amount can input with decimal number (ex; 123.45)<br/>
	</form:form>
</body>
</html>