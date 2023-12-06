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

        JLabel lblUserType = new JLabel("User Type");
        accountBox.add(lblUserType);
        lblUserType.setFont(new Font("Courier", Font.PLAIN, 32));

        String[] userTypes = {"F","S","G"};
        JComboBox<String> cbUserType = new JComboBox<>(userTypes);
        accountBox.add(cbUserType);
        cbUserType.setFont(new Font("Courier", Font.PLAIN, 32));
        cbUserType.setForeground(Color.BLUE);
        cbUserType.setSelectedIndex
        (
            _userService.getCurrentUser().getTypeID().equals("F") ? 0 : 
            _userService.getCurrentUser().getTypeID().equals("S") ? 1 : 
            _userService.getCurrentUser().getTypeID().equals("G") ? 2 : 
            -1
        );

        JLabel lblUsername = new JLabel("Username");
        accountBox.add(lblUsername);
        lblUsername.setFont(new Font("Courier", Font.PLAIN, 32));

        JTextField tfUsername = new JTextField(_userService.getCurrentUser().getUsername());
        accountBox.add(tfUsername);
        tfUsername.setFont(new Font("Courier", Font.PLAIN, 32));
        tfUsername.setForeground(Color.BLUE);

        JLabel lblPassword = new JLabel("Password");
        accountBox.add(lblPassword);
        lblPassword.setFont(new Font("Courier", Font.PLAIN, 32));

        JTextField tfPassword = new JTextField(_userService.getCurrentUser().getPassword());
        accountBox.add(tfPassword);
        tfPassword.setFont(new Font("Courier", Font.PLAIN, 32));
        tfPassword.setForeground(Color.BLUE);

        if
        (
            _userService.getCurrentUser().getTypeID().equals("F") || 
            _userService.getCurrentUser().getTypeID().equals("S") || 
            _userService.getCurrentUser().getTypeID().equals("G")
        )
        {
            JLabel lblFName = new JLabel("First Name");
            accountBox.add(lblFName);
            lblFName.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfFName = new JTextField("");
            accountBox.add(tfFName);
            tfFName.setFont(new Font("Courier", Font.PLAIN, 32));
            tfFName.setForeground(Color.BLUE);
        }

        if
        (
            _userService.getCurrentUser().getTypeID().equals("F") ||
            _userService.getCurrentUser().getTypeID().equals("S") ||
            _userService.getCurrentUser().getTypeID().equals("G")
        )
        {
            JLabel lblLName = new JLabel("Last Name");
            accountBox.add(lblLName);
            lblLName.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfLName = new JTextField("");
            accountBox.add(tfLName);
            tfLName.setFont(new Font("Courier", Font.PLAIN, 32));
            tfLName.setForeground(Color.BLUE);
        }

        if
        (
            _userService.getCurrentUser().getTypeID().equals("F") ||
            _userService.getCurrentUser().getTypeID().equals("S")
        )
        {
            JLabel lblEmail = new JLabel("Email");
            accountBox.add(lblEmail);
            lblEmail.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfEmail = new JTextField("");
            accountBox.add(tfEmail);
            tfEmail.setFont(new Font("Courier", Font.PLAIN, 32));
            tfEmail.setForeground(Color.BLUE);
        }

        if
        (
            _userService.getCurrentUser().getTypeID().equals("F") ||
            _userService.getCurrentUser().getTypeID().equals("S")
        )
        {
            JLabel lblPhoneNumber = new JLabel("Phone Number");
            accountBox.add(lblPhoneNumber);
            lblPhoneNumber.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfPhoneNumber = new JTextField("");
            accountBox.add(tfPhoneNumber);
            tfPhoneNumber.setFont(new Font("Courier", Font.PLAIN, 32));
            tfPhoneNumber.setForeground(Color.BLUE);
        }

        if
        (
            _userService.getCurrentUser().getTypeID().equals("F")
        )
        {
            JLabel lblLocation = new JLabel("Location");
            accountBox.add(lblLocation);
            lblLocation.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfLocation = new JTextField("");
            accountBox.add(tfLocation);
            tfLocation.setFont(new Font("Courier", Font.PLAIN, 32));
            tfLocation.setForeground(Color.BLUE);
        }

        if
        (
            _userService.getCurrentUser().getTypeID().equals("G")
        )
        {
            JLabel lblContactInfo = new JLabel("Contact Info");
            accountBox.add(lblContactInfo);
            lblContactInfo.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfContactInfo = new JTextField("");
            accountBox.add(tfContactInfo);
            tfContactInfo.setFont(new Font("Courier", Font.PLAIN, 32));
            tfContactInfo.setForeground(Color.BLUE);
        }

        if
        (
            _userService.getCurrentUser().getTypeID().equals("F") ||
            _userService.getCurrentUser().getTypeID().equals("S")
        )
        {
            JLabel lblInterests = new JLabel("Interests");
            accountBox.add(lblInterests);
            lblInterests.setFont(new Font("Courier", Font.PLAIN, 32));

            //drop down
            //new option
        }

        if
        (
            _userService.getCurrentUser().getTypeID().equals("F")
        )
        {
            JLabel lblAbstracts = new JLabel("Abstracts");
            accountBox.add(lblAbstracts);
            lblAbstracts.setFont(new Font("Courier", Font.PLAIN, 32));

            //drop down
            //new option
        }

        if
        (
            _userService.getCurrentUser().getTypeID().equals("S")
        )
        {
            JLabel lblMajors = new JLabel("Majors");
            accountBox.add(lblMajors);
            lblMajors.setFont(new Font("Courier", Font.PLAIN, 32));

            //drop down
            //new option
        }

        //delete option

        JOptionPane.showMessageDialog(null, accountBox,"Account", JOptionPane.QUESTION_MESSAGE);

        if(_userService.getCurrentUser().getUserID() == 0)
        {
            _userService.createUser();
        }
        else
        {
            _userService.updateUser();
        }
    }    
}
