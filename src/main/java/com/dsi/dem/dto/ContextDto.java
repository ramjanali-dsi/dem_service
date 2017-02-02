package com.dsi.dem.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 2/2/17.
 */
public class ContextDto {

    private List<String> teamId;

    private String employeeId;

    public List<String> getTeamId() {
        return teamId;
    }

    public void setTeamId(List<String> teamId) {
        this.teamId = teamId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
