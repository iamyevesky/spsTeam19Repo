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
 * This class represents a single user of the chatroom platform.
 *
 */
public final class User{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final String email;
    private final String username;
    private final College college;
    private final boolean isProf;
    private Key key;
    private BlobKey displayImageKey; 
    private float sentiment;
    private int numOfSentimentUpdate;
    private ArrayList<Key> classKeys;
    private ArrayList<Key> departmentKeys;

    private User(String email, String username, College college, boolean isProf, float sentiment, int numOfUpdates){
        this.email = email;
        this.username = username;
        this.college = college;
        this.isProf = isProf;
        this.sentiment = sentiment;
        this.numOfSentimentUpdate = numOfUpdates;
    }

    private void setKey(Key key){
        this.key = key;
    }

    private void setClassKeys(ArrayList<Key> classKeys){
        this.classKeys = classKeys;
    }

    private void setDepartmentKeys(ArrayList<Key> departmentKeys){
        this.departmentKeys = departmentKeys;
    }

    /*
     * Returns the email of the User object
     * 
     */
    public String getEmail(){
        return this.email;
    }

    /*
     * Returns the College object of the User object
     *
     */
    public College getCollege(){
        return this.college;
    }

    /*
     * Returns the username of the User object
     * 
     */
    public String getName(){
        return this.username;
    }

    /*
     * Returns the BlobKey of the displayImage of a User object
     */
    public BlobKey getDisplayImageKey(){
        return this.displayImageKey;
    }

    /*
     * Returns the key of the User object in the Database
     * 
     */
    public Key getKey(){
        return this.key;
    }

    /*
     * Returns the sentiment of the User object
     * 
     */
    public float getSentiment(){
        return this.sentiment;
    }

    /*
     * Returns true if the User is a professor
     * 
     */
    public boolean professor(){
        return isProf;
    }

    /*
     * Returns true if the User is a student
     * 
     */
    public boolean student(){
        return !isProf;
    }

    /*
     * Returns an ArrayList of Class objects of User object
     */
    public ArrayList<Class> getClasses(){
        ArrayList<Class> output = new ArrayList<Class>();
        for(Key classKey : classKeys){
            output.add(Class.getClass(classKey));
        }
        return output;
    }

    /*
     * Returns an ArrayList of Department objects of User object
     */
    public ArrayList<Department> getDepartments(){
        ArrayList<Department> output = new ArrayList<Department>();
        for(Key departmentKey : departmentKeys){
            output.add(Department.getDepartment(departmentKey));
        }
        return output;
    }

    /*
     * Adds a new Class Key to User object
     * 
     */
    public void addClassKey(Key classKey){
        if (classKeys == null){
            classKeys = new ArrayList<Key>();
        }
        classKeys.add(classKey);
        if (key == null) return;
        Entity user = datastore.get(this.key);
        user.setProperty("classKeys", this.classKeys);
        datastore.put(user);
    }

    /*
     * Adds a new Department Key to User object
     * 
     */
    public void addDepartmentKey(Key departmentKey){
        if (departmentKeys == null){
            departmentKeys = new ArrayList<Key>();
        }
        deparmentKeys.add(departmentKey);
        if (key == null) return;
        Entity user = datastore.get(this.key);
        user.setProperty("departmentKeys", this.departmentKeys);
        datastore.put(user);
    }

    /*
     * Removes a Class Key to User object
     * 
     */
    public void removeClassKey(Key classKey){
        classKeys.remove(classKey);
        if (key == null) return;
        Entity user = datastore.get(this.key);
        user.setProperty("classKeys", this.classKeys);
        datastore.put(user);
    }

