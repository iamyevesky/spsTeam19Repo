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
@WebServlet("/departmentPost")
public class DepartmentPostServlet extends HttpServlet {
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
            response.sendRedirect("/startup");
            System.out.println("No user found in /departmentPost");
            return;
        }

        Department department = null;
        String title = request.getParameter("title");
        String body = request.getParameter("body");
        try
        {
            department = Department.getDepartment(KeyFactory.stringToKey(request.getParameter("departmentID")));
        }
        catch(EntityNotFoundException e)
        {
            response.sendRedirect("/startup");
            System.out.println("No department found in /departmentPost");
            return;
        }
        BulletinPost.addPostToDatabase(user, title, body, department);
        response.sendRedirect("/departmentPost?departmentID="+department.getKey());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        Department department = null;
        try
        {
            department = Department.getDepartment(KeyFactory.stringToKey(request.getParameter("departmentID")));
        }
        catch(EntityNotFoundException e)
        {
            response.sendRedirect("/bulletin.html");
            System.out.println("No department found in /departmentPost");
            return;
        }
        try
        {
            response.getWriter().println(department.getPostsJson());
        }
        catch(EntityNotFoundException e)
        {
            System.out.println("Failure getting posts");
        }
    }

}