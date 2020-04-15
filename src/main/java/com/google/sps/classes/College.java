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
import com.google.gson.Gson;
import java.util.*;

/*
 * This class represents a single college of the chatroom platform.
 *
 */
public final class College{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final String name;
    private final long id;
    private String key; 

    private College(String name, long id){
        this.name = name;
        this.id = id;
    }

    private void setKey(Key key){
        this.key = KeyFactory.keyToString(key);
    }

    public long getID(){
        return this.id;
    }

    public static College getCollege(long id){
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

    public static String getAllCollegesJSON(){
        Query query = new Query("__Stat_Kind__");
        query.addFilter("kind_name", Query.FilterOperator.EQUAL, "College");       
        Entity entityStat = datastore.prepare(query).asSingleEntity();
        long totalEntities = ((Long) entityStat.getProperty("count")).longValue();
        
        ArrayList<College> output = new ArrayList<>();
        for (long i = 0; i < totalEntities; i++){
            output.add(College.getCollege(i));
        }
        Gson gson = new Gson();
        return gson.toJson(output);
    }

    public static College getCollegeTest(long id){
        College output = new College("AUniversity", id);
        return output;
    }
}