     /*
     * Removes a Deparment Key to User object
     * 
     */
    public void removeDepartment(Key departmentKey){
        departmentKeys.remove(departmentKey));
        if (key == null) return;
        Entity user = datastore.get(this.key);
        user.setProperty("departmentKeys", this.departmentKeys);
        datastore.put(user);
    }

    /*
     * Updates the BlobKey of the displayImage of a User object
     */
    public void updateDisplayImageKey(BlobKey newKey){
        this.displayImageKey = newKey;
        if (key == null) return;
        Entity user = datastore.get(this.key);
        user.setProperty("displayImageKey", this.displayImageKey);
        datastore.put(user);
    }

    /*
     * Updates the sentiment of a User object
     */
    public void updateSentiment(float newSentiment){
        this.sentiment = (this.numOfSentimentUpdate*this.sentiment + newSentiment)/(this.numOfSentimentUpdate + 1);
        this.numOfSentimentUpdate++;
        if (key == null) return;
        Entity user = datastore.get(this.key);
        user.setProperty("sentiment", this.sentiment);
        user.setProperty("sentimentUpdate", this.numOfSentimentUpdate);
        datastore.put(user);
    }

    /*
     * Saves User object to database. 
     */
    public void saveToDatabase(){
        if(key != null){
            return;
        }

        Entity user = new Entity("User");
        user.setProperty("username", this.username);
        user.setProperty("email", this.email);
        user.setProperty("collegeID", this.college.getKey());
        user.setProperty("displayImageKey", this.displayImageKey);
        user.setProperty("isProf", this.isProf);
        user.setProperty("sentiment", this.sentiment);
        user.setProperty("sentimentUpdate", this.numOfSentimentUpdate);
        user.setProperty("classKeys", this.classKeys);
        user.setProperty("departmentKeys", this.departmentKeys);
        this.key = user.getKey();
        datastore.put(user);
    }

    /*
     * Returns a User object which already exists in the database system.
     * Returns null if the User does not exist in the database.
     */
    public static User getUser(String email){
        Query query =
        new Query("User")
            .setFilter(new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email));
        PreparedQuery result = datastore.prepare(query);
        Entity entity = result.asSingleEntity();
        
        if(entity == null){
            return null;
        }

        User output = new User((String) entity.getProperty("email"), 
            (String) entity.getProperty("username"),
            College.getCollege((Key) entity.getProperty("collegeID")), 
            (boolean) entity.getProperty("isProf"),
            (float) entity.getProperty("sentiment"),
            (int) entity.getProperty("sentimentUpdate"));
        output.updateDisplayImageKey((BlobKey) entity.getProperty("displayImageKey"));
        output.setKey((Key) entity.getKey());
        output.setClassKeys((ArrayList<Key>) entity.getProperty("classKeys"));
        output.setDepartmentKeys((ArrayList<Key>) entity.getProperty("departmentKeys"));
        return output;
    }

    /*
     * Returns a User object which already exists in the database system.
     * Returns null if the User does not exist in the database.
     */
    public static User getUser(Key userKey){
        Entity entity = datastore.get(userKey);
        User output = new User((String) entity.getProperty("email"), 
            (String) entity.getProperty("username"),
            College.getCollege((Key) entity.getProperty("collegeID")), 
            (boolean) entity.getProperty("isProf"),
            (float) entity.getProperty("sentiment"),
            (int) entity.getProperty("sentimentUpdate"));
        output.updateDisplayImageKey((BlobKey) entity.getProperty("displayImageKey"));
        output.setClassKeys((ArrayList<Key>) entity.getProperty("classKeys"));
        output.setDepartmentKeys((ArrayList<Key>) entity.getProperty("departmentKeys"));
        output.setKey((Key) entity.getKey());
        return output;
    }

    public static User getUser(String email){
        Query emailQuery =
        new Query("User")
            .setFilter(new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email));
        Entity entity = datastore.prepare(emailQuery).asSingleEntity()
        if(entity == null){
            return null;
        }
        User output = new User((String) entity.getProperty("email"), 
            (String) entity.getProperty("username"),
            College.getCollege((Key) entity.getProperty("collegeID")), 
            (boolean) entity.getProperty("isProf"),
            (float) entity.getProperty("sentiment"),
            (int) entity.getProperty("sentimentUpdate"));
        output.updateDisplayImageKey((BlobKey) entity.getProperty("displayImageKey"));
        output.setClassKeys((ArrayList<Key>) entity.getProperty("classKeys"));
        output.setDepartmentKeys((ArrayList<Key>) entity.getProperty("departmentKeys"));
        output.setKey((Key) entity.getKey());
        return output;
    }

    /*
     * Returns a User object which does not exists in the database system.
     * Returns null if the User does exist in the database.
     */
    public static User createUser(String email, String username, College college, boolean isProfessor){
        Query emailQuery =
        new Query("User")
            .setFilter(new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email));
        
        Query usernameQuery =
        new Query("User")
            .setFilter(new Query.FilterPredicate("username", Query.FilterOperator.EQUAL, username));
        Entity emailEntity = datastore.prepare(emailQuery).asSingleEntity();
        Entity usernameEntity = datastore.prepare(usernameQuery).asSingleEntity();

        if(emailEntity != null || usernameEntity != null){
            return null;
        }

        return new User(email, username, college, isProfessor, 0, 0);
    }
} 