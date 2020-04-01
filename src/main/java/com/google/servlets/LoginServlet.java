package com.google.sps.servlets;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    UserService userService = UserServiceFactory.getUserService();

    //if not logged in, show login form
    if (!userService.isUserLoggedIn()) {
      String loginUrl = userService.createLoginURL("/index.html");
      out.println("<p>You are not logged in.</p>");
      out.println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
      return;
    }

     //if user does not have nickname, prompt them to enter one
    String name = getUserName(userService.getCurrentUser().getUserId());
    if (name == null) {
      response.sendRedirect("/name");
      return;
    }

      String logoutUrl = userService.createLogoutURL("/index.html");

      response.getWriter().println("<p>Hello " + name + "!</p>");
      response.getWriter().println("<p>Logout <a href=\"" + logoutUrl + "\">here</a>.</p>");
      out.println("<p>Change your name <a href=\"/name\">here</a>.</p>");
    } 
  //stores user inputs in datastore
  private String getUserName(String id) {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query =
        new Query("UserInfo")
            .setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id));
    PreparedQuery result = datastore.prepare(query);
    Entity entity = result.asSingleEntity();
    if (entity == null) {
      return null;
    }
    String name = (String) entity.getProperty("name");
    return name;
  }
}
