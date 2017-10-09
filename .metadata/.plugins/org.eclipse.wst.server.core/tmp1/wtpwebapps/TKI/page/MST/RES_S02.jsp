<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var resS02Form;
	var selReasonType;
	var txtReasonCode;
	var txtReasonName;
	var txtDescription;
	var trParentReason;
	var parentReason;
	var hidCreateDate;
	var btnSave;
	var btnBack;
	var btnDelete;
	
	$(document).ready(function(){
		resS02Form		= $("#resS02Form");
		selReasonType	= $("select#reasonType");
		txtReasonCode	= $("#reasonCode");
		txtReasonName	= $("#reasonName");
		txtDescription	= $("#description");
		trParentReason	= $("tr[id=parentReason]");
		parentReason	= $("#parentReasonId");
		hidCreateDate   = $("#hidCreateDate");
		btnSave			= $("#btnSave");
		btnBack         = $("#btnBack");
		btnDelete 		= $("#btnDelete");
		
		if(hidCreateDate.val()!= ""){
			resS02Form.append("<input type='hidden' name='reasonType' value= "+selReasonType.val()+" />");
			selReasonType.attr("disabled", true);
		}		
		if(selReasonType.val()==2){
			trParentReason.show();
		}else{
			trParentReason.hide();
		}		
		selReasonType.change(function(){
			if(selReasonType.val()==2){
				trParentReason.show();
			}else{
				trParentReason.hide();
			}
		});
		btnSave.click(function(){
			var msg = [];
			if(txtReasonCode.val()==""){
				msg.push({"code":"err.cmm.001","arguments":["Reason Code"]});
			}
			if(txtReasonName.val()==""){
				msg.push({"code":"err.cmm.001","arguments":["Reason Name"]});
			}
			if (msg.length > 0) {
				message.setErrors(msg);
				return;
			}else{
				message.clear();					
			}
			
			if(hidCreateDate.val()== ""){	
				var params = resS02Form.serialize();
				if( confirm("<spring:message code='cfm.cmm.001'/>") ){	
					postJSON("RES_S02_save", params, function( result ){	
						if(result.errors == ""){
							message.clear();
							message.setInfos([{"code":"inf.cmm.002","arguments":[""]}]);
							$("input, select, textarea").attr("disabled", true);
						}else{
							message.setErrors(result.errors);
						}
					});
				}
			}else{
				var params = resS02Form.serialize();
				if( confirm("<spring:message code='cfm.cmm.001'/>") ){	
					postJSON("RES_S02_edit", params, function( result ){	
						if(result.errors == ""){
							message.clear();
							message.setInfos([{"code":"inf.cmm.002","arguments":[""]}]);
						}else{
							message.setErrors(result.errors);
						}
					});
				}
			}
		});
		btnBack.click(function(){
			resS02Form.attr("action","RES_S01_search.html?button=back");
			resS02Form.submit();
		});	
		btnDelete.click(function(){
			if (confirm("<spring:message code='cfm.cmm.002'/>")) {
				resS02Form.attr("action","RES_S02_delete.html");
				resS02Form.submit();
			}
		});
	});
	
</script>
</head>
<body>
	<form:form method="post" id="resS02Form" commandName="mReason" action="RES_S02.html">
		<h1><spring:message code='menu.ReasonMaster'/></h1>
		<ul id="navlist">
        	<li><a href="RES_S01.html" >Reason Search/List</a></li>
        	<li><a href="RES_S02.html" id="current">Reason Add/Edit</a></li>
        	<li><a href="RES_S03.html">Reason WIP Mapping</a></li>
      	</ul>
		<page:message item="${mReason}" />
		<table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
  			<tr>
  				<th width="12%" class="label">
  					<form:hidden path="createDate" id="hidCreateDate" />
  					Reason Type <span class="textred">*</span></th>
  				<td width="23%">
  					<form:select path="reasonType" id="reasonType">
  						<form:option value="1">NG Reason</form:option>
  						<form:option value="2">Machine Stop</form:option>
  						<form:option value="3">Diecast</form:option>
  					</form:select>
        		</td>
  			</tr>    			       
      		<tr>
        		<th class="label">
        		<form:hidden path="reasonId" id="hidReasonId" />
        		Reason Code <span class="textred">*</span></th>
        		<td>
        			<form:input path="reasonCode" id="reasonCode" maxlength="5" />
        		</td>
      		</tr>
	     	<tr>
	        	<th class="label">Reason Name <span class="textred">*</span></th>
	        	<td>
	        		<form:input path="reasonName" id="reasonName" />
	        	</td>
	      	</tr>
	      	<tr>
	        	<th class="label">Description</th>
	        	<td>
	        		<form:textarea path="description" id="description" cols="35" rows="5" />
	        	</td>
	      	</tr>			
      		<tr id="parentReason">
        		<th class="label">Parent Reason</th>
        		<td >
        			<form:select path="parentReasonId" id="parentReasonId" items="${parentReason}" />
        		</td>
      		</tr>
    	</table>
      	<table width="100%">
  			<tr>
    			<td align="left">
      				<input name="btnSave" type="button" id="btnSave" value="Save" />
					<c:if test="${not empty mReason.createDate}">
						<input type="button" id="btnDelete" value="Delete" />
			      		<input name="btnBack" type="button" id="btnBack" value="Back" />
			     	</c:if>	
				</td>
				<td><div align="right"><span class="ui-state-error-text">*</span> Required Field</div></td>
  			</tr>
		</table>
	</form:form>
</body>
</html>