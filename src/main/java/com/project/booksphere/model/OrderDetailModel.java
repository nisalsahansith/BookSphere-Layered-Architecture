package com.project.booksphere.model;

import com.project.booksphere.dto.ItemDetailDto;
import com.project.booksphere.dto.OrderDetailDto;
import com.project.booksphere.util.CrudUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailModel {
    private ItemModel itemModel = new ItemModel();
    public boolean orderDetailSave(ArrayList<OrderDetailDto> orderDetailDtos) throws SQLException {
        for (OrderDetailDto orderDetailDto : orderDetailDtos){
            boolean isODERSaved = saveOrderDetail(orderDetailDto);
            if (!isODERSaved){
                return false;
            }
            boolean isUpdate = reduceQty(orderDetailDto);
            if (!isUpdate){
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetail(OrderDetailDto orderDetailDto) throws SQLException {
        return CrudUtil.execute("insert into orderdetails values(?,?,?)",
                    orderDetailDto.getOrderId(),
                    orderDetailDto.getItemId(),
                    orderDetailDto.getQty()
                );
    }

    public boolean reduceQty(OrderDetailDto orderDetailDto) throws SQLException {
        String itemId = orderDetailDto.getItemId();
        double qtyToReduce = orderDetailDto.getQty();
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

    public ArrayList<OrderDetailDto> getItems(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from orderdetails where OrderID = ? ",id);
        ArrayList<OrderDetailDto> orderDetailDtos = new ArrayList<>();
        while (rst.next()){
            OrderDetailDto orderDetailDto = new OrderDetailDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3)
            );
            orderDetailDtos.add(orderDetailDto);
        }
        return orderDetailDtos;
    }

    public boolean deleteOrder(String id) throws SQLException {
        return CrudUtil.execute("delete from orderdetails where OrderID = ?",id);
    }
}
