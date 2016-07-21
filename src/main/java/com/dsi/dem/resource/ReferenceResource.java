package com.dsi.dem.resource;

import com.wordnik.swagger.annotations.Api;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by sabbir on 7/21/16.
 */


@Api(value = "/Reference", description = "Operations about Reference")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ReferenceResource {

    private static final Logger logger = Logger.getLogger(ReferenceResource.class);

}
