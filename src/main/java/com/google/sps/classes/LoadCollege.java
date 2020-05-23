package com.google.sps.classes;

import java.io.*;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.*;

public class LoadCollege{
    private final static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private LoadCollege(){}

    public static void loadColleges(){
        if (exists()) return;
        System.out.println(exists());
        try
        {
            String path = "";
            path = path.concat("/home/yevesky2020/spsTeam19Repo/src/main/java/com/google/sps/classes/newCollegeNamesList.txt");
            Scanner scanner = new Scanner(new File(path));
            while (scanner.hasNextLine())
            {
                College.saveCollege(scanner.nextLine());
            }
            Entity entity = new Entity("data");
            datastore.put(entity);
            System.out.println("Done!");
        }
        catch(IOException e)
        {
            System.out.println("Could not load colleges");
            System.out.println(e);
        }
    }

    private static boolean exists(){
        Query q = new Query("data").setKeysOnly();
        PreparedQuery pq = DatastoreServiceFactory.getDatastoreService().prepare(q);
        List<Entity> list = pq.asList(FetchOptions.Builder.withLimit(1));
        if (list != null)
        {
            return list.size() > 0;
        }
        else
        {
            return false;
        }
    }
}