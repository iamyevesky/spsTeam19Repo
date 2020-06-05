package com.google.sps.classes;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.DatastoreNeedIndexException;
import com.google.cloud.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public final class Message{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final String chat;
    private final User sender;
    private final Timestamp time;
    private final String message;
    
    private Message(User user, String chatID, String message){
        this.sender = user;
        this.chat = chatID;
        this.message = message;
        this.time = Timestamp.now();
    }

    private Message(User user, String chatID, String message, Timestamp time){
        this.sender = user;
        this.chat = chatID;
        this.message = message;
        this.time = time;
    }

    public void saveToDatabase(){
        Entity message = new Entity("Message");
        message.setProperty("chatID", KeyFactory.stringToKey(this.chat));
        message.setProperty("userID", KeyFactory.stringToKey(this.sender.getKey()));
        message.setProperty("message", this.message);
        message.setProperty("timestamp", this.time.toDate());
        datastore.put(message);
    }

    public static Message get(Entity entity) throws EntityNotFoundException{
        return new Message(User.getUser((Key) entity.getProperty("userID")), KeyFactory.keyToString((Key) entity.getProperty("chatID")), (String) entity.getProperty("message"), Timestamp.of((Date) entity.getProperty("timestamp")));
    }

    public static void addMessageToDatabase(User user, Chatroom chatroom, String message){
        Message newPost = new Message(user, chatroom.getKey(), message);
        newPost.saveToDatabase();
    }

    @SuppressWarnings("DatastoreNeedIndexException")
    public static ArrayList<Message> getChatMessages(String chatKey) throws EntityNotFoundException{
        Query query = new Query("Message").setFilter(new Query.FilterPredicate("chatID", Query.FilterOperator.EQUAL, KeyFactory.stringToKey(chatKey)));
        query.addSort("timestamp", SortDirection.ASCENDING);
        PreparedQuery result = datastore.prepare(query);
        ArrayList<Message> output = new ArrayList<>();
        for (Entity entity : result.asIterable()){
            output.add(Message.get(entity));    
        }
        return output;
    }
}