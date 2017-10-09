<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var wors01Form;
	var workerUnitCost;
	var btnSave;
	
	$(document).ready(function(){
		wors01Form 		= $("#wors01Form");
		workerUnitCost	= $("input#unitCost");
		btnSave			= $("input#btnSave");

		btnSave.click(function(){
			if(workerUnitCost.val() == ""){
				message.setErrors([{"code":"err.cmm.001","arguments":["Worker Unit Cost"]}]);
				return false;
			}
			if(confirm("<spring:message code='cfm.cmm.001'/>")){
			    wors01Form.append("<input type='hidden' name='workerUnitCost' value='"+ (workerUnitCost.val().replace(/,/g,'')) +"'/>");
				wors01Form.submit();
			}
		});		
	});
</script>
</head>
<body>
  	<h1 class="header"><spring:message code='menu.WorkerMaster'/></h1>
	<page:message item="${mWorker}" />
	<form:form method="post" id="wors01Form" commandName="mWorker" action="WOR_S01_save.html">
		<table class="ui-widget ui-widget-content " width="100%" border="0" cellpadding="3" cellspacing="1">
			<tr>
				<th width="20%"><span class="label">Worker Unit Cost</span></th>
				<td><input type="text" name="unitCost" id="unitCost" value="<fmt:formatNumber pattern="#,##0.000" value="${mWorker.workerUnitCost}" />" onkeyup="addCommas(this);" onkeypress="return PositiveDoubleFilter(event);" />  Bath/1 Hr./1 Worker</td>
			</tr>
		</table>
		<br/>
			<div align="left">
      			<input name="btnSave" type="button" id="btnSave" value="Save" />
      		</div>
	</form:form >
</body>
</html>