package com.mysite.banking.util;

public class Validator {
    public static Boolean validateNumber(String number){
        return (number != null) &&
                number.matches("^0\\d{10}$|^00\\d{12}$|^\\+\\d{12}$");
    }
    public static Boolean validateEmail(String email){
        return (email != null) &&
                email.matches("^.+@.+[.].+$");
    }
}
