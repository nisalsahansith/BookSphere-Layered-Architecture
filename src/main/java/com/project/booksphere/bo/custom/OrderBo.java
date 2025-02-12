package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;
import com.project.booksphere.dto.CustomDto;
import com.project.booksphere.dto.OrderDetailDto;
import com.project.booksphere.tm.ViewOrderTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ViewOrderBo extends SuperBo {
    ArrayList<CustomDto> searchFromOrderID(String id) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean deleteOrders(ArrayList<OrderDetailDto> items) throws SQLException;
    ArrayList<CustomDto> getOrdersAll() throws SQLException;
}
