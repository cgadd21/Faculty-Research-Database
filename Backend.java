import java.sql.*;
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
        else if(cUser.getTypeID().equals("S"))
        {
            Student studentUser = new Student();
            return studentUser;
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
