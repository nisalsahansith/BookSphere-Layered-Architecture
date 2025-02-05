package com.project.booksphere.dao.custom.impl;

import com.project.booksphere.dao.CrudUtil;
import com.project.booksphere.dao.custom.QueryDAO;
import com.project.booksphere.entity.Custom;
import com.project.booksphere.entity.Item;
import com.project.booksphere.tm.ViewStockTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<Custom> searching(String search) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from item i join item_details id on i.ItemID = id.ItemID where i.ItemID = ?",search);
        ArrayList<Custom> customs = new ArrayList<>();
        while (rst.next()){
            Custom custom = new Custom(
                    rst.getString("ItemID"),
                    rst.getString("Description"),
                    rst.getString("ISBN"),
                    rst.getInt("QtyOnHand")
            );
            customs.add(custom);
        }
        return customs;
    }

    @Override
    public ArrayList<Custom> searchByName(String search) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from item i join item_details id on i.ItemID = id.ItemID where i.Description = ?",search);
        ArrayList<Custom> customs = new ArrayList<>();
        while (rst.next()){
            Custom custom = new Custom(
                    rst.getString("ItemID"),
                    rst.getString("Description"),
                    rst.getString("ISBN"),
                    rst.getInt("QtyOnHand")
            );
            customs.add(custom);
        }
        return customs;
    }

    @Override
    public double getSellPrice(String itemId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select id.sell_price from item i join item_details id on i.ItemID = id.ItemID  where id.ItemID = ?",itemId);
        double price = 0;
        if (rst.next()){
            price = Double.parseDouble(rst.getString(1));
        }
        return price;
    }

    @Override
    public ArrayList<Custom> searchFromName(String search) throws SQLException {
        ResultSet rst = CrudUtil.execute("select i.*,id.StockID,id.QtyOnHand,id.sell_price,s.Name from item i join item_details id on i.ItemID = id.ItemID join stock s on id.StockID = s.StockID where i.Description = ?",search);
        ArrayList<Custom> customs = new ArrayList<>();
        while (rst.next()){
            Custom custom = new Custom(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(6),
                    rst.getInt(5),
                    rst.getString(4),
                    rst.getString(7)
            );
            customs.add(custom);
        }
        return customs;
    }

    @Override
    public ArrayList<Custom> getAllItems() throws SQLException {
        ResultSet rst = CrudUtil.execute("select i.*,id.StockID,id.QtyOnHand,id.sell_price,s.Name from item i join item_details id on i.ItemID = id.ItemID join stock s on id.StockID = s.StockID");
        ArrayList<Custom> customs = new ArrayList<>();
        while (rst.next()){
            Custom custom = new Custom(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(6),
                    rst.getInt(5),
                    rst.getString(4),
                    rst.getString(7)
            );
            customs.add(custom);
        }
        return customs;
    }

    @Override
    public ArrayList<Custom> searchFromID(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select i.*,id.StockID,id.QtyOnHand,id.sell_price,s.Name from item i join item_details id on i.ItemID = id.ItemID join stock s on id.StockID = s.StockID where i.ItemID = ?",id);
        ArrayList<Custom> customs = new ArrayList<>();
        while (rst.next()){
            Custom custom = new Custom(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(6),
                    rst.getInt(5),
                    rst.getString(4),
                    rst.getString(7)
            );
            customs.add(custom);
        }
        return customs;
    }

    @Override
    public ArrayList<Custom> searchFromOrderID(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select O.OrderID,O.OrderDate,O.NetPrice,O.customer_id,O.UserID,OD.OrderQty,I.Description,ID.StockID from orders O " +
                "join orderdetails OD on O.OrderID = OD.OrderID " +
                "join item I on OD.ItemID = I.ItemID " +
                "join item_details ID on  I.ItemID = ID.ItemID where O.OrderID = ?",id );
        ArrayList<Custom> customs = new ArrayList<>();
        Custom custom = new Custom();
        while (rst.next()) {
            custom.setOrderId(rst.getString(1));
            custom.setCustomerId(rst.getString(4));
            custom.setUserId(rst.getString(5));
            custom.setNetPrice(rst.getDouble(3));
            custom.setOrderDate(rst.getDate(2));
            custom.setQtyOnHand(rst.getInt(6));
            custom.setItemDescription(rst.getString(7));
            custom.setStockId(rst.getString(8));
            customs.add(custom);
        }
        return customs;
    }

    @Override
    public ArrayList<Custom> getOrdersAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select OD.OrderID,O.OrderDate,O.NetPrice,O.customer_id,O.UserID,OD.OrderQty,I.Description,ID.StockID from orders O " +
                "join orderdetails OD on O.OrderID = OD.OrderID " +
                "join item I on OD.ItemID = I.ItemID " +
                "join item_details ID on  I.ItemID = ID.ItemID " );
        ArrayList<Custom> customs = new ArrayList<>();
        while (rst.next()){
            Custom custom = new Custom(
                    rst.getString(1),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(3),
                    rst.getDate(2),
                    rst.getInt(6),
                    rst.getString(7),
                    rst.getString(8)
            );
            customs.add(custom);
        }
        return customs;
    }

    @Override
    public ArrayList<Custom> getAlls() throws SQLException {
        ResultSet rst = CrudUtil.execute("select s.*,id.ItemID,id.QtyOnHand,id.sell_price,sd.SupplierID,sd.unit_price,sup.Name from stock s " +
                "join item_details id on s.StockID = id.StockID" +
                " join stock_detail sd on s.StockID = sd.StockID " +
                "join supplier sup on sd.SupplierID = sup.SupplierID ");
        ArrayList<Custom> customs = new ArrayList<>();
        while (rst.next()){
            Custom custom = new Custom(
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
            customs.add(custom);
        }
        return customs;
    }

    @Override
    public ArrayList<Custom> searchID(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select s.*,id.ItemID,id.QtyOnHand,id.sell_price,sd.SupplierID,sd.unit_price,sup.Name from stock s " +
                "join item_details id on s.StockID = id.StockID join stock_detail sd on s.StockID = sd.StockID " +
                "join supplier sup on sd.SupplierID = sup.SupplierID where s.StockID = ?",id);
        ArrayList<Custom> viewStockTMS = new ArrayList<>();
        while (rst.next()){
            Custom custom = new Custom(
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
            viewStockTMS.add(custom);
        }
        return viewStockTMS;
    }

    @Override
    public ArrayList<Custom> getAllItem() throws SQLException {
        ResultSet rst = CrudUtil.execute("select i.*,SUM(id.QtyOnHand) AS TotalQtyOnHand from item i LEFT JOIN item_details id ON i.ItemId = id.ItemId GROUP BY i.ItemId ");
        ArrayList<Custom> items = new ArrayList<>();
        while (rst.next()) {
            Custom custom = new Custom(
                    rst.getString(1),  //tm ekata tawa ekk demma passe ain karanna oona
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4) != null ? Integer.parseInt(rst.getString(4)) : 0
                    //  Integer.parseInt(rst.getString(4))
            );
            items.add(custom);
        }
        return items;
    }

}
