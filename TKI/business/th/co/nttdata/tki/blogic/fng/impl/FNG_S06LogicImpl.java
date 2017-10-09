package th.co.nttdata.tki.blogic.fng.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.PrintTagLabel;
import th.co.nttdata.tki.bean.TFGStock;
import th.co.nttdata.tki.bean.TLotNo;
import th.co.nttdata.tki.bean.TLotSequence;
import th.co.nttdata.tki.bean.TTagLabelDetail;
import th.co.nttdata.tki.bean.TTaglabelHistory;
import th.co.nttdata.tki.blogic.fng.FNG_S06Logic;
import th.co.nttdata.tki.dao.TFGStockDao;
import th.co.nttdata.tki.dao.TLotNoDao;
import th.co.nttdata.tki.dao.TLotSequenceDao;
import th.co.nttdata.tki.dao.TTagLabelDao;
import th.co.nttdata.tki.dao.TTaglabelHistoryDao;

@Service
public class FNG_S06LogicImpl implements FNG_S06Logic {
	@Autowired
	private TFGStockDao tfgStockDao;

	@Autowired
	private TLotNoDao tLotNoDao;

	@Autowired
	private TTagLabelDao tTagLabelDao;

	@Autowired
	private TTaglabelHistoryDao tTaglabelHistoryDao;

	@Autowired
	private TLotSequenceDao tLotSequenceDao;

//	private static final Log logger = LogFactory.getLog("FNG_S06LogicImpl");

	@Override
	public TFGStock searchFGAdjustHistory(TFGStock tfgStock) {
		return tfgStockDao.queryFGAdjustHistory(tfgStock);
	}

	@Override
	public List<TLotNo> getLotNoAuto(String lotNo) {
		return tLotNoDao.getLotNoAuto(lotNo);
	}

	@Override
	public TTagLabelDetail getLotNoDetail(String lotNo) {
		return tTagLabelDao.getLotNoDetail(lotNo);
	}

	@Override
	public TTagLabelDetail getLotNoDetailExisting(String lotNo) {
		return tTagLabelDao.getLotNoDetailExisting(lotNo);
	}

	@Override
	public List<TLotSequence> preview(TTagLabelDetail taglabel) {
		List<TLotSequence> results = new ArrayList<TLotSequence>();
		String lotNo = taglabel.getLotNo();
		TTagLabelDetail temp = this.getLotNoDetailExisting(lotNo);
		if (null != temp
				&& StringUtils.isNotEmpty(String.valueOf(temp.getTagId()))) {
			taglabel.setTagId(temp.getTagId());
		}

		if (taglabel.getTagId() != null) {
			results = tLotSequenceDao.getLotSeqExisting(taglabel);
		}

		int sequenceNumber = 1;
		String seqNo = null;
		if (results.isEmpty()) {
			int maxSequenceNumber = taglabel.getQty() / taglabel.getSnp_wip();
			while (sequenceNumber <= maxSequenceNumber
					&& maxSequenceNumber < 1000) {
				TLotSequence lotSeq = new TLotSequence();
				seqNo = fillDigit(lotNo, sequenceNumber);
				lotSeq.setSeq(sequenceNumber);
				lotSeq.setLotSeqNo(seqNo);
				lotSeq.setLotSeqQty(taglabel.getSnp_wip());
				lotSeq.setFlagPrint(0);
				lotSeq.setPrintStatus(0);
				lotSeq.setIsReadOnly(0);
				results.add(lotSeq);
//				logger.info("Lot seq No. : " + seqNo + "\n");
				sequenceNumber++;
				seqNo = null;
			}

			// calculate last row
			int lastRow = taglabel.getQty() % taglabel.getSnp_wip();
			if (lastRow > 0) {
				TLotSequence lotSeq = new TLotSequence();
				seqNo = fillDigit(lotNo, sequenceNumber);
				lotSeq.setSeq(sequenceNumber);
				lotSeq.setLotSeqNo(seqNo);
				lotSeq.setLotSeqQty(lastRow);
				lotSeq.setFlagPrint(0);
				lotSeq.setPrintStatus(0);
				lotSeq.setIsReadOnly(0);
				results.add(lotSeq);
//				logger.info("Lot seq No. : " + seqNo + "\n");
			}
		}
		return results;
	}

	public String fillDigit(String lotNo, int sequenceNumber) {
		String seqNo = null;
		if (sequenceNumber < 10) {
			seqNo = lotNo.concat("-100" + sequenceNumber);
		} else if (sequenceNumber < 100) {
			seqNo = lotNo.concat("-10" + sequenceNumber);
		} else if (sequenceNumber < 1000) {
			seqNo = lotNo.concat("-1" + sequenceNumber);
		}
		return seqNo;
	}

