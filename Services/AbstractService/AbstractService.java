package Services.AbstractService;

import java.sql.*;
import java.util.*;
import Models.*;
import Services.DataService.*;

public class AbstractService implements IAbstractService 
{
    private IDataService _dataService = new DataService();
    private List<Abstract> abstracts = new ArrayList<>();
    private Abstract newAbstract = new Abstract();

    @Override
    public List<Abstract> getAbstractsList()
    {
        return abstracts;
    }

    @Override
    public Abstract getNewAbstract()
    {
        return newAbstract;
    }

    @Override
    public void getAbstracts()
    {
        try
        {
            if(!abstracts.isEmpty()) abstracts.clear();
            String query = "SELECT abstractID, professorAbstract FROM abstractList";
            Statement stmt = _dataService.connect().createStatement();
            ResultSet abstractResultSet = stmt.executeQuery(query);
            while(abstractResultSet.next())
            {
                Abstract facultyAbstract = new Abstract
                (
                    abstractResultSet.getInt("abstractID"),
                    abstractResultSet.getString("professorAbstract")
                );
                if(!abstracts.stream().anyMatch(a -> a.getProfessorAbstract().equals(facultyAbstract.getProfessorAbstract()))) abstracts.add(facultyAbstract);
            }
        }
        catch (Exception e) {}
        finally
        {
            _dataService.close();
        }
    }

    @Override
    public void createAbstract()
    {
        try
        {
            String query = "INSERT INTO abstractList (professorAbstract) VALUES (?)";
            PreparedStatement stmt = _dataService.connect().prepareStatement(query);
            stmt.setString(1, newAbstract.getProfessorAbstract());
            stmt.executeUpdate();
            newAbstract = null;
            getAbstracts();
        }
        catch (Exception e) {}
        finally
        {
            _dataService.close();
        }
    }
}
