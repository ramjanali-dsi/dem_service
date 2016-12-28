package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.TeamDao;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.ProjectTeam;
import com.dsi.dem.model.RoleType;
import com.dsi.dem.model.Team;
import com.dsi.dem.model.TeamMember;
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
public class TeamDaoImpl extends CommonService implements TeamDao {

    private static final Logger logger = Logger.getLogger(TeamDaoImpl.class);

    private Session session;

    @Override
    public void setSession(Session session){
        this.session = session;
    }

    @Override
    public void saveTeam(Team team) throws CustomException {
        try{
            session.save(team);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateTeam(Team team) throws CustomException {
        try{
            session.update(team);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteTeam(String teamID) throws CustomException {
        try {
            Query query = session.createQuery("DELETE FROM Team t WHERE t.teamId =:teamID");
            query.setParameter("teamID", teamID);

            query.executeUpdate();

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public Team getTeamByID(String teamID) {
        Query query = session.createQuery("FROM Team t WHERE t.teamId =:teamID");
        query.setParameter("teamID", teamID);

        Team team = (Team) query.uniqueResult();
        if(team != null){
            return team;
        }
        return null;
    }

    @Override
    public Team getTeamByName(String teamName) {
        Query query = session.createQuery("FROM Team t WHERE t.name =:name");
        query.setParameter("name", teamName);

        Team team = (Team) query.uniqueResult();
        if(team != null){
            return team;
        }
        return null;
    }

    @Override
    public List<Team> getAllTeams() {
        Query query = session.createQuery("FROM Team");

        List<Team> teamList = query.list();
        if(teamList != null){
            return teamList;
        }
        return null;
    }

    @Override
    public List<Team> searchTeams(String teamName, String status, String floor, String room, String memberName,
                                  String projectName, String clientName, String from, String range) {

        StringBuilder queryBuilder = new StringBuilder();
        boolean hasClause = false;
        Map<String, String> paramValue = new HashMap<>();

        queryBuilder.append("FROM Team t");

        if(!Utility.isNullOrEmpty(teamName)){
            queryBuilder.append(" WHERE t.name like :teamName");
            paramValue.put("teamName", "%" + teamName + "%");
            hasClause = true;
        }

        if(!Utility.isNullOrEmpty(status)){
            if(hasClause){
                queryBuilder.append(" AND t.isActive =:active");

            } else {
                queryBuilder.append(" WHERE t.isActive =:active");
                hasClause = true;
            }
            paramValue.put("active", status);
        }

        if(!Utility.isNullOrEmpty(floor)){
            if(hasClause){
                queryBuilder.append(" AND t.floor =:floor");

            } else {
                queryBuilder.append(" WHERE t.floor =:floor");
                hasClause = true;
            }
            paramValue.put("floor", floor);
        }

        if(!Utility.isNullOrEmpty(room)){
            if(hasClause){
                queryBuilder.append(" AND t.room =:room");

            } else {
                queryBuilder.append(" WHERE t.room =:room");
                hasClause = true;
            }
            paramValue.put("room", room);
        }

        if(!Utility.isNullOrEmpty(memberName)){
            if(hasClause){
                queryBuilder.append(" AND t.teamId in (SELECT tm.team.teamId FROM TeamMember tm WHERE " +
                        "tm.employee.firstName like :memberName OR tm.employee.lastName like :memberName OR tm.employee.nickName like :memberName)");

            } else {
                queryBuilder.append(" WHERE t.teamId in (SELECT tm.team.teamId FROM TeamMember tm WHERE " +
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
                queryBuilder.append(" WHERE t.teamId in (SELECT pt.team.teamId FROM ProjectTeam pt WHERE " +
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
                queryBuilder.append(" WHERE t.teamId in (SELECT pt.team.teamId FROM ProjectTeam pt WHERE pt.project.projectId in " +
                        "(SELECT pc.project.projectId FROM ProjectClient pc WHERE pc.client.memberName like :clientName))");
                //hasClause = true;
            }
            paramValue.put("clientName", "%" + clientName + "%");
        }

        queryBuilder.append(" ORDER BY t.name ASC");

        logger.info("Query builder: " + queryBuilder.toString());
        Query query = session.createQuery(queryBuilder.toString());

        for(Map.Entry<String, String> entry : paramValue.entrySet()){
            if(entry.getKey().equals("active")){
                query.setParameter(entry.getKey(), entry.getValue().equals("true"));

            } else {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        if(!Utility.isNullOrEmpty(from) && !Utility.isNullOrEmpty(range))
            query.setFirstResult(Integer.valueOf(from)).setMaxResults(Integer.valueOf(range));

        List<Team> teamList = query.list();
        if(teamList != null){
            return teamList;
        }
        return null;
    }

    @Override
    public RoleType getRoleTypeByRoleId(String roleId) {
        Query query = session.createQuery("FROM RoleType rt WHERE rt.roleId =:roleId");
        query.setParameter("roleId", roleId);

        RoleType roleType = (RoleType) query.uniqueResult();
        if(roleType != null){
            return roleType;
        }
        return null;
    }

    @Override
    public void saveTeamMember(TeamMember teamMember) throws CustomException {
        try{
            session.save(teamMember);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteTeamMember(String teamID, String teamMemberID) throws CustomException {
        Query query;
        try {
            if(teamID != null){
                query = session.createQuery("DELETE FROM TeamMember tm WHERE tm.team.teamId =:teamID");
                query.setParameter("teamID", teamID);

            } else {
                query = session.createQuery("DELETE FROM TeamMember tm WHERE tm.teamMemberId =:teamMemberID");
                query.setParameter("teamMemberID", teamMemberID);
            }

            query.executeUpdate();

        } catch (Exception e) {
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public TeamMember getTeamMemberByTeamIDAndMemberID(String teamID, String memberID) {
        Query query = session.createQuery("FROM TeamMember tm WHERE tm.team.teamId =:teamID AND tm.employee.employeeId =:employeeID");
        query.setParameter("teamID", teamID);
        query.setParameter("employeeID", memberID);

        TeamMember teamMember = (TeamMember) query.uniqueResult();
        if(teamMember != null){
            return teamMember;
        }
        return null;
    }

    @Override
    public List<TeamMember> getTeamMembers(String teamID, String employeeID) {
        Query query;
        if(teamID != null) {
            query = session.createQuery("FROM TeamMember tm WHERE tm.team.teamId =:teamID");
            query.setParameter("teamID", teamID);

        } else {
            query = session.createQuery("FROM TeamMember tm WHERE tm.employee.employeeId =:employeeID");
            query.setParameter("employeeID", employeeID);
        }

        List<TeamMember> teamMemberList = query.list();
        if(teamMemberList != null) {
            return teamMemberList;
        }
        return null;
    }

    @Override
    public void saveTeamProject(ProjectTeam projectTeam) throws CustomException {
        try{
            session.save(projectTeam);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0003);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteProjectTeam(String teamID, String teamProjectID) throws CustomException {
        Query query;
        try {
            if(teamID != null){
                query = session.createQuery("DELETE FROM ProjectTeam pm WHERE pm.team.teamId =:teamID");
                query.setParameter("teamID", teamID);

            } else {
                query = session.createQuery("DELETE FROM ProjectTeam pm WHERE pm.projectTeamId =:teamProjectID");
                query.setParameter("teamProjectID", teamProjectID);
            }

            query.executeUpdate();

        } catch (Exception e) {
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0003);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public List<ProjectTeam> getProjectTeams(String teamID) {
        Query query = session.createQuery("FROM ProjectTeam pm WHERE pm.team.teamId =:teamID");
        query.setParameter("teamID", teamID);

        List<ProjectTeam> projectTeamList = query.list();
        if(projectTeamList != null){
            return projectTeamList;
        }
        return null;
    }
}
