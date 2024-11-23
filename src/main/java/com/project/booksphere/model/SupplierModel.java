package com.project.booksphere.model;

import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.SupplierDto;
import com.project.booksphere.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    StockDetailModel stockDetailModel = new StockDetailModel();

    public String searchSupplier(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select Name from supplier where SupplierID = ?",id);
        String ID = "No Supplier";
        if(rst.next()){
            ID = rst.getString(1);
        }
        return ID;
    }

    public ArrayList<SupplierDto> getAllSupplier() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier");
        ArrayList<SupplierDto> suppliers = new ArrayList<>();
        while(rst.next()){
            SupplierDto dto = new SupplierDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            suppliers.add(dto);
        }
        return suppliers;
    }

    public String getNextID() throws SQLException {
        ResultSet rst = CrudUtil.execute("select SupplierID from supplier order by SupplierID desc limit 1");
        if(rst.next()){
            String string =  rst.getString(1);
            String substring = string.substring(3);
            int id = Integer.parseInt(substring);
            int nextID = id + 1;
            String ID = String.format("SUP%03d", nextID);
            return ID;
        }
        return "SUP001";
    }

    public boolean saveSupplier(SupplierDto supplierDto) throws SQLException {
        return CrudUtil.execute("insert into supplier values (?,?,?,?,?,?)",
                    supplierDto.getSupId(),
                    supplierDto.getName(),
                    supplierDto.getPhone(),
                    supplierDto.getAddress(),
                    supplierDto.getEmail(),
                    supplierDto.getUserId()
                );
    }

    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException {
        return CrudUtil.execute("update supplier SET Name = ?, Phone = ?, Address = ?, Email = ? where SupplierID = ? ",
                supplierDto.getName(),supplierDto.getPhone(),supplierDto.getAddress(),supplierDto.getEmail(),supplierDto.getSupId()
        );
    }

    public boolean deleteSupplier(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isDeleteFromSpDetails = stockDetailModel.deleteDetailSup(id);
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

    public boolean deleteUser(String id) throws SQLException {
        return CrudUtil.execute("update user set UserID = ? where UserID = ?","DELETED",id);
    }

    public ArrayList<SupplierDto> searchSupId(String search) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier where SupplierID = ?",search);
        ArrayList<SupplierDto> suppliers = new ArrayList<>();
        while(rst.next()){
            SupplierDto dto = new SupplierDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            suppliers.add(dto);
        }
        return suppliers;
    }
}
