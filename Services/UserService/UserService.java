package Services.UserService;

import java.sql.*;
import java.util.*;

import Models.*;
import Services.DataService.*;

public class UserService implements IUserService 
{
    private IDataService _dataService = new DataService();

    private User user;

    @Override
    public User getUser() 
    {
        return user;
    }

    @Override
    public void setUser(User user) 
    {
        this.user = user;
    }

    @Override
    public void login()
    {
        try 
        {
            String query = "SELECT * FROM users WHERE BINARY username = ? AND BINARY password = ?";
            PreparedStatement preparedStatement = _dataService.getConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            //encrpyt password
            preparedStatement.setString(2, user.getPassword());

            try (ResultSet userResultSet = preparedStatement.executeQuery()) 
            {
                if (userResultSet.next()) 
                {
                    User user = new User
                    (
                        userResultSet.getInt("userID"),
                        userResultSet.getString("typeID"),
                        userResultSet.getString("username"),
                        userResultSet.getString("password")
                    );
                    
                    getUser(user);
                }
            }
        } 
        catch (SQLException e) {}
    }

    @Override
    public void getUser(User user)
    {
        if (user.getTypeID().equals("F")) 
        {
            try 
            {
                String query = "SELECT * FROM users JOIN faculty ON users.userID = faculty.facultyID LEFT JOIN facultyInterests ON faculty.facultyID = facultyInterests.facultyID LEFT JOIN interestList ON facultyInterests.interestID = interestList.interestID LEFT JOIN facultyAbstract ON faculty.facultyID = facultyAbstract.facultyID LEFT JOIN abstractList ON facultyAbstract.abstractID = abstractList.abstractID WHERE users.userID = ?";
                PreparedStatement preparedStatement = _dataService.getConnection().prepareStatement(query);
                preparedStatement.setInt(1, user.getUserID());
                ResultSet facultyResultSet = preparedStatement.executeQuery();
        
                if (facultyResultSet.next()) 
                {
                    Faculty facultyUser = new Faculty
                    (
                        facultyResultSet.getInt("userID"),
                        facultyResultSet.getString("typeID"),
                        facultyResultSet.getString("username"),
                        facultyResultSet.getString("password"),
                        facultyResultSet.getInt("facultyID"),
                        facultyResultSet.getString("fname"),
                        facultyResultSet.getString("lname"),
                        facultyResultSet.getString("email"),
                        facultyResultSet.getString("phoneNumber"),
                        facultyResultSet.getString("location")
                    );
    
                    List<Interest> interests = new ArrayList<>();
                    List<Abstract> abstracts = new ArrayList<>();

                    while (facultyResultSet.next()) 
                    {
                        Interest interest = new Interest
                        (
                            facultyResultSet.getInt("interestID"),
                            facultyResultSet.getString("intDesc")
                        );
                        if(!interests.stream().anyMatch(i -> i.getIntDesc().equals(interest.getIntDesc()))) interests.add(interest);

                        Abstract facultyAbstract = new Abstract
                        (
                            facultyResultSet.getInt("abstractID"),
                            facultyResultSet.getString("professorAbstract")
                        );
                        if(!abstracts.stream().anyMatch(a -> a.getProfessorAbstract().equals(facultyAbstract.getProfessorAbstract()))) abstracts.add(facultyAbstract);
                    }
    
                    facultyUser.setInterests(interests);
                    facultyUser.setAbstracts(abstracts);
    
                    user = facultyUser;
                }
            } 
            catch (SQLException e) {}
        }        
        else if (user.getTypeID().equals("S")) 
        {
            try 
            {
                String query = "SELECT * FROM users JOIN student ON users.userID = student.studentID LEFT JOIN studentInterests ON student.studentID = studentInterests.studentID LEFT JOIN interestList ON studentInterests.interestID = interestList.interestID WHERE users.userID = ?";
                PreparedStatement preparedStatement = _dataService.getConnection().prepareStatement(query);
                preparedStatement.setInt(1, user.getUserID());
                ResultSet studentResultSet = preparedStatement.executeQuery();

                if (studentResultSet.next()) 
                {
                    Student studentUser = new Student
                    (
                        studentResultSet.getInt("userID"),
                        studentResultSet.getString("typeID"),
                        studentResultSet.getString("username"),
                        studentResultSet.getString("password"),
                        studentResultSet.getInt("studentID"),
                        studentResultSet.getString("fname"),
                        studentResultSet.getString("lname"),
                        studentResultSet.getString("email"),
                        studentResultSet.getString("phonenumber")
                    );

                    List<Interest> interests = new ArrayList<>();
                    while (studentResultSet.next()) 
                    {
                        Interest interest = new Interest
                        (
                            studentResultSet.getInt("interestID"),
                            studentResultSet.getString("intDesc")
                        );
                        if(!interests.stream().anyMatch(i -> i.getIntDesc().equals(interest.getIntDesc()))) interests.add(interest);
                    }

                    studentUser.setInterests(interests);

                    user = studentUser;
                }
            } 
            catch (SQLException e) {}
        }
        else if(user.getTypeID().equals("G"))
        {
            try 
            {
                String query = "SELECT * FROM users JOIN guest ON users.userID = guest.guestID WHERE users.userID = ?";
                PreparedStatement preparedStatement = _dataService.getConnection().prepareStatement(query);
                preparedStatement.setInt(1, user.getUserID());
                ResultSet guestResultSet = preparedStatement.executeQuery();

                if (guestResultSet.next()) 
                {
                    Guest guestUser = new Guest
                    (
                        guestResultSet.getInt("userID"),
                        guestResultSet.getString("typeID"),
                        guestResultSet.getString("username"),
                        guestResultSet.getString("password"),
                        guestResultSet.getInt("guestID"),
                        guestResultSet.getString("business"),
                        guestResultSet.getString("fname"),
                        guestResultSet.getString("lname"),
                        guestResultSet.getString("contactinfo")
                    );

                    user = guestUser;
                }
            } 
            catch (SQLException e) {}
        }
    }

