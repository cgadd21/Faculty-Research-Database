package Services.SearchService;

import java.sql.*;
import java.util.*;

import Models.*;
import Services.DataService.*;

public class SearchService implements ISearchService 
{
    private IDataService _dataService = new DataService();

    private List<Interest> interests;
    private List<InterestResult> interestResults;
    private List<Abstract> abstracts;
    private List<AbstractResult> abstractResults;

    @Override
    public List<Interest> getInterestList()
    {
        return interests;
    }

    @Override
    public List<InterestResult> getInterestResultList()
    {
        return interestResults;
    }

    @Override
    public List<Abstract> getAbstractsList()
    {
        return abstracts;
    }

    @Override
    public List<AbstractResult> getAbstractsResultList()
    {
        return abstractResults;
    }

    @Override
    public void getInterests()
    {
        try
        {
            interests.clear();
            String query = "SELECT interestID, intDesc FROM interestList";
            Statement stmt = _dataService.getConnection().createStatement();
            ResultSet interestsResultSet = stmt.executeQuery(query);
            while(interestsResultSet.next())
            {
                Interest interest = new Interest
                (
                    interestsResultSet.getInt("interestID"),
                    interestsResultSet.getString("intDesc")
                );
                if(!interests.stream().anyMatch(i -> i.getIntDesc().equals(interest.getIntDesc()))) interests.add(interest);
            }
        }
        catch (SQLException e) {}
    }

    @Override
    public void getInterests(User user, List<Interest> interests)
    {
        try
        {
            interestResults.clear();
            if (user.getTypeID().equals("F"))
            {
                String query = "SELECT CONCAT(student.fname, ' ', student.lname) AS 'Student Name', GROUP_CONCAT(Interestlist.intDesc) AS 'Interest' FROM studentinterests LEFT JOIN student ON student.studentID = studentinterests.studentID LEFT JOIN interestList ON studentinterests.interestID = Interestlist.interestID WHERE studentinterests.interestID IN (";

                for (int i = 0; i < interests.size(); i++) 
                {
                    query += (i == 0 ? "?" : ",?");
                }

                query += ") GROUP BY studentinterests.studentID, CONCAT(student.fname, ' ', student.lname)";

                PreparedStatement stmt = _dataService.getConnection().prepareStatement(query);

                for (int i = 0; i < interests.size(); i++) 
                {
                    stmt.setInt(i + 1, interests.get(i).getInterestID());
                }

                ResultSet resultSet = stmt.executeQuery();

                while (resultSet.next()) 
                {
                    InterestResult interestResult = new InterestResult
                    (
                        resultSet.getString("Student Name"),
                        resultSet.getString("Interest"),
                        null, 
                        null, 
                        null
                    );
                    interestResults.add(interestResult);
                }
            }
            else if (user.getTypeID().equals("S"))
            {
                String query = "SELECT CONCAT(faculty.fname, ' ', faculty.lname) AS 'Faculty Name', GROUP_CONCAT(Interestlist.intDesc) AS 'Interest', faculty.location AS 'Location', faculty.phonenumber AS 'Phone Number', faculty.email AS 'Email' FROM facultyinterests LEFT JOIN faculty ON faculty.facultyId = facultyInterests.facultyId LEFT JOIN interestList ON facultyInterests.interestID = Interestlist.interestID WHERE facultyinterests.interestID IN (";

                for (int i = 0; i < interests.size(); i++) 
                {
                    query += (i == 0 ? "?" : ",?");
                }
                
                query += ") GROUP BY facultyinterests.facultyID, CONCAT(faculty.fname, ' ', faculty.lname)";

                PreparedStatement stmt = _dataService.getConnection().prepareStatement(query);

                for (int i = 0; i < interests.size(); i++) 
                {
                    stmt.setInt(i + 1, interests.get(i).getInterestID());
                }
                
                ResultSet resultSet = stmt.executeQuery();

                while (resultSet.next()) 
                {
                    InterestResult interestResult = new InterestResult
                    (
                        resultSet.getString("Faculty Name"),
                        resultSet.getString("Interest"),
                        resultSet.getString("Location"),
                        resultSet.getString("Phone Number"),
                        resultSet.getString("Email")
                    );
                    interestResults.add(interestResult);
                }
            }
            else if (user.getTypeID().equals("G"))
            {
                String query = "WITH schoolInterests AS ( SELECT CONCAT(student.fname, ' ', student.lname) AS 'Name', GROUP_CONCAT(Interestlist.intDesc) AS 'Interest', studentinterests.interestID FROM studentinterests LEFT JOIN student ON student.studentID = studentinterests.studentID LEFT JOIN interestList ON studentinterests.interestID = Interestlist.interestID GROUP BY studentinterests.studentID, CONCAT(student.fname, ' ', student.lname), studentinterests.interestID UNION SELECT CONCAT(faculty.fname, ' ', faculty.lname) AS 'Name', GROUP_CONCAT(Interestlist.intDesc) AS 'Interest', facultyinterests.interestID FROM facultyinterests LEFT JOIN faculty ON faculty.facultyID = facultyinterests.facultyID LEFT JOIN interestList ON facultyinterests.interestID = Interestlist.interestID GROUP BY facultyinterests.facultyID, CONCAT(faculty.fname, ' ', faculty.lname), facultyinterests.interestID) SELECT Name, GROUP_CONCAT(Interest) AS 'Interest' FROM schoolInterests WHERE interestID IN (";

                for (int i = 0; i < interests.size(); i++) 
                {
                    query += (i == 0 ? "?" : ",?");
                }

                query += ") GROUP BY Name";

                PreparedStatement stmt = _dataService.getConnection().prepareStatement(query);

                for (int i = 0; i < interests.size(); i++) 
                {
                    stmt.setInt(i + 1, interests.get(i).getInterestID());
                }

                ResultSet resultSet = stmt.executeQuery();

                while (resultSet.next()) 
                {
                    InterestResult interestResult = new InterestResult
                    (
                        resultSet.getString("Name"),
                        resultSet.getString("Interest"),
                        null, 
                        null, 
                        null
                    );
                    interestResults.add(interestResult);
                }
            }
        }
        catch (SQLException e) {}
    }

