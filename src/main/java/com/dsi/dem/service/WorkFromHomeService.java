package com.dsi.dem.service;

import com.dsi.dem.dto.WorkFromHomeDetails;
import com.dsi.dem.dto.WorkFromHomeDto;
import com.dsi.dem.exception.CustomException;

import java.util.List;

/**
 * Created by sabbir on 1/11/17.
 */
public interface WorkFromHomeService {

    WorkFromHomeDto saveWorkFromHomeRequest(WorkFromHomeDto workFromHomeDto, String userId,
                                            String tenantName) throws CustomException;
    WorkFromHomeDto updateWorkFromHomeRequest(WorkFromHomeDto workFromHomeDto, String userId,
                                              String tenantName) throws CustomException;
    WorkFromHomeDto getWorkFromHomeRequestById(String wfhId) throws CustomException;
    List<WorkFromHomeDto> searchOrReadWorkFormHomeRequests(String userId, String date, String reason, String statusId,
                                                           String from, String range) throws CustomException;

    WorkFromHomeDetails approveWFHRequest(WorkFromHomeDto wfhDto, String userId, String tenantName) throws CustomException;
    List<WorkFromHomeDetails> searchOrReadEmployeesWFHRequests(String date, String reason, String statusId, String employeeNo,
                                                               String firstName, String lastName, String nickName, String wfhId,
                                                               String from, String range) throws CustomException;
}
