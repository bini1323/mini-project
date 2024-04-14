package main;

import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import main.dao.ApplyDAO;
import main.dao.ApplyDAOImpl;
import main.dao.EmployeeDAO;
import main.dao.EmployeeDAOImpl;
import main.dao.StockDAO;
import main.dao.StockDAOImpl;
import main.ui.LoginSwing;
import main.ui.MainSwing;
import main.ui.StockSwing;
import main.util.OracleConnector;
import main.vo.ApplyVO;
import main.vo.EmployeeVO;
import main.vo.StockVO;

public class Main {
	private EmployeeDAO employeeDAO;
	private ApplyDAO applyDAO;
	private StockDAO stockDAO;
	
	private Main() {
		employeeDAO = new EmployeeDAOImpl();
		applyDAO = new ApplyDAOImpl();
		stockDAO = new StockDAOImpl();
	}
	
	// APPLY
	private int insertApply() {
		ApplyVO applyVO = new ApplyVO(1, 10, 1, 1);
		int count = applyDAO.insert(applyVO);
		return count;
	}
	
	private ArrayList<ApplyVO> selectApply() {
		return applyDAO.select();
	}
	
	private int acceptApply(ApplyVO applyVO) { // ���� ���ο� ���� ����, �� ����, �׸��� ��� ���� ���� ������Ʈ
		EmployeeVO employeeVO = employeeDAO.selectOne(applyVO.getEmployeeId());
		StockVO stockVO = stockDAO.selectOne(applyVO.getStockId());
		if (verifyJob(employeeVO.getJob())) {
			return -1;
		}
		int count = applyDAO.acceptApply(applyVO, employeeVO);
		count += stockDAO.updateStockNum(stockVO, applyVO);
		return count;
	}
	
	private int refuseApply(ApplyVO applyVO) {
		EmployeeVO employeeVO = employeeDAO.selectOne(applyVO.getEmployeeId());
		if (verifyJob(employeeVO.getJob())) {
			return -1;
		}
		int count = applyDAO.refuseApply(applyVO, employeeVO);
		return count;
	}
	
	private boolean verifyJob(String job) {
		if (job.equals("�븮")) {
			return false;
		}
		return true;
	}
	
	// STOCK
	private int insertStock() {
		// VO ���� �ϰ� VO ������ �Է� 
		StockVO lastData = stockDAO.selectLast();
		long pid = Long.parseLong(lastData.getPid()) + 1;
    	StockVO newProduct = new StockVO(String.valueOf(pid), "��ǰ1", 10, "2024-04-01", "USA", "2024-03-25", null);
    	// DAO ȣ���ؼ� 
        // VO�� ������ addProduct �Լ��� �μ�Ʈ �� ���� �Ѵ�.
        int count = stockDAO.insertStock(newProduct);
		return count;
	}
	
	private ArrayList<StockVO> selectStock() {
		return stockDAO.getAllProducts();
	}
	
	//EMPLOYEE
	private int registerEmployee() {
		if (employeeDAO.isExistsByEmail("a@s.d")) {
			return -1;
		}
		EmployeeVO lastData = employeeDAO.selectLast();
		long employeeId = Long.parseLong(lastData.getEmployeeId()) + 1;
		EmployeeVO evo = new EmployeeVO(String.valueOf(employeeId), "������", "����", null, "2002-06-25", 
				"��õ ����", "a@s.d", "asd", "01012345678", null, null);
		int nCnt = employeeDAO.insert(evo);
		return nCnt;
	}

	private String loginCheck() {
		EmployeeVO evo = new EmployeeVO("qwer@gmail.com", "qwre1234");
		ArrayList<EmployeeVO> aList = employeeDAO.loginCheck(evo);
		String loginMsg = "";
	
		if (aList !=null && aList.size() == 1) {
			
			loginMsg = "SUCCESS";						
	
		}else if(aList.size() > 1){
		
			loginMsg = "FAIL_1";
		}else {
		
			loginMsg = "FAIL_2";
		}
		return loginMsg;
	}	
	

	public static void main(String[] args) {
		Main main = new Main();
		// APPLY
		//System.out.println(main.insertApply());
		//main.selectApply();
		//System.out.println(main.acceptApply(new ApplyVO(1, 10, 1, 1)));
		//System.out.println(main.refuseApply(new ApplyVO(1, 10, 1, 1)));
	
		// STOCK
        //System.out.println(main.insertStock());
        //main.selectStock();
        
        // EMPLOYEE
        //System.out.println(main.registerEmployee());
		//System.out.println(main.loginCheck());
		
		// SWING
		new LoginSwing();
		
	}

}
