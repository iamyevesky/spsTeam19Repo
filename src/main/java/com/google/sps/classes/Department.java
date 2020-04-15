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
    private final long id;
    private final String name;
    private final College college;
    private String key;
    private ArrayList<User> professors;
    private ArrayList<User> students;

    private Department(String name, long collegeID, long departmentID){
        this.name = name;
        this.college = College.getCollege(collegeID);
        this.id = departmentID;
        this.students = new ArrayList<>();
        this.professors = new ArrayList<>();
    }

    private Department(String name, College college, long departmentID){
        this.name = name;
        this.college = college;
        this.id = departmentID;
        this.students = new ArrayList<>();
        this.professors = new ArrayList<>();
    }

    private void setKey(Key key){
        this.key = KeyFactory.keyToString(key);
    }

    private void setStudents(ArrayList<Key> studentKeys) throws EntityNotFoundException{
        if (studentsKeys == null) return;

        for(Key key : studentKeys){
            this.students.add(User.getUser(key));
        }
    }

    private void setProfessors(ArrayList<Key> professorKeys) throws EntityNotFoundException{
        if (professorKeys == null) return;

        for(Key key : professorKeys){
            this.professors.add(User.getUser(key));
        }
    }

    private ArrayList<Key> getKeys(ArrayList<User> users){
        if (users == null) return new ArrayList<Key>();

        ArrayList<Key> output = new ArrayList<>();
        for(User user : users){
            output.add(KeyFactory.stringToKey(user.getKey()));
        }
        return output;
    }

    public void saveToDatabase(){
        Entity entity = new Entity("Department");
        entity.setProperty("name", this.name);
        entity.setProperty("collegeID", this.collegeID);
        entity.setProperty("departmentID", this.departmentID);
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
    }

    public String getKey(){
        return this.key;
    }

    public void addStudent(User user){
        this.students.add(user);
    }

    public String getAllClassesJson() throws EntityNotFoundException{
        Query query =
        new Query("ClassObject")
        .setFilter(new Query.CompositeFilter(Query.CompositeFilterOperator.AND, Arrays
        .asList(new Query.FilterPredicate("collegeID", Query.FilterOperator.EQUAL, collegeID),
        new Query.FilterPredicate("departmentID", Query.FilterOperator.EQUAL, departmentID))))
        .addSort("classID", SortDirection.ASCENDING);
        PreparedQuery result = datastore.prepare(query);

        ArrayList<ClassObject> classList = new ArrayList<ClassObject>();

        for (Entity entity : results.asIterable()){
            classList.add(ClassObject.getClassObject(entity.getKey()));    
        }

        Gson gson = new Gson();
        return gson.toJson(classList);
    }

    public static Department getDepartment(Key key) throws EntityNotFoundException{
        Entity department = datastore.get(departmentKey);

        if (department == null){
            return null;
        }

        Department output = new Department((String) department.getProperty("name"), College.getCollege(((Long) department.getProperty("collegeID")).longValue()), ((Long) entity.getProperty("departmentID")).longValue());
        output.setStudentKeys((ArrayList<Key>) department.getProperty("studentKeys"));
        output.setProfessorKeys((ArrayList<Key>) department.getProperty("professorKeys"));
        output.setKey(department.getKey());
        return output;
    }

    public static Department getDepartment(long collegeID, long departmentID) throws EntityNotFoundException{
        Query query = new Query("Department")
        .setFilter(new Query.CompositeFilter(Query.CompositeFilterOperator.AND, Arrays
        .asList(new Query.FilterPredicate("collegeID", Query.FilterOperator.EQUAL, collegeID),
        new Query.FilterPredicate("departmentID", Query.FilterOperator.EQUAL, departmentID))));
        PreparedQuery result = datastore.prepare(query);
        Entity entity = result.asSingleEntity();
        
        if (entity == null){
            return null;
        }
        
        Department output = new Department((String) entity.getProperty("name"), College.getCollege(((Long) entity.getProperty("collegeID")).longValue()), ((Long) entity.getProperty("departmentID")).longValue());
        output.setStudentKeys((ArrayList<Key>) entity.getProperty("studentKeys"));
        output.setProfessorKeys((ArrayList<Key>) entity.getProperty("professorKeys"));
        output.setKey(entity.getKey());
        return output;
    }

    public static Department getDepartmentTest(long collegeID, long departmentID){
        Department output = new Department("aDepartment", College.getCollegeTest(collegeID), departmentID);
        output.saveToDatabase();
        return output;
    }
}