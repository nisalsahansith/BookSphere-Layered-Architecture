package com.project.booksphere.dao.custom.impl;

import com.project.booksphere.dao.custom.ItemDAO;
import com.project.booksphere.dto.ItemDto;
import com.project.booksphere.entity.Item;
import com.project.booksphere.tm.ItemTm;
import com.project.booksphere.tm.ViewItemTM;
import com.project.booksphere.dao.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    ItemDetailDAOImpl itemDetailDAO = new ItemDetailDAOImpl();
    StockDetailDAOImpl stockDetailDAO = new StockDetailDAOImpl();
    StockDAOImpl stockDAO = new StockDAOImpl();

    @Override
    public ArrayList<Item> getAll() throws SQLException {
//        ResultSet rst = CrudUtil.execute("select i.*,SUM(id.QtyOnHand) AS TotalQtyOnHand from item i LEFT JOIN item_details id ON i.ItemId = id.ItemId GROUP BY i.ItemId ");
//        ArrayList<Item> items = new ArrayList<>();
//        while (rst.next()) {
//            Item item = new Item(
//                    rst.getString(1),  //tm ekata tawa ekk demma passe ain karanna oona
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getString(4) != null ? Integer.parseInt(rst.getString(4)) : 0
//                    //  Integer.parseInt(rst.getString(4))
//            );
//            items.add(item);
//        }
//        return items;
        return null;
    }

    @Override
    public boolean save(Item item) throws SQLException {
        return CrudUtil.execute("insert into item values(?,?,?)",
                item.getId(),
                item.getDescription(),
                item.getISBN());

    }

    @Override
    public String nextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT ItemID FROM item ORDER BY ItemID DESC LIMIT 1");
        if (rst.next()) {
            String itemId = rst.getString(1);
            String subItemId = itemId.substring(1);
            int lastIndex = Integer.parseInt(subItemId);
            int nextItemId = lastIndex + 1;
            String newItemId = String.format("I%03d", nextItemId);
            return newItemId;
        }
        return "I001";
    }

    @Override
    public ArrayList<Item> search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(false);
//            String stockId = itemDetailDAO.getStock(id);
//            boolean isStockDetailDelete = stockDetailDAO.delete(stockId);
//            boolean isOrderDetailDelete = itemDetailDAO.delete(id);
//            if (isStockDetailDelete | isOrderDetailDelete ){
//                boolean isStockDelete = stockDAO.delete(stockId);
//                boolean isItemDelete = CrudUtil.execute("delete from item where ItemID = ?",id);
//                if (isStockDelete | isItemDelete){
//                    connection.commit();
//                    return true;
//                }
//            }else {
//                CrudUtil.execute("delete from item where ItemID = ?",id);
//                connection.commit();
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

    public boolean update(Item item) throws SQLException {
        return CrudUtil.execute("update item set Description =? ,ISBN = ? where ItemID =?",
                item.getDescription(),
                item.getISBN(),
                item.getId()
        );
    }

//    @Override
//    public ArrayList<ItemTm> searching(String search) throws SQLException {
//        ResultSet rst = CrudUtil.execute("select * from item i join item_details id on i.ItemID = id.ItemID where i.ItemID = ?",search);
//        ArrayList<ItemTm> itemTMS = new ArrayList<>();
//        while (rst.next()){
//            ItemTm itemTm = new ItemTm(
//                    rst.getString("ItemID"),
//                    rst.getString("Description"),
//                    rst.getString("ISBN"),
//                    rst.getInt("QtyOnHand")
//            );
//            itemTMS.add(itemTm);
//        }
//        return itemTMS;
//    }

//    @Override
//    public ArrayList<ItemTm> searchByName(String search) throws SQLException {
//        ResultSet rst = CrudUtil.execute("select * from item i join item_details id on i.ItemID = id.ItemID where i.Description = ?",search);
//        ArrayList<ItemTm> itemTMS = new ArrayList<>();
//        while (rst.next()){
//            ItemTm itemTm = new ItemTm(
//                    rst.getString("ItemID"),
//                    rst.getString("Description"),
//                    rst.getString("ISBN"),
//                    rst.getInt("QtyOnHand")
//            );
//            itemTMS.add(itemTm);
//        }
//        return itemTMS;
//    }

    @Override
    public String searchItem(String id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Description from item where ItemID = ?",id);
        String itemId = "No Items";
        if (resultSet.next()){
            itemId = resultSet.getString(1);
        }
        return itemId;
    }

//    @Override
//    public double getSellPrice(String itemId) throws SQLException {
//        ResultSet rst = CrudUtil.execute("select id.sell_price from item i join item_details id on i.ItemID = id.ItemID  where id.ItemID = ?",itemId);
//        double price = 0;
//        if (rst.next()){
//            price = Double.parseDouble(rst.getString(1));
//        }
//        return price;
//    }

    @Override
    public String searchBy(String search) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select ItemID from item where Description = ?",search);
        String itemId = "No Items";
        if (resultSet.next()){
            itemId = resultSet.getString(1);
        }
        return itemId;
    }

//    @Override
//    public ArrayList<ViewItemTM> searchFromID(String id) throws SQLException {
//        ResultSet rst = CrudUtil.execute("select i.*,id.StockID,id.QtyOnHand,id.sell_price,s.Name from item i join item_details id on i.ItemID = id.ItemID join stock s on id.StockID = s.StockID where i.ItemID = ?",id);
//        ArrayList<ViewItemTM> viewItemTMS = new ArrayList<>();
//        while (rst.next()){
//            ViewItemTM viewItemTM = new ViewItemTM(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getDouble(6),
//                    rst.getInt(5),
//                    rst.getString(4),
//                    rst.getString(7)
//            );
//            viewItemTMS.add(viewItemTM);
//        }
//        return viewItemTMS;
//    }

//    @Override
//    public ArrayList<ViewItemTM> searchFromName(String search) throws SQLException {
//        ResultSet rst = CrudUtil.execute("select i.*,id.StockID,id.QtyOnHand,id.sell_price,s.Name from item i join item_details id on i.ItemID = id.ItemID join stock s on id.StockID = s.StockID where i.Description = ?",search);
//        ArrayList<ViewItemTM> viewItemTMS = new ArrayList<>();
//        while (rst.next()){
//            ViewItemTM viewItemTM = new ViewItemTM(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getDouble(6),
//                    rst.getInt(5),
//                    rst.getString(4),
//                    rst.getString(7)
//            );
//            viewItemTMS.add(viewItemTM);
//        }
//        return viewItemTMS;
//    }

//    @Override
//    public ArrayList<ViewItemTM> getAllItems() throws SQLException {
//        ResultSet rst = CrudUtil.execute("select i.*,id.StockID,id.QtyOnHand,id.sell_price,s.Name from item i join item_details id on i.ItemID = id.ItemID join stock s on id.StockID = s.StockID");
//        ArrayList<ViewItemTM> viewItemTMS = new ArrayList<>();
//        while (rst.next()){
//            ViewItemTM viewItemTM = new ViewItemTM(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getDouble(6),
//                    rst.getInt(5),
//                    rst.getString(4),
//                    rst.getString(7)
//            );
//            viewItemTMS.add(viewItemTM);
//        }
//        return viewItemTMS;
//    }

}
