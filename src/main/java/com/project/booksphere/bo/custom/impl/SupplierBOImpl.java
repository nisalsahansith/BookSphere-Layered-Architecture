package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.SupplierBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.StockDetailDAO;
import com.project.booksphere.dao.custom.SupplierDAO;
import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.CustomerDto;
import com.project.booksphere.dto.SupplierDto;
import com.project.booksphere.dao.CrudUtil;
import com.project.booksphere.entity.Supplier;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBo {
    private final SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);
    private final StockDetailDAO stockDetailDAO = (StockDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STOCK_DETAIL);

    @Override
    public boolean saveSupplier(SupplierDto supplierDto) throws SQLException {
        Supplier supplier = new Supplier(supplierDto.getSupId(),supplierDto.getName(),supplierDto.getPhone(),supplierDto.getAddress(),supplierDto.getEmail(),supplierDto.getUserId());
        return supplierDAO.save(supplier);
    }
    @Override
    public boolean deleteSupplier(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isDeleteFromSpDetails = stockDetailDAO.deleteDetailSup(id);
            boolean isDelete = CrudUtil.execute("delete from supplier where SupplierID = ?",id);
            if (isDelete){
                connection.setAutoCommit(true);
                return true;
            }
            connection.rollback();
            return false;
        }catch (SQLException e){
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException {
        Supplier supplier = new Supplier(supplierDto.getSupId(),supplierDto.getName(),supplierDto.getPhone(),supplierDto.getAddress(),supplierDto.getEmail(),supplierDto.getUserId());
        return supplierDAO.update(supplier);
    }

    @Override
    public String nextSupplierId() throws SQLException {
        return supplierDAO.nextId();
    }

    @Override
    public ArrayList<SupplierDto> getAllSupplier() throws SQLException {
        ArrayList<Supplier> suppliers = supplierDAO.getAll();
        ArrayList<SupplierDto> supplierDtos = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            SupplierDto supplierDto = new SupplierDto(
                    supplier.getSupId(),
                    supplier.getName(),
                    supplier.getPhone(),
                    supplier.getAddress(),
                    supplier.getEmail(),
                    supplier.getUserId()
            );
            supplierDtos.add(supplierDto);
        }
        return supplierDtos;
    }

    @Override
    public ArrayList<SupplierDto> searchSupplier(String search) throws SQLException {
        ArrayList<Supplier> suppliers = supplierDAO.search(search);
        ArrayList<SupplierDto> supplierDtos = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            SupplierDto supplierDto = new SupplierDto(
                    supplier.getSupId(),
                    supplier.getName(),
                    supplier.getPhone(),
                    supplier.getAddress(),
                    supplier.getEmail(),
                    supplier.getUserId()
            );
            supplierDtos.add(supplierDto);
        }
        return supplierDtos;
    }
}
