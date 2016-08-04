package com.dsi.dem.service.impl;

import com.dsi.dem.dao.ProjectDao;
import com.dsi.dem.dao.TeamDao;
import com.dsi.dem.dao.impl.ProjectDaoImpl;
import com.dsi.dem.dao.impl.TeamDaoImpl;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Employee;
import com.dsi.dem.model.ProjectTeam;
import com.dsi.dem.model.Team;
import com.dsi.dem.model.TeamMember;
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

        boolean res = teamDao.saveTeam(team);
        if(!res){
            ErrorContext errorContext = new ErrorContext(null, "Team", "Team create failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Save team success");

        for(TeamMember member : team.getMembers()){

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

    }

    @Override
    public void deleteTeam(String teamID) throws CustomException {

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
    public void saveTeamProjects(List<String> projectIdList, Team team) throws CustomException {

        for(String projectId : projectIdList){
            ProjectTeam projectTeam = new ProjectTeam();
            projectTeam.setTeam(team);
            projectTeam.setProject(projectDao.getProjectByID(projectId));

            boolean res = projectDao.saveProjectTeam(projectTeam);
            if(!res){
                ErrorContext errorContext = new ErrorContext(null, "Team", "Team project create failed.");
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                        Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
                throw new CustomException(errorMessage);
            }
            logger.info("Save team project success");
        }
    }

    private Team setTeamAllProperty(String teamID, Team team) throws CustomException {

        List<TeamMember> memberList = teamDao.getTeamMembers(teamID);
        if(!Utility.isNullOrEmpty(memberList)){
            for(TeamMember member : memberList){
                Employee employee = employeeService.getEmployeeByID(member.getEmployee().getEmployeeId());
                member.setEmployee(employee);
            }
            team.setMembers(memberList);
        }

        List<ProjectTeam> projectTeams = projectDao.getProjectTeamsByTeamID(teamID);
        if(!Utility.isNullOrEmpty(projectTeams)){
            team.setProjects(projectTeams);
        }

        return team;
    }
}
