package com.java.sps.classes;

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
import java.util.ArrayList;
import java.util.Date;

public final class Message{
    /*
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final Chatroom chat;
    private final User sender;
    private final Date date;
    private final String message;
    private float sentiment;
    
    private Message(User user, Chatroom chat, String message){
        this.sender = user;
        this.chat = chat;
        this.message = message;
        this.date = new Date();
    }

    public void saveToDatabase(){
        Entity message = new Entity("Message");
        message.setProperty("chatroomID", this.chat.getKey());
        message.setProperty("userID", this.sender.getKey());
        message.setProperty("message", this.message);
        message.setProperty("timestamp", this.date);
        message.setProperty("sentiment", this.sentiment);
        datastore.put(message);
    }

    public static void addPMessageToDatabase(User user, Chatroom chatroom, String message){
        Message newPost = new Message(user, chatroom, message);
        newPost.saveToDataBase();
    }
    */
}