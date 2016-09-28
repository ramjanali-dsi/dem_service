package com.dsi.dem.dto.transformer;

import com.dsi.dem.dto.LeaveDetailsDto;
import com.dsi.dem.dto.LeaveSummaryDto;
import com.dsi.dem.dto.LeaveRequestDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.LeaveService;
import com.dsi.dem.service.impl.LeaveServiceImpl;
import com.dsi.dem.util.Constants;
import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sabbir on 8/25/16.
 */
public class LeaveDtoTransformer {

    private static final LeaveService leaveService = new LeaveServiceImpl();

    public LeaveRequest getLeaveRequest(LeaveRequestDto leaveDto) throws CustomException {

        LeaveRequest leaveRequest = new LeaveRequest();
        try {
            BeanUtils.copyProperties(leaveRequest, leaveDto);

            LeaveType leaveType = new LeaveType();
            BeanUtils.copyProperties(leaveType, leaveDto);
            leaveRequest.setLeaveType(leaveType);

            LeaveStatus leaveStatus = new LeaveStatus();
            BeanUtils.copyProperties(leaveStatus, leaveDto);
            leaveRequest.setLeaveStatus(leaveStatus);

        } catch (Exception e) {
            e.printStackTrace();
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return leaveRequest;
    }

    public LeaveRequestDto getLeaveRequestDto(LeaveRequest leaveRequest) throws CustomException {

        LeaveRequestDto leaveRequestDto = new LeaveRequestDto();
        try {
            BeanUtils.copyProperties(leaveRequestDto, leaveRequest);
            BeanUtils.copyProperties(leaveRequestDto, leaveRequest.getLeaveType());
            BeanUtils.copyProperties(leaveRequestDto, leaveRequest.getLeaveStatus());
            BeanUtils.copyProperties(leaveRequestDto, leaveRequest.getEmployee());

        } catch (Exception e) {
            e.printStackTrace();
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, errorContext);
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
            leaveDto.setTotalCasual(Constants.TOTAL_CASUAL);
            leaveDto.setTotalSick(Constants.TOTAL_SICK);
            leaveDto.setTotalLeave(Constants.TOTAL_CASUAL + Constants.TOTAL_SICK);

        } catch (Exception e) {
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, errorContext);
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

    public LeaveDetailsDto getEmployeesLeaveDetails(EmployeeLeave employeeLeave, List<LeaveRequest> leaveRequests) throws CustomException {

        LeaveDetailsDto leaveDetailsDto = new LeaveDetailsDto();
        try {
            BeanUtils.copyProperties(leaveDetailsDto, employeeLeave.getEmployee());
            //TODO phone, email, designation, team, project

            LeaveSummaryDto summaryDto = new LeaveSummaryDto();
            BeanUtils.copyProperties(summaryDto, employeeLeave);
            BeanUtils.copyProperties(summaryDto, employeeLeave.getEmployee());
            summaryDto.setTotalCasual(Constants.TOTAL_CASUAL);
            summaryDto.setTotalSick(Constants.TOTAL_SICK);
            summaryDto.setTotalLeave(Constants.TOTAL_CASUAL + Constants.TOTAL_SICK);
            leaveDetailsDto.setLeaveSummary(summaryDto);

            List<LeaveRequestDto> detailsDtoList = new ArrayList<>();
            for(LeaveRequest leaveRequest : leaveRequests){
                LeaveRequestDto detailsDto = new LeaveRequestDto();
                BeanUtils.copyProperties(detailsDto, leaveRequest);
                BeanUtils.copyProperties(detailsDto, leaveRequest.getLeaveType());
                BeanUtils.copyProperties(detailsDto, leaveRequest.getLeaveStatus());

                detailsDtoList.add(detailsDto);
            }
            leaveDetailsDto.setLeaveDetails(detailsDtoList);

        } catch (Exception e) {
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return leaveDetailsDto;
    }

    public List<LeaveDetailsDto> getAllEmployeesLeaveDetails(List<LeaveRequest> leaveDetails) throws CustomException {

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

            leaveDetailsDtoList.add(getEmployeesLeaveDetails(leaveService.
                    getEmployeeLeaveSummary(employeeId), employeesLeaveDetail));
        }
        return leaveDetailsDtoList;
    }
}