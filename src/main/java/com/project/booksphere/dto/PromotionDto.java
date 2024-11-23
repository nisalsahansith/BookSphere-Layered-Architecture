package com.project.booksphere.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDto {
    private String promotionId;
    private String desc;
    private double rate;
    private double range;
}
