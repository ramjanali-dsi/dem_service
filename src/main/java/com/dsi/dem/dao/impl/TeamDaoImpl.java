package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.TeamDao;
import com.dsi.dem.model.Team;
import com.dsi.dem.model.TeamMember;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

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
    public boolean saveTeamMember(TeamMember teamMember) {
        return save(teamMember);
    }

    @Override
    public boolean updateTeamMember(TeamMember teamMember) {
        return update(teamMember);
    }

    @Override
    public boolean deleteTeamMember(String teamMemberID) {
        Session session = null;
        boolean success = true;
        try {
            session = getSession();
            Query query = session.createQuery("DELETE FROM TeamMember tm WHERE tm.teamMemberId =:teamMemberID");
            query.setParameter("teamMemberID", teamMemberID);

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
}
