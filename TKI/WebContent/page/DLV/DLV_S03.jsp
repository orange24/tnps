<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
	<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">

var dlvS03Form;
var boxDeliveryDate;

	//initialze script run
	$(document).ready(function() {  
		dlvS03Form = $("#dlvS03Form");
		boxDeliveryDate = $("input#boxDeliveryDate");
		
		// export excel
		$("input#btnExport").click(function(){
			var errors = [];
			
			if(boxDeliveryDate.val() == "")
				errors.push({"code": "err.cmm.001", "arguments": ["Delivery Date"]});
				
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
			message.clear();

			downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));

			// <!-- CALL: 'DLV_S03Controller'. -->
			dlvS03Form.attr("action", "DLV_S03_export.xls");
			/* dlvS03Form.submit(); */
			document.getElementById('dlvS03Form').submit();
		});
	});

</script>
</head>
<body>
<form:form method="post" id="dlvS03Form" commandName="deliveryPlan" action="DLV_S03_export.html">
<h1><spring:message code='menu.DeliveryPlanEntry'/></h1>
	<ul id="navlist">
	  <li>
		<a href="DLV_S01.html">Delivery Plan Search/List</a>
		<a href="DLV_S03.html" id="current"> Daily Customer Delivery</a>
	 </li>     
	</ul>    
    
	<page:message item="${deliveryPlan}"/>

	<table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
        <tr>
          <th width="15%">Delivery Date&nbsp;<span class="textred">*</span></th>
          <td><form:input path="deliveryDate" id="boxDeliveryDate" cssClass="date"/></td>
        </tr>
    </table>
    <br/>
   <security:authorize ifAnyGranted="DLV_R02_EXPORT">
    <div align="right">
    	<input type="button" value="Summary Report" id="btnExport" style="width:150px"/>
    </div>
    </security:authorize>
    <br/>
</form:form>
</body>
</html>
