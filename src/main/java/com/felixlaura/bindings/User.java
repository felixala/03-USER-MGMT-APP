package com.felixlaura.bindings;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private String fullName;

    private String email;

    private Integer mobile;

    private String gender;

    private LocalDate dob;

    private Long ssn;
}
