package com.google.sps.classes;

import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
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
        ArrayList<String> loginStrings = new ArrayList<>();
        String loginUrl = userService.createLoginURL("/index.html");
        String createURL = userService.createLoginURL("/index.html");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        JsonElement jsonElement = gson.toJsonTree(loginStrings, type);
        loginStrings.add(loginUrl);
        loginStrings.add(createURL);
        PrintWriter out = response.getWriter();
        if(!userService.isUserLoggedIn()){
            jsonElement.getAsJsonArray().add(true);
            jsonElement.getAsJsonArray().add(true);
            jsonElement.getAsJsonArray().add(loginUrl);
            jsonElement.getAsJsonArray().add(createURL);
            out.println(gson.toJson(jsonElement));
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
            jsonElement.getAsJsonArray().add(false);
            jsonElement.getAsJsonArray().add(true);
            jsonElement.getAsJsonArray().add(loginUrl);
            jsonElement.getAsJsonArray().add(createURL);
            out.println(gson.toJson(jsonElement));
        }
        else
        {
            jsonElement.getAsJsonArray().add(false);
            jsonElement.getAsJsonArray().add(false);
            jsonElement.getAsJsonArray().add(loginUrl);
            jsonElement.getAsJsonArray().add(createURL);
            out.println(gson.toJson(jsonElement));
        }
    }
}