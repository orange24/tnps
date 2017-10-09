<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var ldts01Form;
	var btnSearch;
	var btnSave;
	var tblDeltail;
	var index;
	var tr;
	var selectSt;
	var st1Qty;
	var st1Sec;
	var st1Result;			
	var st2Qty;
	var st2Sec;
	var st2Result;
	var st3Qty;
	var st3Sec;
	var st3Result;
	var st4From;
	var st4To;
	var st5From;
	var st5To;
	var st6From;
	var st6To;
	var option;
	var no;	
	
	$(document).ready(function(){
		ldts01Form		= $("#ldts01Form"); 
		btnSearch		= $("#btnSearch");
		btnSave			= $("#btnSave");
				
		btnSearch.click(function(){
			ldts01Form.attr("action","LDT_S01_search.html");
			ldts01Form.submit();		
		});	
		btnSave.click(function(){			
			tblDeltail 	= $("table > tbody");
			index 		= 0;
			var errors 	= [];
			
			tblDeltail.find("tr[id=leadtimeDetail]").each(function(){
				tr 			= $(this);
				selectSt	= tr.find("td:eq(4)>select[id=leadTimeList"+index+".stUseNo]").attr("selected",true).val();
				st1Qty		= tr.find("td:eq(5)>input[id=leadTimeList"+index+".stQty1]").val();
				st1Sec		= tr.find("td:eq(5)>input[id=leadTimeList"+index+".stSec1]").val();
				st1Result	= tr.find("td:eq(5)>input[id=leadTimeList"+index+".stResult1]").val();			
				st2Qty		= tr.find("td:eq(6)>input[id=leadTimeList"+index+".stQty2]").val();
				st2Sec		= tr.find("td:eq(6)>input[id=leadTimeList"+index+".stSec2]").val();
				st2Result	= tr.find("td:eq(6)>input[id=leadTimeList"+index+".stResult2]").val();
				st3Qty		= tr.find("td:eq(7)>input[id=leadTimeList"+index+".stQty3]").val();
				st3Sec		= tr.find("td:eq(7)>input[id=leadTimeList"+index+".stSec3]").val();
				st3Result	= tr.find("td:eq(7)>input[id=leadTimeList"+index+".stResult3]").val();
				st4From		= tr.find("td:eq(9)>input[id=leadTimeList"+index+".stDateFr4]");
				st4To		= tr.find("td:eq(9)>input[id=leadTimeList"+index+".stDateTo4]");
				st5From		= tr.find("td:eq(11)>input[id=leadTimeList"+index+".stDateFr5]");
				st5To		= tr.find("td:eq(11)>input[id=leadTimeList"+index+".stDateTo5]");
				st6From		= tr.find("td:eq(13)>input[id=leadTimeList"+index+".stDateFr6]");
				st6To		= tr.find("td:eq(13)>input[id=leadTimeList"+index+".stDateTo6]");
				
				if(selectSt != ""){
					if((selectSt === "1")&&(st1Result === "")){
						errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST1 Result"]});
					}else if((selectSt === "2")&&(st2Result === "")){
						errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST2 Result"]});
					}else if((selectSt === "3")&&(st3Result === "")){
						errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST3 Result"]});
					}else if((selectSt === "4")&&((st4From.val() === "")||(st4To.val() === ""))){
						errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST4 Period(From-To)"]});
					}else if((selectSt === "5")&&((st5From.val() === "")||(st5To.val() === ""))){
						errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST5 Period(From-To)"]});
					}else if((selectSt === "6")&&((st6From.val() === "")||(st6To.val() === ""))){
						errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST6 Period(From-To)"]});
					}
				}
				if((st1Result === "")&&(st1Qty !== "")&&(st1Sec === "")){
					errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST1 Sec"]});
				}else if((st1Result === "")&&(st1Qty === "")&&(st1Sec !== "")){
					errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST1 Qty"]});
				}
				if((st2Result === "")&&(st2Qty !== "")&&(st2Sec === "")){
					errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST2 Sec"]});
				}else if((st2Result === "")&&(st2Qty === "")&&(st2Sec !== "")){
					errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST2 Qty"]});
				}
				if((st3Result === "")&&(st3Qty !== "")&&(st3Sec === "")){
					errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST3 Sec"]});
				}else if((st3Result === "")&&(st3Qty === "")&&(st3Sec !== "")){
					errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST3 Qty"]});
				}
				if((st4From.val() !== "")&&(st4To.val() === "")){
					errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST4 Period To"]});
				}else if((st4From.val() === "")&&(st4To.val() !== "")){
					errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST4 Period From"]});
				}
				if((st5From.val() !== "")&&(st5To.val() === "")){
					errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST5 Period To"]});
				}else if((st5From.val() === "")&&(st5To.val() !== "")){
					errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST5 Period From"]});
				}
				if((st6From.val() !== "")&&(st6To.val() === "")){
					errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST6 Period To"]});
				}else if((st6From.val() === "")&&(st6To.val() !== "")){
					errors.push({"code": "err.cmm.001","arguments": ["Line "+(index+1)+":ST6 Period From"]});
				}
				if (st4From.val() && st4To.val() && st4From.datepicker("getDate").getTime() > st4To.datepicker("getDate").getTime()) {
					errors.push({"code":"err.cmm.008", "arguments":["Line "+(index+1)+":ST4 "+st4To.attr("title"),st4From.attr("title")]});
				}
				if (st5From.val() && st5To.val() && st5From.datepicker("getDate").getTime() > st5To.datepicker("getDate").getTime()) {
					errors.push({"code":"err.cmm.008", "arguments":["Line "+(index+1)+":ST5 "+st5To.attr("title"),st5From.attr("title")]});
				}
				if (st6From.val() && st6To.val() && st6From.datepicker("getDate").getTime() > st6To.datepicker("getDate").getTime()) {
					errors.push({"code":"err.cmm.008", "arguments":["Line "+(index+1)+":ST6 "+st6To.attr("title"),st6From.attr("title")]});
				}
				index++;
			});
		
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
			message.clear();
			
			if( confirm("<spring:message code='cfm.cmm.001'/>") ){
				ldts01Form.attr("action","LDT_S01_save.html");
				ldts01Form.submit();
			}
		});
	});	
	
	// parseInt trime zero( 010 = 10) 
	function ParseIntTrimZero( o ){
		var val = $.trim(o.value);
		if(val)
			o.value = parseInt(val || 0,10);
	}
	
	function calResultST( e ){
		var element = $(e.target || e.srcElement);		
		var sec		= element.closest("td").find("input:eq(0)").val();
		var qty		= element.closest("td").find("input:eq(1)").val();		
		var result	= "";
		if((qty !== "")&&(sec !== "")){
			if((sec !== "0")&&(qty !== "0")){
				result = Math.round((sec/qty)*100)/100;
			}else{
				result = 0;
			}
		}
		element.closest("td").find("input:eq(2)").val(result);
	}
