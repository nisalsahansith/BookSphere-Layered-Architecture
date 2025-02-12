package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;

import java.sql.SQLException;

public interface StockManagerHomePageBo extends SuperBo {

    String getEmployeeName(String id) throws SQLException;
    String getUserID(String id) throws SQLException;
}
