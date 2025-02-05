package com.project.booksphere.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDetail {
    private String promotionId;
    private String orderId;
    private double discountPrice;
}
