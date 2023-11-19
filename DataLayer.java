// Student name: Gabro, George
// Practical Exam 01
// 10-13-2023

// Java program that connects to a database (Using StudentDB)

import java.sql.*; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;     

public class DataLayer{

   // Attributes
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String sql;
    private boolean connection;
    private boolean editPerm; //Determines if user has edit permissions.
    public int col;


   //Sets up default driver and basis for the SQL database
	final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";    
    static String url = "jdbc:mysql://localhost/";
    
   //Connect to db, Takes username password and databasename
    public boolean connect(String user, String password, String database){
      //Nulls connection to avoid any issues
      //Sets up path to db
    url = url + database;
      //trys Connection
    try {
        Class.forName(DEFAULT_DRIVER);                                                             
        conn = DriverManager.getConnection(url, user, password);   
        System.out.println("\nCreated Connection! - Gabro, George\n");
        connection = true;
        
		} // end of try      
	catch(ClassNotFoundException cnfe) {
		System.out.print("ERROR IN CLASS, CONNECTION FAILED \n" + cnfe);
        connection = false;
    }catch(SQLException se) {
		System.out.print("ERROR SQLException, CONNECTION FAILED \n" + se);
        connection = false;
		}//end of catch
    
    return connection;
    }

   //Closes all connections after checking if connection is true.
    public boolean close(){
        try {
        if (connection) {
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("SQL Connection Closed");
            return true;
        }else{
            System.out.println("SQL Connection was already closed");
            return true;
        }
    }
    catch(SQLException sqle){
        System.out.println("ERROR IN METHOD close()");
        System.out.println("ERROR MESSAGE -> "+sqle);
        return false;
    }
    }

    //Return true if user has edit permissions, otherwise false. Determines if user can log in.
    private boolean login(String username, String password) {
        
        try {
            String query = "SELECT * FROM userlogin WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet userIdResultSet = preparedStatement.executeQuery()) {
                if (userIdResultSet.next()) {
                    int userId = userIdResultSet.getInt("userID");

                    // Second query to get type_id based on user_id
                    String userTypeQuery = "SELECT typeID FROM users WHERE userID = ?";
                    try (PreparedStatement userTypeStatement = conn.prepareStatement(userTypeQuery)) {
                        userTypeStatement.setInt(1, userId);

                        try (ResultSet userTypeResultSet = userTypeStatement.executeQuery()) {
                            if (userTypeResultSet.next()) {
                                String userType = userTypeResultSet.getString("typeID");
                                if ("F".equals(userType)) {
                                    System.out.println("Login successful! User is faculty.");
                                    return true; // User is faculty
                                } else if("S".equals(userType)){
                                    System.out.println("Login successful! User is student.");
                                    return false; // User is not faculty
                                } else{
                                    System.out.println("Login successful! User is guest.");
                                    return false; // User is not faculty
                                }
                            } else {
                                System.out.println("Login failed. No matching user in faculty table.");
                                return false; // No matching user in faculty table
                            }
                        }
                    }
                } else {
                    System.out.println("Login failed. No matching user in userlogin table.");
                    return false; // No matching user in userlogin table
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Login failed due to an error.");
            return false;
        }
    }

    public static void main(String[] args) {
        DataLayer dataLayer = new DataLayer();
        String username = "root";
        String password = "student";
        String database = "project";

        boolean isConnected = dataLayer.connect(username, password, database);

        username = "jmd4173";
        password = "StudentPass";

        dataLayer.login(username, password);

        username = "JimHab";
        password = "FacultyPass";

        dataLayer.login(username, password);

        username = "Wegmans";
        password = "GuestPass";

        dataLayer.login(username, password);

    }

}