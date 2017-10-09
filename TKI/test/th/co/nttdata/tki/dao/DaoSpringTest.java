package th.co.nttdata.tki.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import th.co.nttdata.tki.dao.impl.MActionDaoImpl;

public class DaoSpringTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private MActionDaoImpl dao;
	
	protected void deleteAll(String tableName) {
		dao.delete("test.delete", tableName);
	}

}