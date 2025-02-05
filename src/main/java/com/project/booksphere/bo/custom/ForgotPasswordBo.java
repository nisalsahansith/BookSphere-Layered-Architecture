package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;

import java.sql.SQLException;

public interface ForgotPasswordBo extends SuperBo {
    public String getEmpId(String email) throws SQLException;
    public boolean checkEmail(String email) throws SQLException;
    public boolean resetPassword(String newPassword, String empId) throws SQLException;
}
