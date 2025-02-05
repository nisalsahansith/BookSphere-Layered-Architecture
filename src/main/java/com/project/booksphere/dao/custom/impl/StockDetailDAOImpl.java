package com.project.booksphere.dao.custom.impl;

import com.project.booksphere.dao.custom.StockDetailDAO;
import com.project.booksphere.dto.StockDetailDto;
import com.project.booksphere.dao.CrudUtil;
import com.project.booksphere.entity.StockDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class StockDetailDAOImpl implements StockDetailDAO {

    @Override
    public ArrayList<StockDetail> getAll() throws SQLException {
        return null;
    }

    @Override
    public String nextId() throws SQLException {
        return "";
    }

    @Override
    public ArrayList<StockDetail> search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean save(StockDetail stockDetail) throws SQLException {
        return CrudUtil.execute("insert into stock_detail values(?,?,?,?) ",
                stockDetail.getSupplyId(),
                stockDetail.getStockId(),
                stockDetail.getQuantity(),
                stockDetail.getUnitPrice());
    }

    @Override
    public boolean delete(String stockId) throws SQLException {
        return CrudUtil.execute("delete from stock_detail where StockID = ?",stockId);
    }

    @Override
    public boolean update(StockDetail stockDetail) throws SQLException {
        return CrudUtil.execute("update stock_detail set supply_qty = ?,unit_price = ? where StockID = ? ",stockDetail.getQuantity()
                ,stockDetail.getUnitPrice(),stockDetail.getStockId());
    }

    @Override
    public boolean deleteDetailSup(String id) throws SQLException {
        return CrudUtil.execute("delete from stock_detail where SupplierID = ?",id);
    }

}
