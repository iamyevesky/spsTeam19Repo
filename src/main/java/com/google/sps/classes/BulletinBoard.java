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
 * This class represents a single bulletin board of the chatroom platform.
 *
 */
public final class BulletinBoard{
    /*
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final Department department;
    private String description;
    private Key key;

    private void setKey(Key newKey){
        this.key = newKey;
    }
    
    private BulletinBoard(Department dept){
        this.department = department;
    }

    public Department getDepartment(){
        return this.department;
    }

    public String getDecription(){
        return this.description;
    }

    public Key getKey(){
        return this.key;
    }

    public void setDescription(String newDescription){
        this.description = newDescription;
        if (key == null) return;
        Entity board = datastore.get(this.key);
        board.setProperty("description", this.description);
        datastore.put(board);
    }

    public void saveToDatabase(){
        Entity board = new Entity("BulletinBoard");
        board.setProperty("departmentID", this.department.getKey());
        board.setProperty("description", this.description);
        this.key = board.getKey();
        datastore.put(board);
    }

    public static BulletinBoard getBulletinBoard(Key departmentKey){
        Query boardQuery =
        new Query("BulletinBoard")
            .setFilter(new Query.FilterPredicate("departmentID", Query.FilterOperator.EQUAL, departmentKey));
        Entity board = datastore.prepare(boardQuery).asSingleEntity();
        if (board == null){
            return null;
        }
        BulletinBoard newBoard = new BulletinBoard(Department.getDepartment((Key)board.getProperty("departmentID")));
        newBoard.setDescription((String) board.getProperty("description"));
        newBoard.setKey((Key) board.getKey());
    }

    public static BulletinBoard createNewBulletinBoard(Key departmentKey){
        return new BulletinBoard(Department.getDepartment(departmentKey));
    }
    */
}