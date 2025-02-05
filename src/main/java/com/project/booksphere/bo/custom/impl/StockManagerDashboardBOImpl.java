package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.StockManagerDashboardBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.EmployeeDAO;

import java.sql.SQLException;

public class StockManagerDashboardBOImpl implements StockManagerDashboardBo {
    private final EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public String getStockManagerName(String id) throws SQLException {
        return employeeDAO.getName(id);
    }
}
