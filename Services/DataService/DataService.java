package Services.DataService;

import java.sql.*;

public class DataService implements IDataService
{
    private Connection conn;

    @Override
    public Connection getConnection() 
    {
        return conn;
    }
    
    @Override
    public void connect() 
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/project", "root", "student");
        } 
        catch (ClassNotFoundException cnfe) {} 
        catch (SQLException sqle) {}
    }

    @Override
    public void close() 
    {
        try 
        {
            if (conn != null) conn.close();
        } 
        catch (SQLException sqle) {}
    }

}
