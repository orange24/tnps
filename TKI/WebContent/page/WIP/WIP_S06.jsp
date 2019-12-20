<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
	<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var wipStockForm;
	var boxCustomer;
	var boxPartNo;
	var boxWIPCode;
	var btnExport;
	var btnSearch;
        var reportDateFr;
	var reportDateTo;

	$(document).ready(function(){
		wipStockForm = $("form#wipStockForm");
		boxCustomer  = $("select#boxCustom");
		boxPartNo    = $("select#boxPartNo");
		boxWIPCode   = $("select#boxWIP06");
		btnExport   = $("#btnExport");
		btnSearch   = $("#btnSearch");
		
		
        reportDateFr = $("input#reportDateFr");
		reportDateTo = $("input#reportDateTo");
		
		btnExport.click(function(){
			message.clear();
			wipStockForm.attr("action","WIP_S06_export.html");
			wipStockForm.submit();
		});
		
		btnSearch.click(function(){
			message.clear();
			wipStockForm.attr("action","WIP_S06_search.html");
			wipStockForm.submit();
		});
		

		boxCustomer.change(initPartNo);
		boxPartNo.change(selectWIP);
		boxCustomer.change(selectWIP);
	});

	function initPartNo() {
		if( !boxPartNo || !boxPartNo.exists() )
			return;
		
		var params = { "customerId": "", "wip": "" };
		if( boxCustomer.val() ) params.customerId = boxCustomer.val();
		getJSON("boxPartNameNoAll",params,function(result){
			boxPartNo.empty();
			$.each(result,function(val, text){
				boxPartNo.append( $("<option></option>").val(val).html(text) );
			});
		});
	}
	function selectWIP() {
		boxWIPCode.empty();
		var params = { "customerId": "", "partId": "" };
		if( boxCustomer.val() ) params.customerId = boxCustomer.val();
		if( boxPartNo.val() )params.partId        = boxPartNo.val();
		getJSON("boxWIP06",params,function(result){
			boxWIPCode.empty();
			$.each(result,function(val, text){
				boxWIPCode.append( $("<option></option>").val(val).html(text) );
			});
		});
	}
</script>
</head>
<body>
	<h1 class="header"><spring:message code='menu.WIPStockStatus2'/></h1>
	<page:message item="${wip}" />

	<form:form id="wipStockForm" method="post" action="WIP_S06_search.html" commandName="wip">
		<table width="100%" border="0" cellpadding="3" cellspacing="1">
			<tr>
		    	<td>
		    		<table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
		        		<tr>
		          			<th width="12%"><span class="label">Customer </span></th>
		          			<td width="34%"><form:select path="customerId" id="boxCustom" items="${customerMap}"></form:select></td>
		          			<th width="16%"><span class="label">Part</span></th>
		          			<td width="38%"><form:select path="partId" id="boxPartNo" items="${partMap}"></form:select></td>
		        		</tr>
		        		<tr>
			          		<th><span class="label"> WIP</span></th>
			          		<td><form:select path="wip" id="boxWIP06" items="${wipMap}" disabled="true"></form:select></td>
			          		<th>Report Date (From - To) <span class="textred">*</span></th>
			          		<td>
								<form:input path="reportDateFr" cssClass="date" title="Report Date (From)" /> 
								- 
								<form:input path="reportDateTo" cssClass="date" title="Report Date (To)" />
			          		</td>
		        		</tr>
		    		</table>
		    	</td>
		  	</tr>
		  	<tr>
		    	<td align="right">
		    		<security:authorize ifAnyGranted="WIP_R06_EXPORT">
		    			<input type="button" id="btnExport" value="Summary Report"  />
		    		</security:authorize>
		    		<input type="button" id="btnSearch" value="Search"  />
		   		</td>
		  	</tr>
		</table>
	</form:form>
