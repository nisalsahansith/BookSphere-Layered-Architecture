package com.project.booksphere.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDto{

    private String id;
    private String name;
    private String phone;
    private String email;
    private String orderId;
}
