package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.ProjectDao;
import com.dsi.dem.model.Project;
import com.dsi.dem.model.ProjectTeam;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public class ProjectDaoImpl extends BaseDao implements ProjectDao {

    private static final Logger logger = Logger.getLogger(ProjectDaoImpl.class);

    @Override
    public boolean saveProject(Project project) {
        return save(project);
    }

    @Override
    public boolean updateProject(Project project) {
        return update(project);
    }

    @Override
    public boolean deleteProject(Project project) {
        return delete(project);
    }

    @Override
    public Project getProjectByID(String projectID) {
        Session session = null;
        Project project = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM Project p WHERE p.projectId =:projectID");
            query.setParameter("projectID", projectID);

            project = (Project) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return project;
    }

    @Override
    public List<Project> getAllProjects() {
        Session session = null;
        List<Project> projectList = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM Project");

            projectList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return projectList;
    }
}
