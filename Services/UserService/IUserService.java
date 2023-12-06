package Services.UserService;

import Models.*;

public interface IUserService 
{
    User getCurrentUser();
    void setCurrentUser(User user);
    void login();
    void logout();
    void getUser();
    void updateUser();
    void createUser();
    void deleteUser();
}
