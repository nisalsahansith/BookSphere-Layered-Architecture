package com.project.booksphere.dto;

import com.project.booksphere.db.DBConnection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDetailsDto {
    private String paymentId;
    private String orderId;
    private String paymentMethod;
    private double total;
    private Date date;
}
