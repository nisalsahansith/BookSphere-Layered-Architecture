package com.project.booksphere.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewItemTM {
    private String itemId;
    private String desc;
    private String isbn;
    private double price;
    private int qty;
    private String stockId;
    private String name;
}
