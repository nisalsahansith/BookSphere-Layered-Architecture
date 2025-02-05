package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.ManagerDashboardBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.EmployeeDAO;

import java.sql.SQLException;

public class ManagerDashboardBOImpl implements ManagerDashboardBo {
    private final EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public String getManagerName(String id) throws SQLException {
        return employeeDAO.getName(id);
    }
}
