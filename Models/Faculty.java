package Models;

import java.util.*;

public class Faculty extends User 
{
    private int facultyID;
    private String fname;
    private String lname;
    private String email;
    private String phoneNumber;
    private String location;
    private List<Interest> interests;
    private List<Abstract> abstracts;

    public Faculty() {}

    public Faculty(String typeID)
    {
        super(typeID);
    }

    public Faculty(int userID, String typeID, String username, String password, String salt, String encryptedPassword, int facultyID, String fname, String lname, String email, String phoneNumber, String location)
    {
        super(userID, typeID, username, password, salt, encryptedPassword);
        this.facultyID = facultyID;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
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

        StringBuilder abstractsStringBuilder = new StringBuilder();
        for (Abstract facultyAbstract : abstracts) 
        {
            abstractsStringBuilder.append(facultyAbstract.getProfessorAbstract()).append(", ");
        }

        String abstractsString = abstractsStringBuilder.toString();
        if (!abstractsString.isEmpty()) 
        {
            abstractsString = abstractsString.substring(0, abstractsString.length() - 2);
        }

        return "Faculty{" +
                "userID=" + getUserID() +
                ", typeID='" + getTypeID() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", facultyID='" + facultyID + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", location='" + location + '\'' +
                ", interests=[" + interestsString + ']' +
                ", abstracts=[" + abstractsString + ']' +
                '}';
    }

    @Override
    public String toSummary() 
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

        StringBuilder abstractsStringBuilder = new StringBuilder();
        for (Abstract facultyAbstract : abstracts) 
        {
            abstractsStringBuilder.append(facultyAbstract.getProfessorAbstract()).append(", ");
        }

        String abstractsString = abstractsStringBuilder.toString();
        if (!abstractsString.isEmpty()) 
        {
            abstractsString = abstractsString.substring(0, abstractsString.length() - 2);
        }

        return "Faculty{ Name: " + fname + " " + lname + " | Email: " + email + " | Phone Number: " + phoneNumber + " | Location: " + location + " | Interests: " + interestsString + " | Abstracts: " + abstractsString + " }";
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
