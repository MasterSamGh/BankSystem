package com.mysite.banking;

import com.mysite.banking.view.ConsoleUi;

public class ApplicationRunner {
    public static void main(String[] args) {
        try (ConsoleUi consoleUi = new ConsoleUi()) {
            consoleUi.startApp();
        } catch (Throwable exception) {
            System.out.println("System error!");
        }
    }
}