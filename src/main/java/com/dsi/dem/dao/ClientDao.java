package com.dsi.dem.dao;

import com.dsi.dem.model.Client;
import com.dsi.dem.model.ProjectClient;

import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public interface ClientDao {

    boolean saveClient(Client client);
    boolean updateClient(Client client);
    boolean deleteClient(String clientID);
    Client getClientByID(String clientID);
    Client getClientByName(String name);
    List<Client> getAllClients();
    List<Client> searchClients(String clientName, String organization, String clientEmail,
                               String from, String range);

    boolean saveClientProject(ProjectClient projectClient);
    boolean deleteClientProject(String clientID, String projectClientID);
    List<ProjectClient> getClientProjects(String clientID);
}
