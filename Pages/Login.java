package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Services.UserService.*;

public class Login extends JFrame
{
    public Login(IUserService _userService)
    {
        super("Login");
        setSize(630,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jLogin = new JPanel();
        jLogin.setLayout(new GridLayout(3,2));
        add(jLogin);
        setVisible(true);

        JLabel lblUsername = new JLabel("Username ");
        JTextField tfUsername = new JTextField("");
        JLabel lblPassword = new JLabel("Password ");
        JTextField tfPassword = new JPasswordField("");
        JButton btnCancel = new JButton("Cancel");
        JButton btnLogin = new JButton("Login");

        lblUsername.setFont(new Font("Courier", Font.PLAIN, 32));
        tfUsername.setFont(new Font("Courier", Font.PLAIN, 32));
        lblPassword.setFont(new Font("Courier", Font.PLAIN, 32));
        tfPassword.setFont(new Font("Courier", Font.PLAIN, 32));
        btnCancel.setFont(new Font("Courier", Font.PLAIN, 32));
        btnLogin.setFont(new Font("Courier", Font.PLAIN, 32));

        tfPassword.setForeground(Color.BLUE);
        tfUsername.setForeground(Color.BLUE);
        
        jLogin.add(lblUsername);
        jLogin.add(tfUsername);
        jLogin.add(lblPassword);
        jLogin.add(tfPassword);
        jLogin.add(btnCancel);
        jLogin.add(btnLogin);

        btnCancel.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
        {
            new Index(_userService);
            setVisible(false);
        }});

        btnLogin.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
        {
            _userService.getCurrentUser().setUsername(tfUsername.getText());
            _userService.getCurrentUser().setPassword(tfPassword.getText());
            _userService.login();
            if(_userService.getCurrentUser().getUserID() != 0)
            {
                new Home(_userService);
                setVisible(false);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Incorrect Username or Password","Error", JOptionPane.ERROR_MESSAGE);
            }
        }});
    }
}
