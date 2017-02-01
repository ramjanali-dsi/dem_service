package com.dsi.dem.service;

import com.dsi.dem.dto.ClientDto;
import com.dsi.dem.dto.ClientProjectDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Client;
import com.dsi.dem.model.ProjectClient;

import java.util.List;

/**
 * Created by sabbir on 7/20/16.
 */
public interface ClientService {

    ClientDto saveClient(ClientDto clientDto, String tenantName) throws CustomException;
    ClientDto updateClient(ClientDto clientDto, String clientId, String tenantName) throws CustomException;
    String deleteClient(String clientID, String tenantName) throws CustomException;
    ClientDto getClientByID(String clientID) throws CustomException;
    List<Client> getAllClients() throws CustomException;
    List<ClientDto> searchClients(String clientName, String organization, String clientEmail,
                               String from, String range) throws CustomException;

    List<ClientProjectDto> createClientProjects(String clientId, List<ClientProjectDto> projectDtoList,
                                                String tenantName) throws CustomException;
    void deleteClientProject(String clientProjectID) throws CustomException;
    List<ProjectClient> getClientProjects(String clientID) throws CustomException;
}
