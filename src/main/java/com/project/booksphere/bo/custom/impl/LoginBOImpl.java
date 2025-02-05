package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.LoginBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.EmployeeDAO;
import com.project.booksphere.dao.custom.UserDAO;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBo {
    private final EmployeeDAO employeeDAOImpl = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);
    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public String getEmployeeId(String username) throws SQLException {
        return employeeDAOImpl.getEmployeeId(username);
    }

    @Override
    public String checkRole(String userId) throws SQLException {
        return employeeDAOImpl.checkRole(userId);
    }

    @Override
    public boolean checkOwner() throws SQLException {
        return employeeDAOImpl.checkOwner();
    }

    @Override
    public String getUserPassword(String employId) throws SQLException {
        return userDAO.getUserPassword(employId);
    }
}
