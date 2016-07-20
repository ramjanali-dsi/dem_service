package com.dsi.dem.service;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Team;

/**
 * Created by sabbir on 7/20/16.
 */
public interface TeamService {

    void saveTeam(Team team) throws CustomException;

    void updateTeam(Team team) throws CustomException;

    void deleteTeam(Team team) throws CustomException;


}
