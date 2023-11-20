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
import java.io.*;
import javax.swing.*;

public class DataLayer {

    // Attributes
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private boolean connection;
    public int col;

    // Sets up default driver and basis for the SQL database
    final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";
    static String url = "jdbc:mysql://localhost/";

    // Connect to db, Takes username password and databasename
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
    public int getNextAbstractId() throws SQLException {
        int nextId = 1; // Default starting value
        String query = "SELECT MAX(abstractID) as maxId FROM abstractList";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                nextId = rs.getInt("maxId") + 1;
            }
        } catch (SQLException e) {
            throw e;
        }

        return nextId;
    }

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

    public void displayCommonInterestsForFaculty(int facultyID) {
        try {
            String query = "SELECT " +
                    "CONCAT(faculty.fname, ' ', faculty.lname) AS 'Faculty Name', " +
                    "CONCAT(student.fname, ' ', student.lname) AS 'Student Name', " +
                    "GROUP_CONCAT(Interestlist.intDesc) AS 'Common Interests' " +
                    "FROM facultyinterests " +
                    "LEFT JOIN studentinterests ON facultyinterests.interestID = studentinterests.interestID " +
                    "LEFT JOIN faculty ON facultyinterests.facultyID = faculty.facultyID " +
                    "RIGHT JOIN Interestlist ON studentinterests.interestID = Interestlist.interestID " +
                    "RIGHT JOIN student ON studentinterests.studentID = student.studentID " +
                    "WHERE facultyinterests.facultyID = ? " +
                    "GROUP BY facultyinterests.facultyID, studentinterests.studentID";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                // Set the facultyID parameter in the query
                statement.setInt(1, facultyID);
                // Execute the query
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Display the result
                    while (resultSet.next()) {
                        String facultyName = resultSet.getString("Faculty Name");
                        String studentName = resultSet.getString("Student Name");
                        String commonInterests = resultSet.getString("Common Interests");
                        System.out.println("Faculty Name: " + facultyName);
                        System.out.println("Student Name: " + studentName);
                        System.out.println("Common Interests: " + commonInterests);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error displaying common interests for Faculty.");
        }
    }

    public void displayCommonInterestsForStudents(int studentID) {
        try {
            String query = "SELECT " +
                    "CONCAT(faculty.fname, ' ', faculty.lname) AS 'Faculty Name', " +
                    "GROUP_CONCAT(DISTINCT Interestlist.intDesc) AS 'Matching Interests' " +
                    "FROM studentinterests " +
                    "JOIN facultyinterests ON studentinterests.interestID = facultyinterests.interestID " +
                    "JOIN faculty ON facultyinterests.facultyID = faculty.facultyID " +
                    "JOIN Interestlist ON facultyinterests.interestID = Interestlist.interestID " +
                    "WHERE studentinterests.studentID = ? " +
                    "GROUP BY faculty.facultyID";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, studentID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String facultyName = resultSet.getString("Faculty Name");
                        String matchingInterests = resultSet.getString("Matching Interests");
                        System.out.println("Faculty Name: " + facultyName);
                        System.out.println("Matching Interests: " + matchingInterests);
                        System.out.println();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error displaying faculty for student interests.");
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

    public int getUserIdByUsername(String username) throws SQLException {
        int userId = -1;
        String query = "SELECT userID FROM users WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("userID");
                } else {
                    System.out.println("No user found with username: " + username);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while fetching user ID: " + e.getMessage());
            throw e;
        }

        return userId;
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

    public void addFacultyAbstract(int facultyId) throws SQLException, IOException {
        // Get the next available ID for the abstract
        int abstractId = getNextAbstractId();

        // Open a file chooser dialog to select the abstract file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an Abstract File");

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            StringBuilder abstractContent = new StringBuilder();

            // Read the contents of the selected file
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    abstractContent.append(line).append("\n");
                }
            } catch (IOException e) {
                throw e; // Re-throw the exception to be handled by the caller
            }

            String professorAbstract = abstractContent.toString();

            // Insert the abstract into the abstractList table
            String abstractInsertQuery = "INSERT INTO abstractList (abstractID, professorAbstract) VALUES (?, ?)";
            try (PreparedStatement abstractStmt = conn.prepareStatement(abstractInsertQuery)) {
                abstractStmt.setInt(1, abstractId);
                abstractStmt.setString(2, professorAbstract);
                abstractStmt.executeUpdate();
            }

            // Link the abstract to the faculty in the facultyAbstract table
            String facultyAbstractInsertQuery = "INSERT INTO facultyAbstract (facultyID, abstractID) VALUES (?, ?)";
            try (PreparedStatement facultyAbstractStmt = conn.prepareStatement(facultyAbstractInsertQuery)) {
                facultyAbstractStmt.setInt(1, facultyId);
                facultyAbstractStmt.setInt(2, abstractId);
                facultyAbstractStmt.executeUpdate();
            }
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

    public void updateUser(int userId, String newUsername, String newPassword) throws SQLException {
        String updateQuery = "UPDATE users SET username = ?, password = ? WHERE userID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, newUsername);
            stmt.setString(2, newPassword);
            stmt.setInt(3, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void updateFaculty(int facultyId, String newFirstName, String newLastName, String newEmail,
            String newPhoneNumber, String newLocation, String newUsername, String newPassword) throws SQLException {
        updateUser(facultyId, newUsername, newPassword); // Update common attributes

        String updateFacultyQuery = "UPDATE faculty SET fname = ?, lname = ?, email = ?, phonenumber = ?, location = ? WHERE facultyID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateFacultyQuery)) {
            stmt.setString(1, newFirstName);
            stmt.setString(2, newLastName);
            stmt.setString(3, newEmail);
            stmt.setString(4, newPhoneNumber);
            stmt.setString(5, newLocation);
            stmt.setInt(6, facultyId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void updateStudent(int studentId, String newFirstName, String newLastName, String newEmail,
            String newPhoneNumber, String newUsername, String newPassword) throws SQLException {
        updateUser(studentId, newUsername, newPassword); // Update common attributes

        String updateStudentQuery = "UPDATE student SET fname = ?, lname = ?, email = ?, phonenumber = ? WHERE studentID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateStudentQuery)) {
            stmt.setString(1, newFirstName);
            stmt.setString(2, newLastName);
            stmt.setString(3, newEmail);
            stmt.setString(4, newPhoneNumber);
            stmt.setInt(5, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void updateGuest(int guestId, String newBusiness, String newFirstName, String newLastName,
            String newContactInfo, String newUsername, String newPassword) throws SQLException {
        updateUser(guestId, newUsername, newPassword);
        String updateGuestQuery = "UPDATE guest SET business = ?, fname = ?, lname = ?, contactinfo = ? WHERE guestID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateGuestQuery)) {
            stmt.setString(1, newBusiness);
            stmt.setString(2, newFirstName);
            stmt.setString(3, newLastName);
            stmt.setString(4, newContactInfo);
            stmt.setInt(5, guestId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e; // Handle the exception as needed
        }
    }

    public void addInterests(int userId, String[] interestIds, String userType) {
        try {
            int interestsAdded = 0;
            String idColumn = "studentID"; // Default column name
            String interestTable = "studentInterests"; // Default interest table

            if ("F".equals(userType)) {
                idColumn = "facultyID";
                interestTable = "facultyInterests";
            }

            for (String interestIdStr : interestIds) {
                int interestId = Integer.parseInt(interestIdStr.trim());

                String insertInterestQuery = "INSERT INTO " + interestTable + " (" + idColumn
                        + ", interestID) VALUES (?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(insertInterestQuery)) {
                    ps.setInt(1, userId);
                    ps.setInt(2, interestId);
                    ps.executeUpdate();
                    interestsAdded++;
                }
            }

            System.out.println(interestsAdded + " interests added.");
        } catch (Exception e) {
            System.out.println("ERROR adding interests: " + e.getMessage());
        }
    }

    public void displayAllInterests() {
        try {
            String query = "SELECT interestID, intDesc FROM interestList";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                System.out.println("Available Interests:");
                while (rs.next()) {
                    int id = rs.getInt("interestID");
                    String desc = rs.getString("intDesc");
                    System.out.println(id + ": " + desc);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error displaying interests: " + e.getMessage());
        }
    }

    public void deleteUser(int userID) {
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
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;
        boolean isConnected = dataLayer.connect();
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
                        int userId = dataLayer.getUserIdByUsername(username);
                        if (userType != null) {
                            postLoginMenu(dataLayer, scanner, userType, userId);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR logging in");
                    }
                    break;

                case 2:
                    System.out.print("Are you a Student (1) or Faculty (2)?");
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
                        try {
                            int userId = dataLayer.getUserIdByUsername(sUser);
                            userType = dataLayer.login(sUser, sPass);
                            postLoginMenu(dataLayer, scanner, userType, userId);
                        } catch (Exception e) {
                            System.out.println("ERROR logging in");
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
                            System.out.print("Would you like to add an abstract now? (yes/no): ");
                            String response = scanner.nextLine();
                            if ("yes".equalsIgnoreCase(response)) {
                                // Assuming you have a method to retrieve the newly created faculty ID
                                int facultyId = dataLayer.getUserIdByUsername(fUser);
                                dataLayer.addFacultyAbstract(facultyId);
                                System.out.println("Abstract added successfully.");
                            }

                            try {
                                int userId = dataLayer.getUserIdByUsername(fUser);
                                userType = dataLayer.login(fUser, fPass);
                                postLoginMenu(dataLayer, scanner, userType, userId);
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

                    System.out.print("(1) Login to a guest account\n(2) Create a new guest account\nSelection:");
                    int choose = scanner.nextInt();
                    scanner.nextLine();
                    switch (choose) {
                        case 1:
                            System.out.print("Login\nUsername: ");
                            username = scanner.next();
                            System.out.print("Password: ");
                            password = scanner.next();
                            try {
                                userType = dataLayer.login(username, password);
                                int userId = dataLayer.getUserIdByUsername(username);
                                if (userType != null) {
                                    postLoginMenu(dataLayer, scanner, userType, userId);
                                }
                            } catch (Exception e) {
                                System.out.println("ERROR logging in");
                            }
                            break;
                        case 2:
                            System.out.print("Enter business: ");
                            String business = scanner.nextLine();
                            System.out.print("Enter first name: ");
                            String firstName = scanner.nextLine();
                            System.out.print("Enter last name: ");
                            String lastName = scanner.nextLine();
                            System.out.print("Enter contact info: ");
                            String contactInfo = scanner.nextLine();
                            System.out.print("Enter username: ");
                            String gUser = scanner.nextLine();
                            System.out.print("Enter password: ");
                            String gPass = scanner.nextLine();
                            dataLayer.createGuest(business, firstName, lastName, contactInfo, gUser, gPass);
                            try {
                                userType = dataLayer.login(gUser, gPass);
                                int userId = dataLayer.getUserIdByUsername(gUser);
                                if (userType != null) {
                                    postLoginMenu(dataLayer, scanner, userType, userId);
                                }
                            } catch (Exception e) {
                                System.out.println("ERROR logging in");
                            }
                            break;
                        default:
                            System.out.println("Invalid option");
                            break;
                    }

                    break;

                case 4:
                    System.out.print("Enter User you would like to delete: ");
                    String UserID = scanner.next();
                    dataLayer.deleteUser(Integer.parseInt(UserID));
                    break;
                case 5:
                    System.out.println("Goodbye!\n");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Please enter a valid option");
                    break;
            }
        }
    }

    private static void postLoginMenu(DataLayer dataLayer, Scanner scanner, String userType, int userId) {
        while (true) {
            System.out.println("1. Update User Information\n2. Add Interest\n3. Delete user\n4. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Update User Information
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    try {
                        if ("F".equals(userType)) {
                            // Collect additional faculty information
                            System.out.print("Enter new first name: ");
                            String newFirstName = scanner.nextLine();
                            System.out.print("Enter new last name: ");
                            String newLastName = scanner.nextLine();
                            System.out.print("Enter new email: ");
                            String newEmail = scanner.nextLine();
                            System.out.print("Enter new phone number: ");
                            String newPhoneNumber = scanner.nextLine();
                            System.out.print("Enter new location: ");
                            String newLocation = scanner.nextLine();

                            dataLayer.updateFaculty(userId, newFirstName, newLastName, newEmail, newPhoneNumber,
                                    newLocation, newUsername, newPassword);
                        } else if ("S".equals(userType)) {
                            System.out.print("Enter new first name: ");
                            String newFirstName = scanner.nextLine();
                            System.out.print("Enter new last name: ");
                            String newLastName = scanner.nextLine();
                            System.out.print("Enter new email: ");
                            String newEmail = scanner.nextLine();
                            System.out.print("Enter new phone number: ");
                            String newPhoneNumber = scanner.nextLine();
                            dataLayer.updateStudent(userId, newFirstName, newLastName, newEmail, newPhoneNumber,
                                    newUsername, newPassword);

                        } else if ("G".equals(userType)) {
                            System.out.print("Enter new business: ");
                            String newBusiness = scanner.nextLine();
                            System.out.print("Enter new first name: ");
                            String newFirstName = scanner.nextLine();
                            System.out.print("Enter new last name: ");
                            String newLastName = scanner.nextLine();
                            System.out.print("Enter new contact info: ");
                            String newContactInfo = scanner.nextLine();

                            dataLayer.updateGuest(userId, newBusiness, newFirstName, newLastName, newContactInfo,
                                    newUsername, newPassword);
                        }
                        System.out.println("User information updated successfully.");
                    } catch (Exception e) {
                        System.out.println("Error updating user information: " + e.getMessage());
                    }
                    break;
                case 2:
                    // Add Interest
                    dataLayer.displayAllInterests();
                    System.out.println("Enter Interest IDs (comma-separated): ");
                    String interestInput = scanner.nextLine();
                    String[] interestIds = interestInput.split(",");
                    try {
                        dataLayer.addInterests(userId, interestIds, userType);
                        System.out.println("Interests added successfully.");
                    } catch (Exception e) {
                        System.out.println("Error adding interests: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("Are you sure you want to delete your account? (yes/no)");
                    String conf = scanner.next();
                    if ("yes".equals(conf)) {
                        dataLayer.deleteUser(userId);
                    }
                    System.out.println("Logging out...");
                    return;
                case 4:
                    // Logout
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
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
