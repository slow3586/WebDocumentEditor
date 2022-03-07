
package ru.demskv.webapplicationproject;

import com.google.gson.Gson;
import jakarta.ejb.EJB;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("users/")
public class UserRest {

    @EJB
    private UserDAO userDAO;
    
    @GET
    @Path("")
    @Produces("application/json")
    public Response date(
            @QueryParam("login") String login,
            @QueryParam("password") String password) {
        //ObjectMapper m = new ObjectMapper();
            List<EditorUser> allUsers = userDAO.allUsers();
            Gson g = new Gson();
            String toJson = g.toJson(allUsers);
            return Response.ok().entity(toJson).build();
    }
}