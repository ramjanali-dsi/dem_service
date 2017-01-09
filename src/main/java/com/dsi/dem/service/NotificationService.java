package com.dsi.dem.service;

import com.dsi.dem.exception.CustomException;
import org.codehaus.jettison.json.JSONArray;

/**
 * Created by sabbir on 1/9/17.
 */
public interface NotificationService {

    JSONArray getHrManagerEmailList() throws CustomException;
}
