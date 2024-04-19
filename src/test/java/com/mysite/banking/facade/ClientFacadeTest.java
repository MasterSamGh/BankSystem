package com.mysite.banking.facade;

import com.mysite.banking.dto.RealClientDto;
import com.mysite.banking.facade.impl.ClientFacadeImpl;
import com.mysite.banking.model.RealClient;
import com.mysite.banking.service.exception.ClientNotFindException;
import com.mysite.banking.service.exception.DuplicateClientException;
import com.mysite.banking.service.exception.ValidationException;
import com.mysite.banking.service.impl.ClientServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientFacadeTest {
    @Mock
    private ClientServiceImpl clientService;
    private ClientFacade clientFacade;
    private static final String JDBC_URL = "jdbc:h2:mem:testDB";
    static {
        System.setProperty("CUSTOM_JDBC_URL",JDBC_URL);
    }
    @Before
    public void setup(){
        clientFacade = ClientFacadeImpl.getInstance(clientService);
    }
    @Test
    public void addClientInvalidEmailTest() throws ParseException, ClientNotFindException {
        RealClientDto client =new RealClientDto();
        client.setEmail("test");
        client.setNumber("09113424250");
        client.setName("sam");
        client.setBirthday(new SimpleDateFormat("dd-MM-yyy").parse("11-11-1111"));
        client.setFamily("rad");
        when(clientService.searchClientByEmail("test")).thenThrow(new ClientNotFindException());
        Exception exception = assertThrows(ValidationException.class,()->{
            clientFacade.addClient(client);
        });
        assertEquals("Invalid email format.",exception.getMessage());
    }
    @Test
    public void addClientInvalidFamilyTest() throws ParseException, ClientNotFindException {
        RealClientDto client =new RealClientDto();
        client.setEmail("test@site.com");
        client.setNumber("09113424250");
        client.setName("sam");
        client.setBirthday(new SimpleDateFormat("dd-MM-yyy").parse("11-11-1111"));
        client.setFamily("rad Gh");
        when(clientService.searchClientByEmail("test@site.com")).thenThrow(new ClientNotFindException());
        Exception exception = assertThrows(ValidationException.class,()->{
            clientFacade.addClient(client);
        });
        assertEquals("Family must not be empty or null, and should be in lower case.",exception.getMessage());
    }
    @Test
    public void addClientTest() throws ParseException, ValidationException, DuplicateClientException,ClientNotFindException {
        RealClientDto client =new RealClientDto();
        client.setEmail("test@site.com");
        client.setNumber("09113424250");
        client.setName("sam");
        client.setBirthday(new SimpleDateFormat("dd-MM-yyy").parse("11-11-1111"));
        client.setFamily("rad gh");
        when(clientService.searchClientByEmail("test@site.com")).thenThrow(new ClientNotFindException());
        clientFacade.addClient(client);
        RealClient clientEntity = new RealClient();
        clientEntity.setName("sam");
        clientEntity.setEmail("test@site.com");
        clientEntity.setNumber("09113424250");
        clientEntity.setBirthday(new SimpleDateFormat("dd-MM-yyy").parse("11-11-1111"));
        clientEntity.setFamily("rad gh");
        verify(clientService).addClient(clientEntity);
    }

    @Test
    public void addClientDuplicateEmailTest() throws ParseException, ClientNotFindException {
        RealClientDto client =new RealClientDto();
        client.setId(1);
        client.setEmail("test@site.com");
        client.setNumber("09113424250");
        client.setName("sam");
        client.setBirthday(new SimpleDateFormat("dd-MM-yyy").parse("11-11-1111"));
        client.setFamily("rad");
        when(clientService.searchClientByEmail("test@site.com")).thenReturn(new RealClient());
        Exception exception = assertThrows(ValidationException.class,()->{
            clientFacade.addClient(client);
        });
        assertEquals("Email must not duplicate",exception.getMessage());
    }

}