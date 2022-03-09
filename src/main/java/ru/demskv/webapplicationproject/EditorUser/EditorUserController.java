
package ru.demskv.webapplicationproject.EditorUser;

import ru.demskv.webapplicationproject.EditorUser.EditorUserDAO;
import ru.demskv.webapplicationproject.EditorUser.EditorUser;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("users/")
public class EditorUserController {

    @EJB
    private EditorUserDAO userDAO;
    
    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response date(
            EditorUser jsonuser) {
        //ObjectMapper m = new ObjectMapper();
            if(jsonuser!=null){
                Optional<EditorUser> u = userDAO.getUser(jsonuser.getLogin());
                if(u.isPresent()){
                    return Response.ok().entity("Yay").build();
                }
                
            }
                    return Response.ok().entity(jsonuser.getLogin()+" "+jsonuser.getPassword()+" Nay").build();
            
    }
}