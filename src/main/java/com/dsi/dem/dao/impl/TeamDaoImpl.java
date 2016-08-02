package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.TeamDao;
import com.dsi.dem.model.Team;
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
    public boolean deleteTeam(Team team) {
        return delete(team);
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
}
