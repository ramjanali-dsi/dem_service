package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.ProjectDao;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Project;
import com.dsi.dem.model.ProjectClient;
import com.dsi.dem.model.ProjectStatus;
import com.dsi.dem.model.ProjectTeam;
import com.dsi.dem.service.impl.CommonService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sabbir on 8/1/16.
 */
public class ProjectDaoImpl extends CommonService implements ProjectDao {

    private static final Logger logger = Logger.getLogger(ProjectDaoImpl.class);

    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void saveProject(Project project) throws CustomException {
        try{
            session.save(project);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateProject(Project project) throws CustomException {
        try{
            session.update(project);

        } catch (Exception e){
            close(session);
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteProject(String projectID) throws CustomException {
        try {
            Query query = session.createQuery("DELETE FROM Project p WHERE p.projectId =:projectID");
            query.setParameter("projectID", projectID);

            query.executeUpdate();

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public Project getProjectByID(String projectID) {
        Query query = session.createQuery("FROM Project p WHERE p.projectId =:projectID");
        query.setParameter("projectID", projectID);

        Project project = (Project) query.uniqueResult();
        if(project != null){
            return project;
        }
        return null;
    }

    @Override
    public Project getProjectByName(String name) {
        Query query = session.createQuery("FROM Project p WHERE p.projectName =:name");
        query.setParameter("name", name);

        Project project = (Project) query.uniqueResult();
        if(project != null){
            return project;
        }
        return null;
    }

    @Override
    public List<Project> getAllProjects() {
        Query query = session.createQuery("FROM Project");

        List<Project> projectList = query.list();
        if(projectList != null){
            return projectList;
        }
        return null;
    }

    @Override
    public List<Project> searchProjects(String projectName, String status, String clientName,
                                        String teamName, String memberName, String from, String range) {

        StringBuilder queryBuilder = new StringBuilder();
        boolean hasClause = false;
        Map<String, String> paramValue = new HashMap<>();

        queryBuilder.append("FROM Project p");

        if(!Utility.isNullOrEmpty(projectName)){
            queryBuilder.append(" WHERE p.projectName like :projectName");
            paramValue.put("projectName", "%" + projectName + "%");
            hasClause = true;
        }

        if(!Utility.isNullOrEmpty(status)){
            if(hasClause){
                queryBuilder.append(" AND p.status.projectStatusName =:status");

            } else {
                queryBuilder.append(" WHERE p.status.projectStatusName =:status");
                hasClause = true;
            }
            paramValue.put("status", status);
        }

        if(!Utility.isNullOrEmpty(clientName)){
            if(hasClause){
                queryBuilder.append(" AND p.projectId in (SELECT pc.project.projectId FROM ProjectClient pc " +
                        "WHERE pc.client.memberName like :clientName)");

            } else {
                queryBuilder.append(" WHERE p.projectId in (SELECT pc.project.projectId FROM ProjectClient pc " +
                        "WHERE pc.client.memberName like :clientName)");
                hasClause = true;
            }
            paramValue.put("clientName", "%" + clientName + "%");
        }

        if(!Utility.isNullOrEmpty(teamName)){
            if(hasClause){
                queryBuilder.append(" AND p.projectId in (SELECT pt.project.projectId FROM ProjectTeam pt " +
                        "WHERE pt.team.name like :teamName)");

            } else {
                queryBuilder.append(" WHERE p.projectId in (SELECT pt.project.projectId FROM ProjectTeam pt " +
                        "WHERE pt.team.name like :teamName)");
                hasClause = true;
            }
            paramValue.put("teamName", "%" + teamName + "%");
        }

        if(!Utility.isNullOrEmpty(memberName)){
            if(hasClause){
                queryBuilder.append(" AND p.projectId in (SELECT pt.project.projectId FROM ProjectTeam pt WHERE pt.team.teamId in " +
                        "(SELECT tm.team.teamId FROM TeamMember tm WHERE tm.employee.firstName like :memberName " +
                        "OR tm.employee.lastName like :memberName OR tm.employee.nickName like :memberName))");

            } else {
                queryBuilder.append(" WHERE p.projectId in (SELECT pt.project.projectId FROM ProjectTeam pt WHERE pt.team.teamId in " +
                        "(SELECT tm.team.teamId FROM TeamMember tm WHERE tm.employee.firstName like :memberName " +
                        "OR tm.employee.lastName like :memberName OR tm.employee.nickName like :memberName))");
                //hasClause = true;
            }
            paramValue.put("memberName", "%" + memberName + "%");
        }

        queryBuilder.append(" ORDER BY p.projectName ASC");

        logger.info("Query builder: " + queryBuilder.toString());
        Query query = session.createQuery(queryBuilder.toString());

        for(Map.Entry<String, String> entry : paramValue.entrySet()){
            query.setParameter(entry.getKey(), entry.getValue());
        }

        if(!Utility.isNullOrEmpty(from) && !Utility.isNullOrEmpty(range))
            query.setFirstResult(Integer.valueOf(from)).setMaxResults(Integer.valueOf(range));

        List<Project> projectList = query.list();
        if(projectList != null){
            return projectList;
        }
        return null;
    }

    @Override
    public ProjectStatus getProjectStatusById(String statusID) {
        Query query = session.createQuery("FROM ProjectStatus ps WHERE ps.projectStatusId =:statusID");
        query.setParameter("statusID", statusID);

        ProjectStatus status = (ProjectStatus) query.uniqueResult();
        if(status != null){
            return status;
        }
        return null;
    }

    @Override
    public void saveProjectTeam(ProjectTeam projectTeam) throws CustomException {
        try{
            session.save(projectTeam);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteProjectTeam(String projectID, String projectTeamID) throws CustomException {
        Query query;
        try {
            if(projectID != null){
                query = session.createQuery("DELETE FROM ProjectTeam pt WHERE pt.project.projectId =:projectID");
                query.setParameter("projectID", projectID);

            } else {
                query = session.createQuery("DELETE FROM ProjectTeam pt WHERE pt.projectTeamId =:projectTeamID");
                query.setParameter("projectTeamID", projectTeamID);
            }

            query.executeUpdate();

        } catch (Exception e) {
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public List<ProjectTeam> getProjectTeams(String projectID, String employeeID) {
        Query query;
        if(employeeID == null) {
            query = session.createQuery("FROM ProjectTeam pt WHERE pt.project.projectId =:projectID");
            query.setParameter("projectID", projectID);

        } else {
            query = session.createQuery("FROM ProjectTeam  pt WHERE pt.team.teamId in " +
                    "(SELECT tm.team.teamId FROM TeamMember tm WHERE tm.employee.employeeId =:employeeID)");
            query.setParameter("employeeID", employeeID);
        }

        List<ProjectTeam> projectTeams = query.list();
        if(projectTeams != null){
            return projectTeams;
        }
        return null;
    }

    @Override
    public void saveProjectClient(ProjectClient projectClient) throws CustomException {
        try{
            session.save(projectClient);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0003);
            throw new CustomException(errorMessage);        }
    }

    @Override
    public void deleteProjectClient(String projectID, String projectClientID) throws CustomException {
        Query query;
        try {
            if(projectID != null){
                query = session.createQuery("DELETE FROM ProjectClient pc WHERE pc.project.projectId =:projectID");
                query.setParameter("projectID", projectID);

            } else {
                query = session.createQuery("DELETE FROM ProjectClient pc WHERE pc.projectClientId =:projectClientID");
                query.setParameter("projectClientID", projectClientID);
            }

            query.executeUpdate();

        } catch (Exception e) {
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0003);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public List<ProjectClient> getProjectClients(String projectID) {
        Query query = session.createQuery("FROM ProjectClient pc WHERE pc.project.projectId =:projectID");
        query.setParameter("projectID", projectID);

        List<ProjectClient> projectClients = query.list();
        if(projectClients != null){
            return projectClients;
        }
        return null;
    }
}
