package com.project.booksphere.model;

import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.EmployeeDto;
import com.project.booksphere.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeeModel {

    public ArrayList<EmployeeDto> getAllEmployee() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from employee");
        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();
        while (rst.next()) {
            java.sql.Date sqlDate = rst.getDate(6);
            LocalDate localDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;
            EmployeeDto employeeDto = new EmployeeDto(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getString(4),
                rst.getString(5),
                localDate
            );
            employeeDtos.add(employeeDto);
        }
        return employeeDtos;
    }

    public String nextEmployeeId() throws SQLException {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT EmployeeID FROM Employee ORDER BY EmployeeID DESC LIMIT 1 ";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rst = statement.executeQuery();
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

    public boolean saveEmployee(EmployeeDto employeeDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO employee (EmployeeID,Name,Role,Phone,Email,register_date) VALUES (?,?,?,?,?,?)",
                employeeDto.getId(),
                employeeDto.getName(),
                employeeDto.getRole(),
                employeeDto.getPhoneNumber(),
                employeeDto.getEmail(),
                employeeDto.getDate());
    }

    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException {
        return  CrudUtil.execute("UPDATE employee SET  Name = ?, Role = ?, Phone = ?, Email = ?  WHERE EmployeeID = ?",
                employeeDto.getName(),
                employeeDto.getRole(),
                employeeDto.getPhoneNumber(),
                employeeDto.getEmail(),
                employeeDto.getId());
    }

    public String getName(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select Name from employee where EmployeeID = ?", id);
        String name = null;
        if (rst.next()){
            name = rst.getString(1);
        }
        return name;
    }

    public boolean deleteCustomer(String employeeId) throws SQLException {
        return CrudUtil.execute("delete from employee where EmployeeID = ?",employeeId);
    }

    public boolean checkOwner() throws SQLException {
           ResultSet rst = CrudUtil.execute("select Role from employee where Role = ?", "Owner");
           String name = "";
           if (rst.next()) {
               name = rst.getString(1);
           }
           if (name.equals("Owner")) {
               return true;
           }
           return false;
    }

    public boolean checkEmail(String email) throws SQLException {
        ResultSet rst = CrudUtil.execute("select Email from employee where Email = ?",email);
        String Email = "";
        if (rst.next()){
            Email = rst.getString(1);
        }
        if (Email.equals(email)){
            return true;
        }
        return false;
    }

    public String getEmpId(String email) throws SQLException {
        ResultSet rst = CrudUtil.execute("select EmployeeID from employee where Email = ?", email);
        String Email = null;
        if (rst.next()){
            Email = rst.getString(1);
        }
        return Email;
    }

    public String getMail() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Email from employee where Role = ?", "Owner");
        String Email = null;
        if (rst.next()){
            Email = rst.getString(1);
        }
        return Email;
    }

    public ArrayList<EmployeeDto> search(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from employee where EmployeeID = ?", id);
        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();
        while (rst.next()) {
            java.sql.Date sqlDate = rst.getDate(6);
            LocalDate localDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;
            EmployeeDto employeeDto = new EmployeeDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    localDate
            );
            employeeDtos.add(employeeDto);
        }
        return employeeDtos;
    }
}
