package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;

import java.sql.SQLException;

public interface SettingBo extends SuperBo {

    public String getUserPassword(String userId) throws SQLException;
    public boolean updateUserPassword(String newPassword, String userId) throws SQLException;
    public boolean setUserName(String userId, String userName) throws SQLException;
    public String getUserName(String id) throws SQLException;
}
