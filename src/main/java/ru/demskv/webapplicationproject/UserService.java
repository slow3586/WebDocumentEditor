package ru.demskv.webapplicationproject;


import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import java.io.IOException;

//@Path("userservice/")
public class UserService {
    @Inject
    private User user;
    
    @Inject
    @Profile(ProfileType.ADMIN)
    private UserProfile userProfileAdmin;

    @Inject
    @Profile(ProfileType.OPERATOR)
    private UserProfile userProfileOperator;

    @Inject
    private UserProfile userProfileDefault;

    @Inject
    private Event<User> userEvent;

    //@GET
    //@Path("getUser")
    public Response getUser(@Context HttpServletRequest request,
                            @Context HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("result", user);
        request.getRequestDispatcher("/result.jsp")
                .forward(request, response);
        return Response.ok().build();
    }
    
   // @GET
    //@Path("getProfileAdmin")
    public Response getProfileAdmin(@Context HttpServletRequest request,
                                    @Context HttpServletResponse response)
            throws ServletException, IOException{
        request.setAttribute("result",
                fireUserEvents(userProfileAdmin.type()));
        request.getRequestDispatcher("/result.jsp")
                .forward(request, response);
        return Response.ok().build();
    }

    //@GET
    //@Path("getProfileOperator")
    public Response getProfileOperator(@Context HttpServletRequest request,
                                       @Context HttpServletResponse response)
            throws ServletException, IOException{
        request.setAttribute("result",
                fireUserEvents(userProfileOperator.type()));
        request.getRequestDispatcher("/result.jsp")
                .forward(request, response);
        return Response.ok().build();
    }

    //@GET
    //@Path("getProfileDefault")
    public Response getProfileDefault(@Context HttpServletRequest request,
                                      @Context HttpServletResponse response)
            throws ServletException, IOException{
        request.setAttribute("result",
                fireUserEvents(userProfileDefault.type()));
        request.getRequestDispatcher("/result.jsp")
                .forward(request, response);
        return Response.ok().build();
    }
    private ProfileType fireUserEvents(ProfileType type){
        userEvent.fire(user);
        userEvent.fireAsync(user);
        return type;
    }
    public void sendUserNotification(@Observes User user){
        System.out.println("sendUserNotification: " + user);
    }
    public void sendUserNotificationAsync(@ObservesAsync User user){
        System.out.println("sendUserNotificationAsync: " + user);
    }
}
