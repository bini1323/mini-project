package vo;

public class ApplyVO {
	private long applyId;
	private int cnt;
	private long stockId;
	private long employeeId;
	private String applyCondition;
	private String createdDate;

	public ApplyVO() {
	}

	public ApplyVO(long applyId, int cnt, long stockId, long employeeId) {
		this.applyId = applyId;
		this.cnt = cnt;
		this.stockId = stockId;
		this.employeeId = employeeId;
	}

	public long getApplyId() {
		return applyId;
	}
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public long getStockId() {
		return stockId;
	}
	public void setStockId(long stockId) {
		this.stockId = stockId;
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getApplyCondition() {
		return applyCondition;
	}

	public void setApplyCondition(String applyCondition) {
		this.applyCondition = applyCondition;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

}
