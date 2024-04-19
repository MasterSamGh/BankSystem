package com.mysite.banking.facade.impl;
import com.mysite.banking.dto.AccountDto;
import com.mysite.banking.facade.ClientFacade;
import com.mysite.banking.service.exception.ClientNotFindException;
import com.mysite.banking.service.exception.ValidationException;
import com.mysite.banking.service.validation.ValidationContext;

import java.math.BigDecimal;

public class AccountValidationContext extends ValidationContext<AccountDto> {
    private final ClientFacade clientFacade;
    public AccountValidationContext() {
        this.clientFacade = ClientFacadeImpl.getInstance();
        addValidation(accountDto -> {
            BigDecimal balance = accountDto.getBalance().getValue();
            if (balance.compareTo(BigDecimal.ZERO) < 0){
                throw new ValidationException("Balance must not be less than zero ");
            }
        });
        addValidation(accountDto -> {
            Integer clientId = accountDto.getClientId();
            try {
                clientFacade.getClientById(clientId);
            } catch (ClientNotFindException e) {
                throw new ValidationException("Client Id is not valid.");
            }
        });
    }
}
