package com.project.booksphere.model;

import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.ItemDetailDto;
import com.project.booksphere.dto.StockDetailDto;
import com.project.booksphere.dto.StockDto;
import com.project.booksphere.dto.tm.ItemDetailTM;
import com.project.booksphere.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDetailModel {
    public ArrayList<ItemDetailTM> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from item_details");
        ArrayList<ItemDetailTM> itemDetailTMS = new ArrayList<>();
        while (rst.next()){
            ItemDetailTM itemDetailTM = new ItemDetailTM(
                    rst.getString(1),
                    rst.getString(2),
                    Integer.parseInt(rst.getString(3)),
                    (int) Double.parseDouble(rst.getString(4))

            );
             itemDetailTMS.add(itemDetailTM);
        }
        return itemDetailTMS;
    }


    public boolean saveItemDetail(ItemDetailDto itemDetailDto, StockDto stockDto, StockDetailDto stockDetailDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isStockSave = CrudUtil.execute("insert into stock values(?,?,?)",  stockDto.getStockId(),stockDto.getStockName(),stockDto.getUserID());
            if(isStockSave){
                boolean isStockDetailSaved = CrudUtil.execute("insert into stock_detail values(?,?,?,?) ",
                        stockDetailDto.getSupplyId(),
                        stockDetailDto.getStockId(),
                        stockDetailDto.getQuantity(),
                        stockDetailDto.getUnitPrice());
                if(isStockDetailSaved){
                    int qty = itemDetailDto.getQty();
                    ResultSet rst = CrudUtil.execute("select QtyOnHand from item_details where ItemId = ?",itemDetailDto.getItemId());
                    int oldQty = 0;
                    if (rst.next()){
                        oldQty = rst.getInt(1);
                    }
                    if (oldQty != 0){
                        qty += oldQty;
                    }
                    boolean isItemDetailSaved = CrudUtil.execute("insert into item_details values(?,?,?,?)",
                            itemDetailDto.getItemId(),
                            itemDetailDto.getStockId(),
                            qty,
                            itemDetailDto.getSellPrice());
                    if (isItemDetailSaved){
                        connection.commit();
                        return true;
                    }
                }
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

    public int getQty(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT SUM(QtyOnHand) FROM item_details WHERE ItemID = ?",id);
        int qty = 0;
        if (rst.next()){
            qty = rst.getInt(1);
        }
        return qty;
    }

    public boolean deleteItems(String id) throws SQLException {
        return CrudUtil.execute("delete from item_details where ItemID =?",id);
    }

    public String getStock(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select StockID from item_details where ItemID = ?",id);
        String stockId = null;
        if (rst.next()){
            stockId = rst.getString(1);
        }
        return stockId;
    }


    public boolean increaseQTY(String id, double qty) throws SQLException {
        return CrudUtil.execute("update item_details set QtyOnHand = QtyOnHand+? where ItemId=?",qty,id);
    }

    public boolean update(String id, int qty, double sellPrice) throws SQLException {
        return CrudUtil.execute("update item_details set QtyOnHand = ?,sell_price = ? where StockID = ? ",qty,sellPrice,id);
    }
}
