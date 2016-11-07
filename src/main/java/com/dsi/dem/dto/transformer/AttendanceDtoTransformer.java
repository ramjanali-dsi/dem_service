package com.dsi.dem.dto.transformer;

import com.dsi.dem.dto.AttendanceDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Employee;
import com.dsi.dem.model.EmployeeAttendance;
import com.dsi.dem.model.TemporaryAttendance;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 10/19/16.
 */
public class AttendanceDtoTransformer {

    public AttendanceDto getEmployeesAttendance(EmployeeAttendance attendance) throws CustomException {
        AttendanceDto attendanceDto = new AttendanceDto();
        try{
            BeanUtils.copyProperties(attendanceDto, attendance);
            BeanUtils.copyProperties(attendanceDto, attendance.getEmployee());

        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return attendanceDto;
    }

    public List<AttendanceDto> getEmployeesAttendanceList(List<EmployeeAttendance> attendanceList) throws CustomException {
        List<AttendanceDto> attendanceDtoList = new ArrayList<>();
        for(EmployeeAttendance attendance : attendanceList){
            attendanceDtoList.add(getEmployeesAttendance(attendance));
        }
        return attendanceDtoList;
    }

    public AttendanceDto getTempAttendanceDto(TemporaryAttendance temporaryAttendance) throws CustomException {
        AttendanceDto attendanceDto = new AttendanceDto();
        try{
            BeanUtils.copyProperties(attendanceDto, temporaryAttendance);
            BeanUtils.copyProperties(attendanceDto, temporaryAttendance.getEmployee());

        } catch (Exception e) {
            //ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return attendanceDto;
    }

    public List<AttendanceDto> getTempAttendancesDto(List<TemporaryAttendance> temporaryAttendances) throws CustomException {
        List<AttendanceDto> dtoList = new ArrayList<>();
        for(TemporaryAttendance temporaryAttendance : temporaryAttendances){
            dtoList.add(getTempAttendanceDto(temporaryAttendance));
        }
        return dtoList;
    }
}
