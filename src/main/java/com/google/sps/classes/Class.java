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
 * This class represents a single Class of the chatroom platform.
 *
 */
public final class Class{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final String name;
    private final College college;
    private final Department department;
    private BlobKey displayImageKey;
    private String description;
    private String website;
    private Key key;
    private float sentiment;
    private int numOfSentimentUpdate;
    private ArrayList<Key> studentKeys;
    private ArrayList<Key> professorKeys;

    private Class(String name, College college, Department department, float sentiment, float numOfUpdates){
        this.name = name;
        this.sentiment = sentiment;
        this.numOfSentimentUpdate = numOfUpdates;
        this.college = college;
        this.department = department;
    }

    private void setKey(Key key){
        this.key = key;
    }

    private void setStudentKeys(ArrayList<Key> studentKeys){
        this.studentKeys = studentKeys;
    }

    private void setProfessorKeys(ArrayList<Key> professorKeys){
        this.professorKeys = professorKeys;
    }

    /*
     * Returns the name of the Class object
     * 
     */
    public String getName(){
        return this.name;
    }

    /*
     * Returns the description of the Class object
     * 
     */
    public String getDescription(){
        return this.description;
    }

    /*
     * Returns the website of the Class object
     * 
     */
    public String getWebsite(){
        return this.website;
    }

    /*
     * Returns the key of the Class object
     * 
     */
    public Key getKey(){
        return this.key;
    }

    /*
     * Returns the sentiment of the Class object
     * 
     */
    public float getSentiment(){
        return this.sentiment;
    }

    /*
     * Returns the College object of the Class object
     *
     */
    public College getCollege(){
        return this.college;
    }

    /*
     * Returns the Department object of the Class object
     *
     */
    public Department getDepartment(){
        return this.department;
    }

    /*
     * Returns the BlobKey of the displayImage of a Class object
     */
    public BlobKey getDisplayImageKey(){
        return this.displayImageKey;
    }

    /*
     * Returns an ArrayList of User objects of students of Class object
     */
    public ArrayList<User> getStudents(){
        ArrayList<User> output = new ArrayList<>();
        for(Key studentKey : studentKeys){
            output.add(User.getUser(studentKey));
        }
        return output;
    }

    /*
     * Returns an ArrayList of User objects of professors of Class object
     */
    public ArrayList<Key> getProfessors(){
        ArrayList<User> output = new ArrayList<>();
        for(Key professorKey : professorKeys){
            output.add(User.getUser(professorKey));
        }
        return output;
    }

    /*
     * Updates the BlobKey of the displayImage of a Class object
     */
    public void updateDisplayImageKey(BlobKey newKey){
        this.displayImageKey = newKey;
        if (key == null) return;
        Entity class = datastore.get(this.key);
        class.setProperty("displayImageKey", this.displayImageKey);
        datastore.put(class);
    }

    /*
     * Updates the sentiment of a Class object
     */
    public void updateSentiment(float newSentiment){
        this.sentiment = (this.numOfSentimentUpdate*this.sentiment + newSentiment)/(this.numOfSentimentUpdate + 1);
        this.numOfSentimentUpdate++;
        if (key == null) return;
        Entity class = datastore.get(this.key);
        class.setProperty("sentiment", this.sentiment);
        class.setProperty("sentimentUpdate", this.numOfSentimentUpdate);
        datastore.put(class);
    }

    /*
     * Updates the description of the Class object
     * 
     */
    public void updateDescription(String newDescription){
        this.description = newDescription;
        if (key == null) return;
        Entity class = datastore.get(this.key);
        class.setProperty("description", this.description);
        datastore.put(class);
    }

    /*
     * Updates the website address of the Class object
     * 
     */
    public void updateWebsite(String newWebsite){
        this.website = newWebsite;
        if (key == null) return;
        Entity class = datastore.get(this.key);
        class.setProperty("website", this.website);
        datastore.put(class);
    }

    /*
     * Saves Class object to database. 
     */
    public void saveToDatabase(){
        if(key != null){
            return;
        }

        Entity class = new Entity("Class");
        class.setProperty("name", this.name);
        class.setProperty("email", this.email);
        class.setProperty("description", this.description);
        class.setProperty("website", this.website);
        class.setProperty("displayImageKey", this.displayImageKey);
        class.setProperty("sentiment", this.sentiment);
        class.setProperty("sentimentUpdate", this.numOfSentimentUpdate);
        class.setProperty("studentKeys", this.studentKeys);
        class.setProperty("professorKeys", this.professorKeys);
        this.key = class.getKey();
        datastore.put(class);
    }

    public static Class getClass(Key classKey){
        Entity class = datastore.get(classKey);
        Class output = new Class((String) class.getProperty("name"),
        College.getCollege((Key) class.getProperty("collegeID")),
        Department.getDepartment((Key) class.getProperty("departmentID"))
        0,
        0,
        );
        output.updateWebsite((String) class.getProperty("website"));
        output.updateDisplayImageKey((BlobKey) class.getProperty("displayImageKey"));
        output.updateDescription((String) class.getProperty("description"));
        output.setProfessorKeys((ArrayList<Key>) class.getProperty("professorKeys"));
        output.setStudentKeys((ArrayList<Key>) class.getProperty("studentKeys"));
        output.setKey(class.getKey());
        return output;
    }

    public static Class createNewClass(String name, College college, Department department){
        return new Class(name, college, department, 0, 0);
    }

    
}