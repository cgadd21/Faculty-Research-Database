package Pages;

import javax.swing.*;
import java.awt.*;
import Services.UserService.*;

public class Login 
{
    public Login(IUserService _userService)
    {
        Font myFontForOutput = new Font("Courier", Font.PLAIN, 32);

        JPanel Loginbox = new JPanel(new GridLayout(2,2));

        JLabel lblUser= new JLabel("Username  -> ");
        Loginbox.add(lblUser);
        lblUser.setFont(myFontForOutput);

        JTextField tfUser = new JTextField("");
        Loginbox.add(tfUser);
        tfUser.setFont(myFontForOutput);
        tfUser.setForeground(Color.BLUE);

        JLabel lblPassword = new JLabel("Password  -> ");
        Loginbox.add(lblPassword);
        lblPassword.setFont(myFontForOutput);

        JTextField tfPassword = new JPasswordField("");
        Loginbox.add(tfPassword);
        tfPassword.setFont(myFontForOutput);
        tfPassword.setForeground(Color.BLUE);

        JOptionPane.showMessageDialog(null, Loginbox,"Please Login", JOptionPane.QUESTION_MESSAGE);

        _userService.getCurrentUser().setUsername(tfUser.getText());
        _userService.getCurrentUser().setPassword(tfPassword.getText());

        _userService.login();
    }
}
