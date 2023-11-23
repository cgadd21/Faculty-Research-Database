import java.sql.*;
import java.util.*;

public class Backend 
{
    private Connection conn;

    public boolean connect() 
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/project", "root", "student");
            return true;
        } 
        catch (ClassNotFoundException cnfe) 
        {
            return false;
        } 
        catch (SQLException sqle) 
        {
            return false;
        }
    }

    public boolean close() 
    {
        try 
        {
            if (conn != null) conn.close();
            return true;
        } 
        catch (SQLException sqle) 
        {
            return false;
        }
    }

    public User login(String username, String password)
    {
        try 
        {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            //encrpyt password
            preparedStatement.setString(2, password);

            try (ResultSet userResultSet = preparedStatement.executeQuery()) 
            {
                if (userResultSet.next()) 
                {
                    User user = new User
                    (
                        userResultSet.getInt("userID"),
                        userResultSet.getString("typeID"),
                        userResultSet.getString("username"),
                        userResultSet.getString("password")
                    );
                    
                    return getUser(user);
                } 
                else 
                {
                    return null;
                }
            }
        } 
        catch (SQLException e) 
        {
            return null;
        }
    }

    public User getUser(User user)
    {
        if (user.getTypeID().equals("F")) 
        {
            try 
            {
                String query = "SELECT * FROM users JOIN faculty ON users.userID = faculty.facultyID LEFT JOIN facultyInterests ON faculty.facultyID = facultyInterests.facultyID LEFT JOIN interestList ON facultyInterests.interestID = interestList.interestID LEFT JOIN facultyAbstract ON faculty.facultyID = facultyAbstract.facultyID LEFT JOIN abstractList ON facultyAbstract.abstractID = abstractList.abstractID WHERE users.userID = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, user.getUserID());
                ResultSet facultyResultSet = preparedStatement.executeQuery();
        
                if (facultyResultSet.next()) 
                {
                    Faculty facultyUser = new Faculty
                    (
                        facultyResultSet.getInt("userID"),
                        facultyResultSet.getString("typeID"),
                        facultyResultSet.getString("username"),
                        facultyResultSet.getString("password"),
                        facultyResultSet.getInt("facultyID"),
                        facultyResultSet.getString("fname"),
                        facultyResultSet.getString("lname"),
                        facultyResultSet.getString("email"),
                        facultyResultSet.getString("phoneNumber"),
                        facultyResultSet.getString("location")
                    );
    
                    List<Interest> interests = new ArrayList<>();
                    List<Abstract> abstracts = new ArrayList<>();

                    while (facultyResultSet.next()) 
                    {
                        Interest interest = new Interest
                        (
                            facultyResultSet.getInt("interestID"),
                            facultyResultSet.getString("intDesc")
                        );
                        if(!interests.contains(interest)) interests.add(interest);

                        Abstract facultyAbstract = new Abstract
                        (
                            facultyResultSet.getInt("abstractID"),
                            facultyResultSet.getString("professorAbstract")
                        );
                        if(!abstracts.contains(facultyAbstract)) abstracts.add(facultyAbstract);
                    }
    
                    facultyUser.setInterests(interests);
                    facultyUser.setAbstracts(abstracts);
    
                    return facultyUser;
                } 
                else 
                {
                    return null;
                }
            } 
            catch (SQLException e) 
            {
                return null;
            }
        }        
        else if (user.getTypeID().equals("S")) 
        {
            try 
            {
                String query = "SELECT * FROM users JOIN student ON users.userID = student.studentID LEFT JOIN studentInterests ON student.studentID = studentInterests.studentID LEFT JOIN interestList ON studentInterests.interestID = interestList.interestID WHERE users.userID = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, user.getUserID());
                ResultSet studentResultSet = preparedStatement.executeQuery();

                if (studentResultSet.next()) 
                {
                    Student studentUser = new Student
                    (
                        studentResultSet.getInt("userID"),
                        studentResultSet.getString("typeID"),
                        studentResultSet.getString("username"),
                        studentResultSet.getString("password"),
                        studentResultSet.getInt("studentID"),
                        studentResultSet.getString("fname"),
                        studentResultSet.getString("lname"),
                        studentResultSet.getString("email"),
                        studentResultSet.getString("phonenumber")
                    );

                    List<Interest> interests = new ArrayList<>();
                    while (studentResultSet.next()) 
                    {
                        Interest interest = new Interest
                        (
                            studentResultSet.getInt("interestID"),
                            studentResultSet.getString("intDesc")
                        );
                        if(!interests.contains(interest)) interests.add(interest);
                    }

                    studentUser.setInterests(interests);

                    return studentUser;
                } 
                else 
                {
                    return null;
                }
            } 
            catch (SQLException e) 
            {
                return null;
            }
        }
        else if(user.getTypeID().equals("G"))
        {
            try 
            {
                String query = "SELECT * FROM users JOIN guest ON users.userID = guest.guestID WHERE users.userID = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, user.getUserID());
                ResultSet guestResultSet = preparedStatement.executeQuery();

                if (guestResultSet.next()) 
                {
                    Guest guestUser = new Guest
                    (
                        guestResultSet.getInt("userID"),
                        guestResultSet.getString("typeID"),
                        guestResultSet.getString("username"),
                        guestResultSet.getString("password"),
                        guestResultSet.getInt("guestID"),
                        guestResultSet.getString("business"),
                        guestResultSet.getString("fname"),
                        guestResultSet.getString("lname"),
                        guestResultSet.getString("contactinfo")
                    );

                    return guestUser;
                } 
                else 
                {
                    return null;
                }
            } 
            catch (SQLException e) 
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    public User updateUser(User user) 
    {
        try 
        {
            String queryUser = "UPDATE users SET username = ?, password = ? WHERE userID = ?";
            PreparedStatement stmtUser = conn.prepareStatement(queryUser);
            stmtUser.setString(1, user.getUsername());
            stmtUser.setString(2, user.getPassword());
            stmtUser.setInt(3, user.getUserID());
            stmtUser.executeUpdate();

            if (user.getTypeID().equals("F")) 
            {
                Faculty facultyUser = (Faculty) user;
                String updateFacultyQuery = "UPDATE faculty SET fname = ?, lname = ?, email = ?, phonenumber = ?, location = ? WHERE facultyID = ?";
                PreparedStatement updateFacultyStmt = conn.prepareStatement(updateFacultyQuery);
                updateFacultyStmt.setString(1, facultyUser.getFname());
                updateFacultyStmt.setString(2, facultyUser.getLname());
                updateFacultyStmt.setString(3, facultyUser.getEmail());
                updateFacultyStmt.setString(4, facultyUser.getPhoneNumber());
                updateFacultyStmt.setString(5, facultyUser.getLocation());
                updateFacultyStmt.setInt(6, facultyUser.getFacultyID());
                updateFacultyStmt.executeUpdate();

                String deleteInterestsQuery = "DELETE FROM facultyInterests WHERE facultyID = ?";
                PreparedStatement deleteInterestsStmt = conn.prepareStatement(deleteInterestsQuery);
                deleteInterestsStmt.setInt(1, facultyUser.getFacultyID());
                deleteInterestsStmt.executeUpdate();

                String insertInterestsQuery = "INSERT INTO facultyInterests (facultyID, interestID) VALUES (?, ?)";
                PreparedStatement insertInterestsStmt = conn.prepareStatement(insertInterestsQuery);
                for (Interest interest : facultyUser.getInterests()) 
                {
                    insertInterestsStmt.setInt(1, facultyUser.getFacultyID());
                    insertInterestsStmt.setInt(2, interest.getInterestID());
                    insertInterestsStmt.executeUpdate();
                }

                String updateAbstractQuery = "Update abstractList SET professorAbstract = ? WHERE abstractID = ?";
                PreparedStatement updateAbstractStmt = conn.prepareStatement(updateAbstractQuery);
                for (Abstract abstract1 : facultyUser.getAbstracts()) 
                {
                    updateAbstractStmt.setString(1, abstract1.getProfessorAbstract());
                    updateAbstractStmt.setInt(2, abstract1.getAbstractID());
                    updateAbstractStmt.executeUpdate();
                }
            } 
            else if (user.getTypeID().equals("S")) 
            {
                Student studentUser = (Student) user;
                String updateStudentQuery = "UPDATE student SET fname = ?, lname = ?, email = ?, phonenumber = ? WHERE studentID = ?";
                PreparedStatement updateStudentStmt = conn.prepareStatement(updateStudentQuery);
                updateStudentStmt.setString(1, studentUser.getFname());
                updateStudentStmt.setString(2, studentUser.getLname());
                updateStudentStmt.setString(3, studentUser.getEmail());
                updateStudentStmt.setString(4, studentUser.getPhoneNumber());
                updateStudentStmt.setInt(5, studentUser.getStudentID());
                updateStudentStmt.executeUpdate();

                String deleteInterestsQuery = "DELETE FROM studentinterests WHERE studentID = ?";
                PreparedStatement deleteInterestsStmt = conn.prepareStatement(deleteInterestsQuery);
                deleteInterestsStmt.setInt(1, studentUser.getStudentID());
                deleteInterestsStmt.executeUpdate();

                String insertInterestsQuery = "INSERT INTO studentinterests (studentID, interestID) VALUES (?, ?)";
                PreparedStatement insertInterestsStmt = conn.prepareStatement(insertInterestsQuery);
                for (Interest interest : studentUser.getInterests()) 
                {
                    insertInterestsStmt.setInt(1, studentUser.getStudentID());
                    insertInterestsStmt.setInt(2, interest.getInterestID());
                    insertInterestsStmt.executeUpdate();
                }
            } 
            else if (user.getTypeID().equals("G")) 
            {
                Guest guestUser = (Guest) user;
                String query = "UPDATE guest SET business = ?, fname = ?, lname = ?, contactinfo = ? WHERE guestID = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, guestUser.getBusiness());
                stmt.setString(2, guestUser.getFname());
                stmt.setString(3, guestUser.getLname());
                stmt.setString(4, guestUser.getContactInfo());
                stmt.setInt(5, guestUser.getGuestID());
                stmt.executeUpdate();
            }
            else 
            {
                return null;
            }
            return getUser(user);
        } 
        catch (SQLException e) 
        {
            return null;
        }
    }
    
    public User createUser(User user)
    {
        try
        {
            String queryUser = "INSERT INTO users (typeID, username, password) VALUES (?, ?, ?)";
            PreparedStatement stmtUser = conn.prepareStatement(queryUser, Statement.RETURN_GENERATED_KEYS);
            stmtUser.setString(1, user.getTypeID());
            stmtUser.setString(2, user.getUsername());
            stmtUser.setString(3, user.getPassword());
            stmtUser.executeUpdate();
            ResultSet generatedKeys = stmtUser.getGeneratedKeys();
            if(generatedKeys.next()) user.setUserID(generatedKeys.getInt(1));

            if (user.getTypeID().equals("F"))
            {
                Faculty facultyUser = (Faculty) user;
                String query = "INSERT INTO faculty (facultyID, fname, lname, email, phonenumber, location) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, facultyUser.getUserID());
                stmt.setString(2, facultyUser.getFname());
                stmt.setString(3, facultyUser.getLname());
                stmt.setString(4, facultyUser.getEmail());
                stmt.setString(5, facultyUser.getPhoneNumber());
                stmt.setString(6, facultyUser.getLocation());
                stmt.executeUpdate();

                String insertInterestsQuery = "INSERT INTO facultyInterests (facultyID, interestID) VALUES (?, ?)";
                PreparedStatement insertInterestsStmt = conn.prepareStatement(insertInterestsQuery);
                for (Interest interest : facultyUser.getInterests()) 
                {
                    insertInterestsStmt.setInt(1, facultyUser.getFacultyID());
                    insertInterestsStmt.setInt(2, interest.getInterestID());
                    insertInterestsStmt.executeUpdate();
                }

                String insertAbstractsQuery = "INSERT INTO abstractList (abstractID, professorAbstract) VALUES (?, ?)";
                PreparedStatement insertAbstractsStmt = conn.prepareStatement(insertAbstractsQuery, Statement.RETURN_GENERATED_KEYS);
                for (Abstract facultyAbstract : facultyUser.getAbstracts()) 
                {
                    insertAbstractsStmt.setInt(1, facultyAbstract.getAbstractID());
                    insertAbstractsStmt.setString(2, facultyAbstract.getProfessorAbstract());
                    insertAbstractsStmt.executeUpdate();

                    ResultSet abstractGeneratedKeys = insertAbstractsStmt.getGeneratedKeys();
                    if (generatedKeys.next()) 
                    {
                        int abstractID = abstractGeneratedKeys.getInt(1);
                        String associateAbstractQuery = "INSERT INTO facultyAbstract (facultyID, abstractID) VALUES (?, ?)";
                        PreparedStatement associateAbstractStmt = conn.prepareStatement(associateAbstractQuery);
                        associateAbstractStmt.setInt(1, facultyUser.getUserID());
                        associateAbstractStmt.setInt(2, abstractID);
                        associateAbstractStmt.executeUpdate();
                    }
                }
            } 
            else if (user.getTypeID().equals("S")) 
            {
                Student studentUser = (Student) user;
                String query = "INSERT INTO student (studentID, fname, lname, email, phonenumber) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, studentUser.getUserID());
                stmt.setString(2, studentUser.getFname());
                stmt.setString(3, studentUser.getLname());
                stmt.setString(4, studentUser.getEmail());
                stmt.setString(5, studentUser.getPassword());
                stmt.executeUpdate();

                String insertInterestsQuery = "INSERT INTO studentinterests (studentID, interestID) VALUES (?, ?)";
                PreparedStatement insertInterestsStmt = conn.prepareStatement(insertInterestsQuery);
                for (Interest interest : studentUser.getInterests()) 
                {
                    insertInterestsStmt.setInt(1, studentUser.getStudentID());
                    insertInterestsStmt.setInt(2, interest.getInterestID());
                    insertInterestsStmt.executeUpdate();
                }
            }
            else if (user.getTypeID().equals("G")) 
            {
                Guest guestUser = (Guest) user;
                String query = "INSERT INTO guest (guestID, business, fname, lname, contactinfo) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, guestUser.getUserID());
                stmt.setString(2, guestUser.getBusiness());
                stmt.setString(3, guestUser.getFname());
                stmt.setString(4, guestUser.getLname());
                stmt.setString(5, guestUser.getContactInfo());
                stmt.executeUpdate();
            }
            else
            {
                return null;
            }
            return getUser(user);
        }
        catch (SQLException e) 
        {
            return null;
        }
    }

    public void deleteUser(User user)
    {
        try
        {
            String queryUser = "DELETE FROM users WHERE userID = ?";
            PreparedStatement stmtUser = conn.prepareStatement(queryUser);
            stmtUser.setInt(1, user.getUserID());
            stmtUser.executeUpdate();

            if (user.getTypeID().equals("F"))
            {
                Faculty facultyUser = (Faculty) user;
                String deleteFacultyQuery = "DELETE FROM faculty WHERE facultyID = ?";
                PreparedStatement deleteFacultyStmt = conn.prepareStatement(deleteFacultyQuery);
                deleteFacultyStmt.setInt(1, facultyUser.getFacultyID());
                deleteFacultyStmt.executeUpdate();
                
                String deleteInterestsQuery = "DELETE FROM facultyInterests WHERE facultyID = ?";
                PreparedStatement deleteInterestsStmt = conn.prepareStatement(deleteInterestsQuery);
                deleteInterestsStmt.setInt(1, facultyUser.getFacultyID());
                deleteInterestsStmt.executeUpdate();

                String deleteAbstractQuery = "DELETE FROM facultyAbstract WHERE facultyID = ?";
                PreparedStatement deleteAbstractStmt = conn.prepareStatement(deleteAbstractQuery);
                deleteAbstractStmt.setInt(1, facultyUser.getFacultyID());
                deleteAbstractStmt.executeUpdate();
            } 
            else if (user.getTypeID().equals("S")) 
            {
                Student studentUser = (Student) user;
                String deleteStudentQuery = "DELETE FROM student WHERE studentID = ?";
                PreparedStatement deleteStudentStmt = conn.prepareStatement(deleteStudentQuery);
                deleteStudentStmt.setInt(1, studentUser.getStudentID());
                deleteStudentStmt.executeUpdate();

                String deleteInterestsQuery = "DELETE FROM studentinterests WHERE studentID = ?";
                PreparedStatement deleteInterestsStmt = conn.prepareStatement(deleteInterestsQuery);
                deleteInterestsStmt.setInt(1, studentUser.getStudentID());
                deleteInterestsStmt.executeUpdate();
            }
            else if (user.getTypeID().equals("G")) 
            {
                Guest guestUser = (Guest) user;
                String query = "DELETE FROM guest WHERE guestID = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, guestUser.getGuestID());
                stmt.executeUpdate();
            }
        }
        catch (SQLException e) {}
    }

    public List<Interest> getInterests()
    {
        try
        {
            List<Interest> interests = new ArrayList<>();
            String query = "SELECT interestID, intDesc FROM interestList";
            Statement stmt = conn.createStatement();
            ResultSet interestsResultSet = stmt.executeQuery(query);
            while(interestsResultSet.next())
            {
                Interest interest = new Interest
                (
                    interestsResultSet.getInt("interestID"),
                    interestsResultSet.getString("intDesc")
                );
                if(!interests.contains(interest)) interests.add(interest);
            }
            return interests;
        }
        catch (SQLException e) 
        {
            return null;
        }
    }

    public List<InterestResult> getInterests(User user, List<Interest> interests)
    {
        try
        {
            List<InterestResult> interestResults = new ArrayList<>();

            if (user.getTypeID().equals("F"))
            {
                String query = "SELECT CONCAT(student.fname, ' ', student.lname) AS 'Student Name', GROUP_CONCAT(Interestlist.intDesc) AS 'Interest' FROM studentinterests LEFT JOIN student ON student.studentID = studentinterests.studentID LEFT JOIN interestList ON studentinterests.interestID = Interestlist.interestID WHERE studentinterests.interestID IN (";

                for (int i = 0; i < interests.size(); i++) 
                {
                    query += (i == 0 ? "?" : ",?");
                }

                query += ") GROUP BY studentinterests.studentID, CONCAT(student.fname, ' ', student.lname)";

                PreparedStatement stmt = conn.prepareStatement(query);

                for (int i = 0; i < interests.size(); i++) 
                {
                    stmt.setInt(i + 1, interests.get(i).getInterestID());
                }

                ResultSet resultSet = stmt.executeQuery();

                while (resultSet.next()) 
                {
                    InterestResult interestResult = new InterestResult
                    (
                        resultSet.getString("Student Name"),
                        resultSet.getString("Interest"),
                        null, 
                        null, 
                        null
                    );
                    if(!interestResults.contains(interestResult)) interestResults.add(interestResult);
                }
            }
            else if (user.getTypeID().equals("S"))
            {
                String query = "SELECT CONCAT(faculty.fname, ' ', faculty.lname) AS 'Faculty Name', GROUP_CONCAT(Interestlist.intDesc) AS 'Interest', faculty.location AS 'Location', faculty.phonenumber AS 'Phone Number', faculty.email AS 'Email' FROM facultyinterests LEFT JOIN faculty ON faculty.facultyId = facultyInterests.facultyId LEFT JOIN interestList ON facultyInterests.interestID = Interestlist.interestID WHERE facultyinterests.interestID IN (";

                for (int i = 0; i < interests.size(); i++) 
                {
                    query += (i == 0 ? "?" : ",?");
                }
                
                query += ") GROUP BY facultyinterests.facultyID, CONCAT(faculty.fname, ' ', faculty.lname)";

                PreparedStatement stmt = conn.prepareStatement(query);

                for (int i = 0; i < interests.size(); i++) 
                {
                    stmt.setInt(i + 1, interests.get(i).getInterestID());
                }
                
                ResultSet resultSet = stmt.executeQuery();

                while (resultSet.next()) 
                {
                    InterestResult interestResult = new InterestResult
                    (
                        resultSet.getString("Faculty Name"),
                        resultSet.getString("Interest"),
                        resultSet.getString("Location"),
                        resultSet.getString("Phone Number"),
                        resultSet.getString("Email")
                    );
                    if(!interestResults.contains(interestResult)) interestResults.add(interestResult);
                }
            }
            else if (user.getTypeID().equals("G"))
            {
                String query = "WITH schoolInterests AS ( SELECT CONCAT(student.fname, ' ', student.lname) AS 'Name', GROUP_CONCAT(Interestlist.intDesc) AS 'Interest', studentinterests.interestID FROM studentinterests LEFT JOIN student ON student.studentID = studentinterests.studentID LEFT JOIN interestList ON studentinterests.interestID = Interestlist.interestID GROUP BY studentinterests.studentID, CONCAT(student.fname, ' ', student.lname), studentinterests.interestID UNION SELECT CONCAT(faculty.fname, ' ', faculty.lname) AS 'Name', GROUP_CONCAT(Interestlist.intDesc) AS 'Interest', facultyinterests.interestID FROM facultyinterests LEFT JOIN faculty ON faculty.facultyID = facultyinterests.facultyID LEFT JOIN interestList ON facultyinterests.interestID = Interestlist.interestID GROUP BY facultyinterests.facultyID, CONCAT(faculty.fname, ' ', faculty.lname), facultyinterests.interestID) SELECT Name, GROUP_CONCAT(Interest) AS 'Interest' FROM schoolInterests WHERE interestID IN (";

                for (int i = 0; i < interests.size(); i++) 
                {
                    query += (i == 0 ? "?" : ",?");
                }

                query += ") GROUP BY Name";

                PreparedStatement stmt = conn.prepareStatement(query);

                for (int i = 0; i < interests.size(); i++) 
                {
                    stmt.setInt(i + 1, interests.get(i).getInterestID());
                }

                ResultSet resultSet = stmt.executeQuery();

                while (resultSet.next()) 
                {
                    InterestResult interestResult = new InterestResult
                    (
                        resultSet.getString("Name"),
                        resultSet.getString("Interest"),
                        null, 
                        null, 
                        null
                    );
                    if(!interestResults.contains(interestResult)) interestResults.add(interestResult);
                }
            }
            return interestResults;
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        Backend backend = new Backend();
        User cUser;

        backend.connect();

        System.out.print("Login\nUsername: ");
        String username = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();
        
        cUser = backend.login(username,password);

        if (cUser != null)
        {
            if(cUser instanceof Faculty)
            {
                Faculty cFacultyUser = (Faculty) cUser;
                System.out.println(cFacultyUser.toString());
            }
            else if(cUser instanceof Student)
            {
                Student cStudnetUser = (Student) cUser;
                System.out.println(cStudnetUser.toString());
            }
            else if(cUser instanceof Guest)
            {
                Guest cGuestUser = (Guest) cUser;
                System.out.println(cGuestUser.toString());
            }
        }
        else
        {
            System.out.println("Error");
        }

        scanner.close();
        backend.close();
    }
}
