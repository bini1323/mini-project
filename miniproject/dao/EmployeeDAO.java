package main.dao;

import java.util.ArrayList;

import main.vo.EmployeeVO;
import main.vo.StockVO;

public interface EmployeeDAO {
	
	public int insert(EmployeeVO evo);
	public ArrayList<EmployeeVO> loginCheck(EmployeeVO evo);
	public EmployeeVO selectOne(long employeeId);
	public EmployeeVO selectLast();
	public boolean isExistsByEmail(String email);
}
