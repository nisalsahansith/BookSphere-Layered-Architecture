package com.project.booksphere.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    private String supId;
    private String name;
    private String phone;
    private String address;
    private String email;
    private String userId;
}
