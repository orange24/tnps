package th.co.nttdata.tki.blogic.dal.impl;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TDailyMCWK;
import th.co.nttdata.tki.bean.TDailyMCWKDetail;
import th.co.nttdata.tki.blogic.AbstractBaseLogic;
import th.co.nttdata.tki.blogic.dal.DAL_S06Logic;
import th.co.nttdata.tki.dao.MPartDao;
import th.co.nttdata.tki.dao.MWorkOrderDao;
import th.co.nttdata.tki.dao.TDailyMCWKDao;

@Service
public class DAL_S06LogicImpl extends AbstractBaseLogic implements DAL_S06Logic {

	@Autowired
	private TDailyMCWKDao dailyMCWKDao;
	@Autowired
	private MWorkOrderDao workOrderDao;

	@Autowired
	private MPartDao partDao;

	@Override
	public TDailyMCWK check(TDailyMCWK TDailyMCWK) {
		// <!-- Check: if 'workOrderNo' not found. -->
		// <!-- Check: if 'lotNo' not found. -->
		for (int i = 0; i < TDailyMCWK.getDetailList().size(); i++) {
			TDailyMCWKDetail detail = TDailyMCWK.getDetailList().get(i);
			MWorkOrder workOrder = new MWorkOrder();
			workOrder.setWorkOrderNo(detail.getWorkOrderNo());
			if (!"".equals(detail.getLotNo()))
				workOrder.setLotNo(detail.getWorkOrderNo() + detail.getLotNo());
			workOrder.setCustomerId(detail.getCustomerId());
			workOrder.setPartId(detail.getPartId());
			if (!workOrderDao.existsLotNo(workOrder))
				TDailyMCWK.getErrors().add(
						new Message("err.dal.008", new String[] { String
								.valueOf(i + 1) }));
		}
		return TDailyMCWK;
	}

	@Override
	public void delete(TDailyMCWK TDailyMCWK) {
		dailyMCWKDao.delete(TDailyMCWK);

		// <!-- Processing Successfully. -->
		TDailyMCWK.setDailyMCWKId(null);
		TDailyMCWK.setWip(null);
		TDailyMCWK.setCustomerId(null);
		TDailyMCWK.setMachineId(null);
		TDailyMCWK.setShift(null);
		TDailyMCWK.setStaff(null);
		TDailyMCWK.setReportDate(null);
		TDailyMCWK.setReportType(null);
		TDailyMCWK.setDetailList(null);
		TDailyMCWK.setStopMCList(null);
		TDailyMCWK.getInfos().add(new Message("inf.cmm.003", new String[] {}));
	}

	@Override
	public TDailyMCWK edit(TDailyMCWK TDailyMCWK) {
		return dailyMCWKDao.query(TDailyMCWK);
	}

	@Override
	public void save(TDailyMCWK TDailyMCWK) {
		// <!-- Check: if 'dailyMCWKId' is existing. -->
		if (TDailyMCWK.getDailyMCWKId() == null)
			dailyMCWKDao.insert(TDailyMCWK);
		else {
			dailyMCWKDao.update(TDailyMCWK);
		}

		TDailyMCWK.getInfos().add(new Message("inf.cmm.002", new String[] {}));
	}

	@Override
	public List<MWorkOrder> getWorkOrderNoList(MWorkOrder MWorkOrder) {
		return dailyMCWKDao.queryWorkOrderNoList(MWorkOrder);
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