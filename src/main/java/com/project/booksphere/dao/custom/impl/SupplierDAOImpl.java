package com.project.booksphere.dao.custom.impl;

import com.project.booksphere.dao.custom.SupplierDAO;
import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.SupplierDto;
import com.project.booksphere.dao.CrudUtil;
import com.project.booksphere.entity.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    StockDetailDAOImpl stockDetailDAO = new StockDetailDAOImpl();

    @Override
    public ArrayList<Supplier> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier");
        ArrayList<Supplier> suppliers = new ArrayList<>();
        while(rst.next()){
            Supplier supplier = new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            suppliers.add(supplier);
        }
        return suppliers;
    }

    @Override
    public String nextId() throws SQLException {
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

    @Override
    public ArrayList<Supplier> search(String search) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier where SupplierID = ?",search);
        ArrayList<Supplier> suppliers = new ArrayList<>();
        while(rst.next()){
            Supplier supplier = new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            suppliers.add(supplier);
        }
        return suppliers;
    }

    @Override
    public boolean save(Supplier supplier) throws SQLException {
        return CrudUtil.execute("insert into supplier values (?,?,?,?,?,?)",
                supplier.getSupId(),
                supplier.getName(),
                supplier.getPhone(),
                supplier.getAddress(),
                supplier.getEmail(),
                supplier.getUserId()
        );
    }

    @Override
    public String searchSupplier(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select Name from supplier where SupplierID = ?",id);
        String ID = "No Supplier";
        if(rst.next()){
            ID = rst.getString(1);
        }
        return ID;
    }

    @Override
    public boolean delete(String id) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(false);
//            boolean isDeleteFromSpDetails = stockDetailDAO.deleteDetailSup(id);
//            boolean isDelete = CrudUtil.execute("delete from supplier where SupplierID = ?",id);
//            if (isDelete){
//                connection.setAutoCommit(true);
//                return true;
//            }
//            connection.rollback();
//            return false;
//        }catch (SQLException e){
//            connection.rollback();
//            return false;
//        }finally {
//            connection.setAutoCommit(true);
//        }
        return false;
    }

    @Override
    public boolean update(Supplier supplier) throws SQLException {
        return CrudUtil.execute("update supplier SET Name = ?, Phone = ?, Address = ?, Email = ? where SupplierID = ? ",
                supplier.getName(),supplier.getPhone(),supplier.getAddress(),supplier.getEmail(),supplier.getSupId()
        );
    }

    @Override
    public boolean deleteUser(String id) throws SQLException {
        return CrudUtil.execute("update user set UserID = ? where UserID = ?","DELETED",id);
    }
}
