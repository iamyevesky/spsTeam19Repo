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

/*
 * This class represents a single user of the chatroom platform.
 *
 */
public final class User{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private String email;
    private String username;
    private BlobKey displayImageKey;
    private final College college;
    private final Key key;
    private boolean updated; 
    private float sentiment;

    private User(String email, String username, College college, boolean isProf){
        this.email = email;
        this.username = username;
        this.college = college;
        this.isProf = isProf;
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
        return this.key;
    }

    /*
     * Updates the email of a User object
     */
    public void updateEmail(String newEmail){
        this.email = newEmail;
        if (key == null) return;
        Entity user = datastore.get(this.key);
        user.setProperty("email", this.email);
        datastore.put(user);
    }

    /*
     * Updates the username of a User object
     */
    public void updateName(String newUsername){
        this.username = newUsername;
        if (key == null) return;
        Entity user = datastore.get(this.key);
        user.setProperty("username", this.username);
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

    public void updateSentiment(int newSentiment){
        this.sentiment = newSentiment;
        if (key == null) return;
        Entity user = datastore.get(this.key);
        user.setProperty("sentiment", this.sentiment);
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
            (boolean) entity.getProperty("isProf"));
        output.updateDisplayImageKey((BlobKey) entity.getProperty("displayImageKey"));
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

        return new User(email, username, college, isProfessor)
    }
} 