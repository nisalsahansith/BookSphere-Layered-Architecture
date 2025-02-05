package com.project.booksphere.dao.custom.impl;

import com.project.booksphere.dao.custom.StockDAO;
import com.project.booksphere.dto.StockDto;
import com.project.booksphere.entity.Stock;
import com.project.booksphere.tm.ViewStockTM;
import com.project.booksphere.dao.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockDAOImpl implements StockDAO {

    StockDetailDAOImpl stockDetailDAO = new StockDetailDAOImpl();

//    @Override
//    public ArrayList<ViewStockTM> getAlls() throws SQLException {
//        ResultSet rst = CrudUtil.execute("select s.*,id.ItemID,id.QtyOnHand,id.sell_price,sd.SupplierID,sd.unit_price,sup.Name from stock s " +
//                "join item_details id on s.StockID = id.StockID" +
//                " join stock_detail sd on s.StockID = sd.StockID " +
//                "join supplier sup on sd.SupplierID = sup.SupplierID ");
//        ArrayList<ViewStockTM> viewStockTMS = new ArrayList<>();
//        while (rst.next()){
//            ViewStockTM viewStockTM = new ViewStockTM(
//                    rst.getString("StockID"),
//                    rst.getString("Name"),
//                    rst.getString("ItemID"),
//                    rst.getInt("QtyOnHand"),
//                    rst.getDouble("sell_price"),
//                    rst.getDouble("unit_price"),
//                    rst.getString("SupplierID"),
//                    rst.getString(9),
//                    rst.getString(3)
//            );
//            viewStockTMS.add(viewStockTM);
//        }
//        return viewStockTMS;
//    }

    @Override
    public boolean save(Stock stock) throws SQLException {
        return CrudUtil.execute("insert into stock values(?,?,?)",  stock.getStockId(),stock.getStockName(),stock.getUserID());
    }

    @Override
    public boolean update(Stock stock) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Stock> getAll() throws SQLException {
        return null;
    }

    @Override
    public String nextId() throws SQLException {
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

    @Override
    public ArrayList<Stock> search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String stockId) throws SQLException {
        return CrudUtil.execute("delete from stock where StockID = ?",stockId);
    }

//    @Override
//    public ArrayList<ViewStockTM> searchID(String id) throws SQLException {
//        ResultSet rst = CrudUtil.execute("select s.*,id.ItemID,id.QtyOnHand,id.sell_price,sd.SupplierID,sd.unit_price,sup.Name from stock s " +
//                "join item_details id on s.StockID = id.StockID join stock_detail sd on s.StockID = sd.StockID " +
//                "join supplier sup on sd.SupplierID = sup.SupplierID where s.StockID = ?",id);
//        ArrayList<ViewStockTM> viewStockTMS = new ArrayList<>();
//        while (rst.next()){
//            ViewStockTM viewStockTM = new ViewStockTM(
//                    rst.getString("StockID"),
//                    rst.getString("Name"),
//                    rst.getString("ItemID"),
//                    rst.getInt("QtyOnHand"),
//                    rst.getDouble("sell_price"),
//                    rst.getDouble("unit_price"),
//                    rst.getString("SupplierID"),
//                    rst.getString(9),
//                    rst.getString(3)
//            );
//            viewStockTMS.add(viewStockTM);
//        }
//        return viewStockTMS;
//    }

//    @Override
//    public boolean updateStock(String id, String name, int qty, double sellPrice, double buyPrice) throws SQLException {
//        ItemDetailDAOImpl itemDetailDAO = new ItemDetailDAOImpl();
//
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(false);
//            boolean isUpdateStockDetail = stockDetailDAO.update(new StockDetailDto(id,"",buyPrice,qty));
//            if (isUpdateStockDetail){
//                boolean isUpdateItemDetail = itemDetailDAO.update(new ItemDetailDto("",id,sellPrice,qty));
//                if (isUpdateItemDetail){
//                    boolean isUpdate = CrudUtil.execute("update stock set Name = ? where StockID = ? ",name,id);
//                    if (isUpdate){
//                        connection.setAutoCommit(true);
//                        return true;
//                    }
//                }
//            }
//            connection.rollback();
//            return false;
//        } catch (SQLException e) {
//            connection.rollback();
//            return false;
//        }finally {
//            connection.setAutoCommit(true);
//        }
//    }


}
