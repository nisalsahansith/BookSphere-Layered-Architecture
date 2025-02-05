package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;
import com.project.booksphere.dto.CustomDto;
import com.project.booksphere.tm.ViewStockTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ViewStockBo extends SuperBo {
    ArrayList<CustomDto> searchStockID(String id) throws SQLException;
    boolean updateStock(String id, String name, int qty, double sellPrice, double buyPrice) throws SQLException;
    ArrayList<CustomDto> getAllDetails() throws SQLException;
}
