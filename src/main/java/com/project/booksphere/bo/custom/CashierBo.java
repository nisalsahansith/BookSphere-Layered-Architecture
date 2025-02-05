package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;

import java.sql.SQLException;

public interface CashierBo extends SuperBo {
    String getNameCashier(String id) throws SQLException;
    String getUserIDCashier(String id) throws SQLException;
}
