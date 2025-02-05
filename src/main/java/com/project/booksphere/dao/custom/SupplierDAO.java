package com.project.booksphere.dao.custom;

import com.project.booksphere.dao.CrudDAO;
import com.project.booksphere.dto.SupplierDto;
import com.project.booksphere.entity.Supplier;

import java.sql.SQLException;

public interface SupplierDAO extends CrudDAO<Supplier> {
    String searchSupplier(String id) throws SQLException;
    boolean deleteUser(String id) throws SQLException;
}
