
package ru.demskv.webapplicationproject.assignment;

import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Optional;
import ru.demskv.webapplicationproject.GsonUtil;


@Path("api/assignment/")
public class AssignmentController {

    @EJB
    private AssignmentService assignmentService;
    
    @GET
    @Path("find_all/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return Response.ok().entity(GsonUtil.getInstance().toJson(assignmentService.findAll())).build();
    }
    
    @POST
    @Path("find_by_id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") @Min(1) Integer id) {
        return Response.ok().entity(GsonUtil.getInstance().toJson(assignmentService.findById(id))).build();
    }
    
    @POST
    @Path("create/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid Assignment assignment) {
        return Response.ok().entity(GsonUtil.getInstance().toJson(assignment.getAuthor().getFirstname())).build();
       // return Response.ok().entity(GsonUtil.getInstance().toJson(assignmentService.create(assignment))).build();
    }
    
    @POST
    @Path("update/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@Valid Assignment assignment) {
        return Response.ok().entity(GsonUtil.getInstance().toJson(assignmentService.update(assignment))).build();
    }
    
    @POST
    @Path("delete_by_id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") @Min(1) Integer id) {
        return Response.ok().entity(GsonUtil.getInstance().toJson(assignmentService.deleteById(id))).build();
    }
}