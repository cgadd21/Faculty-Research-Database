package Services.DataService;

import java.sql.*;

public interface IDataService 
{
    Connection connect();
    void close();
}