    @Override
    public void createInterest(Interest interest)
    {
        try
        {
            String query = "INSERT INTO interestList (intDesc) VALUES (?)";
            PreparedStatement stmt = _dataService.getConnection().prepareStatement(query);
            stmt.setString(1, interest.getIntDesc());
            stmt.executeUpdate();
            getInterests();
        }
        catch (SQLException e) {}
    }

    @Override
    public void getAbstracts()
    {
        try
        {
            abstracts.clear();
            String query = "SELECT abstractID, professorAbstract FROM abstractList";
            Statement stmt = _dataService.getConnection().createStatement();
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
        catch (SQLException e) {}
    }

    @Override
    public void getAbstracts(String search)
    {
        try
        {
            abstractResults.clear();
            String query = "SELECT a.professorAbstract as Abstract, GROUP_CONCAT(CONCAT(f.lname, ', ', f.fname) SEPARATOR '; ') AS Professors FROM abstractList a JOIN facultyabstract fa ON a.abstractID = fa.abstractID JOIN faculty f ON fa.facultyID = f.facultyID WHERE a.professorAbstract LIKE '%?%' GROUP BY a.abstractID;";
            PreparedStatement stmt = _dataService.getConnection().prepareStatement(query);
            stmt.setString(1, search);
            ResultSet abstractResultSet = stmt.executeQuery();
            while (abstractResultSet.next()) 
            {
                AbstractResult abstractResult = new AbstractResult
                (
                    abstractResultSet.getString("Abstract"),
                    abstractResultSet.getString("Professors") 
                );
                abstractResults.add(abstractResult);
            }
        }
        catch (SQLException e) {}
    }

    @Override
    public void createAbstract(Abstract facultyAbstract)
    {
        try
        {
            String query = "INSERT INTO abstractList (professorAbstract) VALUES (?)";
            PreparedStatement stmt = _dataService.getConnection().prepareStatement(query);
            stmt.setString(1, facultyAbstract.getProfessorAbstract());
            stmt.executeUpdate();
            getAbstracts();
        }
        catch (SQLException e) {}
    }

}
