<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<title></title>
<%@ include file="../importResources.jsp" %>
<script src="../js/jquery-ui-1.8.6.custom.min.js" type="text/javascript"></script>
<script language="javascript">

var btnSearch;
var btnSave;
var matS01Form;

	//initialze script run
	$(document).ready(function() {  
		
		// <!--check search button -->
		btnSearch = $("#btnSearch");
		matS01Form = $("#matS01Form");
		btnSave = $("#btnSave");
		
		btnSave.click(function(){		
			if( !confirm("<spring:message code='cfm.cmm.001'/>") )
				return;
			$(':text[name$="materialCost"]').each(function(i){
				this.value = this.value.replace(/[,]/g,"");
			});
			matS01Form.attr("action","MAT_S01_save.html");
			matS01Form.submit();
		});
	});
</script>
</head>
<body>
<h1 class="header"><spring:message code='menu.MaterialMaster'/></h1>
<form:form method="post" id="matS01Form" commandName="material" action="MAT_S01_save.html">
<page:message item="${material}"/>
<c:if test="${not empty material.materialList}">
<table width="100%" border="1" align="center" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content" >
  <tr>
  	<th align="center">Material Code</th>
    <th align="center">Material Name</th>
    <th align="center">Material Unit Cost (Bath/Kg)</th>
  </tr>
  <c:forEach items="${material.materialList}" var="materialDetail" varStatus="status" begin="0" step="1"> 
  <tr>
  	<td align="center">${materialDetail.materialCode}</td>
    <td align="left">${materialDetail.materialName}</td>
    <td align="center">
        <input name="materialList[${status.index}].materialCost" onkeyup="addCommas(this);" onkeypress="return PositiveDoubleFilter(event);" value="<fmt:formatNumber pattern="#,##0.000" value="${materialDetail.materialCost}" />"/>
    	<input type="hidden" name="materialList[${status.index}].materialId" value="${materialDetail.materialId}"/>
    </td>
  </tr>
  </c:forEach>
</table>
<!-- security:authorize ifAnyGranted="MAT_S01_SAVE"> -->
	<p align="left">
	  <input name="btnSave" type="button" class="submit_button" id="btnSave" value="Save"/>
	</p>
<!-- /security:authorize> -->
</c:if>
</form:form>
</body>
</html>
