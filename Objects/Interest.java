package Objects;

public class Interest 
{
    private int interestID;
    private String intDesc;

    public Interest(int interestID, String intDesc)
    {
        this.interestID = interestID;
        this.intDesc = intDesc;
    }
    
    public int getInterestID() 
    {
        return interestID;
    }
    
    public void setInterestID(int interestID) 
    {
        this.interestID = interestID;
    }
    
    public String getIntDesc() 
    {
        return intDesc;
    }
    
    public void setIntDesc(String intDesc) 
    {
        this.intDesc = intDesc;
    }
}
