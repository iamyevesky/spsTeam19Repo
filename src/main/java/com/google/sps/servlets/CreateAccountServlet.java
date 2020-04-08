package com.google.sps.servlets;

import com.google.sps.classes.*;

import java.io.IOException;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
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
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(!userService.isUserLoggedIn()){
            response.sendRedirect("/startup");
            return;
        }
        out.println("<p>Hello!</p>");
        out.println("<p>Enter details below.</p>");
        out.println("<form action = \"/createAccount\" method = \"POST\">"+
        "<label for = \"username\">Name:</label>"+
        "<textarea type = \"text\" id = \"username\" name = \"username\">Your name</textarea>"+
        "<input type = \"submit\">"+
        "<br/><br/>"+
        "</form>");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String name = request.getParameter("username")+"";
        User newUser = User.createUser(
        userService.getCurrentUser().getEmail(),
        name,
        College.getCollege("defaultUniversity"),
        false
        );
        newUser.saveToDatabase();
        response.sendRedirect("/startup");
    }
}