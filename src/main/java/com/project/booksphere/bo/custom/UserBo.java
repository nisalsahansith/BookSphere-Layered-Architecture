package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;
import com.project.booksphere.dto.UserDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBo extends SuperBo{

    boolean deleteUser(String id) throws SQLException;
    ArrayList<UserDto> searchUser(String id) throws SQLException;
    String getMailUser(String employeeId) throws SQLException;
    ArrayList<UserDto> getAllUsers() throws SQLException;
}
