package com.mysite.banking.util;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GlobalAttributes {
    private final static GlobalAttributes INSTANCE;
    public static GlobalAttributes getInstance(){
        return INSTANCE;
    }
    static {
        INSTANCE = new GlobalAttributes();
    }
    private Integer clientId;
    private GlobalAttributes(){
        clientId = null;
    }

}
