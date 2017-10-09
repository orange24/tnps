package th.co.nttdata.tki.blogic.adm;

import java.util.List;

import th.co.nttdata.tki.bean.MRole;
import th.co.nttdata.tki.bean.MUser;

public interface USR_S01Logic {
	public MUser search(MUser MUser);
	public List<MRole> getComboBox();
	public void delete(MUser MUser);
	public MUser getCurrentUser();
}
