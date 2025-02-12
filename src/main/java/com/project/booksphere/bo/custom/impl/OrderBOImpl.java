package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.ViewOrderBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.*;
import com.project.booksphere.db.DBConnection;
import com.project.booksphere.dto.CustomDto;
import com.project.booksphere.dto.OrderDetailDto;
import com.project.booksphere.entity.Custom;
import com.project.booksphere.entity.OrderDetail;
import com.project.booksphere.tm.ViewOrderTM;
import com.project.booksphere.dao.CrudUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewOrderBOImpl implements ViewOrderBo {
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER_DETAIL);
    private final PromotionDetailDAO promotionDetailDAO = (PromotionDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PROMOTION_DETAIL);
    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
    private final ItemDetailDAO itemDetailDAO = (ItemDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ITEM_DETAIL);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY_DAO);

    @Override
    public ArrayList<CustomDto> searchFromOrderID(String id) throws SQLException {
        ArrayList<Custom> customs = queryDAO.searchFromOrderID(id);
        ArrayList<CustomDto> customDtos = new ArrayList<>();
        for (Custom custom: customs){
            CustomDto customDto = new CustomDto(
                    custom.getOrderId(),
                    custom.getCustomerId(),
                    custom.getUserId(),
                    custom.getNetPrice(),
                    custom.getOrderDate(),
                    custom.getOrderQty(),
                    custom.getItemDescription(),
                    custom.getStockId()
            );
            customDtos.add(customDto);
        }
        return customDtos;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            ArrayList<OrderDetail> items = orderDetailDAO.search(id);
            ArrayList<OrderDetailDto> orders = new ArrayList<>();
            for (OrderDetail orderDetail:items){
                OrderDetailDto orderDetailDto = new OrderDetailDto(
                        orderDetail.getOrderId(),orderDetail.getItemId(),orderDetail.getQty()
                );
                orders.add(orderDetailDto);
            }
            boolean itemUpdated = deleteOrders(orders);
            if (itemUpdated){
                boolean isDeleteOrderDetail = orderDetailDAO.delete(id);
                if (isDeleteOrderDetail){
                    boolean isDeletePromotion = promotionDetailDAO.deleteOrderPromotion(id);
                    boolean isDeletePayment = paymentDAO.delete(id);
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

    @Override
    public boolean deleteOrders(ArrayList<OrderDetailDto> items) throws SQLException {
        boolean isItemDetailUpdated = true;
        for (int i = 0; i < items.size(); i++) {
            String id = items.get(i).getItemId();
            double qty = items.get(i).getQty();
            isItemDetailUpdated = itemDetailDAO.increaseQTY(id,qty);
            if (!isItemDetailUpdated){
                break;
            }
        }
        return isItemDetailUpdated;
    }

    @Override
    public ArrayList<CustomDto> getOrdersAll() throws SQLException {
        ArrayList<Custom> customs = queryDAO.getOrdersAll();
        ArrayList<CustomDto> customDtos = new ArrayList<>();
        for (Custom custom: customs){
            CustomDto customDto = new CustomDto(
                    custom.getOrderId(),
                    custom.getCustomerId(),
                    custom.getUserId(),
                    custom.getNetPrice(),
                    custom.getOrderDate(),
                    custom.getOrderQty(),
                    custom.getItemDescription(),
                    custom.getStockId()
            );
            customDtos.add(customDto);
        }
        return customDtos;
    }
}
