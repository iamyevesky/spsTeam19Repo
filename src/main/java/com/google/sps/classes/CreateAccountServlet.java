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
@WebServlet("/createAccount")
public class CreateAccountServlet extends HttpServlet {
    UserService userService = UserServiceFactory.getUserService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        if(!userService.isUserLoggedIn()){
            response.sendRedirect("/index.html");
            return;
        }
        else
        {
            String email = userService.getCurrentUser().getEmail();
            User user = null;
            try
            {
                user = User.getUser(email);
            }
            catch(EntityNotFoundException e)
            {

            }
            catch(ClassCastException f)
            {
                user = null;
            }

            if(user != null)
            {
                response.sendRedirect("/chat.html");
                return;
            }
            try
            {
                out.println(College.getAllCollegesJson());
            }
            catch(EntityNotFoundException e)
            {

            }
        }
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String name = request.getParameter("username");
        String key =  request.getParameter("collegeList");
        College college = null;
        try
        {
            college = College.getCollege(key);
        }
        catch(EntityNotFoundException e)
        {
            response.sendRedirect("/createAccount.html");
        }
        User newUser = User.createUser(userService.getCurrentUser().getEmail(), name, college);
        if (newUser != null){
            newUser.saveToDatabase();
        }
        response.sendRedirect("/chat.html");
    }
}