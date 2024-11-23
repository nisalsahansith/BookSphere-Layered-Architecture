package com.project.booksphere.model;

import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.ItemDto;
import com.project.booksphere.dto.OrderDetailDto;
import com.project.booksphere.dto.tm.ItemTm;
import com.project.booksphere.dto.tm.ViewItemTM;
import com.project.booksphere.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {

    private final ItemDetailModel itemDetailModel = new ItemDetailModel();
    private final StockModel stockModel = new StockModel();
    private final StockDetailModel stockDetailModel = new StockDetailModel();

    public ArrayList<ItemDto> getAllItem() throws SQLException {
        ResultSet rst = CrudUtil.execute("select i.*,SUM(id.QtyOnHand) AS TotalQtyOnHand from item i LEFT JOIN item_details id ON i.ItemId = id.ItemId GROUP BY i.ItemId ");
        ArrayList<ItemDto> itemDtos = new ArrayList<>();
        while (rst.next()) {
            ItemDto itemDto = new ItemDto(
            rst.getString(1),
            rst.getString(2),
            rst.getString(3),
            rst.getString(4) != null ? Integer.parseInt(rst.getString(4)) : 0
          //  Integer.parseInt(rst.getString(4))
            );
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }

    public String nextItemId() throws SQLException {
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

    public boolean saveItem(ItemDto itemDto) throws SQLException {
        return CrudUtil.execute("insert into item values(?,?,?)",
                    itemDto.getId(),
                    itemDto.getDescription(),
                    itemDto.getISBN());

    }

    public boolean updateItem(ItemDto itemDto) throws SQLException {
        return CrudUtil.execute("update item set Description =? ,ISBN = ? where ItemID =?",
                    itemDto.getDescription(),
                    itemDto.getISBN(),
                    itemDto.getId()
                );
    }

    public String searchItem(String id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Description from item where ItemID = ?",id);
        String itemId = "No Items";
        if (resultSet.next()){
            itemId = resultSet.getString(1);
        }
        return itemId;
    }


    public double getSellPrice(String itemId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select id.sell_price from item i join item_details id on i.ItemID = id.ItemID  where id.ItemID = ?",itemId);
        double price = 0;
        if (rst.next()){
            price = Double.parseDouble(rst.getString(1));
        }
        return price;
    }

    public boolean deleteItem(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            String stockId = itemDetailModel.getStock(id);
            boolean isStockDetailDelete = stockDetailModel.deleteDetails(stockId);
            boolean isOrderDetailDelete = itemDetailModel.deleteItems(id);
            if (isStockDetailDelete | isOrderDetailDelete ){
                boolean isStockDelete = stockModel.deleteStock(stockId);
                boolean isItemDelete = CrudUtil.execute("delete from item where ItemID = ?",id);
                if (isStockDelete | isItemDelete){
                    connection.commit();
                    return true;
                }
            }else {
                CrudUtil.execute("delete from item where ItemID = ?",id);
                connection.commit();
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

    public boolean deleteOrders(ArrayList<OrderDetailDto> items) throws SQLException {
        boolean isItemDetailUpdated = true;
        for (int i = 0; i < items.size(); i++) {
            String id = items.get(i).getItemId();
            double qty = items.get(i).getQty();
            isItemDetailUpdated = itemDetailModel.increaseQTY(id,qty);
            if (!isItemDetailUpdated){
                break;
            }
        }
        return isItemDetailUpdated;
    }

    public ArrayList<ViewItemTM> getAllItems() throws SQLException {
        ResultSet rst = CrudUtil.execute("select i.*,id.StockID,id.QtyOnHand,id.sell_price,s.Name from item i join item_details id on i.ItemID = id.ItemID join stock s on id.StockID = s.StockID");
        ArrayList<ViewItemTM> viewItemTMS = new ArrayList<>();
        while (rst.next()){
            ViewItemTM viewItemTM = new ViewItemTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(6),
                    rst.getInt(5),
                    rst.getString(4),
                    rst.getString(7)
            );
            viewItemTMS.add(viewItemTM);
        }
        return viewItemTMS;
    }

    public ArrayList<ViewItemTM> searchFromID(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select i.*,id.StockID,id.QtyOnHand,id.sell_price,s.Name from item i join item_details id on i.ItemID = id.ItemID join stock s on id.StockID = s.StockID where i.ItemID = ?",id);
        ArrayList<ViewItemTM> viewItemTMS = new ArrayList<>();
        while (rst.next()){
            ViewItemTM viewItemTM = new ViewItemTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(6),
                    rst.getInt(5),
                    rst.getString(4),
                    rst.getString(7)
            );
            viewItemTMS.add(viewItemTM);
        }
        return viewItemTMS;
    }

    public ArrayList<ItemTm> search(String search) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from item i join item_details id on i.ItemID = id.ItemID where i.ItemID = ?",search);
        ArrayList<ItemTm> itemTMS = new ArrayList<>();
        while (rst.next()){
            ItemTm itemTm = new ItemTm(
                    rst.getString("ItemID"),
                    rst.getString("Description"),
                    rst.getString("ISBN"),
                    rst.getInt("QtyOnHand")
            );
            itemTMS.add(itemTm);
        }
        return itemTMS;
    }

    public ArrayList<ItemTm> searchByName(String search) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from item i join item_details id on i.ItemID = id.ItemID where i.Description = ?",search);
        ArrayList<ItemTm> itemTMS = new ArrayList<>();
        while (rst.next()){
            ItemTm itemTm = new ItemTm(
                    rst.getString("ItemID"),
                    rst.getString("Description"),
                    rst.getString("ISBN"),
                    rst.getInt("QtyOnHand")
            );
            itemTMS.add(itemTm);
        }
        return itemTMS;
    }

    public String searchBy(String search) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select ItemID from item where Description = ?",search);
        String itemId = "No Items";
        if (resultSet.next()){
            itemId = resultSet.getString(1);
        }
        return itemId;
    }

    public ArrayList<ViewItemTM> searchFromName(String search) throws SQLException {
        ResultSet rst = CrudUtil.execute("select i.*,id.StockID,id.QtyOnHand,id.sell_price,s.Name from item i join item_details id on i.ItemID = id.ItemID join stock s on id.StockID = s.StockID where i.Description = ?",search);
        ArrayList<ViewItemTM> viewItemTMS = new ArrayList<>();
        while (rst.next()){
            ViewItemTM viewItemTM = new ViewItemTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(6),
                    rst.getInt(5),
                    rst.getString(4),
                    rst.getString(7)
            );
            viewItemTMS.add(viewItemTM);
        }
        return viewItemTMS;
    }
}
