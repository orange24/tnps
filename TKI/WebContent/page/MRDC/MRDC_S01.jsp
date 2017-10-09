<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var customerId;
	var partId;
	var btnExport;
	var mrdc_s01Form;
	
	$(document).ready(function(){
		customerId	= $("select#customerId");
		partId	= $("select#partId");
		btnExport	= $("input#btnExport");
		mrdc_s01Form= $("form#mrdc_s01Form");
		
		btnExport.click(function(){
			var errors = [];
			if( customerId.val() < 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["Customer"]});
			if( partId.val() < 0 )
				errors.push({"code": "err.cmm.001", "arguments": ["Part Name/Part No"]});
			
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
			message.clear();
			
			if (message.isNoError()) {
				downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));
				var param = ["customerCode="+ customerId.find("option:selected").text()];
				mrdc_s01Form.attr("action","MRDC_R01_export.html?"+param.join("&"));
				mrdc_s01Form.submit();
			}
		});
		
		customerId.change(function(){
			getJSON("boxPartNameNo", { "customerId" : customerId.val(),"wip" : "" }, function(result){
				partId.empty();
				$.each(result,function(val, text){
					partId.append( $("<option></option>").val(val).html(text) );
				});
			});
		});
	});
</script>
</head>
<body>
	<h1 class="header"><spring:message code='menu.ProductCompositionList'/></h1>
	<page:message item="${deliveryPlan}" />
	<form:form id="mrdc_s01Form" method="post" action="MRDC_R01_export.html" commandName="deliveryPlan">
		<table width="100%" border="0" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
			<tr>
				<th width="16%" class="label">Customer <span class="textred">*</span></th>
				<td width="34%"><form:select path="customerId" id="customerId" items="${customerMap}"/></td>
				<th width="20%">Part Name/Part No <span class="textred">*</span></th>
				<td width="30%"><form:select path="partId" id="partId" items="${partMap}"/></td>
			</tr>
		</table>
		<br/>
		<!-- security:authorize ifAnyGranted="MRDC_R14_EXPORT"> -->
			<div align="right">
				<input name="btnExport" type="button" id="btnExport" value="Summary Report"/>
			</div>
		<!-- /security:authorize> -->
	</form:form>
</body>
</html>