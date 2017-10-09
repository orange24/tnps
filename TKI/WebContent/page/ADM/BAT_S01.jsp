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
	var executeDate;
	var batS01Form;
	var selectBatch ;
	var finishDateTime;
	var runBy;
	var status;
	var btnRun;
	
	$(document).ready(function(){
		executeDate		= $("#executeDate");
		batS01Form		= $("#batS01Form");
		selectBatch 	= $("select#selectBatch");
		finishDateTime 	= $("#finishDateTime");
		runBy			= $("#runBy");
		status			= $("#status");
		btnRun			= $("#btnRun");	
		
		getBatchStatus();
		
		selectBatch.change(function(){
			postJSON("BAT_S01_Batch", { "batchId": selectBatch.val() }, function( result ){	
				if(result.finishDate){					
					var date        = new Date(result.finishDate);
					var dateMonth   = date.getMonth() + 1;							
					var month		= ((dateMonth+'').length === 1 ? "0" : "") + (dateMonth);
					var day			= ((date.getDate()+'').length === 1 ? "0" : "")+ date.getDate();
					var hour		= ((date.getHours()+'').length === 1 ? "0" : "")+ date.getHours();
					var min			= ((date.getMinutes()+'').length === 1 ? "0" : "")+ date.getMinutes();
					var sec			= ((date.getSeconds()+'').length === 1 ? "0" : "")+ date.getSeconds();
					var finishDate  = day +"/"+ month +"/"+ date.getFullYear() +" "+ hour +":"+ min +":"+ sec;
					finishDateTime.html(finishDate);
				}else{
					finishDateTime.html("");
				}
				var batchStatus = result.batchStatus;				
				if(batchStatus === 0){
					status.html("<strong class='textgreen'>Success</strong>");
					btnRun.attr("disabled",false);
				}else if(batchStatus === 1){
					status.html("<strong class='textblue'>Running...</strong>");
					btnRun.attr("disabled",true);
				}else if(batchStatus === 2){
					status.html("<strong class='textred'>Fail</strong>");
					btnRun.attr("disabled",false);
				}else if(batchStatus === 3){
					status.html("<strong class='textorange'>Success with error.</strong>");
					btnRun.attr("disabled",false);
				}
				var resultRunBy = result.runBy;
				runBy.html(resultRunBy);
			});
		});
		btnRun.click(function(){
			if($("#executeDate").datepicker("getDate") == null){
				alert('Please select date to run batch');
				return;
			}
			
			if(confirm("Confirm to Run Batch")){
				batS01Form.attr("action","BAT_S01_run.html");
				batS01Form.submit();
			}
		});	
		setInterval(function(){
			if( $("#status:contains('Running')").length > 0 ){
				postJSON("BAT_S01_Batch", { "batchId": selectBatch.val() }, function( result ){					
					var date        = new Date(result.finishDate);
					var dateMonth   = date.getMonth() + 1;
					var batchStatus = result.batchStatus;
					var resultRunBy = result.runBy;				
					var month		= ((dateMonth+'').length === 1 ? "0" : "") + (dateMonth);
					var day			= ((date.getDate()+'').length === 1 ? "0" : "")+ date.getDate();
					var hour		= ((date.getHours()+'').length === 1 ? "0" : "")+ date.getHours();
					var min			= ((date.getMinutes()+'').length === 1 ? "0" : "")+ date.getMinutes();
					var sec			= ((date.getSeconds()+'').length === 1 ? "0" : "")+ date.getSeconds();
					var finishDate  = day +"/"+ month +"/"+ date.getFullYear() +" "+ hour +":"+ min +":"+ sec;				
					if(batchStatus === 0){
						status.html("<strong class='textgreen'>Success</strong>");
						btnRun.attr("disabled",false);
					}else if(batchStatus === 1){
						status.html("<strong class='textblue'>Running...</strong>");
						btnRun.attr("disabled",true);
					}else if(batchStatus === 2){
						status.html("<strong class='textred'>Fail</strong>");
						btnRun.attr("disabled",false);
					}else if(batchStatus === 3){
						status.html("<strong class='textorange'>Success with error.</strong>");
						btnRun.attr("disabled",false);
					}				
					finishDateTime.html(finishDate);
					runBy.html(resultRunBy);				
				});
			}
			getBatchStatus();
		}, 30000);	

		if(!$("#executeDate").val()){
			$("#executeDate").datepicker( "setDate" , new Date() );
		}
	});	

	function getBatchStatus() {
		// clear table status
		$("#tableStatus tr:not(#rowHeader)").remove();
		//Get status of all batch to display status
		postJSON("BAT_S01_getBatchStatus", function(result) {
			if (result.length > 0) {
				$.each(result, function(rowIndex, data) {
					$('#tableStatus tr:last')
							.after(
									'<tr><td>'
											+ data.batchName
											+ '</td><td>'
											+ getStatusLabel(data.batchStatus)
											+ '</td></tr>');
					
					if (data.batchId == selectBatch.val()) {
						if (data.batchStatus === 0) {
							status.html("<strong class='textgreen'>Success</strong>");
							btnRun.attr("disabled", false);
						} else if (data.batchStatus === 1) {
							status.html("<strong class='textblue'>Running...</strong>");
							btnRun.attr("disabled", true);
						} else if (data.batchStatus === 2) {
							status.html("<strong class='textred'>Fail</strong>");
							btnRun.attr("disabled", false);
						} else if (data.batchStatus === 3) {
							status.html("<strong class='textorange'>Success with error.</strong>");
							btnRun.attr("disabled", false);
						}
					}
				});

				$('#tableStatus').show();

			} else {
				$('#tableStatus').hide();
			}
		});
	}

	function getStatusLabel(batchStatus) {
		var result = "";
		if (batchStatus === 0) {
			result = "<strong class='textgreen'>Success</strong>";
		} else if (batchStatus === 1) {
			result = "<strong class='textblue'>Running...</strong>";
		} else if (batchStatus === 2) {
			result = "<strong class='textred'>Fail</strong>";
		} else if (batchStatus === 3) {
			result = "<strong class='textorange'>Success with error.</strong>";
		}

		return result;
	}
