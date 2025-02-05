package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.SecuirityProtectionBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.EmployeeDAO;

import java.sql.SQLException;

public class SecuirityProtectionBOImpl implements SecuirityProtectionBo {
    private final EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public String getMail() throws SQLException {
        return employeeDAO.getMail();
    }

}
