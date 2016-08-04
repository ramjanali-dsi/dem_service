package com.dsi.dem.dao;

import com.dsi.dem.model.Team;
import com.dsi.dem.model.TeamMember;

import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public interface TeamDao {

    boolean saveTeam(Team team);
    boolean updateTeam(Team team);
    boolean deleteTeam(String teamID);
    Team getTeamByID(String teamID);
    Team getTeamByName(String teamName);
    List<Team> getAllTeams();

    boolean saveTeamMember(TeamMember teamMember);
    boolean updateTeamMember(TeamMember teamMember);
    boolean deleteTeamMember(String teamMemberID);
    TeamMember getTeamMemberByTeamIDAndMemberID(String teamID, String memberID);
    List<TeamMember> getTeamMembers(String teamID);
}
