package th.co.nttdata.tki.blogic.mst;
import java.util.List;
import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MFgPart;


public interface PRT_S02Logic {
	public  MCustomer getAllCustomer();
	public  MFgPart  searchFgPartMappingByCustomerId(MFgPart  mFgPart);
	public  void   saveList(List<MFgPart> mFgpart);
	public  List<MFgPart> getFgNoNameListByCustomerId(MFgPart mFgPart);
	public  List<MFgPart>   selectPartListByFgId(MFgPart  mFgPart);

}
