package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;
import com.project.booksphere.dto.EmployeeDto;
import com.project.booksphere.dto.UserDto;

import java.sql.SQLException;

public interface SignUpBo extends SuperBo {

    boolean checkOwner() throws SQLException;
    String getNextEmployId() throws SQLException;
    boolean checkPhone(String phone) throws SQLException;
    boolean checkEmail(String email) throws SQLException;
    boolean saveEmployee(EmployeeDto employeeDto) throws SQLException;
    String nextUserId() throws SQLException;
    boolean checkUser(String userName) throws SQLException;
    boolean saveUser(UserDto userDto) throws SQLException;
}
