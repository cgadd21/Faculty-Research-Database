package Pages;

import javax.swing.*;
import java.awt.*;
import Services.UserService.*;

public class Login 
{
    public Login(IUserService _userService)
    {
        JPanel loginBox = new JPanel(new GridLayout(2,2));

        JLabel lblUser= new JLabel("Username  -> ");
        loginBox.add(lblUser);
        lblUser.setFont(new Font("Courier", Font.PLAIN, 32));

        JTextField tfUser = new JTextField("");
        loginBox.add(tfUser);
        tfUser.setFont(new Font("Courier", Font.PLAIN, 32));
        tfUser.setForeground(Color.BLUE);

        JLabel lblPassword = new JLabel("Password  -> ");
        loginBox.add(lblPassword);
        lblPassword.setFont(new Font("Courier", Font.PLAIN, 32));

        JTextField tfPassword = new JPasswordField("");
        loginBox.add(tfPassword);
        tfPassword.setFont(new Font("Courier", Font.PLAIN, 32));
        tfPassword.setForeground(Color.BLUE);

        JOptionPane.showMessageDialog(null, loginBox,"Please Login", JOptionPane.QUESTION_MESSAGE);

        _userService.getCurrentUser().setUsername(tfUser.getText());
        _userService.getCurrentUser().setPassword(tfPassword.getText());

        _userService.login();
    }
}
