package th.co.nttdata.tki.blogic.fng.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.FNG_S08PrintRequest;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.PrintTagLabel;
import th.co.nttdata.tki.bean.TLotSequence;
import th.co.nttdata.tki.bean.TMultiLotTag;
import th.co.nttdata.tki.bean.TMultiLotTagDetail;
import th.co.nttdata.tki.bean.TTagLabelDetail;
import th.co.nttdata.tki.blogic.fng.FNG_S06Logic;
import th.co.nttdata.tki.blogic.fng.FNG_S08Logic;
import th.co.nttdata.tki.blogic.fng.MultiLotValidationException;
import th.co.nttdata.tki.blogic.fng.multilot.Candidate;
import th.co.nttdata.tki.blogic.fng.multilot.LabelData;
import th.co.nttdata.tki.blogic.fng.multilot.LabelLine;
import th.co.nttdata.tki.blogic.fng.multilot.MultiLotRules;
import th.co.nttdata.tki.blogic.fng.multilot.MultiLotZpl;
import th.co.nttdata.tki.dao.TLotSequenceDao;
import th.co.nttdata.tki.dao.TMultiLotTagDao;
import th.co.nttdata.tki.dao.TTagLabelDao;

@Service
public class FNG_S08LogicImpl implements FNG_S08Logic {
	@Autowired
	private FNG_S06Logic fng_S06Logic;

	@Autowired
	private TMultiLotTagDao tMultiLotTagDao;

	@Autowired
	private TTagLabelDao tTagLabelDao;

	@Autowired
	private TLotSequenceDao tLotSequenceDao;

	@Override
	public TTagLabelDetail getLotDetail(String lotNo) {
		// same lookup as FNG_S06_getDetailByLotNo
		TTagLabelDetail lot = fng_S06Logic.getLotNoDetailExisting(lotNo);
		if (lot != null) {
			Integer printQtyRemain = fng_S06Logic.getPrintQtyRemain(lot.getTagId());
			lot.setPrintQtyRemain(printQtyRemain == null ? 0 : printQtyRemain);
		} else {
			lot = fng_S06Logic.getLotNoDetail(lotNo);
		}
		if (lot != null && lot.getFgId() != null) {
			// an FG with no SNP in the master keeps the lot's SNP
			Integer snp = tMultiLotTagDao.findFgSnp(lot.getFgId());
			if (snp != null && snp.intValue() > 0) {
				lot.setSnp_wip(snp);
			}
		}
		return lot;
	}

	@Override
	public TMultiLotTag printMultiLot(FNG_S08PrintRequest req) throws Exception {
		// 1. save every lot the way 6.04 does, and collect the ticked seqs
		List<String> selectedNos = new ArrayList<String>();
		Map<String, TTagLabelDetail> lotOfSeq = new HashMap<String, TTagLabelDetail>();
		for (TTagLabelDetail lot : req.getLots()) {
			fng_S06Logic.saveTagAndSequences(lot);
			for (TLotSequence seq : lot.getLotSequenceList()) {
				if (!seq.isDelete() && seq.getFlagPrint() != null
						&& seq.getFlagPrint().intValue() == 1) {
					selectedNos.add(seq.getLotSeqNo());
					lotOfSeq.put(seq.getLotSeqNo(), lot);
				}
			}
		}

		// 2. candidates from the DB as saved. A seq printed alone before may go on the tag (its old
		// tag stops working in 6.01); one already scanned IN may not
		List<String> scannedIn = tMultiLotTagDao.findScannedInSeqs(selectedNos);
		Map<String, String> activeRefNo = new HashMap<String, String>();
		for (TMultiLotTagDetail d : tMultiLotTagDao.findActiveDetailsForSeqs(selectedNos)) {
			activeRefNo.put(d.getLotSeqNo(), d.getRefNo());
		}
		Map<String, TTagLabelDetail> headerOfLot = new HashMap<String, TTagLabelDetail>();
		Map<String, PrintTagLabel> rowOfSeq = new HashMap<String, PrintTagLabel>();
		for (TTagLabelDetail lot : req.getLots()) {
			headerOfLot.put(lot.getLotNo(), fng_S06Logic.getLotNoDetailExisting(lot.getLotNo()));
			for (PrintTagLabel row : tTagLabelDao.selectPrint(lot.getLotNo())) {
				rowOfSeq.put(row.getLotSeqNo(), row);
			}
		}
		List<Candidate> candidates = new ArrayList<Candidate>();
		List<PrintTagLabel> rows = new ArrayList<PrintTagLabel>();
		for (String no : selectedNos) {
			PrintTagLabel row = rowOfSeq.get(no);
			TTagLabelDetail header = headerOfLot.get(lotOfSeq.get(no).getLotNo());
			if (row == null || header == null) {
				continue;
			}
			rows.add(row);
			candidates.add(new Candidate(no, row.getLotSeqQty(), header.getCustomerId(),
					header.getFgId(), header.getWip(), header.getLabelType(),
					scannedIn.contains(no), activeRefNo.get(no)));
		}

		// 3. rules; throwing rolls back step 1
		List<String[]> errors = MultiLotRules.validateSelection(candidates);
		if (!errors.isEmpty()) {
			throw new MultiLotValidationException(errors);
		}

		// 4. new ML
		Date now = new Date();
		String refNo = MultiLotRules.formatRefNo(now,
				tMultiLotTagDao.nextRunning(new SimpleDateFormat("yyMMdd").format(now)));
		Candidate first = candidates.get(0);
		TMultiLotTag tag = new TMultiLotTag();
		tag.setRefNo(refNo);
		tag.setCustomerId(first.customerId);
		tag.setFgId(first.fgId);
		tag.setWip(first.wip);
		tag.setPrinterName(req.getPrinterName());
		for (int i = 0; i < candidates.size(); i++) {
			Candidate c = candidates.get(i);
			TMultiLotTagDetail d = new TMultiLotTagDetail();
			d.setRefNo(refNo);
			d.setLineNo(i + 1);
			d.setLotSeqNo(c.lotSeqNo);
			d.setQty(c.qty);
			d.setDateCode(tMultiLotTagDao.findLotPrintDate(c.lotSeqNo.substring(0, 14)));
			tag.getDetails().add(d);
		}
		tMultiLotTagDao.insertHeader(tag);
		tMultiLotTagDao.insertDetails(tag);

		// 5. print; a failed send rolls everything back
		PrintTagLabel firstRow = rows.get(0);
		send(req.getPrinterName(), label(refNo, first.wip, firstRow.getCustomerName(),
				firstRow.getFgNo(), firstRow.getFgName(), tag.getDetails()));

		// 6. mark the seqs printed
		for (Candidate c : candidates) {
			TLotSequence lot = new TLotSequence();
			lot.setLotSeqNo(c.lotSeqNo);
			tLotSequenceDao.updatePrintStatus(lot);
		}

		TMultiLotTag result = findByRefNo(refNo);
		result.getInfos().add(new Message("inf.cmm.006", new String[] {}));
		return result;
	}

