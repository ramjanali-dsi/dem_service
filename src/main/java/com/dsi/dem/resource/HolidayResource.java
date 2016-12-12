package com.dsi.dem.resource;

import com.dsi.dem.dto.HolidayDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.service.HolidayService;
import com.dsi.dem.service.impl.HolidayServiceImpl;
import com.wordnik.swagger.annotations.*;
import com.wordnik.swagger.jaxrs.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by sabbir on 11/30/16.
 */
@Path("/v1/holiday")
@Api(value = "/Holiday", description = "Operations about Holidays")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class HolidayResource {

    private static final HolidayService holidayService = new HolidayServiceImpl();

    @POST
    @ApiOperation(value = "Create Holiday", notes = "Create Holiday", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Holiday create success"),
            @ApiResponse(code = 500, message = "Holiday create failed, unauthorized.")
    })
    public Response createHoliday(@ApiParam(value = "Holiday", required = true)
                                        HolidayDto holidayDto) throws CustomException {

        return Response.ok().entity(holidayService.saveHoliday(holidayDto)).build();
    }

    @PATCH
    @Path("/{holiday_id}")
    @ApiOperation(value = "Update Holiday", notes = "Update Holiday", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Holiday update success"),
            @ApiResponse(code = 500, message = "Holiday update failed, unauthorized.")
    })
    public Response updateHoliday(@PathParam("holiday_id") String holidayId,
                                  @ApiParam(value = "Holiday", required = true)
                                          HolidayDto holidayDto) throws CustomException {

        return Response.ok().entity(holidayService.updateHoliday(holidayDto, holidayId)).build();
    }

    @DELETE
    @Path("/{holiday_id}")
    @ApiOperation(value = "Delete Holiday", notes = "Delete Holiday", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Holiday delete success"),
            @ApiResponse(code = 500, message = "Holiday delete failed, unauthorized.")
    })
    public Response deleteHoliday(@PathParam("holiday_id") String holidayId) throws CustomException {

        holidayService.deleteHoliday(holidayId);

        return Response.ok().entity(null).build();
    }

    @GET
    @ApiOperation(value = "Search Or Read Holidays", notes = "Search Or Read Holidays", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read or search holiday success"),
            @ApiResponse(code = 500, message = "Read or search holiday failed, unauthorized.")
    })
    public Response searchOrReadHolidays(@QueryParam("holidayName") String holidayName,
                                         @QueryParam("year") String year,
                                         @QueryParam("holidayId") String holidayId,
                                         @QueryParam("from") String from,
                                         @QueryParam("range") String range) throws CustomException {

        return Response.ok().entity(holidayService.searchOrReadAllHolidays
                (holidayName, year, holidayId, from, range)).build();
    }

    @PATCH
    @Path("/copy")
    @ApiOperation(value = "Copy A Specific Holiday", notes = "Copy A Specific Holiday", position = 5)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Copy a specific holiday success"),
            @ApiResponse(code = 500, message = "Copy a specific holiday failed, unauthorized.")
    })
    public Response copySpecificHoliday(@ApiParam(value = "HolidayList", required = true)
                                                    List<HolidayDto> holidayDtoList) throws CustomException {

        return Response.ok().entity(holidayService.copyHoliday(holidayDtoList)).build();
    }

    @PATCH
    @Path("/publish")
    @ApiOperation(value = "Publish All or A Specific Holiday", notes = "Publish All or A Specific Holiday", position = 6)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Publish all or a specific holiday success"),
            @ApiResponse(code = 500, message = "Publish all or a specific holiday failed, unauthorized.")
    })
    public Response publishHoliday(@ApiParam(value = "HolidayList", required = true)
                                               List<HolidayDto> holidayDtoList) throws CustomException {

        return Response.ok().entity(holidayService.publishHoliday(holidayDtoList)).build();
    }
}
