package th.co.nttdata.tki.blogic.fng.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.nttdata.tki.bean.MWorkOrder;
import th.co.nttdata.tki.bean.Message;
import th.co.nttdata.tki.bean.TFG;
import th.co.nttdata.tki.bean.TFGDetail;
import th.co.nttdata.tki.bean.TLotSequence;
import th.co.nttdata.tki.blogic.fng.FNG_S01Logic;
import th.co.nttdata.tki.dao.MWorkOrderDao;
import th.co.nttdata.tki.dao.TFGDetailDao;
import th.co.nttdata.tki.dao.TLotSequenceDao;

@Service
public class FNG_S01LogicImpl implements FNG_S01Logic {
	@Autowired
	private TFGDetailDao tfgDetailDao;
	@Autowired
	private MWorkOrderDao mWorkOrderDao;
	@Autowired
	private TLotSequenceDao tLotSequenceDao;

	@Override
	public Map<String, Object> findLotQty(String lotNo) {
		return mWorkOrderDao.getLotQty(lotNo);
	}

	@Override
	@Transactional
	public void save(TFG tfg) {
		/*
		 * Comment By Toi+ : [Bug #2068]Not check data match because on screen
		 * can't change customer and FG.
		 */
		/*
		 * int lineNo = 1; String lotNo = ""; String workOrderNo = ""; for
		 * (TFGDetail tfgDetail : tfg.getDetails()) { MWorkOrder workOrder = new
		 * MWorkOrder(); workOrder.setLotSeqNo(tfgDetail.getLotSeqNo());
		 * workOrder.setFgId(tfgDetail.getFgId());
		 * workOrder.setCustomerId(tfgDetail.getCustomerId()); lotNo =
		 * tfgDetail.getLotNo(); workOrder.setLotNo(lotNo); if (10 <
		 * lotNo.length()) { workOrderNo = lotNo.substring(0, 11); }
		 * workOrder.setWorkOrderNo(workOrderNo); if
		 * (!mWorkOrderDao.existsLotNo(workOrder)) { tfg.getErrors().add(new
		 * Message("err.fng.003", new String[] { Integer.toString(lineNo) })); }
		 * lineNo++; } if (tfg.hasErrors()) { return; } else {
		 */

		if (tfg.getDetails() != null && tfg.getDetails().size() > 0) {
			//Check err Out not In 
			if("fgout".equalsIgnoreCase(tfg.getDetails().get(0).getFgType())){
				boolean isErr = false;
				for(TFGDetail tmpDetail : tfg.getDetails()){
					if(tfgDetailDao.checkOutNotIn(tmpDetail)){
						isErr = true;
						tfg.getErrors().add(new Message("err.fng.012", new String[]{tmpDetail.getLotNo()+"-"+tmpDetail.getLotSeqNo()}));
					}
				}
				if(isErr){
					return;
				}
			}
			
			tfgDetailDao.insertFgDetail(tfg);
			tfg.getInfos().add(new Message("inf.cmm.002", null));

			// successful msg
			Map<String, String> maps = new TreeMap<String, String>();
			if ("fgIn".equalsIgnoreCase(tfg.getDetails().get(0).getFgType())) {
				// checking FG type
				for (TFGDetail tfgDetail : tfg.getDetails()) {
					String custCodeAndFgNo = tfgDetail.getCustomerCode()
							+ " : " + tfgDetail.getFgName() + " Type : "
							+ tfgDetail.getReportTypeName() + ", ";
					if (maps.get(custCodeAndFgNo) != null) {
						maps.put(custCodeAndFgNo, String.valueOf(Integer
								.parseInt(maps.get(custCodeAndFgNo))
								+ tfgDetail.getFgIn()));
					} else {
						maps.put(custCodeAndFgNo,
								String.valueOf(tfgDetail.getFgIn()));
					}
				}
				for (Map.Entry<String, String> e : maps.entrySet()) {
					// display list of lotNo
					tfg.getInfos().add(
							new Message("inf.fng.007", new String[] {
									e.getKey(), "in", e.getValue() + "" }));
				}
			} else {
				for (TFGDetail tfgDetail : tfg.getDetails()) {
					String custCodeAndFgNo = tfgDetail.getCustomerCode()
							+ " : " + tfgDetail.getFgName() + " Type : "
							+ tfgDetail.getReportTypeName() + ", ";
					if (maps.get(custCodeAndFgNo) != null) {
						maps.put(custCodeAndFgNo, String.valueOf(Integer
								.parseInt(maps.get(custCodeAndFgNo))
								+ tfgDetail.getFgOut()));
					} else {
						maps.put(custCodeAndFgNo,
								String.valueOf(tfgDetail.getFgOut()));
					}
				}
				for (Map.Entry<String, String> e : maps.entrySet()) {
					// display list of lotNo
					tfg.getInfos().add(
							new Message("inf.fng.007", new String[] {
									e.getKey(), "out", e.getValue() + "" }));
				}
			}
		} else {
			tfg.getErrors().add(new Message("err.fng.010", null));
		}

	}

