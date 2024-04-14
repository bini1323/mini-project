package vo;

public class StockVO {

	private String pid;
	private String pname;
	private int stockNum;
	private String sdate;
	private String place;
	private String odate;
	private String deleteyn;


	public StockVO() {

	}

	public StockVO(String pid, String pname, int stockNum, String sdate,
				   String place, String odate, String deleteyn) {

		this.pid = pid;
		this.pname = pname;
		this.stockNum = stockNum;
		this.sdate = sdate;
		this.place = place;
		this.odate = odate;
		this.deleteyn = deleteyn;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getOdate() {
		return odate;
	}

	public void setOdate(String odate) {
		this.odate = odate;
	}

	public String getDeleteyn() {
		return deleteyn;
	}

	public void setDeleteyn(String deleteyn) {
		this.deleteyn = deleteyn;
	}

	public int getStockNum() {
		return stockNum;
	}

	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}
}
