package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;

import java.sql.SQLException;

public interface StockManagerDashboardBo extends SuperBo {
    String getStockManagerName(String id) throws SQLException;
}