	@Override
	public TMultiLotTag findByRefNo(String refNo) {
		TMultiLotTag tag = tMultiLotTagDao.findByRefNo(refNo);
		if (tag == null) {
			tag = new TMultiLotTag();
			tag.getErrors().add(new Message("err.fng.018", new String[] { refNo }));
			return tag;
		}
		tag.setDetails(tMultiLotTagDao.findDetails(refNo));
		return tag;
	}

	@Override
	public TMultiLotTag reprint(String refNo, String printerName) throws Exception {
		TMultiLotTag tag = findByRefNo(refNo);
		if (tag.hasErrors()) {
			return tag;
		}
		if (!"A".equals(tag.getStatus())) {
			tag.getErrors().add(new Message("err.fng.020", new String[] { refNo }));
			return tag;
		}
		send(printerName, label(refNo, tag.getWip(), tag.getCustomerName(),
				tag.getFgNo(), tag.getFgName(), tag.getDetails()));
		tag.setPrinterName(printerName);
		tMultiLotTagDao.updateReprint(tag);

		TMultiLotTag result = findByRefNo(refNo);
		result.getInfos().add(new Message("inf.cmm.006", new String[] {}));
		return result;
	}

	@Override
	public TMultiLotTag cancel(String refNo) {
		TMultiLotTag tag = findByRefNo(refNo);
		if (tag.hasErrors()) {
			return tag;
		}
		if (!"A".equals(tag.getStatus())) {
			tag.getErrors().add(new Message("err.fng.020", new String[] { refNo }));
			return tag;
		}
		if (tMultiLotTagDao.sumFgInForRefNo(refNo) > 0) {
			tag.getErrors().add(new Message("err.fng.019", new String[] { refNo }));
			return tag;
		}
		if (tMultiLotTagDao.updateCancel(tag) == 0) {
			tag.getErrors().add(new Message("err.fng.020", new String[] { refNo }));
			return tag;
		}
		// the seqs stay printed: they can go on a new QR tag or be reprinted alone in 6.04
		return findByRefNo(refNo);
	}

	private static LabelData label(String refNo, String wip, String customerName,
			String fgNo, String fgName, List<TMultiLotTagDetail> details) {
		LabelData d = new LabelData();
		d.refNo = refNo;
		d.wip = wip;
		d.customerName = customerName;
		d.fgNo = fgNo;
		// the 6.04 normal label prints fgname as PART NAME too
		d.partName = fgName;
		for (TMultiLotTagDetail detail : details) {
			LabelLine line = new LabelLine();
			line.lotSeqNo = detail.getLotSeqNo();
			line.qty = detail.getQty();
			line.dateCode = detail.getDateCode();
			d.lines.add(line);
		}
		return d;
	}

	/** Always fails with a RuntimeException: the tx advice commits on checked exceptions. */
	private void send(String printerName, LabelData d) {
		boolean sent;
		try {
			sent = fng_S06Logic.sendRaw(printerName, MultiLotZpl.build(d).getBytes("US-ASCII"));
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Print failed: " + printerName + " (" + e + ")", e);
		}
		if (!sent) {
			throw new RuntimeException("Printer not found: " + printerName);
		}
	}
}
