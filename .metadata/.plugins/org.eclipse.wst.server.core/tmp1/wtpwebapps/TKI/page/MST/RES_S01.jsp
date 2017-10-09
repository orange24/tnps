<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var selectWip;
	var btnSearch;
	var resS01Form;
	var checkRSSTM;
	var radioReason;
	var radioWip;
	var selectWip;

	$(document).ready(function() {
		resS01Form 		= $("#resS01Form");
		btnSearch 		= $("#btnSearch");
		checkRSNG		= $("#rsNGR");
		checkRSSTM		= $("#rsStopMac");
		checkRSDC		= $("#rsDiecast");
		radioReason		= $("#rdRs");
		radioWip		= $("#rdWip");
		selectWip		= $("#wip");

		if(radioReason.attr("checked")==true){
			selectWip.attr("disabled",true).hide();
		}
		radioReason.click(function(){
			selectWip.attr("disabled",true).val("").hide();
		});
		radioWip.click(function(){
			selectWip.attr("disabled",false).show();
		});
		
		btnSearch.click(function(){	
			var msg = [];
			if(checkRSNG.attr("checked")!= true){
				if(checkRSSTM.attr("checked")!= true){
					if(checkRSDC.attr("checked")!=true){
						msg.push({"code":"err.cmm.001","arguments":["Reason Type"]});
					}
				}
			}
			if (msg.length > 0) {
				message.setErrors(msg);
				return;
			}
			
			message.clear();
			
			resS01Form.attr("action","RES_S01_search.html");
			resS01Form.submit();
		});
	});
	
	function fnDelete( row ) {
		var rowNo = row.find("td:first-child").html().trim();
		
		if( !confirm("<spring:message code='cfm.cmm.003'/>".replace(/\{0\}/g, rowNo)) )
			return;

		resS01Form.attr("action", "RES_S01_delete.html");
		resS01Form.append("<input type='hidden' name='reasonId' value='"+ row.attr("id") +"'/>");
		resS01Form.submit();
	}
</script>
</head>
<body>
	<form:form method="post" id="resS01Form" commandName="mReason" action="RES_S01_search.html">
		<h1><spring:message code='menu.ReasonMaster'/></h1>
		<ul id="navlist">
        	<li><a href="RES_S01.html" id="current">Reason Search/List</a> </li>
        	<li><a href="RES_S02.html" > Reason Add/Edit</a> </li>
        	<li><a href="RES_S03.html">Reason WIP Mapping</a></li>
      	</ul>
      	<page:message item="${mReason}" />
      	<table width="100%" border="0" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
      		<tr>
      			<th width="11%" class="label"> Type</th>
      			<td width="37%">
	      			<c:if test="${mReason.wip == null}">
	      				<input type="radio" name="rd" id="rdRs" checked="checked"> Reason
      					<input type="radio" name="rd" id="rdWip"> WIP
      					<form:select path="wip" id="wip" items="${wip}"></form:select>
	      			</c:if>    
	      			<c:if test="${mReason.wip != null}">  
	      				<input type="radio" name="rd" id="rdRs"> Reason
      					<input type="radio" name="rd" id="rdWip" checked="checked"> WIP
      					<form:select path="wip" id="wip" items="${wip}"></form:select>
	      			</c:if>				
      			</td>
          		<th width="12%" >Reason Type <span class="textred">*</span></th>
          		<td width="40%" align="left">
          			<form:checkbox path="reasonTypeCode" id="rsNGR" value="1" />   NG Reason 
          			<form:checkbox path="reasonTypeCode" id="rsStopMac" value="2"/>   Machine Stop   
          			<form:checkbox path="reasonTypeCode" id="rsDiecast" value="3"/>   Diecast          			
				</td>
        	</tr>
        	<tr>
          		<th class="label">Reason Code</th>
          		<td><form:input path="reasonCode"/></td>
          		<th>Reason</th>
          		<td align="left"><form:input path="reasonName"/></td>
        	</tr>
      	</table>
      	<table width="100%">
			<tr>
				<td align="right">					
			      	<input name="btnSearch" type="button" id="btnSearch" value="Search"/>
			    </td>
			</tr>
		</table>
		<c:if test="${fn:length(mReason.reasonList) >0}">
		<table width="100%" border="1" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
		    <tr>
		  		<td colspan="8">
		  			<div style="float:left" ><page:display item="${mReason}" /></div>
		  			<div style="float:right" ><page:number item="${mReason}" /></div>
		  		</td>
		  	</tr>
			<tr>
				<th align="center">No.</th>
				<c:if test="${mReason.wip != null}">
		       		<th align="center">WIP</th>
		        </c:if>
		        <th align="center">Reason Type</th>
		        <th align="center">Reason Code</th>
		        <th align="center">Reason</th>
		        <th align="center">Action</th>
	      	</tr>
	      	<c:forEach items="${mReason.reasonList}" varStatus="status" var="reason" begin="0" step="1">
	      		<tr id="${reason.reasonId}" align="center">
	      			<td align="center"><page:rowno item="${mReason}" index="${status.index}"/></td>
	      			<c:if test="${mReason.wip != null}">
	      				<td>${reason.wip}&nbsp;:&nbsp;${reason.wipName}</td>
	      			</c:if>
	      			<td>${reason.reasonTypeName}&nbsp;</td>
	      			<td>${reason.reasonCode}</td>
	      			<td>${reason.reasonName}</td>
	      			<td>
				    	<a href="RES_S02_view.html?reasonId=${reason.reasonId}"><img src="image/icon/update.gif" border="0"/></a> 	
				    	<a href="javascript:void(0);" onclick="fnDelete( $(this).closest('tr') );" ><img src="image/icon/delete.gif" border="0"/></a>
	    				&nbsp;			    	
				    </td>
	      		</tr>
	      	</c:forEach>
	      	<tr>
				<td colspan="8">
					<div style="float:left" ><page:display item="${mReason}" /></div>
					<div style="float:right" ><page:number item="${mReason}" /></div>
				</td>
 			</tr>
      	</table>
      	</c:if>
	</form:form>
</body>
</html>
