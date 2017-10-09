<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="../importResources.jsp" %>

<html>
<head>
	<script type="text/javascript" src="page/PRD/PRD_S02.js"></script>
</head>
<body>
<h1 class="header"><spring:message code='menu.DIECASTPlanEntry'/></h1>
<form:form id="prdS02Form" action="PRD_S02_search.html" methodParam="post" commandName="mProduction">
<table cellpadding="3" cellspacing="1" border="0" width="100%">
	<tr>
		<td >
			<div id="navcontainer">
				<ul id="navlist">
		        	<li><a href="PRD_S01.html">				DIE CAST Plan Entry	</a> </li>
		        	<li><a href="PRD_S02.html" id="current">DIE CAST Plan Search</a> </li>
		      	</ul>
		    </div>
	    </td>
	</tr>
</table>

<!-- Display error messages -->
<page:message item="${mProduction}" />

<!-- The form layout -->
<table width="100%" border="0" cellpadding="3" cellspacing="1">
  <tr>
    <td>
    	<table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
			<tr>
          		<th width="12%"><span class="label">Diecast Plan Date</span></th>
          		<td width="35%">
          			<form:input id="txbDieCastDateFrom" path="dcPlanDateFrom" cssClass="date"/> - <form:input id="txbDieCastDateTo" path="dcPlanDateTo" cssClass="date" />
          		</td>
          		
          		<th width="15%"><span class="label">WIP</span></th>
          		<td width="38%">
	          		<form:select id="cboWip" path="wip"  itemValue="wip" itemLabel="wip" items="${mProduction.wipList}"></form:select>
           		</td>
        	</tr>
        	
        	<tr>
	        	<th><span class="label">Machine</span></th>
	          	<td>
	          		<form:select id="cboMachine" path="machineId"  itemValue="machineId" itemLabel="machineNo" items="${mProduction.machineList}"></form:select>
				</td>
				
	        	<th><span class="label">Shift</span></th>
	          	<td>
	          		<form:select id="cboShift" path="shift" >
	          			<form:option value="">--All--</form:option>
	          			<form:option value="D">D</form:option>
	          			<form:option value="N">N</form:option>
	          		</form:select>
				</td>
	        </tr>
	        
        	<tr>
				<th><span class="label">Customer</span></th>
				<td>
					<form:select id="cboCustomer" path="customerId"  itemValue="customerId" itemLabel="customerCode" items="${mProduction.customerList}"></form:select>
          		</td>
          		
          		<th><span class="label">Remark</span></th>
	          	<td>
	          		<form:select id="cboRemark" path="reasonId"  itemValue="reasonId" itemLabel="reasonName" items="${mProduction.reasonList}"></form:select>
	          	</td>
	        </tr>
	        
        	<tr>
	        	<th><span class="label">Part No</span></th>
	          	<td><form:input id="txbPartNo" path="partNo"/></td>
	          	
	        	<th><span class="label">Part Name</span></th>
	          	<td><form:input id="txbPartName" path="partName"/></td>
	        </tr>
	        
			<tr colspan="2">
	        	<th><span class="label">Generate Lot</span></th>
	          	<td>
	          		<form:select id="cboGenerate" path="generateLot">
	          			<form:option value="">--All--</form:option>
	          			<form:option value="1">Yes</form:option>
	          			<form:option value="0">No</form:option>
	          		</form:select>
				</td>
	        </tr>
    	</table>
    	</td>
  	</tr>
</table>
<br />

<!-- Location for triggering events -->
<table width="100%" border="0" cellpadding="3" cellspacing="1">
	<tr>
		<td width="54%">
		<c:if test="${not empty mProduction.recordFound && mProduction.recordFound > 0}">
			<div style="float:left;font-size:11px;" ><page:display item="${mProduction}" /></div>
		</c:if>
		<div align="right">
			<span class="label">
			    <input id="btnExport" type="button" class="submit_button" value="Export Result"/>
			   	<input id="btnSearch" type="button" class="submit_button" value="Search" style="width:100px"/>
			</span>
		</div></td>
  	</tr>
</table>
<br />

<!-- Location for search result -->
<c:if test="${fn:length(mProduction.planList) >0}">
	<table width="100%" border="1" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
	    <tr>
	  		<td colspan="17">
	  			<div style="float:left" >${mProduction.recordFound} record(s) found.</div>
	  			<div style="float:right" ><page:number item="${mProduction}" /></div>
	  		</td>
	  	</tr>
	  	
	  	<tr>
			<th align="center">Plan Date	</th>
	       	<th align="center">WIP			</th>
	        <th align="center">Machine		</th>
	        <th align="center">SEQ			</th>
	        <th align="center">Shift		</th>
	        <th align="center">Customer		</th>
	        <th align="center">Part No		</th>
	        <th align="center">Part Name	</th>
	        <th align="center">Mold No	</th>
	        <th align="center">ST(sec)		</th>
	        <th align="center">QTY			</th>
	        <th align="center">SNP			</th>
	        <th align="center">Remark		</th>
	        <th align="center">Work Order No</th>
	        <th align="center">Start Lot No	</th>
	        <th align="center">End Lot No	</th>
	        <th align="center">Gen. Status	</th>
	        <th align="center">Gen. Date	</th>
	     </tr>
	  		
	  	 <c:forEach varStatus="status" var="object" items="${mProduction.planList}">
	  	 	<tr>
	  	 		<td>${object.dcPlanDate}</td>
	  	 		<td>${object.wip}</td>
	  	 		<td>${object.machineNo}</td>
	  	 		<td>${object.seq}</td>
	  	 		<td>${object.shift}</td>
	  	 		<td>${object.customerCode}</td>
	  	 		<td>${object.partNo}</td>
	  	 		<td>${object.partName}</td>
	  	 		<td>${object.moldNo}</td>
	  	 		<td>${object.st}</td>
	  	 		<td>${object.quantity}</td>
	  	 		<td>${object.snpWip}</td>
	  	 		<td>${object.reasonName}</td>
	  	 		<td>
	  	 			<c:if test="${not empty object.workOrderNo}">${object.workOrderNo}</c:if>
	  	 			<c:if test="${empty object.workOrderNo}">&nbsp;</c:if>
	  	 		</td>
	  	 		<td>
	  	 			<c:if test="${not empty object.startLotNo}">${object.startLotNo}</c:if>
	  	 			<c:if test="${empty object.startLotNo}">&nbsp;</c:if>
	  	 		</td>
	  	 		<td>
	  	 			<c:if test="${not empty object.endLotNo}">${object.endLotNo}</c:if>
	  	 			<c:if test="${empty object.endLotNo}">&nbsp;</c:if>
	  	 		</td>
	  	 		<td>
	  	 			<c:if test="${not empty object.genStatus}">
	  	 				<c:choose>
	  	 					<c:when test="${object.genStatus}">Yes</c:when>
	  	 					<c:otherwise>No</c:otherwise>
	  	 				</c:choose>
	  	 			</c:if>
	  	 			<c:if test="${empty object.genStatus}">&nbsp;</c:if>
	  	 		</td>
	  	 		<td>
	  	 			<c:if test="${not empty object.genDate}">${object.genDate}</c:if>
	  	 			<c:if test="${empty object.genDate}">&nbsp;</c:if>
	  	 		</td>
	  	 	</tr>
	  	 </c:forEach>
	</table>
	
	<br/>
	
	<!-- Location for triggering the event for generating work order and snpWip -->
	<div align="right">
		<input id="btnGenerateWipLot" type="button" class="submit_button" value="Generate W/O and Lot"/>
	</div>
	
</c:if>
</form:form>
</body>
</html>