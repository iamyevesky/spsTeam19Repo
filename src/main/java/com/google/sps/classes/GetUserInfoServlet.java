package com.google.sps.classes;

import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonNull;
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

@WebServlet("/getUserInfo")
public class GetUserInfoServlet extends HttpServlet {
    UserService userService = UserServiceFactory.getUserService();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Gson gson;
    JsonObject object;
    String logout;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        logout = userService.createLogoutURL("/index.html");
        gson = new GsonBuilder().setPrettyPrinting().create();
        object = new JsonObject(); 

        object.addProperty("redirect", "/index.html");
        object.addProperty("logout", logout);
        if(!userService.isUserLoggedIn()){
            object.addProperty("status", false);
            object.addProperty("register", false);
            object.add("user", JsonNull.INSTANCE);
            out.println(gson.toJson(object));
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
            object.addProperty("status", true);
            object.addProperty("register", false);
            object.add("user", JsonNull.INSTANCE);
            response.getWriter().println(gson.toJson(object));
            return;
        }
        catch(ClassCastException f){
            object.addProperty("status", true);
            object.addProperty("register", false);
            object.add("user", JsonNull.INSTANCE);
            response.getWriter().println(gson.toJson(object));
            return;
        }

        if (user == null)
        {
            object.addProperty("status", true);
            object.addProperty("register", false);
            object.add("user", JsonNull.INSTANCE);
            response.getWriter().println(gson.toJson(object));
            return;
        }
        
        JsonObject userJson = gson.toJsonTree(user, User.class).getAsJsonObject();
        object.addProperty("status", true);
        object.addProperty("register", true);
        object.add("user", userJson);
        response.getWriter().println(gson.toJson(object));
        return;
    }
}