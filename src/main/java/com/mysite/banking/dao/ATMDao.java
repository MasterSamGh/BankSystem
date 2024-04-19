package com.mysite.banking.dao;

import com.mysite.banking.model.ATM;
import java.util.List;

public interface ATMDao {
    List<ATM> getAll();
    Long save(ATM atm);
    void update(ATM atm);
    List<ATM> getByDenomination(int denomination);
}
