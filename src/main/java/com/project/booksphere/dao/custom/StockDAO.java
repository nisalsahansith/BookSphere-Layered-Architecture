package com.project.booksphere.dao.custom;

import com.project.booksphere.dao.CrudDAO;
import com.project.booksphere.dto.StockDto;
import com.project.booksphere.entity.Stock;
import com.project.booksphere.tm.ViewStockTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StockDAO extends CrudDAO<Stock> {
//    ArrayList<ViewStockTM> getAlls() throws SQLException;
//    ArrayList<ViewStockTM> searchID(String id) throws SQLException;
//    boolean updateStock(String id, String name, int qty, double sellPrice, double buyPrice) throws SQLException;
}
