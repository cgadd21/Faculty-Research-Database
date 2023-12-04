package Pages;

import javax.swing.*;
import java.awt.*;
import Services.UserService.*;

public class Search 
{
    public Search(IUserService _userService)
    {
        JPanel searchBox = new JPanel(new GridLayout(2,2));

        JOptionPane.showMessageDialog(null, searchBox,"Search", JOptionPane.QUESTION_MESSAGE);
    }
}
