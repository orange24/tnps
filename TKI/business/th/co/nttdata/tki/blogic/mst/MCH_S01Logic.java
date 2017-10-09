package th.co.nttdata.tki.blogic.mst;
import java.util.List;
import th.co.nttdata.tki.bean.MMachine;

public interface MCH_S01Logic {
	public MMachine getMachineList(MMachine MMchine);
	public void delete(MMachine MMchine);
	public List<MMachine> getPartMachineList(MMachine MMchine);
	public List<MMachine> getCopyPartMachineList(MMachine MMchine);
	public List<MMachine> getWipList();
    public void saveCopyPartMappingList(MMachine  MMchine);
    public List<MMachine> getCustomerList(MMachine MMchine);
    public List<MMachine> getPartNoList(MMachine MMchine);
    public void  savePartMappingList(List<MMachine> MMchine);
	
}
