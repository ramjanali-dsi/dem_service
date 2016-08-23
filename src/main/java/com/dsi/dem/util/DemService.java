package com.dsi.dem.util;

import com.dsi.checkauthorization.filter.CheckAuthorizationFilter;
import com.dsi.dem.filter.ResponseCORSFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by sabbir on 7/13/16.
 */
public class DemService extends ResourceConfig {

    public DemService(){
        packages("com.dsi.dem");
        register(ResponseCORSFilter.class);
        register(MultiPartFeature.class);
        register(CheckAuthorizationFilter.class);

        SessionUtil.getSession();
    }
}
