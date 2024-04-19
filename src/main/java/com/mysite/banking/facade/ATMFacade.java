package com.mysite.banking.facade;

import com.mysite.banking.dto.AmountDto;
import com.mysite.banking.service.exception.AccountNotFindException;
import com.mysite.banking.service.exception.ValidationException;

import java.math.BigDecimal;
import java.util.List;

public interface ATMFacade {
    int[] withdraw(int accountId, AmountDto amountDto) throws AccountNotFindException, ValidationException;

    void deposit(int denomination, int quantity);
    List<Integer> getBillValues();

    Boolean login(String userName, String password);
}
