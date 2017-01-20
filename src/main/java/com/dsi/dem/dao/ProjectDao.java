package com.dsi.dem.dao;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.*;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public interface ProjectDao {

    void setSession(Session session);
    void saveProject(Project project) throws CustomException;
    void updateProject(Project project) throws CustomException;
    void deleteProject(String projectID) throws CustomException;
    Project getProjectByID(String projectID);
    Project getProjectByName(String name);
    List<Project> getAllProjects();
    List<Project> searchProjects(String projectName, String status, String clientName, String teamName,
                                 String memberName, String from, String range);

    List<TeamMember> getTeamMembersByProjectId(String projectId);
    ProjectStatus getProjectStatusById(String statusID);

    void saveProjectTeam(ProjectTeam projectTeam) throws CustomException;
    void deleteProjectTeam(String projectID, String projectTeamID) throws CustomException;
    List<ProjectTeam> getProjectTeams(String projectID, String employeeID);
    ProjectTeam getProjectTeamByTeamIdAndProjectId(String teamId, String projectId);

    void saveProjectClient(ProjectClient projectClient) throws CustomException;
    void deleteProjectClient(String projectID, String projectClientID) throws CustomException;
    void deleteProjectClientByClientId(String projectID, String clientID) throws CustomException;
    List<ProjectClient> getProjectClients(String projectID);
    ProjectClient getProjectClientByClientIdAndProjectId(String clientId, String projectId);
}
