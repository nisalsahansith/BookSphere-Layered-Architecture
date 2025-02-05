package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.EmployeeBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.EmployeeDAO;
import com.project.booksphere.dto.CustomerDto;
import com.project.booksphere.dto.EmployeeDto;
import com.project.booksphere.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBo {
    private final EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public boolean saveEmployee(EmployeeDto employeeDto) throws SQLException {
        Employee employee = new Employee(employeeDto.getId(),employeeDto.getName(),employeeDto.getRole(),employeeDto.getPhoneNumber(),employeeDto.getEmail(),employeeDto.getDate());
        return employeeDAO.save(employee);
    }

    @Override
    public boolean deleteEmployee(String employeeId) throws SQLException {
        return employeeDAO.delete(employeeId);
    }

    @Override
    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException {
        Employee employee = new Employee(employeeDto.getId(),employeeDto.getName(),employeeDto.getRole(),employeeDto.getPhoneNumber(),employeeDto.getEmail(),employeeDto.getDate());
        return employeeDAO.update(employee);
    }

    @Override
    public String nextEmployeeId() throws SQLException {
        return employeeDAO.nextId();
    }

    @Override
    public ArrayList<EmployeeDto> getAllEmployee() throws SQLException {
        ArrayList<Employee> employees = employeeDAO.getAll();
        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDto employeeDto = new EmployeeDto(
                    employee.getId(),
                    employee.getName(),
                    employee.getRole(),
                    employee.getPhoneNumber(),
                    employee.getEmail(),
                    employee.getDate()
            );
            employeeDtos.add(employeeDto);
        }
        return employeeDtos;
    }

    @Override
    public ArrayList<EmployeeDto> searchEmployee(String id) throws SQLException {
        ArrayList<Employee> employees = employeeDAO.search(id);
        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDto employeeDto = new EmployeeDto(
                    employee.getId(),
                    employee.getName(),
                    employee.getRole(),
                    employee.getPhoneNumber(),
                    employee.getEmail(),
                    employee.getDate()
            );
            employeeDtos.add(employeeDto);
        }
        return employeeDtos;
    }
}
