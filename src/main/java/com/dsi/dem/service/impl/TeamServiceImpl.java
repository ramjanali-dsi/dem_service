package com.dsi.dem.service.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.ProjectDao;
import com.dsi.dem.dao.TeamDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.dao.impl.ProjectDaoImpl;
import com.dsi.dem.dao.impl.TeamDaoImpl;
import com.dsi.dem.dto.TeamDto;
import com.dsi.dem.dto.TeamMemberDto;
import com.dsi.dem.dto.TeamProjectDto;
import com.dsi.dem.dto.transformer.TeamDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.TeamService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public class TeamServiceImpl extends CommonService implements TeamService {

    private static final Logger logger = Logger.getLogger(TeamServiceImpl.class);

    private static final TeamDtoTransformer TRANSFORMER = new TeamDtoTransformer();
    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private static final TeamDao teamDao = new TeamDaoImpl();
    private static final ProjectDao projectDao = new ProjectDaoImpl();

    @Override
    public TeamDto saveTeam(TeamDto teamDto) throws CustomException {
        logger.info("Team Create:: Start");
        logger.info("Convert Team Dto to Team Object");
        Team team = TRANSFORMER.getTeam(teamDto);

        if(Utility.isNullOrEmpty(team.getMembers())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

        validateInputForCreation(team);

        Session session = getSession();
        teamDao.setSession(session);

        if(teamDao.getTeamByName(team.getName()) != null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0004);
            throw new CustomException(errorMessage);
        }

        team.setVersion(1);
        teamDao.saveTeam(team);
        logger.info("Save team success");

        for(TeamMember member : team.getMembers()){
            member.setVersion(1);
            member.setTeam(team);
            member.setRole(teamDao.getRoleTypeByRoleId(member.getRole().getRoleId()));
            member.setEmployee(employeeDao.getEmployeeByUserID(member.getEmployee().getUserId()));
            teamDao.saveTeamMember(member);
        }
        logger.info("Save team members success");

        if(!Utility.isNullOrEmpty(teamDto.getProjectIds())) {
            projectDao.setSession(session);
            saveTeamProjects(teamDto.getProjectIds(), team);
        }

        setTeamAllProperty(team.getTeamId(), team);
        logger.info("Team Create:: End");

        close(session);
        return TRANSFORMER.getTeamDto(team);
    }

    private void validateInputForCreation(Team team) throws CustomException {
        if(team.getName() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0005);
            throw new CustomException(errorMessage);
        }

        if(Utility.isNullOrEmpty(team.getMembers())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public TeamDto updateTeam(TeamDto teamDto, String teamId) throws CustomException {
        logger.info("Team Update:: Start");
        logger.info("Convert Team Dto to Team Object");
        Team team = TRANSFORMER.getTeam(teamDto);

        validateInputForUpdate(team);

        Session session = getSession();
        teamDao.setSession(session);
        //employeeDao.setSession(session);

        team.setTeamId(teamId);
        if(teamDao.getTeamByID(team.getTeamId()) == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        Team existTeam = teamDao.getTeamByID(team.getTeamId());

        existTeam.setName(team.getName());
        existTeam.setFloor(team.getFloor());
        existTeam.setRoom(team.getRoom());
        existTeam.setActive(team.isActive());
        existTeam.setVersion(team.getVersion());
        teamDao.updateTeam(existTeam);
        logger.info("Update team success");

        setTeamAllProperty(teamId, existTeam);
        logger.info("Team Update:: End");

        close(session);
        return TRANSFORMER.getTeamDto(existTeam);
    }

    private void validateInputForUpdate(Team team) throws CustomException {
        if(team.getVersion() == 0){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteTeam(String teamID) throws CustomException {
        logger.info("Team delete:: Start");
        Session session = getSession();
        teamDao.setSession(session);

        teamDao.deleteTeamMember(teamID, null);
        teamDao.deleteProjectTeam(teamID, null);

        teamDao.deleteTeam(teamID);
        logger.info("Delete team success");
        logger.info("Team delete:: End");

        close(session);
    }

    @Override
    public TeamDto getTeamByID(String teamID) throws CustomException {
        logger.info("Read team:: Start");
        Session session = getSession();
        teamDao.setSession(session);
        //employeeDao.setSession(session);

        Team team = teamDao.getTeamByID(teamID);
        if(team == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        setTeamAllProperty(teamID, team);
        logger.info("Read team:: End");

        close(session);
        return TRANSFORMER.getTeamDto(team);
    }

    @Override
    public List<Team> getAllTeams() throws CustomException {
        Session session = getSession();
        teamDao.setSession(session);
        //employeeDao.setSession(session);

        List<Team> teamList = teamDao.getAllTeams();
        if(teamList == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        List<Team> teams = new ArrayList<>();
        for(Team team : teamList){
            teams.add(setTeamAllProperty(team.getTeamId(), team));
        }

        close(session);
        return teams;
    }

    @Override
    public List<TeamDto> searchTeams(String teamName, String status, String floor, String room, String memberName,
                                  String projectName, String clientName, String from, String range) throws CustomException {

        logger.info("Read all teams:: Start");
        Session session = getSession();
        teamDao.setSession(session);
        //employeeDao.setSession(session);

        List<Team> teamList = teamDao.searchTeams(teamName, status, floor, room, memberName, projectName, clientName, from, range);
        if(teamList == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        logger.info("Team list size: " + teamList.size());

        List<Team> teams = new ArrayList<>();
        for(Team team : teamList){
            teams.add(setTeamAllProperty(team.getTeamId(), team));
        }
        logger.info("Read all teams:: End");

        close(session);
        return TRANSFORMER.getTeamsDto(teams);
    }

    @Override
    public List<TeamMemberDto> createTeamMembers(String teamId, List<TeamMemberDto> teamMembers) throws CustomException {
        logger.info("Team members create:: Start");
        logger.info("Convert TeamMember Dto to TeamMember Object");
        List<TeamMember> teamMemberList = TRANSFORMER.getTeamMembers(teamMembers);

        if(Utility.isNullOrEmpty(teamMemberList)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

        validateInputForMemberCreation(teamMemberList);

        Session session = getSession();
        teamDao.setSession(session);

        saveTeamMembers(teamMemberList, teamId);
        List<TeamMember> teamMembersList = teamDao.getTeamMembers(teamId, null);
        logger.info("Team members create:: End");

        close(session);
        return TRANSFORMER.getTeamMembersDto(teamMembersList);
    }

    @Override
    public void saveTeamMembers(List<TeamMember> teamMembers, String teamID) throws CustomException {

        teamDao.deleteTeamMember(teamID, null);
        logger.info("Delete all team members.");

        for (TeamMember member : teamMembers) {
            Team team = teamDao.getTeamByID(teamID);
            member.setVersion(team.getVersion());
            member.setTeam(team);
            member.setRole(teamDao.getRoleTypeByRoleId(member.getRole().getRoleId()));
            member.setEmployee(employeeDao.getEmployeeByUserID(member.getEmployee().getUserId()));
            teamDao.saveTeamMember(member);
        }
        logger.info("Save team members success.");

        Team existTeam = teamDao.getTeamByID(teamID);
        existTeam.setMemberCount(teamMembers.size());
        teamDao.updateTeam(existTeam);
        logger.info("Team (member count) update success.");

        /*JSONObject resultObj, contentObj;
        String result;
        boolean isCheck = true;
        try {

            List<TeamMember> assignedTeamMember = new ArrayList<>();
            List<TeamMember> unassignedTeamMember = new ArrayList<>();

            List<TeamMember> teamMemberList = teamDao.getTeamMembers(teamID, null);
            for(TeamMember existMember : teamMemberList){

                for(TeamMember newMember : teamMembers){

                    if(existMember.getEmployee().getUserId().equals(newMember.getEmployee().getUserId())){
                        isCheck = false;
                    }
                }

                if(isCheck){
                    logger.info("Unassigned team member.");
                    unassignedTeamMember.add(existMember);
                }
            }

            isCheck = true;
            for(TeamMember newMember : teamMembers){

                for(TeamMember existMember : teamMemberList){

                    if(newMember.getEmployee().getUserId().equals(existMember.getEmployee().getUserId())){
                        isCheck = false;
                    }
                }

                if(isCheck){
                    logger.info("Assigned team member.");
                    assignedTeamMember.add(newMember);
                }
            }

            teamDao.deleteTeamMember(teamID, null);
            logger.info("Delete all team members.");

            for (TeamMember member : teamMembers) {
                Team team = teamDao.getTeamByID(teamID);
                member.setVersion(team.getVersion());
                member.setTeam(team);
                member.setRole(teamDao.getRoleTypeByRoleId(member.getRole().getRoleId()));
                member.setEmployee(employeeDao.getEmployeeByUserID(member.getEmployee().getUserId()));
                teamDao.saveTeamMember(member);
            }
            logger.info("Save team members success.");

            Team existTeam = teamDao.getTeamByID(teamID);
            existTeam.setMemberCount(teamMembers.size());
            teamDao.updateTeam(existTeam);
            logger.info("Team (member count) update success.");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/
    }

    @Override
    public void deleteTeamMember(String teamMemberID) throws CustomException {
        logger.info("Team member delete:: Start");
        Session session = getSession();
        teamDao.setSession(session);

        teamDao.deleteTeamMember(null, teamMemberID);
        logger.info("Delete team member success");
        logger.info("Team member delete:: end");

        close(session);
    }

    @Override
    public TeamMember getTeamMemberByTeamIDAndMemberID(String teamID, String memberID) throws CustomException {
        Session session = getSession();
        teamDao.setSession(session);

        TeamMember member = teamDao.getTeamMemberByTeamIDAndMemberID(teamID, memberID);
        if(member == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        close(session);
        return member;
    }

    @Override
    public List<TeamMember> getTeamMembers(String teamID, String employeeID) throws CustomException {
        Session session = getSession();
        teamDao.setSession(session);

        List<TeamMember> memberList = teamDao.getTeamMembers(teamID, employeeID);
        if(memberList == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        close(session);
        return memberList;
    }

    @Override
    public List<TeamProjectDto> createTeamProjects(TeamDto teamDto, String teamId) throws CustomException {
        logger.info("Team project create:: Start");
        if(Utility.isNullOrEmpty(teamDto.getProjectIds())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0003);
            throw new CustomException(errorMessage);
        }

        Session session = getSession();
        teamDao.setSession(session);
        projectDao.setSession(session);

        saveTeamProjects(teamDto.getProjectIds(), teamDao.getTeamByID(teamId));
        List<ProjectTeam> teamProjects = teamDao.getProjectTeams(teamId);
        logger.info("Team project create:: End");

        close(session);
        return TRANSFORMER.getProjectTeamsDto(teamProjects);
    }

    private void validateInputForMemberCreation(List<TeamMember> memberList) throws CustomException {

        for(TeamMember member : memberList){
            if(member.getEmployee().getUserId() == null){
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                        Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0006);
                throw new CustomException(errorMessage);
            }

            if(member.getRole().getRoleId() == null){
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                        Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0007);
                throw new CustomException(errorMessage);
            }
        }
    }

    @Override
    public void saveTeamProjects(List<String> projectIdList, Team team) throws CustomException {

        teamDao.deleteProjectTeam(team.getTeamId(), null);
        logger.info("Delete all team projects");

        for (String projectId : projectIdList) {
            Project project = projectDao.getProjectByID(projectId);
            ProjectTeam projectTeam = new ProjectTeam();
            projectTeam.setTeam(team);
            projectTeam.setVersion(project.getVersion());
            projectTeam.setProject(project);

            teamDao.saveTeamProject(projectTeam);
        }
        logger.info("Save team projects success");
    }

    @Override
    public void deleteTeamProject(String teamProjectID) throws CustomException {
        logger.info("Team project delete:: Start");
        Session session = getSession();
        teamDao.setSession(session);

        teamDao.deleteProjectTeam(null, teamProjectID);
        logger.info("Delete team project success");
        logger.info("Team project delete:: End");

        close(session);
    }

    @Override
    public List<ProjectTeam> getTeamProjects(String teamID) throws CustomException {
        Session session = getSession();
        teamDao.setSession(session);

        List<ProjectTeam> projectTeamList = teamDao.getProjectTeams(teamID);
        if(projectTeamList == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        close(session);
        return projectTeamList;
    }

    private Team setTeamAllProperty(String teamID, Team team) throws CustomException {

        List<TeamMember> memberList = teamDao.getTeamMembers(teamID, null);
        if(!Utility.isNullOrEmpty(memberList)){
            for(TeamMember member : memberList){
                Employee employee = employeeDao.getEmployeeByID(member.getEmployee().getEmployeeId());
                member.setEmployee(employee);
            }
            team.setMembers(memberList);
        }

        List<ProjectTeam> projectTeams = teamDao.getProjectTeams(teamID);
        if(!Utility.isNullOrEmpty(projectTeams)){
            team.setProjects(projectTeams);
        }

        return team;
    }
}
