package com.mysite.banking.dao;

import com.mysite.banking.model.Client;
import java.util.List;

public interface ClientDao {
    Integer save(Client client);
    void delete(Client client);
    void update(Client client);
    Client getByID(Integer id);
    List<Client> getByStatus(Boolean deleted);
    List<Client> getAll();
    List<Client> getByFamily(String family);
    Client getFirstByEmail(String email);
    List<Client> getByName(String name);
}
