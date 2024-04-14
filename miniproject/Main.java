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
	
	private int acceptApply(ApplyVO applyVO) { // 권한 여부에 따라 실행, 안 실행, 그리고 재고 수량 갯수 업데이트
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
		if (job.equals("대리")) {
			return false;
		}
		return true;
	}
	
	// STOCK
	private int insertStock() {
		// VO 생성 하고 VO 데이터 입력 
		StockVO lastData = stockDAO.selectLast();
		long pid = Long.parseLong(lastData.getPid()) + 1;
    	StockVO newProduct = new StockVO(String.valueOf(pid), "부품1", 10, "2024-04-01", "USA", "2024-03-25", null);
    	// DAO 호출해서 
        // VO를 보내서 addProduct 함수로 인서트 할 려고 한다.
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
		EmployeeVO evo = new EmployeeVO(String.valueOf(employeeId), "장태현", "개발", null, "2002-06-25", 
				"인천 부평구", "a@s.d", "asd", "01012345678", null, null);
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