    @Override
    public void updateUser(User user) 
    {
        try 
        {
            String queryUser = "UPDATE users SET username = ?, password = ? WHERE userID = ?";
            PreparedStatement stmtUser = _dataService.getConnection().prepareStatement(queryUser);
            stmtUser.setString(1, user.getUsername());
            //encrpyt password
            stmtUser.setString(2, user.getPassword());
            stmtUser.setInt(3, user.getUserID());
            stmtUser.executeUpdate();

            if (user.getTypeID().equals("F")) 
            {
                Faculty facultyUser = (Faculty) user;
                String updateFacultyQuery = "UPDATE faculty SET fname = ?, lname = ?, email = ?, phonenumber = ?, location = ? WHERE facultyID = ?";
                PreparedStatement updateFacultyStmt = _dataService.getConnection().prepareStatement(updateFacultyQuery);
                updateFacultyStmt.setString(1, facultyUser.getFname());
                updateFacultyStmt.setString(2, facultyUser.getLname());
                updateFacultyStmt.setString(3, facultyUser.getEmail());
                updateFacultyStmt.setString(4, facultyUser.getPhoneNumber());
                updateFacultyStmt.setString(5, facultyUser.getLocation());
                updateFacultyStmt.setInt(6, facultyUser.getFacultyID());
                updateFacultyStmt.executeUpdate();

                String deleteInterestsQuery = "DELETE FROM facultyInterests WHERE facultyID = ?";
                PreparedStatement deleteInterestsStmt = _dataService.getConnection().prepareStatement(deleteInterestsQuery);
                deleteInterestsStmt.setInt(1, facultyUser.getFacultyID());
                deleteInterestsStmt.executeUpdate();

                String insertInterestsQuery = "INSERT INTO facultyInterests (facultyID, interestID) VALUES (?, ?)";
                PreparedStatement insertInterestsStmt = _dataService.getConnection().prepareStatement(insertInterestsQuery);
                for (Interest interest : facultyUser.getInterests()) 
                {
                    insertInterestsStmt.setInt(1, facultyUser.getFacultyID());
                    insertInterestsStmt.setInt(2, interest.getInterestID());
                    insertInterestsStmt.executeUpdate();
                }
                
                String deleteAbstractsQuery = "DELETE FROM facultyAbstract WHERE facultyID = ?";
                PreparedStatement deleteAbstractsStmt = _dataService.getConnection().prepareStatement(deleteAbstractsQuery);
                deleteAbstractsStmt.setInt(1, facultyUser.getFacultyID());
                deleteAbstractsStmt.executeUpdate();

                String insertAbstractsQuery = "INSERT INTO facultyAbstract (facultyID, abstractID) VALUES (?, ?)";
                PreparedStatement insertAbstractsStmt = _dataService.getConnection().prepareStatement(insertAbstractsQuery);
                for (Abstract facultyAbstract : facultyUser.getAbstracts()) 
                {
                    insertAbstractsStmt.setInt(1, facultyUser.getUserID());
                    insertAbstractsStmt.setInt(1, facultyAbstract.getAbstractID());
                    insertAbstractsStmt.executeUpdate();
                }
            } 
            else if (user.getTypeID().equals("S")) 
            {
                Student studentUser = (Student) user;
                String updateStudentQuery = "UPDATE student SET fname = ?, lname = ?, email = ?, phonenumber = ? WHERE studentID = ?";
                PreparedStatement updateStudentStmt = _dataService.getConnection().prepareStatement(updateStudentQuery);
                updateStudentStmt.setString(1, studentUser.getFname());
                updateStudentStmt.setString(2, studentUser.getLname());
                updateStudentStmt.setString(3, studentUser.getEmail());
                updateStudentStmt.setString(4, studentUser.getPhoneNumber());
                updateStudentStmt.setInt(5, studentUser.getStudentID());
                updateStudentStmt.executeUpdate();

                String deleteInterestsQuery = "DELETE FROM studentinterests WHERE studentID = ?";
                PreparedStatement deleteInterestsStmt = _dataService.getConnection().prepareStatement(deleteInterestsQuery);
                deleteInterestsStmt.setInt(1, studentUser.getStudentID());
                deleteInterestsStmt.executeUpdate();

                String insertInterestsQuery = "INSERT INTO studentinterests (studentID, interestID) VALUES (?, ?)";
                PreparedStatement insertInterestsStmt = _dataService.getConnection().prepareStatement(insertInterestsQuery);
                for (Interest interest : studentUser.getInterests()) 
                {
                    insertInterestsStmt.setInt(1, studentUser.getStudentID());
                    insertInterestsStmt.setInt(2, interest.getInterestID());
                    insertInterestsStmt.executeUpdate();
                }
            } 
            else if (user.getTypeID().equals("G")) 
            {
                Guest guestUser = (Guest) user;
                String query = "UPDATE guest SET business = ?, fname = ?, lname = ?, contactinfo = ? WHERE guestID = ?";
                PreparedStatement stmt = _dataService.getConnection().prepareStatement(query);
                stmt.setString(1, guestUser.getBusiness());
                stmt.setString(2, guestUser.getFname());
                stmt.setString(3, guestUser.getLname());
                stmt.setString(4, guestUser.getContactInfo());
                stmt.setInt(5, guestUser.getGuestID());
                stmt.executeUpdate();
            }
            getUser(user);
        } 
        catch (SQLException e) {}
    }
    
