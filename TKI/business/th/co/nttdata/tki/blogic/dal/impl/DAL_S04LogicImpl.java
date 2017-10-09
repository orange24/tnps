package th.co.nttdata.tki.blogic.dal.impl;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TDailyWK;
import th.co.nttdata.tki.bean.TDailyWKDetail;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.dal.DAL_S04Logic;
import th.co.nttdata.tki.dao.MPartDao;
import th.co.nttdata.tki.dao.MWorkOrderDao;
import th.co.nttdata.tki.dao.TDailyWKDao;

@Service
public class DAL_S04LogicImpl extends AbstractBaseLogic implements DAL_S04Logic {

	@Autowired
	private TDailyWKDao dailyWKDao;
	@Autowired
	private MWorkOrderDao workOrderDao;
	@Autowired
	private MPartDao partDao;

	@Override
	public TDailyWK check(TDailyWK TDailyWK) {
		// <!-- Check: if 'workOrderNo' not found. -->
		// <!-- Check: if 'lotNo' not found. -->
		for (int i = 0; i < TDailyWK.getDailyWKDetailList().size(); i++) {
			TDailyWKDetail detail = TDailyWK.getDailyWKDetailList().get(i);
			MWorkOrder workOrder = new MWorkOrder();
			workOrder.setWorkOrderNo(detail.getWorkOrderNo());
			if (!"".equals(detail.getLotNo()))
				workOrder.setLotNo(detail.getWorkOrderNo() + detail.getLotNo());
			workOrder.setCustomerId(detail.getCustomerId());
			workOrder.setPartId(detail.getPartId());
			if (!workOrderDao.existsLotNo(workOrder))
				TDailyWK.getErrors().add(
						new Message("err.dal.008", new String[] { String
								.valueOf(i + 1) }));
		}
		return TDailyWK;
	}

	@Override
	public void delete(TDailyWK TDailyWK) {
		dailyWKDao.delete(TDailyWK);

		// <!-- Processing Successfully. -->
		TDailyWK.setDailyWKId(null);
		TDailyWK.setWip(null);
		TDailyWK.setShift(null);
		TDailyWK.setReportDate(null);
		TDailyWK.setReportType(null);
		TDailyWK.setDailyWKList(null);
		TDailyWK.getInfos().add(new Message("inf.cmm.003", new String[] {}));
	}

	@Override
	public TDailyWK edit(TDailyWK TDailyWK) {
		return dailyWKDao.query(TDailyWK);
	}

	@Override
	public void save(TDailyWK TDailyWK) {
		// <!-- Check: if 'dailyWKId' is existing. -->
		if (TDailyWK.getDailyWKId() == null)
			dailyWKDao.insert(TDailyWK);
		else {
			dailyWKDao.update(TDailyWK);
		}

		TDailyWK.getInfos().add(new Message("inf.cmm.002", new String[] {}));
	}

	@Override
	public List<MWorkOrder> getWorkOrderNoList(MWorkOrder MWorkOrder) {
		return dailyWKDao.queryWorkOrderNoList(MWorkOrder);
	}

	@Override
	public List<MPart> getPartNo(Integer customerId, String wip) {
		// <!-- Validating Parameter. -->
		if (customerId == null)
			return new LinkedList<MPart>();

		MPart MPart = new MPart();
		MPart.setCustomerId(customerId);
		MPart.setWip(wip);

		return isBlank(MPart.getWip()) ? partDao.getPartEnableList(MPart)
				: partDao.getPartInWipEnable(MPart);
	}
}