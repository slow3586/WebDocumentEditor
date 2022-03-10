
package ru.demskv.webapplicationproject.assignment;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
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


@Path("api/assignment/")
public class AssignmentController {
    final static Logger logger = LoggerFactory.getLogger(AssignmentController.class);
    
    @EJB
    private AssignmentService assignmentService;
    
    @GET
    @Path("find_all/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(
            @QueryParam("from") int from,
            @QueryParam("to") int to,
            @QueryParam("orderBy") String orderBy
    ) {
        logger.info("HI findAll");
        System.out.println("HI findAll");
        return Response.ok().entity(GsonUtil.tojson((assignmentService.findAll()))).build();
    }
    
    @GET
    @Path("find_all_order/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllLimit(@QueryParam("from") int from,
            @QueryParam("limit") int limit,
            @QueryParam("orderBy") String orderBy
    ) {
        logger.info("HI findAllLimit");
        System.out.println("HI findAllLimit");
        return Response.ok().entity(GsonUtil.tojson((assignmentService.findAllOrder(from, limit, orderBy)))).build();
    }
    
    @POST
    @Path("find_by_id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") @Min(1) Integer id) {
        return Response.ok().entity(GsonUtil.tojson((assignmentService.findById(id)))).build();
    }
    
    @POST
    @Path("create/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid Assignment assignment) {
        return Response.ok().entity(GsonUtil.tojson(assignmentService.create(assignment).orElseThrow())).build();
    }
 
    @POST
    @Path("update/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@Valid Assignment assignment) {
        return Response.ok().entity(GsonUtil.tojson(assignmentService.update(assignment))).build();
    }
    
    @POST
    @Path("delete_by_id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") @Min(1) Integer id) {
        return Response.ok().entity(GsonUtil.tojson(assignmentService.deleteById(id))).build();
    }
}