package th.co.nttdata.tki.batch.blogic;


public interface NirvanaService {
	/**
	 * Merge FG and customer master data.
	 * 
	 * @throws Exception - if have error occur
	 */
	void mergeMasterData() throws Exception;
}