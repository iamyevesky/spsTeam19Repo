package com.google.sps.classes;

import org.jsoup.safety.Whitelist;
import org.jsoup.Jsoup;
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
        College college = null;
        String email = userService.getCurrentUser().getEmail();
        String name = Jsoup.clean(request.getParameter("username"), Whitelist.basic());
        String collegeKey = Jsoup.clean(request.getParameter("collegeKey"), Whitelist.basic());
        try
        {
            user = User.getUser(email);
            college = College.getCollege(collegeKey);
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
        user.setCollege(college);

        try
        {
            user.updateDatabase();
            response.sendRedirect("/profile.html");
        }
        catch(EntityNotFoundException e)
        {
            response.sendRedirect("/index.html");
            return;
        }
    }
}