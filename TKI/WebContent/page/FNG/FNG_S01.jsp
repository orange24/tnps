<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
String contextPath = request.getContextPath();
%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script src="page/FNG/FNG_S01.js" language="javascript"></script>
</head>
<body>
<h1><spring:message code='menu.FGStockIn/Out'/></h1>
  <ul id="navlist">
    <li><a href="FNG_S01.html" id="current">FG Stock In/Out</a></li>
    <li><a href="FNG_S02.html" >FG Stock In/Out History</a></li>
  </ul>
  
  <page:message item="${tfg}" />

<form id="fngForm" method="post">
  <table width="100%" border="0" cellpadding="3" cellspacing="1">
    <tr>
      <td>
        <table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
          <tr>
         	<th width="13%">Report Date&nbsp;<span class="textred">*</span></th>
            <td width="29%">
            	<form:input path="tfg.reportDate" id="reportDate" tabindex="2" size="10" cssClass="date"  />
            </td> 
            <th width="13%">FG Stock Type&nbsp;<span class="textred">*</span></th>
            <td width="32%">
            	<form:radiobutton path="tfg.fgStockType" disabled="${tfg.fgStockType != null}" id="rdiIn" value="fgin" tabindex="1"  checked="checked"/> IN
            	<form:radiobutton path="tfg.fgStockType" disabled="${tfg.fgStockType != null}" id="rdiIn" value="fgout" /> OUT
            </td>
            <td align="right">
              <input type="submit" value="OK" id="btnOk" class="submit_button" tabindex="4"
              	<c:if test="${not empty tfg.fgStockType}">
              		disabled="true"
              	</c:if>
              />
            </td>       
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <br />
  <c:if test="${not empty tfg.fgStockType}">
  <div>
    	<span class="textred">* Required Field</span>
        <table width="100%" border="1" align="center" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content"  id="dynamictbl">
    	  <tr>
    	    <th rowspan="2" width="4%">
    	      No.
    	    </th>
    	    <th rowspan="2" width="12%">
    	      Lot Seq.  <span class="textred">*</span><br/>(Lot 18 Digits)
   	        </th>
    	    <th rowspan="2" width="20%">
    	      Customer <span class="textred">*</span>
    	    </th>
    	    <th rowspan="2" width="30%">
    	      FG No : FG Name <span class="textred">*</span>
    	    </th>
    	    <th rowspan="2" width="10%">
    	      Type <span class="textred">*</span>
    	    </th>
    	    <th colspan="2" width="16%">
    	      Stock FG <span class="textred">*</span>
    	    </th>
    	    <th rowspan="2" width="4%">
    	      Action
    	    </th>
  	      </tr>
          <tr>
            <th>
              In
            </th>
            <th>
              Out
            </th>
          </tr>
          <tr>
            <td align="center" id="idx">1.</td>
            <td>
              	<input name="lotSeqNo" type="text" id="lotSeqNo" onkeyup="checkscanlot(this)" tabindex="18" size="22" title="Lot Sequence No." />
            	<input name="tagId" type="hidden" id="tagId" />
            </td>
            <td>
            	<div align="left" id="customerCode">&nbsp;</div>
            	<input type="hidden" id="hideCustId" name="hideCustId"/>
            	<input type="hidden" id="hideCustCode" name="hideCustCode"/>
            </td>
            <td>
             	<div align="left" id="fgId">&nbsp;</div>
             	<input type="hidden" id="hideFgId" name="hideFgId"/>
             	<input type="hidden" id="hideFgNo" name="hideFgNo"/>
            </td>
            <td>
             	<select name="reportType" id="reportType" title="Type">
	                <c:forEach items="${reportTypeList}" var="item">
	                  	<option value="${item.typeCode}" class="${item.report}">${item.typeName}</option>
	                </c:forEach>
           	   	</select>  
            </td>
            <td align="center">
              <input name="fgIn" type="text" id="fgIn" tabindex="17" maxlength="10" size="5" title="FG In/Out" onkeypress="return fngIntegerFilter(event)" onchange="getTotalFgIn()" />
            </td>
            <td align="center">
              <input name="fgOut" type="text" id="fgOut" tabindex="17" maxlength="10" size="5" title="FG In/Out" onkeypress="return fngIntegerFilter(event)" onchange="getTotalFgOut()"/>
            </td>
            <td align="center">
              <img id="delRow" name="delRow" src="image/icon/delete.gif" width="16" height="16" class="delete"/>
            </td>
          </tr>                    
        </table>
        <table width="100%" border="1" align="center" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content"  id="totaltbl">
        	<tr>
	            <td align="right" width="76%">
	             	<b>Total</b>
	            </td>
	            <td align="center"  width="8%">
	              <div id="sumFgIn"  class="text-bold">0</div>
	            </td>
	            <td align="center"  width="8%">
	              <div id="sumFgOut" class="text-bold">0</div>
	            </td>
	            <td align="center" width="4%">
	            </td>
        	
        	</tr>
        </table>
	</div>
	<br/>
	<div align="right">
		<input name="btnAdd" type="button" class="submit_button" id="btnAdd" value="Add New Row"/>
    	<input name="btnSave" type="button" class="submit_button" id="btnSave" value="Save"/>
	</div>
  </c:if>
</form>
</body>
</html>
