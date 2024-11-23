package com.project.booksphere.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDetailDto {
    private String itemId;
    private String stockId;
    private double sellPrice;
    private int qty;
}
