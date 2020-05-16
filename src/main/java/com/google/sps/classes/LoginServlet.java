package com.google.sps.classes;

import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
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

@WebServlet("/getInfoIndex")
public class LoginServlet extends HttpServlet {
    UserService userService = UserServiceFactory.getUserService();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    String login;
    String logout;
    Gson gson;
    JsonObject object;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        LoadCollege.loadColleges();
        response.setContentType("application/json; charset=utf-8");

        login = userService.createLoginURL("/index.html");
        logout = userService.createLogoutURL("/index.html");
        gson = new GsonBuilder().setPrettyPrinting().create();
        object = new JsonObject(); 

        PrintWriter out = response.getWriter();
        if(!userService.isUserLoggedIn()){
            object.add("login", new JsonPrimitive(login));
            object.add("logout", new JsonPrimitive(""));
            object.add("status", new JsonPrimitive(false));
            object.add("register", new JsonPrimitive(false));
            object.add("user", JsonNull.INSTANCE);
            object.add("continue", new JsonPrimitive(""));
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
            object.add("login", new JsonPrimitive(""));
            object.add("logout", new JsonPrimitive(logout));
            object.add("status", new JsonPrimitive(true));
            object.add("register", new JsonPrimitive(false));
            object.add("user", JsonNull.INSTANCE);
            object.add("continue", new JsonPrimitive(""));
            out.println(gson.toJson(object));
            return;
        }
        catch(ClassCastException f){
            object.add("login", new JsonPrimitive(""));
            object.add("logout", new JsonPrimitive(logout));
            object.add("status", new JsonPrimitive(true));
            object.add("register", new JsonPrimitive(false));
            object.add("user", JsonNull.INSTANCE);
            object.add("continue", new JsonPrimitive(""));
            out.println(gson.toJson(object));
            return;
        }

        if (user != null)
        {
            object.add("login", new JsonPrimitive(""));
            object.add("logout", new JsonPrimitive(logout));
            object.add("status", new JsonPrimitive(true));
            object.add("register", new JsonPrimitive(false));
            object.add("user", JsonNull.INSTANCE);
            object.add("continue", new JsonPrimitive(""));
            out.println(gson.toJson(object));
            return;
        }
        
        JsonObject userJson = gson.toJsonTree(user, User.class).getAsJsonObject();
        object.add("login", new JsonPrimitive(""));
        object.add("logout", new JsonPrimitive(logout));
        object.add("status", new JsonPrimitive(true));
        object.add("register", new JsonPrimitive(true));
        object.add("user", userJson);
        object.add("continue", new JsonPrimitive("/profile.html"));
        out.println(gson.toJson(object));
    }
}