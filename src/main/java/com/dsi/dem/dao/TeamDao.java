package com.dsi.dem.dao;

import com.dsi.dem.dto.ContextDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.ProjectTeam;
import com.dsi.dem.model.RoleType;
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
    List<Team> searchTeams(String teamName, String status, String floor, String room, String memberName, String projectName,
                           String clientName, ContextDto contextDto, String from, String range);

    RoleType getRoleTypeByRoleId(String roleId);

    void saveTeamMember(TeamMember teamMember) throws CustomException;
    void deleteTeamMember(String teamID, String teamMemberID) throws CustomException;
    void deleteTeamMemberByUserId(String teamID, String userID) throws CustomException;
    void deleteTeamLeadByUserId(String teamId, String userId) throws CustomException;
    TeamMember getTeamMemberByTeamIDAndMemberID(String teamID, String memberID);
    TeamMember getTeamMemberByTeamIDAndUserID(String teamID, String userId);
    TeamMember getTeamMemberByTeamIDAndUserIDAndRole(String teamID, String userId, String roleId);
    TeamMember getTeamLeadByTeamID(String teamID);
    List<TeamMember> getTeamMembers(String teamID, String employeeID);
    List<TeamMember> getTeamMemberByEmployeeId(String employeeId);
    int getTeamMembersCount(String teamId);

    void saveTeamProject(ProjectTeam projectTeam) throws CustomException;
    void deleteProjectTeam(String teamID, String teamProjectID) throws CustomException;
    void deleteProjectTeamByProjectId(String teamID, String projectID) throws CustomException;
    ProjectTeam getProjectTeamByTeamIdAndProjectId(String teamId, String projectId);
    List<ProjectTeam> getProjectTeams(String teamID);
}
