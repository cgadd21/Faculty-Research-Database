package Services.SearchService;

import java.util.*;
import Models.*;

public interface ISearchService 
{
    List<String> getSearch();
    List<User> getSearchResults();
    void search();
}
