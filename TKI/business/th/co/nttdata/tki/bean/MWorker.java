package th.co.nttdata.tki.bean;

public class MWorker extends AbstractBaseBean {
			
	private Integer workerId;
	private Double workerUnitCost;
	
	public Integer getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	public Double getWorkerUnitCost() {
		return workerUnitCost;
	}
	public void setWorkerUnitCost(Double workerUnitCost) {
		this.workerUnitCost = workerUnitCost;
	}
	
}
