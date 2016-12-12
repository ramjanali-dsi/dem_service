package com.dsi.dem.service;

import com.dsi.dem.dto.EmployeeDesignationDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.EmployeeContact;
import com.dsi.dem.model.EmployeeDesignation;

import java.util.List;

/**
 * Created by sabbir on 8/4/16.
 */
public interface DesignationService {

    List<EmployeeDesignationDto> saveEmployeeDesignation(List<EmployeeDesignationDto> designationDtoList,
                                                         String employeeID) throws CustomException;
    void saveEmployeeDesignation(EmployeeDesignation designation, String employeeID) throws CustomException;
    EmployeeDesignationDto updateEmployeeDesignation(EmployeeDesignationDto designationDto, String employeeID,
                                                     String designationId) throws CustomException;
    void updateEmployeeDesignation(EmployeeDesignation designation, String employeeID) throws CustomException;
    void deleteEmployeeDesignation(String designationID) throws CustomException;
    List<EmployeeDesignationDto> getEmployeesDesignationByEmployeeID(String employeeID) throws CustomException;
    EmployeeDesignationDto getEmployeeDesignation(String designationID, String employeeID) throws CustomException;
}
