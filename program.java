import Services.UserService.*;

public class program {
    public static void main(String[] args) {
        IUserService _userService = new UserService();
        _userService.getCurrentUser().setUsername("Jimhab");
        _userService.getCurrentUser().setPassword("FacultyPass");
        _userService.login();
        System.out.println(_userService.getCurrentUser().toString());
    }
}
