package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.ManagerHomePageBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.EmployeeDAO;
import com.project.booksphere.dao.custom.UserDAO;

import java.sql.SQLException;

public class ManagerHomePageBOImpl implements ManagerHomePageBo {
    private final EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);
    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public String getEmployeeName(String id) throws SQLException {
        return employeeDAO.getName(id);
    }

    @Override
    public String getUserID(String id) throws SQLException {
        return userDAO.getUserID(id);
    }
}
