package Services.DataService;

import java.sql.*;

public class DataService implements IDataService
{
    @Override
    public Connection connect() 
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/project", "root", "student");
        } 
        catch (ClassNotFoundException | SQLException e)
        {
            return null;
        }
    }

    @Override
    public void close() 
    {
        try 
        {
            if (connect() != null) connect().close();
        } 
        catch (SQLException sqle) {}
    }
}
