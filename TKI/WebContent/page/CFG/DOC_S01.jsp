<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var docNo;
	var revDocNo;
	var btnSave;
	var docS01Form;
	
	$(document).ready(function(){
		docS01Form		= $("#docS01Form");
		docNo		= $("#docNo");
		revDocNo		= $("#revDocNo");
		btnSave			= $("#btnSave");	
		
		btnSave.click(function(){
			if(confirm("Confirm to Save Doc No.")){
				/* docS01Form.submit(); */
				document.getElementById('docS01Form').submit();
			}
		});	

	});	
</script>
</head>
<body>
	
	<form:form method="post" id="docS01Form" commandName="docNo" action="DOC_S01_save.html">
	<h1 class="header"><spring:message code='menu.DocNo'/></h1>
		<page:message item="${batch}" />
		<table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
			<tr>		      	
				<th width="100%" class="label" colspan="2"><spring:message code='menu.DocNo.report1'/></th>
			</tr>
			<tr>		      	
				<th width="12%" class="label">Doc No.</th>
		        <td width="35%"><form:input title="Doc No." path="docNo" id="docNo" tabindex="1"  /></td>
			</tr>
			<tr>		      	
				<th width="12%" class="label">Rev Doc No.</th>
		        <td width="35%"><form:input title="Rev Doc No." path="revDocNo" id="revDocNo" tabindex="2"  /></td>
			</tr>
			<tr>		      	
				<th width="100%" class="label" colspan="2"><spring:message code='menu.DocNo.report2'/></th>
			</tr>
			<tr>		      	
				<th width="12%" class="label">Doc No.</th>
		        <td width="35%"><form:input title="Doc No." path="docNoR2" id="docNoR2" tabindex="3"  /></td>
			</tr>
			<tr>		      	
				<th width="12%" class="label">Rev Doc No.</th>
		        <td width="35%"><form:input title="Rev Doc No." path="revDocNoR2" id="revDocNoR2" tabindex="4"  /></td>
			</tr>
			<tr>		      	
				<th width="100%" class="label" colspan="2"><spring:message code='menu.DocNo.report3'/></th>
			</tr>
			<tr>		      	
				<th width="12%" class="label">Doc No.</th>
		        <td width="35%"><form:input title="Doc No." path="docNoR3" id="docNoR3" tabindex="5"  /></td>
			</tr>
			<tr>		      	
				<th width="12%" class="label">Rev Doc No.</th>
		        <td width="35%"><form:input title="Rev Doc No." path="revDocNoR3" id="revDocNoR3" tabindex="6"  /></td>
			</tr>
		</table>
		<table width="100%">
			<tr>
				<td align="right">
    				<input name="btnSave" type="button" id="btnSave" value="Save" />
  				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>