import java.util.List;

public class Faculty extends User {
    private int facultyID;
    private String fname;
    private String lname;
    private String email;
    private String phoneNumber;
    private String location;
    private List<Interest> interests;
    private List<Abstract> abstracts;

    public Faculty() {}

    public Faculty(int userID, String typeID, String username, String password, String location)
    {
        super(userID, typeID, username, password);
        this.location = location;
    }

    @Override
    public String toString() 
    {
        return "Faculty{" +
                "userID=" + getUserID() +
                ", typeID='" + getTypeID() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
    
    public int getFacultyID() 
    {
        return facultyID;
    }
    
    public void setFacultyID(int facultyID) 
    {
        this.facultyID = facultyID;
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
    
    public String getLocation() 
    {
        return location;
    }
    
    public void setLocation(String location) 
    {
        this.location = location;
    }
    
    public List<Interest> getInterests() 
    {
        return interests;
    }
    
    public void setInterests(List<Interest> interests) 
    {
        this.interests = interests;
    }
    
    public List<Abstract> getAbstracts() 
    {
        return abstracts;
    }
    
    public void setAbstracts(List<Abstract> abstracts) 
    {
        this.abstracts = abstracts;
    }
}
