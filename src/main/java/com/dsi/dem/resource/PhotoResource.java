package com.dsi.dem.resource;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.service.impl.APIProvider;
import com.dsi.dem.util.Constants;
import com.wordnik.swagger.annotations.Api;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

/**
 * Created by sabbir on 8/17/16.
 */
@Path("/v1/photo")
@Api(value = "/Photo", description = "Operations about Employees Photo")
@Consumes({MediaType.TEXT_PLAIN})
public class PhotoResource {

    private static final Logger logger = Logger.getLogger(PhotoResource.class);

    @GET
    @Path("{photoId}")
    @Produces("image/png")
    public Response getEmployeesPhoto(@PathParam("photoId") String photoId) throws CustomException {
        try{
            String photo = APIProvider.PHOTO_DIRECTORY + photoId;
            logger.info("Photo fetch url: " + photo);
            return getPhoto(photo);

        } catch (Exception e){
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0011,
                    Constants.DEM_SERVICE_0011_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    private Response getPhoto(String image) throws Exception {

        BufferedImage bufferedImage = ImageIO.read(new File(image));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", out);
        byte[] imageData = out.toByteArray();

        InputStream stream = new ByteArrayInputStream(imageData);

        return Response.ok(stream).build();
    }
}
