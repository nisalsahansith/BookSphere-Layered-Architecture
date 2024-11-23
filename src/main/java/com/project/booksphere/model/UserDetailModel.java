package com.project.booksphere.model;

import com.project.booksphere.util.CrudUtil;

import java.sql.SQLException;

public class UserDetailModel {
    public boolean delete(String customerId) throws SQLException {
        return CrudUtil.execute("delete from user_details where CustomerID = ? ",customerId);
    }

    public boolean saveData(String id, String userId) throws SQLException {
        return CrudUtil.execute("insert into user_details values (?,?)",userId,id);
    }

    public boolean nullUser(String id) throws SQLException {
        return CrudUtil.execute("update user set UserID = ? where UserID = ?", "DELETED",id);
    }
}
