package com.mysite.banking.service.exception;

public class EmptyClientException extends BaseException {
    public EmptyClientException(){
        super("There is no Client!");
    }
}
