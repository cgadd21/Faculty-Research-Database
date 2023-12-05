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
                _userService.getCurrentUser().getTypeID().equals("F") ? 10 : 
                _userService.getCurrentUser().getTypeID().equals("S") ? 9 : 
                _userService.getCurrentUser().getTypeID().equals("G") ? 7 : 
                3,
                2
            )
        );

        //user type

        JLabel lblUsername = new JLabel("Username");
        accountBox.add(lblUsername);
        lblUsername.setFont(new Font("Courier", Font.PLAIN, 32));

        JTextField tfUsername = new JTextField("");
        accountBox.add(tfUsername);
        tfUsername.setFont(new Font("Courier", Font.PLAIN, 32));
        tfUsername.setForeground(Color.BLUE);

        JOptionPane.showMessageDialog(null, accountBox,"Account", JOptionPane.QUESTION_MESSAGE);
    }    
}
