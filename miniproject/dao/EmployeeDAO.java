package dao;

import vo.EmployeeVO;

public interface EmployeeDAO {
	
	public int insert(EmployeeVO evo);
	public EmployeeVO loginCheck(EmployeeVO evo);
	public EmployeeVO selectById(long employeeId);
	public EmployeeVO selectLast();
	public boolean isExistsByEmail(String email);
}
