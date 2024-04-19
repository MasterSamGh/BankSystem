package com.mysite.banking.dao;

import com.mysite.banking.model.Account;
import java.util.List;

public interface AccountDao {
    Integer save(Account account);
    void delete(Account account);
    void update(Account account);
    Account getByID(Integer id);
    List<Account> getByStatus(Boolean deleted);
    List<Account> getByClientId(Integer clientId);
    List<Account> getAll();
}
