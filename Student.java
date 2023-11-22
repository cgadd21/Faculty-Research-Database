import java.util.List;

public class Student extends User {
    private int studentID;
    private String fname;
    private String lname;
    private String email;
    private String phoneNumber;
    private List<Interest> interests;
    
    public int getStudentID() 
    {
        return studentID;
    }

    public void setStudentID(int studentID) 
    {
        this.studentID = studentID;
    }
    
    public String getFname() 
    {
        return fname;
    }
    
    public void setFname(String fname) 
    {
        this.fname = fname;
    }
    
    public String getLname() 
    {
        return lname;
    }
    
    public void setLname(String lname) 
    {
        this.lname = lname;
    }
    
    public String getEmail() 
    {
        return email;
    }
    
    public void setEmail(String email) 
    {
        this.email = email;
    }
    
    public String getPhoneNumber() 
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    public List<Interest> getInterests() 
    {
        return interests;
    }
    
    public void setInterests(List<Interest> interests) 
    {
        this.interests = interests;
    }
}
