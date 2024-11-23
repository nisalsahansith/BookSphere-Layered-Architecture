package com.project.booksphere.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDetailDto {
    private String promotionId;
    private String orderId;
    private double discountPrice;
}
