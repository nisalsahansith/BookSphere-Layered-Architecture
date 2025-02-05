package com.project.booksphere.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTM {
    private String employeeId;
    private String Name;
    private String Role;
    private String Phone;
    private String Email;
    private LocalDate date;
}
