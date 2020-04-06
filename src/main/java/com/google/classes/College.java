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
 * This class represents a single college of the chatroom platform.
 *
 */
public final class College{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final String name;
    private BlobKey displayImageKey;
    private String website;
    private String telephone;
    private String description;
    private String email;
    private Key key;
    private float sentiment;
    private int numOfSentimentUpdate; 

    private College(String name, float sentiment, int numOfUpdates){
        this.name = name;
        this.sentiment = sentiment;
        this.numOfSentimentUpdate = numOfUpdates;
    }

    private void setKey(Key key){
        this.key = key;
    }

    /*
     * Returns the email of the College object
     * 
     */
    public String getEmail(){
        return this.email;
    }

    /*
     * Returns the name of the College object
     * 
     */
    public String getName(){
        return this.name;
    }

    /*
     * Returns the description of the College object
     * 
     */
    public String getDescription(){
        return this.description;
    }

    /*
     * Returns the website of the College object
     * 
     */
    public String getWebsite(){
        return this.website;
    }

    /*
     * Returns the telephone of the College object
     * 
     */
    public String getTelephone(){
        return this.telephone;
    }

    /*
     * Returns the key of the College object
     * 
     */
    public Key getKey(){
        return this.key;
    }

    /*
     * Returns the sentiment of the College object
     * 
     */
    public float getSentiment(){
        return this.sentiment;
    }

    /*
     * Returns the BlobKey of the displayImage of a College object
     */
    public BlobKey getDisplayImageKey(){
        return this.displayImageKey;
    }

    /*
     * Updates the BlobKey of the displayImage of a College object
     */
    public void updateDisplayImageKey(BlobKey newKey){
        this.displayImageKey = newKey;
        if (key == null) return;
        Entity college = datastore.get(this.key);
        college.setProperty("displayImageKey", this.displayImageKey);
        datastore.put(college);
    }

    /*
     * Updates the sentiment of a College object
     */
    public void updateSentiment(float newSentiment){
        this.sentiment = (this.numOfSentimentUpdate*this.sentiment + newSentiment)/(this.numOfSentimentUpdate + 1);
        this.numOfSentimentUpdate++;
        if (key == null) return;
        Entity college = datastore.get(this.key);
        college.setProperty("sentiment", this.sentiment);
        college.setProperty("sentimentUpdate", this.numOfSentimentUpdate);
        datastore.put(college);
    }

    /*
     * Updates the email of the College object
     * 
     */
    public void updateEmail(String newEmail){
        this.email = newEmail;
        if (key == null) return;
        Entity college = datastore.get(this.key);
        college.setProperty("email", this.email);
        datastore.put(college);
    }

    /*
     * Updates the description of the College object
     * 
     */
    public void updateDescription(String newDescription){
        this.description = newDescription;
        if (key == null) return;
        Entity college = datastore.get(this.key);
        college.setProperty("description", this.description);
        datastore.put(college);
    }

    /*
     * Updates the website address of the College object
     * 
     */
    public void updateWebsite(String newWebsite){
        this.website = newWebsite;
        if (key == null) return;
        Entity college = datastore.get(this.key);
        college.setProperty("website", this.website);
        datastore.put(college);
    }

    /*
     * Updates the telephone of the College object
     * 
     */
    public void updateTelephone(String newTelephone){
        this.telephone = newTelephone;
        if (key == null) return;
        Entity college = datastore.get(this.key);
        college.setProperty("telephone", this.telephone);
        datastore.put(college);
    }

    /*
     * Returns a College object with the same Key in Datastore. 
     */
    public static College getCollege(Key collegeKey){
        Entity college = datastore.get(collegeKey);
        College output = new College((String) college.getProperty("name"), (float)college.getProperty("sentiment"), (int)college.getProperty("sentimentUpdate"));
        output.updateEmail((String) college.getProperty("email"));
        output.updateDescription((String) college.getProperty("description"));
        output.updateTelephone((String) college.getProperty("telephone"));
        output.updateWebsite((String) college.getProperty("website"));
        output.updateDisplayImageKey((BlobKey) college.getProperty("displayImageKey"));
        output.setKey(collegeKey);
        return output;
    }

     /*
     * Returns a College object with the same name in Datastore. 
     */
    public static College getCollege(String collegeName){
        Query query =
        new Query("College")
            .setFilter(new Query.FilterPredicate("name", Query.FilterOperator.EQUAL, collegeName));
        PreparedQuery result = datastore.prepare(query);
        Entity college = result.asSingleEntity();
        
        if(college == null){
            return null;
        }
        College output = new College((String) college.getProperty("name"), (float)college.getProperty("sentiment"), (int)college.getProperty("sentimentUpdate"));
        output.updateEmail((String) college.getProperty("email"));
        output.updateDescription((String) college.getProperty("description"));
        output.updateTelephone((String) college.getProperty("telephone"));
        output.updateWebsite((String) college.getProperty("website"));
        output.updateDisplayImageKey((BlobKey) college.getProperty("displayImageKey"));
        output.setKey(collegeKey);
        return output;
    }
}