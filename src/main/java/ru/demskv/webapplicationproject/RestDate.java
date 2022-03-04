
package ru.demskv.webapplicationproject;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
/**
* REST Web Service
*/
@Path("webapi/")
public class RestDate {

    @GET
    @Path("date")
    @Produces("application/json")
    public Response date(
            @QueryParam("dateFormat") @DefaultValue("") String dateFormat) {
        ZonedDateTime zdt = ZonedDateTime.now();
        String outStr = "";
        String errMsg = "";
        try {
            outStr = ("".equals(dateFormat)
                    ? zdt.toString()
                    : zdt.format(DateTimeFormatter.
                            ofPattern(dateFormat)));
            errMsg = "";
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        return Response.ok().entity(
                "{"
                + "\"date\":\"" + outStr + "\","
                + "\"errMsg\":\"" + errMsg + "\""
                + "}"
        ).build();
    }
}