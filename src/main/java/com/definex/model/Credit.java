package com.definex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credit extends BaseModel{
    private double creditAmount;
    private String currency;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private User customerRepresentative;
}
