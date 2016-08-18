package com.dsi.dem.dao;

import com.dsi.dem.model.Project;
import com.dsi.dem.model.ProjectClient;
import com.dsi.dem.model.ProjectTeam;

import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public interface ProjectDao {

    boolean saveProject(Project project);
    boolean updateProject(Project project);
    boolean deleteProject(String projectID);
    Project getProjectByID(String projectID);
    Project getProjectByName(String name);
    List<Project> getAllProjects();
    List<Project> searchProjects(String projectName, String status, String clientName, String teamName,
                                 String memberName);

    boolean saveProjectTeam(ProjectTeam projectTeam);
    boolean deleteProjectTeam(String projectID, String projectTeamID);
    List<ProjectTeam> getProjectTeams(String projectID);

    boolean saveProjectClient(ProjectClient projectClient);
    boolean deleteProjectClient(String projectID, String projectClientID);
    List<ProjectClient> getProjectClients(String projectID);
}