</script>
</head>
<body>
<h1><spring:message code='menu.LeadtimeMaster'/></h1>
	<ul id="navlist">
  		<li><a href="LDT_S01.html" id="current">Leadtime Management</a></li>
  		<li><a href="LDT_S02.html">ST Configuration</a></li>
	</ul>
	
	<page:message item="${mLeadtime}" />
	<form:form method="post" id="ldts01Form" commandName="mLeadtime" action="LDT_S01_search.html">
		<table width="100%" border="0" cellpadding="3" cellspacing="1">
			<tr>
				<td>
					<table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
				      	<tr>
					        <th width="12%"><span class="label">WIP </span></th>
					        <td width="34%"><form:select path="wip" id="boxWIP" items="${wipMap}" /></td>
							<th width="16%"><span class="label">Customer</span></th>
							<td width="38%"><form:select name="customerId" path="customerId" id="boxCustom" items="${customerMap}" /></td>
						</tr>
						<tr>
							<th><span class="label">Part No</span></th>
							<td><form:input name="partNo" path="partNo"/></td>
							<th><span class="label">Part Name</span></th>
							<td><form:input name="partName" path="partName"/></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right">
					<input name="btnSearch" type="submit" id="btnSearch" value="Search" />
				</td>
			</tr>
		</table>	
		<br />
	
		<c:if test="${not empty mLeadtime.leadTimeList}">
			<table width="1700" border="1" align="center" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
				<tr>
				  	<td colspan="16">
				  		<div style="float:left" ><page:display item="${mLeadtime}" /></div>
				  		<div style="float:right" ><page:number item="${mLeadtime}" /></div>
				  	</td>
				</tr>
				<tr>
					<th rowspan="2" align="center">WIP</th>
					<th rowspan="2" align="center">Customer</th>
					<th rowspan="2" align="center">Part No</th>
					<th rowspan="2" align="center">Part Name</th>
					<th rowspan="2" align="center">ST Use</th>
					<th rowspan="2" align="center" width="130">ST 1<br />(Second/Qty)</th>
					<th rowspan="2" align="center" width="130">ST 2<br />(Second/Qty)</th>
					<th rowspan="2" align="center" width="130">ST 3<br />(Second/Qty)</th>
					<th colspan="2" align="center">ST 4</th>
					<th colspan="2" align="center">ST 5</th>
					<th colspan="2" align="center">ST 6</th>
	        	</tr>
	        	<tr>
					<th align="center">Second</th>
					<th align="center"width="230">Period<br />(From - To)</th>
					<th align="center">Second</th>
					<th align="center" width="230">Period<br />(From - To)</th>
					<th align="center">Second</th>
					<th align="center" width="230">Period<br />(From - To)</th>
	        	</tr>
	        	<c:forEach var="leadtime" items="${mLeadtime.leadTimeList}" varStatus="status" begin="0" step="1">
	        		<tr id="leadtimeDetail" align="center">
	        			<td>
	        				${leadtime.wip}&nbsp;
	        				<form:hidden path="leadTimeList[${status.index}].wip"/>
	        				<form:hidden path="leadTimeList[${status.index}].leadTimeId"/>
	        			</td>
	        			<td>
	        				${leadtime.customerCode}&nbsp;
	        				<form:hidden path="leadTimeList[${status.index}].customerCode"/>
	        			</td>
	        			<td>
	        				${leadtime.partNo}&nbsp;
	        				<form:hidden path="leadTimeList[${status.index}].partId"/>
	        				<form:hidden path="leadTimeList[${status.index}].partNo"/>
	        			</td>
	        			<td>
	        				${leadtime.partName}&nbsp;
	        				<form:hidden path="leadTimeList[${status.index}].partName"/>
	        			</td>
	        			<td>
	        				<form:select path="leadTimeList[${status.index}].stUseNo">
	        					<form:option value="">--</form:option>
	        					<form:option value="1">ST1</form:option>
	        					<form:option value="2">ST2</form:option>
	        					<form:option value="3">ST3</form:option>
	        					<form:option value="4">ST4</form:option>
	        					<form:option value="5">ST5</form:option>
	        					<form:option value="6">ST6</form:option>
	        				</form:select>
	        			</td>
	        			<td>
	        				<form:input path="leadTimeList[${status.index}].stSec1" onkeypress="return PositiveIntegerFilter(event)" size="3" onchange="ParseIntTrimZero(this);return calResultST(event);"/> 
	        				/ 
	        				<form:input path="leadTimeList[${status.index}].stQty1" onkeypress="return PositiveIntegerFilter(event)" size="3" onchange="ParseIntTrimZero(this);return calResultST(event);"/> 
	        				= 
	        				<form:input path="leadTimeList[${status.index}].stResult1" onkeypress="return PositiveDoubleFilter(event)" size="3"/>
	        			</td>
	        			<td>
	        				<form:input path="leadTimeList[${status.index}].stSec2"  onkeypress="return PositiveIntegerFilter(event)" size="3" onchange="ParseIntTrimZero(this);return calResultST(event);"/> 
	        				/ 
	        				<form:input path="leadTimeList[${status.index}].stQty2" onkeypress="return PositiveIntegerFilter(event)" size="3" onchange="ParseIntTrimZero(this);return calResultST(event);"/> 
	        				= 
	        				<form:input path="leadTimeList[${status.index}].stResult2" onkeypress="return PositiveDoubleFilter(event)" size="3"/>
	        			</td>
	        			<td>
	        				<form:input path="leadTimeList[${status.index}].stSec3" onkeypress="return PositiveIntegerFilter(event);" size="3" onchange="ParseIntTrimZero(this);return calResultST(event);"/> 
	        				/ 
	        				<form:input path="leadTimeList[${status.index}].stQty3" onkeypress="return PositiveIntegerFilter(event)" size="3" onchange="ParseIntTrimZero(this);return calResultST(event);"/> 
	        				= 
	        				<form:input path="leadTimeList[${status.index}].stResult3" onkeypress="return PositiveDoubleFilter(event)" size="3"/>
	        			</td>
	        			<td>
	        				<c:if test="${not empty leadtime.stResult4}">
	        					<fmt:formatNumber pattern="0.00" value="${leadtime.stResult4}" ></fmt:formatNumber>
	        					<form:hidden path="leadTimeList[${status.index}].stResult4" />
	        				</c:if>
	        			</td>
	        			<td>
	        				<form:input title="Period From(Date)" path="leadTimeList[${status.index}].stDateFr4" cssClass="date" size="10" onchange="this.focus();"/> 
	        				- 
	        				<form:input title="Period To(Date)" path="leadTimeList[${status.index}].stDateTo4" cssClass="date" size="10" onchange="this.focus();"/>
	        			</td>
	        			<td>
	        				<c:if test="${not empty leadtime.stResult5}">
	        					<fmt:formatNumber pattern="0.00" value="${leadtime.stResult5}" ></fmt:formatNumber>
	        					<form:hidden path="leadTimeList[${status.index}].stResult5" />
	        				</c:if>
	        			</td>
	        			<td>
	        				<form:input title="Period From(Date)" path="leadTimeList[${status.index}].stDateFr5" cssClass="date" size="10" onchange="this.focus();"/> 
	        				- 
	        				<form:input title="Period To(Date)" path="leadTimeList[${status.index}].stDateTo5" cssClass="date" size="10" onchange="this.focus();"/>
	        			</td>
	        			<td>
	        				<c:if test="${not empty leadtime.stResult6}">
	        					<fmt:formatNumber pattern="0.00" value="${leadtime.stResult6}" ></fmt:formatNumber>        					
	        					<form:hidden path="leadTimeList[${status.index}].stResult6" />
	        				</c:if>
	        			</td>
	        			<td>
	        				<form:input title="Period From(Date)" path="leadTimeList[${status.index}].stDateFr6" cssClass="date" size="10" onchange="this.focus();"/> 
	        				- 
	        				<form:input title="Period To(Date)" path="leadTimeList[${status.index}].stDateTo6" cssClass="date" size="10" onchange="this.focus();"/>
	        			</td>			
	        		</tr>
	        	</c:forEach>
	        	<tr>
				  	<td colspan="16">
				  		<div style="float:left" ><page:display item="${mLeadtime}" /></div>
				  		<div style="float:right" ><page:number item="${mLeadtime}" /></div>
				  	</td>
				</tr>
			</table>
			<table width="1700">
	  			<tr>
	    			<td align="left">
	      				<input name="btnSave" type="button" id="btnSave" value="Save" />
					</td>
	  			</tr>
			</table>
		</c:if>
	</form:form>
	<p>
		ST1 - ST2 =  Standard value<br />
		ST3 = Initial high vloume value<br />
		ST4 - ST6 = Average value in set period
	</p>
</body>
</html>
