package com.definex.util;

public enum RoleType {
    ADMIN("ADMIN");

    private String value;

    RoleType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
