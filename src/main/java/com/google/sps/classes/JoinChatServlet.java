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
import java.util.ArrayList;

@WebServlet("/updateChat")
public class JoinChatServlet extends HttpServlet {
    UserService userService = UserServiceFactory.getUserService();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Gson gson;
    JsonObject object;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String chatKey = request.getParameter("chatKey");
        gson = new GsonBuilder().setPrettyPrinting().create();
        object = new JsonObject();
        try
        {
            ArrayList<User> users  = Chatroom.getChatroom(KeyFactory.stringToKey(chatKey)).getUsers();
            Type userType = new TypeToken<ArrayList<User>>(){}.getType();
            JsonArray usersJson = gson.toJsonTree(users, userType).getAsJsonArray();
            response.getWriter().println(gson.toJson(usersJson));
        }
        catch(EntityNotFoundException e)
        {

        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        User user = null;
        Chatroom chat = null;
        String email = userService.getCurrentUser().getEmail();
        String chatKey = request.getParameter("chatKey");
        boolean kind = true;
        try
        {
            user = User.getUser(email);
            chat = Chatroom.getChatroom(KeyFactory.stringToKey(chatKey));
            if (kind)
            {
                user.addChat(chat);
                user.updateDatabase();
                chat.addUser(user);
                chat.addAdmin(user);
                chat.updateDatabase();
            }
            else
            {
                user.leaveChat(chat);
                user.saveToDatabase();
                chat.removeUser(user);
                chat.saveToDatabase();
            }
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
        response.sendRedirect("/chat.html");
    }
}