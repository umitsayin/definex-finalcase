package com.definex.model;

import com.definex.util.CollateralType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Collateral extends BaseModel{
    @Enumerated(value = EnumType.STRING)
    private CollateralType collateral;
    private double price;
    private String currency;
}
