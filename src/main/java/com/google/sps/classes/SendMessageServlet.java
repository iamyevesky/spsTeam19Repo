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
import com.google.cloud.Timestamp;

@WebServlet("/sendMessage")
public class SendMessageServlet extends HttpServlet {
    UserService userService = UserServiceFactory.getUserService();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Gson gson;
    JsonObject object;
    String logout;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        User user = null;
        String email = userService.getCurrentUser().getEmail();
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
            object.add("chats", JsonNull.INSTANCE);
            response.getWriter().println(gson.toJson(object));
            return;
        }

        try
        {
            user = User.getUser(email);
        }
        catch(EntityNotFoundException e)
        {
            object.addProperty("status", false);
            object.addProperty("register", false);
            object.add("user", JsonNull.INSTANCE);
            object.add("chats", JsonNull.INSTANCE);
            response.getWriter().println(gson.toJson(object));
            return;
        }
        catch(ClassCastException f){
            object.addProperty("status", false);
            object.addProperty("register", false);
            object.add("user", JsonNull.INSTANCE);
            object.add("chats", JsonNull.INSTANCE);
            response.getWriter().println(gson.toJson(object));
            return;
        }
        
        try
        {
            ArrayList<Chatroom> chats  = user.getChats();
            Type chatType = new TypeToken<ArrayList<Chatroom>>(){}.getType();
            JsonArray chatsJson = gson.toJsonTree(chats, chatType).getAsJsonArray();
            JsonObject userJson = gson.toJsonTree(user, User.class).getAsJsonObject();
            for (int i = 0; i < chats.size(); i++){
                JsonObject chat = chatsJson.get(i).getAsJsonObject();
                Type messageType = new TypeToken<ArrayList<Message>>(){}.getType();
                JsonArray messages = gson.toJsonTree(chats.get(i).getMessages(), messageType).getAsJsonArray();
                chat.add("messages", messages);
                chatsJson.set(i, chat);
            }
            object.addProperty("status", true);
            object.addProperty("register", true);
            object.add("user", userJson);
            object.add("chats", chatsJson);
            response.getWriter().println(gson.toJson(object));
            return;
        }
        catch(EntityNotFoundException e)
        {
            JsonObject userJson = gson.toJsonTree(user, User.class).getAsJsonObject();
            object.addProperty("status", true);
            object.addProperty("register", true);
            object.add("user", userJson);
            object.add("chats", JsonNull.INSTANCE);
            response.getWriter().println(gson.toJson(object));
            return;
        }
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        User user = null;
        String email = userService.getCurrentUser().getEmail();
        String key = Jsoup.clean(request.getParameter("chatKey"), Whitelist.basic());
        String message = Jsoup.clean(request.getParameter("message"), Whitelist.basic());

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
            Chatroom chat = Chatroom.getChatroom(KeyFactory.stringToKey(key));
            chat.updateTime(Timestamp.now());
            Message.addMessageToDatabase(user, chat, message);
            chat.updateTime(Timestamp.now());
            chat.updateDatabase();
        }
        catch(EntityNotFoundException e)
        {
            response.sendRedirect("/chat.html");
            return;
        }
        response.sendRedirect("/chatDemoPage.html");
    }
}