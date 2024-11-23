package com.project.booksphere.db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DBConnection {
    private static DBConnection dbConnection;
    private  Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/BookSphere";
    private final String user = "root";
    private final String password = "Ijse@1234";

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    public static DBConnection getInstance() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }
}
