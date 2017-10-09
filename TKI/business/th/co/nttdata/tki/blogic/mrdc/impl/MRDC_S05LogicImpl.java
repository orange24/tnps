package th.co.nttdata.tki.blogic.mrdc.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.co.nttdata.tki.bean.VProductProcesMaster;
import th.co.nttdata.tki.blogic.mrdc.MRDC_S05Logic;
import th.co.nttdata.tki.dao.VProductProcesMasterDao;

@Service
public class MRDC_S05LogicImpl implements MRDC_S05Logic{

	@Autowired
	protected Properties settings;
	
	@Autowired
	public VProductProcesMasterDao vProDao;
	
	@Override
	public VProductProcesMaster exportMRDC_R05(VProductProcesMaster vPro) {
		Integer maxRecord = Integer.parseInt(settings.getProperty("CMM.MaxRecord","40000"));
		vPro.setMaxRecord(maxRecord);
		vPro = vProDao.selectMRDC_S05(vPro);
		if(vPro.getvList().size() > 0){
			for(VProductProcesMaster part : vPro.getvList()){
				part.setMoldList(vProDao.selectMoldByPart(part.getnPartId()));				
			}
		}
		return vPro;
	}
	
	@Override
	public Integer countMRDC_S05() {
		
		return vProDao.countMRDC_S05();
	}

}
