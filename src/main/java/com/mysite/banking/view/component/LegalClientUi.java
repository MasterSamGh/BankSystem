package com.mysite.banking.view.component;
import com.mysite.banking.dto.ClientDto;
import com.mysite.banking.dto.LegalClientDto;

import java.util.function.Function;

public class LegalClientUi extends AbstractClientUi {
    public LegalClientUi() {
        super();
    }

    @Override
    public ClientDto additionalGenerateClient(String name, String number, String password, String email) {
        String fax = scannerWrapper.getUserInput("Enter fax: ", Function.identity());
        LegalClientDto legalClient = new LegalClientDto(null,name, number,password,email);
        legalClient.setFax(fax);
        return legalClient;
    }

        @Override
        public void editClient (ClientDto client){
            LegalClientDto legalClient = (LegalClientDto) client;
            String number = scannerWrapper.getUserInput("Enter new number:",Function.identity());
            client.setNumber(number);
            String fax = scannerWrapper.getUserInput("Enter fax number:",Function.identity());
            legalClient.setFax(fax);
        }
    }
