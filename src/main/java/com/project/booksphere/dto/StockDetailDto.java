package com.project.booksphere.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StockDetailDto {
    private String StockId;
    private String SupplyId;
    private double unitPrice;
    private int quantity;
}
