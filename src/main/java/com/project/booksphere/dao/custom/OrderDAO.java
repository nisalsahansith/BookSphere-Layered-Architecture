package com.project.booksphere.dao.custom;

import com.project.booksphere.dao.CrudDAO;
import com.project.booksphere.dto.OrderDto;
import com.project.booksphere.entity.Order;
import com.project.booksphere.tm.ViewOrderTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO extends CrudDAO<Order> {
    boolean nullOrders(String customerId) throws SQLException;
//    ArrayList<ViewOrderTM> searchFromOrderID(String id) throws SQLException;
//    boolean deleteOrders(ArrayList<OrderDetailDto> items) throws SQLException;
//    ArrayList<ViewOrderTM> getOrdersAll() throws SQLException ;
//    boolean orderSaved(OrderDto orderDto, PromotionDetailDto promotionDetailDto, PaymentDetailsDto paymentDetailsDto) throws SQLException;
    boolean orderSaveWithOutCustomer(Order order) throws SQLException;
}
