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
	var resS03Form;	
	var wip;
	var reasonTypeCode;
	var btnSave;
	var reasonIdAll;
	
	$(document).ready(function() {
		
		resS03Form 		= $("#resS03Form");
		btnSearch 		= $("#btnSearch");
		wip				= $("#wip");
		reasonTypeCode	= $("#reasonType");	
		btnSave			= $("#btnSave");
		chkReasonIdAll	= $(".reasonIdAll");

		btnSearch.click(function(){
			var msg = [];
			if(wip.val()==""){
				msg.push({"code":"err.cmm.001","arguments":["WIP"]});
			}
			if (msg.length > 0) {
				message.setErrors(msg);
				return;
			}else{
				message.clear();					
			}
			resS03Form.append("<input type='hidden' name='wipName' value='"+wip.find("option:selected").text()+"'/>");
			resS03Form.attr("action","RES_S03_search.html");
			resS03Form.submit();
		});
		btnSave.click(function(){			
			message.clear();			
			if(confirm("<spring:message code='cfm.cmm.001'/>") ){
				resS03Form.append("<input type='hidden' name='wipName' value='"+wip.find("option:selected").text()+"'/>");
				resS03Form.attr("action","RES_S03_save.html");
				resS03Form.submit();
			}
		});
	});
	function jqCheckAll(id, name)
	{
		$("."+name).attr('checked', $('#' + id).is(':checked'));
	}
</script>
</head>
<body>
	<form:form method="post" id="resS03Form" commandName="mReason" action="RES_S03_search.html">
		<h1><spring:message code='menu.ReasonMaster'/></h1>
		<ul id="navlist">
        	<li><a href="RES_S01.html" >Reason Search/List</a> </li>
        	<li><a href="RES_S02.html" > Reason Add/Edit</a> </li>
        	<li><a href="RES_S03.html" id="current">Reason WIP Mapping</a></li>
      	</ul>
	    <page:message item="${mReason}" />
		<table width="100%" border="0" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
	    	<tr>
          		<th width="11%" class="label"> WIP<span class="textred"> *</span></th>
          		<td width="37%">
          			<form:select path="wip" id="wip" items="${wip}"></form:select>
          		</td>
		    	<th width="12%" >Reason Type</th>
		        <td width="40%" align="left">	
		        	<form:select path="reasonType">
		        		<form:option value="1">NG Reason</form:option>
		        		<form:option value="2">Machine Stop</form:option>
		        		<form:option value="3">Diecast</form:option>
		        	</form:select>			        	
          		</td>
        	</tr>
	    </table>
	    <table width="100%">
			<tr>
				<td align="right">					
			      	<input name="btnSearch" type="button" class="submit_button" id="btnSearch" value="Search"/>
			    </td>
			</tr>
		</table>
		<c:if test="${fn:length(mReason.reasonList) >0}">
			<table width="100%" border="1" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
			    <tr>
			        <th align="center">WIP</th>
			        <th align="center">Reason Type</th>
			        <th align="center">Reason Code</th>
			        <th align="center">Reason</th>
			        <c:if test="${mReason.reasonType == 2}">
			        	<th align="center">Category</th>
			        </c:if>
			        <th align="center"><input type="checkbox" name="checkAll" id="checkAll" onclick="jqCheckAll( this.id, 'reasonIdAll' )" /></th>
				</tr>
				<c:forEach items="${mReason.reasonList}" varStatus="status" var="reason" begin="0" step="1">
		      		<tr id="${reason.reasonId}" align="center">
		      			<td>${mReason.wipName}</td>		      				      			
		      			<td>${reason.reasonTypeName}&nbsp;</td>
		      			<td>${reason.reasonCode}&nbsp;</td>
		      			<td>${reason.reasonName}&nbsp;</td>
		      			<c:if test="${mReason.reasonType == 2 }">
		      				<c:if test="${not empty reason.parentReasonCode}">
		      					<td>${reason.parentReasonCode}&nbsp;:&nbsp;${reason.parentReasonName}</td>
		      				</c:if>
		      				<c:if test="${empty reason.parentReasonCode}"><td></td></c:if>
		      			</c:if>
		      			<td>
		      				<c:if test="${empty reason.wip}">
		      					<input type="checkbox" class="reasonIdAll" name="reasonList[${status.index}].reasonId" 
		      					id="reasonList[${status.index}].reasonId" value="${reason.reasonId} " />
		      				</c:if>
		      				<c:if test="${not empty reason.wip}">
		      					<c:if test="${mReason.reasonType == reason.reasonType}">		      					
			      					<input type="checkbox" class="reasonIdAll" name="reasonList[${status.index}].reasonId" 
			      					id="reasonList[${status.index}].reasonId" value="${reason.reasonId}"  checked="checked" />
			      				</c:if>
			      				<c:if test="${mReason.reasonType != reason.reasonType}">		      					
			      					<input type="checkbox" class="reasonIdAll" name="reasonList[${status.index}].reasonId" 
			      					id="reasonList[${status.index}].reasonId" value="${reason.reasonId}" />
			      				</c:if>
		      				</c:if>	      				
		      			</td>
		      		</tr>
				</c:forEach>
			</table>
			<table width="100%">
	  			<tr>
	    			<td align="right">
	      				<input name="btnSave" type="button" id="btnSave" value="Save" />
					</td>
	  			</tr>
			</table>
		</c:if>
	</form:form>
</body>
</html>
