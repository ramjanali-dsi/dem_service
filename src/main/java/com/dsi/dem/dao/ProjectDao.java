package com.dsi.dem.dao;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Project;
import com.dsi.dem.model.ProjectClient;
import com.dsi.dem.model.ProjectStatus;
import com.dsi.dem.model.ProjectTeam;
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

    ProjectStatus getProjectStatusById(String statusID);

    void saveProjectTeam(ProjectTeam projectTeam) throws CustomException;
    void deleteProjectTeam(String projectID, String projectTeamID) throws CustomException;
    List<ProjectTeam> getProjectTeams(String projectID, String employeeID);

    void saveProjectClient(ProjectClient projectClient) throws CustomException;
    void deleteProjectClient(String projectID, String projectClientID) throws CustomException;
    List<ProjectClient> getProjectClients(String projectID);
}
