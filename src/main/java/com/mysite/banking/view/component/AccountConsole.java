package com.mysite.banking.view.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mysite.banking.dto.AccountDto;

import com.mysite.banking.dto.AmountDto;
import com.mysite.banking.facade.AccountFacade;
import com.mysite.banking.facade.impl.AccountFacadeImpl;
import com.mysite.banking.model.FileType;
import com.mysite.banking.service.exception.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.function.Function;

public class AccountConsole extends BaseConsole{
    final AccountFacade accountFacade;

    public AccountConsole() {
        this.accountFacade = AccountFacadeImpl.getInstance();
    }

    public void AccountMenu(){
        int choice ;
        do {
            printAccountMenu();
            choice = scannerWrapper.getUserInput("Enter your choice:", Integer::valueOf);
            try {
                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        addAccount();
                        break;
                    case 2:
                        printAllAccount();
                        break;
                    case 3:
                        editAccountById();
                        break;
                    case 4:
                        deleteAccountById();
                        break;
                    case 5:
                        printAllDeletedAccount();
                        break;
                    case 6:
                        searchAccountByClientName();
                        break;
                    case 7:
                        deposit();
                        break;
                    case 8:
                        withdraw();
                        break;
                    case 9:
                        transfer();
                        break;
                    case 10:
                        exportJson();
                        break;
                    default:
                        System.out.println("invalid Number!");
                }
            }catch (AccountNotFindException | EmptyAccountException  | ValidationException exception) {
                System.out.println(exception.getMessage());
            }
        }while (choice!=0);
    }

    private void exportJson() {
        String fileName = scannerWrapper.getUserInput("Enter the file name:",Function.identity());
        accountFacade.exportJson(fileName);
    }

    private void transfer() throws AccountNotFindException, ValidationException {
        int fromAccountId = scannerWrapper.getUserInput("Enter your account id:", Integer::valueOf);
        int toAccountId = scannerWrapper.getUserInput("Enter your account id:", Integer::valueOf);
        BigDecimal amount = scannerWrapper.getUserInput("Enter your amount:", BigDecimal::new);
        Currency currency = getCurrency();
        accountFacade.transfer(fromAccountId,toAccountId,new AmountDto(currency, amount));
    }

    private void withdraw() throws AccountNotFindException,ValidationException{
        int accountId = scannerWrapper.getUserInput("Enter your account id:", Integer::valueOf);
        BigDecimal amount = scannerWrapper.getUserInput("Enter your amount:", BigDecimal::new);
        Currency currency = getCurrency();
        accountFacade.withdraw(accountId,new AmountDto(currency,amount));
    }

    private void deposit() throws AccountNotFindException{
        int accountId = scannerWrapper.getUserInput("Enter your account id:", Integer::valueOf);
        BigDecimal amount = scannerWrapper.getUserInput("Enter your amount:", BigDecimal::new);
        Currency currency = getCurrency();
        accountFacade.deposit(accountId,new AmountDto(currency,amount));
    }

    private void searchAccountByClientName() {
        String name = scannerWrapper.getUserInput("Enter the name:", Function.identity());
        List<AccountDto> accountDtoList = accountFacade.searchAccountByClientName(name);
        accountDtoList.forEach(System.out::println);
    }
    private void deleteAccountById() throws AccountNotFindException {
        String id = scannerWrapper.getUserInput("Enter the customer id: ", Function.identity());
        accountFacade.deleteAccountById(Integer.valueOf(id));
    }

    private void editAccountById() throws AccountNotFindException {
        String id = scannerWrapper.getUserInput("Enter the account id: ", Function.identity());
        AccountDto accountDto = accountFacade.getAccountById(Integer.valueOf(id));
        System.out.println(accountDto);
        int number = scannerWrapper.getUserInput("Enter new client id:",Integer::valueOf);
        accountDto.setClientId(number);
        try {
            accountFacade.updateAccount(accountDto);
        } catch (ValidationException e){
            System.out.println(e.getMessage());
            editAccountById();
        }
    }

    private void printAllDeletedAccount() throws EmptyAccountException {
        List<AccountDto> allAccount = accountFacade.getDeletedAccount();
        System.out.println("All Deleted Account:");
        for (AccountDto account : allAccount) {
            System.out.println(account);
        }
    }

    private void printAllAccount() throws EmptyAccountException {
        List<AccountDto> allAccount = accountFacade.getActiveAccount();
        System.out.println("All Account:");
        for (AccountDto account : allAccount) {
            try {
                System.out.println(objectMapper.writeValueAsString(account));
            } catch (JsonProcessingException e) {
                System.out.println("Error on print account id" + account.getId());
            }
        }
    }

    private void addAccount() {
        try {
            Currency currency = getCurrency();
            int number = scannerWrapper.getUserInput("Enter client id:",Integer::valueOf);
            AccountDto accountDto = new AccountDto(null, new AmountDto(currency, BigDecimal.ZERO),number);
            accountFacade.addAccount(accountDto);
        }catch (ValidationException exception){
            System.out.println(exception.getMessage());
            addAccount();
        } catch (DuplicateAccountException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Account added successfully!");
    }


    private void printAccountMenu() {
        System.out.println("Menu:");
        System.out.println("0. Back");
        System.out.println("1. Add Account");
        System.out.println("2. Print All Accounts");
        System.out.println("3. Edit account by id");
        System.out.println("4. Delete account by id");
        System.out.println("5. Print all deleted accounts");
        System.out.println("6. Search clients by name");
        System.out.println("7. Deposit");
        System.out.println("8. Withdraw");
        System.out.println("9. Transfer");
        System.out.println("10. Export JSON File");
        System.out.println();
    }
    private Currency getCurrency(){
        System.out.println("Currency:");
        System.out.println("1.EUR");
        System.out.println("2.USD");
        int choice = scannerWrapper.getUserInput("Enter your choice:", Integer::valueOf);
        Currency currency;
        if (choice == 1){
            currency = Currency.getInstance("EUR");
        }else if (choice == 2){
            currency = Currency.getInstance("USD");
        }else {
            currency = Currency.getInstance("GBP");
        }
        return currency;
    }
}
