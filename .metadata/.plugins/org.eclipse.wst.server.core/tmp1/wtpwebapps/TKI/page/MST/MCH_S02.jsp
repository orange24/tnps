<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var mchS02Form;
	var boxMachineNo;
	var boxWIP;
	var textMachineNo;
	var textMachineName;
	var textMachineCost;
	var textRemark;
	var checkInfinity;
	var textStartDate;
	var textEndDate;
	var btnSave;
	var btnBack;
	var hidCreateDate;
	var btnDelete;
	
	$(document).ready(function(){
		mchS02Form		= $("#mchS02Form");
		boxWIP          = $("select#selectWip");
		boxMachineNo    = $("select#machineId");
		textMachineNo   = $("input#machineNo");
		textMachineName = $("input#machineName");
		textMachineCost	= $("input#machineCost");
		checkInfinity   = $("#infinity");
		textStartDate   = $("input#startDate");
		textEndDate     = $("input#endDate");
		btnSave			= $("#btnSave");
		btnBack         = $("#btnBack");
		hidCreateDate   = $("#hidCreateDate");
		textRemark		= $("#remark");
		btnDelete 		= $("#btnDelete");
				
		if(hidCreateDate.val()!= ""){
			var params = {
				"wip" : boxWIP.val()
			};
			getJSON("machineList",params,function(result){
				boxMachineNo.find("option:not(:first)").remove();
				var machineNoMap = {}; 
				$.each(result,function(index, item){
					if( !machineNoMap[item.machineNo] ) {
						boxMachineNo.append( $("<option></option>").val(item.machineId).html(item.machineNo) );
						machineNoMap[item.machineNo] = true;
					}
				});
				boxMachineNo.find("option[value='${mMachine.machineId}']").attr("selected", "selected");
			});
			$("select").attr("disabled", true);
		}
		boxWIP.change(function(){
			comboBox.setMachineNo(boxMachineNo, $(this));
				textMachineNo.show();
		});
		boxMachineNo.change(function(){
			if(boxMachineNo.val()!=""){
				textMachineNo.hide();
			}else{
				textMachineNo.show();
			}
		});	 
		checkInfinity.change(function(){			
			if(checkInfinity.attr("checked")== true){
				textEndDate.val("31/12/9999");
				textEndDate.datepicker("disable");				
			}else{
				textEndDate.val("");
				textEndDate.datepicker("enable");
			}
		});
		btnSave.click(function(){
			var params = {};
			if(boxMachineNo.val()==""){
				params["machineNo"] = textMachineNo.val();
			}else{
				params["machineNo"] = boxMachineNo.find("option:selected").text();
			}
			params["machineId"] = boxMachineNo.val();
			params["wip"] = boxWIP.val();
			params["machineName"] = textMachineName.val();
			params["machineCost"] = textMachineCost.val();
			params["remark"] = textRemark.val();
			params["startDate"] = textStartDate.val();
			params["endDate"] = textEndDate.val();
			
			var msg = [];
			if(boxWIP.val()==""){
				msg.push({"code":"err.cmm.001","arguments":["WIP"]});
			}
			if(boxMachineNo.val()==""){
				if(textMachineNo.val()==""){
					msg.push({"code":"err.cmm.001","arguments":["Machine No"]});
				}
			}
			if(textMachineName.val()==""){
				msg.push({"code":"err.cmm.001","arguments":["Machine Name"]});
			}
			if(textStartDate.val()==""){
				msg.push({"code":"err.cmm.001","arguments":["Start Date"]});
			}
			if(textEndDate.val()==""){
				msg.push({"code":"err.cmm.001","arguments":["End Date"]});
			}
			if(textStartDate.val() && textEndDate.val() && textStartDate.datepicker("getDate").getTime() > textEndDate.datepicker("getDate").getTime()) {
				msg.push({"code":"err.cmm.008", "arguments":[textEndDate.attr("title"),textStartDate.attr("title")]});				
			}	
			if (msg.length > 0) {
				message.setErrors(msg);
				return;
			}else{
				message.clear();
			}

			if(hidCreateDate.val()== ""){	
				if(boxMachineNo.val()!=""){
					params["machineId"] = 0;
				}
				if( confirm("<spring:message code='cfm.cmm.001'/>") ){	
					getJSON("MCH_S02_save", params, function( result ){	
						if(result.errors == ""){
							message.clear();
							message.setInfos([{"code":"inf.cmm.002","arguments":[""]}]);
							textMachineCost.val(result.machineCost).keyup();
							$("input, select, textarea").attr("disabled", true);
						}else{
							message.setErrors(result.errors);
						}
					});
				}				
			}else{
				var params1 = mchS02Form.serialize();
				if( confirm("<spring:message code='cfm.cmm.001'/>") ){	
					getJSON("MCH_S02_save", params1, function( result ){	
						if(result.errors == ""){
							message.clear();
							message.setInfos([{"code":"inf.cmm.002","arguments":[""]}]);
							textMachineCost.val(result.machineCost).keyup();

						}else{
							message.setErrors(result.errors);
						}
					});
				}	
			}			
		});
		btnDelete.click(function(){
			if (confirm("<spring:message code='cfm.cmm.002'/>")) {
				mchS02Form.attr("action","MCH_S02_delete.html");
				mchS02Form.submit();
			}
		});
		btnBack.click(function(){
			mchS02Form.attr("action","MCH_S01_search.html?button=back");
			mchS02Form.submit();
		});
	});
