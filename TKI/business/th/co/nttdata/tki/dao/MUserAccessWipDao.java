package th.co.nttdata.tki.dao;

import java.util.List;

import th.co.nttdata.tki.bean.MUser;
import th.co.nttdata.tki.bean.MWip;

public interface MUserAccessWipDao {

	public List<MWip> getWip(MUser MUser);

	public List<MWip> query(MUser MUser);

	public void insert(MUser MUser);

	public void delete(MUser MUser);
}