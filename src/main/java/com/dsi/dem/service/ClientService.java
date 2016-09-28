package com.dsi.dem.service;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Client;
import com.dsi.dem.model.ProjectClient;

import java.util.List;

/**
 * Created by sabbir on 7/20/16.
 */
public interface ClientService {

    void saveClient(Client client) throws CustomException;
    void updateClient(Client client) throws CustomException;
    void deleteClient(String clientID) throws CustomException;
    Client getClientByID(String clientID) throws CustomException;
    List<Client> getAllClients() throws CustomException;
    List<Client> searchClients(String clientName, String organization, String clientEmail,
                               String from, String range) throws CustomException;

    void saveClientProject(List<String> projectIds, Client client) throws CustomException;
    void deleteClientProject(String clientProjectID) throws CustomException;
    List<ProjectClient> getClientProjects(String clientID) throws CustomException;
}
