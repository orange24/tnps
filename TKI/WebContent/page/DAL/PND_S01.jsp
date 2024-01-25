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
<%@ include file="../importResources.jsp" %>
<script language="javascript">
	var dailyMCForm;
	var boxWIPCode;
	var tbResult;

	$(document).ready(function(){
		dailyMCForm = $("form#dailyMCForm");
		boxWIPCode = $("select#boxWIPCode");
		tbResult = $("table#tblDetailPart > tbody > tr:gt(2):not(:last)");
		
		calActQty();

		/*
			// clone template row
			var clonedRow = $(".sampleRow").clone(true);
			$(clonedRow).removeClass("sampleRow");
			$(clonedRow).css("display", "");
			$(clonedRow).attr("id", "tr_sublist");
			
			var tr = $("#tblDetailPart").find("#mainRow");
			tr.find("table[id=tbl_sublist]").each(function(){
				$(this).append(clonedRow.clone(true));
			});
		 */
	});
	
	function calActQty(){
		tbResult.each(function(){
			var pending = parseInt($(this).find("td:eq(7)").html() || 0);
			var ok  = $(this).find("input:eq(3)");
			var ng  = $(this).find("input:eq(4)");			
			var reworkQty =  $(this).find("[id=reworkQty]");
			var evt = function(){	
				var reworkQtyTotal = 0;	
				var reworkQty = $(this).closest("#mainRow").find("[id=reworkQty]");
				reworkQty.each(function(){
					var _reworkQty = $(this);
					var v = parseInt(_reworkQty.val() || 0);
					reworkQtyTotal = reworkQtyTotal + v;	
				});
				if( parseInt(ok.val() || 0) + parseInt(ng.val() || 0) + reworkQtyTotal > pending ) {
					ok.removeClass("highlightGreen highlightYellow").addClass("highlightRed");
					ng.removeClass("highlightGreen highlightYellow").addClass("highlightRed");
					reworkQty.each(function(){ $(this).removeClass("highlightGreen highlightYellow").addClass("highlightRed");});
				}else if( parseInt(ok.val() || 0) + parseInt(ng.val() || 0) + reworkQtyTotal == pending ) {
					ok.removeClass("highlightRed highlightYellow").addClass("highlightGreen");
					ng.removeClass("highlightRed highlightYellow").addClass("highlightGreen");
					reworkQty.each(function(){ $(this).removeClass("highlightRed highlightYellow").addClass("highlightGreen");});
				}else if( parseInt(ok.val() || 0) + parseInt(ng.val() || 0) + reworkQtyTotal  < pending ) {
					ok.removeClass("highlightRed highlightGreen").addClass("highlightYellow");
					ng.removeClass("highlightRed highlightGreen").addClass("highlightYellow");
					reworkQty.each(function(){ $(this).removeClass("highlightRed highlightGreen").addClass("highlightYellow");});
				}
				if(parseInt(ok.val() || 0)==0 && parseInt(ng.val() || 0)+ reworkQtyTotal == 0){
					ok.removeClass("highlightRed highlightGreen highlightYellow");
					ng.removeClass("highlightRed highlightGreen highlightYellow");
					reworkQty.each(function(){ $(this).removeClass("highlightRed highlightGreen highlightYellow");});
				}
			};
			ok.keyup(evt);
			ng.keyup(evt);
			reworkQty.keyup(evt);
		});
	}
	
	$(function(){
	  $("[id=btnAdd]").click(function(){ 
		/*
	    var clonedRow = $(".sampleRow").clone(true);
		$(clonedRow).removeClass("sampleRow");
		$(clonedRow).css("display", "");
		$(clonedRow).attr("id", "tr_sublist");
		*/
		var clonedRow = $(this).parents("#mainRow").find("#tdout_sublist").find("#templateTbl").find("#templateRow").clone(true);
		$(clonedRow).removeClass("sampleRow");
		$(clonedRow).css("display", "");
		$(clonedRow).attr("id", "tr_sublist");


		var tr = $(this).parents("#mainRow");
		var o = tr.find("[id=tdout_sublist]").find("[id=tbl_sublist]");
		clonedRow.find("[id=reworkQty]").attr("class",o.find("[id=reworkQty]").attr("class"));
		o.append(clonedRow);
		
	  })
	});
	
	$(function(){
	  $("[id=delRow]").click(function(){ 
	   	if($(this).parents("#tr_sublist").parents("#tbl_sublist").find("#delRow").length > 1){
	      $(this).parents("#tr_sublist").remove();
		}
	  })
	});

	$(function() {
		$("[name=searchBtn]").click(function(){
		message.clear();
		/*if($("[name=wip]").val() == ''){
	  	  message.setError("err.cmm.001",["WIP"]);
		}
		if($("[id=customerId]").val() == -2147483648){
		  message.setError("err.cmm.001",["Customer"]);
		}*/
		if (message.isNoError()){
			var dailyPDForm = $("form#dailyPDForm");
			dailyPDForm.attr("action", "PND_S01_search.html");
			dailyPDForm.submit();
		}
	  })
	});
	
	function checkInput(){
		
	  var line = 1;
	  var hasInput = false;
	  $("[id=tblDetailPart] [id=mainRow]").each(function(idx){
			
	    // check value not exceed pdQty
		var pdQty = $(this).find("[name=pdQty]").val();
		var sumAdjust = 0;
		$(this).find("#ok,#ng").each(function(){
		if(!isNaN(parseInt($(this).val(),0)))
		  sumAdjust = parseInt(sumAdjust,0) + parseInt($(this).val(),0);
		});
		$(this).find("[id=tdout_sublist]").each(function(){
		  $(this).find("[id=tbl_sublist]").find("[id=tr_sublist]").each(function(inneridx){
		    $(this).find("input,select").each(function(){	
		      if(!isNaN(parseInt($(this).val(),0)))
		       sumAdjust = parseInt(sumAdjust,0) + parseInt($(this).val(),0);
		    })
		  })
		});
		if(parseInt(pdQty,0) < parseInt(sumAdjust,0)){
		  message.setError("err.pnd.001",[line]);
		}
	    if(parseInt(sumAdjust,0) > 0){
		  hasInput = true;
		}
		  
		var isDuplicate = false;
		$(this).find("[id=tdout_sublist]").each(function(){
		  var wipValue = "";  
	      $(this).find("[id=tbl_sublist]").find("[id=tr_sublist]").each(function(i){
		    // if rework wip is chosen, reworkQty must greater than zero
		    if($(this).find("[id=wip]").val() != '' && $(this).find("[id=reworkQty]").val() == ''){
		      message.setError("err.cmm.002",[line, "Rework Qty"]);
		    }
	        // if rework Qty is greater than zero, rework wip must be chosen
			if($(this).find("[id=wip]").val() == '' && $(this).find("[id=reworkQty]").val() != ''){
			  message.setError("err.cmm.002",[line, "Rework WIP"]);
			}
			  
			// Cannot rework in duplicate WIP.
			var current_val = $(this).find("[id=wip]").val();
			
			if(current_val != ''){
			  $(this).parent().find("[id=tr_sublist]").each(function(j){
			    if(current_val == $(this).find("[id=wip]").val() && parseInt(i,0) != parseInt(j,0)){
				  isDuplicate = true;
				  return false;
			    }
			  })
			}
		  })
		});
		if(isDuplicate){
		  message.setError("err.pnd.002",[line]);
		}
		line++;
	    }); 
		if(!hasInput){
		  message.setError("err.cmm.003");
		}
	}
	
	$(function(){
		$("[name=saveBtn]").click(function(){
			message.clear();
			checkInput();
			var dailyPDForm = $("form#dailyPDForm");
			if (message.isNoError() && confirm(message.getMessage("cfm.cmm.001"))) {
			  	var details = new Object();
			  	$("[id=tblDetailPart] [id=mainRow]").each(function(idx){
				    var sumAdjust = 0;
					
				    $(this).find("#ok,#ng").each(function(){
						var name = "adjustList[" + idx + "]."+ $(this).attr("name");
					 	$(this).attr("name", name);
					 	if(!isNaN(parseInt($(this).val(),0)))
							sumAdjust = parseInt(sumAdjust,0) + parseInt($(this).val(),0);
				 	});
					
					$(this).find("[id=tdout_sublist]").each(function(){
						$(this).find("[id=tbl_sublist]").find("#tr_sublist").each(function(inneridx){
							$(this).find("input,select").each(function(){
							var subname = "adjustList[" + idx + "]." + "reworkList[" + inneridx + "]." + $(this).attr("id");
							$(this).attr("name", subname);
							if(!isNaN(parseInt($(this).val(),0)))
								sumAdjust = parseInt(sumAdjust,0) + parseInt($(this).val(),0);
						    })
						})
					});
					
					$(this).find("[name=pdAdjustQty]").val(sumAdjust);
					var pdAdjustQtyName = "adjustList[" + idx + "]."+ "pdAdjustQty";
					$(this).find("[name=pdAdjustQty]").attr("name", pdAdjustQtyName);
						
					var pdId = "adjustList[" + idx + "]."+ "pdId";
					$(this).find("[name=pdId]").attr("name", pdId);
				
				}); 
				postJSON("PND_S01_save", dailyPDForm.serialize(), function(result){
					if (result.infos.length > 0) {
					  	//message.setInfo(result.infos[0].code,result.infos[0].arguments);
					  	//var dailyPDForm = $("form#dailyPDForm");
					  	dailyPDForm.attr("action", "PND_S01_searchAfterSave.html");
				      	dailyPDForm.submit();
					}else if (result.errors.length > 0) {
					  	message.clear();
					  	message.setErrors(result.errors);
				    }  
				})
			}
		})
	});
