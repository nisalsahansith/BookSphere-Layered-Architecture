package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;

import java.sql.SQLException;

public interface CashierDashboardBo extends SuperBo {
    public String getNameCashier(String id) throws SQLException;
}
