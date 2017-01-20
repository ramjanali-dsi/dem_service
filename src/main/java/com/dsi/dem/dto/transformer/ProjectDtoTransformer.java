package com.dsi.dem.dto.transformer;

import com.dsi.dem.dto.ProjectClientDto;
import com.dsi.dem.dto.ProjectDto;
import com.dsi.dem.dto.ProjectTeamDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 8/8/16.
 */
public class ProjectDtoTransformer {

    public Project getProject(ProjectDto projectDto) throws CustomException {

        Project project = new Project();
        try {
            BeanUtils.copyProperties(project, projectDto);

            ProjectStatus status = new ProjectStatus();
            BeanUtils.copyProperties(status, projectDto);
            project.setStatus(status);

            List<ProjectTeam> projectTeams = new ArrayList<>();
            for(ProjectTeamDto projectTeamDto : projectDto.getTeamList()){
                projectTeams.add(getProjectTeam(projectTeamDto));
            }
            project.setTeams(projectTeams);

            List<ProjectClient> projectClients = new ArrayList<>();
            for(ProjectClientDto projectClientDto : projectDto.getClientList()){
                projectClients.add(getProjectClient(projectClientDto));
            }
            project.setClients(projectClients);

        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return project;
    }

    public ProjectDto getProjectDto(Project project) throws CustomException {

        ProjectDto projectDto = new ProjectDto();
        try{
            BeanUtils.copyProperties(projectDto, project);
            BeanUtils.copyProperties(projectDto, project.getStatus());
            projectDto.setDescription(project.getDescription());

            List<ProjectTeamDto> teamDtoList = new ArrayList<>();
            for(ProjectTeam projectTeam : project.getTeams()){
                teamDtoList.add(getProjectTeamDto(projectTeam));
            }
            projectDto.setTeamList(teamDtoList);

            List<ProjectClientDto> clientDtoList = new ArrayList<>();
            for(ProjectClient projectClient : project.getClients()){
                clientDtoList.add(getProjectClientDto(projectClient));
            }
            projectDto.setClientList(clientDtoList);

        } catch (Exception e){
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return projectDto;
    }

    public List<ProjectDto> getProjectsDto(List<Project> projects) throws CustomException {

        List<ProjectDto> projectDtoList = new ArrayList<>();
        for(Project project : projects){
            projectDtoList.add(getProjectDto(project));
        }
        return projectDtoList;
    }

    private ProjectTeam getProjectTeam(ProjectTeamDto projectTeamDto) throws CustomException {

        ProjectTeam projectTeam = new ProjectTeam();
        try{
            BeanUtils.copyProperties(projectTeam, projectTeamDto);

            Team team = new Team();
            BeanUtils.copyProperties(team, projectTeamDto);
            projectTeam.setTeam(team);

        } catch (Exception e){
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return projectTeam;
    }

    public List<ProjectTeam> getProjectTeams(List<ProjectTeamDto> projectTeamDtoList) throws CustomException {
        List<ProjectTeam> projectTeams = new ArrayList<>();
        for(ProjectTeamDto projectTeamDto : projectTeamDtoList){
            projectTeams.add(getProjectTeam(projectTeamDto));
        }
        return projectTeams;
    }

    private ProjectTeamDto getProjectTeamDto(ProjectTeam projectTeam) throws CustomException {

        ProjectTeamDto teamDto = new ProjectTeamDto();
        try{
            BeanUtils.copyProperties(teamDto, projectTeam);
            BeanUtils.copyProperties(teamDto, projectTeam.getTeam());

        } catch (Exception e){
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return teamDto;
    }

    public List<ProjectTeamDto> getProjectTeamsDto(List<ProjectTeam> projectTeams) throws CustomException {

        List<ProjectTeamDto> teamDtoList = new ArrayList<>();
        for(ProjectTeam projectTeam : projectTeams){
            teamDtoList.add(getProjectTeamDto(projectTeam));
        }
        return teamDtoList;
    }

    private ProjectClient getProjectClient(ProjectClientDto projectClientDto) throws CustomException {

        ProjectClient projectClient = new ProjectClient();
        try{
            BeanUtils.copyProperties(projectClient, projectClientDto);

            Client client = new Client();
            BeanUtils.copyProperties(client, projectClientDto);
            projectClient.setClient(client);

        } catch (Exception e){
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return projectClient;
    }

    public List<ProjectClient> getProjectClients(List<ProjectClientDto> projectClientDtoList) throws CustomException {
        List<ProjectClient> projectClients = new ArrayList<>();
        for(ProjectClientDto clientDto : projectClientDtoList) {
            projectClients.add(getProjectClient(clientDto));
        }
        return projectClients;
    }

    private ProjectClientDto getProjectClientDto(ProjectClient projectClient) throws CustomException {

        ProjectClientDto clientDto = new ProjectClientDto();
        try{
            BeanUtils.copyProperties(clientDto, projectClient);
            BeanUtils.copyProperties(clientDto, projectClient.getClient());

        } catch (Exception e){
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return clientDto;
    }

    public List<ProjectClientDto> getProjectClientsDto(List<ProjectClient> projectClients) throws CustomException {

        List<ProjectClientDto> clientDtoList = new ArrayList<>();
        for(ProjectClient projectClient : projectClients){
            clientDtoList.add(getProjectClientDto(projectClient));
        }
        return clientDtoList;
    }
}
