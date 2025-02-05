package com.project.booksphere.dao.custom;

import com.project.booksphere.dao.CrudDAO;
import com.project.booksphere.dto.UserDto;
import com.project.booksphere.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    String getUserPassword(String employId) throws SQLException;
    boolean checkUser(String userName) throws SQLException;
    String getUserID(String id) throws SQLException;
    boolean resetPassword(String newPassword, String empId) throws SQLException ;
    String getPassword(String userId) throws SQLException;
    boolean updatePassword(String newPassword, String userId) throws SQLException;
    boolean setUserName(String userId, String userName) throws SQLException;
    String getName(String id) throws SQLException;
    boolean deleteUser(String id) throws SQLException;
    boolean nullUser(String id) throws SQLException;
    String getMail(String employeeId) throws SQLException ;
}
