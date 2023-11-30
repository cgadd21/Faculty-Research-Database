package Services.DataService;

import java.sql.*;

public interface IDataService 
{
    void connect();
    void close();
    Connection getConnection();
}
