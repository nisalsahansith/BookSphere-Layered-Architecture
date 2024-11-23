package com.project.booksphere.model;

import com.project.booksphere.db.DBConnection;
import com.project.booksphere.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    public String login(String username) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Employ_id from user where Username = ?", username);
        if(resultSet.next()) {
            String employ_id = resultSet.getString("Employ_id");
            if (!employ_id.equals(null)){
                return employ_id;
            }
        }
        return null;
    }

    public String checkRole(String userId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select Role from employee where EmployeeId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            String role = resultSet.getString("Role");
            return role;
        }
        return null;
    }

    public String getPassword(String employId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select Password from user where Employ_ID = ?",employId);
        if (rst.next()){
            return rst.getString(1);
        }
        return null;
    }

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

}