	@Override
	public TTagLabelDetail printLabel(TTagLabelDetail tagLabel)
			throws Exception {
		// To declare variable for returning msg
		TTagLabelDetail param = new TTagLabelDetail();
		String lotNo = tagLabel.getLotNo();
		TTagLabelDetail oldData = null;

		TTagLabelDetail temp = this.getLotNoDetailExisting(lotNo);
		if (null != temp
				&& StringUtils.isNotEmpty(String.valueOf(temp.getTagId()))) {
			tagLabel.setTagId(temp.getTagId());
		}
		if (tagLabel.getTagId() == null || "".equals(tagLabel.getTagId())) {
			oldData = tTagLabelDao.getLotNoDetail(lotNo);

			/*
			 * Compare between Taglabel hidden field and old data for changing
			 * data
			 */
			boolean isChangefromOldData = false;
			isChangefromOldData = !(oldData.getCustomerId().intValue() == tagLabel
					.getCustomerId().intValue()
					&& oldData.getFgId().intValue() == tagLabel.getFgId()
							.intValue() && oldData.getSnp_wip().intValue() == tagLabel
					.getSnp_wip().intValue());
			TTaglabelHistory history = null;
			if (isChangefromOldData) {
				history = new TTaglabelHistory(lotNo, oldData.getCustomerId(),
						tagLabel.getCustomerId(), oldData.getFgId(),
						tagLabel.getFgId(), oldData.getSnp_wip(),
						tagLabel.getSnp_wip(), tagLabel.getQty());
				tTaglabelHistoryDao.insertHistory(history);
			}
		} else {
			oldData = this.getLotNoDetailExisting(lotNo);
		}

		Integer tagId = 0;
		if (tagLabel.getTagId() == null || "".equals(tagLabel.getTagId())) {
			tTagLabelDao.insertTaglabel(tagLabel);

			// get new tagId after insert
			tagId = this.getLotNoDetailExisting(lotNo).getTagId();
		} else {
			tTagLabelDao.updateTaglabel(tagLabel);

			// get tagId from old data
			tagId = tagLabel.getTagId();
		}

		// separate lot seq
		tLotSequenceDao.deleteByTag(tagId);

		// check is delete all ?
		Integer countDelete = 0;
		Integer lotSeqSize = tagLabel.getLotSequenceList().size();
		for (TLotSequence tmp : tagLabel.getLotSequenceList()) {
			tmp.setTagId(tagId);
			if (tmp.isDelete()) {
				countDelete++;
			}
		}
		if (countDelete.intValue() == lotSeqSize.intValue()) {
			tTagLabelDao.deleteByLotNo(tagLabel);
		} else {
			// insert lot seq
			TLotSequence insertLot = new TLotSequence();
			for (TLotSequence tmp : tagLabel.getLotSequenceList()) {
				tmp.setTagId(tagId);
				if (!tmp.isDelete()) {
					insertLot.addLotSequence(tmp);
				}
			}
			if (!insertLot.getLotSequenceList().isEmpty()) {
				tLotSequenceDao.insertLotSequence(insertLot);
			}
		}

		// get session for get or set printer value
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		if (org.apache.commons.lang.StringUtils.isNotEmpty(tagLabel
				.getPrinterId())) {
			session.setAttribute("FNG_S06printerName",
					tagLabel.getPrinterName());
		}
		String printerName = tagLabel.getPrinterName();

		// get path
		String realfilePath = request.getSession().getServletContext()
				.getRealPath("");
		String filePath = null;

		// get lot sequence for print
		List<PrintTagLabel> printTagLabel = tTagLabelDao.selectPrint(tagLabel
				.getLotNo());

		// check label type
		if ("S".equals(tagLabel.getLabelType())) {
			filePath = request.getSession().getServletContext()
					.getInitParameter("special_label_template");
		}
		else {
			filePath = request.getSession().getServletContext()
					.getInitParameter("normal_label_template");
		}

		// All data of lot sequence
		for (PrintTagLabel tmp : printTagLabel) {
			// List of lot sequence which must printed.
			for (TLotSequence lot : tagLabel.getLotSequenceList()) {
				if ((tmp.getLotSeqNo().equals(lot.getLotSeqNo()))
						&& lot.getFlagPrint().intValue() == 1) {

					// read file from path
					BufferedReader is = null;
					is = new BufferedReader(new FileReader(
							realfilePath.concat(filePath)));

					// write command string
					byte[] data;
					String line = null;
					StringBuffer printData = new StringBuffer();
					while ((line = is.readLine()) != null) {
						printData.append("\n");
						// replace String
						line = line.replaceAll("_WIP_", tagLabel.getWip());
						line = line.replaceAll("_FGNO_", tmp.getFgNo());
						//Check Barcode with Qty (Barcode special 2)
						if ("S".equals(tagLabel.getLabelType()) && null != tmp.getIsBarcodeQty() && tmp.getIsBarcodeQty() == true) {
							line = line.replaceAll("_PARTNO_", tmp.getVendorFgNo()+"%"+tmp.getLotSeqQty());		
							line = line.replaceAll("PART NO.", "ICS NO.");							
						}else{
							line = line.replaceAll("_PARTNO_", tmp.getVendorFgNo());
						}
						line = line.replaceAll("_PARTNAME_", tmp.getFgName());
						line = line.replaceAll("_CUSTNAME_", tmp
								.getCustomerName().toString());
						line = line.replaceAll("_LOTNO_", lotNo);
						line = line.replaceAll("_QTY_", tmp.getLotSeqQty()
								.toString());
						line = line.replaceAll("_LOTSEQNO_", tmp.getLotSeqNo());
						line = line.replaceAll("_VENDORCODE_",
								tagLabel.getVendorCode());
//						System.out.println(line);
						
						printData.append(line);
					}

//					logger.info(printData);
					// close BufferedReader
					if (is != null) {
						is.close();
					}

					// prepare for print
					DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
					PrintService[] printService = PrintServiceLookup
							.lookupPrintServices(flavor, null);

					// check printService
					if (printService != null) {
						// print
						for (int i = 0; i < printService.length; i++) {
							if (printService[i].getName().equals(printerName)) {
								data = printData.toString().getBytes();
								Doc doc = new SimpleDoc(data, flavor, null);
								DocPrintJob ps = printService[i]
										.createPrintJob();
								ps.print(doc, null);
								tLotSequenceDao.updatePrintStatus(lot);
								param.getInfos().clear();
								param.getInfos().add(
										new Message("inf.cmm.006",
												new String[] {}));
								break;
							}
						}

					}

				}
			}

		}
		return param;
	}

	@Override
	public Integer getPrintQtyRemain(Integer tagId) {
		return tTagLabelDao.getPrintQtyRemain(tagId);
	}
}
