package Services.SearchService;

import java.util.*;
import Models.*;
import Services.UserService.*;

public interface ISearchService 
{
    List<String> getSearch();
    List<User> getSearchResults();
    void search(IUserService _userService);
}
