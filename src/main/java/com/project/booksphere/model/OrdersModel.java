package com.project.booksphere.model;

import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.*;
import com.project.booksphere.dto.tm.ViewOrderTM;
import com.project.booksphere.util.CrudUtil;
import com.project.booksphere.util.SharedInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersModel {
    public OrderDetailModel orderDetailModel = new OrderDetailModel();
    public PromotionDetailModel promotionDetailModel = new PromotionDetailModel();
    public PaymentModel paymentModel = new PaymentModel();
    public ItemDetailModel itemDetailModel = new ItemDetailModel();
    public ItemModel itemModel = new ItemModel();
    private SharedInfo sharedInfo = SharedInfo.getInstance();

    public String getNextID() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT OrderID FROM orders ORDER BY OrderID DESC LIMIT 1  ");
        if (rst.next()){
            String string = rst.getString(1);
            String subString = string.substring(3);
            int lastIndex = Integer.parseInt(subString);
            int nextIndex = lastIndex + 1;
            String newId = String.format("ORD%03d", nextIndex);
            return newId;
        }
        return "ORD001";
    }

    public boolean orderSaved(OrderDto orderDto, PromotionDetailDto promotionDetailDto, PaymentDetailsDto paymentDetailsDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isOrderSaved = saveOrder(connection,orderDto);
            String OrderId = orderDto.getOrderId();
            sharedInfo.setOrderId(OrderId);
            if (isOrderSaved){
                boolean isOrderDetailSaved = orderDetailModel.orderDetailSave(orderDto.getOrderDetailsDto());
                if (isOrderDetailSaved){
                    boolean isPromotionDetailSaved = promotionDetailModel.savePromotion(promotionDetailDto);
                    if (isPromotionDetailSaved){
                        boolean isPaymentSuccess = paymentModel.savePayment(paymentDetailsDto);
                        if (isPaymentSuccess){
                            connection.commit();
                            return true;
                        }
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

    private boolean saveOrder(Connection connection, OrderDto orderDto) throws SQLException {
        if (orderDto.getCustomerId().isEmpty()) {
            return orderSaveWithOutCustomer(orderDto);
        }
        return CrudUtil.execute("insert into orders(OrderID, OrderDate, TotalPrice, NetPrice, customer_id, UserID) values(?, ?, ?, ?, ?, ?)",
                orderDto.getOrderId(),
                orderDto.getDate(),
                orderDto.getTotalPrice(),
                orderDto.getNetTotal(),
                orderDto.getCustomerId(),
                orderDto.getUserId());
    }

    public boolean orderSaveWithOutCustomer(OrderDto orderDto) throws SQLException {
        return CrudUtil.execute("insert into orders(OrderID,OrderDate,TotalPrice,NetPrice,UserID) values(?,?,?,?,?)",
                orderDto.getOrderId(),
                orderDto.getDate(),
                orderDto.getTotalPrice(),
                orderDto.getNetTotal(),
                orderDto.getUserId()
            );
    }

    public ArrayList<ViewOrderTM> getOrdersAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select OD.OrderID,O.OrderDate,O.NetPrice,O.customer_id,O.UserID,OD.OrderQty,I.Description,ID.StockID from orders O " +
                "join orderdetails OD on O.OrderID = OD.OrderID " +
                "join item I on OD.ItemID = I.ItemID " +
                "join item_details ID on  I.ItemID = ID.ItemID " );
        ArrayList<ViewOrderTM> viewOrderTMS = new ArrayList<>();
        while (rst.next()){
            ViewOrderTM viewOrderTM = new ViewOrderTM(
                    rst.getString(1),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(3),
                    rst.getDate(2),
                    rst.getInt(6),
                    rst.getString(7),
                    rst.getString(8)
            );
            viewOrderTMS.add(viewOrderTM);
        }
        return viewOrderTMS;
    }

    public ArrayList<ViewOrderTM> searchFromOrderID(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select O.OrderID,O.OrderDate,O.NetPrice,O.customer_id,O.UserID,OD.OrderQty,I.Description,ID.StockID from orders O " +
                "join orderdetails OD on O.OrderID = OD.OrderID " +
                "join item I on OD.ItemID = I.ItemID " +
                "join item_details ID on  I.ItemID = ID.ItemID where O.OrderID = ?",id );
        ArrayList<ViewOrderTM> viewOrderTMS = new ArrayList<>();
        ViewOrderTM viewOrderTM = new ViewOrderTM();
        while (rst.next()) {
            viewOrderTM.setOrderID(rst.getString(1));
            viewOrderTM.setCustomerID(rst.getString(4));
            viewOrderTM.setUserID(rst.getString(5));
            viewOrderTM.setTotal(rst.getDouble(3));
            viewOrderTM.setDate(rst.getDate(2));
            viewOrderTM.setQty(rst.getInt(6));
            viewOrderTM.setDesc(rst.getString(7));
            viewOrderTM.setStockID(rst.getString(8));
            viewOrderTMS.add(viewOrderTM);
        }
        return viewOrderTMS;
    }

    public boolean deleteItem(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            ArrayList<OrderDetailDto> items = orderDetailModel.getItems(id);
            boolean itemUpdated = itemModel.deleteOrders(items);
            if (itemUpdated){
                boolean isDeleteOrderDetail = orderDetailModel.deleteOrder(id);
                if (isDeleteOrderDetail){
                    boolean isDeletePromotion = promotionDetailModel.deleteOrderPromotion(id);
                    boolean isDeletePayment = paymentModel.deletePayment(id);
                    if (isDeletePromotion | isDeletePayment){
                        boolean isOrderDelete = CrudUtil.execute("delete from orders where OrderID = ?",id);
                        if (isOrderDelete){
                            connection.commit();
                            return true;
                        }
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

    public boolean nullOrders(String customerId) throws SQLException {
        return CrudUtil.execute("UPDATE orders " +
                "SET customer_id = NULL " +
                "WHERE OrderID = ?", customerId);
    }

    public boolean nullUser(String id) throws SQLException {
        return CrudUtil.execute("update orders set UserID = ? where UserID = ?", "DELETED",id);
    }
}
