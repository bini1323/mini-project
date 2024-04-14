package main.dao;

import java.util.ArrayList;

import main.vo.ApplyVO;
import main.vo.EmployeeVO;
import main.vo.StockVO;

public interface ApplyDAO {

	public int insert(ApplyVO applyVO);

	public ArrayList<ApplyVO> select();

	public int acceptApply(ApplyVO applyVO, EmployeeVO employeeVO);

	public int refuseApply(ApplyVO applyVO, EmployeeVO employeeVO);
}
