package com.project.booksphere.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewStockTM {
    private String stockId;
    private String name;
    private String itemId;
    private int qty;
    private double sellPrice;
    private double buyPrice;
    private String supplierId;
    private String supplierName;
    private String userId;
}
