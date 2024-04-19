package com.mysite.banking.view;
import com.mysite.banking.view.component.ATMConsole;
import com.mysite.banking.view.component.AccountConsole;
import com.mysite.banking.view.component.BaseConsole;
import com.mysite.banking.view.component.ClientConsole;

public class ConsoleUi extends BaseConsole implements AutoCloseable{
    private final ClientConsole clientConsole;
    private final AccountConsole accountConsole;
    private final ATMConsole atmConsole;
    public ConsoleUi(){
        super();
        clientConsole = new ClientConsole();
        accountConsole = new AccountConsole();
        atmConsole = new ATMConsole();
    }


    public void startApp(){
        int choice ;
        do {
            printMainMenu();
            choice = scannerWrapper.getUserInput("Enter your choice:", Integer::valueOf);
            switch (choice) {
                case 0:
                    System.out.println("Exit!");
                    break;
                case 1:
                    clientConsole.clientMenu();
                    break;
                case 2:
                    accountConsole.AccountMenu();
                    break;
                case 3:
                    atmConsole.ATMMenu();
                    break;
                default:
                    System.out.println("invalid Number!");
            }
        }while (choice != 0);
    }
    private void printMainMenu() {
        System.out.println("Menu:");
        System.out.println("0. Exit");
        System.out.println("1. Client Menu");
        System.out.println("2. Account Menu");
        System.out.println("3. ATM Menu");
        System.out.println();
    }

    @Override
    public void close(){
        scannerWrapper.close();
    }
}
