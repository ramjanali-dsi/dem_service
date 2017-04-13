package com.dsi.dem.service.impl;

import com.dsi.dem.dao.ClientDao;
import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.ProjectDao;
import com.dsi.dem.dao.TeamDao;
import com.dsi.dem.dao.impl.ClientDaoImpl;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.dao.impl.ProjectDaoImpl;
import com.dsi.dem.dao.impl.TeamDaoImpl;
import com.dsi.dem.dto.ContextDto;
import com.dsi.dem.dto.ProjectClientDto;
import com.dsi.dem.dto.ProjectDto;
import com.dsi.dem.dto.ProjectTeamDto;
import com.dsi.dem.dto.transformer.ProjectDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.NotificationService;
import com.dsi.dem.service.ProjectService;
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
public class ProjectServiceImpl extends CommonService implements ProjectService {

    private static final Logger logger = Logger.getLogger(ProjectServiceImpl.class);

    private static final ProjectDtoTransformer TRANSFORMER = new ProjectDtoTransformer();
    private static final ProjectDao projectDao = new ProjectDaoImpl();
    private static final TeamDao teamDao = new TeamDaoImpl();
    private static final ClientDao clientDao = new ClientDaoImpl();
    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private static final NotificationService notificationService = new NotificationServiceImpl();

    @Override
    public ProjectDto saveProject(ProjectDto projectDto, String tenantName) throws CustomException {
        logger.info("Project Create:: Start");
        logger.info("Convert Project Dto to Project Object");
        Project project = TRANSFORMER.getProject(projectDto);

        validateInputForCreation(project);
        Session session = getSession();
        projectDao.setSession(session);
        teamDao.setSession(session);

        if(projectDao.getProjectByName(project.getProjectName()) != null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0004);
            throw new CustomException(errorMessage);
        }
        project.setVersion(1);
        projectDao.saveProject(project);
        logger.info("Save project success");

        int cnt = 0;
        String teamName = "";
        for(ProjectTeam projectTeam : project.getTeams()){
            Team team = teamDao.getTeamByID(projectTeam.getTeam().getTeamId());

            projectTeam.setTeam(team);
            projectTeam.setProject(project);
            projectTeam.setVersion(1);
            projectDao.saveProjectTeam(projectTeam);

            teamName += team.getName();
            if(cnt != project.getTeams().size() - 1){
                teamName += ",";
            }
            cnt++;
        }
        logger.info("Save project teams success");

        if(!Utility.isNullOrEmpty(project.getClients())){
            clientDao.setSession(session);

            for(ProjectClient projectClient : project.getClients()){
                projectClient.setClient(clientDao.getClientByID(projectClient.getClient().getClientId()));
                projectClient.setProject(project);
                projectClient.setVersion(1);
                projectDao.saveProjectClient(projectClient);
            }
            logger.info("Save project clients success");
        }

