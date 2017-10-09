package th.co.nttdata.tki.dao;

import java.util.List;
import java.util.Map;

import th.co.nttdata.tki.bean.MWip;
import th.co.nttdata.tki.bean.filter.MWipFilter;

public interface MWipDao {

	public List<MWip> getWip();

	public List<MWip> getWipMachine();

	public List<MWip> getWipWorker();

	public List<MWip> getWipMachineWorker();

	public List<MWip> getWip(MWip MWip);

	public List<MWip> getWipSearchList(MWip mWip);

	public MWip getWipList(MWip mWip);

	public void delete(MWip mWip);

	public void save(MWip mWip);

	public abstract MWip mergeWip(MWip wip);

	public void insert(MWip wip);

	public void insertMWip(MWip wip);

	public void updateMWip(MWip wip);

	public void deleteMWip(MWip wip);

	public MWip selectWipListByWip(MWip mWip);

	public MWip getWipTpicList(MWip wip);

	public boolean checkReason(MWip mWip);

	public boolean checkPart(MWip mWip);

	public boolean checkMachine(MWip mWip);

	public List<MWip> getWipFlFn();

	public List<MWip> getWipTypeName();

	public List<MWip> getWipByWipType();

	Map<String, String> getAllWipMap();

	MWip selectWipListByWipFilter(MWipFilter mWip);

}