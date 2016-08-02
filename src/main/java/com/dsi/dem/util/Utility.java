package com.dsi.dem.util;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

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

    public static final String generateRandomString(){
        return UUID.randomUUID().toString();
    }

    public static final Date today() {
        return new Date();
    }

    public static final Object convertMapToObject(String body, Object object) throws CustomException {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> bodyMap = new HashMap<>();
        try {
            bodyMap = mapper.readValue(body, new TypeReference<Map<String, Object>>(){});
            logger.info("Body map: " + bodyMap);

            Employee employee = mapper.convertValue(bodyMap, Employee.class);

            System.out.println(employee.getBankAcNo());

            /*Method[] methods = object.getClass().getMethods();
            for(Map.Entry<String, Object> property : bodyMap.entrySet()){
                Method setter = object.getClass().getMethod("set" + property.getKey().substring(0, 1).toUpperCase()
                        + property.getKey().substring(1), property.getValue().getClass());
            }*/

        } catch (IOException e) {

            e.printStackTrace();

            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0011,
                    Constants.DEM_SERVICE_0011_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return object;
    }
}
