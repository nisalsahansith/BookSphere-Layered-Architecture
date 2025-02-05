package com.project.booksphere.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Custom {
    private String ItemId;
    private String ItemDescription;
    private String ISBN;
    private double sellPrice;
    private int qtyOnHand;
    private String stockId;
    private String stockName;

    private String orderId;
    private Date orderDate;
    private double netPrice;
    private String customerId;
    private String userId;
    private int orderQty;

    private String supId;
    private double unitPrice;
    private String supName;

    public Custom(String itemID, String description, String isbn, int qtyOnHand) {
        this.ItemId = itemID;
        this.ItemDescription = description;
        this.ISBN = isbn;
        this.qtyOnHand = qtyOnHand;
    }

    public Custom(String itemId,String itemDescription,String ISBN,double sellPrice,int qtyOnHand,String stockId,String stockName){
        this.ItemId = itemId;
        this.ItemDescription = itemDescription;
        this.ISBN = ISBN;
        this.sellPrice = sellPrice;
        this.qtyOnHand = qtyOnHand;
        this.stockId = stockId;
        this.stockName = stockName;
    }

    public Custom(String orderId,String customerId,String userId,double netPrice, Date orderDate,int orderQty,String itemDescription,String stockId){
        this.orderId = orderId;
        this.customerId = customerId;
        this.userId = userId;
        this.netPrice = netPrice;
        this.orderDate = orderDate;
        this.orderQty = orderQty;
        this.ItemDescription = itemDescription;
        this.stockId = stockId;
    }


    public Custom(String stockID, String name, String itemID, int qtyOnHand, double sellPrice, double unitPrice, String supplierID, String supName, String userId) {
        this.stockId = stockID;
        this.stockName = name;
        this.ItemId = itemID;
        this.qtyOnHand = qtyOnHand;
        this.sellPrice = sellPrice;
        this.unitPrice = unitPrice;
        this.supId = supplierID;
        this.supName = supName;
        this.userId = userId;
    }
}
