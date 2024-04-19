package com.mysite.banking.facade;

import com.mysite.banking.dto.AccountDto;
import com.mysite.banking.dto.AmountDto;
import com.mysite.banking.facade.impl.AccountFacadeImpl;
import com.mysite.banking.facade.impl.ClientFacadeImpl;
import com.mysite.banking.model.Account;
import com.mysite.banking.model.RealClient;
import com.mysite.banking.service.exception.ClientNotFindException;
import com.mysite.banking.service.exception.DuplicateAccountException;
import com.mysite.banking.service.exception.ValidationException;
import com.mysite.banking.service.impl.AccountServiceImpl;
import com.mysite.banking.service.impl.ClientServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.math.BigDecimal;
import java.util.Currency;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountFacadeTest {
    @Mock
    private AccountServiceImpl accountService;
    private AccountFacade accountFacade;
    @Mock
    private ClientServiceImpl clientService;
    private static final String JDBC_URL = "jdbc:h2:mem:testDB";
    static {
        System.setProperty("CUSTOM_JDBC_URL",JDBC_URL);
    }
    @Before
    public void setup(){

        accountFacade = AccountFacadeImpl.getInstance();
        ClientFacadeImpl.getInstance(clientService);
        AccountFacadeImpl.getInstance(accountService);
    }
    @Test
    public void addAccountTest() throws ClientNotFindException {
        AccountDto accountDto =new AccountDto();
        accountDto.setClientId(12);
        accountDto.setBalance(new AmountDto(Currency.getInstance("EUR"),BigDecimal.ZERO));
        when(clientService.getClientById(12)).thenThrow(new ClientNotFindException());
        Exception exception = assertThrows(ValidationException.class,()-> accountFacade.addAccount(accountDto));
        assertEquals("Client Id is not valid.",exception.getMessage());
    }
    @Test
    public void addAccount() throws ClientNotFindException, ValidationException, DuplicateAccountException {
        AccountDto accountDto =new AccountDto();
        accountDto.setClientId(12);
        accountDto.setBalance(new AmountDto(Currency.getInstance("EUR"),BigDecimal.ZERO));
        when(clientService.getClientById(12)).thenReturn(new RealClient());
        accountFacade.addAccount(accountDto);
        Account account = new Account();
        account.setClientId(12);
        accountDto.setBalance(new AmountDto(Currency.getInstance("EUR"),BigDecimal.ZERO));
        verify(accountService).addAccount(any(Account.class));
    }
}
