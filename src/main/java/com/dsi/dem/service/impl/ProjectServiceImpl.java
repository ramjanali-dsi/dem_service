package com.dsi.dem.service.impl;

import com.dsi.dem.dao.ClientDao;
import com.dsi.dem.dao.ProjectDao;
import com.dsi.dem.dao.TeamDao;
import com.dsi.dem.dao.impl.ClientDaoImpl;
import com.dsi.dem.dao.impl.ProjectDaoImpl;
import com.dsi.dem.dao.impl.TeamDaoImpl;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Project;
import com.dsi.dem.model.ProjectClient;
import com.dsi.dem.model.ProjectTeam;
import com.dsi.dem.model.Team;
import com.dsi.dem.service.ProjectService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public class ProjectServiceImpl implements ProjectService {

    private static final Logger logger = Logger.getLogger(ProjectServiceImpl.class);

    private static final ProjectDao projectDao = new ProjectDaoImpl();
    private static final TeamDao teamDao = new TeamDaoImpl();
    private static final ClientDao clientDao = new ClientDaoImpl();

    @Override
    public void saveProject(Project project) throws CustomException {
        validateInputForCreation(project);

        boolean res = projectDao.saveProject(project);
        if(!res){
            ErrorContext errorContext = new ErrorContext(null, "Project", "Project create failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Save project success");
    }

    private void validateInputForCreation(Project project) throws CustomException {
        if(project.getProjectName() == null){
            ErrorContext errorContext = new ErrorContext(null, "Project", "Project Name not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(project.getStatus().getProjectStatusId() == null){
            ErrorContext errorContext = new ErrorContext(null, "Project", "Project status not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(projectDao.getProjectByName(project.getProjectName()) != null){
            ErrorContext errorContext = new ErrorContext(project.getProjectName(), "Project", "Project already exist by this name.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateProject(Project project) throws CustomException {
        validateInputForUpdate(project);

        boolean res = projectDao.updateProject(project);
        if(!res){
            ErrorContext errorContext = new ErrorContext(project.getProjectId(), "Project", "Project update failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Update project success");
    }

    private void validateInputForUpdate(Project project) throws CustomException {
        if(project.getVersion() == 0){
            ErrorContext errorContext = new ErrorContext(null, "Project", "Version not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(projectDao.getProjectByID(project.getProjectId()) == null){
            ErrorContext errorContext = new ErrorContext(project.getProjectId(), "Project", "Project not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteProject(String projectID) throws CustomException {
        projectDao.deleteProjectTeam(projectID, null);
        projectDao.deleteProjectClient(projectID, null);

        boolean res = projectDao.deleteProject(projectID);
        if(!res){
            ErrorContext errorContext = new ErrorContext(projectID, "Project", "Project delete failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete project success");
    }

    @Override
    public Project getProjectByID(String projectID) throws CustomException {
        Project project = projectDao.getProjectByID(projectID);
        if(project == null){
            ErrorContext errorContext = new ErrorContext(projectID, "Project", "Project not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return setProjectAllProperty(project);
    }

    @Override
    public List<Project> getAllProjects() throws CustomException {
        List<Project> projectList = projectDao.getAllProjects();
        if(projectList == null){
            ErrorContext errorContext = new ErrorContext(null, "Project", "Project list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        List<Project> projects = new ArrayList<>();
        for(Project project : projectList){
            projects.add(setProjectAllProperty(project));
        }
        return projects;
    }

    @Override
    public List<Project> searchProjects(String projectName, String status, String clientName,
                                        String teamName, String memberName) throws CustomException {

        List<Project> projectList = projectDao.searchProjects(projectName, status, clientName, teamName, memberName);
        if(projectList == null){
            ErrorContext errorContext = new ErrorContext(null, "Project", "Project list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        List<Project> projects = new ArrayList<>();
        for(Project project : projectList){
            projects.add(setProjectAllProperty(project));
        }
        return projects;
    }

    @Override
    public void saveProjectTeam(List<String> teamIds, Project project) throws CustomException {

        for(String teamID : teamIds){
            ProjectTeam projectTeam = new ProjectTeam();
            projectTeam.setTeam(teamDao.getTeamByID(teamID));
            projectTeam.setProject(project);
            projectTeam.setVersion(project.getVersion());

            boolean res = teamDao.saveTeamProject(projectTeam);
            if(!res){
                ErrorContext errorContext = new ErrorContext(null, "ProjectTeam", "Project team create failed.");
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                        Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
                throw new CustomException(errorMessage);
            }
            logger.info("Save project team success");
        }
    }

    @Override
    public void deleteProjectTeam(String projectTeamID) throws CustomException {
        boolean res = projectDao.deleteProjectTeam(null, projectTeamID);
        if(!res){
            ErrorContext errorContext = new ErrorContext(projectTeamID, "ProjectTeam", "Project team delete failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete project team success");
    }

    @Override
    public List<ProjectTeam> getProjectTeams(String projectID) throws CustomException {
        List<ProjectTeam> projectTeams = projectDao.getProjectTeams(projectID);
        if(projectTeams == null){
            ErrorContext errorContext = new ErrorContext(projectID, "ProjectTeam", "Project teams not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return projectTeams;
    }

    @Override
    public void saveProjectClient(List<String> clientIds, Project project) throws CustomException {

        for(String clientID : clientIds){
            ProjectClient projectClient = new ProjectClient();
            projectClient.setProject(project);
            projectClient.setClient(clientDao.getClientByID(clientID));

            boolean res = projectDao.saveProjectClient(projectClient);
            if(!res){
                ErrorContext errorContext = new ErrorContext(null, "ProjectClient", "Project client create failed.");
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                        Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
                throw new CustomException(errorMessage);
            }
            logger.info("Save project client success");
        }
    }

    @Override
    public void deleteProjectClient(String projectClientID) throws CustomException {
        boolean res = projectDao.deleteProjectClient(null, projectClientID);
        if(!res){
            ErrorContext errorContext = new ErrorContext(projectClientID, "ProjectClient", "Project client delete failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete project client success");
    }

    @Override
    public List<ProjectClient> getProjectClients(String projectID) throws CustomException {
        List<ProjectClient> projectClients = projectDao.getProjectClients(projectID);
        if(projectClients == null){
            ErrorContext errorContext = new ErrorContext(projectID, "ProjectClient", "Project clients not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return projectClients;
    }

    private Project setProjectAllProperty(Project project) throws CustomException {

        List<ProjectTeam> projectTeams = projectDao.getProjectTeams(project.getProjectId());
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
