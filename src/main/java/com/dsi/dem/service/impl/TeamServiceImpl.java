package com.dsi.dem.service.impl;

import com.dsi.dem.dao.ProjectDao;
import com.dsi.dem.dao.TeamDao;
import com.dsi.dem.dao.impl.ProjectDaoImpl;
import com.dsi.dem.dao.impl.TeamDaoImpl;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.EmployeeService;
import com.dsi.dem.service.TeamService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public class TeamServiceImpl implements TeamService {

    private static final Logger logger = Logger.getLogger(TeamServiceImpl.class);

    private static final EmployeeService employeeService = new EmployeeServiceImpl();

    private static final TeamDao teamDao = new TeamDaoImpl();
    private static final ProjectDao projectDao = new ProjectDaoImpl();

    @Override
    public void saveTeam(Team team) throws CustomException {
        validateInputForCreation(team);

        team.setVersion(1);
        boolean res = teamDao.saveTeam(team);
        if(!res){
            ErrorContext errorContext = new ErrorContext(null, "Team", "Team create failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Save team success");

        for(TeamMember member : team.getMembers()){

            member.setVersion(1);
            member.setTeam(team);
            res = teamDao.saveTeamMember(member);
            if(!res){
                ErrorContext errorContext = new ErrorContext(null, "Team", "Team member create failed.");
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                        Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
                throw new CustomException(errorMessage);
            }
            logger.info("Save team member success");
        }
    }

    private void validateInputForCreation(Team team) throws CustomException {
        if(team.getName() == null){
            ErrorContext errorContext = new ErrorContext(null, "Team", "Team Name not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(Utility.isNullOrEmpty(team.getMembers())){
            ErrorContext errorContext = new ErrorContext(null, "Team", "Team members not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(teamDao.getTeamByName(team.getName()) != null){
            ErrorContext errorContext = new ErrorContext(team.getName(), "Team", "Team already exist by this name.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateTeam(Team team) throws CustomException {
        validateInputForUpdate(team);

        boolean res = teamDao.updateTeam(team);
        if(!res){
            ErrorContext errorContext = new ErrorContext(team.getTeamId(), "Team", "Team update failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Update team success");
    }

    private void validateInputForUpdate(Team team) throws CustomException {
        if(team.getVersion() == 0){
            ErrorContext errorContext = new ErrorContext(null, "Team", "Version not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(teamDao.getTeamByID(team.getTeamId()) == null){
            ErrorContext errorContext = new ErrorContext(team.getTeamId(), "Team", "Team not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteTeam(String teamID) throws CustomException {
        teamDao.deleteTeamMember(teamID, null);
        teamDao.deleteProjectTeam(teamID, null);

        boolean res = teamDao.deleteTeam(teamID);
        if(!res){
            ErrorContext errorContext = new ErrorContext(teamID, "Team", "Team delete failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete team success");
    }

    @Override
    public Team getTeamByID(String teamID) throws CustomException {
        Team team = teamDao.getTeamByID(teamID);
        if(team == null){
            ErrorContext errorContext = new ErrorContext(teamID, "Team", "Team not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return setTeamAllProperty(teamID, team);
    }

    @Override
    public List<Team> getAllTeams() throws CustomException {
        List<Team> teamList = teamDao.getAllTeams();
        if(teamList == null){
            ErrorContext errorContext = new ErrorContext(null, "Team", "Team list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        List<Team> teams = new ArrayList<>();
        for(Team team : teamList){
            teams.add(setTeamAllProperty(team.getTeamId(), team));
        }
        return teams;
    }

    @Override
    public List<Team> searchTeams(String teamName, String status, String floor, String room, String memberName,
                                  String projectName, String clientName, String from, String range) throws CustomException {

        List<Team> teamList = teamDao.searchTeams(teamName, status, floor, room, memberName, projectName, clientName, from, range);
        if(teamList == null){
            ErrorContext errorContext = new ErrorContext(null, "Team", "Team list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        List<Team> teams = new ArrayList<>();
        for(Team team : teamList){
            teams.add(setTeamAllProperty(team.getTeamId(), team));
        }
        return teams;
    }

    @Override
    public void saveTeamMembers(List<TeamMember> teamMembers, String teamID) throws CustomException {

        if(Utility.isNullOrEmpty(teamMembers)){
            ErrorContext errorContext = new ErrorContext(null, "TeamMember", "Team member list not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(teamDao.deleteTeamMember(teamID, null))
            logger.info("Delete all team members.");

        for(TeamMember member : teamMembers){
            validateInputForMemberCreation(member);

            Team team = teamDao.getTeamByID(teamID);
            member.setVersion(team.getVersion());
            member.setTeam(team);
            boolean res = teamDao.saveTeamMember(member);
            if(!res){
                ErrorContext errorContext = new ErrorContext(null, "TeamMember", "Team member create failed.");
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                        Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
                throw new CustomException(errorMessage);
            }
            logger.info("Save team member success.");
        }
    }

    @Override
    public void deleteTeamMember(String teamMemberID) throws CustomException {
        boolean res = teamDao.deleteTeamMember(null, teamMemberID);
        if(!res){
            ErrorContext errorContext = new ErrorContext(teamMemberID, "TeamMember", "Team member delete failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete team member success");
    }

    @Override
    public List<TeamMember> getTeamMembers(String teamID, String employeeID) throws CustomException {
        List<TeamMember> memberList = teamDao.getTeamMembers(teamID, employeeID);
        if(memberList == null){
            ErrorContext errorContext = new ErrorContext(teamID, "TeamMember", "Team members not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return memberList;
    }

    private void validateInputForMemberCreation(TeamMember member) throws CustomException {
        if(member.getEmployee().getEmployeeId() == null){
            ErrorContext errorContext = new ErrorContext(null, "TeamMember", "Team member info not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(member.getRole().getRoleId() == null){
            ErrorContext errorContext = new ErrorContext(null, "TeamMember", "Team member role not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void saveTeamProjects(List<String> projectIdList, Team team) throws CustomException {

        if(teamDao.deleteProjectTeam(team.getTeamId(), null))
            logger.info("Delete all team projects");

        if(!Utility.isNullOrEmpty(projectIdList)) {
            for (String projectId : projectIdList) {
                Project project = projectDao.getProjectByID(projectId);
                ProjectTeam projectTeam = new ProjectTeam();
                projectTeam.setTeam(team);
                projectTeam.setVersion(project.getVersion());
                projectTeam.setProject(project);

                boolean res = teamDao.saveTeamProject(projectTeam);
                if (!res) {
                    ErrorContext errorContext = new ErrorContext(null, "TeamProject", "Team project create failed.");
                    ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                            Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
                    throw new CustomException(errorMessage);
                }
                logger.info("Save team project success");
            }
        }
    }

    @Override
    public void deleteTeamProject(String teamProjectID) throws CustomException {
        boolean res = teamDao.deleteProjectTeam(null, teamProjectID);
        if(!res){
            ErrorContext errorContext = new ErrorContext(teamProjectID, "TeamProject", "Team project delete failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete team project success");
    }

    @Override
    public List<ProjectTeam> getTeamProjects(String teamID) throws CustomException {
        List<ProjectTeam> projectTeamList = teamDao.getProjectTeams(teamID);
        if(projectTeamList == null){
            ErrorContext errorContext = new ErrorContext(teamID, "TeamProject", "Team projects not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return projectTeamList;
    }

    private Team setTeamAllProperty(String teamID, Team team) throws CustomException {

        List<TeamMember> memberList = teamDao.getTeamMembers(teamID, null);
        if(!Utility.isNullOrEmpty(memberList)){
            for(TeamMember member : memberList){
                Employee employee = employeeService.getEmployeeByID(member.getEmployee().getEmployeeId());
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
