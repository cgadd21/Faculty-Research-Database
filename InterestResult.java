public class InterestResult 
{
    private String name;
    private String interest;
    private String location;
    private String phoneNumber;
    private String email;

    public InterestResult(String name, String interest, String location, String phoneNumber, String email) 
    {
        this.name = name;
        this.interest = interest;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() 
    {
        return name;
    }
    
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getInterest() 
    {
        return interest;
    }
    
    public void setInterest(String interest) 
    {
        this.interest = interest;
    }

    public String getLocation() 
    {
        return location;
    }

    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }
}
