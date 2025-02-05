package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.EmployeeDAO;
import com.project.booksphere.dao.custom.impl.EmployeeDAOImpl;

import java.sql.SQLException;

public class CashierDashboardBOImpl implements com.project.booksphere.bo.custom.CashierDashboardBo {
    private final EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);
    @Override
    public String getNameCashier(String id) throws SQLException {
        return employeeDAO.getName(id);
    }
}
