package com.google.sps.classes;

import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.*;
@WebServlet("/startup")
public class StartupServlet extends HttpServlet {
    UserService userService = UserServiceFactory.getUserService();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String loginUrl = userService.createLoginURL("/startup");
        String createAccountUrl = userService.createLoginURL("/createAccount");

        if(!userService.isUserLoggedIn()){
            out.println("<p>You are not logged in. Log in to post comments.</p>");
            out.println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
            out.println("<p>Create an account <a href=\"" + createAccountUrl + "\">here</a>.</p>");
            return;
        }
        String email = userService.getCurrentUser().getEmail();
        User user = null;

        try
        {
            user = User.getUserTest(email);
        }
        catch(EntityNotFoundException e)
        {

        }
        if(user == null){
            response.sendRedirect("/createAccount");
            return;
        }

        String logoutUrl = userService.createLogoutURL("/startup");
        out.println("<p>Welcome User: "+ email+".</p>");
        out.println("<p>Your data has been registered in the database.</p>");
        out.println("<p>"+User.convertToJSON(user)+"</p>");
        out.println("<p>Log out <a href=\""+logoutUrl+"\">here</a>.</p>");
        out.println("");
    }
}