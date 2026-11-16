// 6.05 Print Multi-Lot Tag (QR)
// Lot search / preview reuse the 6.04 endpoints (FNG_S06_*); the table keeps every lot
// previewed so seqs from several lots can go on one QR tag (max 4 seqs).
var MAX_SEQ = 4;
var lots = [];			// [{header: {...TTagLabelDetail fields}, remain: n, seqs: [TLotSequence]}]
var current = null;		// lot detail loaded in the form, not yet in the table
var customerListCache = null;
var fgListCache = {};
var lastLoadedLotNo = null;
var pendingPrint = null;	// function(printerName) run when the printer dialog says Print

$(function(){
	$('#printDialog').hide();
	$('#lotNo').focus();

	$('#lotNo').autocomplete({
		source: function(request, response){
			var lotNo = $('#lotNo').val();
			if(lotNo.length == 14){
				response([]);
				loadLot(lotNo);
			}else{
				getJSON("FNG_S06_getLotNo", {"lotNo": lotNo}, function(result){
					response($.map(result, function(item){
						return {label: item.lotNo, value: item.lotNo};
					}));
				});
			}
		},
		minLength: 1,
		delay: 1000,
		select: function(event, ui){ loadLot(ui.item.value); }
	}).change(function(){ loadLot($('#lotNo').val()); });

	$('#btnPreview').click(preview);
	$('#btnClear').click(function(){ location.href = "FNG_S08.html"; });
	$('#btnPrintMulti').click(printMulti);
	$('#btnPrintSingle').click(printSingle);
	$('#btnFind').click(findTag);
	$('#btnReprint').click(reprintTag);
	$('#btnCancelTag').click(cancelTag);
	$('#btnCancelDialog').click(function(){ $('#printDialog').dialog("close"); });
	$('#btnPrintDialog').click(function(){
		var printerName = $('#cboPrinter').val();
		if(printerName == null || printerName == '' || printerName == 'null'){
			alert(message.getMessage('err.cmm.027'));
			return;
		}
		$('#printerName').val(printerName);
		$('#printDialog').dialog("close");
		if(pendingPrint){ pendingPrint(printerName); }
	});
	$('#seqTbl input.seqChk').live('click', onSeqClick);
	$('#seqTbl input.seqQty').live('keyup', onQtyEdit).live('change', onQtyEdit);
	$('#seqTbl a.delLot').live('click', function(){
		lots.splice($(this).attr('rel') * 1, 1);
		renderTable();
		return false;
	});
	$('#refNo').keypress(function(e){
		if((e.keyCode || e.which) == 13){ findTag(); return false; }
	});
});

//---------------- lot form ----------------
function loadLot(lotNo){
	lotNo = $.trim(lotNo == null ? "" : lotNo).toUpperCase();
	if(lotNo == "" || lotNo == lastLoadedLotNo){
		return;
	}
	lastLoadedLotNo = lotNo;
	$('#lotNo').val(lotNo);
	// as FNG_S06_getDetailByLotNo, but SNP comes from the FG master
	getJSON("FNG_S08_getDetailByLotNo", {"lotNo": lotNo}, function(result){
		current = null;
		$('#wip, #snp_wip, #qty').val("");
		$('#customerName, #fgName').html("&nbsp;");
		if(result == null){
			return;
		}
		// same rule as 6.04 getDataByLot: a vendor FG no. means special label
		var labelType = (result.vendorFgNo != null && result.vendorFgNo != "") ? "S" : (result.labelType || "N");
		current = {
			"tagId": result.tagId, "lotNo": lotNo, "wip": result.wip,
			"customerId": result.customerId, "fgId": result.fgId,
			"snp_wip": result.snp_wip, "qty": result.qty, "labelType": labelType,
			"vendorCode": emptyToNull(result.vendorCode), "vendorFgNo": emptyToNull(result.vendorFgNo)
		};
		current.remain = result.printQtyRemain;
		$('#wip').val(result.wip);
		$('#snp_wip').val(result.snp_wip);
		$('#qty').val(result.qty);
		showCustomerAndFg(result.customerId, result.fgId);
	});
}
function emptyToNull(v){
	return (v == null || v == "") ? null : v;
}
function showCustomerAndFg(customerId, fgId){
	var showFg = function(list){
		$.each(list, function(i, item){
			if(item.fgId == fgId){ $('#fgName').html(item.fg); }
		});
	};
	var showCustomer = function(map){
		if(map[customerId] != undefined){ $('#customerName').html(map[customerId]); }
		if(fgListCache[customerId] != undefined){
			showFg(fgListCache[customerId]);
		}else{
			postJSON("boxFgNoNameByCustomer", {"customerId": customerId}, function(list){
				fgListCache[customerId] = list;
				showFg(list);
			});
		}
	};
	if(customerListCache != null){
		showCustomer(customerListCache);
	}else{
		postJSON("FNG_S06_boxCustomer", {}, function(map){
			customerListCache = map;
			showCustomer(map);
		});
	}
}

