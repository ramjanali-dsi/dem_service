package com.dsi.dem.service.impl;

import com.dsi.dem.dao.ClientDao;
import com.dsi.dem.dao.impl.ClientDaoImpl;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Client;
import com.dsi.dem.service.ClientService;
import scala.collection.immutable.List;

/**
 * Created by sabbir on 8/1/16.
 */
public class ClientServiceImpl implements ClientService {

    private static final ClientDao clientDao = new ClientDaoImpl();

    @Override
    public void saveClient(Client client) throws CustomException {

    }

    @Override
    public void updateClient(Client client) throws CustomException {

    }

    @Override
    public void deleteClient(Client client) throws CustomException {

    }

    @Override
    public Client getClientByID(String clientID) throws CustomException {
        return null;
    }

    @Override
    public List<Client> getAllClients() throws CustomException {
        return null;
    }
}
