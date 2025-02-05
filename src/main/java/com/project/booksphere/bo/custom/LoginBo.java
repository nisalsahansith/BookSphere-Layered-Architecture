package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;

import java.sql.SQLException;

public interface LoginBo extends SuperBo {

    public String getEmployeeId(String username) throws SQLException;
    public String checkRole(String userId) throws SQLException;
    public boolean checkOwner() throws SQLException;
    public String getUserPassword(String employId) throws SQLException;
}
