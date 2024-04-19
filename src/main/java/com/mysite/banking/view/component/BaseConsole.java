package com.mysite.banking.view.component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.banking.util.ScannerWrapper;

public abstract class BaseConsole{
    protected final ScannerWrapper scannerWrapper;
    protected final ObjectMapper objectMapper;
    public BaseConsole(){
        scannerWrapper = ScannerWrapper.getInstance();
        objectMapper = new ObjectMapper();
    }
}
