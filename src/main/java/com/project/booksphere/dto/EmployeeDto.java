package com.project.booksphere.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDto {
    private String id;
    private String name;
    private String role;
    private String phoneNumber;
    private String email;
    private LocalDate date;
}
