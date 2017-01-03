package com.dsi.dem.service.impl;

import com.dsi.dem.dao.ClientDao;
import com.dsi.dem.dao.ProjectDao;
import com.dsi.dem.dao.TeamDao;
import com.dsi.dem.dao.impl.ClientDaoImpl;
import com.dsi.dem.dao.impl.ProjectDaoImpl;
import com.dsi.dem.dao.impl.TeamDaoImpl;
import com.dsi.dem.dto.ProjectClientDto;
import com.dsi.dem.dto.ProjectDto;
import com.dsi.dem.dto.ProjectTeamDto;
import com.dsi.dem.dto.transformer.ProjectDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.ProjectService;
import com.dsi.dem.util.*;
import com.dsi.httpclient.HttpClient;
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

    private static final HttpClient httpClient = new HttpClient();

    @Override
    public ProjectDto saveProject(ProjectDto projectDto, String tenantName) throws CustomException {

        logger.info("Project Create:: Start");
        if(Utility.isNullOrEmpty(projectDto.getTeamIds())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

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

        saveProjectTeam(projectDto.getTeamIds(), project);

        if(!Utility.isNullOrEmpty(projectDto.getClientIds())){
            clientDao.setSession(session);
            saveProjectClient(projectDto.getClientIds(), project);
        }

        logger.info("Project Create:: End");
        setProjectAllProperty(project);
        close(session);

        /*try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = new JSONArray();
            //TODO Manager & HR email config

            JSONObject contentObj = EmailContent.getContentForProject(project, tenantName, emailList);
            notificationList.put(EmailContent.getNotificationObject(contentObj,
                    NotificationConstant.PROJECT_CREATE_TEMPLATE_ID));

            logger.info("Notification create request body :: " + notificationList.toString());
            String result = httpClient.sendPost(APIProvider.API_NOTIFICATION_CREATE, notificationList.toString(),
                    Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            JSONObject resultObj = new JSONObject(result);
            if(!resultObj.has(Constants.MESSAGE)){
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0009,
                        Constants.DEM_SERVICE_0009_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_010);
                throw new CustomException(errorMessage);
            }
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/

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

        /*try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = new JSONArray();
            //TODO Manager & HR email config

            JSONObject contentObj = EmailContent.getContentForProject(project, tenantName, emailList);
            notificationList.put(EmailContent.getNotificationObject(contentObj,
                    NotificationConstant.PROJECT_UPDATE_TEMPLATE_ID));

            logger.info("Notification create request body :: " + notificationList.toString());
            String result = httpClient.sendPost(APIProvider.API_NOTIFICATION_CREATE, notificationList.toString(),
                    Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            JSONObject resultObj = new JSONObject(result);
            if(!resultObj.has(Constants.MESSAGE)){
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0009,
                        Constants.DEM_SERVICE_0009_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_010);
                throw new CustomException(errorMessage);
            }
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/

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

        projectDao.deleteProjectTeam(projectID, null);
        projectDao.deleteProjectClient(projectID, null);

        projectDao.deleteProject(projectID);
        logger.info("Delete project success");
        logger.info("Project delete:: End");
        close(session);

        /*Project project = projectDao.getProjectByID(projectID);
        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = new JSONArray();
            //TODO Manager & HR email config

            JSONObject contentObj = EmailContent.getContentForProject(project, tenantName, emailList);
            notificationList.put(EmailContent.getNotificationObject(contentObj,
                    NotificationConstant.PROJECT_DELETE_TEMPLATE_ID));

            projectDao.deleteProjectTeam(projectID, null);
            projectDao.deleteProjectClient(projectID, null);

            projectDao.deleteProject(projectID);
            logger.info("Delete project success");
            logger.info("Project delete:: End");
            close(session);

            logger.info("Notification create request body :: " + notificationList.toString());
            String result = httpClient.sendPost(APIProvider.API_NOTIFICATION_CREATE, notificationList.toString(),
                    Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            JSONObject resultObj = new JSONObject(result);
            if(!resultObj.has(Constants.MESSAGE)){
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0009,
                        Constants.DEM_SERVICE_0009_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_010);
                throw new CustomException(errorMessage);
            }
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/

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
    public List<ProjectDto> searchProjects(String projectName, String status, String clientName,
                                        String teamName, String memberName, String from, String range) throws CustomException {

        logger.info("Read all projects :: Start");
        Session session = getSession();
        projectDao.setSession(session);

        List<Project> projectList = projectDao.searchProjects(projectName, status, clientName, teamName, memberName, from, range);
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
    public List<ProjectTeamDto> createProjectTeams(ProjectDto projectDto, String projectId) throws CustomException {
        logger.info("Project team create:: Start");
        if(Utility.isNullOrEmpty(projectDto.getTeamIds())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

        Session session = getSession();
        projectDao.setSession(session);
        teamDao.setSession(session);

        saveProjectTeam(projectDto.getTeamIds(), projectDao.getProjectByID(projectId));

        List<ProjectTeam> projectTeams = projectDao.getProjectTeams(projectId, null);
        logger.info("Project team create:: End");

        close(session);
        return TRANSFORMER.getProjectTeamsDto(projectTeams);
    }

    @Override
    public void saveProjectTeam(List<String> teamIds, Project project) throws CustomException {

        projectDao.deleteProjectTeam(project.getProjectId(), null);
        logger.info("Delete all project teams");

        for(String teamID : teamIds){
            ProjectTeam projectTeam = new ProjectTeam();
            projectTeam.setTeam(teamDao.getTeamByID(teamID));
            projectTeam.setProject(project);
            projectTeam.setVersion(project.getVersion());

            teamDao.saveTeamProject(projectTeam);
        }
        logger.info("Save project teams success");
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
    public List<ProjectClientDto> createProjectClients(ProjectDto projectDto, String projectId) throws CustomException {
        logger.info("Project client create:: Start");
        if(Utility.isNullOrEmpty(projectDto.getClientIds())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_PROJECT_ERROR_TYPE_0003);
            throw new CustomException(errorMessage);
        }

        Session session = getSession();
        projectDao.setSession(session);
        clientDao.setSession(session);

        saveProjectClient(projectDto.getClientIds(), projectDao.getProjectByID(projectId));

        List<ProjectClient> projectClients = projectDao.getProjectClients(projectId);
        logger.info("Project client create:: End");

        close(session);
        return TRANSFORMER.getProjectClientsDto(projectClients);
    }

    @Override
    public void saveProjectClient(List<String> clientIds, Project project) throws CustomException {

        projectDao.deleteProjectClient(project.getProjectId(), null);
        logger.info("Delete all project clients.");

        for (String clientID : clientIds) {
            Client client = clientDao.getClientByID(clientID);
            ProjectClient projectClient = new ProjectClient();
            projectClient.setProject(project);
            projectClient.setClient(client);
            projectClient.setVersion(client.getVersion());

            projectDao.saveProjectClient(projectClient);
        }
        logger.info("Save project clients success");
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
