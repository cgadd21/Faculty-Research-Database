import Services.UserService.*;
import Pages.*;

public class program 
{
    public static void main(String[] args) 
    {
        IUserService _userService = new UserService();
        new Login(_userService);
        System.out.println(_userService.getCurrentUser().toString());
    }
}
