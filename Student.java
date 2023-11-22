import java.util.List;

public class Student extends User 
{
    private int studentID;
    private String fname;
    private String lname;
    private String email;
    private String phoneNumber;
    private List<Interest> interests;

    public Student() {}

    public Student(int userID, String typeID, String username, String password, int studentID, String fname, String lname, String email, String phoneNumber) 
    {
        super(userID, typeID, username, password);
        this.studentID = studentID;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public String toString() 
    {
        StringBuilder interestsStringBuilder = new StringBuilder();
        for (Interest interest : interests) 
        {
            interestsStringBuilder.append(interest.getIntDesc()).append(", ");
        }

        String interestsString = interestsStringBuilder.toString();
        if (!interestsString.isEmpty()) 
        {
            interestsString = interestsString.substring(0, interestsString.length() - 2);
        }

        return "Student{" +
                "userID=" + getUserID() +
                ", typeID='" + getTypeID() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", studentID='" + studentID + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", interests=[" + interestsString + ']' +
                '}';
    }
    
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
