package th.co.nttdata.tki.batch.blogic;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import th.co.nttdata.batch.BatchLogic;
import th.co.nttdata.batch.BatchRunner;

@ ContextConfiguration(locations={"classpath:/config-batch.xml"})
@ TransactionConfiguration(transactionManager="sqlsvrTxManager",defaultRollback=false)
public class MoldShotLogicImplTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Resource(name="MoldShot")
	BatchLogic MoldShot;
	
	@Test
	public void testTask(){
		try {
			BatchRunner.execute(MoldShot);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
