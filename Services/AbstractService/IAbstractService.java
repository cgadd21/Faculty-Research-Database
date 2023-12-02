package Services.AbstractService;

import java.util.*;
import Models.*;

public interface IAbstractService 
{
    List<Abstract> getAbstractsList();
    Abstract getNewAbstract();
    void getAbstracts();
    void createAbstract();
}
