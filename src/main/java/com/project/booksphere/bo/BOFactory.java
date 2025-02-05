package com.project.booksphere.bo;

import com.project.booksphere.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){}

    public static BOFactory getInstance(){
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOType{
        CASHIER,CASHIER_DASHBOARD,CUSTOMER,EMPLOYEE,FORGOT_PASSWORD,
        INVENTORY,ITEM_DETAIL,LOGIN,MANAGER_DASHBOARD,MANAGER_HOMEPAGE,OWNER_DASHBOARD,
        OWNER_HOMEPAGE,PAYMENT,PROMOTION,PURCHASE,SECUIRITY_PROTECTION,SETTING,
        SIGNUP,STOCK_MANAGER_DASHBOARD,STOCK_MANAGER_HOMEPAGE,SUPPLIER,USER,VIEW_ITEM,
        VIEW_ORDER,VIEW_PAYMENT,VIEW_PROMOTION,VIEW_STOCK
    }

    public SuperBo getBO(BOType type){
        switch (type){
            case CASHIER:
                return new CashierBOImpl();
            case CASHIER_DASHBOARD:
                return new CashierDashboardBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case FORGOT_PASSWORD:
                return new ForgotPasswordBOImpl();
            case INVENTORY:
                return new InventoryBOImpl();
            case ITEM_DETAIL:
                return new ItemDetailBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            case MANAGER_DASHBOARD:
                return new ManagerDashboardBOImpl();
            case MANAGER_HOMEPAGE:
                return new ManagerHomePageBOImpl();
            case OWNER_DASHBOARD:
                return new OwnerDashboardBOImpl();
            case OWNER_HOMEPAGE:
                return new OwnerHomePageBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case PROMOTION:
                return new PromotionBOImpl();
            case PURCHASE:
                return new PurchaseBOImpl();
            case SECUIRITY_PROTECTION:
                return new SecuirityProtectionBOImpl();
            case SETTING:
                return new SettingBOImpl();
            case SIGNUP:
                return new SignUpBOImpl();
            case STOCK_MANAGER_DASHBOARD:
                return new StockManagerDashboardBOImpl();
            case STOCK_MANAGER_HOMEPAGE:
                return new StockManagerHomePageBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case USER:
                return new UserBOImpl();
            case VIEW_ITEM:
                return new ViewItemBOImpl();
            case VIEW_ORDER:
                return new ViewOrderBOImpl();
            case VIEW_PAYMENT:
                return new ViewPaymentBOImpl();
            case VIEW_PROMOTION:
                return new ViewPromotionBOImpl();
            case VIEW_STOCK:
                return new ViewStockBOImpl();
            default:
                return null;
        }
    }
}
