package com.mysite.banking.service.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.banking.dao.ClientDao;
import com.mysite.banking.dao.impl.ClientDaoImpl;
import com.mysite.banking.model.Client;
import com.mysite.banking.service.ClientService;
import com.mysite.banking.service.exception.ClientNotFindException;
import com.mysite.banking.service.exception.EmptyClientException;
import com.mysite.banking.util.GlobalAttributes;
import com.mysite.banking.util.MapperWrapper;
import com.mysite.banking.util.PasswordEncoderUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ClientServiceImpl implements ClientService {
    private ClientDao clientDao;
    private final ObjectMapper objectMapper;
    private final GlobalAttributes globalAttributes;

    private final static ClientServiceImpl INSTANCE;

    public static ClientServiceImpl getInstance() {
        return INSTANCE;
    }

    static {
        INSTANCE = new ClientServiceImpl();
    }

    private ClientServiceImpl() {
        globalAttributes = GlobalAttributes.getInstance();
        clientDao = ClientDaoImpl.getInstance();
        objectMapper = MapperWrapper.getInstance();
    }

    @Override
    public List<Client> getActiveClient() throws EmptyClientException {
        List<Client> clientList = clientDao.getByStatus(false);
        if (clientList.isEmpty()) {
            throw new EmptyClientException();
        }
        return clientList;
    }

    @Override
    public List<Client> getDeletedClient() throws EmptyClientException {
        List<Client> clientList = clientDao.getByStatus(true);
        if (clientList.isEmpty()) {
            throw new EmptyClientException();
        }
        return clientList;
    }

    @Override
    public Client getClientById(Integer id) throws ClientNotFindException {
        Client client = clientDao.getByID(id);
        if (client == null) {
            throw new ClientNotFindException();
        } else {
            return client;
        }
    }

    @Override
    public void addClient(Client client) {
        Integer id = clientDao.save(client);
        client.setPassword(PasswordEncoderUtil.encodePassword(client.getPassword(), id));
        clientDao.update(client);
    }

    @Override
    public void updateClient(Client client) {
        clientDao.update(client);
    }


    @Override
    public Boolean login(String userName, String password){
        try {
            Client client = searchClientByEmail(userName);
            if (Objects.equals(
                    client.getPassword(),
                    PasswordEncoderUtil.encodePassword(password, client.getId()))) {
                globalAttributes.setClientId(client.getId());
                return true;
            }
            else {
                globalAttributes.setClientId(null);
                return false;
            }
        } catch (ClientNotFindException e) {
            globalAttributes.setClientId(null);
            return false;
        }
    }



    @Override
    public void exportJson(String fileName) {
        File file = new File(fileName+".json");
        try {
            file.createNewFile();
            List<Client> clientList = clientDao.getAll();
            objectMapper.writeValue(file,clientList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteClientById(Integer id) throws ClientNotFindException {
        Client clientById = getClientById(id);
        clientDao.delete(clientById);
    }
    @Override
    public List<Client> searchClientsByFamily(String family) {
        return clientDao.getByFamily(family);


    }
    @Override
    public Client searchClientByEmail(String email) throws ClientNotFindException {
        Client firstByEmail = clientDao.getFirstByEmail(email);
        if (firstByEmail == null){
            throw new ClientNotFindException();
        }else {
            return firstByEmail;
        }
    }
    @Override
    public List<Client> searchClientByName(String name) {
        return clientDao.getByName(name);
    }
}
