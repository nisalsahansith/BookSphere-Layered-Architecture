package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.UserBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.UserDAO;
import com.project.booksphere.dto.SupplierDto;
import com.project.booksphere.dto.UserDto;
import com.project.booksphere.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBo {
    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public boolean deleteUser(String id) throws SQLException {
        return userDAO.deleteUser(id);
    }

    @Override
    public ArrayList<UserDto> searchUser(String id) throws SQLException {
        ArrayList<User> users = userDAO.search(id);
        ArrayList<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto(
                    user.getUserId(),user.getUserName(),user.getPassword(),user.getEmployeeId()
            );
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public String getMailUser(String employeeId) throws SQLException {
        return userDAO.getMail(employeeId);
    }

    @Override
    public ArrayList<UserDto> getAllUsers() throws SQLException {
        ArrayList<User> users = userDAO.getAll();
        ArrayList<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto(
                    user.getUserId(),user.getUserName(),user.getPassword(),user.getEmployeeId()
            );
            userDtos.add(userDto);
        }
        return userDtos;
    }
}
