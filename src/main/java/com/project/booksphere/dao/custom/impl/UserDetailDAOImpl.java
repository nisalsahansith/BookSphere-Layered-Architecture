package com.project.booksphere.dao.custom.impl;

import com.project.booksphere.dao.custom.UserDetailDAO;
import com.project.booksphere.dto.UserDetailDto;
import com.project.booksphere.dao.CrudUtil;
import com.project.booksphere.entity.UserDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserDetailDAOImpl implements UserDetailDAO {

    @Override
    public boolean save(UserDetail userDetail) throws SQLException {
        return CrudUtil.execute("insert into user_details values (?,?)",userDetail.getUserId(),userDetail.getCustomerId());
    }

    @Override
    public ArrayList<UserDetail> getAll() throws SQLException {
        return null;
    }

    @Override
    public String nextId() throws SQLException {
        return "";
    }

    @Override
    public ArrayList<UserDetail> search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean update(UserDetail dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return CrudUtil.execute("delete from user_details where CustomerID = ? ",customerId);
    }
}
