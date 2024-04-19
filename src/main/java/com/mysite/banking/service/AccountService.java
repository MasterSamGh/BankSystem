package com.mysite.banking.service;

import com.mysite.banking.model.Account;
import com.mysite.banking.model.Amount;
import com.mysite.banking.model.FileType;
import com.mysite.banking.service.exception.*;

import java.util.List;

public interface AccountService {
    void deleteAccountById(Integer id) throws AccountNotFindException;
    List<Account> getActiveAccount() throws EmptyAccountException;
    List<Account> getDeletedAccount() throws EmptyAccountException;
    Account getAccountById(Integer id) throws AccountNotFindException;
    void addAccount(Account account);
    List<Account> getAccountByClientId(Integer id);

    void deposit(int accountId, Amount amount)throws AccountNotFindException;

    void withdraw(int accountId, Amount amount)throws AccountNotFindException, ValidationException;

    void transfer(int fromAccountId, int toAccountId, Amount amount)throws AccountNotFindException, ValidationException;

    void exportJson(String fileName);
}
