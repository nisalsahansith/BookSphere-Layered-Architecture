package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;
import com.project.booksphere.dto.*;

import java.sql.SQLException;

public interface PurchaseBo extends SuperBo {

    public String searchItem(String id) throws SQLException;
    public double getSellPrice(String itemId) throws SQLException;
    public String searchItemDesc(String id) throws SQLException;
    public String searchByItemId(String search) throws SQLException;
    public boolean orderSaved(OrderDto orderDto, PromotionDetailDto promotionDetailDto, PaymentDto paymentDto) throws SQLException;
    public String nextOrderId() throws SQLException;
    public CustomerDto searchCustomer(String no) throws SQLException;
    public double getRate(double total) throws SQLException;
    public String getPromotionId(double discount) throws SQLException;
    public int getItemQty(ItemDetailDto itemDetailDto) throws SQLException;
}
