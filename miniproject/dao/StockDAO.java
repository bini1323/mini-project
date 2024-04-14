package dao;

import vo.ApplyVO;
import vo.StockVO;

import java.util.ArrayList;


public interface StockDAO {

	public ArrayList<StockVO> getAllProducts();
	
	public int insertStock(StockVO stockVO);

	public StockVO selectById(long stockId);

	public int updateStockNum(StockVO stockVO, ApplyVO applyVO);

	public StockVO selectLast();

	public StockVO selectByPartName(String partName);
	
}
