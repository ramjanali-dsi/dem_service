package com.dsi.dem.dao;

import com.dsi.dem.model.Project;

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
}