</script>
</head>
<body>
	
	<form:form method="post" id="batS01Form" commandName="batch" action="BAT_S01.html">
	<h1 class="header"><spring:message code='menu.Batch'/></h1>
		<page:message item="${batch}" />
		<table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
			<tr>		      	
				<th width="12%" class="label">Batch Name</th>
		        <td width="35%" ><form:select id="selectBatch" path="batchId" items="${bacthMap}" /></td>
			</tr>
			<tr>		      	
				<th width="12%" class="label">Execute date</th>
		        <td width="35%"><form:input title="Execute date" path="executeDate" id="executeDate" cssClass="date" tabindex="3"  /></td>
			</tr>
			<tr>		      	
				<th width="12%" class="label">Batch Status</th>
		        <td width="35%" id="status">
			        <c:choose>
			        	<c:when test="${batch.batchStatus == 0}">
					        <strong class="textgreen">Success</strong> 
					    </c:when>
					    <c:when test="${batch.batchStatus == 1}">
							<strong class="textblue">Running...</strong>
						</c:when>
					    <c:when test="${batch.batchStatus == 2}">
					        <strong class="textred">Fail</strong>
					    </c:when>				    
					    <c:when test="${batch.batchStatus == 3}">
					        <strong class="textorange">Success with error.</strong>
					    </c:when>
					</c:choose>			    
				</td>
			</tr>
			<tr>		      	
				<th width="12%" class="label">Last Run Time</th>
		        <td width="35%"><div id="finishDateTime"><fmt:formatDate value="${batch.finishDate}" pattern="dd/MM/yyyy HH:mm:ss" /></div></td>
			</tr>
			<tr>		      	
				<th width="12%" class="label">Last Run By</th>
		        <td width="35%" id="runBy"><c:out value="${batch.runBy}"/></td>
			</tr>
		</table>
		<table width="100%">
			<tr>
				<td align="right">
					<c:if test="${batch.batchStatus == 1}">
    					<input name="btnRun" type="button" id="btnRun" value="Run Batch" disabled="disabled"/>
    				</c:if>
					<c:if test="${batch.batchStatus != 1}">
    					<input name="btnRun" type="button" id="btnRun" value="Run Batch" />
    				</c:if>
  				</td>
			</tr>
		</table>

		<table id="tableStatus" width="40%" border="1" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content"
			style="display: none">
			<tr id="rowHeader">
				<th>Batch Name</th>
				<th>Batch Status</th>
			</tr>
		</table>
	</form:form>
</body>
</html>