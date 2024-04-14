package main.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import main.util.OracleConnector;
import main.vo.ApplyVO;
import main.vo.EmployeeVO;
import main.vo.StockVO;


public class StockDAOImpl implements StockDAO {

	
	@Override
	public int insertStock(StockVO stockVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int nCnt = 0;
		
		try {
			
			conn = OracleConnector.getConnection();
			String sql = "INSERT INTO STOCK (STOCK_ID, PART_NAME, STOCK_NUM, PLACE, DELETE_YN, STOCK_DATE, OVERHAUL_DATE)" + 
					     " VALUES (?, ?, ?, ?, 'N', TO_DATE(?), ADD_MONTHS(?, 6))"; // Y - 삭제, N - 안 삭제
			pstmt = conn.prepareStatement(sql);
			
			System.out.println("입력 쿼리 >>> : \n" + sql);
			System.out.println(stockVO.getSdate() + "<- sdate,odate ->" +  stockVO.getOdate());
			pstmt.clearParameters();
			pstmt.setString(1, stockVO.getPid());
			pstmt.setString(2, stockVO.getPname());
			pstmt.setInt(3, stockVO.getStockNum());
			pstmt.setString(4, stockVO.getPlace());
			pstmt.setString(5, stockVO.getSdate());
			pstmt.setString(6, stockVO.getOdate());

			
			nCnt = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			System.out.println("에러가 >>> : " + e.getMessage());
		}
		
		return nCnt;		
	}
		

	
	@Override
	public ArrayList<StockVO> getAllProducts() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rsRs = null;
		ArrayList<StockVO> products = null;
		
		try {
			conn = OracleConnector.getConnection(); 
			
			String sql = "SELECT * FROM STOCK";
			pstmt = conn.prepareStatement(sql);
			System.out.println("조회 쿼리 >>> : \n" + sql);
			
			rsRs= pstmt.executeQuery();
			
			if (rsRs !=null) {
				products = new ArrayList<StockVO>();
				
				while (rsRs.next()) {
					StockVO svo = new StockVO();
					svo.setPid(rsRs.getString(1));
					svo.setPname(rsRs.getString(2));
					svo.setStockNum(rsRs.getInt(3));
					svo.setPlace(rsRs.getString(4));
					svo.setDeleteyn(rsRs.getString(5));
					svo.setSdate(rsRs.getString(6));
					svo.setOdate(rsRs.getString(7));
					
					
					products.add(svo);
				}

			} 
		}catch (SQLException e) {
			System.out.println("에러가 >>> : "+ e.getMessage());
			
		}
		
		for (StockVO vo : products) {
			System.out.println("pid : " + vo.getPid());
			System.out.println("pname : " + vo.getPname());
			System.out.println("stockNum : " + vo.getStockNum());
			System.out.println("place : " + vo.getPlace());
			System.out.println("deleteyn : " + vo.getDeleteyn());
			System.out.println("sdate : " + vo.getSdate());
			System.out.println("odate : " + vo.getOdate());
		}
		
		return products;		
	}

	@Override
	public StockVO selectOne(long stockId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rsRs = null;
		StockVO result = null;
		
		try {
			conn = OracleConnector.getConnection();
			
			String sql = ("SELECT * FROM STOCK "
					+ "WHERE STOCK_ID = ?");
			pstmt =  conn.prepareStatement(sql);
			
			pstmt.setLong(1, stockId);
			
			//ResultSet 에 파일 받아오기
			rsRs = pstmt.executeQuery();
			
			if (rsRs != null) {
				
				while (rsRs.next()) {
					result = new StockVO();

					result.setPid(rsRs.getString(1));
					result.setPname(rsRs.getString(2));
					result.setStockNum(rsRs.getInt(3));
					result.setSdate(rsRs.getString(4));
					result.setPlace(rsRs.getString(5));
					result.setOdate(rsRs.getString(6));
					result.setDeleteyn(rsRs.getString(7));														
				}					
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}



	@Override
	public int updateStockNum(StockVO stockVO, ApplyVO applyVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int nCnt = 0;
		try {
			conn = OracleConnector.getConnection();
			String sql = "UPDATE STOCK SET STOCK_NUM = ?"
					+ "WHERE STOCK_ID = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.clearParameters();
			pstmt.setInt(1, stockVO.getStockNum() - applyVO.getCnt());
			pstmt.setString(2, stockVO.getPid());
			
			nCnt = pstmt.executeUpdate();
		}catch(SQLException e) {
			System.out.println("에러가 >>> : " + e.getMessage());
		}
		return nCnt;
	}



	@Override
	public StockVO selectLast() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rsRs = null;
		StockVO result = null;
		
		try {
			conn = OracleConnector.getConnection();
			
			String sql = ("SELECT MAX(STOCK_ID) FROM STOCK");
			pstmt =  conn.prepareStatement(sql);
			
			rsRs = pstmt.executeQuery();
			
			if (rsRs != null) {
				
				while (rsRs.next()) {
					result = new StockVO();

					if (rsRs.getString(1) != null) {
						result.setPid(rsRs.getString(1));
					} else {
						result.setPid("0");
					}
				}	
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
}