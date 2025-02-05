package com.project.booksphere.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTM {
    private String customerId;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
//    private String orderID;
}
