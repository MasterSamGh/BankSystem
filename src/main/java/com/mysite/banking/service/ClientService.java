package com.mysite.banking.service;

import com.mysite.banking.model.Client;
import com.mysite.banking.model.FileType;
import com.mysite.banking.service.exception.*;

import java.util.List;
public interface ClientService {
    void deleteClientById(Integer id) throws ClientNotFindException;
    List<Client> searchClientsByFamily(String family);

    Client searchClientByEmail(String email) throws ClientNotFindException;

    List<Client> searchClientByName(String name);
    List<Client> getActiveClient() throws EmptyClientException;
    List<Client> getDeletedClient() throws EmptyClientException;
    Client getClientById(Integer id) throws ClientNotFindException;
    void addClient(Client client) ;
    void updateClient(Client client);




    Boolean login(String userName, String password);

    void exportJson(String fileName);
}
