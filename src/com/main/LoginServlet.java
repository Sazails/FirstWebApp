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
        String gender = request.getParameter("gender");
        int age = Integer.parseInt(request.getParameter("age"));

        addUserToDatabase(new User(username, password, age, gender));
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    public static void addUserToDatabase(User user){
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
