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

public final class Chatroom{
    /*
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final String name;
    private final College college;
    private final Department department;
    private final Class class;
    private final boolean isDM;
    private Key key;
    private ArrayList<Key> adminKeys;
    private float sentiment;
    private int numOfSentimentUpdate;
    private ArrayList<Key> userKeys;

    private Chatroom(String name, boolean isDM, Key adminKey, College college, Department department, Class class, float sentiment, int numOfSentimentUpdate){
        this.name = name;
        this.sentiment = sentiment;
        this.numOfSentimentUpdate = numOfSentimentUpdate;
        this.class = class;
        this.college = college;
        this.department = department;
        this.userKeys = new ArrayList<>();
        this.userKeys.add(adminKey);
        this.adminKeys = new ArrayList<>();
        this.adminKeys.add(adminKey);
    }

    private void setKey(Key key){
        this.key = key;
    }

    private void setUserKeys(ArrayList<Key> userKeys){
        this.userKeys = userKeys;
    }

    private void setAdminKeys(ArrayList<Key> adminKeys){
        this.adminKeys = adminKeys;
    }

    public College getCollege(){
        return this.college;
    }


    public Department getDepartment(){
        return this.department;
    }


    public Class getClass(){
        return this.class;
    }


    public ArrayList<User> getUsers(){
        ArrayList<User> output = new ArrayList<>();
        for(Key userKey : userKeys){
            output.add(User.getUser(userKey));
        }
        return output;
    }

    public ArrayList<User> getAdmins(){
        ArrayList<User> output = new ArrayList<>();
        for(Key adminKey : adminKeys){
            output.add(User.getUser(adminKey));
        }
        return output;
    }


    public float getSentiment(){
        return this.sentiment;
    }


    public void updateSentiment(float newSentiment){
        this.sentiment = (this.numOfSentimentUpdate*this.sentiment + newSentiment)/(this.numOfSentimentUpdate + 1);
        this.numOfSentimentUpdate++;
        if (key == null) return;
        Entity chatroom = datastore.get(this.key);
        chatroom.setProperty("sentiment", this.sentiment);
        chatroom.setProperty("sentimentUpdate", this.numOfSentimentUpdate);
        datastore.put(chatroom);
    }

    public void saveToDatabase(){
        if(key != null){
            return;
        }
        Entity chatroom = new Entity("Chatroom");
        chatroom.setProperty("name", this.name);
        chatroom.setProperty("sentiment", this.sentiment);
        chatroom.setProperty("sentimentUpdate", this.numOfSentimentUpdate);
        chatroom.setProperty("userKeys", this.userKeys);
        chatroom.setProperty("adminKeys", this.adminKeys);
        chatroom.setProperty("isDM", this.isDM);
        if(isDM){
            chatroom.setProperty("collegeID", null);
            chatroom.setProperty("departmentID", null);
            chatroom.setProperty("classID", null);
        }else{
            chatroom.setProperty("collegeID", this.college.getKey());
            if(department == null){
                chatroom.setProperty("departmentID", null);
            }else{
                chatroom.setProperty("departmentID", this.department.getKey());
            }

            if(class == null){
                chatroom.setProperty("classID", null);
            }else{
                chatroom.setProperty("classID", this.class.getKey());
            }
        }
        this.key = chatroom.getKey();
        datastore.put(chatroom);
    }
    
    public static Chatroom getChatroom(Key chatroomKey){
        Entity chatroom = datastore.get(chatroomKey);
        Chatroom output = new Chatroom((String) chatroom.getProperty("name"), (boolean) chatroom.getProperty("isDM"), null, College.getCollege((Key) chatroom.getProperty("collegeID")), Department.getDepartment((Key) chatroom.getProperty("departmentID")), Class.getClass((Key) chatroom.getProperty("classID")), (float) chatroom.getProperty("sentiment"), (int) chatroom.getProperty("sentimentUpdate"));
        output.setUserKeys((ArrayList<Key>) chatroom.getProperty("userKeys"));
        output.setAdminKeys((ArrayList<Key>) chatroom.getProperty("adminKeys"));
        output.setKey(chatroom.getKey());
        return output;
    }

    
    public static Chatroom createNewChatroom(String name, College college, Department department, Class class, User admin){
        Chatroom output = new Chatroom(name, false, admin.getKey(), college, department, class, 0, 0);
        return output;
    }

    public static Chatroom createDM(User first, User second){
        Chatroom output = new Chatroom(null, true, null, first.getCollege(), null, null, 0, 0);
        ArrayList<Key> keys = new ArrayList<>();
        keys.add(first.getKey());
        keys.add(second.getKey());
        output.setUserKeys(keys);
        output.setAdminKeys(keys);
        return output;
    }
    */
}