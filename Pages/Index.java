package Pages;

import Services.UserService.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Index extends JFrame
{
    public Index(IUserService _userService)
    {
        super("Faculty Research Database");
        setSize(630,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jIndex = new JPanel();
		jIndex.setLayout(new GridLayout(0,1));
        add(jIndex);
        setVisible(true);

        JButton jbLogin = new JButton("Login");
        JButton jbSignUp = new JButton("Sign Up");

        jbLogin.setFont(new Font("Courier", Font.PLAIN, 38));
        jbSignUp.setFont(new Font("Courier", Font.PLAIN, 38));

        jIndex.add(jbLogin);
        jIndex.add(jbSignUp);

        jbLogin.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae) 
        {
            new Login(_userService);
            setVisible(false);
        }});

        jbSignUp.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
        {
            new Account(_userService);
            setVisible(false);
        }});
    }
}
