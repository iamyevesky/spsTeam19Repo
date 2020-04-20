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
import java.util.Arrays;

//START OF PRODUCTION CODE

/*
 * This class represents a single Class of the chatroom platform.
 *
 */
public final class ClassObject{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final String name;
    private final College college;
    private final Department department;
    private String key;
    private transient ArrayList<String> students;
    private transient String professor;

    private ClassObject(String name, College college, Department department){
        this.name = name;
        this.college = college;
        this.department = department;
        this.students = new ArrayList<String>();
    }

    private void setKey(Key key){
        this.key = KeyFactory.keyToString(key);
    }

    private void setStudents(ArrayList<Key> studentKeys) throws EntityNotFoundException{
        if (studentKeys == null) return;
        for(Key key : studentKeys){
            this.students.add(KeyFactory.keyToString(key));
        }
    }

    private void setProfessor(Key professorKey) throws EntityNotFoundException{
        if (professorKey == null) return;
        this.professor = KeyFactory.keyToString(professorKey);
    }

    private ArrayList<Key> getKeys(ArrayList<String> users){
        ArrayList<Key> output = new ArrayList<>();
        for(String user : users){
            output.add(KeyFactory.stringToKey(user));
        }
        return output;
    }

    public void addStudent(User user){
        this.students.add(user.getKey());
    }

    public String getKey(){
        return this.key;
    }

    public void saveToDatabase(){
        Entity classObject = new Entity("classObject");
        classObject.setProperty("name", this.name);
        classObject.setProperty("collegeID", KeyFactory.stringToKey(this.college.getKey()));
        classObject.setProperty("departmentID", KeyFactory.stringToKey(this.department.getKey()));
        classObject.setProperty("sentiment", 0.0);
        classObject.setProperty("studentKeys", getKeys(this.students));

        if (this.professor != null){
            classObject.setProperty("professorKey", KeyFactory.stringToKey(this.professor));
        }
        datastore.put(classObject);
        this.key = KeyFactory.keyToString(classObject.getKey());
    }

    public void updateDatabase() throws EntityNotFoundException{
        if (key == null) return;
        Entity entity = datastore.get(KeyFactory.stringToKey(this.key));
        entity.setProperty("studentKeys", getKeys(this.students));
        if (this.professor != null){
            entity.setProperty("professorKey", KeyFactory.stringToKey(this.professor));
        }
        datastore.put(entity);
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
        ClassObject output = new ClassObject((String)classObject.getProperty("name"), College.getCollege((Key)classObject.getProperty("collegeID")), Department.getDepartment((Key)classObject.getProperty("departmentID")));
        output.setStudents((ArrayList<Key>) classObject.getProperty("studentKeys"));
        output.setProfessor((Key) classObject.getProperty("professorKey"));
        output.setKey(classObject.getKey());
        return output;
    }
    //END OF PRODUCTION CODE

    //START OF DEVELOPMENT CODE
    public static ClassObject getClassObjectTest(College college, Department department){
        ClassObject output = new ClassObject("aClass", college, department);
        output.saveToDatabase();
        return output;
    }
    //END OF DEVELOPMENT CODE
}