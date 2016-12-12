package com.dsi.dem.dto.transformer;

import com.dsi.dem.dto.HolidayDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Holiday;
import com.dsi.dem.model.HolidayType;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 11/30/16.
 */
public class HolidayDtoTransformer {

    public Holiday getHoliday(HolidayDto holidayDto) throws CustomException {
        Holiday holiday = new Holiday();
        try{
            BeanUtils.copyProperties(holiday, holidayDto);

            HolidayType holidayType = new HolidayType();
            BeanUtils.copyProperties(holidayType, holidayDto);

            holiday.setHolidayType(holidayType);

        } catch (Exception e){
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return holiday;
    }

    public HolidayDto getHolidayDto(Holiday holiday) throws CustomException {
        HolidayDto holidayDto = new HolidayDto();
        try{
            BeanUtils.copyProperties(holidayDto, holiday);
            BeanUtils.copyProperties(holidayDto, holiday.getHolidayType());
            holidayDto.setDescription(holiday.getDescription());

        } catch (Exception e){
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }
        return holidayDto;
    }

    public List<HolidayDto> getHolidaysDto(List<Holiday> holidays) throws CustomException {
        List<HolidayDto> holidayDtoList = new ArrayList<>();
        for(Holiday holiday : holidays){
            holidayDtoList.add(getHolidayDto(holiday));
        }
        return holidayDtoList;
    }
}
