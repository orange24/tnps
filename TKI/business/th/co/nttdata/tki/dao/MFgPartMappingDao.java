package th.co.nttdata.tki.dao;
import java.util.List;
import th.co.nttdata.tki.bean.MFgPart;


public interface MFgPartMappingDao {
	
	public  MFgPart  searchFgPartMappingByCustomerId(MFgPart  mFgPart);
	public void insertMFgpart(MFgPart mfg);
	public void updateMFgpart(MFgPart mfg);
	public List<MFgPart> getFgNoNameListByCustomerId(MFgPart mFgPart);
	public  List<MFgPart>   selectPartListByFgId(MFgPart  mFgPart);
}