function preview(){
	message.clear();
	var errors = [];
	if(current == null || current.lotNo != $.trim($('#lotNo').val()).toUpperCase()){
		errors.push({'code': 'err.cmm.001', 'arguments': ["Lot No."]});
	}
	var snp = $('#snp_wip').val() * 1, qty = $('#qty').val() * 1;
	if(!(snp > 0)){ errors.push({"code": "err.cmm.014", "arguments": ["SNP"]}); }
	if(!(qty > 0)){ errors.push({"code": "err.cmm.014", "arguments": ["QTY"]}); }
	if(errors.length == 0){
		if(current.labelType == "S"){
			errors.push({"code": "err.fng.015", "arguments": [current.lotNo]});
		}
		if(lots.length > 0){
			var first = lots[0].header;
			if(first.customerId != current.customerId || first.fgId != current.fgId || first.wip != current.wip){
				errors.push({"code": "err.fng.014", "arguments": [current.lotNo]});
			}
		}
		for(var i = 0; i < lots.length; i++){
			if(lots[i].header.lotNo == current.lotNo){
				errors.push({"code": "err.cmm.020", "arguments": [i + 1, "Lot No. " + current.lotNo]});
			}
		}
	}
	if(errors.length > 0){
		message.setErrors(errors);
		return;
	}
	var header = $.extend({}, current);
	delete header.remain;
	header.snp_wip = snp;
	header.qty = qty;
	var remain = current.remain;
	var params = {"lotNo": header.lotNo, "tagId": header.tagId, "wipName": header.wip,
			"customerId": header.customerId, "fgId": header.fgId, "snp_wip": snp, "qty": qty};
	postJSONObject("FNG_S06_preview", params, function(seqs){
		lots.push({"header": header, "remain": remain, "seqs": seqs});
		renderTable();
		lastLoadedLotNo = null;
		$('#lotNo').val("").focus();
	});
}

