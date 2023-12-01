import Services.UserService.*;

public class program 
{
    public static void main(String[] args) 
    {
        IUserService _userService = new UserService();
        _userService.getCurrentUser().setUsername("jmd4173");
        _userService.getCurrentUser().setPassword("StudentPass");
        _userService.login();
        System.out.println(_userService.getCurrentUser().toString());
    }
}