    @Override
    public void createUser(User user)
    {
        try
        {
            String queryUser = "INSERT INTO users (typeID, username, password) VALUES (?, ?, ?)";
            PreparedStatement stmtUser = _dataService.getConnection().prepareStatement(queryUser, Statement.RETURN_GENERATED_KEYS);
            stmtUser.setString(1, user.getTypeID());
            stmtUser.setString(2, user.getUsername());
            //encrpyt password
            stmtUser.setString(3, user.getPassword());
            stmtUser.executeUpdate();
            ResultSet generatedKeys = stmtUser.getGeneratedKeys();
            if(generatedKeys.next()) user.setUserID(generatedKeys.getInt(1));

            if (user.getTypeID().equals("F"))
            {
                Faculty facultyUser = (Faculty) user;
                String query = "INSERT INTO faculty (facultyID, fname, lname, email, phonenumber, location) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = _dataService.getConnection().prepareStatement(query);
                stmt.setInt(1, facultyUser.getUserID());
                stmt.setString(2, facultyUser.getFname());
                stmt.setString(3, facultyUser.getLname());
                stmt.setString(4, facultyUser.getEmail());
                stmt.setString(5, facultyUser.getPhoneNumber());
                stmt.setString(6, facultyUser.getLocation());
                stmt.executeUpdate();

                String insertInterestsQuery = "INSERT INTO facultyInterests (facultyID, interestID) VALUES (?, ?)";
                PreparedStatement insertInterestsStmt = _dataService.getConnection().prepareStatement(insertInterestsQuery);
                for (Interest interest : facultyUser.getInterests()) 
                {
                    insertInterestsStmt.setInt(1, facultyUser.getFacultyID());
                    insertInterestsStmt.setInt(2, interest.getInterestID());
                    insertInterestsStmt.executeUpdate();
                }

                String insertAbstractsQuery = "INSERT INTO facultyAbstract (facultyID, abstractID) VALUES (?, ?)";
                PreparedStatement insertAbstractsStmt = _dataService.getConnection().prepareStatement(insertAbstractsQuery);
                for (Abstract facultyAbstract : facultyUser.getAbstracts()) 
                {
                    insertAbstractsStmt.setInt(1, facultyUser.getUserID());
                    insertAbstractsStmt.setInt(1, facultyAbstract.getAbstractID());
                    insertAbstractsStmt.executeUpdate();
                }
            } 
            else if (user.getTypeID().equals("S")) 
            {
                Student studentUser = (Student) user;
                String query = "INSERT INTO student (studentID, fname, lname, email, phonenumber) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = _dataService.getConnection().prepareStatement(query);
                stmt.setInt(1, studentUser.getUserID());
                stmt.setString(2, studentUser.getFname());
                stmt.setString(3, studentUser.getLname());
                stmt.setString(4, studentUser.getEmail());
                stmt.setString(5, studentUser.getPassword());
                stmt.executeUpdate();

                String insertInterestsQuery = "INSERT INTO studentinterests (studentID, interestID) VALUES (?, ?)";
                PreparedStatement insertInterestsStmt = _dataService.getConnection().prepareStatement(insertInterestsQuery);
                for (Interest interest : studentUser.getInterests()) 
                {
                    insertInterestsStmt.setInt(1, studentUser.getStudentID());
                    insertInterestsStmt.setInt(2, interest.getInterestID());
                    insertInterestsStmt.executeUpdate();
                }
            }
            else if (user.getTypeID().equals("G")) 
            {
                Guest guestUser = (Guest) user;
                String query = "INSERT INTO guest (guestID, business, fname, lname, contactinfo) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = _dataService.getConnection().prepareStatement(query);
                stmt.setInt(1, guestUser.getUserID());
                stmt.setString(2, guestUser.getBusiness());
                stmt.setString(3, guestUser.getFname());
                stmt.setString(4, guestUser.getLname());
                stmt.setString(5, guestUser.getContactInfo());
                stmt.executeUpdate();
            }
            getUser(user);
        }
        catch (SQLException e) {}
    }

