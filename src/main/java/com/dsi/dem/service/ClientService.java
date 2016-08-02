package com.dsi.dem.service;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Client;
import scala.collection.immutable.List;

/**
 * Created by sabbir on 7/20/16.
 */
public interface ClientService {

    void saveClient(Client client) throws CustomException;
    void updateClient(Client client) throws CustomException;
    void deleteClient(Client client) throws CustomException;
    Client getClientByID(String clientID) throws CustomException;
    List<Client> getAllClients() throws CustomException;
}
