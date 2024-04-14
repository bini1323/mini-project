package vo;

public class EmployeeVO {


	private					String employeeId;
	private					String name;
	private					String dep;
	private					String job;
	private					String birthday;
	private					String address;
	private					String email;
	private					String password;
	private					String tel;
	private					String deleteYN;
	private					String createdDate;

	public EmployeeVO() {

	}

	public EmployeeVO(String email, String password) {
		this(null, null, null, null, null, null,
				email, password, null, null, null);
	}

	public EmployeeVO(String employeeId, String name, String dep, String job, String birthday, String address,
					  String email, String password, String tel, String deleteYN, String createdDate) {
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.dep = dep;
		this.job = job;
		this.birthday = birthday;
		this.address = address;
		this.email = email;
		this.password = password;
		this.tel = tel;
		this.deleteYN = deleteYN;
		this.createdDate = createdDate;
	}



	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDeleteYN() {
		return deleteYN;
	}

	public void setDeleteYN(String deleteYN) {
		this.deleteYN = deleteYN;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}





}

