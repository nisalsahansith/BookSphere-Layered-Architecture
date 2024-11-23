package com.project.booksphere.model;

import com.project.booksphere.db.DBConnection;
import com.project.booksphere.util.CrudUtil;

import java.sql.*;

public class SignUpModel {
    public boolean signUp(String employId, String userTd, String name, String role, String contactNum, String userName, String password, String email, Date date) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO employee (EmployeeID, Name, Role, Phone,Email,register_date) VALUES (?,?,?,?,?,?) ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, employId);
        statement.setString(2, name);
        statement.setString(3, role);
        statement.setString(4, contactNum);
        statement.setString(5,email);
        statement.setDate(6,date);
        int rst = statement.executeUpdate();
        boolean isEmployUpdate = false;
        if (rst > 0) {
            isEmployUpdate = true;
        }
        if (isEmployUpdate) {
            String sql1 = "INSERT INTO user (UserId, UserName, Password, Employ_id) VALUES (?,?,?,?)";
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            statement1.setString(1, userTd);
            statement1.setString(2, userName);
            statement1.setString(3, password);
            statement1.setString(4, employId);
            int index = statement1.executeUpdate();
            if (index > 0){
                return true;
            }
            return false;
        }
        return false;
    }

    public String getNextEmployId() throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT EmployeeID FROM employee ORDER BY EmployeeID DESC LIMIT 1 ";
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

    public String getNextUserId() throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT UserId from user ORDER BY UserId DESC LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rst = statement.executeQuery();
        if (rst.next()){
            String string = rst.getString(1);
            String subString = string.substring(1);
            int lastIndex = Integer.parseInt(subString);
            int nextIndex = lastIndex + 1;
            String newId = String.format("U%03d",nextIndex);
            return newId;
        }
        return "U001";
    }

    public boolean checkUser(String userName) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT UserName from user where UserName = ?",userName);
        if (rst.next()){
            return true;
        }
        return false;
    }

    public boolean checkPhone(String phone) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Phone from employee where Phone = ?",phone);
        if (rst.next()){
            return true;
        }
        return false;
    }

    public boolean checkEmail(String email) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Email from employee where Email = ?",email);
        if (rst.next()){
            return true;
        }
        return false;
    }
}
