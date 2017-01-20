package com.dsi.dem.dto.transformer;

import com.dsi.dem.dto.TeamProjectDto;
import com.dsi.dem.dto.TeamDto;
import com.dsi.dem.dto.TeamMemberDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 8/4/16.
 */
public class TeamDtoTransformer {

    public Team getTeam(TeamDto teamDto) throws CustomException {

        Team team = new Team();
        try {
            BeanUtils.copyProperties(team, teamDto);

            List<TeamMember> memberList = new ArrayList<>();
            for(TeamMemberDto memberDto : teamDto.getMemberList()){
                memberList.add(getTeamMember(memberDto));
            }
            team.setMembers(memberList);

            List<ProjectTeam> projectList = new ArrayList<>();
            for(TeamProjectDto projectDto : teamDto.getProjectList()){
                projectList.add(getProjectTeam(projectDto));
            }
            team.setProjects(projectList);

        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return team;
    }

    public List<TeamMember> getTeamMembers(List<TeamMemberDto> memberDtoList) throws CustomException {

        List<TeamMember> memberList = new ArrayList<>();
        for(TeamMemberDto memberDto : memberDtoList){
            memberList.add(getTeamMember(memberDto));
        }
        return memberList;
    }

    public TeamMember getTeamMember(TeamMemberDto memberDto) throws CustomException {

        TeamMember teamMember = new TeamMember();
        try {
            BeanUtils.copyProperties(teamMember, memberDto);

            Employee employee = new Employee();
            BeanUtils.copyProperties(employee, memberDto);
            teamMember.setEmployee(employee);

            RoleType roleType = new RoleType();
            BeanUtils.copyProperties(roleType, memberDto);
            teamMember.setRole(roleType);

        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return teamMember;
    }

    public List<TeamMemberDto> getTeamMembersDto(List<TeamMember> members) throws CustomException {

        List<TeamMemberDto> memberDtoList = new ArrayList<>();
        for(TeamMember member : members){
            memberDtoList.add(getTeamMemberDto(member));
        }
        return memberDtoList;
    }

    public TeamMemberDto getTeamMemberDto(TeamMember teamMember) throws CustomException {

        TeamMemberDto memberDto = new TeamMemberDto();
        try {
            BeanUtils.copyProperties(memberDto, teamMember);
            BeanUtils.copyProperties(memberDto, teamMember.getEmployee());
            BeanUtils.copyProperties(memberDto, teamMember.getRole());

        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }

        return memberDto;
    }

    public TeamDto getTeamDto(Team team) throws CustomException {

        TeamDto teamDto = new TeamDto();
        try {
            BeanUtils.copyProperties(teamDto, team);

            List<TeamMemberDto> memberDtoList = new ArrayList<>();
            for(TeamMember member : team.getMembers()){
                memberDtoList.add(getTeamMemberDto(member));
            }
            teamDto.setMemberList(memberDtoList);

            List<TeamProjectDto> teamProjectDtoList = new ArrayList<>();
            for(ProjectTeam projectTeam : team.getProjects()){
                teamProjectDtoList.add(getProjectTeamDto(projectTeam));
            }
            teamDto.setProjectList(teamProjectDtoList);

        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return teamDto;
    }

    public List<TeamDto> getTeamsDto(List<Team> teams) throws CustomException {

        List<TeamDto> dtoList = new ArrayList<>();
        for(Team team : teams){
            dtoList.add(getTeamDto(team));
        }
        return dtoList;
    }

    public List<ProjectTeam> getProjectTeams(List<TeamProjectDto> teamProjects) throws CustomException {
        List<ProjectTeam> projectTeams = new ArrayList<>();
        for(TeamProjectDto projectDto : teamProjects){
            projectTeams.add(getProjectTeam(projectDto));
        }
        return projectTeams;
    }

    public List<TeamProjectDto> getProjectTeamsDto(List<ProjectTeam> projectTeams) throws CustomException {

        List<TeamProjectDto> teamProjectDtoList = new ArrayList<>();
        for(ProjectTeam projectTeam : projectTeams){
            teamProjectDtoList.add(getProjectTeamDto(projectTeam));
        }
        return teamProjectDtoList;
    }

    public TeamProjectDto getProjectTeamDto(ProjectTeam projectTeam) throws CustomException {

        TeamProjectDto teamProjectDto = new TeamProjectDto();
        try {
            BeanUtils.copyProperties(teamProjectDto, projectTeam);
            BeanUtils.copyProperties(teamProjectDto, projectTeam.getProject());
            BeanUtils.copyProperties(teamProjectDto, projectTeam.getProject().getStatus());

        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return teamProjectDto;
    }

    public ProjectTeam getProjectTeam(TeamProjectDto projectDto) throws CustomException {

        ProjectTeam projectTeam = new ProjectTeam();
        try{
            BeanUtils.copyProperties(projectTeam, projectDto);

            Project project = new Project();
            BeanUtils.copyProperties(project, projectDto);
            projectTeam.setProject(project);

        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return projectTeam;
    }
}
