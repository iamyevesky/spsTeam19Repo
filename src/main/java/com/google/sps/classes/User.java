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
import com.google.gson.Gson;

import java.util.ArrayList;

/*
 * This class represents a single user of the chatroom platform.
 *
 */
public final class User{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final String email;
    private final String username;
    private final College college;
    private final boolean isProf;
    private String key;
    private float sentiment; 
    private ArrayList<ClassObject> classes;
    private ArrayList<Department> departments;

    private User(String email, String username, int collegeID, boolean isProf){
        this.email = email;
        this.username = username;
        this.college = College.getCollege(collegeID);
        this.isProf = isProf;
        this.classes = new ArrayList<ClassObject>();
        this.departments = new ArrayList<Department>();
    }

    private User(String email, String username, College college, boolean isProf){
        this.email = email;
        this.username = username;
        this.college = college;
        this.isProf = isProf;
        this.classes = new ArrayList<ClassObject>();
        this.departments = new ArrayList<Department>();
    }

    private void setKey(Key key){
        this.key = KeyFactory.keyToString(key);
    }

    private void setClasses(ArrayList<Key> classObjectKeys) throws EntityNotFoundException{
        this.classes = new ArrayList<>();
        
        if (classObjectKeys == null){
            return;
        }
        
        for(Key classObjectKey : classObjectKeys){
            this.classes.add(ClassObject.getClassObject(classObjectKey));
        }
    }

    private void setDepartments(ArrayList<Key> departmentKeys) throws EntityNotFoundException{
        this.departments = new ArrayList<>();

        if (departmentKeys == null){
            return;
        }

        for(Key departmentKey : departmentKeys){
            this.departments.add(Department.getDepartment(departmentKey));
        }
    }

    private ArrayList<Key> getKeysClasses(ArrayList<ClassObject> classes){
        ArrayList<Key> output = new ArrayList<>();
        for(ClassObject classObject : classes){
            output.add(KeyFactory.stringToKey(classObject.getKey()));
        }
        return output;
    }

    private ArrayList<Key> getKeysDepartments(ArrayList<Department> departments){
        ArrayList<Key> output = new ArrayList<>();
        for(Department department : departments){
            output.add(KeyFactory.stringToKey(department.getKey()));
        }
        return output;
    }

    public String getKey(){
        return this.key;
    }

    public void saveToDatabase(){
        if (key != null) return;
        Entity user = new Entity("User");
        user.setProperty("username", this.username);
        user.setProperty("email", this.email);
        user.setProperty("collegeID", this.college.getID());
        user.setProperty("isProf", this.isProf);
        user.setProperty("sentiment", 0.0);
        user.setProperty("classObjectKeys", getKeysClasses(this.classes));
        user.setProperty("departmentKeys", getKeysDepartments(this.departments));
        datastore.put(user);
        this.key = KeyFactory.keyToString(user.getKey());
    }

    public void updateDatabase() throws EntityNotFoundException{
        if (key == null) return;
        Entity user = datastore.get(KeyFactory.stringToKey(this.key));
        user.setProperty("username", this.username);
        user.setProperty("email", this.email);
        user.setProperty("collegeID", this.college.getID());
        user.setProperty("isProf", this.isProf);
        user.setProperty("sentiment", 0.0);
        user.setProperty("classObjectKeys", getKeysClasses(this.classes));
        user.setProperty("departmentKeys", getKeysDepartments(this.departments));
        datastore.put(user);
    }

    public static String convertToJSON(User user){
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    public static User getUser(String email) throws EntityNotFoundException{
        Query query =
        new Query("User")
            .setFilter(new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email));
        PreparedQuery result = datastore.prepare(query);
        Entity entity = result.asSingleEntity();
        
        if(entity == null){
            return null;
        }

        User output = new User((String) entity.getProperty("email"), (String) entity.getProperty("username"), (int) entity.getProperty("collegeID"), (boolean) entity.getProperty("isProf"));
        output.setKey((Key) entity.getKey());
        output.setClasses((ArrayList<Key>) entity.getProperty("classObjectKeys"));
        output.setDepartments((ArrayList<Key>) entity.getProperty("departmentKeys"));
        return output;
    }

    public static User getUser(Key key) throws EntityNotFoundException{
        Entity entity = datastore.get(key);
        
        if(entity == null){
            return null;
        }

        User output = new User((String) entity.getProperty("email"), (String) entity.getProperty("username"), (int) entity.getProperty("collegeID"), (boolean) entity.getProperty("isProf"));
        output.setKey((Key) entity.getKey());
        output.setClasses((ArrayList<Key>) entity.getProperty("classKeys"));
        output.setDepartments((ArrayList<Key>) entity.getProperty("departmentKeys"));
        return output;
    }

    public static User getUserTest(String email) throws EntityNotFoundException{
        Query query =
        new Query("User")
            .setFilter(new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email));
        PreparedQuery result = datastore.prepare(query);
        Entity entity = result.asSingleEntity();
        
        if(entity == null){
            return null;
        }

        User output = new User((String) entity.getProperty("email"), (String) entity.getProperty("username"), College.getCollegeTest(((Long) entity.getProperty("collegeID")).intValue()), (boolean) entity.getProperty("isProf"));
        output.setKey((Key) entity.getKey());
        output.setClasses((ArrayList<Key>) entity.getProperty("classKeys"));
        output.setDepartments((ArrayList<Key>) entity.getProperty("departmentKeys"));
        return output;
    }

    /*
     * Returns a User object which does not exists in the database system.
     * Returns null if the User does exist in the database.
     */
    public static User createUser(String email, String username, int collegeID, boolean isProfessor){
        Query emailQuery =
        new Query("User").setFilter(new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email));

        PreparedQuery result = datastore.prepare(emailQuery);
        Entity emailEntity = result.asSingleEntity();
        if(emailEntity != null){
            return null;
        }
        return new User(email, username, collegeID, isProfessor);
    }

    public static User createUserTest(String email, String username, int collegeID, boolean isProfessor){
        Query emailQuery =
        new Query("User").setFilter(new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email));

        PreparedQuery result = datastore.prepare(emailQuery);
        Entity emailEntity = result.asSingleEntity();
        if(emailEntity != null){
            return null;
        }
        return new User(email, username, College.getCollegeTest(collegeID), isProfessor);
    }
} 