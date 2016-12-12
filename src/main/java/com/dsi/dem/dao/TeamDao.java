package com.dsi.dem.dao;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.ProjectTeam;
import com.dsi.dem.model.Team;
import com.dsi.dem.model.TeamMember;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public interface TeamDao {

    void setSession(Session session);
    void saveTeam(Team team) throws CustomException;
    void updateTeam(Team team) throws CustomException;
    void deleteTeam(String teamID) throws CustomException;
    Team getTeamByID(String teamID);
    Team getTeamByName(String teamName);
    List<Team> getAllTeams();
    List<Team> searchTeams(String teamName, String status, String floor, String room,
                           String memberName, String projectName, String clientName, String from, String range);

    void saveTeamMember(TeamMember teamMember) throws CustomException;
    void deleteTeamMember(String teamID, String teamMemberID) throws CustomException;
    TeamMember getTeamMemberByTeamIDAndMemberID(String teamID, String memberID);
    List<TeamMember> getTeamMembers(String teamID, String employeeID);

    void saveTeamProject(ProjectTeam projectTeam) throws CustomException;
    void deleteProjectTeam(String teamID, String teamProjectID) throws CustomException;
    List<ProjectTeam> getProjectTeams(String teamID);
}