        logger.info("Project Create:: End");
        setProjectAllProperty(project);
        close(session);

        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = notificationService.getHrManagerEmailList();
            JSONObject contentObj = EmailContent.getContentForProject(project, tenantName, teamName, emailList);
            notificationList.put(EmailContent.getNotificationObject(contentObj,
                    NotificationConstant.PROJECT_CREATE_TEMPLATE_ID));

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        return TRANSFORMER.getProjectDto(project);
    }

    private void validateInputForCreation(Project project) throws CustomException {
        if(project.getProjectName() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0005);
            throw new CustomException(errorMessage);
        }

        if(project.getStatus().getProjectStatusId() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0006);
            throw new CustomException(errorMessage);
        }

        if(Utility.isNullOrEmpty(project.getTeams())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public ProjectDto updateProject(String projectId, ProjectDto projectDto, String tenantName) throws CustomException {

        logger.info("Project Update:: Start");
        logger.info("Convert Project Dto to Project Object");
        Project project = TRANSFORMER.getProject(projectDto);

        validateInputForUpdate(project);

        Session session = getSession();
        projectDao.setSession(session);

        Project existProject = projectDao.getProjectByID(projectId);
        if(existProject == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        existProject.setDescription(project.getDescription());
        existProject.setProjectName(project.getProjectName());
        existProject.setStatus(projectDao.getProjectStatusById(project.getStatus().getProjectStatusId()));
        existProject.setVersion(project.getVersion());
        projectDao.updateProject(existProject);
        logger.info("Update project success");

        setProjectAllProperty(existProject);
        logger.info("Project Update:: End");
        close(session);

        int cnt = 0;
        String teamName = "";
        for(ProjectTeam projectTeam : existProject.getTeams()){
            teamName += projectTeam.getTeam().getName();
            if(cnt != existProject.getTeams().size()){
                teamName += ",";
            }
            cnt++;
        }

        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = notificationService.getHrManagerEmailList();
            JSONObject contentObj = EmailContent.getContentForProject(existProject, tenantName, teamName, emailList);
            notificationList.put(EmailContent.getNotificationObject(contentObj,
                    NotificationConstant.PROJECT_UPDATE_TEMPLATE_ID));

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        return TRANSFORMER.getProjectDto(existProject);
    }

    private void validateInputForUpdate(Project project) throws CustomException {
        if(project.getVersion() == 0){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public String deleteProject(String projectID, String tenantName) throws CustomException {
        logger.info("Project delete:: Start");
        Session session = getSession();
        projectDao.setSession(session);

        /*projectDao.deleteProjectTeam(projectID, null);
        projectDao.deleteProjectClient(projectID, null);

        projectDao.deleteProject(projectID);
        logger.info("Delete project success");
        logger.info("Project delete:: End");
        close(session);*/

        if(!Utility.isNullOrEmpty(projectDao.getProjectClients(projectID))){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0007);
            throw new CustomException(errorMessage);
        }

        Project project = projectDao.getProjectByID(projectID);
        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = notificationService.getHrManagerEmailList();
            JSONObject contentObj = EmailContent.getContentForProject(project, tenantName, null, emailList);
            notificationList.put(EmailContent.getNotificationObject(contentObj,
                    NotificationConstant.PROJECT_DELETE_TEMPLATE_ID));

            projectDao.deleteProjectTeam(projectID, null);
            projectDao.deleteProjectClient(projectID, null);

            projectDao.deleteProject(projectID);
            logger.info("Delete project success");
            logger.info("Project delete:: End");
            close(session);

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        return null;
    }

    @Override
    public ProjectDto getProjectByID(String projectID) throws CustomException {
        logger.info("Read project :: Start");
        Session session = getSession();
        projectDao.setSession(session);

        Project project = projectDao.getProjectByID(projectID);
        if(project == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        logger.info("Set project property.");
        setProjectAllProperty(project);
        logger.info("Read project :: End");

        close(session);
        return TRANSFORMER.getProjectDto(project);
    }

    @Override
    public List<Project> getAllProjects() throws CustomException {
        Session session = getSession();
        projectDao.setSession(session);

        List<Project> projectList = projectDao.getAllProjects();
        if(projectList == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        List<Project> projects = new ArrayList<>();
        for(Project project : projectList){
            projects.add(setProjectAllProperty(project));
        }

        close(session);
        return projects;
    }

    @Override
    public List<ProjectDto> searchProjects(String projectName, String status, String clientName, String teamName, String memberName,
                                           String context, String from, String range) throws CustomException {

        logger.info("Read all projects :: Start");
        Session session = getSession();
        projectDao.setSession(session);

        ContextDto contextDto = Utility.getContextDtoObj(context);
        //List<String> contextList = Utility.getContextObj(context);
        List<Project> projectList = projectDao.searchProjects(projectName, status, clientName, teamName, memberName, contextDto,
                from, range);
        if(projectList == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        logger.info("Set projects property.");
        List<Project> projects = new ArrayList<>();
        for(Project project : projectList){
            projects.add(setProjectAllProperty(project));
        }
        logger.info("Read all projects :: End");

        close(session);
        return TRANSFORMER.getProjectsDto(projects);
    }

    @Override
    public List<ProjectTeamDto> createProjectTeams(List<ProjectTeamDto> teamDtoList, String projectId,
                                                   String tenantName) throws CustomException {
        logger.info("Convert ProjectTeam Dto to ProjectTeam Object");
        List<ProjectTeam> projectTeams = TRANSFORMER.getProjectTeams(teamDtoList);
        if(Utility.isNullOrEmpty(projectTeams)) {
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

        Session session = getSession();
        projectDao.setSession(session);
        teamDao.setSession(session);
        Project project = projectDao.getProjectByID(projectId);

        List<ProjectTeam> assignedProjectTeam = new ArrayList<>();
        List<ProjectTeam> unassignedProjectTeam = new ArrayList<>();

        for(ProjectTeam projectTeam : projectTeams){

            switch (projectTeam.getActivity()){
                case 1:
                    logger.info("Create project team:: Start");
                    validateInputForProjectTeam(projectTeam, session);

                    saveProjectTeam(projectTeam, project);
                    logger.info("Create project team:: End");

                    projectTeam.setProject(project);
                    assignedProjectTeam.add(projectTeam);
                    break;

                case 2:
                    logger.info("Delete project team:: Start");
                    validateInputForProjectTeam(projectTeam, session);

                    teamDao.deleteProjectTeamByProjectId(projectTeam.getTeam().getTeamId(), projectId);
                    logger.info("Delete project team:: End");

                    projectTeam.setProject(project);
                    unassignedProjectTeam.add(projectTeam);
                    break;
            }
        }
        List<ProjectTeam> projectTeamList = projectDao.getProjectTeams(projectId, null);

        JSONObject contentObj;
        JSONArray memberEmails;
        try {

            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();
            JSONArray hrManagerEmailList = notificationService.getHrManagerEmailList();

            JSONArray clientEmails = new JSONArray();
            List<ProjectClient> projectClients = projectDao.getProjectClients(projectId);
            if(!Utility.isNullOrEmpty(projectClients)) {
                for(ProjectClient projectClient : projectClients){
                    if(projectClient.getClient().isNotify()) {
                        clientEmails.put(projectClient.getClient().getMemberEmail());
                    }
                }
            }

            if(!Utility.isNullOrEmpty(assignedProjectTeam)) {
                Employee leadMember = null;
                for (ProjectTeam assignProject : assignedProjectTeam) {

                    memberEmails = new JSONArray();
                    List<TeamMember> teamMembersList = teamDao.getTeamMembers(assignProject.getTeam().getTeamId(),null);
                    if(!Utility.isNullOrEmpty(teamMembersList)) {
                        for (TeamMember member : teamMembersList) {
                            memberEmails.put(employeeDao.getPreferredEmail(member.getEmployee().getEmployeeId()).getEmail());

                            if(member.getRole().getRoleName().equals(RoleName.LEAD.getValue())){
                                leadMember = member.getEmployee();
                            }
                        }
                    }

                    contentObj = EmailContent.getContentForProjectTeamAssignUnAssign(assignProject, tenantName, leadMember,
                            teamMembersList.size(), hrManagerEmailList);
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.TEAM_PROJECT_ASSIGNED_TEMPLATE_ID_FOR_MANAGER_HR));

                    if(memberEmails.length() > 0) {
                        contentObj = EmailContent.getContentForProjectTeamAssignUnAssign(assignProject, tenantName, leadMember,
                                teamMembersList.size(), memberEmails);
                        notificationList.put(EmailContent.getNotificationObject(contentObj,
                                NotificationConstant.TEAM_PROJECT_ASSIGNED_TEMPLATE_ID_FOR_MEMBERS));
                    }

                    if(clientEmails.length() > 0){
                        contentObj = EmailContent.getContentForProjectTeamAssignUnAssign(assignProject, tenantName, leadMember,
                                teamMembersList.size(), clientEmails);
                        notificationList.put(EmailContent.getNotificationObject(contentObj,
                                NotificationConstant.TEAM_PROJECT_ASSIGNED_TEMPLATE_ID_FOR_CLIENT));
                    }
                }
            }

            if(!Utility.isNullOrEmpty(unassignedProjectTeam)){
                Employee leadMember = null;
                for(ProjectTeam unAssignProject : unassignedProjectTeam){

                    memberEmails = new JSONArray();
                    List<TeamMember> teamMembersList = teamDao.getTeamMembers(unAssignProject.getTeam().getTeamId(),null);
                    if(!Utility.isNullOrEmpty(teamMembersList)) {
                        for (TeamMember member : teamMembersList) {
                            memberEmails.put(employeeDao.getPreferredEmail(member.getEmployee().getEmployeeId()).getEmail());

                            if(member.getRole().getRoleName().equals(RoleName.LEAD.getValue())){
                                leadMember = member.getEmployee();
                            }
                        }
                    }

                    contentObj = EmailContent.getContentForProjectTeamAssignUnAssign(unAssignProject, tenantName, leadMember,
                            teamMembersList.size(),  hrManagerEmailList);
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.TEAM_PROJECT_UNASSIGNED_TEMPLATE_ID_FOR_MANAGER_HR));

                    if(memberEmails.length() > 0) {
                        contentObj = EmailContent.getContentForProjectTeamAssignUnAssign(unAssignProject, tenantName, leadMember,
                                teamMembersList.size(), memberEmails);
                        notificationList.put(EmailContent.getNotificationObject(contentObj,
                                NotificationConstant.TEAM_PROJECT_UNASSIGNED_TEMPLATE_ID_FOR_MEMBERS));
                    }

                    if(clientEmails.length() > 0){
                        contentObj = EmailContent.getContentForProjectTeamAssignUnAssign(unAssignProject, tenantName, leadMember,
                                teamMembersList.size(), clientEmails);
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
        }

        close(session);
        return TRANSFORMER.getProjectTeamsDto(projectTeamList);
    }

    private void validateInputForProjectTeam(ProjectTeam projectTeam, Session session) throws CustomException {
        if(projectTeam.getTeam().getTeamId() == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }
    }

    private void saveProjectTeam(ProjectTeam projectTeam, Project project) throws CustomException {
        ProjectTeam existProjectTeam = projectDao.getProjectTeamByTeamIdAndProjectId(projectTeam.getTeam().getTeamId(),
                project.getProjectId());
        if (existProjectTeam == null) {
            projectTeam.setTeam(teamDao.getTeamByID(projectTeam.getTeam().getTeamId()));
            projectTeam.setProject(project);
            projectTeam.setVersion(1);
            projectDao.saveProjectTeam(projectTeam);
            logger.info("Save project team success.");
        }
    }

    @Override
    public void deleteProjectTeam(String projectTeamID) throws CustomException {
        logger.info("Project team delete:: Start");
        Session session = getSession();
        projectDao.setSession(session);

        projectDao.deleteProjectTeam(null, projectTeamID);
        logger.info("Delete project team success");
        logger.info("Project team delete:: end");

        close(session);
    }

    @Override
    public List<ProjectTeam> getProjectTeams(String projectID, String employeeID) throws CustomException {
        Session session = getSession();
        projectDao.setSession(session);

        List<ProjectTeam> projectTeams = projectDao.getProjectTeams(projectID, employeeID);
        if(projectTeams == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        close(session);
        return projectTeams;
    }

    @Override
    public List<ProjectClientDto> createProjectClients(List<ProjectClientDto> clientDtoList, String projectId,
                                                       String tenantName) throws CustomException {
        logger.info("Convert ProjectClient Dto to ProjectClient Object");
        List<ProjectClient> projectClients = TRANSFORMER.getProjectClients(clientDtoList);
        if(Utility.isNullOrEmpty(projectClients)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0003);
            throw new CustomException(errorMessage);
        }

        Session session = getSession();
        projectDao.setSession(session);
        clientDao.setSession(session);
        Project project = projectDao.getProjectByID(projectId);

        List<ProjectClient> assignedProjectClient = new ArrayList<>();
        List<ProjectClient> unassignedProjectClient = new ArrayList<>();

        for(ProjectClient projectClient : projectClients){

            switch (projectClient.getActivity()){
                case 1:
                    logger.info("Project client create:: Start");
                    validateInput(projectClient, session);

                    saveProjectClient(projectClient, project);
                    logger.info("Project client create:: End");

                    projectClient.setProject(project);
                    assignedProjectClient.add(projectClient);
                    break;

                case 2:
                    logger.info("Delete project client:: Start");
                    validateInput(projectClient, session);

                    projectDao.deleteProjectClientByClientId(projectId, projectClient.getClient().getClientId());
                    logger.info("Delete project client:: End");

                    projectClient.setProject(project);
                    unassignedProjectClient.add(projectClient);
                    break;
            }
        }
        List<ProjectClient> projectClientList = projectDao.getProjectClients(projectId);

        JSONObject contentObj;
        try {

            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray hrManagerEmailList = notificationService.getHrManagerEmailList();

            JSONArray memberEmails = new JSONArray();
            List<TeamMember> teamMembers = projectDao.getTeamMembersByProjectId(projectId);
            if(!Utility.isNullOrEmpty(teamMembers)){
                for(TeamMember member : teamMembers){
                    memberEmails.put(employeeDao.getPreferredEmail(member.getEmployee().getEmployeeId()).getEmail());
                }
            }

            if(!Utility.isNullOrEmpty(assignedProjectClient)) {
                for (ProjectClient assignedClient : assignedProjectClient) {

                    contentObj = EmailContent.getContentForProjectClientAssignUnAssign(assignedClient, tenantName, hrManagerEmailList);
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.PROJECT_CLIENT_ASSIGNED_TEMPLATE_ID_FOR_MANAGER_HR));

                    if(memberEmails.length() > 0) {
                        contentObj = EmailContent.getContentForProjectClientAssignUnAssign(assignedClient, tenantName, memberEmails);
                        notificationList.put(EmailContent.getNotificationObject(contentObj,
                                NotificationConstant.PROJECT_CLIENT_ASSIGNED_TEMPLATE_ID_FOR_MEMBERS));
                    }

                    contentObj = EmailContent.getContentForProjectClientAssignUnAssign(assignedClient, tenantName,
                            new JSONArray().put(assignedClient.getClient().getMemberEmail()));
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.PROJECT_CLIENT_ASSIGNED_TEMPLATE_ID_FOR_CLIENT));

                }
            }

            if(!Utility.isNullOrEmpty(unassignedProjectClient)){
                for(ProjectClient unassignedClient : unassignedProjectClient){

                    contentObj = EmailContent.getContentForProjectClientAssignUnAssign(unassignedClient, tenantName, hrManagerEmailList);
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.PROJECT_CLIENT_UNASSIGNED_TEMPLATE_ID_FOR_MANAGER_HR));

                    if(memberEmails.length() > 0) {
                        contentObj = EmailContent.getContentForProjectClientAssignUnAssign(unassignedClient, tenantName, memberEmails);
                        notificationList.put(EmailContent.getNotificationObject(contentObj,
                                NotificationConstant.PROJECT_CLIENT_UNASSIGNED_TEMPLATE_ID_FOR_MEMBERS));
                    }

                    /*contentObj = EmailContent.getContentForProjectClientAssignUnAssign(unassignedClient, tenantName,
                            new JSONArray().put(unassignedClient.getClient().getMemberEmail()));
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.PROJECT_CLIENT_UNASSIGNED_TEMPLATE_ID_FOR_CLIENT));*/

                }
            }

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        close(session);
        return TRANSFORMER.getProjectClientsDto(projectClientList);
    }

    private void validateInput(ProjectClient projectClient, Session session) throws CustomException {
        if(projectClient.getClient().getClientId() == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0003);
            throw new CustomException(errorMessage);
        }
    }

    private void saveProjectClient(ProjectClient projectClient, Project project) throws CustomException {
        ProjectClient existProjectClient = projectDao.getProjectClientByClientIdAndProjectId(projectClient.getClient().getClientId(),
                project.getProjectId());
        if(existProjectClient == null){
            projectClient.setClient(clientDao.getClientByID(projectClient.getClient().getClientId()));
            projectClient.setProject(project);
            projectClient.setVersion(1);
            projectDao.saveProjectClient(projectClient);
            logger.info("Save project client success.");
        }
    }

    @Override
    public void deleteProjectClient(String projectClientID) throws CustomException {
        logger.info("Project client delete:: Start");
        Session session = getSession();
        projectDao.setSession(session);

        projectDao.deleteProjectClient(null, projectClientID);
        logger.info("Delete project client success");
        logger.info("Project client delete:: End");

        close(session);
    }

    @Override
    public List<ProjectClient> getProjectClients(String projectID) throws CustomException {
        Session session = getSession();
        projectDao.setSession(session);

        List<ProjectClient> projectClients = projectDao.getProjectClients(projectID);
        if(projectClients == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        close(session);
        return projectClients;
    }

    private Project setProjectAllProperty(Project project) throws CustomException {

        List<ProjectTeam> projectTeams = projectDao.getProjectTeams(project.getProjectId(), null);
        if(!Utility.isNullOrEmpty(projectTeams)){
            project.setTeams(projectTeams);
        }

        List<ProjectClient> projectClients = projectDao.getProjectClients(project.getProjectId());
        if(!Utility.isNullOrEmpty(projectClients)){
            project.setClients(projectClients);
        }

        return project;
    }
}
