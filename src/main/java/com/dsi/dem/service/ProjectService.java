package com.dsi.dem.service;

import com.dsi.dem.dto.ProjectClientDto;
import com.dsi.dem.dto.ProjectDto;
import com.dsi.dem.dto.ProjectTeamDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Project;
import com.dsi.dem.model.ProjectClient;
import com.dsi.dem.model.ProjectTeam;

import java.util.List;

/**
 * Created by sabbir on 7/20/16.
 */
public interface ProjectService {

    ProjectDto saveProject(ProjectDto projectDto, String tenantName) throws CustomException;
    ProjectDto updateProject(String projectId, ProjectDto projectDto, String tenantName) throws CustomException;
    String deleteProject(String projectID, String tenantName) throws CustomException;
    ProjectDto getProjectByID(String projectID) throws CustomException;
    List<Project> getAllProjects() throws CustomException;
    List<ProjectDto> searchProjects(String projectName, String status, String clientName, String teamName,
                                 String memberName, String from, String range) throws CustomException;

    List<ProjectTeamDto> createProjectTeams(List<ProjectTeamDto> teamDtoList, String projectId) throws CustomException;
    void deleteProjectTeam(String projectTeamID) throws CustomException;
    List<ProjectTeam> getProjectTeams(String projectID, String employeeID) throws CustomException;

    List<ProjectClientDto> createProjectClients(List<ProjectClientDto> clientDtoList, String projectId) throws CustomException;
    void deleteProjectClient(String projectClientID) throws CustomException;
    List<ProjectClient> getProjectClients(String projectID) throws CustomException;
}
