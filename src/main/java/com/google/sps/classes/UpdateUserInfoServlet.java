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

@WebServlet("/updateUserInfo")
public class UpdateUserInfoServlet extends HttpServlet {
    UserService userService = UserServiceFactory.getUserService();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        User user = null;
        String email = userService.getCurrentUser().getEmail();
        String name = request.getParameter("username");
        try
        {
            user = User.getUser(email);
        }
        catch(EntityNotFoundException e)
        {
            response.sendRedirect("/index.html");
            return;
        }
        catch(ClassCastException f){
            response.sendRedirect("/index.html");
            return;
        }
        user.setName(name);

        try
        {
            user.updateDatabase();
        }
        catch(EntityNotFoundException e)
        {
            response.sendRedirect("/index.html");
            return;
        }
        response.sendRedirect("/profile.html");
    }
}