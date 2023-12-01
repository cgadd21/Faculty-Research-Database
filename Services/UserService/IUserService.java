package Services.UserService;

import Models.*;

public interface IUserService 
{
    User login(String username, String password);
    User getUser(User user);
    User updateUser(User user);
    User createUser(User user);
    void deleteUser(User user);
}
