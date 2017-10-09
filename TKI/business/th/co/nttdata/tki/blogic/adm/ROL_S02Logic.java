package th.co.nttdata.tki.blogic.adm;

import java.util.List;

import th.co.nttdata.tki.bean.MRole;
import th.co.nttdata.tki.bean.MultiChoice;

public interface ROL_S02Logic {
	public List<MultiChoice> getRolMenu( MRole MRole );
	public List<MultiChoice> getRolCommand( MRole MRole );
	public boolean existRoleName(MRole MRole);
	public String save(MRole MRole);
	public MRole search(MRole MRole);
}
