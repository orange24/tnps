<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script src="page/FNG/FNG_S02.js" language="javascript"></script>
</head>
<body>
<h1><spring:message code='menu.FGStockIn/Out'/></h1>
  <ul id="navlist">
    <li><a href="FNG_S01.html" >FG Stock In/Out</a></li>
    <li><a href="FNG_S02.html" id="current">FG Stock In/Out History</a></li>
  </ul>
  <page:message item="${searchCriteria}" />
  
<form:form id="fngForm" methodParam="post" action="FNG_S02_search.html" commandName="searchCriteria">
  <table width="100%" border="0" cellpadding="3" cellspacing="1">
    <tr>
      <td colspan="2"><table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
        <tr>
          <th width="20%">Report Date(From - To)</th>
          <td width="39%" >
            <form:input path="reportDateFr" id="dateFrom" tabindex="1" cssClass="date" title="Report Date From"/> &nbsp; - &nbsp; 
            <form:input path="reportDateTo" id="dateTo" tabindex="2" cssClass="date" title="Report Date To"/>
          </td>
          <th width="12%" >Customer</th>
          <td width="29%" >
            <form:select path="customerId" id="customerId" tabindex="3">
              <form:options items="${customerMap}"/>
            </form:select>
          </td>
        </tr>
        <tr>
          <th>FG No</th>
          <td><form:input path="fgNo" id="fgNo" tabindex="4"/></td>
          <th>FG Name</th>
          <td><form:input path="fgName" id="fgName" tabindex="5"/></td>
        </tr>
        <tr>
          <th>Report Type</th>
          <td>
            <form:select path="reportType" id="selectType">
              <form:option value="">-- All Types --</form:option>
              <form:options items="${reportTypeList}" itemLabel="typeName" itemValue="typeCode"/>
            </form:select>
          </td>
          <th>Nirvana Export Status</th>
          <td>
            <form:select path="nirvanaExportStatus" id="nirvanaExportStatus">
              <form:option value="">-- All Types --</form:option>
              <form:option value="Y">Exported</form:option>
              <form:option value="N">Not Export</form:option>
            </form:select>
          </td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td colspan="2" align="right">
      	<security:authorize ifAnyGranted="FNG_R01_EXPORT">
      		<input name="btnExport" type="button" id="btnExportNirvana" value="Export to Nirvana"/>
      		<input name="btnExport" type="button" id="btnExport2" value="History Report"/>
        	<input name="btnExport" type="button" id="btnExport" value="Summary Report"/>
        </security:authorize>
        <input name="btnSearch" type="button" id="btnSearch" value="Search" tabindex="7" style="width:100px"/>
      </td>
    </tr>
  </table>
  <br/>
  <c:if test="${not empty searchResult.details}">
  <table id="dynamictbl" border="1" cellpadding="3" cellspacing="0" width="100%" class="ui-widget ui-widget-content">
    <tr>
      <td colspan="10">
        <div style="float:left" ><page:display item="${searchCriteria}"/></div>
        <div style="float:right" ><page:number item="${searchCriteria}"/></div>
      </td>
    </tr>
    <tr>
      <th rowspan="2">Report Date</th>
      <th rowspan="2">Customer</th>
      <th rowspan="2">FG No</th>
 	  <th rowspan="2">FG Name</th>
 	  <th rowspan="2">Report Type</th>
 	  <th rowspan="2">Lot Seq No</th>
 	  <th rowspan="2">Mold No</th>
 	  <th colspan="2">Stock FG</th>
 	  <th rowspan="2">Update By</th>
 	  <th rowspan="2">Last Update</th>
    </tr>
    <tr>
      <th>In</th>
      <th>Out</th>
    </tr>
    <c:forEach var="row" items="${searchResult.details}">
      <tr>
        <td class="border_all" align="center"><fmt:formatDate value="${row.reportDate}" pattern="dd/MM/yyyy"/></td>
        <td class="border_all" align="left"  >${row.customerCode}</td>
        <td class="border_all" align="left"  >${row.fgNo}</td>
        <td class="border_all" align="left"  >${row.fgName}</td>
        <td class="border_all" align="center">${reportTypeMap[row.reportType]}&nbsp;</td>
        <td class="border_all" align="left"  >${row.lotSeqNo}</td>
        <td class="border_all" align="left"  >${row.moldNo}</td>
        <td class="border_all" align="right" >&nbsp;<fmt:formatNumber value="${row.fgIn}"/></td>
        <td class="border_all" align="right" >&nbsp;<fmt:formatNumber value="${row.fgOut}"/></td>
        <td class="border_all" align="center">${row.updateBy}&nbsp;</td>
        <td class="border_all" align="center"><fmt:formatDate value="${row.lastUpdate}" pattern="dd/MM/yyyy HH:mm:ss"/>&nbsp;</td>
      </tr>
    </c:forEach>
    <tr>
      <td colspan="10">
        <div style="float:left" ><page:display item="${searchCriteria}"/></div>
        <div style="float:right" ><page:number item="${searchCriteria}"/></div>
      </td>
    </tr>
  </table>
  </c:if>
</form:form>
</body>
</html>
