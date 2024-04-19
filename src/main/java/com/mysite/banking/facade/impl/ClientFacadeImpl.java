package com.mysite.banking.facade.impl;

import com.mysite.banking.dto.ClientDto;
import com.mysite.banking.facade.ClientFacade;
import com.mysite.banking.mapper.ClientMapstruct;
import com.mysite.banking.model.Client;
import com.mysite.banking.model.FileType;
import com.mysite.banking.service.ClientService;
import com.mysite.banking.service.exception.*;
import com.mysite.banking.service.impl.ClientServiceImpl;
import com.mysite.banking.service.validation.ValidationContext;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class ClientFacadeImpl implements ClientFacade {
    private ValidationContext<ClientDto> validationContext;
    private ClientService clientService;
    private final ClientMapstruct clientMapstruct;
    private static final ClientFacadeImpl INSTANCE;
    public static ClientFacadeImpl getInstance(){
        return INSTANCE;
    }
    public static ClientFacadeImpl getInstance(ClientService clientService){
        INSTANCE.validationContext = new ClientValidationContext(INSTANCE);
        INSTANCE.clientService = clientService;
        return INSTANCE;
    }
    static {
        INSTANCE = new ClientFacadeImpl();
    }

    private ClientFacadeImpl() {
        this.clientMapstruct = Mappers.getMapper(ClientMapstruct.class);
        this.clientService = ClientServiceImpl.getInstance();
        this.validationContext = new ClientValidationContext(this);
    }

    @Override
    public void deleteClientById(Integer id) throws ClientNotFindException {
        clientService.deleteClientById(id);
    }

    @Override
    public List<ClientDto> searchClientByFamily(String family) {
        return clientMapstruct.mapToClientDtoList(
                clientService.searchClientsByFamily(family)
        );
    }

    @Override
    public List<ClientDto> searchClientByName(String name) {
        return clientMapstruct.mapToClientDtoList(
                clientService.searchClientByName(name)
        );
    }

    @Override
    public ClientDto searchClientByEmail(String email) throws ClientNotFindException {
        return clientMapstruct.mapToClientDto(clientService.searchClientByEmail(email));
    }

    @Override
    public List<ClientDto> getActiveClients() throws EmptyClientException {
        return clientMapstruct.mapToClientDtoList(
                clientService.getActiveClient());
    }

    @Override
    public List<ClientDto> getDeletedClients() throws EmptyClientException {
        return clientMapstruct.mapToClientDtoList(
                clientService.getDeletedClient());
    }

    @Override
    public ClientDto getClientById(Integer id) throws ClientNotFindException {
        return clientMapstruct.mapToClientDto(clientService.getClientById(id));
    }

    @Override
    public void addClient(ClientDto clientDto) throws DuplicateClientException, ValidationException  {
        validationContext.validate(clientDto);
        clientService.addClient(clientMapstruct.mapToClient(clientDto));
    }

    @Override
    public void updateClient(ClientDto clientDto) throws ValidationException, ClientNotFindException {
        validationContext.validate(clientDto);
        Client client = clientService.getClientById(clientDto.getId());
        clientMapstruct.mapToClient(clientDto,client);
        clientService.updateClient(client);
    }
    @Override
    public Boolean login(String userName, String password) {
        return clientService.login(userName,password);
    }

    @Override
    public void exportJson(String fileName) {
        clientService.exportJson(fileName);
    }
}
