package com.dsi.dem.dao;

import com.dsi.dem.model.Team;

import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public interface TeamDao {

    boolean saveTeam(Team team);
    boolean updateTeam(Team team);
    boolean deleteTeam(Team team);
    Team getTeamByID(String teamID);
    List<Team> getAllTeams();
}