    @Override
    public void deleteUser(User user)
    {
        try
        {
            String queryUser = "DELETE FROM users WHERE userID = ?";
            PreparedStatement stmtUser = _dataService.getConnection().prepareStatement(queryUser);
            stmtUser.setInt(1, user.getUserID());
            stmtUser.executeUpdate();

            if (user.getTypeID().equals("F"))
            {
                Faculty facultyUser = (Faculty) user;
                String deleteFacultyQuery = "DELETE FROM faculty WHERE facultyID = ?";
                PreparedStatement deleteFacultyStmt = _dataService.getConnection().prepareStatement(deleteFacultyQuery);
                deleteFacultyStmt.setInt(1, facultyUser.getFacultyID());
                deleteFacultyStmt.executeUpdate();
                
                String deleteInterestsQuery = "DELETE FROM facultyInterests WHERE facultyID = ?";
                PreparedStatement deleteInterestsStmt = _dataService.getConnection().prepareStatement(deleteInterestsQuery);
                deleteInterestsStmt.setInt(1, facultyUser.getFacultyID());
                deleteInterestsStmt.executeUpdate();

                String deleteAbstractQuery = "DELETE FROM facultyAbstract WHERE facultyID = ?";
                PreparedStatement deleteAbstractStmt = _dataService.getConnection().prepareStatement(deleteAbstractQuery);
                deleteAbstractStmt.setInt(1, facultyUser.getFacultyID());
                deleteAbstractStmt.executeUpdate();
            } 
            else if (user.getTypeID().equals("S")) 
            {
                Student studentUser = (Student) user;
                String deleteStudentQuery = "DELETE FROM student WHERE studentID = ?";
                PreparedStatement deleteStudentStmt = _dataService.getConnection().prepareStatement(deleteStudentQuery);
                deleteStudentStmt.setInt(1, studentUser.getStudentID());
                deleteStudentStmt.executeUpdate();

                String deleteInterestsQuery = "DELETE FROM studentinterests WHERE studentID = ?";
                PreparedStatement deleteInterestsStmt = _dataService.getConnection().prepareStatement(deleteInterestsQuery);
                deleteInterestsStmt.setInt(1, studentUser.getStudentID());
                deleteInterestsStmt.executeUpdate();
            }
            else if (user.getTypeID().equals("G")) 
            {
                Guest guestUser = (Guest) user;
                String query = "DELETE FROM guest WHERE guestID = ?";
                PreparedStatement stmt = _dataService.getConnection().prepareStatement(query);
                stmt.setInt(1, guestUser.getGuestID());
                stmt.executeUpdate();
            }
        }
        catch (SQLException e) {}
    }
}
