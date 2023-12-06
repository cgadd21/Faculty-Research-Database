package Pages;

import javax.swing.*;
import java.awt.*;
import Models.*;
import Services.UserService.*;

public class Account 
{
    public Account(IUserService _userService)
    {
        User accountUser = new User();

        if(_userService.getCurrentUser().getUserID() == 0)
        {
            JPanel initialBox = new JPanel(new GridLayout(3,2));

            JLabel lblUserType = new JLabel("User Type");
            initialBox.add(lblUserType);
            lblUserType.setFont(new Font("Courier", Font.PLAIN, 32));

            String[] userTypes = {"Faculty","Student","Guest"};
            JComboBox<String> cbUserType = new JComboBox<>(userTypes);
            initialBox.add(cbUserType);
            cbUserType.setFont(new Font("Courier", Font.PLAIN, 32));
            cbUserType.setForeground(Color.BLUE);

            JLabel lblUsername = new JLabel("Username");
            initialBox.add(lblUsername);
            lblUsername.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfUsername = new JTextField("");
            initialBox.add(tfUsername);
            tfUsername.setFont(new Font("Courier", Font.PLAIN, 32));
            tfUsername.setForeground(Color.BLUE);

            JLabel lblPassword = new JLabel("Password");
            initialBox.add(lblPassword);
            lblPassword.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfPassword = new JTextField("");
            initialBox.add(tfPassword);
            tfPassword.setFont(new Font("Courier", Font.PLAIN, 32));
            tfPassword.setForeground(Color.BLUE);

            JOptionPane.showMessageDialog(null, initialBox,"Sign Up", JOptionPane.QUESTION_MESSAGE);

            accountUser = new User
            (
                cbUserType.getSelectedItem().equals("Faculty") ? "F" :
                cbUserType.getSelectedItem().equals("Student") ? "S" :
                cbUserType.getSelectedItem().equals("Guest") ? "G" :
                null,
                tfUsername.getText(),
                tfPassword.getText()
            );

            accountUser = 
            (
                accountUser.getTypeID().equals("F") ? 
                new Faculty(accountUser.getTypeID(),accountUser.getUsername(),accountUser.getPassword()) : 
                accountUser.getTypeID().equals("S") ? 
                new Student(accountUser.getTypeID(),accountUser.getUsername(),accountUser.getPassword()) :
                accountUser.getTypeID().equals("G") ? 
                new Guest(accountUser.getTypeID(),accountUser.getUsername(),accountUser.getPassword()) :
                new User(accountUser.getTypeID(),accountUser.getUsername(),accountUser.getPassword())
            );

        }
        else
        {
            accountUser = _userService.getCurrentUser();
        }

        if(accountUser instanceof Faculty)
        {
            Faculty accountFaculty = (Faculty) accountUser;

            JPanel facultyBox = new JPanel(new GridLayout (5,5));

            JLabel lblFname = new JLabel("First Name");
            facultyBox.add(lblFname);
            lblFname.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfFname = new JTextField(accountFaculty.getFname());
            facultyBox.add(tfFname);
            tfFname.setFont(new Font("Courier", Font.PLAIN, 32));
            tfFname.setForeground(Color.BLUE);

            JLabel lblLname = new JLabel("Last Name");
            facultyBox.add(lblLname);
            lblLname.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfLname = new JTextField(accountFaculty.getLname());
            facultyBox.add(tfLname);
            tfLname.setFont(new Font("Courier", Font.PLAIN, 32));
            tfLname.setForeground(Color.BLUE);

            JLabel lblEmail = new JLabel("Email");
            facultyBox.add(lblEmail);
            lblEmail.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfEmail = new JTextField(accountFaculty.getEmail());
            facultyBox.add(tfEmail);
            tfEmail.setFont(new Font("Courier", Font.PLAIN, 32));
            tfEmail.setForeground(Color.BLUE);

            JLabel lblPhoneNumber = new JLabel("Phone Number");
            facultyBox.add(lblPhoneNumber);
            lblPhoneNumber.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfPhoneNumber = new JTextField(accountFaculty.getPhoneNumber());
            facultyBox.add(tfPhoneNumber);
            tfPhoneNumber.setFont(new Font("Courier", Font.PLAIN, 32));
            tfPhoneNumber.setForeground(Color.BLUE);

            JLabel lblLocation = new JLabel("Location");
            facultyBox.add(lblLocation);
            lblLocation.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfLocation = new JTextField(accountFaculty.getLocation());
            facultyBox.add(tfLocation);
            tfLocation.setFont(new Font("Courier", Font.PLAIN, 32));
            tfLocation.setForeground(Color.BLUE);

            JLabel lblInterests = new JLabel("Interests");
            //facultyBox.add(lblInterests);
            lblInterests.setFont(new Font("Courier", Font.PLAIN, 32));

            //drop down
            //new option

            JLabel lblAbstracts = new JLabel("Abstracts");
            //facultyBox.add(lblAbstracts);
            lblAbstracts.setFont(new Font("Courier", Font.PLAIN, 32));

            //drop down
            //new option

            JOptionPane.showMessageDialog(null, facultyBox,"Faculty", JOptionPane.QUESTION_MESSAGE);

            accountFaculty = new Faculty(accountFaculty.getUserID(), accountFaculty.getTypeID(), accountFaculty.getUsername(), accountFaculty.getPassword(), accountFaculty.getSalt(), accountFaculty.getEncryptedPassword(), accountFaculty.getFacultyID(), tfFname.getText(), tfLname.getText(), tfEmail.getText(), tfPhoneNumber.getText(), tfLocation.getText());

            //set interests & abstracts

            _userService.setCurrentUser(accountFaculty);
        }
        else if(accountUser instanceof Student)
        {
            Student accountStudent = (Student) accountUser;

            JPanel studentBox = new JPanel(new GridLayout (4,4));

            JLabel lblFname = new JLabel("First Name");
            studentBox.add(lblFname);
            lblFname.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfFname = new JTextField(accountStudent.getFname());
            studentBox.add(tfFname);
            tfFname.setFont(new Font("Courier", Font.PLAIN, 32));
            tfFname.setForeground(Color.BLUE);

            JLabel lblLname = new JLabel("Last Name");
            studentBox.add(lblLname);
            lblLname.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfLname = new JTextField(accountStudent.getLname());
            studentBox.add(tfLname);
            tfLname.setFont(new Font("Courier", Font.PLAIN, 32));
            tfLname.setForeground(Color.BLUE);

            JLabel lblEmail = new JLabel("Email");
            studentBox.add(lblEmail);
            lblEmail.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfEmail = new JTextField(accountStudent.getEmail());
            studentBox.add(tfEmail);
            tfEmail.setFont(new Font("Courier", Font.PLAIN, 32));
            tfEmail.setForeground(Color.BLUE);

            JLabel lblPhoneNumber = new JLabel("Phone Number");
            studentBox.add(lblPhoneNumber);
            lblPhoneNumber.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfPhoneNumber = new JTextField(accountStudent.getPhoneNumber());
            studentBox.add(tfPhoneNumber);
            tfPhoneNumber.setFont(new Font("Courier", Font.PLAIN, 32));
            tfPhoneNumber.setForeground(Color.BLUE);

            JLabel lblInterests = new JLabel("Interests");
            //studentBox.add(lblInterests);
            lblInterests.setFont(new Font("Courier", Font.PLAIN, 32));

            //drop down
            //new option

            JLabel lblMajors = new JLabel("Majors");
            //studentBox.add(lblMajors);
            lblMajors.setFont(new Font("Courier", Font.PLAIN, 32));

            //drop down
            //new option

            JOptionPane.showMessageDialog(null, studentBox,"Student", JOptionPane.QUESTION_MESSAGE);

            accountStudent = new Student(accountStudent.getUserID(), accountStudent.getTypeID(), accountStudent.getUsername(), accountStudent.getPassword(), accountStudent.getSalt(), accountStudent.getEncryptedPassword(), accountStudent.getStudentID(), tfFname.getText(), tfLname.getText(), tfEmail.getText(), tfPhoneNumber.getText());

            //set interests & abstracts

            _userService.setCurrentUser(accountStudent);
        }
        else if(accountUser instanceof Guest)
        {
            Guest accountGuest = (Guest) accountUser;

            JPanel guestBox = new JPanel(new GridLayout (4,4));

            JLabel lblBusiness = new JLabel("Business");
            guestBox.add(lblBusiness);
            lblBusiness.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfBusiness = new JTextField(accountGuest.getBusiness());
            guestBox.add(tfBusiness);
            tfBusiness.setFont(new Font("Courier", Font.PLAIN, 32));
            tfBusiness.setForeground(Color.BLUE);

            JLabel lblFname = new JLabel("First Name");
            guestBox.add(lblFname);
            lblFname.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfFname = new JTextField(accountGuest.getFname());
            guestBox.add(tfFname);
            tfFname.setFont(new Font("Courier", Font.PLAIN, 32));
            tfFname.setForeground(Color.BLUE);

            JLabel lblLname = new JLabel("Last Name");
            guestBox.add(lblLname);
            lblLname.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfLname = new JTextField(accountGuest.getLname());
            guestBox.add(tfLname);
            tfLname.setFont(new Font("Courier", Font.PLAIN, 32));
            tfLname.setForeground(Color.BLUE);

            JLabel lblContactInfo = new JLabel("Contact Info");
            guestBox.add(lblContactInfo);
            lblContactInfo.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfContactInfo = new JTextField(accountGuest.getContactInfo());
            guestBox.add(tfContactInfo);
            tfContactInfo.setFont(new Font("Courier", Font.PLAIN, 32));
            tfContactInfo.setForeground(Color.BLUE);

            accountGuest = new Guest(accountGuest.getUserID(), accountGuest.getTypeID(), accountGuest.getUsername(), accountGuest.getPassword(), accountGuest.getSalt(), accountGuest.getEncryptedPassword(), accountGuest.getGuestID(), tfBusiness.getText(), tfFname.getText(), tfLname.getText(), tfContactInfo.getText());

            _userService.setCurrentUser(accountGuest);
        }

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
