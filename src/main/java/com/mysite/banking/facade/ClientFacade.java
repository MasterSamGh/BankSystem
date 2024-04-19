package com.mysite.banking.facade;

import com.mysite.banking.dto.ClientDto;
import com.mysite.banking.model.Client;
import com.mysite.banking.model.FileType;
import com.mysite.banking.service.exception.*;

import java.util.List;

public interface ClientFacade {
    void deleteClientById(Integer id) throws ClientNotFindException;
    List<ClientDto> searchClientByFamily(String family);
    List<ClientDto> searchClientByName(String name);
    ClientDto searchClientByEmail(String email) throws ClientNotFindException;
    List<ClientDto> getActiveClients()throws EmptyClientException;
    List<ClientDto> getDeletedClients()throws EmptyClientException;
    ClientDto getClientById(Integer id) throws ClientNotFindException;
    void addClient(ClientDto client)throws DuplicateClientException , ValidationException;
    void updateClient(ClientDto client)throws ValidationException , ClientNotFindException;
    Boolean login(String userName, String password);

    void exportJson(String fileName);
}
