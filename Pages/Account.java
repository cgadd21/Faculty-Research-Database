package Pages;

import javax.swing.*;
import java.awt.*;
import Services.UserService.*;

public class Account 
{
    public Account(IUserService _userService)
    {   
        JPanel accountBox = new JPanel
        (
            new GridLayout
            (
                _userService.getCurrentUser().getTypeID().equals("F") ? 12 : 
                _userService.getCurrentUser().getTypeID().equals("S") ? 11 : 
                _userService.getCurrentUser().getTypeID().equals("G") ? 9 : 
                4,
                2
            )
        );

        JOptionPane.showMessageDialog(null, accountBox,"Account", JOptionPane.QUESTION_MESSAGE);
    }    
}
