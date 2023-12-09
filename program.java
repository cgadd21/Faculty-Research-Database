import Pages.*;
import Services.UserService.*;

public class program
{
    IUserService _userService = new UserService();

    public program(){new Index(_userService);}

    public static void main(String[] args){new program();}
}
