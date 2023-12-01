package Services.SearchService;

import java.util.*;

import Models.*;

public interface ISearchService 
{
    List<Interest> getInterests();
    List<InterestResult> getInterests(User user, List<Interest> interests);
    List<Interest> createInterest(Interest interest);
    List<Abstract> getAbstracts();
    List<AbstractResult> getAbstracts(String search);
    List<Abstract> createAbstract(Abstract facultyAbstract);
}
