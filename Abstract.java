public class Abstract 
{
    private int abstractID;
    private String professorAbstract;

    public Abstract() {}

    public Abstract(int abstractID, String professorAbstract)
    {
        this.abstractID = abstractID;
        this.professorAbstract = professorAbstract;
    }
    
    public int getAbstractID() 
    {
        return abstractID;
    }
    
    public void setAbstractID(int abstractID) 
    {
        this.abstractID = abstractID;
    }
    
    public String getProfessorAbstract() 
    {
        return professorAbstract;
    }
    
    public void setProfessorAbstract(String professorAbstract) 
    {
        this.professorAbstract = professorAbstract;
    }
}
