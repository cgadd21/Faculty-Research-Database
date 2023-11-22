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
                    User currUser = new User
                    (
                        userResultSet.getInt("userID"),
                        userResultSet.getString("typeID"),
                        userResultSet.getString("username"),
                        userResultSet.getString("password")
                    );
                    
                    return currUser;
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

    public User GetUser(User cUser)
    {
        if(cUser.getTypeID().equals("F"))
        {
            Faculty facultyUser = new Faculty();
            return facultyUser;
        }
        else if (cUser.getTypeID().equals("S")) 
        {
            try 
            {
                String query = "SELECT * FROM users " +
                            "JOIN student ON users.userID = student.studentID " +
                            "LEFT JOIN studentInterests ON student.studentID = studentInterests.studentID " +
                            "LEFT JOIN interestList ON studentInterests.interestID = interestList.interestID " +
                            "WHERE users.userID = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, cUser.getUserID());

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
                            studentResultSet.getString("email"),
                            studentResultSet.getString("phonenumber")
                        );

                        List<Interest> interests = new ArrayList<>();
                        do 
                        {
                            Interest interest = new Interest();
                            interest.setInterestID(studentResultSet.getInt("interestID"));
                            interest.setIntDesc(studentResultSet.getString("intDesc"));
                            interests.add(interest);
                        } while (studentResultSet.next());

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
        else if(cUser.getTypeID().equals("G"))
        {
            Guest guestUser = new Guest();
            return guestUser;
        }
        else
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
            cUser = backend.GetUser(cUser);

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

        scanner.close();
        backend.close();
    }
}
