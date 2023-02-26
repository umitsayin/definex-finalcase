package com.definex.util;

public enum CollateralType {
    LAND("LAND"),
    HOUSE("HOUSE"),
    CAR("CAR");

    private String value;
    CollateralType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
