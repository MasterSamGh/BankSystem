package com.mysite.banking.service.impl;

import com.mysite.banking.dao.ATMDao;
import com.mysite.banking.dao.impl.ATMDaoImpl;
import com.mysite.banking.model.ATM;
import com.mysite.banking.model.Amount;
import com.mysite.banking.service.ATMService;
import com.mysite.banking.service.AccountService;
import com.mysite.banking.service.ClientService;
import com.mysite.banking.service.exception.AccountNotFindException;
import com.mysite.banking.service.exception.ValidationException;
import java.math.BigDecimal;
import java.util.*;

public class ATMServiceImpl implements ATMService {
    private ClientService clientService;
    private ATMDao atmDao;
    public static final int[] billValues = new int[]{5, 10, 20, 50, 100, 200, 500};
    private final AccountService accountService;
    private static final ATMServiceImpl INSTANCE;

    public static ATMServiceImpl getInstance() {
        return INSTANCE;
    }

    static {
        INSTANCE = new ATMServiceImpl();
    }

    private ATMServiceImpl() {
        accountService = AccountServiceImpl.getInstance();
        atmDao = ATMDaoImpl.getInstance();
    }

    private int calculateBalance() {
        List<ATM> all = atmDao.getAll();
        int balance = 0;
        for (int i = 0; i < all.size(); i++) {
            balance += (all.get(i).getDenomination() * all.get(i).getQuantity());
        }
        return balance;
    }
    private int[] withdrawSpecificAmount(BigDecimal amount) throws ValidationException {
        List<ATM> all = atmDao.getAll();
        int[] billsToWithdraw = new int[all.size()];
        BigDecimal remainingAmount = amount;
        for(int i = all.size() - 1; i >= 0; i--){
            int billValue = all.get(i).getDenomination();
            BigDecimal billValueDecimal = new BigDecimal(billValue);
            int numBillsNeeded = remainingAmount.divideToIntegralValue(billValueDecimal).intValue();
            if(numBillsNeeded > all.get(i).getQuantity()){
                numBillsNeeded = all.get(i).getQuantity();
            }
            billsToWithdraw[i] = numBillsNeeded;
            remainingAmount = remainingAmount.subtract(
                    billValueDecimal.multiply(BigDecimal.valueOf(numBillsNeeded))
            );
        }
        if(remainingAmount.compareTo(BigDecimal.ZERO) != 0){
            throw new ValidationException("The amount is larger than ATM balance!");
        }
        return billsToWithdraw;
    }
    @Override
    public int[] withdraw(int accountId, Amount amount) throws AccountNotFindException, ValidationException {
        if (amount.getValue().compareTo(BigDecimal.valueOf(calculateBalance())) > 0) {
            throw new ValidationException("The amount is larger then ATM balance!");
        }
        int[] billToWithdraw = withdrawSpecificAmount(amount.getValue());
        accountService.withdraw(accountId, amount);
        finalWithdrawSpecificAmount(billToWithdraw);
        return billToWithdraw;
    }

    @Override
    public void deposit(int denomination, int quantity) {
        List<ATM> byDenomination = atmDao.getByDenomination(denomination);
        if (byDenomination.isEmpty()){
            ATM atm = new ATM();
            atm.setDenomination(denomination);
            atm.setQuantity(quantity);
            atmDao.save(atm);
        }else {
            ATM first = byDenomination.getFirst();
            first.setQuantity(first.getQuantity()+quantity);
            atmDao.update(first);
        }
    }
    public List<Integer> getBillValues(){
        List<ATM> all = atmDao.getAll();
        List<Integer> billValues = new ArrayList<>();
        for (ATM atm : all) {
            billValues.add(atm.getDenomination());
        }
        return billValues;
    }

    @Override
    public Boolean login(String userName, String password) {
        return clientService.login(userName, password);
    }

    private void finalWithdrawSpecificAmount(int[] billsToWithdraw) {
        List<ATM> all = atmDao.getAll();
        for (int i = 0; i < all.size(); i++) {
            if(billsToWithdraw[i]>0){
                all.get(i).setQuantity(all.get(i).getQuantity() - billsToWithdraw[i]);
                atmDao.update(all.get(i));
            }
        }
    }

}

