package dao;

import util.OracleConnector;
import vo.ApplyVO;
import vo.EmployeeVO;
import vo.StockVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ApplyDAOImpl implements ApplyDAO {
	private Connection conn;

	public ApplyDAOImpl() {
		conn = OracleConnector.getConnection();
	}

	@Override
	public int insert(ApplyVO applyVO) {
		PreparedStatement pstmt;
		int count = 0;
		try {
			String sql = "INSERT INTO APPLY(APPLY_ID, CNT, STOCK_ID, EMPLOYEE_ID, APPLY_CONDITION, APPLY_DATE) "
					+ " VALUES  (?, ?, ?, ?, 'W', SYSDATE())"; // 승인 여부 A - 승인, W - 대기, R - 거절

			// statement 를 전달하고 결과값을 받아오는 통로 만들기
			pstmt = conn.prepareStatement(sql);

			// 쿼리문에 플레이스 홀더에 입력할 데이터 바인딩 하기
			pstmt.clearParameters(); // clearParameters() 함수 항상 사용하기
			pstmt.setLong(1, applyVO.getApplyId());
			pstmt.setInt(2, applyVO.getCnt());
			pstmt.setLong(3, applyVO.getStockId());
			pstmt.setLong(4, applyVO.getEmployeeId());

			// statement 를 전달하고 결과값을 받기
			count = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("저장 실패");
			System.out.println(e);
		}

		return count;
	}

	@Override
	public ArrayList<ApplyVO> select() {
		ArrayList<ApplyVO> results = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM APPLY";

			ResultSet rsRs = stmt.executeQuery(sql);
			while (rsRs.next()) {
				ApplyVO applyVO = new ApplyVO();
				applyVO.setApplyId(rsRs.getLong(1));
				applyVO.setCnt(rsRs.getInt(2));
				applyVO.setStockId(rsRs.getLong(3));
				applyVO.setEmployeeId(rsRs.getLong(4));
				applyVO.setApplyCondition(rsRs.getString(5));
				applyVO.setCreatedDate(rsRs.getString(6));

				results.add(applyVO);
			}
		} catch (SQLException e) {
			System.out.println("조회 실패");
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public int acceptApply(ApplyVO applyVO) {
		PreparedStatement pstmt;
		int count = 0;
		try {
			String sql = "UPDATE APPLY SET APPLY_CONDITION = 'A'"
					+ "WHERE APPLY_ID = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.clearParameters();
			pstmt.setLong(1, applyVO.getApplyId());

			count = pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("업데이트 실패");
			e.getStackTrace();
		}
		return count;
	}

	@Override
	public int refuseApply(ApplyVO applyVO) {
		PreparedStatement pstmt;
		int count = 0;
		try {
			String sql = "UPDATE APPLY SET APPLY_CONDITION = 'R'"
					+ "WHERE APPLY_ID = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.clearParameters();
			pstmt.setLong(1, applyVO.getApplyId());

			count = pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("업데이트 실패");
			e.getStackTrace();
		}
		return count;
	}

	@Override
	public ApplyVO selectById(long applyId) {
		PreparedStatement pstmt;
		ApplyVO result = null;
		try {
			conn = OracleConnector.getConnection();

			String sql = ("SELECT * FROM APPLY "
					+ "WHERE APPLY_ID = ?");
			pstmt =  conn.prepareStatement(sql);

			pstmt.setLong(1, applyId);

			//ResultSet 에 파일 받아오기
			ResultSet rsRs = pstmt.executeQuery();
			if (rsRs != null) {
				while (rsRs.next()) {
					result = new ApplyVO();
					result.setApplyId(rsRs.getLong(1));
					result.setCnt(rsRs.getInt(2));
					result.setStockId(rsRs.getInt(3));
					result.setEmployeeId(rsRs.getInt(4));
					result.setApplyCondition(rsRs.getString(5));
					result.setCreatedDate(rsRs.getString(6));
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	@Override
	public ApplyVO selectLast() {
		ApplyVO result = new ApplyVO();
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT MAX(APPLY_ID) FROM APPLY";

			ResultSet rsRs = stmt.executeQuery(sql);
			if (rsRs != null) {
				while (rsRs.next()) {
					result = new ApplyVO();

					if (rsRs.getString(1) != null) {
						result.setApplyId(rsRs.getLong(1));
					} else {
						result.setApplyId(0);
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("조회 실패");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<ApplyVO> findByApplyConditionOrderByApplyDateASC(String applyCondition) {
		PreparedStatement pstmt;
		ArrayList<ApplyVO> results = new ArrayList();
		try {
			conn = OracleConnector.getConnection();

			String sql = ("SELECT * FROM APPLY "
					+ "WHERE APPLY_CONDITION = ?"
					+ "ORDER BY APPLY_DATE ASC");
			pstmt =  conn.prepareStatement(sql);

			pstmt.setString(1, applyCondition);

			//ResultSet 에 파일 받아오기
			ResultSet rsRs = pstmt.executeQuery();
			if (rsRs != null) {
				while (rsRs.next()) {
					ApplyVO applyVO = new ApplyVO();
					applyVO.setApplyId(rsRs.getLong(1));
					applyVO.setCnt(rsRs.getInt(2));
					applyVO.setStockId(rsRs.getInt(3));
					applyVO.setEmployeeId(rsRs.getInt(4));
					applyVO.setApplyCondition(rsRs.getString(5));
					applyVO.setCreatedDate(rsRs.getString(6));

					results.add(applyVO);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return results;
	}
}
