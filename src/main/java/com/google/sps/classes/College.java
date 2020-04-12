package com.google.sps.classes;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
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
    private final int id;
    private String key; 

    private College(String name, int id){
        this.name = name;
        this.id = id;
    }

    private void setKey(Key key){
        this.key = KeyFactory.keyToString(key);
    }

    public int getID(){
        return this.id;
    }

    public static College getCollege(int id){
        Query query =
        new Query("College")
            .setFilter(new Query.FilterPredicate("collegeID", Query.FilterOperator.EQUAL, id));
        PreparedQuery result = datastore.prepare(query);
        Entity college = result.asSingleEntity();
        
        if(college == null){
            return null;
        }
        College output = new College((String) college.getProperty("name"), id);
        output.setKey(college.getKey());
        return output;
    }

    public static College getCollegeTest(int id){
        College output = new College("AUniversity", id);
        return output;
    }
}