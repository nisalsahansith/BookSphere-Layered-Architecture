package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.PaymentBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.PaymentDAO;

import java.sql.SQLException;

public class PaymentBOImpl implements PaymentBo {
    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
    @Override
    public String nextPaymentId() throws SQLException {
        return paymentDAO.nextId();
    }
}
