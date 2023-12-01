package Services.UserService;

import Models.*;

public interface IUserService 
{
    User getCurrentUser();
    void login();
    void getUser();
    void updateUser();
    void createUser();
    void deleteUser();
}
