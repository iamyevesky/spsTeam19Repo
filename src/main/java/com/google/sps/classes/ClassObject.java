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

import java.util.ArrayList;

/*
 * This class represents a single Class of the chatroom platform.
 *
 */
public final class ClassObject{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final String name;
    private final College college;
    private final Department department;
    private final int id;
    private String key;
    private ArrayList<User> students;
    private User professor;

    private ClassObject(String name, int classObjectID, int collegeID, int departmentID){
        this.name = name;
        this.college = College.getCollege(collegeID);
        this.department = Department.getDepartment(collegeID, departmentID);
        this.id = classObjectID;
    }

    private void setKey(Key key){
        this.key = KeyFactory.keyToString(key);
    }

    private void setStudents(ArrayList<Key> studentKeys) throws EntityNotFoundException{
        this.students = new ArrayList<>();
        if (studentKeys == null) return;
        for(Key key : studentKeys){
            this.students.add(User.getUser(key));
        }
    }

    private void setProfessor(Key professorKey) throws EntityNotFoundException{
        if (professorKey == null) return;
        this.professor = User.getUser(key);
    }

    private ArrayList<Key> getKeys(ArrayList<User> users){
        if (users == null) return new ArrayList<Key>();
        ArrayList<Key> output = new ArrayList<>();
        for(User user : users){
            output.add(KeyFactory.stringToKey(user.getKey()));
        }
        return output;
    }

    public String getKey(){
        return this.key;
    }

    public void saveToDatabase(){
        Entity classObject = new Entity("classObject");
        classObject.setProperty("name", this.name);
        classObject.setProperty("collegeID", this.college.getID());
        classObject.setProperty("classID", this.id);
        classObject.setProperty("sentiment", 0.0);
        classObject.setProperty("studentKeys", getKeys(this.students));
        classObject.setProperty("professorKey", KeyFactory.stringToKey(this.professor.getKey()));
        datastore.put(classObject);
        this.key = KeyFactory.keyToString(classObject.getKey());
    }
    
    //Not needed for now. Will implement when necessary
    /*
    public static ClassObject createNewClassObject(String name, College college, Department department){
        return new ClassObject(name, college, department, 0, 0);
    }
    */

    public static ClassObject getClassObject(Key key) throws EntityNotFoundException{
        Entity classObject = datastore.get(key);
        
        if (classObject == null) return null;
        ClassObject output = new ClassObject((String) classObject.getProperty("name"), (int) classObject.getProperty("collegeID"), (int) classObject.getProperty("departmentID"), (int) classObject.getProperty("classID"));
        output.setStudents((ArrayList<Key>) classObject.getProperty("studentKeys"));
        output.setProfessor((Key) classObject.getProperty("professorKey"));
        output.setKey(classObject.getKey());
        return output;
    }

    public static ClassObject getClassObject(int collegeID, int departmentID, int classID){
        return null;
    }
}