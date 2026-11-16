package th.co.nttdata.tki.dao;

import java.util.Date;
import java.util.List;

import th.co.nttdata.tki.bean.TMultiLotTag;
import th.co.nttdata.tki.bean.TMultiLotTagDetail;

public interface TMultiLotTagDao {

	int nextRunning(String yyMMdd);

	void insertHeader(TMultiLotTag tag);

	void insertDetails(TMultiLotTag tag);

	TMultiLotTag findByRefNo(String refNo);

	List<TMultiLotTagDetail> findDetails(String refNo);

	List<TMultiLotTagDetail> findScanRows(String refNo);

	List<TMultiLotTagDetail> findActiveDetailsForSeqs(List<String> lotSeqNos);

	List<TMultiLotTagDetail> findActiveDetailsByLotNo(String lotNo);

	/** refno of the active ML the seq is on, null if none */
	String findActiveRefNoForSeq(String lotSeqNo);

	/** the given seqs that were already scanned IN */
	List<String> findScannedInSeqs(List<String> lotSeqNos);

	Date findLotPrintDate(String lotNo);

	Integer findFgSnp(Integer fgId);

	int sumFgInForRefNo(String refNo);

	void updateReprint(TMultiLotTag tag);

	int updateCancel(TMultiLotTag tag);

}
