package th.co.nttdata.tki.blogic.mst;

import java.util.List;

import th.co.nttdata.tki.bean.MMachine;

public interface MCH_S02Logic {
	public MMachine getMachine(MMachine MMachine);
	public List<MMachine> getMachineList(MMachine MMachine);
	public MMachine save(MMachine MMachine);
	public void delete(MMachine MMachine);
}