<br />

	<c:if test="${not empty wip.fgMap}">
		<table width="100%" border="1" align="left" cellpadding="3" cellspacing="0" bordercolor="#000000" class="ui-widget ui-widget-content" >
			<tr >
			  <th rowspan="2" align="center" class="submit_button" >Customer</th>
			  <th rowspan="2" align="center" class="submit_button" >Part No </th>
			  <th rowspan="2" align="center" class="submit_button" >Part Name </th>
			  <th rowspan="2" align="center" class="submit_button" >Process</th>
			  <c:forEach var="dd" items="${wip.fgMapKey}" varStatus="status1" begin="0" step="1">
			  <th colspan="3" align="center" class="submit_button" ><fmt:formatDate pattern="dd/MM/yyyy" value="${dd.value[0].reportDate}" /></th>
			  </c:forEach>
		  </tr>
			<tr >
				<c:forEach var="wipList" items="${wip.fgMapKey}" varStatus="status1" begin="0" step="1">
				<th align="center" width="45" class="submit_button" >Stock<br />
				  (After)</th>
			    <th align="center" width="10" class="submit_button">Pending</th>
			    <th align="center" width="45" class="submit_button">Total</th>
				</c:forEach>
			</tr>
			
		  	  <c:set var="ggg" scope="page" value="${0}"/>		 			  			
              
			  <c:forEach var="fgMap" items="${wip.fgMapDay}" varStatus="loop" begin="0" step="${fn:length(wip.fgMapKey)}">
			  		 			  
			  <tr >
			  
			  <c:if test="${fgMap.value[0].wiporder == 1}">
			  
			  <c:set var="rowspan" scope="page" value="${fgMap.value[0].countProcess}"/>
			   			  	 		  
			  <td rowspan="${rowspan + 3}" align="center" >${fgMap.value[0].customer}</td>
			  <td rowspan="${rowspan + 3}" align="center" >${fgMap.value[0].partno}</td>
			  <td rowspan="${rowspan + 3}" align="center" >${fgMap.value[0].partname}</td>
              </c:if>
			  <td align="left" >${fgMap.value[0].wiporder}. ${fgMap.value[0].wip}</td>
			  
			  <c:set var="keyMap" scope="page" value="${wip.fgMapDay}"/>
			  
			  <c:forEach var="wip" items="${wip.fgMapKey}" varStatus="status1" begin="0" step="1">
			  
			  <c:set var="keyData" scope="page" 
			  value="${fgMap.value[0].customerid}${fgMap.value[0].partid}${fgMap.value[0].wip}${fgMap.value[0].wiporder}${wip.value[0].reportDate}"/>
			  			 
			 <c:forEach var="ww" items="${keyMap.get(keyData)}" varStatus="ss" begin="0" step="1">
			  <td align="right"><fmt:formatNumber pattern="#,##0" value="${ww.stockAfterQty}"/></td>
			  <td align="right"><fmt:formatNumber pattern="#,##0" value="${ww.preWipPdQty}"/></td>
			  <td align="right"><fmt:formatNumber pattern="#,##0" value="${ww.totals}"/></td>
			 </c:forEach>
			  </c:forEach>
			  </tr>
		  				  		  				   		  				  			  
		  					  
			  <c:if test="${fgMap.value[0].wiporder == fgMap.value[0].countProcess}">
			  <tr >
			  <td align="left" ><strong>WIP Total</strong></td>	
			  	  
			  <c:set var="keyMap" scope="page" value="${wip.fgMapDay}"/>
			  
			  <c:forEach var="wip" items="${wip.fgMapKey}" varStatus="status1" begin="0" step="1">
			  
			  <c:set var="keyData" scope="page" 
			  value="${fgMap.value[0].customerid}${fgMap.value[0].partid}${fgMap.value[0].wip}${fgMap.value[0].wiporder}${wip.value[0].reportDate}"/>
			  			 
			 <c:forEach var="ww" items="${keyMap.get(keyData)}" varStatus="ss" begin="0" step="1">
			  <td align="right"><fmt:formatNumber pattern="#,##0" value="${ww.sumWip}"/></td>
			  <td align="right"><fmt:formatNumber pattern="#,##0" value="${ww.sumPending}"/></td>
			  <td align="right"><fmt:formatNumber pattern="#,##0" value="${ww.sumTotal}"/></td>
			 </c:forEach>
			  </c:forEach>
			  </tr>
			  
			  <td align="left" ><strong>FG Stock</strong></td>
			  
			  <c:set var="keyMap" scope="page" value="${wip.fgMapDay}"/>
			  
			  <c:forEach var="wip" items="${wip.fgMapKey}" varStatus="status1" begin="0" step="1">
			  
			  <c:set var="keyData" scope="page" 
			  value="${fgMap.value[0].customerid}${fgMap.value[0].partid}${fgMap.value[0].wip}${fgMap.value[0].wiporder}${wip.value[0].reportDate}"/>
			  			 
			 <c:forEach var="ww" items="${keyMap.get(keyData)}" varStatus="ss" begin="0" step="1">
			  <td align="right" bgcolor="#DDDDFF" colspan="3"><strong><fmt:formatNumber pattern="#,##0" value="${ww.fgBl}"/></strong></td>
			 </c:forEach>
			 
			  </c:forEach>
			  </tr>
			  
			  
			    <td align="left" ><strong>Grand Total</strong></td>
			  
			      <c:set var="keyMap" scope="page" value="${wip.fgMapDay}"/>
			  
			  <c:forEach var="wip" items="${wip.fgMapKey}" varStatus="status1" begin="0" step="1">
			  
			  <c:set var="keyData" scope="page" 
			  value="${fgMap.value[0].customerid}${fgMap.value[0].partid}${fgMap.value[0].wip}${fgMap.value[0].wiporder}${wip.value[0].reportDate}"/>
			  			 
			 <c:forEach var="ww" items="${keyMap.get(keyData)}" varStatus="ss" begin="0" step="1">
			  <td align="right" bgcolor="#DDDDFF" colspan="3"><strong><fmt:formatNumber pattern="#,##0" value="${ww.grandTotal}"/></strong></td>
			 </c:forEach>
			 
			  </c:forEach>
			  </tr>
 
			  </c:if>
			 </c:forEach>
	  </table>
	</c:if>
</body>
</html>
