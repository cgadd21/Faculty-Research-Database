// login, sign up
// login - user and pass return user type F,S,G
// sign up - student/faculty/guest create new account
//First input type values
//Login and password
//Specific values of each table
// 3 methods CreateUser/ 3 for EditUser/ ? amount for DeleteUser overload based on F,S,G.
/*
 * INSERT INTO users (typeID) VALUES ('F'); SELECT * FROM users ORDER BY userID DESC LIMIT 1; - how to get the userID -- only increment
 * INSERT INTO ALL OTHER TABLES WITH THIS userID
 */
// search interest/abstract depending on usertype 
// edit account
// delete account
// class for each table and return object

import java.sql.*;
import java.util.Scanner;

public class DataLayer {

    // Attributes
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private boolean connection;
    public int col;

    // Connect to db
    public boolean connect() {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/project", "root", "student");
            return true;
        }
        catch(ClassNotFoundException cnfe)
        {
            return false;
        }
        catch(SQLException sqle)
        {
            return false;
        }
    }

    // Closes all connections after checking if connection is true.
    public boolean close() 
    {
        try 
        {
            if (connection) 
            {
                rs.close();
                stmt.close();
                conn.close();
                return true;
            } 
            else 
            {
                return true;
            }
        } 
        catch (SQLException sqle) 
        {
            System.out.println("ERROR IN METHOD close()");
            System.out.println("ERROR MESSAGE -> " + sqle);
            return false;
        }
    }

    // Returns the user type if user exists else null
    public String login(String username, String password)
    {
        try
        {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet userResultSet = preparedStatement.executeQuery())
            {
                if (userResultSet.next()) 
                {
                    return userResultSet.getString("typeID");
                } 
                else
                {
                    System.out.println("Login failed. No matching user in userlogin table.");
                    return null; // No matching user in userlogin table
                }
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            System.out.println("Login failed due to an error.");
            return null;
        }
    }

    public void createOrUpdateAccount(String userType, int id, String firstName, String lastName, String email,
            String phoneNumber, String location, String username, String password) {
        try {
            String query;

            if ("Faculty".equalsIgnoreCase(userType)) {
                // Create or update Faculty account
                query = "INSERT INTO faculty (facultyid, fname, lname) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE fname = ?, lname = ?";

                try (PreparedStatement facultyStatement = conn.prepareStatement(query)) {
                    facultyStatement.setInt(1, id);
                    facultyStatement.setString(2, firstName);
                    facultyStatement.setString(3, lastName);

                    // Set parameters for ON DUPLICATE KEY UPDATE clause
                    facultyStatement.setString(4, firstName);
                    facultyStatement.setString(5, lastName);

                    facultyStatement.executeUpdate();
                }

                // Create or update Faculty contact information
                query = "INSERT INTO facultycontact (facultyid, email, phonenumber, location) " +
                        "VALUES (?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE email = ?, phonenumber = ?, location = ?";
            } else if ("Student".equalsIgnoreCase(userType)) {
                // Create or update Student account
                query = "INSERT INTO student (studentid, fname, lname) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE fname = ?, lname = ?";

                try (PreparedStatement studentStatement = conn.prepareStatement(query)) {
                    studentStatement.setInt(1, id);
                    studentStatement.setString(2, firstName);
                    studentStatement.setString(3, lastName);

                    // Set parameters for ON DUPLICATE KEY UPDATE clause
                    studentStatement.setString(4, firstName);
                    studentStatement.setString(5, lastName);

                    studentStatement.executeUpdate();
                }

                // Create or update Student contact information
                query = "INSERT INTO studentcontact (studentid, email, phonenumber) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE email = ?, phonenumber = ?";
            } else {
                System.out.println("Invalid user type.");
                return;
            }

            try (PreparedStatement contactStatement = conn.prepareStatement(query)) {
                contactStatement.setInt(1, id);

                if ("Faculty".equalsIgnoreCase(userType)) {
                    contactStatement.setString(2, email);
                    contactStatement.setString(3, phoneNumber);
                    contactStatement.setString(4, location);

                    // Set parameters for ON DUPLICATE KEY UPDATE clause
                    contactStatement.setString(5, email);
                    contactStatement.setString(6, phoneNumber);
                    contactStatement.setString(7, location);
                } else if ("Student".equalsIgnoreCase(userType)) {
                    contactStatement.setString(2, email);
                    contactStatement.setString(3, phoneNumber);

                    // Set parameters for ON DUPLICATE KEY UPDATE clause
                    contactStatement.setString(4, email);
                    contactStatement.setString(5, phoneNumber);
                }

                contactStatement.executeUpdate();

                // Create or update User Login
                query = "INSERT INTO userlogin (id, username, password) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE password = ?";

                try (PreparedStatement loginStatement = conn.prepareStatement(query)) {
                    loginStatement.setInt(1, id);
                    loginStatement.setString(2, username);
                    loginStatement.setString(3, password);

                    // Set parameter for ON DUPLICATE KEY UPDATE clause
                    loginStatement.setString(4, password);

                    loginStatement.executeUpdate();

                    System.out.println("Account created/updated successfully.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error creating/updating account.");
        }
    }

    public static void main(String[] args) {
        DataLayer dataLayer = new DataLayer();
        String username;
        String password;
        boolean isConnected = dataLayer.connect();
        Scanner scanner = new Scanner(System.in);
        int choice;
        String userType;

        if (isConnected) {
            System.out.print("1. Login\n2. Sign Up\n3. Guest\n4. Exit\nSelection: ");
            choice = scanner.nextInt();
            System.out.println("");

            switch (choice) 
            {
                case 1:
                    System.out.print("Login\nUsername: ");
                    username = scanner.next();
                    System.out.print("Password: ");
                    password = scanner.next();
                    try 
                    {
                        userType = dataLayer.login(username, password);
                    } 
                    catch (Exception e) 
                    {
                        System.out.println("ERROR logging in");
                    }
                    break;

                case 2:

                    break;

                case 3:

                    break;

                case 4:
                    System.out.println("Goodbye!\n");
                    scanner.close();
                    dataLayer.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Please enter a valid option");
                    break;
            }
        }
    }

}
