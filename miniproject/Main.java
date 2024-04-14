import dao.*;
import session.UserSession;
import ui.*;
import vo.ApplyVO;
import vo.EmployeeVO;
import vo.StockVO;

import java.util.ArrayList;

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
		// appyId 자동 증가
		ApplyVO lastData = applyDAO.selectLast();
		long applyId = lastData.getApplyId() + 1;
		// 부품이름에 해당하는 id 조회
		StockVO stockVO = stockDAO.selectByPartName("부품1");
		String stockId = stockVO.getPid();
		if (checkNullString(stockId)) {
			System.out.println("해당 부품이 존재하지 않습니다");
			return -1;
		}
		ApplyVO applyVO = new ApplyVO(applyId, 10, Long.parseLong(stockId), 1);
		int count = applyDAO.insert(applyVO);
		return count;
	}

	private boolean checkNullString(String s) {
		if (s != null) {
			return false;
		}
		return true;
	}

	private ArrayList<ApplyVO> selectApply() {
		return applyDAO.findByApplyConditionOrderByApplyDateASC("W");
	}

	private int acceptApply(long applyId) { // 권한 여부에 따라 실행, 안 실행, 그리고 재고 수량 갯수 업데이트
		ApplyVO applyVO = applyDAO.selectById(applyId);
		EmployeeVO employeeVO = employeeDAO.selectById(1);
		StockVO stockVO = stockDAO.selectById(applyVO.getStockId());
		if (verifyJob(employeeVO.getJob())) {
			System.out.println("대리 직급이 아닙니다");
			return -1;
		}
		if (verifyStockCount(stockVO.getStockNum(), applyVO.getCnt())) {
			System.out.println("부품 재고가 부족합니다");
			return -1;
		}
		int count = 0;
		count += applyDAO.acceptApply(applyVO);
		count += stockDAO.updateStockNum(stockVO, applyVO);
		return count;
	}

	private int refuseApply(long applyId) {
		ApplyVO applyVO = applyDAO.selectById(applyId);
		EmployeeVO employeeVO = employeeDAO.selectById(1);
		if (verifyJob(employeeVO.getJob())) {
			System.out.println("대리 직급이 아닙니다");
			return -1;
		}
		int count = applyDAO.refuseApply(applyVO);
		return count;
	}

	private boolean verifyJob(String job) {
		if (!job.equals("대리")) {
			return true;
		}
		return false;
	}

	private boolean verifyStockCount(int stockCount, int applyCount) {
		if (stockCount < applyCount) {
			return true;
		}
		return false;
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

	private void loginCheck() {
		EmployeeVO evo = new EmployeeVO("a@s.d", "asd");
		EmployeeVO result = employeeDAO.loginCheck(evo);

		if (result != null) {
			System.out.println("SUCCESS");
			UserSession.setLoginUser(result);
		} else {
			System.out.println("FAIL");
		}
	}


	public static void main(String[] args) {
		Main main = new Main();
		// APPLY
		//System.out.println(main.insertApply());
		/*for (ApplyVO vo : main.selectApply()) {
			System.out.println(vo.getApplyId() + ", " + vo.getCnt() + ", " + vo.getStockId() + ", " + vo.getEmployeeId()
					+ ", " + vo.getApplyCondition() + ", " + vo.getCreatedDate());
		}*/
		//System.out.println(main.acceptApply(1));
		//System.out.println(main.refuseApply(1));

		// STOCK
		//System.out.println(main.insertStock());
		//main.selectStock();

		// EMPLOYEE
		//System.out.println(main.registerEmployee());
		//main.loginCheck();

		// SWING
		new LoginSwing();
		//new ApplyHistorySwing();
		//new ApplySwing();
	}

}
