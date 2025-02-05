package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.SignUpBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.EmployeeDAO;
import com.project.booksphere.dao.custom.UserDAO;
import com.project.booksphere.dto.EmployeeDto;
import com.project.booksphere.dto.UserDto;
import com.project.booksphere.entity.Employee;
import com.project.booksphere.entity.User;

import java.sql.SQLException;

public class SignUpBOImpl implements SignUpBo {
    private final EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);
    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public boolean checkOwner() throws SQLException {
        return employeeDAO.checkOwner();
    }

    @Override
    public String getNextEmployId() throws SQLException{
        return employeeDAO.nextId();
    }

    @Override
    public boolean checkPhone(String phone) throws SQLException {
        return employeeDAO.checkPhone(phone);
    }

    @Override
    public boolean checkEmail(String email) throws SQLException {
        return employeeDAO.checkEmail(email);
    }

    @Override
    public boolean saveEmployee(EmployeeDto employeeDto) throws SQLException {
        Employee employee = new Employee(employeeDto.getId(),employeeDto.getName(),employeeDto.getRole(),employeeDto.getPhoneNumber(),employeeDto.getEmail(),employeeDto.getDate());
        return employeeDAO.save(employee);
    }

    @Override
    public String nextUserId() throws SQLException{
        return userDAO.nextId();
    }

    @Override
    public boolean checkUser(String userName) throws SQLException {
        return userDAO.checkUser(userName);
    }

    @Override
    public boolean saveUser(UserDto userDto) throws SQLException {
        User user = new User(userDto.getUserId(),userDto.getUserName(),userDto.getPassword(),userDto.getEmployeeId());
        return userDAO.save(user);
    }

}
