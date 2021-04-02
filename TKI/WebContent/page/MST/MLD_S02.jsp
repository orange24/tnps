<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags"%>
<html>
<head>
<title></title>
<%@ include file="../importResources.jsp" %>
<script language="javascript">
var customerIdSel;
var partNameBoxId;
var partNoBoxId;
var btnSearchPart;
var btnSave;
var moldNameBox;
var moldNoBox;
var qtyShotBox;
var cavNoBox;
var startDateBox;
var endDateBox;
var alertShotBox;
var guaranteeShotBox;
var initialShotBox;
var partMapSel;
var mldS02Form;
var totalShot;
var fgSoldShot;
var totalShotTxt;
var fgSoldShotTxt;
var dcStatusHid;
var fgStatusHid;
var moldNameSel;
var editMoldNameBtn;
var createDateHid;
var btnDelete;
var btnBack;
var imgUpdateMoldNo;
var moldNoEditBox;

$(document).ready(function() {
	
	customerIdSel 	= $("#customerIdSel");
	partNameBoxId 	= $("#partNameBoxId");
	partNoBoxId 	= $("#partNoBoxId");
	btnSearchPart 	= $("#btnSearchPart");
	btnSave 		= $("#btnSave");
	moldNameBox 	= $("#moldNameBox");
	moldNoBox 		= $("#moldNoBox");
	qtyShotBox 		= $("#qtyShotBox");
	cavNoBox 		= $("#cavNoBox");
	startDateBox 	= $("#startDateBox");
	endDateBox 		= $("#endDateBox");
	alertShotBox 	= $("#alertShotBox");
	guaranteeShotBox = $("#guaranteeShotBox");
	initialShotBox 	= $("#initialShotBox");
	partMapSel 		= $("#partMapSel");
	mldS02Form 		= $("#mldS02Form");
	totalShot 		= $("#totalShot");
	fgSoldShot 		= $("#fgSoldShot");
	totalShotTxt 	= $("#totalShotTxt");
	fgSoldShotTxt 	= $("#fgSoldShotTxt");
	dcStatusHid 	= $("#dcStatusHid");
	fgStatusHid 	= $("#fgStatusHid");
	moldNameSel 	= $("#moldNameSel");
	editMoldNameBtn = $("#editMoldNameBtn");
	createDateHid 	= $("#createDateHid");
	btnDelete 		= $("#btnDelete");
	btnBack 		= $("#btnBack");
	imgUpdateMoldNo = $("#imgUpdateMoldNo");
	moldNoEditBox 	= $("#moldNoEditBox");
	
	moldNameSel.focus();
	if (createDateHid.val() != "") {
		btnDelete.show();
		btnBack.show();
		imgUpdateMoldNo.show();
		moldNoEditBox.show();
		moldNameSel.attr("disabled",true);
		moldNameBox.attr("disabled",true);
		moldNoEditBox.attr("disabled",true);
		moldNoBox.attr("readonly",true);
		moldNoBox.css("background-color", "#E8E8E8");
		editMoldNameBtn.show();
	}else{
		btnDelete.hide();
		btnBack.hide();
		imgUpdateMoldNo.hide();
		moldNoEditBox.hide();
		editMoldNameBtn.hide();
	}
	
	//only digit in textbox
	alertShotBox.keypress(PositiveIntegerFilter);
	guaranteeShotBox.keypress(PositiveIntegerFilter);
	initialShotBox.keypress(PositiveIntegerFilter);
	qtyShotBox.keypress(PositiveIntegerFilter);
	
	//call function
	fnSearchPart();
	fnSave();
	fnMoldNameChange();
	fnBack();
	fnDelete();
	
	alertShotBox.keyup(function(){
		checkShot();
	});
	guaranteeShotBox.keyup(function(){
		checkShot();
	});
	initialShotBox.keyup(function(){
		checkShot();
	});
	
	imgUpdateMoldNo.click(function(){
		if(moldNoEditBox.val() == ""){
			moldNoEditBox.attr("disabled",false);
			moldNoEditBox.val(moldNoBox.val());
			moldNoEditBox.focus();
		}else{
			moldNoEditBox.attr("disabled",true);
			moldNoEditBox.val("");
			moldNoEditBox.focus();
		}
	});
	editMoldNameBtn.click(function(){
		if(moldNameBox.val() == ""){
			moldNameBox.attr("disabled",false);
			moldNameBox.val(moldNameSel.find(":selected").text());
			moldNameBox.focus();
		}else{
			moldNameBox.attr("disabled",true);
			moldNameBox.val("");
			moldNameSel.focus();
		}
	});
});

