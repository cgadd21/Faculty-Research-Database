package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import Models.*;
import Services.UserService.*;
import Services.InterestService.*;
import Services.AbstractService.*;
import Services.FileService.*;
import Services.MajorService.*;

public class Account 
{
    IInterestService _interestService = new InterestService();
    IAbstractService _abstractService = new AbstractService();
    IFileService _fileService = new FileService();
    IMajorService _majorService = new MajorService();
    boolean delete = false;

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

            JOptionPane.showMessageDialog(null, initialBox,"Sign Up", JOptionPane.QUESTION_MESSAGE);

            accountUser = new User
            (
                cbUserType.getSelectedItem().equals("Faculty") ? "F" :
                cbUserType.getSelectedItem().equals("Student") ? "S" :
                cbUserType.getSelectedItem().equals("Guest") ? "G" :
                null
            );

            accountUser = 
            (
                accountUser.getTypeID().equals("F") ? 
                new Faculty(accountUser.getTypeID()) : 
                accountUser.getTypeID().equals("S") ? 
                new Student(accountUser.getTypeID()) :
                accountUser.getTypeID().equals("G") ? 
                new Guest(accountUser.getTypeID()) :
                new User(accountUser.getTypeID())
            );

        }
        else
        {
            accountUser = _userService.getCurrentUser();
        }

        if(accountUser instanceof Faculty)
        {
            Faculty accountFaculty = (Faculty) accountUser;

            JPanel facultyBox = new JPanel(new GridLayout (accountFaculty.getUserID() != 0 ? 8 : 7,2));

            JLabel lblUsername = new JLabel("Username");
            facultyBox.add(lblUsername);
            lblUsername.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfUsername = new JTextField(accountFaculty.getUsername());
            facultyBox.add(tfUsername);
            tfUsername.setFont(new Font("Courier", Font.PLAIN, 32));
            tfUsername.setForeground(Color.BLUE);

            JLabel lblPassword = new JLabel("Password");
            facultyBox.add(lblPassword);
            lblPassword.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfPassword = new JTextField(accountFaculty.getPassword());
            facultyBox.add(tfPassword);
            tfPassword.setFont(new Font("Courier", Font.PLAIN, 32));
            tfPassword.setForeground(Color.BLUE);

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

            JLabel lblDelete = new JLabel("Delete");
            if(accountFaculty.getUserID() != 0) facultyBox.add(lblDelete);
            lblDelete.setFont(new Font("Courier", Font.PLAIN, 32));

            JCheckBox cbDelete = new JCheckBox("");
            if(accountFaculty.getUserID() != 0) facultyBox.add(cbDelete);
            cbDelete.addActionListener
            (
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                        delete = true;
                    }
                }
            );

            JOptionPane.showMessageDialog(null, facultyBox,"Faculty", JOptionPane.QUESTION_MESSAGE);

            accountFaculty = new Faculty(accountFaculty.getUserID(), accountFaculty.getTypeID(), accountFaculty.getUsername(), accountFaculty.getPassword(), accountFaculty.getSalt(), accountFaculty.getEncryptedPassword(), accountFaculty.getFacultyID(), tfFname.getText(), tfLname.getText(), tfEmail.getText(), tfPhoneNumber.getText(), tfLocation.getText());

            if(!delete)
            {
                _interestService.getInterests();

                List<Interest> facultyInterests = new ArrayList<>();

                JPanel interestBox = new JPanel(new GridLayout (_interestService.getInterestList().size() + 1,2));

                JLabel lblInterest = new JLabel("Interest");
                interestBox.add(lblInterest);
                lblInterest.setFont(new Font("Courier", Font.PLAIN, 32));

                JButton btnNewInterest = new JButton("New");
                btnNewInterest.setFont(new Font("Courier", Font.PLAIN, 32));
                interestBox.add(btnNewInterest);
                btnNewInterest.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae) 
                    {
                        _interestService.getNewInterest().setIntDesc(null);
                        _interestService.getNewInterest().setInterestID(0);

                        JPanel newInterestBox = new JPanel(new GridLayout(1,2));

                        JLabel lblNewInterest = new JLabel("New Interest");
                        newInterestBox.add(lblNewInterest);
                        lblNewInterest.setFont(new Font("Courier", Font.PLAIN, 32));

                        JTextField tfNewInterest = new JTextField("");
                        newInterestBox.add(tfNewInterest);
                        tfNewInterest.setFont(new Font("Courier", Font.PLAIN, 32));
                        tfNewInterest.setForeground(Color.BLUE);

                        JOptionPane.showMessageDialog(null, newInterestBox,"New Interest", JOptionPane.QUESTION_MESSAGE);

                        _interestService.getNewInterest().setIntDesc(tfNewInterest.getText());
                    }
                });

                for (Interest interest : _interestService.getInterestList()) 
                {
                    JCheckBox cbInterest = new JCheckBox(interest.getIntDesc());
                    interestBox.add(cbInterest);
                    cbInterest.addActionListener
                    (
                        new ActionListener()
                        {
                            public void actionPerformed(ActionEvent ae)
                            {
                                facultyInterests.add(interest);
                            }
                        }
                    );
                }

                JOptionPane.showMessageDialog(null, interestBox,"Interest", JOptionPane.QUESTION_MESSAGE);

                accountFaculty.setInterests(facultyInterests);
            
                //abstracts
            }

            _userService.setCurrentUser(accountFaculty);
        }
        else if(accountUser instanceof Student)
        {
            Student accountStudent = (Student) accountUser;

            JPanel studentBox = new JPanel(new GridLayout (accountStudent.getUserID() != 0 ? 7 : 6,2));

            JLabel lblUsername = new JLabel("Username");
            studentBox.add(lblUsername);
            lblUsername.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfUsername = new JTextField(accountStudent.getUsername());
            studentBox.add(tfUsername);
            tfUsername.setFont(new Font("Courier", Font.PLAIN, 32));
            tfUsername.setForeground(Color.BLUE);

            JLabel lblPassword = new JLabel("Password");
            studentBox.add(lblPassword);
            lblPassword.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfPassword = new JTextField(accountStudent.getPassword());
            studentBox.add(tfPassword);
            tfPassword.setFont(new Font("Courier", Font.PLAIN, 32));
            tfPassword.setForeground(Color.BLUE);

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

            JLabel lblDelete = new JLabel("Delete");
            if(accountStudent.getUserID() != 0) studentBox.add(lblDelete);
            lblDelete.setFont(new Font("Courier", Font.PLAIN, 32));

            JCheckBox cbDelete = new JCheckBox("");
            if(accountStudent.getUserID() != 0) studentBox.add(cbDelete);
            cbDelete.addActionListener
            (
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                        delete = true;
                    }
                }
            );

            JOptionPane.showMessageDialog(null, studentBox,"Student", JOptionPane.QUESTION_MESSAGE);

            accountStudent = new Student(accountStudent.getUserID(), accountStudent.getTypeID(), accountStudent.getUsername(), accountStudent.getPassword(), accountStudent.getSalt(), accountStudent.getEncryptedPassword(), accountStudent.getStudentID(), tfFname.getText(), tfLname.getText(), tfEmail.getText(), tfPhoneNumber.getText());

            if(!delete)
            {
                //interests

                //majors
            }

            _userService.setCurrentUser(accountStudent);
        }
        else if(accountUser instanceof Guest)
        {
            Guest accountGuest = (Guest) accountUser;

            JPanel guestBox = new JPanel(new GridLayout (accountGuest.getUserID() != 0 ? 7 : 6,2));

            JLabel lblUsername = new JLabel("Username");
            guestBox.add(lblUsername);
            lblUsername.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfUsername = new JTextField(accountGuest.getUsername());
            guestBox.add(tfUsername);
            tfUsername.setFont(new Font("Courier", Font.PLAIN, 32));
            tfUsername.setForeground(Color.BLUE);

            JLabel lblPassword = new JLabel("Password");
            guestBox.add(lblPassword);
            lblPassword.setFont(new Font("Courier", Font.PLAIN, 32));

            JTextField tfPassword = new JTextField(accountGuest.getPassword());
            guestBox.add(tfPassword);
            tfPassword.setFont(new Font("Courier", Font.PLAIN, 32));
            tfPassword.setForeground(Color.BLUE);

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

            JLabel lblDelete = new JLabel("Delete");
            if(accountGuest.getUserID() != 0) guestBox.add(lblDelete);
            lblDelete.setFont(new Font("Courier", Font.PLAIN, 32));

            JCheckBox cbDelete = new JCheckBox("");
            if(accountGuest.getUserID() != 0) guestBox.add(cbDelete);
            cbDelete.addActionListener
            (
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                        delete = true;
                    }
                }
            );

            accountGuest = new Guest(accountGuest.getUserID(), accountGuest.getTypeID(), accountGuest.getUsername(), accountGuest.getPassword(), accountGuest.getSalt(), accountGuest.getEncryptedPassword(), accountGuest.getGuestID(), tfBusiness.getText(), tfFname.getText(), tfLname.getText(), tfContactInfo.getText());

            _userService.setCurrentUser(accountGuest);
        }

        if(delete)
        {
            _userService.deleteUser();
        }
        else if(_userService.getCurrentUser().getUserID() == 0)
        {
            _userService.createUser();
        }
        else
        {
            _userService.updateUser();
        }
        
    }
}
