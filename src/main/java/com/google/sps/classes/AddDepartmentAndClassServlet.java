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
        long departmentID = Long.parseLong(request.getParameter("department"));
        long classID = Long.parseLong(request.getParameter("class"));

        if(!userService.isUserLoggedIn()){
            response.sendRedirect("/startup");
            return;
        }

        try
        {
            User user = user.getUser(userService.getCurrentUser().getEmail());
        }
        catch(EntityNotFoundException e)
        {
            response.sendRedirect("/startup");
            return;
        }
        Department department = Department.getDepartmentTest(0, 0);
        ClassObject classObject = ClassObject.getClassObjectTest(0, 0, 0);
        configure(user, department, classObject);
        response.sendRedirect("/startup");
    }

    private void configure(User user, Department department, ClassObject classObject){
        user.addDepartment(department);
        user.addClassObject(classObject);
        department.addStudent(user);
        classObject.addStudent(user);
        user.updateDatabase();
        department.updateDatabase();
        classObject.updateDatabase();
    }
}