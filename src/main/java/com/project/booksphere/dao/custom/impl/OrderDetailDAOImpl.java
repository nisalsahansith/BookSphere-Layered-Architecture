package com.project.booksphere.dao.custom.impl;

import com.project.booksphere.dao.custom.OrderDetailDAO;
import com.project.booksphere.dto.OrderDetailDto;
import com.project.booksphere.dao.CrudUtil;
import com.project.booksphere.entity.OrderDetail;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public ArrayList<OrderDetail> search(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from orderdetails where OrderID = ? ",id);
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        while (rst.next()){
            OrderDetail orderDetail = new OrderDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3)
            );
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from orderdetails where OrderID = ?",id);
    }

    @Override
    public boolean orderDetailSave(ArrayList<OrderDetail> orderDetails) throws SQLException {

        for (OrderDetail orderDetail : orderDetails){
            boolean isODERSaved = save(orderDetail);
            if (!isODERSaved){
                return false;
            }
            boolean isUpdate = reduceQty(orderDetail);
            if (!isUpdate){
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException {
        return null;
    }

    @Override
    public String nextId() throws SQLException {
        return "";
    }


    @Override
    public boolean save(OrderDetail orderDetail) throws SQLException {
        return CrudUtil.execute("insert into orderdetails values(?,?,?)",
                orderDetail.getOrderId(),
                orderDetail.getItemId(),
                orderDetail.getQty()
        );
    }

    @Override
    public boolean update(OrderDetail orderDetail) throws SQLException {
        return false;
    }

    @Override
    public boolean reduceQty(OrderDetail orderDetail) throws SQLException {
        String itemId = orderDetail.getItemId();
        double qtyToReduce = orderDetail.getQty();
        ResultSet rst = CrudUtil.execute("SELECT StockID, QtyOnHand FROM item_details WHERE ItemID = ? AND QtyOnHand > 0 ORDER BY StockID ASC", itemId);
        while (rst.next() && qtyToReduce > 0){
            String stockId = rst.getString("StockID");
            int qtyOnHand = rst.getInt("QtyOnHand");
            double qtyToDeduct = Math.min(qtyToReduce,qtyOnHand); //get from Stack Over flow ,this is used to return smaller of two values
            CrudUtil.execute(
                    "UPDATE item_details SET QtyOnHand = QtyOnHand - ? WHERE StockID = ?",
                    qtyToDeduct, stockId
            );
            qtyToReduce -= qtyToDeduct;
        }
        if (qtyToReduce > 0){
            new Alert(Alert.AlertType.INFORMATION,"Enter valid qty", ButtonType.OK).show();
        }
        return true;
//        return CrudUtil.execute("update item_details set QtyOnHand = QtyOnHand-? where ItemId=?",
//                orderDetailDto.getQty(),
//                orderDetailDto.getItemId()
//        );
    }
}
