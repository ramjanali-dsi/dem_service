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
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static final int getDaysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24) );
    }

    public static Boolean checkDate(Date startDate, Date endDate, Date checkDate) {
        Interval interval = new Interval(new DateTime(startDate),
                new DateTime(endDate));
        return interval.contains(new DateTime(checkDate));
    }

    public static boolean isGreater02PM(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("14:00")))
                return true;

        } catch (ParseException e) {
            logger.error("Date parse error occurs: " + e.getMessage());
        }
        return false;
    }

    public static final String getTimeCalculation(String dateTime1, String dateTime2){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date1;
        Date date2;
        String time = "";
        try{
            date1 = dateFormat.parse(dateTime1);
            date2 = dateFormat.parse(dateTime2);

            long difference = (date2.getTime() - date1.getTime()) / 1000;

            long s = difference % 60;
            long m = (difference / 60) % 60;
            long h = (difference / (60 * 60)) % 24;
            time += String.format("%02d:%02d:%02d", h,m,s);

        } catch (ParseException e) {
            logger.error("Date parse error occurs: " + e.getMessage());
        }
        return time;
    }

    public static final Date getDateFormatFromDate(Date date) {
        Date formatDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatDate = dateFormat.parse(dateFormat.format(date));

        } catch (ParseException e) {
            logger.error("Date parse error occurs: " + e.getMessage());
        }
        return formatDate;
    }

    public static final Date getDateFromString(String date) {

        Date formatDate = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatDate = dateFormat.parse(date);

        } catch (ParseException e) {
            logger.error("Date parse error occurs: " + e.getMessage());
        }
        return formatDate;
    }

    public static final String getDateFromStringForAttendance(String date){
        Date formatDate = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat finalFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatDate = dateFormat.parse(date);

        } catch (ParseException e) {
            logger.error("Date parse error occurs: " + e.getMessage());
        }
        return finalFormat.format(formatDate);
    }

    public static final Timestamp getTimeStampFromString(String date){
        Timestamp timestamp = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            timestamp = new Timestamp(dateFormat.parse(date).getTime());

        } catch (ParseException e) {
            logger.error("Date parse error occurs: " + e.getMessage());
        }
        return timestamp;
    }

    public static final String getTime(String date){
        Timestamp timestamp = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            timestamp = new Timestamp(dateFormat.parse(date).getTime());

        } catch (ParseException e) {
            logger.error("Date parse error occurs: " + e.getMessage());
        }
        return timeFormat.format(timestamp);
    }

    public static final String getLoginObject(Employee employee, String currentUserId) throws JSONException {
        JSONObject loginObject = new JSONObject();
        loginObject.put("firstName", employee.getFirstName());
        loginObject.put("lastName", employee.getLastName());
        loginObject.put("gender", employee.getInfo().getGender());
        loginObject.put("email", employee.getEmailInfo().get(0).getEmail());
        loginObject.put("phone", employee.getContactInfo().get(0).getPhone());
        loginObject.put("roleId", employee.getRoleId());
        loginObject.put("createBy", currentUserId);
        loginObject.put("modifiedBy", currentUserId);
        loginObject.put("version", 1);

        return loginObject.toString();
    }

    public static final void saveToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws CustomException {
        logger.debug("Uploaded File Location: " + uploadedFileLocation);

        OutputStream out = null;
        int read = 0;
        byte[] bytes = new byte[1024];

        try {
            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (Exception e){
            //ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0008,
                    Constants.DEM_SERVICE_0008_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_008);
            throw new CustomException(errorMessage);

        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
