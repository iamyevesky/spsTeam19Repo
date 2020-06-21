package com.google.sps.classes;

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
import com.google.appengine.api.blobstore.BlobKey;
import com.google.gson.Gson;
import com.google.cloud.Timestamp;
import java.util.ArrayList;


/*
 * This class represents a single user of the chatroom platform.
 *
 */
public final class User{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final String email;
    private College college;
    private String username;
    private String key;
    private transient ArrayList<Key> chat;

    //START OF PRODUCTION CODE

    private User(String email, String username, College college){
        this.email = email;
        this.username = username;
        this.college = college;
        this.chat = new ArrayList<>();
    }

    private void setKey(Key key){
        this.key = KeyFactory.keyToString(key);
    }

    private void setChatKeys(ArrayList<Key> keys){
        if (keys == null){
            return;
        }
        for (Key key : keys){
            chat.add(key);
        }
    }

    public String getKey(){
        return this.key;
    }

    public String getName() {
        return this.username;
    }

    public College getCollege(){
        return this.college;
    }

    public void saveToDatabase(){
        if (key != null) return;
        Entity user = new Entity("User");
        user.setProperty("username", this.username);
        user.setProperty("email", this.email);
        user.setProperty("collegeID", KeyFactory.stringToKey(this.college.getKey()));
        user.setProperty("chatKeys", this.chat);
        datastore.put(user);
        this.key = KeyFactory.keyToString(user.getKey());
    }

    public void updateDatabase() throws EntityNotFoundException{
        if (this.key == null) return;
        Entity user = datastore.get(KeyFactory.stringToKey(this.key));
        user.setProperty("username", this.username);
        user.setProperty("chatKeys", this.chat);
        user.setProperty("collegeID", KeyFactory.stringToKey(this.college.getKey()));
        datastore.put(user);
    }

    public String convertToJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void setName(String name){
        this.username = name;
    }

    public void setCollege(College college)
    {
        this.college = college;
    }

    public void addChat(Chatroom chat){
        Key key = KeyFactory.stringToKey(chat.getKey());
        if (!this.chat.contains(key)){
            this.chat.add(key);   
        }
    }

    public void leaveChat(Chatroom chat){
        Key key = KeyFactory.stringToKey(chat.getKey());
        if (this.chat.contains(key)){
            this.chat.remove(key);   
        }
    }

    public ArrayList<Chatroom> getChats() throws EntityNotFoundException{
        ArrayList<Chatroom> output = new ArrayList<>();
        for (Key key : chat){
            output.add(Chatroom.getChatroom(key));
        }
        output.sort(new ChatComparator());
        return output;
    }

    public static User getUser(String email) throws EntityNotFoundException{
        Query query =
        new Query("User")
            .setFilter(new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email));
        PreparedQuery result = datastore.prepare(query);
        Entity entity = result.asSingleEntity();
        
        if(entity == null){
            return null;
        }

        User output = new User((String) entity.getProperty("email"), (String) entity.getProperty("username"), College.getCollege((Key) entity.getProperty("collegeID")));
        output.setChatKeys((ArrayList<Key>) entity.getProperty("chatKeys"));
        output.setKey((Key) entity.getKey());
        return output;
    }

    public static User getUser(Key key) throws EntityNotFoundException{
        Entity entity = datastore.get(key);
        if(entity == null){
            return null;
        }
        User output = new User((String) entity.getProperty("email"), (String) entity.getProperty("username"), College.getCollege((Key) entity.getProperty("collegeID")));
        output.setChatKeys((ArrayList<Key>) entity.getProperty("chatKeys"));
        output.setKey((Key) entity.getKey());
        return output;
    }

    public static User createUser(String email, String username, College college){
        Query emailQuery =
        new Query("User").setFilter(new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email));
        PreparedQuery result = datastore.prepare(emailQuery);
        Entity emailEntity = result.asSingleEntity();
        if(emailEntity != null){
            return null;
        }
        return new User(email, username, college);
    }
    //END OF PRODUCTION CODE
    //START OF DEVELOPMENT CODE
    //END OF DEVELOPMENT CODE
}