package th.co.nttdata.tki.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.TMultiLotTag;
import th.co.nttdata.tki.bean.TMultiLotTagDetail;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TMultiLotTagDao;

@Repository
@SuppressWarnings("unchecked")
public class TMultiLotTagDaoImpl extends AbstractBaseDao implements
		TMultiLotTagDao {

	@Override
	public int nextRunning(String yyMMdd) {
		return (Integer) queryForObject("t_multilot_tag.select_next_running", yyMMdd);
	}

	@Override
	public void insertHeader(TMultiLotTag tag) {
		insert("t_multilot_tag.insert_header", tag);
	}

	@Override
	public void insertDetails(TMultiLotTag tag) {
		insert("t_multilot_tag.insert_details", tag);
	}

	@Override
	public TMultiLotTag findByRefNo(String refNo) {
		return (TMultiLotTag) queryForObject("t_multilot_tag.select_by_refno", refNo);
	}

	@Override
	public List<TMultiLotTagDetail> findDetails(String refNo) {
		return queryForList("t_multilot_tag.select_details", refNo);
	}

	@Override
	public List<TMultiLotTagDetail> findScanRows(String refNo) {
		return queryForList("t_multilot_tag.select_scan_rows", refNo);
	}

	@Override
	public List<TMultiLotTagDetail> findActiveDetailsForSeqs(List<String> lotSeqNos) {
		if (lotSeqNos == null || lotSeqNos.isEmpty()) {
			return new ArrayList<TMultiLotTagDetail>();
		}
		return queryForList("t_multilot_tag.select_active_by_seq", lotSeqNos);
	}

	@Override
	public List<TMultiLotTagDetail> findActiveDetailsByLotNo(String lotNo) {
		return queryForList("t_multilot_tag.select_active_by_lotno", lotNo);
	}

	@Override
	public String findActiveRefNoForSeq(String lotSeqNo) {
		return (String) queryForObject("t_multilot_tag.select_active_refno_by_seq", lotSeqNo);
	}

	@Override
	public List<String> findScannedInSeqs(List<String> lotSeqNos) {
		if (lotSeqNos == null || lotSeqNos.isEmpty()) {
			return new ArrayList<String>();
		}
		return queryForList("t_multilot_tag.select_scanned_in_seqs", lotSeqNos);
	}

	@Override
	public Date findLotPrintDate(String lotNo) {
		return (Date) queryForObject("t_multilot_tag.select_lot_printdate", lotNo);
	}

	@Override
	public Integer findFgSnp(Integer fgId) {
		return (Integer) queryForObject("t_multilot_tag.select_fg_snp", fgId);
	}

	@Override
	public int sumFgInForRefNo(String refNo) {
		return (Integer) queryForObject("t_multilot_tag.select_sum_fgin", refNo);
	}

	@Override
	public void updateReprint(TMultiLotTag tag) {
		update("t_multilot_tag.update_reprint", tag);
	}

	@Override
	public int updateCancel(TMultiLotTag tag) {
		return update("t_multilot_tag.update_cancel", tag);
	}
}
