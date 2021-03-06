package com.dsi.dem.dto.transformer;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.LeaveDao;
import com.dsi.dem.dao.ProjectDao;
import com.dsi.dem.dao.TeamDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.dao.impl.LeaveDaoImpl;
import com.dsi.dem.dao.impl.ProjectDaoImpl;
import com.dsi.dem.dao.impl.TeamDaoImpl;
import com.dsi.dem.dto.LeaveDetailsDto;
import com.dsi.dem.dto.LeaveSummaryDto;
import com.dsi.dem.dto.LeaveRequestDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.impl.CommonService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sabbir on 8/25/16.
 */
public class LeaveDtoTransformer extends CommonService {

    private static final ProjectDao projectDao = new ProjectDaoImpl();
    private static final TeamDao teamDao = new TeamDaoImpl();
    private static final LeaveDao leaveDao = new LeaveDaoImpl();
    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();

    private Session session;

    public void setSession(Session session){
        this.session = session;
    }

    public LeaveRequest getLeaveRequest(LeaveRequestDto leaveDto) throws CustomException {

        LeaveRequest leaveRequest = new LeaveRequest();
        try {
            BeanUtils.copyProperties(leaveRequest, leaveDto);

            LeaveType leaveType = new LeaveType();
            BeanUtils.copyProperties(leaveType, leaveDto);
            leaveRequest.setLeaveType(leaveType);

            if(leaveDto.getLeaveStatusId() != null || leaveDto.getLeaveStatusName() != null){
                LeaveStatus leaveStatus = new LeaveStatus();
                BeanUtils.copyProperties(leaveStatus, leaveDto);
                leaveRequest.setLeaveStatus(leaveStatus);
            }

            LeaveRequestType leaveRequestType = new LeaveRequestType();
            BeanUtils.copyProperties(leaveRequestType, leaveDto);
            leaveRequest.setRequestType(leaveRequestType);

        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return leaveRequest;
    }

    public LeaveRequestDto getLeaveRequestDto(LeaveRequest leaveRequest) throws CustomException {

        //employeeDao.setSession(session);
        LeaveRequestDto leaveRequestDto = new LeaveRequestDto();
        try {
            BeanUtils.copyProperties(leaveRequestDto, leaveRequest);
            BeanUtils.copyProperties(leaveRequestDto, leaveRequest.getLeaveType());
            BeanUtils.copyProperties(leaveRequestDto, leaveRequest.getRequestType());
            BeanUtils.copyProperties(leaveRequestDto, leaveRequest.getEmployee());
            if(leaveRequest.getLeaveStatus() != null) {
                BeanUtils.copyProperties(leaveRequestDto, leaveRequest.getLeaveStatus());
            }

            if(leaveRequest.getApprovalId() != null) {
                Employee approvedBy = employeeDao.getEmployeeByID(leaveRequest.getApprovalId());
                String identity = approvedBy.getFirstName();
                if(approvedBy.getLastName() != null){
                    identity =  identity + " " + approvedBy.getLastName();
                }
                leaveRequestDto.setApprovedBy(identity);
            }

        } catch (Exception e) {
            e.printStackTrace();
            //close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return leaveRequestDto;
    }

    public List<LeaveRequestDto> getAllLeaveRequestDto(List<LeaveRequest> leaveRequests) throws CustomException {

        List<LeaveRequestDto> leaveDtoList = new ArrayList<>();
        for(LeaveRequest leaveRequest : leaveRequests){
            leaveDtoList.add(getLeaveRequestDto(leaveRequest));
        }
        return leaveDtoList;
    }

    public LeaveSummaryDto getEmployeeLeaveDto(EmployeeLeave employeeLeave) throws CustomException {

        LeaveSummaryDto leaveDto = new LeaveSummaryDto();
        try {
            BeanUtils.copyProperties(leaveDto, employeeLeave);
            BeanUtils.copyProperties(leaveDto, employeeLeave.getEmployee());
            leaveDto.setTotalLeave(employeeLeave.getTotalSick() + employeeLeave.getTotalCasual());

        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return leaveDto;
    }

    public List<LeaveSummaryDto> getAllEmployeesLeaveDto(List<EmployeeLeave> employeeLeaves) throws CustomException {

        List<LeaveSummaryDto> leaveDtoList = new ArrayList<>();
        for(EmployeeLeave employeeLeave : employeeLeaves){
            leaveDtoList.add(getEmployeeLeaveDto(employeeLeave));
        }
        return leaveDtoList;
    }

    public LeaveDetailsDto getEmployeesLeaveDetails(EmployeeLeave employeeLeave,
                                                    List<LeaveRequest> leaveRequests) throws CustomException {

        //employeeDao.setSession(session);
        teamDao.setSession(session);
        projectDao.setSession(session);

        LeaveDetailsDto leaveDetailsDto = new LeaveDetailsDto();
        try {
            BeanUtils.copyProperties(leaveDetailsDto, employeeLeave.getEmployee());
            leaveDetailsDto.setEmail(CommonTransformer.getEmail(employeeDao.getEmployeeEmailsByEmployeeID
                    (employeeLeave.getEmployee().getEmployeeId())));
            leaveDetailsDto.setPhone(CommonTransformer.getPhone(employeeDao.getEmployeeContactsByEmployeeID
                    (employeeLeave.getEmployee().getEmployeeId())));
            leaveDetailsDto.setDesignation(CommonTransformer.getDesignation(employeeDao.getEmployeeDesignationsByEmployeeID
                    (employeeLeave.getEmployee().getEmployeeId())));

            List<TeamMember> memberTeams = teamDao.getTeamMembers(null, employeeLeave.getEmployee().getEmployeeId());
            leaveDetailsDto.setTeam(CommonTransformer.getTeamList(memberTeams));

            List<ProjectTeam> memberProjects = projectDao.getProjectTeams(null, employeeLeave.getEmployee().getEmployeeId());
            leaveDetailsDto.setProject(CommonTransformer.getProjectList(memberProjects));

            LeaveSummaryDto summaryDto = new LeaveSummaryDto();
            BeanUtils.copyProperties(summaryDto, employeeLeave);
            BeanUtils.copyProperties(summaryDto, employeeLeave.getEmployee());
            summaryDto.setTotalLeave(employeeLeave.getTotalSick() + employeeLeave.getTotalCasual());
            leaveDetailsDto.setLeaveSummary(summaryDto);

            List<LeaveRequestDto> detailsDtoList = new ArrayList<>();
            for (LeaveRequest leaveRequest : leaveRequests) {
                LeaveRequestDto detailsDto = new LeaveRequestDto();
                BeanUtils.copyProperties(detailsDto, leaveRequest);
                BeanUtils.copyProperties(detailsDto, leaveRequest.getLeaveType());
                BeanUtils.copyProperties(detailsDto, leaveRequest.getRequestType());
                if (leaveRequest.getLeaveStatus() != null) {
                    BeanUtils.copyProperties(detailsDto, leaveRequest.getLeaveStatus());
                }

                if (leaveRequest.getApprovalId() != null) {
                    Employee approvedBy = employeeDao.getEmployeeByID(leaveRequest.getApprovalId());
                    String name = approvedBy.getFirstName();
                    if (approvedBy.getLastName() != null) {
                        name += " " + approvedBy.getLastName();
                    }
                    detailsDto.setApprovedBy(name);
                }
                detailsDtoList.add(detailsDto);
            }
            leaveDetailsDto.setLeaveDetails(detailsDtoList);

        } catch (Exception e) {
            e.printStackTrace();
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return leaveDetailsDto;
    }

    public List<LeaveDetailsDto> getAllEmployeesLeaveDetails(List<LeaveRequest> leaveDetails) throws CustomException {

        leaveDao.setSession(session);
        List<String> employees = leaveDetails
                .stream()
                .map(leaveDetail
                        -> leaveDetail
                        .getEmployee()
                        .getEmployeeId())
                .collect(Collectors.toList());

        Set<String> uniqueEmployee = new HashSet<>(employees);

        List<LeaveDetailsDto> leaveDetailsDtoList = new ArrayList<>();
        for(String employeeId : uniqueEmployee){

            List<LeaveRequest> employeesLeaveDetail = leaveDetails
                    .stream()
                    .filter(leaveDetail
                            -> leaveDetail
                            .getEmployee()
                            .getEmployeeId().equals(employeeId))
                    .collect(Collectors.toList());

            leaveDetailsDtoList.add(getEmployeesLeaveDetails(leaveDao.
                    getEmployeeLeaveSummary(employeeId), employeesLeaveDetail));
        }
        return leaveDetailsDtoList;
    }
}