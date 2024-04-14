package main.dao;

import java.util.ArrayList;
import java.util.List;

import main.vo.ApplyVO;
import main.vo.StockVO;

public interface StockDAO {

	public ArrayList<StockVO> getAllProducts();
	
	public int insertStock(StockVO stockVO);

	public StockVO selectOne(long stockId);

	public int updateStockNum(StockVO stockVO, ApplyVO applyVO);

	public StockVO selectLast();
	
}
