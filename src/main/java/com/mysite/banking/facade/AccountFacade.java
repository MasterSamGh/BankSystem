package com.mysite.banking.facade;

import com.mysite.banking.dto.AccountDto;
import com.mysite.banking.dto.AmountDto;
import com.mysite.banking.model.FileType;
import com.mysite.banking.service.exception.*;

import java.util.List;

public interface AccountFacade {
    void deleteAccountById(Integer id) throws AccountNotFindException;
    List<AccountDto> getActiveAccount()throws EmptyAccountException;
    List<AccountDto> getDeletedAccount()throws EmptyAccountException;
    AccountDto getAccountById(Integer id) throws AccountNotFindException;
    void addAccount(AccountDto accountDto)throws DuplicateAccountException , ValidationException;
    void updateAccount(AccountDto accountDto)throws ValidationException , AccountNotFindException;
    List<AccountDto> searchAccountByClientName(String name);
    List<AccountDto> searchAccountByClientId(Integer id);

    void deposit(int accountId, AmountDto amount)throws AccountNotFindException;

    void withdraw(int accountId, AmountDto amount)throws AccountNotFindException,ValidationException;

    void transfer(int fromAccountId, int toAccountId, AmountDto amount)throws AccountNotFindException,ValidationException;

    void exportJson(String fileName);
}
