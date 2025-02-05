package com.project.booksphere.dao.custom.impl;

import com.project.booksphere.dao.custom.UserDAO;
import com.project.booksphere.dto.UserDto;
import com.project.booksphere.dao.CrudUtil;
import com.project.booksphere.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    @Override
    public ArrayList<User> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from user");
        ArrayList<User> users = new ArrayList<>();
        while (rst.next()){
            User user = new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            users.add(user);
        }
        return users;
    }

    @Override
    public String getUserPassword(String employId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select Password from user where Employ_ID = ?",employId);
        if (rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public boolean save(User user) throws SQLException {
        return CrudUtil.execute("INSERT INTO user (UserId, UserName, Password, Employ_id) VALUES (?,?,?,?)"
                ,user.getUserId()
                ,user.getUserName()
                ,user.getPassword()
                ,user.getEmployeeId());
    }

    @Override
    public boolean update(User user) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public String nextId() throws SQLException{
        ResultSet rst = CrudUtil.execute("SELECT UserId from user ORDER BY UserId DESC LIMIT 1");
        if (rst.next()){
            String string = rst.getString(1);
            String subString = string.substring(1);
            int lastIndex = Integer.parseInt(subString);
            int nextIndex = lastIndex + 1;
            String newId = String.format("U%03d", nextIndex);
            return newId;
        }
        return "U001";
    }

    @Override
    public boolean checkUser(String userName) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT UserName from user where UserName = ?",userName);
        if (rst.next()){
            return true;
        }
        return false;
    }

    @Override
    public String getUserID(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select UserID from user where Employ_ID = ?", id);
        String name = null;
        if (rst.next()){
            name = rst.getString(1);
        }
        return name;
    }

    @Override
    public boolean resetPassword(String newPassword, String empId) throws SQLException {
        return CrudUtil.execute("update user set Password = ? where Employ_ID = ?",newPassword,empId);
    }

    @Override
    public String getPassword(String userId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Password from user where UserID = ?",userId);
        String password = "";
        if (resultSet.next()){
            password = resultSet.getString(1);
        }
        return password;
    }

    @Override
    public boolean updatePassword(String newPassword, String userId) throws SQLException {
        return CrudUtil.execute("update user set Password = ? where UserID = ?",newPassword,userId);
    }

    @Override
    public boolean setUserName(String userId, String userName) throws SQLException {
        return CrudUtil.execute("update user set UserName = ? where UserID = ?",userName,userId);
    }

    @Override
    public String getName(String id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select UserName from user where UserID = ?",id);
        String name = "";
        if (resultSet.next()){
            name = resultSet.getString(1);
        }
        return name;
    }

    @Override
    public boolean deleteUser(String id) throws SQLException {
        return CrudUtil.execute("update stock set StockID = ? where StockID = ?","DELETED",id);
    }

    @Override
    public boolean nullUser(String id) throws SQLException {
        return CrudUtil.execute("update user set UserID = ? where UserID = ?", "DELETED",id);
    }

    @Override
    public ArrayList<User> search(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from user where UserID = ?",id);
        ArrayList<User> users = new ArrayList<>();
        while (rst.next()){
            User user = new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            users.add(user);
        }
        return users;
    }

    @Override
    public String getMail(String employeeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select email from employee where EmployeeID = ?", employeeId);
        if (rst.next()){
            return rst.getString(1);
        }
        return null;
    }

//    public boolean deleteUser(String id) throws SQLException {
//        return CrudUtil.execute("delete from user where UserID = ?", id);
//
//    }

}
