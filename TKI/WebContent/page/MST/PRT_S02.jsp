<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
<%@ include file="../importResourcesSlickgrid.jsp"%>
<script type="text/javascript" src='page/MST/PRT_S02.js'></script>
<title>Insert title here</title>
</head>
<body>
<h1 class="header"><spring:message code='menu.PartMaster'/></h1>
<form:form id="fgMstForm"  commandName="mfgpart">	
 <div id="gridJs"></div>

  <table cellpadding="3" cellspacing="1" border="0" width="100%">
	<tr>
		<td ><div id="navcontainer">
			<ul id="navlist">
				<!-- CSS Tabs -->
				<li><a href="PRT_S01.html" >Part Master</a> </li>
				<li><a href="PRT_S02.html" id="current">FG Part Mapping</a> </li>
			</ul>
		</div></td>
	</tr>
</table>
<table width="100%" border="0">
  <tr>
    <td >
        <page:message item="${mfgpart}" /> 
    </td>
  </tr>
</table>
<table width="100%" border="0" cellpadding="3" cellspacing="1">
  <tr>
    <td colspan="2"><table cellpadding="3" cellspacing="1" width="100%" border="0" class="ui-widget ui-widget-content">
      <tr>
        <th class="label" width="17%">Customer <span class="textred">*</span></th>
			<td colspan="3">
			
	    	<form:select tabindex="1" path="customerId" items="${custMap}" id="customerNameSel" ></form:select>
			
			</td> 
		</tr>
		<tr>
			<th class="label" width="17%">FG No.</th>
			<td ><input type="text" id="fgNo" /></td> 
			<th class="label" width="17%">FG Name</th>
			<td ><input type="text" id="fgName" /></td> 
		</tr>
    </table>
      <div align="right"></div></td>
  </tr>
  <tr>
    <td width="44%"></td>
    <td width="56%"><div align="right">
      <input name="btnSearch" type="button" class="submit_button" id="btnSearch" value="Search"  />
    </div></td>
  </tr>
</table>
<div id="searchGrid">
<div class="grid-header">
	<label>FG Part Mapping</label>
	<label id="searchIcon" style="float:right; margin-right:10px">Search</label>
	<span id="searchIcon" class="ui-icon ui-icon-search"  style="float:right" title="Search panel"></span>
</div>
<div>
	<div id="fgpartGrid" class="grid-detail"
		style="width: 99.8%; height: 260px; overflow: hidden; outline: 0px none; position: relative;"></div>
</div>

<div align="right">
	  <input name="btnSave" type="button" class="submit_button" id="btnSave" value="Save"  />
    </div>
    </div>
<br/>
<div id="partDialog" title="Part Master" style="display: none;">
	<div class="grid-header">
		<label>Part Master</label>
		<label id="searchIcon" style="float:right; margin-right:10px">Search</label>
		<span id="searchIcon" class="ui-icon ui-icon-search"  style="float:right" title="Search panel"></span>
	</div>
	<div>
		<div id="partGrid" class="grid-detail"
			style="width: 99.8%; height: 350px; overflow: hidden; outline: 0px none; position: relative;"></div>
	</div>
</div>
</form:form>
</body>
</html>