	@Override
	public MWorkOrder getWorkOrderDetail(MWorkOrder workOrder) {
		return tLotSequenceDao.getWorkOrderDetail(workOrder);
	}

	@Override
	public List<TLotSequence> getOnlyLotSeqNoAuto(String lotSeqNo) {
		return tLotSequenceDao.getOnlyLotSeqNoAuto(lotSeqNo);
	}

	@Override
	public Boolean checkLotNo(TFGDetail workOrder) {
		return tfgDetailDao.checkLotNo(workOrder);
	}

	@Override
	public TFG clearDuplicateLotSeqNo(TFG tfg) {
		List<TFGDetail> list = new ArrayList<TFGDetail>();
		Map<String, String> maps = new LinkedHashMap<String, String>();
		int detailSize = tfg.getDetails().size();
		for (int i = 0; i < detailSize; i++) {
			TFGDetail detail = tfg.getDetails().get(i);
			if (maps.get(detail.getLotNo() + detail.getLotSeqNo()) == null) {
				maps.put(detail.getLotNo() + detail.getLotSeqNo(), "ok");
				list.add(detail);
			} else {
				if (StringUtils.isNotEmpty(detail.getLotNo())) {
					tfg.getErrors().add(
							new Message("err.fng.005", new String[] {
									String.valueOf(i + 1),
									detail.getLotNo() + "-"
											+ detail.getLotSeqNo() }));
				}
			}
		}
		tfg.getDetails().clear();
		tfg.setDetails(list);
		return tfg;
	}

	@Override
	public TFG skipBlankRow(TFG tfg) {
		List<TFGDetail> list = new ArrayList<TFGDetail>();
		for (TFGDetail tmp : tfg.getDetails()) {
			if (StringUtils.isNotEmpty(tmp.getLotNo())) {
				list.add(tmp);
			}
		}
		tfg.getDetails().clear();
		tfg.setDetails(list);
		return tfg;
	}

	@Override
	public TFG checkNotExist(TFG tfg) {
		List<TFGDetail> list = new ArrayList<TFGDetail>();
		Boolean isNotExist = false;
		int detailSize = tfg.getDetails().size();
		for (int i = 0; i < detailSize; i++) {
			TFGDetail t = tfg.getDetails().get(i);
			isNotExist = tfgDetailDao.checkLotNotExist(t);
			if (isNotExist) {
				// if (StringUtils.isEmpty(t.getLotSeqNo())) {
				// tfg.getErrors().add(
				// new Message("err.fng.011", new String[] { t
				// .getLotNo() }));
				// } else {
				// tfg.getErrors().add(
				// new Message("err.fng.011", new String[] { t
				// .getLotNo() + "-" + t.getLotSeqNo() }));
				// }
				tfg.getErrors().add(
						new Message("err.fng.011", new String[] { t
								.getFullLotSeqNo() }));
			} else {
				list.add(t);
			}
		}
		tfg.getDetails().clear();
		tfg.setDetails(list);
		return tfg;
	}
}
