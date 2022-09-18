package com.felixlaura.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "USER_MASTER")
public class UserMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE")
    private Integer mobile;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dob;

    @Column(name = "SSN")
    private Integer ssn;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ACC_STATUS")
    private String accStatus;

    @CreationTimestamp
    @Column(name = "CREATED_DATE", updatable = false)
    private LocalDate createdDate;

    @UpdateTimestamp
    @Column(name = "UPDATED_DATE", insertable = false)
    private LocalDate updatedDate;

    @Column(name = "CREATED_BY")
    private String creadtedBy;

    @Column(name = "UPDATED_BY")
    private String updatedBy;
}
