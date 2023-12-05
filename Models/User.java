package Models;

public class User 
{
    private int userID;
    private String typeID;
    private String username;
    private String salt;
    private String password;

    public User() {}

    public User(int userID, String typeID, String username, String password) 
    {
        this.userID = userID;
        this.typeID = typeID;
        this.username = username;
        this.password = password;
    }

    public User(int userID, String typeID, String username, String password, String salt) 
    {
        this.userID = userID;
        this.typeID = typeID;
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    @Override
    public String toString() 
    {
        return "User{" +
                "userID=" + userID +
                ", typeID='" + typeID + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getUserID() 
    {
        return userID;
    }

    public void setUserID(int userID) 
    {
        this.userID = userID;
    }

    public String getTypeID() 
    {
        return typeID;
    }

    public void setTypeID(String typeID) 
    {
        this.typeID = typeID;
    }

    public String getUsername() 
    {
        return username;
    }

    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getSalt() 
    {
        return salt;
    }
}
