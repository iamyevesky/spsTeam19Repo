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
import java.util.Date;

/*
 * This class represents a single bulletin post of the chatroom platform.
 *
 */
public final class BulletinPost{
    /*
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final String title;
    private final BulletinBoard board;
    private final User user;
    private final String body;
    private final Date date;
    private Key key;

    private BulletinPost(User user, String title, String body, BulletinBoard board){
        this.title = title;
        this.user = user;
        this.board = board;
        this.body = body;
        this.date = new Date();
    }

    private void saveToDataBase(){
        Entity post = new Entity("BulletinPost");
        post.setProperty("title", this.title);
        post.setProperty("body", this.body);
        post.setProperty("timestamp", this.date);
        post.setProperty("boardID", this.board.getKey());
        post.setProperty("userID", this.user.getKey());
        datastore.put(post);
    }

    public static void addPostToDatabase(User user, String title, String body, BulletinBoard board){
        BulletinPost newPost = new BulletinPost(user, title, body, board);
        newPost.saveToDataBase();
    }
    */
}