import Services.SearchService.*;
import Services.UserService.*;
import Pages.*;
import Models.*;

public class program 
{
    public static void main(String[] args) 
    {
        IUserService _userService = new UserService();
        new Login(_userService);
        System.out.println(_userService.getCurrentUser().toString());

        ISearchService _searchService = new SearchService();
        _searchService.getSearch().add("Java");
        _searchService.search(_userService);

        for (User user : _searchService.getSearchResults()) 
        {
            System.out.println(user);
        }
    }
}
