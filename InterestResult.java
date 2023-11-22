public class InterestResult {
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

    public String getInterest() 
    {
        return interest;
    }

    public String getLocation() 
    {
        return location;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }

    public String getEmail() 
    {
        return email;
    }
}
