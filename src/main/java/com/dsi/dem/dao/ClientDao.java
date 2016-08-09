package com.dsi.dem.dao;

import com.dsi.dem.model.Client;

import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public interface ClientDao {

    boolean saveClient(Client client);
    boolean updateClient(Client client);
    boolean deleteClient(Client client);
    Client getClientByID(String clientID);
    Client getClientByName(String name);
    List<Client> getAllClients();
}
