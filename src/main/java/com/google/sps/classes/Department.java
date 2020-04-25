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
import java.util.Arrays;
import com.google.gson.Gson;

/*
 * This class represents a single department of the chatroom platform.
 *
 */
public final class Department{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final String name;
    private final College college;
    private String key;
    private transient ArrayList<String> professors;
    private transient ArrayList<String> students;

    //START OF PRODUCTION CODE

    private Department(String name, College college){
        this.name = name;
        this.college = college;
        this.students = new ArrayList<String>();
        this.professors = new ArrayList<String>();
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

    private void setProfessors(ArrayList<Key> professorKeys) throws EntityNotFoundException{
        if (professorKeys == null) return;

        for(Key key : professorKeys){
            this.professors.add(KeyFactory.keyToString(key));
        }
    }

    private ArrayList<Key> getKeys(ArrayList<String> users){
        ArrayList<Key> output = new ArrayList<>();
        for(String user : users){
            output.add(KeyFactory.stringToKey(user));
        }
        return output;
    }

    public void saveToDatabase(){
        Entity entity = new Entity("Department");
        entity.setProperty("name", this.name);
        entity.setProperty("collegeID", KeyFactory.stringToKey(this.college.getKey()));
        entity.setProperty("departmentID", this.key);
        entity.setProperty("studentKeys", getKeys(this.students));
        entity.setProperty("professorKeys", getKeys(this.professors));
        datastore.put(entity);
        this.key = KeyFactory.keyToString(entity.getKey());
    }

    public void updateDatabase() throws EntityNotFoundException{
        if (key == null) return;
        Entity entity = datastore.get(KeyFactory.stringToKey(this.key));
        entity.setProperty("studentKeys", getKeys(this.students));
        entity.setProperty("professorKeys", getKeys(this.professors));
        datastore.put(entity);
    }

    public String getKey(){
        return this.key;
    }

    public void addStudent(User user){
        this.students.add(user.getKey());
    }

    public void addProfessor(User user){
        this.professors.add(user.getKey());
    }

    public String getAllClassesJson() throws EntityNotFoundException{
        Query query =
        new Query("ClassObject").setFilter(new Query.FilterPredicate("departmentID", Query.FilterOperator.EQUAL, this.key));
        PreparedQuery result = datastore.prepare(query);
        ArrayList<ClassObject> classList = new ArrayList<ClassObject>();
        for (Entity entity : result.asIterable()){
            classList.add(ClassObject.getClassObject(entity.getKey()));    
        }
        Gson gson = new Gson();
        return gson.toJson(classList);
    }

    public String getPostsJson() throws EntityNotFoundException{
        Gson gson = new Gson();
        return gson.toJson(BulletinPost.getPosts(this));
    }

    public static Department getDepartment(Key key) throws EntityNotFoundException{
        Entity department = datastore.get(key);
        if (department == null){
            return null;
        }
        Department output = new Department((String) department.getProperty("name"), College.getCollege((Key) department.getProperty("collegeID")));
        output.setStudents((ArrayList<Key>) department.getProperty("studentKeys"));
        output.setProfessors((ArrayList<Key>) department.getProperty("professorKeys"));
        output.setKey(department.getKey());
        return output;
    }
    //END OF PRODUCTION CODE

    //START OF DEVELOPMENT CODE

    public static Department getDepartmentTest(College college){
        Department output = new Department("aDepartment", college);
        output.saveToDatabase();
        return output;
    }
    //END OF DEVELOPMENT CODE
}