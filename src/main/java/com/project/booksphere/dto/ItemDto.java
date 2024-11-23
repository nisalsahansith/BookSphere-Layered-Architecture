package com.project.booksphere.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ItemDto {
    private String id;
    private String description;
    private String ISBN;
    private int qty;
}
