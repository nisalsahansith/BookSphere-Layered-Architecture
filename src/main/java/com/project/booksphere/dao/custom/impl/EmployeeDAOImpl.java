package com.project.booksphere.dao.custom.impl;

import com.project.booksphere.dao.custom.EmployeeDAO;
import com.project.booksphere.dto.EmployeeDto;
import com.project.booksphere.dao.CrudUtil;
import com.project.booksphere.entity.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public String nextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT EmployeeID FROM Employee ORDER BY EmployeeID DESC LIMIT 1 ");
        if (rst.next()){
            String string = rst.getString(1);
            String subString = string.substring(1);
            int lastIndex = Integer.parseInt(subString);
            int nextIndex = lastIndex + 1;
            String newId = String.format("E%03d", nextIndex);
            return newId;
        }
        return "E001";
    }

    @Override
    public String getEmployeeId(String username) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Employ_id from user where Username = ?", username);
        if(resultSet.next()) {
            String employ_id = resultSet.getString("Employ_id");
            if (!employ_id.equals(null)){
                return employ_id;
            }
        }
        return null;
    }

    @Override
    public String checkRole(String userId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Role from employee where EmployeeId = ?",userId);
        if(resultSet.next()) {
            String role = resultSet.getString("Role");
            return role;
        }
        return null;
    }

    @Override
    public boolean checkOwner() throws SQLException {
        String role = "Owner";
        ResultSet rst = CrudUtil.execute("select Role from Employee where Role = ?",role);
        if (rst.next()){
            if (rst.getString(1).equals("Owner")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean save(Employee employee) throws SQLException {
        return CrudUtil.execute("INSERT INTO employee (EmployeeID, Name, Role, Phone,Email,register_date) VALUES (?,?,?,?,?,?) ",
                employee.getId(),employee.getName(),employee.getRole(),employee.getPhoneNumber(),employee.getEmail(),employee.getDate());
    }

    @Override
    public String getNextEmployId() throws SQLException{
        String employId = CrudUtil.execute("SELECT EmployeeID FROM employee ORDER BY EmployeeID DESC LIMIT 1 ");
        if (!employId.equals("")){
            return employId;
        }
        return "E001";
    }

    @Override
    public boolean checkPhone(String phone) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Phone from employee where Phone = ?",phone);
        if (rst.next()){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkEmail(String email) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Email from employee where Email = ?",email);
        if (rst.next()){
            return true;
        }
        return false;
    }

    @Override
    public String getName(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select Name from employee where EmployeeID = ?", id);
        String name = null;
        if (rst.next()){
            name = rst.getString(1);
        }
        return name;
    }

    @Override
    public String getEmpId(String email) throws SQLException {
        ResultSet rst = CrudUtil.execute("select EmployeeID from employee where Email = ?", email);
        String Email = null;
        if (rst.next()){
            Email = rst.getString(1);
        }
        return Email;
    }

    @Override
    public boolean delete(String employeeId) throws SQLException {
        return CrudUtil.execute("delete from employee where EmployeeID = ?",employeeId);
    }

    @Override
    public boolean update(Employee employee) throws SQLException {
        return  CrudUtil.execute("UPDATE employee SET  Name = ?, Role = ?, Phone = ?, Email = ?  WHERE EmployeeID = ?",
                employee.getName(),
                employee.getRole(),
                employee.getPhoneNumber(),
                employee.getEmail(),
                employee.getId());
    }

    @Override
    public ArrayList<Employee> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from employee");
        ArrayList<Employee> employees = new ArrayList<>();
        while (rst.next()) {
            java.sql.Date sqlDate = rst.getDate(6);
            LocalDate localDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;
            Employee employee = new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    localDate
            );
            employees.add(employee);
        }
        return employees;
    }

    @Override
    public ArrayList<Employee> search(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from employee where EmployeeID = ?", id);
        ArrayList<Employee> employees = new ArrayList<>();
        while (rst.next()) {
            java.sql.Date sqlDate = rst.getDate(6);
            LocalDate localDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;
            Employee employee = new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    localDate
            );
            employees.add(employee);
        }
        return employees;
    }

    @Override
    public String getMail() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Email from employee where Role = ?", "Owner");
        String Email = null;
        if (rst.next()){
            Email = rst.getString(1);
        }
        return Email;
    }

}
