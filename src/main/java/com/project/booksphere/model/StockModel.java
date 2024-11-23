package com.project.booksphere.model;

import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.tm.ViewStockTM;
import com.project.booksphere.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockModel {
    private final StockDetailModel stockDetailModel = new StockDetailModel();
    private final ItemDetailModel itemDetailModel = new ItemDetailModel();
    public String getNextStockId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT StockID FROM stock ORDER BY StockID DESC LIMIT 1");
        if (rst.next()){
            String string = rst.getString(1);
            String subString = string.substring(1);
            int lastIndex = Integer.parseInt(subString);
            int nextIndex = lastIndex + 1;
            String newId = String.format("S%03d", nextIndex);
            return newId;
        }
        return "S001";
    }

    public boolean deleteStock(String stockId) throws SQLException {
        return CrudUtil.execute("delete from stock where StockID = ?",stockId);
    }

    public ArrayList<ViewStockTM> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select s.*,id.ItemID,id.QtyOnHand,id.sell_price,sd.SupplierID,sd.unit_price,sup.Name from stock s " +
                "join item_details id on s.StockID = id.StockID" +
                " join stock_detail sd on s.StockID = sd.StockID " +
                "join supplier sup on sd.SupplierID = sup.SupplierID ");
        ArrayList<ViewStockTM> viewStockTMS = new ArrayList<>();
        while (rst.next()){
            ViewStockTM viewStockTM = new ViewStockTM(
                    rst.getString("StockID"),
                    rst.getString("Name"),
                    rst.getString("ItemID"),
                    rst.getInt("QtyOnHand"),
                    rst.getDouble("sell_price"),
                    rst.getDouble("unit_price"),
                    rst.getString("SupplierID"),
                    rst.getString(9),
                    rst.getString(3)
            );
            viewStockTMS.add(viewStockTM);
        }
        return viewStockTMS;
    }

    public ArrayList<ViewStockTM> searchID(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select s.*,id.ItemID,id.QtyOnHand,id.sell_price,sd.SupplierID,sd.unit_price,sup.Name from stock s " +
                "join item_details id on s.StockID = id.StockID join stock_detail sd on s.StockID = sd.StockID " +
                "join supplier sup on sd.SupplierID = sup.SupplierID where s.StockID = ?",id);
        ArrayList<ViewStockTM> viewStockTMS = new ArrayList<>();
        while (rst.next()){
            ViewStockTM viewStockTM = new ViewStockTM(
                    rst.getString("StockID"),
                    rst.getString("Name"),
                    rst.getString("ItemID"),
                    rst.getInt("QtyOnHand"),
                    rst.getDouble("sell_price"),
                    rst.getDouble("unit_price"),
                    rst.getString("SupplierID"),
                    rst.getString(9),
                    rst.getString(3)
            );
            viewStockTMS.add(viewStockTM);
        }
        return viewStockTMS;
    }

    public boolean updateStock(String id, String name, int qty, double sellPrice, double buyPrice) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isUpdateStockDetail = stockDetailModel.update(id,qty,buyPrice);
            if (isUpdateStockDetail){
                boolean isUpdateItemDetail = itemDetailModel.update(id,qty,sellPrice);
                if (isUpdateItemDetail){
                    boolean isUpdate = CrudUtil.execute("update stock set Name = ? where StockID = ? ",name,id);
                    if (isUpdate){
                        connection.setAutoCommit(true);
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    public boolean deleteUser(String id) throws SQLException {
        return CrudUtil.execute("update stock set StockID = ? where StockID = ?","DELETED",id);
    }
}