</script>

<style type="text/css">
<!--
.style1 {
	color: #FF0000
}
-->
</style>
</head>

<body>
	<h1><spring:message code='menu.PendingAdjustment'/></h1>
	<ul id="navlist">
		<li><a href="PND_S01.html" id="current">Pending Adjustment</a></li>
		<li><a href="PND_S02.html" >Pending Adjust History</a></li>
	</ul>
	<page:message item="${searchCriteria}" />
	<!-- Search Criteria -->
	<form:form id="dailyPDForm" methodParam="post" commandName="searchCriteria">
		<table width="100%" border="0" cellpadding="3" cellspacing="1">
			<tr>
			  	<td colspan="2">
				  	<table class="ui-widget ui-widget-content" cellpadding="3" cellspacing="1" width="100%" border="0">
					    <tr>
							<th width="20%">WIP</th>
							<td width="39%"><form:select path="wip" tabindex="1" items="${wipMap}"></form:select></td>
							<th width="13%">Customer</th>
							<td width="28%"><form:select path="customerId" tabindex="1" items="${custMap}"></form:select></td>
					    </tr>
					    <tr>
							<th>Part No</th>
							<td><form:input path="partNo" tabindex="3" size="10"/></td>
							<th>Part Name</th>
							<td><form:input path="partName" tabindex="3" size="10"/></td>
					    </tr>
					    <tr>
							<th>Workorder No.</th>
							<td><form:input path="workorderNo" tabindex="3" size="10"/></td>
							<th>&nbsp;</th>
							<td>&nbsp;</td>
					  	</tr>
				    </table>
			  	</td>
			</tr>
			<tr>
			  	<td width="52%">&nbsp;</td>
			  	<td width="48%">
			  		<div align="right">
				  		<span class="label">
				    		<input class="submit_button" name="searchBtn" type="button" value="Search" style="width:100px"/>
				    	</span>
				    </div>
				</td>
			</tr>
		</table>
		<c:if test="${not empty searchCriteria.adjustList}">
			<table id="tblDetailPart" width="100%" border="1" cellpadding="3" cellspacing="0" class="ui-widget ui-widget-content ">
				<tr>
					<td colspan="13">
						<div style="float:left" ><page:display item="${searchCriteria}"/></div>
						<div style="float:right"><page:number item="${searchCriteria}"/></div>
					</td>
				</tr>
				<tr class="submit_button">
					<th align="center" rowspan="2" width="5%">No. </th>
					<th align="center" rowspan="2" width="5%">Customer</th>
					<th align="center" rowspan="2" width="11%">Part No</th>
					<th align="center" rowspan="2" width="20%">Part Name</th>
					<th align="center" rowspan="2" width="10%">Work Order No.</th>
					<th align="center" rowspan="2" width="5%">Lot No.</th>
					<th align="center" rowspan="2" width="5%">WIP</th>
					<th align="center" colspan="5" width="39%">Actual Product Qty</th>
				</tr>
				<tr class="submit_button">
				 	<th align="center">Problem (Ng Reason)</th>
				 	<th align="center">Pending</th>
				  	<th align="center">OK</th>
				  	<th align="center">NG</th>
				  	<th align="center" width="180">Rework (WIP : Qty)</th>
				 	<th align="center">WIP (Rework)</th>
				  	<th align="center">Remark</th>
				  	<th align="center">Action</th>
				</tr>

				<c:forEach var="dailyPD" items="${searchCriteria.adjustList}" varStatus="prop" begin="0" step="1">
				    <tr id="mainRow">
						<td align="center" class="border_all" >${prop.index + 1} <input type="hidden" name="pdAdjustQty" value=""/><input type="hidden" name="pdId" value="${dailyPD.pdId}"/></td>
						<td class="border_all" ><div align="center">${dailyPD.customerCode}&nbsp;</div></td>
						<td align="left" class="border_all">${dailyPD.partNo}&nbsp;</td>
						<td align="left" class="border_all">${dailyPD.partName}&nbsp;</td>
						<td align="left" class="border_all">${dailyPD.workorderNo}&nbsp;</td>
						<td align="center" class="border_all">${dailyPD.lotNo}&nbsp;</td>
						<td align="center" class="border_all">${dailyPD.wipName}&nbsp;</td>
						<td align="center" class="border_all">
							<select id="ng_reason" name="ng_reason">
								<option value="">-- Select Reason --</option>
							  	<c:forEach var="item" items="${searchCriteria.resonList}">
									<option value="${item.reasonId}">${item.reasonId}:${item.reasonName}</option>
								</c:forEach>
							</select>
						</td>
						<td align="center" class="border_all">${dailyPD.pdQty}&nbsp;<input type="hidden" name="pdQty" value="${dailyPD.pdQty}"/></td>
						<td align="center" class="border_all"><input type="text" name="ok" id="ok" size="3" class="posInt"/></td>
						<td align="center" class="border_all"><input type="text" name="ng" id="ng" size="3" class="posInt"/></td>
						<td align="center" class="border_all" id="tdout_sublist">
							<table id="tbl_sublist">
						    	<tr id="tr_sublist">
									<td>
										<select id="wip">
											<option value="">-- Select WIP --</option>
										  	<c:forEach var="item" items="${searchCriteria.wipList}">
												<option value="${item.wip}">${item.wip}:${item.wipName}</option>
											</c:forEach>
										</select>
										: 
										<input type="text" id="reworkQty" name="reworkQty" size="5" class="posInt"/>
										<a id="delRow" style=cursor:hand;>
										  	<img id="deleteRow" src="image/icon/delete.gif" width="16" height="16" class="delete"/>
										</a>
									</td>
				   				</tr>
				     		</table>
							<table id="templateTbl">
							    <tr id="templateRow" class="sampleRow" style="display:none ">
							      	<td>
								        <select id="wip">
								     	   <option value="">-- Select WIP --</option>
								          <c:forEach var="item" items="${searchCriteria.wipList}">
								            <option value="${item.wip}">${item.wip}:${item.wipName}</option>
								          </c:forEach>
								        </select>
										: 
										<input type="text" id="reworkQty" name="reworkQty" size="5" class="posInt"/>
										<a id="delRow" style=cursor:hand;>
										  	<img id="deleteRow" src="image/icon/delete.gif" width="16" height="16" class="delete"/>
										</a>
							 		</td>
							    </tr>
							</table>
						</td>
						<td align="center" class="border_all">
							<select name="wip">
								<option value="">-- Select WIP --</option>
								<c:forEach var="item" items="${searchCriteria.wipList}">
									<option value="${item.wip}">${item.wip}:${item.wipName}</option>
								</c:forEach>
							</select>
						</td>
						<td align="center" class="border_all"><input type="text" name="remark" size="30"/></td>
				      	<td align="center" class="border_all">
							<a id="btnAdd" style=cursor:hand;>
							  	<img id="addButton" src="image/icon/plus.gif" width="16" height="16" alt=""/>
							</a>
				      	</td>
				   </tr>
				</c:forEach>
				<tr>
			   		<td colspan="13">
				  		<div style="float:left" ><page:display item="${searchCriteria}"/></div>
				  		<div style="float:right"><page:number item="${searchCriteria}"/></div>
			   		</td>
				</tr>
			</table>
			<table cellpadding="3" cellspacing="1" border="0" width="100%">
				<tr>
				    <td width="48%" align="left">
				      <input type="button" name="saveBtn" value="Save" style="width:100px"/>
				    </td>
			  	</tr>
			</table>
		</c:if>
    </form:form>
</body>
</html>
