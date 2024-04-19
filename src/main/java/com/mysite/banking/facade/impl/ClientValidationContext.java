package com.mysite.banking.facade.impl;

import com.mysite.banking.dto.ClientDto;
import com.mysite.banking.dto.LegalClientDto;
import com.mysite.banking.dto.RealClientDto;
import com.mysite.banking.facade.ClientFacade;
import com.mysite.banking.service.exception.ClientNotFindException;
import com.mysite.banking.service.exception.ValidationException;
import com.mysite.banking.service.validation.ValidationContext;
import com.mysite.banking.util.Validator;

import java.util.Objects;

public class ClientValidationContext extends ValidationContext<ClientDto> {
    private final ClientFacade clientFacade;
    public ClientValidationContext(ClientFacade clientFacade) {
        this.clientFacade = clientFacade;
        addValidation(client -> {
            String email = client.getEmail();
            if (email==null||
                    email.trim().isEmpty()){
                throw new ValidationException("Email must not empty or null");
            }
            try {
                ClientDto clientDto = clientFacade.searchClientByEmail(email);
                if (!Objects.equals(client.getId(), clientDto.getId()))
                    throw new ValidationException("Email must not duplicate");
            } catch (ClientNotFindException ignored) {

            }
            if (!Validator.validateEmail(email)){
                throw new ValidationException("Invalid email format.");
            }
        });
        addValidation(client -> {
            String name = client.getName();
            if (name==null||
            name.trim().isEmpty()){
                throw new ValidationException("Name must not empty or null");
            }
        });
        addValidation(client -> {
            String number = client.getNumber();
            if (!Validator.validateNumber(number)){
                throw new ValidationException("Invalid number format.");
            }
        });
        addValidation(client -> {
            if (client instanceof LegalClientDto){
                String fax = ((LegalClientDto)client).getFax();
                if (!Validator.validateNumber(fax)){
                    throw new ValidationException("Invalid fax number format.");
                }
            }
        });
        addValidation(client -> {
            if (client instanceof RealClientDto){
                String family = ((RealClientDto)client).getFamily();
                if (family == null ||
                family.trim().isEmpty() ||
                !family.equals(family.toLowerCase())){
                    throw new ValidationException("Family must not be empty or null, and should be in lower case.");
                }
            }
        });
    }
}
