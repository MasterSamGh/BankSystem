package com.mysite.banking.view.component;

import com.mysite.banking.dto.ClientDto;
import com.mysite.banking.model.ClientType;
import com.mysite.banking.util.ScannerWrapper;

import java.util.function.Function;

public abstract class AbstractClientUi {
    protected final ScannerWrapper scannerWrapper;

    public AbstractClientUi() {
        this.scannerWrapper = ScannerWrapper.getInstance();
    }
    public static AbstractClientUi fromClientType(ClientType type){
        return switch (type) {
            case REAL -> new RealClientUi();
            case LEGAL -> new LegalClientUi();
        };
    }
    public ClientDto generateClient(){
        String name = scannerWrapper.getUserInput("Enter name: ", Function.identity());
        String number = scannerWrapper.getUserInput("Enter number: ",Function.identity());
        String password = scannerWrapper.getUserInput("Enter password: ",Function.identity());
        String email = scannerWrapper.getUserInput("Enter email: ",Function.identity());
        return additionalGenerateClient(name,number,password,email);
    }
    protected abstract ClientDto additionalGenerateClient(String name , String number, String password, String email);
    public abstract void editClient(ClientDto client);

}
