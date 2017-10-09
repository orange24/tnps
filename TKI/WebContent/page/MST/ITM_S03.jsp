<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var chbWipAll;
	var chbWips;
	var txaRemark;
	var btnSave;
	var btnBack;
	var frmItem;
	
	$(document).ready(function() {
  		chbWipAll = $("#chbWipAll");
  		chbWips = $("input[id=chbWip]");
  		txaRemark = $("#txaRemark");
  		btnSave = $("#btnSave");
  		btnBack = $("#btnBack");
  		frmItem = $("#frmItem");
  		
  		chbWipAll.click(function(){
  			chbWips.attr("checked", chbWipAll.attr("checked"));
		});  
  		
  		inputText.textareaMaxLength(txaRemark);
  		
  		btnBack.click(function(){
  			document.location = "ITM_S01_search.html?back=true";
  		});
  		
  		btnSave.click(function(){
  			if (confirm(message.getMessage("cfm.cmm.001")))
  				frmItem.submit();
  			else
  				return false;
  		});
	});	
</script>
</head>
<body>
<h1><spring:message code='menu.FG/PartMaster'/></h1>
<ul id="navlist">
  	<li><a href="ITM_S01.html" id="current">Part Search/List</a></li>
  	<li><a href="ITM_S02.html">Part Sync From TPics</a></li>
</ul>

<page:message item="${mPart}"/>

<form:form id="frmItem" method="post" action="ITM_S03_save.html" commandName="mPart">
	<form:hidden path="partId"/>
	<form:hidden path="fgId"/>
	<form:hidden path="customerId"/>
	<input type="hidden" name="lastUpdate" value="<fmt:formatDate value="${mPart.lastUpdate}" pattern="dd/MM/yyyy HH:mm:ss.SSS"/>"/>
	
	<table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
        <tr>
          	<th width="12%">Customer</th>
          	<td width="23%">${mPart.customerCode}</td>
        </tr>
        <tr>
          	<th>FG No. / Name</th>
          	<td>${mPart.fgNo} / ${mPart.fgName}</td>
        </tr>
        <tr>
          	<th>Part No. / Name</th>
          	<td>${mPart.partNo} / ${mPart.partName}</td>
        </tr>
        <tr>
          	<th>Process</th>
          	<td>
          		<table width="100%" border="1" cellspacing="0" cellpadding="2" class="ui-widget ui-widget-content">
            		<tr>
              			<th width="14%">
              				WIP Calc.<br/><input type="checkbox" id="chbWipAll"/>
              			</th>
              			<th width="29%">Process Order</th>
              			<th width="57%"><div align="left">Process Name</div></th>
            		</tr>
            		<c:forEach items="${mPart.wipList}" var="row" varStatus="loop">
            		<tr>
              			<td align="center">
              				<input type="checkbox" id="chbWip" name="wipList[${loop.index}].wip.isCalc" 
              					value="true" <c:if test="${row.wip.isCalc == true}">checked</c:if>/>
                			<input type="hidden" name="wipList[${loop.index}].wip.wip" value="${row.wip.wip}"/>
                			<input type="hidden" name="wipList[${loop.index}].wip.wipOrder" value="${row.wip.wipOrder}"/>
              			</td>
              			<td align="center">${row.wip.wipOrder}</td>
              			<td align="left">${row.wip.wip} : ${row.wip.wipName}</td>
            		</tr>
            		</c:forEach>
          		</table>
          	</td>
		</tr>
        <tr>
        	<th>Remark</th>
        	<td><form:textarea path="remark" id="txaRemark" cols="45" rows="5"/></td>
        </tr>
        <tr>
        	<th>Sync Date</th>
        	<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${mPart.createDate}"/></td>
        </tr>
        <tr>
          <th>Last Update</th>
          <td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${mPart.lastUpdate}"/></td>
        </tr>
    </table>
    <div align="left"><span class="ui-state-error-text">*</span> Required Field<br/></div>
    <div align="right">
      	<input type="button" id="btnSave" value="Save"/>
      	<input type="button" id="btnBack" value="Back"/>
    </div>
</form:form>
</body>
</html>
