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

    ProjectDto saveProject(ProjectDto projectDto) throws CustomException;
    ProjectDto updateProject(String projectId, ProjectDto projectDto) throws CustomException;
    void deleteProject(String projectID) throws CustomException;
    ProjectDto getProjectByID(String projectID) throws CustomException;
    List<Project> getAllProjects() throws CustomException;
    List<ProjectDto> searchProjects(String projectName, String status, String clientName, String teamName,
                                 String memberName, String from, String range) throws CustomException;

    List<ProjectTeamDto> createProjectTeams(ProjectDto projectDto, String projectId) throws CustomException;
    void saveProjectTeam(List<String> projectIds, Project project) throws CustomException;
    void deleteProjectTeam(String projectTeamID) throws CustomException;
    List<ProjectTeam> getProjectTeams(String projectID, String employeeID) throws CustomException;

    List<ProjectClientDto> createProjectClients(ProjectDto projectDto, String projectId) throws CustomException;
    void saveProjectClient(List<String> clientIds, Project project) throws CustomException;
    void deleteProjectClient(String projectClientID) throws CustomException;
    List<ProjectClient> getProjectClients(String projectID) throws CustomException;
}
