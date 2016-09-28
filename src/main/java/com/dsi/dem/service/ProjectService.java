package com.dsi.dem.service;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Project;
import com.dsi.dem.model.ProjectClient;
import com.dsi.dem.model.ProjectTeam;

import java.util.List;

/**
 * Created by sabbir on 7/20/16.
 */
public interface ProjectService {

    void saveProject(Project project) throws CustomException;
    void updateProject(Project project) throws CustomException;
    void deleteProject(String projectID) throws CustomException;
    Project getProjectByID(String projectID) throws CustomException;
    List<Project> getAllProjects() throws CustomException;
    List<Project> searchProjects(String projectName, String status, String clientName, String teamName,
                                 String memberName, String from, String range) throws CustomException;

    void saveProjectTeam(List<String> projectIds, Project project) throws CustomException;
    void deleteProjectTeam(String projectTeamID) throws CustomException;
    List<ProjectTeam> getProjectTeams(String projectID) throws CustomException;

    void saveProjectClient(List<String> clientIds, Project project) throws CustomException;
    void deleteProjectClient(String projectClientID) throws CustomException;
    List<ProjectClient> getProjectClients(String projectID) throws CustomException;
}
