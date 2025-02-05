package com.project.booksphere.dao.custom.impl;

import com.project.booksphere.dao.custom.OrderDAO;
import com.project.booksphere.dto.OrderDto;
import com.project.booksphere.entity.Order;
import com.project.booksphere.tm.ViewOrderTM;
import com.project.booksphere.dao.CrudUtil;
import com.project.booksphere.util.SharedInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    private SharedInfo sharedInfo = SharedInfo.getInstance();
    OrderDetailDAOImpl orderDetailDAO = new OrderDetailDAOImpl();
    PromotionDetailDAOImpl promotionDetailDAO = new PromotionDetailDAOImpl();
    PaymentDAOImpl paymentDAO = new PaymentDAOImpl();
    ItemDetailDAOImpl itemDetailDAO = new ItemDetailDAOImpl();

    @Override
    public boolean nullOrders(String customerId) throws SQLException {
        return CrudUtil.execute("UPDATE orders " +
                "SET customer_id = NULL " +
                "WHERE OrderID = ?", customerId);
    }

//    @Override
//    public ArrayList<ViewOrderTM> searchFromOrderID(String id) throws SQLException {
//        ResultSet rst = CrudUtil.execute("select O.OrderID,O.OrderDate,O.NetPrice,O.customer_id,O.UserID,OD.OrderQty,I.Description,ID.StockID from orders O " +
//                "join orderdetails OD on O.OrderID = OD.OrderID " +
//                "join item I on OD.ItemID = I.ItemID " +
//                "join item_details ID on  I.ItemID = ID.ItemID where O.OrderID = ?",id );
//        ArrayList<ViewOrderTM> viewOrderTMS = new ArrayList<>();
//        ViewOrderTM viewOrderTM = new ViewOrderTM();
//        while (rst.next()) {
//            viewOrderTM.setOrderID(rst.getString(1));
//            viewOrderTM.setCustomerID(rst.getString(4));
//            viewOrderTM.setUserID(rst.getString(5));
//            viewOrderTM.setTotal(rst.getDouble(3));
//            viewOrderTM.setDate(rst.getDate(2));
//            viewOrderTM.setQty(rst.getInt(6));
//            viewOrderTM.setDesc(rst.getString(7));
//            viewOrderTM.setStockID(rst.getString(8));
//            viewOrderTMS.add(viewOrderTM);
//        }
//        return viewOrderTMS;
//    }

    @Override
    public boolean delete(String id) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(false);
//            ArrayList<OrderDetailDto> items = orderDetailDAO.search(id);
//            boolean itemUpdated = deleteOrders(items);
//            if (itemUpdated){
//                boolean isDeleteOrderDetail = orderDetailDAO.delete(id);
//                if (isDeleteOrderDetail){
//                    boolean isDeletePromotion = promotionDetailDAO.deleteOrderPromotion(id);
//                    boolean isDeletePayment = paymentDAO.delete(id);
//                    if (isDeletePromotion | isDeletePayment){
//                        boolean isOrderDelete = CrudUtil.execute("delete from orders where OrderID = ?",id);
//                        if (isOrderDelete){
//                            connection.commit();
//                            return true;
//                        }
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
        return false;
    }

//    @Override
//    public boolean deleteOrders(ArrayList<OrderDetailDto> items) throws SQLException {
//        boolean isItemDetailUpdated = true;
//        for (int i = 0; i < items.size(); i++) {
//            String id = items.get(i).getItemId();
//            double qty = items.get(i).getQty();
//            isItemDetailUpdated = itemDetailDAO.increaseQTY(id,qty);
//            if (!isItemDetailUpdated){
//                break;
//            }
//        }
//        return isItemDetailUpdated;
//    }

//    @Override
//    public ArrayList<ViewOrderTM> getOrdersAll() throws SQLException {
//        ResultSet rst = CrudUtil.execute("select OD.OrderID,O.OrderDate,O.NetPrice,O.customer_id,O.UserID,OD.OrderQty,I.Description,ID.StockID from orders O " +
//                "join orderdetails OD on O.OrderID = OD.OrderID " +
//                "join item I on OD.ItemID = I.ItemID " +
//                "join item_details ID on  I.ItemID = ID.ItemID " );
//        ArrayList<ViewOrderTM> viewOrderTMS = new ArrayList<>();
//        while (rst.next()){
//            ViewOrderTM viewOrderTM = new ViewOrderTM(
//                    rst.getString(1),
//                    rst.getString(4),
//                    rst.getString(5),
//                    rst.getDouble(3),
//                    rst.getDate(2),
//                    rst.getInt(6),
//                    rst.getString(7),
//                    rst.getString(8)
//            );
//            viewOrderTMS.add(viewOrderTM);
//        }
//        return viewOrderTMS;
//    }

//    @Override
//    public boolean orderSaved(OrderDto orderDto, PromotionDetailDto promotionDetailDto, PaymentDetailsDto paymentDetailsDto) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(false);
//            boolean isOrderSaved = save(orderDto);
//            String OrderId = orderDto.getOrderId();
//            sharedInfo.setOrderId(OrderId);
//            if (isOrderSaved){
//                boolean isOrderDetailSaved = orderDetailDAO.orderDetailSave(orderDto.getOrderDetailsDto());
//                if (isOrderDetailSaved){
//                    boolean isPromotionDetailSaved = promotionDetailDAO.save(promotionDetailDto);
//                    if (isPromotionDetailSaved){
//                        boolean isPaymentSuccess = paymentDAO.save(paymentDetailsDto);
//                        if (isPaymentSuccess){
//                            connection.commit();
//                            return true;
//                        }
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
    public boolean save(Order order) throws SQLException {
        if (order.getCustomerId().isEmpty()) {
            return orderSaveWithOutCustomer(order);
        }
        return CrudUtil.execute("insert into orders(OrderID, OrderDate, TotalPrice, NetPrice, customer_id, UserID) values(?, ?, ?, ?, ?, ?)",
                order.getOrderId(),
                order.getDate(),
                order.getTotalPrice(),
                order.getNetTotal(),
                order.getCustomerId(),
                order.getUserId());
    }

    @Override
    public boolean update(Order order) throws SQLException {
        return false;
    }

    @Override
    public boolean orderSaveWithOutCustomer(Order order) throws SQLException {
        return CrudUtil.execute("insert into orders(OrderID,OrderDate,TotalPrice,NetPrice,UserID) values(?,?,?,?,?)",
                order.getOrderId(),
                order.getDate(),
                order.getTotalPrice(),
                order.getNetTotal(),
                order.getUserId()
        );
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException {
        return null;
    }

    @Override
    public String nextId() throws SQLException {
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

    @Override
    public ArrayList<Order> search(String id) throws SQLException {
        return null;
    }

//    public boolean nullUser(String id) throws SQLException {
//        return CrudUtil.execute("update orders set UserID = ? where UserID = ?", "DELETED",id);
//    }
}
