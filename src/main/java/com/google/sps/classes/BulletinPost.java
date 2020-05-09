package com.google.sps.classes;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.cloud.Timestamp;
import java.util.ArrayList;

/*
 * This class represents a single bulletin post of the chatroom platform.
 *
 */
public final class BulletinPost{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final String title;
    private final College college;
    private final User user;
    private final String body;
    private final Timestamp date;

    private BulletinPost(User user, String title, String body){
        this.title = title;
        this.user = user;
        this.college = user.getCollege();
        this.body = body;
        this.date = Timestamp.now();
    }

    private void saveToDataBase(){
        Entity post = new Entity("BulletinPost");
        post.setProperty("title", this.title);
        post.setProperty("body", this.body);
        post.setProperty("timestamp", this.date.toDate());
        post.setProperty("collegeID", KeyFactory.stringToKey(this.college.getKey()));
        post.setProperty("userID", KeyFactory.stringToKey(this.user.getKey()));
        datastore.put(post);
    }

    private static BulletinPost getPost(Entity entity) throws EntityNotFoundException{
        return new BulletinPost(User.getUser((Key) entity.getProperty("userID")), 
        (String) entity.getProperty("title"), 
        (String) entity.getProperty("body"));
    }

    public static void addPostToDatabase(User user, String title, String body){
        BulletinPost newPost = new BulletinPost(user, title, body);
        newPost.saveToDataBase();
    }

    public static ArrayList<BulletinPost> getPosts(College college) throws EntityNotFoundException{
        Query query = 
        new Query("BulletinPost")
        .setFilter(new Query.
        FilterPredicate("collegeID", Query.FilterOperator.EQUAL, KeyFactory.stringToKey(college.getKey())))
        .addSort("timestamp", SortDirection.ASCENDING);
        
        PreparedQuery result = datastore.prepare(query);
        ArrayList<BulletinPost> output = new ArrayList<BulletinPost>();
        for (Entity entity : result.asIterable()){
            output.add(BulletinPost.getPost(entity));    
        }
        return output;
    }
}