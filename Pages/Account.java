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
    Faculty accountFaculty = new Faculty();
    Student accountStudent = new Student();
    Guest accountGuest = new Guest();
    IInterestService _interestService = new InterestService();
    IAbstractService _abstractService = new AbstractService();
    IFileService _fileService = new FileService();
    IMajorService _majorService = new MajorService();

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
            accountFaculty = (Faculty) accountUser;
            _interestService.getInterests();
            _abstractService.getAbstracts();

            List<Interest> facultyInterests = new ArrayList<>();
            List<Abstract> facultyAbstracts = new ArrayList<>();

            jAccount.setLayout(new GridLayout (_interestService.getInterestList().size() + _abstractService.getAbstractsList().size() + 8,2));

            JLabel lblUsername = new JLabel("Username");
            JTextField tfUsername = new JTextField(accountFaculty.getUsername());
            JLabel lblPassword = new JLabel("Password");
            JTextField tfPassword = new JTextField(accountFaculty.getPassword());
            JLabel lblFname = new JLabel("First Name");
            JTextField tfFname = new JTextField(accountFaculty.getFname());
            JLabel lblLname = new JLabel("Last Name");
            JTextField tfLname = new JTextField(accountFaculty.getLname());
            JLabel lblEmail = new JLabel("Email");
            JTextField tfEmail = new JTextField(accountFaculty.getEmail());
            JLabel lblPhoneNumber = new JLabel("Phone Number");
            JTextField tfPhoneNumber = new JTextField(accountFaculty.getPhoneNumber());
            JLabel lblLocation = new JLabel("Location");
            JTextField tfLocation = new JTextField(accountFaculty.getLocation());
            JLabel lblInterest = new JLabel("Interest");
            JButton btnNewInterest = new JButton("New");
            JLabel lblAbstract = new JLabel("Abstract");
            JButton btnNewAbstract = new JButton("New"); 
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
            lblLocation.setFont(new Font("Courier", Font.PLAIN, 32));
            tfLocation.setFont(new Font("Courier", Font.PLAIN, 32));
            lblInterest.setFont(new Font("Courier", Font.PLAIN, 32));
            btnNewInterest.setFont(new Font("Courier", Font.PLAIN, 32));
            lblAbstract.setFont(new Font("Courier", Font.PLAIN, 32));
            btnNewAbstract.setFont(new Font("Courier", Font.PLAIN, 32));
            btnDelete.setFont(new Font("Courier", Font.PLAIN, 32));
            btnDone.setFont(new Font("Courier", Font.PLAIN, 32));

            tfUsername.setForeground(Color.BLUE);
            tfPassword.setForeground(Color.BLUE);
            tfFname.setForeground(Color.BLUE);
            tfLname.setForeground(Color.BLUE);
            tfEmail.setForeground(Color.BLUE);
            tfPhoneNumber.setForeground(Color.BLUE);
            tfLocation.setForeground(Color.BLUE);

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
            jAccount.add(lblLocation);
            jAccount.add(tfLocation);

            jAccount.add(lblInterest);
            jAccount.add(btnNewInterest);
            for (Interest interest : _interestService.getInterestList()) 
            {
                JCheckBox cbInterest = new JCheckBox(interest.getIntDesc());
                jAccount.add(cbInterest);
                cbInterest.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){facultyInterests.add(interest);}});
            }
            if(_interestService.getInterestList().size() % 2 == 1) jAccount.add(new JLabel(""));

            jAccount.add(lblAbstract);
            jAccount.add(btnNewAbstract);
            for (Abstract facultyAbstract : _abstractService.getAbstractsList()) 
            {
                JCheckBox cbAbstract = new JCheckBox(facultyAbstract.getProfessorAbstract());
                jAccount.add(cbAbstract);
                cbAbstract.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){facultyAbstracts.add(facultyAbstract);}});
            }
            if(_abstractService.getAbstractsList().size() % 2 == 1) jAccount.add(new JLabel(""));

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
                jAccount.setLayout(new GridLayout (_interestService.getInterestList().size() + _abstractService.getAbstractsList().size() + 7,2));
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
                jAccount.add(lblLocation);
                jAccount.add(tfLocation);

                jAccount.add(lblInterest);
                jAccount.add(btnNewInterest);
                for (Interest interest : _interestService.getInterestList()) 
                {
                    JCheckBox cbInterest = new JCheckBox(interest.getIntDesc());
                    jAccount.add(cbInterest);
                    cbInterest.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){facultyInterests.add(interest);}});
                }
                if(_interestService.getInterestList().size() % 2 == 1) jAccount.add(new JLabel(""));

                jAccount.add(lblAbstract);
                jAccount.add(btnNewAbstract);
                for (Abstract facultyAbstract : _abstractService.getAbstractsList()) 
                {
                    JCheckBox cbAbstract = new JCheckBox(facultyAbstract.getProfessorAbstract());
                    jAccount.add(cbAbstract);
                    cbAbstract.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){facultyAbstracts.add(facultyAbstract);}});
                }
                if(_abstractService.getAbstractsList().size() % 2 == 1) jAccount.add(new JLabel(""));

                jAccount.add(btnDelete);
                jAccount.add(btnDone);
            }});

            btnNewAbstract.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
            {
                JPanel newAbstractBox = new JPanel(new GridLayout(1,2));

                JLabel lblNewAbstract = new JLabel("New Abstract");
                JButton btnFile = new JButton("Select File");

                lblNewAbstract.setFont(new Font("Courier", Font.PLAIN, 32));
                btnFile.setFont(new Font("Courier", Font.PLAIN, 32));

                newAbstractBox.add(lblNewAbstract);
                newAbstractBox.add(btnFile);

                btnFile.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){_fileService.getFile();}});

                JOptionPane.showMessageDialog(null, newAbstractBox,"New Abstract", JOptionPane.QUESTION_MESSAGE);

                _abstractService.getNewAbstract().setProfessorAbstract(_fileService.getFileContent());
                _abstractService.createAbstract();

                jAccount.removeAll();
                jAccount.setLayout(new GridLayout (_interestService.getInterestList().size() + _abstractService.getAbstractsList().size() + 7,2));
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
                jAccount.add(lblLocation);
                jAccount.add(tfLocation);

                jAccount.add(lblInterest);
                jAccount.add(btnNewInterest);
                for (Interest interest : _interestService.getInterestList()) 
                {
                    JCheckBox cbInterest = new JCheckBox(interest.getIntDesc());
                    jAccount.add(cbInterest);
                    cbInterest.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){facultyInterests.add(interest);}});
                }
                if(_interestService.getInterestList().size() % 2 == 1) jAccount.add(new JLabel(""));

                jAccount.add(lblAbstract);
                jAccount.add(btnNewAbstract);
                for (Abstract facultyAbstract : _abstractService.getAbstractsList()) 
                {
                    JCheckBox cbAbstract = new JCheckBox(facultyAbstract.getProfessorAbstract());
                    jAccount.add(cbAbstract);
                    cbAbstract.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){facultyAbstracts.add(facultyAbstract);}});
                }
                if(_abstractService.getAbstractsList().size() % 2 == 1) jAccount.add(new JLabel(""));

                jAccount.add(btnDelete);
                jAccount.add(btnDone);
            }});

            btnDelete.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
            {
                _userService.deleteUser();
                new Index(_userService);
                setVisible(false);
            }});

            btnDone.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
            {
                accountFaculty = new Faculty(accountFaculty.getUserID(), accountFaculty.getTypeID(), tfUsername.getText(), tfPassword.getText(), accountFaculty.getSalt(), accountFaculty.getEncryptedPassword(), accountFaculty.getFacultyID(), tfFname.getText(), tfLname.getText(), tfEmail.getText(), tfPhoneNumber.getText(), tfLocation.getText());
                accountFaculty.setInterests(facultyInterests);
                accountFaculty.setAbstracts(facultyAbstracts);
                _userService.setCurrentUser(accountFaculty);
                if(_userService.getCurrentUser().hasNullField())
                {
                    JOptionPane.showMessageDialog(null, "Please fill out all fields","Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    if(_userService.getCurrentUser().getUserID() == 0)
                    {
                        _userService.createUser();
                    }
                    else
                    {
                        _userService.updateUser();
                    }
                    new Home(_userService);
                    setVisible(false);
                }
            }});
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

            btnNewMajor.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae) 
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
            }});

            btnDelete.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
            {
                _userService.deleteUser();
                new Index(_userService);
                setVisible(false);
            }});

            btnDone.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
            {
                accountStudent = new Student(accountStudent.getUserID(), accountStudent.getTypeID(), tfUsername.getText(), tfPassword.getText(), accountStudent.getSalt(), accountStudent.getEncryptedPassword(), accountStudent.getStudentID(), tfFname.getText(), tfLname.getText(), tfEmail.getText(), tfPhoneNumber.getText());
                accountStudent.setInterests(studentInterests);
                accountStudent.setMajors(studentMajors);
                _userService.setCurrentUser(accountStudent);
                if(_userService.getCurrentUser().hasNullField())
                {
                    JOptionPane.showMessageDialog(null, "Please fill out all fields","Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    if(_userService.getCurrentUser().getUserID() == 0)
                    {
                        _userService.createUser();
                    }
                    else
                    {
                        _userService.updateUser();
                    }
                    new Home(_userService);
                    setVisible(false);
                }
            }});
        }
        else if(accountUser instanceof Guest)
        {
            accountGuest = (Guest) accountUser;

            jAccount.setLayout((new GridLayout (7,2)));

            JLabel lblUsername = new JLabel("Username");
            JTextField tfUsername = new JTextField(accountGuest.getUsername());
            JLabel lblPassword = new JLabel("Password");
            JTextField tfPassword = new JTextField(accountGuest.getPassword());
            JLabel lblBusiness = new JLabel("Business");
            JTextField tfBusiness = new JTextField(accountGuest.getBusiness());
            JLabel lblFname = new JLabel("First Name");
            JTextField tfFname = new JTextField(accountGuest.getFname());
            JLabel lblLname = new JLabel("Last Name");
            JTextField tfLname = new JTextField(accountGuest.getLname());
            JLabel lblContactInfo = new JLabel("Contact Info");
            JTextField tfContactInfo = new JTextField(accountGuest.getContactInfo()); 
            JButton btnDelete = new JButton("Delete");
            JButton btnDone = new JButton("Done");

            lblUsername.setFont(new Font("Courier", Font.PLAIN, 32));
            tfUsername.setFont(new Font("Courier", Font.PLAIN, 32));
            lblPassword.setFont(new Font("Courier", Font.PLAIN, 32));
            tfPassword.setFont(new Font("Courier", Font.PLAIN, 32));
            lblBusiness.setFont(new Font("Courier", Font.PLAIN, 32));
            tfBusiness.setFont(new Font("Courier", Font.PLAIN, 32));
            lblFname.setFont(new Font("Courier", Font.PLAIN, 32));
            tfFname.setFont(new Font("Courier", Font.PLAIN, 32));
            lblLname.setFont(new Font("Courier", Font.PLAIN, 32));
            tfLname.setFont(new Font("Courier", Font.PLAIN, 32));
            lblContactInfo.setFont(new Font("Courier", Font.PLAIN, 32));
            tfContactInfo.setFont(new Font("Courier", Font.PLAIN, 32));
            btnDelete.setFont(new Font("Courier", Font.PLAIN, 32));
            btnDone.setFont(new Font("Courier", Font.PLAIN, 32));

            tfUsername.setForeground(Color.BLUE);
            tfPassword.setForeground(Color.BLUE);
            tfBusiness.setForeground(Color.BLUE);
            tfFname.setForeground(Color.BLUE);
            tfLname.setForeground(Color.BLUE);
            tfContactInfo.setForeground(Color.BLUE);

            jAccount.add(lblUsername);
            jAccount.add(tfUsername);
            jAccount.add(lblPassword);
            jAccount.add(tfPassword);
            jAccount.add(lblBusiness);
            jAccount.add(tfBusiness);
            jAccount.add(lblFname);
            jAccount.add(tfFname);
            jAccount.add(lblLname);
            jAccount.add(tfLname);
            jAccount.add(lblContactInfo);
            jAccount.add(tfContactInfo);
            jAccount.add(btnDelete);
            jAccount.add(btnDone);

            btnDelete.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
            {
                _userService.deleteUser();
                new Index(_userService);
                setVisible(false);
            }});

            btnDone.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae)
            {
                accountGuest = new Guest(accountGuest.getUserID(), accountGuest.getTypeID(), tfUsername.getText(), tfPassword.getText(), accountGuest.getSalt(), accountGuest.getEncryptedPassword(), accountGuest.getGuestID(), tfBusiness.getText(), tfFname.getText(), tfLname.getText(), tfContactInfo.getText());
                _userService.setCurrentUser(accountGuest);
                if(_userService.getCurrentUser().hasNullField())
                {
                    JOptionPane.showMessageDialog(null, "Please fill out all fields","Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    if(_userService.getCurrentUser().getUserID() == 0)
                    {
                        _userService.createUser();
                    }
                    else
                    {
                        _userService.updateUser();
                    }
                    new Home(_userService);
                    setVisible(false);
                }
            }});
        }
    }
}
