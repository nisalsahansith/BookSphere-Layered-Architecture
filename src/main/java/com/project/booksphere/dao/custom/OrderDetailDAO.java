package com.project.booksphere.dao.custom;

import com.project.booksphere.dao.CrudDAO;
import com.project.booksphere.dto.OrderDetailDto;
import com.project.booksphere.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {
//    ArrayList<OrderDetailDto> getItems(String id) throws SQLException;
    boolean orderDetailSave(ArrayList<OrderDetail> orderDetails) throws SQLException;
    boolean reduceQty(OrderDetail orderDetail) throws SQLException;
}
