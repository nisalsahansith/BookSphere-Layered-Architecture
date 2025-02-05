package com.project.booksphere.dao;

import com.project.booksphere.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    public static <T>T execute(String sql, Object... object) throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < object.length; i++) {
            preparedStatement.setObject(i + 1, object[i]);
        }
        if (sql.startsWith("select ") || sql.startsWith("SELECT")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return (T) resultSet;
        } else {
            int result = preparedStatement.executeUpdate();
            boolean isSaved = result > 0;
            return (T) (Boolean) isSaved;
        }
    }
}
