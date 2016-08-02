package com.dsi.dem.service;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Project;
import scala.collection.LinearSeq;
import scala.collection.immutable.List;

/**
 * Created by sabbir on 7/20/16.
 */
public interface ProjectService {

    void saveProject(Project project) throws CustomException;
    void updateProject(Project project) throws CustomException;
    void deleteProject(Project project) throws CustomException;
    Project getProjectByID(String projectID) throws CustomException;
    List<Project> getAllProjects() throws CustomException;
}
