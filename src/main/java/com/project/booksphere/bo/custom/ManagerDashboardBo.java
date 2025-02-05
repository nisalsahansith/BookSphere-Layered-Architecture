package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;

import java.sql.SQLException;

public interface ManagerDashboardBo extends SuperBo {
    public String getManagerName(String id) throws SQLException;
}
