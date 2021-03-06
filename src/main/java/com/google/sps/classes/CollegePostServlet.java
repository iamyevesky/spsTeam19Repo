package com.google.sps.classes;

import org.jsoup.safety.Whitelist;
import org.jsoup.Jsoup;
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
import java.util.ArrayList;

@WebServlet("/getInfoPost")
public class CollegePostServlet extends HttpServlet {
    UserService userService = UserServiceFactory.getUserService();
    Gson gson;
    JsonObject object;
    String logout;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!userService.isUserLoggedIn())
        {
            response.sendRedirect("/");
            return;
        }

        User user = null;
        try
        {
            user = user.getUser(userService.getCurrentUser().getEmail());
        }
        catch(EntityNotFoundException e)
        {
            response.sendRedirect("/bulletin.html");
            return;
        }
        String title = Jsoup.clean(request.getParameter("title"), Whitelist.basic());
        String body = Jsoup.clean(request.getParameter("body"), Whitelist.basic());
        BulletinPost.addPostToDatabase(user, title, body);
        response.sendRedirect("/bulletin.html");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        gson = new GsonBuilder().setPrettyPrinting().create();
        object = new JsonObject();
        logout = userService.createLogoutURL("/index.html");
        object.addProperty("redirect", "/index.html");
        object.addProperty("logout", logout);

        if (!userService.isUserLoggedIn())
        {
            object.addProperty("status", false);
            object.addProperty("register", false);
            object.add("user", JsonNull.INSTANCE);
            object.add("posts", JsonNull.INSTANCE);
            response.getWriter().println(gson.toJson(object));
            return;
        }

        User user = null;
        try
        {
            user = user.getUser(userService.getCurrentUser().getEmail());
        }
        catch(EntityNotFoundException e)
        {
            object.addProperty("status", true);
            object.addProperty("register", false);
            object.add("user", JsonNull.INSTANCE);
            object.add("posts", JsonNull.INSTANCE);
            response.getWriter().println(gson.toJson(object));
            return;
        }

        College college = user.getCollege();
        
        try
        {
            ArrayList<BulletinPost> array  = college.getPosts();
            Type type = new TypeToken<ArrayList<BulletinPost>>(){}.getType();
            JsonArray arrayJson = gson.toJsonTree(array, type).getAsJsonArray();
            JsonObject userJson = gson.toJsonTree(user, User.class).getAsJsonObject();
            object.addProperty("status", true);
            object.addProperty("register", true);
            object.add("user", userJson);
            object.add("posts", arrayJson);
            response.getWriter().println(gson.toJson(object));
            return;
        }
        catch(EntityNotFoundException e)
        {
            JsonObject userJson = gson.toJsonTree(user, User.class).getAsJsonObject();
            object.addProperty("status", true);
            object.addProperty("register", true);
            object.add("user", userJson);
            object.add("posts", JsonNull.INSTANCE);
            response.getWriter().println(gson.toJson(object));
            return;
        }
    }
}