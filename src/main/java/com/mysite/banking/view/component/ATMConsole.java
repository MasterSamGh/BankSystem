package com.mysite.banking.view.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mysite.banking.dto.AccountDto;
import com.mysite.banking.dto.AmountDto;
import com.mysite.banking.dto.ClientDto;
import com.mysite.banking.facade.ATMFacade;
import com.mysite.banking.facade.AccountFacade;
import com.mysite.banking.facade.ClientFacade;
import com.mysite.banking.facade.impl.ATMFacadeImpl;
import com.mysite.banking.facade.impl.AccountFacadeImpl;
import com.mysite.banking.facade.impl.ClientFacadeImpl;

import com.mysite.banking.model.ATM;
import com.mysite.banking.service.exception.AccountNotFindException;
import com.mysite.banking.service.exception.ClientNotFindException;
import com.mysite.banking.service.exception.ValidationException;
import com.mysite.banking.service.impl.ATMServiceImpl;
import com.mysite.banking.util.GlobalAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;


public class ATMConsole extends BaseConsole{
    private final GlobalAttributes globalAttributes ;
    private final ClientFacade clientFacade;
    private final AccountFacade accountFacade;
    private final ATMFacade atmFacade;



    public ATMConsole() {
        globalAttributes = GlobalAttributes.getInstance();
        clientFacade = ClientFacadeImpl.getInstance();
        accountFacade = AccountFacadeImpl.getInstance();
        atmFacade = ATMFacadeImpl.getInstance();
    }
    public void ATMMenu(){
        Integer clientId = globalAttributes.getClientId();
        if (clientId !=null){
            ATMMenuWithUser();
        }else
            ATMMenuNoUser();
    }
    public void ATMMenuNoUser() {
        int choice = 0;
        do {

            try {
                printMenu();
                choice = scannerWrapper.getUserInput("Enter your choice:", Integer::valueOf);
                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        login();
                        break;
                    default:
                        System.out.println("invalid Number!");
                }
            }catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }while (choice!=0);
    }
    public void ATMMenuWithUser() {
        int choice = 0;
        do {
            try {
                printMenu();
                choice = scannerWrapper.getUserInput("Enter your choice:", Integer::valueOf);
                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        checkAccountBalance();
                        break;
                    case 2:
                        withdraw();
                        break;
                    case 3:
                        deposit();
                        break;
                    default:
                        System.out.println("invalid Number!");
                }
            }catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }while (choice!=0);
    }

    private void deposit() {
        int quantity = scannerWrapper.getUserInput("Enter your quantity id:", Integer::valueOf);
        System.out.println("Denomination:");
        System.out.println("1.5€");
        System.out.println("2.10€");
        System.out.println("3.50€");
        System.out.println("4.100€");
        System.out.println("5.500€");
        int choice = scannerWrapper.getUserInput("Enter your choice:", Integer::valueOf);
        int denomination;
        if (choice == 1){
            denomination = 5;
        }else if (choice == 2){
            denomination = 10;
        }else if (choice == 3){
            denomination = 50;
        }else if (choice == 4){
            denomination = 100;
        }else{
            denomination = 500;
        }
        atmFacade.deposit(denomination,quantity);
    }

    private void withdraw() throws AccountNotFindException, ValidationException {
        int accountId = scannerWrapper.getUserInput("Enter your account id:", Integer::valueOf);
        BigDecimal amount = scannerWrapper.getUserInput("Enter your amount:", BigDecimal::new);
        Currency currency = getCurrency();
        int[] billToWithdraw = atmFacade.withdraw(accountId, new AmountDto(currency, amount));
        List<Integer> billValues = atmFacade.getBillValues();
        IntStream.range(0,billToWithdraw.length)
                .filter(i-> billToWithdraw[i]>0)
                .forEach(i->System.out.println(billToWithdraw[i] + "€" + billValues.get(i)));
    }
    private void login(){
        String userName = scannerWrapper.getUserInput("Enter your email:", Function.identity());
        String password = scannerWrapper.getUserInput("Enter your password:",Function.identity());
        Boolean validate = atmFacade.login(userName,password);
        if (validate){
            System.out.println("Welcome to the system.");
        }else {
            System.out.println("User or password is wrong!");
        }

    }

    private void checkAccountBalance() {
        Integer clientId = globalAttributes.getClientId();
        List<AccountDto> allAccount = accountFacade.searchAccountByClientId(clientId);
        System.out.println("All Account:");
        for (AccountDto account : allAccount) {
            try {
                System.out.println(objectMapper.writeValueAsString(account));
            } catch (JsonProcessingException e) {
                System.out.println("Error on print account id" + account.getId());
            }
        }
    }


    private void printMenu() throws ClientNotFindException {
        Integer clientId = globalAttributes.getClientId();
        if (clientId !=null){
            ClientDto clientById = clientFacade.getClientById(clientId);
            printMenuWithUser(clientById);
        }else
            printMenuNoUser();
    }
    private void printMenuWithUser(ClientDto client) {
        System.out.println("Hello " + client.getName());
        System.out.println("Menu:");
        System.out.println("0. Back");
        System.out.println("1. Check Account");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit To ATM");
        System.out.println();
    }
    private void printMenuNoUser() {
        System.out.println("Menu:");
        System.out.println("0. Back");
        System.out.println("1. Login");
        System.out.println();
    }
    private Currency getCurrency(){
        System.out.println("Currency:");
        System.out.println("1.EUR");
        System.out.println("2.USD");
        System.out.println("3.GBP");
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
