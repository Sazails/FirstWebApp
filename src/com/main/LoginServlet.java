package com.main;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Utilities;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.attribute.UserPrincipal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int age = Integer.parseInt(request.getParameter("age"));
        String gender = request.getParameter("gender");

        User newUser = new User(0, username, password, age, gender);
        addUserToDatabase(newUser);

        String htmlResponse = "<html>";
        if(newUser != null){
            // Send to main page?
            System.out.println("No input error detected, user created.");
        }else{
            request.setAttribute("error", "Wrong login details, please try again.");
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);

        /*System.out.println("username: " + username);
        System.out.println("password: " + password);
        System.out.println("age: " + age);
        System.out.println("gender: " + gender);

        /*PrintWriter writer = response.getWriter();

        String htmlResponse = "<html>";
        htmlResponse += "<h2>Username: " + username + "<br/>";
        htmlResponse += "Password: " + password + "<br/>";
        htmlResponse += "Age: " + age + "<br/>";
        htmlResponse += "Gender: " + gender + "<br/>";
        htmlResponse += "</html>";

        writer.println(htmlResponse);*/
    }

    public static void addUserToDatabase(User user){
        //String SQL = "INSERT INTO userdata.table_users(id, username, password, age, gender) " + "VALUES(?,?,?,?,?)";
        String SQL = "INSERT INTO userdata.table_users(username, password, age, gender) " + "VALUES(?,?,?,?)";

        try{
            Connection con=getSQLConnection();
            Statement stmt=con.createStatement();
            PreparedStatement ps = con.prepareStatement(SQL,  Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getAge());
            ps.setString(4, user.getGender());

            ps.executeUpdate();

            ResultSet rs = ps.executeQuery("SELECT * from userdata.table_users");

            while(rs.next())
                System.out.println("ID: " + rs.getInt(1) + " USERNAME: " + rs.getString(2) + " PASSWORD: " + rs.getString(3) + " AGE: " + rs.getInt(4) + " GENDER: " + rs.getString(5));

            rs.close();
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    public static String[] getUsernames() throws SQLException {
        try{
            User[] users = getUsers();
            String[] names = new String[users.length];
            for(int u = 0; u < users.length; u++){
                names[u] = users[u].getUsername();
            }
            return names;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static User[] getUsers() throws SQLException {
        try{
            Connection con=getSQLConnection();
            Statement state = con.createStatement();
            ResultSet rs = state.executeQuery("SELECT * from userdata.table_users");

            ArrayList<User> tempUsers = new ArrayList<User>();
            while(rs.next()){
                tempUsers.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)));
            }

            User[] users = new User[tempUsers.size()];
            for(int x = 0; x < users.length; x++){
                users[x] = tempUsers.get(x);
            }
            return users;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    protected static Connection getSQLConnection() throws SQLException {
        try{
            // Link class
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","1321");
        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
