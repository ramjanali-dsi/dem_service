package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.HolidayDao;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Holiday;
import com.dsi.dem.model.HolidayType;
import com.dsi.dem.service.impl.CommonService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sabbir on 11/30/16.
 */
public class HolidayDaoImpl extends CommonService implements HolidayDao {

    private static final Logger logger = Logger.getLogger(HolidayDaoImpl.class);

    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void saveHoliday(Holiday holiday) throws CustomException {
        try{
            session.save(holiday);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_HOLIDAY_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateHoliday(Holiday holiday) throws CustomException {
        try{
            session.update(holiday);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_HOLIDAY_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteHoliday(String holidayId) throws CustomException {
        try{
            Query query = session.createQuery("DELETE FROM Holiday h WHERE h.holidayId =:holidayId");
            query.setParameter("holidayId", holidayId);
            query.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_HOLIDAY_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public boolean checkHolidayFromRangeAndYear(Date startDate, Date endDate, int year) {
        Query query = session.createQuery("FROM Holiday h WHERE h.year =:year AND ((h.startDate between :startDate AND :endDate) OR " +
                "(h.endDate between :startDate AND :endDate) OR (:startDate >= h.startDate AND :endDate <= h.endDate))");

        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("year", year);

        if(query.list().size() > 0){
            return true;
        }
        return false;
    }

    @Override
    public Holiday getHolidayById(String holidayId) {
        Query query = session.createQuery("FROM Holiday h WHERE h.holidayId =:holidayId");
        query.setParameter("holidayId", holidayId);

        Holiday holiday = (Holiday) query.uniqueResult();
        if(holiday != null){
            return holiday;
        }
        return null;
    }

    @Override
    public Holiday getHolidayByNameAndYear(String holidayName, int year) {
        Query query = session.createQuery("FROM Holiday h WHERE h.holidayName =:holidayName AND h.year =:year");
        query.setParameter("holidayName", holidayName);
        query.setParameter("year", year);

        Holiday holiday = (Holiday) query.uniqueResult();
        if(holiday != null){
            return holiday;
        }
        return null;
    }

    @Override
    public List<Holiday> getHolidayByYear(int year) {
        Query query = session.createQuery("FROM Holiday h WHERE h.year =:year");
        query.setParameter("year", year);

        List<Holiday> holidayList = query.list();
        if(holidayList != null){
            return holidayList;
        }
        return null;
    }

    @Override
    public List<Holiday> searchOrReadHolidays(String holidayName, String year, String holidayId, String from, String range) {
        StringBuilder queryBuilder = new StringBuilder();
        boolean hasClause = false;
        Map<String, String> paramValue = new HashMap<>();

        queryBuilder.append("FROM Holiday h");

        if(!Utility.isNullOrEmpty(holidayName)){
            queryBuilder.append(" WHERE h.holidayName =:holidayName");
            paramValue.put("holidayName", holidayName);
            hasClause = true;
        }

        if(!Utility.isNullOrEmpty(year)){
            if(hasClause){
                queryBuilder.append(" AND h.year =:year");

            } else {
                queryBuilder.append(" WHERE h.year =:year");
            }
            paramValue.put("year", year);
        }

        if(!Utility.isNullOrEmpty(holidayId)){
            queryBuilder.append(" WHERE h.holidayId =:holidayId");
            paramValue.put("holidayId", holidayId);
        }

        queryBuilder.append(" ORDER BY h.createdDate ASC");

        logger.info("Query builder: " + queryBuilder.toString());
        Query query = session.createQuery(queryBuilder.toString());

        for(Map.Entry<String, String> entry : paramValue.entrySet()){
            if(entry.getKey().equals("year")) {
                query.setParameter(entry.getKey(), Integer.parseInt(entry.getValue()));

            } else {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        if(!Utility.isNullOrEmpty(from) && !Utility.isNullOrEmpty(range)) {
            query.setFirstResult(Integer.parseInt(from)).setMaxResults(Integer.parseInt(range));
        }

        List<Holiday> holidayList = query.list();
        if(holidayList != null){
            return holidayList;
        }
        return null;
    }

    @Override
    public HolidayType getHolidayTypeById(String typeId) {
        Query query = session.createQuery("FROM HolidayType ht WHERE ht.holidayTypeId =:typeId");
        query.setParameter("typeId", typeId);

        HolidayType holidayType = (HolidayType) query.uniqueResult();
        if(holidayType != null){
            return holidayType;
        }
        return null;
    }
}
