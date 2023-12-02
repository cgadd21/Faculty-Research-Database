package Services.UserService;

import java.util.*;
import Models.*;

public interface IUserService 
{
    User getCurrentUser();
    List<Interest> getInterestList();
    Interest getNewInterest();
    void login();
    void logout();
    void getUser();
    void updateUser();
    void createUser();
    void deleteUser();
    void getInterests();
    void createInterest();
}
