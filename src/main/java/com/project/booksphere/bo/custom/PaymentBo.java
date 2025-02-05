package com.project.booksphere.bo.custom;

import com.project.booksphere.bo.SuperBo;

import java.sql.SQLException;

public interface PaymentBo extends SuperBo {
    public String nextPaymentId() throws SQLException;
}
