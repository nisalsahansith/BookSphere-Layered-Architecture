package com.project.booksphere.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierTM {
    private String supId;
    private String name;
    private String phone;
    private String address;
    private String email;
    private String userID;
}
