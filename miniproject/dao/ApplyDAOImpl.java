package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.util.OracleConnector;
import main.vo.ApplyVO;
import main.vo.EmployeeVO;
import main.vo.StockVO;

public class ApplyDAOImpl implements ApplyDAO {
	private Connection conn;
	
	public ApplyDAOImpl() {
		conn = OracleConnector.getConnection();
	}

	@Override
	public int insert(ApplyVO applyVO) {
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			String sql = "INSERT INTO APPLY(APPLY_ID, CNT, STOCK_ID, EMPLOYEE_ID, APPLY_CONDITION, CREATED_DATE) "
					+ " VALUES  (?, ?, ?, ?, 'W', SYSDATE)"; // ���� ���� A - ����, W - ���, R - ����

			// statement �� �����ϰ� ������� �޾ƿ��� ��� ����� 
			pstmt = conn.prepareStatement(sql);

			// �������� �÷��̽� Ȧ���� �Է��� ������ ���ε� �ϱ� 
			pstmt.clearParameters(); // clearParameters() �Լ� �׻� ����ϱ� 
			pstmt.setLong(1, applyVO.getApplyId());
			pstmt.setInt(2, applyVO.getCnt());
			pstmt.setLong(3, applyVO.getStockId());
			pstmt.setLong(4, applyVO.getEmployeeId());

			// statement �� �����ϰ� ������� �ޱ� 
			count = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("���� ����");
			System.out.println(e);
		}
		
		return count;
	}

	@Override
	public ArrayList<ApplyVO> select() {
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM APPLY";
			
			ResultSet rsRs = stmt.executeQuery(sql);
			while (rsRs.next()) {
				System.out.println("APPLY_ID : " + rsRs.getString(1));
				System.out.println("��û���� : " + rsRs.getString(2));
				System.out.println("STOCK_ID : " + rsRs.getString(3));
				System.out.println("EMPLOYEE_ID : " + rsRs.getString(4));
				System.out.println("���� ���� : " + rsRs.getString(5));
				System.out.println("��û��¥ : " + rsRs.getString(6));
			}
		} catch (SQLException e) {
			System.out.println("��ȸ ����");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int acceptApply(ApplyVO applyVO, EmployeeVO employeeVO) {
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			String sql = "UPDATE APPLY SET APPLY_CONDITION = 'A'"
					+ "WHERE APPLY_ID = ?";
 
			pstmt = conn.prepareStatement(sql);
			
			pstmt.clearParameters();
			pstmt.setLong(1, applyVO.getApplyId());
			
			count = pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("������Ʈ ����");
			e.getStackTrace();
		}
		return count;
	}

	@Override
	public int refuseApply(ApplyVO applyVO, EmployeeVO employeeVO) {
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			String sql = "UPDATE APPLY SET APPLY_CONDITION = 'R'"
					+ "WHERE APPLY_ID = ?";
 
			pstmt = conn.prepareStatement(sql);
			
			pstmt.clearParameters();
			pstmt.setLong(1, applyVO.getApplyId());
			
			count = pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("������Ʈ ����");
			e.getStackTrace();
		}
		return count;
	}
}
