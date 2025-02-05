package com.project.booksphere.entity;

import com.project.booksphere.dto.OrderDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private  String orderId;
    private String customerId;
    private double totalPrice;
    private double netTotal;
    private Date date;
    private String userId;

}
