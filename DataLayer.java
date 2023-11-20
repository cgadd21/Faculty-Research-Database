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
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/project", "root", "student");
            return true;
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return false;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }

    // Closes all connections after checking if connection is true.
    public boolean close() {
        try {
            if (connection) {
                rs.close();
                stmt.close();
                conn.close();
                return true;
            } else {
                return true;
            }
        } catch (SQLException sqle) {
            System.out.println("ERROR IN METHOD close()");
            System.out.println("ERROR MESSAGE -> " + sqle);
            return false;
        }
    }

    // Return true if user has edit permissions, otherwise false. Determines if user
    // can log in.
    // include G
    public String login(String username, String password) {

        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet userResultSet = preparedStatement.executeQuery()) {
                if (userResultSet.next()) {
                    return userResultSet.getString("typeID");
                } else {
                    System.out.println("Login failed. No matching user in userlogin table.");
                    return null; // No matching user in userlogin table
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Login failed due to an error.");
            return null;
        }
    }

    public void createStudent(String firstName, String lastName, String email, String phoneNumber, String username,
            String password) {
        int userId = -1;
        try {
            conn.setAutoCommit(false);

            // Insert into users table
            String userInsertQuery = "INSERT INTO users (typeID, username, password) VALUES ('S', ?, ?)";
            try (PreparedStatement userInsertStmt = conn.prepareStatement(userInsertQuery,
                    Statement.RETURN_GENERATED_KEYS)) {
                userInsertStmt.setString(1, username);
                userInsertStmt.setString(2, password);
                userInsertStmt.executeUpdate();
                try (ResultSet generatedKeys = userInsertStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        userId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }

            // Insert into student table
            String studentInsertQuery = "INSERT INTO student (studentID, fname, lname, email, phonenumber) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement studentStmt = conn.prepareStatement(studentInsertQuery)) {
                studentStmt.setInt(1, userId);
                studentStmt.setString(2, firstName);
                studentStmt.setString(3, lastName);
                studentStmt.setString(4, email);
                studentStmt.setString(5, phoneNumber);
                studentStmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error creating student account.");
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void createFaculty(String firstName, String lastName, String email, String phoneNumber, String location,
            String username, String password) {
        int userId = -1;
        try {
            conn.setAutoCommit(false);

            // Insert into users table
            String userInsertQuery = "INSERT INTO users (typeID, username, password) VALUES ('F', ?, ?)";
            try (PreparedStatement userInsertStmt = conn.prepareStatement(userInsertQuery,
                    Statement.RETURN_GENERATED_KEYS)) {
                userInsertStmt.setString(1, username);
                userInsertStmt.setString(2, password);
                userInsertStmt.executeUpdate();
                try (ResultSet generatedKeys = userInsertStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        userId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }

            // Insert into faculty table
            String facultyInsertQuery = "INSERT INTO faculty (facultyID, fname, lname, email, phonenumber, location) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement facultyStmt = conn.prepareStatement(facultyInsertQuery)) {
                facultyStmt.setInt(1, userId);
                facultyStmt.setString(2, firstName);
                facultyStmt.setString(3, lastName);
                facultyStmt.setString(4, email);
                facultyStmt.setString(5, phoneNumber);
                facultyStmt.setString(6, location);
                facultyStmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error creating faculty account.");
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void createGuest(String business, String firstName, String lastName, String contactInfo, String username,
            String password) {
        int userId = -1;
        try {
            conn.setAutoCommit(false);

            // Insert into users table
            String userInsertQuery = "INSERT INTO users (typeID, username, password) VALUES ('G', ?, ?)";
            try (PreparedStatement userInsertStmt = conn.prepareStatement(userInsertQuery,
                    Statement.RETURN_GENERATED_KEYS)) {
                userInsertStmt.setString(1, username);
                userInsertStmt.setString(2, password);
                userInsertStmt.executeUpdate();
                try (ResultSet generatedKeys = userInsertStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        userId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }

            // Insert into guest table
            String guestInsertQuery = "INSERT INTO guest (guestID, business, fname, lname, contactinfo) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement guestStmt = conn.prepareStatement(guestInsertQuery)) {
                guestStmt.setInt(1, userId);
                guestStmt.setString(2, business);
                guestStmt.setString(3, firstName);
                guestStmt.setString(4, lastName);
                guestStmt.setString(5, contactInfo);
                guestStmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error creating guest account.");
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addFacultyAbstract(int facultyId, String professorAbstract) throws SQLException {
        try {
            // Insert abstract into abstractList table
            String abstractInsertQuery = "INSERT INTO abstractList (professorAbstract) VALUES (?)";
            int abstractId;

            try (PreparedStatement abstractStmt = conn.prepareStatement(abstractInsertQuery,
                    Statement.RETURN_GENERATED_KEYS)) {
                abstractStmt.setString(1, professorAbstract);
                abstractStmt.executeUpdate();

                try (ResultSet generatedKeys = abstractStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        abstractId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating abstract failed, no ID obtained.");
                    }
                }
            }

            // Link abstract to faculty in facultyAbstract table
            String facultyAbstractInsertQuery = "INSERT INTO facultyAbstract (facultyID, abstractID) VALUES (?, ?)";
            try (PreparedStatement facultyAbstractStmt = conn.prepareStatement(facultyAbstractInsertQuery)) {
                facultyAbstractStmt.setInt(1, facultyId);
                facultyAbstractStmt.setInt(2, abstractId);
                facultyAbstractStmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public void addStudentInterest(int studentId, String interest) throws SQLException {
        try {
            // Check if the interest already exists in interestList, if not insert it
            String interestCheckQuery = "SELECT interestID FROM interestList WHERE intDesc = ?";
            int interestId;

            try (PreparedStatement interestCheckStmt = conn.prepareStatement(interestCheckQuery)) {
                interestCheckStmt.setString(1, interest);
                ResultSet rs = interestCheckStmt.executeQuery();

                if (rs.next()) {
                    interestId = rs.getInt("interestID");
                } else {
                    // Interest doesn't exist, so insert it
                    String interestInsertQuery = "INSERT INTO interestList (intDesc) VALUES (?)";
                    try (PreparedStatement interestInsertStmt = conn.prepareStatement(interestInsertQuery,
                            Statement.RETURN_GENERATED_KEYS)) {
                        interestInsertStmt.setString(1, interest);
                        interestInsertStmt.executeUpdate();

                        try (ResultSet generatedKeys = interestInsertStmt.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                interestId = generatedKeys.getInt(1);
                            } else {
                                throw new SQLException("Creating interest failed, no ID obtained.");
                            }
                        }
                    }
                }
            }

            // Link interest to student in studentInterests table
            String studentInterestInsertQuery = "INSERT INTO studentInterests (studentID, interestID) VALUES (?, ?)";
            try (PreparedStatement studentInterestStmt = conn.prepareStatement(studentInterestInsertQuery)) {
                studentInterestStmt.setInt(1, studentId);
                studentInterestStmt.setInt(2, interestId);
                studentInterestStmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public void deleteUser(int userID){
        try {
            String query = "DELETE FROM users WHERE userID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();
            System.out.println("User Deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Delete User failed due to an error.");
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
            System.out.print("1. Login\n2. Sign Up\n3. Guest\n4. Delete User \n5. Exit\nSelection: ");
            choice = scanner.nextInt();
            System.out.println("");

            switch (choice) {
                case 1:
                    System.out.print("Login\nUsername: ");
                    username = scanner.next();
                    System.out.print("Password: ");
                    password = scanner.next();
                    try {
                        userType = dataLayer.login(username, password);
                        if (userType != null) {
                            postLoginMenu(dataLayer, scanner, userType);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR logging in");
                    }
                    break;

                case 2:
                    System.out.print("Are you a Student (1) or Faculty (2)? ");
                    int type = scanner.nextInt();
                    scanner.nextLine(); // consume the leftover newline

                    if (type == 1) {
                        // Collect student information
                        System.out.print("Enter first name: ");
                        String firstName = scanner.nextLine();
                        System.out.print("Enter last name: ");
                        String lastName = scanner.nextLine();
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter phone number: ");
                        String phoneNumber = scanner.nextLine();
                        System.out.print("Enter username: ");
                        String sUser = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String sPass = scanner.nextLine();
                        try {
                            dataLayer.createStudent(firstName, lastName, email, phoneNumber, sUser, sPass);
                            System.out.println("Student account created successfully.");
                        } catch (Exception e) {
                            System.out.println("An unexpected error occurred while creating the student account: "
                                    + e.getMessage());
                        }
                    } else if (type == 2) {
                        // Collect faculty information
                        System.out.print("Enter first name: ");
                        String firstName = scanner.nextLine();
                        System.out.print("Enter last name: ");
                        String lastName = scanner.nextLine();
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter phone number: ");
                        String phoneNumber = scanner.nextLine();
                        System.out.print("Enter location: ");
                        String location = scanner.nextLine();
                        System.out.print("Enter username: ");
                        String fUser = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String fPass = scanner.nextLine();
                        try {
                            dataLayer.createFaculty(firstName, lastName, email, phoneNumber, location, fUser, fPass);
                            System.out.println("Faculty account created successfully.");
                            try {
                                userType = dataLayer.login(fUser, fPass);
                            } catch (Exception e) {
                                System.out.println("ERROR logging in");
                            }
                        } catch (Exception e) {
                            System.out.println("An unexpected error occurred while creating the faculty account: "
                                    + e.getMessage());
                        }

                    } else {
                        System.out.println("Invalid option. Please select 1 for Student or 2 for Faculty.");
                    }
                    break;

                case 3:
               
                    break;

                case 4:
                    System.out.print("Enter User you would like to delete: ");
                    String UserID = scanner.next();
                    dataLayer.deleteUser(Integer.parseInt(UserID));
                    break;
                case 5:
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

    private static void postLoginMenu(DataLayer dataLayer, Scanner scanner, String userType) {
        System.out.println("1. Update User Information\n2. Add Abstract/Interest\n3. Logout");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                // Call method to update user information
                // You'll need to implement this
                break;
            case 2:
                if ("F".equals(userType)) {
                    // Add abstract for faculty
                    System.out.print("Enter your abstract: ");
                    String abstractText = scanner.nextLine();
                    // Call method to add faculty abstract
                    // e.g., dataLayer.addFacultyAbstract(facultyId, abstractText);
                } else if ("S".equals(userType)) {
                    // Add interest for student
                    System.out.print("Enter your interest: ");
                    String interest = scanner.nextLine();
                    // Call method to add student interest
                    // e.g., dataLayer.addStudentInterest(studentId, interest);
                }
                break;
            case 3:
                // Logout
                System.out.println("Logging out...");
                return;
            default:
                System.out.println("Invalid option");
                break;
        }
        // sample data
        // username = "jmd4173";
        // password = "StudentPass";
        // dataLayer.login(username, password);
        // username = "JimHab";
        // password = "FacultyPass";
        // dataLayer.login(username, password);
        // username = "Wegmans";
        // password = "GuestPass";
        // dataLayer.login(username, password);
        // dataLayer.createOrUpdateAccount("Faculty",4, "John", "Doe",
        // "john.doe@example.com", "1234567890", "New Location", "faculty123",
        // "password123");
        // // Example: Creating or updating a Student account
        // dataLayer.createOrUpdateAccount("Student", 5,"Jane", "Doe",
        // "jane.doe@example.com", "9876543210", null, "student123", "password456");

    }

}
