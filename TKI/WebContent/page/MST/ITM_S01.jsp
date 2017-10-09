<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var boxCustomer;
	var txtFGNo;
	var txtFGName;
	var txtPartNo;
	var txtPartName;
	var btnExport;
	var itmS01Form;
	  	
	$(document).ready(function() {
		boxCustomer = $("#customerId");
		txtFGNo 	= $("#fgNo");
		txtFGName 	= $("#fgName");
		txtPartNo 	= $("#partNo");
		txtPartName = $("#partName");
		btnSync 	= $("btnSync");	
		itmS01Form	= $("#itmForm");
	});
	
	function edit(customerId,fgId,partId){
		var params = {};
		params["customerId"] = customerId;
		params["fgId"] = fgId;
		params["partId"] = partId;
		document.location = "ITM_S03.html?"+$.param(params);
	}
	function fnDelete( row,fgId ) {
		var rowNo = row.find("td:first-child").html().trim();
		if( !confirm("<spring:message code='cfm.cmm.003'/>".replace(/\{0\}/g, rowNo)) )
			return;
		
		itmS01Form.attr("action", "ITM_S01_delete.html");
		itmS01Form.append("<input type='hidden' name='partList[0].partId' value='"+ row.attr("id") +"'/>");
		itmS01Form.append("<input type='hidden' name='partList[0].fgId' value='"+ fgId +"'/>");
		itmS01Form.submit();
	}
	function fnDeleteCustomer( row,fgId,customerId,partId ){
		var rowNo = row.find("td:first-child").html().trim();
		if( !confirm("<spring:message code='cfm.cmm.003'/>".replace(/\{0\}/g, rowNo)) )
			return;
		
		itmS01Form.attr("action", "ITM_S01_deleteCustomer.html");
		itmS01Form.append("<input type='hidden' name='partList[0].customerId' value='"+ customerId +"'/>");
		itmS01Form.append("<input type='hidden' name='partList[0].fgId' value='"+ fgId +"'/>");
		itmS01Form.append("<input type='hidden' name='partList[0].partId' value='"+ partId +"'/>");
		itmS01Form.submit();
	}
</script>
</head>
<body>
	<h1><spring:message code='menu.FG/PartMaster'/></h1>
	<ul id="navlist">
	  	<li><a href="ITM_S01.html" id="current">FG/Part Search/List</a></li>
	  	<li><a href="ITM_S02.html">FG/Part Sync From TPics</a></li>
	</ul>
	
	<page:message item="${mPart}" />
	
	<form:form id="itmForm" method="post" action="ITM_S01_search.html" commandName="mPart">
	  	<table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
	    	<tr>
	        	<th width="13%">Customer</th>
	        	<td width="87%" colspan="3">
	        		<form:select path="customerId" id="customerId" cssStyle="width:180px;" tabindex="1" 
	        			items="${customerMap}">
	        		</form:select>
	        	</td>
	        </tr>
	        <tr>
	        	<th>FG No.</th>
				<td><form:input path="fgNo" id="fgNo" tabindex="3"/></td>
				<th width="13%">FG Name</th>
		        <td width="37%"><form:input path="fgName" id="fgName" tabindex="4"/></td>
	      	</tr>
	      	<tr>
	        	<th>Part No.</th>
	        	<td><form:input path="partNo" id="partNo" tabindex="5"/></td>
	        	<th>Part Name</th>
	        	<td><form:input path="partName" id="partName" tabindex="6"/></td>
	      	</tr>
		</table>
	    <br/>
	  	<div align="right">
	  		<security:authorize ifAnyGranted="ITM_S01_EXPORT">
	      		<input type="submit" class="submit_button" id="btnExport" value="Export Parts"/>
	      	</security:authorize>
	      	<security:authorize ifAnyGranted="ITM_S01_SEARCH">
	      		<input type="submit" class="submit_button" id="btnSearch" value="Search"/>
	      	</security:authorize>
	    </div>
		<br />
	  	<c:if test="${not empty mPart.partList}">
			<table id="dynamictbl" border="1" cellpadding="3" cellspacing="0" width="100%" class="ui-widget ui-widget-content">
	      		<tr>
	        		<td colspan="10">
			        	<div style="float:left"><page:display item="${mPart}"/></div>
			          	<div style="float:right"><page:number item="${mPart}"/></div>
	        		</td>
	      		</tr>
	          	<tr>
					<th>No.</th>
	            	<th>Customer</th>
			        <th>FG No.</th>
			        <th>FG Name</th>
	            	<th>Part No.</th>
	            	<th>Part Name</th>
	            	<th>Process</th>
	            	<th>Remark</th>
	            	<th>Action 1</th>
	            	<th>Action 2</th>
	          	</tr>
	          	<c:forEach items="${mPart.partList}" var="item" varStatus="itemStatus">
	            <tr id="${item.partId}">
	              	<td align="center"><page:rowno item="${mPart}" index="${itemStatus.index}"/></td>
	              	<td>${item.customerCode}</td>
	              	<td>${item.fgNo}</td>
	          		<td>${item.fgName}</td>
	          		<td>${item.partNo}</td>
	          		<td>${item.partName}</td>
	          		<td>
	            		<c:forEach items="${item.wipList}" var="wipRow" varStatus="wipStatus">
	              			${wipStatus.count}.&nbsp;${wipRow.wip.wip} : ${wipRow.wip.wipName}<br/>
	            		</c:forEach>
	          		</td>
	              	<td>${item.remark}&nbsp;</td>
	              	<td align="center">
	              		<security:authorize ifNotGranted="ITM_S01_EDIT">&nbsp;</security:authorize>
	              		<security:authorize ifAnyGranted="ITM_S01_EDIT">
		              		<a href="javascript:edit(${item.customerId},${item.fgId},${item.partId})"><img 
		              		src="<%=request.getContextPath()%>/image/icon/update.gif" width="16" height="16" /></a>
		              		<a href="javascript:void(0);" onclick="fnDelete( $(this).closest('tr'),${item.fgId} );" ><img src="image/icon/delete.gif" border="0"/></a>
	              		</security:authorize>
	              	</td>
	              	<td align="center">
		              	<security:authorize ifAnyGranted="ITM_S01_EDIT">
		              		<a href="javascript:void(0);" onclick="fnDeleteCustomer( $(this).closest('tr'),${item.fgId},${item.customerId},${item.partId} );" ><img src="image/icon/delete.gif" border="0"/></a>
		              	</security:authorize>
	              	</td>
	          	</tr>
	          	</c:forEach>
	          	<tr>
	        		<td colspan="10">
	          			<div style="float:left"><page:display item="${mPart}"/></div>
	          			<div style="float:right"><page:number item="${mPart}"/></div>
	        		</td>
	      		</tr>
	        </table><br />
			Action 1 <img src="image/icon/delete.gif" border="0"/> - delete FG and Part.<br />
			Action 2 <img src="image/icon/delete.gif" border="0"/> - delete Customer of FG.<br />
	  	</c:if>
	</form:form>
</body>
</html>
