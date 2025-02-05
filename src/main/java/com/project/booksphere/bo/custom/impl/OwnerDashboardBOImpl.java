package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.OwnerDashboardBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.EmployeeDAO;

import java.sql.SQLException;

public class OwnerDashboardBOImpl implements OwnerDashboardBo {
    private final EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public String getOwnerName(String id) throws SQLException {
        return employeeDAO.getName(id);
    }
}
