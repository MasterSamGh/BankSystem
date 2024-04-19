package com.mysite.banking.facade.impl;

import com.mysite.banking.dto.AccountDto;
import com.mysite.banking.dto.AmountDto;
import com.mysite.banking.facade.AccountFacade;
import com.mysite.banking.mapper.AccountMapstruct;
import com.mysite.banking.model.Account;
import com.mysite.banking.model.Client;
import com.mysite.banking.model.FileType;
import com.mysite.banking.service.AccountService;
import com.mysite.banking.service.ClientService;
import com.mysite.banking.service.exception.*;
import com.mysite.banking.service.impl.AccountServiceImpl;
import com.mysite.banking.service.impl.ClientServiceImpl;
import com.mysite.banking.service.validation.ValidationContext;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class AccountFacadeImpl implements AccountFacade {
    private ValidationContext<AccountDto> validationContext;
    private AccountService accountService;
    private final AccountMapstruct accountMapstruct;
    private final ClientService clientService;
    private static final AccountFacadeImpl INSTANCE;
    public static AccountFacadeImpl getInstance(){
        return INSTANCE;
    }
    public static AccountFacadeImpl getInstance(AccountService accountService){
        INSTANCE.accountService =accountService;

        return INSTANCE;
    }
    static {
        INSTANCE = new AccountFacadeImpl();
    }
    private AccountFacadeImpl() {
        this.accountMapstruct = Mappers.getMapper(AccountMapstruct.class);
        this.accountService = AccountServiceImpl.getInstance();
        this.clientService = ClientServiceImpl.getInstance();
        this.validationContext = new AccountValidationContext();
    }
    public AccountFacadeImpl(AccountServiceImpl accountService) {
        this.accountMapstruct = Mappers.getMapper(AccountMapstruct.class);
        this.accountService = accountService;
        this.clientService = ClientServiceImpl.getInstance();
        this.validationContext = new AccountValidationContext();
    }
    @Override
    public void deleteAccountById(Integer id) throws AccountNotFindException {
        accountService.deleteAccountById(id);
    }

    @Override
    public List<AccountDto> getActiveAccount() throws EmptyAccountException {
        return accountMapstruct.mapToAccountDtoList(
                accountService.getActiveAccount());
    }

    @Override
    public List<AccountDto> getDeletedAccount() throws EmptyAccountException {
        return accountMapstruct.mapToAccountDtoList(
                accountService.getDeletedAccount());
    }

    @Override
    public AccountDto getAccountById(Integer id) throws AccountNotFindException {
        return accountMapstruct.mapToAccountDto(accountService.getAccountById(id));
    }

    @Override
    public void addAccount(AccountDto accountDto) throws ValidationException {
        validationContext.validate(accountDto);
        accountService.addAccount(accountMapstruct.mapToAccount(accountDto));
    }

    @Override
    public void updateAccount(AccountDto accountDto) throws ValidationException, AccountNotFindException {
        validationContext.validate(accountDto);
        Account account = accountService.getAccountById(accountDto.getId());
        accountMapstruct.mapToAccount(accountDto,account);
    }





    @Override
    public List<AccountDto> searchAccountByClientName(String name)  {
        List<Client> clients = clientService.searchClientByName(name);
        List<Account> accountList = new ArrayList<>();
        for (Client client : clients) {
             accountList.addAll(accountService.getAccountByClientId(client.getId()));
        }
        return accountMapstruct.mapToAccountDtoList(accountList);
    }

    @Override
    public List<AccountDto> searchAccountByClientId(Integer id) {
        return accountMapstruct.mapToAccountDtoList(accountService.getAccountByClientId(id));
    }

    @Override
    public void deposit(int accountId, AmountDto amount) throws AccountNotFindException {
        accountService.deposit(accountId,accountMapstruct.mapToAmount(amount));
    }

    @Override
    public void withdraw(int accountId, AmountDto amount) throws AccountNotFindException, ValidationException {
        accountService.withdraw(accountId,accountMapstruct.mapToAmount(amount));
    }

    @Override
    public void transfer(int fromAccountId, int toAccountId, AmountDto amount) throws AccountNotFindException, ValidationException {
        accountService.transfer(fromAccountId,toAccountId,accountMapstruct.mapToAmount(amount));
    }

    @Override
    public void exportJson(String fileName) {
        accountService.exportJson(fileName);
    }
}