function fnDelete(){
	btnDelete.click(function(){
		var isRelate = false;
		var params = mldS02Form.serialize();
		postJSONSync("checkRelateMold",params,function(result){
			if (result) {
				isRelate = true;
			}
		});
		if (isRelate) {
			message.setErrors([{"code": "err.mm.002", "arguments": [null,null]}]);
			return;
		}
		//keep mPartList(customerId,partId)
		var i = 0;
		var partId;
		var part;
		partMapSel.each(function () {
			$(this).find("option").each(function () {
				partId = $(this).val();
				part = $(this).text().split(":");
				mldS02Form.append("<input type='hidden' name='mPartList["+ i +"].partId' value='"+partId+"'");
				mldS02Form.append("<input type='hidden' name='mPartList["+ i +"].partNo' value='"+part[1]+"'");
				mldS02Form.append("<input type='hidden' name='mPartList["+ i +"].partName' value='"+part[0]+"'");
				i++;
			});
		});
		if (confirm("<spring:message code='cfm.cmm.002'/>")){
			mldS02Form.attr("action","MLD_S02_delete.html");
			mldS02Form.submit();
		}
	});
}

function fnBack(){
	btnBack.click(function(){
		mldS02Form.attr("action","MLD_S01_search.html?button=back");
		mldS02Form.submit();
	});
}

function fnMoldNameChange(){
	moldNameSel.bind("change blur" , function(){
		moldNameBox.val("");
		if (moldNameSel.find(":selected").val() == -2147483648) {
			partMapSel.empty();
			editMoldNameBtn.hide();
			moldNameBox.attr("disabled",false);
		}else{
			//findPart
			var params = {
				"moldId" : moldNameSel.find(":selected").val()
			};
			getJSON("partMapping",params,function(result){
				partMapSel.empty();
				$.each(result,function(val, text){
					partMapSel.append( $("<option></option>").val(val).html(text) );
				});
			});
			editMoldNameBtn.show();
			moldNameBox.attr("disabled",true);
		}
		moldNameBox.focus();
	});
}

function checkInput(isDupMName,isDupMNo,isDupMNoEdit){
	var msg = [];
	if (moldNameSel.val() == -2147483648 && moldNameBox.val() == "") {
		msg.push({"code": "err.cmm.001", "arguments": ["Mold Name",null]});
	}
	if (isDupMName) {
		msg.push({"code": "err.cmm.011", "arguments": ["Mold Name",null]});
	}
	if (moldNoBox.val() == "") {
		msg.push({"code": "err.cmm.001", "arguments": ["Mold No.",null]});
	}
	if (isDupMNo) {
		msg.push({"code": "err.cmm.011", "arguments": ["Mold No",null]});
	}
	if (isDupMNoEdit) {
		msg.push({"code": "err.cmm.011", "arguments": ["Mold No",null]});
	}
	if (qtyShotBox.val() == "") {
		msg.push({"code": "err.cmm.001", "arguments": ["Qty / Shot",null]});
	}	
	if (cavNoBox.val() == "") {
		msg.push({"code": "err.cmm.001", "arguments": ["Cav. No.",null]});
	}
	if (startDateBox.val() == "") {
		msg.push({"code": "err.cmm.001", "arguments": ["Start Date",null]});
	}
	if( startDateBox.val() != "" && endDateBox.val() != "" && startDateBox.datepicker("getDate") > endDateBox.datepicker("getDate") ) {
		msg.push({"code": "err.cmm.008", "arguments":["End Date","Start Date"]});
	}
	if (alertShotBox.val() == "") {
		msg.push({"code": "err.cmm.001", "arguments": ["Alert Shot",null]});
	}
	if (guaranteeShotBox.val() == "") {
		msg.push({"code": "err.cmm.001", "arguments": ["Guarantee Shot",null]});
	}
	if (initialShotBox.val() == "") {
		msg.push({"code": "err.cmm.001", "arguments": ["Initial Shot",null]});
	}
	if (parseInt(alertShotBox.val() || 0,10) > parseInt(guaranteeShotBox.val() || 0,10)) {
		msg.push({"code": "err.cmm.001", "arguments": ["Guarantee Shot must more than Alert Shot",null]});
	}

	if (msg.length > 0) {
		message.setErrors(msg);
		return true;
	}
}

