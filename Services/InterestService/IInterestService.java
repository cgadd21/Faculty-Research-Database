package Services.InterestService;

import java.util.*;
import Models.*;

public interface IInterestService 
{
    List<Interest> getInterestList();
    Interest getNewInterest();
    void getInterests();
    void createInterest();
}
