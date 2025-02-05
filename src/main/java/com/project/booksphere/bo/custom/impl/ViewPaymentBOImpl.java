package com.project.booksphere.bo.custom.impl;

import com.project.booksphere.bo.custom.ViewPaymentBo;
import com.project.booksphere.dao.DAOFactory;
import com.project.booksphere.dao.custom.PaymentDAO;
import com.project.booksphere.dto.OrderDetailDto;
import com.project.booksphere.dto.PaymentDto;
import com.project.booksphere.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewPaymentBOImpl implements ViewPaymentBo {
    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    @Override
    public ArrayList<PaymentDto> getAllPaymentID(String id) throws SQLException {
        ArrayList<Payment> payments = paymentDAO.search(id);
        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();
        for (Payment payment: payments){
            PaymentDto paymentDto = new PaymentDto(
                    payment.getPaymentId(),payment.getOrderId(),payment.getPaymentMethod(),payment.getTotal(),payment.getDate()
            );
            paymentDtos.add(paymentDto);
        }
        return paymentDtos;
    }

    @Override
    public ArrayList<PaymentDto> getAllPayments() throws SQLException {
        ArrayList<Payment> payments = paymentDAO.getAll();
        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();
        for (Payment payment: payments){
            PaymentDto paymentDto = new PaymentDto(
                    payment.getPaymentId(),payment.getOrderId(),payment.getPaymentMethod(),payment.getTotal(),payment.getDate()
            );
            paymentDtos.add(paymentDto);
        }
        return paymentDtos;
    }

}
