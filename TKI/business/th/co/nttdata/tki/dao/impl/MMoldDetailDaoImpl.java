package th.co.nttdata.tki.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import th.co.nttdata.tki.bean.MMoldDetail;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.dao.AbstractBaseDao;
import th.co.nttdata.tki.dao.MMoldDetailDao;

@Repository
@SuppressWarnings("unchecked")
public class MMoldDetailDaoImpl extends AbstractBaseDao implements MMoldDetailDao {

	@Override
	public MMoldDetail search(MMoldDetail mDetail) {
		mDetail.setmDetailList( (List<MMoldDetail>) queryForList("m_mold_detail.querySearch",mDetail, getSkipResult(mDetail), mDetail.getPageCount()) );
		calPageTotal("m_mold_detail.count", mDetail);
		return mDetail;
	}

	@Override
	public List<MMoldDetail> getMoldName() {
		return (List<MMoldDetail>) queryForList("m_mold_detail.queryMoldName");
	}
	
	@Override
	public List<MMoldDetail> getMoldName(MMoldDetail MMoldDetail) {
		List<MMoldDetail> moldList = new ArrayList<MMoldDetail>();
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		if(MMoldDetail.getStartDate() != null){
			Calendar reportDate = Calendar.getInstance();
			reportDate.setTime(MMoldDetail.getStartDate());
			if((yesterday.get(Calendar.YEAR)<reportDate.get(Calendar.YEAR))||(yesterday.get(Calendar.YEAR)==reportDate.get(Calendar.YEAR) && yesterday.get(Calendar.DAY_OF_YEAR)<=reportDate.get(Calendar.DAY_OF_YEAR))){
				moldList = (List<MMoldDetail>) queryForList("m_mold_detail.queryMoldName",MMoldDetail);
			}else{
				moldList = (List<MMoldDetail>) queryForList("m_mold_detail.queryMoldNameActive",MMoldDetail);
			}
		}else{
			moldList = (List<MMoldDetail>) queryForList("m_mold_detail.queryMoldNameActive",MMoldDetail);
		}
		return moldList;
	}

	@Override
	public List<MMoldDetail> getMoldNo(MMoldDetail mDetail) {
		List<MMoldDetail> moldList = new ArrayList<MMoldDetail>();
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		if(mDetail.getStartDate() != null){
			Calendar reportDate = Calendar.getInstance();
			reportDate.setTime(mDetail.getStartDate());
			if((yesterday.get(Calendar.YEAR)<reportDate.get(Calendar.YEAR))||(yesterday.get(Calendar.YEAR)==reportDate.get(Calendar.YEAR) && yesterday.get(Calendar.DAY_OF_YEAR)<=reportDate.get(Calendar.DAY_OF_YEAR))){
				moldList = (List<MMoldDetail>) queryForList("m_mold_detail.queryMoldNo",mDetail);
			}else{
				moldList = (List<MMoldDetail>) queryForList("m_mold_detail.queryMoldNoActive",mDetail);
			}
		}else{
			moldList = (List<MMoldDetail>) queryForList("m_mold_detail.queryMoldNoActive",mDetail);
		}
		return moldList;
	}

	@Override
	public MMoldDetail getMoldDetail(MMoldDetail mDetail) {
		return (MMoldDetail) queryForObject("m_mold_detail.queryMoldDetail",mDetail);
	}

	@Override
	public void add(MMoldDetail mDetail) {
		insert("m_mold_detail.add",mDetail);
		
		//<!-- add mold part -->
		if (mDetail.getmPartList() != null && mDetail.getmPartList().size() > 0) {
			delete("m_mold_part.deletMoldPart",mDetail);
			insert("m_mold_part.addMoldPart",mDetail);
		}
	}

	@Override
	public List<MMoldDetail> getMoldDetailList(MMoldDetail mDetail) {
		return (List<MMoldDetail>) queryForList("m_mold_detail.queryMoldDetailList",mDetail);
	}

	@Override
	public void updateEndDate(MMoldDetail mDetail) {
		update("m_mold_detail.updateEndDate",mDetail);
	}

	@Override
	public int checkDupMoldNo(MMoldDetail mDetail) {
		return (Integer) queryForObject("m_mold_detail.queryDupMoldNo",mDetail);
	}

	@Override
	public void edit(MMoldDetail mDetail) {
		update("m_mold_detail.updateMoldDetail",mDetail);
		
		//<!-- add mold part -->
		if (mDetail.getmPartList() != null && mDetail.getmPartList().size() > 0) {
			delete("m_mold_part.deletMoldPart",mDetail);
			insert("m_mold_part.addMoldPart",mDetail);
		}
	}
	
	@Override
	public List<MPart> searchPart(MMoldDetail mMoldDetail){
		return (List<MPart>) queryForList("m_mold_detail.queryPart",mMoldDetail);
	}

	@Override
	public void delete(MMoldDetail mdDetail) {
		delete("m_mold_detail.delete",mdDetail);
	}
	
	@Override
	public MMoldDetail selectMLD_R01(MMoldDetail mDetail) {
		mDetail.setmDetailList( (List<MMoldDetail>) queryForList("m_mold_detail.selectMLD_R01",mDetail));
		return mDetail;
	}

	@Override
	public int checkRelateMold(MMoldDetail mdDetail) {
		return (Integer) queryForObject("m_mold_detail.countRelateMoldNo",mdDetail);
	}

	@Override
	public int checkMoldName(MMoldDetail mdDetail) {
		return (Integer) queryForObject("m_mold_detail.countMoldNo",mdDetail);
	}

	@Override
	public void deleteMoldPart(MMoldDetail mdDetail) {
		delete("m_mold_part.delete",mdDetail);
	}

	@Override
	public int checkDupMoldNoEdit(MMoldDetail mDetail) {
		return (Integer) queryForObject("m_mold_detail.queryDupMoldNoEdit",mDetail);
	}

	@Override
	public MMoldDetail exportMRDC_R21(MMoldDetail mdDetail) {
		mdDetail.setmDetailList( (List<MMoldDetail>) queryForList("m_mold_detail.queryForMRDC_R21",mdDetail));
		return mdDetail;
	}
	
	@Override
	public Integer countMRDC_R21() {
		
		return (Integer) queryForObject("m_mold_detail.countMRDC_R21");
	}

	@Override
	public List<MMoldDetail> selectMoldNo(MMoldDetail MMoldDetail) {
		List<MMoldDetail> moldList = (List<MMoldDetail>) queryForList("m_mold_detail.queryMoldNo",MMoldDetail);
		return moldList;
	}
	
	@Override
	public List<MMoldDetail> selectMoldNoDistinct(MMoldDetail MMoldDetail) {
		List<MMoldDetail> moldList = (List<MMoldDetail>) queryForList("m_mold_detail.queryMoldNoDistinct",MMoldDetail);
		return moldList;
	}
}
