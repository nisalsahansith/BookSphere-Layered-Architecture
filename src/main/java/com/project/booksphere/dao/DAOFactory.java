package com.project.booksphere.dao;

import com.project.booksphere.dao.custom.CustomerDAO;
import com.project.booksphere.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOType{
        CUSTOMER,
        EMPLOYEE,
        ITEM,
        ITEM_DETAIL,
        ORDER,
        ORDER_DETAIL,
        PAYMENT,
        PROMOTION,
        PROMOTION_DETAIL,
        STOCK,
        STOCK_DETAIL,
        SUPPLIER,
        USER,
        USER_DETAIL,
        QUERY_DAO
    }

    public SuperDAO getDAO(DAOType type){
        switch (type){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ITEM_DETAIL:
                return new ItemDetailDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case PROMOTION:
                return new PromotionDAOImpl();
            case PROMOTION_DETAIL:
                return new PromotionDetailDAOImpl();
            case STOCK:
                return new StockDAOImpl();
            case STOCK_DETAIL:
                return new StockDetailDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case USER:
                return new UserDAOImpl();
            case USER_DETAIL:
                return new UserDetailDAOImpl();
            case QUERY_DAO:
                return new QueryDAOImpl();
            default:
                return null;

        }
    }

}
