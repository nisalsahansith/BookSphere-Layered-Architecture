package com.project.booksphere.dto;

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
public class OrderDto {
    private  String orderId;
    private String customerId;
    private double totalPrice;
    private double netTotal;
    private Date date;
    private String userId;
    private ArrayList<OrderDetailDto> orderDetailsDto;
}
