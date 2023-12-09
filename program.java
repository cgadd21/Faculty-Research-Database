import Pages.*;
import Services.UserService.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class program extends JFrame
{
    IUserService _userService = new UserService();

    public program()
    {
        super("Faculty Research Database");
        setSize(630,400);
		setLocation(200,380);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jpProgram = new JPanel();
		jpProgram.setLayout(new GridLayout(0,1));
        add(jpProgram);
        setVisible(true);

        JButton jbAccount = new JButton("Sign Up");
        JButton jbLogin = new JButton("Login");
        JButton jbSearch = new JButton("Search");
        JButton jbLogout = new JButton("Logout");

        jbAccount.setFont(new Font("Courier", Font.PLAIN, 38));
        jbLogin.setFont(new Font("Courier", Font.PLAIN, 38));
        jbSearch.setFont(new Font("Courier", Font.PLAIN, 38));
        jbLogout.setFont(new Font("Courier", Font.PLAIN, 38));

        jpProgram.add(jbAccount);
        jpProgram.add(jbLogin);

        jbAccount.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){new Account(_userService);}});

        jbLogin.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae) 
        {
            new Login(_userService);
            if(_userService.getCurrentUser().getUserID() != 0)
            {
                jpProgram.remove(jbLogin);
                jpProgram.add(jbSearch);
                jpProgram.add(jbLogout);
                jbAccount.setText("Account");
            }
        }});
        
        jbSearch.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){new Search(_userService);}});

        jbLogout.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae) 
        {
            _userService.logout();
            jpProgram.add(jbLogin);
            jpProgram.remove(jbSearch);
            jpProgram.remove(jbLogout);
            jbAccount.setText("Sign Up");
        }});
        
    }

    public static void main(String[] args) 
    {
        new program();
    }
}
