package th.co.nttdata.tki.blogic.adm;

import java.util.List;

import th.co.nttdata.tki.bean.MCustomer;
import th.co.nttdata.tki.bean.MRole;
import th.co.nttdata.tki.bean.MUser;
import th.co.nttdata.tki.bean.MWip;

public interface USR_S02Logic {
	public String save(MUser MUser);
	public void delete(MUser MUser);
	public MUser getUser(MUser MUser);
	public List<MRole> getComboBox();
	public List<MWip> getAcessWip(MUser MUser);
	public MUser getCurrentUser();
	public List<MCustomer> getCustomer(MUser mUser);
}
