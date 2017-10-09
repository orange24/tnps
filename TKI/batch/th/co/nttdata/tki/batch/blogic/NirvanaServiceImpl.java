package th.co.nttdata.tki.batch.blogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service
public class NirvanaServiceImpl implements NirvanaService {
	@Autowired
	@Qualifier("sqlsvrMapClient")
	private SqlMapClient sqlMap;

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @Async - for not use transaction with java this case control transaction by store procedure.
	 */
	@Override
	@Async
	public void mergeMasterData() throws Exception {
		sqlMap.update("m_nirvana_sync_master_batch.spNirMergeData");
	}

}
