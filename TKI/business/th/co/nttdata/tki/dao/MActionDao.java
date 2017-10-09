package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MAction;
import th.co.nttdata.tki.bean.MRole;

public interface MActionDao {

	public List<MAction> getAction(MRole MRole);
}