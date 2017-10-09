package th.co.nttdata.tki.batch.blogic;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import th.co.nttdata.batch.BatchLogic;
import th.co.nttdata.batch.BatchRunner;

@ContextConfiguration(locations={"classpath:/config-batch.xml"})
@TransactionConfiguration(transactionManager="sqlsvrTxManager")
@RunWith(SpringJUnit4ClassRunner.class)
public class TPC_B05LogicImplTest {
	
	@Resource(name="TPC_B05")
	BatchLogic TPC_B05;
	
	@Test
	public void testTask(){
		try{
			BatchRunner.execute(TPC_B05);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
