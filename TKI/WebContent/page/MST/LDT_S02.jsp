<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var ldts02Form;
	var stUseNo;
	var stDateFr4;
	var stDateTo4;
	var stDateFr5;
	var stDateTo5;
	var stDateFr6;
	var stDateTo6;
	var btnSave;
	
	$(document).ready(function(){
		ldts02Form 	= $("#ldts02Form");
		stUseNo		= $("select#stUseNo");
		stDateFr4	= $("input#stDateFr4");
		stDateTo4	= $("input#stDateTo4");
		stDateFr5	= $("input#stDateFr5");
		stDateTo5	= $("input#stDateTo5");
		stDateFr6	= $("input#stDateFr6");
		stDateTo6	= $("input#stDateTo6");
		btnSave		= $("input#btnSave");

		btnSave.click(function(){
			var errors 	= [];
			if(stUseNo.val() == ""){
				errors.push({"code":"err.cmm.001","arguments":["Standard Time Use"]});
			}
			if (stDateFr4.val() && stDateTo4.val() && stDateFr4.datepicker("getDate").getTime() > stDateTo4.datepicker("getDate").getTime()) {
				errors.push({"code":"err.cmm.008", "arguments":["Standard Time 4 : "+stDateTo4.attr("title"),stDateFr4.attr("title")]});
			}
			if (stDateFr5.val() && stDateTo5.val() && stDateFr5.datepicker("getDate").getTime() > stDateTo5.datepicker("getDate").getTime()) {
				errors.push({"code":"err.cmm.008", "arguments":["Standard Time 5 : "+stDateTo5.attr("title"),stDateFr5.attr("title")]});
			}
			if (stDateFr6.val() && stDateTo6.val() && stDateFr6.datepicker("getDate").getTime() > stDateTo6.datepicker("getDate").getTime()) {
				errors.push({"code":"err.cmm.008", "arguments":["Standard Time 6 : "+stDateTo6.attr("title"),stDateFr6.attr("title")]});
			}
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
			message.clear();
			if(confirm("<spring:message code='cfm.cmm.001'/>")){
				ldts02Form.submit();
			}
		});
		
	});
</script>
</head>
<body>
<h1><spring:message code='menu.LeadtimeMaster'/></h1>
	<ul id="navlist">
  		<li><a href="LDT_S01.html">Leadtime Management</a></li>
  		<li><a href="LDT_S02.html" id="current">ST Configuration</a></li>
	</ul>
	<page:message item="${mLeadtime}" />
	<form:form method="post" id="ldts02Form" commandName="mLeadtime" action="LDT_S02_save.html">
		<table class="ui-widget ui-widget-content " width="100%" border="0" cellpadding="3" cellspacing="1">
			<tr>
				<th width="20%"><span class="label">Standard Time Use</span><span class="textred">*</span></th>
				<td>
					<form:select path="stUseNo" id="stUseNo">
       					<form:option value="">-- Select Standard Time --</form:option>
       					<form:option value="1">ST1</form:option>
       					<form:option value="2">ST2</form:option>
       					<form:option value="3">ST3</form:option>
       					<form:option value="4">ST4</form:option>
       					<form:option value="5">ST5</form:option>
       					<form:option value="6">ST6</form:option>
        			</form:select>
				</td>
			</tr>
			<tr>
				<th width="20%">Standard Time 4 Period</th>
				<td>
					<form:input title="Period From(Date)" path="stDateFr4" id="stDateFr4" cssClass="date" size="10" onchange="this.focus();"/> 
        			- 
        			<form:input title="Period To(Date)" path="stDateTo4" id="stDateTo4" cssClass="date" size="10" onchange="this.focus();"/>
				</td>
			</tr>
			<tr>
				<th width="20%">Standard Time 5 Period</th>
				<td>
					<form:input title="Period From(Date)" path="stDateFr5" id="stDateFr5" cssClass="date" size="10" onchange="this.focus();"/> 
        			- 
        			<form:input title="Period To(Date)" path="stDateTo5" id="stDateTo5" cssClass="date" size="10" onchange="this.focus();"/>
				</td>
			</tr>
			<tr>
				<th width="20%">Standard Time 6 Period</th>
				<td>
					<form:input title="Period From(Date)" path="stDateFr6" id="stDateFr6" cssClass="date" size="10" onchange="this.focus();"/> 
        			- 
        			<form:input title="Period To(Date)" path="stDateTo6" id="stDateTo6" cssClass="date" size="10" onchange="this.focus();"/>
				</td>
			</tr>
		</table>	
		<table width="100%">
  			<tr>
    			<td align="left">
      				<input name="btnSave" type="button" id="btnSave" value="Save" />
				</td>
				<td align="right">
					Set up for all WIP and Part
				</td>
  			</tr>
		</table>
	</form:form >
</body>
</html>