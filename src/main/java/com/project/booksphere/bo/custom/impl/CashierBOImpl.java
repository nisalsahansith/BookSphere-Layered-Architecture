package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.CashierBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.EmployeeDAO;
import com.project.booksphere.dao.custom.UserDAO;

import java.sql.SQLException;

public class CashierBOImpl implements CashierBo {
    private final EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);
    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public String getNameCashier(String id) throws SQLException {
        return employeeDAO.getName(id);
    }

    @Override
    public String getUserIDCashier(String id) throws SQLException {
       return userDAO.getUserID(id);
    }
}
