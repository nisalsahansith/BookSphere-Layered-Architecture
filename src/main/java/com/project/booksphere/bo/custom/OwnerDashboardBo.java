package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;

import java.sql.SQLException;

public interface OwnerDashboardBo extends SuperBo {
    public String getOwnerName(String id) throws SQLException;
}
