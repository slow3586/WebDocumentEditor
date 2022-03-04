
package ru.demskv.webapplicationproject;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 *
 * @author eldermoraes
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends HttpServlet {
    
    private User user;
    
    @PostConstruct
    public void instantiateUser(){
        user = new User("Elder Moraes", "elder@eldermoraes.com", LocalDate.now());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "UserServlet for Chapter 02";
    }

    protected void doRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Servlet UserServlet at " + request.getContextPath() + "</h2>");
            out.println("<h2>Now: " + new Date() + "</h2>");
            out.println("<h2>User: " + user.getName() + "/" + user.getEmail() + "</h2>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    @Override
    public void init() throws ServletException {
        System.out.println("Servlet " + this.getServletName() + " has started");
    }

    @Override
    public void destroy() {
        System.out.println("Servlet " + this.getServletName() + " has destroyed");
    }
}