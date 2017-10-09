<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="../importResourcesSlickgrid.jsp" %>

<html>
<head>
<script type="text/javascript" src="page/MST/PRT_S01.js"></script>
</head>
<body>
<h1 class="header"><spring:message code='menu.PartMaster'/></h1>
<!-- Declare form for handling events -->
<form:form id="partMCForm" action="PRT_S01_export.xls" methodParam="post" commandName="mPart">
  <table cellpadding="3" cellspacing="1" border="0" width="100%">
	<tr>
		<td ><div id="navcontainer">
			<ul id="navlist">
				<li><a href="PRT_S01.html" id="current">Part Master</a> </li>
				<li><a href="PRT_S02.html" >FG Part Mapping</a> </li>
			</ul>
		</div></td>
	</tr>
</table>

<!-- Show errors messages if errors found -->
<table width="100%" border="0">
  <tr>
    <td >
    	<page:message item="${mPart}" />
    </td>
  </tr>
</table>
<br />

<!-- Create grid header for filtering grid -->
<div class="grid-header">
	<label>Part Master</label>
	<label id="searchIcon" style="float:right; margin-right:10px">Search</label>
	<span id="searchIcon" class="ui-icon ui-icon-search"  style="float:right" title="Search panel"></span>
</div>

<!-- Declare the grid  -->
<div>
	<div id="partGrid" class="grid-detail" style="width: 99.8%; height: 350px; overflow: hidden; outline: 0px none; position: relative;"/>
</div>

<!-- Create buttons for making CRUD functions -->
<div align="right">
      <input id="btnExportPart" type="button" class="submit_button"  value="Export Part" />
	  <input id="btnSavePart"   type="button" class="submit_button"  value="Save"  />
    </div>
</form:form>

<!-- Create form for exporting grid data -->
<form:form id="exportForm" commandName="mPart" action="PRT_S01_export.xls" method="POST"></form:form>

</body>
</html>