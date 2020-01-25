<%@ page import="com.main.User" %>
<%@ page import="com.main.LoginServlet" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %><%-- Created by IntelliJ IDEA. --%>
<%-- Sources used: https://www.codejava.net/java-ee/servlet/handling-html-form-data-with-java-servlet --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>UserDataBase</title>

  <h1>Welcome</h1>
  <h4>Create a user, created users usernames will be displayed at the bottom.</h4>

  <form name="loginForm" method="post" action="loginServlet">
    Username: <input type="text" name="username"/> <br>
    Password: <input type="password" name="password"/> <br>
    Age: <input type="number" min="0" name="age"/> <br>
    Gender: <select name="gender"/>
    <option value="male">male</option>
    <option value="female">female</option></select>
    <input type="submit" value="Login"/>
    <span class="error">${error}</span>
  </form>

  Users: </br>
  <%
    try {
      User[] users = LoginServlet.getUsers();
      for(User u : users){
        out.print(u.toString()+"<br>");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  %>

</head>
<body>

</body>
</html>