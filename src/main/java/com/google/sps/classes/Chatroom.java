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
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.ArrayList;
import java.util.Arrays;

public final class Chatroom{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final String name;
    private final College college;
    private final boolean isDM;
    private String key;
    private ArrayList<Key> adminKeys;
    private ArrayList<Key> userKeys;

    private Chatroom(String name, boolean isDM, Key adminKey, College college){
        this.name = name;
        this.isDM = isDM;
        this.college = college;
        this.userKeys = new ArrayList<>();
        this.userKeys.add(adminKey);
        this.adminKeys = new ArrayList<>();
        this.adminKeys.add(adminKey);
    }

    private Chatroom(String name, boolean isDM, College college){
        this.name = name;
        this.isDM = isDM;
        this.college = college;
        this.userKeys = new ArrayList<>();
        this.adminKeys = new ArrayList<>();
    }

    private void setKey(Key key){
        this.key = KeyFactory.keyToString(key);
    }

    private void setUserKeys(ArrayList<Key> userKeys){
        if (userKeys != null){
            this.userKeys = userKeys;
        }
    }

    private void setAdminKeys(ArrayList<Key> adminKeys){
        if (adminKeys != null){
            this.adminKeys = adminKeys;
        }
    }

    public College getCollege(){
        return this.college;
    }

    public String getKey(){
        return this.key;
    }

    public ArrayList<User> getUsers() throws EntityNotFoundException{
        ArrayList<User> output = new ArrayList<>();
        for(Key userKey : userKeys){
            output.add(User.getUser(userKey));
        }
        return output;
    }

    public ArrayList<User> getAdmins() throws EntityNotFoundException{
        ArrayList<User> output = new ArrayList<>();
        for(Key adminKey : adminKeys){
            output.add(User.getUser(adminKey));
        }
        return output;
    }

    public void addAdmin(User user){
        Key key = KeyFactory.stringToKey(user.getKey());
        if (!adminKeys.contains(key)){
            adminKeys.add(key);   
        }
    }

    public void addUser(User user){
         Key key = KeyFactory.stringToKey(user.getKey());
        if (!userKeys.contains(key)){
            userKeys.add(key);   
        }
    }

    public ArrayList<Message> getMessages() throws EntityNotFoundException{
        return Message.getChatMessages(this.key);
    }

    public void saveToDatabase(){
        if(key != null){
            return;
        }
        Entity chatroom = new Entity("Chatroom");
        chatroom.setProperty("name", this.name);
        chatroom.setProperty("userKeys", this.userKeys);
        chatroom.setProperty("adminKeys", this.adminKeys);
        chatroom.setProperty("isDM", this.isDM);
        chatroom.setProperty("collegeID", KeyFactory.stringToKey(this.college.getKey()));
        datastore.put(chatroom);
        this.key = KeyFactory.keyToString(chatroom.getKey());
    }

    public void updateDatabase() throws EntityNotFoundException{
        if(key == null){
            return;
        }
        Entity chatroom = datastore.get(KeyFactory.stringToKey(key));
        chatroom.setProperty("name", this.name);
        chatroom.setProperty("userKeys", this.userKeys);
        chatroom.setProperty("adminKeys", this.adminKeys);
        datastore.put(chatroom);
    }
    
    public static Chatroom getChatroom(Key chatroomKey) throws EntityNotFoundException{
        Entity chatroom = datastore.get(chatroomKey);
        Chatroom output = new Chatroom((String) chatroom.getProperty("name"), (boolean) chatroom.getProperty("isDM"), College.getCollege((Key) chatroom.getProperty("collegeID")));
        output.setUserKeys((ArrayList<Key>) chatroom.getProperty("userKeys"));
        output.setAdminKeys((ArrayList<Key>) chatroom.getProperty("adminKeys"));
        output.setKey(chatroom.getKey());
        return output;
    }

    public static Chatroom get(Entity chatroom) throws EntityNotFoundException{
        Chatroom output = new Chatroom((String) chatroom.getProperty("name"), (boolean) chatroom.getProperty("isDM"), College.getCollege((Key) chatroom.getProperty("collegeID")));
        output.setUserKeys((ArrayList<Key>) chatroom.getProperty("userKeys"));
        output.setAdminKeys((ArrayList<Key>) chatroom.getProperty("adminKeys"));
        output.setKey(chatroom.getKey());
        return output;
    }
    
    public static ArrayList<Chatroom> getChats(College college) throws EntityNotFoundException{
        Query query = 
        new Query("Chatroom")
        .setFilter(new Query.CompositeFilter(Query.CompositeFilterOperator.AND, Arrays.asList(
        new Query.FilterPredicate("isDM", Query.FilterOperator.EQUAL, false),
        new Query.FilterPredicate("collegeID", Query.FilterOperator.EQUAL, KeyFactory.stringToKey(college.getKey())))))
        .addSort("timestamp", SortDirection.ASCENDING);
        
        PreparedQuery result = datastore.prepare(query);
        ArrayList<Chatroom> output = new ArrayList<>();
        for (Entity entity : result.asIterable()){
            output.add(Chatroom.get(entity));    
        }
        return output;
    }

    public static Chatroom createChat(String name, User first){
        Chatroom output = new Chatroom(name, false, KeyFactory.stringToKey(first.getKey()), first.getCollege());
        return output;
    }

    public static Chatroom createDM(User first, User second){
        Chatroom output = new Chatroom("", true, first.getCollege());
        ArrayList<Key> keys = new ArrayList<>();
        keys.add(KeyFactory.stringToKey(first.getKey()));
        keys.add(KeyFactory.stringToKey(second.getKey()));
        output.setUserKeys(keys);
        output.setAdminKeys(keys);
        return output;
    }
}