package com.google.sps.classes;

import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonNull;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import com.google.gson.GsonBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
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
import com.google.appengine.api.blobstore.BlobKey;

@WebServlet("/sendMessage")
public class SendMessageServlet extends HttpServlet {
    /*
    UserService userService = UserServiceFactory.getUserService();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        User user = null;
        String email = userService.getCurrentUser().getEmail();
        gson = new GsonBuilder().setPrettyPrinting().create();
        object = new JsonObject(); 

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
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        User user = null;
        String email = userService.getCurrentUser().getEmail();
        String key = request.getParameter("chatKey");
        String message = request.getParameter("message");

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

        try
        {
            user.updateDatabase();
        }
        catch(EntityNotFoundException e)
        {
            response.sendRedirect("/index.html");
            return;
        }
    }
    */
}