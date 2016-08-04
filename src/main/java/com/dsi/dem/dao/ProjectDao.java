package com.dsi.dem.dao;

import com.dsi.dem.model.Project;
import com.dsi.dem.model.ProjectTeam;

import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public interface ProjectDao {

    boolean saveProject(Project project);
    boolean updateProject(Project project);
    boolean deleteProject(Project project);
    Project getProjectByID(String projectID);
    List<Project> getAllProjects();

    boolean saveProjectTeam(ProjectTeam projectTeam);
    boolean updateProjectTeam(ProjectTeam projectTeam);
    boolean deleteProjectTeam(String projectTeamID);
    ProjectTeam getProjectTeamByTeamIDAndProjectID(String teamID, String projectID);
    List<ProjectTeam> getProjectTeamsByTeamID(String teamID);
    List<ProjectTeam> getProjectTeamsByProjectID(String projectID);
}
