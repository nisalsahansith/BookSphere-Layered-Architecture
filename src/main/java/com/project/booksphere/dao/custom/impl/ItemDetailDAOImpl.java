package com.project.booksphere.dao.custom.impl;

import com.project.booksphere.dao.custom.ItemDetailDAO;
import com.project.booksphere.dto.ItemDetailDto;
import com.project.booksphere.dao.CrudUtil;
import com.project.booksphere.entity.ItemDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDetailDAOImpl implements ItemDetailDAO {
//    StockDetailDAOImpl stockDetailDAO = new StockDetailDAOImpl();

//    @Override
//    public boolean saveItemDetails(ItemDetailDto itemDetailDto, StockDto stockDto, StockDetailDto stockDetailDto) throws SQLException {
//        StockDAOImpl stockDAO = new StockDAOImpl();
//
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(false);
//            boolean isStockSave = stockDAO.save(stockDto);
//            if(isStockSave){
//                boolean isStockDetailSaved = stockDetailDAO.save(stockDetailDto);
//                if(isStockDetailSaved){
//                    int qty = itemDetailDto.getQty();
//                    int oldQty = getQty(itemDetailDto);
////                    int  = 0;
////                    if (rst > qty){
////                        oldQty = rst.getInt(1);
////                    }
//                    if (oldQty != 0){
//                        qty += oldQty;
//                    }
//                    itemDetailDto.setQty(qty);
//                    boolean isItemDetailSaved = save(itemDetailDto);
//                    if (isItemDetailSaved){
//                        connection.commit();
//                        return true;
//                    }
//                }
//            }
//            connection.rollback();
//            return false;
//        }catch (SQLException e){
//            connection.rollback();
//            return false;
//        }finally {
//            connection.setAutoCommit(true);
//        }
//    }

    @Override
    public boolean save(ItemDetail itemDetail) throws SQLException {
        return CrudUtil.execute("insert into item_details values(?,?,?,?)",
                itemDetail.getItemId(),
                itemDetail.getStockId(),
                itemDetail.getQty(),
                itemDetail.getSellPrice());
    }

    @Override
    public int getQty(ItemDetail itemDetail) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select QtyOnHand from item_details where ItemId = ?",itemDetail.getItemId());
        if (resultSet.next()) {
            return resultSet.getInt("QtyOnHand");
        } else {
            return 0;
        }
    }

    @Override
    public ArrayList<ItemDetail> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from item_details");
        ArrayList<ItemDetail> itemDetails = new ArrayList<>();
        while (rst.next()){
            ItemDetail itemDetail = new ItemDetail(
                    rst.getString(1),
                    rst.getString(2),
                    Integer.parseInt(rst.getString(3)),
                    (int) Double.parseDouble(rst.getString(4))

            );
            itemDetails.add(itemDetail);
        }
        return itemDetails;
    }

    @Override
    public String nextId() throws SQLException {
        return "";
    }

    @Override
    public ArrayList<ItemDetail> search(String id) throws SQLException {
        return null;
    }

    @Override
    public String getStock(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select StockID from item_details where ItemID = ?",id);
        String stockId = null;
        if (rst.next()){
            stockId = rst.getString(1);
        }
        return stockId;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from item_details where ItemID =?",id);
    }

    @Override
    public boolean increaseQTY(String id, double qty) throws SQLException {
        return CrudUtil.execute("update item_details set QtyOnHand = QtyOnHand+? where ItemId=?",qty,id);
    }

    @Override
    public boolean update(ItemDetail itemDetail) throws SQLException {
        return CrudUtil.execute("update item_details set QtyOnHand = ?,sell_price = ? where StockID = ? ",itemDetail.getQty(),itemDetail.getSellPrice(),itemDetail.getItemId());//edi
    }

//    @Override
//    public int getQtySum(String id) throws SQLException {
//        ResultSet rst = CrudUtil.execute("SELECT SUM(QtyOnHand) FROM item_details WHERE ItemID = ?",id);
//        int qty = 0;
//        if (rst.next()){
//            qty = rst.getInt(1);
//        }
//        return qty;
//    }

}
