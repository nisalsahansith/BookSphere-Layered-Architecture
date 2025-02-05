package com.project.booksphere.dao.custom;

import com.project.booksphere.dao.CrudDAO;
import com.project.booksphere.dto.EmployeeDto;
import com.project.booksphere.entity.Employee;

import java.sql.SQLException;

public interface EmployeeDAO extends CrudDAO<Employee> {
        String getEmployeeId(String username) throws SQLException;
        String checkRole(String userId) throws SQLException;
        boolean checkOwner() throws SQLException;
        String getNextEmployId() throws SQLException;
        boolean checkPhone(String phone) throws SQLException;
        boolean checkEmail(String email) throws SQLException;
        String getName(String id) throws SQLException;
        String getEmpId(String email) throws SQLException;
        String getMail() throws SQLException;
    }
