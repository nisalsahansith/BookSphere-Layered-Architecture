package com.project.booksphere.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemTm {
    private String id;
    private String description;
    private String ISBN;
    private int quantity;
}
