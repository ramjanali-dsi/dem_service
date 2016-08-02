package com.dsi.dem.service.impl;

import com.dsi.dem.dao.ProjectDao;
import com.dsi.dem.dao.impl.ProjectDaoImpl;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Project;
import com.dsi.dem.service.ProjectService;
import org.apache.log4j.Logger;
import scala.collection.immutable.List;

/**
 * Created by sabbir on 8/1/16.
 */
public class ProjectServiceImpl implements ProjectService {

    private static final ProjectDao projectDao = new ProjectDaoImpl();

    @Override
    public void saveProject(Project project) throws CustomException {

    }

    @Override
    public void updateProject(Project project) throws CustomException {

    }

    @Override
    public void deleteProject(Project project) throws CustomException {

    }

    @Override
    public Project getProjectByID(String projectID) throws CustomException {
        return null;
    }

    @Override
    public List<Project> getAllProjects() throws CustomException {
        return null;
    }
}
