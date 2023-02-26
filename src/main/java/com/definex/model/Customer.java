package com.definex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseModel{
    @Column(nullable = false, unique = true)
    private String identityNumber;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private double salary;
    private int creditScore;
    private Date dateOfBirth;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Collateral> collaterals = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Credit> credits = new ArrayList<>();

    @ManyToOne
    private User user;
}
