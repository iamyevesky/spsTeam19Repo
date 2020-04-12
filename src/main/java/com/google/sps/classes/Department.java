package com.google.sps.classes;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.blobstore.BlobKey;
import java.util.ArrayList;

/*
 * This class represents a single department of the chatroom platform.
 *
 */
public final class Department{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final int id;
    private final String name;
    private final College college;
    private String key;
    private ArrayList<User> professors;
    private ArrayList<User> students;

    private Department(String name, int collegeID, int departmentID){
        this.name = name;
        this.college = College.getCollege(collegeID);
        this.id = departmentID;
    }

    private void setKey(Key key){
        this.key = KeyFactory.keyToString(key);
    }

    private void setStudents(ArrayList<Key> studentKeys) throws EntityNotFoundException{
        this.students = new ArrayList<>();
        for(Key key : studentKeys){
            this.students.add(User.getUser(key));
        }
    }

    private void setProfessors(ArrayList<Key> professorKeys) throws EntityNotFoundException{
        this.professors = new ArrayList<>();
        for(Key key : professorKeys){
            this.professors.add(User.getUser(key));
        }
    }

    public String getKey(){
        return this.key;
    }

    /*
    public static Department getDepartment(Key departmentKey){
        Entity department = datastore.get(departmentKey);
        Department output = new Department((String) department.getProperty("name"),
        College.getCollege((Key) department.getProperty("collegeID")),
        0,
        0
        );
        output.updateWebsite((String) department.getProperty("website"));
        output.updateDisplayImageKey((BlobKey) department.getProperty("displayImageKey"));
        output.updateDescription((String) department.getProperty("description"));
        output.setStudentKeys((ArrayList<Key>) department.getProperty("studentKeys"));
        output.setProfessorKeys((ArrayList<Key>) department.getProperty("professorKeys"));
        output.setKey(department.getKey());
        return output;
    }
    */

    public static Department getDepartment(Key key){
        return null;
    }

    public static Department getDepartment(int collegeID, int departmentID){
        return null;
    }
}