package th.co.nttdata.tki.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.bean.TFGDetail;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.TFGDetailDao;

@Repository
@SuppressWarnings("unchecked")
public class TFGDetailDaoImpl extends AbstractBaseDao implements TFGDetailDao {
	@Value("#{settings['FNG.FNG_S01.ROWNOINSERT']}")
	private Integer ROWNOINSERT;
	
	@Override
	public List<TFGDetail> insert(TFG TFG) {
		List<TFGDetail> list = new ArrayList<TFGDetail>();
		list.add((TFGDetail) super.insert("t_fg_detail.insert", TFG));
		return list;
	}

	@Override
	public int insertRow(TFG TFG) {
		// TFG.setUpdateBy(getUsername());
		return super.update("t_fg_detail.insert", TFG);
	}

	@Override
	public TFG query(TFG TFG) {
		TFG.setDetails(queryForList("t_fg_detail.query", TFG,
				getSkipResult(TFG), TFG.getPageCount()));
		calPageTotal("t_fg_detail.count", TFG);
		return TFG;
	}

	@Override
	public TFG selectFNG_R01(TFG TFG) {
		TFG.setStockList(queryForList("t_fg_detail.selectFNG_R01", TFG));

		Map<String, TFGDetail> mapFgOut = queryForMap(
				"t_fg_detail.selectFNG_R01Detail", TFG, "reportTypeName");
		TFG.setDetailMap(mapFgOut);

		return TFG;
	}

	@Override
	public TFG selectFNG_R03(TFG TFG) {
		TFG.setDetails(queryForList("t_fg_detail.query", TFG));
		return TFG;
	}

	@Override
	public List<TFGDetail> queryReportType() {
		return queryForList("t_fg_detail.selectReportType");
	}

	@Override
	public Boolean checkLotNo(TFGDetail workOrder) {
		Boolean isDuplicate = false;
		Integer lotList = (Integer) queryForObject(
				"t_fg_detail.select_check_lotseq", workOrder);
		if (lotList > 0) {
			isDuplicate = true;
		}
		return isDuplicate;
	}

	@Override
	public Boolean checkLotNotExist(TFGDetail t) {
		Boolean isNotExist = false;
		Integer lotList = (Integer) queryForObject(
				"t_fg_detail.select_check_not_exist", t);
		if (lotList > 0) {
			isNotExist = true;
		}
		return isNotExist;
	}

	@Override
	public void insertFgDetail(TFG tfg) {
		boolean hasDetails = tfg.getDetails() != null && 0 < tfg.getDetails().size(); 
		if (hasDetails) {
			List<TFGDetail> details = tfg.getDetails();
			int size = details.size();
			
			if(ROWNOINSERT == null)
				ROWNOINSERT = 100;
			
			for (int i = 0; i < size; ) {
				int maxIndex = i + ROWNOINSERT > size ? size : i + ROWNOINSERT;
				List<TFGDetail> subDetails = details.subList(i, maxIndex);
				tfg.setDetails(subDetails);
				update("t_fg_detail.insertFgDetail", tfg);
				i = maxIndex;
			}
			tfg.setDetails(details);
		}
	}

}