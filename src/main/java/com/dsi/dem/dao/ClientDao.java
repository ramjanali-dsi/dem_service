package com.dsi.dem.dao;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Client;
import com.dsi.dem.model.ProjectClient;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public interface ClientDao {

    void setSession(Session session);
    void saveClient(Client client) throws CustomException;
    void updateClient(Client client) throws CustomException;
    void deleteClient(String clientID) throws CustomException;
    Client getClientByID(String clientID);
    Client getClientByName(String name);
    List<Client> getAllClients();
    List<Client> searchClients(String clientName, String organization, String clientEmail,
                               String from, String range);

    void saveClientProject(ProjectClient projectClient) throws CustomException;
    void deleteClientProject(String clientID, String projectClientID) throws CustomException;
    List<ProjectClient> getClientProjects(String clientID);
}
