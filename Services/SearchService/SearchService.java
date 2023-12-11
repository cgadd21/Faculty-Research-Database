package Services.SearchService;

import java.sql.*;
import java.util.*;
import Models.*;
import Services.DataService.*;
import Services.UserService.*;

public class SearchService implements ISearchService
{
    private IDataService _dataService = new DataService();
    private List<String> search = new ArrayList<>();
    private List<User> searchResults = new ArrayList<>();

    @Override
    public List<String> getSearch() 
    {
        return search;
    }

    @Override
    public List<User> getSearchResults() 
    {
        return searchResults;
    }

    @Override
    public void search(IUserService _userService)
    {
        try
        {
            if(!searchResults.isEmpty()) searchResults.clear();
            
            if(_userService.getCurrentUser().getTypeID().equals("S") || _userService.getCurrentUser().getTypeID().equals("G"))
            {
                String facultyQuery = "SELECT  u.*, f.* FROM users u JOIN faculty f ON u.userID = f.facultyID JOIN facultyabstract fa USING (facultyID) JOIN abstractlist al USING(abstractID) JOIN facultyinterests fi USING(facultyID) JOIN interestList il USING (interestID) WHERE professorAbstract LIKE CONCAT('%', ?, '%') OR (SELECT fi.interestID FROM facultyinterests fi JOIN interestList il USING (interestID) WHERE intDesc LIKE ?) GROUP BY userID";
                PreparedStatement facultyStatement = _dataService.connect().prepareStatement(facultyQuery);
                for (String seach : search)
                {
                    facultyStatement.setString(1, seach);
                    facultyStatement.setString(2, seach);
                    ResultSet facultyResultSet = facultyStatement.executeQuery();
                    while(facultyResultSet.next())
                    {
                        Faculty facultyUser = new Faculty
                        (
                            facultyResultSet.getInt("userID"),
                            facultyResultSet.getString("typeID"),
                            null,
                            null,
                            null,
                            null,
                            facultyResultSet.getInt("facultyID"),
                            facultyResultSet.getString("fname"),
                            facultyResultSet.getString("lname"),
                            facultyResultSet.getString("email"),
                            facultyResultSet.getString("phoneNumber"),
                            facultyResultSet.getString("location")
                        );

                        String interestQuery = "SELECT interestID, intDesc FROM facultyinterests JOIN interestlist USING (interestID) WHERE facultyID = ?;";
                        PreparedStatement interestStatement = _dataService.connect().prepareStatement(interestQuery);
                        interestStatement.setInt(1,facultyUser.getFacultyID());
                        ResultSet interestResultSet = interestStatement.executeQuery();
                        List<Interest> interests = new ArrayList<>();
                        while (interestResultSet.next()) 
                        {
                            Interest interest = new Interest
                            (
                                interestResultSet.getInt("interestID"),
                                interestResultSet.getString("intDesc")
                            );
                            interests.add(interest);
                        }
                        if(!interests.isEmpty()) facultyUser.setInterests(interests);

                        String abstractsQuery = "SELECT abstractID, professorAbstract FROM facultyabstract JOIN abstractlist USING (abstractID) WHERE facultyID = ?";
                        PreparedStatement abstractsStatement = _dataService.connect().prepareStatement(abstractsQuery);
                        abstractsStatement.setInt(1, facultyUser.getFacultyID());
                        ResultSet abstractsResultSet = abstractsStatement.executeQuery();
                        List<Abstract> abstracts = new ArrayList<>();
                        while(abstractsResultSet.next())
                        {
                            Abstract facultyAbstract = new Abstract
                            (
                                abstractsResultSet.getInt("abstractID"),
                                abstractsResultSet.getString("professorAbstract")
                            );
                            abstracts.add(facultyAbstract);
                        }
                        if(!abstracts.isEmpty()) facultyUser.setAbstracts(abstracts);

                        if(facultyUser != null) searchResults.add(facultyUser);
                    }
                }
            }
            if(_userService.getCurrentUser().getTypeID().equals("F") || _userService.getCurrentUser().getTypeID().equals("G"))
            {
                String studentQuery = "SELECT DISTINCT u.*, s.* from USERS u JOIN student s ON u.userID = s.studentID JOIN studentinterests si USING (studentID) JOIN interestList il USING (interestID) WHERE (SELECT si.interestID from studentinterests si JOIN interestList il USING (interestID) WHERE intdesc like ?) GROUP BY userID;";
                PreparedStatement studentStatement = _dataService.connect().prepareStatement(studentQuery);
                for (String seach : search)
                {
                    studentStatement.setString(1, seach);
                    ResultSet studentResultSet = studentStatement.executeQuery();
                    while(studentResultSet.next())
                    {
                        Student studentUser = new Student
                        (
                            studentResultSet.getInt("userID"),
                            studentResultSet.getString("typeID"),
                            null,
                            null,
                            null,
                            null,
                            studentResultSet.getInt("studentID"),
                            studentResultSet.getString("fname"),
                            studentResultSet.getString("lname"),
                            studentResultSet.getString("email"),
                            studentResultSet.getString("phonenumber")
                        );

                        String interestQuery = "SELECT interestID, intDesc FROM studentinterests JOIN interestlist USING (interestID) WHERE studentID = ?;";
                        PreparedStatement interestStatement = _dataService.connect().prepareStatement(interestQuery);
                        interestStatement.setInt(1,studentUser.getStudentID());
                        ResultSet interestResultSet = interestStatement.executeQuery();
                        List<Interest> interests = new ArrayList<>();
                        while (interestResultSet.next()) 
                        {
                            Interest interest = new Interest
                            (
                                interestResultSet.getInt("interestID"),
                                interestResultSet.getString("intDesc")
                            );
                            interests.add(interest);
                        }
                        if(!interests.isEmpty()) studentUser.setInterests(interests);

                        String majorQuery = "SELECT majorID, majorDescription FROM studentmajor JOIN majorlist USING (majorID) WHERE studentID = ?;";
                        PreparedStatement majorStatement = _dataService.connect().prepareStatement(majorQuery);
                        majorStatement.setInt(1, studentUser.getStudentID());
                        ResultSet majorResultSet = majorStatement.executeQuery();
                        List<Major> majors = new ArrayList<>();
                        while(majorResultSet.next())
                        {
                            Major major = new Major
                            (
                                majorResultSet.getInt("majorID"),
                                majorResultSet.getString("majorDescription")
                            );
                            majors.add(major);
                        }
                        if(!majors.isEmpty()) studentUser.setMajors(majors);

                        if(studentUser != null) searchResults.add(studentUser);
                    }
                }
            }
        }
        catch (Exception e) 
        {
            System.out.println("ERROR searching");
        }
        finally
        {
            _dataService.close();
        }
    }
}
