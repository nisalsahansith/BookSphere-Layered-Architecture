package com.project.booksphere.entity;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

        private String id;
        private String name;
        private String role;
        private String phoneNumber;
        private String email;
        private LocalDate date;

}
