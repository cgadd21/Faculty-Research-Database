package Services.UserService;

import Models.*;

public interface IUserService 
{
    User getUser();
    void setUser(User user);
    void login();
    void getUser(User user);
    void updateUser(User user);
    void createUser(User user);
    void deleteUser(User user);
}
