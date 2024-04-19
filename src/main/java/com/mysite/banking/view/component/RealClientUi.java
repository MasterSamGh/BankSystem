package com.mysite.banking.view.component;
import com.mysite.banking.dto.ClientDto;
import com.mysite.banking.dto.RealClientDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

public class RealClientUi extends AbstractClientUi {
    public RealClientUi() {
        super();
    }

    @Override
    public ClientDto additionalGenerateClient(String name, String number, String password, String email) {
            String family = scannerWrapper.getUserInput("Enter family: ", Function.identity());
            Date birthdate = scannerWrapper.getUserInput("Enter birthdate(dd-MM-yyyy):",
                    input->{
                        try {
                            return new SimpleDateFormat("dd-MM-yyy").parse(input);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
            RealClientDto realClient = new RealClientDto(null,name, number,password,email);
            realClient.setFamily(family);
            realClient.setBirthday(birthdate);
            return realClient;
    }

    @Override
    public void editClient(ClientDto client) {
        RealClientDto realClient = (RealClientDto) client;
        String number = scannerWrapper.getUserInput("Enter new number:",Function.identity());
        client.setNumber(number);
        String family = scannerWrapper.getUserInput("Enter new family:",Function.identity());
        realClient.setFamily(family);
    }
}
