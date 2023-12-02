package Services.InterestService;

import java.sql.*;
import java.util.*;
import Models.*;
import Services.DataService.*;

public class InterestService implements IInterestService
{
    private IDataService _dataService = new DataService();
    private List<Interest> interests = new ArrayList<>();
    private Interest newInterest = new Interest();
    
    @Override
    public List<Interest> getInterestList()
    {
        return interests;
    }

    @Override
    public Interest getNewInterest()
    {
        return newInterest;
    }

    @Override
    public void getInterests()
    {
        try
        {
            if(!interests.isEmpty()) interests.clear();
            String query = "SELECT interestID, intDesc FROM interestList";
            Statement stmt = _dataService.connect().createStatement();
            ResultSet interestsResultSet = stmt.executeQuery(query);
            while(interestsResultSet.next())
            {
                Interest interest = new Interest
                (
                    interestsResultSet.getInt("interestID"),
                    interestsResultSet.getString("intDesc")
                );
                interests.add(interest);
            }
        }
        catch (Exception e) {}
        finally
        {
            _dataService.close();
        }
    }

    @Override
    public void createInterest()
    {
        try
        {
            String query = "INSERT INTO interestList (intDesc) VALUES (?)";
            PreparedStatement stmt = _dataService.connect().prepareStatement(query);
            stmt.setString(1, newInterest.getIntDesc());
            stmt.executeUpdate();
            newInterest = null;
            getInterests();
        }
        catch (Exception e) {}
        finally
        {
            _dataService.close();
        }
    }

}