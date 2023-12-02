package Models;

public class Major 
{
    private int majorID;
    private String majorDescription;

    public Major() {}

    public Major(int majorID, String majorDescription)
    {
        this.majorID = majorID;
        this.majorDescription = majorDescription;
    }
    
    public int getMajorID() 
    {
        return majorID;
    }

    public void setMajorID(int majorID) 
    {
        this.majorID = majorID;
    }

    public String getMajorDescription() 
    {
        return majorDescription;
    }

    public void setMajorDescription(String majorDescription) 
    {
        this.majorDescription = majorDescription;
    }
    
}