function fnSave(){
	btnSave.click(function(){
		var totalDCShotVal = totalShot.html();
		totalDCShotVal = parseInt(totalDCShotVal.replace(/[,]/g,"") || 0,10);
		var fgSoldShotVal = fgSoldShot.html();
		fgSoldShotVal = parseInt(fgSoldShotVal.replace(/[,]/g,"") || 0,10);
		var param = mldS02Form.serialize() +'&'+ $.param({
			"initialShot" : initialShotBox.val(),
			"totalDCShot": totalDCShotVal,
			"totalFGSold": fgSoldShotVal
		});
		
		var isDupMName = false;
		var isDupMNo = false;
		var isDupMNoEdit = false;
		
			//check duplication Mold Name
			if (moldNameBox.val() != "") {
				postJSONSync("checkMoldName",param,function(result){
					if (result > 0) {
						isDupMName = true;
					}
				});
			}
			
			//check duplication Mold No
			if (moldNoBox.val() != "" && moldNameSel.val() > 0 && createDateHid.val() == "") {
				postJSONSync("checkMoldNo",param,function(result){
					if (result > 0) {
						isDupMNo = true;
					}
				});
			}
			
			//check duplication Mold No Edit
			if (moldNoEditBox.val != "") {
				postJSONSync("checkMoldNoEdit",param,function(result){
					if (result > 0) {
						isDupMNoEdit = true;
					}
				});
			}
		
		if(checkInput(isDupMName,isDupMNo,isDupMNoEdit)){return;};
		
		//keep mPartList(customerId,partId)
		var customerId;
		var partId;
		var i = 0;
		partMapSel.each(function () {
			$(this).find("option").each(function () {
				//customerId = $(this).data("customerId");
				//partId = $(this).data("partId");
				//param+="&mPartList["+ i +"].customerId="+customerId;
				partId = $(this).val();
				param+="&mPartList["+ i +"].partId="+partId;
				i++;
			});
		});
		
		message.clear();
		if(!confirm("<spring:message code='cfm.cmm.001'/>")){
			return;
		}
		var actionMap = "MLD_S02_add";
		if (createDateHid.val() != "") {
			actionMap = "MLD_S02_edit";
		}
		postJSONSync(actionMap, param, function(result){
			//set message infos/errors
			if (result.errors && result.errors.length > 0) {
				message.clear();
				message.setErrors(result.errors);
				return;
			}
			message.clear();
			message.setInfos(result.infos);

			//FG Status
			fgSoldShot.html(result.totalFGSold);
			if (1 == result.fgStatus) {
				fgSoldShotTxt.html("<FONT class='textblue'>Normal</FONT>");
				fgStatusHid.val(1);
			}else if (2 == result.fgStatus) {
				fgSoldShotTxt.html("<FONT class='textred'>Over Alert.</FONT>");
				fgStatusHid.val(2);
			}else if (3 == result.fgStatus) {
				fgSoldShotTxt.html("<FONT class='textred'>Over Guarantee.</FONT>");
				fgStatusHid.val(3);
			}else{
				fgSoldShotTxt.html("");
				fgStatusHid.val(0);
			}
			//DC Status
			totalShot.html(result.totalDCShot);
			if (1 == result.dcStatus) {
				totalShotTxt.html("<FONT class='textblue'>Normal</FONT>");
				dcStatusHid.val(1);
			}else if (2 == result.dcStatus) {
				totalShotTxt.html("<FONT class='textred'>Over Alert.</FONT>");
				dcStatusHid.val(2);
			}else if (3 == result.dcStatus) {
				totalShotTxt.html("<FONT class='textred'>Over Guarantee.</FONT>");
				dcStatusHid.val(3);
			}else{
				totalShotTxt.html("");
			}
			
			if (result.createDate == null) {
				$("input, select, textarea").attr("disabled", true);
				$("#editMoldNameBtn").unbind('click');
				$("a#imgUpdate").removeAttr('href');
				btnDelete.show();
				btnBack.show();
			}else{
				if (moldNameBox.val() != "") {
					moldNameSel.find(":selected").text(moldNameBox.val());
					moldNameBox.val("");
					moldNameBox.attr("disabled",true);
				}
				if (moldNoEditBox.val() != "") {
					moldNoBox.val(moldNoEditBox.val());
					moldNoEditBox.val("");
					moldNoEditBox.attr("disabled",true);
				}
			}
		});
	});
}
function getObjSize(obj) {
	var len = 0;//obj.length ? --obj.length : -1;
	if(obj==undefined || obj==null) return 0;
	for (var k in obj) len++;
	return len;
}
function fnSearchPart(){
	var partNo = $("select[id=partMasterSel]");
	btnSearchPart.click(function(){
		
		if (customerIdSel.val() == -2147483648 ) {
			message.clear();
			message.setErrors([{"code": "err.cmm.001", "arguments": ["Customer",null]}]);
			return;
		}
		
		var params = $.param({
		    "customerId": customerIdSel.val(),
			"partName": partNameBoxId.val(),
			"partNo": partNoBoxId.val()
		});
		postJSON("MLD_S02_search", params, function(result){
			partNo.empty();
			message.clear();
			if (getObjSize(result) > 0) {
				$.each(result,function(key, value){
					$.each(value,function(key2, value2){
						//partNo.append( $("<option value='"+key2+"' ></option>").data("customerId", key).data("partId", key2).html(value2) );
						partNo.append( $("<option value='"+key2+"' ></option>").html(value2) );
					});
				});
			}else{
				message.setInfos([{"code": "inf.cmm.004", "arguments": ["",null]}]);
			}
		});
	});
}
function checkShot(){
	var alertShot 		= alertShotBox.val();
	var guaranteeShot 	= guaranteeShotBox.val();
	var initShot 		= initialShotBox.val();
	var tShot 			= parseInt(totalShot.html() || 0,10)+parseInt(initShot || 0,10);
	var fgShot 			= parseInt(fgSoldShot.html() || 0,10)+parseInt(initShot || 0,10);
	
	if (alertShot == "" && guaranteeShot == "") {
		totalShotTxt.html("");
		dcStatusHid.val(0);
		fgSoldShotTxt.html("");
		fgStatusHid.val(0);
	}else{
		alertShot 		= parseInt(alertShot || 0,10);
		guaranteeShot 	= parseInt(guaranteeShot || 0,10);
		if(alertShot > guaranteeShot){
			totalShotTxt.html("");
			dcStatusHid.val(0);
			fgSoldShotTxt.html("");
			fgStatusHid.val(0);
		}else{
			//DC Status
			if (tShot <= alertShot) {
				totalShotTxt.html("<FONT class='textblue'>Normal</FONT>");
				dcStatusHid.val(1);
			}else if ((tShot > alertShot) && (tShot <= guaranteeShot)) {
				totalShotTxt.html("<FONT class='textred'>Over Alert.</FONT>");
				dcStatusHid.val(2);
			}else if (tShot > guaranteeShot) {
				totalShotTxt.html("<FONT class='textred'>Over Guarantee.</FONT>");
				dcStatusHid.val(3);
			}else{
				totalShotTxt.html("");
				dcStatusHid.val(0);
			}
			//FG Status
			if (fgShot <= alertShot) {
				fgSoldShotTxt.html("<FONT class='textblue'>Normal</FONT>");
				fgStatusHid.val(1);
			}else if ((fgShot > alertShot)&& (fgShot <= guaranteeShot)) {
				fgSoldShotTxt.html("<FONT class='textred'>Over Alert.</FONT>");
				fgStatusHid.val(2);
			}else if (fgShot > guaranteeShot) {
				fgSoldShotTxt.html("<FONT class='textred'>Over Guarantee.</FONT>");
				fgStatusHid.val(3);
			}else{
				fgSoldShotTxt.html("");
				fgStatusHid.val(0);
			}
		}
	}
}
</script>
</head>
<body>
<h1><spring:message code='menu.MoldMaster'/></h1>
<div id="navcontainer">
   <ul id="navlist">
     <!-- CSS Tabs -->
     <li><a href="MLD_S01.html" >Mold Search/List</a></li>
     <li><a href="MLD_S02.html" id="current">Mold Add/Edit</a></li>
     <li><a href="MLD_S03.html" >Mold History</a></li>
   </ul>
 </div>
