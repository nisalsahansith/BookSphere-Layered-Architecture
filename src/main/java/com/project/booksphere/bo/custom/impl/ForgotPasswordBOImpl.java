package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.ForgotPasswordBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.EmployeeDAO;
import com.project.booksphere.dao.custom.UserDAO;

import java.sql.SQLException;

public class ForgotPasswordBOImpl implements ForgotPasswordBo {
    private final EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);
    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public String getEmpId(String email) throws SQLException {
        return employeeDAO.getEmpId(email);
    }

    @Override
    public boolean checkEmail(String email) throws SQLException {
        return employeeDAO.checkEmail(email);
    }

    @Override
    public boolean resetPassword(String newPassword, String empId) throws SQLException {
        return userDAO.resetPassword(newPassword,empId);
    }
}
