package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.TeamDao;
import com.dsi.dem.model.ProjectTeam;
import com.dsi.dem.model.Team;
import com.dsi.dem.model.TeamMember;
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
public class TeamDaoImpl extends BaseDao implements TeamDao {

    private static final Logger logger = Logger.getLogger(TeamDaoImpl.class);

    @Override
    public boolean saveTeam(Team team) {
        return save(team);
    }

    @Override
    public boolean updateTeam(Team team) {
        return update(team);
    }

    @Override
    public boolean deleteTeam(String teamID) {
        Session session = null;
        boolean success = true;
        try {
            session = getSession();
            Query query = session.createQuery("DELETE FROM Team t WHERE t.teamId =:teamID");
            query.setParameter("teamID", teamID);

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
    public Team getTeamByID(String teamID) {
        Session session = null;
        Team team = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM Team t WHERE t.teamId =:teamID");
            query.setParameter("teamID", teamID);

            team = (Team) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return team;
    }

    @Override
    public Team getTeamByName(String teamName) {
        Session session = null;
        Team team = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM Team t WHERE t.name =:name");
            query.setParameter("name", teamName);

            team = (Team) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return team;
    }

    @Override
    public List<Team> getAllTeams() {
        Session session = null;
        List<Team> teamList = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM Team");

            teamList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return teamList;
    }

    @Override
    public List<Team> searchTeams(String teamName, String status, String floor, String room, String memberName,
                                  String projectName, String clientName) {

        Session session = null;
        List<Team> teamList = null;
        StringBuilder queryBuilder = new StringBuilder();
        boolean hasClause = false;
        Map<String, String> paramValue = new HashMap<>();
        try{
            session = getSession();
            queryBuilder.append("FROM Team ");

            if(!Utility.isNullOrEmpty(teamName)){
                queryBuilder.append("t WHERE t.name like :teamName");
                paramValue.put("teamName", "%" + teamName + "%");
                hasClause = true;
            }

            if(!Utility.isNullOrEmpty(status)){
                if(hasClause){
                    queryBuilder.append(" AND t.isActive =:active");

                } else {
                    queryBuilder.append("t WHERE t.isActive =:active");
                    hasClause = true;
                }
                paramValue.put("active", status);
            }

            if(!Utility.isNullOrEmpty(floor)){
                if(hasClause){
                    queryBuilder.append(" AND t.floor =:floor");

                } else {
                    queryBuilder.append("t WHERE t.floor =:floor");
                    hasClause = true;
                }
                paramValue.put("floor", floor);
            }

            if(!Utility.isNullOrEmpty(room)){
                if(hasClause){
                    queryBuilder.append(" AND t.room =:room");

                } else {
                    queryBuilder.append("t WHERE t.room =:room");
                    hasClause = true;
                }
                paramValue.put("room", room);
            }

            if(!Utility.isNullOrEmpty(memberName)){
                if(hasClause){
                    queryBuilder.append(" AND t.teamId in (SELECT tm.team.teamId FROM TeamMember tm WHERE " +
                            "tm.employee.firstName like :memberName OR tm.employee.lastName like :memberName OR tm.employee.nickName like :memberName)");

                } else {
                    queryBuilder.append("t WHERE t.teamId in (SELECT tm.team.teamId FROM TeamMember tm WHERE " +
                            "tm.employee.firstName like :memberName OR tm.employee.lastName like :memberName OR tm.employee.nickName like :memberName)");
                    hasClause = true;
                }
                paramValue.put("memberName", "%" + memberName + "%");
            }

            if(!Utility.isNullOrEmpty(projectName)){
                if(hasClause){
                    queryBuilder.append(" AND t.teamId in (SELECT pt.team.teamId FROM ProjectTeam pt WHERE " +
                            "pt.project.projectName like :projectName)");

                } else {
                    queryBuilder.append("t WHERE t.teamId in (SELECT pt.team.teamId FROM ProjectTeam pt WHERE " +
                            "pt.project.projectName like :projectName)");
                    hasClause = true;
                }
                paramValue.put("projectName", "%" + projectName + "%");
            }

            if(!Utility.isNullOrEmpty(clientName)){
                if(hasClause){
                    queryBuilder.append(" AND t.teamId in (SELECT pt.team.teamId FROM ProjectTeam pt WHERE pt.project.projectId in " +
                            "(SELECT pc.project.projectId FROM ProjectClient pc WHERE pc.client.memberName like :clientName))");

                } else {
                    queryBuilder.append("t WHERE t.teamId in (SELECT pt.team.teamId FROM ProjectTeam pt WHERE pt.project.projectId in " +
                            "(SELECT pc.project.projectId FROM ProjectClient pc WHERE pc.client.memberName like :clientName))");
                    //hasClause = true;
                }
                paramValue.put("clientName", "%" + clientName + "%");
            }

            logger.info("Query builder: " + queryBuilder.toString());
            Query query = session.createQuery(queryBuilder.toString());

            for(Map.Entry<String, String> entry : paramValue.entrySet()){
                if(entry.getKey().equals("active")){
                    query.setParameter(entry.getKey(), entry.getValue().equals("true"));

                } else {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }

            teamList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return teamList;
    }

    @Override
    public boolean saveTeamMember(TeamMember teamMember) {
        return save(teamMember);
    }

    @Override
    public boolean updateTeamMember(TeamMember teamMember) {
        return update(teamMember);
    }

    @Override
    public boolean deleteTeamMember(String teamID, String teamMemberID) {
        Session session = null;
        boolean success = true;
        Query query;
        try {
            session = getSession();
            if(teamID != null){
                query = session.createQuery("DELETE FROM TeamMember tm WHERE tm.team.teamId =:teamID");
                query.setParameter("teamID", teamID);

            } else {
                query = session.createQuery("DELETE FROM TeamMember tm WHERE tm.teamMemberId =:teamMemberID");
                query.setParameter("teamMemberID", teamMemberID);
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
    public TeamMember getTeamMemberByTeamIDAndMemberID(String teamID, String memberID) {
        Session session = null;
        TeamMember teamMember = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM TeamMember tm WHERE tm.team.teamId =:teamID AND tm.employee.employeeId =:employeeID");
            query.setParameter("teamID", teamID);
            query.setParameter("employeeID", memberID);

            teamMember = (TeamMember) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return teamMember;
    }

    @Override
    public List<TeamMember> getTeamMembers(String teamID) {
        Session session = null;
        List<TeamMember> teamMemberList = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM TeamMember tm WHERE tm.team.teamId =:teamID");
            query.setParameter("teamID", teamID);

            teamMemberList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return teamMemberList;
    }

    @Override
    public boolean saveTeamProject(ProjectTeam projectTeam) {
        return save(projectTeam);
    }

    @Override
    public boolean updateTeamProject(ProjectTeam projectTeam) {
        return update(projectTeam);
    }

    @Override
    public boolean deleteProjectTeam(String teamID, String teamProjectID) {
        Session session = null;
        boolean success = true;
        Query query;
        try {
            session = getSession();
            if(teamID != null){
                query = session.createQuery("DELETE FROM ProjectTeam pm WHERE pm.team.teamId =:teamID");
                query.setParameter("teamID", teamID);

            } else {
                query = session.createQuery("DELETE FROM ProjectTeam pm WHERE pm.projectTeamId =:teamProjectID");
                query.setParameter("teamProjectID", teamProjectID);
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
    public List<ProjectTeam> getProjectTeams(String teamID) {
        Session session = null;
        List<ProjectTeam> projectTeamList = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM ProjectTeam pm WHERE pm.team.teamId =:teamID");
            query.setParameter("teamID", teamID);

            projectTeamList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return projectTeamList;
    }
}
