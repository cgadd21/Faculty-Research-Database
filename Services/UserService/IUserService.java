package Services.UserService;

import Models.*;

import java.util.*;

public interface IUserService 
{
    User getCurrentUser();
    List<Interest> getInterestList();
    Interest getNewInterest();
    List<Abstract> getAbstractsList();
    Abstract getNewAbstract();
    void login();
    void logout();
    void getUser();
    void updateUser();
    void createUser();
    void deleteUser();
    void getInterests();
    void createInterest();
    void getAbstracts();
    void createAbstract();
}
