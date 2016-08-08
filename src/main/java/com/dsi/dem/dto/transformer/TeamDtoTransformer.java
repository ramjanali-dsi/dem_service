package com.dsi.dem.dto.transformer;

import com.dsi.dem.dto.ProjectTeamDto;
import com.dsi.dem.dto.TeamDto;
import com.dsi.dem.dto.TeamMemberDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.Utility;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
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

        } catch (Exception e) {
            e.printStackTrace();
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, errorContext);
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
            Employee employee = new Employee();
            BeanUtils.copyProperties(employee, memberDto);
            teamMember.setEmployee(employee);

            RoleType roleType = new RoleType();
            BeanUtils.copyProperties(roleType, memberDto);
            teamMember.setRole(roleType);

        } catch (Exception e) {
            e.printStackTrace();
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, errorContext);
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

            memberDto.setPhotoUrl(teamMember.getEmployee().getInfo().getPhotoUrl());

            if(!Utility.isNullOrEmpty(teamMember.getEmployee().getDesignations())) {
                for (EmployeeDesignation designation : teamMember.getEmployee().getDesignations()) {
                    if (designation.isCurrent()) {
                        memberDto.setCurrentDesignation(designation.getName());
                        break;
                    }
                }
            }

            if(!Utility.isNullOrEmpty(teamMember.getEmployee().getEmailInfo())){
                for(EmployeeEmail email : teamMember.getEmployee().getEmailInfo()){
                    if(email.getType().getEmailTypeName().equals(Constants.OFFICIAL_TYPE_NAME)){
                        memberDto.setEmail(email.getEmail());
                        break;
                    }
                }
            }

            if(!Utility.isNullOrEmpty(teamMember.getEmployee().getContactInfo())){
                for(EmployeeContact contact : teamMember.getEmployee().getContactInfo()){
                    if(contact.getType().getContactTypeName().equals(Constants.OFFICIAL_TYPE_NAME)){
                        memberDto.setPhone(contact.getPhone());
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, errorContext);
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

            List<ProjectTeamDto> projectTeamDtoList = new ArrayList<>();
            for(ProjectTeam projectTeam : team.getProjects()){
                projectTeamDtoList.add(getProjectTeamDto(projectTeam));
            }
            teamDto.setProjectList(projectTeamDtoList);

        } catch (Exception e) {
            e.printStackTrace();
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, errorContext);
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

    public List<ProjectTeamDto> getProjectTeamsDto(List<ProjectTeam> projectTeams) throws CustomException {

        List<ProjectTeamDto> projectTeamDtoList = new ArrayList<>();
        for(ProjectTeam projectTeam : projectTeams){
            projectTeamDtoList.add(getProjectTeamDto(projectTeam));
        }
        return projectTeamDtoList;
    }

    public ProjectTeamDto getProjectTeamDto(ProjectTeam projectTeam) throws CustomException {

        ProjectTeamDto projectTeamDto = new ProjectTeamDto();
        try {
            BeanUtils.copyProperties(projectTeamDto, projectTeam);
            BeanUtils.copyProperties(projectTeamDto, projectTeam.getProject());
            BeanUtils.copyProperties(projectTeamDto, projectTeam.getProject().getStatus());

        } catch (Exception e) {
            e.printStackTrace();
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return projectTeamDto;
    }
}
