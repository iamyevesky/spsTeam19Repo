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
@WebServlet("/collegePost")
public class CollegePostServlet extends HttpServlet {
    UserService userService = UserServiceFactory.getUserService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!userService.isUserLoggedIn())
        {
            response.sendRedirect("/index.html");
            return;
        }

        User user = null;
        try
        {
            user = user.getUser(userService.getCurrentUser().getEmail());
        }
        catch(EntityNotFoundException e)
        {
            response.sendRedirect("/index.html");
            System.out.println("No user found in /collegePost");
            return;
        }
        String title = request.getParameter("title");
        String body = request.getParameter("body");
        BulletinPost.addPostToDatabase(user, title, body);
        response.sendRedirect("/bulletin.html");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        College college = null;
        try
        {
            college = College.getCollege(KeyFactory.stringToKey(request.getParameter("collegeID")));
        }
        catch(EntityNotFoundException e)
        {
            response.sendRedirect("/bulletin.html");
            System.out.println("No college found in /collegePost");
            return;
        }
        
        try
        {
            response.getWriter().println(college.getPostsJson());
        }
        catch(EntityNotFoundException e)
        {
            System.out.println("Failure getting posts");
        }
    }

}