</script>
</head>
<body>
<h1><spring:message code='menu.MachineMaster'/></h1>
	<form:form method="post" id="mchS02Form" commandName="mMachine" action="MCH_S02.html">
		<ul id="navlist">
		     <li><a href="MCH_S01.html" >Machine Search/List</a> </li>
		     <li><a href="MCH_S02.html" id="current">Machine Add/Edit</a> </li>
		</ul>
		<page:message item="${mMachine}" />
		<table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
		      <tr>		      	
		        <th width="12%" class="label">
      				<form:hidden path="createDate" id="hidCreateDate" />
		        	WIP <span class="textred">*</span>
		        </th>
		        <td width="23%" >
		        	<form:select id="selectWip" path="wip" items="${wipMap}" />
		        	<form:hidden path="wip" />
		        </td>
		      </tr>
		      <tr>
		        <th class="label">Machine No. <span class="textred">*</span></th>
		        <td > 
			        <form:select id="machineId" path="machineId" items="${machineMap}" />
			        <form:hidden path="machineId" />
			        <c:if test="${not empty mMachine.createDate}">
			        	<form:hidden path="machineNo" />
			        </c:if>
					<c:if test="${empty mMachine.createDate}">
			        	<form:input path="machineNo" id="machineNo" />
					</c:if>
		        </td>
		      </tr>
		      <tr>
		        <th class="label">Machine Name <span class="textred">*</span></th>
		        <td ><form:input path="machineName" id="machineName" /></td>
		      </tr>
		       <tr>
		        <th class="label">Machine Cost </th>
		        <td ><input type="text" name="machineCost" id="machineCost" value="<fmt:formatNumber pattern="#,##0.000" value="${mMachine.machineCost}" />" onkeyup="addCommas(this);" onkeypress="return PositiveDoubleFilter(event);" /></td>
		      </tr>
		      <tr>
		        <th class="label">Remark</th>
		        <td ><form:textarea path="remark" id="remark" cols="35" rows="5" /></td>
		      </tr>
		      <tr>
		        <th class="label">Start Date <span class="textred">*</span></th>
		        <td ><form:input title="Start Date" id="startDate" path="startDate" cssClass="date" /></td>
		      </tr>
		      <tr>
		        <th class="label">End Date <span class="textred">*</span></th>
		        <td >
		        	<form:input title="End Date" id="endDate" path="endDate" cssClass="date" />
		        	<input type="checkbox" id="infinity" />&nbsp;Infinity
		        </td>
		      </tr>
	   </table>
	   <table width="100%">
		      <tr>
			    <td><div align="left">
			    	<input name="btnSave" type="button" id="btnSave" value="Save" />
					<c:if test="${not empty mMachine.createDate}">
						<input type="button" id="btnDelete" value="Delete" />
			      		<input name="btnBack" type="button" id="btnBack" value="Back" />
			     	</c:if>	
			    </div></td>
			    <td><div align="right"><span class="ui-state-error-text">*</span> Required Field</div></td>
			  </tr>
		</table>
	</form:form>
</body>
</html>