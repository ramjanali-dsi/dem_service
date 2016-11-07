package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.ProjectDao;
import com.dsi.dem.model.Project;
import com.dsi.dem.model.ProjectClient;
import com.dsi.dem.model.ProjectTeam;
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
    public boolean deleteProject(String projectID) {
        Session session = null;
        boolean success = true;
        try {
            session = getSession();
            Query query = session.createQuery("DELETE FROM Project p WHERE p.projectId =:projectID");
            query.setParameter("projectID", projectID);

            if(query.executeUpdate() > 0){
                success = true;

            } else {
                success = false;
            }

        } catch (Exception e) {
            logger.error("Database error occurs when delete: " + e.getMessage());
            success = false;

        } finally {
            if(session != null) {
                close(session);
            }
        }
        return success;
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
    public Project getProjectByName(String name) {
        Session session = null;
        Project project = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM Project p WHERE p.projectName =:name");
            query.setParameter("name", name);

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

    @Override
    public List<Project> searchProjects(String projectName, String status, String clientName,
                                        String teamName, String memberName, String from, String range) {

        Session session = null;
        List<Project> projectList = null;
        StringBuilder queryBuilder = new StringBuilder();
        boolean hasClause = false;
        Map<String, String> paramValue = new HashMap<>();
        try{
            session = getSession();
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
                paramValue.put("clientName", clientName);
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
                paramValue.put("teamName", teamName);
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
                paramValue.put("memberName", memberName);
            }

            queryBuilder.append(" ORDER BY p.projectName ASC");

            logger.info("Query builder: " + queryBuilder.toString());
            Query query = session.createQuery(queryBuilder.toString());

            for(Map.Entry<String, String> entry : paramValue.entrySet()){
                query.setParameter(entry.getKey(), entry.getValue());
            }

            if(!Utility.isNullOrEmpty(from) && !Utility.isNullOrEmpty(range))
                query.setFirstResult(Integer.valueOf(from)).setMaxResults(Integer.valueOf(range));

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

    @Override
    public boolean saveProjectTeam(ProjectTeam projectTeam) {
        return save(projectTeam);
    }

    @Override
    public boolean deleteProjectTeam(String projectID, String projectTeamID) {
        Session session = null;
        boolean success = true;
        Query query;
        try {
            session = getSession();
            if(projectID != null){
                query = session.createQuery("DELETE FROM ProjectTeam pt WHERE pt.project.projectId =:projectID");
                query.setParameter("projectID", projectID);

            } else {
                query = session.createQuery("DELETE FROM ProjectTeam pt WHERE pt.projectTeamId =:projectTeamID");
                query.setParameter("projectTeamID", projectTeamID);
            }

            if(query.executeUpdate() > 0){
                success = true;

            } else {
                success = false;
            }

        } catch (Exception e) {
            logger.error("Database error occurs when delete: " + e.getMessage());
            success = false;

        } finally {
            if(session != null) {
                close(session);
            }
        }
        return success;
    }

    @Override
    public List<ProjectTeam> getProjectTeams(String projectID, String employeeID) {
        Session session = null;
        List<ProjectTeam> projectTeams = null;
        Query query;
        try{
            session = getSession();
            if(employeeID == null) {
                query = session.createQuery("FROM ProjectTeam pt WHERE pt.project.projectId =:projectID");
                query.setParameter("projectID", projectID);

            } else {
                query = session.createQuery("FROM ProjectTeam  pt WHERE pt.team.teamId in " +
                        "(SELECT tm.team.teamId FROM TeamMember tm WHERE tm.employee.employeeId =:employeeID)");
                query.setParameter("employeeID", employeeID);
            }

            projectTeams = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return projectTeams;
    }

    @Override
    public boolean saveProjectClient(ProjectClient projectClient) {
        return save(projectClient);
    }

    @Override
    public boolean deleteProjectClient(String projectID, String projectClientID) {
        Session session = null;
        boolean success = true;
        Query query;
        try {
            session = getSession();
            if(projectID != null){
                query = session.createQuery("DELETE FROM ProjectClient pc WHERE pc.project.projectId =:projectID");
                query.setParameter("projectID", projectID);

            } else {
                query = session.createQuery("DELETE FROM ProjectClient pc WHERE pc.projectClientId =:projectClientID");
                query.setParameter("projectClientID", projectClientID);
            }

            if(query.executeUpdate() > 0){
                success = true;

            } else {
                success = false;
            }

        } catch (Exception e) {
            logger.error("Database error occurs when delete: " + e.getMessage());
            success = false;

        } finally {
            if(session != null) {
                close(session);
            }
        }
        return success;
    }

    @Override
    public List<ProjectClient> getProjectClients(String projectID) {
        Session session = null;
        List<ProjectClient> projectClients = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM ProjectClient pc WHERE pc.project.projectId =:projectID");
            query.setParameter("projectID", projectID);

            projectClients = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return projectClients;
    }
}
