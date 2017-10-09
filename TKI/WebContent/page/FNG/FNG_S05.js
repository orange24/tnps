var fngS05Form;
	var btnSearch;
	var reportDateFrom;
	var reportDateTo;
	var createDateFrom;
	var createDateTo;
	
	$(document).ready(function() {

		reportDateFrom = $("input[name=reportDateFrom]");
		reportDateTo   = $("input[name=reportDateTo]");
		createDateFrom = $("input[name=createDateFrom]");
		createDateTo   = $("input[name=createDateTo]");
		fngS05Form	   = $("#fngS05Form");
		btnSearch	   = $("#btnSearch");
		
		// <!-- Initial: Set 'maxDate'. -->
		reportDateFrom.datepicker( "option", "maxDate", '0d' );
		reportDateTo.datepicker( "option", "maxDate", '0d' );
		createDateFrom.datepicker( "option", "maxDate", '0d' );
		createDateTo.datepicker( "option", "maxDate", '0d' );

		btnSearch.click(function(){
			var errors = [];
			if(reportDateFrom.val()!= "" && reportDateTo.val() != ""){
				if(reportDateFrom.datepicker("getDate").getTime() > reportDateTo.datepicker("getDate").getTime()) {
					errors.push({"code": "err.cmm.008", "arguments": [reportDateTo.attr("title"),reportDateFrom.attr("title")]});
				}
			}
			if(createDateFrom.val()!= "" && createDateTo.val() != ""){
				if(createDateFrom.datepicker("getDate").getTime() > createDateTo.datepicker("getDate").getTime()) {
					errors.push({"code": "err.cmm.008", "arguments": [createDateTo.attr("title"),createDateFrom.attr("title")]});
				}
			}
			
			if( errors.length > 0 ) {
				message.setErrors(errors);
				return false;
			}
			message.clear();
			fngS05Form.submit();
		});
		
	});