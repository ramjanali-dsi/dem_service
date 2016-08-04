package com.dsi.dem.service;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Team;

import java.util.List;

/**
 * Created by sabbir on 7/20/16.
 */
public interface TeamService {

    void saveTeam(Team team) throws CustomException;
    void updateTeam(Team team) throws CustomException;
    void deleteTeam(String teamID) throws CustomException;
    Team getTeamByID(String teamID) throws CustomException;
    List<Team> getAllTeams() throws CustomException;

    void saveTeamProjects(List<String> projectIdList, Team team) throws CustomException;
}
