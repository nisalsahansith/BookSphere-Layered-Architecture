package com.project.booksphere.dao;

import com.project.booksphere.dto.CustomerDto;
import com.project.booksphere.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T> extends SuperDAO {
    ArrayList<T> getAll() throws SQLException;
    String nextId() throws SQLException;
    ArrayList<T> search(String id) throws SQLException;
    boolean save(T dto) throws SQLException;
    boolean update(T dto) throws SQLException;
    boolean delete(String id) throws SQLException;

}
