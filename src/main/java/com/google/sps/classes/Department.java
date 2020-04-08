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

/*
 * This class represents a single department of the chatroom platform.
 *
 */
public final class Department{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final String name;
    private final College college;
    private BlobKey displayImageKey;
    private String website;
    private String description;
    private Key key;
    private float sentiment;
    private int numOfSentimentUpdate;
    private ArrayList<Key> professorKeys;
    private ArrayList<Key> studentKeys;

    private Department(String name, College college, int sentiment, int numOfUpdates){
        this.name = name;
        this.college = college;
        this.sentiment = sentiment;
        this.numOfSentimentUpdate = numOfUpdates;
    }

    private void setKey(Key key){
        this.key = key;
    }

    private void setProfessorKeys(ArrayList<Key> professorKeys){
        this.professorKeys = professorKeys;
    }

    private void setStudentKeys(ArrayList<Key> studentKeys){
        this.studentKeys = studentKeys;
    }

    private void setClassKeys(ArrayList<Key> classKeys){
        this.classKeys = classKeys;
    }

    /*
     * Returns the name of the Department object
     * 
     */
    public String getName(){
        return this.name;
    }

    /*
     * Returns the description of the Department object
     * 
     */
    public String getDescription(){
        return this.description;
    }

    /*
     * Returns the website of the Department object
     * 
     */
    public String getWebsite(){
        return this.website;
    }

    /*
     * Returns the key of the Department object
     * 
     */
    public Key getKey(){
        return this.key;
    }

    /*
     * Returns the sentiment of the Department object
     * 
     */
    public float getSentiment(){
        return this.sentiment;
    }

    /*
     * Returns the BlobKey of the displayImage of a Department object
     */
    public BlobKey getDisplayImageKey(){
        return this.displayImageKey;
    }

    /*
     * Updates the BlobKey of the displayImage of a Department object
     */
    public void updateDisplayImageKey(BlobKey newKey){
        this.displayImageKey = newKey;
        if (key == null) return;
        Entity department = datastore.get(this.key);
        department.setProperty("displayImageKey", this.displayImageKey);
        datastore.put(department);
    }

    /*
     * Updates the sentiment of a Department object
     */
    public void updateSentiment(float newSentiment){
        this.sentiment = (this.numOfSentimentUpdate*this.sentiment + newSentiment)/(this.numOfSentimentUpdate + 1);
        this.numOfSentimentUpdate++;
        if (key == null) return;
        Entity department = datastore.get(this.key);
        department.setProperty("sentiment", this.sentiment);
        department.setProperty("sentimentUpdate", this.numOfSentimentUpdate);
        datastore.put(department);
    }

    /*
     * Updates the description of the Department object
     * 
     */
    public void updateDescription(String newDescription){
        this.description = newDescription;
        if (key == null) return;
        Entity department = datastore.get(this.key);
        department.setProperty("description", this.description);
        datastore.put(department);
    }

    /*
     * Updates the website address of the Department object
     * 
     */
    public void updateWebsite(String newWebsite){
        this.website = newWebsite;
        if (key == null) return;
        Entity department = datastore.get(this.key);
        department.setProperty("website", this.website);
        datastore.put(department);
    }

    public static Department getDepartment(Key departmentKey){
        Entity department = datastore.get(departmentKey);
        Department output = new Department((String) department.getProperty("name"),
        College.getCollege((Key) department.getProperty("collegeID")),
        0,
        0
        );
        output.updateWebsite((String) department.getProperty("website"));
        output.updateDisplayImageKey((BlobKey) department.getProperty("displayImageKey"));
        output.updateDescription((String) department.getProperty("description"));
        output.setStudentKeys((ArrayList<Key>) department.getProperty("studentKeys"));
        output.setProfessorKeys((ArrayList<Key>) department.getProperty("professorKeys"));
        output.setKey(department.getKey());
        return output;
    }
}