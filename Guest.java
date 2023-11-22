public class Guest extends User {
    private int guestID;
    private String business;
    private String fname;
    private String lname;
    private String contactInfo;

    public int getGuestID() 
    {
        return guestID;
    }

    public void setGuestID(int guestID) 
    {
        this.guestID = guestID;
    }

    public String getBusiness() 
    {
        return business;
    }

    public void setBusiness(String business) 
    {
        this.business = business;
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

    public String getContactInfo() 
    {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) 
    {
        this.contactInfo = contactInfo;
    }
}
