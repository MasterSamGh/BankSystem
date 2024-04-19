package com.mysite.banking.model;

import com.mysite.banking.service.exception.InvalidType;

public enum ClientType {
    REAL(1),
    LEGAL(2);

    private final int value;

    ClientType(int value) {
        this.value = value;
    }

    public static ClientType fromValue(int value) throws InvalidType {
        for (ClientType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
       throw new InvalidType();
    }
}


