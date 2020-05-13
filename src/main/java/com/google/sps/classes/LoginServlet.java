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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    UserService userService = UserServiceFactory.getUserService();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        LoadCollege.loadColleges();
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        if(!userService.isUserLoggedIn()){
            Gson gson = new Gson();
            ArrayList<String> loginStrings = new ArrayList<>();
            String loginUrl = userService.createLoginURL("/chat.html");
            String createURL = userService.createLoginURL("/createAccount.html");
            loginStrings.add(loginUrl);
            loginStrings.add(createURL);
            out.println(gson.toJson(loginStrings));
            return;
        }
        String email = userService.getCurrentUser().getEmail();
        User user = null;
        try
        {
            user = User.getUser(email);
        }
        catch(EntityNotFoundException e)
        {

        }
        catch(ClassCastException f){
            user = null;
        }

        if(user == null){
            response.sendRedirect("/createAccount.html");
        }
        else
        {
            response.sendRedirect("/chat.html");
        }
    }
}