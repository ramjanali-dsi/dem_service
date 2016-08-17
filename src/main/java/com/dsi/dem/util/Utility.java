package com.dsi.dem.util;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by sabbir on 7/13/16.
 */
public class Utility {

    private static final Logger logger = Logger.getLogger(Utility.class);

    public static boolean isNullOrEmpty(String s){

        if(s==null ||s.isEmpty() ){
            return true;
        }
        return false;
    }

    public static boolean isNullOrEmpty(List list){

        if(list==null || list.size() == 0 ){
            return true;
        }
        return false;
    }

    public static final Date today() {
        return new Date();
    }

    public static final String getUserObject(Employee employee, String currentUserID) throws JSONException {
        JSONObject userObj = new JSONObject();
        userObj.put("firstName", employee.getFirstName());
        userObj.put("lastName", employee.getLastName());
        userObj.put("gender", employee.getInfo().getGender());
        userObj.put("email", employee.getEmailInfo().get(0).getEmail());
        userObj.put("phone", employee.getContactInfo().get(0).getPhone());
        userObj.put("createBy", currentUserID);
        userObj.put("modifiedBy", currentUserID);
        userObj.put("roleId", employee.getRoleId());
        userObj.put("version", 1);

        return userObj.toString();
    }


}
