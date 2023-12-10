package Pages;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import Models.*;
import Services.UserService.*;
import Services.InterestService.*;
import Services.AbstractService.*;
import Services.FileService.*;
import Services.MajorService.*;

public class Account extends JFrame
{
    User accountUser = new User();
    Student accountStudent = new Student();
    IInterestService _interestService = new InterestService();
    IAbstractService _abstractService = new AbstractService();
    IFileService _fileService = new FileService();
    IMajorService _majorService = new MajorService();
    boolean delete = false;

    public Account(IUserService _userService)
    {
        super("Account");
        setSize(1000,1000);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        accountUser = _userService.getCurrentUser();

        JPanel jAccount = new JPanel();
        jAccount.setLayout(new GridLayout(1,3));
        add(jAccount);
        setVisible(true);

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
            cbDelete.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){delete = true;}});

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
                btnNewInterest.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
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
                    _interestService.createInterest();
                }});

                for (Interest interest : _interestService.getInterestList()) 
                {
                    JCheckBox cbInterest = new JCheckBox(interest.getIntDesc());
                    interestBox.add(cbInterest);
                    cbInterest.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){facultyInterests.add(interest);}});
                }

                JOptionPane.showMessageDialog(null, interestBox,"Interest", JOptionPane.QUESTION_MESSAGE);

                accountFaculty.setInterests(facultyInterests);
            
                _abstractService.getAbstracts();

                List<Abstract> facultyAbstracts = new ArrayList<>();

                JPanel abstractBox = new JPanel(new GridLayout (_abstractService.getAbstractsList().size() + 1,1));

                JLabel lblAbstract = new JLabel("Abstract");
                abstractBox.add(lblAbstract);
                lblAbstract.setFont(new Font("Courier", Font.PLAIN, 32));

                JButton btnNewAbstract = new JButton("New");
                btnNewAbstract.setFont(new Font("Courier", Font.PLAIN, 32));
                abstractBox.add(btnNewAbstract);
                btnNewAbstract.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae) 
                {
                    JPanel newAbstractBox = new JPanel(new GridLayout(1,2));

                    JLabel lblNewAbstract = new JLabel("New Abstract");
                    newAbstractBox.add(lblNewAbstract);
                    lblNewAbstract.setFont(new Font("Courier", Font.PLAIN, 32));

                    JCheckBox cbAbstract = new JCheckBox("Select File");
                    newAbstractBox.add(cbAbstract);
                    cbAbstract.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){_fileService.getFile();}});

                    JOptionPane.showMessageDialog(null, newAbstractBox,"New Abstract", JOptionPane.QUESTION_MESSAGE);

                    _abstractService.getNewAbstract().setProfessorAbstract(_fileService.getFileContent());
                    _abstractService.createAbstract();
                }});

                for (Abstract facultyAbstract : _abstractService.getAbstractsList()) 
                {
                    JCheckBox cbAbstract = new JCheckBox(facultyAbstract.getProfessorAbstract());
                    abstractBox.add(cbAbstract);
                    cbAbstract.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){facultyAbstracts.add(facultyAbstract);}});
                }

                JOptionPane.showMessageDialog(null, abstractBox,"Abstract", JOptionPane.QUESTION_MESSAGE);

                accountFaculty.setAbstracts(facultyAbstracts);
            }

            _userService.setCurrentUser(accountFaculty);
        }
        else if(accountUser instanceof Student)
        {
            accountStudent = (Student) accountUser;
            _interestService.getInterests();
            _majorService.getMajors();

            List<Interest> studentInterests = new ArrayList<>();
            List<Major> studentMajors = new ArrayList<>();

            jAccount.setLayout(new GridLayout (_interestService.getInterestList().size() + _majorService.getMajorsList().size() + 7,2));

            JLabel lblUsername = new JLabel("Username");
            JTextField tfUsername = new JTextField(accountStudent.getUsername());
            JLabel lblPassword = new JLabel("Password");
            JTextField tfPassword = new JTextField(accountStudent.getPassword());
            JLabel lblFname = new JLabel("First Name");
            JTextField tfFname = new JTextField(accountStudent.getFname());
            JLabel lblLname = new JLabel("Last Name");
            JTextField tfLname = new JTextField(accountStudent.getLname());
            JLabel lblEmail = new JLabel("Email");
            JTextField tfEmail = new JTextField(accountStudent.getEmail());
            JLabel lblPhoneNumber = new JLabel("Phone Number");
            JTextField tfPhoneNumber = new JTextField(accountStudent.getPhoneNumber());
            JLabel lblInterest = new JLabel("Interest");
            JButton btnNewInterest = new JButton("New");
            JLabel lblMajor = new JLabel("Major");
            JButton btnNewMajor = new JButton("New"); 
            JButton btnDelete = new JButton("Delete");
            JButton btnDone = new JButton("Done");

            lblUsername.setFont(new Font("Courier", Font.PLAIN, 32));
            tfUsername.setFont(new Font("Courier", Font.PLAIN, 32));
            lblPassword.setFont(new Font("Courier", Font.PLAIN, 32));
            tfPassword.setFont(new Font("Courier", Font.PLAIN, 32));
            lblFname.setFont(new Font("Courier", Font.PLAIN, 32));
            tfFname.setFont(new Font("Courier", Font.PLAIN, 32));
            lblLname.setFont(new Font("Courier", Font.PLAIN, 32));
            tfLname.setFont(new Font("Courier", Font.PLAIN, 32));
            lblEmail.setFont(new Font("Courier", Font.PLAIN, 32));
            tfEmail.setFont(new Font("Courier", Font.PLAIN, 32));
            lblPhoneNumber.setFont(new Font("Courier", Font.PLAIN, 32));
            tfPhoneNumber.setFont(new Font("Courier", Font.PLAIN, 32));
            lblInterest.setFont(new Font("Courier", Font.PLAIN, 32));
            btnNewInterest.setFont(new Font("Courier", Font.PLAIN, 32));
            lblMajor.setFont(new Font("Courier", Font.PLAIN, 32));
            btnNewMajor.setFont(new Font("Courier", Font.PLAIN, 32));
            btnDelete.setFont(new Font("Courier", Font.PLAIN, 32));
            btnDone.setFont(new Font("Courier", Font.PLAIN, 32));

            tfUsername.setForeground(Color.BLUE);
            tfPassword.setForeground(Color.BLUE);
            tfFname.setForeground(Color.BLUE);
            tfLname.setForeground(Color.BLUE);
            tfEmail.setForeground(Color.BLUE);
            tfPhoneNumber.setForeground(Color.BLUE);

            jAccount.add(lblUsername);
            jAccount.add(tfUsername);
            jAccount.add(lblPassword);
            jAccount.add(tfPassword);
            jAccount.add(lblFname);
            jAccount.add(tfFname);
            jAccount.add(lblLname);
            jAccount.add(tfLname);
            jAccount.add(lblEmail);
            jAccount.add(tfEmail);
            jAccount.add(lblPhoneNumber);
            jAccount.add(tfPhoneNumber);
            
            jAccount.add(lblInterest);
            jAccount.add(btnNewInterest);
            for (Interest interest : _interestService.getInterestList()) 
            {
                JCheckBox cbInterest = new JCheckBox(interest.getIntDesc());
                jAccount.add(cbInterest);
                cbInterest.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){studentInterests.add(interest);}});
            }
            if(_interestService.getInterestList().size() % 2 == 1) jAccount.add(new JLabel(""));

            jAccount.add(lblMajor);
            jAccount.add(btnNewMajor);
            for (Major major : _majorService.getMajorsList()) 
            {
                JCheckBox cbMajor = new JCheckBox(major.getMajorDescription());
                jAccount.add(cbMajor);
                cbMajor.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){studentMajors.add(major);}});
            }
            if(_majorService.getMajorsList().size() % 2 == 1) jAccount.add(new JLabel(""));

            jAccount.add(btnDelete);
            jAccount.add(btnDone);

            btnNewInterest.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae) 
            {
                _interestService.getNewInterest().setIntDesc(null);
                _interestService.getNewInterest().setInterestID(0);

                JPanel newInterestBox = new JPanel(new GridLayout(1,2));

                JLabel lblNewInterest = new JLabel("New Interest");
                JTextField tfNewInterest = new JTextField("");

                lblNewInterest.setFont(new Font("Courier", Font.PLAIN, 32));
                tfNewInterest.setFont(new Font("Courier", Font.PLAIN, 32));

                tfNewInterest.setForeground(Color.BLUE);

                newInterestBox.add(lblNewInterest);
                newInterestBox.add(tfNewInterest);
                
                JOptionPane.showMessageDialog(null, newInterestBox,"New Interest", JOptionPane.QUESTION_MESSAGE);

                _interestService.getNewInterest().setIntDesc(tfNewInterest.getText());
                _interestService.createInterest();

                jAccount.removeAll();
                jAccount.setLayout(new GridLayout (_interestService.getInterestList().size() + _majorService.getMajorsList().size() + 7,2));
                jAccount.add(lblUsername);
                jAccount.add(tfUsername);
                jAccount.add(lblPassword);
                jAccount.add(tfPassword);
                jAccount.add(lblFname);
                jAccount.add(tfFname);
                jAccount.add(lblLname);
                jAccount.add(tfLname);
                jAccount.add(lblEmail);
                jAccount.add(tfEmail);
                jAccount.add(lblPhoneNumber);
                jAccount.add(tfPhoneNumber);
                
                jAccount.add(lblInterest);
                jAccount.add(btnNewInterest);
                for (Interest interest : _interestService.getInterestList()) 
                {
                    JCheckBox cbInterest = new JCheckBox(interest.getIntDesc());
                    jAccount.add(cbInterest);
                    cbInterest.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){studentInterests.add(interest);}});
                }
                if(_interestService.getInterestList().size() % 2 == 1) jAccount.add(new JLabel(""));

                jAccount.add(lblMajor);
                jAccount.add(btnNewMajor);
                for (Major major : _majorService.getMajorsList()) 
                {
                    JCheckBox cbMajor = new JCheckBox(major.getMajorDescription());
                    jAccount.add(cbMajor);
                    cbMajor.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){studentMajors.add(major);}});
                }
                if(_majorService.getMajorsList().size() % 2 == 1) jAccount.add(new JLabel(""));

                jAccount.add(btnDelete);
                jAccount.add(btnDone);
            }});

            btnNewMajor.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae) 
                {
                    _majorService.getNewMajor().setMajorDescription(null);
                    _majorService.getNewMajor().setMajorID(0);

                    JPanel newMajorBox = new JPanel(new GridLayout(1,2));

                    JLabel lblNewMajor = new JLabel("New Major");
                    JTextField tfNewMajor = new JTextField("");

                    lblNewMajor.setFont(new Font("Courier", Font.PLAIN, 32));
                    tfNewMajor.setFont(new Font("Courier", Font.PLAIN, 32));

                    tfNewMajor.setForeground(Color.BLUE);

                    newMajorBox.add(lblNewMajor);
                    newMajorBox.add(tfNewMajor);

                    JOptionPane.showMessageDialog(null, newMajorBox,"New Major", JOptionPane.QUESTION_MESSAGE);

                    _majorService.getNewMajor().setMajorDescription(tfNewMajor.getText());
                    _majorService.createMajor();

                    jAccount.removeAll();
                    jAccount.setLayout(new GridLayout (_interestService.getInterestList().size() + _majorService.getMajorsList().size() + 7,2));
                    jAccount.add(lblUsername);
                    jAccount.add(tfUsername);
                    jAccount.add(lblPassword);
                    jAccount.add(tfPassword);
                    jAccount.add(lblFname);
                    jAccount.add(tfFname);
                    jAccount.add(lblLname);
                    jAccount.add(tfLname);
                    jAccount.add(lblEmail);
                    jAccount.add(tfEmail);
                    jAccount.add(lblPhoneNumber);
                    jAccount.add(tfPhoneNumber);
                    
                    jAccount.add(lblInterest);
                    jAccount.add(btnNewInterest);
                    for (Interest interest : _interestService.getInterestList()) 
                    {
                        JCheckBox cbInterest = new JCheckBox(interest.getIntDesc());
                        jAccount.add(cbInterest);
                        cbInterest.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){studentInterests.add(interest);}});
                    }
                    if(_interestService.getInterestList().size() % 2 == 1) jAccount.add(new JLabel(""));

                    jAccount.add(lblMajor);
                    jAccount.add(btnNewMajor);
                    for (Major major : _majorService.getMajorsList()) 
                    {
                        JCheckBox cbMajor = new JCheckBox(major.getMajorDescription());
                        jAccount.add(cbMajor);
                        cbMajor.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){studentMajors.add(major);}});
                    }
                    if(_majorService.getMajorsList().size() % 2 == 1) jAccount.add(new JLabel(""));

                    jAccount.add(btnDelete);
                    jAccount.add(btnDone);
                }
            });

            btnDelete.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
            {
                _userService.deleteUser();
                new Index(_userService);
                setVisible(false);
            }});

            btnDone.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
            {
                accountStudent = new Student(accountStudent.getUserID(), accountStudent.getTypeID(), accountStudent.getUsername(), accountStudent.getPassword(), accountStudent.getSalt(), accountStudent.getEncryptedPassword(), accountStudent.getStudentID(), tfFname.getText(), tfLname.getText(), tfEmail.getText(), tfPhoneNumber.getText());
                accountStudent.setInterests(studentInterests);
                accountStudent.setMajors(studentMajors);
                _userService.setCurrentUser(accountStudent);
                if(_userService.getCurrentUser().getUserID() == 0)
                {
                    _userService.createUser();
                }
                else
                {
                    _userService.updateUser();
                }
                setVisible(false);
            }});
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
            cbDelete.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){delete = true;}});

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
