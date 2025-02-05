package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;
import com.project.booksphere.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBo extends SuperBo {

    boolean saveEmployee(EmployeeDto employeeDto) throws SQLException;
    boolean deleteEmployee(String employeeId) throws SQLException;
    boolean updateEmployee(EmployeeDto employeeDto) throws SQLException;
    String nextEmployeeId() throws SQLException;
    ArrayList<EmployeeDto> getAllEmployee() throws SQLException;
    ArrayList<EmployeeDto> searchEmployee(String id) throws SQLException;
}