//---------------- table ----------------
function renderTable(){
	var body = $('#seqTbl tbody').empty();
	$.each(lots, function(li, lot){
		var remain = (lot.remain == null || lot.remain == undefined) ? "" : " &middot; Print QTY remain " + lot.remain;
		body.append('<tr class="grp"><td colspan="4">Lot ' + lot.header.lotNo + remain
				+ ' &middot; Qty ' + lot.header.qty + ', seq total <span class="lotSum" lot="' + li + '">' + seqTotal(lot) + '</span>'
				+ ' &nbsp; <a href="#" class="delLot" rel="' + li + '">[remove]</a></td></tr>');
		$.each(lot.seqs, function(si, s){
			// a printed seq may be ticked again (QR tag or reprint); onSeqClick asks first
			var printed = s.printStatus == 1;
			body.append('<tr><td><input type="checkbox" class="seqChk" lot="' + li + '" seq="' + si + '" /></td>'
					+ '<td>' + s.lotSeqNo + '</td>'
					+ '<td><input type="text" class="seqQty posInt" size="6" lot="' + li + '" seq="' + si + '" value="' + s.lotSeqQty + '" /></td>'
					+ '<td>' + (printed ? 'Printed' : '-') + '</td></tr>');
		});
	});
	updateCounter();
}
function seqTotal(lot){
	var total = 0;
	$.each(lot.seqs, function(i, s){ total += s.lotSeqQty * 1; });
	return total;
}
/** Qty of a seq is editable as in 6.04; the seq total of a lot must stay equal to the lot Qty. */
function onQtyEdit(){
	var lot = lots[$(this).attr('lot') * 1];
	lot.seqs[$(this).attr('seq') * 1].lotSeqQty = $.trim($(this).val());
	$('#seqTbl span.lotSum[lot=' + $(this).attr('lot') + ']').html(seqTotal(lot));
	updateCounter();
}
/** err.fng.007 (6.04 rule) for every lot that is going to be saved; empty = OK. */
function qtyErrors(toSave){
	var errors = [];
	$.each(toSave, function(i, lot){
		var total = 0, bad = false;
		$.each(lot.lotSequenceList, function(j, s){
			if(!(s.lotSeqQty * 1 > 0) || !/^\d+$/.test(String(s.lotSeqQty))){ bad = true; }
			total += s.lotSeqQty * 1;
		});
		if(bad){
			errors.push({"code": "err.cmm.014", "arguments": ["Qty of Lot " + lot.lotNo]});
		}else if(total != lot.qty * 1){
			errors.push({"code": "err.fng.007", "arguments": []});
		}
	});
	return errors;
}
function selectedSeqs(){
	var list = [];
	$('#seqTbl input.seqChk:checked').each(function(){
		var lot = lots[$(this).attr('lot') * 1];
		list.push({"lot": lot, "seq": lot.seqs[$(this).attr('seq') * 1]});
	});
	return list;
}
function onSeqClick(){
	if($(this).attr('checked') && selectedSeqs().length > MAX_SEQ){
		$(this).attr('checked', false);
		message.setError('err.fng.013');
	}
	if($(this).attr('checked')){
		var seq = lots[$(this).attr('lot') * 1].seqs[$(this).attr('seq') * 1];
		if(seq.printStatus == 1 && !confirm(message.getMessage('cfm.fng.001', [seq.lotSeqNo]))){
			$(this).attr('checked', false);
		}
	}
	$(this).closest('tr').toggleClass('sel', !!$(this).attr('checked'));
	updateCounter();
}
function updateCounter(){
	var sel = selectedSeqs(), total = 0;
	$.each(sel, function(i, x){ total += x.seq.lotSeqQty * 1; });
	$('#selCounter').html('Selected ' + sel.length + '/' + MAX_SEQ + ' seq, total ' + total + ' pcs');
}
/** The lots that have a ticked seq, each with ALL its seqs (6.04 saves the full list). */
function lotsToSave(){
	var ticked = {};
	$.each(selectedSeqs(), function(i, x){ ticked[x.seq.lotSeqNo] = true; });
	var out = [];
	$.each(lots, function(li, lot){
		var hasTick = false;
		var list = $.map(lot.seqs, function(s){
			var flag = ticked[s.lotSeqNo] ? 1 : 0;
			if(flag == 1){ hasTick = true; }
			return {"tagId": s.tagId, "seq": s.seq, "lotSeqNo": s.lotSeqNo, "lotSeqQty": s.lotSeqQty,
					"printStatus": s.printStatus, "isReadOnly": s.isReadOnly, "flagPrint": flag};
		});
		if(hasTick){
			out.push($.extend({}, lot.header, {"lotSequenceList": list}));
		}
	});
	return out;
}
function choosePrinter(onPrint){
	pendingPrint = onPrint;
	postJSONSync("boxPrinterName", {}, function(list){
		var box = $('#cboPrinter').empty();
		$.each(list, function(i, p){
			box.append($("<option></option>").val(p.printerName).html(p.displayName));
		});
		if($('#printerName').val()){ box.val($('#printerName').val()); }
	});
	$('#printDialog').dialog({height: 150, width: 400, modal: true});
}
/** Re-preview every lot in the table so print status is current. */
function refreshTable(){
	var pending = lots.length;
	if(pending == 0){ renderTable(); return; }
	$.each(lots, function(li, lot){
		getJSON("FNG_S06_getDetailByLotNo", {"lotNo": lot.header.lotNo}, function(d){
			if(d != null){
				lot.header.tagId = d.tagId;
				lot.remain = d.printQtyRemain;
			}
			var h = lot.header;
			postJSONObject("FNG_S06_preview", {"lotNo": h.lotNo, "tagId": h.tagId, "wipName": h.wip,
					"customerId": h.customerId, "fgId": h.fgId, "snp_wip": h.snp_wip, "qty": h.qty}, function(seqs){
				lot.seqs = seqs;
				if(--pending == 0){ renderTable(); }
			});
		});
	});
}

