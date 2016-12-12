package com.dsi.dem.service;

import com.dsi.dem.dto.EmployeeEmailDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.EmployeeEmail;

import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public interface EmailService {

    List<EmployeeEmailDto> saveEmployeeEmail(List<EmployeeEmailDto> emailDtoList, String employeeID) throws CustomException;
    void saveEmployeeEmail(EmployeeEmail employeeEmail, String employeeId) throws CustomException;
    EmployeeEmailDto updateEmployeeEmail(EmployeeEmailDto employeeEmailDto, String employeeID,
                                         String emailId) throws CustomException;
    void updateEmployeeEmail(EmployeeEmail employeeEmail, String employeeId) throws CustomException;
    void deleteEmployeeEmail(String emailID) throws CustomException;
    List<EmployeeEmailDto> getEmployeesEmailByEmployeeID(String employeeID) throws CustomException;
    EmployeeEmailDto getEmployeeEmail(String emailID, String employeeID) throws CustomException;
}
