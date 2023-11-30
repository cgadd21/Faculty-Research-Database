package Objects;
public class AbstractResult 
{
    private String Abstract;
    private String Professors;

    public AbstractResult(String facultyAbstract, String professors)
    {
        this.Abstract = facultyAbstract;
        this.Professors = professors;
    }

    public String getAbstract() 
    {
        return Abstract;
    }

    public void setAbstract(String facultyAbstract) 
    {
        Abstract = facultyAbstract;
    }
    
    public String getProfessors() 
    {
        return Professors;
    }

    public void setProfessors(String professors) 
    {
        Professors = professors;
    }
}