<page:message item="${mDetail}" />
<br />
<form:form method="post" action="MLD_S02.html" commandName="mDetail" id="mldS02Form" >
<table width="100%" border="0" cellpadding="3" cellspacing="1">
  <tr>
    <td colspan="2">
	<table class="ui-widget ui-widget-content " cellpadding="3" cellspacing="1" width="100%" border="0">
	  <tr>
	    <th width="19%" align="center" >Mold Name <span class="textred">*</span></th>
	    <td align="left">
	    	<!-- For mode Edit -->
	    	<form:select tabindex="1" path="moldId" items="${moldNameMap}" id="moldNameSel" ></form:select>
	    	<form:hidden path="moldId" />
	    	<input type="hidden" name="mDetailList[0].moldId" value="${mDetail.moldId}"/>
	    	<input type="hidden" name="mDetailList[0].moldNo" value="${mDetail.moldNo}"/>
	    	<a href="javascript:void(0);" id="imgUpdate" ><img src="image/icon/update.gif" alt="Edit" width="16" height="16" id="editMoldNameBtn" /></a>
	    	<form:input tabindex="2" path="moldName" id="moldNameBox" size="20px" maxlength="50" />
	    </td>
	    <th align="center" >Mold No. <span class="textred">*</span></th>
	    <td align="left">
	    	<form:input tabindex="3" path="moldNo" id="moldNoBox" maxlength="10" />
	    	<a href="javascript:void(0);" id="imgUpdateMoldNo" ><img src="image/icon/update.gif" alt="Edit" width="16" height="16" id="editMoldNameBtn" /></a>
	    	<form:input tabindex="3" path="moldNoEdit" id="moldNoEditBox" size="20px" maxlength="10" />
	    </td>
	  </tr>
	  <tr>
	    <th align="center" >Qty / Shot <span class="textred">*</span></th>
	    <td align="left"><form:input tabindex="4" path="qtyShot" id="qtyShotBox" maxlength="10" /></td>
	    <th width="13%" align="center" >Cav. No. <span class="textred">*</span></th>
	    <td width="36%" align="left"><form:input tabindex="5" path="cavNo" id="cavNoBox" maxlength="50" /> </td>
	  </tr>
	  <tr>
	    <th align="center" >Start Date <span class="textred">*</span></th>
	    <td align="left"><form:input tabindex="6" path="startDate" cssClass="date" id="startDateBox" /></td>
	    <th align="center" >End Date</th>
	    <td align="left"><form:input tabindex="7" path="endDate" cssClass="date" id="endDateBox" /></td>
	  </tr>
	  <tr>
	    <th align="center" >Alert Shot <span class="textred">*</span></th>
	    <td align="left"><form:input tabindex="8" path="alertShot" id="alertShotBox" maxlength="10" /></td>
	    <th align="center" >Guarantee Shot <span class="textred">*</span></th>
	    <td align="left"><form:input tabindex="9" path="guaranteeShot" id="guaranteeShotBox" maxlength="10" /></td>
	  </tr>
	  <tr>
	    <th align="center" >Initial Shot <span class="textred">*</span></th>
	    <td align="left"><form:input tabindex="10" path="initialShot" id="initialShotBox" maxlength="10" /></td>
	    <th align="left" >Mold Order Sheet</th>
	    <td align="left" ><form:input tabindex="9" path="moldOrderSheet" id="moldOrderSheet" maxlength="20" /></td>
	  </tr>
	  <tr>
	    <th align="center" >Total Shot</th>
	    <td align="left">
	    	<span id="totalShot" >
	    		<c:choose>
	    			<c:when test="${mDetail.totalDCShot == null or mDetail.totalDCShot == ''}">0</c:when>
	    			<c:otherwise><fmt:formatNumber pattern="#,##0" value="${mDetail.totalDCShot}" /></c:otherwise>
	    		</c:choose>
	    	</span>&nbsp;
	    	<span id="totalShotTxt" >
	    		<c:if test="${mDetail.dcStatus == 1}"><font class="textblue">Normal</font></c:if>
	    		<c:if test="${mDetail.dcStatus == 2}"><font class="textred">Over Alert.</font></c:if>
	    		<c:if test="${mDetail.dcStatus == 3}"><font class="textred">Over Guarantee.</font></c:if>
	    	</span>
	    	<form:hidden path="dcStatus" id="dcStatusHid" />
	    </td>
	    <th align="center" >FG Sold Shot</th>
	    <td align="left" >
	    	<span id="fgSoldShot" >
	    		<c:choose>
	    			<c:when test="${mDetail.totalFGSold == null or mDetail.totalFGSold == ''}">0</c:when>
	    			<c:otherwise><fmt:formatNumber pattern="#,##0" value="${mDetail.totalFGSold}" /></c:otherwise>
	    		</c:choose>
	    	</span>&nbsp;
	    	<span id="fgSoldShotTxt" >
	    		<c:if test="${mDetail.fgStatus == 1}"><font class="textblue">Normal</font></c:if>
	    		<c:if test="${mDetail.fgStatus == 2}"><font class="textred">Over Alert.</font></c:if>
	    		<c:if test="${mDetail.fgStatus == 3}"><font class="textred">Over Guarantee.</font></c:if>
	    	</span>
	    	<form:hidden path="fgStatus" id="fgStatusHid" />
	    </td>
	  </tr>
	  <tr>
	    <th align="center" >Part Mapping</th>
	    <td colspan="3" align="left">
			<table width="100%" border="0" cellpadding="2" cellspacing="2"  class="ui-widget ui-widget-content ">
				<tr>
					<td width="20%" align="center"><b>Customer</b> <span class="textred">*</span></td>
					<td width="20%"><form:select tabindex="11" id="customerIdSel" path="customerId" items="${custMap}" ></form:select> </td>
					<td width="20%" align="center"><b>Part Name </b></td>
					<td width="20%"><input tabindex="12" id="partNameBoxId" type="text" /></td>
					<td width="20%" align="center"><b>Part No.</b></td>
					<td width="20%"><input tabindex="13" id="partNoBoxId" type="text" /></td>
					<td align="center"><input tabindex="14" id="btnSearchPart" type="button" value="Search" style="width:100px" /></td>
				</tr>
			</table>	
			<table  width="100%" align="center" cellpadding="2" cellspacing="2" class="ui-widget ui-widget-content ">
				<tr>
					<td align="right"><b>Part Master </b></td>
					<td align="center">&nbsp;</td>
					<td><b>Part Mapping </b></td>
				</tr>
				<tr>
					<td align="right">
						<select tabindex="15" size="10" multiple="multiple" id="partMasterSel" style="width:400px;height:110px" ></select>
					</td>
					<td align="center">
		                <input tabindex="16" type="button" style="width:50px" onclick=" $('select#partMasterSel option').attr('selected', 'selected'); moveList( 'partMasterSel', 'partMapSel' ); " value="&gt;&gt;" ></input><br />
						<input tabindex="17" type="button" style="width:50px" onclick=" moveList( 'partMasterSel', 'partMapSel' ); " value="&gt;" ></input><br/>
		                <input tabindex="18" type="button" style="width:50px" onclick=" moveList( 'partMapSel', 'partMasterSel' ); " value="&lt;"></input><br/>
		                <input tabindex="19" type="button" style="width:50px" onclick=" $('select#partMapSel option').attr('selected', 'selected'); moveList( 'partMapSel', 'partMasterSel' ); " value="&lt;&lt;" ></input><br />
					</td>
					<td align="left">
						<select tabindex="20" size="10" multiple="multiple" id="partMapSel" style="width:400px;height:110px" >
							<c:forEach items="${mDetail.mPartList}" var="mPart" >
								<option value="${mPart.partId}">${mPart.partName} : ${mPart.partNo}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
		</td>
	  </tr>
	  <tr>
	    <th align="center" >Remark</th>
	    <td colspan="3" align="left"><form:textarea tabindex="21" path="remark" cols="45" rows="5" /></td>
	  </tr>
	</table>
  <tr>
	<td>
		<div align="left">
			<input tabindex="22" type="button" id="btnSave" value="Save" />
			<input tabindex="23" type="button" id="btnDelete" value="Delete" />
			<input tabindex="24" type="button" id="btnBack" value="Back" />
		</div>
	    <form:hidden path="createDate" id="createDateHid"  />
	 </td>
	 <td align="right" class="textred">* Requied Field</td>
  </tr>
</table>	
</form:form>
</body>
</html>
