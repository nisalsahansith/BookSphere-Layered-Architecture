package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;
import com.project.booksphere.dto.PaymentDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ViewPaymentBo extends SuperBo {

    ArrayList<PaymentDto> getAllPaymentID(String id) throws SQLException;
    ArrayList<PaymentDto> getAllPayments() throws SQLException;
}
