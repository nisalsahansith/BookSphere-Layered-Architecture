package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.PurchaseBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.*;
import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.*;
import com.project.booksphere.entity.*;
import com.project.booksphere.util.PaymentInfo;
import com.project.booksphere.util.SharedInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseBOImpl implements PurchaseBo {
    private final PaymentInfo paymentInfo = PaymentInfo.getInstance();
    SharedInfo sharedInfo = SharedInfo.getInstance();

    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);
    private final PromotionDAO promotionDAO = (PromotionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PROMOTION);
    private final ItemDetailDAO itemDetailDAO = (ItemDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ITEM_DETAIL);

    private final PromotionDetailDAO promotionDetailDAO = (PromotionDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PROMOTION_DETAIL);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER_DETAIL);
    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY_DAO);

    @Override
    public String searchItem(String id) throws SQLException {
        return itemDAO.searchItem(id);
    }

    @Override
    public double getSellPrice(String itemId) throws SQLException {
        return queryDAO.getSellPrice(itemId);
    }

    @Override
    public String searchItemDesc(String id) throws SQLException {
        return itemDAO.searchItem(id);
    }

    @Override
    public String searchByItemId(String search) throws SQLException {
        return itemDAO.searchBy(search);
    }

    @Override
    public boolean orderSaved(OrderDto orderDto, PromotionDetailDto promotionDetailDto, PaymentDto paymentDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            Order order = new Order(orderDto.getOrderId(),orderDto.getCustomerId(),orderDto.getTotalPrice(),orderDto.getNetTotal(),orderDto.getDate(),orderDto.getUserId());
            boolean isOrderSaved = orderDAO.save(order);
            String OrderId = orderDto.getOrderId();
            sharedInfo.setOrderId(OrderId);
            if (isOrderSaved){
                ArrayList<OrderDetailDto> orderDetails = orderDto.getOrderDetailsDto();
                ArrayList<OrderDetail> orderDetails1 = new ArrayList<>();
                for (OrderDetailDto orderDetailDto :orderDetails){
                    OrderDetail orderDetail = new OrderDetail(
                            orderDetailDto.getOrderId(),
                            orderDetailDto.getItemId(),
                            orderDetailDto.getQty()
                    );
                    orderDetails1.add(orderDetail);
                }
                boolean isOrderDetailSaved = orderDetailDAO.orderDetailSave(orderDetails1);
                if (isOrderDetailSaved){
                    PromotionDetail promotionDetail = new PromotionDetail(promotionDetailDto.getPromotionId(),promotionDetailDto.getOrderId(),promotionDetailDto.getDiscountPrice());
                    boolean isPromotionDetailSaved = promotionDetailDAO.save(promotionDetail);
                    if (isPromotionDetailSaved){
                        Payment payment = new Payment(paymentDto.getPaymentId(),paymentDto.getOrderId(),paymentDto.getPaymentMethod(),paymentDto.getTotal(),paymentDto.getDate());
                        boolean isPaymentSuccess = paymentDAO.save(payment);
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

    @Override
    public String nextOrderId() throws SQLException {
        return orderDAO.nextId();
    }

    @Override
    public CustomerDto searchCustomer(String no) throws SQLException {
        Customer customer = customerDAO.searchCustomer(no);
        CustomerDto customerDto = new CustomerDto(customer.getId(),customer.getName(),customer.getPhone(),customer.getEmail(),"");
        return customerDto;
    }

    @Override
    public double getRate(double total) throws SQLException {
        return promotionDAO.getRate(total);
    }

    @Override
    public String getPromotionId(double discount) throws SQLException {
        return promotionDAO.getId(discount);
    }

    @Override
    public int getItemQty(ItemDetailDto itemDetailDto) throws SQLException {
        ItemDetail itemDetail = new ItemDetail(itemDetailDto.getItemId(),itemDetailDto.getStockId(),itemDetailDto.getSellPrice(),itemDetailDto.getQty());
        return itemDetailDAO.getQty(itemDetail);
    }
}
