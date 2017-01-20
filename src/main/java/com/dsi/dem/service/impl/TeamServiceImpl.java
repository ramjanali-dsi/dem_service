package com.dsi.dem.service.impl;

import com.dsi.checkauthorization.model.Role;
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
import com.dsi.dem.service.NotificationService;
import com.dsi.dem.service.TeamService;
import com.dsi.dem.util.*;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
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
    private static final NotificationService notificationService = new NotificationServiceImpl();

    @Override
    public TeamDto saveTeam(TeamDto teamDto, String tenantName) throws CustomException {
        logger.info("Team Create:: Start");
        logger.info("Convert Team Dto to Team Object");
        Team team = TRANSFORMER.getTeam(teamDto);

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
        team.setMemberCount(team.getMembers().size());
        teamDao.saveTeam(team);
        logger.info("Save team success");

        String leadEmail = "";
        for(TeamMember member : team.getMembers()){

            RoleType roleType = teamDao.getRoleTypeByRoleId(member.getRole().getRoleId());
            Employee employee = employeeDao.getEmployeeByUserID(member.getEmployee().getUserId());

            member.setVersion(1);
            member.setTeam(team);
            member.setRole(roleType);
            member.setEmployee(employee);
            teamDao.saveTeamMember(member);

            if(roleType.getRoleName().equals(RoleName.LEAD.getValue())){
                leadEmail = employeeDao.getEmployeeEmailsByEmployeeID(employee.getEmployeeId()).get(0).getEmail();
            }
        }
        logger.info("Save team members success");

        if(!Utility.isNullOrEmpty(team.getProjects())){

            for(ProjectTeam projectTeam : team.getProjects()){
                projectTeam.setTeam(team);
                projectTeam.setVersion(team.getVersion());
                projectTeam.setProject(projectDao.getProjectByID(projectTeam.getProject().getProjectId()));

                teamDao.saveTeamProject(projectTeam);
            }
            logger.info("Save team projects success");
        }

        setTeamAllProperty(team);
        logger.info("Team Create:: End");
        close(session);

        /*try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = new JSONArray();
            //TODO Manager & HR email config

            JSONObject globalContentObj = EmailContent.getContentForTeam(team, tenantName, emailList);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.TEAM_CREATE_TEMPLATE_ID_FOR_MANAGER_HR));

            emailList.put(leadEmail);
            globalContentObj = EmailContent.getContentForTeam(team, tenantName, emailList);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.TEAM_CREATE_TEMPLATE_ID_FOR_LEAD));

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/

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
    public TeamDto updateTeam(TeamDto teamDto, String teamId, String tenantName) throws CustomException {
        logger.info("Team Update:: Start");
        logger.info("Convert Team Dto to Team Object");
        Team team = TRANSFORMER.getTeam(teamDto);

        validateInputForUpdate(team);

        Session session = getSession();
        teamDao.setSession(session);

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

        setTeamAllProperty(existTeam);
        logger.info("Team Update:: End");
        close(session);

        String leadEmail = "";
        for(TeamMember member : team.getMembers()){

            if(member.getRole().getRoleName().equals(RoleName.LEAD.getValue())){
                leadEmail = employeeDao.getEmployeeEmailsByEmployeeID(member.getEmployee().getEmployeeId()).get(0).getEmail();
                break;
            }
        }

        /*try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = new JSONArray();
            //TODO Manager & HR email config

            JSONObject globalContentObj = EmailContent.getContentForTeam(team, tenantName, emailList);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.TEAM_UPDATE_TEMPLATE_ID_FOR_MANAGER_HR));

            emailList.put(leadEmail);
            globalContentObj = EmailContent.getContentForTeam(team, tenantName, emailList);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.TEAM_UPDATE_TEMPLATE_ID_FOR_LEAD));

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/

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
    public String deleteTeam(String teamID, String tenantName) throws CustomException {
        logger.info("Team delete:: Start");
        Session session = getSession();
        teamDao.setSession(session);

        teamDao.deleteTeamMember(teamID, null);
        teamDao.deleteProjectTeam(teamID, null);

        teamDao.deleteTeam(teamID);
        logger.info("Delete team success");
        logger.info("Team delete:: End");
        close(session);

        /*Team team = teamDao.getTeamByID(teamID);
        TeamMember leadMember = teamDao.getTeamLeadByTeamID(teamID);
        String leadEmail = employeeDao.getEmployeeEmailsByEmployeeID(leadMember.getEmployee().getEmployeeId()).get(0).getEmail();

        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = new JSONArray();
            //TODO Manager & HR email config

            JSONObject globalContentObj = EmailContent.getContentForTeam(team, tenantName, emailList);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.TEAM_DELETE_TEMPLATE_ID_FOR_MANAGER_HR));

            emailList.put(leadEmail);
            globalContentObj = EmailContent.getContentForTeam(team, tenantName, emailList);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.TEAM_DELETE_TEMPLATE_ID_FOR_LEAD));

            teamDao.deleteTeamMember(teamID, null);
            teamDao.deleteProjectTeam(teamID, null);

            teamDao.deleteTeam(teamID);
            logger.info("Delete team success");
            logger.info("Team delete:: End");
            close(session);

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/

        return null;
    }

    @Override
    public TeamDto getTeamByID(String teamID) throws CustomException {
        logger.info("Read team:: Start");
        Session session = getSession();
        teamDao.setSession(session);

        Team team = teamDao.getTeamByID(teamID);
        if(team == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        setTeamAllProperty(team);
        logger.info("Read team:: End");

        close(session);
        return TRANSFORMER.getTeamDto(team);
    }

    @Override
    public List<Team> getAllTeams() throws CustomException {
        Session session = getSession();
        teamDao.setSession(session);

        List<Team> teamList = teamDao.getAllTeams();
        if(teamList == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        List<Team> teams = new ArrayList<>();
        for(Team team : teamList){
            teams.add(setTeamAllProperty(team));
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
            teams.add(setTeamAllProperty(team));
        }
        logger.info("Read all teams:: End");

        close(session);
        return TRANSFORMER.getTeamsDto(teams);
    }

    @Override
    public List<TeamMemberDto> createTeamMembers(String teamId, List<TeamMemberDto> teamMembers,
                                                 String tenantName) throws CustomException {
        logger.info("Convert TeamMember Dto to TeamMember Object");
        List<TeamMember> teamMemberList = TRANSFORMER.getTeamMembers(teamMembers);
        if(Utility.isNullOrEmpty(teamMemberList)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

        Session session = getSession();
        teamDao.setSession(session);
        Team team = teamDao.getTeamByID(teamId);

        List<TeamMember> assignedTeamMembers = new ArrayList<>();
        List<TeamMember> unassignedTeamMembers = new ArrayList<>();

        for(TeamMember teamMember : teamMemberList){

            switch (teamMember.getActivity()){
                case 1:
                    logger.info("Create Team members:: Start");
                    validateInputForMemberCreation(teamMember, session);

                    saveTeamMember(teamMember, team);
                    logger.info("Create Team members:: End");

                    assignedTeamMembers.add(teamMember);
                    break;

                case 2:
                    logger.info("Delete Team members:: Start");
                    if(teamMember.getEmployee().getUserId() == null){
                        close(session);
                        ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                                Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0008);
                        throw new CustomException(errorMessage);
                    }

                    teamDao.deleteTeamMemberByUserId(teamId, teamMember.getEmployee().getUserId());
                    logger.info("Delete Team members:: End");

                    unassignedTeamMembers.add(teamMember);
                    break;
            }
        }
        team.setMemberCount(teamDao.getTeamMembersCount(teamId));
        teamDao.updateTeam(team);
        logger.info("Team (member count) update success.");

        session.flush();
        session.clear();
        List<TeamMember> teamMembersList = teamDao.getTeamMembers(teamId, null);

        /*JSONObject contentObj;
        try {

            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray hrManagerEmailList = notificationService.getHrManagerEmailList();

            JSONArray clientEmails = new JSONArray();
            List<ProjectTeam> teamProjects = teamDao.getProjectTeams(teamId);
            if(!Utility.isNullOrEmpty(teamProjects)){
                clientEmails = getClientEmails(teamProjects);
            }

            TeamMember teamLeadMember = teamDao.getTeamLeadByTeamID(teamId);
            String leadEmail = employeeDao.getEmployeeEmailsByEmployeeID(teamLeadMember.getEmployee().getEmployeeId()).get(0).getEmail();
            String memberEmail;

            if(!Utility.isNullOrEmpty(assignedTeamMembers)) {
                for (TeamMember assignMember : assignedTeamMembers) {

                    contentObj = EmailContent.getContentForTeamMemberAssignUnAssign(assignMember.getEmployee(),
                            team.getName(), null, null, teamLeadMember.getEmployee(),
                            tenantName, hrManagerEmailList);
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.TEAM_MEMBER_ASSIGN_TEMPLATE_ID_FOR_MANAGER_HR));

                    contentObj = EmailContent.getContentForTeamMemberAssignUnAssign(assignMember.getEmployee(), team.getName(),
                            null, null, teamLeadMember.getEmployee(), tenantName, new JSONArray().put(leadEmail));
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.TEAM_MEMBER_ASSIGN_TEMPLATE_ID_FOR_LEAD));

                    memberEmail = employeeDao.getEmployeeEmailsByEmployeeID(assignMember.getEmployee().getEmployeeId()).get(0).getEmail();
                    contentObj = EmailContent.getContentForTeamMemberAssignUnAssign(assignMember.getEmployee(), team.getName(),
                                null, null, teamLeadMember.getEmployee(), tenantName, new JSONArray().put(memberEmail));
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.TEAM_MEMBER_ASSIGN_TEMPLATE_ID_FOR_EMPLOYEE));

                    if(clientEmails.length() > 0){
                        contentObj = EmailContent.getContentForTeamMemberAssignUnAssign(assignMember.getEmployee(), team.getName(),
                                null, null, teamLeadMember.getEmployee(), tenantName, clientEmails);
                        notificationList.put(EmailContent.getNotificationObject(contentObj,
                                NotificationConstant.TEAM_MEMBER_ASSIGN_TEMPLATE_ID_FOR_CLIENT));
                    }
                }
            }

            if(!Utility.isNullOrEmpty(unassignedTeamMembers)){
                for(TeamMember unassignedMember : unassignedTeamMembers){

                    contentObj = EmailContent.getContentForTeamMemberAssignUnAssign(unassignedMember.getEmployee(),
                            team.getName(), null, null, teamLeadMember.getEmployee(),
                            tenantName, hrManagerEmailList);
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.TEAM_MEMBER_UNASSIGNED_TEMPLATE_ID_FOR_MANAGER_HR));

                    contentObj = EmailContent.getContentForTeamMemberAssignUnAssign(unassignedMember.getEmployee(), team.getName(),
                            null, null, teamLeadMember.getEmployee(), tenantName, new JSONArray().put(leadEmail));
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.TEAM_MEMBER_UNASSIGNED_TEMPLATE_ID_FOR_LEAD));

                    memberEmail = employeeDao.getEmployeeEmailsByEmployeeID(unassignedMember.getEmployee().getEmployeeId()).get(0).getEmail();
                    contentObj = EmailContent.getContentForTeamMemberAssignUnAssign(unassignedMember.getEmployee(), team.getName(),
                            null, null, teamLeadMember.getEmployee(), tenantName, new JSONArray().put(memberEmail));
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.TEAM_MEMBER_UNASSIGNED_TEMPLATE_ID_FOR_EMPLOYEE));

                    if(clientEmails.length() > 0){
                        contentObj = EmailContent.getContentForTeamMemberAssignUnAssign(unassignedMember.getEmployee(), team.getName(),
                                null, null, teamLeadMember.getEmployee(), tenantName, clientEmails);
                        notificationList.put(EmailContent.getNotificationObject(contentObj,
                                NotificationConstant.TEAM_MEMBER_UNASSIGNED_TEMPLATE_ID_FOR_CLIENT));
                    }
                }
            }

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/

        close(session);
        return TRANSFORMER.getTeamMembersDto(teamMembersList);
    }

    private void validateInputForMemberCreation(TeamMember member, Session session) throws CustomException {
        if(member.getEmployee().getUserId() == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0006);
            throw new CustomException(errorMessage);
        }

        if(member.getRole().getRoleId() == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0007);
            throw new CustomException(errorMessage);
        }
    }

    private void saveTeamMember(TeamMember teamMember, Team team) throws CustomException {
        TeamMember existMember = teamDao.getTeamMemberByTeamIDAndUserID(team.getTeamId(),
                teamMember.getEmployee().getUserId());
        if(existMember == null){
            teamMember.setVersion(team.getVersion());
            teamMember.setTeam(team);
            teamMember.setRole(teamDao.getRoleTypeByRoleId(teamMember.getRole().getRoleId()));
            teamMember.setEmployee(employeeDao.getEmployeeByUserID(teamMember.getEmployee().getUserId()));
            teamDao.saveTeamMember(teamMember);
            logger.info("Save team members success.");
        }
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
    public List<TeamProjectDto> createTeamProjects(String teamId, List<TeamProjectDto> teamProjects) throws CustomException {
        logger.info("Convert TeamProject Dto to ProjectTeam Object");

        List<ProjectTeam> projectTeams = TRANSFORMER.getProjectTeams(teamProjects);
        if(Utility.isNullOrEmpty(projectTeams)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0003);
            throw new CustomException(errorMessage);
        }

        Session session = getSession();
        projectDao.setSession(session);
        teamDao.setSession(session);
        Team team = teamDao.getTeamByID(teamId);

        List<ProjectTeam> assignedProjectTeam = new ArrayList<>();
        List<ProjectTeam> unassignedProjectTeam = new ArrayList<>();

        for(ProjectTeam projectTeam : projectTeams){

            switch (projectTeam.getActivity()){
                case 1:
                    logger.info("Team project create:: Start");
                    if(projectTeam.getProject().getProjectId() == null){
                        close(session);
                        ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                                Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0003);
                        throw new CustomException(errorMessage);
                    }

                    saveTeamProject(projectTeam, team);
                    logger.info("Team project create:: End");

                    assignedProjectTeam.add(projectTeam);
                    break;

                case 2:
                    logger.info("Delete team project:: Start");
                    if(projectTeam.getProject().getProjectId() == null){
                        close(session);
                        ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                                Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_TEAM_ERROR_TYPE_0003);
                        throw new CustomException(errorMessage);
                    }

                    teamDao.deleteProjectTeamByProjectId(teamId, projectTeam.getProject().getProjectId());
                    logger.info("Delete team project:: End");

                    unassignedProjectTeam.add(projectTeam);
                    break;
            }
        }
        List<ProjectTeam> teamProjectList = teamDao.getProjectTeams(teamId);

        /*JSONObject contentObj;
        JSONArray memberEmails = new JSONArray();
        try {

            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();
            JSONArray hrManagerEmailList = notificationService.getHrManagerEmailList();

            List<TeamMember> teamMembersList = teamDao.getTeamMembers(teamId, null);
            if(!Utility.isNullOrEmpty(teamMembersList)) {
                for (TeamMember member : teamMembersList) {
                    memberEmails.put(employeeDao.getEmployeeEmailsByEmployeeID(member.getEmployee().getEmployeeId())
                            .get(0).getEmail());
                }
            }

            JSONArray clientEmails = new JSONArray();
            if(!Utility.isNullOrEmpty(teamProjectList)) {
                clientEmails = getClientEmails(teamProjectList);
            }

            if(!Utility.isNullOrEmpty(assignedProjectTeam)) {
                for (ProjectTeam assignProject : assignedProjectTeam) {

                    contentObj = EmailContent.getContentForProjectTeamAssignUnAssign(assignProject,null, hrManagerEmailList);
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.TEAM_PROJECT_ASSIGNED_TEMPLATE_ID_FOR_MANAGER_HR));

                    if(memberEmails.length() > 0) {
                        contentObj = EmailContent.getContentForProjectTeamAssignUnAssign(assignProject,null, memberEmails);
                        notificationList.put(EmailContent.getNotificationObject(contentObj,
                                NotificationConstant.TEAM_PROJECT_ASSIGNED_TEMPLATE_ID_FOR_MEMBERS));
                    }

                    if(clientEmails.length() > 0){
                        contentObj = EmailContent.getContentForProjectTeamAssignUnAssign(assignProject,null, clientEmails);
                        notificationList.put(EmailContent.getNotificationObject(contentObj,
                                NotificationConstant.TEAM_PROJECT_ASSIGNED_TEMPLATE_ID_FOR_CLIENT));
                    }
                }
            }

            if(!Utility.isNullOrEmpty(unassignedProjectTeam)){
                for(ProjectTeam unAssignProject : unassignedProjectTeam){

                    contentObj = EmailContent.getContentForProjectTeamAssignUnAssign(unAssignProject,null, hrManagerEmailList);
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.TEAM_PROJECT_UNASSIGNED_TEMPLATE_ID_FOR_MANAGER_HR));

                    if(memberEmails.length() > 0) {
                        contentObj = EmailContent.getContentForProjectTeamAssignUnAssign(unAssignProject,null, memberEmails);
                        notificationList.put(EmailContent.getNotificationObject(contentObj,
                                NotificationConstant.TEAM_PROJECT_UNASSIGNED_TEMPLATE_ID_FOR_MEMBERS));
                    }

                    if(clientEmails.length() > 0){
                        contentObj = EmailContent.getContentForProjectTeamAssignUnAssign(unAssignProject,null, clientEmails);
                        notificationList.put(EmailContent.getNotificationObject(contentObj,
                                NotificationConstant.TEAM_PROJECT_UNASSIGNED_TEMPLATE_ID_FOR_CLIENT));
                    }
                }
            }

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/

        close(session);
        return TRANSFORMER.getProjectTeamsDto(teamProjectList);
    }

    private void saveTeamProject(ProjectTeam projectTeam, Team team) throws CustomException {
        ProjectTeam existProjectTeam = teamDao.getProjectTeamByTeamIdAndProjectId(team.getTeamId(),
                projectTeam.getProject().getProjectId());
        if(existProjectTeam == null){
            projectTeam.setProject(projectDao.getProjectByID(projectTeam.getProject().getProjectId()));
            projectTeam.setTeam(team);
            projectTeam.setVersion(team.getVersion());
            teamDao.saveTeamProject(projectTeam);
            logger.info("Save team projects success.");
        }
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

    private Team setTeamAllProperty(Team team) throws CustomException {

        List<TeamMember> memberList = teamDao.getTeamMembers(team.getTeamId(), null);
        if(!Utility.isNullOrEmpty(memberList)){
            for(TeamMember member : memberList){
                Employee employee = employeeDao.getEmployeeByID(member.getEmployee().getEmployeeId());
                member.setEmployee(employee);
            }
            team.setMembers(memberList);
        }

        List<ProjectTeam> projectTeams = teamDao.getProjectTeams(team.getTeamId());
        if(!Utility.isNullOrEmpty(projectTeams)){
            team.setProjects(projectTeams);
        }

        return team;
    }

    private JSONArray getClientEmails(List<ProjectTeam> teamProjects) {
        JSONArray emailArray = new JSONArray();
        for(ProjectTeam teamProject : teamProjects){
            List<ProjectClient> projectClients = projectDao.getProjectClients(teamProject.getProject().getProjectId());
            if(!Utility.isNullOrEmpty(projectClients)){
                for(ProjectClient projectClient : projectClients){
                    if(projectClient.getClient().isNotify()) {
                        emailArray.put(projectClient.getClient().getMemberEmail());
                    }
                }
            }
        }
        return emailArray;
    }
}
