package com.project.booksphere.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderTM {
    private String OrderID;
    private String customerID;
    private String UserID;
    private double total;
    private Date date;
    private int qty;
    private String desc;
    private String stockID;
}
