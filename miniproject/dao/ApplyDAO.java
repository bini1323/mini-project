package dao;

import vo.ApplyVO;
import vo.EmployeeVO;

import java.util.ArrayList;


public interface ApplyDAO {

	public int insert(ApplyVO applyVO);

	public ArrayList<ApplyVO> select();

	public int acceptApply(ApplyVO applyVO);

	public int refuseApply(ApplyVO applyVO);

	ApplyVO selectById(long applyId);

    public ApplyVO selectLast();

	ArrayList<ApplyVO> findByApplyConditionOrderByApplyDateASC(String applyCondition);
}