//---------------- print ----------------
function printMulti(){
	message.clear();
	var n = selectedSeqs().length;
	if(n < 1 || n > MAX_SEQ){
		message.setError('err.fng.013');
		return;
	}
	var toSave = lotsToSave(), errors = qtyErrors(toSave);
	if(errors.length > 0){
		message.setErrors(errors);
		return;
	}
	if(!confirm(message.getMessage("cfm.cmm.006"))){
		return;
	}
	choosePrinter(function(printerName){
		postJSONObject("FNG_S08_print", {"printerName": printerName, "lots": toSave}, function(tag){
			message.showMessage(tag);
			if(tag.refNo){
				$('#refNo').val(tag.refNo);
				renderTag(tag);
			}
			refreshTable();
		});
	});
}
/** One normal 6.04 tag per ticked seq, lot by lot through FNG_S06_printLabel. */
function printSingle(){
	message.clear();
	if(selectedSeqs().length == 0){
		message.setError('err.fng.013');
		return;
	}
	var queue = lotsToSave(), errors = qtyErrors(queue);
	if(errors.length > 0){
		message.setErrors(errors);
		return;
	}
	if(!confirm(message.getMessage("cfm.cmm.006"))){
		return;
	}
	choosePrinter(function(printerName){
		var next = function(){
			if(queue.length == 0){ refreshTable(); return; }
			var lot = queue.shift();
			lot.printerName = printerName;
			postJSONObject("FNG_S06_printLabel", lot, function(bean){
				message.showMessage(bean);
				if(bean.errors && bean.errors.length > 0){ refreshTable(); return; }
				next();
			});
		};
		next();
	});
}

//---------------- QR tag search / reprint / cancel ----------------
function refNoValue(){
	return $.trim($('#refNo').val()).toUpperCase();
}
function findTag(){
	message.clear();
	if(refNoValue() == ""){
		message.setError('err.cmm.001', ["QR Tag No."]);
		return;
	}
	getJSON("FNG_S08_find", {"refNo": refNoValue()}, function(tag){
		message.showMessage(tag);
		renderTag(tag);
	});
}
function reprintTag(){
	message.clear();
	if(refNoValue() == ""){
		message.setError('err.cmm.001', ["QR Tag No."]);
		return;
	}
	if(!confirm(message.getMessage("cfm.cmm.006"))){
		return;
	}
	choosePrinter(function(printerName){
		postJSON("FNG_S08_reprint", {"refNo": refNoValue(), "printerName": printerName}, function(tag){
			message.showMessage(tag);
			renderTag(tag);
		});
	});
}
function cancelTag(){
	message.clear();
	if(refNoValue() == ""){
		message.setError('err.cmm.001', ["QR Tag No."]);
		return;
	}
	if(!confirm(message.getMessage("cfm.cmm.002"))){
		return;
	}
	postJSON("FNG_S08_cancel", {"refNo": refNoValue()}, function(tag){
		message.showMessage(tag);
		renderTag(tag);
		refreshTable();
	});
}
function formatDate(ms){
	if(ms == null){ return ""; }
	var d = new Date(ms);
	var p = function(n){ return (n < 10 ? "0" : "") + n; };
	return d.getFullYear() + "-" + p(d.getMonth() + 1) + "-" + p(d.getDate());
}
function renderTag(tag){
	var body = $('#mlTbl tbody').empty();
	if(!tag || !tag.refNo){
		return;
	}
	var status = tag.status == 'C' ? 'Cancelled' : 'Active';
	$.each(tag.details, function(i, d){
		var head = i == 0
			? '<td rowspan="' + tag.details.length + '">' + tag.refNo + '</td>'
				+ '<td rowspan="' + tag.details.length + '">' + status + '</td>'
				+ '<td rowspan="' + tag.details.length + '">' + tag.printCount + '</td>'
				+ '<td rowspan="' + tag.details.length + '">' + (tag.customerName || '') + '</td>'
				+ '<td rowspan="' + tag.details.length + '">' + (tag.fgNo || '') + '</td>'
				+ '<td rowspan="' + tag.details.length + '">' + tag.wip + '</td>'
			: '';
		body.append('<tr>' + head + '<td>' + d.lotSeqNo + '</td><td>' + d.qty + '</td><td>' + formatDate(d.dateCode) + '</td></tr>');
	});
}
