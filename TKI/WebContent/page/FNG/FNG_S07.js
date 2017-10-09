	var fngS07Form;
	var btnSearch;
	var btnExport;
	var lotNo;
	var customerFrom;
	var customerTo;
	var fgNoFrom;
	var fgNoTo;
	var fgNameFrom;
	var fgNameTo;
	var grid;
	var errors = [];
	
	$(document).ready(function() {

		fngS07Form	   = $("#fngS07Form");
		btnSearch	   = $("#btnSearch");
		btnExport	   = $("#btnExport");
		lotNo          = $("#_lotNo");
		customerFrom   = $("#_customerFrom");
		customerTo     = $("#_customerTo");
		fgNoFrom       = $("#_fgNoFrom");
		fgNoTo         = $("#_fgNoTo");
		fgNameFrom     = $("#_fgNameFrom");
		fgNameTo       = $("#_fgNameTo");
		
		//data
		
		
		//view
		createGrid();
		hideGrid();
		
		//event
		btnSearch.click(search);
		btnExport.click(exportData);
	});
	function hideGrid(){
		$('#itemInformationGrid').hide();
	}
	function getLotData(){
		var params = {"lotNo":lotNo.val(), "customerFrom":customerFrom.find(":selected").val(), "customerTo":customerTo.find(":selected").val(), "fgNoFrom":fgNoFrom.val(),"fgNoTo":fgNoTo.val(),"fgNameFrom":fgNameFrom.val(),"fgNameTo":fgNameTo.val()};
		var data = postJSONObject("FNG_S07_search", params, 
				function(result){
					grid.getDataView().setItems(result);
		});
		return data;
	}
	function search(){
		message.clear();
		errors = [];
		
		createGrid();
		$('#itemInformationGrid').show();
		grid.reloadData({
			data : {
				url : "FNG_S07_search.html",
				method : "POST",
				json : true,
				param : {"lotNo":lotNo.val(),"customerFrom":customerFrom.find(":selected").val(),"customerTo":customerTo.find(":selected").val(),"fgNoFrom":fgNoFrom.val(),"fgNoTo":fgNoTo.val(),"fgNameFrom":fgNameFrom.val(),"fgNameTo":fgNameTo.val()}
			}
		});
		
	}
	function exportData(){
			errors = [];
			message.clear();
			var noData = grid.getFilteredRows().length === 0;
			if(noData){
				errors.push({'code': 'err.cmm.018', 'arguments': []});
				message.setErrors(errors);
				return false ;
			}else{
				//fngS07Form.empty();
				downloadNotify($("<div title='"+message.getMessage("downloadAlertContent")+"'/>'>"+message.getMessage("downloadAlertContent")+"</div>"));
				fngS07Form.submit();
			}		
		;
	}

	
	function createGrid(){
		columns = [
		      	 	{
		      		id 				: "lotNo",
		      		name 			: "Lot No.",
		      		field 			: "lotNo",
		      		width 			: 110,
		      		cssClass 		: "cell-l",
		      		resizable 		: true,
		      		sortable 		: true
		      	}, {
		      		id 				: "customerFrom",
		      		name 			: "Customer From",
		      		field 			: "customerFrom",
		      		width 			: 110,
					cssClass 		: "cell-l",
		      		resizable 		: true,
		      		sortable 		: true
		      	}, {
					id 				: "customerTo",
					name 			: "Customer To",
					field 			: "customerTo",
					width 			: 110,
					cssClass 		: "cell-l",
					resizable 		: true,
					sortable 		: true
				}, {
		      		id 				: "fgNoFrom",
		      		name 			: "FG No. From",
		      		field 			: "fgNoFrom",
		      		width 			: 110,
		      		cssClass 		: "cell-l",
		      		resizable 		: true,
		      		sortable 		: true
		      	}, {
					id 				: "fgNameFrom",
					name 			: "FG Name From",
					field 			: "fgNameFrom",
					width 			: 110,
					cssClass 		: "cell-l",
					resizable 		: true,
					sortable 		: true
				}, {
					id 				: "fgNoTo",
					name 			: "FG No. To",
					field 			: "fgNoTo",
					width 			: 110,
					cssClass 		: "cell-l",
					resizable 		: true,
					sortable 		: true
				}, {
					id 				: "fgNameTo",
					name 			: "FG Name To",
					field 			: "fgNameTo",
					width 			: 110,
					cssClass 		: "cell-l",
					resizable 		: true,
					sortable 		: true
				}, {
		      		id 				: "snpFrom",
		      		name 			: "SNP From",
		      		field 			: "snpFrom",
		      		width 			: 75,
		      		cssClass 		: "cell-r",
		      		resizable 		: true,
		      		sortable 		: true
				}, {
					id 				: "snpTo",
					name 			: "SNP To",
					field 			: "snpTo",
					width 			: 75,
					cssClass 		: "cell-r",
					resizable 		: true,
					sortable 		: true
				}, {
					id 				: "qty",
					name 			: "QTY",
					field 			: "qty",
					width 			: 50,
					cssClass 		: "cell-r",
					resizable 		: true,
					sortable 		: true
				}, {
					id 				: "createDate",
					name 			: "Create Date",
					field 			: "createDate",
					width 			: 100,
					cssClass 		: "cell-c",
					resizable 		: true,
					sortable 		: true,
					formatter	: common.SlickGrid.Formatters.DateTimeFormatter
				}, {
					id 				: "createBy",
					name 			: "Create By",
					field 			: "createBy",
					width 			: 110,
					cssClass 		: "cell-l",
					resizable 		: true,
					sortable 		: true
				}];
		
		var options = $.extend( {}, common.SlickGrid.newGrid().getOptions(), {
			showHeaderRow : true,
			headerRowHeight : 30,
			enableAddRow : true
		});// Extend default options from common grid
		
		grid = common.SlickGrid.newGrid();
		grid = grid.setOptions(options);
		grid = grid.setGrid( {
			"id" : "itemInformationGrid",
			"columns" : columns,
			"data" : []
		});	
}
