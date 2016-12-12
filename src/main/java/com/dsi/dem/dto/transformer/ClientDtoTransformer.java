package com.dsi.dem.dto.transformer;

import com.dsi.dem.dto.ClientDto;
import com.dsi.dem.dto.ClientProjectDto;
import com.dsi.dem.dto.ProjectClientDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Client;
import com.dsi.dem.model.ProjectClient;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 8/9/16.
 */
public class ClientDtoTransformer {

    public Client getClient(ClientDto clientDto) throws CustomException {

        Client client = new Client();
        try {
            BeanUtils.copyProperties(client, clientDto);

        } catch (Exception e) {
            e.printStackTrace();
            //ErrorContext ErrorTypeConstans = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return client;
    }

    public List<ClientDto> getClientsDto(List<Client> clients) throws CustomException {

        List<ClientDto> clientDtoList = new ArrayList<>();
        for(Client client : clients){
            clientDtoList.add(getClientDto(client));
        }
        return clientDtoList;
    }

    public ClientDto getClientDto(Client client) throws CustomException {

        ClientDto clientDto = new ClientDto();
        try{
            BeanUtils.copyProperties(clientDto, client);

            List<ClientProjectDto> dtoList = new ArrayList<>();
            for(ProjectClient projectClient : client.getProjects()){
                dtoList.add(getClientProjectDto(projectClient));
            }
            clientDto.setProjectList(dtoList);

        } catch (Exception e){
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return clientDto;
    }

    public ClientProjectDto getClientProjectDto(ProjectClient projectClient) throws CustomException {

        ClientProjectDto clientProjectDto = new ClientProjectDto();
        try{
            BeanUtils.copyProperties(clientProjectDto, projectClient);
            BeanUtils.copyProperties(clientProjectDto, projectClient.getProject());
            BeanUtils.copyProperties(clientProjectDto, projectClient.getProject().getStatus());

        } catch (Exception e){
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return clientProjectDto;
    }

    public List<ClientProjectDto> getClientProjectsDto(List<ProjectClient> projectClients) throws CustomException {

        List<ClientProjectDto> clientProjectDtoList = new ArrayList<>();
        for(ProjectClient projectClient : projectClients){
            clientProjectDtoList.add(getClientProjectDto(projectClient));
        }
        return clientProjectDtoList;
    }
}
