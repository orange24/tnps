package th.co.nttdata.tki.blogic.mst;

import java.util.List;
import java.util.Map;

import th.co.nttdata.tki.bean.MMaterial;
import th.co.nttdata.tki.bean.MPart;
import th.co.nttdata.tki.bean.MPartFilter;
/**
 * @author NDTH
 * @since July 9, 2013
 */
public interface PRT_S01Logic{
	
	List<MPart> load();
	
	List<MPart> getExportData(MPartFilter mPart) throws Exception;
	
	void save(List<Map<String, Object>>  param) throws Exception;

	List<MMaterial> getMaterial();
	
}
