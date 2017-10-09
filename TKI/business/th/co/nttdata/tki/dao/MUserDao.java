package th.co.nttdata.tki.dao;

import th.co.nttdata.tki.bean.MUser;

public interface MUserDao {

	public void delete(MUser user);

	public void insert(MUser user);

	public MUser update(MUser user);

	public int updatePassword(MUser user);

	public MUser getUser();

	public MUser getUser(MUser user);

	public MUser queryList(MUser user);
}