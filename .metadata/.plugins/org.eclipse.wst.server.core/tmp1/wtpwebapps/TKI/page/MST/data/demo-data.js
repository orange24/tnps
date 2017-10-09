var fgPartData = [];
var customerData = [];
var fgData = [];
var partData = [];
var processData = [];
var custfgData = [];
var fgpartData = [];

$( function($) {	
	for (var i = 0; i < 8; i++) {
		var random = Math.round(Math.random() * 100);
		var name = "Aaaaaa Bbbbbb";
		var ranPart = Math.round(Math.random() * 100);
		if(random%2 ==0){
			name = "Cccccc Dddddd";
		}
		var flagRandom;
		var random = Math.round(Math.random() * 100);
		if(random % 2 == 0){
			flagRandom = "e";
		}else{
			if(random < 20){
				flagRandom = "d";
			}else{
				flagRandom = "e";
			}
		}
		  
		fgPartData.push({
			_id: i+1,
			flag: flagRandom,
			customerName: "ALPINE (H)",
			fgNo: "FG A",
			fgName: "FG AAAAAA",
			partNo: "Part A",
			partName: "Part AAAAAA",
			process: [
				{rowNo:"1.", processName: "Diecast"},
				{rowNo:"2.", processName: "Shot Blast"},
				{rowNo:"3.", processName: "Finishing"},
				{rowNo:"4.", processName: "Machine"},
				{rowNo:"5.", processName: "Final"}],
			wipcalc: [
				{rowNo:"1.", iscalc: "Yes"},
				{rowNo:"2.", iscalc: "Yes"},
				{rowNo:"3.", iscalc: "Yes"},
				{rowNo:"4.", iscalc: "No"},
				{rowNo:"5.", iscalc: "Yes"}],	
			remark: "xxxxxxxxxxx",
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		});		
	}

	customerData = [{
			customerId:'1',
			customerCode:'AAP', 
			customerName:'ASIAN AUTOPARTS CO.,LTD',
			remark: "xxxxxxxxxxx",
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		},{
			customerId:'2',
			customerCode:'ABLE', 
			customerName:'ABLE PROGRESS INDUSTRY CO., LTD.',			
			remark: "xxxxxxxxxxx",
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		},{
			customerId:'3',
			customerCode:'ADVANCE', 
			customerName:'ADVANCE SOLARIUM CORPORATION CO.,LTD',
			remark: "xxxxxxxxxxx",
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		},{
			customerId:'4',
			customerCode:'AIWA (M)', 
			customerName:'AIWA IPC (M) SDN. BHD.',
			remark: "xxxxxxxxxxx",
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		},{
			customerId:'5',
			customerCode:'ALPINE', 
			customerName:'ALPINE TECHNOLOGY MANUFACTURING (THAILAND) CO.,LTD.',
			remark: "xxxxxxxxxxx",
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		},{
			customerId:'6',
			customerCode:'SONY', 
			customerName:'SONY (THAILAND) CO.,LTD.',
			vendorCode:'1G003',
			remark: "xxxxxxxxxxx",
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		}];
	
	fgData = [{
			fgId:'1',
			fgNo:'FG A', 
			fgName:'FG AAAAA',			
			uom: 'PCS',
			snp: '100',
			price: 1234.56,
			weight: 100,
			vendorFGNo:'123456',
			classifyBusiness: 'classifyBusiness',
			place: 'place',
			subBusiness: 'subBusiness',
			isenabled:'Yes',
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		},{
			fgId:'2',
			fgNo:'FG B', 
			fgName:'FG BBBBB',
			uom: 'PCS',
			snp: '100',
			price: 1234.56,
			weight: 100,
			classifyBusiness: 'classifyBusiness',
			place: 'place',
			subBusiness: 'subBusiness',
			isenabled:'Yes',
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		}];
		
	custfgData = [{
			customer:'AAP',
			fgNo:'FG AAP 1', 
			fgName:'FG AAP Name 1'			
		},{
			customer:'AAP',
			fgNo:'FG AAP 2', 
			fgName:'FG AAP Name 2'
		}];	
	
	partData = [{
			partId:'1',
			partNo:'Part A', 
			partName:'Part AAAAA',
			lot: '50',
			material:'Aluminuim 1',
			isenabled:'Yes',
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		},{
			partId:'2',
			partNo:'Part B', 
			partName:'Part BBBBB',
			lot: '65',
			material:'Zinc 1',
			isenabled:'Yes',
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		}];
		
	fgpartData = [{
			customer:'AAP',
			fgNo:'FG AAP 1 : FG AAP Name 1', 
			partNo:'Part AAP 1',
			partName:'Part AAP Name 1'
		},{
			customer:'AAP',
			fgNo:'FG AAP 2 : FG AAP Name 2', 
			partNo:'Part AAP 2',
			partName:'Part AAP Name 2'
		}];		
	
	processData = [{			
			processOrder:'1', 
			processCode:'ALDC',
			processName:'Aluminium Die Cast',
			processNoName: 'ALDC : Aluminium Die Cast',
			wipCal:'Yes',
			wipType:'Die Cast',
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		},{
			processOrder:'2', 
			processCode:'SB',
			processName:'Shot Blast',
			processNoName: 'SB : Shot Blast',
			wipCal:'Yes',
			wipType:'Machine',
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		},{
			processOrder:'3', 
			processCode:'FN-1',
			processName:'Finishing',
			processNoName:'FN-1 : Finishing',
			wipCal:'Yes',
			wipType:'Worker',
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		},{
			processOrder:'4', 
			processCode:'MC-2',
			processName:'Machine',
			processNoName:'MC-2 : Machine',
			wipCal:'Yes',
			wipType:'Machine',
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		},{
			processOrder:'5', 
			processCode:'WS',
			processName:'Washing',
			processNoName:'WS : Washing',
			wipCal:'No',
			wipType:'Worker',
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		},{
			processOrder:'6', 
			processCode:'FL-2',
			processName:'Final',
			processNoName:'FL-2 : Final',
			wipCal:'Yes',
			wipType:'Worker',
			createDate: "13/03/2013 13:30:01",
			createBy: name,
			updateDate: "15/03/2013 13:30:01",
			updateBy: name
		}];
				
});
 