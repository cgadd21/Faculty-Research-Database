package Pages;

import Services.UserService.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame
{
    public Home(IUserService _userService)
    {
        super("Home");
        setSize(630,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jProgram = new JPanel();
		jProgram.setLayout(new GridLayout(0,1));
        add(jProgram);
        setVisible(true);

        JButton jbAccount = new JButton("Account");
        JButton jbSearch = new JButton("Search");
        JButton jbLogout = new JButton("Logout");

        jbAccount.setFont(new Font("Courier", Font.PLAIN, 38));
        jbSearch.setFont(new Font("Courier", Font.PLAIN, 38));
        jbLogout.setFont(new Font("Courier", Font.PLAIN, 38));

        jProgram.add(jbAccount);
        jProgram.add(jbSearch);
        jProgram.add(jbLogout);

        jbAccount.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){new Account(_userService);}});
        
        jbSearch.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){new Search(_userService);}});

        jbLogout.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae) 
        {
            _userService.logout();
            new Index(_userService);
            setVisible(false);
        }});
    }   
}
