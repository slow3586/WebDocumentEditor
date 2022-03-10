
package ru.demskv.webapplicationproject.assignment;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.demskv.webapplicationproject.GsonUtil;


@Path("api/assignments/")
public class AssignmentController {
    final static Logger logger = LoggerFactory.getLogger(AssignmentController.class);
    
    @EJB
    private AssignmentService assignmentService;
    
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(
            @Min(0) @QueryParam("from") int from,
            @Min(1) @Max(50) @QueryParam("limit") int limit,
            @QueryParam("orderBy") String orderBy
    ) {
        return Response.ok().header("Content-Range", "items "+from+"-"+(from+limit)+"/"+assignmentService.countAll()).entity(GsonUtil.tojson((assignmentService.findAll(from, limit)))).build();
    }
    
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(@Valid Assignment assignment) {
        return Response.ok().entity(GsonUtil.tojson((assignmentService.create(assignment)))).build();
    }
    
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(@Valid Assignment assignment) {
        return Response.ok().entity(GsonUtil.tojson(assignmentService.update(assignment))).build();
    }
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") @Min(1) Integer id) {
        return Response.ok().entity(GsonUtil.tojson(assignmentService.deleteById(id))).build();
    }
}