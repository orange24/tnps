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
var MRDCS06Form;
var boxYearMonthDateFr;
var boxYearMonthDateTo;
var customerId;
var partNo;
var partName;
var careerSheetNo;

	$(document).ready(function() {
		MRDCS06Form 		= $("#MRDCS06Form");
		boxYearMonthDateFr 	= $("input#boxYearMonthDateFr");
		boxYearMonthDateTo 	= $("input#boxYearMonthDateTo");
		customerId			= $("select#customerId");
		partNo				= $("input#partNo");
		partName			= $("input#partName");
		careerSheetNo		= $("input#careerSheetNo");
		
		$("input#btnExport").click(function(){
			var errors = [];
			if((boxYearMonthDateFr.val() == "")&&(boxYearMonthDateTo.val() == "")){
				errors.push({"code": "err.cmm.001", "arguments": ["Delivery Date"]});
			}else{
				if((boxYearMonthDateFr.val() != "")&&(boxYearMonthDateTo.val() != "")){
					if(boxYearMonthDateFr.datepicker("getDate").getTime() > boxYearMonthDateTo.datepicker("getDate").getTime()) {
						errors.push({"code":"err.cmm.008", "arguments":["Delivery Date To","Delivery Date From"]});
					}
				}
			}
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
			message.clear();
			downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));
			var params = {
		               "sCareerSheetNo" : careerSheetNo.val()
		              ,"sPartNo" 		: partNo.val() 
		              ,"sPartName" 		: partName.val() 
		              ,"sCustomerId" 	: customerId.val()
		              ,"reportDateFr" 	: boxYearMonthDateFr.val()
		              ,"reportDateTo" 	: boxYearMonthDateTo.val()
			};
			
			/*getJSON("MRDC_R06_export_count", $.param(params), function( result ){
				
				if(result.count > result.maxRecord && result.size == result.maxRecord){
					alert("<spring:message code='cfm.excel.001'/>".replace(/\{0\}/g, result.maxRecord).replace(/\{1\}/g, result.count));
				}
				
				MRDCS06Form.submit();
			});
			return false;*/
			MRDCS06Form.submit();
		});
		
		// <!-- Initial: Auto Completion. -->
		var partNoList = {
			source: function( request, response ) {
				getJSON("partAutoComplete",  
						{"partNo" : partNo.val(),"customerId": customerId.val(),"partName" : partName.val()}, 
						function(result){
							response($.map(result,function(item){
								return {
									label: item.partNo,
									partNo: item.partNo,
									partName : item.partName
								};
							}));
						});
			},
			select: function( event, ui ) {
				partName.val(ui.item.partName);
			},
			minLength: 1,
			delay: 1000
		};
		partNo.autocomplete(partNoList);
		
		var partNameList = {
			source: function( request, response ) {
				getJSON("partAutoComplete",  
						{"partNo" : partNo.val(),"partName" : partName.val(),"customerId": customerId.val()}, 
						function(result){
							response($.map(result,function(item){
								return {
									label: item.partName,
									partNo: item.partNo,
									partName: item.partName
								};
							}));
					});
			},
			select: function( event, ui ) {
				partNo.val(ui.item.partNo);
			},
			minLength: 1,
			delay: 1000
		};
		partName.autocomplete(partNameList);
		
		var partId = -1;
		careerSheetNo.keydown(function(){
			partId = -1;
			getJSON("queryPartId",  
				{"partNo" : partNo.val(),"partName" : partName.val(),"customerId": customerId.val()}, 
				function(result){
						partId = result.partId;
				});
		});
		
		var workOrderNoList = {
			source: function( request, response ) {
				getJSON("workOrderAutoComplete",
						{"partId" : partId,"workOrderNo" : careerSheetNo.val()} ,
					function(result){
						response($.map(result,function(item){
							return {
								label: item.workOrderNo,
								careerSheetNo: item.workOrderNo
							};
					}));
				});
			},
				minLength: 1,
				delay: 1000
		};
		careerSheetNo.autocomplete(workOrderNoList);
	});
</script>
</head>
<body>
	<h1 class="header"><spring:message code='menu.SaleOrderDetail'/></h1>
	<form:form method="post" id="MRDCS06Form" commandName="tDelivery" action="MRDC_R06_export.html">
	<page:message item="${tDelivery}"/>

	<table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
        <tr>
        	<th width="20%">Delivery Plan Date (From-To)<span class="textred">*</span></th>
    		<td width="32%">
    			<form:input path="reportDateFr" id="boxYearMonthDateFr" cssClass="date"/>
    			<form:input path="reportDateTo" id="boxYearMonthDateTo" cssClass="date"/>
    			<input type='hidden' name='sCustomerCode' id="sCustomerCode" value=''/>
    		</td>
    		<th width="16%">Customer</th>
    		<td width="32%"><form:select path="sCustomerId" items="${customerMap}" id="customerId"/></td>
        </tr>
        <tr>
        	<th width="20%">Part No</th>
    		<td width="32%"><form:input path="sPartNo" id = "partNo"/></td>
    		<th width="20%">Part Name</th>
    		<td width="32%"><form:input path="sPartName" id = "partName" size="80"/></td>
        </tr>
        <tr>
        	<th width="20%">Career Sheet No(Work Order No)</th>
    		<td colspan="3"><form:input path="sCareerSheetNo" id = "careerSheetNo"/></td>
        </tr>
    </table>
    <br/>
	   <div align="right">
	   	<input type="button" value="Summary Report" id="btnExport" style="width:150px"/>
	   </div>
    <br/>
	</form:form>
</body>
</html>