package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;
import com.project.booksphere.dto.SupplierDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBo extends SuperBo {

    boolean saveSupplier(SupplierDto supplierDto) throws SQLException;
    boolean deleteSupplier(String id) throws SQLException;
    boolean updateSupplier(SupplierDto supplierDto) throws SQLException;
    String nextSupplierId() throws SQLException;
    ArrayList<SupplierDto> getAllSupplier() throws SQLException;
    ArrayList<SupplierDto> searchSupplier(String search) throws SQLException;
}
