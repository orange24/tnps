package th.co.nttdata.tki.blogic.mst;

import java.util.List;

import th.co.nttdata.tki.bean.MMaterial;

public interface MAT_S01Logic {
	public List<MMaterial> search(MMaterial MMaterial);
	public void save(MMaterial MMaterial);
}
