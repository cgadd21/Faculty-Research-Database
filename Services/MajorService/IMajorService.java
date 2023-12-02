package Services.MajorService;

import java.util.*;
import Models.*;

public interface IMajorService 
{
    List<Major> getMajorsList();
    Major getNewMajor();
    void getMajors();
    void createMajor();
}
