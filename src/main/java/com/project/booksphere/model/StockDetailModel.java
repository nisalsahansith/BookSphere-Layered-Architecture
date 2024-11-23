package com.project.booksphere.model;

import com.project.booksphere.util.CrudUtil;

import java.sql.SQLException;

public class StockDetailModel {
    public boolean deleteDetails(String stockId) throws SQLException {
        return CrudUtil.execute("delete from stock_detail where StockID = ?",stockId);
    }

    public boolean update(String id, int qty, double sellPrice) throws SQLException {
        return CrudUtil.execute("update stock_detail set supply_qty = ?,unit_price = ? where StockID = ? ",qty,sellPrice,id);
    }

    public boolean deleteDetailSup(String id) throws SQLException {
        return CrudUtil.execute("delete from stock_detail where SupplierID = ?",id);
    }
}
