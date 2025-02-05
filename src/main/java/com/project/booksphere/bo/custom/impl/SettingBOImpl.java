package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.SettingBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.UserDAO;

import java.sql.SQLException;

public class SettingBOImpl implements SettingBo {
    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public String getUserPassword(String userId) throws SQLException {
        return userDAO.getPassword(userId);
    }

    @Override
    public boolean updateUserPassword(String newPassword, String userId) throws SQLException {
        return userDAO.updatePassword(newPassword,userId);
    }

    @Override
    public boolean setUserName(String userId, String userName) throws SQLException {
        return userDAO.setUserName(userId,userName);
    }

    @Override
    public String getUserName(String id) throws SQLException {
        return userDAO.getName(id);
    }
}
