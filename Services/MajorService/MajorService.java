package Services.MajorService;

import java.sql.*;
import java.util.*;
import Models.*;
import Services.DataService.*;

public class MajorService implements IMajorService
{
    private IDataService _dataService = new DataService();
    private List<Major> majors = new ArrayList<>();
    private Major newMajor = new Major();

    @Override
    public List<Major> getMajorsList()
    {
        return majors;
    }

    @Override
    public Major getNewMajor()
    {
        return newMajor;
    }

    @Override
    public void getMajors()
    {
        try
        {
            if(!majors.isEmpty()) majors.clear();
            String query = "SELECT majorID, majorDescription FROM majorList";
            Statement stmt = _dataService.connect().createStatement();
            ResultSet majorResultSet = stmt.executeQuery(query);
            while(majorResultSet.next())
            {
                Major major = new Major
                (
                    majorResultSet.getInt("majorID"),
                    majorResultSet.getString("majorDescription")
                );
                majors.add(major);
            }
        }
        catch (Exception e) 
        {
            System.out.println("ERROR getting majors");
        }
        finally
        {
            _dataService.close();
        }
    }

    @Override
    public void createMajor()
    {
        try
        {
            String query = "INSERT INTO majorList (majorDescription) VALUES (?)";
            PreparedStatement stmt = _dataService.connect().prepareStatement(query);
            stmt.setString(1, newMajor.getMajorDescription());
            stmt.executeUpdate();
            newMajor = null;
            getMajors();
        }
        catch (Exception e) 
        {
            System.out.println("ERROR creating major");
        }
        finally
        {
            _dataService.close();
        }
    }
}
