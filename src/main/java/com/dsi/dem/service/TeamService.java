package com.dsi.dem.service;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.ProjectTeam;
import com.dsi.dem.model.Team;
import com.dsi.dem.model.TeamMember;

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

    void saveTeamMembers(List<TeamMember> teamMembers, String teamID) throws CustomException;
    void deleteTeamMember(String teamMemberID) throws CustomException;
    List<TeamMember> getTeamMembers(String teamID) throws CustomException;

    void saveTeamProjects(List<String> projectIdList, Team team) throws CustomException;
    void deleteTeamProject(String teamProjectID) throws CustomException;
    List<ProjectTeam> getTeamProjects(String teamID) throws CustomException;
}
