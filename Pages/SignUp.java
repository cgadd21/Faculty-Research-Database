package Pages;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Models.*;
import Services.UserService.*;

public class SignUp extends JFrame
{
    User signUpUser = new User();
    String[] userTypes = {"Faculty","Student","Guest"};

    public SignUp(IUserService _userService)
    {
        super("Sign Up");
        setSize(630,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jSignUp = new JPanel();
        jSignUp.setLayout(new GridLayout(3,1));
        add(jSignUp);
        setVisible(true);

        JLabel lblUserType = new JLabel("User Type");
        JComboBox<String> cbUserType = new JComboBox<>(userTypes);
        JButton btnNext = new JButton("Next");

        lblUserType.setFont(new Font("Courier", Font.PLAIN, 32));
        cbUserType.setFont(new Font("Courier", Font.PLAIN, 32));
        btnNext.setFont(new Font("Courier", Font.PLAIN, 32));

        cbUserType.setForeground(Color.BLUE);

        jSignUp.add(lblUserType);
        jSignUp.add(cbUserType);
        jSignUp.add(btnNext);
        
        btnNext.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
        {
            signUpUser = new User
            (
                cbUserType.getSelectedItem().equals("Faculty") ? "F" :
                cbUserType.getSelectedItem().equals("Student") ? "S" :
                cbUserType.getSelectedItem().equals("Guest") ? "G" :
                null
            );

            signUpUser = 
            (
                signUpUser.getTypeID().equals("F") ? 
                new Faculty(signUpUser.getTypeID()) : 
                signUpUser.getTypeID().equals("S") ? 
                new Student(signUpUser.getTypeID()) :
                signUpUser.getTypeID().equals("G") ? 
                new Guest(signUpUser.getTypeID()) :
                new User(signUpUser.getTypeID())
            );

            _userService.setCurrentUser(signUpUser);
            new Account(_userService);
            setVisible(false);
        }});
    }
}
