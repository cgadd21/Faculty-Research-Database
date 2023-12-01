package Services.SearchService;

import java.util.*;

import Models.*;

public interface ISearchService 
{
    List<Interest> getInterestList();
    List<InterestResult> getInterestResultList();
    List<Abstract> getAbstractsList();
    List<AbstractResult> getAbstractsResultList();
    void getInterests();
    void getInterests(User user, List<Interest> interests);
    void createInterest(Interest interest);
    void getAbstracts();
    void getAbstracts(String search);
    void createAbstract(Abstract facultyAbstract);
}
