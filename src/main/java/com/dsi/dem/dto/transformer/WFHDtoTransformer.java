package com.dsi.dem.dto.transformer;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.ProjectDao;
import com.dsi.dem.dao.TeamDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.dao.impl.ProjectDaoImpl;
import com.dsi.dem.dao.impl.TeamDaoImpl;
import com.dsi.dem.dto.WorkFromHomeDetails;
import com.dsi.dem.dto.WorkFromHomeDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.impl.CommonService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 1/11/17.
 */
public class WFHDtoTransformer extends CommonService {

    private static final ProjectDao projectDao = new ProjectDaoImpl();
    private static final TeamDao teamDao = new TeamDaoImpl();
    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();

    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    public WorkFromHome getWorkFromHomeRequest(WorkFromHomeDto workFromHomeDto) throws CustomException {

        WorkFromHome workFromHome = new WorkFromHome();
        try {
            BeanUtils.copyProperties(workFromHome, workFromHomeDto);

            WorkFormHomeStatus status = new WorkFormHomeStatus();
            BeanUtils.copyProperties(status, workFromHomeDto);
            workFromHome.setStatus(status);

        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return workFromHome;
    }

    public WorkFromHomeDto getWFHRequestDto(WorkFromHome workFromHome) throws CustomException {

        WorkFromHomeDto wfhDto = new WorkFromHomeDto();
        try{
            BeanUtils.copyProperties(wfhDto, workFromHome);
            BeanUtils.copyProperties(wfhDto, workFromHome.getStatus());
            BeanUtils.copyProperties(wfhDto, workFromHome.getEmployee());

            if(workFromHome.getApprovedBy() != null){
                Employee approvedBy = employeeDao.getEmployeeByID(workFromHome.getApprovedBy());
                String name = approvedBy.getFirstName();
                if (approvedBy.getLastName() != null) {
                    name += " " + approvedBy.getLastName();
                }
                wfhDto.setApprovedBy(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return wfhDto;
    }

    public List<WorkFromHomeDto> getAllWFHRequestDto(List<WorkFromHome> workFromHomes) throws CustomException {
        List<WorkFromHomeDto> wfhDtoList = new ArrayList<>();
        for(WorkFromHome workFromHome : workFromHomes){
            wfhDtoList.add(getWFHRequestDto(workFromHome));
        }
        return wfhDtoList;
    }

    public WorkFromHomeDetails getWFHRequestDetailsDto(WorkFromHome workFromHome) throws CustomException {

        teamDao.setSession(session);
        projectDao.setSession(session);

        WorkFromHomeDetails wfhDetails=  new WorkFromHomeDetails();
        try{
            BeanUtils.copyProperties(wfhDetails, workFromHome);
            BeanUtils.copyProperties(wfhDetails, workFromHome.getStatus());
            BeanUtils.copyProperties(wfhDetails, workFromHome.getEmployee());

            wfhDetails.setEmail(CommonTransformer.getEmail(employeeDao.getEmployeeEmailsByEmployeeID
                    (workFromHome.getEmployee().getEmployeeId())));
            wfhDetails.setPhone(CommonTransformer.getPhone(employeeDao.getEmployeeContactsByEmployeeID
                    (workFromHome.getEmployee().getEmployeeId())));
            wfhDetails.setDesignation(CommonTransformer.getDesignation(employeeDao.getEmployeeDesignationsByEmployeeID
                    (workFromHome.getEmployee().getEmployeeId())));

            List<TeamMember> memberTeams = teamDao.getTeamMembers(null, workFromHome.getEmployee().getEmployeeId());
            wfhDetails.setTeam(CommonTransformer.getTeamList(memberTeams));

            List<ProjectTeam> memberProjects = projectDao.getProjectTeams(null, workFromHome.getEmployee().getEmployeeId());
            wfhDetails.setProject(CommonTransformer.getProjectList(memberProjects));

        } catch (Exception e) {
            e.printStackTrace();
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return wfhDetails;
    }

    public List<WorkFromHomeDetails> getWFHRequestDetailsDto(List<WorkFromHome> workFromHomes) throws CustomException {
        List<WorkFromHomeDetails> wfhDetailsList = new ArrayList<>();
        for(WorkFromHome workFromHome : workFromHomes){
            wfhDetailsList.add(getWFHRequestDetailsDto(workFromHome));
        }
        return wfhDetailsList;
    }
}
