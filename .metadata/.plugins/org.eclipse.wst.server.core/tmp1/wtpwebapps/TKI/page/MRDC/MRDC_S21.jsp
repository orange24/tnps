<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
var customerIdSel;
var startDateBox;
var endDateBox;
var partNameBox;
var partNoBox;
var percentBox1;
var percentBox2;
var btnExport;
var mrdc_s21Form;
$(document).ready(function(){
	partNameBox 	= $("#partNameBox");
	partNoBox 		= $("#partNoBox");
	customerIdSel 	= $("#customerIdSel");
	startDateBox 	= $("#startDateBox");
	endDateBox 		= $("#endDateBox");
	percentBox1		= $("#percent1");
	percentBox2		= $("#percent2");
	btnExport 		= $("#btnExport");
	mrdc_s21Form 	= $("#mrdc_s21Form");
	
	btnExport.click(function(){
		var errors = [];
		if(startDateBox.val()+"".trim().length != 0 && endDateBox.val()+"".trim().length != 0){	
			if( startDateBox.datepicker("getDate") > endDateBox.datepicker("getDate") ) {
				errors.push({"code":"err.cmm.008","arguments":["Mold Updated Date (From)","Mold Updated Date (To)"]});
			}
		}
		if(($('input[id=categoryType]:checked').val() === '1' && percentBox1.val()==="" )||($('input[id=categoryType]:checked').val() === '2' && percentBox2.val()==="" )){
			errors.push({"code":"err.cmm.001","arguments":["Percent"]});
		}
		if( errors.length > 0 ) {
			message.setErrors(errors);
			return false;
		}
		message.clear();
		downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));
		
		var percent = "";
		if($('input[id=categoryType]:checked').val() === '1'){
			percent = "&percent="+percentBox1.val();
		}else if($('input[id=categoryType]:checked').val() === '2'){
			percent = "&percent="+percentBox2.val();
		}
		
		var params = {
	               "customerId" 	: customerIdSel.val()
	              ,"partNo" 		: partNoBox.val() 
	              ,"partName" 		: partNameBox.val() 
	              ,"startDate" 		: startDateBox.val()
	              ,"endDate" 		: endDateBox.val()
	              ,"categoryType" 	: $('input[id=categoryType]:checked').val()
		};

		getJSON("MRDC_R21_export_count", $.param(params)+percent, function( result ){
			
			if(result.count > result.maxRecord && result.size == result.maxRecord){
				alert("<spring:message code='cfm.excel.001'/>".replace(/\{0\}/g, result.maxRecord).replace(/\{1\}/g, result.count));
			}
			
			$("input[type='hidden'][name='percent']", mrdc_s21Form).remove();
			if($('input[id=categoryType]:checked').val() === '1'){
				mrdc_s21Form.append("<input type='hidden' name='percent' value='"+percentBox1.val()+"'/>");
			}else if($('input[id=categoryType]:checked').val() === '2'){
				mrdc_s21Form.append("<input type='hidden' name='percent' value='"+percentBox2.val()+"'/>");
			}		
			mrdc_s21Form.attr("action","MRDC_S21_export.html");
			mrdc_s21Form.submit();
		});
		return false;
	});
	
	// <!-- Initial: Auto Completion. -->
	var partNoList = {
		source: function( request, response ) {
			getJSON("partAutoComplete",  
					{"partNo" : partNoBox.val(),"customerId": customerIdSel.val(),"partName" : partNameBox.val()}, 
					function(result){
						response($.map(result,function(item){
							return {
								label: item.partNo,
								partNoBox: item.partNo,
								partName : item.partName
							};
						}));
					});
		},
		select: function( event, ui ) {
			partNameBox.val(ui.item.partName);
		},
		minLength: 1,
		delay: 1000
	};
	partNoBox.autocomplete(partNoList);
	
	var partNameList = {
		source: function( request, response ) {
			getJSON("partAutoComplete",  
					{"partNo" : partNoBox.val(),"partName" : partNameBox.val(),"customerId": customerIdSel.val()}, 
					function(result){
						response($.map(result,function(item){
							return {
								label: item.partName,
								partNo: item.partNo,
								partNameBox: item.partName
							};
						}));
				});
		},
		select: function( event, ui ) {
			partNoBox.val(ui.item.partNo);
		},
		minLength: 1,
		delay: 1000
	};
	partNameBox.autocomplete(partNameList);
});
</script>
</head>
<body>
	<h1 class="header"><spring:message code='menu.MoldShotHistory'/></h1>
<page:message item="${mDetail}" />
<form:form id="mrdc_s21Form" method="post" action="MRDC_S21.html" commandName="mDetail" >
	<table width="100%" border="0" cellpadding="3" cellspacing="1">
	  <tr>
	    <td><table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
	        <tr>
	          <th width="12%"><span class="label">Customer </span> </th>
	          <td width="32%"><form:select path="customerId" items="${custMap}" id="customerIdSel" ></form:select></td>
	          <th width="16%"><span class="label">Mold Updated Date<br />(From - To)</span> </th>
	          <td width="40%">
	          <form:input path="startDate" cssClass="date" id="startDateBox" /> - <form:input path="endDate" cssClass="date" id="endDateBox" />
	          </td>
	        </tr>
	        <tr>
	          <th width="12%"><span class="label">Part No</span></th>
	          <td width="32%"><form:input path="partNo" id="partNoBox" /></td>
	          <th width="16%"><span class="label">Part Name</span></th>
	          <td width="40%"><form:input path="partName" id="partNameBox" size="80"/></td>
	        </tr>
	        <tr>
	        	<th width="12%">Output Category</th>
	        	<td colspan="3">
	        		<form:radiobutton path="categoryType" value="0" id="categoryType" checked ="checked"/>All<br />
	        		<form:radiobutton path="categoryType" value="1" id="categoryType"/>Total Production Shot &gt; Maximum Shot * <input id="percent1" size="6" maxlength="6" onkeypress="return PositiveDoubleFilter(event);"/> % only<br />
	        		<form:radiobutton path="categoryType" value="2" id="categoryType"/>Total Sales Shot &gt; Maximum Shot * <input id="percent2" size="6" maxlength="6" onkeypress="return PositiveDoubleFilter(event);"/> % only 
	        	</td>
	        </tr>
	    </table></td>
	  </tr>
	  <tr>
	    <td align="right">
	    	<input type="button" id="btnExport" value="Summary Report"  />
	    </td>
	  </tr>
	</table>
</form:form>
</body>
</html>