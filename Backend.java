import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
                String query = "SELECT * FROM users " +
                               "JOIN faculty ON users.userID = faculty.facultyID " +
                               "LEFT JOIN facultyInterests ON faculty.facultyID = facultyInterests.facultyID " +
                               "LEFT JOIN interestList ON facultyInterests.interestID = interestList.interestID " +
                               "LEFT JOIN facultyAbstract ON faculty.facultyID = facultyAbstract.facultyID " +
                               "LEFT JOIN abstractList ON facultyAbstract.abstractID = abstractList.abstractID " +
                               "WHERE users.userID = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, user.getUserID());
        
                try (ResultSet facultyResultSet = preparedStatement.executeQuery()) 
                {
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
                            Interest interest = new Interest();
                            interest.setInterestID(facultyResultSet.getInt("interestID"));
                            interest.setIntDesc(facultyResultSet.getString("intDesc"));
                            if(!interests.contains(interest)) interests.add(interest);

                            Abstract facultyAbstract = new Abstract();
                            facultyAbstract.setAbstractID(facultyResultSet.getInt("abstractID"));
                            facultyAbstract.setProfessorAbstract(facultyResultSet.getString("professorAbstract"));
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
                String query = "SELECT * FROM users " +
                            "JOIN student ON users.userID = student.studentID " +
                            "LEFT JOIN studentInterests ON student.studentID = studentInterests.studentID " +
                            "LEFT JOIN interestList ON studentInterests.interestID = interestList.interestID " +
                            "WHERE users.userID = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, user.getUserID());

                try (ResultSet studentResultSet = preparedStatement.executeQuery()) 
                {
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
                            Interest interest = new Interest();
                            interest.setInterestID(studentResultSet.getInt("interestID"));
                            interest.setIntDesc(studentResultSet.getString("intDesc"));
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
                String query = "SELECT * FROM users " +
                        "JOIN guest ON users.userID = guest.guestID " +
                        "WHERE users.userID = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, user.getUserID());

                try (ResultSet guestResultSet = preparedStatement.executeQuery()) 
                {
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

    public User updateUser(User user) {
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
                String query = "UPDATE faculty SET fname = ?, lname = ?, email = ?, phonenumber = ?, location = ? WHERE facultyID = ?";
                PreparedStatement stmt = conn.prepareStatement(query);

                stmt.setString(1, facultyUser.getFname());
                stmt.setString(2, facultyUser.getLname());
                stmt.setString(3, facultyUser.getEmail());
                stmt.setString(4, facultyUser.getPhoneNumber());
                stmt.setString(5, facultyUser.getLocation());
                stmt.setInt(6, facultyUser.getFacultyID());
    
                stmt.executeUpdate();
            } 
            else if (user.getTypeID().equals("S")) 
            {
                Student studentUser = (Student) user;
                String query = "UPDATE student SET fname = ?, lname = ?, email = ?, phonenumber = ? WHERE studentID = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
    
                stmt.setString(1, studentUser.getFname());
                stmt.setString(2, studentUser.getLname());
                stmt.setString(3, studentUser.getEmail());
                stmt.setString(4, studentUser.getPhoneNumber());
                stmt.setInt(5, studentUser.getStudentID());
    
                stmt.executeUpdate();
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
