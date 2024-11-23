package com.project.booksphere.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromotionTM {
    private String promotionId;
    private String description;
    private double discountRate;
    private double range;
}
