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
    private String key; 

    //START OF PRODUCTION CODE

    private College(String name){
        this.name = name;
    }

    private void setKey(Key key){
        this.key = KeyFactory.keyToString(key);
    }

    public String getKey(){
        return this.key;
    }

    public String getAllDepartmentsJson() throws EntityNotFoundException{
        Query query =
        new Query("Department").setFilter(new Query.FilterPredicate("collegeID", Query.FilterOperator.EQUAL, this.key));
        PreparedQuery result = datastore.prepare(query);
        ArrayList<Department> deptList = new ArrayList<Department>();
        for (Entity entity : result.asIterable()){
            deptList.add(Department.getDepartment(entity.getKey()));    
        }
        Gson gson = new Gson();
        return gson.toJson(deptList);
    }

    public static College getCollege(Key key) throws EntityNotFoundException{
        Entity entity = datastore.get(key);
        College output = new College((String) entity.getProperty("name"));
        output.setKey(entity.getKey());
        return output;
    }

    public static String getAllCollegesJSON() throws EntityNotFoundException{
        Query query = new Query("College");
        PreparedQuery result = datastore.prepare(query);
        ArrayList<College> output = new ArrayList<College>();
        for (Entity entity : result.asIterable()){
            output.add(College.getCollege(entity.getKey()));    
        }
        Gson gson = new Gson();
        return gson.toJson(output);
    }
    //END OF PRODUCTION CODE

    //START OF DEVELOPMENT CODE
    
    public College(String name, int id){
        this.name = name;
        Entity entity = new Entity("College");
        entity.setProperty("name", name);
        datastore.put(entity);
        this.key = KeyFactory.keyToString(entity.getKey());
    }

    public static College getCollegeTest(){
        College output = new College("NewUniversity", 0);
        return output;
    }
    //END OF DEVELOPMENT CODE
}