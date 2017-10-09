<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
var btnSearch;
var mldS01Form;

$(document).ready(function(){
	btnSearch = $("#btnSearch");
	mldS01Form = $("#mldS01Form");
	btnSearch.click(function(){
		mldS01Form.attr("action","MLD_S01_search.html");
		/* mldS01Form.submit(); */
		document.getElementById("mldS01Form").submit();
	});
	
	// export excel
	$("input#btnExport").click(function(){

		downloadNotify($("<div title='<spring:message code='downloadAlertTitle'/>'><spring:message code='downloadAlertContent'/></div>"));

		// <!-- CALL: 'MLD_S01Controller'. -->
		mldS01Form.attr("action", "MLD_R01_export.xls");
		mldS01Form.submit();
		mldS01Form.attr("action","MLD_S01_search.html");
	});
});

function fnDelete( row ) {
	message.clear();
	var rowNo = row.find("td:first-child").html().trim();
	var moldName = row.find("td:eq(4)").html().trim();
	var moldNo = row.find("td:eq(5)").html().trim();
	var idRow = row.attr("id").split(",");
	
	var isRelate = false;
	var params = $.param({
	    "mDetailList[0].moldId": idRow[0],
		"mDetailList[0].moldNo": idRow[1],
		"mDetailList[0].moldName": idRow[2]
	});
	postJSONSync("checkRelateMold",params,function(result){
		if (result) {
			isRelate = true;
		}
	});
	if (isRelate) {
		message.setErrors([{"code": "err.mm.001", "arguments": [idRow[2],idRow[1]]}]);
		return;
	}
	if( !confirm("<spring:message code='cfm.mm.001'/>".replace(/\{0\}/g, moldName).replace(/\{1\}/g, moldNo)) ){
		return;
	}
	mldS01Form.attr("action", "MLD_S01_delete.html");
	mldS01Form.append("<input type='hidden' name='mDetailList[0].moldId' value='"+ idRow[0] +"'/>");
	mldS01Form.append("<input type='hidden' name='mDetailList[0].moldNo' value='"+ idRow[1] +"'/>");
	mldS01Form.append("<input type='hidden' name='mDetailList[0].moldName' value='"+ idRow[2] +"'/>");
	mldS01Form.submit();
}
</script>
</head>
<body>
	<form:form id="mldS01Form" method="post" action="MLD_S01_search.html" commandName="mDetail" >
		<h1><spring:message code='menu.MoldMaster'/></h1>
		<div id="navcontainer">
			<ul id="navlist">
				<li><a href="MLD_S01.html" id="current">Mold Search/List</a></li>
				<li><a href="MLD_S02.html" >Mold Add/Edit</a></li>
				<li><a href="MLD_S03.html" >Mold History Search/List</a></li>
			</ul>
		</div>
		
		<page:message item="${mDetail}" />
		<br />
		
		<table width="100%" border="0" cellpadding="3" cellspacing="1">
			<tr>
				<td >
					<table class="ui-widget ui-widget-content" cellpadding="3" cellspacing="1" width="100%" border="0">
			      		<tr>
					        <th align="center">Customer</th>
					        <td><form:select path="customerId" items="${custMap}" /></td>
							<th align="center" >Status</th>
							<td align="left" >
								<form:select path="statusActive">
									<form:option value="0">-- All --</form:option>
									<form:option value="1">Active</form:option>
									<form:option value="2">Inactive</form:option>
								</form:select>
								<!-- Status FG & DC -->
								<form:checkbox path="status" value="1" />Normal
								<form:checkbox path="status" value="2" />Over Alert.
								<form:checkbox path="status" value="3" />Over Guarantee.
							</td>
						</tr>
						<tr>
						    <th align="center" >Mold Name</th>
						    <td align="left"><form:input path="moldName" /></td>
							<th align="center" >Mold No</th>
							<td align="left"><form:input path="moldNo" /></td>
						</tr>
						<tr>
							<th align="center" >Part No</th>
							<td align="left"><form:input path="partNo" /></td>
							<th align="center" >Part Name</th>
							<td align="left"><form:input path="partName" /></td>
			      		</tr>
			  		</table>
			  	</td>
			</tr>
		  	<tr>
		    	<td align="right" width="48%">
		    		<security:authorize ifAnyGranted="MLD_R01_EXPORT">
		    			<input type="button" value="Summary Report" id="btnExport" style="width:150px"/>
					</security:authorize>
					<input id="btnSearch" type="button" value="Search" style="width:100px"/>
		    	</td>
		  	</tr>
		</table>
		<br />
		<c:if test="${fn:length(mDetail.mDetailList)>0}">
			<table cellpadding="3" cellspacing="1" border="0" width="100%" class="tableBorder2">
			  	<tr>
			    	<td >
			      		<table width="100%" border="1" align="center" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content" >
			        		<tr>
				     			<td colspan="16">
					  				<div style="float:left" ><page:display item="${mDetail}"/></div>
					  				<div style="float:right"><page:number item="${mDetail}"/></div>
				     			</td>
				    		</tr>
							<tr>
								<th rowspan="2" align="center"  >No.</th>
								<th rowspan="2" align="center"  >Customer</th>
								<th rowspan="2" align="center"  >Part No</th>
								<th rowspan="2" align="center"  >Part Name</th>
								<th rowspan="2" align="center"  >Mold Name</th>
								<th rowspan="2" align="center"  >Mold No.</th>
								<th rowspan="2" align="center"  >Cav No.</th>
								<th rowspan="2" align="center"  >Alert Shot</th>
								<th rowspan="2" align="center"  >Guarantee Shot</th>
								<th rowspan="2" align="center"  >Status</th>
								<th colspan="2" align="center"  >DC Shot<span class="textred">*</span></th>
								<th colspan="2" align="center"  >FG Sold<span class="textred"> **</span></th>
								<th rowspan="2" align="center"  >Detail</th>
							</tr>
					        <tr>
								<th align="center"  >Shot </th>
								<th align="center"  >Status<span class="msg_error"></span></th>
								<th align="center"  >Shot </th>
								<th align="center"  >Status<span class="msg_error"></span></th>
					        </tr>
			        		<c:forEach items="${mDetail.mDetailList}" var="moldDetail" varStatus="status" >
			        			<tr id="${moldDetail.moldId},${moldDetail.moldNo},${moldDetail.moldName}" >
									<td align="center"  ><page:rowno item="${mDetail}" index="${status.index}"/></td>
									<td align="center"  >${moldDetail.customerCode}&nbsp;</td>
									<td align="center"  >${moldDetail.partNo}&nbsp;</td>
									<td align="left"  >${moldDetail.partName}&nbsp;</td>
									<td align="center"  >${moldDetail.moldName}</td>
									<td align="center"  >${moldDetail.moldNo}</td>
									<td align="center"  >${moldDetail.cavNo}</td>
									<td align="center"  >${moldDetail.alertShot}</td>
									<td align="center"  >${moldDetail.guaranteeShot}</td>
									<td align="center"  >
										<c:if test="${moldDetail.statusActive==1}"><font class="textblue" >Active</font></c:if>
										<c:if test="${moldDetail.statusActive==2}"><font class="textred" >Inactive</font></c:if>&nbsp;
									</td>
									<td align="center"  >${moldDetail.totalDCShot}&nbsp;</td>
									<td align="center"  >
										<c:if test="${moldDetail.dcStatus==1}"><font class="textblue">Normal</font></c:if>
										<c:if test="${moldDetail.dcStatus==2}"><font class="textred">Over Alert.</font></c:if>
										<c:if test="${moldDetail.dcStatus==3}"><font class="textred">Over Guarantee.</font></c:if>&nbsp;
									</td>
									<td align="center"  >${moldDetail.totalFGSold}&nbsp;</td>
									<td align="center"  >
										<c:if test="${moldDetail.fgStatus==1}"><font class="textblue">Normal</font></c:if>
										<c:if test="${moldDetail.fgStatus==2}"><font class="textred">Over Alert.</font></c:if>
										<c:if test="${moldDetail.fgStatus==3}"><font class="textred">Over Guarantee.</font></c:if>&nbsp;
									</td>
									<td align="center"  >
										<a href="MLD_S02_edit_page.html?action=editAction&moldId=${moldDetail.moldId}&moldNo=${moldDetail.moldNo}"><img src="image/icon/update.gif" width="16" height="16" /></a>
										<a href="javascript:void(0);" onclick="fnDelete( $(this).closest('tr') );" ><img src="image/icon/delete.gif" width="16" height="16" /></a>
										<a href="MLD_S03_search.html?page=viewHist&customerId=${moldDetail.customerId}&partId=${moldDetail.partId}&moldId=${moldDetail.moldId}&moldNo=${moldDetail.moldNo}"><img src="image/icon/page_find.gif" width="16" height="16" /></a>
									</td>
			        			</tr>
			        		</c:forEach>
					        <tr>
								<td colspan="16">
									<div style="float:left" ><page:display item="${mDetail}"/></div>
									<div style="float:right"><page:number item="${mDetail}"/></div>
								</td>
						    </tr>
			    		</table>
			    	</td>
			  	</tr>
			  	<tr>
			    	<td>
			    		<div align="right" class="ui-state-error-text"><span class="msg_error">* DC Shot </span>count from Diecast include NG<span class="msg_error"><br />
			    		** FG Sold c</span>ount from Finish Good Sale 
			    		</div>
			    	</td>
			  	</tr>
			</table>
		</c:if>
	</form:form>
</body>
</html>
