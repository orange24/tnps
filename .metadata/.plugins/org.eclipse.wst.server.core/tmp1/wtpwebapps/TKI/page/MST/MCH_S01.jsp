<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<title></title>
<%@ include file="../importResourcesSlickgrid.jsp" %>
 <script type="text/javascript" src='page/MST/MCH_S01.js'></script> 
</head>
<body>
<h1 class="header"><spring:message code='menu.MachineMaster'/></h1>
	<form:form method="post" id="form" commandName="machine" action="MCH_S01_search.html">
		<ul id="navlist">
		     <li><a href="MCH_S01.html" id="current">Machine Search/List</a> </li>
		     <li><a href="MCH_S02.html" >Machine Add/Edit</a> </li>
		</ul>
		<page:message item="${machine}" />
		<table width="100%" border="0" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
			<tr>
				<th width="8%" class="label">WIP</th>
				<td width="40%"><form:select id="selectWip" path="wip" items="${wipMap}"/></td>
				<th width="12%">Machine No</th>
				<td width="40%"><div align="left"><form:input path="machineNo" /></div></td>
			</tr>
		</table>
		<table width="100%">
			<tr>
				<td align="right">
					<security:authorize ifAnyGranted="FNG_S02_EXPORT">        
        				<input name="btnExport" type="submit" class="submit_button" id="btnExport" value="Export Machine"/>
        			</security:authorize>
			      	<input name="btnSearch" type="button"  id="btnSearch" value="Search" />
			    </td>
			</tr>
		</table>
		<br />
		<c:if test="${fn:length(machine.machineList) >0}">
			<table id="tblResult" width="100%" border="1" cellpadding="3" cellspacing="1" class="ui-widget ui-widget-content">
			  <tr>
			  	<td colspan="10">
			  		<div style="float:left" ><page:display item="${machine}" /></div>
			  		<div style="float:right" ><page:number item="${machine}" /></div>
			  	</td>
			  </tr>
			  <tr >
			  	<th>No.</th>
				<th>WIP</th>
		        <th>Machine No.</th>
		        <th>Machine Name</th>
		        <th>Machine Cost</th>
		        <th>Remark</th>
		        <th>Start Date - End Date</th>
		        <th>Mapping Part</th>
		        <th>Copy Mapping Part</th>
		        <th>Change Machine</th>
		         
			  </tr>
			  <c:forEach items="${machine.machineList}" varStatus="status" var="m_machine" begin="0" step="1">
				  <tr id="${m_machine.machineId}" align="center">
				  	<td align="center"><page:rowno item="${machine}" index="${status.index}"/></td>
				    <td>${m_machine.wip}&nbsp;</td>
				    <td>${m_machine.machineNo}&nbsp;</td>
				    <td>${m_machine.machineName}&nbsp;</td>
				    <td align="right"><fmt:formatNumber pattern="#,##0.000" value="${m_machine.machineCost}" />&nbsp;</td>
				    <td>${m_machine.remark}&nbsp;</td>				    
				    <td><fmt:formatDate value="${m_machine.startDate}" pattern="dd/MM/yyyy"/> - <fmt:formatDate value="${m_machine.endDate }" pattern="dd/MM/yyyy"/></td>
				   <td>
				   <img src="image/icon/page_find.gif" border="0"  id="${m_machine.machineId}" onclick='OpenPopupMapping(this.id)'/>
				   </td>
				   <td><img src="image/icon/dhtmlgoodies_sheet.gif" border="0"  id="${m_machine.machineId}" onclick='OpenPopupCopyPartMapping(this.id)'/>
				   </td>
				    <td>
				    	<a href="MCH_S02_edit.html?machineId=${m_machine.machineId}"><img src="image/icon/update.gif" border="0"/></a> 	
				    	<a href="javascript:void(0);" onclick="fnDelete( $(this).closest('tr') );" ><img src="image/icon/delete.gif" border="0"/></a>
	    				&nbsp;			    	
				    </td>
				  </tr>
  			 </c:forEach>
  			 		<tr>
						<td colspan="10">
							<div style="float:left" ><page:display item="${machine}" /></div>
							<div style="float:right" ><page:number item="${machine}" /></div>
						</td>
 					</tr>
			 </table>
		</c:if>
  		<div id="partMap" title="Mapping Part" style="display: none;">
			<table width="100%" border="0">
				<tr>
					<td><page:message item="${machine}" /></td>
				</tr>
			</table>
			<br />
			<div class="grid-header">
		<label>Select Part</label>
		<label id="searchIcon" style="float:right; margin-right:10px">Search</label>
		<span id="searchIcon" class="ui-icon ui-icon-search"  style="float:right" title="Search panel"></span>
	</div>
	<div>
		<div id="mappingPartGrid" class="grid-detail"
			style="width: 99.8%; height: 350px; overflow: hidden; outline: 0px none; position: relative;"></div>
	</div>
</div>

<div id="machineDialog" title="Copy Part Mapping" style="display: none;">
<table width="100%" border="0">
  <tr>
    <td><page:message item="${machine}"/>
    </td>
  </tr>
</table><br/>
			<table class="ui-widget ui-widget-content " cellpadding="3"
				cellspacing="1" width="100%" border="0">
				<tr>
					<td><form:select tabindex="1" path="wipDialog" items="${wipList}" name="wipDialog"
							id="wipDialog"></form:select>
							</td>
					<td align="right"   id="btnCol">

					</td>
				</tr>
			</table>
			<div class="grid-header">
		<label>Select Machine Destination</label>
		<label id="searchIcon" style="float:right; margin-right:10px">Search</label>
		<span id="searchIcon" class="ui-icon ui-icon-search"  style="float:right" title="Search panel"></span>
	</div>
	<div>
		<div id="machineGrid" class="grid-detail"
			style="width: 99.8%; height: 300px; overflow: hidden; outline: 0px none; position: relative;"></div>
	</div>
</div>

	</form:form>
</body>
</html>