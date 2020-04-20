package com.google.sps.classes;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.*;
@WebServlet("/addDepartment")
public class AddDepartmentAndClassServlet extends HttpServlet {
    UserService userService = UserServiceFactory.getUserService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        if(!userService.isUserLoggedIn()){
            response.sendRedirect("/startup");
            return;
        }

        User user = null;
        try
        {
            user = user.getUser(userService.getCurrentUser().getEmail());
        }
        catch(EntityNotFoundException e)
        {
            response.sendRedirect("/startup");
            System.out.println("No user found in /addDepartment");
            return;
        }

        Department dept = Department.getDepartmentTest(user.getCollege());
        ClassObject classObject = ClassObject.getClassObjectTest(user.getCollege(), dept);

        try
        {
            configure(user, dept, classObject);
        }
        catch(EntityNotFoundException e)
        {
            response.sendRedirect("/startup");
        }
        response.sendRedirect("/startup");
    }

    private void configure(User user, Department department, ClassObject classObject) throws EntityNotFoundException{
        user.addDepartment(department);
        user.addClassObject(classObject);
        department.addStudent(user);
        classObject.addStudent(user);
        user.updateDatabase();
        department.updateDatabase();
        classObject.updateDatabase();
    }
}