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

@WebServlet("/createAccount")
public class CreateAccountServlet extends HttpServlet {
    UserService userService = UserServiceFactory.getUserService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
         try
        {
            out.println(College.getAllCollegesJson());
        }
        catch(EntityNotFoundException e)
        {

        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String name = Jsoup.clean(request.getParameter("username"), Whitelist.basic());
        String key =  Jsoup.clean(request.getParameter("college"), Whitelist.basic());
        College college = null;

        if (!userService.isUserLoggedIn())
        {
            response.sendRedirect("/index.html");
            return;
        }

        try
        {
            college = College.getCollege(key);
        }
        catch(EntityNotFoundException e)
        {
            response.sendRedirect("/index.html");
            return;
        }
        User newUser = User.createUser(userService.getCurrentUser().getEmail(), name, college);
        if (newUser != null){
            newUser.saveToDatabase();
        }
        response.sendRedirect("/chat.html");
    }
}