package com.dsi.dem.service.impl;

import com.dsi.dem.dao.TeamDao;
import com.dsi.dem.dao.impl.TeamDaoImpl;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Team;
import com.dsi.dem.service.TeamService;

import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public class TeamServiceImpl implements TeamService {

    private static final TeamDao teamDao = new TeamDaoImpl();

    @Override
    public void saveTeam(Team team) throws CustomException {

    }

    @Override
    public void updateTeam(Team team) throws CustomException {

    }

    @Override
    public void deleteTeam(Team team) throws CustomException {

    }

    @Override
    public Team getTeamByID(String teamID) throws CustomException {
        return null;
    }

    @Override
    public List<Team> getAllTeams() throws CustomException {
        return null;
    }
}
