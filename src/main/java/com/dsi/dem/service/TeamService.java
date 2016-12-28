package com.dsi.dem.service;

import com.dsi.dem.dto.TeamDto;
import com.dsi.dem.dto.TeamMemberDto;
import com.dsi.dem.dto.TeamProjectDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.ProjectTeam;
import com.dsi.dem.model.Team;
import com.dsi.dem.model.TeamMember;

import java.util.List;

/**
 * Created by sabbir on 7/20/16.
 */
public interface TeamService {

    TeamDto saveTeam(TeamDto teamDto) throws CustomException;
    TeamDto updateTeam(TeamDto teamDto, String teamId) throws CustomException;
    void deleteTeam(String teamID) throws CustomException;
    TeamDto getTeamByID(String teamID) throws CustomException;
    List<Team> getAllTeams() throws CustomException;
    List<TeamDto> searchTeams(String teamName, String status, String floor, String room,
                           String memberName, String projectName, String clientName, String from,
                           String range) throws CustomException;

    List<TeamMemberDto> createTeamMembers(String teamId, List<TeamMemberDto> teamMembers) throws CustomException;
    void saveTeamMembers(List<TeamMember> teamMembers, String teamID) throws CustomException;
    void deleteTeamMember(String teamMemberID) throws CustomException;
    TeamMember getTeamMemberByTeamIDAndMemberID(String teamID, String memberID) throws CustomException;
    List<TeamMember> getTeamMembers(String teamID, String employeeID) throws CustomException;

    List<TeamProjectDto> createTeamProjects(TeamDto teamDto, String teamId) throws CustomException;
    void saveTeamProjects(List<String> projectIdList, Team team) throws CustomException;
    void deleteTeamProject(String teamProjectID) throws CustomException;
    List<ProjectTeam> getTeamProjects(String teamID) throws CustomException;
}
