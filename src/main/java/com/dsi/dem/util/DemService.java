package com.dsi.dem.util;

import com.dsi.dem.filter.ResponseCORSFilter;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by sabbir on 7/13/16.
 */
public class DemService extends ResourceConfig {

    public DemService(){
        packages("com.dsi.dem");
        register(ResponseCORSFilter.class);

        SessionUtil.getSession();
    }
}
